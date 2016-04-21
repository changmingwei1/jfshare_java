package com.jfshare.common.model;

import java.util.ArrayList;
import java.util.List;

public class TbIp2addressDicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbIp2addressDicExample() {
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

        public Criteria andIpStartIntIsNull() {
            addCriterion("ip_start_int is null");
            return (Criteria) this;
        }

        public Criteria andIpStartIntIsNotNull() {
            addCriterion("ip_start_int is not null");
            return (Criteria) this;
        }

        public Criteria andIpStartIntEqualTo(Integer value) {
            addCriterion("ip_start_int =", value, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntNotEqualTo(Integer value) {
            addCriterion("ip_start_int <>", value, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntGreaterThan(Integer value) {
            addCriterion("ip_start_int >", value, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntGreaterThanOrEqualTo(Integer value) {
            addCriterion("ip_start_int >=", value, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntLessThan(Integer value) {
            addCriterion("ip_start_int <", value, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntLessThanOrEqualTo(Integer value) {
            addCriterion("ip_start_int <=", value, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntIn(List<Integer> values) {
            addCriterion("ip_start_int in", values, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntNotIn(List<Integer> values) {
            addCriterion("ip_start_int not in", values, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntBetween(Integer value1, Integer value2) {
            addCriterion("ip_start_int between", value1, value2, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartIntNotBetween(Integer value1, Integer value2) {
            addCriterion("ip_start_int not between", value1, value2, "ipStartInt");
            return (Criteria) this;
        }

        public Criteria andIpStartStrIsNull() {
            addCriterion("ip_start_str is null");
            return (Criteria) this;
        }

        public Criteria andIpStartStrIsNotNull() {
            addCriterion("ip_start_str is not null");
            return (Criteria) this;
        }

        public Criteria andIpStartStrEqualTo(String value) {
            addCriterion("ip_start_str =", value, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrNotEqualTo(String value) {
            addCriterion("ip_start_str <>", value, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrGreaterThan(String value) {
            addCriterion("ip_start_str >", value, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrGreaterThanOrEqualTo(String value) {
            addCriterion("ip_start_str >=", value, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrLessThan(String value) {
            addCriterion("ip_start_str <", value, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrLessThanOrEqualTo(String value) {
            addCriterion("ip_start_str <=", value, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrLike(String value) {
            addCriterion("ip_start_str like", value, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrNotLike(String value) {
            addCriterion("ip_start_str not like", value, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrIn(List<String> values) {
            addCriterion("ip_start_str in", values, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrNotIn(List<String> values) {
            addCriterion("ip_start_str not in", values, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrBetween(String value1, String value2) {
            addCriterion("ip_start_str between", value1, value2, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpStartStrNotBetween(String value1, String value2) {
            addCriterion("ip_start_str not between", value1, value2, "ipStartStr");
            return (Criteria) this;
        }

        public Criteria andIpEndIntIsNull() {
            addCriterion("ip_end_int is null");
            return (Criteria) this;
        }

        public Criteria andIpEndIntIsNotNull() {
            addCriterion("ip_end_int is not null");
            return (Criteria) this;
        }

        public Criteria andIpEndIntEqualTo(Integer value) {
            addCriterion("ip_end_int =", value, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntNotEqualTo(Integer value) {
            addCriterion("ip_end_int <>", value, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntGreaterThan(Integer value) {
            addCriterion("ip_end_int >", value, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntGreaterThanOrEqualTo(Integer value) {
            addCriterion("ip_end_int >=", value, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntLessThan(Integer value) {
            addCriterion("ip_end_int <", value, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntLessThanOrEqualTo(Integer value) {
            addCriterion("ip_end_int <=", value, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntIn(List<Integer> values) {
            addCriterion("ip_end_int in", values, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntNotIn(List<Integer> values) {
            addCriterion("ip_end_int not in", values, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntBetween(Integer value1, Integer value2) {
            addCriterion("ip_end_int between", value1, value2, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndIntNotBetween(Integer value1, Integer value2) {
            addCriterion("ip_end_int not between", value1, value2, "ipEndInt");
            return (Criteria) this;
        }

        public Criteria andIpEndStrIsNull() {
            addCriterion("ip_end_str is null");
            return (Criteria) this;
        }

        public Criteria andIpEndStrIsNotNull() {
            addCriterion("ip_end_str is not null");
            return (Criteria) this;
        }

        public Criteria andIpEndStrEqualTo(String value) {
            addCriterion("ip_end_str =", value, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrNotEqualTo(String value) {
            addCriterion("ip_end_str <>", value, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrGreaterThan(String value) {
            addCriterion("ip_end_str >", value, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrGreaterThanOrEqualTo(String value) {
            addCriterion("ip_end_str >=", value, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrLessThan(String value) {
            addCriterion("ip_end_str <", value, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrLessThanOrEqualTo(String value) {
            addCriterion("ip_end_str <=", value, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrLike(String value) {
            addCriterion("ip_end_str like", value, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrNotLike(String value) {
            addCriterion("ip_end_str not like", value, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrIn(List<String> values) {
            addCriterion("ip_end_str in", values, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrNotIn(List<String> values) {
            addCriterion("ip_end_str not in", values, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrBetween(String value1, String value2) {
            addCriterion("ip_end_str between", value1, value2, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andIpEndStrNotBetween(String value1, String value2) {
            addCriterion("ip_end_str not between", value1, value2, "ipEndStr");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNull() {
            addCriterion("city_id is null");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNotNull() {
            addCriterion("city_id is not null");
            return (Criteria) this;
        }

        public Criteria andCityIdEqualTo(Integer value) {
            addCriterion("city_id =", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotEqualTo(Integer value) {
            addCriterion("city_id <>", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThan(Integer value) {
            addCriterion("city_id >", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("city_id >=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThan(Integer value) {
            addCriterion("city_id <", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThanOrEqualTo(Integer value) {
            addCriterion("city_id <=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdIn(List<Integer> values) {
            addCriterion("city_id in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotIn(List<Integer> values) {
            addCriterion("city_id not in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdBetween(Integer value1, Integer value2) {
            addCriterion("city_id between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("city_id not between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNull() {
            addCriterion("city_name is null");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNotNull() {
            addCriterion("city_name is not null");
            return (Criteria) this;
        }

        public Criteria andCityNameEqualTo(String value) {
            addCriterion("city_name =", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotEqualTo(String value) {
            addCriterion("city_name <>", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThan(String value) {
            addCriterion("city_name >", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("city_name >=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThan(String value) {
            addCriterion("city_name <", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThanOrEqualTo(String value) {
            addCriterion("city_name <=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLike(String value) {
            addCriterion("city_name like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotLike(String value) {
            addCriterion("city_name not like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameIn(List<String> values) {
            addCriterion("city_name in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotIn(List<String> values) {
            addCriterion("city_name not in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameBetween(String value1, String value2) {
            addCriterion("city_name between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotBetween(String value1, String value2) {
            addCriterion("city_name not between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNull() {
            addCriterion("province_id is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNotNull() {
            addCriterion("province_id is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdEqualTo(Integer value) {
            addCriterion("province_id =", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotEqualTo(Integer value) {
            addCriterion("province_id <>", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThan(Integer value) {
            addCriterion("province_id >", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("province_id >=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThan(Integer value) {
            addCriterion("province_id <", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThanOrEqualTo(Integer value) {
            addCriterion("province_id <=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIn(List<Integer> values) {
            addCriterion("province_id in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotIn(List<Integer> values) {
            addCriterion("province_id not in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdBetween(Integer value1, Integer value2) {
            addCriterion("province_id between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("province_id not between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIsNull() {
            addCriterion("province_name is null");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIsNotNull() {
            addCriterion("province_name is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceNameEqualTo(String value) {
            addCriterion("province_name =", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotEqualTo(String value) {
            addCriterion("province_name <>", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameGreaterThan(String value) {
            addCriterion("province_name >", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameGreaterThanOrEqualTo(String value) {
            addCriterion("province_name >=", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLessThan(String value) {
            addCriterion("province_name <", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLessThanOrEqualTo(String value) {
            addCriterion("province_name <=", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLike(String value) {
            addCriterion("province_name like", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotLike(String value) {
            addCriterion("province_name not like", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIn(List<String> values) {
            addCriterion("province_name in", values, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotIn(List<String> values) {
            addCriterion("province_name not in", values, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameBetween(String value1, String value2) {
            addCriterion("province_name between", value1, value2, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotBetween(String value1, String value2) {
            addCriterion("province_name not between", value1, value2, "provinceName");
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