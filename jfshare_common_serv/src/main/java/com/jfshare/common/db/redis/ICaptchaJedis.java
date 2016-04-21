package com.jfshare.common.db.redis;

import com.jfshare.common.shortmsg.CaptchaWithKey;

import java.util.Map;

/**
 * Created by bq on 16/2/23.
 */
public interface ICaptchaJedis {


    /**
     * captcha写入redis
     * @param captchaWithKey
     * @return
     */
    boolean write(CaptchaWithKey captchaWithKey);

    /**
     * 从redis中读取key
     * @param key
     * @return
     */
    String read(String key);

    /**
     * 获取验证码剩余存活时间(秒)
     * @param key
     * @return -1:永久, -2:验证码已失效, >0:剩余存活时间
     */
    long ttl(String key);

    /**
     * 删除验证码信息
     * @param key
     * @return
     */
    boolean remove(String key);
}
