package com.jfshare.order.service;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.order.dao.IOrderEs;
import com.jfshare.order.model.EsScore;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.server.depend.ScoreClient;
import com.jfshare.order.util.ConstantUtil;
import com.jfshare.order.util.OrderUtil;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bq on 16/5/29.
 */
@Service
public class ScoreService {
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private ScoreClient scoreClient;

    @Autowired
    private IOrderEs orderEs;

    /**
     * 订单支付成功赠送用户积分, 1元送一分, 不足一元部分不送
     * @param orders
     */
    public void finishOrderPay(List<OrderModel> orders) {

        int incomeScore = OrderUtil.gettotalAmount(orders) / 100;
        int userId = orders.get(0).getUserId();
        String transId = orders.get(0).getOrderBatch();
        logger.info("finishOrderPay----支付完成增送积分----transId={}, userId={}, incomeScore={}", transId, userId, incomeScore);
        SearchHits searchHits = orderEs.searchScoreRecord(transId, ConstantUtil.SCORE_TYPE.order_rebate);
        logger.info("finishOrderPay----支付完成赠送积分----重复赠送:{}, hitTotal={}, SearchHit={}", searchHits.getTotalHits()>0, searchHits.getTotalHits(), JSON.toJSONString(searchHits.getHits()));
        if(searchHits.getTotalHits() == 0) {
            scoreClient.incomeScore(userId, incomeScore, transId, ConstantUtil.SCORE_TYPE.order_rebate, transId);
        }
    }

    /**
     * 订单支付后关闭需要扣减支付时赠送的积分
     * @param order
     */
    public void afterOrderClose(OrderModel order) {

        SearchHits searchHits = orderEs.searchScoreRecord(order.getOrderBatch(), null);
        logger.info("afterOrderClose----查询----重复赠送:{}, hitTotal={}, SearchHit={}", searchHits.getTotalHits()>0, searchHits.getTotalHits(), JSON.toJSONString(searchHits.getHits()));
        List<EsScore> esScores = toEsScoreList(searchHits);
        if(order.getExchangeScore() > 0) {
            logger.info("afterOrderClose----使用了积分抵现----exchangeScore={}", order.getExchangeScore());
            if(checkRepeatScore(esScores, ConstantUtil.SCORE_TYPE.rollback_cost_online, order.getOrderId()) == false) {
                logger.info("afterOrderClose----返还支付抵现积成功", order.getExchangeScore());
                scoreClient.incomeScore(order.getUserId(), order.getExchangeScore(), order.getOrderBatch(), ConstantUtil.SCORE_TYPE.rollback_cost_online, "");
            }
        }

        if(order.getPayState() != 1 || checkRepeatScore(esScores, ConstantUtil.SCORE_TYPE.order_rebate, null) == false) {  //未支付订单没增送过积分
            logger.info("afterOrderClose----未支付过没赠送过积分");
            return;
        }

        if(checkRepeatScore(esScores, ConstantUtil.SCORE_TYPE.rollback_order_rebate, order.getOrderId()) == true) {   //已经扣过增送积分
            logger.info("afterOrderClose----之前已扣减过赠送积分, 不再重复扣减");
            return;
        }

        int orderBatchCount = Integer.parseInt(order.getOrderBatch().split("-")[2]);    //一批订单的数量
        int score = order.getClosingPrice() / 100;
        int orderRebateScore = filterEsScore(esScores, ConstantUtil.SCORE_TYPE.order_rebate, null).get(0).getScore();
        logger.info("afterOrderClose----orderBatch订单数量:{}, orderRebateScore={}", orderBatchCount, orderRebateScore);
        List<EsScore> rollbackOrderRebateAll = filterEsScore(esScores, ConstantUtil.SCORE_TYPE.rollback_order_rebate, null);

        /*
         * 一批只有一个订单：score = orderRebateScore
         * 一批多个订单， 且为最后一个订单：score = orderRebateScore - rollbackOrderRebateAll(已扣减增送积分累计)
         * 一批多个订单， 且非最后一个订单：score = closeingPrice / 100
         */
        if(orderBatchCount == 1) {
            score = orderRebateScore;
        } else if(rollbackOrderRebateAll.size() == orderBatchCount - 1) {
            score = orderRebateScore - rollbackOrderRebateScore(rollbackOrderRebateAll);
        }

        logger.info("afterOrderClose----扣减赠送积分:{}", score);

        scoreClient.reduceScore(order.getUserId(), score, order.getOrderBatch(), ConstantUtil.SCORE_TYPE.rollback_order_rebate, order.getOrderId());

    }

    /**
     * 检测同一orderId， 同一type， 是否有过积分操作记录
     * @param esScores
     * @param type
     * @param orderId
     * @return  重复操作-true, 非重复操作-false
     */
    private boolean checkRepeatScore(List<EsScore> esScores, ConstantUtil.SCORE_TYPE type, String orderId) {
        return CollectionUtils.isNotEmpty(filterEsScore(esScores, type, orderId));
    }

    /**
     * 累加已扣减增送积分数
     * @param rollbackOrderRebateAll
     * @return
     */
    private int rollbackOrderRebateScore(List<EsScore> rollbackOrderRebateAll) {
        int s = 0;
        for(EsScore esScore : rollbackOrderRebateAll) {
            s += esScore.getScore();
        }
        return s;
    }

    /**
     * 根据积分类型， orderId过滤EsScore， orderId=null, 按积分类型过滤
     * @param esScores
     * @param type
     * @param orderId
     * @return
     */
    private List<EsScore> filterEsScore(List<EsScore> esScores, ConstantUtil.SCORE_TYPE type, String orderId) {
        if(CollectionUtils.isEmpty(esScores)) {
            return  esScores;
        }

        List<EsScore> fList = new ArrayList<>();
        for(EsScore esScore : esScores) {
            if((orderId == null || orderId.equals(esScore.getOrderId())) && esScore.getType() == type.getEnumVal()) {
                fList.add(esScore);
            }
        }
        return  fList;
    }

    private List<EsScore> toEsScoreList(SearchHits searchHits) {
        List<EsScore> esScores = new ArrayList<>();
        if(searchHits.getTotalHits() == 0 || ArrayUtils.isEmpty(searchHits.getHits())) {
            return esScores;
        }

        for(SearchHit hit : searchHits.getHits()) {
            esScores.add(JSON.parseObject(hit.getSourceAsString(), EsScore.class));
        }
        return esScores;
    }
}
