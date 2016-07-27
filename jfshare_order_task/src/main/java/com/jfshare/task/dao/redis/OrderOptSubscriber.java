package com.jfshare.task.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by Lenovo on 2016/7/26.
 */
@Service
public class OrderOptSubscriber extends JedisPubSub {

    private static Logger logger = LoggerFactory.getLogger(OrderOptSubscriber.class);

    @Override
    public void onMessage(String channel, String message) {
        logger.info(String.format("Message. Channel: %s, Msg: %s", channel, message));
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        logger.info(String.format("PMessage. Pattern: %s, Channel: %s, Msg: %s",
                pattern, channel, message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("onSubscribe {}, {}", channel, subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info("onUnsubscribe");
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        logger.info("onPUnsubscribe");
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        logger.info("onPSubscribe");
    }
}
