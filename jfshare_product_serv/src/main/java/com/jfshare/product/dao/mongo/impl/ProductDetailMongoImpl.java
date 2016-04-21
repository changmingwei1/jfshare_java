package com.jfshare.product.dao.mongo.impl;

import com.jfshare.product.dao.mongo.IProductDetailMongo;
import com.jfshare.product.model.manual.ProductDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lenovo on 2015/10/20.
 */
@Repository
public class ProductDetailMongoImpl implements IProductDetailMongo {

    private Logger logger = LoggerFactory.getLogger(ProductDetailMongoImpl.class);

    @Autowired
    private MongoOperations mongoTemplate;

    @Override
    public String add(ProductDetail obj) {
        //obj  updatestatus
        obj.setUpdateStatus(1);
        logger.info(">>>> add ---- add product detail to mongoDB --- productId : {}", obj.getId());
        this.mongoTemplate.save(obj);
        logger.info("<<<< add ---- add product detail to mongoDB --- productId : {} --- result : success!", obj.getId());
        return obj.getId();
    }

    @Override
    public void update(ProductDetail obj) {
        logger.info(">>>> update ---- update product detail to mongoDB --- productId : {}", obj.getId());
        Criteria criteria = Criteria.where("id").is(obj.getId());
        Query query = new Query(criteria);
        Update update = Update.update("detailContent", obj.getDetailContent())
                .set("updateStatus", 1 /*obj.getUpdateStatus()*/)
                .set("sellerId", obj.getSellerId())
                .set("updateCount", obj.getUpdateCount())
                .set("productId", obj.getProductId())
                .set("createTime", obj.getCreateTime())
                .set("updateTime", obj.getUpdateTime());

        mongoTemplate.updateFirst(query, update, obj.getClass());

    }

//    @Override
//    public void updateStatus(String id) {
//        logger.info(">>>> updateStatus ---- update product detail to mongoDB --- productId : {}", id);
//        Criteria criteria = Criteria.where("id").is(id);
//        Query query = new Query(criteria);
//        Update update = Update.update("updateStatus", 99);
//
//        mongoTemplate.updateFirst(query, update, ProductDetail.class);
//    }

    @Override
    public ProductDetail getById(String id) {
        logger.info(">>>> getById ---- get product detail from mongoDB --- id : {}", id);
        return this.mongoTemplate.findById(id, ProductDetail.class);
    }

    @Override
    public ProductDetail getByProductId(String productId) {
        logger.info(">>>> getById ---- get product detail from mongoDB --- productId : {}", productId);
        Query query = new Query(
                Criteria.where("productId").is(productId)
        );
        return this.mongoTemplate.findOne(query, ProductDetail.class);
    }

    @Override
    public void delete(String id) {
        logger.info(">>>> delete ---- delete product detail from mongoDB --- productId : {}", id);
        ProductDetail obj = new ProductDetail();
        obj.setId(id);
        this.mongoTemplate.remove(obj);
    }

//    @Override
//    @SuppressWarnings("static-access")
//    public long queryDetailDealFailCount(Integer sellerId) {
//        Criteria criteria = new Criteria().andOperator(Criteria.where(ProductDetail.SELLER_ID).is(sellerId),
//                Criteria.where(ProductDetail.UPDATE_STATUS).in(new Integer[] { 0, 2, 4, 5 }),
//                Criteria.where(ProductDetail.UPDATE_TIME).gte(this.getProductDetailDealFailStartTime()));
//        Query query = new Query(criteria);
//        return this.mongoTemplate.count(query, ProductDetail.class);
//    }

//    @SuppressWarnings("static-access")
//    @Override
//    public Set<String> queryDetailDealFailRecord(Integer sellerId, int start,
//                                                 int count) {
//        Criteria criteria = new Criteria().andOperator(Criteria.where(ProductDetail.SELLER_ID).is(sellerId),
//                Criteria.where(ProductDetail.UPDATE_STATUS).in(new Integer[] { 0, 2, 4, 5 }),
//                Criteria.where(ProductDetail.UPDATE_TIME).gte(this.getProductDetailDealFailStartTime()));
//        Query query = new Query(criteria);
//
//        query.skip(start)
//                .limit(count)
//                .with(new Sort(new Sort.Order(Sort.Direction.DESC, ProductDetail.UPDATE_TIME)));//按创建时间倒序
//
//        List<ProductDetail> list = this.mongoTemplate.find(query, ProductDetail.class);
//        Set<String> productIdSet = new HashSet<String>();
//        for (ProductDetail model : list) {
////			System.out.println("=====productId:" + model.getProductId() + ", sellerId:" + model.getSellerId() + ", id:" + model.getId() + ", createTime=" + model.getCreateTime());
//            productIdSet.add(model.getProductId());
//
//        }
//        return productIdSet;
//    }

    /**
     * 处理商品详情处理失败开始计算时间
     * @return Date
     */
    private Date getProductDetailDealFailStartTime() {
        Calendar calendar = Calendar.getInstance();
//		String startTime = Configure.runGet("product_detail_fail_recrod_start_time");
//		if (StringUtils.isEmpty(startTime)) {
//			calendar.set(Integer.valueOf(startTime.split("-")[0]),
//					Integer.valueOf(startTime.split("-")[1]),
//					Integer.valueOf(startTime.split("-")[2]));
//		} else {
////			calendar.set(2014, 9, 20);  //从这个时间算起
        calendar.set(2015, 7, 1);  //从这个时间算起
//		}
        return calendar.getTime();
    }

}

