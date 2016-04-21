package com.jfshare.stock.dao.impl.redis.impl;

import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.finagle.thrift.stock.StockItem;
import com.jfshare.stock.common.StockCommons;
import com.jfshare.stock.dao.impl.redis.BaseRedis;
import com.jfshare.stock.dao.impl.redis.ConstRedis;
import com.jfshare.stock.dao.impl.redis.StockRedis;
import com.jfshare.stock.model.StockLockModel;
import com.jfshare.stock.model.TbProductStock;
import com.jfshare.stock.util.DateTimeUtil;
import com.jfshare.utils.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2015/11/22.
 */
@Service(value = "stockRedis")
public class StockRedisImpl implements StockRedis{
    private Logger logger = LoggerFactory.getLogger(StockRedisImpl.class);

    @Autowired
    private BaseRedis baseRedis;

    @Override
    public boolean setStockCache(String productId, List<TbProductStock> tbProductStockList) {
        if(CollectionUtils.isEmpty(tbProductStockList)) {
            return false;
        }

        int total = 0;
        int lockTotal = 0;
        Map<String, String> cacheMap = new HashMap<>();
        for(TbProductStock tbProductStock : tbProductStockList) {
            int count = tbProductStock.getActiveCount();
            int lockCount = tbProductStock.getLockCount();
            total += count;
            lockTotal += lockCount;
            cacheMap.put(ConstRedis.STOCK_FIELD_COUNT + tbProductStock.getSkuNum(), String.valueOf(count));
            cacheMap.put(ConstRedis.STOCK_FIELD_LOCKCOUNT + tbProductStock.getSkuNum(), String.valueOf(lockCount));
        }
        cacheMap.put(ConstRedis.STOCK_FIELD_TOTAL, String.valueOf(total));
        cacheMap.put(ConstRedis.STOCK_FIELD_TOTALLOCK, String.valueOf(lockTotal));
        DateTime editTime = tbProductStockList.get(0).getCreateTime();
        if(editTime == null) {
            editTime = DateTimeUtil.currentDateTime();
        }
        cacheMap.put(ConstRedis.LAST_UPDATE_TIME, String.valueOf(editTime.getMillis()));

        return this.baseRedis.putMap(ConstRedis.STOCK_KEY + productId, cacheMap);
    }

    @Override
    public StockInfo getStockAll(String productId) {
        Map<String, String> stockMapCache = this.baseRedis.getMapAll(ConstRedis.STOCK_KEY + productId);
        if(MapUtils.isEmpty(stockMapCache)) {
            return null;
        }

        List<String> skuNums = this.getSkuNums(stockMapCache);
        return this.fillStockInfo(productId, stockMapCache, skuNums);
    }

    @Override
    public StockInfo getStockSkus(String productId, List<String> skuNums) {
        Map<String, String> stockMapCache = this.baseRedis.getMapAll(ConstRedis.STOCK_KEY + productId);
        if(MapUtils.isEmpty(stockMapCache)) {
            return null;
        }
        return this.fillStockInfo(productId, stockMapCache, skuNums);
    }

    @Override
    public void updateStockCache(List<StockLockModel> stockLockModels) {
        if(CollectionUtils.isEmpty(stockLockModels)) {
            return;
        }
        for(StockLockModel stockLockModel : stockLockModels) {
            this.updateStockCacheSingle(stockLockModel);
        }
    }

    @Override
    public String removeStockCache(String productId) {
        return this.baseRedis.removeKV(ConstRedis.STOCK_KEY + productId);
    }

    @Override
    public boolean setStockLockFlag(StockLockModel stockLockModel) {
        /*
        * 创建订单锁定库存标识, 默认库存未释放，Key在1天后失效，
        */
        String lockFlagKey = this.getStockLockFlgKey(stockLockModel);
        return this.baseRedis.putKV(lockFlagKey,
                String.valueOf(DateTimeUtil.currentDateTime().getMillis()),
                StockCommons.LOCK_STOCK_EXPIRED_SECOND);
    }

    @Override
    public boolean checkStockLockFlag(StockLockModel stockLockModel) {
        String lockFlagKey = this.getStockLockFlgKey(stockLockModel);
        long productLastEditTime = this.getLastUpdateTime(stockLockModel.getProductId());
        String lockTimeStr = this.baseRedis.getKV(lockFlagKey);
        if(StringUtils.isBlank(lockTimeStr)) {
            return false;
        }
        long lockTime = NumberUtils.toLong(lockTimeStr);

        //正在释放库存
        if(lockTime < 0) {
            return false;
        }

        //下单后商品被编辑过， 库存不要释放了
        if(NumberUtils.toLong(lockTimeStr) < productLastEditTime) {
            return false;
        }

        //设置时间为-lockTime，标识正在释放
        this.baseRedis.putKV(lockFlagKey, String.valueOf(-lockTime));
        return true;
    }

    public void removeStockLockFlag(StockLockModel stockLockModel) {
        this.baseRedis.removeKV(this.getStockLockFlgKey(stockLockModel));
    }

    public void rollbackStockLockFlag(StockLockModel stockLockModel) {
        String lockFlagKey = this.getStockLockFlgKey(stockLockModel);
        String lockTimeStr = this.baseRedis.getKV(lockFlagKey);
        if(StringUtils.isBlank(lockTimeStr)) {
            return;
        }
        long lockTime = NumberUtils.toLong(lockTimeStr);
        this.baseRedis.putKV(lockFlagKey, String.valueOf(-lockTime));
    }

