package com.jfshare.task.elasticsearch;

import com.jfshare.task.elasticsearch.models.EsOrder;

/**
 * Created by Lenovo on 2016/4/7.
 */
public interface IEsOrderHandler {
    void addOrUpdate(EsOrder esOrder);
}
