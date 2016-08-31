package com.jfshare.score.model;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class TbScoreTradeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbScoreTradeExample() {
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
        protected List<Criterion> tradeTimeCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            tradeTimeCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getTradeTimeCriteria() {
            return tradeTimeCriteria;
        }

        protected void addTradeTimeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            tradeTimeCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addTradeTimeCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            tradeTimeCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || tradeTimeCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(tradeTimeCriteria);
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIsNull() {
            addCriterion("trade_time is null");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIsNotNull() {
            addCriterion("trade_time is not null");
            return (Criteria) this;
        }

        public Criteria andTradeTimeEqualTo(DateTime value) {
            addTradeTimeCriterion("trade_time =", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotEqualTo(DateTime value) {
            addTradeTimeCriterion("trade_time <>", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeGreaterThan(DateTime value) {
            addTradeTimeCriterion("trade_time >", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeGreaterThanOrEqualTo(DateTime value) {
            addTradeTimeCriterion("trade_time >=", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeLessThan(DateTime value) {
            addTradeTimeCriterion("trade_time <", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeLessThanOrEqualTo(DateTime value) {
            addTradeTimeCriterion("trade_time <=", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIn(List<DateTime> values) {
            addTradeTimeCriterion("trade_time in", values, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotIn(List<DateTime> values) {
            addTradeTimeCriterion("trade_time not in", values, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeBetween(DateTime value1, DateTime value2) {
            addTradeTimeCriterion("trade_time between", value1,  value2, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotBetween(DateTime value1, DateTime value2) {
            addTradeTimeCriterion("trade_time not between", value1, value2, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andInOrOutIsNull() {
            addCriterion("in_or_out is null");
            return (Criteria) this;
        }

        public Criteria andInOrOutIsNotNull() {
            addCriterion("in_or_out is not null");
            return (Criteria) this;
        }

        public Criteria andInOrOutEqualTo(Integer value) {
            addCriterion("in_or_out =", value, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutNotEqualTo(Integer value) {
            addCriterion("in_or_out <>", value, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutGreaterThan(Integer value) {
            addCriterion("in_or_out >", value, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutGreaterThanOrEqualTo(Integer value) {
            addCriterion("in_or_out >=", value, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutLessThan(Integer value) {
            addCriterion("in_or_out <", value, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutLessThanOrEqualTo(Integer value) {
            addCriterion("in_or_out <=", value, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutIn(List<Integer> values) {
            addCriterion("in_or_out in", values, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutNotIn(List<Integer> values) {
            addCriterion("in_or_out not in", values, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutBetween(Integer value1, Integer value2) {
            addCriterion("in_or_out between", value1, value2, "inOrOut");
            return (Criteria) this;
        }

        public Criteria andInOrOutNotBetween(Integer value1, Integer value2) {
            addCriterion("in_or_out not between", value1, value2, "inOrOut");
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

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Integer value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Integer value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Integer value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Integer value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Integer value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Integer> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Integer> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Integer value1, Integer value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }
        //增加交易编号
        public Criteria andTraderIdEqualTo(String value) {
            addCriterion("tradeid =", value, "tradeid");
            return (Criteria) this;
        }

        public Criteria andTraderIsNull() {
            addCriterion("trader is null");
            return (Criteria) this;
        }

        public Criteria andTraderIsNotNull() {
            addCriterion("trader is not null");
            return (Criteria) this;
        }

        public Criteria andTraderEqualTo(Integer value) {
            addCriterion("trader =", value, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderNotEqualTo(Integer value) {
            addCriterion("trader <>", value, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderGreaterThan(Integer value) {
            addCriterion("trader >", value, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderGreaterThanOrEqualTo(Integer value) {
            addCriterion("trader >=", value, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderLessThan(Integer value) {
            addCriterion("trader <", value, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderLessThanOrEqualTo(Integer value) {
            addCriterion("trader <=", value, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderIn(List<Integer> values) {
            addCriterion("trader in", values, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderNotIn(List<Integer> values) {
            addCriterion("trader not in", values, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderBetween(Integer value1, Integer value2) {
            addCriterion("trader between", value1, value2, "trader");
            return (Criteria) this;
        }

        public Criteria andTraderNotBetween(Integer value1, Integer value2) {
            addCriterion("trader not between", value1, value2, "trader");
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