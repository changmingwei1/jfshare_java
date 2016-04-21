package com.jfshare.ridge.json.template;


/**
 * Class description goes here
 */
public class RedisTemplate extends AbstractConfigTemplate {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6992587895946577047L;
	/**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String pwd;
    /**
     *  ”w/r“ 表示是否可读写
     */
    private String mode;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
