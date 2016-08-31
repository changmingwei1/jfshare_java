package com.jfshare.message.model;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class TbAppVerinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbAppVerinfoExample() {
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

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            createTimeCriteria = new ArrayList<Criterion>();
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

        public boolean isValid() {
            return criteria.size() > 0
                || createTimeCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(createTimeCriteria);
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

        public Criteria andAppTypeIsNull() {
            addCriterion("app_type is null");
            return (Criteria) this;
        }

        public Criteria andAppTypeIsNotNull() {
            addCriterion("app_type is not null");
            return (Criteria) this;
        }

        public Criteria andAppTypeEqualTo(Integer value) {
            addCriterion("app_type =", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeNotEqualTo(Integer value) {
            addCriterion("app_type <>", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeGreaterThan(Integer value) {
            addCriterion("app_type >", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_type >=", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeLessThan(Integer value) {
            addCriterion("app_type <", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeLessThanOrEqualTo(Integer value) {
            addCriterion("app_type <=", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeIn(List<Integer> values) {
            addCriterion("app_type in", values, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeNotIn(List<Integer> values) {
            addCriterion("app_type not in", values, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeBetween(Integer value1, Integer value2) {
            addCriterion("app_type between", value1, value2, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("app_type not between", value1, value2, "appType");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andMinVersionIsNull() {
            addCriterion("min_version is null");
            return (Criteria) this;
        }

        public Criteria andMinVersionIsNotNull() {
            addCriterion("min_version is not null");
            return (Criteria) this;
        }

        public Criteria andMinVersionEqualTo(String value) {
            addCriterion("min_version =", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionNotEqualTo(String value) {
            addCriterion("min_version <>", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionGreaterThan(String value) {
            addCriterion("min_version >", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionGreaterThanOrEqualTo(String value) {
            addCriterion("min_version >=", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionLessThan(String value) {
            addCriterion("min_version <", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionLessThanOrEqualTo(String value) {
            addCriterion("min_version <=", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionLike(String value) {
            addCriterion("min_version like", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionNotLike(String value) {
            addCriterion("min_version not like", value, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionIn(List<String> values) {
            addCriterion("min_version in", values, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionNotIn(List<String> values) {
            addCriterion("min_version not in", values, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionBetween(String value1, String value2) {
            addCriterion("min_version between", value1, value2, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMinVersionNotBetween(String value1, String value2) {
            addCriterion("min_version not between", value1, value2, "minVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionIsNull() {
            addCriterion("max_version is null");
            return (Criteria) this;
        }

        public Criteria andMaxVersionIsNotNull() {
            addCriterion("max_version is not null");
            return (Criteria) this;
        }

        public Criteria andMaxVersionEqualTo(String value) {
            addCriterion("max_version =", value, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionNotEqualTo(String value) {
            addCriterion("max_version <>", value, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionGreaterThan(String value) {
            addCriterion("max_version >", value, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionGreaterThanOrEqualTo(String value) {
            addCriterion("max_version >=", value, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionLessThan(String value) {
            addCriterion("max_version <", value, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionLessThanOrEqualTo(String value) {
            addCriterion("max_version <=", value, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionLike(String value) {
            addCriterion("max_version like", value, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionNotLike(String value) {
            addCriterion("max_version not like", value, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionIn(List<String> values) {
            addCriterion("max_version in", values, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionNotIn(List<String> values) {
            addCriterion("max_version not in", values, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionBetween(String value1, String value2) {
            addCriterion("max_version between", value1, value2, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andMaxVersionNotBetween(String value1, String value2) {
            addCriterion("max_version not between", value1, value2, "maxVersion");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeIsNull() {
            addCriterion("upgrade_type is null");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeIsNotNull() {
            addCriterion("upgrade_type is not null");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeEqualTo(Integer value) {
            addCriterion("upgrade_type =", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeNotEqualTo(Integer value) {
            addCriterion("upgrade_type <>", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeGreaterThan(Integer value) {
            addCriterion("upgrade_type >", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("upgrade_type >=", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeLessThan(Integer value) {
            addCriterion("upgrade_type <", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("upgrade_type <=", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeIn(List<Integer> values) {
            addCriterion("upgrade_type in", values, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeNotIn(List<Integer> values) {
            addCriterion("upgrade_type not in", values, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_type between", value1, value2, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_type not between", value1, value2, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescIsNull() {
            addCriterion("upgrade_desc is null");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescIsNotNull() {
            addCriterion("upgrade_desc is not null");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescEqualTo(String value) {
            addCriterion("upgrade_desc =", value, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescNotEqualTo(String value) {
            addCriterion("upgrade_desc <>", value, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescGreaterThan(String value) {
            addCriterion("upgrade_desc >", value, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescGreaterThanOrEqualTo(String value) {
            addCriterion("upgrade_desc >=", value, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescLessThan(String value) {
            addCriterion("upgrade_desc <", value, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescLessThanOrEqualTo(String value) {
            addCriterion("upgrade_desc <=", value, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescLike(String value) {
            addCriterion("upgrade_desc like", value, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescNotLike(String value) {
            addCriterion("upgrade_desc not like", value, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescIn(List<String> values) {
            addCriterion("upgrade_desc in", values, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescNotIn(List<String> values) {
            addCriterion("upgrade_desc not in", values, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescBetween(String value1, String value2) {
            addCriterion("upgrade_desc between", value1, value2, "upgradeDesc");
            return (Criteria) this;
        }

        public Criteria andUpgradeDescNotBetween(String value1, String value2) {
            addCriterion("upgrade_desc not between", value1, value2, "upgradeDesc");
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