package com.jfshare.stock.service;

import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.stock.dao.IStockDao;
import com.jfshare.stock.dao.impl.redis.StockRedis;
import com.jfshare.stock.exceptions.StockLockException;
import com.jfshare.stock.model.StockLockModel;
import com.jfshare.stock.model.TbProductStock;
import com.jfshare.stock.util.ConvertUtil;
import com.jfshare.stock.util.FailCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * *************************************************************************
 * @文件名称: ProductStockService.java
 *
 * @包路径  : com.jfshare.stock.server
 *
 *
 * @类描述:   库存服务接口服务类
 *
 * @创建人:   sushi
 *
 * @修改记录:
-----------------------------------------------------------------------------------------------
时间						|		修改人		|		修改的方法		|		修改描述
-----------------------------------------------------------------------------------------------
|					|					|
-----------------------------------------------------------------------------------------------

 **************************************************************************
 */
@Service(value = "stockService")
public class StockService{
    private Logger logger = LoggerFactory.getLogger(StockService.class);

    @Resource(name="stockRedis")
    private StockRedis stockRedis;

    @Resource(name = "stockDao")
    private IStockDao stockDao;

    @Transactional(propagation=Propagation.REQUIRED)
    public boolean lockStock(List<StockLockModel> stockInfoModelList) {

        for(StockLockModel stockLockModel : stockInfoModelList){
            stockLockModel.setLockCountChange(-stockLockModel.getCountChange());
            //设置分布式lock标记
            this.stockRedis.setStockLockFlag(stockLockModel);

            if(stockDao.updateStockByChange(stockLockModel) == 0) {
                stockLockModel.setLockCountChange(0);
                throw new StockLockException(FailCode.stockNotEnough, stockInfoModelList);
            }
        }

        this.stockRedis.updateStockCache(stockInfoModelList);

        return true;
    }

    /**
     * @param stockInfoModelList
     * @return
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public boolean releaseStock(List<StockLockModel> stockInfoModelList) {

        for(StockLockModel stockLockModel : stockInfoModelList){
            //校验锁定库存标记，若校验通过设置释放中标记，若校验未通过do nothing
            if(this.stockRedis.checkStockLockFlag(stockLockModel)) {
                stockLockModel.setLockCountChange(stockLockModel.getCountChange());
                stockLockModel.setCountChange(-stockLockModel.getCountChange());
                if(stockDao.updateStockByChange(stockLockModel) > 0) {
                    //刷新缓存
                    this.stockRedis.updateStockCacheSingle(stockLockModel);
                    //删除锁定库存标记
                    this.stockRedis.removeStockLockFlag(stockLockModel);
                }else {
                    //释放库存失败，回滚锁定库存标记
                    this.stockRedis.rollbackStockLockFlag(stockLockModel);
                }
            }
        }

        return true;
    }

   
    public boolean releaseLockCount(List<StockLockModel> stockInfoModelList) {
        for(StockLockModel stockLockModel : stockInfoModelList){
            if(this.stockRedis.checkStockLockFlag(stockLockModel)) {
                stockLockModel.setLockCountChange(stockLockModel.getCountChange());
                stockLockModel.setCountChange(0);
                if(stockDao.updateStockByChange(stockLockModel)>0) {
                    //刷新缓存
                    this.stockRedis.updateStockCacheSingle(stockLockModel);
                    //删除锁定库存标记
                    this.stockRedis.removeStockLockFlag(stockLockModel);
                } else {
                    //释放库存失败，回滚锁定库存标记
                    this.stockRedis.rollbackStockLockFlag(stockLockModel);
                }
            }
        }

        return true;
    }

   
    public StockInfo getStockInfo(String productId) {
        //query from cache
        StockInfo stockInfo = this.stockRedis.getStockAll(productId);
        if(stockInfo != null) {
            return stockInfo;
        }

        //query from db
        List<TbProductStock> stockList = stockDao.getStockByProductId(productId);

        //reload to cache
        this.stockRedis.reloadStockCache(productId, stockList);
        stockInfo = ConvertUtil.convertStockInfo(productId, stockList);
        return stockInfo;
    }

   
    public StockInfo getStockInfo(String productId, List<String> skuNumList) {
        //query from cache
        StockInfo stockInfo = this.stockRedis.getStockSkus(productId, skuNumList);
        return stockInfo;
    }

    public void removeStock(String productId) {
        //删除db中库旧数据
        int resultCount = stockDao.deleteStockByProductId(productId);
        this.stockRedis.removeStockCache(productId);
    }

   
    public void createStock(List<TbProductStock> stockList) {
        //插入新的库存数据
        stockDao.insertStockInfoBatch(stockList);
        this.stockRedis.reloadStockCache(stockList.get(0).getProductId(), stockList);
    }

    public void updateStock(String tranId, List<TbProductStock> stockList) {

        String productId = stockList.get(0).getProductId();
        //删除db中库旧数据，插入新的库存数据
        stockDao.insertStockWithDelete(productId, stockList);

        //删除redis中旧数据
        this.stockRedis.reloadStockCache(productId, stockList);
    }
}
 