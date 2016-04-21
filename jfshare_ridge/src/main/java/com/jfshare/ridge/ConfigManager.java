package com.jfshare.ridge;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.jfshare.ridge.zookeeper.AbstractZookeeperClient;
import com.jfshare.ridge.json.template.KafkaTemplate;
import com.jfshare.ridge.json.template.MongodbTemplate;
import com.jfshare.ridge.json.template.MysqlDb;
import com.jfshare.ridge.json.template.MysqlTemplate;
import com.jfshare.ridge.json.template.RedisTemplate;
import com.jfshare.ridge.utils.FileUtil;
import com.jfshare.ridge.zookeeper.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取集中配置管理项操作类
 *
 */
public class ConfigManager extends AbstractZookeeperClient {

    protected static final Logger logger = LoggerFactory
            .getLogger(ConfigManager.class);

    //本地缓存
    static ConcurrentHashMap<String, String> localCache = new ConcurrentHashMap<String, String>();

    //动态监听map
    private ConcurrentHashMap<String, String> listenerMap = new ConcurrentHashMap<String, String>();
    
    static ConfigManager configManager;

    private volatile boolean isCloseZk = false;

    private volatile boolean isZkTimeout = false;

    //需要加载的项目
    private String appList;


    /**
     * 单例模式加载该类, spring配置文件如下配置, 该构造函数不依赖dns
     * <bean id="configManager" class="znlzkclient.ConfigManager">
     * <constructor-arg name="serverList" value="192.168.10.66:2181,192.168.10.57:2181,192.168.10.58:2181" /><!-- zookeeper主机地址 -->
     * <constructor-arg name="appList" value="stock,promotion,product" /> <!--需要加载哪些项目中的配置项-->
     * </bean>
     *
     * @param serverList
     * @param appList
     */
    public ConfigManager(String serverList, String appList) {
        File file = new File(Constants.ZK_FILE_SNAPSHOT_PATH.concat("/.closezk"));
        //加一个控制是否依赖zookeeper的开关, 如果存在这个文件则剥离对zk的依赖
        if (file.exists()) {//
            this.isCloseZk = true;
            logger.info(Constants.formatLogMsg("Depend Zookeeper is closed !, Direct load local snapshot!", Constants.ZK_LOG_LEVEL_INFO));
            this.connTimeoutHandle();
        } else {
            this.appList = appList;
            if(serverList==null || serverList.isEmpty()){
            	//通过解析域名的dns获取zookeeper集群地址
                serverList = Constants.getZkAddress();
            }
            URL url = new URL(serverList);
            super.initZookeeper(url, appList);
        }
        configManager = this;
    }


    /**
     * 单例模式加载该类, spring配置文件如下配置, 该构造函数依赖dns
     * <bean id="configManager" class="com.jfshare.imago.client.ConfigManager">
     * <constructor-arg name="serverList" value="192.168.10.66:2181,192.168.10.57:2181,192.168.10.58:2181" /> <!--需要加载哪些项目中的配置项-->
     * </bean>
     *
     * @param appList
     */
    public ConfigManager(String serverList) {
        this(serverList,null);
    }


    /**
     * 单例模式加载该类, spring配置文件如下配置, 该构造函数依赖dns
     * <bean id="configManager" class="com.jfshare.imago.client.ConfigManager" />
     */
    public ConfigManager() {
        this(null);
    }


    /**
     * 根据appKey, configKey获取配置值
     *
     * @param appKey
     * @param configKey
     * @return
     */
    public String getConfigValue(String appKey, String configKey) {
        String configPath = Constants.ZK_ROOT_PATH.concat("/").concat(appKey).concat("/").concat(configKey);
        String appPath = Constants.ZK_ROOT_PATH.concat("/").concat(appKey);
        if (!this.listenerMap.containsKey(appPath) && !this.isCloseZk && !this.isZkTimeout) {//在没有屏蔽zk的前提下, zk没有超时,如果不存在该appkey, 则动态添加监听
            //首次使用, 先添加监听
            this.childListener(appKey);
            //初始化该app下的config列表
            this.loadConfigByAppKeyFromZookeeper(appKey);
            //标识该appKey已经动态添加listener和初始化完成
            this.listenerMap.put(appPath, appPath);
        }

        String configValue = ConfigManager.localCache.get(configPath);
        if (configValue == null) {//防御性异常
            logger.error(Constants.formatLogMsg("You get appkey:" + appKey + " configKey:" + configKey + " is null !", Constants.ZK_LOG_LEVEL_ERROR));
        } else {//排重
            configValue = configValue.trim();
        }

        return configValue;

    }


