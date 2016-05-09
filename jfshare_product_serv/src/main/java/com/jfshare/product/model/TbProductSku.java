package com.jfshare.product.model;

import java.util.Date;

public class TbProductSku {
    private Integer id;

    private String productId;

    private String skuNum;

    private String skuName;

    private String sellerClassNum;

    private String shelf;

    private Integer curPrice;

    private Integer orgPrice;

    private String comment;

    private Date lastUpdateTime;

    private Integer lastUpdateUserId;

    private Date createTime;

    private Integer createUserId;

    private String vPicture;

    private Integer refPrice;

    private String weight;

    private Integer storehouseId;

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

    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum == null ? null : skuNum.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getSellerClassNum() {
        return sellerClassNum;
    }

    public void setSellerClassNum(String sellerClassNum) {
        this.sellerClassNum = sellerClassNum == null ? null : sellerClassNum.trim();
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf == null ? null : shelf.trim();
    }

    public Integer getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Integer curPrice) {
        this.curPrice = curPrice;
    }

    public Integer getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(Integer orgPrice) {
        this.orgPrice = orgPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(Integer lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
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

    public String getvPicture() {
        return vPicture;
    }

    public void setvPicture(String vPicture) {
        this.vPicture = vPicture == null ? null : vPicture.trim();
    }

    public Integer getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(Integer refPrice) {
        this.refPrice = refPrice;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public Integer getStorehouseId() {
        return storehouseId;
    }

    public void setStorehouseId(Integer storehouseId) {
        this.storehouseId = storehouseId;
    }
}