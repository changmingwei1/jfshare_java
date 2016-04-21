package com.jfshare.common.captcha;

import com.alibaba.fastjson.JSON;
import com.jfshare.common.db.redis.BaseRedis;
import com.jfshare.common.db.redis.RedisConst;
import com.jfshare.common.util.SerializeUtil;
import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaAndLocale;
import com.octo.captcha.service.captchastore.CaptchaStore;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by Lenovo on 2015/12/2.
 */
public class RedisCaptchaStore implements CaptchaStore {
    //CacheService负责封装redis访问功能
    @Resource
    private BaseRedis baseRedis;

    @Override
    public boolean hasCaptcha(String id) {
        return baseRedis.exists(RedisConst._CAPTCHA + id);
    }

    @Override
    public void storeCaptcha(String id, Captcha captcha) throws CaptchaServiceException {
        try {
            System.err.println("---------------->" + JSON.toJSONString(captcha));
            baseRedis.putKV(RedisConst._CAPTCHA + id, SerializeUtil.serializeEx(new CaptchaAndLocale(captcha)), RedisConst._CAPTCHA_EXPIRE_TIME);
        } catch (Exception e) {
            throw new CaptchaServiceException(e);
        }
    }

    @Override
    public void storeCaptcha(String id, Captcha captcha, Locale locale) throws CaptchaServiceException {
        try {
            baseRedis.putKV(RedisConst._CAPTCHA + id, SerializeUtil.serializeEx(new CaptchaAndLocale(captcha, locale)), RedisConst._CAPTCHA_EXPIRE_TIME);
        } catch (Exception e) {
            throw new CaptchaServiceException(e);
        }
    }

    @Override
    public boolean removeCaptcha(String id) {
        return baseRedis.removeKV(id);
    }

    @Override
    public Captcha getCaptcha(String id) throws CaptchaServiceException {
        String captchaAndLocaleValue = baseRedis.getKV(RedisConst._CAPTCHA + id);
        if(StringUtils.isBlank(captchaAndLocaleValue)) {
            return null;
        }

        CaptchaAndLocale captchaAndLocale = (CaptchaAndLocale)SerializeUtil.unserializeEx(captchaAndLocaleValue);
        return captchaAndLocale != null ? (captchaAndLocale.getCaptcha()) : null;
    }

    @Override
    public Locale getLocale(String id) throws CaptchaServiceException {
        String captchaAndLocaleValue = baseRedis.getKV(RedisConst._CAPTCHA + id);
        if(StringUtils.isBlank(captchaAndLocaleValue)) {
            return null;
        }

        CaptchaAndLocale captchaAndLocale = (CaptchaAndLocale)SerializeUtil.unserialize(captchaAndLocaleValue.getBytes());
        return captchaAndLocale != null ? (captchaAndLocale.getLocale()) : null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Collection getKeys() {
        return null;
    }

    @Override
    public void empty() {
    }

}
