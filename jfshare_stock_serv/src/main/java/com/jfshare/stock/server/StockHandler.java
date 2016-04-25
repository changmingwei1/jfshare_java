package com.jfshare.stock.server;

import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.stock.*;
import com.jfshare.stock.common.StockCommons;
import com.jfshare.stock.exceptions.StockLockException;
import com.jfshare.stock.model.enums.QueryType;
import com.jfshare.stock.service.StockService;
import com.jfshare.stock.util.ConvertUtil;
import com.jfshare.stock.util.FailCode;
import com.jfshare.stock.util.StockResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

        if(StringUtils.isBlank(productId) || StringUtils.isBlank(queryTypeStr)) {
            return StockResultUtil.createGetStockFailResult(FailCode.parametersEmpty);
        }

        QueryType queryType = QueryType.undefined;

        try {
            queryType = QueryType.valueOf(queryTypeStr);
        } catch (IllegalArgumentException e) {
            logger.error("queryStock==> 错误的queryType, {}", queryTypeStr);
        }

        StockInfo stockInfo = null;
        try {
            switch (queryType) {
                case product:{
                    stockInfo = this.stockService.getStockInfo(productId);
                    break;
                }
                case storehouse:{
                    break;
                }
                case sku:{
                    break;
                }
                default:{
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
    public StockResult batchQueryStock(BatchQueryParam param) throws TException {
        return null;
    }

//    @Override
//    public StockResult getStock(String productId) throws TException {
//        if(!super.verifyParams(productId)) {
//            return StockResultUtil.createGetStockFailResult(FailCode.parametersEmpty);
//        }
//
//        StockInfo stockInfo = null;
//        try {
//            stockInfo = this.stockService.getStockInfo(productId);
//        } catch (Exception e) {
//            logger.error("查询库存失败", e);
//            return StockResultUtil.createGetStockFailResult(FailCode.SYS_ERROR);
//        }
//        return StockResultUtil.createGetStockResult(stockInfo);
//    }
//
//    @Override
//    public StockResult getStockForSku(String productId, List<String> skuNums) throws TException {
//        if(!super.verifyParams(productId)) {
//            return StockResultUtil.createGetStockFailResult(FailCode.parametersEmpty);
//        }
//
//        StockInfo stockInfo = null;
//        try {
//            stockInfo = this.stockService.getStockInfo(productId, skuNums);
//        } catch (Exception e) {
//            logger.error("查询库存失败", e);
//            return StockResultUtil.createGetStockFailResult(FailCode.SYS_ERROR);
//        }
//        return StockResultUtil.createGetStockResult(stockInfo);
//    }

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
