package com.jfshare.aftersale.model;

import org.joda.time.DateTime;

public class TbAfterSale {
    private Integer userId;

    private Integer sellerId;

    private String orderId;

    private String productId;

    private String skuNum;

    private Integer type;

    private String reason;

    private String userComment;

    private String approveComment;

    private Integer state;

    private DateTime applyTime;

    private DateTime approveTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment == null ? null : userComment.trim();
    }

    public String getApproveComment() {
        return approveComment;
    }

    public void setApproveComment(String approveComment) {
        this.approveComment = approveComment == null ? null : approveComment.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public DateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(DateTime applyTime) {
        this.applyTime = applyTime;
    }

    public DateTime getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(DateTime approveTime) {
        this.approveTime = approveTime;
    }
}