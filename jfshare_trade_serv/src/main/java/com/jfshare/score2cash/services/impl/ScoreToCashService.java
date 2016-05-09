package com.jfshare.score2cash.services.impl;

import com.jfshare.finagle.thrift.trade.*;
import com.jfshare.score2cash.utils.Globals;
import com.jfshare.score2cash.utils.NumberUtil;
import com.jfshare.score2cash.utils.ResultGenerator;
import com.jfshare.score2cash.utils.StringUtil;
import com.jfshare.trade.server.depend.ScoreClient;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("scoretocashservice")
public class ScoreToCashService {

	private Logger logger = LoggerFactory.getLogger(ScoreToCashService.class);

	@Autowired
	private ScoreClient scoreClient;
	
	public ExchangeResult getExchangeScore(ExchangeParam param) {
		/** 用户为空 OR 金额为空 OR 金额小于0 OR 总金额不等于各商品金额之和-积分抵现部分金额*/
		double sumPrice = -NumberUtil.parseDouble(param.getAmount());
		for(ExchangeProduct exchangeProduct : param.getProductList())
			sumPrice += NumberUtil.parseDouble(exchangeProduct.getPrice());

		if (param == null || param.getUserId() <= 0 || StringUtil.empty(param.getAmount())
				|| NumberUtil.parseDouble(param.getAmount()) <= 0
				|| sumPrice != 0) {
			logger.info(param.getUserId() + "积分兑换失败：" + ResultGenerator.PARAM_ERROR);
			return ResultGenerator.createErrorExchangeResult(ResultGenerator.PARAM_ERROR);
		}

		ExchangeRule exchangeRule = getExchangeRule();

		/** 用户积分 */
		int totalScore = this.scoreClient.getBuyerScore(param.getUserId());

		if (totalScore == 0) {
			logger.info(param.getUserId() + "积分数量为0：" + ResultGenerator.SCORE_QUANTITY_IS_ZERO);
			return ResultGenerator.createExchangeResult("0", "0", PriceUtils.intToStr(0), null);
		}
		/** 积分服务异常 */
		if (totalScore == -1) {
			logger.info(param.getUserId() + "积分兑换失败：" + ResultGenerator.SCORE_SERVER_ERROR);
			return ResultGenerator.createErrorExchangeResult(ResultGenerator.SCORE_SERVER_ERROR);
		}

		/** 积分不足 */
		if (NumberUtil.parseInteger(param.getScore()) > totalScore) {
			logger.info(param.getUserId() + "积分兑换失败：" + ResultGenerator.SCORE_QUANTITY_ERROR);
			return ResultGenerator.createErrorExchangeResult(ResultGenerator.SCORE_QUANTITY_ERROR);
		}

		/** 订单免优惠免运费的总金额 */
		int amount = parseInteger(PriceUtils.strToInt(param.getAmount()));
		/** 最大可以抵现金额(单位：分) */
		int exchangeAmount = parseInteger(PriceUtils.strToInt(param.getAmount()) * Globals.SCORE_TO_CASH_RULE);
		/** 判断积分数量是否大于实际金额可兑换数量 */
		if (NumberUtil.parseInteger(param.getScore()) > changeS(exchangeAmount)) {
			logger.info(param.getUserId() + "积分兑换失败：" + ResultGenerator.SCORE_QUANTITY_PARAM_ERROR);
			return ResultGenerator.createErrorExchangeResult(ResultGenerator.SCORE_QUANTITY_PARAM_ERROR);
		}

		/** 可以使用的积分 */
		int exchangeScore = 0;
		if (StringUtil.empty(param.getScore())) // 默认最多可兑换的积分数
			exchangeScore = changeS(exchangeAmount);
		else
			exchangeScore = NumberUtil.parseInteger(param.getScore());

		/** 可兑换积分与总积分比较 */
		exchangeScore = exchangeScore > totalScore ? totalScore : exchangeScore;
		exchangeAmount = exchangeScore > totalScore ? changeY(totalScore) : changeY(exchangeScore);

		logger.info(param.getUserId() + "," + StringUtil.join(totalScore + "", exchangeScore + "", exchangeAmount + "", "|"));
		List<ExchangeProduct> productList = param.getProductList();
		if (productList == null || productList.size() <= 0) {
			logger.info(param.getUserId() + "商品列表为空");
			return ResultGenerator.createExchangeResult(totalScore + "", exchangeScore + "", PriceUtils.intToStr(exchangeAmount), null);
		}
		/** 商品分配积分 */
		List<ExchangeDetail> list = setProductScore(productList, amount, exchangeScore, exchangeAmount, exchangeRule);
		return ResultGenerator.createExchangeResult(totalScore + "", exchangeScore + "", PriceUtils.intToStr(exchangeAmount), list);
	}

