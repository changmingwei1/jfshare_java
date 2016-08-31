package com.jfshare.score.model;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class TbRequestInterfaceLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbRequestInterfaceLogExample() {
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
        protected List<Criterion> requestdateCriteria;

        protected List<Criterion> exptimeCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            requestdateCriteria = new ArrayList<Criterion>();
            exptimeCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getRequestdateCriteria() {
            return requestdateCriteria;
        }

        protected void addRequestdateCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            requestdateCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addRequestdateCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            requestdateCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getExptimeCriteria() {
            return exptimeCriteria;
        }

        protected void addExptimeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            exptimeCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addExptimeCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            exptimeCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || requestdateCriteria.size() > 0
                || exptimeCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(requestdateCriteria);
                allCriteria.addAll(exptimeCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAppcodeIsNull() {
            addCriterion("appcode is null");
            return (Criteria) this;
        }

        public Criteria andAppcodeIsNotNull() {
            addCriterion("appcode is not null");
            return (Criteria) this;
        }

        public Criteria andAppcodeEqualTo(String value) {
            addCriterion("appcode =", value, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeNotEqualTo(String value) {
            addCriterion("appcode <>", value, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeGreaterThan(String value) {
            addCriterion("appcode >", value, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeGreaterThanOrEqualTo(String value) {
            addCriterion("appcode >=", value, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeLessThan(String value) {
            addCriterion("appcode <", value, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeLessThanOrEqualTo(String value) {
            addCriterion("appcode <=", value, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeLike(String value) {
            addCriterion("appcode like", value, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeNotLike(String value) {
            addCriterion("appcode not like", value, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeIn(List<String> values) {
            addCriterion("appcode in", values, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeNotIn(List<String> values) {
            addCriterion("appcode not in", values, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeBetween(String value1, String value2) {
            addCriterion("appcode between", value1, value2, "appcode");
            return (Criteria) this;
        }

        public Criteria andAppcodeNotBetween(String value1, String value2) {
            addCriterion("appcode not between", value1, value2, "appcode");
            return (Criteria) this;
        }

        public Criteria andRequestdateIsNull() {
            addCriterion("requestdate is null");
            return (Criteria) this;
        }

        public Criteria andRequestdateIsNotNull() {
            addCriterion("requestdate is not null");
            return (Criteria) this;
        }

        public Criteria andRequestdateEqualTo(DateTime value) {
            addRequestdateCriterion("requestdate =", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateNotEqualTo(DateTime value) {
            addRequestdateCriterion("requestdate <>", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateGreaterThan(DateTime value) {
            addRequestdateCriterion("requestdate >", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateGreaterThanOrEqualTo(DateTime value) {
            addRequestdateCriterion("requestdate >=", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateLessThan(DateTime value) {
            addRequestdateCriterion("requestdate <", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateLessThanOrEqualTo(DateTime value) {
            addRequestdateCriterion("requestdate <=", value, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateIn(List<DateTime> values) {
            addRequestdateCriterion("requestdate in", values, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateNotIn(List<DateTime> values) {
            addRequestdateCriterion("requestdate not in", values, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateBetween(DateTime value1, DateTime value2) {
            addRequestdateCriterion("requestdate between", value1, value2, "requestdate");
            return (Criteria) this;
        }

        public Criteria andRequestdateNotBetween(DateTime value1, DateTime value2) {
            addRequestdateCriterion("requestdate not between", value1, value2, "requestdate");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(String value) {
            addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(String value) {
            addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThan(String value) {
            addCriterion("sign >", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThanOrEqualTo(String value) {
            addCriterion("sign >=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThan(String value) {
            addCriterion("sign <", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThanOrEqualTo(String value) {
            addCriterion("sign <=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLike(String value) {
            addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(String value) {
            addCriterion("sign not like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(List<String> values) {
            addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(List<String> values) {
            addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(String value1, String value2) {
            addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(String value1, String value2) {
            addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSpidIsNull() {
            addCriterion("spid is null");
            return (Criteria) this;
        }

        public Criteria andSpidIsNotNull() {
            addCriterion("spid is not null");
            return (Criteria) this;
        }

        public Criteria andSpidEqualTo(String value) {
            addCriterion("spid =", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidNotEqualTo(String value) {
            addCriterion("spid <>", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidGreaterThan(String value) {
            addCriterion("spid >", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidGreaterThanOrEqualTo(String value) {
            addCriterion("spid >=", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidLessThan(String value) {
            addCriterion("spid <", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidLessThanOrEqualTo(String value) {
            addCriterion("spid <=", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidLike(String value) {
            addCriterion("spid like", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidNotLike(String value) {
            addCriterion("spid not like", value, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidIn(List<String> values) {
            addCriterion("spid in", values, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidNotIn(List<String> values) {
            addCriterion("spid not in", values, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidBetween(String value1, String value2) {
            addCriterion("spid between", value1, value2, "spid");
            return (Criteria) this;
        }

        public Criteria andSpidNotBetween(String value1, String value2) {
            addCriterion("spid not between", value1, value2, "spid");
            return (Criteria) this;
        }

        public Criteria andOutorderidIsNull() {
            addCriterion("outorderid is null");
            return (Criteria) this;
        }

        public Criteria andOutorderidIsNotNull() {
            addCriterion("outorderid is not null");
            return (Criteria) this;
        }

        public Criteria andOutorderidEqualTo(String value) {
            addCriterion("outorderid =", value, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidNotEqualTo(String value) {
            addCriterion("outorderid <>", value, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidGreaterThan(String value) {
            addCriterion("outorderid >", value, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidGreaterThanOrEqualTo(String value) {
            addCriterion("outorderid >=", value, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidLessThan(String value) {
            addCriterion("outorderid <", value, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidLessThanOrEqualTo(String value) {
            addCriterion("outorderid <=", value, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidLike(String value) {
            addCriterion("outorderid like", value, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidNotLike(String value) {
            addCriterion("outorderid not like", value, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidIn(List<String> values) {
            addCriterion("outorderid in", values, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidNotIn(List<String> values) {
            addCriterion("outorderid not in", values, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidBetween(String value1, String value2) {
            addCriterion("outorderid between", value1, value2, "outorderid");
            return (Criteria) this;
        }

        public Criteria andOutorderidNotBetween(String value1, String value2) {
            addCriterion("outorderid not between", value1, value2, "outorderid");
            return (Criteria) this;
        }

        public Criteria andDevicenoIsNull() {
            addCriterion("deviceno is null");
            return (Criteria) this;
        }

        public Criteria andDevicenoIsNotNull() {
            addCriterion("deviceno is not null");
            return (Criteria) this;
        }

        public Criteria andDevicenoEqualTo(String value) {
            addCriterion("deviceno =", value, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoNotEqualTo(String value) {
            addCriterion("deviceno <>", value, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoGreaterThan(String value) {
            addCriterion("deviceno >", value, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoGreaterThanOrEqualTo(String value) {
            addCriterion("deviceno >=", value, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoLessThan(String value) {
            addCriterion("deviceno <", value, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoLessThanOrEqualTo(String value) {
            addCriterion("deviceno <=", value, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoLike(String value) {
            addCriterion("deviceno like", value, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoNotLike(String value) {
            addCriterion("deviceno not like", value, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoIn(List<String> values) {
            addCriterion("deviceno in", values, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoNotIn(List<String> values) {
            addCriterion("deviceno not in", values, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoBetween(String value1, String value2) {
            addCriterion("deviceno between", value1, value2, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicenoNotBetween(String value1, String value2) {
            addCriterion("deviceno not between", value1, value2, "deviceno");
            return (Criteria) this;
        }

        public Criteria andDevicetypeIsNull() {
            addCriterion("devicetype is null");
            return (Criteria) this;
        }

        public Criteria andDevicetypeIsNotNull() {
            addCriterion("devicetype is not null");
            return (Criteria) this;
        }

        public Criteria andDevicetypeEqualTo(String value) {
            addCriterion("devicetype =", value, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeNotEqualTo(String value) {
            addCriterion("devicetype <>", value, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeGreaterThan(String value) {
            addCriterion("devicetype >", value, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeGreaterThanOrEqualTo(String value) {
            addCriterion("devicetype >=", value, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeLessThan(String value) {
            addCriterion("devicetype <", value, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeLessThanOrEqualTo(String value) {
            addCriterion("devicetype <=", value, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeLike(String value) {
            addCriterion("devicetype like", value, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeNotLike(String value) {
            addCriterion("devicetype not like", value, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeIn(List<String> values) {
            addCriterion("devicetype in", values, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeNotIn(List<String> values) {
            addCriterion("devicetype not in", values, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeBetween(String value1, String value2) {
            addCriterion("devicetype between", value1, value2, "devicetype");
            return (Criteria) this;
        }

        public Criteria andDevicetypeNotBetween(String value1, String value2) {
            addCriterion("devicetype not between", value1, value2, "devicetype");
            return (Criteria) this;
        }

        public Criteria andProvinceidIsNull() {
            addCriterion("provinceid is null");
            return (Criteria) this;
        }

        public Criteria andProvinceidIsNotNull() {
            addCriterion("provinceid is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceidEqualTo(String value) {
            addCriterion("provinceid =", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidNotEqualTo(String value) {
            addCriterion("provinceid <>", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidGreaterThan(String value) {
            addCriterion("provinceid >", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidGreaterThanOrEqualTo(String value) {
            addCriterion("provinceid >=", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidLessThan(String value) {
            addCriterion("provinceid <", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidLessThanOrEqualTo(String value) {
            addCriterion("provinceid <=", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidLike(String value) {
            addCriterion("provinceid like", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidNotLike(String value) {
            addCriterion("provinceid not like", value, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidIn(List<String> values) {
            addCriterion("provinceid in", values, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidNotIn(List<String> values) {
            addCriterion("provinceid not in", values, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidBetween(String value1, String value2) {
            addCriterion("provinceid between", value1, value2, "provinceid");
            return (Criteria) this;
        }

        public Criteria andProvinceidNotBetween(String value1, String value2) {
            addCriterion("provinceid not between", value1, value2, "provinceid");
            return (Criteria) this;
        }

        public Criteria andCustidIsNull() {
            addCriterion("custid is null");
            return (Criteria) this;
        }

        public Criteria andCustidIsNotNull() {
            addCriterion("custid is not null");
            return (Criteria) this;
        }

        public Criteria andCustidEqualTo(String value) {
            addCriterion("custid =", value, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidNotEqualTo(String value) {
            addCriterion("custid <>", value, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidGreaterThan(String value) {
            addCriterion("custid >", value, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidGreaterThanOrEqualTo(String value) {
            addCriterion("custid >=", value, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidLessThan(String value) {
            addCriterion("custid <", value, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidLessThanOrEqualTo(String value) {
            addCriterion("custid <=", value, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidLike(String value) {
            addCriterion("custid like", value, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidNotLike(String value) {
            addCriterion("custid not like", value, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidIn(List<String> values) {
            addCriterion("custid in", values, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidNotIn(List<String> values) {
            addCriterion("custid not in", values, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidBetween(String value1, String value2) {
            addCriterion("custid between", value1, value2, "custid");
            return (Criteria) this;
        }

        public Criteria andCustidNotBetween(String value1, String value2) {
            addCriterion("custid not between", value1, value2, "custid");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(String value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(String value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(String value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(String value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(String value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(String value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLike(String value) {
            addCriterion("num like", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotLike(String value) {
            addCriterion("num not like", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<String> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<String> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(String value1, String value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(String value1, String value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andActiveidIsNull() {
            addCriterion("activeid is null");
            return (Criteria) this;
        }

        public Criteria andActiveidIsNotNull() {
            addCriterion("activeid is not null");
            return (Criteria) this;
        }

        public Criteria andActiveidEqualTo(String value) {
            addCriterion("activeid =", value, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidNotEqualTo(String value) {
            addCriterion("activeid <>", value, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidGreaterThan(String value) {
            addCriterion("activeid >", value, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidGreaterThanOrEqualTo(String value) {
            addCriterion("activeid >=", value, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidLessThan(String value) {
            addCriterion("activeid <", value, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidLessThanOrEqualTo(String value) {
            addCriterion("activeid <=", value, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidLike(String value) {
            addCriterion("activeid like", value, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidNotLike(String value) {
            addCriterion("activeid not like", value, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidIn(List<String> values) {
            addCriterion("activeid in", values, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidNotIn(List<String> values) {
            addCriterion("activeid not in", values, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidBetween(String value1, String value2) {
            addCriterion("activeid between", value1, value2, "activeid");
            return (Criteria) this;
        }

        public Criteria andActiveidNotBetween(String value1, String value2) {
            addCriterion("activeid not between", value1, value2, "activeid");
            return (Criteria) this;
        }

        public Criteria andExptimeIsNull() {
            addCriterion("exptime is null");
            return (Criteria) this;
        }

        public Criteria andExptimeIsNotNull() {
            addCriterion("exptime is not null");
            return (Criteria) this;
        }

        public Criteria andExptimeEqualTo(DateTime value) {
            addExptimeCriterion("exptime =", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeNotEqualTo(DateTime value) {
            addExptimeCriterion("exptime <>", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeGreaterThan(DateTime value) {
            addExptimeCriterion("exptime >", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeGreaterThanOrEqualTo(DateTime value) {
            addExptimeCriterion("exptime >=", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeLessThan(DateTime value) {
            addExptimeCriterion("exptime <", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeLessThanOrEqualTo(DateTime value) {
            addExptimeCriterion("exptime <=", value, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeIn(List<DateTime> values) {
            addExptimeCriterion("exptime in", values, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeNotIn(List<DateTime> values) {
            addExptimeCriterion("exptime not in", values, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeBetween(DateTime value1, DateTime value2) {
            addExptimeCriterion("exptime between", value1, value2, "exptime");
            return (Criteria) this;
        }

        public Criteria andExptimeNotBetween(DateTime value1, DateTime value2) {
            addExptimeCriterion("exptime not between", value1, value2, "exptime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSysnameIsNull() {
            addCriterion("sysname is null");
            return (Criteria) this;
        }

        public Criteria andSysnameIsNotNull() {
            addCriterion("sysname is not null");
            return (Criteria) this;
        }

        public Criteria andSysnameEqualTo(String value) {
            addCriterion("sysname =", value, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameNotEqualTo(String value) {
            addCriterion("sysname <>", value, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameGreaterThan(String value) {
            addCriterion("sysname >", value, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameGreaterThanOrEqualTo(String value) {
            addCriterion("sysname >=", value, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameLessThan(String value) {
            addCriterion("sysname <", value, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameLessThanOrEqualTo(String value) {
            addCriterion("sysname <=", value, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameLike(String value) {
            addCriterion("sysname like", value, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameNotLike(String value) {
            addCriterion("sysname not like", value, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameIn(List<String> values) {
            addCriterion("sysname in", values, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameNotIn(List<String> values) {
            addCriterion("sysname not in", values, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameBetween(String value1, String value2) {
            addCriterion("sysname between", value1, value2, "sysname");
            return (Criteria) this;
        }

        public Criteria andSysnameNotBetween(String value1, String value2) {
            addCriterion("sysname not between", value1, value2, "sysname");
            return (Criteria) this;
        }

        public Criteria andSyscodeIsNull() {
            addCriterion("syscode is null");
            return (Criteria) this;
        }

        public Criteria andSyscodeIsNotNull() {
            addCriterion("syscode is not null");
            return (Criteria) this;
        }

        public Criteria andSyscodeEqualTo(String value) {
            addCriterion("syscode =", value, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeNotEqualTo(String value) {
            addCriterion("syscode <>", value, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeGreaterThan(String value) {
            addCriterion("syscode >", value, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeGreaterThanOrEqualTo(String value) {
            addCriterion("syscode >=", value, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeLessThan(String value) {
            addCriterion("syscode <", value, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeLessThanOrEqualTo(String value) {
            addCriterion("syscode <=", value, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeLike(String value) {
            addCriterion("syscode like", value, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeNotLike(String value) {
            addCriterion("syscode not like", value, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeIn(List<String> values) {
            addCriterion("syscode in", values, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeNotIn(List<String> values) {
            addCriterion("syscode not in", values, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeBetween(String value1, String value2) {
            addCriterion("syscode between", value1, value2, "syscode");
            return (Criteria) this;
        }

        public Criteria andSyscodeNotBetween(String value1, String value2) {
            addCriterion("syscode not between", value1, value2, "syscode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeIsNull() {
            addCriterion("reperrcode is null");
            return (Criteria) this;
        }

        public Criteria andReperrcodeIsNotNull() {
            addCriterion("reperrcode is not null");
            return (Criteria) this;
        }

        public Criteria andReperrcodeEqualTo(String value) {
            addCriterion("reperrcode =", value, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeNotEqualTo(String value) {
            addCriterion("reperrcode <>", value, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeGreaterThan(String value) {
            addCriterion("reperrcode >", value, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeGreaterThanOrEqualTo(String value) {
            addCriterion("reperrcode >=", value, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeLessThan(String value) {
            addCriterion("reperrcode <", value, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeLessThanOrEqualTo(String value) {
            addCriterion("reperrcode <=", value, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeLike(String value) {
            addCriterion("reperrcode like", value, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeNotLike(String value) {
            addCriterion("reperrcode not like", value, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeIn(List<String> values) {
            addCriterion("reperrcode in", values, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeNotIn(List<String> values) {
            addCriterion("reperrcode not in", values, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeBetween(String value1, String value2) {
            addCriterion("reperrcode between", value1, value2, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrcodeNotBetween(String value1, String value2) {
            addCriterion("reperrcode not between", value1, value2, "reperrcode");
            return (Criteria) this;
        }

        public Criteria andReperrmsgIsNull() {
            addCriterion("reperrmsg is null");
            return (Criteria) this;
        }

        public Criteria andReperrmsgIsNotNull() {
            addCriterion("reperrmsg is not null");
            return (Criteria) this;
        }

        public Criteria andReperrmsgEqualTo(String value) {
            addCriterion("reperrmsg =", value, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgNotEqualTo(String value) {
            addCriterion("reperrmsg <>", value, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgGreaterThan(String value) {
            addCriterion("reperrmsg >", value, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgGreaterThanOrEqualTo(String value) {
            addCriterion("reperrmsg >=", value, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgLessThan(String value) {
            addCriterion("reperrmsg <", value, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgLessThanOrEqualTo(String value) {
            addCriterion("reperrmsg <=", value, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgLike(String value) {
            addCriterion("reperrmsg like", value, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgNotLike(String value) {
            addCriterion("reperrmsg not like", value, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgIn(List<String> values) {
            addCriterion("reperrmsg in", values, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgNotIn(List<String> values) {
            addCriterion("reperrmsg not in", values, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgBetween(String value1, String value2) {
            addCriterion("reperrmsg between", value1, value2, "reperrmsg");
            return (Criteria) this;
        }

        public Criteria andReperrmsgNotBetween(String value1, String value2) {
            addCriterion("reperrmsg not between", value1, value2, "reperrmsg");
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