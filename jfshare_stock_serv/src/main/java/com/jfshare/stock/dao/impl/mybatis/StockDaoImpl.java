/** 
 * Project Name:stock-serv
 * File Name:StockDaoImpl.java 
 * Package Name:com.jfshare.stock.dao.impl
 * Date:2014年12月23日下午12:56:29 
 * 
*/  
  
package com.jfshare.stock.dao.impl.mybatis;

import com.jfshare.stock.dao.IStockDao;
import com.jfshare.stock.model.StockLockModel;
import com.jfshare.stock.model.TbProductStock;
import com.jfshare.stock.model.TbProductStockExample;
import com.jfshare.stock.model.mappers.TbProductStockMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/** 
 * ClassName:StockDaoImpl <br/> 
 * Function: FUNCTION. <br/> 
 * Reason:   ADD REASON. <br/> 
 * Date:     2014年12月23日 下午12:56:29 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
@Repository(value="stockDao")
public class StockDaoImpl implements IStockDao {

    @Resource(name="sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

	@Autowired
	private TbProductStockMapper tbProductStockMapper;
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED) 
	public List<TbProductStock> getStockByProductId(String productId){
		TbProductStockExample e = new TbProductStockExample();
        TbProductStockExample.Criteria c = e.createCriteria();
        c.andProductIdEqualTo(productId);
        List<TbProductStock> ls = this.tbProductStockMapper.selectByExample(e);
        return ls;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int insertStockInfoBatch(List<TbProductStock> list) {
		
		int insertCount = 0;
		for(TbProductStock tbProductStock : list) {
			insertCount += tbProductStockMapper.insert(tbProductStock);
		}
		return insertCount;
	}

    @Override
    public int updateStockByChange(StockLockModel model) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int rCount = 0;
        try {
            rCount = sqlSession.update(UPDATE_STOCK_COUNT_BY_CHANGE, model);
        } finally {
            sqlSession.close();
        }
        return rCount;
    }

    @Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteStockByProductId(String productId) {
		TbProductStockExample example = new TbProductStockExample();
		example.createCriteria().andProductIdEqualTo(productId);
		return tbProductStockMapper.deleteByExample(example);
	}

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void insertStockWithDelete(String productId, List<TbProductStock> list) {
        this.deleteStockByProductId(productId);
        this.insertStockInfoBatch(list);
    }

    private final static String UPDATE_STOCK_COUNT_BY_CHANGE = "update_stock_count_by_change";
}
  