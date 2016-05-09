package com.jfshare.product.model.mapper.manual;

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
}