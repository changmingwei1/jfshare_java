package com.jfshare.baseTemplate.mybatis.model.manual;

/**
 * Created by Lenovo on 2016/5/14.
 */
public class ProductStorehouseModel {

    private int sellerId;
    private String productId;
    private int storehouseId;

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getStorehouseId() {
        return storehouseId;
    }

    public void setStorehouseId(int storehouseId) {
        this.storehouseId = storehouseId;
    }
}
