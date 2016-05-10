package com.jfshare.baseTemplate.mybatis.model.manual;

/**
 * Created by Lenovo on 2016/5/8.
 */
public class CalculatePostageModel {

    private int templateId;
    private int number;
    private int weight;
    private String orderAmount;
    private String sendToProvince;

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getSendToProvince() {
        return sendToProvince;
    }

    public void setSendToProvince(String sendToProvince) {
        this.sendToProvince = sendToProvince;
    }
}
