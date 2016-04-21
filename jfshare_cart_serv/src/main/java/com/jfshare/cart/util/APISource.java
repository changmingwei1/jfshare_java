package com.jfshare.cart.util;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Repository;

import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig.Yes;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Duration;

/**
 * API接口配置公共类
 * 
 * @author ZHUXINZE
 * 
 */
@Repository
public class APISource {

	private Map<Services, Service<ThriftClientRequest, byte[]>> mapClient = new HashMap<Services, Service<ThriftClientRequest, byte[]>>();

	/**
	 * 获取client
	 * 
	 * @param flag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getClient(Services flag) {
		// 连接池对象
		Service<ThriftClientRequest, byte[]> service = this.mapClient.get(flag);
		if (service == null) {
			synchronized (flag) {
				if (this.mapClient.get(flag) == null) {
					ClientBuilder<ThriftClientRequest, byte[], Yes, Yes, Yes> clientBuilder = ClientBuilder.get()
							.codec(ThriftClientFramedCodec.get()).hosts(new InetSocketAddress(flag.getIp(), flag.getPort()))
							.keepAlive(true).hostConnectionLimit(100).hostConnectionCoresize(50)
							.hostConnectionIdleTime(new Duration(2 * Duration.NanosPerSecond()))
							.hostConnectionMaxIdleTime(new Duration(10 * Duration.NanosPerSecond()))
							.hostConnectionMaxLifeTime(new Duration(300 * Duration.NanosPerSecond()))
							.tcpConnectTimeout(new Duration(2 * Duration.NanosPerSecond()));
					service = ClientBuilder.safeBuild(clientBuilder);
					this.mapClient.put(flag, service);
				}
			}
		}

		switch (flag) {
//		case STOCK:
//			return (T) new StockServ.ServiceToClient(service, new TBinaryProtocol.Factory());
//		case SELLER:
//			return (T) new SellerServ.ServiceToClient(service, new TBinaryProtocol.Factory());
//		case ORDER:
//			return (T) new OrderServ.ServiceToClient(service, new TBinaryProtocol.Factory());
//		case PRODUCT:
//			return (T) new ProductServ.ServiceToClient(service, new TBinaryProtocol.Factory());
//		case PROMOTION:
//    		return (T) new PromotionServ.ServiceToClient(service,new TBinaryProtocol.Factory());
		default:
			break;
		}
		return null;
	}

	/**
	 * 释放client中的链接
	 */
	@Deprecated
	public void releaseClient(TServiceClient client) {
		try {
			if (client == null) {
				return;
			}
			// 关闭输入链接
			TTransport inputTransport = client.getInputProtocol().getTransport();
			if (inputTransport.isOpen()) {
				inputTransport.close();
			}
			// 关闭输出链接
			TTransport outputTransport = client.getOutputProtocol().getTransport();
			if (outputTransport.isOpen()) {
				outputTransport.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 服务定义
	 */
	public enum Services {
		a("s1","s2", 2);
//		// 库存服务
//		STOCK("stock", Globals.STOCK_SERVER_IP, Globals.STOCK_SERVER_PORT),
//		// 商家服务
//		SELLER("seller", Globals.SELLER_SERVER_IP, Globals.SELLER_SERVER_PORT),
//		// 订单服务
//		ORDER("order", Globals.ORDER_SERVER_IP, Globals.ORDER_SERVER_PORT),
//		// 商品服务
//		PRODUCT("product", Globals.PRODUCT_SERVER_IP, Globals.PRODUCT_SERVER_PORT),
//		//排期服务
//		PROMOTION("promotion", Globals.PROMOTION_SERVER_IP, Globals.PROMOTION_SERVER_PORT);

		private String name;
		private String ip;
		private int port;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		private Services(String name, String ip, int port) {
			this.name = name;
			this.ip = ip;
			this.port = port;
		}
	}
}
