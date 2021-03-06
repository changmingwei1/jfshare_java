package com.jfshare.product.model;

import java.util.Date;

public class TbThirdPartyProduct {
    private Integer id;

    private String productId;

    private String thirdPartyProductId;

    private Integer thirdPartyIdentify;

    private String name;

    private String viceName;

    private String imgKey;

    private String detailKey;

    private Integer activeState;

    private String productSnapshootId;

    private Integer type;

    private Date createTime;

    private Integer createUserId;

    private Date lastUpdateTime;

    private Integer lastUpdateId;

    private Integer state;

    private String tags;

    private String storehouseIds;

    private Integer postageId;

    private Integer stockState;

    private Integer priceState;

    private Integer offerState;

    private String stockInfo;

    private String sellerclassnum;

    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getThirdPartyProductId() {
        return thirdPartyProductId;
    }

    public void setThirdPartyProductId(String thirdPartyProductId) {
        this.thirdPartyProductId = thirdPartyProductId == null ? null : thirdPartyProductId.trim();
    }

    public Integer getThirdPartyIdentify() {
        return thirdPartyIdentify;
    }

    public void setThirdPartyIdentify(Integer thirdPartyIdentify) {
        this.thirdPartyIdentify = thirdPartyIdentify;
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

    public Integer getStockState() {
        return stockState;
    }

    public void setStockState(Integer stockState) {
        this.stockState = stockState;
    }

    public Integer getPriceState() {
        return priceState;
    }

    public void setPriceState(Integer priceState) {
        this.priceState = priceState;
    }

    public Integer getOfferState() {
        return offerState;
    }

    public void setOfferState(Integer offerState) {
        this.offerState = offerState;
    }

    public String getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(String stockInfo) {
        this.stockInfo = stockInfo == null ? null : stockInfo.trim();
    }

    public String getSellerclassnum() {
        return sellerclassnum;
    }

    public void setSellerclassnum(String sellerclassnum) {
        this.sellerclassnum = sellerclassnum == null ? null : sellerclassnum.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}