    /**
     * 根据appKey, configKey获取配置值, 如果没有, 则返回默认值
     *
     * @param appKey
     * @param configKey
     * @param defValue
     * @return
     */
    public String getConfigValue(String appKey, String configKey, String defValue) {
        String configValue = this.getConfigValue(appKey, configKey);
        if (configValue == null) {
            configValue = defValue;
        }
        return configValue;
    }

    /**
     * 根据appKey, configKey获取mysql配置模板对象
     *
     * @param appKey
     * @param configKey
     * @return
     */
    public MysqlTemplate getMysqlConfig(String appKey, String configKey) {
        String jsonStr = getConfigValue(appKey, configKey, "");
        MysqlTemplate mySqlTemplate = null;
        try {
            mySqlTemplate = JSON.parseObject(jsonStr, MysqlTemplate.class);
            //拼接mysql链接的url字符串
            for (MysqlDb mysqlDb : mySqlTemplate.getDbs()) {
                mysqlDb.setUrl("jdbc:mysql://".concat(mySqlTemplate.getIp()).concat(":")
                        .concat(mySqlTemplate.getPort()).concat("/").concat(mysqlDb.getName())
                        .concat("?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull"));
            }
        } catch (Exception e) {
            String configPath = Constants.ZK_ROOT_PATH.concat("/").concat(appKey).concat("/").concat(configKey);
            logger.error(Constants.formatLogMsg("zk-java-client get mysql MySqlTemplate by key:" + configPath + " error!" +
                    "Failed to parse Object:" + jsonStr, Constants.ZK_LOG_LEVEL_ERROR), e);
        }
        return mySqlTemplate;
    }

    /**
     * @param appKey
     * @param configKey
     * @param dataBaseName
     * @return
     */
    public MysqlDb getMysqlConfig(String appKey, String configKey, String dataBaseName) {
        MysqlTemplate mysqlTemplate = getMysqlConfig(appKey, configKey);
        try {
            for (MysqlDb mysqlDb : mysqlTemplate.getDbs()) {
                if (dataBaseName.equals(mysqlDb.getName())) {
                    return mysqlDb;
                }
            }
            throw new Exception("zk-java-client dataBase :" + dataBaseName + " not found error!");
        } catch (Exception e) {
            logger.error(Constants.formatLogMsg("zk-java-client dataBase :" + dataBaseName + " not found error!", Constants.ZK_LOG_LEVEL_ERROR), e);
        }
        return null;
    }

    /**
     * 获取mongon的配置
     *
     * @param appKey
     * @param configKey
     * @return
     */
    public MongodbTemplate getMongoConfig(String appKey, String configKey) {
        String jsonStr = getConfigValue(appKey, configKey, "");
        MongodbTemplate mongodbTemplate = null;
        try {
            mongodbTemplate = JSON.parseObject(jsonStr, MongodbTemplate.class);
            mongodbTemplate.setHostsStr(mongodbTemplate.getHosts().toString().replace("[", "").replace("]", "").replace(" ", ""));
        } catch (Exception e) {
            String configPath = Constants.ZK_ROOT_PATH.concat("/").concat(appKey).concat("/").concat(configKey);
            logger.error(Constants.formatLogMsg("zk-java-client get mongo MongodbTemplate by key:" + configPath + " error!" +
                    "Failed to parse Object:" + jsonStr, Constants.ZK_LOG_LEVEL_ERROR), e);
        }
        return mongodbTemplate;
    }

    /**
     * 获取Kafka模板信息
     *
     * @param appKey
     * @param configKey
     * @return
     */
    public KafkaTemplate getKafkaConfig(String appKey, String configKey) {
        String jsonStr = getConfigValue(appKey, configKey, "");
        KafkaTemplate kafkaTemplate = null;
        try {
            kafkaTemplate = JSON.parseObject(jsonStr, KafkaTemplate.class);
        } catch (Exception e) {
            String configPath = Constants.ZK_ROOT_PATH.concat("/").concat(appKey).concat("/").concat(configKey);
            logger.error(Constants.formatLogMsg("zk-java-client get Kafka KafkaTemplate by key:" + configPath + " error!" +
                    "Failed to parse Object:" + jsonStr, Constants.ZK_LOG_LEVEL_ERROR), e);
        }
        return kafkaTemplate;
    }

    /**
     * 根据appKey, configKey获取mysql配置模板对象
     *
     * @param appKey
     * @param configKey
     * @return
     */
    public RedisTemplate getRedisConfig(String appKey, String configKey) {
        String jsonStr = getConfigValue(appKey, configKey, "");
        RedisTemplate redisTemplate = null;
        try {
            redisTemplate = JSON.parseObject(jsonStr, RedisTemplate.class);
        } catch (Exception e) {
            String configPath = Constants.ZK_ROOT_PATH.concat("/").concat(appKey).concat("/").concat(configKey);
            logger.error(Constants.formatLogMsg("zk-java-client get mysql MySqlTemplate by key:" + configPath + " error!" +
                    "Failed to parse Object:" + jsonStr, Constants.ZK_LOG_LEVEL_ERROR), e);
        }
        return redisTemplate;
    }

