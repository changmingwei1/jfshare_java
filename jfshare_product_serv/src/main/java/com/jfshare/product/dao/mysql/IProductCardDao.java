package com.jfshare.product.dao.mysql;

import com.jfshare.product.dao.BaseDAO;
import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/28.
 */
public interface IProductCardDao extends BaseDAO<TbProductCard, Integer>{
    /**
     * 获取可以使用的卡密信息，需要商品ID，交易唯一标识（transactionId）
     * @param queryMap 查询参数
     * @return
     */
    List<TbProductCard> selectAvailableCard(Map queryMap);

    /**
     * 锁定卡密信息，需要商品ID，交易唯一标识（transactionId），获取卡密个数
     * @param lockMap
     * @return
     */
    int lockProductCard(Map lockMap);

    /**
     * 释放卡密记录，需要商品ID，交易唯一标识（transactionId）
     * @param releaseMap
     * @return
     */
    int releaseProductCard(Map releaseMap);

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
