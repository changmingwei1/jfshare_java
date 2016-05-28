package com.jfshare.product.dependserv;

import com.jfshare.finagle.thrift.manager.ManagerServ;
import com.jfshare.finagle.thrift.manager.ProductOpt;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.product.util.asynclog.LogCommon;
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
 * Created by Lenovo on 2015/12/19.
 */
@org.springframework.stereotype.Service(value = "managerClient")
public class ManagerClient {
    private Logger logger = LoggerFactory.getLogger(ManagerClient.class);
    private ManagerServ.ServiceIface managerServRef = null;
    @Autowired
    private ConfigManager configManager;

    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "manager_ips", "120.24.153.155");
        String port = configManager.getConfigValue("jfx_public_client", "manager_port", "1988");
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
        managerServRef = new ManagerServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

    public Result logProductOpt(ProductOpt productOpt) {
        Result ret = null;
        try {
            ret = Await.result(managerServRef.logProductOpt(productOpt));
        } catch (Exception e) {
            LogCommon.asyncProductOptLogsQueue.offer(productOpt);
            logger.error("调用ManagerServ--logProductOpt失败！ 再次放入异步队列", e);
        }

        return ret;
    }
}
