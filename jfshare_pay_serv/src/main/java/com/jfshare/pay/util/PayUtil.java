package com.jfshare.pay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.finagle.thrift.pay.PayRes;
import com.jfshare.finagle.thrift.pay.PayRet;
import com.jfshare.pay.model.TbPayRecordWithBLOBs;
import com.jfshare.pay.util.alipay.config.AlipayConfig;
import com.jfshare.pay.util.alipay.util.AlipaySubmit;
import com.jfshare.pay.util.hebaopay.HebaoConfig;
import com.jfshare.pay.util.hebaopay.HebaoSubmit;
import com.jfshare.pay.util.weixinpay.WeixinConfig;
import com.jfshare.pay.util.weixinpay.WeixinSubmit;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.*;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by Administrator on 2015/12/15.
 */
public class PayUtil {
    private static final transient Logger logger = LoggerFactory.getLogger(PayUtil.class);

    /**
     * 生成支付平台交易流水号
     * @param payReq 支付请求
     * @return
     */
    public static String getPayId(PayReq payReq) {
        try {
            return IdCreator.getPayIdExZk(payReq.getOrderNo() + "_" + String.valueOf(payReq.getPayChannel() + "_" + payReq.getTokenId()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 类型转换
     * @param payRecord
     * @return
     */
    public static PayRet toPayRet(TbPayRecordWithBLOBs payRecord) {
        PayRet payRet = new PayRet();
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(payRecord);
        BeanUtil.fillBeanData(payRet, stringObjectMap);
        payRet.setRetCode(payRecord.getPayState());
        payRet.setPayAccount(payRecord.getThirdAccount());
        payRet.setProcessTime(DateUtils.toDateTimeStr(payRecord.getProcessTime().toDate()));

        //签名
        StringBuilder sbUrl = new StringBuilder(500);
        sbUrl.append("tokenId=").append(payRet.getTokenId());
        sbUrl.append("&orderNo=").append(payRet.getOrderNo());
        sbUrl.append("&processTime=").append(payRet.getProcessTime());
        sbUrl.append("&payChannel=").append(payRet.getPayChannel());
        payRet.setSign(CryptoUtil.md5Encode(sbUrl.toString()));

        return payRet;
    }

//    public final static String TIANYI_SPID = "160260"; //天翼集分平台统一分配给各接调用者的业务编号
//    private final static String TIANYI_COMMON_KEY = "JFX54475254"; //天翼分配的调用者id
//    private final static String TIANYI_KEY = "TICKET02"; //天翼Des加密key
//    private final static String TIANYI_PAY_URL_PREFIX = "http://jf.189.cn/preview/CommonPage/PayPWD.aspx?strParm=";
//
//    /**
//     * 生成第三方支付请求
//     * @param payReq
//     * @param payId
//     * @return
//     */
//    @Deprecated
//    public static String getReqTianYi(PayReq payReq, String payId) {
//        String curTime = DateUtils.toDateTimeStr(new Date());
//        StringBuilder sbUrl = new StringBuilder(500);
//        sbUrl.append("spid=").append(TIANYI_SPID);
//        sbUrl.append("&orderNo=").append(payId);
//        sbUrl.append("&point=").append(payReq.getScore());
//        sbUrl.append("&place_time=").append(curTime);
//        sbUrl.append("&custID=").append(payReq.getCustId());
//        sbUrl.append("&price=").append(PriceUtils.intToStr(payReq.getPrice()));
//        sbUrl.append("&goodsName=").append(payReq.getTitle());
//        String urlencodeParams = "";
//        try {
//            String sign = CryptoUtil.md5Encode(sbUrl.toString() + TIANYI_COMMON_KEY).toUpperCase();
////            logger.debug("md5加密 ==> " + sign);
//            String paramStr = sbUrl.toString() + "&sign="+sign;
////            logger.debug("des前数据 ==> " + paramStr);
//
//            BASE64Encoder enc = new BASE64Encoder();
//            String data = enc.encode(paramStr.getBytes("UTF-8"));
//            String encryptedtext = DesUtil.encrypt(data, TIANYI_KEY, TIANYI_KEY);
////            logger.debug("des后数据 ==> " + encryptedtext);
//            urlencodeParams = URLEncoder.encode(encryptedtext, "UTF-8");
//        } catch (Exception e) {
//            logger.error("支付申请encode支付url失败！", e);
//            return null;
//        }
//
//        String payUrl = TIANYI_PAY_URL_PREFIX + urlencodeParams;
//        logger.info("支付申请url ==> " + payUrl);
//
//        return payUrl;
//    }

//    public final static String TY_SPID = "160260"; //天翼集分平台统一分配给各接调用者的业务编号
//    private final static String TY_COMMON_KEY = "CX";  //"JFSC"; //天翼分配的调用者id
//    private final static String TY_KEY = "DH8A2412"; //"*^gf54"; //天翼Des加密key
//    private final static String TY_PAY_URL_PREFIX = "http://jf.189.cn/preview/WebCashier/Cashier.aspx"; //http://jf.189.cn/WebCashier/Cashier.aspx";
//    登录页：
//			http://jf.189.cn/preview/CommonPage/PTLGCK.aspx?Partner=160260&Sign=F71E34303426963F3ABF713D3370EA09
//			http://jf.189.cn/preview/CommonPage/Default.aspx?Partner=160260&Sign=F71E34303426963F3ABF713D3370EA09
    public static String getTianYiPayId() {
        return PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_spid") + "_" + DateUtils.date2Str(new DateTime().toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS2) + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 生成第三方支付请求
     * 天翼，post请求， 拼接成url，前端组织成功post参数
     * @param payReq
     * @param payId
     * @return
     */
    public static String getReqTY(PayReq payReq, String payId) {
        DateTime now = new DateTime();
        String curTime = DateUtils.date2Str(now.toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS2);//.date2Str()DateUtils.getCurrentStringDateYYMDHMSS();
        String sign = "";

        try {
            StringBuilder toSignStr = new StringBuilder(100);
            toSignStr.append("AppCode=").append(PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_appcode"));
            toSignStr.append("&SingKey=").append(PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_signkey"));
            toSignStr.append("&Date=").append(DateUtils.date2Str(now.plusMinutes(5).toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS2));
            toSignStr.append("&DeviceNo=").append(payReq.getCustId());
            toSignStr.append("&SPID=").append(PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_spid"));
            toSignStr.append("&DeviceType=").append(payReq.getCustType());
            toSignStr.append("&PayIntegral=").append(ConvertUtil.getString(payReq.getPrice()));
            toSignStr.append("&PayDefaultIntegral=").append(ConvertUtil.getString(payReq.getScore()));
            toSignStr.append("&PayDefaultMoney=").append(ConvertUtil.getString(payReq.getPrice() - payReq.getScore2cashAmount()));
            toSignStr.append("&CommodityName=").append(payReq.getTitle());
            toSignStr.append("&BusinessRemark=").append(ConvertUtil.getString(payReq.getRemark(), "无"));
            toSignStr.append("&SPOrderID=").append(payId);
            sign = CryptoUtil.md5Encode(toSignStr.toString());
        } catch (Exception e) {
            logger.error("支付申请Sign生成失败！", e);
            return null;
        }

        Map<String, String> payUrlMap = new HashMap<String, String>();
        payUrlMap.put("action", PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_url"));
        payUrlMap.put("AppCode", PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_appcode"));
        payUrlMap.put("RequestDate", curTime);
        payUrlMap.put("Sign", sign);
        payUrlMap.put("SpId",PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_spid"));
        payUrlMap.put("DeviceNo", payReq.getCustId());
        payUrlMap.put("DeviceType", payReq.getCustType());
        payUrlMap.put("ProvinceID", payReq.getProcustID());
        payUrlMap.put("PayIntegral", ConvertUtil.getString(payReq.getPrice()));
        payUrlMap.put("PayDefaultIntegral", ConvertUtil.getString(payReq.getScore()));
        payUrlMap.put("PayDefaultMoney", ConvertUtil.getString(payReq.getPrice() - payReq.getScore2cashAmount()));
        payUrlMap.put("CommodityName", payReq.getTitle());
        payUrlMap.put("BusinessRemark", ConvertUtil.getString(payReq.getRemark(), "无"));
        payUrlMap.put("SPOrderID", payId);

        String payUrl = JSON.toJSONString(payUrlMap);
        logger.info("TY支付申请url ==> " + payUrl);
        return payUrl;
    }

    /**
     * 解析第三方支付通知
     * //	strReturnSecret=Server.UrlEncode(Server.UrlEncode(des.Encrypt(Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes(strReturnSecret))
     * @param payRes
     * @return
     */
    public static TbPayRecordWithBLOBs getResTianYi(PayRes payRes) {
        TbPayRecordWithBLOBs tbPayRecord = null;
        try {
            JSONObject jsonObject = JSON.parseObject(payRes.getResUrl());
            tbPayRecord = new TbPayRecordWithBLOBs();
            tbPayRecord.setPayId(jsonObject.getString("SPOrderID"));
            tbPayRecord.setThirdRet(payRes.getResUrl());
            tbPayRecord.setThirdId(jsonObject.getString("OrderID"));
            tbPayRecord.setThirdPrice(jsonObject.getInteger("PayMoney"));
            tbPayRecord.setThirdScore(jsonObject.getInteger("PayIntegral"));
            tbPayRecord.setThirdTime(DateUtils.strToDateTime(jsonObject.getString("RequestsDate"), DateUtils.PATTERN_YYYYMMDDHHMMSS2));
            tbPayRecord.setThirdAccount(jsonObject.getString("UserNameTX"));
            tbPayRecord.setPayState(2);
            tbPayRecord.setProcessTime(new DateTime());
        } catch (Exception e) {
            logger.error("解析支付通知失败 ==> " + payRes);
        }

        return tbPayRecord;
    }

    /**
     * 生成第三方支付请求
     * 支付宝pc，post请求， 拼接成url，前端组织成功post参数
     * @param payReq
     * @param payId
     * @return
     */
    public static String getReqAliPay(PayReq payReq, String payId) {
        Map<String, String> payUrlMap = new HashMap<String, String>();
        payUrlMap.put("_input_charset", AlipayConfig.input_charset);
        payUrlMap.put("body", ConvertUtil.getString(payReq.getRemark(), "无"));
//        payUrlMap.put("exter_invoke_ip", payReq.getPayIp());
        payUrlMap.put("notify_url", PropertiesUtil.getProperty("jfx_pay_serv", "alipay_notify_url"));
        payUrlMap.put("out_trade_no", payId);
        payUrlMap.put("partner", AlipayConfig.partner);
        payUrlMap.put("payment_type", "1");
        payUrlMap.put("return_url", PropertiesUtil.getProperty("jfx_pay_serv", "alipay_return_url"));
        payUrlMap.put("seller_email", AlipayConfig.seller_email);
//        payUrlMap.put("seller_id", AlipayConfig.partner);
        payUrlMap.put("service", "create_direct_pay_by_user");
        payUrlMap.put("subject", payReq.getTitle());
        payUrlMap.put("total_fee", PriceUtils.intToStr(payReq.getPrice()));

//        payUrlMap.put("show_url", null);
//        payUrlMap.put("anti_phishing_key", null);

        Map<String, String> retMap = AlipaySubmit.buildRequestPara(payUrlMap, SignType.MD5);
        retMap.put("action", AlipayConfig.ALIPAY_GATEWAY_NEW);

        String payUrl = JSON.toJSONString(retMap);
        logger.info("AliPay支付申请url ==> " + payUrl);
        return payUrl;
    }

    public static String getReqAliH5(PayReq payReq, String payId) {
        Map<String, String> payUrlMap = new HashMap<String, String>();
        payUrlMap.put("_input_charset", AlipayConfig.input_charset);
        payUrlMap.put("body", ConvertUtil.getString(payReq.getRemark(), "无"));
//        payUrlMap.put("exter_invoke_ip", payReq.getPayIp());
        payUrlMap.put("notify_url", PropertiesUtil.getProperty("jfx_pay_serv", "alipay_notify_url"));
        payUrlMap.put("out_trade_no", payId);
        payUrlMap.put("partner", AlipayConfig.partner);
        payUrlMap.put("payment_type", "1");
        payUrlMap.put("return_url", PropertiesUtil.getProperty("jfx_pay_serv", "aliwap_return_url"));
        payUrlMap.put("seller_id", AlipayConfig.partner);
        payUrlMap.put("service", "alipay.wap.create.direct.pay.by.user");
        payUrlMap.put("subject", payReq.getTitle());
        payUrlMap.put("total_fee", PriceUtils.intToStr(payReq.getPrice()));
        payUrlMap.put("show_url", PropertiesUtil.getProperty("jfx_pay_serv", "aliwap_show_url"));

        Map<String, String> retMap = AlipaySubmit.buildRequestPara(payUrlMap, SignType.MD5);
        retMap.put("action", AlipayConfig.ALIPAY_GATEWAY_NEW);

        String payUrl = JSON.toJSONString(retMap);
        logger.info("AliH5支付申请url ==> " + payUrl);
        return payUrl;
    }

    public static String getReqAliApp(PayReq payReq, String payId) {
        Map<String, String> payUrlMap = new HashMap<String, String>();
        payUrlMap.put("_input_charset", AlipayConfig.input_charset);
        payUrlMap.put("body", ConvertUtil.getString(payReq.getRemark(), "无"));
//        payUrlMap.put("exter_invoke_ip", payReq.getPayIp());
        payUrlMap.put("notify_url", PropertiesUtil.getProperty("jfx_pay_serv", "alipay_notify_url"));
        payUrlMap.put("out_trade_no", payId);
        payUrlMap.put("partner", AlipayConfig.partner);
        payUrlMap.put("payment_type", "1");
        payUrlMap.put("seller_id", AlipayConfig.partner);
        payUrlMap.put("service", "mobile.securitypay.pay");
        payUrlMap.put("subject", payReq.getTitle());
        payUrlMap.put("total_fee", PriceUtils.intToStr(payReq.getPrice()));

        Map<String, String> retMap = AlipaySubmit.buildRequestPara(payUrlMap, SignType.RSA);
        retMap.put("sign", AlipayConfig.ALIPAY_RSA_PRIVATE); //TODO 临时方案 RSA加密有问题, 先由app拿到私钥自行加密

        String payUrl = JSON.toJSONString(retMap);
        logger.info("AliApp支付申请url ==> " + payUrl);
        return payUrl;
    }

    public static TbPayRecordWithBLOBs getResAliPay(PayRes payRes, Map<String, String> jsonObject) {
        TbPayRecordWithBLOBs tbPayRecord = null;
        try {
            tbPayRecord = new TbPayRecordWithBLOBs();
            tbPayRecord.setPayId(jsonObject.get("out_trade_no"));
            tbPayRecord.setThirdRet(payRes.getResUrl());
            tbPayRecord.setThirdId(jsonObject.get("trade_no"));
            tbPayRecord.setThirdPrice(PriceUtils.strToInt(jsonObject.get("total_fee")));
            tbPayRecord.setThirdScore(0);
            tbPayRecord.setThirdTime(DateUtils.strToDateTime(jsonObject.get("notify_time"), DateUtils.PATTERN_YYYYMMDDHHMMSS2));
            tbPayRecord.setThirdAccount(jsonObject.get("buyer_email"));
            tbPayRecord.setPayState(jsonObject.get("trade_status").equals("TRADE_SUCCESS") ? 2 : 3);
            tbPayRecord.setProcessTime(new DateTime());
        } catch (Exception e) {
            logger.error("解析支付通知失败 ==> " + payRes);
        }

        return tbPayRecord;
    }

    /**
     * 生成第三方支付请求
     * 微信pc，h5 get请求， 拼接成url，前端组织成功get参数
     * httpPost返回demo
     *
     <xml><return_code><![CDATA[SUCCESS]]></return_code>
     <return_msg><![CDATA[OK]]></return_msg>
     <appid><![CDATA[wxd20631014da48053]]></appid>
     <mch_id><![CDATA[1241526502]]></mch_id>
     <device_info><![CDATA[WEB]]></device_info>
     <nonce_str><![CDATA[nPp2cdQ0jLSJzGUJ]]></nonce_str>
     <sign><![CDATA[B03BCAFBE3676FFF47E4144A26E538DB]]></sign>
     <result_code><![CDATA[SUCCESS]]></result_code>
     <prepay_id><![CDATA[wx201603102354142b3b2b6da50408946808]]></prepay_id>
     <trade_type><![CDATA[NATIVE]]></trade_type>
     <code_url><![CDATA[weixin://wxpay/bizpayurl?pr=iz3iyJf]]></code_url>
     </xml>
     * @param payReq
     * @param payId
     * @return
     */
    public static String getWeixinPay(PayReq payReq, String payId) throws Exception {
        DateTime now = new DateTime();
        String curTime = DateUtils.date2Str(now.toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS2);//.date2Str()DateUtils.getCurrentStringDateYYMDHMSS();

        Map<String, String> payUrlMap = new HashMap<String, String>();
        payUrlMap.put("appid", WeixinConfig.appid);
        payUrlMap.put("mch_id", WeixinConfig.mch_id);
        payUrlMap.put("device_info", "WEB");
        payUrlMap.put("nonce_str", RandomStringUtils.randomAlphanumeric(20));
        payUrlMap.put("body", ConvertUtil.getString(payReq.getRemark(), "无"));
        payUrlMap.put("out_trade_no", payId);
        payUrlMap.put("total_fee", ConvertUtil.getString(payReq.getPrice()));
        payUrlMap.put("spbill_create_ip", StringUtil.isNullOrEmpty(payReq.getPayIp()) ? "127.0.0.1" : payReq.getPayIp());
        payUrlMap.put("time_start", curTime); //交易起始时间
        payUrlMap.put("time_expire", DateUtils.date2Str(now.plusHours(2).toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS2)); //交易结束时间
        payUrlMap.put("notify_url", PropertiesUtil.getProperty("jfx_pay_serv", "weixinpay_notify_url")); //后端通知地址
        String tradeType = "NATIVE";
        if (payReq.getPayChannel() == 4) {
            tradeType = "JSAPI";
        } else if (payReq.getPayChannel() == 9) {
            tradeType = "APP";
        }
        payUrlMap.put("trade_type", tradeType);
        if (payReq.getPayChannel() != 9) {
            payUrlMap.put("product_id", payId);
        }
        if (payReq.getPayChannel() == 4) {
            payUrlMap.put("openid", payReq.getCustId());
        }

        Map<String, String> paramMap = WeixinSubmit.buildRequestPara(payUrlMap);
        String paramXml = WeixinSubmit.map2XmlStr(paramMap);
        String retXml = HttpUtils.xmlHttpPost(paramXml, WeixinConfig.GATEWAY_NEW);
        logger.info("微信调起支付返回的结果为＝＝＝"+retXml);
        Map<String, String> retMap = WeixinSubmit.xmlStr2Map(retXml);

        if (retMap.get("return_code") != null && retMap.get("return_code").equals("SUCCESS") &&
              retMap.get("result_code") != null && retMap.get("result_code").equals("SUCCESS")) {
            //        retMap.put("action", WeixinConfig.GATEWAY_NEW);
            payUrlMap.put("code_url", retMap.get("code_url"));
            payUrlMap.put("prepay_id", retMap.get("prepay_id"));

            //调起支付额外参数
            if (payReq.getPayChannel() == 4) {
                payUrlMap.clear();
                payUrlMap.put("appId", WeixinConfig.appid);
                payUrlMap.put("timeStamp",ConvertUtil.getString(new DateTime().getMillis()/1000));
                payUrlMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(20));
                payUrlMap.put("package", "prepay_id=" + retMap.get("prepay_id"));
                payUrlMap.put("signType", "MD5");
                Map<String, String> paramSign = WeixinSubmit.buildRequestPara(payUrlMap);
                payUrlMap.put("paySign", paramSign.get("sign"));
            } else if (payReq.getPayChannel() == 9) {
                payUrlMap.clear();
                payUrlMap.put("appid", WeixinConfig.appid);
                payUrlMap.put("partnerid", WeixinConfig.mch_id);
                payUrlMap.put("prepayid", retMap.get("prepay_id"));
                payUrlMap.put("package", "Sign=WXPay");
                payUrlMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(20));
                payUrlMap.put("timestamp",ConvertUtil.getString(new DateTime().getMillis()/1000));
                Map<String, String> paramSign = WeixinSubmit.buildRequestPara(payUrlMap);
                payUrlMap.put("sign", paramSign.get("sign"));
            }
            String payUrl = JSON.toJSONString(payUrlMap);
            logger.info("WeixinPay支付申请url ==> " + payUrl);
            return payUrl;
        } else {
            logger.warn("$$$$WeixinPay支付申请返回失败! url ==> " + payReq);
        }

        return null;
    }

    //通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒
    public static TbPayRecordWithBLOBs getResWeixinPay(PayRes payRes, Map<String, String> jsonObject) {
        TbPayRecordWithBLOBs tbPayRecord = null;
        try {
            tbPayRecord = new TbPayRecordWithBLOBs();
            tbPayRecord.setPayId(jsonObject.get("out_trade_no"));
            tbPayRecord.setThirdRet(payRes.getResUrl());
            tbPayRecord.setThirdId(jsonObject.get("transaction_id"));
            tbPayRecord.setThirdPrice(ConvertUtil.getInt(jsonObject.get("total_fee")));
            tbPayRecord.setThirdScore(0);
            tbPayRecord.setThirdTime(DateUtils.strToDateTime(jsonObject.get("time_end"), DateUtils.PATTERN_YYYYMMDDHHMMSS2));
            tbPayRecord.setThirdAccount(jsonObject.get("openid"));
            tbPayRecord.setPayState((jsonObject.get("return_code") != null && jsonObject.get("return_code").equals("SUCCESS") &&
                    jsonObject.get("result_code") != null && jsonObject.get("result_code").equals("SUCCESS")) ? 2 : 3);
            tbPayRecord.setProcessTime(new DateTime());
        } catch (Exception e) {
            logger.error("解析支付通知失败 ==> " + payRes);
        }

        return tbPayRecord;
    }

    /**
     * 生成第三方支付请求
     * 移动和包wap支付
     * @param payReq
     * @param payId
     * @return
     */
    public static String getHebaoH5(PayReq payReq, String payId) {
        String payUrl = null;
        Map<String, String> payUrlMap = new LinkedHashMap<String, String>();
        payUrlMap.put("characterSet", "02");
        payUrlMap.put("callbackUrl", PropertiesUtil.getProperty("jfx_pay_serv", "hebaowap_return_url"));
        payUrlMap.put("notifyUrl", PropertiesUtil.getProperty("jfx_pay_serv", "hebaopay_notify_url"));
        payUrlMap.put("ipAddress", StringUtil.isNullOrEmpty(payReq.getPayIp()) ? "127.0.0.1" : payReq.getPayIp());
        payUrlMap.put("merchantId", HebaoConfig.merchantId);
        payUrlMap.put("requestId", payId);
        payUrlMap.put("signType", "MD5");
        payUrlMap.put("type", "WAPDirectPayConfirm");
        payUrlMap.put("version", "2.0.0");

        payUrlMap.put("amount", ConvertUtil.getString(payReq.getPrice()));
        payUrlMap.put("bankAbbr", "");
        payUrlMap.put("currency", "00");
        payUrlMap.put("orderDate", DateUtils.getCurrentStringDateYYYYMMDD());
        payUrlMap.put("orderId", payId);
        payUrlMap.put("merAcDate", DateUtils.getCurrentStringDateYYYYMMDD());
        payUrlMap.put("period", "24");
        payUrlMap.put("periodUnit", "01");
        payUrlMap.put("merchantAbbr", "");
        payUrlMap.put("productDesc", ConvertUtil.getString(payReq.getRemark(), "无"));
        payUrlMap.put("productId", "");
        payUrlMap.put("productName", payReq.getTitle());
        payUrlMap.put("productNum", "");
        payUrlMap.put("reserved1", "");
        payUrlMap.put("reserved2", "");
        payUrlMap.put("userToken", "");
        payUrlMap.put("showUrl", "");
        payUrlMap.put("couponsFlag", "00");
        //1.签名
        Map<String, String> paramSign = HebaoSubmit.buildRequestPara(payUrlMap);
        payUrlMap.put("action", HebaoConfig.action);

        //2.调用和包获取支付链接
        String payH5Req = HebaoSubmit.getPayH5Ret(payUrlMap);
        if (payH5Req == null) {
            logger.error("组织和包h5支付链接失败！");
            return null;
        }
//        System.out.println("和包h5支付链接为===" + payH5Req);
        try {
            String payH5Ret = HttpUtils.httpPost(payH5Req);
            //3.1 验证返回结果状态
            if (!HebaoSubmit.getValue(payH5Ret, "returnCode").equals("000000")) {
                logger.warn("和包h5支付链接申请结果失败！" + payH5Ret);
                return null;
            }
            //3.2 验证返回结果签名
            String hmac1 = HebaoSubmit.getValue(payH5Ret, "hmac");
            Map<String, String> payRetMap = new LinkedHashMap<String, String>();
            payRetMap.put("merchantId", HebaoSubmit.getValue(payH5Ret, "merchantId"));
            payRetMap.put("requestId", HebaoSubmit.getValue(payH5Ret, "requestId"));
            payRetMap.put("signType", HebaoSubmit.getValue(payH5Ret, "signType"));
            payRetMap.put("type", HebaoSubmit.getValue(payH5Ret, "type"));
            payRetMap.put("version", HebaoSubmit.getValue(payH5Ret, "version"));
            payRetMap.put("returnCode", HebaoSubmit.getValue(payH5Ret, "returnCode"));
            payRetMap.put("message", URLDecoder.decode(HebaoSubmit.getValue(payH5Ret, "message"), "UTF-8"));
            payRetMap.put("payUrl",  HebaoSubmit.getValue(payH5Ret, "payUrl"));
            Map<String, String> retSign = HebaoSubmit.buildRequestPara(payRetMap);
//            System.out.println("自生成retSign=" + retSign.get("hmac") + ",和包返回sign=" + hmac1);
            if (!hmac1.equals(retSign.get("hmac"))) {
                logger.warn("和包h5申请支付结果签名验证失败！");
                return null;
            }
            String submit_url = HebaoSubmit.getRedirectUrl(HebaoSubmit.getValue(payH5Ret, "payUrl"));
            payUrlMap.put("action_submit", submit_url);

            payUrl = JSON.toJSONString(payUrlMap);
//            System.out.println("和包h5支付链接为===" + submit_url);
        } catch (Exception e) {
            logger.error("请求和包h5支付链接失败！", e);
            return null;
        }

        logger.info("hebaoH5支付申请url ==> " + payUrl);
        return payUrl;
    }

    /**
     * 生成第三方支付请求
     * 移动和包PC支付
     * @param payReq
     * @param payId
     * @return
     */
    public static String getHebaoPay(PayReq payReq, String payId) {
        String payUrl = null;
        Map<String, String> payUrlMap = new LinkedHashMap<String, String>();
        payUrlMap.put("characterSet", "02");
        payUrlMap.put("callbackUrl", PropertiesUtil.getProperty("jfx_pay_serv", "hebaopay_return_url"));
        payUrlMap.put("notifyUrl", PropertiesUtil.getProperty("jfx_pay_serv", "hebaopay_notify_url"));
        payUrlMap.put("ipAddress", StringUtil.isNullOrEmpty(payReq.getPayIp()) ? "127.0.0.1" : payReq.getPayIp());
        payUrlMap.put("merchantId", HebaoConfig.merchantId);
        payUrlMap.put("requestId", payId);
        payUrlMap.put("signType", "MD5");
        payUrlMap.put("type", "DirectPayConfirm");
        payUrlMap.put("version", "2.0.0");

        payUrlMap.put("amount", ConvertUtil.getString(payReq.getPrice()));
        payUrlMap.put("bankAbbr", "");
        payUrlMap.put("currency", "00");
        payUrlMap.put("orderDate", DateUtils.getCurrentStringDateYYYYMMDD());
        payUrlMap.put("orderId", payId);
        payUrlMap.put("merAcDate", DateUtils.getCurrentStringDateYYYYMMDD());
        payUrlMap.put("period", "24");
        payUrlMap.put("periodUnit", "01");
        payUrlMap.put("merchantAbbr", "");
        payUrlMap.put("productDesc", ConvertUtil.getString(payReq.getRemark(), "无"));
        payUrlMap.put("productId", "");
        payUrlMap.put("productName", payReq.getTitle());
        payUrlMap.put("productNum", "");
        payUrlMap.put("reserved1", "");
        payUrlMap.put("reserved2", "");
        payUrlMap.put("userToken", "");
        payUrlMap.put("showUrl", "");
        payUrlMap.put("couponsFlag", "00");
        //1.签名
        Map<String, String> paramSign = HebaoSubmit.buildRequestPara(payUrlMap);
        payUrlMap.put("action", HebaoConfig.action);

        //2.调用和包获取支付链接
        String payPCReq = HebaoSubmit.getPayH5Ret(payUrlMap);
        if (payPCReq == null) {
            logger.error("组织和包pc支付链接失败！");
            return null;
        }

        try {
            String payPCRet = HttpUtils.httpPost(payPCReq);
            //3.1 验证返回结果状态
            if (!HebaoSubmit.getValue(payPCRet, "returnCode").equals("000000")) {
                logger.warn("和包PC支付链接申请结果失败！" + payPCRet);
                return null;
            }
            //3.2 验证返回结果签名
            String hmac1 = HebaoSubmit.getValue(payPCRet, "hmac");
            Map<String, String> payRetMap = new LinkedHashMap<String, String>();
            payRetMap.put("merchantId", HebaoSubmit.getValue(payPCRet, "merchantId"));
            payRetMap.put("requestId", HebaoSubmit.getValue(payPCRet, "requestId"));
            payRetMap.put("signType", HebaoSubmit.getValue(payPCRet, "signType"));
            payRetMap.put("type", HebaoSubmit.getValue(payPCRet, "type"));
            payRetMap.put("version", HebaoSubmit.getValue(payPCRet, "version"));
            payRetMap.put("returnCode", HebaoSubmit.getValue(payPCRet, "returnCode"));
            payRetMap.put("message", URLDecoder.decode(HebaoSubmit.getValue(payPCRet, "message"), "UTF-8"));
            payRetMap.put("payUrl",  HebaoSubmit.getValue(payPCRet, "payUrl"));
            Map<String, String> retSign = HebaoSubmit.buildRequestPara(payRetMap);
//            System.out.println("自生成retSign=" + retSign.get("hmac") + ",和包返回sign=" + hmac1);
            if (!hmac1.equals(retSign.get("hmac"))) {
                logger.warn("和包PC申请支付结果签名验证失败！");
                return null;
            }
            String submit_url = HebaoSubmit.getRedirectUrl(HebaoSubmit.getValue(payPCRet, "payUrl"));
            payUrlMap.put("action_submit", submit_url);

            payUrl = JSON.toJSONString(payUrlMap);
//            System.out.println("和包PC支付链接为===" + submit_url);
        } catch (Exception e) {
            logger.error("请求和包PC支付链接失败！", e);
            return null;
        }

        logger.info("hebaoPC支付申请url ==> " + payUrl);
        return payUrl;
    }

    public static TbPayRecordWithBLOBs getResHebaoPay(PayRes payRes, Map<String, String> jsonObject) {
        TbPayRecordWithBLOBs tbPayRecord = null;
        try {
            tbPayRecord = new TbPayRecordWithBLOBs();
            tbPayRecord.setPayId(jsonObject.get("orderId"));
            tbPayRecord.setThirdRet(payRes.getResUrl());
            tbPayRecord.setThirdId(jsonObject.get("payNo"));
            tbPayRecord.setThirdPrice(ConvertUtil.getInt(jsonObject.get("amount")));
            tbPayRecord.setThirdScore(0);
            tbPayRecord.setThirdTime(DateUtils.strToDateTime(jsonObject.get("payDate"), DateUtils.PATTERN_YYYYMMDDHHMMSS2));
            tbPayRecord.setThirdAccount(jsonObject.get("mobile"));
            tbPayRecord.setPayState(jsonObject.get("mobile").equals("SUCCESS") ? 2 : 3);
            tbPayRecord.setProcessTime(new DateTime());
        } catch (Exception e) {
            logger.error("解析支付通知失败 ==> " + payRes);
        }

        return tbPayRecord;
    }

    /**
     * 生成二维码byte流
     * @param param
     * @return
     */
//    public static ByteBuffer gerQRCode(String param) {
//        ByteBuffer ret = null;
//        ByteArrayOutputStream out = null;
//        try {
//            param = new String(param.getBytes("UTF-8"),"ISO-8859-1");
//            out = QRCode.from(param)
//                    .to(ImageType.PNG).withSize(165, 165).stream();
//            ret = ByteBuffer.wrap(out.toByteArray());
//        } catch (IOException e) {
//            logger.error("生成二维码流失败!", param);
//        } finally {
//            if(out!= null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return ret;
//    }

    public static void main(String args[]) throws Exception {
//        PayReq payReq = new PayReq();
//        payReq.setPrice(1);
//        payReq.setRemark("dd");
//        payReq.setTitle("j的");
//        String payId = "ef3c240626f2e00ea032b4b00ff768f82";
//
//        String hebaoH5 = getHebaoH5(payReq, payId);

        PayReq payReq1 = new PayReq();
//        PayReq(tokenId:fvBLEJEHNOw=, orderNo:fa44514e9acb568385da064067e00feb, extraParam:24_4660024, title:jfx订单, price:1000, score:1, payChannel:9, payIp:null, returnUrl:null, remark:null, custId:100017286150, custType:7)
        payReq1.setTokenId("fvBLEJEHNOw=");
        payReq1.setOrderNo("fa44514e9acb568385da064067e00feb");
        payReq1.setExtraParam("24_4660024");
        payReq1.setTitle("jfx");
        payReq1.setPrice(1000);
        payReq1.setScore(1);
        payReq1.setPayChannel(9);

        getWeixinPay(payReq1, "9dc5c3c8981aa8d9be7c21c4366bc8c1");

//        System.out.println(hebaoH5);
//        JSONObject jsonObject = JSON.parseObject(hebaoH5);
//        StringBuilder ss = new StringBuilder();
//        ss.append(jsonObject.get("action")).append("?");
//        ss.append("hmac=" +  jsonObject.get("hmac") + "&");
//        ss.append("characterSet="+jsonObject.get("characterSet")).append("&");
//        ss.append("callbackUrl="+jsonObject.get("callbackUrl")).append("&");
//        ss.append("notifyUrl="+jsonObject.get("notifyUrl")).append("&");
//        ss.append("ipAddress="+jsonObject.get("ipAddress")).append("&");
//        ss.append("merchantId="+jsonObject.get("merchantId")).append("&");
//        ss.append("requestId="+jsonObject.get("requestId")).append("&");
//        ss.append("signType="+jsonObject.get("signType")).append("&");
//        ss.append("type="+jsonObject.get("type")).append("&");
//        ss.append("version="+jsonObject.get("version")).append("&");
//        ss.append("amount="+jsonObject.get("amount")).append("&");
//        ss.append("bankAbbr="+jsonObject.get("bankAbbr")).append("&");
//        ss.append("currency="+jsonObject.get("currency")).append("&");
//        ss.append("orderDate="+jsonObject.get("orderDate")).append("&");
//        ss.append("orderId="+jsonObject.get("orderId")).append("&");
//        ss.append("merAcDate="+jsonObject.get("merAcDate")).append("&");
//        ss.append("period="+jsonObject.get("period")).append("&");
//        ss.append("periodUnit="+jsonObject.get("periodUnit")).append("&");
//        ss.append("merchantAbbr="+jsonObject.get("merchantAbbr")).append("&");
//        ss.append("productDesc="+jsonObject.get("productDesc")).append("&");
//        ss.append("productId="+jsonObject.get("productId")).append("&");
//        ss.append("productName="+URLEncoder.encode((String)jsonObject.get("productName"),"gbk")).append("&");
//        ss.append("productNum="+jsonObject.get("productNum")).append("&");
//        ss.append("reserved1="+jsonObject.get("reserved1")).append("&");
//        ss.append("reserved2="+jsonObject.get("reserved2")).append("&");
//        ss.append("userToken="+jsonObject.get("userToken")).append("&");
//        ss.append("showUrl="+jsonObject.get("showUrl")).append("&");
//        ss.append("couponsFlag="+jsonObject.get("couponsFlag"));
//
////        System.out.println(ss.toString());
//        String s = HttpUtils.httpPost(ss.toString());
////        System.out.println(s);
////        System.out.println(HebaoSubmit.getValue(s, "hmac"));
//        System.out.println(HebaoSubmit.getValue(s, "payUrl"));
//        String redirectUrl = HebaoSubmit.getRedirectUrl(HebaoSubmit.getValue(s, "payUrl"));
//        System.out.println("redirct=====" + redirectUrl);

//        Map<String, String> retMap = new HashMap<String, String>();
//        retMap.put("merchantId", "888009953110701");
//        retMap.put("", "");
//        retMap.put("", "");
//        retMap.put("", "");
//        String mysign = HebaoSubmit.buildRequestMysign(retMap);
//        System.out.println(mysign + ",是否有效=" + mysign.equals());

//        System.out.println(ConvertUtil.getString(new DateTime().getMillis()/1000));
//        System.out.println(getTianYiPayId());
//        System.out.println(String.valueOf(new Random().nextInt(9999-1000+1)+1000));
//        for(int i = 0;i<40;i++)
//        System.out.println(RandomStringUtils.randomNumeric(4));
//        System.out.println("g3Qivc5P9zaGy81FR2slqb03r2Xgx6HY".length());
//        while(true) {
//            String s = RandomStringUtils.randomAlphanumeric(32);
//            if (s.contains("o") || s.contains("l") || s.contains("1") || s.contains("O") || s.contains("0")) {
//                continue;
//            }
            System.out.println(RandomStringUtils.randomAlphanumeric(32));
//            return;
//        }

//        PayReq req = new PayReq();
//        req.setRemark("无");req.setPrice(11);
//        System.out.println(getWeixinPay(req, "pay0000000000id"));

        //生成二维码
//        String toParseStr = "解决了中文乱码";
//        toParseStr = new String(toParseStr.getBytes("UTF-8"),"ISO-8859-1");
//        ByteArrayOutputStream out = QRCode.from(toParseStr)
//                .to(ImageType.PNG).withSize(165, 165).stream();
//        try {
//            FileOutputStream fout = new FileOutputStream(new File(
//                    "E:/QR_Code.JPG"));
//            fout.write(out.toByteArray());
//
//            fout.flush();
//            fout.close();
//
//        } catch (FileNotFoundException e) {
//            // Do Logging
//        } catch (IOException e) {
//            // Do Logging
//        }
//
//        // get QR file from text using defaults
//        File file = QRCode.from(toParseStr).file();
//        // get QR stream from text using defaults
//        ByteArrayOutputStream stream = QRCode.from(toParseStr).stream();
//
//        // override the image type to be JPG
//        QRCode.from(toParseStr).to(ImageType.JPG).file();
//        QRCode.from(toParseStr).to(ImageType.JPG).stream();
//
//        // override image size to be 250x250
//        QRCode.from(toParseStr).withSize(250, 250).file();
//        QRCode.from(toParseStr).withSize(250, 250).stream();
//
//        // override size and image type
//        QRCode.from(toParseStr).to(ImageType.GIF).withSize(250, 250).file();
//        QRCode.from(toParseStr).to(ImageType.GIF).withSize(250, 250).stream();
//
//        //       Website Link (URLs) QR Code in Java
//        ByteArrayOutputStream out1 = QRCode.from("http://viralpatel.net")
//                .to(ImageType.PNG).stream();
}

}
