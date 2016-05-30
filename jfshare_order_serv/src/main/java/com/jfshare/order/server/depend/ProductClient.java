package com.jfshare.order.server.depend;

import com.jfshare.finagle.thrift.product.ProductCard;
import com.jfshare.finagle.thrift.product.ProductCardParam;
import com.jfshare.finagle.thrift.product.ProductCardResult;
import com.jfshare.finagle.thrift.product.ProductServ;
import com.jfshare.order.exceptions.BaseException;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.order.util.FailCode;
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
import java.util.List;

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
                //.timeout(new Duration(5000L))  //总的请求超时间
                .retries(3)   // 重试次数
                .keepAlive(true)     //是否是长连接
                ;
        Service<ThriftClientRequest, byte[]> client = ClientBuilder.safeBuild(clientBuilder);
        client.isAvailable();
        service = new ProductServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

    /**
     * 虚拟商品发货， 获取卡密
     * @param orderModel
     * @return
     */
    public List<ProductCard> getProductCard(OrderModel orderModel) throws Exception {
        ProductCardParam param = new ProductCardParam();
        TbOrderInfoRecord orderInfo = orderModel.getTbOrderInfoList().get(0);
        param.setProductId(orderInfo.getProductId());
        param.setSkuNum(orderInfo.getSkuNum());
        param.setNum(orderInfo.getCount());
        param.setTransactionId(orderModel.getOrderId());
        param.setBuyerId(orderModel.getUserId());
        ProductCardResult result = null;
        result = Await.result(service.getProductCard(param));
        if(result == null || result.getResult().getCode() != 0) {
            throw new BaseException(FailCode.DELIVER_GET_PRODUCTCARD_FAIL);
        }

        logger.info("成功获取卡密[{}]个", result.getCardListSize());
        return result.getCardList();
    }
}
