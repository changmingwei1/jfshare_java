package com.jfshare.common.db.redis.impl;

import com.alibaba.fastjson.JSON;
import com.jfshare.common.db.redis.BaseRedis;
import com.jfshare.common.db.redis.ICaptchaJedis;
import com.jfshare.common.shortmsg.CaptchaWithKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bq on 16/2/24.
 */
@Service
public class CaptchaJedisImpl implements ICaptchaJedis {

    @Autowired
    private BaseRedis baseRedis;

    @Override
    public boolean write(CaptchaWithKey captchaWithKey) {
        return baseRedis.putKV(captchaWithKey.getKey(), JSON.toJSONString(captchaWithKey), captchaWithKey.getExp());
    }

    @Override
    public String read(String key) {
        return baseRedis.getKV(key);
    }

    @Override
    public long ttl(String key) {
        return baseRedis.ttl(key);
    }

    @Override
    public boolean remove(String key) {
        return baseRedis.removeKV(key);
    }
}
