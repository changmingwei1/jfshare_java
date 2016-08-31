package com.jfshare.card.model;

import org.joda.time.DateTime;

public class TbBatchCardsRecord {
    private Integer id;

    private Integer activityId;

    private String cardName;

    private String cardPsd;

    private String sendStatus;

    private String rechargeStatus;

    private String rechargeAccount;

    private DateTime rechargeTime;

    private DateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public String getCardPsd() {
        return cardPsd;
    }

    public void setCardPsd(String cardPsd) {
        this.cardPsd = cardPsd == null ? null : cardPsd.trim();
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    public String getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(String rechargeStatus) {
        this.rechargeStatus = rechargeStatus == null ? null : rechargeStatus.trim();
    }

    public String getRechargeAccount() {
        return rechargeAccount;
    }

    public void setRechargeAccount(String rechargeAccount) {
        this.rechargeAccount = rechargeAccount == null ? null : rechargeAccount.trim();
    }

    public DateTime getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(DateTime rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }
}