package com.jfshare.product.service;

import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/28.
 */
public interface IProductCartSvc {

    /**
     * 获取卡密信息，需要商品ID，交易唯一标识（transactionId）, 获取个数
     * @param queryMap
     * @return
     */
    List<TbProductCard> getProductCard(Map queryMap);

    /**
     * 查询虚拟商品卡密，根据transactionId，查询对应的卡密信息
     * @param queryMap
     * @return
     */
    List<TbProductCard> queryProductCard(Map queryMap);

    /**
     * 查询虚拟商品卡密统计列表，管理专用
     * @param queryMap
     * @return
     */
    List<ProductCardStatisticsModel> statisticsProductCard(Map queryMap);

    /**
     * 查询虚拟商品卡密统计列表记录数
     * @param queryMap
     * @return
     */
    int statisticsProductCardCount(Map queryMap);

    /**
     * 查询单个虚拟商品卡密列表，管理专用
     * @param queryMap
     * @return
     */
    List<TbProductCard> queryProductCardViewList(Map queryMap);

    /**
     * 查询单个虚拟商品卡密列表记录数
     * @param queryMap
     * @return
     */
    int queryProductCardViewListCount(Map queryMap);

    /**
     * 使用虚拟商品卡密
     * @param productCard
     * @return
     */
    int useProductCard(TbProductCard productCard);
}
