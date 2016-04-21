package com.jfshare.order.util;

import com.jfshare.order.common.Commons;

/**
 * Created by Lenovo on 2015/10/26.
 */
public class TableNameUtil {

    public static String getOrderNameBySeller(int sellerId) {
        return Commons.TABLE_NAME_ORDER_PREFIX + Commons.TABLE_SELLER_PART + sellerId % 64;
    }

    public static String getOrderInfoNameBySeller(int sellerId) {
        return Commons.TABLE_NAME_ORDER_INFO_PREFIX + Commons.TABLE_SELLER_PART + sellerId % 64;
    }

    public static String getOrderNameByUser(int userId) {
        return Commons.TABLE_NAME_ORDER_PREFIX + Commons.TABLE_USER_PART + userId % 64;
    }

    public static String getOrderInfoNameByUser(int userId) {
        return Commons.TABLE_NAME_ORDER_INFO_PREFIX + Commons.TABLE_USER_PART + userId % 64;
    }
}
