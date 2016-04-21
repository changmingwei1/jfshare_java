package com.jfshare.cart.server.depend;

import com.jfshare.cart.util.NumberUtil;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.stock.StockServ;
import com.jfshare.finagle.thrift.trade.TradeServ;
import com.jfshare.ridge.ConfigManager;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.util.Await;

import javax.annotation.PostConstruct;

@Service
public class TradeClient {
	private Logger logger = LoggerFactory.getLogger(TradeClient.class);
	private TradeServ.ServiceIface service = null;
	@Autowired
	private ConfigManager configManager;

	@PostConstruct
	public void init() {
		String ips = configManager.getConfigValue("jfx_public_client", "trade_serv_ips");
		String port = configManager.getConfigValue("jfx_public_client", "trade_port");
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
		service = new TradeServ.ServiceToClient(client, new TBinaryProtocol.Factory());
	}

	
	/**
	 * 查询用户已购数量
	 * 
	 * @param userId
	 * @param productId
	 * @return
	 */
	public int buyCount(int userId, String productId) {
		long doneTime = System.currentTimeMillis();
		logger.info("buyCount param:::" + userId + "," + productId);

		int count = -1;
		try {
			StringResult ss = Await.result(service.buyCount(userId, productId));

			if (ss == null || ss.getResult() == null) {
				logger.warn(userId + "," + productId + "读取Trade服务返回NULL");
				return count;
			}

			if (ss.result.code != 0 || NumberUtil.parseInteger(ss.value) < 0) {
				logger.warn(userId + "," + productId + ",buyCount接口错误编号" + ss.getResult().getFailDescList().get(0).getFailCode());
				return count;
			}
			logger.info(userId + "," + productId + ",buyCount接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
			count = NumberUtil.parseInteger(ss.value);
		} catch (Exception e) {
			logger.error(userId + "," + productId + ",buyCount接口异常", e);
			count = -1;
		}

		return count;
	}
}
