package com.jfshare.task.dao.mysql;

import com.jfshare.task.model.manual.OrderModel;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IOrderDao {
    List<OrderModel> selectConfirmOrder(int index, int pageLimit);

    List<OrderModel> selectCancelOrder(int index, int pageLimit);
}
