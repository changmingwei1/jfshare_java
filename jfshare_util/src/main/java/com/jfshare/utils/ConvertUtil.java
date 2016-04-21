package com.jfshare.utils;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lilx
 * @createTime 2013-8-1
 * @version v1.0
 * @remark 数据类型转换工具
 **/
public final class ConvertUtil {
    public final static String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static int getInt(Object item) {
        return getInt(item, Integer.MIN_VALUE);
    }

    /**
     * @param item 待转换对象
     * @param defValue 转换失败时默认值
     * @return
     */
    public static int getInt(Object item, int defValue) {
        try {
            return Integer.parseInt(item.toString());
        } catch (Exception e) {
            return defValue;
        }
    }

    public static long getLong(Object item) {
        return getLong(item, Long.MIN_VALUE);
    }

    /**
     * @param item 待转换对象
     * @param defValue 转换失败时默认值
     * @return
     */
    public static long getLong(Object item, long defValue) {
        try {
            return Long.parseLong(item.toString());
        } catch (Exception e) {
            return defValue;
        }
    }
    
    public static double getDouble(Object item) {
        return getDouble(item, Double.MIN_VALUE);
    }
    
    /**
     * 
     * @param item 待转换对象
     * @param defValue 转换失败时默认值
     * @return
     */
    public static double getDouble(Object item, double defValue) {
        try {
            return Double.parseDouble(item.toString());
        } catch (Exception e) {
            return defValue;
        }
    }
    
    public static String getString(Object item) {
        try {
            return item.toString().trim();
        } catch (Exception e) {
            return StringUtil.Empty;
        }
    }
    
    /**
     * @param item 待转换对象
     * @param defValue 转换失败时默认值
     * @return
     */
    public static String getString(Object item, String defValue) {
        try {
            return StringUtil.isNullOrEmpty(item) ? defValue : item.toString();
        } catch (Exception e) {
            return defValue;
        }
    }
    
    /**
     * 增强版转换String
     * 处理boolean转"1,0", "null"转空
     * @param item
     * @return
     */
    public static String getStringPlus(Object item) {
        try {
        	String itemPlus = item.toString().trim();
        	if (itemPlus.equalsIgnoreCase("true")) {
        	    return "1";
        	}
        	if (itemPlus.equalsIgnoreCase("false")) {
        		return "0";
        	}
        	if (itemPlus.equalsIgnoreCase("null")) {
        	    return StringUtil.Empty;
        	}
            return itemPlus;
        } catch (Exception e) {
            return StringUtil.Empty;
        }
    }

    public static boolean getBoolean(Object item) {
        try {
            return Boolean.parseBoolean(item.toString()) || getString(item).equals("1");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取金额，默认保留4位小数,四舍五入
     * 万元
     * @param amount
     * @return
     */
    public static BigDecimal getAmount(Object amount) {
        return getAmount(amount, 4);
    }

    /**
     * 获取金额，保留2位小数,四舍五入
     * 元
     * @param amount
     * @return
     */
    public static BigDecimal getAmount2(Object amount) {
        return getAmount(amount, 2);
    }

    /**
     * 获取金额通用方法,四舍五入
     * 
     * @param amount
     * @param dotScale
     *            保留的小数位数
     * @return 转换失败则返回零.dotScale位
     */
    public static BigDecimal getAmount(Object amount, int dotScale) {
        try {
            return new BigDecimal(amount.toString()).setScale(dotScale, RoundingMode.HALF_UP);
        } catch (Exception e) {
            return new BigDecimal(0).setScale(dotScale, RoundingMode.HALF_UP);
        }
    }

    /**
     * 获取日期格式
     * 
     * @param item
     *            待转换对象
     * @param format
     *            格式化串 常用 yyyy-MM-dd|yyyy-MM-dd HH:mm:ss
     * @return 转换失败则返回NULL
     */
    public static Date getDate(Object item, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(item.toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取日期格式
     * @param millSec  待转换long
     * @return 换失败则返回NULL
     */
    public static Date getDate(long millSec) {
        try {
            return new Date(millSec);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取日期格式字符串
     * @param item 待转换对象
     * @param format 格式化串 常用 yyyy-MM-dd|yyyy-MM-dd HH:mm:ss
     * @return 转换失败返回空串
     */
    public static String getDateStr(Object item, String format) {
        return date2String(getDate(item, format), format);
    }

    /**
     * 获取日期字符串
     * 
     * @param date
     *            待转换日期
     * @param format
     *            格式化串 常用 yyyy-MM-dd|yyyy-MM-dd HH:mm:ss
     * @return 转换失败返回空串
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return StringUtil.Empty;
        }
    }

    /**
     * 获取系统日期字符串
     * 
     * @return
     */
    public static String getCurrentDate() {
        return date2String(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取系统时间字符串
     * 
     * @return
     */
    public static String getCurrentDateTime() {
        return date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期型通用计算
     * 
     * @param srcDate
     *            源日期
     * @param calendarType
     *            Calendar中某种整型
     * @param interval
     *            间隔差值
     * @return 计算后的目标日期
     */
    public static Date addDateKind(Date srcDate, int calendarType, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(srcDate);
        cal.add(calendarType, interval);
        return cal.getTime();
    }

    /**
     * 在指定日期增加天数(可为负数
     * 
     * @param srcDate
     *            源日期 2013-01-04
     * @param interval
     *            天数差值 -2
     * @return 目标日期 2013-01-02
     */
    public static Date addDays(Date srcDate, int interval) {
        return addDateKind(srcDate, Calendar.DATE, interval);
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
        try {
            return date.toString(format);
        } catch(Exception e) {
            return StringUtil.Empty;
        }
    }

    public static String DateTimeToStr(DateTime date) {
        return DateTimeToStr(date, FORMAT_DEFAULT);
    }

    /**
     * 是否为数字
     * 
     * @param item
     * @return
     */
    public static boolean isNumber(Object item) {
        if (StringUtil.isNullOrEmpty(item)) return false;
        return item.toString().matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
}
