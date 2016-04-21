/** 
 * Project Name:manager-serv
 * File Name:StockDao.java 
 * Package Name:com.jfshare.manager.dao.impl.redis
 * Date:2014年12月23日下午12:54:55 
 * 
*/  
  
package com.jfshare.manager.dao;

import com.jfshare.finagle.thrift.manager.QueryConditions;
import com.jfshare.manager.model.TbProductOperator;

import java.util.List;

/** 
 * ClassName:StockDao <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2014年12月23日 下午12:54:55 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public interface IProductOptDao {

	public List<TbProductOperator> getProductOperators(QueryConditions conditions);

    int insert(TbProductOperator tbProductOperator);
}
  