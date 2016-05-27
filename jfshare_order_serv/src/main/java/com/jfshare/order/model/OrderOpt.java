package com.jfshare.order.model;

import com.jfshare.order.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bq on 16/5/22.
 */
public class OrderOpt {
    private String orderId;
    private int sellerId;
    private int userId;
    private String optType;
    private String optTime;

    public OrderOpt() {
    }

    public OrderOpt(String orderId, String optType, int userId, int sellerId) {
        this.orderId = orderId;
        this.optType = optType;
        this.userId = userId;
        this.sellerId = sellerId;
        this.optTime = DateTimeUtil.DateTimeToStr(DateTime.now());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderOpt{" +
                "orderId='" + orderId + '\'' +
                ", sellerId=" + sellerId +
                ", userId=" + userId +
                ", optType='" + optType + '\'' +
                ", optTime='" + optTime + '\'' +
                '}';
    }

    public static class OrderOptPush{
        private List<OrderOpt> orderOpts;

        public List<OrderOpt> getOrderOpts() {
            return orderOpts;
        }

        public void setOrderOpts(List<OrderOpt> orderOpts) {
            this.orderOpts = orderOpts;
        }

        @Override
        public String toString() {
            return "OrderOptPush{" +
                    "orderOpts=" + orderOpts +
                    '}';
        }

        public void addToOrderOptList(OrderOpt orderOpt) {
            if(CollectionUtils.isEmpty(this.orderOpts)) {
                this.orderOpts = new ArrayList<>();
            }

            this.orderOpts.add(orderOpt);
        }

        public void addToOrderOptList(String orderId, String optType, int userId, int sellerId) {
            if(CollectionUtils.isEmpty(this.orderOpts)) {
                this.orderOpts = new ArrayList<>();
            }

            this.orderOpts.add(new OrderOpt(orderId, optType, userId, sellerId));
        }
    }
}
