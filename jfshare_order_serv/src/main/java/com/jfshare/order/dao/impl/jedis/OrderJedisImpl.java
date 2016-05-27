package com.jfshare.order.dao.impl.jedis;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.order.PayState;
import com.jfshare.order.dao.IOrderJedis;
import com.jfshare.order.model.OrderOpt;
import com.jfshare.order.util.ConstantUtil;
import com.jfshare.order.util.DateTimeUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by stepyee on 16/3/15.
 */
@Repository
public class OrderJedisImpl implements IOrderJedis {
    private Logger logger = LoggerFactory.getLogger(OrderJedisImpl.class);
    @Autowired
    private JedisPool jedisPool;
    @Override
    public int addPayState(String payId, int retCode, DateTime cancelTime) {
        Jedis jedis = null;
        int ret = 0;
        try {
            jedis = jedisPool.getResource();
            String key  = ConstantUtil.REDIS_KEY_PAY_STATE_PREFIX + payId;
            String strRet = jedis.setex(key, 3600 * 24, retCode + ":" + cancelTime.getMillis());
            if(strRet == null || strRet.isEmpty())
                return 0;
            if("OK".equalsIgnoreCase(strRet)) {
                return 1;
            }
        } catch (Exception e) {
            logger.error("设置支付状态redis失败！", e);
            return -1;
        } finally {
            jedisPool.returnResource(jedis);
        }

        return ret;
    }

    @Override
    public PayState getPayState(String payId) {
        Jedis jedis = null;
        PayState payState = new PayState();
        payState.setPayId(payId);
        try {
            jedis = jedisPool.getResource();
            String key  = ConstantUtil.REDIS_KEY_PAY_STATE_PREFIX + payId;
            String strRet = jedis.get(key);
            if (!StringUtil.isNullOrEmpty(strRet)) {
                String[] tmp = strRet.split(":");
                payState.setRetCode(ConvertUtil.getInt(tmp[0]));
                payState.setCancelTime(ConvertUtil.getInt(tmp[1]));
            }
        } catch (Exception e) {
            logger.error("设置支付状态redis失败！", e);
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }

        return payState;
    }

    @Override
    public boolean pushOrderNotification(OrderOpt.OrderOptPush orderOptPush) {
        if(orderOptPush == null || CollectionUtils.isEmpty(orderOptPush.getOrderOpts())) {
            return false;
        }

        List<OrderOpt> orderOpts = orderOptPush.getOrderOpts();

        Jedis jedis = null;
        long resRedis = 0;
        try {
            String[] msgs = new String[orderOpts.size()];
            for (int i = 0; i < orderOpts.size(); i++) {
                msgs[i] = JSON.toJSONString(orderOpts.get(i));
            }

            jedis = jedisPool.getResource();
            resRedis = jedis.lpush(ConstantUtil.REDIS_KEY_ORDER_OPT_QUEUE, msgs);
            logger.info("orderNotify ==> lpush={}, result={}", JSON.toJSONString(msgs), resRedis);
        }catch(Exception e) {
            logger.error("推送订单消息 ==> push消息发生异常, 原因:", e);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return resRedis > 0;
    }
}
