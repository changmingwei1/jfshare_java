package com.jfshare.stock.server;

import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.stock.*;
import com.jfshare.stock.common.StockCommons;
import com.jfshare.stock.exceptions.StockException;
import com.jfshare.stock.exceptions.StockLockException;
import com.jfshare.stock.model.enums.BatchQueryType;
import com.jfshare.stock.model.enums.QueryType;
import com.jfshare.stock.service.StockService;
import com.jfshare.stock.util.ConvertUtil;
import com.jfshare.stock.util.FailCode;
import com.jfshare.stock.util.StockResultUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(value = "stockHandler") 
public class StockHandler extends BaseHandler implements StockServ.Iface {
    private Logger logger = LoggerFactory.getLogger(StockHandler.class);
    
    @Resource(name = "stockService")
    private StockService stockService;

    @Override
    public Result setStock(String tranId, StockInfo stockInfo) throws TException {
        if(!super.verifyParams(tranId, stockInfo)) {
            return StockResultUtil.createNormalFailResult(FailCode.parametersEmpty);
        }
        try {
            this.stockService.updateStock(tranId, ConvertUtil.convertProductStockList(stockInfo));
        } catch (Exception e) {
            logger.error("设置库存失败", e);
            return StockResultUtil.createNormalFailResult(FailCode.SYS_ERROR);
        }

        return StockResultUtil.createNormalResult();
    }

