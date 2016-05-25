package com.jfshare.order.dao;

import com.jfshare.finagle.thrift.order.PayState;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.OrderOpt;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by stepyee on 16/3/15.
 */
public interface IOrderJedis {
    int addPayState(String payId, int retCode, DateTime cancelTime);

    PayState getPayState(String payId);

    boolean pushOrderNotification(OrderOpt.OrderOptPush orderOptPush);
}
