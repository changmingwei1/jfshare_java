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
        String requestXML= "5XjbC6AJKswuM7pnCFzoM4Z24r6RvxS90HUNOPzACVGNE0FqNDGKrcokbkfJGdLC%2bw7WaQmBB99eRqyEQX1xitZnSjP89UvzmuXJ5VhD7bjDz9mbxxKFwAUh7EsdVGRtBlb%2bsBhkgAU1zrSEN5WmkkEEFM%2bEt%2fdS2s2X9xnBixVCXSgZxyOvn9RA4tK7";
        String urlDecrypt = URLDecoder.decode(requestXML, "utf-8");
        System.out.println("CBC加密解密");
        String str5 = des3EncodeCBC("telefenpaytes@pay17$#3#$", "13386191", "12345");
        String str6 = des3DecodeCBC("telefenpaytes@pay17$#3#$", "13386191", urlDecrypt);
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
}
