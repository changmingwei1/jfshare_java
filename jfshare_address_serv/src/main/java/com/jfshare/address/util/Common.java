package com.jfshare.address.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {

    
    public static String formatTimeToMill(Date date)
    {
    	
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

        if(date == null) {
            Long cur = Calendar.getInstance().getTimeInMillis();
            return timeFormat.format(new Date(cur));
        } else
            return timeFormat.format(date);
    }
    
   
}
