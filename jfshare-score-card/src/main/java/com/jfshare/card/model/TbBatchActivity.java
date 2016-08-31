package com.jfshare.card.model;

import org.joda.time.DateTime;

public class TbBatchActivity {
    private Integer id;

    private String name;

    private Integer pieceValue;

    private Integer totalCount;

    private String rechargeType;

    private DateTime createTime;

    private DateTime startTime;

    private DateTime endTime;

    private String curStatus;

    private String password;

    private String multiRechargeEnable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPieceValue() {
        return pieceValue;
    }

    public void setPieceValue(Integer pieceValue) {
        this.pieceValue = pieceValue;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType == null ? null : rechargeType.trim();
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public String getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus == null ? null : curStatus.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMultiRechargeEnable() {
        return multiRechargeEnable;
    }

    public void setMultiRechargeEnable(String multiRechargeEnable) {
        this.multiRechargeEnable = multiRechargeEnable == null ? null : multiRechargeEnable.trim();
    }
}