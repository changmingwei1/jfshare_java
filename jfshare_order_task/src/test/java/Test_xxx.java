import com.alibaba.fastjson.JSON;
import com.jfshare.elasticsearch.drive.ESClient;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderProfilePage;
import com.jfshare.finagle.thrift.order.OrderProfileResult;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.task.elasticsearch.models.EsOrder;
import com.jfshare.task.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by lenovo on 2015/9/28.
 */
public class Test_xxx {


    private static Logger logger = LoggerFactory.getLogger(Test_xxx.class);

    public static void main(String[] str) {
       OrderQueryConditions conditions = new OrderQueryConditions();
        conditions.setStartTime("2016-06-01 00:00:00");
        conditions.setEndTime("2016-06-30 00:00:00");
//        conditions.setOrderState(6);
        conditions.setOrderId("64390093");
//        conditions.addToOrderIds("49090129");
//        conditions.setSellerId(13);
//        conditions.setCurPage(6);
//        conditions.setCount(100);

        ESClient esClient = new ESClient("jfshare-app", "101.201.39.61:9300,101.201.39.62:9300");
        try {
            for(int i=0; i < 10; i++) {
                long start = System.nanoTime();
                OrderProfileResult orderProfileResult = orderProfileQueryFull(conditions, esClient);
                logger.info("第{}次，耗时：{}", i, (System.nanoTime()-start)/1000000);
            }

        } catch (TException e) {
            e.printStackTrace();
        } finally {
            esClient.close();
        }
    }


    public static OrderProfileResult orderProfileQueryFull(OrderQueryConditions conditions, ESClient esClient) throws TException {
        OrderProfileResult orderProfileResult = new OrderProfileResult();
        orderProfileResult.setResult(new Result(0));
        orderProfileResult.setOrderProfilePage(new OrderProfilePage());
        if(StringUtils.isBlank(conditions.getStartTime()) || StringUtils.isBlank(conditions.getEndTime())) {
            logger.error("时间错误");
        }

        if(conditions.getCurPage() <1) {
            conditions.setCurPage(1);
        }
        if(conditions.getCount() <=0) {
            conditions.setCount(10);
        }

        String startTime = conditions.getStartTime();
        String endTime = conditions.getEndTime();
        int orderState = conditions.getOrderState();
        String[] monthArr = getBetweenMonth(startTime, endTime);
        String[] indexArr = new String[0];
        for(String month : monthArr) {
            ArrayUtils.add(indexArr, "jfshare_order_" + month);
        }

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();


        if(orderState > 0) {
            if(orderState < 10) {
                queryBuilder.must(QueryBuilders.rangeQuery("orderState")
                        .from(orderState * 10)
                        .to((orderState+1) * 10 - 1));
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

        SearchResponse searchResponse = esClient.getTransportClient().prepareSearch(indexArr)
                .setTypes("esOrder")
                .setQuery(queryBuilder)
                .setFrom((conditions.getCurPage() - 1) * conditions.getCount())
                .setSize(conditions.getCount())
                .setExplain(true)
                .addSort(SortBuilders.fieldSort("orderCreateTime").order(SortOrder.DESC))
                .execute()
                .actionGet();

        int hitsTotal = (int)searchResponse.getHits().getTotalHits();
        logger.info("ES==| search {}$$查询结果", searchResponse.getHits().getTotalHits());
        if(hitsTotal > 0) {
            orderProfileResult.getOrderProfilePage().setCount(conditions.getCount());
            orderProfileResult.getOrderProfilePage().setCurPage(conditions.getCurPage());
            orderProfileResult.getOrderProfilePage().setTotal(hitsTotal);
            for(SearchHit searchHit : searchResponse.getHits().getHits()) {
                String orderJson = JSON.parseObject(searchHit.getSourceAsString()).getString("orderJson");
                Order order = JSON.parseObject(orderJson, Order.class);
                logger.info("==> orderId={}, orderState={}", order.getOrderId(), order.getOrderState());
                orderProfileResult.getOrderProfilePage().addToOrderProfileList(order);
            }
        }

        return orderProfileResult;
    }

    /**
     * 返回2个日期区间内的全部月份, 格式yyyy-MM
     * @param ds1
     * @param ds2
     * @return
     */
    public static String[] getBetweenMonth(String ds1, String ds2) {
        List<String> months = new ArrayList<String>();
        DateTime start = DateTimeUtil.strToDateTime(ds1);
        DateTime end = DateTimeUtil.strToDateTime(ds2);
        do{
            months.add(DateTimeUtil.dateToStrOfDefaulfFormat(start.toDate()).substring(0,7));
            start = start.plusMonths(1);
        } while (start.isBefore(end));
        String[] monthArr = new String[months.size()];
        months.toArray(monthArr);
        logger.info(months.toString());
        return monthArr;
    }
}
