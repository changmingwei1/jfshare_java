package com.jfshare.pay.model;

import org.joda.time.DateTime;

public class TbPayRecord {
    private Integer id;

    private String payId;

    private String tokenId;

    private String orderNo;

    private String extraParam;

    private String title;

    private Integer price;

    private Integer score;

    private Integer payChannel;

    private String payIp;

    private String returnUrl;

    private String remark;

    private Integer payState;

    private Integer bizCode;

    private String thirdId;

    private Integer thirdPrice;

    private Integer thirdScore;

    private String thirdAccount;

    private DateTime thirdTime;

    private DateTime processTime;

    private DateTime createTime;

    private DateTime lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId == null ? null : tokenId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam == null ? null : extraParam.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayIp() {
        return payIp;
    }

    public void setPayIp(String payIp) {
        this.payIp = payIp == null ? null : payIp.trim();
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl == null ? null : returnUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public Integer getBizCode() {
        return bizCode;
    }

    public void setBizCode(Integer bizCode) {
        this.bizCode = bizCode;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId == null ? null : thirdId.trim();
    }

    public Integer getThirdPrice() {
        return thirdPrice;
    }

    public void setThirdPrice(Integer thirdPrice) {
        this.thirdPrice = thirdPrice;
    }

    public Integer getThirdScore() {
        return thirdScore;
    }

    public void setThirdScore(Integer thirdScore) {
        this.thirdScore = thirdScore;
    }

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount == null ? null : thirdAccount.trim();
    }

    public DateTime getThirdTime() {
        return thirdTime;
    }

    public void setThirdTime(DateTime thirdTime) {
        this.thirdTime = thirdTime;
    }

    public DateTime getProcessTime() {
        return processTime;
    }

    public void setProcessTime(DateTime processTime) {
        this.processTime = processTime;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(DateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}