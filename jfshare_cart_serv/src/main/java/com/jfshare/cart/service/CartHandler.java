package com.jfshare.cart.service;

import java.util.List;
import java.util.Map;

import com.jfshare.cart.dao.queue.CartQueue;
import com.jfshare.cart.dao.redis.MapRedisManager;
import com.jfshare.cart.model.CartInfo;
import com.jfshare.cart.service.impl.CartService;
import com.jfshare.cart.util.Constant;
import com.jfshare.cart.util.NumberUtil;
import com.jfshare.cart.util.ResultGenerator;
import com.jfshare.cart.util.StringUtil;
import com.jfshare.finagle.thrift.cart.*;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 购物车
 * 
 * @author lilx
 * 
 */
@Service(value = "handler")
public class CartHandler implements CartServ.Iface {

	private Logger logger = LoggerFactory.getLogger(CartHandler.class);

	@Autowired
	private MapRedisManager readCartRedisManager;

	@Autowired
	private MapRedisManager writeCartRedisManager;
	
	@Autowired
	private CartService cartservice;
	
	/**
	 * 获取购物车中商品项的数量
	 * 
	 * @param userKey
	 * @param source
	 * @return
	 */
	public StringResult countItem(String userKey, int source) throws TException {
		long doneTime = System.currentTimeMillis();
		logger.info("itemCount param:::" + userKey + "," + source);
		if (StringUtil.empty(userKey)) {
			logger.info("itemCount param is error!");
			return ResultGenerator.createStringResult(ResultGenerator.FAIL_CODE, ResultGenerator.PARAM_ERROR, null);
		}

		String key = StringUtil.join(Constant.CART_KEY_PREFIX, userKey, Constant.SEPARATOR);

		Integer count = readCartRedisManager.getMapSize(key);

		/**** 重置失效时间,未登录数据设置过期时间 ****/
		if (!StringUtil.isNumber(userKey))
			writeCartRedisManager.expire(key, Constant.FAILURE_TIME);

		logger.info("userKey=" + userKey + ",购物车商品数量：" + count);
		logger.info(userKey + ",itemCount接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return ResultGenerator.createStringResult(ResultGenerator.SUCCESS_CODE, null, count.toString());
	}

	/**
	 * 添加购物车
	 * 
	 * @param userKey
	 * @param item
	 * @param source
	 * @return
	 */
	public CheckCartResult addItem(String userKey, Item item, int source) throws TException  {
		logger.info("addCart param::: " + userKey + "," + source + "," + item);
		if (StringUtil.empty(userKey) || item == null || item.getCount() <= 0 || NumberUtil.parseDouble(item.getPrice()) <= 0) {
			logger.info("addCart param is error!");
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.PARAM_ERROR, null);
		}

		long doneTime = System.currentTimeMillis();
		// 处理参数为NULL的情况
		item.setSkuNum(StringUtil.empty(item.getSkuNum()) ? "" : item.getSkuNum());
		item.setWi(StringUtil.empty(item.getWi()) ? "" : item.getWi());
		
		String key = StringUtil.join(Constant.CART_KEY_PREFIX, userKey, Constant.SEPARATOR);
		Map<String, String> map = readCartRedisManager.getMap(key);

		CartInfo cartInfo = cartservice.getCartInfo(userKey, item.getProductId(), item.getSkuNum(), map);
		if(cartInfo == null){	//新添加
			cartInfo = cartservice.getCartInfo(userKey, item);
		}else{
			cartInfo.setCount(cartInfo.getCount() + item.getCount());	//合并商品数量
			cartInfo.setLongTime(System.currentTimeMillis());	//更新添加时间
			if(!StringUtil.empty(item.getWi()))	
				cartInfo.setWi(item.getWi());	//更新渠道wi
		}
		map.put(cartInfo.getField(), cartInfo.getValue());	//更新购物车map
		
		/**** 数量校验 ****/
		CheckCartResult result = cartservice.checkCart(map, null, userKey, cartInfo);
		if (result != null && result.getResult().getCode() == ResultGenerator.FAIL_CODE) {
			logger.info(userKey + "添加购物车失败" + result.getResult().getCode());
			return result;
		}

		/**** 保存购物车信息 ****/
		Long ret = writeCartRedisManager.setMap(key, cartInfo.getField(), cartInfo.getValue());
		logger.info("setMap.result=" + ret);
		/**** 设置过期时间,未登录数据设置过期时间 ****/
		if (!StringUtil.isNumber(userKey))
			writeCartRedisManager.expire(key, Constant.FAILURE_TIME);

		/**设置购物车变化的时间戳*/
		cartservice.setCartTimeStamp(userKey);
		
		CartQueue.add(userKey, item.getProductId(), item.getSkuNum(), 1);
		
		logger.warn("type=1,source=" + source + ",userKey=" + userKey + ",productId=" + item.getProductId() + ",skuNum=" + item.getSkuNum() + ",wi=" + item.getWi());
		logger.info(userKey + "|" + item.getProductId() + ",addCart接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");

		return ResultGenerator.creatCheckCartResult(ResultGenerator.SUCCESS_CODE, null, null);
	}

