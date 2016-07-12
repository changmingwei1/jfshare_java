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

    public static enum MsgFailCode{
        param_err("-1", "传递参数错误"),
        user_id_or_pwd_err("-2", "用户id或密码错误"),
        channel_id_err("-3", "通道id错误"),
        tele_no_err("-4", "手机号码错误"),
        content_err("-5", "短信内容错误"),
        balance_not_enough("-6", "余额不足错误"),
        ip_err("-7", "绑定ip错误"),
        not_exist_sign_err("-8", "未带签名"),
        sign_check_fail("-9", "签名字数不对"),
        channel_pause("-10", "通道暂停"),
        send_time_limit("-11", "该时间禁止发送"),
        date_err("-12", "时间戳错误"),
        code_err("-13", "编码异常"),
        send_num_out_of_limit("-14.2", "发送被限制"),
        content_err1("-15", "短信内容不正确")
        ;

        private String code;
        private String desc;

        MsgFailCode(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "MsgFailCode{" +
                    "code='" + code + '\'' +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

}
