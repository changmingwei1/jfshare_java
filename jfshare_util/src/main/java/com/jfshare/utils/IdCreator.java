package com.jfshare.utils;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.uniqueid.IDGenerater;
import com.jfshare.uniqueid.ZKUniqueIdClient;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    //生成订单号使用
    private static IDGenerater idGenerater;

    public static IDGenerater getIDGenerater(){
        String zk_host = PropertiesUtil.getProperty("jfx_public_zookeeper", "zk01");
        String id_path = PropertiesUtil.getProperty("jfx_trade_serv", "order_id_path");
        int gap = NumberUtils.toInt(PropertiesUtil.getProperty("jfx_trade_serv", "order_id_get_count"), 1000);
        ZKUniqueIdClient zkClient = new ZKUniqueIdClient(zk_host, id_path, gap);
        IDGenerater idGenerater = null;
        try {
            idGenerater = new IDGenerater(zkClient);
        } catch (Exception e) {
            logger.error("getIDGenerater==> 创建IDGenerater实例失败！", e);
            throw new RuntimeException("创建IDGenerater实例失败！请联系系统管理员！—————————————————————————————");
        }
        return idGenerater;
    }

    //生成支付流水号使用
    private static IDGenerater payIdGen;
    public static IDGenerater getPayIdIDGen(){
        String zk_host = PropertiesUtil.getProperty("jfx_public_zookeeper", "zk01");
        String id_path = PropertiesUtil.getProperty("jfx_pay_serv", "pay_id_path");
        int gap = NumberUtils.toInt(PropertiesUtil.getProperty("jfx_pay_serv", "pay_id_get_count"), 1000);
        ZKUniqueIdClient zkClient = new ZKUniqueIdClient(zk_host, id_path, gap);
        IDGenerater idGenerater = null;
        try {
            idGenerater = new IDGenerater(zkClient);
        } catch (Exception e) {
            logger.error("getPayIdIDGen==> 创建IDGenerater实例失败！", e);
            throw new RuntimeException("创建IDGenerater实例失败！请联系系统管理员！—————————————————————————————");
        }
        return idGenerater;
    }

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
        return sBuffer.append(getOrderId()).toString();
    }

     @Deprecated  /*now use getOrderIdExZk*/
    public synchronized static String getOrderId() {
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

        sBuffer.append("001");


        ++idCounter;
        return sBuffer.toString();
    }

    public static String getOrderIdExZk(int userId) {
        StringBuilder sBuilder = new StringBuilder();
        if(idGenerater == null) {
            idGenerater = getIDGenerater();
        }

        try {
            sBuilder.append(idGenerater.getId());
        } catch (Exception e) {
            throw new RuntimeException("ERROR  生成订单ID失败！请联系系统管理员！—————————————————————————————");
        }

        String strUserId = String.valueOf(userId);
        strUserId = strUserId.length() >= 4 ? strUserId.substring(strUserId.length()-4) : "0000".substring(strUserId.length()).concat(strUserId);
        sBuilder.append(strUserId);

        return sBuilder.toString();
    }

    public static String getTradePayId(int userId, List<String> orderIdList)
    {
        Set<String> strSet = new TreeSet<String>();
        strSet.addAll(orderIdList);

        String strMd5 = userId + "_";

        Iterator<String> iter = strSet.iterator();
        while(iter.hasNext()) {
            String orderId= iter.next();
            strMd5 += orderId;
        }

        return  CryptoUtil.md5Encode(strMd5);
    }

    public static String getTradePayIdTY(int userId)
    {
        return "17921" + getPayIdExZk(String.valueOf(userId));
    }

    public static String getPayIdExZk(String strkey) {
        StringBuilder sBuilder = new StringBuilder();
        if(payIdGen == null) {
            payIdGen = getPayIdIDGen();
        }

        try {
            sBuilder.append(payIdGen.getId());
        } catch (Exception e) {
            throw new RuntimeException("ERROR  生成payID失败！请联系系统管理员！—————————————————————————————");
        }

        sBuilder.append(strkey);

        return CryptoUtil.md5Encode(sBuilder.toString());
    }
}
