package com.jfshare.card.model;

import org.joda.time.DateTime;

public class TbBatchRechargeRecord {
    private Integer id;

    private String cardName;

    private Integer pieceValue;

    private String rechargeType;

    private Integer userId;

    private Integer activityId;

    private DateTime rechargeTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public Integer getPieceValue() {
        return pieceValue;
    }

    public void setPieceValue(Integer pieceValue) {
        this.pieceValue = pieceValue;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType == null ? null : rechargeType.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public DateTime getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(DateTime rechargeTime) {
        this.rechargeTime = rechargeTime;
    }
}