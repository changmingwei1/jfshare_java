package com.jfshare.order.service;

import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.server.depend.ScoreClient;
import com.jfshare.order.util.ConstantUtil;
import com.jfshare.order.util.OrderUtil;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bq on 16/5/29.
 */
@Service
public class ScoreService {
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private ScoreClient scoreClient;

    /**
     * 订单支付成功赠送用户积分, 1元送一分, 不足一元部分不送
     * @param orders
     */
    public void finishOrderPay(List<OrderModel> orders) {

        int incomeScore = OrderUtil.gettotalAmount(orders) / 100;
        int userId = orders.get(0).getUserId();
        String transId = orders.get(0).getOrderBatch();
        if(incomeScore > 0)
            scoreClient.incomeScore(userId, incomeScore, transId, ConstantUtil.SCORE_TYPE.order_rebate);
    }

    /**
     * 订单支付后关闭需要扣减支付时赠送的积分
     * @param order
     */
    public void afterOrderClose(OrderModel order) {

        if(order.getExchangeScore() > 0) {
            scoreClient.incomeScore(order.getUserId(), order.getExchangeScore(), order.getOrderId(), ConstantUtil.SCORE_TYPE.rollback_cost_online);
        }

        if(order.getPayState() != 1) {
            return;
        }

        int score = order.getClosingPrice() / 100;

        if(score > 0)
            scoreClient.reduceScore(order.getUserId(), score, order.getOrderId(), ConstantUtil.SCORE_TYPE.rollback_order_rebate);
    }
}
