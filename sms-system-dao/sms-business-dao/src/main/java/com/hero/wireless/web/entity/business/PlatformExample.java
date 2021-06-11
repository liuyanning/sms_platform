package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlatformExample extends BaseExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    protected String dataLock;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public PlatformExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public void setDataLock(String dataLock) {
        this.dataLock = dataLock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public String getDataLock() {
        return dataLock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
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

        public Criteria andPlatform_NoIsNull() {
            addCriterion("Platform_No is null");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoIsNotNull() {
            addCriterion("Platform_No is not null");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoEqualTo(String value) {
            addCriterion("Platform_No =", value, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoNotEqualTo(String value) {
            addCriterion("Platform_No <>", value, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoGreaterThan(String value) {
            addCriterion("Platform_No >", value, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoGreaterThanOrEqualTo(String value) {
            addCriterion("Platform_No >=", value, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoLessThan(String value) {
            addCriterion("Platform_No <", value, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoLessThanOrEqualTo(String value) {
            addCriterion("Platform_No <=", value, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoLike(String value) {
            addCriterion("Platform_No like", value, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoNotLike(String value) {
            addCriterion("Platform_No not like", value, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoIn(List<String> values) {
            addCriterion("Platform_No in", values, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoNotIn(List<String> values) {
            addCriterion("Platform_No not in", values, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoBetween(String value1, String value2) {
            addCriterion("Platform_No between", value1, value2, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NoNotBetween(String value1, String value2) {
            addCriterion("Platform_No not between", value1, value2, "platform_No");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameIsNull() {
            addCriterion("Platform_Name is null");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameIsNotNull() {
            addCriterion("Platform_Name is not null");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameEqualTo(String value) {
            addCriterion("Platform_Name =", value, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameNotEqualTo(String value) {
            addCriterion("Platform_Name <>", value, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameGreaterThan(String value) {
            addCriterion("Platform_Name >", value, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameGreaterThanOrEqualTo(String value) {
            addCriterion("Platform_Name >=", value, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameLessThan(String value) {
            addCriterion("Platform_Name <", value, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameLessThanOrEqualTo(String value) {
            addCriterion("Platform_Name <=", value, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameLike(String value) {
            addCriterion("Platform_Name like", value, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameNotLike(String value) {
            addCriterion("Platform_Name not like", value, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameIn(List<String> values) {
            addCriterion("Platform_Name in", values, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameNotIn(List<String> values) {
            addCriterion("Platform_Name not in", values, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameBetween(String value1, String value2) {
            addCriterion("Platform_Name between", value1, value2, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andPlatform_NameNotBetween(String value1, String value2) {
            addCriterion("Platform_Name not between", value1, value2, "platform_Name");
            return (Criteria) this;
        }

        public Criteria andSign_KeyIsNull() {
            addCriterion("Sign_Key is null");
            return (Criteria) this;
        }

        public Criteria andSign_KeyIsNotNull() {
            addCriterion("Sign_Key is not null");
            return (Criteria) this;
        }

        public Criteria andSign_KeyEqualTo(String value) {
            addCriterion("Sign_Key =", value, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyNotEqualTo(String value) {
            addCriterion("Sign_Key <>", value, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyGreaterThan(String value) {
            addCriterion("Sign_Key >", value, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyGreaterThanOrEqualTo(String value) {
            addCriterion("Sign_Key >=", value, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyLessThan(String value) {
            addCriterion("Sign_Key <", value, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyLessThanOrEqualTo(String value) {
            addCriterion("Sign_Key <=", value, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyLike(String value) {
            addCriterion("Sign_Key like", value, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyNotLike(String value) {
            addCriterion("Sign_Key not like", value, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyIn(List<String> values) {
            addCriterion("Sign_Key in", values, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyNotIn(List<String> values) {
            addCriterion("Sign_Key not in", values, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyBetween(String value1, String value2) {
            addCriterion("Sign_Key between", value1, value2, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andSign_KeyNotBetween(String value1, String value2) {
            addCriterion("Sign_Key not between", value1, value2, "sign_Key");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusIsNull() {
            addCriterion("Statistics_Status is null");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusIsNotNull() {
            addCriterion("Statistics_Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusEqualTo(Boolean value) {
            addCriterion("Statistics_Status =", value, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusNotEqualTo(Boolean value) {
            addCriterion("Statistics_Status <>", value, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusGreaterThan(Boolean value) {
            addCriterion("Statistics_Status >", value, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("Statistics_Status >=", value, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusLessThan(Boolean value) {
            addCriterion("Statistics_Status <", value, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusLessThanOrEqualTo(Boolean value) {
            addCriterion("Statistics_Status <=", value, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusIn(List<Boolean> values) {
            addCriterion("Statistics_Status in", values, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusNotIn(List<Boolean> values) {
            addCriterion("Statistics_Status not in", values, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusBetween(Boolean value1, Boolean value2) {
            addCriterion("Statistics_Status between", value1, value2, "statistics_Status");
            return (Criteria) this;
        }

        public Criteria andStatistics_StatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("Statistics_Status not between", value1, value2, "statistics_Status");
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

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table platform
     *
     * @mbg.generated do_not_delete_during_merge Wed Jun 10 18:52:20 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table platform
     *
     * @mbg.generated Wed Jun 10 18:52:20 CST 2020
     */
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