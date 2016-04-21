package com.jfshare.ridge.zookeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.exception.ZkTimeoutException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfshare.ridge.Constants;

public abstract class AbstractZookeeperClient {

    protected static final Logger logger = LoggerFactory
            .getLogger(AbstractZookeeperClient.class);

    private volatile ZkClientUtil zkClient = null;

    private volatile KeeperState state = KeeperState.SyncConnected;

    private volatile boolean closed = false;

    /**
     * zkClient默认获取连接超时限制
     */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 20 * 1000;


    protected void initZookeeper(URL url, final String appList) {
        try {
            zkClient = new ZkClientUtil(url.getServerList(), DEFAULT_CONNECTION_TIMEOUT);
            logger.info(Constants.formatLogMsg("Create zookeeper's connection successfully!", Constants.ZK_LOG_LEVEL_INFO));
        } catch (ZkTimeoutException e) {
            logger.error(Constants.formatLogMsg("Connect zookeeper error", Constants.ZK_LOG_LEVEL_ERROR), e);
            //如果出现异常需要做的事情
            connTimeoutHandle();
        }
        if (zkClient != null) {//如果获取到了zookeeper的链接
            zkClient.setZkSerializer(new BytesPushThroughSerializer());
            //监听zookeeper的链接状态
            zkClient.subscribeStateChanges(new IZkStateListener() {
                public void handleStateChanged(KeeperState state) throws Exception {
                    AbstractZookeeperClient.this.state = state;
                    if (state == KeeperState.Disconnected) {
                        logger.info(Constants.formatLogMsg("Zookeeper's connection is disconnected", Constants.ZK_LOG_LEVEL_INFO));
                    } else if (state == KeeperState.SyncConnected) {
//						childListener(appList);
                        logger.info(Constants.formatLogMsg("Zookeeper's connection is syncConnected", Constants.ZK_LOG_LEVEL_INFO));
                    }
                }

                public void handleNewSession() throws Exception {
                    logger.info(Constants.formatLogMsg("Zookeeper's connection is reconnected", Constants.ZK_LOG_LEVEL_INFO));
                }

                @Override
                public void handleSessionEstablishmentError(Throwable throwable) throws Exception {
                    logger.error(Constants.formatLogMsg("Zookeeper's connection is reconnected error", Constants.ZK_LOG_LEVEL_ERROR));
                }
            });
            //添加监听
            this.childListener(appList);
            //如果链接正常需要做的初始化工作
            initHandle();
        }

    }