    /**
     * 如果连接zookeeper超时, 将从配置文件恢复配置项到本地缓存.
     */
    @Override
    protected void connTimeoutHandle() {
        logger.info(Constants.formatLogMsg("loading file snapshot to local cache!", Constants.ZK_LOG_LEVEL_INFO));
        this.isZkTimeout = true;
        //获取所有文件快照的配置
        File snapshotDir = new File(Constants.ZK_FILE_SNAPSHOT_PATH);
        if (snapshotDir.exists() && snapshotDir.isDirectory()) {
            File[] snapshotFileList = snapshotDir.listFiles();
            for (File snapshotFile : snapshotFileList) {
                String fileName = snapshotFile.getName();
                String localSnapshotPath = snapshotFile.getPath();
                if (fileName != null && fileName.toLowerCase().indexOf(".properties") > 0) {
                    String app = fileName.replaceAll(".properties", "");
                    try {
                        InputStream in = new BufferedInputStream(new FileInputStream(localSnapshotPath));// 加载本地快照文件
                        ResourceBundle rb = new PropertyResourceBundle(in);
                        String cacheKeyPrefix = Constants.ZK_ROOT_PATH.concat("/").concat(app);
                        for (String key : rb.keySet()) {
                            String cacheKey = cacheKeyPrefix.concat("/").concat(key);
                            String cacheValue = rb.getString(key);
                            this.updateLocalCache(cacheKey, cacheValue);
                            logger.info(Constants.formatLogMsg("Loading configuration [" + cacheKey + "][" + cacheValue + "] from local snapshot successfully !", Constants.ZK_LOG_LEVEL_INFO));
                        }
                    } catch (Exception e) {
                        logger.error(Constants.formatLogMsg("zk-java-client loading " + localSnapshotPath + " error!", Constants.ZK_LOG_LEVEL_ERROR), e);
                    }
                }

            }
        }
    }


    /**
     * 初始化配置项到本地文件和本地缓存
     */
    @Override
    protected void initHandle() {
        if (appList != null && !appList.trim().isEmpty()) {
            String[] apps = appList.split(",");
            for (String app : apps) {
            	//标识已经创建监听
            	String appPath = Constants.ZK_ROOT_PATH.concat("/").concat(app);
            	 //标识该appKey已经动态添加listener和初始化完成
                this.listenerMap.put(appPath, appPath);
                //
                this.loadConfigByAppKeyFromZookeeper(app);
            }
        }
    }


    /**
     * 根据appKey生产本地文件快照, 和本地缓存
     *
     * @param appKey
     */
    private void loadConfigByAppKeyFromZookeeper(String appKey) {
        //初始化本地快照
        try {
            this.genFileSnapshotByAppKey(appKey);
        } catch (Exception e) {
            logger.error(Constants.formatLogMsg("update file snapshot error :[" + appKey + "]", Constants.ZK_LOG_LEVEL_ERROR), e);
        }

        //初始化本地缓存
        String appPath = Constants.ZK_ROOT_PATH.concat("/").concat(appKey);

        //获取这些应用下面所有的配置项
        List<String> configKeyList = this.getDataKeyListByParent(appPath);
        if (configKeyList != null) {
            for (String configKey : configKeyList) {
                String dataPath = appPath.concat("/").concat(configKey);
                String configValue = this.getDataValueByKey(dataPath);

                ConfigManager.localCache.put(dataPath, configValue);
                logger.debug(Constants.formatLogMsg("configKey[" + dataPath + "] Value:[" + configValue + "] add into local cache successfully!", Constants.ZK_LOG_LEVEL_DEBUG));
            }
        }
    }


    /**
     * 当添加新的子节点时会触发，删除的时候已经过滤掉
     *
     * @param parentPath
     * @param currentChildren
     * @param newChildrenPath
     */
    @Override
    protected void childListenerHandle(String parentPath, List<String> currentChildren, String newChildrenPath) {
        String appKey = parentPath.substring(parentPath.lastIndexOf("/") + 1);
        //更新本地文件快照, 文件默认在classpath下
        try {
            this.genFileSnapshotByAppKey(appKey);
        } catch (Exception e) {
            logger.error(Constants.formatLogMsg("Update file snapshot error :[" + appKey + "]", Constants.ZK_LOG_LEVEL_ERROR), e);
        }
        //将新加的key添加到map中
        String dataValue = this.getDataValueByKey(newChildrenPath);
        this.updateLocalCache(newChildrenPath, dataValue);
        logger.info(Constants.formatLogMsg("change parentPath:" + parentPath, Constants.ZK_LOG_LEVEL_INFO));
    }

