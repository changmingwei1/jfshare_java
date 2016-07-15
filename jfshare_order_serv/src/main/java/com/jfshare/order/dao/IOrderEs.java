package com.jfshare.order.dao;

import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.order.model.EsScore;
import com.jfshare.order.util.ConstantUtil;
import org.elasticsearch.search.SearchHits;

/**
 * Created by stepyee on 16/3/15.
 */
public interface IOrderEs {

    SearchHits search(OrderQueryConditions orderQueryConditions);

    SearchHits searchScoreRecord(String orderBatch, ConstantUtil.SCORE_TYPE scoreType);

    void addScoreRecord(EsScore esScore);

}
