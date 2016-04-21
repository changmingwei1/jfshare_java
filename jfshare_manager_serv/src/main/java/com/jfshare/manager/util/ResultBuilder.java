/** 
 * Project Name:manager-serv
 * File Name:StockResultUtil.java 
 * Package Name:com.jfshare.manager.util
 * Date:2014年12月22日下午2:29:29 
 * 
*/  
  
package com.jfshare.manager.util;

import com.jfshare.finagle.thrift.manager.ProductOpt;
import com.jfshare.finagle.thrift.manager.ProductOptResult;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.manager.common.ManagerCommons;
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
public class ResultBuilder {
	Logger logger = LoggerFactory.getLogger(ResultBuilder.class);

    /**
     *
     * @param productOpts
     * @return
     */
	public static ProductOptResult createProductOptResult(List<ProductOpt> productOpts) {
        Result r = new Result(ManagerCommons.THRIFT_RESULT_SUCC);
        ProductOptResult optResult = new ProductOptResult();
        optResult.setResult(r);
        optResult.setProductOptRecords(productOpts);
        return  optResult;
    }

    /**
     *
     * @param productOpts
     * @return
     */
    public static ProductOptResult createFailProductOptResult(FailDesc failDesc) {
        Result r = new Result(ManagerCommons.THRIFT_RESULT_FAIL);
        r.addToFailDescList(failDesc);
        ProductOptResult optResult = new ProductOptResult();
        optResult.setResult(r);
        return  optResult;
    }
	
	/**
	 * 
	 *  Function:
	 *  功能说明：Result 失败结果填充
	 *	 使用说明：
	 *	 返回类型: Result
	 *  @param failDesc
	 *  @return
	 */
	public static Result createNormalFailResult(FailDesc failDesc) {
		Result result = new Result(ManagerCommons.THRIFT_RESULT_FAIL);
		List<FailDesc> failList = new ArrayList<FailDesc>();
		failList.add(failDesc);
		result.setFailDescList(failList);
		return result;
	}
	
	/**
	 *  Function: 构造成功的Result结果
	 *	 返回类型: Result
	 *  @return
	 */
	public static Result createNormalResult() {
		Result r = new Result();
		r.setCode(ManagerCommons.THRIFT_RESULT_SUCC);
		return r;
	}
}
  