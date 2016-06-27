package com.jfshare.trade.dao.redis.impl;

import com.jfshare.trade.dao.redis.IBuyLimitRedis;
import com.jfshare.trade.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2015/11/2.
 */
@Repository
public class BuyLimitRedisImpl implements IBuyLimitRedis {
    private Logger logger = LoggerFactory.getLogger(BuyLimitRedisImpl.class);
    /*
    @Resource
    private JedisPool jedisRedoPool;*/

    @Resource(name = "redisSentinel")
    private JedisSentinelPool jedisRedoPool;

    @Override
    public int getBuyHistoryCount(int userId, String productId) {
        Jedis jedis = null;
        int ret = 0;
        try {
            jedis = jedisRedoPool.getResource();
            String key  = ConstantUtil.REDIS_KEY_BUY_LIMIT_PREFIX + userId+"_"+productId;
            String strRet = jedis.get(key);
            if(strRet == null || strRet.isEmpty())
                return 0;
            ret = Integer.parseInt(strRet);
        } catch (Exception e) {
            logger.error("获取商品购买历史redis失败！", e);
            return -1;
        } finally {
            jedisRedoPool.returnResource(jedis);
        }

        return ret;
    }
}
