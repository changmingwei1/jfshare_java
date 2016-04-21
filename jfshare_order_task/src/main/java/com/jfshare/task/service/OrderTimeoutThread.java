package com.jfshare.task.service;

import com.jfshare.task.dao.mysql.IOrderDao;
import com.jfshare.task.model.manual.OrderModel;
import com.jfshare.task.server.depend.OrderClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单自动关闭
 */

@Service
public class OrderTimeoutThread extends Thread {
    private Logger logger = LoggerFactory.getLogger(OrderTimeoutThread.class);
	@Resource
	private IOrderDao orderDao;
	@Autowired
	private OrderClient orderClient;

    private int taskInterval = 60*60;  //轮休间隔
    private int orderTimeoutLimit = 15;
	private final int maxTableIndex = 63; //分表区间
	private final int pageLimit = 1000; //每次查询记录数

    @Override
    public void run() {
    	while (true) {
    		int count  = 0;
    		try {
    				count = orderStateCheck();
    		} catch(Exception e) {
    			logger.error("订单超时未付款任务异常!", e);
    		}
    		
    		logger.info("订单超时未付款超时在运行， " +count +
    				" 条记录在处理...........................");
    		
    		if(count < pageLimit ) {
	    		synchronized(this){
	    			try {
						this.wait(1000*taskInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	    		}
    		}
    	}
    }

	private int orderStateCheck() {
		int count = 0;
		for(int index = 0; index <=maxTableIndex; index++)
		{
			List<OrderModel> waitCancelList = null;
			try {
				waitCancelList = orderDao.selectCancelOrder(index, pageLimit);
			} catch (Exception e) {
				logger.error("获取确认订单失败：" + index, e);
			}

			if(waitCancelList == null || waitCancelList.size() <= 0)
			{
				return count;
			}

//    		count += waitCancelList.size();
			for(OrderModel order : waitCancelList)
			{
				try {
					//未超过取消时间跳过
					DateTime endTime = order.getCancelTime();
					if (new DateTime().getMillis() < endTime.getMillis()) {
						continue;
					}
					count ++;



					int ret = orderClient.autoCancelOrder(order.getUserId(), order.getOrderId());
					if(ret > 0)
					{
						logger.info("更新订单：自动取消订单， userId: " + order.getUserId() +
								"orderId:" + order.getOrderId() + "取消时间：" + order.getCancelTime());
					} else {
						count --; //不可确认收货订单不再轮询
						logger.info("可能有售后/维权正在处理：自动取消订单自动延期， userId: " + order.getUserId() +
								"orderId:" + order.getOrderId() + "取消时间：" + order.getCancelTime());
					}

				} catch (Exception e) {
					logger.error("更新订单：自动取消订单异常， userId: " + order.getUserId() +
							"orderId:" + order.getOrderId() + "取消时间：" + order.getCancelTime(), e);
				}
			}
		}

		return count;
	}

}
