package com.jfshare.productTask.mybatis.model.automatic;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class TbProductCardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbProductCardExample() {
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
        protected List<Criterion> createTimeCriteria;

        protected List<Criterion> lastUpdateTimeCriteria;

        protected List<Criterion> checkedTimeCriteria;

        protected List<Criterion> soldTimeCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            createTimeCriteria = new ArrayList<Criterion>();
            lastUpdateTimeCriteria = new ArrayList<Criterion>();
            checkedTimeCriteria = new ArrayList<Criterion>();
            soldTimeCriteria = new ArrayList<Criterion>();
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

        public List<Criterion> getCheckedTimeCriteria() {
            return checkedTimeCriteria;
        }

        protected void addCheckedTimeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            checkedTimeCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addCheckedTimeCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            checkedTimeCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getSoldTimeCriteria() {
            return soldTimeCriteria;
        }

        protected void addSoldTimeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            soldTimeCriteria.add(new Criterion(condition, value, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        protected void addSoldTimeCriterion(String condition, DateTime value1, DateTime value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            soldTimeCriteria.add(new Criterion(condition, value1, value2, "com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || createTimeCriteria.size() > 0
                || lastUpdateTimeCriteria.size() > 0
                || checkedTimeCriteria.size() > 0
                || soldTimeCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(createTimeCriteria);
                allCriteria.addAll(lastUpdateTimeCriteria);
                allCriteria.addAll(checkedTimeCriteria);
                allCriteria.addAll(soldTimeCriteria);
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

        public Criteria andCardNumberIsNull() {
            addCriterion("card_number is null");
            return (Criteria) this;
        }

        public Criteria andCardNumberIsNotNull() {
            addCriterion("card_number is not null");
            return (Criteria) this;
        }

        public Criteria andCardNumberEqualTo(String value) {
            addCriterion("card_number =", value, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberNotEqualTo(String value) {
            addCriterion("card_number <>", value, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberGreaterThan(String value) {
            addCriterion("card_number >", value, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberGreaterThanOrEqualTo(String value) {
            addCriterion("card_number >=", value, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberLessThan(String value) {
            addCriterion("card_number <", value, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberLessThanOrEqualTo(String value) {
            addCriterion("card_number <=", value, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberLike(String value) {
            addCriterion("card_number like", value, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberNotLike(String value) {
            addCriterion("card_number not like", value, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberIn(List<String> values) {
            addCriterion("card_number in", values, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberNotIn(List<String> values) {
            addCriterion("card_number not in", values, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberBetween(String value1, String value2) {
            addCriterion("card_number between", value1, value2, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andCardNumberNotBetween(String value1, String value2) {
            addCriterion("card_number not between", value1, value2, "cardNumber");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIsNull() {
            addCriterion("transaction_id is null");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIsNotNull() {
            addCriterion("transaction_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionIdEqualTo(String value) {
            addCriterion("transaction_id =", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotEqualTo(String value) {
            addCriterion("transaction_id <>", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdGreaterThan(String value) {
            addCriterion("transaction_id >", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdGreaterThanOrEqualTo(String value) {
            addCriterion("transaction_id >=", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLessThan(String value) {
            addCriterion("transaction_id <", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLessThanOrEqualTo(String value) {
            addCriterion("transaction_id <=", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLike(String value) {
            addCriterion("transaction_id like", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotLike(String value) {
            addCriterion("transaction_id not like", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIn(List<String> values) {
            addCriterion("transaction_id in", values, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotIn(List<String> values) {
            addCriterion("transaction_id not in", values, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdBetween(String value1, String value2) {
            addCriterion("transaction_id between", value1, value2, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotBetween(String value1, String value2) {
            addCriterion("transaction_id not between", value1, value2, "transactionId");
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

        public Criteria andCreateIdIsNull() {
            addCriterion("create_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNotNull() {
            addCriterion("create_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIdEqualTo(Integer value) {
            addCriterion("create_id =", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotEqualTo(Integer value) {
            addCriterion("create_id <>", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThan(Integer value) {
            addCriterion("create_id >", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_id >=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThan(Integer value) {
            addCriterion("create_id <", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_id <=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdIn(List<Integer> values) {
            addCriterion("create_id in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotIn(List<Integer> values) {
            addCriterion("create_id not in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdBetween(Integer value1, Integer value2) {
            addCriterion("create_id between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_id not between", value1, value2, "createId");
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

        public Criteria andLastUpdateIdIsNull() {
            addCriterion("last_update_id is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdIsNotNull() {
            addCriterion("last_update_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdEqualTo(Integer value) {
            addCriterion("last_update_id =", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdNotEqualTo(Integer value) {
            addCriterion("last_update_id <>", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdGreaterThan(Integer value) {
            addCriterion("last_update_id >", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_update_id >=", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdLessThan(Integer value) {
            addCriterion("last_update_id <", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdLessThanOrEqualTo(Integer value) {
            addCriterion("last_update_id <=", value, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdIn(List<Integer> values) {
            addCriterion("last_update_id in", values, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdNotIn(List<Integer> values) {
            addCriterion("last_update_id not in", values, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdBetween(Integer value1, Integer value2) {
            addCriterion("last_update_id between", value1, value2, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("last_update_id not between", value1, value2, "lastUpdateId");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andSkuNumIsNull() {
            addCriterion("sku_num is null");
            return (Criteria) this;
        }

        public Criteria andSkuNumIsNotNull() {
            addCriterion("sku_num is not null");
            return (Criteria) this;
        }

        public Criteria andSkuNumEqualTo(String value) {
            addCriterion("sku_num =", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumNotEqualTo(String value) {
            addCriterion("sku_num <>", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumGreaterThan(String value) {
            addCriterion("sku_num >", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumGreaterThanOrEqualTo(String value) {
            addCriterion("sku_num >=", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumLessThan(String value) {
            addCriterion("sku_num <", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumLessThanOrEqualTo(String value) {
            addCriterion("sku_num <=", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumLike(String value) {
            addCriterion("sku_num like", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumNotLike(String value) {
            addCriterion("sku_num not like", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumIn(List<String> values) {
            addCriterion("sku_num in", values, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumNotIn(List<String> values) {
            addCriterion("sku_num not in", values, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumBetween(String value1, String value2) {
            addCriterion("sku_num between", value1, value2, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumNotBetween(String value1, String value2) {
            addCriterion("sku_num not between", value1, value2, "skuNum");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIsNull() {
            addCriterion("buyer_id is null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIsNotNull() {
            addCriterion("buyer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdEqualTo(Integer value) {
            addCriterion("buyer_id =", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotEqualTo(Integer value) {
            addCriterion("buyer_id <>", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdGreaterThan(Integer value) {
            addCriterion("buyer_id >", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("buyer_id >=", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLessThan(Integer value) {
            addCriterion("buyer_id <", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLessThanOrEqualTo(Integer value) {
            addCriterion("buyer_id <=", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIn(List<Integer> values) {
            addCriterion("buyer_id in", values, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotIn(List<Integer> values) {
            addCriterion("buyer_id not in", values, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdBetween(Integer value1, Integer value2) {
            addCriterion("buyer_id between", value1, value2, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("buyer_id not between", value1, value2, "buyerId");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeIsNull() {
            addCriterion("checked_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeIsNotNull() {
            addCriterion("checked_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeEqualTo(DateTime value) {
            addCheckedTimeCriterion("checked_time =", value, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeNotEqualTo(DateTime value) {
            addCheckedTimeCriterion("checked_time <>", value, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeGreaterThan(DateTime value) {
            addCheckedTimeCriterion("checked_time >", value, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeGreaterThanOrEqualTo(DateTime value) {
            addCheckedTimeCriterion("checked_time >=", value, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeLessThan(DateTime value) {
            addCheckedTimeCriterion("checked_time <", value, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeLessThanOrEqualTo(DateTime value) {
            addCheckedTimeCriterion("checked_time <=", value, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeIn(List<DateTime> values) {
            addCheckedTimeCriterion("checked_time in", values, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeNotIn(List<DateTime> values) {
            addCheckedTimeCriterion("checked_time not in", values, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeBetween(DateTime value1, DateTime value2) {
            addCheckedTimeCriterion("checked_time between", value1, value2, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andCheckedTimeNotBetween(DateTime value1, DateTime value2) {
            addCheckedTimeCriterion("checked_time not between", value1, value2, "checkedTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeIsNull() {
            addCriterion("sold_time is null");
            return (Criteria) this;
        }

        public Criteria andSoldTimeIsNotNull() {
            addCriterion("sold_time is not null");
            return (Criteria) this;
        }

        public Criteria andSoldTimeEqualTo(DateTime value) {
            addSoldTimeCriterion("sold_time =", value, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeNotEqualTo(DateTime value) {
            addSoldTimeCriterion("sold_time <>", value, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeGreaterThan(DateTime value) {
            addSoldTimeCriterion("sold_time >", value, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeGreaterThanOrEqualTo(DateTime value) {
            addSoldTimeCriterion("sold_time >=", value, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeLessThan(DateTime value) {
            addSoldTimeCriterion("sold_time <", value, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeLessThanOrEqualTo(DateTime value) {
            addSoldTimeCriterion("sold_time <=", value, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeIn(List<DateTime> values) {
            addSoldTimeCriterion("sold_time in", values, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeNotIn(List<DateTime> values) {
            addSoldTimeCriterion("sold_time not in", values, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeBetween(DateTime value1, DateTime value2) {
            addSoldTimeCriterion("sold_time between", value1, value2, "soldTime");
            return (Criteria) this;
        }

        public Criteria andSoldTimeNotBetween(DateTime value1, DateTime value2) {
            addSoldTimeCriterion("sold_time not between", value1, value2, "soldTime");
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