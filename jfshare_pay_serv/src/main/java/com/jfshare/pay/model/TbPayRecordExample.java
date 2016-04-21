package com.jfshare.pay.model;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class TbPayRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbPayRecordExample() {
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
        protected List<Criterion> thirdTimeCriteria;

        protected List<Criterion> processTimeCriteria;

        protected List<Criterion> createTimeCriteria;

        protected List<Criterion> lastUpdateTimeCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            thirdTimeCriteria = new ArrayList<Criterion>();
            processTimeCriteria = new ArrayList<Criterion>();
            createTimeCriteria = new ArrayList<Criterion>();
            lastUpdateTimeCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getThirdTimeCriteria() {
            return thirdTimeCriteria;
        }

        protected void addThirdTimeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            thirdTimeCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addThirdTimeCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            thirdTimeCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getProcessTimeCriteria() {
            return processTimeCriteria;
        }

        protected void addProcessTimeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            processTimeCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addProcessTimeCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            processTimeCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getCreateTimeCriteria() {
            return createTimeCriteria;
        }

        protected void addCreateTimeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            createTimeCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addCreateTimeCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            createTimeCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getLastUpdateTimeCriteria() {
            return lastUpdateTimeCriteria;
        }

        protected void addLastUpdateTimeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            lastUpdateTimeCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addLastUpdateTimeCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            lastUpdateTimeCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || thirdTimeCriteria.size() > 0
                || processTimeCriteria.size() > 0
                || createTimeCriteria.size() > 0
                || lastUpdateTimeCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(thirdTimeCriteria);
                allCriteria.addAll(processTimeCriteria);
                allCriteria.addAll(createTimeCriteria);
                allCriteria.addAll(lastUpdateTimeCriteria);
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

        public Criteria andPayIdIsNull() {
            addCriterion("pay_id is null");
            return (Criteria) this;
        }

        public Criteria andPayIdIsNotNull() {
            addCriterion("pay_id is not null");
            return (Criteria) this;
        }

        public Criteria andPayIdEqualTo(String value) {
            addCriterion("pay_id =", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdNotEqualTo(String value) {
            addCriterion("pay_id <>", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdGreaterThan(String value) {
            addCriterion("pay_id >", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdGreaterThanOrEqualTo(String value) {
            addCriterion("pay_id >=", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdLessThan(String value) {
            addCriterion("pay_id <", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdLessThanOrEqualTo(String value) {
            addCriterion("pay_id <=", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdLike(String value) {
            addCriterion("pay_id like", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdNotLike(String value) {
            addCriterion("pay_id not like", value, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdIn(List<String> values) {
            addCriterion("pay_id in", values, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdNotIn(List<String> values) {
            addCriterion("pay_id not in", values, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdBetween(String value1, String value2) {
            addCriterion("pay_id between", value1, value2, "payId");
            return (Criteria) this;
        }

        public Criteria andPayIdNotBetween(String value1, String value2) {
            addCriterion("pay_id not between", value1, value2, "payId");
            return (Criteria) this;
        }

        public Criteria andTokenIdIsNull() {
            addCriterion("token_id is null");
            return (Criteria) this;
        }

        public Criteria andTokenIdIsNotNull() {
            addCriterion("token_id is not null");
            return (Criteria) this;
        }

        public Criteria andTokenIdEqualTo(String value) {
            addCriterion("token_id =", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdNotEqualTo(String value) {
            addCriterion("token_id <>", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdGreaterThan(String value) {
            addCriterion("token_id >", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdGreaterThanOrEqualTo(String value) {
            addCriterion("token_id >=", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdLessThan(String value) {
            addCriterion("token_id <", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdLessThanOrEqualTo(String value) {
            addCriterion("token_id <=", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdLike(String value) {
            addCriterion("token_id like", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdNotLike(String value) {
            addCriterion("token_id not like", value, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdIn(List<String> values) {
            addCriterion("token_id in", values, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdNotIn(List<String> values) {
            addCriterion("token_id not in", values, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdBetween(String value1, String value2) {
            addCriterion("token_id between", value1, value2, "tokenId");
            return (Criteria) this;
        }

        public Criteria andTokenIdNotBetween(String value1, String value2) {
            addCriterion("token_id not between", value1, value2, "tokenId");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andExtraParamIsNull() {
            addCriterion("extra_param is null");
            return (Criteria) this;
        }

        public Criteria andExtraParamIsNotNull() {
            addCriterion("extra_param is not null");
            return (Criteria) this;
        }

        public Criteria andExtraParamEqualTo(String value) {
            addCriterion("extra_param =", value, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamNotEqualTo(String value) {
            addCriterion("extra_param <>", value, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamGreaterThan(String value) {
            addCriterion("extra_param >", value, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamGreaterThanOrEqualTo(String value) {
            addCriterion("extra_param >=", value, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamLessThan(String value) {
            addCriterion("extra_param <", value, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamLessThanOrEqualTo(String value) {
            addCriterion("extra_param <=", value, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamLike(String value) {
            addCriterion("extra_param like", value, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamNotLike(String value) {
            addCriterion("extra_param not like", value, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamIn(List<String> values) {
            addCriterion("extra_param in", values, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamNotIn(List<String> values) {
            addCriterion("extra_param not in", values, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamBetween(String value1, String value2) {
            addCriterion("extra_param between", value1, value2, "extraParam");
            return (Criteria) this;
        }

        public Criteria andExtraParamNotBetween(String value1, String value2) {
            addCriterion("extra_param not between", value1, value2, "extraParam");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Integer value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Integer value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Integer value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Integer value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Integer value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Integer> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Integer> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Integer value1, Integer value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andPayChannelIsNull() {
            addCriterion("pay_channel is null");
            return (Criteria) this;
        }

        public Criteria andPayChannelIsNotNull() {
            addCriterion("pay_channel is not null");
            return (Criteria) this;
        }

        public Criteria andPayChannelEqualTo(Integer value) {
            addCriterion("pay_channel =", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotEqualTo(Integer value) {
            addCriterion("pay_channel <>", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelGreaterThan(Integer value) {
            addCriterion("pay_channel >", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_channel >=", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelLessThan(Integer value) {
            addCriterion("pay_channel <", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelLessThanOrEqualTo(Integer value) {
            addCriterion("pay_channel <=", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelIn(List<Integer> values) {
            addCriterion("pay_channel in", values, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotIn(List<Integer> values) {
            addCriterion("pay_channel not in", values, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelBetween(Integer value1, Integer value2) {
            addCriterion("pay_channel between", value1, value2, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_channel not between", value1, value2, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayIpIsNull() {
            addCriterion("pay_ip is null");
            return (Criteria) this;
        }

        public Criteria andPayIpIsNotNull() {
            addCriterion("pay_ip is not null");
            return (Criteria) this;
        }

        public Criteria andPayIpEqualTo(String value) {
            addCriterion("pay_ip =", value, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpNotEqualTo(String value) {
            addCriterion("pay_ip <>", value, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpGreaterThan(String value) {
            addCriterion("pay_ip >", value, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpGreaterThanOrEqualTo(String value) {
            addCriterion("pay_ip >=", value, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpLessThan(String value) {
            addCriterion("pay_ip <", value, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpLessThanOrEqualTo(String value) {
            addCriterion("pay_ip <=", value, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpLike(String value) {
            addCriterion("pay_ip like", value, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpNotLike(String value) {
            addCriterion("pay_ip not like", value, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpIn(List<String> values) {
            addCriterion("pay_ip in", values, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpNotIn(List<String> values) {
            addCriterion("pay_ip not in", values, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpBetween(String value1, String value2) {
            addCriterion("pay_ip between", value1, value2, "payIp");
            return (Criteria) this;
        }

        public Criteria andPayIpNotBetween(String value1, String value2) {
            addCriterion("pay_ip not between", value1, value2, "payIp");
            return (Criteria) this;
        }

        public Criteria andReturnUrlIsNull() {
            addCriterion("return_url is null");
            return (Criteria) this;
        }

        public Criteria andReturnUrlIsNotNull() {
            addCriterion("return_url is not null");
            return (Criteria) this;
        }

        public Criteria andReturnUrlEqualTo(String value) {
            addCriterion("return_url =", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlNotEqualTo(String value) {
            addCriterion("return_url <>", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlGreaterThan(String value) {
            addCriterion("return_url >", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlGreaterThanOrEqualTo(String value) {
            addCriterion("return_url >=", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlLessThan(String value) {
            addCriterion("return_url <", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlLessThanOrEqualTo(String value) {
            addCriterion("return_url <=", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlLike(String value) {
            addCriterion("return_url like", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlNotLike(String value) {
            addCriterion("return_url not like", value, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlIn(List<String> values) {
            addCriterion("return_url in", values, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlNotIn(List<String> values) {
            addCriterion("return_url not in", values, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlBetween(String value1, String value2) {
            addCriterion("return_url between", value1, value2, "returnUrl");
            return (Criteria) this;
        }

        public Criteria andReturnUrlNotBetween(String value1, String value2) {
            addCriterion("return_url not between", value1, value2, "returnUrl");
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

        public Criteria andPayStateIsNull() {
            addCriterion("pay_state is null");
            return (Criteria) this;
        }

        public Criteria andPayStateIsNotNull() {
            addCriterion("pay_state is not null");
            return (Criteria) this;
        }

        public Criteria andPayStateEqualTo(Integer value) {
            addCriterion("pay_state =", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotEqualTo(Integer value) {
            addCriterion("pay_state <>", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateGreaterThan(Integer value) {
            addCriterion("pay_state >", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_state >=", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLessThan(Integer value) {
            addCriterion("pay_state <", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateLessThanOrEqualTo(Integer value) {
            addCriterion("pay_state <=", value, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateIn(List<Integer> values) {
            addCriterion("pay_state in", values, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotIn(List<Integer> values) {
            addCriterion("pay_state not in", values, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateBetween(Integer value1, Integer value2) {
            addCriterion("pay_state between", value1, value2, "payState");
            return (Criteria) this;
        }

        public Criteria andPayStateNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_state not between", value1, value2, "payState");
            return (Criteria) this;
        }

        public Criteria andBizCodeIsNull() {
            addCriterion("biz_code is null");
            return (Criteria) this;
        }

        public Criteria andBizCodeIsNotNull() {
            addCriterion("biz_code is not null");
            return (Criteria) this;
        }

        public Criteria andBizCodeEqualTo(Integer value) {
            addCriterion("biz_code =", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotEqualTo(Integer value) {
            addCriterion("biz_code <>", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeGreaterThan(Integer value) {
            addCriterion("biz_code >", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("biz_code >=", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeLessThan(Integer value) {
            addCriterion("biz_code <", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeLessThanOrEqualTo(Integer value) {
            addCriterion("biz_code <=", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeIn(List<Integer> values) {
            addCriterion("biz_code in", values, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotIn(List<Integer> values) {
            addCriterion("biz_code not in", values, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeBetween(Integer value1, Integer value2) {
            addCriterion("biz_code between", value1, value2, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("biz_code not between", value1, value2, "bizCode");
            return (Criteria) this;
        }

        public Criteria andThirdIdIsNull() {
            addCriterion("third_id is null");
            return (Criteria) this;
        }

        public Criteria andThirdIdIsNotNull() {
            addCriterion("third_id is not null");
            return (Criteria) this;
        }

        public Criteria andThirdIdEqualTo(String value) {
            addCriterion("third_id =", value, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdNotEqualTo(String value) {
            addCriterion("third_id <>", value, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdGreaterThan(String value) {
            addCriterion("third_id >", value, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdGreaterThanOrEqualTo(String value) {
            addCriterion("third_id >=", value, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdLessThan(String value) {
            addCriterion("third_id <", value, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdLessThanOrEqualTo(String value) {
            addCriterion("third_id <=", value, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdLike(String value) {
            addCriterion("third_id like", value, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdNotLike(String value) {
            addCriterion("third_id not like", value, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdIn(List<String> values) {
            addCriterion("third_id in", values, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdNotIn(List<String> values) {
            addCriterion("third_id not in", values, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdBetween(String value1, String value2) {
            addCriterion("third_id between", value1, value2, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdIdNotBetween(String value1, String value2) {
            addCriterion("third_id not between", value1, value2, "thirdId");
            return (Criteria) this;
        }

        public Criteria andThirdPriceIsNull() {
            addCriterion("third_price is null");
            return (Criteria) this;
        }

        public Criteria andThirdPriceIsNotNull() {
            addCriterion("third_price is not null");
            return (Criteria) this;
        }

        public Criteria andThirdPriceEqualTo(Integer value) {
            addCriterion("third_price =", value, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceNotEqualTo(Integer value) {
            addCriterion("third_price <>", value, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceGreaterThan(Integer value) {
            addCriterion("third_price >", value, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("third_price >=", value, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceLessThan(Integer value) {
            addCriterion("third_price <", value, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceLessThanOrEqualTo(Integer value) {
            addCriterion("third_price <=", value, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceIn(List<Integer> values) {
            addCriterion("third_price in", values, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceNotIn(List<Integer> values) {
            addCriterion("third_price not in", values, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceBetween(Integer value1, Integer value2) {
            addCriterion("third_price between", value1, value2, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("third_price not between", value1, value2, "thirdPrice");
            return (Criteria) this;
        }

        public Criteria andThirdScoreIsNull() {
            addCriterion("third_score is null");
            return (Criteria) this;
        }

        public Criteria andThirdScoreIsNotNull() {
            addCriterion("third_score is not null");
            return (Criteria) this;
        }

        public Criteria andThirdScoreEqualTo(Integer value) {
            addCriterion("third_score =", value, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreNotEqualTo(Integer value) {
            addCriterion("third_score <>", value, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreGreaterThan(Integer value) {
            addCriterion("third_score >", value, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("third_score >=", value, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreLessThan(Integer value) {
            addCriterion("third_score <", value, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreLessThanOrEqualTo(Integer value) {
            addCriterion("third_score <=", value, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreIn(List<Integer> values) {
            addCriterion("third_score in", values, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreNotIn(List<Integer> values) {
            addCriterion("third_score not in", values, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreBetween(Integer value1, Integer value2) {
            addCriterion("third_score between", value1, value2, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("third_score not between", value1, value2, "thirdScore");
            return (Criteria) this;
        }

        public Criteria andThirdAccountIsNull() {
            addCriterion("third_account is null");
            return (Criteria) this;
        }

        public Criteria andThirdAccountIsNotNull() {
            addCriterion("third_account is not null");
            return (Criteria) this;
        }

        public Criteria andThirdAccountEqualTo(String value) {
            addCriterion("third_account =", value, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountNotEqualTo(String value) {
            addCriterion("third_account <>", value, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountGreaterThan(String value) {
            addCriterion("third_account >", value, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountGreaterThanOrEqualTo(String value) {
            addCriterion("third_account >=", value, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountLessThan(String value) {
            addCriterion("third_account <", value, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountLessThanOrEqualTo(String value) {
            addCriterion("third_account <=", value, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountLike(String value) {
            addCriterion("third_account like", value, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountNotLike(String value) {
            addCriterion("third_account not like", value, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountIn(List<String> values) {
            addCriterion("third_account in", values, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountNotIn(List<String> values) {
            addCriterion("third_account not in", values, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountBetween(String value1, String value2) {
            addCriterion("third_account between", value1, value2, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdAccountNotBetween(String value1, String value2) {
            addCriterion("third_account not between", value1, value2, "thirdAccount");
            return (Criteria) this;
        }

        public Criteria andThirdTimeIsNull() {
            addCriterion("third_time is null");
            return (Criteria) this;
        }

        public Criteria andThirdTimeIsNotNull() {
            addCriterion("third_time is not null");
            return (Criteria) this;
        }

        public Criteria andThirdTimeEqualTo(DateTime value) {
            addThirdTimeCriterion("third_time =", value, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeNotEqualTo(DateTime value) {
            addThirdTimeCriterion("third_time <>", value, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeGreaterThan(DateTime value) {
            addThirdTimeCriterion("third_time >", value, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeGreaterThanOrEqualTo(DateTime value) {
            addThirdTimeCriterion("third_time >=", value, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeLessThan(DateTime value) {
            addThirdTimeCriterion("third_time <", value, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeLessThanOrEqualTo(DateTime value) {
            addThirdTimeCriterion("third_time <=", value, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeIn(List<DateTime> values) {
            addThirdTimeCriterion("third_time in", values, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeNotIn(List<DateTime> values) {
            addThirdTimeCriterion("third_time not in", values, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeBetween(DateTime value1, DateTime value2) {
            addThirdTimeCriterion("third_time between", value1, value2, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andThirdTimeNotBetween(DateTime value1, DateTime value2) {
            addThirdTimeCriterion("third_time not between", value1, value2, "thirdTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIsNull() {
            addCriterion("process_time is null");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIsNotNull() {
            addCriterion("process_time is not null");
            return (Criteria) this;
        }

        public Criteria andProcessTimeEqualTo(DateTime value) {
            addProcessTimeCriterion("process_time =", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotEqualTo(DateTime value) {
            addProcessTimeCriterion("process_time <>", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeGreaterThan(DateTime value) {
            addProcessTimeCriterion("process_time >", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeGreaterThanOrEqualTo(DateTime value) {
            addProcessTimeCriterion("process_time >=", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeLessThan(DateTime value) {
            addProcessTimeCriterion("process_time <", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeLessThanOrEqualTo(DateTime value) {
            addProcessTimeCriterion("process_time <=", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIn(List<DateTime> values) {
            addProcessTimeCriterion("process_time in", values, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotIn(List<DateTime> values) {
            addProcessTimeCriterion("process_time not in", values, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeBetween(DateTime value1, DateTime value2) {
            addProcessTimeCriterion("process_time between", value1, value2, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotBetween(DateTime value1, DateTime value2) {
            addProcessTimeCriterion("process_time not between", value1, value2, "processTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(DateTime value) {
            addCreateTimeCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(DateTime value) {
            addCreateTimeCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(DateTime value) {
            addCreateTimeCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(DateTime value) {
            addCreateTimeCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(DateTime value) {
            addCreateTimeCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(DateTime value) {
            addCreateTimeCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<DateTime> values) {
            addCreateTimeCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<DateTime> values) {
            addCreateTimeCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(DateTime value1, DateTime value2) {
            addCreateTimeCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(DateTime value1, DateTime value2) {
            addCreateTimeCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("last_update_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("last_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(DateTime value) {
            addLastUpdateTimeCriterion("last_update_time =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(DateTime value) {
            addLastUpdateTimeCriterion("last_update_time <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(DateTime value) {
            addLastUpdateTimeCriterion("last_update_time >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(DateTime value) {
            addLastUpdateTimeCriterion("last_update_time >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(DateTime value) {
            addLastUpdateTimeCriterion("last_update_time <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(DateTime value) {
            addLastUpdateTimeCriterion("last_update_time <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<DateTime> values) {
            addLastUpdateTimeCriterion("last_update_time in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<DateTime> values) {
            addLastUpdateTimeCriterion("last_update_time not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(DateTime value1, DateTime value2) {
            addLastUpdateTimeCriterion("last_update_time between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(DateTime value1, DateTime value2) {
            addLastUpdateTimeCriterion("last_update_time not between", value1, value2, "lastUpdateTime");
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