package com.jfshare.ridge.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <PRE>
 * 作用:
 *       字符串操作工具类
 * 限制:
 *       无.
 * 注意事项:
 *       无.
 * 修改历史:
 * -----------------------------------------------------------------------------
 *         VERSION       DATE                BY              CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 *          1.0        2011-07-14           null              create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public class StrUtil {


	/**
	 * 高效替换字符串的方法 可以替换特殊字符的替换方法,replaceAll只能替换普通字符串,含有特殊字符的不能替换
	 * 
	 * @param strSource
	 *            用户输入的字符串
	 * @param strFrom
	 *            数据库用需要替换的字符
	 * @param strTo
	 *            需要替换的字符替换为该字符串
	 * @return
	 */
	public static String replaceStr(String strSource, String strFrom,
			String strTo) {
		if (strSource == null) {
			return null;
		}
		try {
			int i = 0;
			if ((i = strSource.indexOf(strFrom, i)) >= 0) {
				char[] cSrc = strSource.toCharArray();
				char[] cTo = strTo.toCharArray();
				int len = strFrom.length();
				StringBuilder sb = new StringBuilder();
				sb.append(cSrc, 0, i).append(cTo);
				i += len;
				int j = i;
				while ((i = strSource.indexOf(strFrom, i)) > 0) {
					sb.append(cSrc, j, i - j).append(cTo);
					i += len;
					j = i;
				}
				sb.append(cSrc, j, cSrc.length - j);
				strSource = sb.toString();
				sb = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			strSource = "";
		}
		return strSource;
	}

	/**
	 * 字符串编码转换
	 * 
	 * @param rawStr
	 *            原始字符串
	 * @param characterSet
	 *            需要转换的字符集,例如:UTF-8,GB2312,GBK
	 * @return 转换以后的字符串
	 */
	public static String convertEncode(String rawStr, String characterSet) {
		String str = null;
		try {
			str = new String(rawStr.getBytes(), characterSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}




	/**
	 * 把字符串中的大写字母全部转换为小写字母
	 * 
	 * @param rawStr
	 *            原始字符串
	 * @return 过滤转换后的字符串
	 */
	public static String convertUpper2Low(String rawStr) {
		StringBuilder lastStr = new StringBuilder();
		if (rawStr != null) {
			for (char ch : rawStr.toCharArray()) {
				if (ch >= 65 && ch <= 90) { // 如果是大写字母就转化成小写字母
					ch = (char) (ch + 32);
				}
				lastStr.append(ch);
			}
		} else {
			return "";
		}
		return lastStr.toString();
	}



	/**
	 * 验证是否只是数字和字母,用来排除非法字符,如果是验证数字则正则为: "[0-9]*"
	 * 
	 * @param str
	 * @return
	 */
	public static Boolean isLetterAndNumber(String str) {
		String reg = "[0-9a-zA-Z]+";
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}

	/**
	 * 验证是否只是数字
	 * 
	 * @param str
	 * @return
	 */
	public static Boolean isNumber(String str) {
		String reg = "[0-9]*";
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}
	
	/**
	 * 验证是否包含数字
	 * @param str
	 * @return
	 */
	public static Boolean isContainNumber(String str){
		String reg = ".*\\d+.*";
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}
	
	public static void main(String[] args) throws Exception{
		String str = "/instrument/instrumentManage/querySimpleInstrumentList/1,2,3,4.html";
		System.out.println(StrUtil.isContainNumber(str));
	}


	/**
	 * Java去除字符串中的空格,回车,换行符,制表符
	 * 
	 * @param strSource
	 * @return
	 */
	public static String replaceBlank(String strSource) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(strSource);
		String afterStr = m.replaceAll("");
		return afterStr;
	}



	/**
	 * 将字符串数组转换为字符串 格式为: sdf,123,fgf
	 * 
	 * @param strs
	 * @return
	 */
	public static String arrayToString(String[] strs) {
		String str = "";
		if (strs != null && strs.length != 0) {
			str = strs[0];
			for (int i = 1; i < strs.length; i++) {
				str += "," + strs[i];
			}
		}
		return str;
	}

	
	
}
