package com.jfshare.common.shortmsg;

/**
 * Created by Lenovo on 2016/3/25.
 */
public class CaptchaWithKey {

    private String captcha;
    private String key;
    private int exp;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaptchaWithKey that = (CaptchaWithKey) o;

        if (captcha != null ? !captcha.equalsIgnoreCase(that.captcha) : that.captcha != null) return false;
        return !(key != null ? !key.equals(that.key) : that.key != null);

    }

    @Override
    public int hashCode() {
        int result = captcha != null ? captcha.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CaptchaWithKey{" +
                "captcha='" + captcha + '\'' +
                ", key='" + key + '\'' +
                ", exp=" + exp +
                '}';
    }
}
