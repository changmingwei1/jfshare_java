package com.jfshare.task.elasticsearch.models;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.task.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;

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
        this.orderJson = getJsonStr(order);
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

    public String getJsonStr(Order order){
        PropertyFilter filter = new PropertyFilter() {
            //过滤不需要的字段
            public boolean apply(Object source, String name, Object value) {
                if(value == null || StringUtils.isBlank(String.valueOf(value))
                        || (name.length()>3 && name.startsWith("set") && name.charAt(3) >=65 && name.charAt(3) <=90)){
                    return false;
                }
                return true;
            }
        };

        return JSON.toJSONString(order, filter);
    }

    public String toJsonStr() {
        return JSON.toJSONString(this);
    }
}
