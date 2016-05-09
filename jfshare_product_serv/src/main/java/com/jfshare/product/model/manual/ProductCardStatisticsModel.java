package com.jfshare.product.model.manual;

import org.joda.time.DateTime;

/**
 * Created by Lenovo on 2016/5/7.
 */
public class ProductCardStatisticsModel {
    private String productId;
    private String productName;
    private int total;
    private int usedNum;
    private int unusedNum;
    private DateTime createTime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(int usedNum) {
        this.usedNum = usedNum;
    }

    public int getUnusedNum() {
        return unusedNum;
    }

    public void setUnusedNum(int unusedNum) {
        this.unusedNum = unusedNum;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }
}
