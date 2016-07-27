package com.jfshare.task.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by Lenovo on 2016/7/26.
 */
@Service
public class OrderOptSubThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListRedisManager.class);

    @Autowired
    private OrderOptSubscriber subscriber;

    @Autowired
    private JedisPool jedisPool;

    @PostConstruct
    public void init() {
        final Jedis subscriberJedis = jedisPool.getResource();
        new Thread(new Runnable() {
            public void run() {
                try {
                    LOGGER.info("Subscribing to \"pubsub_order_notify\". This thread will be blocked.");
                    //使用subscriber订阅CHANNEL_NAME上的消息，这一句之后，线程进入订阅模式，阻塞。
                    subscriberJedis.subscribe(subscriber, CHANNEL_NAME);

                    //当unsubscribe()方法被调用时，才执行以下代码
                    LOGGER.info("Subscription ended.");
                } catch (Exception e) {
                    LOGGER.error("Subscribing failed.", e);
                }
            }
        }).start();
    }

    public static final String CHANNEL_NAME = "pubsub_order_notify";
}
