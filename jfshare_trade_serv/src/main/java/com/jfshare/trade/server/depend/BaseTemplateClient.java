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
import com.jfshare.score2cash.utils.NumberUtil;
import com.jfshare.utils.PriceUtils;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;
import org.apache.commons.lang3.math.NumberUtils;
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

	/**
	 * 计算运费
	 * @param buyInfo
	 * @param orders
     * @return
     */
	public CalculatePostageResult calcPostage(BuyInfo buyInfo, List<Order> orders) {
		CalculatePostageResult result = null;
		long doneTime = System.currentTimeMillis();
		CalculatePostageParam param = new CalculatePostageParam();
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
					productPostageBasic.setWeight(NumberUtils.toDouble(orderInfo.getWeight())*orderInfo.getCount());
					productPostageMap.put(orderInfo.getProductId(), productPostageBasic);
				} else {
					productPostageBasic.setAmount(plusOrderInfoAmount(productPostageBasic.getAmount(), calcOrderInfoAmount(orderInfo)));
					productPostageBasic.setWeight(plusOrderInfoWeight(productPostageBasic.getWeight(), orderInfo.getWeight()));
					productPostageBasic.setNumber(productPostageBasic.getNumber() + orderInfo.getCount());
				}

			}

			SellerPostageBasic sellerPostageBasic = new SellerPostageBasic();
			sellerPostageBasic.setSellerId(order.getSellerId());
			for(String key : productPostageMap.keySet()) {
				sellerPostageBasic.addToProductPostageBasicList(productPostageMap.get(key));
			}
			param.addToSellerPostageBasicList(sellerPostageBasic);
		}

		param.setSendToProvince(String.valueOf(orders.get(0).getDeliverInfo().getProvinceId()));

		try {
			result = Await.result(this.service.calculatePostage(param));
			if(result != null && result.getResult().getCode() == 0) {
				logger.info("获取计算后总运费：{}", result.getTotalPostage());
			} else{
				logger.warn(",计算运费失败:{}", result);
			}
		} catch (Exception e) {
			logger.error("计算运费发生异常!", e);
		}
		logger.info("调用basicTemplate服务calcPostage接口调用时间：{} ms!!", (System.currentTimeMillis() - doneTime));
		return result;
	}

	private double plusOrderInfoWeight(double weight, String weight1) {
		return weight + NumberUtils.toDouble(weight1);
	}

	private String plusOrderInfoAmount(String p1, String p2) {
		int plus1 = PriceUtils.strToInt(p1);
		int plus2 = PriceUtils.strToInt(p2);

		return PriceUtils.intToStr(plus1 + plus2);
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
