package com.jfshare.order.dao;

import com.jfshare.finagle.thrift.order.PayState;
import org.joda.time.DateTime;

/**
 * Created by stepyee on 16/3/15.
 */
public interface IOrderJedis {
    int addPayState(String payId, int retCode, DateTime cancelTime);

    PayState getPayState(String payId);
}
