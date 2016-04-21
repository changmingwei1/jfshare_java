/** 
 * Project Name:stock-serv
 * File Name:StockResultUtil.java 
 * Package Name:com.jfshare.stock.util
 * Date:2014年12月22日下午2:29:29 
 * 
*/  
  
package com.jfshare.stock.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.stock.LockStockResult;
import com.jfshare.finagle.thrift.stock.StockInfo;
import com.jfshare.finagle.thrift.stock.StockResult;
import com.jfshare.stock.common.StockCommons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/** 
 * ClassName:StockResultUtil <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2014年12月22日 下午2:29:29 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public class StockResultUtil {
	Logger logger = LoggerFactory.getLogger(StockResultUtil.class);
	
	/**
	 * 
	 *  Function:
	 *  功能说明：填充LockStockResult填充
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年1月9日 下午2:34:25
	 *	 返回类型: LockStockResult    
	 *  @param code
	 *  @param lockInfoList
	 *  @param failDesc
	 *  @return
	 */
	public static LockStockResult createLockStockResult(int code, List<LockInfo> lockInfoList, FailDesc failDesc){
		LockStockResult lockStockResult = new LockStockResult();
		lockStockResult.result = new Result(code);
		lockStockResult.setLockInfoList(lockInfoList);
		
		//处理失败，填充失败描述信息
		if(code != 0) {
			lockStockResult.result.addToFailDescList(failDesc);
		}
		return lockStockResult;
	}
	
	/**
	 * 
	 *  Function:
	 *  功能说明：锁库存LockStockResult失败结果填充
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年1月9日 下午2:37:08
	 *	 返回类型: LockStockResult    
	 *  @param failDesc
	 *  @return
	 */
	public static LockStockResult createLockStockFailResult(FailDesc failDesc){
		LockStockResult lockStockResult = new LockStockResult();
		List<FailDesc> failList = new ArrayList<FailDesc>();
		failList.add(failDesc);
		lockStockResult.result = new Result(StockCommons.STOCK_RESULT_FAIL);
		lockStockResult.result.setFailDescList(failList);
		
		return lockStockResult;
	}
	
	/**
	 * 
	 *  Function:
	 *  功能说明：库存查询StockResult 失败结果填充
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年1月9日 下午3:13:09
	 *	 返回类型: StockResult    
	 *  @param failDesc
	 *  @return
	 */
	public static StockResult createGetStockFailResult(FailDesc failDesc) {
		StockResult stockResult = new StockResult();
		List<FailDesc> failList = new ArrayList<FailDesc>();
		failList.add(failDesc);
		stockResult.result = new Result(StockCommons.STOCK_RESULT_FAIL);
		stockResult.result.setFailDescList(failList);
		return stockResult;
	}
	
	/**
	 * 
	 *  Function:
	 *  功能说明：库存查询StockResult结果填充
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年1月9日 下午3:11:06
	 *	 返回类型: StockResult    
	 *  @param stockInfo
	 *  @return
	 */
	public static StockResult createGetStockResult(StockInfo stockInfo) {
		StockResult stockResult = new StockResult();
        stockResult.setStockInfo(stockInfo);
		stockResult.result = new Result(StockCommons.STOCK_RESULT_SUCC);
		return stockResult;
	}
	
	/**
	 * 
	 *  Function:
	 *  功能说明：Result 失败结果填充
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年1月9日 下午3:10:37
	 *	 返回类型: Result    
	 *  @param failDesc
	 *  @return
	 */
	public static Result createNormalFailResult(FailDesc failDesc) {
		Result result = new Result(StockCommons.STOCK_RESULT_FAIL);
		List<FailDesc> failList = new ArrayList<FailDesc>();
		failList.add(failDesc);
		result.setFailDescList(failList);
		return result;
	}
	
	/**
	 *  Function: 构造成功的Result结果
	 *  @author  sushi  DateTime 2015年3月20日 下午5:55:23
	 *	 返回类型: Result    
	 *  @return
	 */
	public static Result createNormalResult() {
		Result r = new Result();
		r.setCode(StockCommons.STOCK_RESULT_SUCC);
		return r;
	}
}
  