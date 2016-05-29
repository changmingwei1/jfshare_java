package com.jfshare.task.service.impl;

import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.task.server.depend.ScoreClient;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bq on 16/5/29.
 */
@Service
public class OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private ScoreClient scoreClient;

    /**
     * 订单支付成功赠送用户积分, 1元送一分, 不足一元部分不送
     * @param order
     */
    public void afterOrderPay(Order order) {

        int incomeScore = getScoreWithOrder(order);
        if(incomeScore > 0)
            scoreClient.incomeScore(order.getUserId(), incomeScore);
    }

    /**
     * 订单支付后关闭需要扣减支付时赠送的积分
     * @param order
     */
    public void afterOrderClose(Order order) {
        if(order.getPayInfo().getPayState() != 1) {
            return;
        }

        int score = getScoreWithOrder(order);
        if(score > 0)
            scoreClient.reduceScore(order.getUserId(), score);
    }

    private int getScoreWithOrder(Order order) {
        int amount = PriceUtils.strToInt(order.getClosingPrice());
        if(BizUtil.PAY_CHANNEL.TIAN_YI.getEnumVal() == NumberUtils.toInt(order.getPayInfo().getPayChannel(), -1)) {
            for(OrderInfo orderInfo : order.getProductList()) {
                int ThirdExchangAmount = NumberUtils.toInt(orderInfo.getThirdExchangeRate());
                if(ThirdExchangAmount > 1) {
                    amount -= (ThirdExchangAmount - 1) * 100;
                }
            }
        }
        return amount / 100;
    }
}
