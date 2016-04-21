package com.jfshare.cart.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jfshare.cart.dao.queue.CartQueue;
import com.jfshare.cart.dao.redis.MapRedisManager;
import com.jfshare.cart.dao.redis.StringRedisManager;
import com.jfshare.cart.model.CartInfo;
import com.jfshare.cart.model.CartSort;
import com.jfshare.cart.model.GoodsInfo;
import com.jfshare.cart.model.ProductSkuInfo;
import com.jfshare.cart.util.Constant;
import com.jfshare.cart.util.NumberUtil;
import com.jfshare.cart.util.ResultGenerator;
import com.jfshare.cart.util.StringUtil;
import com.jfshare.finagle.thrift.cart.*;
import com.jfshare.finagle.thrift.result.StringResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("cartservice")
public class CartService {
	
	private Logger logger = LoggerFactory.getLogger(CartService.class);
	
	@Autowired
	private ProductService productservice;
	
	private static final ExecutorService executor = Executors.newCachedThreadPool();

	@Autowired
	private MapRedisManager readCartRedisManager;

	@Autowired
	private MapRedisManager writeCartRedisManager;

	@Autowired
	private MapRedisManager mapRedisManagerRdb;

	@Autowired
	private StringRedisManager stringRedisManagerRdb;
	
	/**
	 * List<CartSort> list 转换 List<sellerItemDetail> itemList
	 * 
	 * @param list
	 * @param itemList
	 */
	private void changeCartInfo(List<CartSort> list, List<sellerItemDetail> itemList) {
		if (list == null || list.size() <= 0) {
			logger.info("changeCartInfo::::param is null!");
			logger.info("有效商品为空");
			return;
		}
		for (CartSort cartSort : list) {
			sellerItemDetail sid = new sellerItemDetail();
			sid.setSeller(cartSort.getSeller());
			List<ItemDetail> iList = changePruductInfo(cartSort.getGoodsList());
			sid.setItemDetailList(iList);
			itemList.add(sid);
		}
	}

	/**
	 * List<GoodsInfo> goodsList 转换 List<ItemDetail>
	 * 
	 * @param goodsList
	 * @return
	 */
	private List<ItemDetail> changePruductInfo(List<GoodsInfo> goodsList) {
		if (goodsList == null || goodsList.size() <= 0) {
			logger.info("changePruductInfo::::param is error!");
			return null;
		}
		List<ItemDetail> itemDetailList = new ArrayList<ItemDetail>();
		for (GoodsInfo info : goodsList) {
			ItemDetail detail = new ItemDetail();
			detail.setProduct(info.getProductInfo());
			itemDetailList.add(detail);
		}
		return itemDetailList;
	}

	/**
	 * 按商家分组 并 排序
	 * 
	 * @param list
	 * @param invalidItemList
	 * @return
	 */
	private List<CartSort> sortByShop(List<GoodsInfo> list, List<ItemDetail> invalidItemList) {
		if (list == null || list.size() <= 0) {
			logger.info("sortByShop::::param is NULL");
			return null;
		}
		Map<Integer, List<GoodsInfo>> map = new HashMap<Integer, List<GoodsInfo>>();

		/**** 按商家分组 ****/
		for (GoodsInfo info : list) {
			if (info == null || info.getSeller() == null)
				continue;
			if(info.getInvalidProductInfo() != null && info.getProductInfo() == null){	//SKU不存在的失效商品
				ItemDetail itemDetail = new ItemDetail();
				itemDetail.setProduct(info.getInvalidProductInfo());
				invalidItemList.add(itemDetail);
				continue;
			}
			
			List<GoodsInfo> gList = (List<GoodsInfo>) map.get(info.getSeller().getSellerId());
			if (info.getProductInfo().getProduct().getActiveState() >= Constant.PRODUCT_SELL_STATE_START && info.getProductInfo().getProduct().getActiveState() <= Constant.PRODUCT_SELL_STATE_END &&
					info.getProductInfo().getSkuCount() > 0 &&
					(info.getProductInfo().getProduct().getMaxBuyLimit() == 0 || info.getProductInfo().getProduct().getMaxBuyLimit() > info.getProductInfo().getCurrBuy())) {
				if (gList == null) {
					gList = new ArrayList<GoodsInfo>();
				}
				gList.add(info);
				map.put(info.getSeller().getSellerId(), gList);
			} else {
				ItemDetail itemDetail = new ItemDetail();
				itemDetail.setProduct(info.getProductInfo());
				invalidItemList.add(itemDetail);
			}

		}

		List<CartSort> _list = new ArrayList<CartSort>();
		/**** 按商品添加顺序排序 ****/
		for (Integer sellerId : map.keySet()) {
			CartSort cartSort = new CartSort();
			for (GoodsInfo goods : list) {
				if (goods == null)
					continue;

				if (goods.getSeller().getSellerId() == sellerId) {
					cartSort.setSeller(goods.getSeller());
					List<GoodsInfo> gList = (List<GoodsInfo>) map.get(sellerId);
					sort(gList);	// 商品排序
					sortBySku(gList);	//同SKU商品要并列排放
					cartSort.setGoodsList(gList);
					cartSort.setLongTime(gList.get(0).getLongTime()); // 把排最前的商品的时间戳传给店铺
				}
			}
			_list.add(cartSort);
		}
		/**** 按商家排序 ****/
		sortByShop(_list);
		return _list;
	}
	
