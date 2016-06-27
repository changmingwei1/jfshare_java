package com.jfshare.pay.util.hebaopay;

import com.alibaba.fastjson.JSON;
import com.jfshare.pay.util.alipay.sign.MD5;
import com.jfshare.pay.util.alipay.util.AlipayCore;
import com.jfshare.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by stepyee on 16/4/1.
 */
public class HebaoSubmit {
    private static final transient Logger logger = LoggerFactory.getLogger(HebaoSubmit.class);
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());

        StringBuilder prestr = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

//            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
//                prestr = prestr + key + "=" + value;
//            } else {
//                prestr = prestr + key + "=" + value + "&";
//            }
            prestr.append(value);
        }

        logger.info("params = [" + prestr + "]");
        return prestr.toString();
    }

    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, String> sPara) {
        String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysignTmp = MD5.cryptMd5(prestr, ""); //MD5.sign(prestr, HebaoConfig.key, HebaoConfig.input_charset).toUpperCase();
        String mysign = MD5.cryptMd5(mysignTmp, HebaoConfig.key);
        return mysign;
    }

    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
//        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sParaTemp);

        //签名结果与签名方式加入请求提交参数组中
        sParaTemp.put("hmac", mysign);

        return sParaTemp;
    }

    public static String getValue(String respMsg, String name)
    {
        String resArr[] = StringUtils.split(respMsg, "&");
        Map resMap = new HashMap();
        for (int i = 0; i < resArr.length; i++)
        {
            String data = resArr[i];
            int index = StringUtils.indexOf(data, '=');
            String nm = StringUtils.substring(data, 0, index);
            String val = StringUtils.substring(data, index + 1);
            resMap.put(nm, val);
        }

        return (String)resMap.get(name) != null ? (String)resMap.get(name) : "";
    }

    /**
     * 解析申请支付返回的结果
     * demo:
     * merchantId=888009953110701&requestId=ef3c240626f2e00ea032b4b00ff768f81&signType=MD5&type=WAPDirectPayConfirm&version=2.0.0&returnCode=000000&message=SUCCESS&payUrl=url<hi:=>http://ipos.10086.cn/wap/index.xhtml<hi:$$>method<hi:=>POST<hi:$$>sessionId<hi:=>20160408201604080466790130&hmac=89937537b442211b99e1d2a72d2f4e3a
     * @param payUrl
     * @return
     */
    public static String getRedirectUrl(String payUrl)
    {
        HashMap rdUrl = new HashMap();
        if (payUrl != null)
        {
            String items[] = payUrl.split("[<hi:$$>]{7}");
            if (items != null)
            {
                for (int i = 0; i < items.length; i++)
                {
                    String item = items[i];
                    if (item == null)
                        continue;
                    String element[] = item.split("[<hi:=>]{6}");
                    if (element != null && element.length == 2)
                        rdUrl.put(element[0], element[1]);
                }

            }
        }
        return (new StringBuilder()).append(rdUrl.get("url")).append("?").append("sessionId=").append(rdUrl.get("sessionId")).toString();
    }

    /**
     * 申请和包H5支付链接
     * demo:https://ipos.10086.cn/ips/cmpayService?hmac=22526be0233a1d842d955f128fb24f61&characterSet=02&callbackUrl=http%3A%2F%2Fh5.jfshare.com%2Fshare%2Fhtml%2Fpay-success.html&notifyUrl=http%3A%2F%2F120.24.153.102%3A13005%2Fpay%2Fnotify%2Fhebaowap&ipAddress=127.0.0.1&merchantId=888009953110701&requestId=ef3c240626f2e00ea032b4b00ff768f81&signType=MD5&type=WAPDirectPayConfirm&version=2.0.0&amount=1&bankAbbr=&currency=00&orderDate=20160408&orderId=ef3c240626f2e00ea032b4b00ff768f81&merAcDate=20160408&period=24&periodUnit=01&merchantAbbr=&productDesc=dd&productId=&productName=jssss&productNum=&reserved1=&reserved2=&userToken=&showUrl=&couponsFlag=00
     * @param payUrlMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getPayH5Ret(Map<String, String> payUrlMap) {
        StringBuilder ss = new StringBuilder();
        ss.append(payUrlMap.get("action")).append("?");
        ss.append("hmac=" +  payUrlMap.get("hmac") + "&");

        List<String> keys = new ArrayList<String>(payUrlMap.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (key.equals("action") || key.equals("hmac")) {
                continue;
            }
            String value = payUrlMap.get(key);
            try {
                ss.append(key + "=" + URLEncoder.encode(value,"gbk") + "&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
        ss.deleteCharAt(ss.length()-1);

//        logger.info("params = [" + ss + "]");
        return ss.toString();
    }

    /**
     * {"hmac":"d0d0de92470f9d00ea4ad3c817c3f17e","merchantId":"888009953110701","payNo":"201605226650729006",
     * "returnCode":"000000","message":"SUCCESS","signType":"MD5","type":"OfflineNotify","version":"2.0.0","amount":"1",
     * "amtItem":"CNY_AMT=1#CMY_AMT=0#RED_AMT=0#VCH_AMT=0#POT_CHG_AMT=0","bankAbbr":"","mobile":"150****8779",
     * "orderId":"3404ab75feb1a15d9d34cce62385a5b2","payDate":"20160522173209","accountDate":"20160522","reserved1":"",
     * "reserved2":"","status":"SUCCESS","orderDate":"20160522","fee":"0"}
     * @param params
     *
     * @return
     */
    public static boolean verify(Map<String, String> params) {
        String hmac = params.get("hmac");
        if (StringUtil.isNullOrEmpty(hmac)) {
            return false;
        }


        //按照排序的字段
        Map<String, String> sParaTemp = new LinkedHashMap<String, String>();
        sParaTemp.put("hmac", params.get("hmac"));
        sParaTemp.put("merchantId", params.get("merchantId"));
        if (params.get("payNo") != null) {
            sParaTemp.put("payNo", params.get("payNo"));
        }
        if (params.get("returnCode") != null) {
            sParaTemp.put("returnCode", params.get("returnCode"));
        }
        if (params.get("message") != null) {
            sParaTemp.put("message", params.get("message"));
        }
        if (params.get("signType") != null) {
            sParaTemp.put("signType", params.get("signType"));
        }
        if (params.get("type") != null) {
            sParaTemp.put("type", params.get("type"));
        }
        if (params.get("version") != null) {
            sParaTemp.put("version", params.get("version"));
        }
        if (params.get("amount") != null) {
            sParaTemp.put("amount", params.get("amount"));
        }
        if (params.get("amtItem") != null) {
            sParaTemp.put("amtItem", params.get("amtItem"));
        }
        if (params.get("bankAbbr") != null) {
            sParaTemp.put("bankAbbr", params.get("bankAbbr"));
        }
        if (params.get("mobile") != null) {
            sParaTemp.put("mobile", params.get("mobile"));
        }
        if (params.get("orderId") != null) {
            sParaTemp.put("orderId", params.get("orderId"));
        }
        if (params.get("payDate") != null) {
            sParaTemp.put("payDate", params.get("payDate"));
        }
        if (params.get("accountDate") != null) {
            sParaTemp.put("accountDate", params.get("accountDate"));
        }
        if (params.get("reserved1") != null) {
            sParaTemp.put("reserved1", params.get("reserved1"));
        }
        if (params.get("reserved2") != null) {
            sParaTemp.put("reserved2", params.get("reserved2"));
        }
        if (params.get("status") != null) {
            sParaTemp.put("status", params.get("status"));
        }
        if (params.get("orderDate") != null) {
            sParaTemp.put("orderDate", params.get("orderDate"));
        }
        if (params.get("fee") != null) {
            sParaTemp.put("fee", params.get("fee"));
        }

        sParaTemp.remove("hmac");


        Map<String, String> retMac = buildRequestPara(sParaTemp);
        if (retMac.get("hmac") == null || !retMac.get("hmac").equals(hmac)) {
            logger.error("和包支付支付通知的签名验证fail!");
            return false;
        }
        if (retMac.get("status") == null || !retMac.get("status").equals("SUCCESS")) {
            logger.error("和包支付支付通知的返回状态fail!");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String ss = "{\"hmac\":\"d0d0de92470f9d00ea4ad3c817c3f17e\",\"merchantId\":\"888009953110701\",\"payNo\":\"201605226650729006\",\"returnCode\":\"000000\",\"message\":\"SUCCESS\",\"signType\":\"MD5\",\"type\":\"OfflineNotify\",\"version\":\"2.0.0\",\"amount\":\"1\",\"amtItem\":\"CNY_AMT=1#CMY_AMT=0#RED_AMT=0#VCH_AMT=0#POT_CHG_AMT=0\",\"bankAbbr\":\"\",\"mobile\":\"150****8779\",\"orderId\":\"3404ab75feb1a15d9d34cce62385a5b2\",\"payDate\":\"20160522173209\",\"accountDate\":\"20160522\",\"reserved1\":\"\",\"reserved2\":\"\",\"status\":\"SUCCESS\",\"orderDate\":\"20160522\",\"fee\":\"0\"}";
        Map<String, String> params = (Map) JSON.parseObject(ss);
        boolean verify = HebaoSubmit.verify(params);
        System.out.println(verify);
    }
}