	public ExchangeResult score2cash(ExchangeParam param) {
		return getExchangeScore(param);
	}

	/**
	 * 
	 * 
	 * @param productList
	 * @param amount
	 *            订单免优惠免运费的总金额
	 * @param exchangeScore
	 *            可以使用的积分
	 * @param exchangeAmount
	 *            抵现金额
	 * @return
	 */
	private List<ExchangeDetail> setProductScore(List<ExchangeProduct> productList, int amount, int exchangeScore, int exchangeAmount, ExchangeRule rule) {
		if (productList == null || productList.size() <= 0)
			return null;
		List<ExchangeDetail> eList = new ArrayList<ExchangeDetail>();
		int s = 0; // 累计积分
		int a = 0; // 累计金额
		/** 按商品拆分积分和金额 */
		for (int i = 0; i < productList.size(); i++) {
			ExchangeProduct e = productList.get(i);
			ExchangeDetail d = new ExchangeDetail();
			d.setProductId(e.getProductId());
			d.setSkuNum(e.getSkuNum());
			d.setPrice(e.getPrice());

			/** 每个商品可使用积分兑换的钱数(单位分) */
			int pUseAmount = 0;
			/** 每个商品可以使用多少积分兑换 */
			int pUseScore = 0;
			if (i == productList.size() - 1) { // 最后一次用总数量减去已经分配的钱和积分(避免四舍五入不相等的情况)
				pUseAmount = exchangeAmount - a;
				pUseScore = exchangeScore - s;
			} else {
				double pPrice = PriceUtils.strToInt(e.getPrice());
				pUseScore = parseInteger((pPrice / amount) * exchangeScore);
				pUseAmount = changeY(pUseScore);
				
				a += pUseAmount;
				s += pUseScore;
			}

			d.setScore(pUseScore + "");
			d.setAmount(PriceUtils.intToStr(pUseAmount));
			d.setRule(rule);
			eList.add(d);
		}
		return eList;
	}

	/**
	 * 兑换规则
	 * 
	 * @return
	 */
	public ExchangeRule getExchangeRule() {
		ExchangeRule rule = new ExchangeRule();
		rule.setCash("1");
		rule.setPercent(Globals.SCORE_TO_CASH_RULE + "");
		rule.setScore(Globals.SCORE_PER_YUAN + "");
		return rule;
	}

	/**
	 * 四舍五入
	 * 
	 * @param num
	 * @return
	 */
	private int parseInteger(double num) {
		BigDecimal b = new BigDecimal(num);
		return b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}

	/**
	 * 积分转换成金额(单位：分)
	 * 
	 * @param score
	 * @return
	 */
	private int changeY(int score) {
		if (score <= 0)
			return 0;
		return score * 100 / Globals.SCORE_PER_YUAN;
	}

	/**
	 * 金额(单位：分) 转换成 积分
	 * 
	 * @param amount
	 * @return
	 */
	private int changeS(int amount) {
		if (amount <= 0)
			return 0;
		return parseInteger(amount * Globals.SCORE_PER_YUAN / 100);
	}

	public static void main(String[] args) {
	}
}
