import com.alibaba.fastjson.JSON;
import com.jfshare.elasticsearch.drive.ESClient;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by lenovo on 2015/9/28.
 */
public class Test_ES {


    private static Logger logger = LoggerFactory.getLogger(Test_ES.class);

    private static ESClient esClient = new ESClient("jfshare-app", "120.24.153.155:9300");
    public static void main(String[] str) {
        try {
            SearchHits searchHits = searchScoreRecord("12222_1", null);
            logger.info("total={}", searchHits.getTotalHits());
            for(SearchHit hit : searchHits.getHits()) {
                logger.info(hit.getSourceAsString());
            }
//            logger.info("total={}",  JSON.toJSONString(searchHits.getHits()));


//            addScoreRecord(new EsScore("1-2222-1", "", 100, 10, 1));
//            addScoreRecord(new EsScore("1-2222-1", "orderId1", 100, -1, 2));
//            addScoreRecord(new EsScore("1-2222-1", "orderId2", 100, -2, 2));
//            addScoreRecord(new EsScore("1-2222-1", "orderId2", 100, 2, 3));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            esClient.close();
        }

        System.err.println(new Date(1464704488000l));
    }

    public static SearchHits searchScoreRecord(String orderBatch, Integer scoreType) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("orderBatch", orderBatch));
        if(scoreType != null) {
            queryBuilder.must(QueryBuilders.matchQuery("type", scoreType.intValue()));
        }

        SearchRequestBuilder searchRequestBuilder = esClient.getTransportClient().prepareSearch(ES_SCORE_INDEX)
                .setTypes(ES_SCORE_TYPE)
                .setQuery(queryBuilder)
                .setFrom(0).setSize(200)
                .setExplain(true);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        return searchResponse.getHits();
    }

    /**
     * 插入积分操作记录
     * @param esScore
     */
    public static void addScoreRecord(EsScore esScore) {
        logger.info("ES==> addScoreRecord {}", esScore);
        try {
//            esClient.add(ES_SCORE_INDEX, ES_SCORE_TYPE, esScore.toJSONString());
            TransportClient transportClient = esClient.getTransportClient();
            IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex(ES_SCORE_INDEX, ES_SCORE_TYPE);
            indexRequestBuilder.setSource(JSON.toJSONString(esScore)).get();
        } catch (Exception e) {
            logger.error("ES==ERROR addScoreRecord {}", esScore.toJSONString());
            logger.error("ES==>| addScoreRecord 发生异常", e);
        }
    }

    private final static String ES_SCORE_INDEX = "jfshare_order_score";
    private final static String ES_SCORE_TYPE = "esOrderScore";
}
