/** 
 * Project Name:manager-serv
 * File Name:StockDaoImpl.java 
 * Package Name:com.jfshare.manager.dao.impl
 * Date:2014年12月23日下午12:56:29 
 * 
*/  
  
package com.jfshare.manager.dao.impl.mybatis;

import com.jfshare.finagle.thrift.manager.QueryConditions;
import com.jfshare.manager.dao.IProductOptDao;
import com.jfshare.manager.model.TbProductOperator;
import com.jfshare.manager.model.mappers.TbProductOperatorMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
@Repository(value="productOptDao")
public class ProductOptDaoImpl implements IProductOptDao {

    @Resource(name="sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private TbProductOperatorMapper tbProductOperatorMapper;

    private final static String SELECT_PRODUCT_OPT_RECORDS = "select_product_opt_records";

    @Override
    public List<TbProductOperator> getProductOperators(QueryConditions conditions) {
        SqlSession sqlSession = null;
        List<TbProductOperator> tbProductOperators = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            tbProductOperators = sqlSession.selectList(SELECT_PRODUCT_OPT_RECORDS, conditions);
        } finally {
            if(sqlSession!=null) {
                sqlSession.close();
            }
        }
        return tbProductOperators;
    }

    @Override
    public int insert(TbProductOperator tbProductOperator) {
        return tbProductOperatorMapper.insert(tbProductOperator);
    }
}
  