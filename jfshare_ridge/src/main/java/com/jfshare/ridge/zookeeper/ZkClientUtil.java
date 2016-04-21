package com.jfshare.ridge.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Set;

/**
 * Class description goes here
 */
public class ZkClientUtil extends ZkClient {


    public ZkClientUtil(String zkServers, int connectionTimeout) {
        super(zkServers, connectionTimeout);
    }

    /**
     * 引出获取监听器的方法
     * @param path
     * @return
     */
    public Set<IZkDataListener> getDataListener(String path) {
        return super.getDataListener(path);
    }

}
