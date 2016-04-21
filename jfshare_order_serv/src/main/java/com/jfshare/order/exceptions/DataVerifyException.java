/** 
 * Project Name:order-serv
 * File Name:StockException.java 
 * Package Name:com.jfshare.order.exceptions
 * Date:2015年1月14日上午11:29:32 
 * 
*/  
  
package com.jfshare.order.exceptions;

import com.jfshare.finagle.thrift.result.FailDesc;

import java.util.List;

/** 
 * ClassName:StockException <br/> 
 * Function: 库存数据异常类<br/> 
 * Reason:   <br/> 
 * Date:     2015年1月14日 上午11:29:32 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public class DataVerifyException extends BaseException {

	static final long serialVersionUID = 1L;

    public DataVerifyException(String message) {
        super(message);
    }

    public DataVerifyException(FailDesc failDesc) {
        super(failDesc);
    }

    public DataVerifyException(FailDesc failDesc, Throwable e) {
        super(failDesc, e);
    }

    public DataVerifyException(List<FailDesc> failDescs) {
        super(failDescs);
    }

    public DataVerifyException(List<FailDesc> failDescs, Throwable e) {
        super(failDescs, e);
    }

    public DataVerifyException(Throwable e) {
        super(e);
    }

    public DataVerifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
  