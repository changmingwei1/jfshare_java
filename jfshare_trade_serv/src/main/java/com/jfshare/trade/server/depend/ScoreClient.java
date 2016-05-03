package com.jfshare.trade.server.depend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ScoreClient {

	private static Logger LOGGER = LoggerFactory.getLogger(ScoreClient.class);

//	@Resource(name = "scoreReference")
//	private Score.ServiceIface scoreReference;
	
	public int getBuyerScore(int buyerId) {
		long doneTime = System.currentTimeMillis();
		if (buyerId <= 0)
			return -1;
		int score = 0;
		try {
//			score = Await.result(this.scoreReference.user_score(buyerId));
			LOGGER.info(buyerId + ",用户积分：" + score);
		} catch (Exception e) {
			LOGGER.error(buyerId + "积分服务异常!", e);
			score = -1;
		}
		LOGGER.info(buyerId + ",积分服务user_score接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return score;
	}

}