	private void sortBySku(List<GoodsInfo> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = 1; j < list.size(); j++) {
				if (list.get(i).getProductInfo().getProduct().getProductId().equals(list.get(j).getProductInfo().getProduct().getProductId()))
					list.get(j).setLongTime(list.get(i).getLongTime() - j);
			}
		}
		sort(list);
	}

//	/**
//	 * SkuProductDetailResult,CartInfo 转换 ProductInfo and PromotionInfo
//	 *
//	 * @param skuProductDetail
//	 * @param productInfo
//	 * @param promotionInfo
//	 * @param cartInfo
//	 */
//	private void productDetailToProductInfo(SkuProductDetailResult skuProductDetail, ProductInfo productInfo, PromotionInfo promotionInfo,
//			CartInfo cartInfo) {
//		long doneTime = System.currentTimeMillis();
//		if (skuProductDetail == null || skuProductDetail.getProduct() == null) {
//			logger.info("商品详细信息为NULL");
//			return;
//		}
//		if (cartInfo == null) {
//			logger.info("商品购物车信息为NULL");
//			return;
//		}
//		String productId = skuProductDetail.getProduct().getDetail().getId();
//		String sku = skuProductDetail.getProduct().getSkuNum();
//		int sellerId = skuProductDetail.getProduct().getDetail().getSellerInfo().getSellerId();
//
//		productInfo.setProductId(productId);
//		productInfo.setProductName(skuProductDetail.getProduct().getDetail().getName());
//		productInfo.setImagesUrl(skuProductDetail.getProduct().getDetail().getImgKey());
//		if(!StringUtil.empty(skuProductDetail.getProduct().getDetail().getProductUrl()))
//			productInfo.setSkuImageUrl(skuProductDetail.getProduct().getDetail().getProductUrl());
//		productInfo.setSkuNum(sku);
//		List<SkuDesc> list = skuProductDetail.getProduct().getSkuDescList();
//		productInfo.setSkuDescList(list == null ? new ArrayList<SkuDesc>() : list);
//		productInfo.setCurPrice(skuProductDetail.getProduct().getDetail().getCurPrice());
//		productInfo.setOrgPrice(skuProductDetail.getProduct().getDetail().getOrgPrice());
//		productInfo.setCartPrice(cartInfo.getPrice());
//		productInfo.setWi(StringUtil.empty(cartInfo.getWi()) ? "" : cartInfo.getWi());
//
//		/** 发货时间 */
//		productInfo.setSellerSendTime(skuProductDetail.getProduct().getDetail().getDeliverLimitTime());
//		/**** 商品归属地 ****/
//		productInfo.setLocationCityId(skuProductDetail.getProduct().getDetail().getLocationCityId());
//		productInfo.setLocationProvinceId(skuProductDetail.getProduct().getDetail().getLocationProvinceId());
//
//		/**** 邮费信息 ****/
//		productInfo.setPost(skuProductDetail.getProduct().getDetail().getPost());
//
//		/**** 商品库存 ****/
//		String key = StringUtil.join("stock", sellerId + "", "_");
//		String filed = StringUtil.join(productId, sku, "_");
//		String skuCount = mapRedisManagerRdb.get(key, filed);
//		String totalFiled = StringUtil.join(productId, "", "_");
//		String totalCount = mapRedisManagerRdb.get(key, totalFiled);
//		productInfo.setTotalCount(NumberUtil.parseInteger(totalCount));
//		productInfo.setSkuCount(NumberUtil.parseInteger(skuCount));
//		productInfo.setCount(cartInfo.getCount());
//		logger.info(productId + ":" + sku + "库存：skuCount=" + skuCount + ",totalCount=" + totalCount);
//		/**** 读取商品状态和活动时间 ****/
//		String state = mapRedisManagerRdb.get(Constant.PRODUCT_STATE_PREFIX + cartInfo.getProductId(), "state");
//		String timeRange = stringRedisManagerRdb.get(Constant.PRODUCT_LIFECYCLE_PREFIX + cartInfo.getProductId());
//		logger.info(productId + ":" + sku + ",state=" + state + ",timeRange=" + timeRange);
//		if (StringUtil.empty(state) || StringUtil.empty(timeRange)) {
//			state = StringUtil.empty(state) ? "0" : state;
//			timeRange = StringUtil.empty(timeRange) ? "" : timeRange;
//		}
//
//		String[] range = timeRange.split(Constant.SEPARATOR);
//		Long sTime = 0L;
//		Long eTime = 0L;
//		Long curTime = System.currentTimeMillis();
//		if (range != null && range.length == 2) {
//			sTime = NumberUtil.parseLong(range[0]);
//			eTime = NumberUtil.parseLong(range[1]);
//		}
//		int productState = NumberUtil.parseInteger(state.trim());
//
//		if (productState == Constant.PRODUCT_SELL_STATE) { // 上架状态 -->>校验活动时间
//			if (sTime > 0 && curTime < sTime)// 待上架
//				productState = Constant.PRODUCT_WAIT_STATE;
//			else if (eTime > 0 && curTime > eTime)// 下架
//				productState = Constant.PRODUCT_SOLDOUT_STATE;
//		}
//		productInfo.setState(productState);
//		/**** promotionInfo ****/
//		String startTime = DateTimeUtil.dateToStrOfDefaulfFormat(sTime);
//		String endTime = DateTimeUtil.dateToStrOfDefaulfFormat(eTime);
//		promotionInfo.setProductId(cartInfo.getProductId());
//		promotionInfo.setStartTime(startTime);
//		promotionInfo.setEndTime(endTime);
//
//		/**** 该商品未付款的数量 ****/
//		String s = StringUtil.join(Constant.STOCK_INFO_LOCK, productId, Constant.SEPARATOR);
//		String lockCountKey = StringUtil.join(s, sku, "_");
//		String lockCount = mapRedisManagerRdb.get(lockCountKey, Constant.PRODUCT_STOCK_LOCK_COUNT);
//		logger.info(productId + ":" + sku + ",lockCount=" + lockCount);
//		productInfo.setLockCount(NumberUtil.parseInteger(lockCount));
//		/** 优质商家 */
//		String promotionKey = StringUtil.join("promotion", "product", productId, Constant.SEPARATOR);
//		String promotionId = mapRedisManagerRdb.get(promotionKey);
//		logger.info(StringUtil.join(productId, sku, Constant.SEPARATOR) + ",promotionID=" + promotionId);
//		logger.info("获取商品库存、状态、活动时间、未付款的数量调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
//	}

	/**
	 * 购物车信息转换成List<CartInfo>
	 *
	 *            (已勾选的商品信息)
	 */
	public List<CartInfo> changeInfo(String userId, Map<String, String> map, List<CartKey> cartKeyList) {
		if (map == null || map.size() <= 0)
			return null;

		List<CartInfo> list = new ArrayList<CartInfo>();
		for (String key : map.keySet()) {
			int index = key.indexOf(Constant.SEPARATOR);
			if (index == -1)
				continue;

			String value = map.get(key);
			if (StringUtil.empty(value))
				continue;
			String[] values = value.split(Constant.SEPARATOR);
			if (values == null || values.length < 3)
				continue;

			CartInfo cartInfo = new CartInfo();
			cartInfo.setUserId(userId);
			cartInfo.setProductId(key.substring(0, index));
			String skuNum = key.substring(index + 1);
			cartInfo.setSkuNum(skuNum == null ? "" : skuNum);
			cartInfo.setCount(NumberUtil.parseInteger(values[0]));
			cartInfo.setPrice(values[1]);
			cartInfo.setLongTime(NumberUtil.parseLong(values[2]));
			if(values.length >3)
				cartInfo.setWi(values[3]);
			if (cartKeyList != null && cartKeyList.size() > 0) { // 处理查询已勾选的商品
				for (CartKey cartKey : cartKeyList) {
					String sku = cartKey.getSkuNum() == null ? "" : cartKey.getSkuNum();
					if (key.equals(StringUtil.join(cartKey.getProductId(), sku, Constant.SEPARATOR)))
						list.add(cartInfo);
				}
			} else {
				list.add(cartInfo);
			}
		}
		return list;
	}
	
	/**
	 * 按商家排序
	 * 
	 * @param list
	 */
	private void sortByShop(List<CartSort> list) {
		if (list == null || list.size() <= 0)
			return;
		Comparator<CartSort> comparator = new Comparator<CartSort>() {
			public int compare(CartSort c1, CartSort c2) {
				return c1.getLongTime() >= c2.getLongTime() ? -1 : 1;
			}
		};
		Collections.sort(list, comparator);
	}

	/**
	 * 按商品添加先后排序
	 * 
	 * @param list
	 */
	private void sort(List<GoodsInfo> list) {
		if (list == null || list.size() <= 0)
			return;
		Comparator<GoodsInfo> comparator = new Comparator<GoodsInfo>() {
			public int compare(GoodsInfo g1, GoodsInfo g2) {
				return g1.getLongTime() >= g2.getLongTime() ? -1 : 1;
			}
		};
		Collections.sort(list, comparator);
	}

	/**
	 * 按商品添加先后排序
	 * 
	 * @param list
	 */
	public void cartSort(List<CartInfo> list) {
		if (list == null || list.size() <= 0)
			return;
		Comparator<CartInfo> comparator = new Comparator<CartInfo>() {
			public int compare(CartInfo g1, CartInfo g2) {
				return g1.getLongTime() >= g2.getLongTime() ? -1 : 1;
			}
		};
		Collections.sort(list, comparator);
	}

	/**
	 * 通过productId AND skuNum 获取购物车中的数量
	 * 
	 * @param map
	 * @param productId
	 * @param skuNum
	 * @return
	 */
	public int getCountByProductIdAndSku(Map<String, String> map, String productId, String skuNum) {
		if (map == null || map.size() <= 0 || StringUtil.empty(productId))
			return 0;

		skuNum = StringUtil.empty(skuNum) ? "" : skuNum;

		String filed = StringUtil.join(productId, skuNum, Constant.SEPARATOR);
		String value = map.get(filed);
		if (!StringUtil.empty(value)) {
			String[] values = value.split(Constant.SEPARATOR);
			if (values != null && values.length > 0)
				return NumberUtil.parseInteger(values[0]);
		}
		return 0;
	}
	
	public String getWiByProductIdAndSku(Map<String, String> map, String productId, String skuNum) {
		if (map == null || map.size() <= 0 || StringUtil.empty(productId))
			return "";

		skuNum = StringUtil.empty(skuNum) ? "" : skuNum;

		String filed = StringUtil.join(productId, skuNum, Constant.SEPARATOR);
		String value = map.get(filed);
		if (!StringUtil.empty(value)) {
			String[] values = value.split(Constant.SEPARATOR);
			if (values != null && values.length > 3)
				return values[3];
		}
		return "";
	}

	/**
	 * 获取购物车商品列表
	 * 
	 * @param key
	 * @param cartList
	 * @return
	 */
	public CartResult getList(final String key, List<CartInfo> cartList) {
		if (StringUtil.empty(key) || cartList == null || cartList.size() <= 0) {
			logger.info("购物车信息为NULL");
			return ResultGenerator.createCartResult(ResultGenerator.SUCCESS_CODE, null, null, null);
		}
		long doneTime = System.currentTimeMillis();

		final List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		// 同步辅助类
		final CountDownLatch countDownLatch = new CountDownLatch(cartList.size());
		for (final CartInfo cart : cartList) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						// 查询商品详情
						long sTime = System.currentTimeMillis();
						GoodsInfo goods = productservice.getGoods(cart);
						logger.info(key + "|" + cart.getProductId() + "|" + cart.getSkuNum() + ",商品详情接口getGoods调用时间：" + (System.currentTimeMillis() - sTime) + " ms!!");
						if (goods != null)
							list.add(goods);
					} catch (Exception e) {
						logger.info(key + "连接商品服务失败", e);
					} finally {
						countDownLatch.countDown();
					}
				}
			});
		}

		try { countDownLatch.await(Constant.DB_THREAD_TIMEOUT, TimeUnit.MILLISECONDS); } catch (Exception e) { }

		if (countDownLatch.getCount() > 0) {
			logger.info(key + "======================读取商品数据超时!=====================");
			return ResultGenerator.createCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.DATA_TIME_OUT, null, null);
		}
		logger.info(key + ",cartList::获取商品详情信息调用总时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");

		// 购物车列表项
		List<sellerItemDetail> itemList = new ArrayList<sellerItemDetail>();
		List<ItemDetail> invalidItemList = new ArrayList<ItemDetail>();
		// 上架状态的商品 按商家分组并排序 ：：： 分离失效商品invalidItemList
		List<CartSort> _list = sortByShop(list, invalidItemList);
		// List<CartSort> 转换>>>>List<sellerItemDetail>
		changeCartInfo(_list, itemList);

		return ResultGenerator.createCartResult(ResultGenerator.SUCCESS_CODE, null, itemList, invalidItemList);
	}

