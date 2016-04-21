package com.jfshare.task.service;

import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.task.dao.mysql.IOrderDao;
import com.jfshare.task.model.manual.OrderModel;
import com.jfshare.task.server.depend.OrderClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 订单自动确认收货
 */
@Service
public class OrderConfirmThread extends Thread {
    private Logger logger = LoggerFactory.getLogger(OrderConfirmThread.class);

    @Resource
    private IOrderDao orderDao;
	@Autowired
	private OrderClient orderClient;

    private int taskInterval = 60*60;  //轮休间隔
    private int orderConfirmLimit = 15; //单位 天
	private final int maxTableIndex = 63; //分表区间
	private final int pageLimit = 1000; //每次查询记录数

	@PostConstruct
	public	void Init()
	{
		try {
			
			String confirmLimit = PropertiesUtil.getProperty("jfx_order_serv","order_confirm_day");
			
			if(confirmLimit != null && !confirmLimit.isEmpty())
				orderConfirmLimit = Integer.parseInt(confirmLimit);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
    @Override
    public void run() {
    	
    	while (true) {
    		
    		int count  =0 ;
    		try {
    			count = orderConfirm();
    		} catch (Exception e) {
    			logger.error("自动确认订单任务异常:", e);
    		}
    		
    		logger.info("订单确认收货超时在运行， " +
    				count +
    				" 条记录在处理...........................");
    		
    		if(count <= 0 ) 
    		{
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
    
    public int orderConfirm()
    {
    	int count = 0;
    	for(int index = 0; index <=maxTableIndex; index++)
    	{
    		//--------------------------------------------------------
    		List<OrderModel> waitConformList = null;
    		try {
    			 waitConformList = orderDao.selectConfirmOrder(index, pageLimit);
    		} catch (Exception e) {
    			logger.error("获取确认订单失败：" + index, e);
    		}
    		
    		if(waitConformList == null || waitConformList.size() <= 0)
    		{
				return count;
    		}
    		
//    		count += waitConformList.size();
    		for(OrderModel order : waitConformList)
    		{
    			try {
					//未超过确认收货时间跳过
					DateTime endTime = order.getDeliverTime().plusDays(orderConfirmLimit);
					if (new DateTime().getMillis() < endTime.getMillis()) {
						continue;
					}
					count ++;



					int ret = orderClient.autoConfirmReceipt(order.getUserId(), order.getOrderId());
					if(ret > 0)
					{
						logger.info("更新订单：自动确认收货， userId: " + order.getUserId() +
								"orderId:" + order.getOrderId() + "发货时间：" + order.getDeliverTime());
					} else {
						count --; //不可确认收货订单不再轮询
						logger.info("可能有售后/维权正在处理：自动确认收货自动延期， userId: " + order.getUserId() +
								"orderId:" + order.getOrderId() + "发货时间：" + order.getDeliverTime());
					}
					
	    		} catch (Exception e) {
					logger.error("更新订单：自动确认收货异常， userId: " + order.getUserId() +
							"orderId:" + order.getOrderId() + "发货时间：" + order.getDeliverTime(), e);
				}
    		}
    	}
    	
    	return count;
    }
}
