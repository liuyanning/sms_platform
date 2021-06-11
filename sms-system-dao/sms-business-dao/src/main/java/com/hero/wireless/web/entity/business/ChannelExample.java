package com.hero.wireless.web.entity.business;

import com.hero.wireless.web.entity.base.BaseExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChannelExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected String dataLock;

    protected List<Criteria> oredCriteria;

    public ChannelExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("Name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("Name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("Name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("Name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("Name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("Name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("Name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("Name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("Name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("Name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("Name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("Name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("Name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("Name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNoIsNull() {
            addCriterion("No is null");
            return (Criteria) this;
        }

        public Criteria andNoIsNotNull() {
            addCriterion("No is not null");
            return (Criteria) this;
        }

        public Criteria andNoEqualTo(String value) {
            addCriterion("No =", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotEqualTo(String value) {
            addCriterion("No <>", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThan(String value) {
            addCriterion("No >", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThanOrEqualTo(String value) {
            addCriterion("No >=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThan(String value) {
            addCriterion("No <", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThanOrEqualTo(String value) {
            addCriterion("No <=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLike(String value) {
            addCriterion("No like", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotLike(String value) {
            addCriterion("No not like", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoIn(List<String> values) {
            addCriterion("No in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotIn(List<String> values) {
            addCriterion("No not in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoBetween(String value1, String value2) {
            addCriterion("No between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotBetween(String value1, String value2) {
            addCriterion("No not between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeIsNull() {
            addCriterion("Status_Code is null");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeIsNotNull() {
            addCriterion("Status_Code is not null");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeEqualTo(String value) {
            addCriterion("Status_Code =", value, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeNotEqualTo(String value) {
            addCriterion("Status_Code <>", value, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeGreaterThan(String value) {
            addCriterion("Status_Code >", value, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Status_Code >=", value, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeLessThan(String value) {
            addCriterion("Status_Code <", value, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeLessThanOrEqualTo(String value) {
            addCriterion("Status_Code <=", value, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeLike(String value) {
            addCriterion("Status_Code like", value, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeNotLike(String value) {
            addCriterion("Status_Code not like", value, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeIn(List<String> values) {
            addCriterion("Status_Code in", values, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeNotIn(List<String> values) {
            addCriterion("Status_Code not in", values, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeBetween(String value1, String value2) {
            addCriterion("Status_Code between", value1, value2, "status_Code");
            return (Criteria) this;
        }

        public Criteria andStatus_CodeNotBetween(String value1, String value2) {
            addCriterion("Status_Code not between", value1, value2, "status_Code");
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

        public Criteria andSession_StatusIsNull() {
            addCriterion("Session_Status is null");
            return (Criteria) this;
        }

        public Criteria andSession_StatusIsNotNull() {
            addCriterion("Session_Status is not null");
            return (Criteria) this;
        }

        public Criteria andSession_StatusEqualTo(String value) {
            addCriterion("Session_Status =", value, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusNotEqualTo(String value) {
            addCriterion("Session_Status <>", value, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusGreaterThan(String value) {
            addCriterion("Session_Status >", value, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusGreaterThanOrEqualTo(String value) {
            addCriterion("Session_Status >=", value, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusLessThan(String value) {
            addCriterion("Session_Status <", value, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusLessThanOrEqualTo(String value) {
            addCriterion("Session_Status <=", value, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusLike(String value) {
            addCriterion("Session_Status like", value, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusNotLike(String value) {
            addCriterion("Session_Status not like", value, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusIn(List<String> values) {
            addCriterion("Session_Status in", values, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusNotIn(List<String> values) {
            addCriterion("Session_Status not in", values, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusBetween(String value1, String value2) {
            addCriterion("Session_Status between", value1, value2, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_StatusNotBetween(String value1, String value2) {
            addCriterion("Session_Status not between", value1, value2, "session_Status");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateIsNull() {
            addCriterion("Session_Status_Date is null");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateIsNotNull() {
            addCriterion("Session_Status_Date is not null");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateEqualTo(Date value) {
            addCriterion("Session_Status_Date =", value, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateNotEqualTo(Date value) {
            addCriterion("Session_Status_Date <>", value, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateGreaterThan(Date value) {
            addCriterion("Session_Status_Date >", value, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateGreaterThanOrEqualTo(Date value) {
            addCriterion("Session_Status_Date >=", value, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateLessThan(Date value) {
            addCriterion("Session_Status_Date <", value, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateLessThanOrEqualTo(Date value) {
            addCriterion("Session_Status_Date <=", value, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateIn(List<Date> values) {
            addCriterion("Session_Status_Date in", values, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateNotIn(List<Date> values) {
            addCriterion("Session_Status_Date not in", values, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateBetween(Date value1, Date value2) {
            addCriterion("Session_Status_Date between", value1, value2, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andSession_Status_DateNotBetween(Date value1, Date value2) {
            addCriterion("Session_Status_Date not between", value1, value2, "session_Status_Date");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("Ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("Ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("Ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("Ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("Ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("Ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("Ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("Ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("Ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("Ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("Ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("Ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("Ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("Ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andPortIsNull() {
            addCriterion("Port is null");
            return (Criteria) this;
        }

        public Criteria andPortIsNotNull() {
            addCriterion("Port is not null");
            return (Criteria) this;
        }

        public Criteria andPortEqualTo(Integer value) {
            addCriterion("Port =", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotEqualTo(Integer value) {
            addCriterion("Port <>", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThan(Integer value) {
            addCriterion("Port >", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("Port >=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThan(Integer value) {
            addCriterion("Port <", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThanOrEqualTo(Integer value) {
            addCriterion("Port <=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortIn(List<Integer> values) {
            addCriterion("Port in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotIn(List<Integer> values) {
            addCriterion("Port not in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortBetween(Integer value1, Integer value2) {
            addCriterion("Port between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotBetween(Integer value1, Integer value2) {
            addCriterion("Port not between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("Account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("Account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("Account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("Account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("Account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("Account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("Account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("Account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("Account like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("Account not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("Account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("Account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("Account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("Account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("Password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("Password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("Password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("Password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("Password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("Password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("Password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("Password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("Password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("Password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("Password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("Password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("Password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("Password not between", value1, value2, "password");
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

        public Criteria andArea_CodeIsNull() {
            addCriterion("Area_Code is null");
            return (Criteria) this;
        }

        public Criteria andArea_CodeIsNotNull() {
            addCriterion("Area_Code is not null");
            return (Criteria) this;
        }

        public Criteria andArea_CodeEqualTo(String value) {
            addCriterion("Area_Code =", value, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeNotEqualTo(String value) {
            addCriterion("Area_Code <>", value, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeGreaterThan(String value) {
            addCriterion("Area_Code >", value, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Area_Code >=", value, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeLessThan(String value) {
            addCriterion("Area_Code <", value, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeLessThanOrEqualTo(String value) {
            addCriterion("Area_Code <=", value, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeLike(String value) {
            addCriterion("Area_Code like", value, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeNotLike(String value) {
            addCriterion("Area_Code not like", value, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeIn(List<String> values) {
            addCriterion("Area_Code in", values, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeNotIn(List<String> values) {
            addCriterion("Area_Code not in", values, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeBetween(String value1, String value2) {
            addCriterion("Area_Code between", value1, value2, "area_Code");
            return (Criteria) this;
        }

        public Criteria andArea_CodeNotBetween(String value1, String value2) {
            addCriterion("Area_Code not between", value1, value2, "area_Code");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("Version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("Version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("Version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("Version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("Version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("Version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("Version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("Version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("Version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("Version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("Version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("Version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("Version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("Version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalIsNull() {
            addCriterion("Max_Concurrent_Total is null");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalIsNotNull() {
            addCriterion("Max_Concurrent_Total is not null");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalEqualTo(Integer value) {
            addCriterion("Max_Concurrent_Total =", value, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalNotEqualTo(Integer value) {
            addCriterion("Max_Concurrent_Total <>", value, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalGreaterThan(Integer value) {
            addCriterion("Max_Concurrent_Total >", value, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("Max_Concurrent_Total >=", value, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalLessThan(Integer value) {
            addCriterion("Max_Concurrent_Total <", value, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalLessThanOrEqualTo(Integer value) {
            addCriterion("Max_Concurrent_Total <=", value, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalIn(List<Integer> values) {
            addCriterion("Max_Concurrent_Total in", values, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalNotIn(List<Integer> values) {
            addCriterion("Max_Concurrent_Total not in", values, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalBetween(Integer value1, Integer value2) {
            addCriterion("Max_Concurrent_Total between", value1, value2, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andMax_Concurrent_TotalNotBetween(Integer value1, Integer value2) {
            addCriterion("Max_Concurrent_Total not between", value1, value2, "max_Concurrent_Total");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedIsNull() {
            addCriterion("Submit_Speed is null");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedIsNotNull() {
            addCriterion("Submit_Speed is not null");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedEqualTo(Integer value) {
            addCriterion("Submit_Speed =", value, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedNotEqualTo(Integer value) {
            addCriterion("Submit_Speed <>", value, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedGreaterThan(Integer value) {
            addCriterion("Submit_Speed >", value, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedGreaterThanOrEqualTo(Integer value) {
            addCriterion("Submit_Speed >=", value, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedLessThan(Integer value) {
            addCriterion("Submit_Speed <", value, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedLessThanOrEqualTo(Integer value) {
            addCriterion("Submit_Speed <=", value, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedIn(List<Integer> values) {
            addCriterion("Submit_Speed in", values, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedNotIn(List<Integer> values) {
            addCriterion("Submit_Speed not in", values, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedBetween(Integer value1, Integer value2) {
            addCriterion("Submit_Speed between", value1, value2, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSubmit_SpeedNotBetween(Integer value1, Integer value2) {
            addCriterion("Submit_Speed not between", value1, value2, "submit_Speed");
            return (Criteria) this;
        }

        public Criteria andSp_NumberIsNull() {
            addCriterion("Sp_Number is null");
            return (Criteria) this;
        }

        public Criteria andSp_NumberIsNotNull() {
            addCriterion("Sp_Number is not null");
            return (Criteria) this;
        }

        public Criteria andSp_NumberEqualTo(String value) {
            addCriterion("Sp_Number =", value, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberNotEqualTo(String value) {
            addCriterion("Sp_Number <>", value, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberGreaterThan(String value) {
            addCriterion("Sp_Number >", value, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberGreaterThanOrEqualTo(String value) {
            addCriterion("Sp_Number >=", value, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberLessThan(String value) {
            addCriterion("Sp_Number <", value, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberLessThanOrEqualTo(String value) {
            addCriterion("Sp_Number <=", value, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberLike(String value) {
            addCriterion("Sp_Number like", value, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberNotLike(String value) {
            addCriterion("Sp_Number not like", value, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberIn(List<String> values) {
            addCriterion("Sp_Number in", values, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberNotIn(List<String> values) {
            addCriterion("Sp_Number not in", values, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberBetween(String value1, String value2) {
            addCriterion("Sp_Number between", value1, value2, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSp_NumberNotBetween(String value1, String value2) {
            addCriterion("Sp_Number not between", value1, value2, "sp_Number");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeIsNull() {
            addCriterion("Signature_Direction_Code is null");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeIsNotNull() {
            addCriterion("Signature_Direction_Code is not null");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeEqualTo(String value) {
            addCriterion("Signature_Direction_Code =", value, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeNotEqualTo(String value) {
            addCriterion("Signature_Direction_Code <>", value, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeGreaterThan(String value) {
            addCriterion("Signature_Direction_Code >", value, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeGreaterThanOrEqualTo(String value) {
            addCriterion("Signature_Direction_Code >=", value, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeLessThan(String value) {
            addCriterion("Signature_Direction_Code <", value, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeLessThanOrEqualTo(String value) {
            addCriterion("Signature_Direction_Code <=", value, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeLike(String value) {
            addCriterion("Signature_Direction_Code like", value, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeNotLike(String value) {
            addCriterion("Signature_Direction_Code not like", value, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeIn(List<String> values) {
            addCriterion("Signature_Direction_Code in", values, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeNotIn(List<String> values) {
            addCriterion("Signature_Direction_Code not in", values, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeBetween(String value1, String value2) {
            addCriterion("Signature_Direction_Code between", value1, value2, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_Direction_CodeNotBetween(String value1, String value2) {
            addCriterion("Signature_Direction_Code not between", value1, value2, "signature_Direction_Code");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionIsNull() {
            addCriterion("Signature_Position is null");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionIsNotNull() {
            addCriterion("Signature_Position is not null");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionEqualTo(String value) {
            addCriterion("Signature_Position =", value, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionNotEqualTo(String value) {
            addCriterion("Signature_Position <>", value, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionGreaterThan(String value) {
            addCriterion("Signature_Position >", value, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionGreaterThanOrEqualTo(String value) {
            addCriterion("Signature_Position >=", value, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionLessThan(String value) {
            addCriterion("Signature_Position <", value, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionLessThanOrEqualTo(String value) {
            addCriterion("Signature_Position <=", value, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionLike(String value) {
            addCriterion("Signature_Position like", value, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionNotLike(String value) {
            addCriterion("Signature_Position not like", value, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionIn(List<String> values) {
            addCriterion("Signature_Position in", values, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionNotIn(List<String> values) {
            addCriterion("Signature_Position not in", values, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionBetween(String value1, String value2) {
            addCriterion("Signature_Position between", value1, value2, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andSignature_PositionNotBetween(String value1, String value2) {
            addCriterion("Signature_Position not between", value1, value2, "signature_Position");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceIsNull() {
            addCriterion("Channel_Source is null");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceIsNotNull() {
            addCriterion("Channel_Source is not null");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceEqualTo(String value) {
            addCriterion("Channel_Source =", value, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceNotEqualTo(String value) {
            addCriterion("Channel_Source <>", value, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceGreaterThan(String value) {
            addCriterion("Channel_Source >", value, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceGreaterThanOrEqualTo(String value) {
            addCriterion("Channel_Source >=", value, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceLessThan(String value) {
            addCriterion("Channel_Source <", value, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceLessThanOrEqualTo(String value) {
            addCriterion("Channel_Source <=", value, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceLike(String value) {
            addCriterion("Channel_Source like", value, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceNotLike(String value) {
            addCriterion("Channel_Source not like", value, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceIn(List<String> values) {
            addCriterion("Channel_Source in", values, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceNotIn(List<String> values) {
            addCriterion("Channel_Source not in", values, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceBetween(String value1, String value2) {
            addCriterion("Channel_Source between", value1, value2, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andChannel_SourceNotBetween(String value1, String value2) {
            addCriterion("Channel_Source not between", value1, value2, "channel_Source");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterIsNull() {
            addCriterion("Other_Parameter is null");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterIsNotNull() {
            addCriterion("Other_Parameter is not null");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterEqualTo(String value) {
            addCriterion("Other_Parameter =", value, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterNotEqualTo(String value) {
            addCriterion("Other_Parameter <>", value, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterGreaterThan(String value) {
            addCriterion("Other_Parameter >", value, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterGreaterThanOrEqualTo(String value) {
            addCriterion("Other_Parameter >=", value, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterLessThan(String value) {
            addCriterion("Other_Parameter <", value, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterLessThanOrEqualTo(String value) {
            addCriterion("Other_Parameter <=", value, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterLike(String value) {
            addCriterion("Other_Parameter like", value, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterNotLike(String value) {
            addCriterion("Other_Parameter not like", value, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterIn(List<String> values) {
            addCriterion("Other_Parameter in", values, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterNotIn(List<String> values) {
            addCriterion("Other_Parameter not in", values, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterBetween(String value1, String value2) {
            addCriterion("Other_Parameter between", value1, value2, "other_Parameter");
            return (Criteria) this;
        }

        public Criteria andOther_ParameterNotBetween(String value1, String value2) {
            addCriterion("Other_Parameter not between", value1, value2, "other_Parameter");
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