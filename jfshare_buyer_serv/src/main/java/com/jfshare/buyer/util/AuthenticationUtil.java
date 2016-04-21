package com.jfshare.buyer.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 密码加密
 * 密码验证
 * 生成TOKEN， PPINFO
 * 鉴权验证
 * @author zhaoshenghai
 */
public class AuthenticationUtil {
	private final static String KEY = "jfshareP";
//	private final static String IV = "";
	
	/**
	 * spa512 加密
	 * @param password: MD5后的密码
	 * @return
	 */
	public static String spa512Encode(String password) throws Exception {
		String newPassword = disorder(password);
		
		String ciphertext =  encryptDES(newPassword.getBytes("UTF-8"), KEY.getBytes("UTF-8"), KEY.getBytes("UTF-8"));
		
		return ciphertext;
	}
	
	/**
	 * 生成TOKEN
	 * @param userId：用户ＩＤ
	 * @param mobile：用户手机号
	 * @param email：用户电子邮箱
	 * @param deviceId：用户设备ＩＤ
	 * @return
	 * @throws Exception 
	 */
	public static String getToken(String userId, String mobile, String email, String deviceId) throws Exception {
		String md5Str = MD5Util.md5Encode(String.format("%s%s%s%s", userId, mobile, email, deviceId));
		String disorderText = disorder(md5Str);
		String ciphertext =  encryptDES(disorderText.getBytes("UTF-8"), KEY.getBytes("UTF-8"), KEY.getBytes("UTF-8")); 
		
		return ciphertext;
	}
	/**
	 * 生成PPINFO
	 * @param userId：用户ID
	 * @param mobile：用户手机好
	 * @param email：用户Email
	 * @return 
	 */
	public static String getPPInfo(String userId, String mobile, String email) throws Exception {
		String base64Str = Base64.encodeBase64String(String.format("%s%s%s", userId, mobile, email).getBytes());
		
		return disorder(base64Str);
	}
	
	public static boolean tokenVerification(String Token, String ppInfo, String deviceId) throws Exception{
		String str = new String(Base64.decodeBase64(restore(ppInfo)), "UTF-8");
		String md5Str = MD5Util.md5Encode(str + deviceId);
		String disorderText = disorder(md5Str);
		String ciphertext =  encryptDES(disorderText.getBytes("UTF-8"), KEY.getBytes("UTF-8"), KEY.getBytes("UTF-8")); 
		if(Token.equals(ciphertext)){
			return true;
		} else {
			return false;
		}
	}
	
	private static String restore(String pwd) {
		String[] parts = pwd.split("%");
		return parts[1] + parts[0];
	}
	
	private static String disorder(String password) {
		int length = password.length();
		
		String partA = password.substring(0, length / 2);
		String partB = password.substring(length / 2);
		
		String newPassword = partB + "%" + partA;
		return newPassword;
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
    private static String encryptDES(byte[] data, byte[] key, byte[] iv) {
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

            return Base64.encodeBase64String(encryptedData);
        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }
        return null;
    }
    
//    /**
//     * Description 根据键值进行解密
//     *
//     * @param data
//     * @param key  加密键byte数组
//     * @param iv  偏移向量iv
//     * @return
//     * @throws Exception
//     */
//    private static byte[] decryptCBC(byte[] data, byte[] key, byte[] iv) throws Exception {
//        // 生成一个可信任的随机数源
//        SecureRandom sr = new SecureRandom();
//
//        // 从原始密钥数据创建DESKeySpec对象
//        DESKeySpec dks = new DESKeySpec(key);
//
//        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//        SecretKey securekey = keyFactory.generateSecret(dks);
//
//        // Cipher对象实际完成解密操作
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//
//        // 用密匙初始化Cipher对象
//        IvParameterSpec param = new IvParameterSpec(iv);
//        cipher.init(Cipher.DECRYPT_MODE, securekey, param);
//
//        return cipher.doFinal(data);
//    }
    
    public static void main(String[] args) throws Exception{
    	String pwd = MD5Util.md5Encode("123456");
    	System.out.println("md5 hou:" + pwd);
    	String pwdEn = spa512Encode(pwd);
    	System.out.println("spa hou:" + pwd);
    	
    	String pwd2 = MD5Util.md5Encode("123456");
    	System.out.println("md5 hou:" + pwd);
    	String pwdEn2 = spa512Encode(pwd);
    	System.out.println("spa hou:" + pwd);
    	
    	if(pwdEn2.equals(pwdEn)){
    		System.out.println("true" + pwd);
    	} else {
    		System.out.println("false" + pwd);
    	}
    	
    	String token = getToken("1", "15010938534", "123@132.com", "aaaaaaaaaa");
    	String ppInfo = getPPInfo("1", "15010938534", "123@132.com");
    	
    	System.out.println("token:" + token + "  ppinfo:" + ppInfo);
    	
    	boolean b =  tokenVerification(token, ppInfo, "aaaaaaaaaa");
    	System.out.println("resut:" + b);
    }
    
}
