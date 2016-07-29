package com.jfshare.product.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbThirdPartyProductExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbThirdPartyProductExample() {
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

        public Criteria andThirdPartyProductIdIsNull() {
            addCriterion("third_party_product_id is null");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdIsNotNull() {
            addCriterion("third_party_product_id is not null");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdEqualTo(Integer value) {
            addCriterion("third_party_product_id =", value, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdNotEqualTo(Integer value) {
            addCriterion("third_party_product_id <>", value, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdGreaterThan(Integer value) {
            addCriterion("third_party_product_id >", value, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("third_party_product_id >=", value, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdLessThan(Integer value) {
            addCriterion("third_party_product_id <", value, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdLessThanOrEqualTo(Integer value) {
            addCriterion("third_party_product_id <=", value, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdIn(List<Integer> values) {
            addCriterion("third_party_product_id in", values, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdNotIn(List<Integer> values) {
            addCriterion("third_party_product_id not in", values, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdBetween(Integer value1, Integer value2) {
            addCriterion("third_party_product_id between", value1, value2, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyProductIdNotBetween(Integer value1, Integer value2) {
            addCriterion("third_party_product_id not between", value1, value2, "thirdPartyProductId");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyIsNull() {
            addCriterion("third_party_identify is null");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyIsNotNull() {
            addCriterion("third_party_identify is not null");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyEqualTo(String value) {
            addCriterion("third_party_identify =", value, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyNotEqualTo(String value) {
            addCriterion("third_party_identify <>", value, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyGreaterThan(String value) {
            addCriterion("third_party_identify >", value, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyGreaterThanOrEqualTo(String value) {
            addCriterion("third_party_identify >=", value, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyLessThan(String value) {
            addCriterion("third_party_identify <", value, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyLessThanOrEqualTo(String value) {
            addCriterion("third_party_identify <=", value, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyLike(String value) {
            addCriterion("third_party_identify like", value, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyNotLike(String value) {
            addCriterion("third_party_identify not like", value, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyIn(List<String> values) {
            addCriterion("third_party_identify in", values, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyNotIn(List<String> values) {
            addCriterion("third_party_identify not in", values, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyBetween(String value1, String value2) {
            addCriterion("third_party_identify between", value1, value2, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andThirdPartyIdentifyNotBetween(String value1, String value2) {
            addCriterion("third_party_identify not between", value1, value2, "thirdPartyIdentify");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andViceNameIsNull() {
            addCriterion("vice_name is null");
            return (Criteria) this;
        }

        public Criteria andViceNameIsNotNull() {
            addCriterion("vice_name is not null");
            return (Criteria) this;
        }

        public Criteria andViceNameEqualTo(String value) {
            addCriterion("vice_name =", value, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameNotEqualTo(String value) {
            addCriterion("vice_name <>", value, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameGreaterThan(String value) {
            addCriterion("vice_name >", value, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameGreaterThanOrEqualTo(String value) {
            addCriterion("vice_name >=", value, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameLessThan(String value) {
            addCriterion("vice_name <", value, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameLessThanOrEqualTo(String value) {
            addCriterion("vice_name <=", value, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameLike(String value) {
            addCriterion("vice_name like", value, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameNotLike(String value) {
            addCriterion("vice_name not like", value, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameIn(List<String> values) {
            addCriterion("vice_name in", values, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameNotIn(List<String> values) {
            addCriterion("vice_name not in", values, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameBetween(String value1, String value2) {
            addCriterion("vice_name between", value1, value2, "viceName");
            return (Criteria) this;
        }

        public Criteria andViceNameNotBetween(String value1, String value2) {
            addCriterion("vice_name not between", value1, value2, "viceName");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNull() {
            addCriterion("subject_id is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNotNull() {
            addCriterion("subject_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdEqualTo(Integer value) {
            addCriterion("subject_id =", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotEqualTo(Integer value) {
            addCriterion("subject_id <>", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThan(Integer value) {
            addCriterion("subject_id >", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("subject_id >=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThan(Integer value) {
            addCriterion("subject_id <", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("subject_id <=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIn(List<Integer> values) {
            addCriterion("subject_id in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotIn(List<Integer> values) {
            addCriterion("subject_id not in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdBetween(Integer value1, Integer value2) {
            addCriterion("subject_id between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("subject_id not between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andBrandIdIsNull() {
            addCriterion("brand_id is null");
            return (Criteria) this;
        }

        public Criteria andBrandIdIsNotNull() {
            addCriterion("brand_id is not null");
            return (Criteria) this;
        }

        public Criteria andBrandIdEqualTo(Integer value) {
            addCriterion("brand_id =", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotEqualTo(Integer value) {
            addCriterion("brand_id <>", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdGreaterThan(Integer value) {
            addCriterion("brand_id >", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("brand_id >=", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdLessThan(Integer value) {
            addCriterion("brand_id <", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdLessThanOrEqualTo(Integer value) {
            addCriterion("brand_id <=", value, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdIn(List<Integer> values) {
            addCriterion("brand_id in", values, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotIn(List<Integer> values) {
            addCriterion("brand_id not in", values, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdBetween(Integer value1, Integer value2) {
            addCriterion("brand_id between", value1, value2, "brandId");
            return (Criteria) this;
        }

        public Criteria andBrandIdNotBetween(Integer value1, Integer value2) {
            addCriterion("brand_id not between", value1, value2, "brandId");
            return (Criteria) this;
        }

        public Criteria andImgKeyIsNull() {
            addCriterion("img_key is null");
            return (Criteria) this;
        }

        public Criteria andImgKeyIsNotNull() {
            addCriterion("img_key is not null");
            return (Criteria) this;
        }

        public Criteria andImgKeyEqualTo(String value) {
            addCriterion("img_key =", value, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyNotEqualTo(String value) {
            addCriterion("img_key <>", value, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyGreaterThan(String value) {
            addCriterion("img_key >", value, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyGreaterThanOrEqualTo(String value) {
            addCriterion("img_key >=", value, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyLessThan(String value) {
            addCriterion("img_key <", value, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyLessThanOrEqualTo(String value) {
            addCriterion("img_key <=", value, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyLike(String value) {
            addCriterion("img_key like", value, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyNotLike(String value) {
            addCriterion("img_key not like", value, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyIn(List<String> values) {
            addCriterion("img_key in", values, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyNotIn(List<String> values) {
            addCriterion("img_key not in", values, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyBetween(String value1, String value2) {
            addCriterion("img_key between", value1, value2, "imgKey");
            return (Criteria) this;
        }

        public Criteria andImgKeyNotBetween(String value1, String value2) {
            addCriterion("img_key not between", value1, value2, "imgKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyIsNull() {
            addCriterion("detail_key is null");
            return (Criteria) this;
        }

        public Criteria andDetailKeyIsNotNull() {
            addCriterion("detail_key is not null");
            return (Criteria) this;
        }

        public Criteria andDetailKeyEqualTo(String value) {
            addCriterion("detail_key =", value, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyNotEqualTo(String value) {
            addCriterion("detail_key <>", value, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyGreaterThan(String value) {
            addCriterion("detail_key >", value, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyGreaterThanOrEqualTo(String value) {
            addCriterion("detail_key >=", value, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyLessThan(String value) {
            addCriterion("detail_key <", value, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyLessThanOrEqualTo(String value) {
            addCriterion("detail_key <=", value, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyLike(String value) {
            addCriterion("detail_key like", value, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyNotLike(String value) {
            addCriterion("detail_key not like", value, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyIn(List<String> values) {
            addCriterion("detail_key in", values, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyNotIn(List<String> values) {
            addCriterion("detail_key not in", values, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyBetween(String value1, String value2) {
            addCriterion("detail_key between", value1, value2, "detailKey");
            return (Criteria) this;
        }

        public Criteria andDetailKeyNotBetween(String value1, String value2) {
            addCriterion("detail_key not between", value1, value2, "detailKey");
            return (Criteria) this;
        }

        public Criteria andActiveStateIsNull() {
            addCriterion("active_state is null");
            return (Criteria) this;
        }

        public Criteria andActiveStateIsNotNull() {
            addCriterion("active_state is not null");
            return (Criteria) this;
        }

        public Criteria andActiveStateEqualTo(Integer value) {
            addCriterion("active_state =", value, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateNotEqualTo(Integer value) {
            addCriterion("active_state <>", value, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateGreaterThan(Integer value) {
            addCriterion("active_state >", value, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("active_state >=", value, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateLessThan(Integer value) {
            addCriterion("active_state <", value, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateLessThanOrEqualTo(Integer value) {
            addCriterion("active_state <=", value, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateIn(List<Integer> values) {
            addCriterion("active_state in", values, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateNotIn(List<Integer> values) {
            addCriterion("active_state not in", values, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateBetween(Integer value1, Integer value2) {
            addCriterion("active_state between", value1, value2, "activeState");
            return (Criteria) this;
        }

        public Criteria andActiveStateNotBetween(Integer value1, Integer value2) {
            addCriterion("active_state not between", value1, value2, "activeState");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdIsNull() {
            addCriterion("product_snapshoot_id is null");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdIsNotNull() {
            addCriterion("product_snapshoot_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdEqualTo(String value) {
            addCriterion("product_snapshoot_id =", value, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdNotEqualTo(String value) {
            addCriterion("product_snapshoot_id <>", value, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdGreaterThan(String value) {
            addCriterion("product_snapshoot_id >", value, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_snapshoot_id >=", value, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdLessThan(String value) {
            addCriterion("product_snapshoot_id <", value, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdLessThanOrEqualTo(String value) {
            addCriterion("product_snapshoot_id <=", value, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdLike(String value) {
            addCriterion("product_snapshoot_id like", value, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdNotLike(String value) {
            addCriterion("product_snapshoot_id not like", value, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdIn(List<String> values) {
            addCriterion("product_snapshoot_id in", values, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdNotIn(List<String> values) {
            addCriterion("product_snapshoot_id not in", values, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdBetween(String value1, String value2) {
            addCriterion("product_snapshoot_id between", value1, value2, "productSnapshootId");
            return (Criteria) this;
        }

        public Criteria andProductSnapshootIdNotBetween(String value1, String value2) {
            addCriterion("product_snapshoot_id not between", value1, value2, "productSnapshootId");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
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

        public Criteria andLastUpdateTimeEqualTo(Date value) {
            addCriterion("last_update_time =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(Date value) {
            addCriterion("last_update_time <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(Date value) {
            addCriterion("last_update_time >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update_time >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(Date value) {
            addCriterion("last_update_time <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_update_time <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<Date> values) {
            addCriterion("last_update_time in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<Date> values) {
            addCriterion("last_update_time not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("last_update_time between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_update_time not between", value1, value2, "lastUpdateTime");
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

        public Criteria andTagsIsNull() {
            addCriterion("tags is null");
            return (Criteria) this;
        }

        public Criteria andTagsIsNotNull() {
            addCriterion("tags is not null");
            return (Criteria) this;
        }

        public Criteria andTagsEqualTo(String value) {
            addCriterion("tags =", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotEqualTo(String value) {
            addCriterion("tags <>", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThan(String value) {
            addCriterion("tags >", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThanOrEqualTo(String value) {
            addCriterion("tags >=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThan(String value) {
            addCriterion("tags <", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThanOrEqualTo(String value) {
            addCriterion("tags <=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLike(String value) {
            addCriterion("tags like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotLike(String value) {
            addCriterion("tags not like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsIn(List<String> values) {
            addCriterion("tags in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotIn(List<String> values) {
            addCriterion("tags not in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsBetween(String value1, String value2) {
            addCriterion("tags between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotBetween(String value1, String value2) {
            addCriterion("tags not between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsIsNull() {
            addCriterion("storehouse_ids is null");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsIsNotNull() {
            addCriterion("storehouse_ids is not null");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsEqualTo(String value) {
            addCriterion("storehouse_ids =", value, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsNotEqualTo(String value) {
            addCriterion("storehouse_ids <>", value, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsGreaterThan(String value) {
            addCriterion("storehouse_ids >", value, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsGreaterThanOrEqualTo(String value) {
            addCriterion("storehouse_ids >=", value, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsLessThan(String value) {
            addCriterion("storehouse_ids <", value, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsLessThanOrEqualTo(String value) {
            addCriterion("storehouse_ids <=", value, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsLike(String value) {
            addCriterion("storehouse_ids like", value, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsNotLike(String value) {
            addCriterion("storehouse_ids not like", value, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsIn(List<String> values) {
            addCriterion("storehouse_ids in", values, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsNotIn(List<String> values) {
            addCriterion("storehouse_ids not in", values, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsBetween(String value1, String value2) {
            addCriterion("storehouse_ids between", value1, value2, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andStorehouseIdsNotBetween(String value1, String value2) {
            addCriterion("storehouse_ids not between", value1, value2, "storehouseIds");
            return (Criteria) this;
        }

        public Criteria andPostageIdIsNull() {
            addCriterion("postage_id is null");
            return (Criteria) this;
        }

        public Criteria andPostageIdIsNotNull() {
            addCriterion("postage_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostageIdEqualTo(Integer value) {
            addCriterion("postage_id =", value, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdNotEqualTo(Integer value) {
            addCriterion("postage_id <>", value, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdGreaterThan(Integer value) {
            addCriterion("postage_id >", value, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("postage_id >=", value, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdLessThan(Integer value) {
            addCriterion("postage_id <", value, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdLessThanOrEqualTo(Integer value) {
            addCriterion("postage_id <=", value, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdIn(List<Integer> values) {
            addCriterion("postage_id in", values, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdNotIn(List<Integer> values) {
            addCriterion("postage_id not in", values, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdBetween(Integer value1, Integer value2) {
            addCriterion("postage_id between", value1, value2, "postageId");
            return (Criteria) this;
        }

        public Criteria andPostageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("postage_id not between", value1, value2, "postageId");
            return (Criteria) this;
        }

        public Criteria andStockStateIsNull() {
            addCriterion("stock_state is null");
            return (Criteria) this;
        }

        public Criteria andStockStateIsNotNull() {
            addCriterion("stock_state is not null");
            return (Criteria) this;
        }

        public Criteria andStockStateEqualTo(Integer value) {
            addCriterion("stock_state =", value, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateNotEqualTo(Integer value) {
            addCriterion("stock_state <>", value, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateGreaterThan(Integer value) {
            addCriterion("stock_state >", value, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_state >=", value, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateLessThan(Integer value) {
            addCriterion("stock_state <", value, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateLessThanOrEqualTo(Integer value) {
            addCriterion("stock_state <=", value, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateIn(List<Integer> values) {
            addCriterion("stock_state in", values, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateNotIn(List<Integer> values) {
            addCriterion("stock_state not in", values, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateBetween(Integer value1, Integer value2) {
            addCriterion("stock_state between", value1, value2, "stockState");
            return (Criteria) this;
        }

        public Criteria andStockStateNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_state not between", value1, value2, "stockState");
            return (Criteria) this;
        }

        public Criteria andPriceStateIsNull() {
            addCriterion("price_state is null");
            return (Criteria) this;
        }

        public Criteria andPriceStateIsNotNull() {
            addCriterion("price_state is not null");
            return (Criteria) this;
        }

        public Criteria andPriceStateEqualTo(Integer value) {
            addCriterion("price_state =", value, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateNotEqualTo(Integer value) {
            addCriterion("price_state <>", value, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateGreaterThan(Integer value) {
            addCriterion("price_state >", value, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("price_state >=", value, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateLessThan(Integer value) {
            addCriterion("price_state <", value, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateLessThanOrEqualTo(Integer value) {
            addCriterion("price_state <=", value, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateIn(List<Integer> values) {
            addCriterion("price_state in", values, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateNotIn(List<Integer> values) {
            addCriterion("price_state not in", values, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateBetween(Integer value1, Integer value2) {
            addCriterion("price_state between", value1, value2, "priceState");
            return (Criteria) this;
        }

        public Criteria andPriceStateNotBetween(Integer value1, Integer value2) {
            addCriterion("price_state not between", value1, value2, "priceState");
            return (Criteria) this;
        }

        public Criteria andOfferStateIsNull() {
            addCriterion("offer_state is null");
            return (Criteria) this;
        }

        public Criteria andOfferStateIsNotNull() {
            addCriterion("offer_state is not null");
            return (Criteria) this;
        }

        public Criteria andOfferStateEqualTo(Integer value) {
            addCriterion("offer_state =", value, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateNotEqualTo(Integer value) {
            addCriterion("offer_state <>", value, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateGreaterThan(Integer value) {
            addCriterion("offer_state >", value, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("offer_state >=", value, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateLessThan(Integer value) {
            addCriterion("offer_state <", value, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateLessThanOrEqualTo(Integer value) {
            addCriterion("offer_state <=", value, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateIn(List<Integer> values) {
            addCriterion("offer_state in", values, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateNotIn(List<Integer> values) {
            addCriterion("offer_state not in", values, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateBetween(Integer value1, Integer value2) {
            addCriterion("offer_state between", value1, value2, "offerState");
            return (Criteria) this;
        }

        public Criteria andOfferStateNotBetween(Integer value1, Integer value2) {
            addCriterion("offer_state not between", value1, value2, "offerState");
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