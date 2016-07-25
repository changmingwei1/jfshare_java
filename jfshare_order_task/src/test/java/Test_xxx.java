import com.alibaba.fastjson.JSON;
import com.jfshare.elasticsearch.drive.ESClient;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.task.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public class Test_xxx {


    private static Logger logger = LoggerFactory.getLogger(Test_xxx.class);

    public static void main(String[] str) {
       OrderQueryConditions conditions = new OrderQueryConditions();
        conditions.setStartTime("2016-06-01 00:00:00");
        conditions.setEndTime("2016-06-26 00:00:00");
//        conditions.setOrderState(5);
        conditions.setOrderId("96650041");
//        conditions.addToOrderIds("59090131");
//        conditions.setSellerId(13);
//        conditions.setCurPage(6);
        conditions.setCount(100);

        ESClient esClient = new ESClient("jfshare-app", "101.201.39.61:9300,101.201.39.62:9300");
        try {
            for(int i=0; i < 1; i++) {
                long start = System.nanoTime();
                SearchHits hits = search(conditions, esClient);
                int hitsTotal = (int)hits.getTotalHits();
                logger.info("ES==| search {}$$查询结果", hits.getTotalHits());
                if(hitsTotal > 0) {
                    for(SearchHit searchHit : hits.getHits()) {
                        String orderJson = JSON.parseObject(searchHit.getSourceAsString()).getString("orderJson");
                        Order order = JSON.parseObject(orderJson, Order.class);
                        logger.info("==> orderId={}, orderState={}, createTime={}", order.getOrderId(), order.getOrderState(), searchHit.getSource().get("orderCreateTime"));
                    }
                }
                logger.info("第{}次，耗时：{}", i, (System.nanoTime()-start)/1000000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            esClient.close();
        }

        System.err.println(new Date(1464704488000l));
    }


    public static SearchHits search(OrderQueryConditions conditions, ESClient esClient) {
        if(conditions.getCurPage() <= 0) {
            conditions.setCurPage(1);
        }
        String startTime = conditions.getStartTime();
        if(StringUtils.isBlank(startTime)
                || DateTimeUtil.strToDateTime(startTime).isBefore(DateTimeUtil.strToDateTime("2016-05-01 00:00:00"))) {
            startTime = "2016-05-01 00:00:00";
        }
        String endTime = StringUtils.isBlank(conditions.getEndTime()) ? "2016-06-21 00:00:00" : conditions.getEndTime();
        logger.info("esSearch----params:startTime={}, endTime={}, orderId={}, orderIds={}", startTime, endTime, conditions.getOrderId(), conditions.getOrderIds());
        int orderState = conditions.getOrderState();
        String[] monthArr = getBetweenMonth(startTime, endTime);
        String[] indexArr = new String[monthArr.length];
        for(int i = 0; i<  monthArr.length; i++) {
            indexArr[i] = "jfshare_order_" + monthArr[i];
        }

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if(StringUtils.isNotBlank(conditions.getOrderId())) {
            queryBuilder.must(QueryBuilders.matchQuery("orderId", conditions.getOrderId()));
        }

        if(orderState > 0) {
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

        SearchRequestBuilder searchRequestBuilder = esClient.getTransportClient().prepareSearch(indexArr)
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
     * 返回2个日期区间内的全部月份, 格式yyyy-MM
     * @param ds1
     * @param ds2
     * @return
     */
    public static String[] getBetweenMonth(String ds1, String ds2) {
        List<String> months = new ArrayList<String>();
        DateTime dt1 = DateTimeUtil.strToDateTime(ds1);
        DateTime start = new DateTime().withDate(dt1.getYear(), dt1.getMonthOfYear(), 1);
        DateTime end = DateTimeUtil.strToDateTime(ds2);
        do{
            System.err.println(start);
            months.add(DateTimeUtil.dateToStrOfDefaulfFormat(start.toDate()).substring(0,7));
            start = start.plusMonths(1).plusMillis(-1);
        } while (start.isBefore(end));
        String[] monthArr = new String[months.size()];
        months.toArray(monthArr);
        logger.info(months.toString());
        return monthArr;
    }
}
