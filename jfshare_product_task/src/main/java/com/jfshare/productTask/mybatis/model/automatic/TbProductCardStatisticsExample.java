package com.jfshare.productTask.mybatis.model.automatic;

import java.util.ArrayList;
import java.util.List;

public class TbProductCardStatisticsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbProductCardStatisticsExample() {
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

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
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

        public Criteria andSellerIdIsNull() {
            addCriterion("seller_id is null");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNotNull() {
            addCriterion("seller_id is not null");
            return (Criteria) this;
        }

        public Criteria andSellerIdEqualTo(Integer value) {
            addCriterion("seller_id =", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotEqualTo(Integer value) {
            addCriterion("seller_id <>", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThan(Integer value) {
            addCriterion("seller_id >", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("seller_id >=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThan(Integer value) {
            addCriterion("seller_id <", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThanOrEqualTo(Integer value) {
            addCriterion("seller_id <=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdIn(List<Integer> values) {
            addCriterion("seller_id in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotIn(List<Integer> values) {
            addCriterion("seller_id not in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdBetween(Integer value1, Integer value2) {
            addCriterion("seller_id between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("seller_id not between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(String value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(String value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(String value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(String value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(String value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLike(String value) {
            addCriterion("product_id like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotLike(String value) {
            addCriterion("product_id not like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<String> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<String> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(String value1, String value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(String value1, String value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andSoldNumIsNull() {
            addCriterion("sold_num is null");
            return (Criteria) this;
        }

        public Criteria andSoldNumIsNotNull() {
            addCriterion("sold_num is not null");
            return (Criteria) this;
        }

        public Criteria andSoldNumEqualTo(Integer value) {
            addCriterion("sold_num =", value, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumNotEqualTo(Integer value) {
            addCriterion("sold_num <>", value, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumGreaterThan(Integer value) {
            addCriterion("sold_num >", value, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("sold_num >=", value, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumLessThan(Integer value) {
            addCriterion("sold_num <", value, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumLessThanOrEqualTo(Integer value) {
            addCriterion("sold_num <=", value, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumIn(List<Integer> values) {
            addCriterion("sold_num in", values, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumNotIn(List<Integer> values) {
            addCriterion("sold_num not in", values, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumBetween(Integer value1, Integer value2) {
            addCriterion("sold_num between", value1, value2, "soldNum");
            return (Criteria) this;
        }

        public Criteria andSoldNumNotBetween(Integer value1, Integer value2) {
            addCriterion("sold_num not between", value1, value2, "soldNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumIsNull() {
            addCriterion("checked_num is null");
            return (Criteria) this;
        }

        public Criteria andCheckedNumIsNotNull() {
            addCriterion("checked_num is not null");
            return (Criteria) this;
        }

        public Criteria andCheckedNumEqualTo(Integer value) {
            addCriterion("checked_num =", value, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumNotEqualTo(Integer value) {
            addCriterion("checked_num <>", value, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumGreaterThan(Integer value) {
            addCriterion("checked_num >", value, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("checked_num >=", value, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumLessThan(Integer value) {
            addCriterion("checked_num <", value, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumLessThanOrEqualTo(Integer value) {
            addCriterion("checked_num <=", value, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumIn(List<Integer> values) {
            addCriterion("checked_num in", values, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumNotIn(List<Integer> values) {
            addCriterion("checked_num not in", values, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumBetween(Integer value1, Integer value2) {
            addCriterion("checked_num between", value1, value2, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andCheckedNumNotBetween(Integer value1, Integer value2) {
            addCriterion("checked_num not between", value1, value2, "checkedNum");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateIsNull() {
            addCriterion("statistics_date is null");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateIsNotNull() {
            addCriterion("statistics_date is not null");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateEqualTo(String value) {
            addCriterion("statistics_date =", value, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateNotEqualTo(String value) {
            addCriterion("statistics_date <>", value, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateGreaterThan(String value) {
            addCriterion("statistics_date >", value, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateGreaterThanOrEqualTo(String value) {
            addCriterion("statistics_date >=", value, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateLessThan(String value) {
            addCriterion("statistics_date <", value, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateLessThanOrEqualTo(String value) {
            addCriterion("statistics_date <=", value, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateLike(String value) {
            addCriterion("statistics_date like", value, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateNotLike(String value) {
            addCriterion("statistics_date not like", value, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateIn(List<String> values) {
            addCriterion("statistics_date in", values, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateNotIn(List<String> values) {
            addCriterion("statistics_date not in", values, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateBetween(String value1, String value2) {
            addCriterion("statistics_date between", value1, value2, "statisticsDate");
            return (Criteria) this;
        }

        public Criteria andStatisticsDateNotBetween(String value1, String value2) {
            addCriterion("statistics_date not between", value1, value2, "statisticsDate");
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