    @Override
    public boolean reloadStockCache(String productId, List<TbProductStock> tbProductStockList) {
        this.removeStockCache(productId);
        return this.setStockCache(productId, tbProductStockList);
    }

    /**
     * 从stockMapCache中筛选出skuNums
     * @param stockMap
     * @return
     */
    private List<String> getSkuNums(Map<String, String> stockMap) {
        List<String> skuNumList = new ArrayList<>();
        for(String key : stockMap.keySet()) {
            if(key.startsWith(ConstRedis.STOCK_FIELD_COUNT)) {
                skuNumList.add(key.substring(ConstRedis.STOCK_FIELD_COUNT.length(), key.length()));
            }
        }
        return skuNumList;
    }

    /**
     * 填充StockInfo, 若某个sku不存在则忽略此sku
     * @param productId
     * @param stockMapCache
     * @param skuNums
     * @return
     */
    private StockInfo fillStockInfo(String productId, Map<String, String> stockMapCache, List<String> skuNums) {
        StockInfo stockInfo = new StockInfo();
        //设置库存总数
        stockInfo.setTotal(NumberUtils.toInt(stockMapCache.get(ConstRedis.STOCK_FIELD_TOTAL)));
        //设置总库存数
        stockInfo.setLockTotal(NumberUtils.toInt(stockMapCache.get(ConstRedis.STOCK_FIELD_TOTALLOCK)));
        //设置商品id
        stockInfo.setProductId(productId);
        //设置stockItemMap
        Map<String, StockItem> stockItemMap = new HashMap<>();
        for(String skuNum : skuNums) {
            String count = stockMapCache.get(ConstRedis.STOCK_FIELD_COUNT+skuNum);

            if(StringUtils.isBlank(count)) {
                //sku不存在，跳过
                continue;
            }

            StockItem stockItem = new StockItem();
            stockItem.setSkuNum(skuNum);
            stockItem.setCount(NumberUtils.toInt(count));
            stockItem.setLockCount(NumberUtils.toInt(stockMapCache.get(ConstRedis.STOCK_FIELD_LOCKCOUNT) + skuNum, 0));
            stockItemMap.put(skuNum, stockItem);
        }

        stockInfo.setStockItemMap(stockItemMap);
        return stockInfo;
    }

    /**
     * 创建订单锁定库存标识
     * 结构：ORDER_LOCK_STOCK:订单id_商品id_skuNum
     * @param stockLockModel
     * @return
     */
    private String getStockLockFlgKey(StockLockModel stockLockModel) {
        String orderId = stockLockModel.getTransId();
        String productId = stockLockModel.getProductId();
        String skuNum = stockLockModel.getSkuNum();

        return ConstRedis.ORDER_LOCK_STOCK + orderId + "_" + productId + "_" + skuNum;
    }

    /**
     * 单sku刷新cache
     * @param stockLockModel
     */
    public void updateStockCacheSingle(StockLockModel stockLockModel) {
        String orderId = stockLockModel.getTransId();
        String productId = stockLockModel.getProductId();
        String skuNum = stockLockModel.getSkuNum();

        int lockCountChange = stockLockModel.getLockCountChange();
        int countChange = stockLockModel.getCountChange();
        if(!this.baseRedis.exists(ConstRedis.STOCK_KEY + productId)) {
            logger.warn("===》 刷新stock缓存失败，缓存不存在，放弃此次刷下cache, productId:{}", productId);
            return;
        }

        if(StringUtils.isBlank(baseRedis.getMap(ConstRedis.STOCK_KEY + productId, ConstRedis.STOCK_FIELD_COUNT + skuNum))) {
            logger.warn("===》 刷新stock缓存失败，ProductId:{}, SkuNum:{} 不存在！", productId, skuNum);
            return;
        }

        Jedis jedis = null;
        try {
            jedis = this.baseRedis.getJedis();
            Transaction tx = jedis.multi();
            tx.hincrBy(ConstRedis.STOCK_KEY + productId, ConstRedis.STOCK_FIELD_COUNT + skuNum, -countChange);
            tx.hincrBy(ConstRedis.STOCK_KEY + productId, ConstRedis.STOCK_FIELD_LOCKCOUNT + skuNum, -lockCountChange);
            tx.hincrBy(ConstRedis.STOCK_KEY + productId, ConstRedis.STOCK_FIELD_TOTAL, -countChange);
            tx.hincrBy(ConstRedis.STOCK_KEY + productId, ConstRedis.STOCK_FIELD_TOTALLOCK, -lockCountChange);
            tx.exec();
        } catch (Exception e) {
            logger.error("===》 刷新stock缓存发生异常, 参数："+ JsonMapper.toJsonNotNull(stockLockModel), e);
        } finally {
            if(jedis!=null) {
                this.baseRedis.returnJedis(jedis);
            }
        }
    }

    /**
     * 取缓存中的上次编辑商品时间
     * @param productId
     * @return
     */
    private long getLastUpdateTime(String productId) {
        String timeStr = this.baseRedis.getMap(ConstRedis.STOCK_KEY + productId, ConstRedis.LAST_UPDATE_TIME);
        return NumberUtils.toLong(timeStr, -1);
    }

}
