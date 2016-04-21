package com.jfshare.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: xujunchi
 * Date: 14-3-1
 * Time: 下午12:59
 * To change this template use File | Settings | File Templates.
 */
public class PriceUtils {
    private static Logger logger = LoggerFactory.getLogger(PriceUtils.class);

    public static int strToInt(String priceStr) {
        boolean isPositivenumber = true;
        if (priceStr == null || priceStr.trim().equals("")) {
            return 0;
        }
        if (priceStr.trim().substring(0, 1).equals("-")) {
            priceStr = priceStr.substring(1, priceStr.length());
            isPositivenumber = false;
        }
        if (priceStr.trim().equals("0") || priceStr.trim().equals("0.0") || priceStr.trim().equals("0.00")) {
            return 0;
        }
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
        } else {
            data2 = "00";
        }
        if (isPositivenumber) {
            return Integer.parseInt(data1 + data2);
        } else {
            return Integer.parseInt("-" + data1 + data2);
        }

    }

    public static String intToStr(int priceInt) {
        boolean isPositivenumber = true;
        if (priceInt <= 0) {
            priceInt = Math.abs(priceInt);
            isPositivenumber = false;
        }
        if (priceInt == 0) {
            return "0.00";
        }
        String priceStr = priceInt + "";
        String data1 = "";
        String data2 = "";
        int length = priceStr.length();
        if (length >= 3) {
            data1 = priceStr.substring(0, length - 2);
            data2 = priceStr.substring(length - 2, length);
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
        if (!isPositivenumber) {
            priceStr = "-" + priceStr;
        }
        return priceStr;
    }

//    public static int strToInt(String priceStr) {
//        logger.info(":::::::::::priceStr:::::::::::::::::::"+priceStr);
//        if(priceStr==null){
//            return 0;
//        }
//        boolean isPositivenumber = true;
//        if (priceStr!=null&&priceStr.length()>=2&&priceStr.trim().substring(0, 1).equals("-")) {
//            priceStr = priceStr.substring(1, priceStr.length());
//            isPositivenumber = false;
//        }
//        if (priceStr.trim().equals("0")) {
//            return Integer.parseInt(priceStr);
//        }
//        String[] data = priceStr.split("\\.");
//        String data1 = data[0];
//        String data2 = "";
//        if (data.length > 1) {
//            data2 = data[1];
//            if (data2.length() >= 2) {
//                data2 = data2.substring(0, 2);
//            } else {
//                data2 = data2 + "0";
//            }
//        } else {
//            data2 = "00";
//        }
//        if (isPositivenumber) {
//            return Integer.parseInt(data1 + data2);
//        } else {
//            return Integer.parseInt("-" + data1 + data2);
//        }
//
//    }
//
//    public static String intToStr(int priceInt) {
//        logger.info(":::::::::::intToStr::::::::priceInt:::::::::::"+priceInt);
//        boolean isPositivenumber = true;
//        if (priceInt <= 0) {
//            priceInt = Math.abs(priceInt);
//            isPositivenumber = false;
//        }
//        if (priceInt == 0) {
//            return priceInt + "";
//        }
//        String priceStr = priceInt + "";
//        String data1 = "";
//        String data2 = "";
//        int length = priceStr.length();
//        if (length >= 3) {
//            data1 = priceStr.substring(0, length - 2);
//            data2 = priceStr.substring(length - 2, length);
//            if (data2.equals("00")) {
//                data2 = "";
//            }
//            if (data2.matches("\\d0")) {
//                data2 = data2.substring(0, 1);
//            }
//        }
//        if (length == 2) {
//            data1 = "0";
//            data2 = priceStr;
//            if (data2.equals("00")) {
//                data2 = "";
//            }
//            if (data2.matches("\\d0")) {
//                data2 = data2.substring(0, 1);
//            }
//        }
//        if (length == 1) {
//            data1 = "0";
//            data2 = "0" + priceStr;
//        }
//        if (data2.equals("")) {
//            priceStr = data1;
//        } else {
//            priceStr = data1 + "." + data2;
//        }
//        if (!isPositivenumber) {
//            priceStr = "-" + priceStr;
//        }
//        return priceStr;
//    }
}
