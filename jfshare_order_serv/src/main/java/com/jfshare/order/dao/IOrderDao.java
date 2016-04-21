/** 
 * Project Name:order-serv
 * File Name:StockDao.java 
 * Package Name:com.jfshare.order.dao.impl.redis
 * Date:2014年12月23日下午12:54:55 
 * 
*/  
  
package com.jfshare.order.dao;

import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderRecordExample;

import java.util.List;

/** 
 * ClassName:StockDao <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2014年12月23日 下午12:54:55 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public interface IOrderDao {

    public OrderModel getOrderBySeller(String orderId, int sellerId);

    public OrderModel getOrderByUser(String orderId, int userId);

    public int getOrderStatBySeller(int sellerId, OrderQueryConditions conditions);

    public List<OrderModel> getOrderListBySeller(int sellerId, OrderQueryConditions conditions);

    int insertOrder(OrderModel orderModel, int userType);

    List<OrderModel> getOrderListByUser(int userId, OrderQueryConditions conditions);

    public int updateOrderWithCriteria(OrderModel orderModel, int userType, TbOrderRecordExample example);

    int getOrderStatByUser(int userId, OrderQueryConditions conditions);

    OrderModel getOrderProfileByUser(int userId, String orderId);

    OrderModel getOrderProfileBySeller(int sellerId, String orderId);

    List<OrderModel> getOrderListByUser(int userId, List<String> orderIdList);

    List<OrderModel> getOrderStateListByUser(int userId);

    List<OrderModel> getOrderStateListBySeller(int sellerId);
}
  