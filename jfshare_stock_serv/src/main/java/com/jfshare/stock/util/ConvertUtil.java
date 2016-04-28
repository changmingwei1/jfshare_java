package com.jfshare.stock.util;

import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.finagle.thrift.stock.StockItem;
import com.jfshare.stock.exceptions.StockConvertException;
import com.jfshare.stock.model.StockLockModel;
import com.jfshare.stock.model.TbProductStock;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2015/11/23.
 */
public class ConvertUtil {
    public static TbProductStock convertProductStock(String productId, StockItem stockItem) {
        if(stockItem == null) {
            throw new StockConvertException(FailCode.EntryIsNull);
        }
        TbProductStock tbProductStock = new TbProductStock();
        tbProductStock.setProductId(productId);
        tbProductStock.setSkuNum(stockItem.getSkuNum());
        tbProductStock.setActiveCount(stockItem.getCount());
        tbProductStock.setLockCount(stockItem.getLockCount());
        tbProductStock.setStorehouseId(Integer.parseInt(stockItem.getStorehouseId()));
        return tbProductStock;
    }

    public static StockItem rConvertProductStock(TbProductStock tbProductStock) {
        if(tbProductStock == null) {
            throw new StockConvertException(FailCode.EntryIsNull);
        }
        StockItem stockItem = new StockItem();
        stockItem.setSkuNum(tbProductStock.getSkuNum());
        stockItem.setCount(tbProductStock.getActiveCount());
        stockItem.setLockCount(tbProductStock.getLockCount());
        stockItem.setStorehouseId(String.valueOf(tbProductStock.getStorehouseId()));
        return stockItem;
    }

    public static List<TbProductStock> convertProductStockList(StockInfo stockInfo) {
        List<TbProductStock> tbProductStockList = new ArrayList<>();
        for (StockItem item : stockInfo.getStockItems()) {
            tbProductStockList.add(convertProductStock(stockInfo.getProductId(), item));
        }
        return tbProductStockList;
    }

    public static List<StockItem> rConvertProductStockList(List<TbProductStock> tbProductStocks) {
        if(CollectionUtils.isEmpty(tbProductStocks)) {
            return new ArrayList<StockItem>();
        }
        List<StockItem> stockItemList = new ArrayList<>();
        for (TbProductStock tbProductStock : tbProductStocks) {
            stockItemList.add(rConvertProductStock(tbProductStock));
        }
        return stockItemList;
    }

    public static StockLockModel convertStockLockModel(String tranId, LockInfo lockInfo) {
        if(lockInfo == null) {
            throw new StockConvertException(FailCode.EntryIsNull);
        }
        StockLockModel stockLLockModel = new StockLockModel();
        stockLLockModel.setTransId(tranId);
        stockLLockModel.setProductId(lockInfo.getProductId());
        stockLLockModel.setSkuNum(lockInfo.getSkuNum());
        stockLLockModel.setCountChange(lockInfo.getApplyCount());
        stockLLockModel.setStorehouseId(Integer.parseInt(lockInfo.getStorehouseId()));
        return stockLLockModel;
    }

    public static List<StockLockModel> convertStockLockModelList(String tranId, List<LockInfo> lockInfos) {
        if(CollectionUtils.isEmpty(lockInfos)) {
            return new ArrayList<StockLockModel>();
        }
        List<StockLockModel> stockLockModelList = new ArrayList<>();
        for (LockInfo lockInfo : lockInfos) {
            stockLockModelList.add(convertStockLockModel(tranId, lockInfo));
        }
        return stockLockModelList;
    }

    public static List<LockInfo> rConvertStockLockModelList(List<StockLockModel> stockLockModels) {
        if(CollectionUtils.isEmpty(stockLockModels)) {
            return new ArrayList<LockInfo>();
        }
        List<LockInfo> stockLockModelList = new ArrayList<>();
        for (StockLockModel model : stockLockModels) {
            stockLockModelList.add(rConvertStockLockModel(model));
        }
        return stockLockModelList;
    }

    public static LockInfo rConvertStockLockModel(StockLockModel stockLockModel) {
        if(stockLockModel == null) {
            throw new StockConvertException(FailCode.EntryIsNull);
        }
        LockInfo lockInfo = new LockInfo();
        lockInfo.setProductId(stockLockModel.getProductId());
        lockInfo.setStorehouseId(String.valueOf(stockLockModel.getStorehouseId()));
        lockInfo.setSkuNum(stockLockModel.getSkuNum());
        lockInfo.setApplyCount(stockLockModel.getCountChange());
        lockInfo.setLockCount(-stockLockModel.getLockCountChange());
        return lockInfo;
    }

    public static StockInfo convertStockInfo(String productId, List<TbProductStock> tbProductStocks) {
        StockInfo stockInfo = new StockInfo();
        List<StockItem> stockItems = new ArrayList<>();
        int totalCount = 0;
        int totalLockCount = 0;
        for(TbProductStock tbProductStock  : tbProductStocks) {
            StockItem item = ConvertUtil.rConvertProductStock(tbProductStock);
            totalCount += tbProductStock.getActiveCount();
            totalLockCount += tbProductStock.getLockCount();
            stockItems.add(item);
        }
        stockInfo.setProductId(productId);
        stockInfo.setTotal(totalCount);
        stockInfo.setLockTotal(totalLockCount);
        stockInfo.setStockItems(stockItems);
        return stockInfo;
    }

    public static StockInfo convertStockInfo(String productId, String storehouseId, List<TbProductStock> tbProductStocks) {
        StockInfo stockInfo = new StockInfo();
        List<StockItem> stockItems = new ArrayList<>();
        int totalCount = 0;
        int totalLockCount = 0;
        for(TbProductStock tbProductStock  : tbProductStocks) {
            if(String.valueOf(tbProductStock.getStorehouseId()).equals(storehouseId) == false)
                continue;

            StockItem item = ConvertUtil.rConvertProductStock(tbProductStock);
            totalCount += tbProductStock.getActiveCount();
            totalLockCount += tbProductStock.getLockCount();
            stockItems.add(item);
        }
        stockInfo.setProductId(productId);
        stockInfo.setTotal(totalCount);
        stockInfo.setLockTotal(totalLockCount);
        stockInfo.setStockItems(stockItems);
        return stockInfo;
    }

    public static StockInfo convertStockInfo(String productId, String storehouseId, String skuNum, List<TbProductStock> tbProductStocks) {
        StockInfo stockInfo = new StockInfo();
        List<StockItem> stockItems = new ArrayList<>();
        int totalCount = 0;
        int totalLockCount = 0;
        for(TbProductStock tbProductStock  : tbProductStocks) {
            if(String.valueOf(tbProductStock.getStorehouseId()).equals(storehouseId) == false || tbProductStock.getSkuNum().equals(skuNum) == false)
                continue;

            StockItem item = ConvertUtil.rConvertProductStock(tbProductStock);
            totalCount += tbProductStock.getActiveCount();
            totalLockCount += tbProductStock.getLockCount();
            stockItems.add(item);
        }
        stockInfo.setProductId(productId);
        stockInfo.setTotal(totalCount);
        stockInfo.setLockTotal(totalLockCount);
        stockInfo.setStockItems(stockItems);
        return stockInfo;
    }
}
