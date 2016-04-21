/** 
 * Project Name:manager-serv
 * File Name:StockException.java 
 * Package Name:com.jfshare.manager.exceptions
 * Date:2015年1月14日上午11:29:32 
 * 
*/  
  
package com.jfshare.product.exceptions;

import com.jfshare.finagle.thrift.result.FailDesc;

/** 
 * ClassName:StockException <br/> 
 * Function: 库存服务异常基类<br/> 
 * Reason:   <br/> 
 * Date:     2015年1月14日 上午11:29:32 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public class BaseException extends RuntimeException {

	private FailDesc failDesc;
	static final long serialVersionUID = 1L;
	
    public FailDesc getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(FailDesc failDesc) {
		this.failDesc = failDesc;
	}

	public BaseException(String message) {
    	super(message);
    }
    
    public BaseException(FailDesc failDesc) {
    	super(failDesc.getDesc());
    	this.failDesc = failDesc;
    }

    public BaseException(Throwable e) {
    	super(e);
    }

    public BaseException(String message, Throwable cause) {
    	super(message, cause);
    }
}
  