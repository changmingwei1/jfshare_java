/** 
 * Project Name:manager-serv
 * File Name:StockException.java 
 * Package Name:com.jfshare.manager.exceptions
 * Date:2015年1月14日上午11:29:32 
 * 
*/  
  
package com.jfshare.manager.exceptions;

import com.jfshare.finagle.thrift.result.FailDesc;

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

	private FailDesc failDesc;
	static final long serialVersionUID = 1L;

    public FailDesc getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(FailDesc failDesc) {
		this.failDesc = failDesc;
	}

	public DataConvertException(String message) {
    	super(message);
    }

    public DataConvertException(FailDesc failDesc) {
    	super(failDesc.getDesc());
    	this.failDesc = failDesc;
    }

    public DataConvertException(Throwable e) {
    	super(e);
    }

    public DataConvertException(String message, Throwable cause) {
    	super(message, cause);
    }
}
  