package com.jfshare.elasticsearch.drive;

import com.jfshare.elasticsearch.drive.utils.ConcurrentHashSet;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class ESClient {

    protected static final Logger logger = LoggerFactory.getLogger(ESClient.class);

    private TransportClient transportClient;

    private static ConcurrentHashSet<TransportClient> transportClientSet = new ConcurrentHashSet<>();
    private static ConcurrentHashSet<ESBulker> esBulkerSet = new ConcurrentHashSet<>();

    public ESBulker getEsBulker() {
        return esBulker;
    }

    private ESBulker esBulker;

    /**
     * ESClient 构造函数,涉及到bulk操作采用默认数值
     * @param clusterName 集群名称
     * @param hosts transportClient的地址集群 eg: "192.168.10.235:9300,192.168.10.236:9300,192.168.10.237:9300"
     */
    public ESClient(String clusterName, String hosts){
        this(clusterName,hosts, null, null, null, true);
    }


    /**
     * ESClient 构造函数,涉及到bulk操作采用自定义配置
     * @param clusterName 集群名称
     * @param hosts transportClient的地址集群 eg: "192.168.10.235:9300,192.168.10.236:9300,192.168.10.237:9300"
     * @param bulkActions 文档数量刷新阈值, 默认1000
     * @param flushInterval flush间隔,单位s
     * @param concurrentRequests 并发请求数,默认1
     * @param sniff 是否开启嗅探
     */
    public ESClient(String clusterName, String hosts,Integer bulkActions, Integer flushInterval, Integer concurrentRequests, boolean sniff){
        Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).put("client.transport.sniff", sniff).build();
        transportClient = TransportClient.builder().settings(settings).build();

        String[] socketAddress = hosts.split(",");
        for (int i = 0; i < socketAddress.length; i++) {
            String ip = socketAddress[i].split(":")[0];
            int port = Integer.parseInt(socketAddress[i].split(":")[1]);
            transportClient.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(ip, port)));
        }
        esBulker = new ESBulker(transportClient, bulkActions, flushInterval, concurrentRequests);
    }


    /**
     * 批量索引数据, 并自己指定document的id
     * @param index
     * @param type
     * @param id
     * @param json
     * @throws Exception
     */
    public void addOrUpdateByCustomId(String index, String type, String id, String json) throws Exception {
        this.esBulker.addOrUpdateByCustomId(index, type, id, json);
    }


    /**
     * 批量索引数据,es自动生成document id
     * @param index
     * @param type
     * @param json
     * @throws Exception
     */
    public void add(String index, String type, String json) throws Exception {
        this.esBulker.add(index, type, json);
    }


    /**
     * 批量删除数据, 并自己指定document的id
     * @param index
     * @param type
     * @param id
     * @throws Exception
     */
    public void delete(String index, String type, String id) throws Exception {
        this.esBulker.delete(index, type, id);
    }




    public TransportClient getTransportClient() {
        return transportClient;
    }

    public void close() {
        if (this.transportClient != null) {
            this.transportClient.close();
        }
    }


    /**
     * 添加默认资源回收钩子
     */
    public void addDefaultShutdownHook(){
        transportClientSet.add(transportClient);
        esBulkerSet.add(esBulker);
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //程序关闭时, 回收ESClient的连接
                logger.info("Run ESClient shutdown hook now. 开始关闭创建的ESClient, [" + transportClientSet.size() + "]个, 禁止使用kill -9 关闭 !!! ");
                for (ESBulker bulker : esBulkerSet) {
                    if (bulker != null) {
                        try {
                            bulker.close();
                        } catch (Exception ex) {
                            logger.error("bulker close failures", ex);
                        }
                    }
                }
                for (TransportClient tc : transportClientSet) {
                    if (tc != null) {
                        try {
                            tc.close();
                        } catch (Exception ex) {
                            logger.error("ESClient close failures", ex);
                        }
                    }
                }
                logger.info("关闭创建的[" + transportClientSet.size() + "]个ESClient结束! ");
            }
        }, "ESClientShutdownHook"));
    }
}
