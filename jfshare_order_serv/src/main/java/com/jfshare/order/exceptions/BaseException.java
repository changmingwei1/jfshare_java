/** 
 * Project Name:order-serv
 * File Name:StockException.java 
 * Package Name:com.jfshare.order.exceptions
 * Date:2015年1月14日上午11:29:32 
 * 
*/  
  
package com.jfshare.order.exceptions;

import com.jfshare.finagle.thrift.result.FailDesc;

import java.util.ArrayList;
import java.util.List;

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

	private List<FailDesc> failDescs;

	static final long serialVersionUID = 1L;

    public List<FailDesc> getFailDescs() {
        return failDescs;
    }

    public void setFailDescs(List<FailDesc> failDescs) {
        this.failDescs = failDescs;
    }

    public BaseException(String message) {
    	super(message);
    }

    public BaseException(FailDesc failDesc) {
        super(failDesc.getDesc());
        failDescs = new ArrayList<>();
        this.failDescs.add(failDesc);
    }

    public BaseException(FailDesc failDesc, Throwable e) {
    	super(failDesc.getDesc(), e);
        failDescs = new ArrayList<>();
    	this.failDescs.add(failDesc);
    }

    public BaseException(List<FailDesc> failDescs) {
        super();
        this.failDescs = failDescs;
    }

    public BaseException(List<FailDesc> failDescs, Throwable e) {
        super(e);
        this.failDescs = failDescs;
    }

    public BaseException(Throwable e) {
    	super(e);
    }

    public BaseException(String message, Throwable cause) {
    	super(message, cause);
    }
}
  