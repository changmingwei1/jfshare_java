package com.jfshare.order.server.depend;

import com.jfshare.finagle.thrift.common.CommonServ;
import com.jfshare.finagle.thrift.common.ShortMsg;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.stock.LockInfo;
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

/**
 * Created by lenovo on 2015/10/31.
 */
@org.springframework.stereotype.Service
public class CommonClient {
    private Logger logger = LoggerFactory.getLogger(CommonClient.class);
    private CommonServ.ServiceIface service = null;
    @Autowired
    private ConfigManager configManager;

    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "common_serv_ips");
        String port = configManager.getConfigValue("jfx_public_client", "common_port");
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
        service = new CommonServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

    /**
     * 发送短信
     * @param mobile
     * @param content
     * @return
     */
    public void sendMsg(String mobile, String content) {
        ShortMsg shortMsg = new ShortMsg(mobile, content);

        try {
            Result result = Await.result(service.sendMsg(shortMsg));
            if(result==null || result.getCode() != 0) {
                logger.error("发送短消息失败，手机号：{}, 消息内容：{}", mobile, content.replaceAll("\\d", "*"));
            } else {
                logger.error("发送短消息成功，手机号：{}, 消息内容：{}", mobile, content.replaceAll("\\d", "*"));
            }
        } catch (Exception e) {
            logger.error("调用commonServ--发送短消息失败！", e);
        }
    }
}
