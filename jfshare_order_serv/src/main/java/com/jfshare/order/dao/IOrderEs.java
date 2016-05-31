package com.jfshare.order.dao;

import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import org.elasticsearch.search.SearchHits;

/**
 * Created by stepyee on 16/3/15.
 */
public interface IOrderEs {

    SearchHits search(OrderQueryConditions orderQueryConditions);
}
