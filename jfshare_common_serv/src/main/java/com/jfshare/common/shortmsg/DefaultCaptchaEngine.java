package com.jfshare.common.shortmsg;

import com.alibaba.fastjson.JSON;
import com.jfshare.common.db.redis.ICaptchaJedis;
import com.jfshare.common.util.FailCode;
import com.jfshare.common.util.RandomUtil;
import com.jfshare.finagle.thrift.result.FailDesc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lenovo on 2016/3/25.
 */
@Service
public class DefaultCaptchaEngine implements ICaptchaFactory {
    private Logger logger = LoggerFactory.getLogger(DefaultCaptchaEngine.class);

    @Autowired
    private ICaptchaJedis captchaJedis;

    //验证码有效期(秒)
    private final static int EXPIRED_SEC = 60*3;
    //验证码位数
    private final static int CAPTCHA_LENGTH = 4;
    //再次申请验证码时间间隔
    private final static int CREATE_LIMIT_SECONDS = 60;

    @Override
    public CaptchaWithKey create(String key, FailDesc failDesc) {
        long ttl = captchaJedis.ttl(key);
        if(ttl > 0 && EXPIRED_SEC - ttl < CREATE_LIMIT_SECONDS) {      //再次申请验证码时间间隔校验
            failDesc = FailCode.CAPTCHA_CREATE_TIME_LIMIT;
            return null;
        }

        CaptchaWithKey captchaWithKey = new CaptchaWithKey();
        captchaWithKey.setCaptcha(RandomUtil.generateUpperString(CAPTCHA_LENGTH));
        captchaWithKey.setKey(key);
        captchaWithKey.setExp(EXPIRED_SEC);
        captchaJedis.write(captchaWithKey);
        logger.info("生成的验证码为:{}", captchaWithKey);
        return captchaWithKey;
    }

    @Override
    public FailDesc validate(CaptchaWithKey captchaWithKey) {

        String key = captchaWithKey.getKey();
        long ttl = captchaJedis.ttl(key);

        if(ttl < 0) {
            if(ttl == -1) {
                captchaJedis.remove(key);   //若key未设置过期时间, 按验证码已过期处理, 并且删除该key
            }
            return FailCode.CAPTCHA_EXPIRED;

        }

        String captchaWithKeyStr = captchaJedis.read(key);

        if(StringUtils.isBlank(captchaWithKeyStr)) {
            return FailCode.CAPTCHA_EXPIRED;
        }

        CaptchaWithKey redisCaptchaWithKey = JSON.parseObject(captchaWithKeyStr, CaptchaWithKey.class);

        if(captchaWithKey.equals(redisCaptchaWithKey)) {
            captchaJedis.remove(key);
            return null;
        } else {
            return FailCode.CAPTCHA_VALIDATE_FAILURE;
        }
    }
}