    @Override
    public StockResult queryStock(QueryParam param) throws TException {
        String productId = param.getProductId();
        String queryTypeStr = param.getQueryType();

        if(StringUtils.isBlank(productId)) {
            return StockResultUtil.createGetStockFailResult(FailCode.parametersEmpty);
        }

        QueryType queryType = null;

        try {
            if(param.getStorehouseId() <= 0) {
                queryType = QueryType.product;
            } else {
                if(StringUtils.isBlank(param.getSkuNum())){
                    queryType = QueryType.storehouse;
                } else {
                    queryType = QueryType.sku;
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("queryStock==> 错误的queryType, {}", queryTypeStr);
            return StockResultUtil.createGetStockFailResult(FailCode.parametersEmpty);
        }

        StockInfo stockInfo = null;
        try {
            switch (queryType) {
                case product:{
                    stockInfo = this.stockService.getStockInfo(productId);
                    break;
                }
                case storehouse:{
                    stockInfo = this.stockService.getStockInfo(productId, param.getStorehouseId());
                    break;
                }
                case sku:{
                    stockInfo = this.stockService.getStockInfo(productId, param.getStorehouseId(), param.getSkuNum());
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("查询库存失败", e);
            return StockResultUtil.createGetStockFailResult(FailCode.SYS_ERROR);
        }
        return StockResultUtil.createGetStockResult(stockInfo);

    }

    @Override
    public BatchStockResult batchQueryStock(BatchQueryParam param) throws TException {

        if(StringUtils.isBlank(param.getQueryType()) || CollectionUtils.isEmpty(param.getQueryContents())) {
            return StockResultUtil.createBatchGetStockFailResult(FailCode.parametersEmpty);
        }

        BatchQueryType queryType = null;

        try {
            queryType = BatchQueryType.valueOf(param.getQueryType());
        } catch (IllegalArgumentException e) {
            logger.error("batchQueryStock==> 错误的queryType, {}", param.getQueryType());
            return StockResultUtil.createBatchGetStockFailResult(FailCode.parametersEmpty);
        }

        List<StockInfo> stockInfos = null;
        List<String> querys = param.getQueryContents();
        try {
            switch (queryType) {
                case all:{
                    stockInfos = stockService.getStockInfoWithConcurrent(querys);
                    break;
                }
                case total:{
                    stockInfos = stockService.getStockInfoWithConcurrent(querys);
                    break;
                }
                case sku: {
                    stockInfos = stockService.getStockInfoWithConcurrent(querys);
                }
            }
        } catch (StockException e) {
            return StockResultUtil.createBatchGetStockFailResult(e.getFailDesc());
        } catch (Exception e) {
            return StockResultUtil.createBatchGetStockFailResult(FailCode.SYS_ERROR);
        }
        return StockResultUtil.createBatchGetStockResult(stockInfos);
    }

    public LockStockResult lockStock(String tranId,  List<LockInfo> lockInfoList){
        if(!super.verifyParams(tranId, lockInfoList)) {
            return StockResultUtil.createLockStockFailResult(FailCode.parametersEmpty);
        }

        try {
            this.stockService.lockStock(ConvertUtil.convertStockLockModelList(tranId, lockInfoList));
        } catch(StockLockException le) {
            logger.error("锁定库存失败，库存不足");
            return StockResultUtil.createLockStockResult(StockCommons.STOCK_RESULT_FAIL, ConvertUtil.rConvertStockLockModelList(le.getModels()), le.getFailDesc());
        } catch(Exception e) {
            logger.error("释放库存失败", e);
            return StockResultUtil.createLockStockResult(StockCommons.STOCK_RESULT_FAIL, null, FailCode.SYS_ERROR);
        }

        return StockResultUtil.createLockStockResult(StockCommons.STOCK_RESULT_SUCC, super.fillStockLockSucc(lockInfoList), null);
    }

    @Override
    public Result releaseStock(String tranId, List<LockInfo> lockInfoList) throws TException {
        if(!super.verifyParams(tranId, lockInfoList)) {
            return StockResultUtil.createNormalFailResult(FailCode.parametersEmpty);
        }

        if(!super.verifyParams(tranId, lockInfoList)) {
            return StockResultUtil.createNormalFailResult(FailCode.parametersEmpty);
        }
        try {
            this.stockService.releaseStock(ConvertUtil.convertStockLockModelList(tranId, lockInfoList));
        } catch (Exception e) {
            logger.error("释放库存失败", e);
            return StockResultUtil.createNormalFailResult(FailCode.SYS_ERROR);
        }
        return StockResultUtil.createNormalResult();
    }

    @Override
    public Result releaseLockCount(String tranId, List<LockInfo> lockInfoList) throws TException {
        if(!super.verifyParams(tranId, lockInfoList)) {
            return StockResultUtil.createNormalFailResult(FailCode.parametersEmpty);
        }
        try {
            this.stockService.releaseLockCount(ConvertUtil.convertStockLockModelList(tranId, lockInfoList));
        } catch (Exception e) {
            logger.error("释放库存锁定量失败", e);
            return StockResultUtil.createNormalFailResult(FailCode.SYS_ERROR);
        }
        return StockResultUtil.createNormalResult();
    }

    @Override
    public Result removeStock(String productId) throws TException {
        if(!super.verifyParams(productId)) {
            return StockResultUtil.createNormalFailResult(FailCode.parametersEmpty);
        }
        try {
            this.stockService.removeStock(productId);
        } catch (Exception e) {
            logger.error("删除库存信息失败", e);
            return StockResultUtil.createNormalFailResult(FailCode.SYS_ERROR);
        }
        return StockResultUtil.createNormalResult();
    }

    @Override
    public Result createStock(String tranId, StockInfo stockInfo) throws TException {
        if(!super.verifyParams(tranId, stockInfo)) {
            return StockResultUtil.createNormalFailResult(FailCode.parametersEmpty);
        }

        try {
            this.stockService.createStock(ConvertUtil.convertProductStockList(stockInfo));
        } catch (Exception e) {
            logger.error("创建库存信息失败", e);
            return StockResultUtil.createNormalFailResult(FailCode.SYS_ERROR);
        }
        return StockResultUtil.createNormalResult();
    }

    @Override
    public Result supplyStock(String tranId, StockInfo stockInfo) throws TException {
        return null;
    }

    @Override
    public Result supplyFullStock(String tranId, StockInfo stockInfo) throws TException {
        return null;
    }
}
