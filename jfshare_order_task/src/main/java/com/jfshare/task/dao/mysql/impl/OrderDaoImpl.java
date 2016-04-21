package com.jfshare.task.dao.mysql.impl;

import com.jfshare.task.model.manual.OrderModel;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
@Repository
public class OrderDaoImpl implements com.jfshare.task.dao.mysql.IOrderDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);
    @Resource(name="sqlSessionFactoryRead")
    private SqlSessionFactory sqlSessionFactoryRead;

    @Override
    public List<OrderModel> selectConfirmOrder(int index, int pageLimit) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderTable", "tb_order_user_" + index);
        map.put("orderState", "4");
        List<OrderModel> orderModels = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("selectByExampleEx_Order", map);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }

        return orderModels;
    }

    @Override
    public List<OrderModel> selectCancelOrder(int index, int pageLimit) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderTable", "tb_order_user_" + index);
        map.put("orderState", "1");
        List<OrderModel> orderModels = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("selectByExampleEx_Order", map);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }

        return orderModels;
    }
}
