package com.jfshare.order.service;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.express.ExpressInfo;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.finagle.thrift.pay.PayRet;
import com.jfshare.order.dao.IOrderDao;
import com.jfshare.order.dao.IOrderJedis;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.OrderOpt;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.order.model.TbOrderRecordExample;
import com.jfshare.order.server.depend.ExpressClient;
import com.jfshare.order.server.depend.SellerClient;
import com.jfshare.order.server.depend.StockClient;
import com.jfshare.order.util.ConstantUtil;
import com.jfshare.order.util.OrderUtil;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.DateUtils;
import com.twitter.concurrent.Spool;
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

    @Autowired
    private ExpressClient expressClient;

    @Autowired
    private IOrderJedis orderJedis;

    @Autowired
    private SellerClient sellerClient;

    @Autowired
    private ScoreService scoreService;

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


        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        for(Order item : orderList) {
            orderOptPush.addToOrderOptList(item.getOrderId(), "order_create", item.getUserId(), item.getSellerId());
        }
        orderJedis.pushOrderNotification(orderOptPush);

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
        order.setOrderState(ConstantUtil.ORDER_STATE.WAIT_COMMENT.getEnumVal());
        DateTime successTime = new DateTime();
        order.setSuccessTime(successTime);

        orderModel.setOrderState(ConstantUtil.ORDER_STATE.WAIT_COMMENT.getEnumVal());
        orderModel.setSuccessTime(successTime);

        TbOrderRecordExample example = new TbOrderRecordExample();
        TbOrderRecordExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStateEqualTo(ConstantUtil.ORDER_STATE.FINISH_DELIVER.getEnumVal());
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


        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        orderOptPush.addToOrderOptList(orderModel.getOrderId(), "order_confirm", orderModel.getUserId(), orderModel.getSellerId());
        orderJedis.pushOrderNotification(orderOptPush);

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

        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        orderOptPush.addToOrderOptList(orderModel.getOrderId(), "order_close", orderModel.getUserId(), orderModel.getSellerId());
        orderJedis.pushOrderNotification(orderOptPush);

        //积分处理， 返回积分， 扣减增送积分
        scoreService.afterOrderClose(orderModel);
        return ret;
    }

    public List<OrderModel> buyerQueryListFull(int userId, List<String> orderIdList) {
        return orderDao.getOrderListByUserFull(userId, orderIdList);
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

        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        orderOptPush.addToOrderOptList(orderModel.getOrderId(), "order_paying", orderModel.getUserId(), orderModel.getSellerId());
        orderJedis.pushOrderNotification(orderOptPush);

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
            order.setExchangeCash(orderModel.getExchangeCash());
            order.setExchangeScore(orderModel.getExchangeScore());

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


        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        for(OrderModel item : orderModels) {
            orderOptPush.addToOrderOptList(item.getOrderId(), "order_paying", item.getUserId(), item.getSellerId());
        }
        orderJedis.pushOrderNotification(orderOptPush);

        return orderModels.size();
    }

    /**
     * 全积分支付
     * @param orderModels
     * @return
     */
    public int payOnlyScore(List<OrderModel> orderModels) {
        for(OrderModel orderModel : orderModels) {
            logger.info("payOnlyScore----全积分支付, orderId={}", orderModel.getOrderId());
            OrderModel order = new OrderModel();
            order.setUserId(orderModel.getUserId());
            order.setSellerId(orderModel.getSellerId());
            order.setPayChannel(0);

            order.setPayState(1);
            order.setOrderState(ConstantUtil.ORDER_STATE.WAIT_DELIVER.getEnumVal());
            //线下订单直接交易成功
            if (orderModel.getOrderType() == 1) {
                order.setOrderState(ConstantUtil.ORDER_STATE.WAIT_COMMENT.getEnumVal());
                addNumber4Seller(order);
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
            stockClient.releaseLockCount(orderModel.getOrderId(), orderModel.getTbOrderInfoList());
            //TODO 超卖

            //推送订单操作通知
            OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
            orderOptPush.addToOrderOptList(orderModel.getOrderId(), "order_pay", orderModel.getUserId(), orderModel.getSellerId());
            orderJedis.pushOrderNotification(orderOptPush);
        }


        return 1;
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
            order.setThirdPrice(payRet.getThirdPrice());
        }

        if (payRet.getRetCode() == 2) {
            order.setPayState(1);
            order.setOrderState(ConstantUtil.ORDER_STATE.WAIT_DELIVER.getEnumVal());
            //线下订单直接交易成功
            if (orderModel.getOrderType() == 1) {
                order.setOrderState(ConstantUtil.ORDER_STATE.WAIT_COMMENT.getEnumVal());
                addNumber4Seller(order);
            }
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
        stockClient.releaseLockCount(orderModel.getOrderId(), orderModel.getTbOrderInfoList());
        //TODO 超卖

        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        orderOptPush.addToOrderOptList(orderModel.getOrderId(), "order_pay", orderModel.getUserId(), orderModel.getSellerId());
        orderJedis.pushOrderNotification(orderOptPush);


        return ret;
    }

    private void addNumber4Seller(OrderModel orderModel) {
        //将买家设置为卖家的会员
        boolean b = sellerClient.insertUserSellerReal(orderModel.getUserId(), orderModel.getSellerId());
        logger.info("线下支付订单将买家:" + orderModel.getUserId() + " 设置为卖家:" + orderModel.getSellerId() +
                "的会员结果为:" + b);
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
        int ret = orderDao.batchDeliverOp(sellerId, deliverOrderList);
        //TODO 通知物流抓取
        if(ret > 0) {
            for(Order order : deliverOrderList) {
                expressClient.subscribeExpressPost(order.getDeliverInfo());
            }

            //推送订单操作通知
            OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
            for(Order item : deliverOrderList) {
                orderOptPush.addToOrderOptList(item.getOrderId(), "order_deliver_batch", item.getUserId(), item.getSellerId());
            }
            orderJedis.pushOrderNotification(orderOptPush);
        }


        return ret;
    }

    public OrderModel buyerQueryDetailOffline(int userId, String orderId) {
        return orderDao.getOrderByUserOffline(orderId, userId);
    }

    public OrderModel sellerQueryDetailOffline(int sellerId, String orderId) {
        return orderDao.getOrderBySellerOffline(orderId, sellerId);
    }

    public int buyerQueryOrderStatOffline(OrderQueryConditions conditions) {
        return orderDao.getOrderStatByUserOffline(conditions.getUserId(), conditions);
    }

    public List<OrderModel> buyerQueryListOffline(OrderQueryConditions conditions) {
        return orderDao.getOrderListByUserOffline(conditions.getUserId(), conditions);
    }

    public int sellerQueryOrderStatOffline(OrderQueryConditions conditions) {
        logger.info("卖家：" + conditions.getSellerId() + "：：：：：：：：：：：：查询订单，统计各种订单状态数量");
        return orderDao.getOrderStatBySellerOffline(conditions.getSellerId(), conditions);
    }

    public List<OrderModel> sellerQueryListOffline(OrderQueryConditions conditions) {
        return orderDao.getOrderListBySellerOffline(conditions.getSellerId(), conditions);
    }

    public int insertOrder(OrderModel orderModel) {
        int ret = orderDao.insertOrder(orderModel, BizUtil.USER_TYPE.BUYER.getEnumVal());
        ret = orderDao.insertOrder(orderModel, BizUtil.USER_TYPE.SELLER.getEnumVal());
        return  ret;
    }
}
 