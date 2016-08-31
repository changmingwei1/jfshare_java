package com.jfshare.buyer.util;

import com.jfshare.buyer.model.TbUser;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.CryptoUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by lenovo on 2015/11/19.
 */
public class BuyerUtil {
    public static TbUser buyer2TbUser(Buyer buyer) {
        TbUser user = new TbUser();
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(buyer);
        BeanUtil.fillBeanData(user, stringObjectMap);
        user.setUserId(buyer.getUserId());
        user.setUserName(buyer.getUserName());
        if (!StringUtil.isNullOrEmpty(buyer.getPwdEnc())) {
            user.setPwdEnc(CryptoUtil.encryptDBPwd(buyer.getPwdEnc().trim()));
        }
        user.setBirthday(DateUtils.strToDateTime(buyer.getBirthday()));
        return user;
    }

    public static Buyer tbUser2Buyer(TbUser tbUser) {
        Buyer buyer = new Buyer();
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbUser);
        BeanUtil.fillBeanData(buyer, stringObjectMap);
        buyer.setUserId(tbUser.getUserId());
        buyer.setUserName(tbUser.getUserName());

        return buyer;
    }
    
    /***
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

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
    }
    
    /**  
	   * 手机号验证   
	   * @param  str  
	   * @return 验证通过返回true  
	   */  
  public static boolean isMobile(String str) {    
  	  Pattern p = null;   
  	  Matcher m = null;   
  	  boolean b = false;    
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号   
        m = p.matcher(str);   
	      b = m.matches();    
	      return b;       
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
	public static void main(String args[]) throws UnsupportedEncodingException{
		byte[] key = "telefenpaytes@pay17$#3#$".getBytes("ISO-8859-1");
		byte[] keyiv = "13386191".getBytes("ISO-8859-1");
		byte[] data = string2Unicode("changmingwei好帅").getBytes("UTF-8");
		System.out.println(key);
		System.out.println(keyiv);
		try {
			byte[] a = des3EncodeCBC(key,keyiv,data);
			System.out.println(des3EncodeCBC(key,keyiv,data));
			byte[] b = des3DecodeCBC(key,keyiv,des3EncodeCBC(key,keyiv,data));
			System.out.println(unicode2String(new String(b,"ISO-8859-1")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(string2Unicode("changmingwei"));
//		System.out.println(unicode2String(string2Unicode("changmingwei")));
	}
	
}
