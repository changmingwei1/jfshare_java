package com.jfshare.trade.server.depend;

import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.finagle.thrift.product.ProductResult;
import com.jfshare.finagle.thrift.product.ProductRetParam;
import com.jfshare.finagle.thrift.product.ProductServ;
import com.jfshare.finagle.thrift.product.ProductSkuParam;
import com.jfshare.ridge.ConfigManager;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lenovo on 2015/10/31.
 */
@org.springframework.stereotype.Service
public class ProductClient {
    private Logger logger = LoggerFactory.getLogger(ProductClient.class);
    private ProductServ.ServiceIface service = null;
    @Autowired
    private ConfigManager configManager;

    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "product_serv_ips");
        String port = configManager.getConfigValue("jfx_public_client", "product_port");
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
        service = new ProductServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

    public List<ProductResult> queryHotSKUsByParallel(List<OrderInfo> productInfos) {
        List<TradeFeature<ProductResult>> resultList = new ArrayList<TradeFeature<ProductResult>>();
        ProductRetParam param = new ProductRetParam();
        param.setBaseTag(1);
        param.setAttributeTag(0);
        param.setSkuTemplateTag(0);
        param.setSkuTag(0);
        try {
            CountDownLatch latch = new CountDownLatch(productInfos.size());
            for (OrderInfo productInfo : productInfos) {
                TradeFeature<ProductResult> productHotSkuListener = new TradeFeature<ProductResult>(latch, productInfo.getProductId(), productInfo.getSkuNum(), param);
                ProductSkuParam skuParam = new ProductSkuParam(productInfo.getProductId(), productInfo.getSkuNum(), productInfo.getStorehouseId());
                service.queryHotSKUV1(skuParam, param).addEventListener(productHotSkuListener);
                resultList.add(productHotSkuListener);
            }

            latch.await();
            if (latch.getCount() > 0) {
                logger.error("调用ProductServ--queryHotSKU--Parallel有误！");
            }
        } catch (Exception e) {
            logger.error("调用ProductServ--queryHotSKU失败！", e);
        }

        List<ProductResult> productRets = new ArrayList<ProductResult>();
        for(TradeFeature<ProductResult> productRet : resultList) {
            if (productRet != null && productRet.getResult() != null) {
                productRets.add((ProductResult)productRet.getResult());
            } else {
                productRets.add(null);
            }
        }

        return productRets;
    }
}
