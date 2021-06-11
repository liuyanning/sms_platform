package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected String dataLock;

    protected List<Criteria> oredCriteria;

    public ContactExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDataLock(String dataLock) {
        this.dataLock = dataLock;
    }

    public String getDataLock() {
        return dataLock;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        public void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        public void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdIsNull() {
            addCriterion("Group_Id is null");
            return (Criteria) this;
        }

        public Criteria andGroup_IdIsNotNull() {
            addCriterion("Group_Id is not null");
            return (Criteria) this;
        }

        public Criteria andGroup_IdEqualTo(Integer value) {
            addCriterion("Group_Id =", value, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdNotEqualTo(Integer value) {
            addCriterion("Group_Id <>", value, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdGreaterThan(Integer value) {
            addCriterion("Group_Id >", value, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Group_Id >=", value, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdLessThan(Integer value) {
            addCriterion("Group_Id <", value, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdLessThanOrEqualTo(Integer value) {
            addCriterion("Group_Id <=", value, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdIn(List<Integer> values) {
            addCriterion("Group_Id in", values, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdNotIn(List<Integer> values) {
            addCriterion("Group_Id not in", values, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdBetween(Integer value1, Integer value2) {
            addCriterion("Group_Id between", value1, value2, "group_Id");
            return (Criteria) this;
        }

        public Criteria andGroup_IdNotBetween(Integer value1, Integer value2) {
            addCriterion("Group_Id not between", value1, value2, "group_Id");
            return (Criteria) this;
        }

        public Criteria andPhone_NoIsNull() {
            addCriterion("Phone_No is null");
            return (Criteria) this;
        }

        public Criteria andPhone_NoIsNotNull() {
            addCriterion("Phone_No is not null");
            return (Criteria) this;
        }

        public Criteria andPhone_NoEqualTo(String value) {
            addCriterion("Phone_No =", value, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoNotEqualTo(String value) {
            addCriterion("Phone_No <>", value, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoGreaterThan(String value) {
            addCriterion("Phone_No >", value, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoGreaterThanOrEqualTo(String value) {
            addCriterion("Phone_No >=", value, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoLessThan(String value) {
            addCriterion("Phone_No <", value, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoLessThanOrEqualTo(String value) {
            addCriterion("Phone_No <=", value, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoLike(String value) {
            addCriterion("Phone_No like", value, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoNotLike(String value) {
            addCriterion("Phone_No not like", value, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoIn(List<String> values) {
            addCriterion("Phone_No in", values, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoNotIn(List<String> values) {
            addCriterion("Phone_No not in", values, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoBetween(String value1, String value2) {
            addCriterion("Phone_No between", value1, value2, "phone_No");
            return (Criteria) this;
        }

        public Criteria andPhone_NoNotBetween(String value1, String value2) {
            addCriterion("Phone_No not between", value1, value2, "phone_No");
            return (Criteria) this;
        }

        public Criteria andReal_NameIsNull() {
            addCriterion("Real_Name is null");
            return (Criteria) this;
        }

        public Criteria andReal_NameIsNotNull() {
            addCriterion("Real_Name is not null");
            return (Criteria) this;
        }

        public Criteria andReal_NameEqualTo(String value) {
            addCriterion("Real_Name =", value, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameNotEqualTo(String value) {
            addCriterion("Real_Name <>", value, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameGreaterThan(String value) {
            addCriterion("Real_Name >", value, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameGreaterThanOrEqualTo(String value) {
            addCriterion("Real_Name >=", value, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameLessThan(String value) {
            addCriterion("Real_Name <", value, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameLessThanOrEqualTo(String value) {
            addCriterion("Real_Name <=", value, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameLike(String value) {
            addCriterion("Real_Name like", value, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameNotLike(String value) {
            addCriterion("Real_Name not like", value, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameIn(List<String> values) {
            addCriterion("Real_Name in", values, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameNotIn(List<String> values) {
            addCriterion("Real_Name not in", values, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameBetween(String value1, String value2) {
            addCriterion("Real_Name between", value1, value2, "real_Name");
            return (Criteria) this;
        }

        public Criteria andReal_NameNotBetween(String value1, String value2) {
            addCriterion("Real_Name not between", value1, value2, "real_Name");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("Gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("Gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(String value) {
            addCriterion("Gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(String value) {
            addCriterion("Gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(String value) {
            addCriterion("Gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(String value) {
            addCriterion("Gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(String value) {
            addCriterion("Gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(String value) {
            addCriterion("Gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLike(String value) {
            addCriterion("Gender like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotLike(String value) {
            addCriterion("Gender not like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<String> values) {
            addCriterion("Gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<String> values) {
            addCriterion("Gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(String value1, String value2) {
            addCriterion("Gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(String value1, String value2) {
            addCriterion("Gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("Birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("Birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterion("Birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterion("Birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterion("Birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterion("Birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterion("Birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterion("Birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterion("Birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterion("Birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterion("Birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterion("Birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("Address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("Address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("Address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("Address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("Address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("Address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("Address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("Address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("Address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("Address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("Address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("Address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("Address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("Address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNull() {
            addCriterion("Company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("Company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("Company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("Company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("Company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("Company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("Company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("Company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("Company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("Company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("Company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("Company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("Company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("Company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andPositionIsNull() {
            addCriterion("Position is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("Position is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(String value) {
            addCriterion("Position =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(String value) {
            addCriterion("Position <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(String value) {
            addCriterion("Position >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(String value) {
            addCriterion("Position >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(String value) {
            addCriterion("Position <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(String value) {
            addCriterion("Position <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLike(String value) {
            addCriterion("Position like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotLike(String value) {
            addCriterion("Position not like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<String> values) {
            addCriterion("Position in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<String> values) {
            addCriterion("Position not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(String value1, String value2) {
            addCriterion("Position between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(String value1, String value2) {
            addCriterion("Position not between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoIsNull() {
            addCriterion("Enterprise_No is null");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoIsNotNull() {
            addCriterion("Enterprise_No is not null");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoEqualTo(String value) {
            addCriterion("Enterprise_No =", value, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoNotEqualTo(String value) {
            addCriterion("Enterprise_No <>", value, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoGreaterThan(String value) {
            addCriterion("Enterprise_No >", value, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoGreaterThanOrEqualTo(String value) {
            addCriterion("Enterprise_No >=", value, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoLessThan(String value) {
            addCriterion("Enterprise_No <", value, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoLessThanOrEqualTo(String value) {
            addCriterion("Enterprise_No <=", value, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoLike(String value) {
            addCriterion("Enterprise_No like", value, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoNotLike(String value) {
            addCriterion("Enterprise_No not like", value, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoIn(List<String> values) {
            addCriterion("Enterprise_No in", values, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoNotIn(List<String> values) {
            addCriterion("Enterprise_No not in", values, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoBetween(String value1, String value2) {
            addCriterion("Enterprise_No between", value1, value2, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andEnterprise_NoNotBetween(String value1, String value2) {
            addCriterion("Enterprise_No not between", value1, value2, "enterprise_No");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdIsNull() {
            addCriterion("Create_Enterprise_User_Id is null");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdIsNotNull() {
            addCriterion("Create_Enterprise_User_Id is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdEqualTo(Integer value) {
            addCriterion("Create_Enterprise_User_Id =", value, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdNotEqualTo(Integer value) {
            addCriterion("Create_Enterprise_User_Id <>", value, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdGreaterThan(Integer value) {
            addCriterion("Create_Enterprise_User_Id >", value, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Create_Enterprise_User_Id >=", value, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdLessThan(Integer value) {
            addCriterion("Create_Enterprise_User_Id <", value, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdLessThanOrEqualTo(Integer value) {
            addCriterion("Create_Enterprise_User_Id <=", value, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdIn(List<Integer> values) {
            addCriterion("Create_Enterprise_User_Id in", values, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdNotIn(List<Integer> values) {
            addCriterion("Create_Enterprise_User_Id not in", values, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdBetween(Integer value1, Integer value2) {
            addCriterion("Create_Enterprise_User_Id between", value1, value2, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_IdNotBetween(Integer value1, Integer value2) {
            addCriterion("Create_Enterprise_User_Id not between", value1, value2, "create_Enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameIsNull() {
            addCriterion("Create_Enterprise_User_Name is null");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameIsNotNull() {
            addCriterion("Create_Enterprise_User_Name is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameEqualTo(String value) {
            addCriterion("Create_Enterprise_User_Name =", value, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameNotEqualTo(String value) {
            addCriterion("Create_Enterprise_User_Name <>", value, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameGreaterThan(String value) {
            addCriterion("Create_Enterprise_User_Name >", value, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameGreaterThanOrEqualTo(String value) {
            addCriterion("Create_Enterprise_User_Name >=", value, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameLessThan(String value) {
            addCriterion("Create_Enterprise_User_Name <", value, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameLessThanOrEqualTo(String value) {
            addCriterion("Create_Enterprise_User_Name <=", value, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameLike(String value) {
            addCriterion("Create_Enterprise_User_Name like", value, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameNotLike(String value) {
            addCriterion("Create_Enterprise_User_Name not like", value, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameIn(List<String> values) {
            addCriterion("Create_Enterprise_User_Name in", values, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameNotIn(List<String> values) {
            addCriterion("Create_Enterprise_User_Name not in", values, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameBetween(String value1, String value2) {
            addCriterion("Create_Enterprise_User_Name between", value1, value2, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_Enterprise_User_NameNotBetween(String value1, String value2) {
            addCriterion("Create_Enterprise_User_Name not between", value1, value2, "create_Enterprise_User_Name");
            return (Criteria) this;
        }

        public Criteria andCreate_DateIsNull() {
            addCriterion("Create_Date is null");
            return (Criteria) this;
        }

        public Criteria andCreate_DateIsNotNull() {
            addCriterion("Create_Date is not null");
            return (Criteria) this;
        }

        public Criteria andCreate_DateEqualTo(Date value) {
            addCriterion("Create_Date =", value, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateNotEqualTo(Date value) {
            addCriterion("Create_Date <>", value, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateGreaterThan(Date value) {
            addCriterion("Create_Date >", value, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateGreaterThanOrEqualTo(Date value) {
            addCriterion("Create_Date >=", value, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateLessThan(Date value) {
            addCriterion("Create_Date <", value, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateLessThanOrEqualTo(Date value) {
            addCriterion("Create_Date <=", value, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateIn(List<Date> values) {
            addCriterion("Create_Date in", values, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateNotIn(List<Date> values) {
            addCriterion("Create_Date not in", values, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateBetween(Date value1, Date value2) {
            addCriterion("Create_Date between", value1, value2, "create_Date");
            return (Criteria) this;
        }

        public Criteria andCreate_DateNotBetween(Date value1, Date value2) {
            addCriterion("Create_Date not between", value1, value2, "create_Date");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}