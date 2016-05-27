package com.jfshare.elasticsearch.drive;

import com.jfshare.elasticsearch.drive.utils.ConcurrentHashSet;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESBulker {

    private static final Logger logger = LoggerFactory.getLogger(ESBulker.class);

    private static ConcurrentHashSet<BulkProcessor> bulkProcessorSet = new ConcurrentHashSet<>();

    private BulkProcessor bulkProcessor;

    private TransportClient transportClient;

    public ESBulker(TransportClient transportClient){
        this(transportClient, null, null, null);
    }

    /**
     * 初始化ESBulker
     * @param transportClient
     * @param bulkActions 文档数量刷新阈值, 默认200
     * @param flushInterval flush间隔,单位s
     * @param concurrentRequests 并发请求数,默认1
     */
    public ESBulker(TransportClient transportClient, Integer bulkActions, Integer flushInterval, Integer concurrentRequests) {

        this.transportClient = transportClient;

        BulkProcessor.Builder builder = BulkProcessor.builder(transportClient, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                logger.info("exeid: [{}], beforeBulk. action size : {} ", executionId, request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                logger.error("[afterBulk] exeid : " + executionId + " error. cause:" + failure.getMessage(), failure);
                bulkProcessor.add(request); //重试一次
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                if (response.hasFailures()){
                    logger.error("exeid: [{}], afterBulk. response : {} ", executionId, response.buildFailureMessage());
                }else{
                    logger.info("exeid: [{}], afterBulk. insert success", executionId);
                }
            }
        });


        //多少条document后触发提  default is 200
        if(bulkActions!=null){
            builder.setBulkActions(bulkActions);
        }else{
            builder.setBulkActions(200);
        }
        //多少秒之后触发bulk操作
        if(flushInterval!=null){
            builder.setFlushInterval(TimeValue.timeValueSeconds(flushInterval));
        }else {
            builder.setFlushInterval(TimeValue.timeValueSeconds(1));
        }
        //并发粒度 default is 1
        if(concurrentRequests!=null){
            builder.setConcurrentRequests(concurrentRequests);
        }

        bulkProcessor = builder.build();
        bulkProcessorSet.add(bulkProcessor);

    }


    /**
     * 批量索引数据, 并自己指定document的id
     * @param index
     * @param type
     * @param id
     * @param json
     * @throws Exception
     */
    public void addOrUpdateByCustomId(String index, String type, String id, String json) throws Exception {
        if (this.bulkProcessor != null) {
            bulkProcessor.add(new IndexRequest(index, type, id).source(json));
        } else {
            throw new Exception("bulkProcessor is null");
        }
    }


    /**
     * 批量索引数据,es自动生成document id
     * @param index
     * @param type
     * @param json
     * @throws Exception
     */
    public void add(String index, String type, String json) throws Exception {
        if (this.bulkProcessor != null) {
            IndexRequest request = transportClient.prepareIndex(index, type)
                    .setSource(json).request();
            bulkProcessor.add(request);
        } else {
            throw new Exception("bulkProcessor is null");
        }
    }


    /**
     * 批量删除数据, 并自己指定document的id
     * @param index
     * @param type
     * @param id
     * @throws Exception
     */
    public void delete(String index, String type, String id) throws Exception {
        if (this.bulkProcessor != null) {
            bulkProcessor.add(new DeleteRequest(index, type, id));
        } else {
            throw new Exception("bulkProcessor is null");
        }
    }


    public void close() {
        if (this.bulkProcessor != null) this.bulkProcessor.close();
    }


    /**
     * Flush pending delete or index requests.
     */
    public void flush(){
        if (this.bulkProcessor != null) {
            this.bulkProcessor.flush();
        }
    }

}