    /**
     * 当某个数据项发送改变时, 触发该方法,用来更新文件快照 ,和本地缓存
     */
    @Override
    protected void changeListenerHandle(String dataPath, String lastDataValue) {
        // 所有数据结构应该都是 /zk/appname/configkey
        //dataPath则是变动的数据节点的路径
        String appKey = "tempFile";
        String configKey = "";
        // String configKey = null;
        if (dataPath != null) {
            String[] tempStrs = dataPath.trim().split("/");
            if (tempStrs.length == 4) {
                appKey = tempStrs[2];
                configKey = tempStrs[3];
            }
        }

        //更新本地文件快照
        try {
            this.genFileSnapshotByAppKey(appKey);
//        	updateFileSnapshotSingleKeyValue(appKey, configKey, lastDataValue);
        } catch (Exception e) {
            logger.error(Constants.formatLogMsg("Update file snapshot error :[" + appKey + "]", Constants.ZK_LOG_LEVEL_ERROR), e);
        }
        this.updateLocalCache(dataPath, lastDataValue);

    }


    /**
     * 根据appKey生成本地文件快照,快照文件默认存储在classpath目录下的zk_snaphost文件夹下,每个文件的以appKey命名,eg: stock.properties
     *
     * @param appKey
     */
    private void genFileSnapshotByAppKey(String appKey) throws Exception {

        String fileSnapshotPathAndName = Constants.ZK_FILE_SNAPSHOT_PATH.concat("/").concat(appKey).concat(".properties");
        String fileSnapshotPathAndNameLock = fileSnapshotPathAndName.concat(".lock");
        FileUtil.createFolder(Constants.ZK_FILE_SNAPSHOT_PATH);

        String appPath = Constants.ZK_ROOT_PATH.concat("/").concat(appKey);

        //获取这些应用下面所有的配置项和值
        Map<String, String> configMap = this.getDataKeyValueMapByParent(appPath);
        if (configMap != null) {
            File file = new File(fileSnapshotPathAndName);
            if (!file.exists()) {
                file.createNewFile();
            }

            File lockFile = new File(fileSnapshotPathAndNameLock);
            if (!lockFile.exists()) {
                lockFile.createNewFile();
            }

            RandomAccessFile raf = new RandomAccessFile(lockFile, "rw");
            try {
                FileChannel channel = raf.getChannel();
                try {
                    FileLock lock = null;
                    try {
                        lock = channel.tryLock();
                    } catch (Exception e) {
                        logger.error(Constants.formatLogMsg(file.getAbsolutePath() + " locking conflict", Constants.ZK_LOG_LEVEL_ERROR), e);
                        return;
                    }
                    if (lock == null) {
                        logger.info(Constants.formatLogMsg("Can not lock the registry cache file " + file.getAbsolutePath() + ", ignore and retry later, maybe multi java process use the file", Constants.ZK_LOG_LEVEL_INFO));
                        return;
                    }
                    // 保存
                    try {
                        FileUtil.writePropertiesFile(fileSnapshotPathAndName, configMap);
                    } finally {
                        lock.release();
                    }
                } finally {
                    channel.close();
                }
            } finally {
                raf.close();
            }
            logger.info(Constants.formatLogMsg("file snapshot " + appKey + ".properties create on path:" + Constants.ZK_FILE_SNAPSHOT_PATH, Constants.ZK_LOG_LEVEL_INFO));
        }
    }


    /**
     * 更新本地缓存
     *
     * @param dataPath
     * @param lastDataValue
     */
    private void updateLocalCache(String dataPath, String lastDataValue) {
        if (lastDataValue != null) {
            //去除掉字符串两端的双引号
            if (lastDataValue.startsWith("\"")) {
                lastDataValue = lastDataValue.substring(1);
            }
            if (lastDataValue.endsWith("\"")) {
                lastDataValue = lastDataValue.substring(lastDataValue.length() - 1);
            }
            //去除双引号, 这样可以手动通过zk的命令行操作数据
//            lastDataValue =lastDataValue.endsWith("\"");
        }
        ConfigManager.localCache.put(dataPath, lastDataValue);
    }


    /**
     * 某个数据项被删除时触发, 配置管理时,没有删除配置的场景,即使删除了某个配置, 也需要重启服务, 所以不需要监听后做任何操作.
     */
    @Override
    protected void deleteListenerHandle(String dataPath) {
        // TODO Auto-generated method stub
        logger.info(Constants.formatLogMsg("delete key:" + dataPath, Constants.ZK_LOG_LEVEL_INFO));
    }


}
