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
        for(int i= 0; i < optCount; i++) {
            String optJson = listRedisManager.rpop(Constant.ORDER_OPT_QUEUE_KEY);
            logger.info("订单操作日志OrderOptHandleTask----处理订单信息， json={}", optJson);
            JSONObject jsonObject = JSON.parseObject(optJson);
            int userId = jsonObject.getIntValue("userId");
            int sellerId = jsonObject.getIntValue("sellerId");
            String orderId = jsonObject.getString("orderId");
            String optTypeStr = jsonObject.getString("OptType");
            Order order = null;
            if(userId > 0) {
                order = orderClient.queryOrderDetail(BizUtil.USER_TYPE.BUYER, userId, orderId);
            } else if(sellerId > 0) {
                order = orderClient.queryOrderDetail(BizUtil.USER_TYPE.SELLER, userId, orderId);
            }

            if(order == null) {
                logger.error("订单操作日志OrderOptHandleTask----查询订单失败, 未成功同步订单数据，需要人工处理,，orderId={}", orderId);
                continue;
            }

            Constant.OptType optType = null;
            try {
                optType = Constant.OptType.valueOf(optTypeStr);
            } catch (IllegalArgumentException e) {
                //Do nothing
            }

            switch (optType) {
                case order_pay:{
                    orderService.afterOrderPay(order);
                    break;
                }
                default:{
                    break;
                }
            }


            EsOrder esOrder = new EsOrder(order);
            esOrderHandler.addOrUpdate(esOrder);
        }
		logger.info("订单操作日志OrderOptHandleTask----运行结束, 共处理记录数：{}, 耗时：{}ms", optCount, (System.nanoTime() - start)/1000000);
    }
}
