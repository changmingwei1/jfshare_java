package com.jfshare.score;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


import java.security.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import org.apache.commons.codec.digest.Md5Crypt;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jfshare.score.model.TbScoreTrade;


public class test {
	public static boolean isMobile(String str) {    
  	  Pattern p = null;   
  	  Matcher m = null;   
  	  boolean b = false;    
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号   
        m = p.matcher(str);   
	      b = m.matches();    
	      return b;       
	}
	  public static void main(String[] args) throws ParseException    {        
//		  JSONObject json = new JSONObject();
//			json.put("AppCode", "1");
//			json.put("RequestDate", "1");
//			json.put("Sign", "1");
//			json.put("SpID", "");
//			json.put("OutOrderID", "");
//			json.put("DeviceNo", "");
//			json.put("DeviceType", "");
//			json.put("ProvinceID", "");
//			json.put("CustID", "");
//			json.put("Num", "");
//			json.put("Remark", "");
//			json.put("ActiveID", "");
//			json.put("ExpTime", "");
//			String postxml =json.toString();
//			System.out.println(postxml);
//			
//			JSONObject json2 = (JSONObject) JSONObject.parse(postxml);
//			System.out.println("---------------------------------------------------");
//			System.out.println(json2.getString("AppCode"));
//			Date date = new Date();   
//		    SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHHmmss");//24小时制   
//		    String lgTime = sdformat.format(date);
//		    System.out.println(lgTime);    
		// 指定好一个日期格式的字符串   
//		  String expTime ="20150517010101";
		  DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");
		  System.out.println(DateTime.now().toString(format));
		  TbScoreTrade trade = new TbScoreTrade();
		  trade.setAmount(1);
		  trade.setTradeTime(DateTime.parse(DateTime.now().toString(format)));
		  
		  Gson gson = new Gson();
		  String json = gson.toJson(trade);
		  System.out.println(json);
//		  String expTime ="20150517010101";
//		  System.out.println(new DateTime(expTime));;
//		  
		  //String.format("%s%s%s%s%s", 336, mobile, email, deviceId,time);            
		  
	  }
}
