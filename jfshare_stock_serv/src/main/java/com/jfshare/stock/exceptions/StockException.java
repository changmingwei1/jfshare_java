/** 
 * Project Name:stock-serv
 * File Name:StockException.java 
 * Package Name:com.jfshare.stock.exceptions
 * Date:2015年1月14日上午11:29:32 
 * 
*/  
  
package com.jfshare.stock.exceptions;

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
public class StockException extends RuntimeException {

	private FailDesc failDesc;
	static final long serialVersionUID = 1L;
	
    public FailDesc getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(FailDesc failDesc) {
		this.failDesc = failDesc;
	}

	public StockException(String message) {
    	super(message);
    }
    
    public StockException(FailDesc failDesc) {
    	super(failDesc.getDesc());
    	this.failDesc = failDesc;
    }

    public StockException(Throwable e) {
    	super(e);
    }

    public StockException(String message, Throwable cause) {
    	super(message, cause);
    }
}
  