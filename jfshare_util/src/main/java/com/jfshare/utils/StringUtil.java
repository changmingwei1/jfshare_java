package com.jfshare.utils;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
	private transient static Logger log = Logger.getLogger(StringUtil.class);
    public final static String Empty = "";

    public static boolean isNullOrEmpty(Object input) {
        return input == null || input.toString().equalsIgnoreCase("null") || input.toString().trim().isEmpty();
    }

	 public static String delHTMLTag(String htmlStr){ 
	     if (isNullOrEmpty(htmlStr)) return Empty;
	     
	     String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
	     String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>";
	     String regEx_html="<[^>]+>";
	      
	     Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	     Matcher m_script=p_script.matcher(htmlStr); 
	     htmlStr=m_script.replaceAll("");
	      
	     Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	     Matcher m_style=p_style.matcher(htmlStr); 
	     htmlStr=m_style.replaceAll("");
	      
	     Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	     Matcher m_html=p_html.matcher(htmlStr); 
	     htmlStr=m_html.replaceAll("");

	     return htmlStr.trim();
	 } 

    public static String highLightKey(String src, String keyWord){
        if (isNullOrEmpty(src)) return Empty; 
        
        if(!isNullOrEmpty(keyWord)){
            return src.replaceAll(keyWord, "<font style='color: red;'>" + keyWord + "</font>");
        }
        
        return src;
    }

    public static String transactSQLInjection(String sqlText) {  
        return sqlText.replaceAll(".*([';]+|(--)+).*", " ");  
    }
}
