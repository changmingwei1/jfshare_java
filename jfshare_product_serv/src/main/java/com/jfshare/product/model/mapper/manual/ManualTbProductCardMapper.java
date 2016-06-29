package com.jfshare.product.model.mapper.manual;

import com.jfshare.finagle.thrift.product.AldCaptchaItem;
import com.jfshare.finagle.thrift.product.DayAldCaptchaCount;
import com.jfshare.finagle.thrift.product.DayAldCaptchaItem;
import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;

import java.util.List;
import java.util.Map;

public interface ManualTbProductCardMapper {

    List<TbProductCard> selectAvailableCard(Map queryMap);

    int lockProductCard(Map lockMap);

    int releaseProductCard(Map releaseMap);

    int queryProductCardViewListCount(Map queryMap);

    List<TbProductCard> queryProductCardViewList(Map queryMap);

    List<ProductCardStatisticsModel> statisticsProductCard(Map queryMap);

    int statisticsProductCardCount(Map queryMap);

    /**
     * 卖家app验证码统计总数
     * @param queryMap
     * @return
     */
    int sellerProductCardCount(Map queryMap);

    /**
     * 卖家app验证码总列表
     * @param queryMap
     * @return
     */
    List<AldCaptchaItem> sellerProductCardList(Map queryMap);

    /**
     * 卖家app按月查询每天总量总数
     * @param queryMap
     * @return
     */
    int sellerProductCardDayAllCount(Map queryMap);

    /**
     * 卖家app按月查询每天总量列表
     * @param queryMap
     * @return
     */
    List<DayAldCaptchaCount> sellerProductCardDayAllList(Map queryMap);

    /**
     * 卖家app验证码按天列表详情总数
     * @param queryMap
     * @return
     */
    int sellerProductCardDayCount(Map queryMap);

    /**
     * 卖家app验证码按天列表详情
     * @param queryMap
     * @return
     */
    List<DayAldCaptchaItem> sellerProductCardDayList(Map queryMap);

    /**
     * 统计某天或者某月数量
     * @param queryMap
     * @return
     */
    int getProductCardCount(Map queryMap);

    /**
     * 获取已验证数量
     * @param queryMap
     * @return
     */
    int queryProductCardCheckCount(Map queryMap);

    /**
     * 获取已验证列表
     * @param queryMap
     * @return
     */
    List<TbProductCard> queryProductCardCheckList(Map queryMap);

}