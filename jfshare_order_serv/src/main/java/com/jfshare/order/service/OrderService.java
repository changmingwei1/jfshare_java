package com.jfshare.order.service;

import com.jfshare.finagle.thrift.express.ExpressInfo;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.finagle.thrift.pay.PayRet;
import com.jfshare.order.dao.IOrderDao;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderRecordExample;
import com.jfshare.order.server.depend.StockClient;
import com.jfshare.order.util.ConstantUtil;
import com.jfshare.order.util.OrderUtil;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * *************************************************************************
 * @文件名称: OrderService.java
 *
 * @包路径  : com.jfshare.order.server
 *
 *
 * @类描述:   订单服务处理
 *
 * @创建人:   sushi
 *
 * @修改记录:
-----------------------------------------------------------------------------------------------
时间						|		修改人		|		修改的方法		|		修改描述
-----------------------------------------------------------------------------------------------
|					|					|
-----------------------------------------------------------------------------------------------

 **************************************************************************
 */
@Service(value = "orderService")
public class OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private StockClient stockClient;

    public OrderModel sellerQueryDetail(int sellerId, String orderId) {
        return orderDao.getOrderBySeller(orderId, sellerId);
    }

    public OrderModel buyerQueryDetail(int userId, String orderId) {
        return orderDao.getOrderByUser(orderId, userId);
    }

    public List<OrderModel> sellerQueryList(OrderQueryConditions conditions) {
        return orderDao.getOrderListBySeller(conditions.getSellerId(), conditions);
    }

    public List<OrderModel> buyerQueryList(OrderQueryConditions conditions) {
        return orderDao.getOrderListByUser(conditions.getUserId(), conditions);
    }

    public int sellerQueryOrderStat(OrderQueryConditions conditions) {
        logger.info("卖家：" + conditions.getSellerId() + "：：：：：：：：：：：：查询订单，统计各种订单状态数量");
        return orderDao.getOrderStatBySeller(conditions.getSellerId(), conditions);
    }

    public int buyerQueryOrderStat(OrderQueryConditions conditions) {
        return orderDao.getOrderStatByUser(conditions.getUserId(), conditions);
    }

    @Transactional
    public int insertOrderList(List<Order> orderList) {
        int ret = -1;
        for (Order item : orderList) {
            OrderModel orderModel = OrderUtil.rConvertOrderModel(item);
            ret = orderDao.insertOrder(orderModel, BizUtil.USER_TYPE.BUYER.getEnumVal());
            if (ret >= 0 &&  ret != 2) {
                ret = orderDao.insertOrder(orderModel, BizUtil.USER_TYPE.SELLER.getEnumVal());
            }
        }

        return ret;
    }

    public OrderModel buyerQueryProfile(int userId, String orderId) {
        return orderDao.getOrderProfileByUser(userId, orderId);
    }

    public OrderModel sellerQueryProfile(int sellerId, String orderId) {
        return orderDao.getOrderProfileBySeller(sellerId, orderId);
    }

    @Transactional
    public int confirmReceipt(OrderModel orderModel) {
        OrderModel order = new OrderModel();
        order.setUserId(orderModel.getUserId());
        order.setSellerId(orderModel.getSellerId());
        order.setOrderState(ConstantUtil.ORDER_STATE.FINISH_COMMENT.getEnumVal());
        DateTime successTime = new DateTime();
        order.setSuccessTime(successTime);

        orderModel.setOrderState(ConstantUtil.ORDER_STATE.FINISH_COMMENT.getEnumVal());
        orderModel.setSuccessTime(successTime);

        TbOrderRecordExample example = new TbOrderRecordExample();
        TbOrderRecordExample.Criteria criteria = example.createCriteria();
//        criteria.andOrderStateEqualTo(ConstantUtil.ORDER_STATE.FINISH_DELIVER.getEnumVal());
        criteria.andOrderIdEqualTo(orderModel.getOrderId());
        criteria.andSellerIdEqualTo(orderModel.getSellerId());
        criteria.andUserIdEqualTo(orderModel.getUserId());
        int ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.BUYER.getEnumVal(),example);
        if (ret <= 0) {
            throw new RuntimeException("confirmReceipt，更新买家表失败, 更新返回："+ ret);
        }

        ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.SELLER.getEnumVal(),example);

        if (ret <= 0) {
            throw new RuntimeException("confirmReceipt，更新卖家表失败, 更新返回："+ ret);
        }

        return ret;
    }

    @Transactional
    public int cancelOrder(OrderModel orderModel, int userType, int reason) {
        OrderModel order = new OrderModel();
        order.setUserId(orderModel.getUserId());
        order.setSellerId(orderModel.getSellerId());
        order.setOrderState(BizUtil.USER_TYPE.BUYER.getEnumVal() == userType ? ConstantUtil.ORDER_STATE.TRADE_CANCEL.getEnumVal() : ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal());
        DateTime cancelTime = new DateTime();
        order.setCancelTime(cancelTime);
        order.setOrderStateType(reason);

        orderModel.setOrderState(BizUtil.USER_TYPE.BUYER.getEnumVal() == userType ? ConstantUtil.ORDER_STATE.TRADE_CANCEL.getEnumVal() : ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal());
        orderModel.setCancelTime(cancelTime);
        orderModel.setOrderStateType(reason);

        TbOrderRecordExample example = new TbOrderRecordExample();
        TbOrderRecordExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderModel.getOrderId());
        criteria.andSellerIdEqualTo(orderModel.getSellerId());
        criteria.andUserIdEqualTo(orderModel.getUserId());
        int ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.BUYER.getEnumVal(),example);
        if (ret <= 0) {
            throw new RuntimeException("cancelOrder，更新买家表失败, 更新返回："+ ret);
        }

        ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.SELLER.getEnumVal(),example);

        if (ret <= 0) {
            throw new RuntimeException("cancelOrder，更新卖家表失败, 更新返回："+ ret);
        }

        stockClient.releaseStock(orderModel.getOrderId(), orderModel.getTbOrderInfoList());

        return ret;
    }

    public List<OrderModel> buyerQueryList(int userId, List<String> orderIdList) {
        return orderDao.getOrderListByUser(userId, orderIdList);
    }

    @Transactional
    public int updateOrderPaying(OrderModel orderModel) {
        OrderModel order = new OrderModel();
        order.setUserId(orderModel.getUserId());
        order.setSellerId(orderModel.getSellerId());
        order.setOrderState(ConstantUtil.ORDER_STATE.PAYING.getEnumVal());

        TbOrderRecordExample example = new TbOrderRecordExample();
        TbOrderRecordExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderModel.getOrderId());
        criteria.andSellerIdEqualTo(orderModel.getSellerId());
        criteria.andUserIdEqualTo(orderModel.getUserId());
        int ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.BUYER.getEnumVal(),example);
        if (ret <= 0) {
            throw new RuntimeException("updateOrderPaying，更新买家表失败, 更新返回："+ ret);
        }

        ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.SELLER.getEnumVal(),example);

        if (ret <= 0) {
            throw new RuntimeException("updateOrderPaying，更新卖家表失败, 更新返回："+ ret);
        }

        return ret;
    }

    @Transactional
    public int updateOrderPaying(List<OrderModel> orderModels, String tradePayId) {
        for (OrderModel orderModel : orderModels) {
            OrderModel order = new OrderModel();
            order.setUserId(orderModel.getUserId());
            order.setSellerId(orderModel.getSellerId());
            order.setPayState(ConstantUtil.PAY_STATE.ING.getEnumVal());
            order.setPayTime(orderModel.getPayTime());
            order.setPayChannel(orderModel.getPayChannel());
            order.setTradePayId(tradePayId);
            order.setThirdScore(orderModel.getThirdScore());

            TbOrderRecordExample example = new TbOrderRecordExample();
            TbOrderRecordExample.Criteria criteria = example.createCriteria();
            criteria.andOrderIdEqualTo(orderModel.getOrderId());
            criteria.andSellerIdEqualTo(orderModel.getSellerId());
            criteria.andUserIdEqualTo(orderModel.getUserId());
            int ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.BUYER.getEnumVal(),example);
            if (ret <= 0) {
                throw new RuntimeException("updateOrderPaying，更新买家表失败, 更新返回："+ ret);
            }

            ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.SELLER.getEnumVal(),example);

            if (ret <= 0) {
                throw new RuntimeException("updateOrderPaying，更新卖家表失败, 更新返回："+ ret);
            }
        }

        return orderModels.size();
    }

    public int payRet(OrderModel orderModel, PayRet payRet) {
        OrderModel order = new OrderModel();
        order.setUserId(orderModel.getUserId());
        order.setSellerId(orderModel.getSellerId());
        order.setPayAccount(payRet.getPayAccount());
        order.setPayId(payRet.getPayId());
        order.setPayTime(payRet.getProcessTime() == null ? new DateTime() : DateUtils.strToDateTime(payRet.getProcessTime()));
        order.setTradePayId(payRet.getOrderNo());
        order.setPayChannel(payRet.getPayChannel());

        //天翼支付，一个订单， 更新使用积分
        if (payRet.getPayChannel() == BizUtil.PAY_CHANNEL.TIAN_YI.getEnumVal()) {
            order.setThirdScore(payRet.getThirdScore());
        }

        if (payRet.getRetCode() == 2) {
            order.setPayState(1);
            order.setOrderState(ConstantUtil.ORDER_STATE.WAIT_DELIVER.getEnumVal());
        } else {
            order.setPayState(-1);
        }

        //重复支付验证
        if (orderModel.getPayState() == 1) {
            logger.warn("$$$$ 支付通知----重复支付, orderModel:" + orderModel + ", payRet:" + payRet);
            return 1;
        }

        TbOrderRecordExample example = new TbOrderRecordExample();
        TbOrderRecordExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderModel.getOrderId());
        criteria.andSellerIdEqualTo(orderModel.getSellerId());
        criteria.andUserIdEqualTo(orderModel.getUserId());
        int ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.BUYER.getEnumVal(),example);
        if (ret <= 0) {
            throw new RuntimeException("updatePayRet，更新买家表失败, 更新返回："+ ret);
        }

        ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.SELLER.getEnumVal(),example);

        if (ret <= 0) {
            throw new RuntimeException("updatePayRet，更新卖家表失败, 更新返回："+ ret);
        }

        //释放库存锁定量
        stockClient.releaseLockCount(order.getOrderId(), orderModel.getTbOrderInfoList());
        //TODO 超卖

        return ret;
    }

    public List<OrderModel> buyerQueryStateList(OrderQueryConditions conditions) {
        List<OrderModel> orderModels = this.orderDao.getOrderStateListByUser(conditions.getUserId());
        return orderModels;
    }

    public List<OrderModel> sellerQueryStateList(OrderQueryConditions conditions) {
        List<OrderModel> orderModels = this.orderDao.getOrderStateListBySeller(conditions.getSellerId());
        return orderModels;
    }

    public List<OrderModel> queryBatch(OrderQueryConditions conditions) {
        return orderDao.queryBatchBySeller(conditions.getSellerId(), conditions);
    }

    public List<OrderModel> getSellerOrderBatch(int sellerId, List<String> resultOrderIdList) {
        return orderDao.getSellerOrderBatch(sellerId, resultOrderIdList);
    }

    public int batchDeliverOp(int sellerId, List<Order> deliverOrderList, List<ExpressInfo> expressList, int deliverType) {
        return orderDao.batchDeliverOp(sellerId, deliverOrderList);
        //TODO 通知物流抓取
    }
}
 