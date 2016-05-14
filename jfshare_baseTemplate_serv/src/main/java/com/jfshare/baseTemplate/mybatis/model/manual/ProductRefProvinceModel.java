package com.jfshare.baseTemplate.mybatis.model.manual;

/**
 * Created by Lenovo on 2016/5/14.
 */
public class ProductRefProvinceModel {

    private int sellerId;
    private String productId;
    private String storehouseIds;
    private String sendToProvince;

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

    public String getStorehouseIds() {
        return storehouseIds;
    }

    public void setStorehouseIds(String storehouseIds) {
        this.storehouseIds = storehouseIds;
    }

    public String getSendToProvince() {
        return sendToProvince;
    }

    public void setSendToProvince(String sendToProvince) {
        this.sendToProvince = sendToProvince;
    }
}
