package com.hero.wireless.web.service;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.sharding.ShardingKeyGenerator;
import com.hero.wireless.web.dao.send.IReportNotifyDAO;
import com.hero.wireless.web.dao.send.ext.IReportNotifyExtDAO;
import com.hero.wireless.web.entity.base.ShardingBatchInsert;
import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.entity.send.ReportNotifyExample;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import com.hero.wireless.web.util.SMSUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.drondea.wireless.util.DateTime.Y_M_D_2;

@Service("reportNotifyService")
public class ReportNotifyServiceImpl implements IReportNotifyService {
	
	@Resource(name = "IReportNotifyDAO")
	IReportNotifyDAO<ReportNotify> reportNotifyDAO;
    @Resource(name = "reportNotifyExtDAO")
	IReportNotifyExtDAO reportNotifyExtDAO;

	@Override
	public int batchInsertReportNotify(List<ReportNotify> reportNotifyList) {
		if(ObjectUtils.isEmpty(reportNotifyList)) {
			throw new ServiceException("插入失败，reportNotifyList is null");
		}
		try {
            //按天拆分
            List<ShardingBatchInsert<ReportNotify>> batchInsert = SMSUtil.getBatchInsert(reportNotifyList, (item) -> {
                item.setId(ShardingKeyGenerator.getKey());
                return DateTime.getString(item.getSubmit_Date(), Y_M_D_2);
            });
            for (ShardingBatchInsert<ReportNotify> insert : batchInsert) {
                reportNotifyExtDAO.insertListSharding(insert);
            }
            return reportNotifyList.size();
		} catch(Exception e) {
			SuperLogger.error("reportNotifyList插入异常：尝试单挑插入", e);
			reportNotifyList.forEach(item -> {
				try {
					reportNotifyDAO.insertSelective(item);
				} catch (Exception ec) {
					SuperLogger.error("reportNotifyList单条插入异常：", item);
				}
			});
			return reportNotifyList.size();
		}
	}

    @Override
    public List<ReportNotify> queryReportNotifyListBySubmit(SubmitExt condition) {
        ReportNotifyExample example = new ReportNotifyExample();
        ReportNotifyExample.Criteria cri = example.createCriteria();
        if (StringUtils.isNotBlank(condition.getChannel_Msg_Id())) {
            cri.andChannel_Msg_IdEqualTo(condition.getChannel_Msg_Id());
        }else {
            cri.andChannel_Msg_IdIsNull();
        }
        if (condition.getMinSubmitDate() != null) {
            cri.andSubmit_DateBetween(condition.getMinSubmitDate(), condition.getMaxSubmitDate());
        }
        if (StringUtils.isNotBlank(condition.getMsg_Batch_No())) {
            cri.andMsg_Batch_NoEqualTo(condition.getMsg_Batch_No());
        }else {
            cri.andMsg_Batch_NoIsNull();
        }
        if (StringUtils.isNotBlank(condition.getPhone_No())) {
            cri.andPhone_NoEqualTo(condition.getPhone_No());
        }else {
            cri.andPhone_NoIsNull();
        }
        return this.reportNotifyDAO.selectByExample(example);
    }

}
