package com.jfshare.product.model;

public class TbProductWithBLOBs extends TbProduct {
    private String skuTemplate;

    private String attribute;

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
}