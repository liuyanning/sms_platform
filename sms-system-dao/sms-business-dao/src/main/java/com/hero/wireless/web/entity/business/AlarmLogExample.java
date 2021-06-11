package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlarmLogExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected String dataLock;

    protected List<Criteria> oredCriteria;

    public AlarmLogExample() {
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

        public Criteria andAlarm_IdIsNull() {
            addCriterion("Alarm_Id is null");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdIsNotNull() {
            addCriterion("Alarm_Id is not null");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdEqualTo(Integer value) {
            addCriterion("Alarm_Id =", value, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdNotEqualTo(Integer value) {
            addCriterion("Alarm_Id <>", value, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdGreaterThan(Integer value) {
            addCriterion("Alarm_Id >", value, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Alarm_Id >=", value, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdLessThan(Integer value) {
            addCriterion("Alarm_Id <", value, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdLessThanOrEqualTo(Integer value) {
            addCriterion("Alarm_Id <=", value, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdIn(List<Integer> values) {
            addCriterion("Alarm_Id in", values, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdNotIn(List<Integer> values) {
            addCriterion("Alarm_Id not in", values, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdBetween(Integer value1, Integer value2) {
            addCriterion("Alarm_Id between", value1, value2, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andAlarm_IdNotBetween(Integer value1, Integer value2) {
            addCriterion("Alarm_Id not between", value1, value2, "alarm_Id");
            return (Criteria) this;
        }

        public Criteria andType_CodeIsNull() {
            addCriterion("Type_Code is null");
            return (Criteria) this;
        }

        public Criteria andType_CodeIsNotNull() {
            addCriterion("Type_Code is not null");
            return (Criteria) this;
        }

        public Criteria andType_CodeEqualTo(String value) {
            addCriterion("Type_Code =", value, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeNotEqualTo(String value) {
            addCriterion("Type_Code <>", value, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeGreaterThan(String value) {
            addCriterion("Type_Code >", value, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Type_Code >=", value, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeLessThan(String value) {
            addCriterion("Type_Code <", value, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeLessThanOrEqualTo(String value) {
            addCriterion("Type_Code <=", value, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeLike(String value) {
            addCriterion("Type_Code like", value, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeNotLike(String value) {
            addCriterion("Type_Code not like", value, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeIn(List<String> values) {
            addCriterion("Type_Code in", values, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeNotIn(List<String> values) {
            addCriterion("Type_Code not in", values, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeBetween(String value1, String value2) {
            addCriterion("Type_Code between", value1, value2, "type_Code");
            return (Criteria) this;
        }

        public Criteria andType_CodeNotBetween(String value1, String value2) {
            addCriterion("Type_Code not between", value1, value2, "type_Code");
            return (Criteria) this;
        }

        public Criteria andBind_ValueIsNull() {
            addCriterion("Bind_Value is null");
            return (Criteria) this;
        }

        public Criteria andBind_ValueIsNotNull() {
            addCriterion("Bind_Value is not null");
            return (Criteria) this;
        }

        public Criteria andBind_ValueEqualTo(String value) {
            addCriterion("Bind_Value =", value, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueNotEqualTo(String value) {
            addCriterion("Bind_Value <>", value, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueGreaterThan(String value) {
            addCriterion("Bind_Value >", value, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueGreaterThanOrEqualTo(String value) {
            addCriterion("Bind_Value >=", value, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueLessThan(String value) {
            addCriterion("Bind_Value <", value, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueLessThanOrEqualTo(String value) {
            addCriterion("Bind_Value <=", value, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueLike(String value) {
            addCriterion("Bind_Value like", value, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueNotLike(String value) {
            addCriterion("Bind_Value not like", value, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueIn(List<String> values) {
            addCriterion("Bind_Value in", values, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueNotIn(List<String> values) {
            addCriterion("Bind_Value not in", values, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueBetween(String value1, String value2) {
            addCriterion("Bind_Value between", value1, value2, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andBind_ValueNotBetween(String value1, String value2) {
            addCriterion("Bind_Value not between", value1, value2, "bind_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueIsNull() {
            addCriterion("Threshold_Value is null");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueIsNotNull() {
            addCriterion("Threshold_Value is not null");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueEqualTo(String value) {
            addCriterion("Threshold_Value =", value, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueNotEqualTo(String value) {
            addCriterion("Threshold_Value <>", value, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueGreaterThan(String value) {
            addCriterion("Threshold_Value >", value, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueGreaterThanOrEqualTo(String value) {
            addCriterion("Threshold_Value >=", value, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueLessThan(String value) {
            addCriterion("Threshold_Value <", value, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueLessThanOrEqualTo(String value) {
            addCriterion("Threshold_Value <=", value, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueLike(String value) {
            addCriterion("Threshold_Value like", value, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueNotLike(String value) {
            addCriterion("Threshold_Value not like", value, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueIn(List<String> values) {
            addCriterion("Threshold_Value in", values, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueNotIn(List<String> values) {
            addCriterion("Threshold_Value not in", values, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueBetween(String value1, String value2) {
            addCriterion("Threshold_Value between", value1, value2, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andThreshold_ValueNotBetween(String value1, String value2) {
            addCriterion("Threshold_Value not between", value1, value2, "threshold_Value");
            return (Criteria) this;
        }

        public Criteria andBegin_DateIsNull() {
            addCriterion("Begin_Date is null");
            return (Criteria) this;
        }

        public Criteria andBegin_DateIsNotNull() {
            addCriterion("Begin_Date is not null");
            return (Criteria) this;
        }

        public Criteria andBegin_DateEqualTo(Date value) {
            addCriterion("Begin_Date =", value, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateNotEqualTo(Date value) {
            addCriterion("Begin_Date <>", value, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateGreaterThan(Date value) {
            addCriterion("Begin_Date >", value, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateGreaterThanOrEqualTo(Date value) {
            addCriterion("Begin_Date >=", value, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateLessThan(Date value) {
            addCriterion("Begin_Date <", value, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateLessThanOrEqualTo(Date value) {
            addCriterion("Begin_Date <=", value, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateIn(List<Date> values) {
            addCriterion("Begin_Date in", values, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateNotIn(List<Date> values) {
            addCriterion("Begin_Date not in", values, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateBetween(Date value1, Date value2) {
            addCriterion("Begin_Date between", value1, value2, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andBegin_DateNotBetween(Date value1, Date value2) {
            addCriterion("Begin_Date not between", value1, value2, "begin_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateIsNull() {
            addCriterion("End_Date is null");
            return (Criteria) this;
        }

        public Criteria andEnd_DateIsNotNull() {
            addCriterion("End_Date is not null");
            return (Criteria) this;
        }

        public Criteria andEnd_DateEqualTo(Date value) {
            addCriterion("End_Date =", value, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateNotEqualTo(Date value) {
            addCriterion("End_Date <>", value, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateGreaterThan(Date value) {
            addCriterion("End_Date >", value, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateGreaterThanOrEqualTo(Date value) {
            addCriterion("End_Date >=", value, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateLessThan(Date value) {
            addCriterion("End_Date <", value, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateLessThanOrEqualTo(Date value) {
            addCriterion("End_Date <=", value, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateIn(List<Date> values) {
            addCriterion("End_Date in", values, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateNotIn(List<Date> values) {
            addCriterion("End_Date not in", values, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateBetween(Date value1, Date value2) {
            addCriterion("End_Date between", value1, value2, "end_Date");
            return (Criteria) this;
        }

        public Criteria andEnd_DateNotBetween(Date value1, Date value2) {
            addCriterion("End_Date not between", value1, value2, "end_Date");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueIsNull() {
            addCriterion("Probe_Value is null");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueIsNotNull() {
            addCriterion("Probe_Value is not null");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueEqualTo(String value) {
            addCriterion("Probe_Value =", value, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueNotEqualTo(String value) {
            addCriterion("Probe_Value <>", value, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueGreaterThan(String value) {
            addCriterion("Probe_Value >", value, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueGreaterThanOrEqualTo(String value) {
            addCriterion("Probe_Value >=", value, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueLessThan(String value) {
            addCriterion("Probe_Value <", value, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueLessThanOrEqualTo(String value) {
            addCriterion("Probe_Value <=", value, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueLike(String value) {
            addCriterion("Probe_Value like", value, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueNotLike(String value) {
            addCriterion("Probe_Value not like", value, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueIn(List<String> values) {
            addCriterion("Probe_Value in", values, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueNotIn(List<String> values) {
            addCriterion("Probe_Value not in", values, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueBetween(String value1, String value2) {
            addCriterion("Probe_Value between", value1, value2, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ValueNotBetween(String value1, String value2) {
            addCriterion("Probe_Value not between", value1, value2, "probe_Value");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultIsNull() {
            addCriterion("Probe_Result is null");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultIsNotNull() {
            addCriterion("Probe_Result is not null");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultEqualTo(String value) {
            addCriterion("Probe_Result =", value, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultNotEqualTo(String value) {
            addCriterion("Probe_Result <>", value, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultGreaterThan(String value) {
            addCriterion("Probe_Result >", value, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultGreaterThanOrEqualTo(String value) {
            addCriterion("Probe_Result >=", value, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultLessThan(String value) {
            addCriterion("Probe_Result <", value, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultLessThanOrEqualTo(String value) {
            addCriterion("Probe_Result <=", value, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultLike(String value) {
            addCriterion("Probe_Result like", value, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultNotLike(String value) {
            addCriterion("Probe_Result not like", value, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultIn(List<String> values) {
            addCriterion("Probe_Result in", values, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultNotIn(List<String> values) {
            addCriterion("Probe_Result not in", values, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultBetween(String value1, String value2) {
            addCriterion("Probe_Result between", value1, value2, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andProbe_ResultNotBetween(String value1, String value2) {
            addCriterion("Probe_Result not between", value1, value2, "probe_Result");
            return (Criteria) this;
        }

        public Criteria andPhone_NosIsNull() {
            addCriterion("Phone_Nos is null");
            return (Criteria) this;
        }

        public Criteria andPhone_NosIsNotNull() {
            addCriterion("Phone_Nos is not null");
            return (Criteria) this;
        }

        public Criteria andPhone_NosEqualTo(String value) {
            addCriterion("Phone_Nos =", value, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosNotEqualTo(String value) {
            addCriterion("Phone_Nos <>", value, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosGreaterThan(String value) {
            addCriterion("Phone_Nos >", value, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosGreaterThanOrEqualTo(String value) {
            addCriterion("Phone_Nos >=", value, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosLessThan(String value) {
            addCriterion("Phone_Nos <", value, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosLessThanOrEqualTo(String value) {
            addCriterion("Phone_Nos <=", value, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosLike(String value) {
            addCriterion("Phone_Nos like", value, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosNotLike(String value) {
            addCriterion("Phone_Nos not like", value, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosIn(List<String> values) {
            addCriterion("Phone_Nos in", values, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosNotIn(List<String> values) {
            addCriterion("Phone_Nos not in", values, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosBetween(String value1, String value2) {
            addCriterion("Phone_Nos between", value1, value2, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andPhone_NosNotBetween(String value1, String value2) {
            addCriterion("Phone_Nos not between", value1, value2, "phone_Nos");
            return (Criteria) this;
        }

        public Criteria andEmailsIsNull() {
            addCriterion("Emails is null");
            return (Criteria) this;
        }

        public Criteria andEmailsIsNotNull() {
            addCriterion("Emails is not null");
            return (Criteria) this;
        }

        public Criteria andEmailsEqualTo(String value) {
            addCriterion("Emails =", value, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsNotEqualTo(String value) {
            addCriterion("Emails <>", value, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsGreaterThan(String value) {
            addCriterion("Emails >", value, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsGreaterThanOrEqualTo(String value) {
            addCriterion("Emails >=", value, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsLessThan(String value) {
            addCriterion("Emails <", value, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsLessThanOrEqualTo(String value) {
            addCriterion("Emails <=", value, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsLike(String value) {
            addCriterion("Emails like", value, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsNotLike(String value) {
            addCriterion("Emails not like", value, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsIn(List<String> values) {
            addCriterion("Emails in", values, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsNotIn(List<String> values) {
            addCriterion("Emails not in", values, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsBetween(String value1, String value2) {
            addCriterion("Emails between", value1, value2, "emails");
            return (Criteria) this;
        }

        public Criteria andEmailsNotBetween(String value1, String value2) {
            addCriterion("Emails not between", value1, value2, "emails");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("Description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("Description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("Description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("Description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("Description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("Description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("Description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("Description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("Description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("Description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("Description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("Description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("Description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("Description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("Remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("Remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
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