package com.jfshare.common.shortmsg;

/**
 * Created by Lenovo on 2016/2/23.
 */
public enum MsgType {
    buyer_signin("买家注册/登录", DefaultCaptchaEngine.class),
    buyer_find_password("买家注册/登录", DefaultCaptchaEngine.class)
    ;

    private String name;
    private Class<?> engine;

    MsgType(String name,  Class<?> engine) {
        this.name = name;
        this.engine = engine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getEngine() {
        return engine;
    }

    public void setEngine(Class<?> engine) {
        this.engine = engine;
    }
}
