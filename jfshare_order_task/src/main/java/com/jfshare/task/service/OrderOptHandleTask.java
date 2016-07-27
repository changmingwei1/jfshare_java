package com.jfshare.task.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.task.dao.redis.ListRedisManager;
import com.jfshare.task.elasticsearch.IEsOrderHandler;
import com.jfshare.task.elasticsearch.models.EsOrder;
import com.jfshare.task.server.depend.OrderClient;
import com.jfshare.task.service.impl.OrderService;
import com.jfshare.task.util.Constant;
import com.jfshare.utils.BizUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderOptHandleTask {
    private Logger logger = LoggerFactory.getLogger(OrderOptHandleTask.class);

	@Autowired
	private  ListRedisManager listRedisManager;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private IEsOrderHandler esOrderHandler;

    @Autowired
    private OrderService orderService;
   
    public  void start()
    {
        long start = System.nanoTime();
		logger.info("订单操作日志OrderOptHandleTask----运行开始");
		long optCount = listRedisManager.queryLength(Constant.ORDER_OPT_QUEUE_KEY);
        logger.info("订单操作日志----order_opt_queue队列长度:{}", optCount);
        for(int i= 0; i < optCount; i++) {
            String optJson = listRedisManager.rpop(Constant.ORDER_OPT_QUEUE_KEY);
            logger.info("订单操作日志OrderOptHandleTask----处理订单信息， json={}", optJson);
            JSONObject jsonObject = JSON.parseObject(optJson);
            int userId = jsonObject.getIntValue("userId");
            int sellerId = jsonObject.getIntValue("sellerId");
            String orderId = jsonObject.getString("orderId");
            String optTypeStr = jsonObject.getString("optType");
            Order order = null;
            if(userId > 0) {
                order = orderClient.queryOrderDetail(BizUtil.USER_TYPE.BUYER, userId, orderId);
            } else if(sellerId > 0) {
                order = orderClient.queryOrderDetail(BizUtil.USER_TYPE.SELLER, userId, orderId);
            }

            if(order == null || StringUtils.isBlank(order.getOrderId())) {
                logger.error("订单操作日志OrderOptHandleTask----查询订单失败, 稍后重试,，orderId={}", orderId);
                listRedisManager.lpush(Constant.ORDER_OPT_QUEUE_KEY, optJson);
                continue;
            }

            Constant.OptType optType = null;
            try {
                optType = Constant.OptType.valueOf(optTypeStr);
            } catch (IllegalArgumentException e) {
                //Do nothing
            }

            orderAfterDeal(optType, order);


            EsOrder esOrder = new EsOrder(order);
            esOrderHandler.addOrUpdate(esOrder);
        }
		logger.info("订单操作日志OrderOptHandleTask----运行结束, 共处理记录数：{}, 耗时：{}ms", optCount, (System.nanoTime() - start)/1000000);
    }

    private void orderAfterDeal(Constant.OptType optType, Order order) {
        switch (optType) {
            case order_deliver: {
                orderService.afterOrderDeliver(order);
                break;
            }
            case order_deliver_batch: {
                orderService.afterOrderDeliver(order);
                break;
            }
            case order_confirm:{
                orderService.afterOrderConfirm(order);
                break;
            }
            default:{
                break;
            }
        }
    }
}
