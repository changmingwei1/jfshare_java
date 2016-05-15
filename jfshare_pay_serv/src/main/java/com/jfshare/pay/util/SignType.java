package com.jfshare.pay.util;

/**
 * 签名类型
 * Created by stepyee on 16/5/15.
 */
public enum SignType {
    MD5("MD5"), RSA("RSA");
    private String enumVal;
    private SignType(String enumVal){
        this.enumVal = enumVal;
    }
    public String getEnumVal(){
        return this.enumVal;
    }
}
