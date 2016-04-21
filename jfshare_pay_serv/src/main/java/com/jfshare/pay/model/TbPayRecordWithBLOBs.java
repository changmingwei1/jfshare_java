package com.jfshare.pay.model;

public class TbPayRecordWithBLOBs extends TbPayRecord {
    private String thirdReq;

    private String thirdRet;

    public String getThirdReq() {
        return thirdReq;
    }

    public void setThirdReq(String thirdReq) {
        this.thirdReq = thirdReq == null ? null : thirdReq.trim();
    }

    public String getThirdRet() {
        return thirdRet;
    }

    public void setThirdRet(String thirdRet) {
        this.thirdRet = thirdRet == null ? null : thirdRet.trim();
    }
}