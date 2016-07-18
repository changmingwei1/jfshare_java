package com.jfshare.utils;

/**
 * 业务公共类
 * Created by lilx on 2015/12/14.
 */
public class BizUtil {
    //用户类型，1买家，2卖家，3系统
    public static enum USER_TYPE {
        BUYER(1), SELLER (2), SYSTEM(3);
        private int enumVal=0;
        private USER_TYPE(int enumVal){
            this.enumVal = enumVal;
        }
        public int getEnumVal(){
            return this.enumVal;
        }
    };

    //支付渠道，1翼支付
    public static enum PAY_CHANNEL {
        JFX_SCORE(0, "聚分享积分支付"),
        TIAN_YI(1, "翼支付"),
        ALIPAY(2, "支付宝"),
        WEIXINPAY(3, "微信"),
        WEIXINWAP(4, "微信H5"),
        ALIWAP(5, "支付宝H5"),
        HEBAOGZWAP(6, "和包H5"),
        ALIAPP(7, "支付宝app"),
        HEBAOPAY(8, "和包PC"),
        WEIXINAPP(9, "微信app");
        private int enumVal=0;
        private String desc="";
        private PAY_CHANNEL(int enumVal, String desc){
            this.enumVal = enumVal;
            this.desc = desc;
        }
        public int getEnumVal(){
            return this.enumVal;
        }

        public String getDesc() {
            return desc;
        }

        public static PAY_CHANNEL getEnumByValue(int enumVal) {
            switch (enumVal) {
                case 0:{
                    return JFX_SCORE;
                }
                case 1:{
                    return TIAN_YI;
                }
                case 2:{
                    return ALIPAY;
                }
                case 3:{
                    return WEIXINPAY;
                }
                case 4:{
                    return WEIXINWAP;
                }
                case 5:{
                    return ALIWAP;
                }
                case 6:{
                    return HEBAOGZWAP;
                }
                case 7:{
                    return ALIAPP;
                }
                case 8:{
                    return HEBAOPAY;
                }
                case 9:{
                    return WEIXINAPP;
                }
            }
            return null;
        }
    }
}
