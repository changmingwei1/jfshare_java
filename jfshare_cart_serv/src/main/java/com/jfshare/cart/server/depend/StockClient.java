package com.jfshare.cart.server.depend;

import java.util.ArrayList;
import java.util.List;

import com.jfshare.cart.util.Constant;
import com.jfshare.cart.util.ResultGenerator;
import com.jfshare.cart.util.StringUtil;
import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.finagle.thrift.stock.StockResult;
import com.jfshare.finagle.thrift.stock.StockServ;
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
public class StockClient {
	private Logger logger = LoggerFactory.getLogger(ProductClient.class);
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
		com.twitter.finagle.Service<ThriftClientRequest, byte[]> client = ClientBuilder.safeBuild(clientBuilder);
		client.isAvailable();
		service = new StockServ.ServiceToClient(client, new TBinaryProtocol.Factory());
	}
	

	public StockInfo getStockForSku(String productId, String skuNum) {
		long doneTime = System.currentTimeMillis();
		String param = StringUtil.join(productId, skuNum, Constant.SEPARATOR);
		logger.info("getStockForSku param:::" + param);
		StockInfo result = null;
		try {
			List<String> items = new ArrayList<String>();
			items.add(skuNum);
			StockResult s = Await.result(service.getStockForSku(productId, items));
			if (s != null && s.getResult().getCode() != ResultGenerator.SUCCESS_CODE) {
				String errorCode = s.getResult().getFailDescList().get(0).getFailCode();
				logger.error(param + ",getStockForSku接口错误编号" + errorCode);
			}
			if (s != null && s.getStockInfo() != null)
				result = s.getStockInfo();
		} catch (Exception e) {
			logger.error(param + ",getStockForSku接口异常", e);
		}
		logger.info(param + ",getStockForSku接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return result;
	}
}
