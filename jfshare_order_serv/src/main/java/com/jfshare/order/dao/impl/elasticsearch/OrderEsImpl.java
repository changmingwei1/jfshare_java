package com.jfshare.order.dao.impl.elasticsearch;

import com.jfshare.elasticsearch.drive.ESClient;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.order.dao.IOrderEs;
import com.jfshare.order.model.EsScore;
import com.jfshare.order.util.ConstantUtil;
import com.jfshare.order.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lenovo on 2016/5/31.
 */
@Service
public class OrderEsImpl implements IOrderEs{
    private Logger logger = LoggerFactory.getLogger(OrderEsImpl.class);

    @Autowired
    private ESClient esClient;

    public SearchHits search(OrderQueryConditions conditions) {
        if(conditions.getCurPage() <= 0) {
            conditions.setCurPage(1);
        }
        String startTime = conditions.getStartTime();
        if(StringUtils.isBlank(startTime)
                || DateTimeUtil.strToDateTime(startTime).isBefore(DateTimeUtil.strToDateTime("2016-05-01 00:00:00"))) {
            startTime = "2016-05-01 00:00:00";
        }
        String endTime = StringUtils.isBlank(conditions.getEndTime()) ? DateTimeUtil.getCurrentDate(DateTimeUtil.FORMAT_DEFAULT) : conditions.getEndTime();
        if(DateTimeUtil.strToDateTime(endTime).isAfter(DateTime.now())) {
            endTime = DateTimeUtil.getCurrentDate(DateTimeUtil.FORMAT_DEFAULT);
        }
        logger.debug("esSearch----params:startTime={}, endTime={}, orderId={}, orderIds={}", startTime, endTime, conditions.getOrderId(), conditions.getOrderIds());
        int orderState = conditions.getOrderState();
        String[] monthArr = DateTimeUtil.getBetweenMonth(startTime, endTime);
        String[] indexArr = new String[monthArr.length];
        for(int i = 0; i<  monthArr.length; i++) {
            indexArr[i] = "jfshare_order_" + monthArr[i];
        }

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if(StringUtils.isNotBlank(conditions.getOrderId())) {
            queryBuilder.must(QueryBuilders.matchQuery("orderId", conditions.getOrderId()));
        }

        if(orderState > 0 && orderState != 1000) {
            if(orderState < 10) {
                queryBuilder.must(QueryBuilders.rangeQuery("orderState")
                        .from(orderState * 10)
                        .to((orderState + 1) * 10 - 1));
            } else {
                queryBuilder.must(QueryBuilders.matchQuery("orderState", orderState));
            }
        }

        if(CollectionUtils.isNotEmpty(conditions.getOrderIds())) {
            BoolQueryBuilder orderIdsQuery = QueryBuilders.boolQuery();
            for(String orderId : conditions.getOrderIds()) {
                orderIdsQuery.should(QueryBuilders.matchQuery("orderId", orderId));
            }
            queryBuilder.must(orderIdsQuery);
        }

        if(conditions.getSellerId() > 0) {
            queryBuilder.must(QueryBuilders.matchQuery("sellerId", conditions.getSellerId()));
        }

        if(conditions.getUserId() > 0) {
            queryBuilder.must(QueryBuilders.matchQuery("userId", conditions.getUserId()));
        }

        //传入orderId或orderIds忽略时间限制
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)
                && CollectionUtils.isEmpty(conditions.getOrderIds())
                && StringUtils.isBlank(conditions.getOrderId())) {
            queryBuilder.filter(QueryBuilders.rangeQuery("orderCreateTime")
                    .from(DateTimeUtil.strToDateTime(startTime))
                    .to(DateTimeUtil.strToDateTime(endTime)));
        }

        SearchRequestBuilder searchRequestBuilder = this.esClient.getTransportClient().prepareSearch(indexArr)
                .setTypes("esOrder")
                .setQuery(queryBuilder)
                .setFrom((conditions.getCurPage() - 1) * conditions.getCount())
                .setExplain(true)
                .addSort(SortBuilders.fieldSort("orderCreateTime").order(SortOrder.DESC));

        if(conditions.getCount() > 0 && conditions.getCurPage() >= 1) {
            searchRequestBuilder.setSize(conditions.getCount());
        }

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        logger.info("ES==| search {}$$查询结果", searchResponse.getHits().getHits());
        return searchResponse.getHits();

    }

    /**
     * 查询积分操作记录
     * @param orderBatch 订单批次号
     * @param scoreType 积分类型
     * @return
     */
    public SearchHits searchScoreRecord(String orderBatch, ConstantUtil.SCORE_TYPE scoreType) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("orderBatch", orderBatch));
        if(scoreType != null) {
            queryBuilder.must(QueryBuilders.matchQuery("type", scoreType.getEnumVal()));
        }

        SearchRequestBuilder searchRequestBuilder = this.esClient.getTransportClient().prepareSearch(ES_SCORE_INDEX)
                .setTypes(ES_SCORE_TYPE)
                .setQuery(queryBuilder)
                .setFrom(0).setSize(200)
                .setExplain(false);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        return searchResponse.getHits();
    }

    /**
     * 插入积分操作记录
     * @param esScore
     */
    public void addScoreRecord(EsScore esScore) {
        logger.info("ES==> addScoreRecord {}", esScore);
        try {
            esClient.add(ES_SCORE_INDEX, ES_SCORE_TYPE, esScore.toJSONString());
        } catch (Exception e) {
            logger.error("ES==ERROR addScoreRecord {}", esScore.toJSONString());
            logger.error("ES==>| addScoreRecord 发生异常", e);
        }
    }


    private final static String ES_SCORE_INDEX = "jfshare_order_score";
    private final static String ES_SCORE_TYPE = "esOrderScore";
}
