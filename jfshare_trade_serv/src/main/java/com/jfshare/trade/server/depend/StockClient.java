package com.jfshare.trade.server.depend;

import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.stock.LockStockResult;
import com.jfshare.finagle.thrift.stock.StockServ;
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
     * 并行调用锁定库存
     * @param tranIds sellerId:orderId map  事务keys
     * @param sellerDetailLockInfos sellerId:stockInfos map  待锁定库存
     * @return  失败的锁定项返回null
     */
    public List<LockInfo> lockStock(Map<Integer, String> tranIds, Map<Integer, List<LockInfo>> sellerDetailLockInfos) {
        List<TradeFeature<LockStockResult>> resultList = new ArrayList<TradeFeature<LockStockResult>>();
        try {
            CountDownLatch latch = new CountDownLatch(sellerDetailLockInfos.size());
            for (Map.Entry<Integer, List<LockInfo>> entry : sellerDetailLockInfos.entrySet()) {
                TradeFeature<LockStockResult> lockStockListener =
                        new TradeFeature<LockStockResult>(latch, tranIds.get(entry.getKey()), entry.getValue());
                service.lockStock(tranIds.get(entry.getKey()), entry.getValue()).addEventListener(lockStockListener);
                resultList.add(lockStockListener);
            }

            latch.await();
            if (latch.getCount() > 0) {
                logger.error("调用StockServ--lockStock--Parallel有误！");
            }
        } catch (Exception e) {
            logger.error("调用StockServ--lockStock失败！", e);
        }

        List<LockInfo> lockStockRets = new ArrayList<LockInfo>();
        for(TradeFeature<LockStockResult> lockStockRet : resultList) {
            if (lockStockRet != null && lockStockRet.getResult() != null) {
                LockStockResult stockResult = (LockStockResult) lockStockRet.getResult();
                if (stockResult.getResult() != null &&
                        (stockResult.getResult().getCode() == 0 ||
                                (stockResult.getResult().getCode() == 1 && stockResult.getResult().getFailDescList().get(0).getFailCode().equals("9004")))) {  //库存不足后续返回错误提示
                    lockStockRets.addAll(stockResult.getLockInfoList());
                } else {
                    lockStockRets.add(null);
                }
            } else {
                lockStockRets.add(null);
            }
        }

        return lockStockRets;
    }

    /**
     * 释放库存
     * @param sellerOrderIdsMap
     * @param lockInfos
     * @return
     */
    public boolean releaseStock(Map<Integer, String> sellerOrderIdsMap, Map<Integer, List<LockInfo>> lockInfos) {
        List<TradeFeature<Result>> resultList = new ArrayList<TradeFeature<Result>>();
        try {
            CountDownLatch latch = new CountDownLatch(sellerOrderIdsMap.size());
            for (Map.Entry<Integer, String> entry : sellerOrderIdsMap.entrySet()) {
                TradeFeature<Result> lockStockListener =
                        new TradeFeature<Result>(latch, entry.getValue(), lockInfos.get(entry.getKey()));
                service.releaseStock(entry.getValue(), lockInfos.get(entry.getKey())).addEventListener(lockStockListener);
                resultList.add(lockStockListener);
            }

            latch.await();
            if (latch.getCount() > 0) {
                logger.error("调用StockServ--releaseStock--Parallel有误！");
                logger.error("$$$$订单释放库存失败！tranIds:" + sellerOrderIdsMap + ", lockInfos:" + lockInfos);
            }
            return true;
        } catch (Exception e) {
            logger.error("调用StockServ--releaseStock失败！", e);
            logger.error("$$$$订单释放库存失败！tranIds:" + sellerOrderIdsMap + ", lockInfos:" + lockInfos);
        }

        return false;
    }
}
