package com.jfshare.common.shortmsg;

import com.jfshare.finagle.thrift.result.FailDesc;

import java.util.List;

/**
 * Created by Lenovo on 2016/3/25.
 */
public interface ICaptchaFactory {

    CaptchaWithKey create(String key, List<FailDesc> failDescs);

    FailDesc validate(CaptchaWithKey captchaWithKey);
}
