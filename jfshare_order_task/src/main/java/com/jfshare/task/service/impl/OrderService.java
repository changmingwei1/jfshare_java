package com.jfshare.task.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.task.server.depend.MessageClient;
import com.jfshare.task.util.Constant;
import com.jfshare.task.util.HandlerBarsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bq on 16/5/29.
 */
@Service
public class OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private MessageClient messageClient;

    /**
     * 订单支付成功
     * @param order
     */
    public void afterOrderPay(Order order) {

    }

    /**
     * 订单确认收货
     * @param order
     */
    public void afterOrderConfirm(Order order) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", order.getOrderId());
        String msg = HandlerBarsUtil.replace(Constant.MSG_TEMPLATE_ORDER_CONFIRM_RECEIPT, map);
        this.messageClient.pushMessage(order.getUserId(), Constant.MSG_TITLE_ORDER_DELIVER, msg, "50");
    }

    public void afterOrderDeliver(Order order) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", order.getOrderId());
        String msg = HandlerBarsUtil.replace(Constant.MSG_TEMPLATE_ORDER_DELIVER, map);
        this.messageClient.pushMessage(order.getUserId(), Constant.MSG_TITLE_ORDER_DELIVER, msg, "40");
    }
}
