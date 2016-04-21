package com.jfshare.trade.server;

import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.product.ProductResult;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.trade.BuyInfo;
import com.jfshare.finagle.thrift.trade.OrderConfirmResult;
import com.jfshare.finagle.thrift.trade.TradeServ;
import com.jfshare.trade.service.impl.ITradeSvc;
import com.jfshare.trade.util.CheckUtil;
import com.jfshare.trade.util.TradeUtil;
import com.jfshare.trade.util.FailCode;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.StringUtil;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value="handler")
public class ServHandle implements TradeServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Autowired
    private ITradeSvc tradeSvcImpl;
	@Autowired
	private CheckUtil checkUtil;

	@Override
	//TODO 验证SellerId、mq
	public OrderConfirmResult orderConfirm(BuyInfo buyInfo) throws TException {
		OrderConfirmResult createOrderResult = new OrderConfirmResult();
		Result result = new Result();
		result.setCode(0);
		createOrderResult.setResult(result);
		Map<Integer, String> sellerOrderIdsMap = new HashMap<Integer, String>();
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
//				checkUtil.releaseStock(sellerOrderIdsMap, buyInfo);
				return createOrderResult;
			}
			logger.info("确认订单----验证库存信息成功");

			//订单参数
            List<Order> orderList = TradeUtil.convertToOrder(buyInfo, sellerOrderIdsMap, productRets, addressInfo);
			logger.info("确认订单----准备入库参数成功");

			//验证价格
			List<FailDesc> priceFailList = checkUtil.orderConfirmPrice(buyInfo, orderList);
			if (priceFailList.size() > 0) {
				logger.error("$$$$确定订单----检测订单价格错误！fails=" + priceFailList);
				FailCode.addFails(result, priceFailList);
				checkUtil.releaseStock(sellerOrderIdsMap, buyInfo);
				return createOrderResult;
			}
			logger.info("确认订单----验证价格成功");

			//订单入库
			List<FailDesc> orderFailList = checkUtil.orderConfirmOrder(orderList);
			if (orderFailList.size() > 0) {
				logger.error("$$$$确定订单----订单入库错误！fails=" + orderFailList);
				FailCode.addFails(result, orderFailList);
				checkUtil.releaseStock(sellerOrderIdsMap, buyInfo);
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
		} catch (Exception e) {
			logger.error("$$$$确定订单----程序异常错误！buyInfo=" + buyInfo, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			checkUtil.releaseStock(sellerOrderIdsMap, buyInfo);
			throw new RuntimeException("$$$$$$$$确认订单发生异常");
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
}
