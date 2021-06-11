package com.hero.wireless.web.entity.send.ext;

import com.hero.wireless.web.entity.base.BaseEntity;

public class WaitSendSmsStatiistic extends BaseEntity {
	private Integer cmppWaitTotal;
	private Integer sgipWaitTotal;
	private Integer smgpWaitTotal;
	private Integer cmppSubmitingTotal;
	private Integer sgipSubmitingTotal;
	private Integer smgpSubmitingTotal;
	private Integer interfaceWaitTotal;
	private Integer interfaceSubmitingTotal;
	private Integer waitSortTotal;
	public Integer getInterfaceWaitTotal() {
		return interfaceWaitTotal;
	}
	public void setInterfaceWaitTotal(Integer interfaceWaitTotal) {
		this.interfaceWaitTotal = interfaceWaitTotal;
	}
	public Integer getInterfaceSubmitingTotal() {
		return interfaceSubmitingTotal;
	}
	public void setInterfaceSubmitingTotal(Integer interfaceSubmitingTotal) {
		this.interfaceSubmitingTotal = interfaceSubmitingTotal;
	}
	public Integer getWaitSortTotal() {
		return waitSortTotal;
	}
	public void setWaitSortTotal(Integer waitSortTotal) {
		this.waitSortTotal = waitSortTotal;
	}
	public Integer getCmppWaitTotal() {
		return cmppWaitTotal;
	}
	public void setCmppWaitTotal(Integer cmppWaitTotal) {
		this.cmppWaitTotal = cmppWaitTotal;
	}
	public Integer getSgipWaitTotal() {
		return sgipWaitTotal;
	}
	public void setSgipWaitTotal(Integer sgipWaitTotal) {
		this.sgipWaitTotal = sgipWaitTotal;
	}
	public Integer getSmgpWaitTotal() {
		return smgpWaitTotal;
	}
	public void setSmgpWaitTotal(Integer smgpWaitTotal) {
		this.smgpWaitTotal = smgpWaitTotal;
	}
	public Integer getCmppSubmitingTotal() {
		return cmppSubmitingTotal;
	}
	public void setCmppSubmitingTotal(Integer cmppSubmitingTotal) {
		this.cmppSubmitingTotal = cmppSubmitingTotal;
	}
	public Integer getSgipSubmitingTotal() {
		return sgipSubmitingTotal;
	}
	public void setSgipSubmitingTotal(Integer sgipSubmitingTotal) {
		this.sgipSubmitingTotal = sgipSubmitingTotal;
	}
	public Integer getSmgpSubmitingTotal() {
		return smgpSubmitingTotal;
	}
	public void setSmgpSubmitingTotal(Integer smgpSubmitingTotal) {
		this.smgpSubmitingTotal = smgpSubmitingTotal;
	}
	public WaitSendSmsStatiistic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WaitSendSmsStatiistic(Integer cmppWaitTotal, Integer sgipWaitTotal,
			Integer smgpWaitTotal, Integer cmppSubmitingTotal,
			Integer sgipSubmitingTotal, Integer smgpSubmitingTotal) {
		super();
		this.cmppWaitTotal = cmppWaitTotal;
		this.sgipWaitTotal = sgipWaitTotal;
		this.smgpWaitTotal = smgpWaitTotal;
		this.cmppSubmitingTotal = cmppSubmitingTotal;
		this.sgipSubmitingTotal = sgipSubmitingTotal;
		this.smgpSubmitingTotal = smgpSubmitingTotal;
	}
	
}
