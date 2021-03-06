package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseExample;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnterpriseUserFeeExample extends BaseExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    protected String dataLock;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public EnterpriseUserFeeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public void setDataLock(String dataLock) {
        this.dataLock = dataLock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public String getDataLock() {
        return dataLock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
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
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
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

        public Criteria andOperatorIsNull() {
            addCriterion("Operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("Operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("Operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("Operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("Operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("Operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("Operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("Operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("Operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("Operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("Operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("Operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("Operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("Operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceIsNull() {
            addCriterion("Unit_Price is null");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceIsNotNull() {
            addCriterion("Unit_Price is not null");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceEqualTo(BigDecimal value) {
            addCriterion("Unit_Price =", value, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceNotEqualTo(BigDecimal value) {
            addCriterion("Unit_Price <>", value, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceGreaterThan(BigDecimal value) {
            addCriterion("Unit_Price >", value, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Unit_Price >=", value, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceLessThan(BigDecimal value) {
            addCriterion("Unit_Price <", value, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Unit_Price <=", value, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceIn(List<BigDecimal> values) {
            addCriterion("Unit_Price in", values, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceNotIn(List<BigDecimal> values) {
            addCriterion("Unit_Price not in", values, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Unit_Price between", value1, value2, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andUnit_PriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Unit_Price not between", value1, value2, "unit_Price");
            return (Criteria) this;
        }

        public Criteria andTax_PointIsNull() {
            addCriterion("Tax_Point is null");
            return (Criteria) this;
        }

        public Criteria andTax_PointIsNotNull() {
            addCriterion("Tax_Point is not null");
            return (Criteria) this;
        }

        public Criteria andTax_PointEqualTo(BigDecimal value) {
            addCriterion("Tax_Point =", value, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointNotEqualTo(BigDecimal value) {
            addCriterion("Tax_Point <>", value, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointGreaterThan(BigDecimal value) {
            addCriterion("Tax_Point >", value, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Tax_Point >=", value, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointLessThan(BigDecimal value) {
            addCriterion("Tax_Point <", value, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Tax_Point <=", value, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointIn(List<BigDecimal> values) {
            addCriterion("Tax_Point in", values, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointNotIn(List<BigDecimal> values) {
            addCriterion("Tax_Point not in", values, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Tax_Point between", value1, value2, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTax_PointNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Tax_Point not between", value1, value2, "tax_Point");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeIsNull() {
            addCriterion("Trade_Type_Code is null");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeIsNotNull() {
            addCriterion("Trade_Type_Code is not null");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeEqualTo(String value) {
            addCriterion("Trade_Type_Code =", value, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeNotEqualTo(String value) {
            addCriterion("Trade_Type_Code <>", value, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeGreaterThan(String value) {
            addCriterion("Trade_Type_Code >", value, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Trade_Type_Code >=", value, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeLessThan(String value) {
            addCriterion("Trade_Type_Code <", value, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeLessThanOrEqualTo(String value) {
            addCriterion("Trade_Type_Code <=", value, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeLike(String value) {
            addCriterion("Trade_Type_Code like", value, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeNotLike(String value) {
            addCriterion("Trade_Type_Code not like", value, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeIn(List<String> values) {
            addCriterion("Trade_Type_Code in", values, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeNotIn(List<String> values) {
            addCriterion("Trade_Type_Code not in", values, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeBetween(String value1, String value2) {
            addCriterion("Trade_Type_Code between", value1, value2, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andTrade_Type_CodeNotBetween(String value1, String value2) {
            addCriterion("Trade_Type_Code not between", value1, value2, "trade_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeIsNull() {
            addCriterion("Message_Type_Code is null");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeIsNotNull() {
            addCriterion("Message_Type_Code is not null");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeEqualTo(String value) {
            addCriterion("Message_Type_Code =", value, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeNotEqualTo(String value) {
            addCriterion("Message_Type_Code <>", value, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeGreaterThan(String value) {
            addCriterion("Message_Type_Code >", value, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Message_Type_Code >=", value, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeLessThan(String value) {
            addCriterion("Message_Type_Code <", value, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeLessThanOrEqualTo(String value) {
            addCriterion("Message_Type_Code <=", value, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeLike(String value) {
            addCriterion("Message_Type_Code like", value, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeNotLike(String value) {
            addCriterion("Message_Type_Code not like", value, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeIn(List<String> values) {
            addCriterion("Message_Type_Code in", values, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeNotIn(List<String> values) {
            addCriterion("Message_Type_Code not in", values, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeBetween(String value1, String value2) {
            addCriterion("Message_Type_Code between", value1, value2, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andMessage_Type_CodeNotBetween(String value1, String value2) {
            addCriterion("Message_Type_Code not between", value1, value2, "message_Type_Code");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberIsNull() {
            addCriterion("Country_Number is null");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberIsNotNull() {
            addCriterion("Country_Number is not null");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberEqualTo(String value) {
            addCriterion("Country_Number =", value, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberNotEqualTo(String value) {
            addCriterion("Country_Number <>", value, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberGreaterThan(String value) {
            addCriterion("Country_Number >", value, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberGreaterThanOrEqualTo(String value) {
            addCriterion("Country_Number >=", value, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberLessThan(String value) {
            addCriterion("Country_Number <", value, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberLessThanOrEqualTo(String value) {
            addCriterion("Country_Number <=", value, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberLike(String value) {
            addCriterion("Country_Number like", value, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberNotLike(String value) {
            addCriterion("Country_Number not like", value, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberIn(List<String> values) {
            addCriterion("Country_Number in", values, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberNotIn(List<String> values) {
            addCriterion("Country_Number not in", values, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberBetween(String value1, String value2) {
            addCriterion("Country_Number between", value1, value2, "country_Number");
            return (Criteria) this;
        }

        public Criteria andCountry_NumberNotBetween(String value1, String value2) {
            addCriterion("Country_Number not between", value1, value2, "country_Number");
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
     * This class corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated do_not_delete_during_merge Mon Jun 15 15:56:38 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table enterprise_user_fee
     *
     * @mbg.generated Mon Jun 15 15:56:38 CST 2020
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