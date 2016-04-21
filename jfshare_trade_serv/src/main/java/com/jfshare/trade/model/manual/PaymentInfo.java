package com.jfshare.trade.model.manual;

public class PaymentInfo {

	private String createTime;

	private String cancelTime;

	private String price;

	private String thirdScore;
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getThirdScore() {
		return thirdScore;
	}

	public void setThirdScore(String thirdScore) {
		this.thirdScore = thirdScore;
	}

}
