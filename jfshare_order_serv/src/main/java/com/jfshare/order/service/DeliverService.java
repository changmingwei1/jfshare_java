package com.jfshare.order.service;

import com.jfshare.finagle.thrift.order.DeliverInfo;
import com.jfshare.order.common.OrderConstant;
import com.jfshare.order.dao.IOrderDao;
import com.jfshare.order.exceptions.DaoManualException;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderRecordExample;
import com.jfshare.order.util.FailCode;
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

    @Transactional
    public void updateOrderDeliverInfo(int sellerId, DeliverInfo deliverInfo) {
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderId(deliverInfo.getOrderId());
        orderModel.setSellerId(sellerId);
        orderModel.setOrderState(OrderConstant.FINISH_DELIVER_STATE);
        orderModel.setUserId(deliverInfo.getUserId());
        if(StringUtils.isNotBlank(deliverInfo.getExpressId())) {
            orderModel.setExpressNo(deliverInfo.getExpressNo());
            orderModel.setExpressId(Integer.parseInt(deliverInfo.getExpressId()));
            orderModel.setExpressName(deliverInfo.getExpressName());
        }
        orderModel.setBuyerComment(deliverInfo.getBuyerComment());
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
        criteria.andOrderStateEqualTo(OrderConstant.WAIT_DELIVER_STATE);
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
    }

}
 