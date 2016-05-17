package com.jfshare.trade.server.depend;

import com.jfshare.finagle.thrift.baseTemplate.*;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.ScoreResult;
import com.jfshare.finagle.thrift.score.ScoreServ;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.finagle.thrift.trade.BuyInfo;
import com.jfshare.ridge.ConfigManager;
import com.jfshare.utils.PriceUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BaseTemplateClient {

	private static Logger logger = LoggerFactory.getLogger(BaseTemplateClient.class);
	private BaseTemplateServ.ServiceIface service = null;
	@Autowired
	private ConfigManager configManager;

	@PostConstruct
	public void init() {
		String ips = configManager.getConfigValue("jfx_public_client", "template_serv_ips");
		String port = configManager.getConfigValue("jfx_public_client", "template_serv_port");
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
		service = new BaseTemplateServ.ServiceToClient(client, new TBinaryProtocol.Factory());
	}

	public int calcPostage(BuyInfo buyInfo, List<Order> orders) {

		long doneTime = System.currentTimeMillis();

		List<SellerPostageBasic> sellerPostageBasics = new ArrayList<SellerPostageBasic>();
		for(Order order : orders) {
			Map<String, ProductPostageBasic> productPostageMap = new HashMap<String, ProductPostageBasic>();
			for(OrderInfo orderInfo : order.getProductList()) {
				ProductPostageBasic productPostageBasic = productPostageMap.get(orderInfo.getProductId());
				if(productPostageBasic == null) {
					productPostageBasic = new ProductPostageBasic();
					productPostageBasic.setProductId(orderInfo.getProductId());
					productPostageBasic.setAmount(calcOrderInfoAmount(orderInfo));
					productPostageBasic.setNumber(orderInfo.getCount());
					productPostageBasic.setTemplateId(orderInfo.getPostageTemplateId());
					productPostageBasic.setWeight(orderInfo.get)
				}
			}
			SellerPostageBasic sellerPostageBasic = new SellerPostageBasic();
			sellerPostageBasic.setSellerId(order.getSellerId());
		}

		int postage = 0;
		try {
			CalculatePostageParam param = new CalculatePostageParam();
			CalculatePostageResult result = Await.result(this.service.calculatePostage(param));
			if(result != null && result.getResult().getCode() == 0) {
				postage = PriceUtils.strToInt(result.getValue());
				logger.info("{},用户积分：{}", buyerId, postage);
			} else{
				logger.warn("{},计算运费是吧:{}", buyerId, scoreResult);
			}
		} catch (Exception e) {
			logger.error(buyerId + "积分服务异常!", e);
			score = -1;
		}
		logger.info("{},积分服务user_score接口调用时间：{} ms!!", buyerId, (System.currentTimeMillis() - doneTime));
		return score;
	}

	/**
	 * 计算商品支付金额
	 * @param orderInfo
	 * @return
     */
	private String calcOrderInfoAmount(OrderInfo orderInfo) {
		int curPrice = PriceUtils.strToInt(orderInfo.getCurPrice());
		return PriceUtils.intToStr(curPrice * orderInfo.getCount());
	}
}
