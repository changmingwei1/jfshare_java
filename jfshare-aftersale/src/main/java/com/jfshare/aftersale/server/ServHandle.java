package com.jfshare.aftersale.server;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfshare.aftersale.service.IAfterSaleService;
import com.jfshare.aftersale.util.FailCode;
import com.jfshare.finagle.thrift.aftersale.AfterSale;
import com.jfshare.finagle.thrift.aftersale.AfterSaleCountResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryCountParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleServ;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.utils.StringUtil;

@Service(value = "handler")
public class ServHandle implements AfterSaleServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Autowired
	private IAfterSaleService afterSaleServiceImpl;
	
	@Override
	public StringResult request(AfterSale afterSale) throws TException {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);
		try{
			logger.info("申请售后请求：" + afterSale != null ? afterSale.toString(): null);
			if(StringUtil.isNullOrEmpty(afterSale)){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return stringResult;
			}
			
			if(afterSale.getUserId() <= 0 || afterSale.getOrderId() == null 
					|| afterSale.getProductId() == null || afterSale.getSkuNum() == null
					|| afterSale.getOrderTime() == null){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return stringResult;
			}
			
			afterSaleServiceImpl.applyAfterSale(stringResult, afterSale);
		} catch (Exception ex){
			logger.error("申请售后失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return stringResult;
	}

//	@Override
//	public StringResult pass(AfterSale afterSale) throws TException {
//		StringResult stringResult = new StringResult();
//		Result result = new Result();
//		result.setCode(0);
//		stringResult.setResult(result);
//		try{
//			logger.info("处理售后请求：" + afterSale != null ? afterSale.toString(): null);
//			if(StringUtil.isNullOrEmpty(afterSale)){
//				FailCode.addFails(result, FailCode.requestArgsIsNull);
//				return stringResult;
//			}
//			
//			if(afterSale.getUserId() <= 0 || afterSale.getOrderId() == null 
//					|| afterSale.getProductId() == null || afterSale.getSkuNum() == null){
//				FailCode.addFails(result, FailCode.requestArgsIsNull);
//				return stringResult;
//			}
//			
//			afterSaleServiceImpl.approveAfterSale(stringResult, afterSale);
//		} catch (Exception ex){
//			logger.error("申请售后失败！", ex);
//			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
//		}
//		return stringResult;
//	}

	@Override
	public AfterSaleResult queryAfterSale(AfterSaleQueryParam param) throws TException {
		AfterSaleResult afterSaleResult = new AfterSaleResult();
		Result result = new Result();
		result.setCode(0);
		afterSaleResult.setResult(result);
		try{
			logger.info("查询售后请求：" + param != null ? param.toString(): null);
			if(StringUtil.isNullOrEmpty(param)){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return afterSaleResult;
			}
			
			afterSaleServiceImpl.queryAfterSale(afterSaleResult, param);
		}catch(Exception ex){
			logger.error("查询售后失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
				
		return afterSaleResult;
	}

		//售后通过
	@Override
	public StringResult auditPass(AfterSale afterSale) throws TException {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);
		try{
			logger.info("处理售后请求：" + afterSale != null ? afterSale.toString(): null);
			if(StringUtil.isNullOrEmpty(afterSale)){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return stringResult;
			}
			
			if(afterSale.getUserId() <= 0 || afterSale.getOrderId() == null 
					|| afterSale.getProductId() == null || afterSale.getSkuNum() == null){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return stringResult;
			}
			
			afterSaleServiceImpl.approveAfterSale(stringResult, afterSale);
		} catch (Exception ex){
			logger.error("申请售后失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return stringResult;
	}

	@Override
	public AfterSaleOrderResult queryAfterSaleOrder(AfterSaleOrderParam param, Pagination pagination) throws TException {
		AfterSaleOrderResult afterSaleOrderResult = new AfterSaleOrderResult();
		Result result = new Result();
		result.setCode(0);
		afterSaleOrderResult.setResult(result);
		try{
			afterSaleServiceImpl.queryAfterSaleOrder(afterSaleOrderResult, param, pagination);
		} catch (Exception ex){
			logger.error("获取售后订单信息失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		
		return afterSaleOrderResult;
	}

	@Override
	public AfterSaleCountResult queryAfterSaleCount(AfterSaleQueryCountParam param) throws TException {
		AfterSaleCountResult afterSaleCountResult = new AfterSaleCountResult();
		Result result = new Result();
		result.setCode(0);
		afterSaleCountResult.setResult(result);
		try{
			logger.info("查询售后请求：" + param != null ? param.toString(): null);
			if(StringUtil.isNullOrEmpty(param)){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return afterSaleCountResult;
			}
			
			afterSaleServiceImpl.queryAfterSaleCount(afterSaleCountResult, param);
		}catch(Exception ex){
			logger.error("查询售后失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
				
		return afterSaleCountResult;
	}
}
