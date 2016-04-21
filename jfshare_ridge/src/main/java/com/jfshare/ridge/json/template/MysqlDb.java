package com.jfshare.ridge.json.template;

/**
 * Class description goes here
 */
public class MysqlDb {
    /**
     * 数据库名
     */
    private String name;
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String pwd;
    /**
     * mysql字符串链接地址
     */
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
