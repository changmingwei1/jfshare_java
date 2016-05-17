package com.jfshare.product.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;

/**
 * Created by Lenovo on 2016/5/16.
 */
public class CodeUtil {


    public static String getMD5(String str) {
        String code = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            code = hexValue(md5.digest(str.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String getMD5Upper(String str) {
        String code = getMD5(str);
        return code == null ? null : code.toUpperCase();
    }


    public static String getURLDecoder(String str) {
        String code = null;
        try {
            code = URLDecoder.decode(str, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    private static String hexValue(byte[] md5Bytes) {
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


}
