package com.jfshare.ridge.json.template;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here
 */
public class MongodbTemplate implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3458329917320785024L;
	/**
     * ip:host格式
     */
    private List<String> hosts = new ArrayList<String>();
    /**
     * ip:host的字符串
     */
    private String hostsStr;
    /**
     * 数据库名字
     */
    private String database;
    /**
     * 数据库账号
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 集群方式
     */
    private String cluster;
    /**
     * replica_set类型的配置
     */
    private MongodbReplicaSet replica_set;

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public String getHostsStr() {
        return hostsStr;
    }

    public void setHostsStr(String hostsStr) {
        this.hostsStr = hostsStr;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public MongodbReplicaSet getReplica_set() {
        return replica_set;
    }

    public void setReplica_set(MongodbReplicaSet replica_set) {
        this.replica_set = replica_set;
    }
}
