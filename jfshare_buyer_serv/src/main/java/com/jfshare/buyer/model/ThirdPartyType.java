package com.jfshare.buyer.model;

/**
 * Created by Lenovo on 2015/12/16.
 */
public enum ThirdPartyType {
    TY(1,"tianyi"),
    H5_FOSHAN(2,"yidong_foshan");
    ;
    private String desc;
    private int code;

    ThirdPartyType(int code, String desc){
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
