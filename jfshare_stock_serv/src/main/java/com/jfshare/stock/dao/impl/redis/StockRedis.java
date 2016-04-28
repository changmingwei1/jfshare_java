package com.jfshare.stock.dao.impl.redis;

import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.stock.model.StockLockModel;
import com.jfshare.stock.model.TbProductStock;

import java.util.List;

/**
 * Created by Lenovo on 2015/11/22.
 */
public interface StockRedis {

    /**
     * 设置缓存
     * @param productId
     * @param tbProductStockList
     * @return
     */
    public boolean setStockCache(String productId, List<TbProductStock> tbProductStockList);

    /**
     * 商品维度库存查询
     * @param productId
     * @return
     */
    public StockInfo getStockAll(String productId);

    /**
     * 仓库维度库存查询
     * @param productId
     * @param storehouseId
     * @return
     */
    public StockInfo getStockStorehouse(String productId, String storehouseId);

    /**
     * SKU维度库存查询
     * @param productId
     * @param storehouseId
     * @param skuNum
     * @return
     */
    public StockInfo getStockSku(String productId, String storehouseId, String skuNum);

    public StockInfo getStockSkus(String productId, List<String> skuNums);

    /**
     * 更新库存缓存
     * @param stockLockModels
     */
    public void updateStockCache(List<StockLockModel> stockLockModels);

    /**
     * 更新库存缓存
     * @param stockLockModel
     */
    public void updateStockCacheSingle(StockLockModel stockLockModel);

    /**
     * 删除库存分布式操作锁
     * @param stockLockModel
     */
    public void removeStockLockFlag(StockLockModel stockLockModel);

    /**
     * 回滚库存分布式操作锁
     * @param stockLockModel
     */
    public void rollbackStockLockFlag(StockLockModel stockLockModel);

    /**
     * 删除库存缓存
     * @param productId
     * @return
     */
    public String removeStockCache(String productId);

    /**
     * 设置库存分布式操作锁
     * @param stockLockModel
     * @return
     */
    public boolean setStockLockFlag(StockLockModel stockLockModel);

    /**
     * 检查库存分布式操作锁
     * @param stockLockModel
     * @return
     */
    public boolean checkStockLockFlag(StockLockModel stockLockModel);

    /**
     * 根据DB数据刷新缓存
     * @param productId
     * @param tbProductStockList
     * @return
     */
    public boolean reloadStockCache(String productId, List<TbProductStock> tbProductStockList);
}
