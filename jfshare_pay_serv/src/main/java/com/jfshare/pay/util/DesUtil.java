package com.jfshare.pay.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;

/**
 * Created by Lenovo on 2015/12/14.
 */
public class DesUtil {
    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decryptCBC(buf, key.getBytes(), iv.getBytes());
        return new String(bt, "UTF-8");
    }

    public static String encrypt(String data, String key, String iv) throws Exception {
        if (data == null) {
            return null;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] bt = encryptCBC(data.getBytes("UTF-8"), key.getBytes(), iv.getBytes());
        return encoder.encodeBuffer(bt);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @param iv  偏移向量iv
     * @return
     * @throws Exception
     */
    private static byte[] decryptCBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        // 用密匙初始化Cipher对象
        IvParameterSpec param = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, securekey, param);

        return cipher.doFinal(data);
    }

    /**
     * 加密函数
     *
     * @param data
     *            加密数据
     * @param key
     *            密钥
     * @return 返回加密后的数据
     */
    public static byte[] encryptCBC(byte[] data, byte[] key, byte[] iv) {
        try {
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 若采用NoPadding模式，data长度必须是8的倍数
            // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

            // 用密匙初始化Cipher对象
            IvParameterSpec param = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);

            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(data);

            return encryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] s) throws Exception {
        String requestXML= "5XjbC6AJKswuM7pnCFzoM4Z24r6RvxS90HUNOPzACVGNE0FqNDGKrcokbkfJGdLC%2bw7WaQmBB99eRqyEQX1xitZnSjP89UvzmuXJ5VhD7bjDz9mbxxKFwAUh7EsdVGRtBlb%2bsBhkgAU1zrSEN5WmkkEEFM%2bEt%2fdS2s2X9xnBixVCXSgZxyOvn9RA4tK7";
        String urlDecrypt = URLDecoder.decode(requestXML, "UTF-8");
        String data = decrypt(urlDecrypt, "telefenpaytes@pay17$#3#$", "13386191");
        System.err.println("data");
    }
}
