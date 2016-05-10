package com.jfshare.baseTemplate.mybatis.model.automatic;

import org.joda.time.DateTime;

public class TbPostage {
    private Integer id;

    private Integer sellerId;

    private Integer templateId;

    private String name;

    private String supportProvince;

    private String rule;

    private DateTime createTime;

    private Integer createId;

    private DateTime lastUpdateTime;

    private Integer lastUpdateId;

    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSupportProvince() {
        return supportProvince;
    }

    public void setSupportProvince(String supportProvince) {
        this.supportProvince = supportProvince == null ? null : supportProvince.trim();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public DateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(DateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(Integer lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}