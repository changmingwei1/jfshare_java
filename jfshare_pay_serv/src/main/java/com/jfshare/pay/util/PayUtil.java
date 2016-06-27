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
import com.jfshare.pay.util.tianyipay.TianYiSubmit;
import com.jfshare.pay.util.weixinpay.WeixinConfig;
import com.jfshare.pay.util.weixinpay.WeixinSubmit;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.*;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.beanutils.converters.URLConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
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
        return DateUtils.date2Str(new DateTime().toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS2) + RandomStringUtils.randomNumeric(8);
    }

    public static String getReqTY(PayReq payReq, String payId){
        DateTime now = new DateTime();
        String curTime = DateUtils.date2Str(now.toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS2);//.date2Str()DateUtils.getCurrentStringDateYYMDHMSS();
        try {
            String AppID = PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_appcode");
            String SPID = PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_spid");
            String Token = PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_signkey");
            String SpOrderID = payReq.getOrderNo();
            String RequestNo = payId;
            String PayContent = ConvertUtil.getString(payReq.getRemark(), "尊享订单");
            String ProvinceID = payReq.getProcustID();
            String DeviceNo = payReq.getCustId();
            String DeviceType = payReq.getCustType();
            String OrderPrice = ConvertUtil.getString(payReq.getPrice());
            String PayPoint = ConvertUtil.getString(payReq.getScore());
            String PayMoney = payReq.getScore() > 0 ? ConvertUtil.getString(payReq.getPrice() - payReq.getScore2cashAmount()) : "0";
            String RequestTime = curTime;
            String AccessChannel = "WEB";
//            String AppID = "BFZX";
//            String SPID = "171445";
//            String SpOrderID = "2016061117435196361771";
//            String RequestNo = "20160611174351";
//            String PayContent = "尊享订单";
//            String ProvinceID = "15";
//            String DeviceNo = "18979177165";
//            String DeviceType = "7";
//            String OrderPrice = "1000";
//            String PayPoint = "100";
//            String PayMoney = "500";
//            String RequestTime = "20160611174351";
//            String AccessChannel = "WEB";
//            String Token = "fWMLsuqg30rgyq9rfte23eJSsKer";

            String waitStr= AppID + SPID + RequestNo + SpOrderID + PayContent + ProvinceID + DeviceNo + DeviceType + OrderPrice + PayPoint + PayMoney + RequestTime + AccessChannel + Token;
            String Sign = DigestUtils.md5Hex(waitStr);
            logger.error("waitStr==> {}", waitStr);
            logger.error("Sign==> {}", Sign);
            Map<String, String> xmlMap = new LinkedHashMap<String, String>();
            xmlMap.put("AppID", AppID);
            xmlMap.put("SPID", SPID);
            xmlMap.put("RequestNo", RequestNo);
            xmlMap.put("SpOrderID", SpOrderID);
            xmlMap.put("PayContent", PayContent);
            xmlMap.put("ProvinceID", ProvinceID);
            xmlMap.put("DeviceNo", DeviceNo);
            xmlMap.put("DeviceType", DeviceType);
            xmlMap.put("OrderPrice", OrderPrice);
            xmlMap.put("PayPoint", PayPoint);
            xmlMap.put("PayMoney", PayMoney);
            xmlMap.put("RequestTime", RequestTime);
            xmlMap.put("AccessChannel", AccessChannel);
            xmlMap.put("Sign", Sign);
            String toXml = TianYiSubmit.map2XmlStr(xmlMap);
            logger.error("toXml==> {}", toXml);
//            toXml = "<AcceptRequest><AppID>BFZX</AppID><SPID>171445</SPID><RequestNo>2016061118472027175035</RequestNo><SpOrderID>20160611184720</SpOrderID><PayContent>尊享订单</PayContent><BankID></BankID><ProvinceID>15</ProvinceID><DeviceNo>18979177165</DeviceNo><DeviceType>7</DeviceType><OrderPrice>1000</OrderPrice><PayPoint>100</PayPoint><PayMoney>500</PayMoney><RequestTime>20160611184720</RequestTime><AccessChannel>WEB</AccessChannel><Sign>830c9d5043812de61cdbf7372bf88cc4</Sign></AcceptRequest>";
            String toUnicode = DesUtil.string2Unicode(toXml);


            String encodeCBCKey = PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_deskey");
            String encodeCBCVi = PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_desvi");

            String requestXml = Des3.des3EncodeCBC(encodeCBCKey, encodeCBCVi, toUnicode);
//            logger.info(requestXml.replaceAll ("[\\t\\n\\r]", ""));


            Map<String, String> payUrlMap = new HashMap<String, String>();
            payUrlMap.put("action", PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_url"));
            payUrlMap.put("requestXml", requestXml.replaceAll ("[\\t\\n\\r]", ""));

            return JSON.toJSONString(payUrlMap);
        } catch (Exception e) {
            logger.error("支付申请Sign生成失败！", e);
        }

        return null;

    }

    /**
     * 解析第三方支付通知
     * //	strReturnSecret=Server.UrlEncode(Server.UrlEncode(des.Encrypt(Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes(strReturnSecret))
     * @param payRes
     * @return
     */
    public static TbPayRecordWithBLOBs getResTianYi(PayRes payRes) {
        String payResStr = payRes.getResUrl();
        if(StringUtils.isBlank(payResStr)) {
            logger.error("解析天翼支付通知----支付通知内容为空");
            return null;
        }

        payResStr = payResStr.replaceAll(" ", "+");    //电信的奇葩加密方式， 把+替换成了空格

        String encodeCBCKey = PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_deskey");
        String encodeCBCVi = PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_desvi");
//        String encodeCBCKey = "telefenpaytes@pay17$#3#$";
//        String encodeCBCVi = "13386191";

        TbPayRecordWithBLOBs tbPayRecord = null;
        try {
            logger.info("解析天翼支付通知----开始3DesDecode");
            String des3DecodeRet = Des3.des3DecodeCBC(encodeCBCKey, encodeCBCVi, payResStr);
            logger.info("解析天翼支付通知----3DesDecode完成");
            String xmlRes = DesUtil.unicode2String(des3DecodeRet);
            logger.info("解析天翼支付通知----unicode转String完成");
            Map<String, String> xmlMap = TianYiSubmit.xmlStr2Map(xmlRes);
            logger.info("解析天翼支付通知----xml转map完成, {}", xmlMap);

            String AppID = xmlMap.get("AppID");
            logger.debug("=====>AppID: " + AppID);
            String SPID = xmlMap.get("SPID");
            logger.debug("=====>SPID: " + SPID);
            String ResponseDate = xmlMap.get("ResponseDate");
            logger.debug("=====>ResponseDate: " + ResponseDate);
            String RequestNo = xmlMap.get("RequestNo");
            logger.debug("=====>RequestNo: " + RequestNo);
            String ResponseNo = xmlMap.get("ResponseNo");
            logger.debug("=====>ResponseNo: " + ResponseNo);
            String PayTotal = xmlMap.get("PayTotal");
            logger.debug("=====>PayTotal: " + PayTotal);
            String PayMoney = xmlMap.get("PayMoney");
            logger.debug("=====>PayMoney: " + PayMoney);
            String PayIntegral = xmlMap.get("PayIntegral");
            logger.debug("=====>PayIntegral: " + PayIntegral);
            String PayVoucher = xmlMap.get("PayVoucher");
            logger.debug("=====>PayVoucher: " + PayVoucher);
            String DeviceNo = xmlMap.get("DeviceNo");
            logger.debug("=====>DeviceNo: " + DeviceNo);
            String MsgCode = xmlMap.get("MsgCode");
            logger.debug("=====>MsgCode: " + MsgCode);
            String MsgContent = xmlMap.get("MsgContent");
            logger.debug("=====>MsgContent: " + MsgContent);
            String Sign = xmlMap.get("Sign");
            if(!"0000".equals(MsgCode)) {
                logger.warn("解析天翼支付通知----支付结果失败, MsgCode={}， MsgContent={}", MsgCode, MsgContent);
                return tbPayRecord;
            }

            //TODO Sign验证
            String Token = PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_signkey");
            String signStr = AppID + SPID + ResponseDate + RequestNo + ResponseNo + PayTotal + PayMoney + PayIntegral + PayVoucher + DeviceNo + MsgCode + MsgContent + Token;
            logger.info("解析天翼支付通知----生成签名, waitSignStr={}", signStr);
            String signMake = DigestUtils.md5Hex(signStr);
            if(!signMake.equalsIgnoreCase(Sign)) {
                logger.warn("解析天翼支付通知----签名验证失败, 通知中sign={}， 生成sign={}", Sign, signMake);
//                return tbPayRecord;
            }

            tbPayRecord = new TbPayRecordWithBLOBs();
            tbPayRecord.setPayId(RequestNo);
            tbPayRecord.setThirdRet(payRes.getResUrl());
            tbPayRecord.setThirdReq(RequestNo);
            tbPayRecord.setThirdId(ResponseNo);
            tbPayRecord.setThirdPrice(NumberUtils.toInt(PayMoney));
            tbPayRecord.setThirdScore(NumberUtils.toInt(PayVoucher) + NumberUtils.toInt(PayIntegral));
            tbPayRecord.setThirdTime(DateUtils.strToDateTime(ResponseDate, DateUtils.PATTERN_YYYYMMDDHHMMSS2));
            tbPayRecord.setThirdAccount(DeviceNo);
            tbPayRecord.setPayState(2);
            tbPayRecord.setProcessTime(new DateTime());
            logger.info("解析天翼支付通知----构建tbPayRecord={}", JSON.toJSON(tbPayRecord));
        } catch (Exception e) {
            logger.error("解析支付支付通知----解析失败，发生异常 PayRes==> {}" + payRes, e);
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
        payUrlMap.put("appid", payReq.getPayChannel() == 9 ? WeixinConfig.appid_app : WeixinConfig.appid_gzh);
        payUrlMap.put("mch_id", payReq.getPayChannel() == 9 ? WeixinConfig.mch_id_app : WeixinConfig.mch_id_gzh);
        payUrlMap.put("device_info", "WEB");
        payUrlMap.put("nonce_str", RandomStringUtils.randomAlphanumeric(20));
        payUrlMap.put("body", ConvertUtil.getString(payReq.getRemark(), "无"));
        payUrlMap.put("out_trade_no", payId);
        payUrlMap.put("total_fee", ConvertUtil.getString(payReq.getPrice()));
        payUrlMap.put("spbill_create_ip", StringUtil.isNullOrEmpty(payReq.getPayIp()) ? "127.0.0.1" : payReq.getPayIp());
        payUrlMap.put("time_start", curTime); //交易起始时间
        payUrlMap.put("time_expire", DateUtils.date2Str(now.plusHours(2).toDate(), DateUtils.PATTERN_YYYYMMDDHHMMSS2)); //交易结束时间
        String notifyUrl = PropertiesUtil.getProperty("jfx_pay_serv", "weixinpay_notify_url");
        String tradeType = "NATIVE";
        if (payReq.getPayChannel() == 4) {
            tradeType = "JSAPI";
            notifyUrl = PropertiesUtil.getProperty("jfx_pay_serv", "weixinwap_notify_url");
        } else if (payReq.getPayChannel() == 9) {
            tradeType = "APP";
            notifyUrl = PropertiesUtil.getProperty("jfx_pay_serv", "weixinapp_notify_url");
        }
        payUrlMap.put("notify_url", notifyUrl); //后端通知地址
        payUrlMap.put("trade_type", tradeType);
        if (payReq.getPayChannel() != 9) {
            payUrlMap.put("product_id", payId);
        }
        if (payReq.getPayChannel() == 4) {
            payUrlMap.put("openid", payReq.getCustId());
        }

        Map<String, String> paramMap = WeixinSubmit.buildRequestPara(payUrlMap, payReq.getPayChannel());
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
                payUrlMap.put("appId", payReq.getPayChannel() == 9 ? WeixinConfig.appid_app : WeixinConfig.appid_gzh);
                payUrlMap.put("timeStamp",ConvertUtil.getString(new DateTime().getMillis()/1000));
                payUrlMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(20));
                payUrlMap.put("package", "prepay_id=" + retMap.get("prepay_id"));
                payUrlMap.put("signType", "MD5");
                Map<String, String> paramSign = WeixinSubmit.buildRequestPara(payUrlMap, payReq.getPayChannel());
                payUrlMap.put("paySign", paramSign.get("sign"));
            } else if (payReq.getPayChannel() == 9) {
                payUrlMap.clear();
                payUrlMap.put("appid", payReq.getPayChannel() == 9 ? WeixinConfig.appid_app : WeixinConfig.appid_gzh);
                payUrlMap.put("partnerid", payReq.getPayChannel() == 9 ? WeixinConfig.mch_id_app : WeixinConfig.mch_id_gzh);
                payUrlMap.put("prepayid", retMap.get("prepay_id"));
                payUrlMap.put("package", "Sign=WXPay");
                payUrlMap.put("noncestr", RandomStringUtils.randomAlphanumeric(20));
                payUrlMap.put("timestamp",ConvertUtil.getString(new DateTime().getMillis()/1000));
                Map<String, String> paramSign = WeixinSubmit.buildRequestPara(payUrlMap, payReq.getPayChannel());
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

    /**
     * hebao notify demo
     * {"hmac":"d0d0de92470f9d00ea4ad3c817c3f17e","merchantId":"888009953110701","payNo":"201605226650729006","returnCode":"000000","message":"SUCCESS","signType":"MD5","type":"OfflineNotify","version":"2.0.0","amount":"1","amtItem":"CNY_AMT=1#CMY_AMT=0#RED_AMT=0#VCH_AMT=0#POT_CHG_AMT=0","bankAbbr":"","mobile":"150****8779","orderId":"3404ab75feb1a15d9d34cce62385a5b2","payDate":"20160522173209","accountDate":"20160522","reserved1":"","reserved2":"","status":"SUCCESS","orderDate":"20160522","fee":"0"}
     * @param payRes
     * @param jsonObject
     * @return
     */
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
            tbPayRecord.setPayState(jsonObject.get("status").equals("SUCCESS") ? 2 : 3);
            tbPayRecord.setProcessTime(new DateTime());
        } catch (Exception e) {
            logger.error("解析支付通知失败 ==> " + payRes);
        }

        return tbPayRecord;
    }

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

