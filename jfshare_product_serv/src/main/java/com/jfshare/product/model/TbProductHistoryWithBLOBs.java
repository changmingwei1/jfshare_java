package com.jfshare.product.model;

public class TbProductHistoryWithBLOBs extends TbProductHistory {
    private String skuTemplate;

    private String attribute;

    private String productSku;

    public String getSkuTemplate() {
        return skuTemplate;
    }

    public void setSkuTemplate(String skuTemplate) {
        this.skuTemplate = skuTemplate == null ? null : skuTemplate.trim();
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku == null ? null : productSku.trim();
    }
}