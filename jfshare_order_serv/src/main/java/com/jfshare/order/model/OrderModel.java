package com.jfshare.order.model;

import java.util.List;

/**
 * Created by Lenovo on 2015/10/19.
 */
public class OrderModel extends TbOrderRecord{

    private List<TbOrderInfoRecord> tbOrderInfoList;

    public List<TbOrderInfoRecord> getTbOrderInfoList() {
        return tbOrderInfoList;
    }

    public void setTbOrderInfoList(List<TbOrderInfoRecord> tbOrderInfoList) {
        this.tbOrderInfoList = tbOrderInfoList;
    }
}
