package com.jfshare.trade.dao.redis;

/**
 * Created by lenovo on 2015/11/2.
 */
public interface IBuyLimitRedis {
    int getBuyHistoryCount(int userId, String productId);
}
