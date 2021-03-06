package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class Invoice extends BaseEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Enterprise_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String enterprise_No;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Enterprise_User_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Integer enterprise_User_Id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Invoice_Type_Code
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String invoice_Type_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Invoice_Title
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String invoice_Title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Invoice_Amount
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private BigDecimal invoice_Amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Duty_Daragraph
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String duty_Daragraph;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Address
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Phone_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String phone_No;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Opening_Bank
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String opening_Bank;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Bank_Account
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String bank_Account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Mailing_Address
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String mailing_Address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Recipient_Phone_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String recipient_Phone_No;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Express_Company
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String express_Company;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Courier_Number
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String courier_Number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Dispose_State_Code
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String dispose_State_Code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Update_User
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String update_User;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Update_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Date update_Date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Remark
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    private Date create_Date;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Id
     *
     * @return the value of invoice.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Id
     *
     * @param id the value for invoice.Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Enterprise_No
     *
     * @return the value of invoice.Enterprise_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getEnterprise_No() {
        return enterprise_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Enterprise_No
     *
     * @param enterprise_No the value for invoice.Enterprise_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setEnterprise_No(String enterprise_No) {
        this.enterprise_No = enterprise_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Enterprise_User_Id
     *
     * @return the value of invoice.Enterprise_User_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Integer getEnterprise_User_Id() {
        return enterprise_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Enterprise_User_Id
     *
     * @param enterprise_User_Id the value for invoice.Enterprise_User_Id
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setEnterprise_User_Id(Integer enterprise_User_Id) {
        this.enterprise_User_Id = enterprise_User_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Name
     *
     * @return the value of invoice.Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Name
     *
     * @param name the value for invoice.Name
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Invoice_Type_Code
     *
     * @return the value of invoice.Invoice_Type_Code
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getInvoice_Type_Code() {
        return invoice_Type_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Invoice_Type_Code
     *
     * @param invoice_Type_Code the value for invoice.Invoice_Type_Code
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setInvoice_Type_Code(String invoice_Type_Code) {
        this.invoice_Type_Code = invoice_Type_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Invoice_Title
     *
     * @return the value of invoice.Invoice_Title
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getInvoice_Title() {
        return invoice_Title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Invoice_Title
     *
     * @param invoice_Title the value for invoice.Invoice_Title
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setInvoice_Title(String invoice_Title) {
        this.invoice_Title = invoice_Title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Invoice_Amount
     *
     * @return the value of invoice.Invoice_Amount
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public BigDecimal getInvoice_Amount() {
        return invoice_Amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Invoice_Amount
     *
     * @param invoice_Amount the value for invoice.Invoice_Amount
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setInvoice_Amount(BigDecimal invoice_Amount) {
        this.invoice_Amount = invoice_Amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Duty_Daragraph
     *
     * @return the value of invoice.Duty_Daragraph
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getDuty_Daragraph() {
        return duty_Daragraph;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Duty_Daragraph
     *
     * @param duty_Daragraph the value for invoice.Duty_Daragraph
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setDuty_Daragraph(String duty_Daragraph) {
        this.duty_Daragraph = duty_Daragraph;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Address
     *
     * @return the value of invoice.Address
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Address
     *
     * @param address the value for invoice.Address
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Phone_No
     *
     * @return the value of invoice.Phone_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getPhone_No() {
        return phone_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Phone_No
     *
     * @param phone_No the value for invoice.Phone_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Opening_Bank
     *
     * @return the value of invoice.Opening_Bank
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getOpening_Bank() {
        return opening_Bank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Opening_Bank
     *
     * @param opening_Bank the value for invoice.Opening_Bank
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setOpening_Bank(String opening_Bank) {
        this.opening_Bank = opening_Bank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Bank_Account
     *
     * @return the value of invoice.Bank_Account
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getBank_Account() {
        return bank_Account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Bank_Account
     *
     * @param bank_Account the value for invoice.Bank_Account
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setBank_Account(String bank_Account) {
        this.bank_Account = bank_Account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Mailing_Address
     *
     * @return the value of invoice.Mailing_Address
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getMailing_Address() {
        return mailing_Address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Mailing_Address
     *
     * @param mailing_Address the value for invoice.Mailing_Address
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setMailing_Address(String mailing_Address) {
        this.mailing_Address = mailing_Address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Recipient_Phone_No
     *
     * @return the value of invoice.Recipient_Phone_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getRecipient_Phone_No() {
        return recipient_Phone_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Recipient_Phone_No
     *
     * @param recipient_Phone_No the value for invoice.Recipient_Phone_No
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setRecipient_Phone_No(String recipient_Phone_No) {
        this.recipient_Phone_No = recipient_Phone_No;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Express_Company
     *
     * @return the value of invoice.Express_Company
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getExpress_Company() {
        return express_Company;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Express_Company
     *
     * @param express_Company the value for invoice.Express_Company
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setExpress_Company(String express_Company) {
        this.express_Company = express_Company;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Courier_Number
     *
     * @return the value of invoice.Courier_Number
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getCourier_Number() {
        return courier_Number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Courier_Number
     *
     * @param courier_Number the value for invoice.Courier_Number
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setCourier_Number(String courier_Number) {
        this.courier_Number = courier_Number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Dispose_State_Code
     *
     * @return the value of invoice.Dispose_State_Code
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getDispose_State_Code() {
        return dispose_State_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Dispose_State_Code
     *
     * @param dispose_State_Code the value for invoice.Dispose_State_Code
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setDispose_State_Code(String dispose_State_Code) {
        this.dispose_State_Code = dispose_State_Code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Update_User
     *
     * @return the value of invoice.Update_User
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getUpdate_User() {
        return update_User;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Update_User
     *
     * @param update_User the value for invoice.Update_User
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setUpdate_User(String update_User) {
        this.update_User = update_User;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Update_Date
     *
     * @return the value of invoice.Update_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Date getUpdate_Date() {
        return update_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Update_Date
     *
     * @param update_Date the value for invoice.Update_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setUpdate_Date(Date update_Date) {
        this.update_Date = update_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Remark
     *
     * @return the value of invoice.Remark
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Remark
     *
     * @param remark the value for invoice.Remark
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice.Create_Date
     *
     * @return the value of invoice.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public Date getCreate_Date() {
        return create_Date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice.Create_Date
     *
     * @param create_Date the value for invoice.Create_Date
     *
     * @mbg.generated Thu May 14 09:56:20 CST 2020
     */
    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
}