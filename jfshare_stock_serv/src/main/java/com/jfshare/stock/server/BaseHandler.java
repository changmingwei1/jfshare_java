package com.jfshare.stock.server;

import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.stock.server.support.IHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public abstract class BaseHandler implements IHandler {

    private Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    List<LockInfo> fillStockLockSucc(List<LockInfo> lockInfos) {
        if(CollectionUtils.isEmpty(lockInfos)) {
            return lockInfos;
        }
        for (LockInfo lockInfo : lockInfos) {
            lockInfo.setLockCount(lockInfo.getApplyCount());
        }
        return lockInfos;
    }

    boolean verifyParams(String param, Collection c) {
        if(StringUtils.isEmpty(param) || CollectionUtils.isEmpty(c)) {
            return false;
        }
        return true;
    }

    boolean verifyParams(String param, StockInfo stockInfo) {
        if(StringUtils.isEmpty(param) || stockInfo == null || CollectionUtils.isEmpty(stockInfo.getStockItems())) {
            return false;
        }
        return true;
    }

    boolean verifyParams(StockInfo stockInfo) {
        if(stockInfo == null || CollectionUtils.isEmpty(stockInfo.getStockItems())) {
            return false;
        }
        return true;
    }

    boolean verifyParams(String param) {
        if(StringUtils.isEmpty(param)) {
            return false;
        }
        return true;
    }
}
