package com.jfshare.common.shortmsg;

import com.jfshare.common.util.SpringContextHolder;
import com.jfshare.finagle.thrift.common.MsgCaptcha;
import com.jfshare.finagle.thrift.result.FailDesc;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Lenovo on 2016/2/23.
 */
@Service
public class MsgHandler{

    public CaptchaWithKey getMsgCaptcha(MsgCaptcha captcha, List<FailDesc> createFailDescs) throws  Exception{
        MsgType msgType = MsgType.valueOf(captcha.getType());
        String key = captcha.getType() + ":" +  captcha.getMobile() ;
        Object handler = SpringContextHolder.getApplicationContext().getBean(msgType.getEngine());
        return (CaptchaWithKey) MethodUtils.invokeMethod(handler, "create", key, createFailDescs);
    }

    public FailDesc validateMsgCaptcha(MsgCaptcha captcha) throws Exception{
        MsgType msgType = MsgType.valueOf(captcha.getType());
        String key = captcha.getType() + ":" +  captcha.getMobile() ;

        CaptchaWithKey captchaWithKey = new CaptchaWithKey();
        captchaWithKey.setCaptcha(captcha.getCaptchaDesc());
        captchaWithKey.setKey(key);

        Object handler = SpringContextHolder.getApplicationContext().getBean(msgType.getEngine());
        return (FailDesc) MethodUtils.invokeMethod(handler, "validate", captchaWithKey);
    }
}
