package com.jfshare.ridge.json.template;

/**
 * Class description goes here
 */
public abstract class AbstractConfigTemplate implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7926154678701263023L;
	/**
     * ip
     */
    private String ip;
    /**
     * 端口号
     */
    private String port;
    /**
     * 类型
     */
    private String type;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
