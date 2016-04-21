package com.jfshare.brain.finagle.server;

/**
 * 服务配置项
 * Created by lenovo on 2015/9/28.
 */
public class FinagleServerCfg {
    //服务唯一标识
    private String sid = "";
    //服务的实现类BeanId
    private String ref;
    //启动服务的api
    private String api = "";
    //启动服务的端口
    private Integer port;
    //启动服务的线程数(默认值300)
    private Integer threads = 300;
    //服务端处理超时时间
    private Long stimeout = 30000L;
    //服务版本号
    private String version = null;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FinagleServerCfg(");
        sb.append("服务标识sid:").append(this.sid).append(",");
        sb.append("spring的bean名称ref:").append(this.ref).append(",");
        sb.append("thrift接口api:").append(this.api).append(",");
        sb.append("端口port:").append(this.port).append(",");
        sb.append("线程数threads:").append(this.threads).append(",");
        sb.append("服务端处理超时（ms）stimeout:").append(this.stimeout).append(",");
        sb.append("版本version:").append(this.version);
        sb.append(")");
        return sb.toString();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Long getStimeout() {
        return stimeout;
    }

    public void setStimeout(Long stimeout) {
        this.stimeout = stimeout;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
