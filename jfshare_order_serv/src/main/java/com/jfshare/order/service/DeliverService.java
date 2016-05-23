package com.jfshare.order.service;

import com.jfshare.finagle.thrift.order.DeliverInfo;
import com.jfshare.order.common.OrderConstant;
import com.jfshare.order.dao.IOrderDao;
import com.jfshare.order.dao.IOrderJedis;
import com.jfshare.order.exceptions.DaoManualException;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.OrderOpt;
import com.jfshare.order.model.TbOrderRecordExample;
import com.jfshare.order.util.ConstantUtil;
import com.jfshare.order.util.FailCode;
import com.jfshare.utils.BizUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service(value = "deliverService")
public class DeliverService {
    private Logger logger = LoggerFactory.getLogger(DeliverService.class);

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IOrderJedis orderJedis;

    @Transactional
    public void updateOrderDeliverInfo(int sellerId, DeliverInfo deliverInfo) {
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderId(deliverInfo.getOrderId());
        orderModel.setSellerId(sellerId);
        orderModel.setOrderState(ConstantUtil.ORDER_STATE.FINISH_DELIVER.getEnumVal());
        orderModel.setUserId(deliverInfo.getUserId());
        if(StringUtils.isNotBlank(deliverInfo.getExpressId())) {
            orderModel.setExpressNo(deliverInfo.getExpressNo());
            orderModel.setExpressId(Integer.parseInt(deliverInfo.getExpressId()));
            orderModel.setExpressName(deliverInfo.getExpressName());
        }
//        orderModel.setBuyerComment(deliverInfo.getBuyerComment());
        orderModel.setSellerComment(deliverInfo.getSellerComment());
        orderModel.setReceiverName(deliverInfo.getReceiverName());
        orderModel.setReceiverMobile(deliverInfo.getReceiverMobile());
        orderModel.setReceiverTele(deliverInfo.getReceiverTele());
        orderModel.setProvinceId(deliverInfo.getProvinceId());
        orderModel.setCityId(deliverInfo.getCityId());
        orderModel.setCountyId(deliverInfo.getCountyId());
        orderModel.setProvinceName(deliverInfo.getProvinceName());
        orderModel.setCityName(deliverInfo.getCityName());
        orderModel.setCountyName(deliverInfo.getCountyName());
        orderModel.setReceiverAddress(deliverInfo.getReceiverAddress());
        orderModel.setPostCode(deliverInfo.getPostCode());
        orderModel.setDeliverTime(new DateTime());

        TbOrderRecordExample example = new TbOrderRecordExample();
        TbOrderRecordExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStateEqualTo(ConstantUtil.ORDER_STATE.WAIT_DELIVER.getEnumVal());
        criteria.andOrderIdEqualTo(deliverInfo.getOrderId());
        criteria.andSellerIdEqualTo(sellerId);
        criteria.andUserIdEqualTo(deliverInfo.getUserId());
        int res = orderDao.updateOrderWithCriteria(orderModel, 2, example);
        if(res > 0 ) {
            res = orderDao.updateOrderWithCriteria(orderModel, 1,example);
        }
        if(res <= 0) {
            throw new DaoManualException(FailCode.ORDER_UPDATE_ERROR);
        }

        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        orderOptPush.addToOrderOptList(orderModel.getOrderId(), "order_pay");
        orderJedis.pushOrderNotification(orderOptPush);
    }

    public void updateDeliverInfo(OrderModel orderModel) {
        OrderModel order = new OrderModel();
        order.setUserId(orderModel.getUserId());
        order.setSellerId(orderModel.getSellerId());
        DateTime deliverTime = new DateTime();
        order.setDeliverTime(deliverTime);
        order.setExpressId(orderModel.getExpressId());
        order.setExpressNo(orderModel.getExpressNo());
        order.setExpressName(orderModel.getExpressName());
        order.setSellerComment(orderModel.getSellerComment());

        TbOrderRecordExample example = new TbOrderRecordExample();
        TbOrderRecordExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStateEqualTo(ConstantUtil.ORDER_STATE.FINISH_DELIVER.getEnumVal());
        criteria.andOrderIdEqualTo(orderModel.getOrderId());
        criteria.andSellerIdEqualTo(orderModel.getSellerId());
        criteria.andUserIdEqualTo(orderModel.getUserId());
        int ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.BUYER.getEnumVal(),example);
        if (ret > 0) {
            ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.SELLER.getEnumVal(),example);
        }

        if (ret <= 0) {
            throw new DaoManualException(FailCode.ORDER_UPDATE_ERROR);
        }

        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        orderOptPush.addToOrderOptList(orderModel.getOrderId(), "order_pay");
        orderJedis.pushOrderNotification(orderOptPush);
    }

    public void updateDeliverInfoVir(OrderModel orderModel) {
        OrderModel order = new OrderModel();
        order.setUserId(orderModel.getUserId());
        order.setSellerId(orderModel.getSellerId());
        DateTime deliverTime = new DateTime();
        order.setDeliverTime(deliverTime);
        order.setOrderState(ConstantUtil.ORDER_STATE.FINISH_DELIVER.getEnumVal());

        TbOrderRecordExample example = new TbOrderRecordExample();
        TbOrderRecordExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStateEqualTo(ConstantUtil.ORDER_STATE.WAIT_DELIVER.getEnumVal());
        criteria.andOrderIdEqualTo(orderModel.getOrderId());
        criteria.andSellerIdEqualTo(orderModel.getSellerId());
        criteria.andUserIdEqualTo(orderModel.getUserId());
        int ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.BUYER.getEnumVal(),example);
        if (ret > 0) {
            ret = orderDao.updateOrderWithCriteria(order, BizUtil.USER_TYPE.SELLER.getEnumVal(),example);
        }

        if (ret <= 0) {
            throw new DaoManualException(FailCode.ORDER_UPDATE_ERROR);
        }

        //推送订单操作通知
        OrderOpt.OrderOptPush orderOptPush = new OrderOpt.OrderOptPush();
        orderOptPush.addToOrderOptList(orderModel.getOrderId(), "order_pay");
        orderJedis.pushOrderNotification(orderOptPush);
    }


}
 