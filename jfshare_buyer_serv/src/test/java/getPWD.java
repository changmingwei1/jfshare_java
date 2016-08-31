import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class getPWD {
	private final static String KEY = "jfshareP";
	private static char srcChars[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
	        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static char desChars[] = { 'G', 'S', '9', 'A', '2', '4', 'T', 'B', 'M', 'R', '7', 'O', 'P', '1', 'I', 'L',
	        '5', 'V', 'C', '8', 'K', 'N', 'X', 'J', 'Q', 'F', '0', 'D', 'Z', 'Y', '3', 'E', 'H', '6', 'W', 'U' };
	public static void main(String agrs[]){
		try {
			System.out.println(encryptDBPwd(spa512Encode("liuyachao122525")));
			System.err.println("sldkjfsld@slkdjf".contains("@"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public static String spa512Encode(String password) throws Exception {
		String newPassword = disorder(password);
		
		String ciphertext =  encryptDES(newPassword.getBytes("UTF-8"), KEY.getBytes("UTF-8"), KEY.getBytes("UTF-8"));
		
		return ciphertext;
	}

	private static String disorder(String password) {
		int length = password.length();
		
		String partA = password.substring(0, length / 2);
		String partB = password.substring(length / 2);
		
		String newPassword = partB + "%" + partA;
		return newPassword;
	}

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
}
