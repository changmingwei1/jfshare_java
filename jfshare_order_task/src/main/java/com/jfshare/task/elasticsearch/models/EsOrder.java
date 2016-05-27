package com.jfshare.task.elasticsearch.models;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.task.util.DateTimeUtil;
import org.joda.time.DateTime;

/**
 * Created by Lenovo on 2016/3/28.
 */
public class EsOrder {
    private String orderId;
    private int userId;
    private int sellerId;
//    private long createTime;
    private int order_state;
    private String orderJson;
    private DateTime createTime;

    public EsOrder() {}

    public EsOrder(Order order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUserId();
        this.sellerId = order.getSellerId();
//        this.createTime = DateTimeUtil.strToDateTime(order.getCreateTime(), DateTimeUtil.FORMAT_DEFAULT).getMillis();
        this.createTime = DateTimeUtil.strToDateTime(order.getCreateTime());
        this.order_state = order.getOrderState();
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

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
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
                ", createTime=" + createTime +
                ", order_state=" + order_state +
                ", orderJson='" + orderJson + '\'' +
                '}';
    }

    public String toJSONStr(){
        return JSON.toJSONString(this);
    }
}