	/**
	 * 修改购物车
	 * 
	 * @param userKey 用户标识
	 * @param cartKeyList 已勾选
	 * @param cartKey 当前修改项key
	 * @param item 修改项详情
	 * @param source 来源
	 * @return
	 */
	public CheckCartResult updateItem(String userKey, List<CartKey> cartKeyList, CartKey cartKey, Item item, int source) throws TException  {
		logger.info("updateCart param:::" + userKey + "," + source + "," + StringUtil.joinCart(cartKeyList, "|") + "," + cartKey + "," + item);
		if (StringUtil.empty(userKey) || cartKey == null || item == null || item.getCount() <= 0
				|| NumberUtil.parseDouble(item.getPrice()) <= 0) {
			logger.info("updateCart param is error!");
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.PARAM_ERROR, null);
		}

		long doneTime = System.currentTimeMillis();
		// 处理参数为NULL的情况
		cartKey.setSkuNum(StringUtil.empty(cartKey.getSkuNum()) ? "" : cartKey.getSkuNum());
		item.setSkuNum(StringUtil.empty(item.getSkuNum()) ? "" : item.getSkuNum());
		item.setWi(StringUtil.empty(item.getWi()) ? "" : item.getWi());
		
		String flag = "update"; // 用于前端判断update OR delete
		String key = StringUtil.join(Constant.CART_KEY_PREFIX, userKey, Constant.SEPARATOR);
		Map<String, String> map = readCartRedisManager.getMap(key);
		if (map == null || map.size() <= 0)
			return addItem(userKey, item, source);

		String oldField = StringUtil.join(cartKey.getProductId(), cartKey.getSkuNum(), Constant.SEPARATOR);
		//获取添加时的wi （修改问题：  更新操作未传wi）
		CartInfo oldCartInfo = cartservice.getCartInfo(userKey, item.getProductId(), item.getSkuNum(), map);
		String oldWi = (oldCartInfo == null || StringUtil.empty(oldCartInfo.getWi())) ? "" : oldCartInfo.getWi();
				
		CartInfo cartInfo = cartservice.getCartInfo(userKey, item.getProductId(), item.getSkuNum(), map);
		if(cartInfo == null){	//修改商品属性; 并且目标商品不存在
			cartInfo = cartservice.getCartInfo(userKey, item);
			cartInfo.setWi(oldWi);
			map.remove(oldField);	//删除原商品
		}else{	//修改商品数量 OR 修改的商品属性已存在
			if(cartKey.getSkuNum().equals(item.getSkuNum())){	//更新商品数量
				cartInfo.setCount(item.getCount());
			}else if(map.containsKey(cartInfo.getField())){	//修改商品SKU
				map.remove(oldField);
				flag = "delete"; // 返回前端标识
			}
			if(!StringUtil.empty(item.getWi()))	
				cartInfo.setWi(item.getWi());	//更新渠道wi
		}
		map.put(cartInfo.getField(), cartInfo.getValue());	//添加新商品

