package com.jfshare.baseTemplate.mybatis.model.automatic;

import org.joda.time.DateTime;

public class TbPostageTemplate {
    private Integer id;

    private Integer sellerId;

    private String name;

    private Integer type;

    private String postageInfo;

    private Integer templateGroup;

    private String templateDesc;

    private DateTime createTime;

    private Integer createId;

    private DateTime lastUpdateTime;

    private Integer lastUpdateId;

    private Integer deleted;

    private Integer isUsed;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPostageInfo() {
        return postageInfo;
    }

    public void setPostageInfo(String postageInfo) {
        this.postageInfo = postageInfo == null ? null : postageInfo.trim();
    }

    public Integer getTemplateGroup() {
        return templateGroup;
    }

    public void setTemplateGroup(Integer templateGroup) {
        this.templateGroup = templateGroup;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc == null ? null : templateDesc.trim();
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

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
}