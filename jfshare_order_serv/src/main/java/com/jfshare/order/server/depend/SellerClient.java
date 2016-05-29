package com.jfshare.order.server.depend;

import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.finagle.thrift.pay.PayServ;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.seller.SellerServ;
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

/**
 * Created by lenovo on 2015/10/31.
 */
@org.springframework.stereotype.Service
public class SellerClient {
    private Logger logger = LoggerFactory.getLogger(SellerClient.class);
    private SellerServ.ServiceIface service = null;
    @Autowired
    private ConfigManager configManager;

    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "seller_serv_ips");
        String port = configManager.getConfigValue("jfx_public_client", "seller_port");
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
        service = new SellerServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

    public boolean insertUserSellerReal(int userId, int sellerId) {
        try {
            Result res = Await.result(service.insertUserSellerReal(String.valueOf(userId), String.valueOf(sellerId)));
            if (res.getCode() == 0) {
                return true;
            } else {
                logger.warn("调用SellerServ--insertUserSellerReal,userId=" + userId + ",sellerId=" + sellerId + ",失败结果=" + res);
            }
        } catch (Exception e) {
            logger.error("调用SellerServ--insertUserSellerReal失败！", e);
        }

        return false;
    }
}