		CheckCartResult result = cartservice.checkCart(map, cartKeyList, userKey, cartInfo);
		if ((result != null) && (result.getResult().getCode() == ResultGenerator.FAIL_CODE)) {
			if(result.getCheckList() != null && result.getCheckList().size() > 0){
				CheckItemInfo cii = result.getCheckList().get(0);
				if(cii != null && cii.isIsOutOfGauge() && item.getCount() <= (cii.getMaxBuyLimit() - cii.getCurrBuy()) && item.getCount() <= cii.getSkuCount()){
					// 保存购物车信息
					if (!StringUtil.empty(cartInfo.getValue())) {
						Long delRet = writeCartRedisManager.del(key, oldField);
						logger.info("del.result=" + delRet);
						Long setRet = writeCartRedisManager.setMap(key, cartInfo.getField(), cartInfo.getValue());
						logger.info("setMap.result=" + setRet);
						CartQueue.add(userKey, item.getProductId(), item.getSkuNum(), 1);
						/**设置购物车变化的时间戳*/
						cartservice.setCartTimeStamp(userKey);
					}
					
					logger.warn("type=2,source=" + source + ",userKey=" + userKey + ",productId=" + cartKey.getProductId() + ",skuNum=" + cartKey.getSkuNum() + ",wi=" + oldWi);
					logger.info(userKey + "|" + cartKey.getProductId() + ",updateCart接口调用时间(超限购)：" + (System.currentTimeMillis() - doneTime) + " ms!!");
					return ResultGenerator.creatCheckCartResult(ResultGenerator.SUCCESS_CODE, null, result.getCheckList(), flag);
				}
			}
			
			logger.info(userKey + "更新购物车失败" + result.getResult().getCode());
			return result;
		}

		// 保存购物车信息
		if (!StringUtil.empty(cartInfo.getValue())) {
			Long delRet = writeCartRedisManager.del(key, oldField);
			logger.info("del.result=" + delRet);
			Long setRet = writeCartRedisManager.setMap(key, cartInfo.getField(), cartInfo.getValue());
			logger.info("setMap.result=" + setRet);
			
			/**设置购物车变化的时间戳*/
			cartservice.setCartTimeStamp(userKey);
		}
		logger.warn("type=2,source=" + source + ",userKey=" + userKey + ",productId=" + cartKey.getProductId() + ",skuNum=" + cartKey.getSkuNum() + ",wi=" + oldWi);
		logger.info(userKey + "|" + cartKey.getProductId() + ",updateCart接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return ResultGenerator.creatCheckCartResult(ResultGenerator.SUCCESS_CODE, null, null, flag);
	}

