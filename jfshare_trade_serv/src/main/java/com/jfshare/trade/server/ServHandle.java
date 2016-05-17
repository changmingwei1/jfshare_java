package com.jfshare.trade.server;

import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.product.ProductResult;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.trade.*;
import com.jfshare.score2cash.services.impl.ScoreToCashService;
import com.jfshare.score2cash.utils.ResultGenerator;
import com.jfshare.trade.commons.enums.ResourseOpt;
import com.jfshare.trade.service.impl.ITradeSvc;
import com.jfshare.trade.util.CheckUtil;
import com.jfshare.trade.util.FailCode;
import com.jfshare.trade.util.TradeUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service(value="handler")
public class ServHandle implements TradeServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Autowired
    private ITradeSvc tradeSvcImpl;
	@Autowired
	private CheckUtil checkUtil;
	@Autowired
	public ScoreToCashService scoretocashservice;


	@Override
	//TODO 验证SellerId、mq
	public OrderConfirmResult orderConfirm(BuyInfo buyInfo) throws TException {
		boolean isConfirmSucc = false;
		OrderConfirmResult createOrderResult = new OrderConfirmResult();
		Result result = new Result();
		result.setCode(0);
		createOrderResult.setResult(result);
		Map<Integer, String> sellerOrderIdsMap = new HashMap<Integer, String>();
		Stack<ResourseOpt> resourseOpts = new Stack<ResourseOpt>();
		try {
			logger.info("确定订单----参数,buyInfo" + buyInfo);
			//region 基本参数校验
		    List<FailDesc> paramFailList = checkUtil.orderConfirmParam(buyInfo);
            if (paramFailList.size() > 0) {
			    logger.error("$$$$确定订单----参数校验错误！fails=" + paramFailList);
				FailCode.addFails(result, paramFailList);
				return createOrderResult;
			}
			logger.info("确认订单----验证参数成功");
			//endregion 基本参数校验

			//region 收货地址
			AddressInfo addressInfo = checkUtil.orderConfirmAddress(buyInfo);
			if (addressInfo == null) {
				logger.error("$$$$确定订单----收货地址验证错误！fails=" + addressInfo);
				FailCode.addFails(result, FailCode.RECEIVER_ADDRESS_NULL_ERROR);
				return createOrderResult;
			}
			logger.info("确认订单----获取收货地址成功");
			//endregion 收货地址

			//获取商品sku
			List<ProductResult> productRets = checkUtil.queryHotSKUsByParallel(buyInfo);
			logger.info("确认订单----获取商品sku信息成功");

			//验证商品sku
			List<FailDesc> productFailList = checkUtil.orderConfirmProduct(buyInfo, productRets);
			if (productFailList.size() > 0) {
				logger.error("$$$$确定订单----商品信息校验错误！fails=" + productFailList);
				FailCode.addFails(result, productFailList);
				return createOrderResult;
			}
			logger.info("确认订单----验证商品sku信息成功");

			//生成订单号
			sellerOrderIdsMap = checkUtil.orderConfirmGerOrderId(buyInfo);
			logger.info("确认订单----生成订单号成功");

			//锁定库存
			List<LockInfo> lockInfos = checkUtil.lockStockByParallel(buyInfo, sellerOrderIdsMap);
			logger.info("确认订单----获取库存信息成功");

			//验证库存
			List<FailDesc> stockFailList = checkUtil.orderConfirmStock(lockInfos);
			if (stockFailList.size() > 0) {
				logger.error("$$$$确定订单----锁定库存错误！fails=" + stockFailList);
				FailCode.addFails(result, stockFailList);
				return createOrderResult;
			}
			resourseOpts.push(ResourseOpt.stock);
			logger.info("确认订单----验证库存信息成功");

			//订单参数
            List<Order> orderList = TradeUtil.convertToOrder(buyInfo, sellerOrderIdsMap, productRets, addressInfo);
			logger.info("确认订单----准备入库参数成功");

			//验证邮费
			List<FailDesc> postageFailList = checkUtil.orderConfirmPostage(buyInfo, orderList);
			if(CollectionUtils.isNotEmpty(postageFailList)) {
				logger.error("$$$$确定订单----获取订单运费错误！fails=" + postageFailList);
				FailCode.addFails(result, postageFailList);
				return createOrderResult;
			}
			logger.info("获取订单----运费获取通过");

			//验证积分抵现
			List<FailDesc> score2CashFailList = checkUtil.orderConfirmScore2Cash(buyInfo, orderList);
			if(CollectionUtils.isNotEmpty(score2CashFailList)) {
				logger.error("$$$$确定订单----检测订单使用积分抵现错误！fails=" + score2CashFailList);
				FailCode.addFails(result, score2CashFailList);
				return createOrderResult;
			}
			if(buyInfo.getExchangeScore() > 0) {
				//使用了积分抵现, 记录操作, 后续操作失败回滚积分
				resourseOpts.push(ResourseOpt.score2cash);
			}
			logger.info("确认订单----积分抵现校验通过");


			//验证价格
			List<FailDesc> priceFailList = checkUtil.orderConfirmPrice(buyInfo, orderList);
			if (priceFailList.size() > 0) {
				logger.error("$$$$确定订单----检测订单价格错误！fails=" + priceFailList);
				FailCode.addFails(result, priceFailList);
				return createOrderResult;
			}
			logger.info("确认订单----验证价格成功");

			//订单入库
			List<FailDesc> orderFailList = checkUtil.orderConfirmOrder(orderList);
			if (orderFailList.size() > 0) {
				logger.error("$$$$确定订单----订单入库错误！fails=" + orderFailList);
				FailCode.addFails(result, orderFailList);
				return createOrderResult;
			}
			logger.info("确认订单----入库成功");

			//删除购物车
			checkUtil.deleteCart(buyInfo, orderList);
			logger.info("确认订单----删除购物车成功");

			//推送消息
			logger.info("确认订单----TODO 推送消息成功");

			//返回参数
			checkUtil.orderConfirmSetRet(buyInfo, orderList, createOrderResult);
			logger.info("确认订单----返回结果成功");

			isConfirmSucc = true;
		} catch (Exception e) {
			logger.error("$$$$确定订单----程序异常错误！buyInfo=" + buyInfo, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			throw new RuntimeException("$$$$$$$$确认订单发生异常");
		} finally {
			//下单失败, 返还库存 积分等资源
			if(isConfirmSucc == false) {
				for(ResourseOpt opt : resourseOpts) {
					switch (opt) {
						case stock:{
							checkUtil.releaseStock(sellerOrderIdsMap, buyInfo);	//释放库存
							break;
						}
						case score2cash:{
							checkUtil.releaseScore2Cash(buyInfo, null);	//返回积分
							break;
						}
					}
				}
			}
		}

		return createOrderResult;
	}

	@Override
	public StringResult buyCount(int userId, String productId) throws TException {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);
		try {
			if ( userId <= 0 || StringUtil.isNullOrEmpty(productId)) {
				logger.warn(MessageFormat.format("buyCount参数验证失败！userId[{0}],productId[{1}]", userId, productId));
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return stringResult;
			}
			logger.info( "获取买家商品历史购买量,userId：" + userId + ", productId：" + productId);
			int count = tradeSvcImpl.getBuyCount(userId, productId);

			if(count < 0 ) {
				logger.warn("获取用户当前购买商品数量redis出错,userId：" + userId + ", productId：" + productId);
				FailCode.addFails(result, FailCode.PRODUCT_BUYLIMIT_HISTORY_ERROR);
				return stringResult;
			}

			stringResult.setValue(ConvertUtil.getString(count));
		} catch (Exception e) {
			logger.error("获取买家商品历史购买量失败，系统异常！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return stringResult;
	}

	public ExchangeRuleResult getExchangeRule(){
		long doneTime = System.currentTimeMillis();
		ExchangeRule rule = scoretocashservice.getExchangeRule();

		logger.info("getExchangeRule接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return ResultGenerator.createExchangeRuleResult(ResultGenerator.SUCCESS_CODE, null, rule);
	}
	/**
	 * 获取可以使用积分
	 *
	 * @param param
	 * @return
	 */
	public ExchangeResult getExchangeScore(ExchangeParam param) {
		long doneTime = System.currentTimeMillis();
		logger.info("getExchangeScore param:::" + param);
		if (param == null) {
			logger.info("getExchangeScore param is error!");
			return ResultGenerator.createErrorExchangeResult(ResultGenerator.PARAM_ERROR);
		}

		ExchangeResult result = scoretocashservice.getExchangeScore(param);

		logger.info(param.getUserId() + ",getExchangeScore接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return result;
	}

	public ExchangeResult score2cash(ExchangeParam param){
		long doneTime = System.currentTimeMillis();
		logger.info("score2cash param:::" + param);
		if (param == null) {
			logger.info("score2cash param is error!");
			return ResultGenerator.createErrorExchangeResult(ResultGenerator.PARAM_ERROR);
		}

		ExchangeResult result = scoretocashservice.score2cash(param);

		logger.info(param.getUserId() + ",score2cash接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return result;
	}
}
