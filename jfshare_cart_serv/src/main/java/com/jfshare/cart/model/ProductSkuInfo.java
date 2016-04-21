package com.jfshare.cart.model;

public class ProductSkuInfo {
	
	private int maxBuyLimit;
	private int skuStock;
	private int currBuy;
	private int totalStock;
	
	public int getMaxBuyLimit() {
		return maxBuyLimit;
	}
	public void setMaxBuyLimit(int maxBuyLimit) {
		this.maxBuyLimit = maxBuyLimit;
	}
	public int getSkuStock() {
		return skuStock;
	}
	public void setSkuStock(int skuStock) {
		this.skuStock = skuStock;
	}
	public int getCurrBuy() {
		return currBuy;
	}
	public void setCurrBuy(int currBuy) {
		this.currBuy = currBuy;
	}
	public int getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(int totalStock) {
		this.totalStock = totalStock;
	}
	
	
}
