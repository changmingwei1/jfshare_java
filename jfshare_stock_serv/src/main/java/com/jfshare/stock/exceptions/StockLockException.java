/** 
 * Project Name:stock-serv
 * File Name:StockException.java 
 * Package Name:com.jfshare.stock.exceptions
 * Date:2015年1月14日上午11:29:32 
 * 
*/  
  
package com.jfshare.stock.exceptions;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.stock.model.StockLockModel;

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
public class StockLockException extends StockException {

	private FailDesc failDesc;
    private List<StockLockModel> models;
	static final long serialVersionUID = 1L;
	
    public FailDesc getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(FailDesc failDesc) {
		this.failDesc = failDesc;
	}

    public List<StockLockModel> getModels() {
        return models;
    }

    public void setModels(List<StockLockModel> models) {
        this.models = models;
    }

    public StockLockException(String message) {
    	super(message);
    }
    
    public StockLockException(FailDesc failDesc, List<StockLockModel> models) {
    	super(failDesc.getDesc());
    	this.failDesc = failDesc;
        this.models = models;
    }

    public StockLockException(Throwable e) {
    	super(e);
    }

    public StockLockException(String message, Throwable cause) {
    	super(message, cause);
    }
}
  