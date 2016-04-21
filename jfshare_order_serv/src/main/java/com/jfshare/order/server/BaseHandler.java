package com.jfshare.order.server;

import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.order.exceptions.DataVerifyException;
import com.jfshare.order.server.support.IHandler;
import com.jfshare.order.util.ParamCheck;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BaseHandler implements IHandler {

    private Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    public OrderQueryConditions verifyConditions(OrderQueryConditions conditions) {
        //检查最大分页数
        List<FailDesc> failList = ParamCheck.CheckMaxQueryCount(conditions.getCount());
        if (failList.size() > 0) {
            throw new DataVerifyException(failList);
        }

        if (conditions.getCount() <= 0) {
            conditions.setCount(10);
        }

        if (conditions.getCurPage() <= 0) {
            conditions.setCurPage(1);
        }

        if (StringUtils.isBlank(conditions.getMobile())) {
            conditions.setMobile(null);
        }

        if (StringUtils.isBlank(conditions.getProductName())) {
            conditions.setProductName(null);
        }

        if (StringUtils.isBlank(conditions.getUserName())) {
            conditions.setUserName(null);
        }

        if (StringUtils.isBlank(conditions.getPayTimeStart())) {
            conditions.setPayTimeStart(null);
        }

        if (StringUtils.isBlank(conditions.getPayTimeEnd())) {
            conditions.setPayTimeEnd(null);
        }

        if (StringUtils.isBlank(conditions.getDeliverTimeStart())) {
            conditions.setDeliverTimeStart(null);
        }

        if (StringUtils.isBlank(conditions.getDeliverTimeEnd())) {
            conditions.setDeliverTimeEnd(null);
        }

        if (StringUtils.isBlank(conditions.getSuccessTimeStart())) {
            conditions.setSuccessTimeStart(null);
        }

        if (StringUtils.isBlank(conditions.getSuccessTimeEnd())) {
            conditions.setSuccessTimeEnd(null);
        }

        if (StringUtils.isBlank(conditions.getLastUpdateTimeStart())) {
            conditions.setLastUpdateTimeStart(null);
        }

        if (StringUtils.isBlank(conditions.getLastUpdateTimeEnd())) {
            conditions.setLastUpdateTimeEnd(null);
        }

        if (StringUtils.isBlank(conditions.getOrderId())) {
            conditions.setOrderId(null);
        }

        if (StringUtils.isBlank(conditions.getExpressNo())) {
            conditions.setExpressNo(null);
        }

        if (StringUtils.isBlank(conditions.getNickName())) {
            conditions.setNickName(null);
        }

        if (StringUtils.isBlank(conditions.getProductId())) {
            conditions.setProductId(null);
        }

        if (StringUtils.isNotBlank(conditions.getStartTime())) {
            conditions.setStartTime(conditions.getStartTime().substring(0,10) + " 00:00:00");
        } else {
            conditions.setStartTime(null);
        }

        if (StringUtils.isNotBlank(conditions.getEndTime())) {
            conditions.setEndTime(conditions.getEndTime().substring(0,10) + " 23:59:59");
        } else {
            conditions.setEndTime(null);
        }
        return conditions;
    }
}
