package com.hero.wireless.web.service;

import com.hero.wireless.web.entity.business.ChargeOrder;
import com.hero.wireless.web.entity.business.Invoice;
import com.hero.wireless.web.entity.business.ext.*;

import org.springframework.cache.annotation.CacheEvict;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 充值管理
 * 
 * @author Administrator
 *
 */
public interface IChargeManage {

	/**
	 * 充值
	 * 
	 * @param data
	 * @return
	 */
	ChargeOrder charge(ChargeOrder data);

	/**
	 * 查询需要审核的订单
	 * 
	 * @return
	 */
	List<ChargeOrder> queryApproveChargeOrderList(ChargeOrder condition);

	/**
	 * 查询订单
	 * 
	 * @return
	 */
	List<ChargeOrderExt> queryChargeOrderList(ChargeOrderExt condition);

	/**
	 * 查询需要开通的订单
	 * 
	 * @return
	 */
	List<ChargeOrder> queryOpenChargeOrderList(ChargeOrder condition);

	/**
	 * 根据主键查询订单
	 * 
	 * @return
	 */
	ChargeOrder queryChargeOrderById(Integer id);

	/**
	 * 审核订单
	 * 
	 * @param data
	 * @return
	 */
	ChargeOrder approve(ChargeOrder data);

	/**
	 * 开通订单
	 * 
	 * @param data
	 * @return
	 */
	ChargeOrder open(ChargeOrder data);

	/**
	 * 修改充值订单
	 * 
	 * @param data
	 * @return int
	 * @exception @since
	 *                1.0.0
	 */
	int editChargeOrder(ChargeOrderExt data);

	/**
	 * 修改订单
	 * @param chargeOrder
	 */
	void updateByPrimaryKeySelective(ChargeOrder chargeOrder);

	/**
	 * 
	 * @param smsList
	 * @return
	 */
	public int chargeOrderBySmsStatisticsListTran(List<SmsStatisticsExt> smsList);

	/**
	 *  客户平台充值接口
	 * 
	 * @param chargeOrderExt
	 * @param request
	 * @return
	 * @throws Exception
	 */
	Map<String, String> recharge(ChargeOrderExt chargeOrderExt, HttpServletRequest request) throws Exception;

	/**
	 *  充值支付结果回调
	 * 
	 * @param resultData
	 * @return
	 * @throws Exception
	 */
	Map<String, String> rechargePayResultTran(String resultData) throws Exception;

	/**
	 *  主账户划拨到子账户
	 * 
	 * @param userCharge
	 * @param master_User_Id
	 * @throws Exception
	 */
	@CacheEvict(value = "enterprise_user_list", allEntries = true)
    void user2charge(ChargeOrder userCharge, Integer master_User_Id) throws Exception;

	/**
	 *  发票列表
	 * 
	 * @param invoice
	 * @return
	 */
	List<Invoice> queryInvoiceList(Invoice invoice);

	/**
	 *  申请发票
	 * 
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	Invoice addInvoice(Invoice invoice) throws Exception;

	/**
	 *  审核编辑发票
	 * 
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	Invoice editInvoice(Invoice invoice) throws Exception;


	/**
	 * 企业用户余额
	 * 
	 * @param enterpriseUserExt
	 * @return
	 */
	double getEnterpriseUserAllBalance(EnterpriseUserExt enterpriseUserExt);


	/**
	 *  导出充值记录
	 * 
	 * @param path
	 * @param chargeOrderExt
	 * @param exportFile
	 * @param exportType
	 */
	void exportChargeOrderList(String path, ChargeOrderExt chargeOrderExt, ExportFileExt exportFile, String exportType);

	/**
	 *  查询充值记录 sql
	 * 
	 * @param chargeOrderExt
	 * @return
	 */
	List<Map<String, Object>> getChargeOrderListForExport(ChargeOrderExt chargeOrderExt);

}
