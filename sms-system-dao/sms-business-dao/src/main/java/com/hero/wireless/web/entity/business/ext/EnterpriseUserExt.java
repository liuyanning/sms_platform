package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.EnterpriseLimit;
import com.hero.wireless.web.entity.business.EnterpriseRole;
import com.hero.wireless.web.entity.business.EnterpriseUser;

import java.util.List;

public class EnterpriseUserExt extends EnterpriseUser {

	private static final long serialVersionUID = 1L;

	private List<EnterpriseRole> roles;

	private List<EnterpriseLimit> limits;
	
	private List<EnterpriseLimit> limitOrderIds;

	private List<String> enterpriseNoList;

	private String graphValidateCode;
	private String smsValidateCode;
	private String dataSource;

	private String enterpriseName;
	private String password;
	private String confirmPassword;

	private String firstGroupBeginTime;
	private String firstGroupEndTime;
	private String secondGroupBeginTime;
	private String secondGroupEndTime;

	private String blacklist_Switch;
	private String sourceFlag;
    private String signatureLocation;//签名位置
    private String countryCodeValue;//过滤国家区号
    private String windowSize;//滑动窗口大小

	private String sgipSpIp;
	private String sgipSpPort;

    public String getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(String windowSize) {
        this.windowSize = windowSize;
    }

    public String getCountryCodeValue() {
        return countryCodeValue;
    }

    public void setCountryCodeValue(String countryCodeValue) {
        this.countryCodeValue = countryCodeValue;
    }

    public String getSgipSpIp() {
		return sgipSpIp;
	}

	public void setSgipSpIp(String sgipSpIp) {
		this.sgipSpIp = sgipSpIp;
	}

	public String getSgipSpPort() {
		return sgipSpPort;
	}

	public void setSgipSpPort(String sgipSpPort) {
		this.sgipSpPort = sgipSpPort;
	}

	public String getSignatureLocation() {
        return signatureLocation;
    }

    public void setSignatureLocation(String signatureLocation) {
        this.signatureLocation = signatureLocation;
    }

    public List<EnterpriseLimit> getLimitOrderIds() {
		return limitOrderIds;
	}

	public void setLimitOrderIds(List<EnterpriseLimit> limitOrderIds) {
		this.limitOrderIds = limitOrderIds;
	}

	public List<EnterpriseLimit> getLimits() {
		return limits;
	}

	public List<EnterpriseRole> getRoles() {
		return roles;
	}

	public void setLimits(List<EnterpriseLimit> limits) {
		this.limits = limits;
	}

	public void setRoles(List<EnterpriseRole> roles) {
		this.roles = roles;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getGraphValidateCode() {
		return graphValidateCode;
	}

	public void setGraphValidateCode(String graphValidateCode) {
		this.graphValidateCode = graphValidateCode;
	}

	public String getSmsValidateCode() {
		return smsValidateCode;
	}

	public void setSmsValidateCode(String smsValidateCode) {
		this.smsValidateCode = smsValidateCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getFirstGroupBeginTime() {
		return firstGroupBeginTime;
	}

	public void setFirstGroupBeginTime(String firstGroupBeginTime) {
		this.firstGroupBeginTime = firstGroupBeginTime;
	}

	public String getFirstGroupEndTime() {
		return firstGroupEndTime;
	}

	public void setFirstGroupEndTime(String firstGroupEndTime) {
		this.firstGroupEndTime = firstGroupEndTime;
	}

	public String getSecondGroupBeginTime() {
		return secondGroupBeginTime;
	}

	public void setSecondGroupBeginTime(String secondGroupBeginTime) {
		this.secondGroupBeginTime = secondGroupBeginTime;
	}

	public String getSecondGroupEndTime() {
		return secondGroupEndTime;
	}

	public void setSecondGroupEndTime(String secondGroupEndTime) {
		this.secondGroupEndTime = secondGroupEndTime;
	}

	public List<String> getEnterpriseNoList() {
		return enterpriseNoList;
	}

	public void setEnterpriseNoList(List<String> enterpriseNoList) {
		this.enterpriseNoList = enterpriseNoList;
	}

	public String getBlacklist_Switch() {
		return blacklist_Switch;
	}

	public void setBlacklist_Switch(String blacklist_Switch) {
		this.blacklist_Switch = blacklist_Switch;
	}

	public String getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}
}
