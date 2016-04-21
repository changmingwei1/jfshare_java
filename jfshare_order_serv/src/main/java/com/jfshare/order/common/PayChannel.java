package com.jfshare.order.common;

/**
 * Created by Lenovo on 2015/10/26.
 */
public enum PayChannel {
    none(0,"none"),
    alipay(1,"alipay");


    private int type;
    private String desc;

    PayChannel(int type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public static PayChannel findByType(int type) {
        switch (type) {
            case 1:
                return alipay;
            default:
                return null;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


