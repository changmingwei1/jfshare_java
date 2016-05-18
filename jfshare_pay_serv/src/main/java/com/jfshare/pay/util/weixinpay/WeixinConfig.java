package com.jfshare.pay.util.weixinpay;

/**
 * Created by stepyee on 16/3/9.
 */
public class WeixinConfig {
    public static String input_charset = "utf-8";
    // 商户的私钥
    public static String key = "ewevqNRrAvhlFmqFiKbOmt4qIM0buotw"; //old2015 "esvlhExwkEjICItRmHJqwP65ohRrFeJR"; //old2014 g3Qivc5P9zaGy81FR2slqb03r2Xgx6HY
    // 签名方式 不需修改
    public static String sign_type = "MD5";
    //公众账号ID
    public static String appid = "wx4ee9e61ac3632537"; //"wx8431024cc21de418"; //old wxd20631014da48053
    //商户号
    public static String mch_id = "1330572901"; //"10011931"; //old 1241526502
    //支付网关
    public static final String GATEWAY_NEW = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 以下为商户配置参数
     */
//    public static String notify_url = "http://120.24.153.102:13005/pay/notify/weixinpay"; //后端支付通知地址
}
