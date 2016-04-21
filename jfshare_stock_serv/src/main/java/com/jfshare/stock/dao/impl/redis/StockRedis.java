package com.jfshare.stock.dao.impl.redis;

import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.stock.model.StockLockModel;
import com.jfshare.stock.model.TbProductStock;

import java.util.List;

/**
 * Created by Lenovo on 2015/11/22.
 */
public interface StockRedis {

    public boolean setStockCache(String productId, List<TbProductStock> tbProductStockList);

    public StockInfo getStockAll(String productId);

    public StockInfo getStockSkus(String productId, List<String> skuNums);

    public void updateStockCache(List<StockLockModel> stockLockModels);

    public void updateStockCacheSingle(StockLockModel stockLockModel);

    public void removeStockLockFlag(StockLockModel stockLockModel);

    public void rollbackStockLockFlag(StockLockModel stockLockModel);

    public String removeStockCache(String productId);

    public boolean setStockLockFlag(StockLockModel stockLockModel);

    public boolean checkStockLockFlag(StockLockModel stockLockModel);

    public boolean reloadStockCache(String productId, List<TbProductStock> tbProductStockList);
}
