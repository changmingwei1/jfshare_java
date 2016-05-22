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
    private String optType;
    private String optTime;

    public OrderOpt() {
    }

    public OrderOpt(String orderId, String optTypeß) {
        this.orderId = orderId;
        this.optType = optType;
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

    @Override
    public String toString() {
        return "OrderOpt{" +
                "orderId='" + orderId + '\'' +
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

        public void addToOrderOptList(String orderId, String optType) {
            if(CollectionUtils.isEmpty(this.orderOpts)) {
                this.orderOpts = new ArrayList<>();
            }

            this.orderOpts.add(new OrderOpt());
        }
    }
}
