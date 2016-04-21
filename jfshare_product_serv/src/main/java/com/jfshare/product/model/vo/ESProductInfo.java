package com.jfshare.product.model.vo;

/**
 * 
 * 存放在es中的商品实例，查询出商品id，供搜索使用
 * 
 * @author L
 */
public class ESProductInfo {

	private String productId;
	
	private String name;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