//	/**
//	 * SkuProductDetailResult 转 GoodsInfo
//	 *
//	 * @param detail
//	 * @param cart
//	 * @param key
//	 * @return
//	 */
//	private GoodsInfo getGoodsInfo(SkuProductDetailResult detail, CartInfo cart, String key) {
//		if (StringUtil.empty(key))
//			return null;
//		if (detail == null || detail.getProduct() == null) {
//			logger.info("商品详细信息为NULL");
//			return null;
//		}
//		if (cart == null) {
//			logger.info("商品购物车信息为NULL");
//			return null;
//		}
//		if (!Constant.COMMON_PRODUCT_TYPE.equals(detail.getProduct().getDetail().getType())) { // 删除非普通商品
//			writeCartRedisManager.del(key, StringUtil.join(cart.getProductId(), cart.getSkuNum(), Constant.SEPARATOR));
//			logger.info("清除非普通商品：productId=" + cart.getProductId() + ",skuNum=" + cart.getSkuNum());
//			return null;
//		}
//
//		GoodsInfo goods = new GoodsInfo();
//		goods.setCount(cart.getCount());
//		goods.setLongTime(cart.getLongTime());
//
//		ProductInfo productInfo = new ProductInfo();
//		PromotionInfo promotionInfo = new PromotionInfo();
//		productDetailToProductInfo(detail, productInfo, promotionInfo, cart);
//
//		goods.setSellerInfo(detail.getProduct().getDetail().getSellerInfo());
//		goods.setProductInfo(productInfo);
//		goods.setPromotionInfo(promotionInfo);
//
//		/**限购失效校验*/
//		String[] s = key.split(Constant.SEPARATOR);
//		int buyerId = 0;
//		if(s != null && s.length > 0 && s[1].length() < 10)
//			buyerId = NumberUtil.parseInteger(s[1]);
////		ProductStateAndStockResult result = productservice.checkProductStateAndStock(cart.getProductId(), cart.getSkuNum(), buyerId);
//		ProductSkuInfo productSkuInfo = productservice.getProductSkuInfo(cart.getProductId(), cart.getSkuNum(), buyerId);
//		if (productSkuInfo != null) {
//			goods.getProductInfo().setAllMaxBuy(productSkuInfo.getMaxBuyLimit());
//			goods.getProductInfo().setCurrBuy(productSkuInfo.getCurrBuy());
//		}else{
//			logger.info("调用接口(getProductSkuInfo)无数据!" + cart.getProductId() + ":" + cart.getSkuNum());
//		}
//
//		return goods;
//	}

	/**
	 * 合并购物车：获取待添加的map
	 * 
	 * @param userId
	 * @param sessionMap
	 * @param userMap
	 * @return
	 */
	public Map<String, String> addCartMap(String userId, Map<String, String> sessionMap, Map<String, String> userMap) {
		/** 登录前购物车为空，则无须处理 */
		if (sessionMap == null || sessionMap.size() <= 0)
			return null;
		/** 登录状态无数据，则直接添加sessionMap */
		if (userMap == null || userMap.size() <= 0)
			return sessionMap;

		Map<String, String> map = new HashMap<String, String>();
		map.putAll(userMap);
		map.putAll(sessionMap);
		/** 超出购物车的最大数量 */
		if (map.size() > Constant.MAX_CART_COUNT) {
			logger.info("购物车合并超过购物车的最大数量");
			List<CartInfo> cartList = changeInfo(userId, map, null);
			/** 按时间排序删除超过购物车最大数量的商品信息 */
			cartSort(cartList);
			List<String> list = new ArrayList<String>();
			for (int i = Constant.MAX_CART_COUNT; i < cartList.size(); i++) {
				String filed = StringUtil.join(cartList.get(i).getProductId(), cartList.get(i).getSkuNum(), Constant.SEPARATOR);
				if (userMap.containsKey(filed)){
					list.add(filed);
					CartQueue.add(userId, cartList.get(i).getProductId(), cartList.get(i).getSkuNum(), 2);
				}else if (sessionMap.containsKey(filed))
					sessionMap.remove(filed);
			}
			if (list != null && list.size() > 0) {
				String[] fields = (String[]) list.toArray(new String[list.size()]);
				writeCartRedisManager.del(StringUtil.join(Constant.CART_KEY_PREFIX, userId, Constant.SEPARATOR), fields);
			}
		}
		return sessionMap;
	}

	/**
	 * 校验购物车已选商品的数量
	 * 
	 * @param userId
	 * @param cartKeyList
	 * @return
	 */
	public CheckCartResult check(final String userId, List<CartKey> cartKeyList) {
		if ((StringUtil.empty(userId)) || (cartKeyList == null) || (cartKeyList.size() <= 0)) {
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.PARAM_ERROR, null);
		}
		String key = StringUtil.join("cart", userId, Constant.SEPARATOR);
		Map<String, String> map = readCartRedisManager.getMap(key);
		final List<CartInfo> list = changeInfo(userId, map, cartKeyList);

		final List<CheckItemInfo> _list = new ArrayList<CheckItemInfo>();
		final CountDownLatch end = new CountDownLatch(list.size());
		for (final CartInfo entity : list)
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						CheckItemInfo c = checkProductQuantity(list, entity.getProductId(), entity.getSkuNum(), entity.getCount(), userId, false);
						if (c != null && !StringUtil.empty(c.getProductId()))
							_list.add(c);
					} catch (Exception e) {
						logger.info(userId + "连接商品服务失败", e);
					} finally {
						end.countDown();
					}
				}
			});
		try {
			end.await(Constant.DB_THREAD_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
		}
		if (end.getCount() > 0L) {
			logger.info(userId + "======================校验商品数量超时!======================");
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.DATA_TIME_OUT, null);
		}

		if (_list.size() > 0) {
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.CART_PRODUCT_QUANTITY_ERROR, _list);
		}
		return ResultGenerator.creatCheckCartResult(ResultGenerator.SUCCESS_CODE, null, null);
	}

	/**
	 * 添加 AND 更新购物车 校验商品数量
	 * 
	 * @param map
	 * @param cartKeyList
	 * @param userId
	 * @param cartInfo
	 * @return
	 */
	public CheckCartResult checkCart(Map<String, String> map, List<CartKey> cartKeyList, String userId, CartInfo cartInfo) {
		if ((map == null) || (map.size() <= 0)) {
			logger.info("购物车为空");
			return null;
		}

		cartInfo.setSkuNum(StringUtil.empty(cartInfo.getSkuNum()) ? "" : cartInfo.getSkuNum());
		/**判断已勾选的list 是否为NULL ::: 加购物车为NULL,更新购物车不为NULL*/
		boolean sign = (cartKeyList == null) || (cartKeyList.size() <= 0);

		int count = map.size();
		if (count > Constant.MAX_CART_COUNT) {
			logger.info(userId + "超过购物车允许的商品最大值");
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.CART_MAX_COUNT_ERROR, null);
		}

		List<CartInfo> list = changeInfo(userId, map, cartKeyList);
		List<CheckItemInfo> _list = new ArrayList<CheckItemInfo>();

		CheckItemInfo cii = checkProductQuantity(list, cartInfo.getProductId(), cartInfo.getSkuNum(), cartInfo.getCount(), userId, sign);
		/** 查不到商品数据 */
		if(cii == null){
			return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.CART_PRODUCT_INFO_ERROR, null);
		}
		/** 成功 */
		if(cii != null && StringUtil.empty(cii.getProductId())){
			return ResultGenerator.creatCheckCartResult(ResultGenerator.SUCCESS_CODE, null, null);
		}
		
		/** 失败信息 */
		logger.info(userId + "," + cii.toString());
		_list.add(cii);
		return ResultGenerator.creatCheckCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.CART_PRODUCT_QUANTITY_ERROR, _list);
	}

	/**
	 * 校验商品数量
	 * 
	 * @param list
	 * @param productId
	 * @param skuNum
	 * @param userKey
	 * @param sign
	 *            true:添加购物车不校验限购 ; false: 更新 或 结算时 库存|限购都校验
	 * @return
	 */
	private CheckItemInfo checkProductQuantity(List<CartInfo> list, String productId, String skuNum, int count, String userKey, boolean sign) {
		if ((list == null) || list.size() <= 0 || StringUtil.empty(productId) || StringUtil.empty(userKey))
			return null;

		skuNum = StringUtil.empty(skuNum) ? "" : skuNum;
		int buyerId = userKey.length() > 10 ? 0 : NumberUtil.parseInteger(userKey); // 大于10位的肯定为sessionId
//		ProductStateAndStockResult result = productservice.checkProductStateAndStock(productId, skuNum, buyerId);
		ProductSkuInfo productSkuInfo = productservice.getProductSkuInfo(productId, skuNum, buyerId);
		if (productSkuInfo == null) {
			logger.info("调用接口getProductSkuInfo无数据!" + productId + ":" + skuNum);
			return null;
		} else {
			int allMaxBuy = productSkuInfo.getMaxBuyLimit(); // 限购的最大数量
			int currBuy = productSkuInfo.getCurrBuy(); // 已购数量
			int stock = productSkuInfo.getSkuStock(); // 库存数量
			int total = getCartProductCount(productId, list); // 购物车中同一商品所有规格的总数量
			logger.info(userKey + "," + productId + ",skuNum:" + skuNum + ",allMaxBuy:" + allMaxBuy + ",currBuy:" + currBuy + ",stock:" + stock);
			boolean flag = true; // 限购 AND 库存 优先校验的标示, 哪个值小 优先校验哪个 , true： 优先限购
			if (allMaxBuy != 0 && ((allMaxBuy - currBuy) - (total - count) > stock))
				flag = false;

			if ((flag) && (!sign)) {
				if ((currBuy + total > allMaxBuy) && (allMaxBuy > 0))
					return ResultGenerator.createCheckItemInfo(productId, skuNum, stock, total, allMaxBuy, currBuy, true, false);
			} else if (count > stock) {
				return ResultGenerator.createCheckItemInfo(productId, skuNum, stock, count, allMaxBuy, currBuy, false, true);
			}
		}
		return new CheckItemInfo();
	}
	
	/**
	 * 购物车中商品所有规格的数量之和
	 * 
	 * @param productId
	 * @param list
	 * @return
	 */
	private int getCartProductCount(String productId, List<CartInfo> list) {
		if ((StringUtil.empty(productId)) || (list == null) || (list.size() <= 0))
			return 0;
		
		int count = 0;
		for (CartInfo cart : list) {
			if (productId.equals(cart.getProductId())) {
				count += cart.getCount();
			}
		}
		return count;
	}

	/**
	 * 购物车数量校验
	 * 
	 * @param map
	 * @param userKey
	 * @param item
	 * @param sign	区分添加 OR 更新操作
	 * @return
	 */
	public StringResult checkQuantity(Map<String, String> map, String userKey, Item item, boolean sign) {
		if (map == null || map.size() <= 0) {
			logger.info("购物车为空");
			return null;
		}
		item.setSkuNum(StringUtil.empty(item.getSkuNum()) ? "" : item.getSkuNum());
		// 校验是否超过购物车允许的最大值
		int count = map.size();
		if (count > Constant.MAX_CART_COUNT) {
			logger.info("超过购物车允许的商品最大值; userID=" + userKey);
			return ResultGenerator.createStringResult(ResultGenerator.FAIL_CODE, ResultGenerator.CART_MAX_COUNT_ERROR, null);
		}
		// 调用商品服务获取库存、及购买信息
		int buyerId = userKey.length() > 10 ? 0 : NumberUtil.parseInteger(userKey); // 大于10位的肯定为sessionId
//		ProductStateAndStockResult result = productservice.checkProductStateAndStock(item.getProductId(), item.getSkuNum(), buyerId);
		ProductSkuInfo productSkuInfo = productservice.getProductSkuInfo(item.getProductId(), item.getSkuNum(), buyerId);
		if (productSkuInfo == null) {
			logger.info("调用接口(getProductSkuInfo)无数据!" + item.getProductId() + ":" + item.getSkuNum());
			return ResultGenerator.createStringResult(ResultGenerator.FAIL_CODE, ResultGenerator.PRODUCT_DATA_ERROR, null);
		}
		int allMaxBuy = productSkuInfo.getMaxBuyLimit(); // 最大限购数量
		int currBuy = productSkuInfo.getCurrBuy(); // 已购数量
		int productCount = getCountByProductId(map, item.getProductId()); // 购物车中已添加的(productId)数量
		int quantity = getCountByProductIdAndSku(map, item.getProductId(), item.getSkuNum()); // 待购数量
		int stock = productSkuInfo.getSkuStock(); // 库存

		boolean flag = true; // 限购 AND 库存 优先校验的标示，默认先校验限购
		if (allMaxBuy != 0 && allMaxBuy > stock)
			flag = false;

		if (flag && !sign) {
			if ((currBuy + productCount > allMaxBuy) && (allMaxBuy > 0)) {
				logger.info("超出商品限购数量! " + item.getProductId() + "," + item.getSkuNum() + "," + allMaxBuy + "," + currBuy);
				String value = StringUtil.join(allMaxBuy + "", currBuy + "", Constant.SEPARATOR);
				return ResultGenerator.createStringResult(ResultGenerator.FAIL_CODE, ResultGenerator.CART_QUANTITY_ERROR, value);
			}
		} else {
			if (quantity > stock) {
				logger.info("超出商品库存数量! " + item.getProductId() + "," + item.getSkuNum() + "," + stock);
				// 购物车中已有的数量
				int currQuantity = quantity != item.getCount() ? quantity - item.getCount() : 0;
				String value = StringUtil.join(stock + "", currQuantity + "", Constant.SEPARATOR);
				return ResultGenerator.createStringResult(ResultGenerator.FAIL_CODE, ResultGenerator.CART_STOCK_LACK, value);
			}
		}
		return ResultGenerator.createStringResult(ResultGenerator.SUCCESS_CODE, null, null);
	}
	
	/**
	 * 获取购物车中某一商品的数量(包括该商品的所有规格)
	 * 
	 * @param map
	 * @param productId
	 * @return
	 */
	private int getCountByProductId(Map<String, String> map, String productId) {
		if (map == null || map.size() <= 0 || StringUtil.empty(productId))
			return 0;
		int count = 0;
		for (String key : map.keySet()) {
			if (key.contains(productId)) {
				String value = map.get(key);
				if (StringUtil.empty(value))
					continue;
				String[] values = value.split(Constant.SEPARATOR);
				count += NumberUtil.parseInteger(values[0]);
			}
		}
		return count;
	}
	
	public CartInfo getCartInfo(String userId, String productId, String skuNum, Map<String, String> map){
		if(StringUtil.empty(userId) || StringUtil.empty(productId) || map == null || map.size() <= 0)
			return null;
		
		CartInfo cartInfo = null;
		for(String key : map.keySet()){
			if(key.equals(StringUtil.join(productId, skuNum, Constant.SEPARATOR))){
				String value = map.get(key);
				if (StringUtil.empty(value))
					break;
				String[] values = value.split(Constant.SEPARATOR);
				if (values == null || values.length < 3)
					break;
				
				cartInfo = new CartInfo();
				cartInfo.setUserId(userId);
				cartInfo.setProductId(productId);
				cartInfo.setSkuNum(skuNum);
				cartInfo.setCount(NumberUtil.parseInteger(values[0]));
				cartInfo.setPrice(values[1]);
				cartInfo.setLongTime(NumberUtil.parseLong(values[2]));
				if (values.length > 3)
					cartInfo.setWi(values[3]);
				else
					cartInfo.setWi("");
			}
		}
		
		return cartInfo;
	}
	
	public CartInfo getCartInfo(String userId, Item item) {
		if (StringUtil.empty(userId) || item == null)
			return null;

		CartInfo cartInfo = new CartInfo();
		cartInfo.setUserId(userId);
		cartInfo.setProductId(item.getProductId());
		cartInfo.setSkuNum(StringUtil.empty(item.getSkuNum()) ? "" : item.getSkuNum());
		cartInfo.setCount(item.getCount());
		cartInfo.setPrice(item.getPrice());
		cartInfo.setLongTime(System.currentTimeMillis());
		cartInfo.setWi(StringUtil.empty(item.getWi()) ? "" : item.getWi());

		return cartInfo;
	}
	
	/**
	 * 购物车变化添加时间戳
	 * 
	 * @param userKey
	 */
	public String getCartTimeStamp(String userKey) {
		String key = StringUtil.join(Constant.CART_CHANGE_KEY_PREFIX, userKey, Constant.SEPARATOR);
		return readCartRedisManager.get(key);
	}
	
	/**
	 * 购物车变化添加时间戳
	 * 
	 * @param userKey
	 */
	public void setCartTimeStamp(String userKey) {
		String key = StringUtil.join(Constant.CART_CHANGE_KEY_PREFIX, userKey, Constant.SEPARATOR);
		String timeStamp = System.currentTimeMillis() + "";
		writeCartRedisManager.setex(key, 24*60*60, timeStamp);
		logger.info(key + ",timeStamp:" + timeStamp);
	}
	
	public MiniCartResult getMiniCartList(final String key, List<CartInfo> cartList) {
		if (StringUtil.empty(key) || cartList == null || cartList.size() <= 0) {
			logger.info("购物车信息为NULL");
			return ResultGenerator.createMiniCartResult(ResultGenerator.SUCCESS_CODE, null, null, 0, 0);
		}
		long doneTime = System.currentTimeMillis();

		final List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		// 同步辅助类
		final CountDownLatch countDownLatch = new CountDownLatch(cartList.size());
		for (final CartInfo cart : cartList) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						// 查询商品详情
						long sTime = System.currentTimeMillis();
						GoodsInfo goods = productservice.getGoods(cart);
						logger.info(key + "|" + cart.getProductId() + "|" + cart.getSkuNum() + ",商品详情接口getGoods调用时间：" + (System.currentTimeMillis() - sTime) + " ms!!");
						if (goods != null)
							list.add(goods);
					} catch (Exception e) {
						logger.info(key + "连接商品服务失败", e);
					} finally {
						countDownLatch.countDown();
					}
				}
			});
		}

		try { countDownLatch.await(Constant.DB_THREAD_TIMEOUT, TimeUnit.MILLISECONDS); } catch (Exception e) { }

		if (countDownLatch.getCount() > 0) {
			logger.info(key + "======================读取商品数据超时!=====================");
			return ResultGenerator.createMiniCartResult(ResultGenerator.FAIL_CODE, ResultGenerator.DATA_TIME_OUT, null, 0, 0);
		}
		logger.info(key + ",cartList::获取商品详情信息调用总时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");

		// 购物车列表项
		List<ItemPlus> _list = new ArrayList<ItemPlus>();
		int invalidCount = sortMiniCartByShop(list, _list);
		
		int validCount = 0;
		if(list != null && list.size() > 0)
			validCount = list.size() - invalidCount;

		return ResultGenerator.createMiniCartResult(ResultGenerator.SUCCESS_CODE, null, _list, validCount, invalidCount);
	}
	
	/**
	 * 获取失效商品数   AND 购物车商品集合
	 * 
	 * @param list
	 * @param _list
	 * @return
	 */
	private int sortMiniCartByShop(List<GoodsInfo> list, List<ItemPlus> _list) {
		if (list == null || list.size() <= 0) {
			logger.info("sortByShop::::param is NULL");
			return 0;
		}
		sort(list);
		int i = 0;
		int j = 0;
		/**** 按商家分组 ****/
		for (GoodsInfo info : list) {
			if (info == null || info.getProductInfo() == null || info.getSeller() == null)
				continue;
			
			if (info.getProductInfo().getProduct().getActiveState() >= Constant.PRODUCT_SELL_STATE_START && info.getProductInfo().getProduct().getActiveState() <= Constant.PRODUCT_SELL_STATE_END &&
					info.getProductInfo().getSkuCount() > 0 &&
					(info.getProductInfo().getProduct().getMaxBuyLimit() == 0 || info.getProductInfo().getProduct().getMaxBuyLimit() > info.getProductInfo().getCurrBuy())) {
				if(i == 5)
					continue;
				else
					i++;
				_list.add(info.getProductInfo());
			} else {
				j++;
			}
		}
		return j;
	}
	
}
