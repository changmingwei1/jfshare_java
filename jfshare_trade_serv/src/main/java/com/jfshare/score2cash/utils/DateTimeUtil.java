package com.jfshare.score2cash.utils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 日期处理工具类
 * 
 * @author cuijianbin
 * 
 */
public class DateTimeUtil {

	public static String FORMAT_DEFAULT_MIN = "yyyyMMddHHmmss";
	public static String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_DEFAULT_CH = "yyyy年MM月dd日 HH时mm分ss秒";
	public static String FORMAT_DEFAULT_YMD = "yyyy-MM-dd";

	public static Date current() {
		return new Date();
	}
	
	public static String getCurrentDateYMDHMS() {
		return dateToStr(current(), FORMAT_DEFAULT_MIN);
	}

	public static Integer[] getYMDHMS(Date date) {
		Calendar cal = getCalendar(date);
		return new Integer[] { cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND) };
	}

	public static Integer getWeekOfMonth(Date date) {
		Calendar cal = getCalendar(date);

		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 取本周周一的日期(周一为一周第一天)
	 * 
	 * @param date
	 * @return
	 */
	public static Date thisWeekMonday(Date date) {
		Calendar cal = getCalendar(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 返回星期数（周日返回7）
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getDayOfWeek(Date date) {
		Calendar cal = getCalendar(date);
		int re = cal.get(Calendar.DAY_OF_WEEK);
		if (re == 0) {
			return 7;
		} else {
			return re - 1;
		}
	}

	public static Date addSecond(Date date, int num) {
		return add(date, Calendar.SECOND, num);
	}

	public static Date addMinute(Date date, int num) {
		return add(date, Calendar.MINUTE, num);
	}

	public static Date addHour(Date date, int num) {
		return add(date, Calendar.HOUR_OF_DAY, num);
	}

	public static Date addDay(Date date, int num) {
		return add(date, Calendar.DAY_OF_MONTH, num);
	}

	public static Date add(Date date, int field, int num) {
		Calendar cal = getCalendar(date);
		cal.add(field, num);
		return cal.getTime();
	}

	public static Date firstOfMonth(Date date) {
		return first(date, Calendar.DAY_OF_MONTH);
	}

	public static Date lastOfMonth(Date date) {
		return last(date, Calendar.DAY_OF_MONTH);
	}

	public static Date firstOfHour(Date date) {
		return first(date, Calendar.HOUR_OF_DAY);
	}

	public static Date lastOfHour(Date date) {
		return last(date, Calendar.HOUR_OF_DAY);
	}

	public static Date first(Date date, int d) {
		if (null == date)
			return date;
		Calendar cal = getCalendar(date);
		cal.set(d, cal.getMinimum(d));
		return cal.getTime();
	}

	public static Date last(Date date, int d) {
		if (null == date)
			return date;
		Calendar cal = getCalendar(date);
		cal.set(d, cal.getMaximum(d));
		return cal.getTime();
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(date);
		return cal;
	}

	public static String dateToStrOfYmdFormat(Date date) {
		return dateToStr(date, FORMAT_DEFAULT_YMD);
	}

	public static String dateToStrOfDefaulfFormat(Date date) {
		return dateToStr(date, FORMAT_DEFAULT);
	}
	
	public static String dateToStrOfDefaulfFormat(long date) {
		return dateToStr(new Date(date), FORMAT_DEFAULT);
	}

	/**
	 * 日期转换为制定的字符串格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToStr(Date date, String format) {
		if (null == date)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date strToDateOfYmdFormat(String dateStr) {
		return strToDate(dateStr, FORMAT_DEFAULT_YMD);
	}

	public static Date strToDateOfDefaulfFormat(String dateStr) {
		return strToDate(dateStr, FORMAT_DEFAULT);
	}

	/**
	 * 字符串转换为日期类型
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date strToDate(String dateStr, String format) {
		if (null == dateStr || StringUtil.empty(dateStr))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算两日期间相差天数.
	 * 
	 * 
	 * @param d1
	 *            开始日期 日期型
	 * @param d2
	 *            结束日期 日期型
	 * @return long 天数
	 */
	public static long signDaysBetweenTowDate(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime()) / (3600 * 24 * 1000);
	}

	public static void main(String[] args) {

		System.out.println("::::" + dateToStr(new Date(), FORMAT_DEFAULT_MIN));
		System.out.println("::::" + strToDate("2011-11-17 08:53:28", FORMAT_DEFAULT));

		System.out.println("::::" + dateToStr(addDay(current(), -7), FORMAT_DEFAULT));

		Date d1 = strToDate("2012-03-04", FORMAT_DEFAULT_YMD);

		System.out.println(getWeekOfMonth(new Date()));
		System.out.println(getDayOfWeek(new Date()));
		System.out.println(getWeekOfMonth(d1));
		// System.out.println(getDayOfWeek(d1));
		// System.out.println(thisWeekMonday(d1).toLocaleString());
	}

	/**
	 * 获取2个日期("yyyy-MM-dd")之间的所有日期
	 * 
	 * @param d1
	 * @param d2
	 * @return GregorianCalendar[]
	 * @throws ParseException
	 */
	public static Calendar[] getBetweenDate(String d1, String d2) {
		List<GregorianCalendar> v = new ArrayList<GregorianCalendar>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar gc1 = new GregorianCalendar(), gc2 = new GregorianCalendar();
		try {
			gc1.setTime(sdf.parse(d1));
			gc2.setTime(sdf.parse(d2));
			do {
				GregorianCalendar gc3 = (GregorianCalendar) gc1.clone();
				v.add(gc3);
				gc1.add(Calendar.DAY_OF_MONTH, 1);
			} while (!gc1.after(gc2));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return v.toArray(new GregorianCalendar[v.size()]);
	}

	/**
	 * 获取2个日期("yyyy-MM-dd")之间的所有日期
	 * 
	 * @param d1
	 * @param d2
	 * @return GregorianCalendar[]
	 * @throws ParseException
	 */
	public static Calendar[] getBetweenDate(Date d1, Date d2) {
		List<GregorianCalendar> v = new ArrayList<GregorianCalendar>();
		Calendar gc1 = new GregorianCalendar(), gc2 = new GregorianCalendar();
		gc1.setTime(d1);
		gc2.setTime(d2);
		do {
			GregorianCalendar gc3 = (GregorianCalendar) gc1.clone();
			v.add(gc3);
			gc1.add(Calendar.DAY_OF_MONTH, 1);
		} while (!gc1.after(gc2));

		return v.toArray(new GregorianCalendar[v.size()]);
	}

	public static DateTime strToDateTime(String strTime, String format) {
		if (strTime == null || strTime.isEmpty())
			return null;
		SimpleDateFormat timeFormat = new SimpleDateFormat(format);

		Date date = null;
		try {
			date = timeFormat.parse(strTime);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}

		if (date != null)
			return new DateTime(date.getTime());
		return null;
	}

	public static DateTime strToDateTime(String strTime) {
		return strToDateTime(strTime, FORMAT_DEFAULT);
	}

	public static String DateTimeToStr(DateTime date, String format) {
		return date.toString(format);
	}

	public static String DateTimeToStr(DateTime date) {
		return date.toString(FORMAT_DEFAULT);
	}

}