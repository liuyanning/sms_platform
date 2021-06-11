package com.hero.wireless.sms.sender.service;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.MessageType;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 接口业务
 * 
 * @author 张丽阳
 * 
 */
@Service("httpService")
public class HttpServiceImpl extends AbstractSenderService {

	public boolean submit(Submit submit) throws Exception {
		try {
			Channel channel = DatabaseCache.getChannelCachedByNo(submit.getChannel_No());
			AbstractHttp abstractSubmit = (AbstractHttp) Class
					.forName(ChannelUtil.getParameter(channel, "full_class_impl", "")).newInstance();
			submit.setChannel_Msg_Id(CodeUtil.buildMsgNo());
			submit.setSubmit_Date(new Date());//提交时间
			submit.setCreate_Date(new Date());
			SubmitStatus submitStatus = abstractSubmit.submit(submit);
            submit.setSubmit_Response_Date(new Date());//响应时间
			submit.setSubmit_Status_Code(submitStatus.toString());
			submit.setChannel_Msg_Id(
					StringUtils.defaultIfEmpty(submit.getChannel_Msg_Id(), CodeUtil.buildMsgNo()));
			submit.setSP_Number(channel.getSp_Number());
			submit.setSender_Local_IP(channel.getSender_Local_IP());
			submit.setGroup_Code(channel.getGroup_Code());
			if (!SubmitStatus.SUCCESS.equals(submitStatus)) {
				SMSUtil.saveFailedReports(submit, true);
				return false;
			}
			
			List<Submit> submits = saveRedisData(submit);
			QueueUtil.saveSubmit(submits);
			return true;
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			if (null == submit) {
				return false;
			}
            submit.setSubmit_Date(submit.getSubmit_Date() == null?
                    new Date():submit.getSubmit_Date());//提交时间
            submit.setSubmit_Response_Date(new Date());//响应时间
			SMSUtil.saveFailedReports(submit, true);
		}
		return false;
	}

	private List<Submit> saveRedisData(Submit submit) {
		List<Submit> submits = new ArrayList<>();
		String enterpriseMsgId = submit.getEnterprise_Msg_Id();
		String[] msgIds = null;
		if (StringUtils.isNotEmpty(enterpriseMsgId)) {
			msgIds = enterpriseMsgId.split(",");
		}
		String[] finalMsgIds = msgIds;
		Arrays.asList(submit.getPhone_No().split(Constant.MUTL_MOBILE_SPLIT)).forEach(item -> {
			String[] contents;
			if (MessageType.SMS.toString().equals(submit.getMessage_Type_Code())) {
				contents = SMSUtil.splitContent(submit.getContent());
			} else {
				contents = new String[1];
				contents[0] = submit.getContent();
			}

			List<String> fragments = Arrays.asList(contents);
			for(int i = 0; i < fragments.size(); i++){
				Submit signle = new Submit();
				CopyUtil.SUBMIT_COPIER.copy(submit, signle, null);
				signle.setPhone_No(item);
				signle = SMSUtil.buildSubmitAreaAndOperator(signle);
				signle.setContent(fragments.get(i));
				signle.setContent_Length(fragments.get(i).length());
				signle.setSequence(i + 1);
				if (finalMsgIds != null && i < finalMsgIds.length) {
					signle.setEnterprise_Msg_Id(finalMsgIds[i]);
				}
				if (i == (fragments.size() - 1)) {
					//保存到本地缓存
					String key = CacheKeyUtil.genNewCacheSubmitedKey(submit.getChannel_Msg_Id(), item);
					SubmitCacheService.saveSubmit2Local(key, signle);
				} else {
					signle.setChannel_Msg_Id(submit.getChannel_Msg_Id() + "_" + signle.getSequence());
					String key = CacheKeyUtil.genNewCacheSubmitedKey(submit.getChannel_Msg_Id() + "_" + signle.getSequence(), item);
					SubmitCacheService.saveSubmit2Local(key, signle);
				}
				submits.add(signle);
			}
		});
		return submits;
	}


}