//        PayReq payReq1 = new PayReq();
//        PayReq(tokenId:fvBLEJEHNOw=, orderNo:fa44514e9acb568385da064067e00feb, extraParam:24_4660024, title:jfx订单, price:1000, score:1, payChannel:9, payIp:null, returnUrl:null, remark:null, custId:100017286150, custType:7)
//        payReq1.setTokenId("fvBLEJEHNOw=");
//        payReq1.setOrderNo("fa44514e9acb568wwwww064067e00jjj");
//        payReq1.setExtraParam("24_4660024");
//        payReq1.setTitle("jfx");
//        payReq1.setPrice(1000);
//        payReq1.setScore(100);
//        payReq1.setPayChannel(1);
//        payReq1.setCustId("18979177165");
//        payReq1.setScore2cashAmount(500);
//        payReq1.setCustType("7");
//        payReq1.setProcustID("15");
//        String reqTY = getReqTY(payReq1, "9dc5c3c8981aa8d9be7c21c4366bc000");
//        logger.error(reqTY);
//        getWeixinPay(payReq1, "9dc5c3c8981aa8d9be7c21c4366bcsss");

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
//            System.out.println(RandomStringUtils.randomAlphanumeric(32));
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
        String tyResStr = "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";
        PayRes payRes = new PayRes();
        payRes.setResUrl(tyResStr);
        TbPayRecordWithBLOBs resTianYi = getResTianYi(payRes);
        logger.info(JSON.toJSONString(resTianYi));
    }

}
