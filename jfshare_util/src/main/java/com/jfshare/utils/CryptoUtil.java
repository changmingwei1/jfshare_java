package com.jfshare.utils;

import com.sun.crypto.provider.SunJCE;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author lilx
 * @createTime 2015-11-18
 * @version v1.0
 * @remark 加解密工具
 **/
@SuppressWarnings("restriction")
public final class CryptoUtil {
    private static String strkey = "jfshare";
    private static char srcChars[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    private static char desChars[] = { 'G', 'S', '9', 'A', '2', '4', 'T', 'B', 'M', 'R', '7', 'O', 'P', '1', 'I', 'L',
            '5', 'V', 'C', '8', 'K', 'N', 'X', 'J', 'Q', 'F', '0', 'D', 'Z', 'Y', '3', 'E', 'H', '6', 'W', 'U' };

    /**
     * 加密字符串
     * 
     * @param str2Encrypt
     * @return
     */
    public static String encryptString(String str2Encrypt) {
        if (StringUtil.isNullOrEmpty(str2Encrypt))
            return null;
        try {
            Security.addProvider(new SunJCE());
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(strkey));
            byte[] src = str2Encrypt.getBytes("UTF-8");
            byte[] enc = cipher.doFinal(src);
            return Base64.encodeBase64String(enc);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return null;
    }

    /**
     * 解密字符串
     * 
     * @param str2Decrypt
     * @return
     */
    public static String decryptString(String str2Decrypt) {
        if (StringUtil.isNullOrEmpty(str2Decrypt))
            return null;
        try {
            byte[] enc = Base64.decodeBase64(str2Decrypt);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, getKey(strkey));
            byte[] dec = cipher.doFinal(enc);
            return new String(dec, "UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * 生成密钥Key
     * 
     * @param srckey
     * @return
     */
    private static SecretKey getKey(String srckey) {
        try {
            Security.addProvider(new SunJCE());
            KeyGenerator keygen = KeyGenerator.getInstance("DESede");

//解决linux系统成功key变化，无法解密
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(srckey.getBytes("UTF-8"));
            keygen.init(random);
            return keygen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据库密码加强版MD5加密
     */
    public static String encryptDBPwd(String pwd2Encrypt) {
        String str = getMD5(pwd2Encrypt);
        if (str == null)
            return null;
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < str.length(); j++) {
                str = replaceByIndex(str, i, mapChar(str.charAt(i)));
            }
        }

        return str;
    }

    public static String getMD5(String str2Encrypt) {
        return getBase64Digest(str2Encrypt, "MD5");
    }
    
    public static String getSHA1(String str2Encrypt) {
        return getBase64Digest(str2Encrypt, "SHA-1");
    }
    
    /**
     * 通用加密算法
     * @param str2Encrypt 待加密串
     * @param digestInc 加密算法名，目前支持SHA-256、SHA-512、SHA、SHA-384、SHA1、MD5、SHA-1、MD2
     * @return 算法对应的Base64编码的加密串
     */
    private static String getBase64Digest(String str2Encrypt, String digestInc) {
        if (str2Encrypt == null)
            return null;
        try {
            MessageDigest dig = MessageDigest.getInstance(digestInc);
            byte[] src = str2Encrypt.getBytes("UTF-8");
            dig.update(src);
            byte[] enc = dig.digest();
            return Base64.encodeBase64String(enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String replaceByIndex(String str, int index, char desChar) {
        char tmp[] = str.toCharArray();
        tmp[index] = desChar;
        str = String.valueOf(tmp);
        return str;
    }

    private static char mapChar(char c) {
        for (int i = 0; i < srcChars.length; i++) {
            if (c == srcChars[i]) {
                c = desChars[i];
                break;
            }
        }
        return c;
    }

    /***
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");

            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        System.out.println(encryptString("order"));
        System.out.println(decryptString("fvBLEJEHNOw="));

        System.out.println(encryptString("weixinnotify"));
        System.out.println(decryptString("iLv6mU/tYV1Vu9zhav4XcA=="));


        String str = new String("amigoxiexiexingxing");
        System.out.println("原始：" + str);
        System.out.println("MD5后：" + md5Encode(str));
    }
}
