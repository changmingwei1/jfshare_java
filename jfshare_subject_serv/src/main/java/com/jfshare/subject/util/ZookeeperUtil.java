package com.jfshare.subject.util;

import com.jfshare.ridge.ConfigManager;
import com.jfshare.ridge.zookeeper.ZkClientUtil;

public class ZookeeperUtil {

    private static ConfigManager configManager = new ConfigManager();

    /**
     * 向zookeeper中写入数据，如果节点不存在，添加节点并写入
     * @param path
     * @param data
     */
    public static void writeData(String path, Object data) {
        ZkClientUtil zkClientUtil = configManager.getZkClient();
        if(!zkClientUtil.exists(path)) {
            zkClientUtil.createPersistent(path, true);
        }
        zkClientUtil.writeData(path, data);
    }

    /**
     * 删除节点，不递归删除父节点
     * @param path
     */
    public static void deleteData(String path) {
        ZkClientUtil zkClientUtil = configManager.getZkClient();
        if(zkClientUtil.exists(path)) {
            zkClientUtil.delete(path);
        }
    }
}
