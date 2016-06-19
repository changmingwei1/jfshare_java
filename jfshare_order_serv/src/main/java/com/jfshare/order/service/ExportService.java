package com.jfshare.order.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.order.common.Commons;
import com.jfshare.order.dao.IOrderEs;
import com.jfshare.order.dao.impl.jedis.BasicRedis;
import com.jfshare.order.model.EsOrder;
import com.jfshare.order.util.FileOpUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Lenovo on 2016/6/3.
 */
@Service
public class ExportService {
    private Logger logger= LoggerFactory.getLogger(ExportService.class);

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Autowired
    private IOrderEs orderEs;

    @Autowired
    private FileOpUtil fileOpUtil;

    @Autowired
    private BasicRedis basicRedis;

    abstract class Executor<T> {

        private FutureTask<T> task = new FutureTask<T>(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return function();
            }
        });

        public Executor() {
        }

        abstract T function();

        public void execute() {
            logger.info("execute is running");
            executor.execute(task);
        }

        public T executeAndRetuan() {
            T result = null;
            try {
                executor.execute(task);
                result = task.get(3, TimeUnit.SECONDS);

                logger.info("executeAndRetuan is running {}", result);
            } catch (Exception e) {
                logger.error("future execute exception", e);
            } finally {
                //释放资源
            }

            return result;
        }
    }

    public void asyncExport(final OrderQueryConditions conditions, final String queryKey){
        new Executor<Object>() {
            @Override
            Object function() {
                try {
                    basicRedis.putKV(queryKey, "query", Commons.exportKeyExpired);
                    int total = getExportTotal(conditions);

                    logger.info("----查询到订单数:{} 条", total);
                    if(total > 0) {
                        conditions.setCount(total + 100);
                        SearchHits hits = orderEs.search(conditions);
                        List<Order> orderDetails = Lists.newArrayList();
                        for (SearchHit searchHit : hits.getHits()) {
                            EsOrder esOrder = JSON.parseObject(searchHit.getSourceAsString(), EsOrder.class);
                            orderDetails.add(JSON.parseObject(esOrder.getOrderJson(), Order.class));
                        }

                        basicRedis.putKV(queryKey, "export", Commons.exportKeyExpired);

                        byte[] xlsBytes = fileOpUtil.gerExportExcel(orderDetails);
                        if (xlsBytes != null) {
                            String fileName = fileOpUtil.getFileName("full", null);
                            String fileKey = fileOpUtil.toFastDFS(xlsBytes, fileName);
                            basicRedis.putKV(queryKey, fileKey, Commons.exportKeyExpired);
                            logger.info("asyncExport异步导出订单成功， queryKey="+queryKey+", fileKey="+fileKey+", count=" + orderDetails.size());
                        }
                    } else{
                        basicRedis.putKV(queryKey, "nodata", Commons.exportKeyExpired);
                    }
                } catch (Exception e) {
                    basicRedis.putKV(queryKey, "exception", Commons.exportKeyExpired);
                    logger.error("asyncExport异步导出订单失败，发生异常", e);
                }

                return null;
            }
        }.execute();
    }

    private int getExportTotal(OrderQueryConditions conditions) {
        conditions.setCount(1);
        conditions.setCurPage(1);
        return (int)orderEs.search(conditions).getTotalHits();
    }

    public String getExportResult(String queryKey) {
        Map<String, String> map = new HashMap<String, String>();
        String ret = basicRedis.getKV(queryKey);
        if(StringUtils.isBlank(ret)) {
            map.put("code", "-1");
            map.put("desc", "expired");
        } else {
            if(ret.endsWith("xls")) {
                map.put("code", "1");
            } else if(ret.equalsIgnoreCase("exception") || ret.equalsIgnoreCase("nodata")){
                map.put("code", "-1");
            } else {
                map.put("code", "0");
            }
            map.put("desc", ret);
        }

        return JSON.toJSONString(map);
    }
}
