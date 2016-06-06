package com.jfshare.order.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jfshare.elasticsearch.drive.ESClient;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.order.common.Commons;
import com.jfshare.order.dao.IOrderEs;
import com.jfshare.order.dao.impl.jedis.BasicRedis;
import com.jfshare.order.model.EsOrder;
import com.jfshare.order.util.FileOpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lenovo on 2016/6/3.
 */
@Service
public class ExportService {
    private Logger logger= LoggerFactory.getLogger(ExportService.class);

    @Autowired
    private IOrderEs orderEs;

    @Autowired
    private FileOpUtil fileOpUtil;

    @Autowired
    private BasicRedis basicRedis;

    abstract class Executor<T> {

        public Executor() {
        }

        /**
         * 回调
         *
         * @return 执行结果
         */
        abstract T execute();

        /**
         * 调用并返回执行结果 它保证在执行execute()
         *
         * @return 执行结果
         */
        public T getResult() {
            T result = null;
            try {
                result = execute();
            } catch (Throwable e) {
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
            Object execute() {
                try {
                    basicRedis.putKV(queryKey, "query", Commons.exportKeyExpired);
                    SearchHits hits = orderEs.search(conditions);

                    if(hits.getTotalHits() > 0) {
                        logger.info("----未查询到订单数据!");

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
                            basicRedis.putKV(queryKey, DigestUtils.md5Hex(fileKey), Commons.exportKeyExpired);
                            logger.info("asyncExport异步导出订单成功， queryKey="+queryKey+", fileKey="+fileKey+", count=" + orderDetails.size());
                        }
                    }
                } catch (Exception e) {
                    basicRedis.putKV(queryKey, "exception", Commons.exportKeyExpired);
                    logger.error("asyncExport异步导出订单失败，发生异常", e);
                }

                return null;
            }
        };
    }
}
