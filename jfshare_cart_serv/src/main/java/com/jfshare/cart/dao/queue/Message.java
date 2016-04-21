package com.jfshare.cart.dao.queue;

public class Message {

	private int userId;
	private String productId;
	private String skuNum;
	private int flag; // 1: 添加; 2:删除

	public Message() {
	}

	public Message(int userId, String productId, String skuNum, int flag) {
		this.userId = userId;
		this.productId = productId;
		this.skuNum = skuNum;
		this.flag = flag;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
