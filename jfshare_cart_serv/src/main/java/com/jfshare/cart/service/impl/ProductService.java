package com.jfshare.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfshare.cart.dao.redis.MapRedisManager;
import com.jfshare.cart.model.CartInfo;
import com.jfshare.cart.model.GoodsInfo;
import com.jfshare.cart.model.ProductSkuInfo;
import com.jfshare.cart.server.depend.TradeClient;
import com.jfshare.cart.server.depend.ProductClient;
import com.jfshare.cart.server.depend.SellerClient;
import com.jfshare.cart.server.depend.StockClient;
import com.jfshare.cart.util.*;
import com.jfshare.finagle.thrift.cart.ItemPlus;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductRetParam;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品服务
 *
 */
@Service("productservice")
public class ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private MapRedisManager mapRedisManagerRdb;
	@Autowired
	private MapRedisManager writeCartRedisManager;
	@Autowired
	private SellerClient sellerClient;
	@Autowired
	private TradeClient tradeClient;
	@Autowired
	private ProductClient productClient;
	@Autowired
	private StockClient stockClient;
	
	
	public ProductSkuInfo getProductSkuInfo(String productId, String skuNum, Integer userId) {
		long doneTime = System.currentTimeMillis();
		String param = StringUtil.join(userId + "", productId, skuNum, Constant.SEPARATOR);
		LOGGER.info("getProductSkuInfo param :::" + param);
		if (StringUtil.empty(productId))
			return null;

		skuNum = skuNum == null ? "" : skuNum;

		ProductRetParam pParam = new ProductRetParam(1, 0, 0, 0);
		Product pBase = productClient.queryProduct(productId, pParam);
		if(pBase == null){
			LOGGER.info(param + ",queryProduct商品信息返回错误");
			return null;
		}

		StockInfo stockForSku = stockClient.getStockForSku(productId, skuNum);
		if(stockForSku == null || CollectionUtils.isEmpty(stockForSku.getStockItems())){
			LOGGER.info(param + ",getStockForSku库存信息返回错误");
			return null;
		}

		int currBuy = 0;
		if(userId != 0) {    //未登录的情况不查询已购数量
			currBuy = tradeClient.buyCount(userId, productId);
			if (currBuy < 0) {
				LOGGER.info(param + ",getStockForSku买家历史购买量信息返回错误");
				return null;
			}
		}
		
		ProductSkuInfo p = new ProductSkuInfo();
		p.setCurrBuy(currBuy);
		p.setMaxBuyLimit(pBase.getMaxBuyLimit());
		p.setSkuStock(stockForSku.getTotal());
		p.setTotalStock(stockForSku.getTotal());
		
		LOGGER.info(param + ",getProductSkuInfo接口调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
		return p;
	}
	
	public GoodsInfo getGoods(CartInfo cart){
		if(cart == null)
			return null;
		
		cart.setSkuNum(cart.getSkuNum() == null ? "" : cart.getSkuNum());

		ProductRetParam pParam = new ProductRetParam(1, 0, 1, 0);
		Product pHotSku = productClient.queryHotSKU(cart.getProductId(), cart.getSkuNum(), pParam);
		if(pHotSku == null){
			LOGGER.info(cart.getUserId() + "," + cart.getField() + ",queryHotSKU查询商品指定sku返回错误");
			return null;
		}

		if (pHotSku.getType() > 0 && !Constant.COMMON_PRODUCT_TYPE.equals(ConvertUtil.getString(pHotSku.getType()))) { // 删除非普通商品
			writeCartRedisManager.del(cart.getKey(), StringUtil.join(cart.getProductId(), cart.getSkuNum(), Constant.SEPARATOR));
			LOGGER.info(cart.getUserId() + "清除非普通商品:" + cart.getField());
			return null;
		}

		Seller sellerInfo = sellerClient.getSellerInfoByCache(pHotSku.getSellerId());
		if(sellerInfo == null){
			LOGGER.info(cart.getUserId() + "," + cart.getField() + ",getSellerInfoByCache商家信息返回为NULL");
			return null;
		}

		StockInfo stockForSku = stockClient.getStockForSku(cart.getProductId(), cart.getSkuNum());
		if(stockForSku == null){
			LOGGER.info(cart.getUserId() + "," + cart.getField() + ",getStockForSku库存信息返回错误");
			return null;
		}
		
		GoodsInfo goods = new GoodsInfo();
		goods.setCount(cart.getCount());
		goods.setLongTime(cart.getLongTime());

		ItemPlus itemPlus = new ItemPlus();
		Product product = new Product();
		ProductSku productSku = new ProductSku();
		product.setProductSku(productSku);
		itemPlus.setProduct(product);

		//填充itemPlus
		itemPlus.setCartPrice(cart.getPrice());
		itemPlus.setCount(cart.getCount());
		itemPlus.setWi(StringUtil.empty(cart.getWi()) ? "" : cart.getWi());
		itemPlus.setTotalCount(stockForSku.getTotal()); //总库存数
		//填充product
		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(pHotSku);
		BeanUtil.fillBeanData(product, stringObjectMap);
		//填充productSku
		productSku.setSkuNum(cart.getSkuNum());
		//SKU
		if(pHotSku.getProductSku() == null){	//SKU不存在，此商品失效
			goods.setInvalidProductInfo(itemPlus);
		}else{
			productSku.setCurPrice(pHotSku.getProductSku().getCurPrice());
			productSku.setOrgPrice(pHotSku.getProductSku().getOrgPrice());
			if(!StringUtil.empty(pHotSku.getProductSku().getVPicture())) {
				productSku.setVPicture(pHotSku.getProductSku().getVPicture());
			}
			productSku.setSkuName(pHotSku.getProductSku().getSkuName());
			itemPlus.setSkuCount(stockForSku.getTotal()); //当前sku库存数
			itemPlus.setLockCount(stockForSku.getLockTotal()); //当前sku锁定库存数
			
			goods.setProductInfo(itemPlus);
		}

//		goods.setProductInfo(itemPlus);
		goods.setSeller(sellerInfo);

		return goods;
	}
}