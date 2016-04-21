package com.jfshare.pay.util.alipay.sign;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

/** 
* 功能：支付宝MD5签名处理核心文件，不需要修改
* 版本：3.3
* 修改日期：2012-08-17
* 说明：
* 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
* 该代码仅供学习和研究支付宝接口使用，只是提供一个
* */

public class MD5 {

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }


    public static String cryptMd5(String source, String key)
    {
        byte k_ipad[] = new byte[64];
        byte k_opad[] = new byte[64];
        byte keyb[];
        byte value[];
        try
        {
            keyb = key.getBytes("UTF-8");
            value = source.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            keyb = key.getBytes();
            value = source.getBytes();
        }
        Arrays.fill(k_ipad, keyb.length, 64, (new Integer(54)).byteValue());
        Arrays.fill(k_opad, keyb.length, 64, (new Integer(92)).byteValue());
        for (int i = 0; i < keyb.length; i++)
        {
            k_ipad[i] = (byte)(keyb[i] ^ 0x36);
            k_opad[i] = (byte)(keyb[i] ^ 0x5c);
        }

        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
        md.update(k_ipad);
        md.update(value);
        byte dg[] = md.digest();
        md.reset();
        md.update(k_opad);
        md.update(dg, 0, 16);
        dg = md.digest();
        return toHex(dg);
    }

    public static String toHex(byte input[])
    {
        if (input == null)
            return null;
        StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++)
        {
            int current = input[i] & 0xff;
            if (current < 16)
                output.append("0");
            output.append(Integer.toString(current, 16));
        }

        return output.toString();
    }

}