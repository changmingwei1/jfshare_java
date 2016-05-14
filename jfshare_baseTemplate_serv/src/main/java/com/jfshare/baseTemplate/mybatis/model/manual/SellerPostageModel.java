package com.jfshare.baseTemplate.mybatis.model.manual;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/8.
 */
public class SellerPostageModel {

    private int sellerId;
    private List<ProductPostageModel> productPostageModels;

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public List<ProductPostageModel> getProductPostageModels() {
        return productPostageModels;
    }

    public void setProductPostageModels(List<ProductPostageModel> productPostageModels) {
        this.productPostageModels = productPostageModels;
    }
}
