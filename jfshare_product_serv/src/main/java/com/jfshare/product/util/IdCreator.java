package com.jfshare.product.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: jerry
 * Date: 12-2-7
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
public class IdCreator {


    private static Logger logger = LoggerFactory.getLogger(IdCreator.class);

    private static String curTime = getTime(null);
    private static int idCounter = 0;


    public static String getTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyMMddHHmmss");

        if (date == null) {
            Long cur = Calendar.getInstance().getTimeInMillis();

            return timeFormat.format(new Date(cur));
        } else
            return timeFormat.format(date);
    }

    /*
    * 生成规则：
    ze +【年（末两位）月日】+【时分秒】【顺序号（3位）】+【预留（3位）】



    订单编号生成规则：
    年（末两位）月日】+【时分秒】【顺序号（3位）】+【预留（2位）】
    注：预留为了将来扩展，默认00；
    例如2014年1月22日 12时23分34秒购买的的第50个订单；
    则订单编号为：14012212233405000
    * */

    public static String getProductId() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("ze");
        return sBuffer.append(getId()).toString();
    }

    public static String getSnapshotId() {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("zs");
        return sBuffer.append(getId()).toString();
    }

    
    public synchronized static String getId() {
        StringBuffer sBuffer = new StringBuffer();

        String tTime = getTime(null);
        if (!tTime.equals(curTime)) {

            curTime = getTime(null);
            sBuffer.append(curTime);
            idCounter = 0;
        } else {
            if (idCounter >= 999) //重新获取
            {
                logger.info("每秒产生订单量 > 1000单");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                curTime = getTime(null);
                sBuffer.append(curTime);
                idCounter = 0;
            } else {
                sBuffer.append(curTime);
            }
        }


        DecimalFormat numFormat = (DecimalFormat) DecimalFormat.getInstance(); // 格式化数字位数
        numFormat.applyPattern("000");
        sBuffer.append(numFormat.format(idCounter));

        
        double s =  Math.random();
        sBuffer.append( numFormat.format( s*1000L  )/* "001"*/);
 

        ++idCounter;
        return sBuffer.toString();
    }

}
