package com.jfshare.order.model;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.order.util.DateTimeUtil;

import java.util.Date;

/**
 * Created by Lenovo on 2016/3/28.
 */
public class EsOrder {
    private String orderId;
    private int userId;
    private int sellerId;
    private int orderState;
    private String orderJson;
    private Date orderCreateTime;

    public EsOrder() {}

    public EsOrder(Order order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUserId();
        this.sellerId = order.getSellerId();
        this.orderCreateTime = DateTimeUtil.strToDateTime(order.getCreateTime()).toDate();
        this.orderState = order.getOrderState();
        this.orderJson = JSON.toJSONString(order);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(String orderJson) {
        this.orderJson = orderJson;
    }



    @Override
    public String toString() {
        return "EsOrder{" +
                "orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", sellerId=" + sellerId +
                ", orderCreateTime=" + orderCreateTime +
                ", orderState=" + orderState +
                ", orderJson='" + orderJson + '\'' +
                '}';
    }

    public String toJSONStr(){
        return JSON.toJSONString(this);
    }
}
