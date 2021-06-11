package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.send.ReportNotify;
import com.hero.wireless.web.entity.send.ext.SubmitExt;

import java.util.List;

/**
 * 
 * 客户通知记录
 * 
 *IReportNotifyService.java
 *
 * @author wjl
 * @date 2020年1月10日下午4:36:35
 */
public interface IReportNotifyService {

	/**
	 * 
	 * 批量保存通知记录
	 * 
	 * @param reportNotifyList
	 * @return
	 */
	int batchInsertReportNotify(List<ReportNotify> reportNotifyList);

	/**
	 * 通过Submit获取对应的ReportNotify
	 *
	 * @param submit
	 * @return
	 */
	List<ReportNotify> queryReportNotifyListBySubmit(SubmitExt submit);

}
