package com.jfshare.fileNameMapped.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbFileNameMappedExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbFileNameMappedExample() {
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

        public Criteria andFilenameMd5IsNull() {
            addCriterion("filename_md5 is null");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5IsNotNull() {
            addCriterion("filename_md5 is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5EqualTo(String value) {
            addCriterion("filename_md5 =", value, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5NotEqualTo(String value) {
            addCriterion("filename_md5 <>", value, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5GreaterThan(String value) {
            addCriterion("filename_md5 >", value, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5GreaterThanOrEqualTo(String value) {
            addCriterion("filename_md5 >=", value, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5LessThan(String value) {
            addCriterion("filename_md5 <", value, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5LessThanOrEqualTo(String value) {
            addCriterion("filename_md5 <=", value, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5Like(String value) {
            addCriterion("filename_md5 like", value, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5NotLike(String value) {
            addCriterion("filename_md5 not like", value, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5In(List<String> values) {
            addCriterion("filename_md5 in", values, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5NotIn(List<String> values) {
            addCriterion("filename_md5 not in", values, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5Between(String value1, String value2) {
            addCriterion("filename_md5 between", value1, value2, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameMd5NotBetween(String value1, String value2) {
            addCriterion("filename_md5 not between", value1, value2, "filenameMd5");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNull() {
            addCriterion("filename is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("filename is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("filename =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("filename <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("filename >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("filename >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("filename <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("filename <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("filename like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("filename not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("filename in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("filename not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("filename between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("filename not between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFileidIsNull() {
            addCriterion("fileid is null");
            return (Criteria) this;
        }

        public Criteria andFileidIsNotNull() {
            addCriterion("fileid is not null");
            return (Criteria) this;
        }

        public Criteria andFileidEqualTo(String value) {
            addCriterion("fileid =", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidNotEqualTo(String value) {
            addCriterion("fileid <>", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidGreaterThan(String value) {
            addCriterion("fileid >", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidGreaterThanOrEqualTo(String value) {
            addCriterion("fileid >=", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidLessThan(String value) {
            addCriterion("fileid <", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidLessThanOrEqualTo(String value) {
            addCriterion("fileid <=", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidLike(String value) {
            addCriterion("fileid like", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidNotLike(String value) {
            addCriterion("fileid not like", value, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidIn(List<String> values) {
            addCriterion("fileid in", values, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidNotIn(List<String> values) {
            addCriterion("fileid not in", values, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidBetween(String value1, String value2) {
            addCriterion("fileid between", value1, value2, "fileid");
            return (Criteria) this;
        }

        public Criteria andFileidNotBetween(String value1, String value2) {
            addCriterion("fileid not between", value1, value2, "fileid");
            return (Criteria) this;
        }

        public Criteria andBackupStateIsNull() {
            addCriterion("backup_state is null");
            return (Criteria) this;
        }

        public Criteria andBackupStateIsNotNull() {
            addCriterion("backup_state is not null");
            return (Criteria) this;
        }

        public Criteria andBackupStateEqualTo(Integer value) {
            addCriterion("backup_state =", value, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateNotEqualTo(Integer value) {
            addCriterion("backup_state <>", value, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateGreaterThan(Integer value) {
            addCriterion("backup_state >", value, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("backup_state >=", value, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateLessThan(Integer value) {
            addCriterion("backup_state <", value, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateLessThanOrEqualTo(Integer value) {
            addCriterion("backup_state <=", value, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateIn(List<Integer> values) {
            addCriterion("backup_state in", values, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateNotIn(List<Integer> values) {
            addCriterion("backup_state not in", values, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateBetween(Integer value1, Integer value2) {
            addCriterion("backup_state between", value1, value2, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupStateNotBetween(Integer value1, Integer value2) {
            addCriterion("backup_state not between", value1, value2, "backupState");
            return (Criteria) this;
        }

        public Criteria andBackupTimeIsNull() {
            addCriterion("backup_time is null");
            return (Criteria) this;
        }

        public Criteria andBackupTimeIsNotNull() {
            addCriterion("backup_time is not null");
            return (Criteria) this;
        }

        public Criteria andBackupTimeEqualTo(Date value) {
            addCriterion("backup_time =", value, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeNotEqualTo(Date value) {
            addCriterion("backup_time <>", value, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeGreaterThan(Date value) {
            addCriterion("backup_time >", value, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("backup_time >=", value, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeLessThan(Date value) {
            addCriterion("backup_time <", value, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeLessThanOrEqualTo(Date value) {
            addCriterion("backup_time <=", value, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeIn(List<Date> values) {
            addCriterion("backup_time in", values, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeNotIn(List<Date> values) {
            addCriterion("backup_time not in", values, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeBetween(Date value1, Date value2) {
            addCriterion("backup_time between", value1, value2, "backupTime");
            return (Criteria) this;
        }

        public Criteria andBackupTimeNotBetween(Date value1, Date value2) {
            addCriterion("backup_time not between", value1, value2, "backupTime");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
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