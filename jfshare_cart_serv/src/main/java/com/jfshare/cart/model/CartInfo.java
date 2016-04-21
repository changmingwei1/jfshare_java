package com.jfshare.cart.model;

import com.jfshare.cart.util.Constant;
import com.jfshare.cart.util.StringUtil;

/**
 * 购物车信息
 * 
 */
public class CartInfo {

	/**
	 * cart key
	 */
	private String userId;

	/**
	 * 商品ID
	 */
	private String productId;
	/**
	 * SKU
	 */
	private String skuNum;
	/**
	 * 商品数量
	 */
	private int count;
	/**
	 * 商品价格
	 */
	private String price;
	/**
	 * 时间戳
	 */
	private long longTime;

	/**
	 * 表明订单是来自购物车 OR 是立即下单
	 */
	private String wi;

	/**
	 * 获取购物车KEY
	 * 
	 * @return
	 */
	public String getKey() {
		return StringUtil.join(Constant.CART_KEY_PREFIX, userId, Constant.SEPARATOR);
	}

	/**
	 * 获取购物车FIELD
	 * 
	 * @return
	 */
	public String getField() {
		return StringUtil.join(productId, StringUtil.empty(skuNum) ? "" : skuNum, Constant.SEPARATOR);
	}
	
	/**
	 * 获取购物车value
	 * 
	 * @return
	 */
	public String getValue() {
		StringBuilder buf = new StringBuilder();
		buf.append(count).append(Constant.SEPARATOR).append(price).append(Constant.SEPARATOR).append(longTime).append(Constant.SEPARATOR)
				.append(StringUtil.empty(wi) ? "" : wi);
		return buf.toString();
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(String skuNum) {
		this.skuNum = skuNum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public long getLongTime() {
		return longTime;
	}

	public void setLongTime(long longTime) {
		this.longTime = longTime;
	}

	public String getWi() {
		return wi;
	}

	public void setWi(String wi) {
		this.wi = wi;
	}

}
