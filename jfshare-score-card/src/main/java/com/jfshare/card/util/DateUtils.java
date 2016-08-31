package com.jfshare.card.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class DateUtils {
	public static String getNowDateTimeString() {
		Date date = new Date();
		DateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf3.format(date);
	}
	
	public static long getNowDateTimeMillis(){
		Date date = new Date();
		return date.getTime();
	}
	
	public static String getTomorrowDayTime() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(date);
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static long getTimeMillisByDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date str = null;
		try {
			str = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str.getTime();
	}
	
	public static String getTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
		String str = sdf.format(date);

		return str;
	}

	public static String getRandNumber(int num) {
		String str = "";
		Random random=new Random();
		for (int i = 0; i < num; i++) {
			str+=random.nextInt(10);
		}
		return str;
	}
}
