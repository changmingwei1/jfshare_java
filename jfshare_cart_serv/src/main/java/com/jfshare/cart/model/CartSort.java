package com.jfshare.cart.model;

import com.jfshare.finagle.thrift.seller.Seller;

import java.util.List;

/**
 * 用于购物车排序
 */
public class CartSort {

	private List<GoodsInfo> goodsList;
	
	private Seller seller;
	
	//用于店铺排序
	private long longTime;

	
	public List<GoodsInfo> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GoodsInfo> goodsList) {
		this.goodsList = goodsList;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public long getLongTime() {
		return longTime;
	}

	public void setLongTime(long longTime) {
		this.longTime = longTime;
	}
}
