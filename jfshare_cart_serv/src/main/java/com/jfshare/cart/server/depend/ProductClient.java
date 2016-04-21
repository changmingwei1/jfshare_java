package com.jfshare.cart.server.depend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.jfshare.cart.util.Constant;
import com.jfshare.cart.util.ResultGenerator;
import com.jfshare.cart.util.StringUtil;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductResult;
import com.jfshare.finagle.thrift.product.ProductRetParam;
import com.jfshare.finagle.thrift.product.ProductServ;
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
		com.twitter.finagle.Service<ThriftClientRequest, byte[]> client = ClientBuilder.safeBuild(clientBuilder);
		client.isAvailable();
		service = new ProductServ.ServiceToClient(client, new TBinaryProtocol.Factory());
	}

	public Product queryProduct(String productId, ProductRetParam pParam) {
		long doneTime = System.currentTimeMillis();
		Product product = null;
		try {
			ProductResult s = Await.result(service.queryProduct(productId, pParam));
			if (s != null) {
				if (s.getResult().getCode() != ResultGenerator.SUCCESS_CODE) {
					String errorCode = s.getResult().getFailDescList().get(0).getFailCode();
					logger.error(productId + ",queryProduct接口错误编号" + errorCode);
					return product;
				}
				product = s.getProduct();
			}
		} catch (Exception e) {
			logger.error(productId + ",queryProduct接口错误编号异常", e);
		}

		logger.info(productId + ",queryProduct接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return product;
	}

	public Product queryHotSKU(String productId, String skuNum, ProductRetParam pParam) {
		long doneTime = System.currentTimeMillis();
		Product product = null;
		try {
			ProductResult s = Await.result(service.queryHotSKU(productId, skuNum, pParam));
			if (s != null) {
				if (s.getResult().getCode() != ResultGenerator.SUCCESS_CODE) {
					String errorCode = s.getResult().getFailDescList().get(0).getFailCode();
					logger.error(productId + ",queryHotSKU接口错误编号" + errorCode);
					return product;
				}
				product = s.getProduct();
			}
		} catch (Exception e) {
			logger.error(productId + ",queryHotSKU接口错误编号异常", e);
		}

		logger.info(productId + ",queryHotSKU接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return product;
	}
}
