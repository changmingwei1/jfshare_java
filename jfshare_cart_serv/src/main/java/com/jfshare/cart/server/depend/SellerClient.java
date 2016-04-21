package com.jfshare.cart.server.depend;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.jfshare.cart.util.ResultGenerator;
import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.finagle.thrift.seller.SellerResult;
import com.jfshare.finagle.thrift.seller.SellerRetParam;
import com.jfshare.finagle.thrift.seller.SellerServ;
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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.twitter.util.Await;

import javax.annotation.PostConstruct;

@Service
public class SellerClient {
	private Logger logger = LoggerFactory.getLogger(ProductClient.class);
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
		com.twitter.finagle.Service<ThriftClientRequest, byte[]> client = ClientBuilder.safeBuild(clientBuilder);
		client.isAvailable();
		service = new SellerServ.ServiceToClient(client, new TBinaryProtocol.Factory());
	}

	private Cache<String, Seller> contentcache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).maximumSize(1000)
			.build(new CacheLoader<String, Seller>() {
				@Override
				public Seller load(String key) throws Exception {
					return null;
				}
			});

	public Seller getSellerInfoByCache(final Integer sellerId) {
		long doneTime = System.currentTimeMillis();
		logger.info("getSellerInfoByCache param:::" + sellerId);
		Seller sllerInfo = null;
		try {
			sllerInfo = contentcache.get("getSellerInfo" + sellerId, new java.util.concurrent.Callable<Seller>() {
				@Override
				public Seller call() throws Exception {
					return getSellerInfo(sellerId);
				}
			});
		} catch (ExecutionException e) {
			logger.error(sellerId + ",getSellerInfoByCache接口异常", e);
		}
		logger.info(sellerId + ",getSellerInfoByCache接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return sllerInfo;
	}

	private Seller getSellerInfo(Integer sellerId) {
		long doneTime = System.currentTimeMillis();
		logger.info("getSellerInfo param:::" + sellerId);
		Seller sellerInfo = null;
		try {
			SellerRetParam param = new SellerRetParam(1);
			SellerResult sellerRet = Await.result(service.querySeller(sellerId, param));

			if(sellerRet != null && sellerRet.getResult().getCode() != ResultGenerator.SUCCESS_CODE){
				logger.error(sellerId + "querySeller接口错误编号" + sellerRet.getResult().getFailDescList().get(0).getFailCode());
			}
			if (sellerRet != null && sellerRet.getSeller() != null) {
				sellerInfo = sellerRet.getSeller();
			}
		} catch (Exception e) {
			logger.error(sellerId + ",querySeller接口异常", e);
		}

		logger.info(sellerId + ",querySeller接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return sellerInfo;
	}

}
