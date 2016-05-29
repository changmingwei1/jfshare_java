package com.jfshare.task.server.depend;

import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.ScoreServ;
import com.jfshare.finagle.thrift.score.ScoreTrade;
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
public class ScoreClient {

	private static Logger logger = LoggerFactory.getLogger(ScoreClient.class);
	private ScoreServ.ServiceIface service = null;
	@Autowired
	private ConfigManager configManager;

	@PostConstruct
	public void init() {
		String ips = configManager.getConfigValue("jfx_public_client", "score_serv_ips");
		String port = configManager.getConfigValue("jfx_public_client", "score_serv_port");
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
		service = new ScoreServ.ServiceToClient(client, new TBinaryProtocol.Factory());
	}

	public void incomeScore(int buyerId, int score, String orderId) {
		long doneTime = System.currentTimeMillis();
		if (buyerId <= 0)
			return;
		try {
			ScoreTrade scoreTrade = new ScoreTrade();
			scoreTrade.setAmount(score);
			scoreTrade.setType(5);
			scoreTrade.setTrader(3);
			scoreTrade.setTradeId("bonus_" + orderId);
			scoreTrade.setInOrOut(1);
			scoreTrade.setUserId(buyerId);
			StringResult scoreResult = Await.result(this.service.income(scoreTrade));
			if(scoreResult != null && scoreResult.getResult().getCode() == 0) {
				logger.info("{},购物赠送用户积分：{}", buyerId, score);
			} else{
				logger.warn("{},购物赠送积分失败:{}", buyerId, scoreResult);
			}
		} catch (Exception e) {
			logger.error(buyerId + "积分服务异常!", e);
		}
		logger.info("{},积分服务income接口调用时间：{} ms!!", buyerId, (System.currentTimeMillis() - doneTime));
	}

	public void reduceScore(int buyerId, int score, String orderId) {
		long doneTime = System.currentTimeMillis();
		if (buyerId <= 0)
			return;
		try {
			ScoreTrade scoreTrade = new ScoreTrade();
			scoreTrade.setAmount(score);
			scoreTrade.setType(5);
			scoreTrade.setInOrOut(2);
			scoreTrade.setTrader(3);
			scoreTrade.setTradeId("bonus_" + orderId);
			scoreTrade.setUserId(buyerId);
			StringResult scoreResult = Await.result(this.service.expenditure(scoreTrade));
			if(scoreResult != null && scoreResult.getResult().getCode() == 0) {
				logger.info("{},扣减购物赠送用户积分：{}", buyerId, score);
			} else{
				logger.warn("{},扣减购物赠送积分失败:{}", buyerId, scoreResult);
			}
		} catch (Exception e) {
			logger.error(buyerId + "积分服务异常!", e);
		}
		logger.info("{},积分服务expenditure接口调用时间：{} ms!!", buyerId, (System.currentTimeMillis() - doneTime));
	}

}
