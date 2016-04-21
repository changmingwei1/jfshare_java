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
public class DataConvertException extends BaseException {

	static final long serialVersionUID = 1L;

    public DataConvertException(String message) {
        super(message);
    }

    public DataConvertException(FailDesc failDesc) {
        super(failDesc);
    }

    public DataConvertException(FailDesc failDesc, Throwable e) {
        super(failDesc, e);
    }

    public DataConvertException(List<FailDesc> failDescs) {
        super(failDescs);
    }

    public DataConvertException(List<FailDesc> failDescs, Throwable e) {
        super(failDescs, e);
    }

    public DataConvertException(Throwable e) {
        super(e);
    }

    public DataConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
  