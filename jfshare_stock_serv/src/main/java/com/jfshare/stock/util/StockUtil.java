package com.jfshare.stock.util;

import com.jfshare.stock.dao.impl.redis.ConstRedis;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by Lenovo on 2016/4/28.
 */
public class StockUtil {

    /**
     * 获取仓库id
     * @param s(仓库id:skuNum)
     * @return
     */
    public static int getStoreHouseIdBySplit(String s) {
        int res = 0;
        if(StringUtils.isNotBlank(s)) {
            String[] arr = s.split(ConstRedis.SPLIT, 2);
            if(arr.length == 2 && NumberUtils.isDigits(arr[0]))
                res = NumberUtils.toInt(arr[0]);
        }
        return res;
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
