package com.jfshare.common.shortmsg;

import com.jfshare.finagle.thrift.result.FailDesc;

/**
 * Created by Lenovo on 2016/3/25.
 */
public interface ICaptchaFactory {

    CaptchaWithKey create(String key, FailDesc failDesc);

    FailDesc validate(CaptchaWithKey captchaWithKey);
}
