/** 
 * Project Name:stock-serv
 * File Name:StockDao.java 
 * Package Name:com.jfshare.stock.dao.impl.redis
 * Date:2014年12月23日下午12:54:55 
 * 
*/  
  
package com.jfshare.stock.dao;

import com.jfshare.stock.model.StockLockModel;
import com.jfshare.stock.model.TbProductStock;

import java.util.List;
import java.util.Map;

/** 
 * ClassName:StockDao <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2014年12月23日 下午12:54:55 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public interface IStockDao {

	public List<TbProductStock> getStockByProductId(String productId);
	
	public int insertStockInfoBatch(List<TbProductStock> list);
	
	public int updateStockByChange(StockLockModel model);
	
	public int deleteStockByProductId(String productId);

    public void insertStockWithDelete(String productId, List<TbProductStock> list);
}
  