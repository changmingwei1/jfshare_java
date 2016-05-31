package com.jfshare.order.dao.impl.elasticsearch;

import com.jfshare.elasticsearch.drive.ESClient;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.order.dao.IOrderEs;
import com.jfshare.order.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
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
        String startTime = conditions.getStartTime();
        String endTime = conditions.getEndTime();
        int orderState = conditions.getOrderState();
        String[] monthArr = DateTimeUtil.getBetweenMonth(startTime, endTime);
        String[] indexArr = new String[monthArr.length];
        for(int i = 0; i<  monthArr.length; i++) {
            indexArr[i] = "jfshare_order_" + monthArr[i];
        }

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();


        if(orderState > 0) {
            if(orderState < 10) {
                queryBuilder.must(QueryBuilders.rangeQuery("orderState")
                        .from(orderState)
                        .to((orderState + 1) * 10 - 1));
            } else {
                queryBuilder.must(QueryBuilders.matchQuery("orderState", orderState));
            }
        }

        if(StringUtils.isNotBlank(conditions.getOrderId())) {
            queryBuilder.must(QueryBuilders.matchQuery("orderId", conditions.getOrderId()));
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
            queryBuilder.must(QueryBuilders.matchQuery("UserId", conditions.getUserId()));
        }

        queryBuilder.filter(QueryBuilders.rangeQuery("orderCreateTime")
                .from(DateTimeUtil.strToDateTime(startTime))
                .to(DateTimeUtil.strToDateTime(endTime)));
        SearchRequestBuilder searchRequestBuilder = this.esClient.getTransportClient().prepareSearch(indexArr)
                .setTypes("esOrder")
                .setQuery(queryBuilder)
                .setFrom((conditions.getCurPage() - 1) * conditions.getCount())
                .setExplain(true)
                .addSort(SortBuilders.fieldSort("orderCreateTime").order(SortOrder.DESC));

        if(conditions.getCount() > 0 && conditions.getCurPage() > 1) {
            searchRequestBuilder.setSize(conditions.getCurPage() * conditions.getCount());
        }

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        logger.info("ES==| search {}$$查询结果", searchResponse.getHits().getHits());
        return searchResponse.getHits();

    }
}
