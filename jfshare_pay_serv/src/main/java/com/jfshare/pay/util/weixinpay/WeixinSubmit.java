package com.jfshare.pay.util.weixinpay;

import com.jfshare.pay.util.alipay.sign.MD5;
import com.jfshare.pay.util.alipay.util.AlipayCore;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class WeixinSubmit {
	
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(WeixinConfig.sign_type.equals("MD5") ) {
        	mysign = MD5.sign(prestr, "&key=" + WeixinConfig.key, WeixinConfig.input_charset).toUpperCase();
        }
        return mysign;
    }
	
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
//        sPara.put("sign_type", WeixinConfig.sign_type);

        return sPara;
    }

    /**
     * map to xml string
     * @param map
     * @return
     */
    public static String map2XmlStr(Map<String, String> map) {
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("xml");
        for (String obj : map.keySet()) {
            Element keyElement = nodeElement.addElement(obj);
            keyElement.setText(map.get(obj));
        }
        return document.getRootElement().asXML();
//        return doc2String(document);
    }

    public static Map<String, String> xmlStr2Map(String xml) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            Document document = DocumentHelper.parseText(xml);
            Element nodeElement = document.getRootElement();
            List node = nodeElement.elements();
            for (Iterator it = node.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
                map.put(elm.getName(), elm.getText());
                elm = null;
            }
            node = null;
            nodeElement = null;
            document = null;
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doc2String(Document document) {
        String s = "";
        try {
            // 使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用UTF-8编码
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

    public static boolean verify(Map<String, String> params, Map<String, String> signParams) {
        String responseTxt = "false";
//        if(params.get("notify_id") != null) {
//            String notify_id = params.get("notify_id");
//            responseTxt = verifyResponse(notify_id);
//        }
        if (params.get("appid") != null && params.get("appid").equals(WeixinConfig.appid)) {
            responseTxt = "true";
        }

        String sign = "";
        if(params.get("sign") != null) {sign = params.get("sign");}
        boolean isSign = sign.equals(signParams.get("sign"));

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
}
