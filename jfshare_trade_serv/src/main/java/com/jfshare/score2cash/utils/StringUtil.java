package com.jfshare.score2cash.utils;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author cuijianbin
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StringUtil {
	
	static Pattern p=Pattern.compile("^\\s*$",Pattern.CASE_INSENSITIVE);
	
	public static boolean empty(String str){
		if(null == str) return true;
        //Pattern.CASE_INSENSITIVE 忽略大小  
        Matcher m=p.matcher(str);
        return m.matches();
	}
	
	public static String getLastWord(String str, String regex){
		String[] strs = str.split(regex);
		if(strs.length <= 0) return "";
		return strs[strs.length - 1];
	}
	
	public static String join(String str1, String str2, String split){
		StringBuilder buf = new StringBuilder();
		if(str1 == null || str2 == null || split == null)
			return "";
		buf.append(str1).append(split).append(str2);
		return buf.toString();
	}
	
	public static String join(String str1, String str2, String str3, String split){
		StringBuilder buf = new StringBuilder();
		if(str1 == null || str2 == null || str3 == null || split == null)
			return "";
		buf.append(str1).append(split).append(str2).append(split).append(str3);
		return buf.toString();
	}
	
	public static String join(String str1, String str2, String str3, String str4, String split){
		StringBuilder buf = new StringBuilder();
		if(str1 == null || str2 == null || str3 == null || str4 == null || split == null)
			return "";
		buf.append(str1).append(split).append(str2).append(split).append(str3).append(split).append(str4);
		return buf.toString();
	}
	
	public static String removeEndLetter(String str){
		if(null == str || str.length() <= 0) return "";
		return str.substring(0, str.length() - 1);
	}
	
	public static String join(List list, String split){
		StringBuilder buf = new StringBuilder();
		for(Object str : list){
			if(null != str && !("").equals(str)){
				buf.append(str).append(split);
			}
		}
		return StringUtil.removeEndLetter(buf.toString());
	}
	
	public static String join(Object[] ary, String split){
		if(null == ary) return "";
		StringBuilder buf = new StringBuilder();
		for(int i = 0; i < ary.length; i++){
			if(null != ary[i] && !("").equals(ary[i])){
				buf.append(ary[i]).append(split);
			}
			
		}
		
		return StringUtil.removeEndLetter(buf.toString());
	}
	
	/**
	 * 首字母转换为大写
	 * @param str
	 * @return
	 */
	public static String firstLetterUpcase(String str){
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * 首字母转换为小写
	 * @param str
	 * @return
	 */
	public static String firstLetterLowerCase(String str){
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	
	public static String listToStr(List list){
		StringBuilder buffer = new StringBuilder("[");
		for(int i = 0; i < list.size(); i++){
			Object sub = list.get(i);
			if(sub instanceof Map)
				buffer.append(mapToStr((Map)sub));
			
			if(i < list.size() - 1)
				buffer.append(",");
		}
		
		buffer.append("]");
		return buffer.toString();
	}
	
	/**
	 * map to string
	 * @param map
	 * @return
	 */
	public static String mapToStr(Map<String, Object> map){
		StringBuilder buffer = new StringBuilder("{");
		int i = 0;
		for(String key : map.keySet()){
			buffer.append("\"").append(key).append("\":").append(wrapStr(map.get(key)));
			if(i++ < map.keySet().size() - 1)
				buffer.append(",");
		}
		buffer.append("}");
		//System.out.println("json:::" + buffer.toString());
		return buffer.toString();
	}
	
	
	
	/**
	 * 对基础数据类型进行字符串包装
	 * @param obj
	 * @return
	 */
	public static String wrapStr(Object obj){
		if(obj instanceof Integer)
			return obj + "";
		else if(obj instanceof Double)
			return obj + "";
		else if(obj instanceof String)
			return "\"" + obj + "\"";
		else if(obj instanceof Date)
			return "\"" + DateTimeUtil.dateToStr((Date)obj, DateTimeUtil.FORMAT_DEFAULT) + "\"";
		else
			return obj + "";
	}
	
	public static String filerHtml(String content){
		if(!StringUtil.empty(content)){
			content = content.replaceAll("<[.[^<]]*>", " ");
			content = content.replaceAll("&\\w{4};", " ");
			content = content.replaceAll("\\\\w", " ");
		}
		return content;
	}
	
	public static String htmlEscape(String content){
		if(!StringUtil.empty(content)){
			content = content.replaceAll("<", "&lt;");
			content = content.replaceAll(">", "&gt;");
			content = content.replaceAll("\"", "&quot;");
		}
		return content;
	}
	
	
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		return sb.toString();
	}
	
	/**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
	public static String ToDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);

			}
		}
		String returnString = new String(c);
		return returnString;
	}
	
	/**
	 * 半角转全角
	 * 
	 * @param input
	 *            String.
	 * @return 全角字符串.
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}
	
	public static boolean checkPhone(String phone) {
		Pattern pattern = Pattern
				.compile("^13\\d{9}||15[0-9]\\d{8}||14[7]\\d{8}||18[0-9]\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(str);
		if (match.matches()) {
			return true;
		} 
		return false;
	}
	
}
