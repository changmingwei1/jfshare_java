package com.jfshare.cart.model;

import com.jfshare.finagle.thrift.cart.ItemPlus;
import com.jfshare.finagle.thrift.seller.Seller;

/**
 * 用于购物车排序
 *
 */
public class GoodsInfo {

	/**
	 * 商品信息
	 */
	private ItemPlus productInfo;
	
	/**
	 * SKU修改后失效的商品
	 */
	private ItemPlus invalidProductInfo;
	
	/**
	 * 商家信息
	 */
	private Seller seller;

	/**
	 * 商品数量
	 */
	private int count;
	
	/**
	 * 用于商品排序
	 */
	private long longTime;
	
	
	public ItemPlus getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ItemPlus productInfo) {
		this.productInfo = productInfo;
	}

	public long getLongTime() {
		return longTime;
	}

	public void setLongTime(long longTime) {
		this.longTime = longTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public ItemPlus getInvalidProductInfo() {
		return invalidProductInfo;
	}

	public void setInvalidProductInfo(ItemPlus invalidProductInfo) {
		this.invalidProductInfo = invalidProductInfo;
	}

}
