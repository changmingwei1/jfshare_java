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
        TIAN_YI(1);
        private int enumVal=0;
        private PAY_CHANNEL(int enumVal){
            this.enumVal = enumVal;
        }
        public int getEnumVal(){
            return this.enumVal;
        }
    };
}
