package com.hero.wireless.web.entity.ext;

import com.hero.wireless.web.entity.base.IEntity;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

/**
 * SQL统计实体，同COUNT语句 保函统计常用字段
 *
 * @author Lenovo
 */
public class SqlStatisticsEntity implements IEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 5365847184704125362L;

	private Integer count; // 条数
	private Long sent_Count;

	private BigDecimal used_Amount;
	private BigDecimal available_Amount; // 可用金额
	private BigDecimal arrears;
	private Long available_Total_Number;

	private Long enterprise_Count; // 企业
	private Long fee_Count; // 计费条数
	private Long faild_Count; // 计费失败条数
	private BigDecimal sale_Amount; // 消费金额（元）
	private BigDecimal profits; // 利润（元）

	private Long phone_Nos_Count; // 手机号码数

	private Float sale_Fee_Total; // 销售金额

	private Float profits_Total; // 利润
	private Integer submit_Success_Total; // 提交成功
	private Integer submit_Faild_Total; // 提交失败
	private Integer send_Success_Total; // 发送成功
	private Integer send_Faild_Total; // 发送失败
	private Integer sort_Faild_Total; // 分拣失败

	private BigDecimal cost_Total; // 通道成本
	private BigDecimal channel_Unit_Price_Total; // 通道成本
	private BigDecimal enterprise_User_Taxes_Total; // 发票成本
	private BigDecimal channel_Taxes_Total; // 发票抵消
	private String create_Date; // 统计日

	public Long getSent_Count() {
		return sent_Count;
	}
	public void setSent_Count(Long sent_Count) {
		this.sent_Count = sent_Count;
	}
	public String getCreate_Date() {
		return create_Date;
	}

	public void setCreate_Date(String create_Date) {
		this.create_Date = create_Date;
	}

	public BigDecimal getCost_Total() {
		return cost_Total;
	}

	public void setCost_Total(BigDecimal cost_Total) {
		this.cost_Total = cost_Total;
	}

	public Long getEnterprise_Count() {
		return enterprise_Count;
	}

	public void setEnterprise_Count(Long enterprise_Count) {
		this.enterprise_Count = enterprise_Count;
	}

	public BigDecimal getAvailable_Amount() {
		return ObjectUtils.defaultIfNull(available_Amount, BigDecimal.ZERO);
	}

	public void setAvailable_Amount(BigDecimal available_Amount) {
		this.available_Amount = available_Amount;
	}

	public Long getFee_Count() {
		return fee_Count;
	}

	public void setFee_Count(Long fee_Count) {
		this.fee_Count = fee_Count;
	}

	public Long getFaild_Count() {
		return faild_Count;
	}

	public void setFaild_Count(Long faild_Count) {
		this.faild_Count = faild_Count;
	}

	public BigDecimal getSale_Amount() {
		return ObjectUtils.defaultIfNull(sale_Amount, BigDecimal.ZERO);
	}

	public void setSale_Amount(BigDecimal sale_Amount) {
		this.sale_Amount = sale_Amount;
	}

	public BigDecimal getProfits() {
		return ObjectUtils.defaultIfNull(profits, BigDecimal.ZERO);
	}

	public void setProfits(BigDecimal profits) {
		this.profits = profits;
	}

	public Integer getCount() {
		return ObjectUtils.defaultIfNull(count, 0);
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getUsed_Amount() {
		return ObjectUtils.defaultIfNull(used_Amount, BigDecimal.ZERO);
	}

	public void setUsed_Amount(BigDecimal used_Amount) {
		this.used_Amount = used_Amount;
	}

	public Long getAvailable_Total_Number() {
		return available_Total_Number;
	}

	public void setAvailable_Total_Number(Long available_Total_Number) {
		this.available_Total_Number = available_Total_Number;
	}

	public BigDecimal getArrears() {
		return arrears;
	}

	public void setArrears(BigDecimal arrears) {
		this.arrears = arrears;
	}

	public Float getSale_Fee_Total() {
		return sale_Fee_Total;
	}

	public void setSale_Fee_Total(Float sale_Fee_Total) {
		this.sale_Fee_Total = sale_Fee_Total;
	}

	public Float getProfits_Total() {
		return ObjectUtils.defaultIfNull(profits_Total, 0F);
	}

	public void setProfits_Total(Float profits_Total) {
		this.profits_Total = profits_Total;
	}

	public Long getPhone_Nos_Count() {
		return phone_Nos_Count;
	}

	public void setPhone_Nos_Count(Long phone_Nos_Count) {
		this.phone_Nos_Count = phone_Nos_Count;
	}

	public Integer getSubmit_Success_Total() {
		return ObjectUtils.defaultIfNull(submit_Success_Total, 0);
	}

	public void setSubmit_Success_Total(Integer submit_Success_Total) {
		this.submit_Success_Total = submit_Success_Total;
	}

	public Integer getSubmit_Faild_Total() {
		return ObjectUtils.defaultIfNull(submit_Faild_Total, 0);
	}

	public void setSubmit_Faild_Total(Integer submit_Faild_Total) {
		this.submit_Faild_Total = submit_Faild_Total;
	}

	public Integer getSend_Success_Total() {
		return ObjectUtils.defaultIfNull(send_Success_Total, 0);
	}

	public void setSend_Success_Total(Integer send_Success_Total) {
		this.send_Success_Total = send_Success_Total;
	}

	public Integer getSend_Faild_Total() {
		return ObjectUtils.defaultIfNull(send_Faild_Total, 0);
	}

	public void setSend_Faild_Total(Integer send_Faild_Total) {
		this.send_Faild_Total = send_Faild_Total;
	}

	public BigDecimal getChannel_Unit_Price_Total() {
		return ObjectUtils.defaultIfNull(channel_Unit_Price_Total, new BigDecimal(0));
	}

	public void setChannel_Unit_Price_Total(BigDecimal channel_Unit_Price_Total) {
		this.channel_Unit_Price_Total = channel_Unit_Price_Total;
	}

	public BigDecimal getEnterprise_User_Taxes_Total() {
		return ObjectUtils.defaultIfNull(enterprise_User_Taxes_Total, BigDecimal.ZERO);
	}

	public void setEnterprise_User_Taxes_Total(BigDecimal enterprise_User_Taxes_Total) {
		this.enterprise_User_Taxes_Total = enterprise_User_Taxes_Total;
	}

	public BigDecimal getChannel_Taxes_Total() {
		return ObjectUtils.defaultIfNull(channel_Taxes_Total, BigDecimal.ZERO);
	}

	public void setChannel_Taxes_Total(BigDecimal channel_Taxes_Total) {
		this.channel_Taxes_Total = channel_Taxes_Total;
	}

	public Integer getSort_Faild_Total() {
		return ObjectUtils.defaultIfNull(sort_Faild_Total, 0);
	}

	public void setSort_Faild_Total(Integer sort_Faild_Total) {
		this.sort_Faild_Total = sort_Faild_Total;
	}
}