    /**
     * 注册所有需要加载的项目节点下的配置项数据的监听器
     *
     * @return
     */
    protected void childListener(String appList) {

        try {
            if (appList != null && !appList.trim().isEmpty()) {
                String[] apps = appList.split(",");
                for (String app : apps) {
                    String appPath = Constants.ZK_ROOT_PATH.concat("/").concat(app);
                    //监听子节点变化
                    childChangeListener(appPath);
                    List<String> dataKeyList = zkClient.getChildren(appPath);
                    for (String dataKey : dataKeyList) {
                        String keyPath = appPath.concat("/").concat(dataKey);
                        dataListener(keyPath);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(Constants.formatLogMsg("listen in all data error!", Constants.ZK_LOG_LEVEL_ERROR), e);
        }

    }

    /**
     * 监听目录中子节点变化
     *
     * @param path
     */
    protected void childChangeListener(String path) {
        try {
            zkClient.subscribeChildChanges(path,
                    new IZkChildListener() {
                        @Override
                        public void handleChildChange(String parentPath,
                                                      List<String> currentChilds) throws Exception {
                            String newChildrenPath = "";
                            for (String currentChild : currentChilds) {
                                String keyPath = parentPath.concat("/").concat(currentChild);
                                if (zkClient.getDataListener(keyPath) == null) {
                                    //新添加的节点，对其进行监控
                                    dataListener(keyPath);
                                    newChildrenPath = keyPath;
                                    break;
                                }
                            }
                            ;
                            //修改子节点出发的事件，只有添加节点的时候触犯，删除节点的时候在handleDataDeleted的事件处理
                            if (StringUtils.isNotBlank(newChildrenPath)) {
                                childListenerHandle(parentPath, currentChilds, newChildrenPath);
                            }
                            logger.info(Constants.formatLogMsg(parentPath + " is changed children", Constants.ZK_LOG_LEVEL_INFO));
                        }
                    });
        } catch (Exception e) {
            logger.error(Constants.formatLogMsg("Create [" + path + "] listener failed", Constants.ZK_LOG_LEVEL_ERROR), e);
        }
    }

    /**
     * 具体的数据项监听
     *
     * @param path 数据项的绝对路径
     */
    protected void dataListener(String path) {
        try {
            zkClient.subscribeDataChanges(path, new IZkDataListener() {
                @Override
                public void handleDataDeleted(String dataPath) throws Exception {
                    //获取该节点所有的监听事件，然后取消对该节点的监听
                    Set<IZkDataListener> zkDataListeners = zkClient.getDataListener(dataPath);
                    if (zkDataListeners != null) {
                        for (IZkDataListener zkDataListener : zkDataListeners) {
                            //取消对该节点的监听
                            zkClient.unsubscribeDataChanges(dataPath, zkDataListener);
                        }
                    }
                    //如果删除某个数据项时触发的事件
                    deleteListenerHandle(dataPath);
                    logger.info(Constants.formatLogMsg(dataPath + " is deleted", Constants.ZK_LOG_LEVEL_INFO));
                }

                @Override
                public void handleDataChange(String dataPath, Object data)
                        throws Exception {
                    String value = new String((byte[]) data);
                    //如果某个数据项发生变动需要触发的事件
                    changeListenerHandle(dataPath, value);
                    logger.info(Constants.formatLogMsg(dataPath + " is changed [" + value + "]", Constants.ZK_LOG_LEVEL_INFO));
                }
            });
        } catch (Exception e) {
            logger.error(Constants.formatLogMsg("Create [" + path + "] listener failed", Constants.ZK_LOG_LEVEL_ERROR), e);
        }
    }

    /**
     * 手动获取zkClient链接
     *
     * @return
     */
    public ZkClientUtil getZkClient() {
        return zkClient;
    }

    /**
     * 创建正式目录
     *
     * @param path
     */
    public void createPersistent(String path) {
        try {
            zkClient.createPersistent(path, true);
        } catch (ZkNodeExistsException e) {
            logger.error("ZkNode exists", e);
        }
    }

    /**
     * 创建临时目录
     *
     * @param path
     */
    public void createEphemeral(String path) {
        try {
            zkClient.createEphemeral(path);
        } catch (ZkNodeExistsException e) {
        }
    }


    /**
     * 根据父节点路径, 获取下面所有的数据节点列表
     * 格式为: redis.ip=192.168.10.66
     *
     * @param parentPath eg: /imago/stock
     * @return
     */
    protected List<String> getDataKeyValueListByParent(String parentPath) {
        List<String> keyValueList = new ArrayList<String>();
        List<String> dataKeyList = zkClient.getChildren(parentPath);

        for (String dataKey : dataKeyList) {
            String dataKeyPath = parentPath.concat("/").concat(dataKey);
            String dataValue = this.getDataValueByKey(dataKeyPath);
            String keyValue = dataKey.concat("=").concat(dataValue);
            keyValueList.add(keyValue);
        }

        return keyValueList;
    }
    
    /**
     * 根据父节点路径, 获取下面所有的数据节点map
     * 格式为: map
     *
     * @param parentPath eg: /imago/stock
     * @return
     */
    protected Map<String,String> getDataKeyValueMapByParent(String parentPath) {
        Map<String,String> keyValueMap = new HashMap<String,String>();
        List<String> dataKeyList = zkClient.getChildren(parentPath);

        for (String dataKey : dataKeyList) {
            String dataKeyPath = parentPath.concat("/").concat(dataKey);
            String dataValue = this.getDataValueByKey(dataKeyPath);
            keyValueMap.put(dataKey, dataValue);
        }

        return keyValueMap;
    }


    /**
     * 根据父节点路径, 获取下面所有的数据节点列表 eg: redis.ip
     *
     * @param parentPath eg: /imago/stock
     * @return
     */
    protected List<String> getDataKeyListByParent(String parentPath) {
        List<String> dataKeyList = zkClient.getChildren(parentPath);
        return dataKeyList;
    }

    /**
     * 根据数据节点key获取节点值
     *
     * @param dataKeyPath eg: /imago/stock/redis.ip
     * @return
     */
    public String getDataValueByKey(String dataKeyPath) {
        String value = null;
        Object data = zkClient.readData(dataKeyPath);
        if(data!=null){
            value = new String((byte[]) data);
        }

        return value;
    }


    public boolean createDir(String path) throws Exception {
        boolean flag = false;
        try {
            if (!zkClient.exists(path)) {
                zkClient.createPersistent(path, true);
            }
            flag = true;
        } catch (Exception e) {
            throw e;
        }
        return flag;
    }


    public boolean writeData(String keyPath, Object value) throws Exception {
        boolean flag = false;
        try {
            if (zkClient.exists(keyPath)) {
                zkClient.writeData(keyPath, value);
            } else {
                zkClient.createPersistent(keyPath, value);
            }
            flag = true;
        } catch (Exception e) {
            throw e;
        }
        return flag;
    }


    public boolean delete(String path) throws Exception {
        boolean flag = false;
        try {
            if (zkClient.exists(path)) {
                zkClient.deleteRecursive(path);
                flag = true;
            }
        } catch (ZkNoNodeException e) {
            throw e;
        }
        return flag;
    }


    public boolean isConnected() {
        return state == KeeperState.SyncConnected;
    }

    /**
     * 创建目录
     *
     * @param path
     * @param ephemeral
     */
    public void create(String path, boolean ephemeral) {
        int i = path.lastIndexOf('/');
        if (i > 0) {
            create(path.substring(0, i), false);
        }
        if (ephemeral) {
            createEphemeral(path);
        } else {
            createPersistent(path);
        }
    }

    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        try {
            if(zkClient != null) {
                zkClient.close();
            }
        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
    }

    protected abstract void childListenerHandle(String parentPath, List<String> currentChildren, String newChildrenPath);

    protected abstract void changeListenerHandle(String dataPath, String lastDataValue);

    protected abstract void deleteListenerHandle(String dataPath);

    protected abstract void connTimeoutHandle();

    protected abstract void initHandle();
}
