package com.hero.wireless.web.entity.send.ext;

import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Input;

public class InputExt extends Input {
	private String audit_Status_Code_Name;
	private String is_LMS_Name;
	private Enterprise enterprise;
	private EnterpriseUser enterpriseUser;
	private String editContentValue;
	private String flag;
	private String filePath;

	private String auditFlag;//审核flag：1普通审核  2批量审核
	private String internationalTelephoneCode;


	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public EnterpriseUser getEnterpriseUser() {
		return enterpriseUser;
	}

	public void setEnterpriseUser(EnterpriseUser enterpriseUser) {
		this.enterpriseUser = enterpriseUser;
	}

	public String getAudit_Status_Code_Name() {
		return audit_Status_Code_Name;
	}

	public void setAudit_Status_Code_Name(String audit_Status_Code_Name) {
		this.audit_Status_Code_Name = audit_Status_Code_Name;
	}

	public String getIs_LMS_Name() {
		return this.is_LMS_Name;
	}

	public void setIs_LMS_Name(String is_LMS_Name) {
		this.is_LMS_Name = is_LMS_Name;
	}

	public String getEditContentValue() {
		return editContentValue;
	}

	public void setEditContentValue(String editContentValue) {
		this.editContentValue = editContentValue;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getInternationalTelephoneCode() {
		return internationalTelephoneCode;
	}

	public void setInternationalTelephoneCode(String internationalTelephoneCode) {
		this.internationalTelephoneCode = internationalTelephoneCode;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

}
