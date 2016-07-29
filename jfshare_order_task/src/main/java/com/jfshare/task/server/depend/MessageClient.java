package com.jfshare.task.server.depend;

import com.jfshare.finagle.thrift.message.MessageServ;
import com.jfshare.finagle.thrift.message.PushMessage;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.ridge.ConfigManager;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class MessageClient {

	private static Logger logger = LoggerFactory.getLogger(MessageClient.class);
	private MessageServ.ServiceIface service = null;
	@Autowired
	private ConfigManager configManager;

	@PostConstruct
	public void init() {
		String ips = configManager.getConfigValue("jfx_public_client", "message_serv_ips");
		String port = configManager.getConfigValue("jfx_public_client", "message_serv_port");
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
		com.twitter.finagle.Service<ThriftClientRequest, byte[]> client = ClientBuilder.safeBuild(clientBuilder);
		client.isAvailable();
		service = new MessageServ.ServiceToClient(client, new TBinaryProtocol.Factory());
	}

	public void pushMessage(int userId, String title, String msg, String orderType) {
		long doneTime = System.currentTimeMillis();
		try {
			PushMessage messageInfo = new PushMessage();
			messageInfo.setAlert(msg);
			messageInfo.setContent(msg);
			messageInfo.setObjType(1);
			messageInfo.setTitle(title);
			Result result = Await.result(this.service.pushMessageInfo(String.valueOf(userId), messageInfo, orderType));
			if(result != null && result.getCode() == 0) {
				logger.info("userId={}, pushMessage={}, 推送消息成功", userId, messageInfo);
			} else{
				logger.warn("userId={}, pushMessage={}, 推送消息失败", userId, messageInfo);
			}
		} catch (Exception e) {
			logger.error(userId + "访问message服务异常!", e);
		}
		logger.info("{},消息推送服务pushMessage接口调用时间：{} ms!!", userId, (System.currentTimeMillis() - doneTime));
	}
}
