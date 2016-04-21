package com.jfshare.trade.service.impl;

import com.jfshare.trade.dao.redis.IBuyLimitRedis;
import com.jfshare.trade.dao.redis.impl.BuyLimitRedisImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service
public class TradeSvcImpl implements com.jfshare.trade.service.impl.ITradeSvc {
    @Autowired
    private IBuyLimitRedis buyLimitRedis;

    @Override
    public int getBuyCount(int userId, String productId) {
        return buyLimitRedis.getBuyHistoryCount(userId, productId);
    }
}
