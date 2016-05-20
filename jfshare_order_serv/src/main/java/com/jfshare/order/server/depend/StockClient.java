package com.jfshare.order.server.depend;

import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.stock.LockStockResult;
import com.jfshare.finagle.thrift.stock.StockServ;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.ridge.ConfigManager;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lenovo on 2015/10/31.
 */
@org.springframework.stereotype.Service
public class StockClient {
    private Logger logger = LoggerFactory.getLogger(StockClient.class);
    private StockServ.ServiceIface service = null;
    @Autowired
    private ConfigManager configManager;

    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "stock_serv_ips");
        String port = configManager.getConfigValue("jfx_public_client", "stock_port");
        StringBuilder hosts = new StringBuilder();
        String[] ipArray = ips.split(";");
        for(String ip : ipArray) {
            hosts.append(ip.trim()).append(":").append(port);
        }
        ClientBuilder<ThriftClientRequest, byte[], ClientConfig.Yes, ClientConfig.Yes, ClientConfig.Yes> clientBuilder = ClientBuilder.get()
                .codec(ThriftClientFramedCodec.get())   //设置解码器类型
                .hosts(hosts.toString())    //设置访问ip和端口
                .hostConnectionLimit(100)  //连接池
                //.timeout(new Duration(5000L))  //总的请求超时
                .retries(3)   // 重试次数
                .keepAlive(true)     //是否是长连接
                ;
        Service<ThriftClientRequest, byte[]> client = ClientBuilder.safeBuild(clientBuilder);
        client.isAvailable();
        service = new StockServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

    /**
     * 释放库存
     * @param transId sellerId:orderId 事务keys
     * @param orderInfos
     * @return
     */
    public void releaseStock(String transId, List<TbOrderInfoRecord> orderInfos) {
        List<LockInfo> lockInfos = buildLockInfoList(orderInfos);

        try {
            Result result = Await.result(service.releaseStock(transId, lockInfos));
            if(result == null || result.getCode() != 0) {
                logger.warn("releaseStock ==> {}, 取消订单释放库存失败，原因: {}", transId, result.getFailDescList());
            } else {
                logger.info("releaseStock ==> {}, 取消订单释放库存成功");
            }
        } catch (Exception e) {
            logger.error("调用StockServ--releaseStock失败！", e);
        }
    }

    /**
     * 支付成功释放库存锁定量
     * @param transId sellerId:orderId 事务keys
     * @param orderInfos
     * @return
     */
    public void releaseLockCount(String transId, List<TbOrderInfoRecord> orderInfos) {
        List<LockInfo> lockInfos = buildLockInfoList(orderInfos);

        try {
            Result result = Await.result(service.releaseLockCount(transId, lockInfos));
        } catch (Exception e) {
            logger.error("调用StockServ--releaseLockCount失败！", e);
        }
    }

    private List<LockInfo> buildLockInfoList(List<TbOrderInfoRecord> orderInfos) {
        List<LockInfo> lockInfos = new ArrayList<>();
        for(TbOrderInfoRecord oInfo : orderInfos) {
            LockInfo lockInfo = new LockInfo();
            lockInfo.setProductId(oInfo.getProductId());
            lockInfo.setApplyCount(oInfo.getCount());
            lockInfo.setSkuNum(oInfo.getSkuNum());
            lockInfo.setStorehouseId(oInfo.getStorehouseId());
            lockInfos.add(lockInfo);
        }

        return lockInfos;
    }
}
