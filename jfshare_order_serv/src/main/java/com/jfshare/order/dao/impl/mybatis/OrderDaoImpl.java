/** 
 * Project Name:order-serv
 * File Name:StockDaoImpl.java 
 * Package Name:com.jfshare.order.dao.impl
 * Date:2014年12月23日下午12:56:29 
 * 
*/  
  
package com.jfshare.order.dao.impl.mybatis;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.express.ExpressInfo;
import com.jfshare.finagle.thrift.order.DeliverInfo;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.order.common.OrderConstant;
import com.jfshare.order.dao.IOrderDao;
import com.jfshare.order.exceptions.DaoManualException;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.order.model.TbOrderRecordExample;
import com.jfshare.order.util.TableNameUtil;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository(value="orderDao")
public class OrderDaoImpl implements IOrderDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Resource(name="sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Resource(name="sqlSessionFactoryRead")
    private SqlSessionFactory sqlSessionFactoryRead;

    @Override
    public OrderModel getOrderBySeller(String orderId, int sellerId) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("sellerId", sellerId);
        map.put("orderTable", TableNameUtil.getOrderNameBySeller(sellerId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameBySeller(sellerId));
        OrderModel orderModel = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModel = sqlSession.selectOne("select_order_detail", map);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return orderModel;
    }

    @Override
    public OrderModel getOrderByUser(String orderId, int userId) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("userId", userId);
        map.put("orderTable", TableNameUtil.getOrderNameByUser(userId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameByUser(userId));
        OrderModel orderModel = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModel = sqlSession.selectOne("select_order_detail", map);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return orderModel;
    }

    @Override
    public int getOrderStatBySeller(int sellerId, OrderQueryConditions conditions) {
        SqlSession sqlSession = null;
        int res = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("sellerId", sellerId);
        map.put("orderTable", TableNameUtil.getOrderNameBySeller(sellerId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameBySeller(sellerId));
        map.put("conditions", conditions);
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            res = sqlSession.selectOne("select_order_stat_count_by_seller", map);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return res;
    }

    @Override
    public int getOrderStatByUser(int userId, OrderQueryConditions conditions) {
        SqlSession sqlSession = null;
        int res = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderTable", TableNameUtil.getOrderNameByUser(userId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameByUser(userId));
        map.put("conditions", conditions);
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            res = sqlSession.selectOne("select_order_stat_count_by_user", map);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return res;
    }

    @Override
    public OrderModel getOrderProfileByUser(int userId, String orderId) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("userId", userId);
        map.put("orderTable", TableNameUtil.getOrderNameByUser(userId));
        OrderModel orderModel = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModel = sqlSession.selectOne("select_order_profile", map);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }

        return orderModel;
    }

    @Override
    public OrderModel getOrderProfileBySeller(int sellerId, String orderId) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("sellerId", sellerId);
        map.put("orderTable", TableNameUtil.getOrderNameBySeller(sellerId));
        OrderModel orderModel = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModel = sqlSession.selectOne("select_order_profile", map);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return orderModel;
    }

    @Override
    public List<OrderModel> getOrderListByUser(int userId, List<String> orderIdList) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<>();
        map.put("orderIds", orderIdList);
        map.put("userId", userId);
        map.put("orderTable", TableNameUtil.getOrderNameByUser(userId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameByUser(userId));
        List<OrderModel> orderModels = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("select_order_list", map);
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
    public List<OrderModel> getOrderStateListByUser(int userId) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderTable", TableNameUtil.getOrderNameByUser(userId));
        List<OrderModel> orderModels = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("select_order_state_list", map);
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
    public List<OrderModel> getOrderStateListBySeller(int sellerId) {
        SqlSession sqlSession = null;
        Map<String, Object> map = new HashMap<>();
        map.put("sellerId", sellerId);
        map.put("orderTable", TableNameUtil.getOrderNameBySeller(sellerId));
        List<OrderModel> orderModels = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("select_order_state_list", map);
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
    public List<OrderModel> queryBatchBySeller(int sellerId, OrderQueryConditions conditions) {
        SqlSession sqlSession = null;
        List<OrderModel> orderModels = null;
        Map<String, Object> map = new HashMap<>();

        int start = (conditions.curPage - 1)*conditions.count;
        int end = conditions.count;
        map.put("start", 0);
        map.put("end", OrderConstant.MAX_BATCH_EXPORT_RECORD);
        map.put("sellerId", sellerId);
        map.put("orderTable", TableNameUtil.getOrderNameBySeller(sellerId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameBySeller(sellerId));
        map.put("conditions", conditions);

        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("select_order_list_by_seller", map);
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
    public List<OrderModel> getSellerOrderBatch(int sellerId, List<String> orderIds) {
        SqlSession sqlSession = null;
        List<OrderModel> orderModels = null;
        Map<String, Object> map = new HashMap<>();
        map.put("orderTable", TableNameUtil.getOrderNameBySeller(sellerId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameBySeller(sellerId));
        map.put("orderIds", orderIds);
        map.put("sellerId", sellerId);
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("select_order_list", map);
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
    public List<OrderModel> getOrderListBySeller(int sellerId, OrderQueryConditions conditions) {
        SqlSession sqlSession = null;
        List<OrderModel> orderModels = null;
        Map<String, Object> map = new HashMap<>();

        int start = (conditions.curPage - 1)*conditions.count;
        int end = conditions.count;
        map.put("start", start);
        map.put("end", end);
        map.put("sellerId", sellerId);
        map.put("orderTable", TableNameUtil.getOrderNameBySeller(sellerId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameBySeller(sellerId));
        map.put("conditions", conditions);

        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("select_order_list_by_seller", map);
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
    public List<OrderModel> getOrderListByUser(int userId, OrderQueryConditions conditions) {
        SqlSession sqlSession = null;
        List<OrderModel> orderModels = null;
        Map<String, Object> map = new HashMap<>();

        int start = (conditions.curPage - 1)*conditions.count;
        int end = conditions.count;
        map.put("start", start);
        map.put("end", end);
        map.put("userId", userId);
        map.put("orderTable", TableNameUtil.getOrderNameByUser(userId));
        map.put("orderInfoTable", TableNameUtil.getOrderInfoNameByUser(userId));
        map.put("conditions", conditions);

        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            orderModels = sqlSession.selectList("select_order_list_by_user", map);
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
    public int updateOrderWithCriteria(OrderModel orderModel, int userType, TbOrderRecordExample example) {

        SqlSession sqlSession = null;
        int res = -1;
        String userTypeName = (userType== BizUtil.USER_TYPE.BUYER.getEnumVal()) ? "买家" : "卖家";

        String tableName = (userType== BizUtil.USER_TYPE.BUYER.getEnumVal()) ? TableNameUtil.getOrderNameByUser(orderModel.getUserId()) : TableNameUtil.getOrderNameBySeller(orderModel.getSellerId());
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("orderTable", tableName);
        if(example == null || CollectionUtils.isEmpty(example.getOredCriteria())) {
            throw new DaoManualException("方法调用参数异常[example或example.getOredCriteria()为空]");
        }

        paraMap.put("_parameter", "1");
        paraMap.put("example", example);
        paraMap.put("record", orderModel);
        logger.info("开始更新" + userTypeName + ", 表[" + tableName + "], data=" + JSON.toJSONString(orderModel));
        try {
            sqlSession = sqlSessionFactory.openSession();
            res = sqlSession.update("updateOrderWithCriteria", paraMap);
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return res;
    }

    @Override
    public int insertOrder(OrderModel orderModel, int userType) {
        if (orderModel == null || orderModel.getTbOrderInfoList() == null || StringUtil.isNullOrEmpty(orderModel.getOrderId()) ||
                StringUtil.isNullOrEmpty(orderModel.getUserId()) || orderModel.getUserId() <= 0 ||
                StringUtil.isNullOrEmpty(orderModel.getSellerId()) || orderModel.getSellerId() <= 0) {
            logger.error("待入库订单信息有误！order=" + orderModel);
            return -1;
        }
        String userTypeName = (userType== BizUtil.USER_TYPE.BUYER.getEnumVal()) ? "买家" : "卖家";

        String tableName = (userType== BizUtil.USER_TYPE.BUYER.getEnumVal()) ? TableNameUtil.getOrderNameByUser(orderModel.getUserId()) : TableNameUtil.getOrderNameBySeller(orderModel.getSellerId());
        String infoTableName = (userType == BizUtil.USER_TYPE.BUYER.getEnumVal()) ? TableNameUtil.getOrderInfoNameByUser(orderModel.getUserId()) : TableNameUtil.getOrderInfoNameBySeller(orderModel.getSellerId());
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("table", tableName);
        paraMap.put("record",orderModel);
        logger.info("开始插入" + userTypeName + "主表[" + tableName + "], data="+ orderModel);
        Integer count = -1;
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            try {
                count = (Integer) sqlSession.insert("insertSelectiveEx_Order", paraMap);
//            logger.info("插入" + userTypeName + "主表[" + tableName + "]成功");
            } catch (DuplicateKeyException e) {
                logger.warn("插入" + userTypeName + "主表[" + tableName + "]主键重复！", e);
                return 2;
            } catch (Exception e) {
                logger.error("插入" + userTypeName + "主表[" + tableName + "]失败！", e);
                throw new RuntimeException("插入" + userTypeName + "主表[" + tableName + "]失败！");
            }

            for (TbOrderInfoRecord orderInfo : orderModel.getTbOrderInfoList()) {
                Map<String, Object> paraMap1 = new HashMap<String, Object>();
                paraMap1.put("table", infoTableName);
                paraMap1.put("record", orderInfo);

                try {
                    count = (Integer) sqlSession.insert("insertSelectiveEx_OrderInfo", paraMap1);
//                logger.info("插入" + userTypeName + "细表[" + infoTableName + "]成功");
                } catch (DuplicateKeyException e) {
                    logger.warn("插入" + userTypeName + "细表[" + infoTableName + "]主键重复！", e);
                    count = 2;
                } catch (Exception e) {
                    logger.error("插入" + userTypeName + "细表[" + infoTableName + "]失败！", e);
                    throw new RuntimeException("插入" + userTypeName + "细表[" + infoTableName + "]失败！");
                }

            }
        } finally {
            try {
                if (sqlSession != null)
                    sqlSession.close();
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }

        logger.info("订单[" + orderModel.getOrderId() +"]入库" + userTypeName + "[" + orderModel.getUserId() + "]表[" + tableName + "," + infoTableName + "]成功");

        return count;
    }

    @Override
    public int batchDeliverOp(int sellerId, List<Order> orderList) {
        for(int i = 0; i < orderList.size(); i++)
        {
            DeliverInfo info = orderList.get(i).getDeliverInfo();

            info.setOrderId(orderList.get(i).getOrderId());

            //updateDeliverInfo(seller, info, orderState);


            OrderModel tbOrder = new OrderModel();
            tbOrder.setOrderId(info.getOrderId());
            if(info.getExpressId() != null && !info.getExpressId().isEmpty()) {
                tbOrder.setExpressNo(info.getExpressNo());
                tbOrder.setExpressId(Integer.parseInt(info.getExpressId()));
                tbOrder.setExpressName(info.getExpressName());
            }
            else  {
                tbOrder.setExpressId(0);
                tbOrder.setExpressNo("");
            }

            tbOrder.setBuyerComment(info.getBuyerComment());
            tbOrder.setSellerComment(info.getSellerComment());
            tbOrder.setReceiverName(info.getReceiverName());
            tbOrder.setReceiverMobile(info.getReceiverMobile());
            tbOrder.setReceiverTele(info.getReceiverTele());
            tbOrder.setProvinceId(info.getProvinceId());
            tbOrder.setCityId(info.getCityId());
            tbOrder.setCountyId(info.getCountyId());
            tbOrder.setReceiverAddress(info.getReceiverAddress());
            tbOrder.setOrderState(OrderConstant.FINISH_DELIVER_STATE);
            tbOrder.setPostCode(info.getPostCode());
            tbOrder.setLastUpdateTime(new DateTime());
            tbOrder.setLastUpdateUserId(sellerId);
            //重新发货时保留上次发货时间
            if(orderList.get(i).getOrderState() != OrderConstant.ORDER_STATE_FINISH_DELIVER)
                tbOrder.setDeliverTime(new DateTime());

            OrderModel orderProfile = this.getOrderProfileBySeller(sellerId, info.getOrderId());

            if(orderProfile == null)
                return -1;

            TbOrderRecordExample example = new TbOrderRecordExample();
            TbOrderRecordExample.Criteria criteria = example.createCriteria();
            criteria.andOrderIdEqualTo(tbOrder.getOrderId());
            criteria.andSellerIdEqualTo(tbOrder.getSellerId());
            criteria.andUserIdEqualTo(tbOrder.getUserId());
            int ret = this.updateOrderWithCriteria(tbOrder, BizUtil.USER_TYPE.BUYER.getEnumVal(),example);
            if (ret <= 0) {
                throw new RuntimeException("batchDeliverOp，更新买家表失败, 更新返回："+ ret);
            }

            ret = this.updateOrderWithCriteria(tbOrder, BizUtil.USER_TYPE.SELLER.getEnumVal(),example);
            if (ret <= 0) {
                throw new RuntimeException("batchDeliverOp，更新卖家表失败, 更新返回："+ ret);
            }
        }


        return  orderList.size();
    }
}
  