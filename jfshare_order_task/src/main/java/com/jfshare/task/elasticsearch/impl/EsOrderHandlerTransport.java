package com.jfshare.task.elasticsearch.impl;

import com.jfshare.elasticsearch.drive.ESClient;
import com.jfshare.task.elasticsearch.IEsOrderHandler;
import com.jfshare.task.elasticsearch.models.EsOrder;
import com.jfshare.task.util.Constant;
import com.jfshare.task.util.DateTimeUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lenovo on 2016/4/6.
 */
@Service
public class EsOrderHandlerTransport implements IEsOrderHandler {

    private Logger logger = LoggerFactory.getLogger(EsOrderHandlerTransport.class);

    @Autowired
    private ESClient esClient;

    @Override
    public void addOrUpdate(EsOrder esOrder) {
        try {
            logger.info("ES==> add {}$$参数信息", esOrder);
            String _index = getIndex(esOrder.getCreateTime());
            String _type = Constant.ES_ORDER_TYPE;
            String _id = esOrder.getOrderId().trim();
            String _content = esOrder.toJSONStr();
            this.esClient.addOrUpdateByCustomId(_index, _type, _id, _content);
            logger.info("ES==< add $$同步ESBulker异步插入订单数据至ES");
        } catch (Exception e) {
            logger.error("ES==< add $$exception ", e);
        }
    }

    /**
     *
     * @param crateTime
     * @return
     */
    private String getIndex(DateTime crateTime) throws Exception {
//        DateTime time = new DateTime(crateTime);
        return "jfshare_order_" + DateTimeUtil.dateToStrOfDefaulfFormat(crateTime.toDate()).substring(0,7);
    }
}
