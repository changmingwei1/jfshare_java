package com.jfshare.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;


public class
        Common {

    private static String curTime = getReportTime(null);
    private static int idCounter = 0;


    public static String TIME_FORMAT_DB = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_FORMAT_REPORT = "yyyyMMddHHmmss";
    
    
    
    
    public static int  strToInt(String priceStr) {
        String[] data = priceStr.split("\\.");
        String data1 = data[0];
        String data2 = "";
        if (data.length > 1) {
            data2 = data[1];
            if (data2.length() >= 2) {
                data2 = data2.substring(0, 2);
            } else {
                data2 = data2 + "0";
            }
        }
        return Integer.parseInt(data1 + data2);
    }

    public static String intToStr(int priceInt) {
        String priceStr = priceInt + "";
        String data1 = "";
        String data2 = "";
        int length = priceStr.length();
        if (length >= 3) {
            data1 = priceStr.substring(0, length - 2);
            data2 = priceStr.substring(length - 3, length);
        }
        if (length == 2) {
            data1 = "0";
            data2 = priceStr;
        }
        if (length == 1) {
            data1 = "0";
            data2 = "0" + priceStr;
        }
        priceStr = data1 + "." + data2;
        return (priceInt * 1.0 / 100) + "";
    }
    
    
    public static String formatTimeToMill(Date date)
    {
    	
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

        if(date == null) {
            Long cur = Calendar.getInstance().getTimeInMillis();
            return timeFormat.format(new Date(cur));
        } else
            return timeFormat.format(date);
    }
    
    
    public static DateTime strToDate(String strTime, String format)
    {
        if(strTime == null || strTime.isEmpty())
    		return null;
    	 SimpleDateFormat timeFormat = new SimpleDateFormat(format);

    	 Date date = null;
	     try{
	        date = timeFormat.parse(strTime);
	     }catch(ParseException e){
	         System.out.println(e.getMessage());
	     }
         
         if(date != null)
        	 return new DateTime(date.getTime());
         return null;
    }
    
    public static DateTime strToDate(String strTime)
    {
    	return strToDate(strTime, TIME_FORMAT_DB);
    }
    
    public static DateTime reportToDate(String strTime)
    {
    	return strToDate(strTime, TIME_FORMAT_REPORT);
    }
    
    public static String getDBTime(Date date)
    {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date == null) {
            Long cur = Calendar.getInstance().getTimeInMillis();
            return timeFormat.format(new Date(cur));
        } else
            return timeFormat.format(date);
    }

    public static String getReportTime(Date date)
    {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        if(date == null) {
            Long cur = Calendar.getInstance().getTimeInMillis();
            return timeFormat.format(new Date(cur));
        } else
            return timeFormat.format(date);
    }

    

}
