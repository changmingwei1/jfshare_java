package com.jfshare.stock.util;

import com.jfshare.stock.dao.impl.redis.ConstRedis;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Lenovo on 2016/4/28.
 */
public class StockUtil {

    /**
     * 获取仓库id
     * @param s(仓库id:skuNum)
     * @return
     */
    public static String getStoreHouseIdBySplit(String s) {
        if(StringUtils.isNotBlank(s)) {
            String[] arr = s.split(ConstRedis.SPLIT, 2);
            if(arr.length == 2)
                return arr[0];
        }
        return null;
    }
    /**
     * 获取skuNum
     * @param s(仓库id:skuNum)
     * @return
     */
    public static String getSkuNumBySplit(String s) {
        if(StringUtils.isNotBlank(s)) {
            String[] arr = s.split(ConstRedis.SPLIT, 2);
            if(arr.length == 2)
                return arr[1];
        }
        return null;
    }

}
