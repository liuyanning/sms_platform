package com.hero.wireless.web.entity.send;

import com.hero.wireless.web.entity.base.BaseExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InboxExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected String dataLock;

    protected List<Criteria> oredCriteria;

    public InboxExample() {
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

        public Criteria andSP_NumberIsNull() {
            addCriterion("SP_Number is null");
            return (Criteria) this;
        }

        public Criteria andSP_NumberIsNotNull() {
            addCriterion("SP_Number is not null");
            return (Criteria) this;
        }

        public Criteria andSP_NumberEqualTo(String value) {
            addCriterion("SP_Number =", value, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberNotEqualTo(String value) {
            addCriterion("SP_Number <>", value, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberGreaterThan(String value) {
            addCriterion("SP_Number >", value, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberGreaterThanOrEqualTo(String value) {
            addCriterion("SP_Number >=", value, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberLessThan(String value) {
            addCriterion("SP_Number <", value, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberLessThanOrEqualTo(String value) {
            addCriterion("SP_Number <=", value, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberLike(String value) {
            addCriterion("SP_Number like", value, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberNotLike(String value) {
            addCriterion("SP_Number not like", value, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberIn(List<String> values) {
            addCriterion("SP_Number in", values, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberNotIn(List<String> values) {
            addCriterion("SP_Number not in", values, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberBetween(String value1, String value2) {
            addCriterion("SP_Number between", value1, value2, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSP_NumberNotBetween(String value1, String value2) {
            addCriterion("SP_Number not between", value1, value2, "SP_Number");
            return (Criteria) this;
        }

        public Criteria andSub_CodeIsNull() {
            addCriterion("Sub_Code is null");
            return (Criteria) this;
        }

        public Criteria andSub_CodeIsNotNull() {
            addCriterion("Sub_Code is not null");
            return (Criteria) this;
        }

        public Criteria andSub_CodeEqualTo(String value) {
            addCriterion("Sub_Code =", value, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeNotEqualTo(String value) {
            addCriterion("Sub_Code <>", value, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeGreaterThan(String value) {
            addCriterion("Sub_Code >", value, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Sub_Code >=", value, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeLessThan(String value) {
            addCriterion("Sub_Code <", value, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeLessThanOrEqualTo(String value) {
            addCriterion("Sub_Code <=", value, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeLike(String value) {
            addCriterion("Sub_Code like", value, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeNotLike(String value) {
            addCriterion("Sub_Code not like", value, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeIn(List<String> values) {
            addCriterion("Sub_Code in", values, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeNotIn(List<String> values) {
            addCriterion("Sub_Code not in", values, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeBetween(String value1, String value2) {
            addCriterion("Sub_Code between", value1, value2, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andSub_CodeNotBetween(String value1, String value2) {
            addCriterion("Sub_Code not between", value1, value2, "sub_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeIsNull() {
            addCriterion("Group_Code is null");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeIsNotNull() {
            addCriterion("Group_Code is not null");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeEqualTo(String value) {
            addCriterion("Group_Code =", value, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeNotEqualTo(String value) {
            addCriterion("Group_Code <>", value, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeGreaterThan(String value) {
            addCriterion("Group_Code >", value, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Group_Code >=", value, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeLessThan(String value) {
            addCriterion("Group_Code <", value, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeLessThanOrEqualTo(String value) {
            addCriterion("Group_Code <=", value, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeLike(String value) {
            addCriterion("Group_Code like", value, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeNotLike(String value) {
            addCriterion("Group_Code not like", value, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeIn(List<String> values) {
            addCriterion("Group_Code in", values, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeNotIn(List<String> values) {
            addCriterion("Group_Code not in", values, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeBetween(String value1, String value2) {
            addCriterion("Group_Code between", value1, value2, "group_Code");
            return (Criteria) this;
        }

        public Criteria andGroup_CodeNotBetween(String value1, String value2) {
            addCriterion("Group_Code not between", value1, value2, "group_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeIsNull() {
            addCriterion("Input_Sub_Code is null");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeIsNotNull() {
            addCriterion("Input_Sub_Code is not null");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeEqualTo(String value) {
            addCriterion("Input_Sub_Code =", value, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeNotEqualTo(String value) {
            addCriterion("Input_Sub_Code <>", value, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeGreaterThan(String value) {
            addCriterion("Input_Sub_Code >", value, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Input_Sub_Code >=", value, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeLessThan(String value) {
            addCriterion("Input_Sub_Code <", value, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeLessThanOrEqualTo(String value) {
            addCriterion("Input_Sub_Code <=", value, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeLike(String value) {
            addCriterion("Input_Sub_Code like", value, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeNotLike(String value) {
            addCriterion("Input_Sub_Code not like", value, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeIn(List<String> values) {
            addCriterion("Input_Sub_Code in", values, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeNotIn(List<String> values) {
            addCriterion("Input_Sub_Code not in", values, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeBetween(String value1, String value2) {
            addCriterion("Input_Sub_Code between", value1, value2, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Sub_CodeNotBetween(String value1, String value2) {
            addCriterion("Input_Sub_Code not between", value1, value2, "input_Sub_Code");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoIsNull() {
            addCriterion("Input_Msg_No is null");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoIsNotNull() {
            addCriterion("Input_Msg_No is not null");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoEqualTo(String value) {
            addCriterion("Input_Msg_No =", value, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoNotEqualTo(String value) {
            addCriterion("Input_Msg_No <>", value, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoGreaterThan(String value) {
            addCriterion("Input_Msg_No >", value, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoGreaterThanOrEqualTo(String value) {
            addCriterion("Input_Msg_No >=", value, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoLessThan(String value) {
            addCriterion("Input_Msg_No <", value, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoLessThanOrEqualTo(String value) {
            addCriterion("Input_Msg_No <=", value, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoLike(String value) {
            addCriterion("Input_Msg_No like", value, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoNotLike(String value) {
            addCriterion("Input_Msg_No not like", value, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoIn(List<String> values) {
            addCriterion("Input_Msg_No in", values, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoNotIn(List<String> values) {
            addCriterion("Input_Msg_No not in", values, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoBetween(String value1, String value2) {
            addCriterion("Input_Msg_No between", value1, value2, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_Msg_NoNotBetween(String value1, String value2) {
            addCriterion("Input_Msg_No not between", value1, value2, "input_Msg_No");
            return (Criteria) this;
        }

        public Criteria andInput_ContentIsNull() {
            addCriterion("Input_Content is null");
            return (Criteria) this;
        }

        public Criteria andInput_ContentIsNotNull() {
            addCriterion("Input_Content is not null");
            return (Criteria) this;
        }

        public Criteria andInput_ContentEqualTo(String value) {
            addCriterion("Input_Content =", value, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentNotEqualTo(String value) {
            addCriterion("Input_Content <>", value, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentGreaterThan(String value) {
            addCriterion("Input_Content >", value, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentGreaterThanOrEqualTo(String value) {
            addCriterion("Input_Content >=", value, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentLessThan(String value) {
            addCriterion("Input_Content <", value, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentLessThanOrEqualTo(String value) {
            addCriterion("Input_Content <=", value, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentLike(String value) {
            addCriterion("Input_Content like", value, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentNotLike(String value) {
            addCriterion("Input_Content not like", value, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentIn(List<String> values) {
            addCriterion("Input_Content in", values, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentNotIn(List<String> values) {
            addCriterion("Input_Content not in", values, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentBetween(String value1, String value2) {
            addCriterion("Input_Content between", value1, value2, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_ContentNotBetween(String value1, String value2) {
            addCriterion("Input_Content not between", value1, value2, "input_Content");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateIsNull() {
            addCriterion("Input_Create_Date is null");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateIsNotNull() {
            addCriterion("Input_Create_Date is not null");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateEqualTo(Date value) {
            addCriterion("Input_Create_Date =", value, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateNotEqualTo(Date value) {
            addCriterion("Input_Create_Date <>", value, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateGreaterThan(Date value) {
            addCriterion("Input_Create_Date >", value, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateGreaterThanOrEqualTo(Date value) {
            addCriterion("Input_Create_Date >=", value, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateLessThan(Date value) {
            addCriterion("Input_Create_Date <", value, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateLessThanOrEqualTo(Date value) {
            addCriterion("Input_Create_Date <=", value, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateIn(List<Date> values) {
            addCriterion("Input_Create_Date in", values, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateNotIn(List<Date> values) {
            addCriterion("Input_Create_Date not in", values, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateBetween(Date value1, Date value2) {
            addCriterion("Input_Create_Date between", value1, value2, "input_Create_Date");
            return (Criteria) this;
        }

        public Criteria andInput_Create_DateNotBetween(Date value1, Date value2) {
            addCriterion("Input_Create_Date not between", value1, value2, "input_Create_Date");
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

        public Criteria andCountry_CodeIsNull() {
            addCriterion("Country_Code is null");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeIsNotNull() {
            addCriterion("Country_Code is not null");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeEqualTo(String value) {
            addCriterion("Country_Code =", value, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeNotEqualTo(String value) {
            addCriterion("Country_Code <>", value, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeGreaterThan(String value) {
            addCriterion("Country_Code >", value, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Country_Code >=", value, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeLessThan(String value) {
            addCriterion("Country_Code <", value, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeLessThanOrEqualTo(String value) {
            addCriterion("Country_Code <=", value, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeLike(String value) {
            addCriterion("Country_Code like", value, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeNotLike(String value) {
            addCriterion("Country_Code not like", value, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeIn(List<String> values) {
            addCriterion("Country_Code in", values, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeNotIn(List<String> values) {
            addCriterion("Country_Code not in", values, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeBetween(String value1, String value2) {
            addCriterion("Country_Code between", value1, value2, "country_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_CodeNotBetween(String value1, String value2) {
            addCriterion("Country_Code not between", value1, value2, "country_Code");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("Content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("Content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("Content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("Content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("Content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("Content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("Content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("Content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("Content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("Content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("Content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("Content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("Content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("Content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andChannel_NoIsNull() {
            addCriterion("Channel_No is null");
            return (Criteria) this;
        }

        public Criteria andChannel_NoIsNotNull() {
            addCriterion("Channel_No is not null");
            return (Criteria) this;
        }

        public Criteria andChannel_NoEqualTo(String value) {
            addCriterion("Channel_No =", value, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoNotEqualTo(String value) {
            addCriterion("Channel_No <>", value, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoGreaterThan(String value) {
            addCriterion("Channel_No >", value, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoGreaterThanOrEqualTo(String value) {
            addCriterion("Channel_No >=", value, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoLessThan(String value) {
            addCriterion("Channel_No <", value, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoLessThanOrEqualTo(String value) {
            addCriterion("Channel_No <=", value, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoLike(String value) {
            addCriterion("Channel_No like", value, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoNotLike(String value) {
            addCriterion("Channel_No not like", value, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoIn(List<String> values) {
            addCriterion("Channel_No in", values, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoNotIn(List<String> values) {
            addCriterion("Channel_No not in", values, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoBetween(String value1, String value2) {
            addCriterion("Channel_No between", value1, value2, "channel_No");
            return (Criteria) this;
        }

        public Criteria andChannel_NoNotBetween(String value1, String value2) {
            addCriterion("Channel_No not between", value1, value2, "channel_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoIsNull() {
            addCriterion("Agent_No is null");
            return (Criteria) this;
        }

        public Criteria andAgent_NoIsNotNull() {
            addCriterion("Agent_No is not null");
            return (Criteria) this;
        }

        public Criteria andAgent_NoEqualTo(String value) {
            addCriterion("Agent_No =", value, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoNotEqualTo(String value) {
            addCriterion("Agent_No <>", value, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoGreaterThan(String value) {
            addCriterion("Agent_No >", value, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoGreaterThanOrEqualTo(String value) {
            addCriterion("Agent_No >=", value, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoLessThan(String value) {
            addCriterion("Agent_No <", value, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoLessThanOrEqualTo(String value) {
            addCriterion("Agent_No <=", value, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoLike(String value) {
            addCriterion("Agent_No like", value, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoNotLike(String value) {
            addCriterion("Agent_No not like", value, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoIn(List<String> values) {
            addCriterion("Agent_No in", values, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoNotIn(List<String> values) {
            addCriterion("Agent_No not in", values, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoBetween(String value1, String value2) {
            addCriterion("Agent_No between", value1, value2, "agent_No");
            return (Criteria) this;
        }

        public Criteria andAgent_NoNotBetween(String value1, String value2) {
            addCriterion("Agent_No not between", value1, value2, "agent_No");
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

        public Criteria andEnterprise_User_IdIsNull() {
            addCriterion("Enterprise_User_Id is null");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdIsNotNull() {
            addCriterion("Enterprise_User_Id is not null");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdEqualTo(Integer value) {
            addCriterion("Enterprise_User_Id =", value, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdNotEqualTo(Integer value) {
            addCriterion("Enterprise_User_Id <>", value, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdGreaterThan(Integer value) {
            addCriterion("Enterprise_User_Id >", value, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Enterprise_User_Id >=", value, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdLessThan(Integer value) {
            addCriterion("Enterprise_User_Id <", value, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdLessThanOrEqualTo(Integer value) {
            addCriterion("Enterprise_User_Id <=", value, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdIn(List<Integer> values) {
            addCriterion("Enterprise_User_Id in", values, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdNotIn(List<Integer> values) {
            addCriterion("Enterprise_User_Id not in", values, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdBetween(Integer value1, Integer value2) {
            addCriterion("Enterprise_User_Id between", value1, value2, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andEnterprise_User_IdNotBetween(Integer value1, Integer value2) {
            addCriterion("Enterprise_User_Id not between", value1, value2, "enterprise_User_Id");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeIsNull() {
            addCriterion("Protocol_Type_Code is null");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeIsNotNull() {
            addCriterion("Protocol_Type_Code is not null");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeEqualTo(String value) {
            addCriterion("Protocol_Type_Code =", value, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeNotEqualTo(String value) {
            addCriterion("Protocol_Type_Code <>", value, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeGreaterThan(String value) {
            addCriterion("Protocol_Type_Code >", value, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Protocol_Type_Code >=", value, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeLessThan(String value) {
            addCriterion("Protocol_Type_Code <", value, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeLessThanOrEqualTo(String value) {
            addCriterion("Protocol_Type_Code <=", value, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeLike(String value) {
            addCriterion("Protocol_Type_Code like", value, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeNotLike(String value) {
            addCriterion("Protocol_Type_Code not like", value, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeIn(List<String> values) {
            addCriterion("Protocol_Type_Code in", values, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeNotIn(List<String> values) {
            addCriterion("Protocol_Type_Code not in", values, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeBetween(String value1, String value2) {
            addCriterion("Protocol_Type_Code between", value1, value2, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andProtocol_Type_CodeNotBetween(String value1, String value2) {
            addCriterion("Protocol_Type_Code not between", value1, value2, "protocol_Type_Code");
            return (Criteria) this;
        }

        public Criteria andPull_TotalIsNull() {
            addCriterion("Pull_Total is null");
            return (Criteria) this;
        }

        public Criteria andPull_TotalIsNotNull() {
            addCriterion("Pull_Total is not null");
            return (Criteria) this;
        }

        public Criteria andPull_TotalEqualTo(Integer value) {
            addCriterion("Pull_Total =", value, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalNotEqualTo(Integer value) {
            addCriterion("Pull_Total <>", value, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalGreaterThan(Integer value) {
            addCriterion("Pull_Total >", value, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("Pull_Total >=", value, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalLessThan(Integer value) {
            addCriterion("Pull_Total <", value, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalLessThanOrEqualTo(Integer value) {
            addCriterion("Pull_Total <=", value, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalIn(List<Integer> values) {
            addCriterion("Pull_Total in", values, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalNotIn(List<Integer> values) {
            addCriterion("Pull_Total not in", values, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalBetween(Integer value1, Integer value2) {
            addCriterion("Pull_Total between", value1, value2, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_TotalNotBetween(Integer value1, Integer value2) {
            addCriterion("Pull_Total not between", value1, value2, "pull_Total");
            return (Criteria) this;
        }

        public Criteria andPull_DateIsNull() {
            addCriterion("Pull_Date is null");
            return (Criteria) this;
        }

        public Criteria andPull_DateIsNotNull() {
            addCriterion("Pull_Date is not null");
            return (Criteria) this;
        }

        public Criteria andPull_DateEqualTo(Date value) {
            addCriterion("Pull_Date =", value, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateNotEqualTo(Date value) {
            addCriterion("Pull_Date <>", value, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateGreaterThan(Date value) {
            addCriterion("Pull_Date >", value, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateGreaterThanOrEqualTo(Date value) {
            addCriterion("Pull_Date >=", value, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateLessThan(Date value) {
            addCriterion("Pull_Date <", value, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateLessThanOrEqualTo(Date value) {
            addCriterion("Pull_Date <=", value, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateIn(List<Date> values) {
            addCriterion("Pull_Date in", values, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateNotIn(List<Date> values) {
            addCriterion("Pull_Date not in", values, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateBetween(Date value1, Date value2) {
            addCriterion("Pull_Date between", value1, value2, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andPull_DateNotBetween(Date value1, Date value2) {
            addCriterion("Pull_Date not between", value1, value2, "pull_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalIsNull() {
            addCriterion("Notify_Total is null");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalIsNotNull() {
            addCriterion("Notify_Total is not null");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalEqualTo(Integer value) {
            addCriterion("Notify_Total =", value, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalNotEqualTo(Integer value) {
            addCriterion("Notify_Total <>", value, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalGreaterThan(Integer value) {
            addCriterion("Notify_Total >", value, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("Notify_Total >=", value, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalLessThan(Integer value) {
            addCriterion("Notify_Total <", value, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalLessThanOrEqualTo(Integer value) {
            addCriterion("Notify_Total <=", value, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalIn(List<Integer> values) {
            addCriterion("Notify_Total in", values, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalNotIn(List<Integer> values) {
            addCriterion("Notify_Total not in", values, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalBetween(Integer value1, Integer value2) {
            addCriterion("Notify_Total between", value1, value2, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_TotalNotBetween(Integer value1, Integer value2) {
            addCriterion("Notify_Total not between", value1, value2, "notify_Total");
            return (Criteria) this;
        }

        public Criteria andNotify_DateIsNull() {
            addCriterion("Notify_Date is null");
            return (Criteria) this;
        }

        public Criteria andNotify_DateIsNotNull() {
            addCriterion("Notify_Date is not null");
            return (Criteria) this;
        }

        public Criteria andNotify_DateEqualTo(Date value) {
            addCriterion("Notify_Date =", value, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateNotEqualTo(Date value) {
            addCriterion("Notify_Date <>", value, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateGreaterThan(Date value) {
            addCriterion("Notify_Date >", value, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateGreaterThanOrEqualTo(Date value) {
            addCriterion("Notify_Date >=", value, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateLessThan(Date value) {
            addCriterion("Notify_Date <", value, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateLessThanOrEqualTo(Date value) {
            addCriterion("Notify_Date <=", value, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateIn(List<Date> values) {
            addCriterion("Notify_Date in", values, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateNotIn(List<Date> values) {
            addCriterion("Notify_Date not in", values, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateBetween(Date value1, Date value2) {
            addCriterion("Notify_Date between", value1, value2, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_DateNotBetween(Date value1, Date value2) {
            addCriterion("Notify_Date not between", value1, value2, "notify_Date");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeIsNull() {
            addCriterion("Notify_Status_Code is null");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeIsNotNull() {
            addCriterion("Notify_Status_Code is not null");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeEqualTo(String value) {
            addCriterion("Notify_Status_Code =", value, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeNotEqualTo(String value) {
            addCriterion("Notify_Status_Code <>", value, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeGreaterThan(String value) {
            addCriterion("Notify_Status_Code >", value, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Notify_Status_Code >=", value, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeLessThan(String value) {
            addCriterion("Notify_Status_Code <", value, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeLessThanOrEqualTo(String value) {
            addCriterion("Notify_Status_Code <=", value, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeLike(String value) {
            addCriterion("Notify_Status_Code like", value, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeNotLike(String value) {
            addCriterion("Notify_Status_Code not like", value, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeIn(List<String> values) {
            addCriterion("Notify_Status_Code in", values, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeNotIn(List<String> values) {
            addCriterion("Notify_Status_Code not in", values, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeBetween(String value1, String value2) {
            addCriterion("Notify_Status_Code between", value1, value2, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andNotify_Status_CodeNotBetween(String value1, String value2) {
            addCriterion("Notify_Status_Code not between", value1, value2, "notify_Status_Code");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPIsNull() {
            addCriterion("Sender_Local_IP is null");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPIsNotNull() {
            addCriterion("Sender_Local_IP is not null");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPEqualTo(String value) {
            addCriterion("Sender_Local_IP =", value, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPNotEqualTo(String value) {
            addCriterion("Sender_Local_IP <>", value, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPGreaterThan(String value) {
            addCriterion("Sender_Local_IP >", value, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPGreaterThanOrEqualTo(String value) {
            addCriterion("Sender_Local_IP >=", value, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPLessThan(String value) {
            addCriterion("Sender_Local_IP <", value, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPLessThanOrEqualTo(String value) {
            addCriterion("Sender_Local_IP <=", value, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPLike(String value) {
            addCriterion("Sender_Local_IP like", value, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPNotLike(String value) {
            addCriterion("Sender_Local_IP not like", value, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPIn(List<String> values) {
            addCriterion("Sender_Local_IP in", values, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPNotIn(List<String> values) {
            addCriterion("Sender_Local_IP not in", values, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPBetween(String value1, String value2) {
            addCriterion("Sender_Local_IP between", value1, value2, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andSender_Local_IPNotBetween(String value1, String value2) {
            addCriterion("Sender_Local_IP not between", value1, value2, "sender_Local_IP");
            return (Criteria) this;
        }

        public Criteria andCharsetIsNull() {
            addCriterion("Charset is null");
            return (Criteria) this;
        }

        public Criteria andCharsetIsNotNull() {
            addCriterion("Charset is not null");
            return (Criteria) this;
        }

        public Criteria andCharsetEqualTo(String value) {
            addCriterion("Charset =", value, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetNotEqualTo(String value) {
            addCriterion("Charset <>", value, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetGreaterThan(String value) {
            addCriterion("Charset >", value, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetGreaterThanOrEqualTo(String value) {
            addCriterion("Charset >=", value, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetLessThan(String value) {
            addCriterion("Charset <", value, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetLessThanOrEqualTo(String value) {
            addCriterion("Charset <=", value, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetLike(String value) {
            addCriterion("Charset like", value, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetNotLike(String value) {
            addCriterion("Charset not like", value, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetIn(List<String> values) {
            addCriterion("Charset in", values, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetNotIn(List<String> values) {
            addCriterion("Charset not in", values, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetBetween(String value1, String value2) {
            addCriterion("Charset between", value1, value2, "charset");
            return (Criteria) this;
        }

        public Criteria andCharsetNotBetween(String value1, String value2) {
            addCriterion("Charset not between", value1, value2, "charset");
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