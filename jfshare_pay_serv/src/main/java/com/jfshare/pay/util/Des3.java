package com.jfshare.pay.util;

import java.net.URLDecoder;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Des3 {

    public static void main(String[] args) throws Exception {
        String requestXML= "\\u3c\\u41\\u63\\u63\\u65\\u70\\u74\\u52\\u65\\u71\\u75\\u65\\u73\\u74\\u3e\\u3c\\u41\\u70\\u70\\u49\\u44\\u3e\\u42\\u46\\u5a\\u58\\u3c\\u2f\\u41\\u70\\u70\\u49\\u44\\u3e\\u3c\\u53\\u50\\u49\\u44\\u3e\\u31\\u37\\u31\\u34\\u34\\u35\\u3c\\u2f\\u53\\u50\\u49\\u44\\u3e\\u3c\\u52\\u65\\u71\\u75\\u65\\u73\\u74\\u4e\\u6f\\u3e\\u32\\u30\\u31\\u36\\u30\\u36\\u30\\u37\\u31\\u33\\u34\\u32\\u31\\u35\\u38\\u33\\u37\\u35\\u38\\u32\\u38\\u39\\u3c\\u2f\\u52\\u65\\u71\\u75\\u65\\u73\\u74\\u4e\\u6f\\u3e\\u3c\\u53\\u70\\u4f\\u72\\u64\\u65\\u72\\u49\\u44\\u3e\\u32\\u30\\u31\\u36\\u30\\u36\\u30\\u37\\u31\\u33\\u34\\u32\\u31\\u35\\u3c\\u2f\\u53\\u70\\u4f\\u72\\u64\\u65\\u72\\u49\\u44\\u3e\\u3c\\u50\\u61\\u79\\u43\\u6f\\u6e\\u74\\u65\\u6e\\u74\\u3e\\u31\\u31\\u3c\\u2f\\u50\\u61\\u79\\u43\\u6f\\u6e\\u74\\u65\\u6e\\u74\\u3e\\u3c\\u42\\u61\\u6e\\u6b\\u49\\u44\\u3e\\u3c\\u2f\\u42\\u61\\u6e\\u6b\\u49\\u44\\u3e\\u3c\\u50\\u72\\u6f\\u76\\u69\\u6e\\u63\\u65\\u49\\u44\\u3e\\u31\\u35\\u3c\\u2f\\u50\\u72\\u6f\\u76\\u69\\u6e\\u63\\u65\\u49\\u44\\u3e\\u3c\\u44\\u65\\u76\\u69\\u63\\u65\\u4e\\u6f\\u3e\\u31\\u38\\u39\\u37\\u39\\u31\\u37\\u37\\u31\\u36\\u35\\u3c\\u2f\\u44\\u65\\u76\\u69\\u63\\u65\\u4e\\u6f\\u3e\\u3c\\u44\\u65\\u76\\u69\\u63\\u65\\u54\\u79\\u70\\u65\\u3e\\u37\\u3c\\u2f\\u44\\u65\\u76\\u69\\u63\\u65\\u54\\u79\\u70\\u65\\u3e\\u3c\\u4f\\u72\\u64\\u65\\u72\\u50\\u72\\u69\\u63\\u65\\u3e\\u31\\u30\\u30\\u30\\u3c\\u2f\\u4f\\u72\\u64\\u65\\u72\\u50\\u72\\u69\\u63\\u65\\u3e\\u3c\\u50\\u61\\u79\\u50\\u6f\\u69\\u6e\\u74\\u3e\\u31\\u30\\u30\\u3c\\u2f\\u50\\u61\\u79\\u50\\u6f\\u69\\u6e\\u74\\u3e\\u3c\\u50\\u61\\u79\\u4d\\u6f\\u6e\\u65\\u79\\u3e\\u35\\u30\\u30\\u3c\\u2f\\u50\\u61\\u79\\u4d\\u6f\\u6e\\u65\\u79\\u3e\\u3c\\u52\\u65\\u71\\u75\\u65\\u73\\u74\\u54\\u69\\u6d\\u65\\u3e\\u32\\u30\\u31\\u36\\u30\\u36\\u30\\u37\\u31\\u33\\u34\\u32\\u31\\u35\\u3c\\u2f\\u52\\u65\\u71\\u75\\u65\\u73\\u74\\u54\\u69\\u6d\\u65\\u3e\\u3c\\u41\\u63\\u63\\u65\\u73\\u73\\u43\\u68\\u61\\u6e\\u6e\\u65\\u6c\\u3e\\u57\\u45\\u42\\u3c\\u2f\\u41\\u63\\u63\\u65\\u73\\u73\\u43\\u68\\u61\\u6e\\u6e\\u65\\u6c\\u3e\\u3c\\u53\\u69\\u67\\u6e\\u3e\\u62\\u35\\u39\\u39\\u66\\u61\\u38\\u36\\u31\\u66\\u33\\u38\\u37\\u62\\u36\\u30\\u61\\u61\\u39\\u38\\u66\\u38\\u31\\u35\\u34\\u63\\u65\\u62\\u38\\u36\\u33\\u34\\u3c\\u2f\\u53\\u69\\u67\\u6e\\u3e\\u3c\\u2f\\u41\\u63\\u63\\u65\\u70\\u74\\u52\\u65\\u71\\u75\\u65\\u73\\u74\\u3e";
        System.out.println("CBC加密解密");
        String str5 = des3EncodeCBC("telefenpaytes@pay17$#3#$", "13386191", requestXML);
        String str6 = des3DecodeCBC("telefenpaytes@pay17$#3#$", "13386191", str5);
        System.out.println(str5);
        System.out.println(str6);
    }

    public static String des3EncodeCBC(String key, String keyiv, String data) throws Exception {
        byte[] str5 = des3EncodeCBC(key.getBytes(), keyiv.getBytes(), data.getBytes());
        return new BASE64Encoder().encode(str5);
    }

    public static String des3DecodeCBC(String key, String keyiv, String str) throws Exception {
        byte[] data = des3DecodeCBC(key.getBytes(), keyiv.getBytes(), new BASE64Decoder().decodeBuffer(str));
        return new String(data);
    }

    /**
     * ECB加密,不要IV
     * 
     * @param key 密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeECB(byte[] key, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    /**
     * ECB解密,不要IV
     * 
     * @param key 密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] ees3DecodeECB(byte[] key, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    /**
     * CBC加密
     * 
     * @param key 密钥
     * @param keyiv IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    /**
     * CBC解密
     * 
     * @param key 密钥
     * @param keyiv IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }


    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }
}
