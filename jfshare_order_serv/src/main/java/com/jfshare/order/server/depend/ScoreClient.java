package com.jfshare.order.server.depend;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.ScoreResult;
import com.jfshare.finagle.thrift.score.ScoreServ;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.order.dao.IOrderEs;
import com.jfshare.order.model.EsScore;
import com.jfshare.order.util.ConstantUtil;
import com.jfshare.order.util.FailCode;
import com.jfshare.ridge.ConfigManager;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service
public class ScoreClient {

	private static Logger logger = LoggerFactory.getLogger(ScoreClient.class);
	private ScoreServ.ServiceIface service = null;
	@Autowired
	private ConfigManager configManager;

	@Autowired
	private IOrderEs orderEs;

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

	public int getBuyerScore(int buyerId) {
		long doneTime = System.currentTimeMillis();
		if (buyerId <= 0)
			return -1;
		int score = 0;
		try {
			ScoreResult scoreResult = Await.result(this.service.getScore(buyerId));
			if(scoreResult != null && scoreResult.getResult().getCode() == 0) {
				score = scoreResult.getSroce().getAmount();
				logger.info("{},用户积分：{}", buyerId, score);
			} else{
				logger.warn("{},获取用户积分失败:{}", buyerId, scoreResult);
			}
		} catch (Exception e) {
			logger.error(buyerId + "积分服务异常!", e);
			score = -1;
		}
		logger.info("{},积分服务user_score接口调用时间：{} ms!!", buyerId, (System.currentTimeMillis() - doneTime));
		return score;
	}

	public List<FailDesc> reduceScore(int buyerId, int score, String orderBatch, ConstantUtil.SCORE_TYPE scoreType, String orderId) {
		List<FailDesc> failDescs = new ArrayList<>();
		long doneTime = System.currentTimeMillis();
		if (buyerId <= 0) {
			failDescs.add(FailCode.PARAM_ERROR);
			return failDescs;
		}

		try {
			if(score > 0) {
				ScoreTrade scoreTrade = new ScoreTrade();
				String transId = StringUtils.isBlank(orderId) ? orderBatch : orderId;
				scoreTrade.setTradeId(transId);
				scoreTrade.setAmount(score);
				scoreTrade.setInOrOut(2);
				scoreTrade.setTrader(3);
				scoreTrade.setType(scoreType.getEnumVal());
				scoreTrade.setUserId(buyerId);
				StringResult scoreResult = Await.result(this.service.expenditure(scoreTrade));
				if(scoreResult != null && scoreResult.getResult().getCode() == 0) {
					logger.info("userId={}, type={}, 扣减用户积分：{}", buyerId, scoreType, score);
				} else{
					logger.warn("userId={}, type={}, 扣减用户积分失败:{}", buyerId, scoreType, scoreResult);
					failDescs.add(FailCode.PAY_SCORE_REDUCE_FAIL);
					return failDescs;
				}
			}
			orderEs.addScoreRecord(new EsScore(orderBatch, orderId, buyerId, score, scoreType));
		} catch (Exception e) {
			logger.error(buyerId + ", 积分服务异常!", e);
			failDescs.add(FailCode.PAY_SCORE_REDUCE_FAIL);
			return failDescs;
		}
		logger.info("userId={}, 积分服务expenditure接口调用时间：{} ms!!", buyerId, (System.currentTimeMillis() - doneTime));
		return null;
	}

	public void incomeScore(int buyerId, int score, String orderBatch, ConstantUtil.SCORE_TYPE scoreType, String orderId) {
		long doneTime = System.currentTimeMillis();
		if (buyerId <= 0)
			return;
		try {
			if(score > 0) {
				ScoreTrade scoreTrade = new ScoreTrade();
				String transId = StringUtils.isBlank(orderId) ? orderBatch : orderId;
				scoreTrade.setTradeId(transId);
				scoreTrade.setAmount(score);
				scoreTrade.setInOrOut(1);
				scoreTrade.setTrader(3);
				scoreTrade.setType(scoreType.getEnumVal());
				scoreTrade.setUserId(buyerId);
				StringResult scoreResult = Await.result(this.service.income(scoreTrade));
				if(scoreResult != null && scoreResult.getResult().getCode() == 0) {
					logger.info("userId={}, type={}, 返还用户积分：{}", buyerId, scoreType, score);
				} else{
					logger.warn("userId={}, type={}, 返还用户积分失败:{}", buyerId, scoreType, scoreResult);
					return;
				}
			}
			orderEs.addScoreRecord(new EsScore(orderBatch, orderId, buyerId, score, scoreType));
		} catch (Exception e) {
			logger.error(buyerId + ", 积分服务异常!", e);
			score = -1;
		}
		logger.info("userId={}, 积分服务expenditure接口调用时间：{} ms!!", buyerId, (System.currentTimeMillis() - doneTime));
	}

}
