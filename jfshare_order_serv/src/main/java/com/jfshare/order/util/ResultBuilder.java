/** 
 * Project Name:order-serv
 * File Name:StockResultUtil.java 
 * Package Name:com.jfshare.order.util
 * Date:2014年12月22日下午2:29:29 
 * 
*/  
  
package com.jfshare.order.util;

import com.jfshare.finagle.thrift.order.*;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.order.common.Commons;
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
	 *  Function:
	 *  功能说明：Result 失败结果填充
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年1月9日 下午3:10:37
	 *	 返回类型: Result    
	 *  @param failDesc
	 *  @return
	 */
	public static Result createFailNormalResult(FailDesc failDesc) {
		Result result = new Result(Commons.CODE_RESULT_FAIL);
		List<FailDesc> failList = new ArrayList<FailDesc>();
		failList.add(failDesc);
		result.setFailDescList(failList);
		return result;
	}

    public static Result createFailNormalResult(List<FailDesc> failDescs) {
        Result result = new Result(Commons.CODE_RESULT_FAIL);
        result.setFailDescList(failDescs);
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
		r.setCode(Commons.CODE_RESULT_SUCC);
		return r;
	}

    public static OrderDetailResult createOrderDetailResult(Order orderDetail) {
        OrderDetailResult orderDetailResult = new OrderDetailResult(new Result(Commons.CODE_RESULT_SUCC));
        orderDetailResult.setOrder(orderDetail);
        return orderDetailResult;
    }

    public static OrderDetailResult createFailOrderDetailResult(FailDesc failDesc) {
        Result result = new Result(Commons.CODE_RESULT_FAIL);
        result.addToFailDescList(failDesc);
        return new OrderDetailResult(result);
    }

    public static OrderProfileResult createOrderProfileResult(OrderQueryConditions conditions, List<Order> orderDetails, int total) {
        Result result = new Result(Commons.CODE_RESULT_SUCC);
        OrderProfileResult orderProfileResult = new OrderProfileResult(result);

        OrderProfilePage orderProfilePage = new OrderProfilePage();
        int count = conditions.getCount();
        int currentPage = conditions.getCurPage();
        int pageCount = total/count + (total%count==0?0:1);
        orderProfilePage.setPageCount(pageCount);
        orderProfilePage.setCount(count);
        orderProfilePage.setCurPage(currentPage);
        orderProfilePage.setTotal(total);

        orderProfilePage.setOrderProfileList(orderDetails);
        orderProfileResult.setOrderProfilePage(orderProfilePage);

        return orderProfileResult;
    }

    public static OrderProfileResult createFailOrderProfileResult(List<FailDesc> failDescs) {
        Result result = new Result(Commons.CODE_RESULT_FAIL);
        result.setFailDescList(failDescs);
        return new OrderProfileResult(result);
    }

    public static OrderStateResult createFailOrderStateResult(List<FailDesc> failDescs) {
        Result result = new Result(Commons.CODE_RESULT_FAIL);
        result.setFailDescList(failDescs);
        OrderStateResult orderStateResult = new OrderStateResult();
        orderStateResult.setResult(result);
        return orderStateResult;
    }

    public static OrderStateResult createOrderStateResult(List<OrderCount> orderStateCounts) {
        Result result = new Result(Commons.CODE_RESULT_SUCC);
        OrderStateResult orderStateResult = new OrderStateResult();
        orderStateResult.setResult(result);
        orderStateResult.setOrderCountList(orderStateCounts);
        return orderStateResult;
    }

    public static OrderProfileResult createFailOrderProfileResult(FailDesc failDesc) {
        Result result = new Result(Commons.CODE_RESULT_FAIL);
        result.addToFailDescList(failDesc);
        return new OrderProfileResult(result);
    }

}
  