	/**
	 * 删除购物车
	 * 
	 * @param userKey
	 * @param cartKeyList
	 * @param source
	 * @return
	 */
	public Result deleteItem(String userKey, List<CartKey> cartKeyList, int source) throws TException  {
		logger.info("deleteItem param:::" + userKey + "," + source + "," + StringUtil.joinCart(cartKeyList, "|"));
		if (StringUtil.empty(userKey) || cartKeyList == null || cartKeyList.size() <= 0) {
			logger.info("deleteItem param is error!");
			return ResultGenerator.createErrorResult(ResultGenerator.PARAM_ERROR);
		}

		long doneTime = System.currentTimeMillis();
		String key = StringUtil.join(Constant.CART_KEY_PREFIX, userKey, Constant.SEPARATOR);
		String[] fields = new String[cartKeyList.size()];
		for (int i = 0; i < cartKeyList.size(); i++) {
			String sku = cartKeyList.get(i).getSkuNum() == null ? "" : cartKeyList.get(i).getSkuNum();
			fields[i] = StringUtil.join(cartKeyList.get(i).getProductId(), sku, Constant.SEPARATOR);
		}
		Long delRet = writeCartRedisManager.del(key, fields);
		logger.info("del.result=" + delRet);
		
		/**设置购物车变化的时间戳*/
		cartservice.setCartTimeStamp(userKey);
		
		for(CartKey ck : cartKeyList){
			CartQueue.add(userKey, ck.getProductId(), ck.getSkuNum(), 2);
		}
		
		logger.warn("type=3,source=" + source + ",userKey=" + userKey + ",productIds:skuNum=" + StringUtil.joinCart(cartKeyList, "|"));
		logger.info(userKey + ",deleteItem接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return ResultGenerator.createCorrectResult();
	}

	/**
	 * 合并购物车
	 * 
	 * @param sessionId
	 * @param userId
	 * @param source
	 * @return
	 */
	public Result mergeItem(String sessionId, String userId, int source) throws TException  {
		logger.info("mergeItem param:::" + sessionId + "," + userId + "," + source);
		if (StringUtil.empty(sessionId) || StringUtil.empty(userId)) {
			logger.info("mergeItem param is error!");
			return ResultGenerator.createErrorResult(ResultGenerator.PARAM_ERROR);
		}

		long doneTime = System.currentTimeMillis();
		String sessionIdKey = StringUtil.join(Constant.CART_KEY_PREFIX, sessionId, Constant.SEPARATOR);
		String userIdKey = StringUtil.join(Constant.CART_KEY_PREFIX, userId, Constant.SEPARATOR);

		Map<String, String> sessionMap = readCartRedisManager.getMap(sessionIdKey);
		if (sessionMap == null || sessionMap.size() <= 0) {
			logger.info(userId + ",mergeItem接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!!");
			return ResultGenerator.createCorrectResult();
		}
		
		/**设置购物车变化的时间戳*/
		cartservice.setCartTimeStamp(userId);
		
		Map<String, String> userMap = readCartRedisManager.getMap(userIdKey);

		Map<String, String> map = cartservice.addCartMap(userId, sessionMap, userMap);
		if (map == null || map.size() <= 0) {
			logger.info(userId + ",mergeItem接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!!");
			return ResultGenerator.createCorrectResult();
		}
		// 保存购物车信息
		String setRet = writeCartRedisManager.setMap(userIdKey, map);
		logger.info("setMap.result=" + setRet);
		// 删除未登录时添加的商品
		Long delRet = writeCartRedisManager.delete(sessionIdKey);
		/**设置购物车变化的时间戳*/
		cartservice.setCartTimeStamp(sessionId);
		
		logger.info("delete.result=" + delRet);
		
		//添加队列
		for(String s : map.keySet()){
			int index = s.indexOf(Constant.SEPARATOR);
			if (index == -1)
				continue;
			String productId= s.substring(0, index);
			String skuNume = s.substring(index + 1);
			CartQueue.add(userId, productId, skuNume, 1);
		}
		
		logger.warn("type=4,source=" + source + ",sessionId=" + sessionId + ",userId=" + userId);
		logger.info(userId + ",mergeItem接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return ResultGenerator.createCorrectResult();
	}

	/**
	 * 购物车列表查询
	 * 
	 * @param userKey
	 * @param source
	 * @return
	 */
	public CartResult listItem(String userKey, int source) throws TException  {
		logger.info("cartList param:::" + userKey + "," + source);
		if (StringUtil.empty(userKey)) {
			logger.info("cartList param is error!");
			return ResultGenerator.createCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.PARAM_ERROR, null, null);
		}

		long doneTime = System.currentTimeMillis();
		String key = StringUtil.join(Constant.CART_KEY_PREFIX, userKey, Constant.SEPARATOR);
		Map<String, String> map = readCartRedisManager.getMap(key);

		// 购物车信息转List<CartInfo>
		List<CartInfo> cartList = cartservice.changeInfo(userKey, map, null);

		CartResult cr = cartservice.getList(key, cartList);
		logger.info(userKey + ",cartList接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return cr;
	}

	/**
	 * 查询已勾选的购物车商品
	 * 
	 * @param userKey
	 * @param cartKeyList
	 * @param source
	 * @return
	 */
	public CartResult findListByCartKey(String userKey, List<CartKey> cartKeyList, int source) throws TException  {
		logger.info("findListByCartKey param:::" + userKey + "," + source + "," + StringUtil.joinCart(cartKeyList, "|"));
		if (StringUtil.empty(userKey) || cartKeyList == null || cartKeyList.size() <= 0) {
			logger.info("findListByCartKey param is error!");
			return ResultGenerator.createCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.PARAM_ERROR, null, null);
		}

		long doneTime = System.currentTimeMillis();
		String key = StringUtil.join(Constant.CART_KEY_PREFIX, userKey, Constant.SEPARATOR);
		Map<String, String> map = readCartRedisManager.getMap(key);

		// 购物车信息转List<CartInfo>
		List<CartInfo> cartList = cartservice.changeInfo(userKey, map, cartKeyList);

		CartResult cr = cartservice.getList(key, cartList);
		logger.info(userKey + ",findListByCartKey接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return cr;
	}

	/**
	 * 校验购物车已选商品的数量
	 * 
	 * @param userKey
	 * @param cartKeyList
	 * @param source
	 * @return
	 */
	public CheckCartResult checkItem(String userKey, List<CartKey> cartKeyList, int source) throws TException  {
		long doneTime = System.currentTimeMillis();
		logger.info("checkItem param:::" + userKey + "," + source + "," + StringUtil.joinCart(cartKeyList, "|"));
		if ((StringUtil.empty(userKey)) || (cartKeyList == null) || (cartKeyList.size() <= 0)) {
			logger.info("checkItem param is error!");
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.PARAM_ERROR, null);
		}
		CheckCartResult c = null;
		try {
			c = cartservice.check(userKey, cartKeyList);
		} catch (Exception e) {
			logger.info(userKey + "checkItem接口异常!", e);
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.SYSTEM_ERROR, null);
		}
		logger.info(userKey + ",checkItem接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return c;
	}
	
	/**
	 * 迷你购物车(查询前5条数据)
	 * @param userKey
	 * @param source
	 * @return
	 */
	public MiniCartResult listMiniItem(String userKey, int source) {
		logger.info("miniCartList param:::" + userKey + "," + source);
		if (StringUtil.empty(userKey)) {
			logger.info("miniCartList param is error!");
			return ResultGenerator.createMiniCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.PARAM_ERROR, null, 0, 0);
		}

		long doneTime = System.currentTimeMillis();
		String key = StringUtil.join(Constant.CART_KEY_PREFIX, userKey, Constant.SEPARATOR);
		Map<String, String> map = readCartRedisManager.getMap(key);

		// 购物车信息转List<CartInfo>
		List<CartInfo> cartList = cartservice.changeInfo(userKey, map, null);

		MiniCartResult cr = cartservice.getMiniCartList(key, cartList);
		logger.info(userKey + ",miniCartList接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return cr;
	}
	
	public StringResult getTimeStamp(String userKey, int source){
		long doneTime = System.currentTimeMillis();
		String timeStamp = "";
		try {
			timeStamp = cartservice.getCartTimeStamp(userKey);
		} catch (Exception e) {
			logger.error(userKey + "," + source + "读取购物车时间戳异常" + e);
		}
		logger.info(userKey + "," + source + "," + timeStamp + ",getTimeStamp接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return ResultGenerator.createStringResult(ResultGenerator.SUCCESS_CODE, null, timeStamp);
	}

}
