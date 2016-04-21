package com.jfshare.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

	/**
	 * 判断文本是否有脚本注入代码
	 * @param content
	 * @return
	 */
	public static boolean scriptInjection(String content){
		// 如果字符串为null或空串，直接返回false
		if(content == null || content.length() <= 0){
			return false;
		}
		
	    // 如果包含<或者>则有注入的危险，返回true
		String regEx = "<|>"; 
	    
	    Pattern pattern = Pattern.compile(regEx);  
	    Matcher matcher = pattern.matcher(content);  
	    return matcher.find();   
	}
	
}
