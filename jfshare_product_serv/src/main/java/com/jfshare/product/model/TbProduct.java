package com.jfshare.product.model;

import java.util.Date;

public class TbProduct {
    private String id;

    private Integer sellerId;

    private String name;

    private String viceName;

    private Integer subjectId;

    private Integer brandId;

    private String imgKey;

    private String detailKey;

    private Integer maxBuyLimit;

    private Integer activeState;

    private String productSnapshootId;

    private Integer type;

    private String remark;

    private Date createTime;

    private Integer createUserId;

    private Date lastUpdateTime;

    private Integer lastUpdateId;

    private Integer state;

    private String tags;

    private String storehouseIds;

    private Integer postageId;

    private Integer clickRate;

    private String thirdexchangerate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getViceName() {
        return viceName;
    }

    public void setViceName(String viceName) {
        this.viceName = viceName == null ? null : viceName.trim();
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getImgKey() {
        return imgKey;
    }

    public void setImgKey(String imgKey) {
        this.imgKey = imgKey == null ? null : imgKey.trim();
    }

    public String getDetailKey() {
        return detailKey;
    }

    public void setDetailKey(String detailKey) {
        this.detailKey = detailKey == null ? null : detailKey.trim();
    }

    public Integer getMaxBuyLimit() {
        return maxBuyLimit;
    }

    public void setMaxBuyLimit(Integer maxBuyLimit) {
        this.maxBuyLimit = maxBuyLimit;
    }

    public Integer getActiveState() {
        return activeState;
    }

    public void setActiveState(Integer activeState) {
        this.activeState = activeState;
    }

    public String getProductSnapshootId() {
        return productSnapshootId;
    }

    public void setProductSnapshootId(String productSnapshootId) {
        this.productSnapshootId = productSnapshootId == null ? null : productSnapshootId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(Integer lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getStorehouseIds() {
        return storehouseIds;
    }

    public void setStorehouseIds(String storehouseIds) {
        this.storehouseIds = storehouseIds == null ? null : storehouseIds.trim();
    }

    public Integer getPostageId() {
        return postageId;
    }

    public void setPostageId(Integer postageId) {
        this.postageId = postageId;
    }

    public Integer getClickRate() {
        return clickRate;
    }

    public void setClickRate(Integer clickRate) {
        this.clickRate = clickRate;
    }

    public String getThirdexchangerate() {
        return thirdexchangerate;
    }

    public void setThirdexchangerate(String thirdexchangerate) {
        this.thirdexchangerate = thirdexchangerate == null ? null : thirdexchangerate.trim();
    }
}