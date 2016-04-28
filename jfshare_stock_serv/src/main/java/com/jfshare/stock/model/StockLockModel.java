package com.jfshare.stock.model;

/**
 * Created by Lenovo on 2015/10/19.
 */
public class StockLockModel {
    private String transId;
    private String productId;
    private int storehouseId;
    private String skuNum;
    private int countChange;
    private int lockCountChange;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
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

    public int getCountChange() {
        return countChange;
    }

    public void setCountChange(int countChange) {
        this.countChange = countChange;
    }

    public int getLockCountChange() {
        return lockCountChange;
    }

    public void setLockCountChange(int lockCountChange) {
        this.lockCountChange = lockCountChange;
    }

    public int getStorehouseId() {
        return storehouseId;
    }

    public void setStorehouseId(int storehouseId) {
        this.storehouseId = storehouseId;
    }
}
