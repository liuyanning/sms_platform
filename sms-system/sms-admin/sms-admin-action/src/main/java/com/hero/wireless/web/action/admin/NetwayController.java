package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.ChannelFee;
import com.hero.wireless.web.service.INetwayManage;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.ChannelUtil.OtherParameter;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/")
public class NetwayController extends BaseAdminController {

	@Resource(name = "netwayManage")
	private INetwayManage netwayManage;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setAutoGrowNestedPaths(true);
        dataBinder.setAutoGrowCollectionLimit(1024);
		dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * admin-->通道列表
	 */
	@RequestMapping("netway_channelList")
	@ResponseBody
	public String channelList(Channel channel) {
		List<Channel> channelList = netwayManage.queryChannelList(channel);
		return new LayUiObjectMapper().asSuccessString(channelList, channel.getPagination().getTotalCount());
	}

	@RequestMapping("netway_channelSave")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道管理", option = "添加通道")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"netway_channelSave")
	public LayUiJsonObjectFmt channelSave(Channel channel) {
		try {
			this.netwayManage.addChannel(channel);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			return LayuiResultUtil.error(e);
		}
		return LayuiResultUtil.success();
	}

	/**
	 * 修改前置
	 *
	 * @return
	 */
	@RequestMapping("netway_preEditChannel")
	public String preEditCmpp(@RequestParam(name = "ckNos") List<String> ckNos) {
		if (ckNos == null || ckNos.size() == 0) {
			return "/netway/channel_edit";
		}
		Channel channel = DatabaseCache.getChannelByNo(ckNos.get(0));
		if (channel != null) {
			request.setAttribute("channel", channel);
		}
		return "/netway/channel_edit";
	}

	/**
	 * 修改保存
	 *
	 * @return
	 */
	@RequestMapping("netway_editChannel")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道管理", option = "修改通道")
	public LayUiJsonObjectFmt editChannel(Channel channel) {
		netwayManage.editChannel(channel);
		return LayuiResultUtil.success();
	}

	/**
	 * 修改参数配置前置
	 *
	 * @return
	 */
	@RequestMapping("netway_preEditChannelParameters")
	public String preEditChannelParameters(@RequestParam(name = "ckNos") List<String> ckNos) {
		Channel channel = DatabaseCache.getChannelByNo(ckNos.get(0));
		Map<String, OtherParameter> parameterMap = ChannelUtil.getParameter(channel);
		if (channel != null) {
			request.setAttribute("channel", channel);
			request.setAttribute("reMap", parameterMap);
		}
		return "/netway/" + channel.getProtocol_Type_Code() + "_parameters_config";
	}

	/**
	 * 修改参数配置
	 *
	 * @return
	 */
	@RequestMapping("netway_editChannelParameters")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道管理", option = "修改参数配置")
	public LayUiJsonObjectFmt editChannelParameters(@RequestParam Map<String, String> parMap) {
		netwayManage.updateChannelByPrimaryKey(parMap);
		return LayuiResultUtil.success();
	}

	@RequestMapping("netway_channelBalance")
	public String channelBalance(String no) {
		try {
			String result = this.netwayManage.channelBalance(no);
			try {
				request.setAttribute("res", result);
			} catch (Exception e) {
				request.setAttribute("res", e.getMessage());
			}
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
		return "/netway/http_balance";
	}

	@RequestMapping("netway_editChannelStatus")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道管理", option = "修改通道状态")
	public LayUiJsonObjectFmt editCmppStatus(@RequestParam(name = "ckIds") List<Integer> ckIds, String status) {
		netwayManage.editChannelStatus(ckIds, status);
		return LayuiResultUtil.success();
	}

	@RequestMapping("netway_try2Try")
	@ResponseBody
	public LayUiJsonObjectFmt try2Try(String channelNo, String phoneNos
            , String content, String subCode, String countryCode) {
		this.netwayManage.try2Try(channelNo, phoneNos, content, subCode,countryCode);
		return LayuiResultUtil.success();
	}

	@RequestMapping("netway_channelFeeIndex")
	public String channelFeeIndex(String limitCode, String ckNos) {
		request.setAttribute("limitCode",limitCode);
		request.setAttribute("channelNo",ckNos);
        return "/netway/channel_fee_list";
	}

	/**
	 * 资费列表
	 *
	 */
	@RequestMapping("netway_channelFeeList")
	@ResponseBody
	public String channelFeeList(ChannelFee channelFee) {
		List<ChannelFee> channelFeeList = this.netwayManage.queryChannelFeeList(channelFee);
		return new LayUiObjectMapper().asSuccessString(channelFeeList, channelFee.getPagination().getTotalCount());
	}


	/**
	 * 保存资费
	 *
	 */
	@RequestMapping("netway_addChannelFee")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道管理", option = "添加资费")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"netway_addChannelFee")
	public LayUiJsonObjectFmt addChannelFee(ChannelFee channelFee) {
	    try {
            this.netwayManage.addChannelFee(channelFee);
            return LayuiResultUtil.success();
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
	        SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
	}

	/**
	 * 资费修改前置
	 *
	 */
	@RequestMapping("netway_preEditChannelFee")
	public ModelAndView preEditChannelFee(BaseParamEntity entity) {
		ModelAndView mv = new ModelAndView();
		ChannelFee channelFee = new ChannelFee();
		channelFee.setId(entity.getCkIds().get(0));
		channelFee = this.netwayManage.queryChannelFeeList(channelFee).get(0);
        mv.setViewName("/netway/channel_fee_edit");
		mv.addObject("channelFee", channelFee);
		return mv;
	}

	/**
	 * 资费修改
	 *
	 */
	@RequestMapping("netway_editChannelFee")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道管理", option = "资费修改")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"netway_editChannelFee")
	public LayUiJsonObjectFmt editChannelFee(ChannelFee channelFee) {
        try {
            this.netwayManage.editChannelFee(channelFee);
            return LayuiResultUtil.success();
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
	}
	/**
	 * 删除资费
	 *
	 */
	@RequestMapping("netway_delChannelFee")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道管理", option = "删除资费")
	public LayUiJsonObjectFmt delChannelFee(@RequestParam(value ="ckIds")List<Integer> ckIds) {
		this.netwayManage.delChannelFee(ckIds);
		return LayuiResultUtil.success();
	}

    /**
     * 通道产品=》通道管理=》区域配置前置
     */
    @RequestMapping("netway_preEditChannelAreaCode")
    public String preEditChannelAreaCode(BaseParamEntity entity) {
        Channel channel = new Channel();
        channel.setId(entity.getCkIds().get(0));
        request.setAttribute("channel",netwayManage.queryChannelList(channel).get(0));
        request.setAttribute("locationCode", DatabaseCache.getCodeListBySortCode("location"));
        return "/netway/channel_area_limit";
    }


    /**
     * 通道产品=》通道管理=》区域配置保存
     */
    @RequestMapping("netway_editChannelAreaCode")
    @ResponseBody
    public LayUiJsonObjectFmt editChannelAreaCode(Channel channel) {
        try {
            netwayManage.editChannel(channel);
            return LayuiResultUtil.success();
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.fail("保存失败");
        }
    }

	/**
	 * 通道提交速度
	 * @return
	 */
	@RequestMapping("netway_preSubmitSpeed")
	public String preNetwaySubmitSpeed(BaseParamEntity entity) {
		List<Integer> ckIds = entity.getCkIds();
		request.setAttribute("ckIds",ckIds);
		return "/netway/channel_submit_speed";
	}
}