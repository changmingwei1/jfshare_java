package com.jfshare.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductDetailParam;
import com.jfshare.finagle.thrift.product.ProductOpt;
import com.jfshare.finagle.thrift.product.ProductRetParam;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.finagle.thrift.product.ProductSurvey;
import com.jfshare.finagle.thrift.product.ProductSurveyQueryParam;
import com.jfshare.finagle.thrift.product.ProductSurveyResult;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.subject.SubjectTreeResult;
import com.jfshare.product.commons.ProductCommons;
import com.jfshare.product.dao.es.ESProductInfoDao;
import com.jfshare.product.dao.mongo.IProductDetailMongo;
import com.jfshare.product.dao.mysql.IProductDao;
import com.jfshare.product.dao.mysql.IProductHistoryDao;
import com.jfshare.product.dao.mysql.pageutil.PageNumUtil;
import com.jfshare.product.dao.redis.BaseRedis;
import com.jfshare.product.dao.redis.ProductRedis;
import com.jfshare.product.dependserv.SubjectClient;
import com.jfshare.product.model.TbProduct;
import com.jfshare.product.model.TbProductHistoryWithBLOBs;
import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.TbProductWithBLOBs;
import com.jfshare.product.model.enums.ProductOptEnum;
import com.jfshare.product.model.manual.ProductDetail;
import com.jfshare.product.model.vo.ESProductInfo;
import com.jfshare.product.service.transaction.ProductTransactionManager;
import com.jfshare.product.util.ConvertUtil;
import com.jfshare.product.util.DateTimeUtil;
import com.jfshare.product.util.FailCode;
import com.jfshare.product.util.IdCreator;
import com.jfshare.product.util.ProductUtil;
import com.jfshare.product.util.asynclog.LogCommon;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.JsonMapper;
import com.jfshare.utils.PriceUtils;
import com.jfshare.utils.StringUtil;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service
public class ProductSvcImpl implements com.jfshare.product.service.IProductSvc {
    private Logger logger = LoggerFactory.getLogger(ProductSvcImpl.class);
    @Autowired
    private IProductDao productDaoImpl;

    @Autowired
    private ProductTransactionManager productTransactionManager;

    @Autowired
    private IProductDetailMongo productDetailMongo;

    @Autowired
    private IProductHistoryDao productHistoryDao;

    @Autowired
    private ProductRedis productRedis;
    
    @Autowired
    private BaseRedis baseRedis;

    @Autowired
    private SubjectClient subjectClient;
    
    @Autowired
    private ESProductInfoDao eSProductInfoDao;
    
    @Override
    public Product queryProduct(String productId, ProductRetParam param) {
        Product product = new Product();
        product.setProductId(productId);
        if (param.getBaseTag() == 1 || param.getSkuTemplateTag() == 1 || param.getAttributeTag() == 1) {
            //query from cache
            product = productRedis.getProductAll(productId);
            if(product == null) {

                //query from db
                product = new Product();
                TbProductWithBLOBs tbProductWithBLOBs = productDaoImpl.queryProductWithBLOBs(productId);
                if (tbProductWithBLOBs != null) {
                    Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbProductWithBLOBs);
                    BeanUtil.fillBeanData(product, stringObjectMap);
                    ProductUtil.fillAliasProduct(product, tbProductWithBLOBs);
                    product.setSkuTemplate(tbProductWithBLOBs.getSkuTemplate());
                    product.setAttribute(tbProductWithBLOBs.getAttribute());

                    //reload cache
                    this.productRedis.removeProductCache(productId);
                    this.productRedis.setProductCache(product);
                } else {
                    product = null;
                }
            }
        }
        if (product != null && param.getSkuTag() == 1) {
           product.setProductSku(this.getProductSku(productId));
        }

        return product;
    }


    /**
     * 	方法描述:  商品概要信息查询
     *
     *	@see com.jfshare.product.service.IProductSvc#productSurveyQuery(com.jfshare.finagle.thrift.product.ProductSurveyQueryParam)
     *  @author : ljx 创建时间 2015年10月19日 下午9:23:31
     */
	@Override
	public ProductSurveyResult productSurveyQuery(ProductSurveyQueryParam param) {

		ProductSurveyResult productSurveyResult = new ProductSurveyResult();
		
		List<ProductSurvey> productSurveyList = new ArrayList<ProductSurvey>();
		
        String key = ProductCommons.RK_PRODUCT_LIST + param.getSubjectId() + "_" + param.getBrandId() + "_" + param.getSort();

        //买家查询，先查看redis
        Set<String> productSet  = productRedis.getProductList(key);

        if(productSet == null || productSet.size() == 0){
            //数据库汇总查询

            //卖家查询，直接查询数据库
            productSurveyList = productDaoImpl.productSurveyQuery(param);
            //将结果存入redis中

            for(ProductSurvey productSurvey : productSurveyList){
                String value = JSON.toJSONString(productSurvey);
                productRedis.setProductListMember(key, value, ProductUtil.getScore(key, productSurvey));
            }

        }else{
            // 买家查询，不需要查询数据库

            // 转换查询结果
            for(String productStr : productSet){
                ProductSurvey productSurvey = JSON.parseObject(productStr, ProductSurvey.class);
                productSurveyList.add(productSurvey);
            }

        }

        //按照分页的参数自己处理list的获取范围
        int fromIndex = param.getPagination().getNumPerPage()*(param.getPagination().getCurrentPage()-1);
        int toIndex = param.getPagination().getNumPerPage()*param.getPagination().getCurrentPage();

        if(fromIndex < 0) fromIndex = 0;
        if(toIndex > productSurveyList.size()) toIndex = productSurveyList.size();
        productSurveyResult.setProductSurveyList(productSurveyList.subList(fromIndex, toIndex));

        //设置总记录数
        param.getPagination().setTotalCount(productSurveyList.size());
        productSurveyResult.setPagination(PageNumUtil.setPageNumCount(param.getPagination()));
		/*
		for(ProductSurvey productSurvey : productSurveyList){
			
			ProductSurvey productSurvey = new ProductSurvey();
			
			Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbProduct);
	        BeanUtil.fillBeanData(productSurvey, stringObjectMap);
	        
			productSurvey.setProductId(tbProduct.getId());
			productSurvey.setProductName(tbProduct.getName());
			productSurvey.setImgUrl(tbProduct.getImgKey());
			
	        productSurveyList.add(productSurvey);
		}
		*/
		
		return productSurveyResult;
	}

    @Override
    public ProductSurveyResult productSurveyQueryFromDB(ProductSurveyQueryParam param) {
        ProductSurveyResult productSurveyResult = new ProductSurveyResult();

        List<ProductSurvey> productSurveyList = new ArrayList<ProductSurvey>();

        //卖家中心、管理中心查询，直接查询数据库
        productSurveyList = productDaoImpl.productSurveyQueryPage(param);
        //写如结果中
        productSurveyResult.setProductSurveyList(productSurveyList);
        productSurveyResult.setPagination(PageNumUtil.setPageNumCount(param.getPagination()));
        return productSurveyResult;
    }

    @Override
    public ProductSku getProductSku(String productId) {
        //query from cache
        ProductSku productSku = this.productRedis.getProductSku(productId);
        if(productSku != null) {
            return productSku;
        }

        //query from db
        productSku = new ProductSku();
        List<TbProductSku> tbProductSkus = productDaoImpl.getProductSku(productId);
        if (tbProductSkus == null || tbProductSkus.size() == 0) {
            return null;
        }

//        Map<String,ProductSkuItem> productSkuMap = new HashMap<String,ProductSkuItem>();
        List<ProductSkuItem> productSkuItems = new ArrayList<ProductSkuItem>();
        int minCurPrice=Integer.MAX_VALUE, maxCurPrice=0, minOrgPrice=Integer.MAX_VALUE,maxOrgPrice=0;
        for(TbProductSku item : tbProductSkus) {
            Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(item);
            //sku总
            if (StringUtil.isNullOrEmpty(item.getSkuNum())) {
                BeanUtil.fillBeanData(productSku, stringObjectMap);
                productSku.setCurPrice(PriceUtils.intToStr(item.getCurPrice()));
                productSku.setOrgPrice(PriceUtils.intToStr(item.getOrgPrice()));
            } else {
                ProductSkuItem skuItem = new ProductSkuItem();
                BeanUtil.fillBeanData(skuItem, stringObjectMap);
                skuItem.setCurPrice(PriceUtils.intToStr(item.getCurPrice()));
                skuItem.setOrgPrice(PriceUtils.intToStr(item.getOrgPrice()));

//                productSkuMap.put(item.getSkuNum(), skuItem);
                productSkuItems.add(skuItem);

                if (minCurPrice > item.getCurPrice()) {
                    minCurPrice = item.getCurPrice();
                }
                if (maxCurPrice < item.getCurPrice()) {
                    maxCurPrice = item.getCurPrice();
                }
                if (minOrgPrice > item.getOrgPrice()) {
                    minOrgPrice = item.getOrgPrice();
                }
                if (maxOrgPrice < item.getOrgPrice()) {
                    maxOrgPrice = item.getOrgPrice();
                }
            }
        }
        if (productSkuItems.size() > 0) {
            productSku.setMinCurPrice(PriceUtils.intToStr(minCurPrice));
            productSku.setMaxCurPrice(PriceUtils.intToStr(maxCurPrice));
            productSku.setMinOrgPrice(PriceUtils.intToStr(minOrgPrice));
            productSku.setMaxOrgPrice(PriceUtils.intToStr(maxOrgPrice));
            productSku.setSkuItems(productSkuItems);
        }

        //reload cache
        this.productRedis.removeProductSkuCache(productId);
        this.productRedis.setProductSkuCache(productId, productSku);
        return productSku;
    }

    @Override
    public ProductSku getProductHotSku(String productId, String skuNum) {
        //query from cache
        ProductSku productSku = productRedis.getProductSkuSingle(productId, skuNum);
        if(productSku != null) {
            return productSku;
        }

        //query from db
        TbProductSku tbProductSku = productDaoImpl.getProductHotSku(productId, skuNum);
        if (tbProductSku == null) {
            return null;
        }
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbProductSku);
        productSku = new ProductSku();
        BeanUtil.fillBeanData(productSku, stringObjectMap);
        productSku.setCurPrice(PriceUtils.intToStr(tbProductSku.getCurPrice()));
        productSku.setOrgPrice(PriceUtils.intToStr(tbProductSku.getOrgPrice()));

        //load cache
        this.productRedis.removeProductSkuCache(productId);
        ProductSku cacheProductSku = this.getProductSku(productId);
        this.productRedis.setProductSkuCache(productId, cacheProductSku);

        return productSku;
    }

    @Override
    public FailDesc updateProductState(ProductOpt productOpt) throws InterruptedException {
        TbProduct tbProduct = productDaoImpl.queryProduct(productOpt.getProductId());
        if(tbProduct==null) {
            return FailCode.PRODUCT_NOT_EXISTS_ERROR;
        }

        if(productOpt.getCurState() != tbProduct.getActiveState()) {
            return FailCode.PRODUCT_STATE_ERROR;
        }
        
        // -- 删除redis缓存列表中的此数据
        ProductSurveyQueryParam productSurveyQueryParam = new ProductSurveyQueryParam();
        productSurveyQueryParam.setProductId(productOpt.getProductId());
        ProductSurvey productSurvey = productDaoImpl.productSurveyQueryById(productSurveyQueryParam);
        
        Integer activeState = productSurvey.getActiveState();
        
        if(activeState == ProductCommons.PRODUCT_STATE_ONSELL){
            SubjectTreeResult subjectTreeResult = subjectClient.getSuperTree(productSurvey.getSubjectId());
            List<String> keyList = ProductUtil.buildProductListRK(subjectTreeResult, productSurvey.getBrandId());
    		for(String key : keyList){
    	        productRedis.removeProductListMember(key, JSON.toJSONString(productSurvey));
    		}
        }
        
        //操作数据库
        TbProductHistoryWithBLOBs tbProductHistoryWithBLOBs = productHistoryDao.getById(tbProduct.getProductSnapshootId());
        productTransactionManager.updateProductState(tbProductHistoryWithBLOBs, productOpt);

        //清空缓存
        productRedis.removeProductCacheAll(productOpt.getProductId());
        
        // -- 插入reids缓存列表中的 此数据
        //操作数据库之后重新查询此数据
        productSurvey = productDaoImpl.productSurveyQueryById(productSurveyQueryParam);
        if(productOpt.getActiveState() == ProductCommons.PRODUCT_STATE_ONSELL){
            SubjectTreeResult subjectTreeResult = subjectClient.getSuperTree(productSurvey.getSubjectId());
            List<String> keyList = ProductUtil.buildProductListRK(subjectTreeResult, productSurvey.getBrandId());
    		for(String key : keyList){
    			Set<String> productSet = productRedis.getProductList(key);
    			if(productSet != null && productSet.size() > 0){
    				productRedis.setProductListMember(key, JSON.toJSONString(productSurvey), ProductUtil.getScore(key, productSurvey));
    			}
    		}
    		/*//es中增加
    		ESProductInfo eSProductInfo = new ESProductInfo();
    		eSProductInfo.setProductId(productSurvey.getProductId());
    		eSProductInfo.setName(productSurvey.getProductName() + " " + productSurvey.getViceName());
    		eSProductInfoDao.insert(eSProductInfo);*/
        }else{
        	//es 中删除
        	/*eSProductInfoDao.delete(productSurvey.getProductId());*/
        }
        
        //放入异步队列
        com.jfshare.finagle.thrift.manager.ProductOpt logProductOpt = ConvertUtil.productOpt2LogProductOpt(productOpt);
        LogCommon.asyncProductOptLogsQueue.put(logProductOpt);
        return null;
    }

    @Override
    public String queryProductDetail(ProductDetailParam param) {
        ProductDetail productDetail = null;
        if (!StringUtil.isNullOrEmpty(param.getDetailKey())) {
            productDetail = productDetailMongo.getById(param.getDetailKey().trim());
        } else if (!StringUtil.isNullOrEmpty(param.getProductId())) {
            productDetail = productDetailMongo.getByProductId(param.getProductId().trim());
        }

        return productDetail == null ? null : productDetail.getDetailContent();
    }

    @Override
    public String addProduct(Product product) {
        //1.生成商品ID
        String productId = IdCreator.getProductId();
        product.setProductId(productId);
        product.setActiveState(ProductCommons.PRODUCT_STATE_CREATE);   //设置默认下架状态

        //2.保存商品详情
        ProductDetail pdb = new ProductDetail();
        pdb.setSellerId(product.getSellerId());
        pdb.setDetailContent(product.getDetailContent());
        pdb.setProductId(productId);
        String strDetailKey = this.productDetailMongo.add(pdb);

        //3.通过productTransactionManager更新mysql
        product.setDetailKey(strDetailKey);

        TbProductWithBLOBs tbProductWithBLOBs = ConvertUtil.convertTbProductWithBLOBs(product);
        productTransactionManager.addProduct(tbProductWithBLOBs, product.getProductSku());

        //4.异步写商品日志
        com.jfshare.finagle.thrift.manager.ProductOpt productOpt = createProductOpt(product);
        try {
            LogCommon.asyncProductOptLogsQueue.put(productOpt);
        } catch (InterruptedException e) {
            logger.error("productOpt放入asyncProductOptLogsQueue失败，{}", productOpt);
        }

        return productId;
    }

    private com.jfshare.finagle.thrift.manager.ProductOpt createProductOpt(Product product) {
        com.jfshare.finagle.thrift.manager.ProductOpt productOpt = new com.jfshare.finagle.thrift.manager.ProductOpt();
        productOpt.setProductId(product.getProductId());
        productOpt.setActiveState(ProductCommons.PRODUCT_STATE_CREATE);
        productOpt.setCreateTime(DateTimeUtil.DateTimeToStr(DateTimeUtil.currentDateTime()));
        productOpt.setDesc(ProductOptEnum.getDescByState(ProductCommons.PRODUCT_STATE_CREATE));
        productOpt.setOperatorType(1);
        productOpt.setOperatorId(String.valueOf(product.getSellerId()));
        return productOpt;
    }

    @Override
    public String updateProduct(Product product) {
        //1.生成商品ID
        product.setActiveState(ProductCommons.PRODUCT_STATE_CREATE);   //设置默认下架状态

        //2.保存商品详情
        ProductDetail pdb = new ProductDetail();
        pdb.setSellerId(product.getSellerId());
        pdb.setDetailContent(product.getDetailContent());
        pdb.setProductId(product.getProductId());
        String strDetailKey = this.productDetailMongo.add(pdb);

        //3.通过productTransactionManager更新mysql
        product.setDetailKey(strDetailKey);

        // -- 删除redis缓存列表中的此数据
        ProductSurveyQueryParam productSurveyQueryParam = new ProductSurveyQueryParam();
        productSurveyQueryParam.setProductId(product.getProductId());
        ProductSurvey productSurvey = productDaoImpl.productSurveyQueryById(productSurveyQueryParam);
        
        Integer activeState = productSurvey.getActiveState();
        
        if(activeState == ProductCommons.PRODUCT_STATE_ONSELL){
            SubjectTreeResult subjectTreeResult = subjectClient.getSuperTree(productSurvey.getSubjectId());
            List<String> keyList = ProductUtil.buildProductListRK(subjectTreeResult, productSurvey.getBrandId());
    		for(String key : keyList){
    	        productRedis.removeProductListMember(key, JSON.toJSONString(productSurvey));
    		}
        }
        
        //数据库修改操作
        TbProductWithBLOBs tbProductWithBLOBs = ConvertUtil.convertTbProductWithBLOBs(product);
        productTransactionManager.updateProduct(tbProductWithBLOBs, product.getProductSku());

        //4.清空相关缓存信息
        productRedis.removeProductCacheAll(product.getProductId());
        
        // -- 插入reids缓存列表中的 此数据
        //操作数据库之后重新查询此数据
        productSurvey = productDaoImpl.productSurveyQueryById(productSurveyQueryParam);
        if(activeState == ProductCommons.PRODUCT_STATE_ONSELL){
            SubjectTreeResult subjectTreeResult = subjectClient.getSuperTree(productSurvey.getSubjectId());
            List<String> keyList = ProductUtil.buildProductListRK(subjectTreeResult, productSurvey.getBrandId());
    		for(String key : keyList){
    			Set<String> productSet = productRedis.getProductList(key);
    			if(productSet != null && productSet.size() > 0){
    				productRedis.setProductListMember(key, JSON.toJSONString(productSurvey), ProductUtil.getScore(key, productSurvey));
    			}
    		}
        }
		
        return product.getProductId();
    }

    
    /**
     * 	方法描述:  首页楼层商品信息查询
     *
     *	@see com.jfshare.product.service.IProductSvc#queryFloorProduct(int)
     *  @author : ljx 创建时间 2015年11月23日 下午11:26:31
     */
	@Override
	public ProductSurveyResult queryFloorProduct(int floorId) {

		ProductSurveyResult productSurveyResult = new ProductSurveyResult();
		
		List<ProductSurvey> productSurveyList = new ArrayList<ProductSurvey>();
		
		Map<String, String> productId2ProductSurveyMap  = baseRedis.getMapAll(ProductCommons.FLOOR_PRODUCT+floorId);
		
		if(productId2ProductSurveyMap != null && productId2ProductSurveyMap.size() > 0){
			// 从redis中获取到了数据
			for(Map.Entry<String, String> entry : productId2ProductSurveyMap.entrySet()){
				
				productSurveyList.add(JsonMapper.toObject(entry.getValue(), ProductSurvey.class) );
			}
			
		}else{
			// 没有从redis获取到数据
			productSurveyList = productDaoImpl.queryFloorProduct(floorId);
			if(productSurveyList !=null && productSurveyList.size() > 0){
				productId2ProductSurveyMap = new HashMap<String, String>();
				for(ProductSurvey productSurvey : productSurveyList){
					productId2ProductSurveyMap.put(productSurvey.getProductId(), JsonMapper.toJson(productSurvey));
				}
				baseRedis.putMap(ProductCommons.FLOOR_PRODUCT+floorId, productId2ProductSurveyMap, 77);
			}
		}
		
		//写如结果中
		productSurveyResult.setProductSurveyList(productSurveyList);
		//productSurveyResult.setPagination(PageNumUtil.setPageNumCount(param.getPagination()));
		
		return productSurveyResult;
	}

    @Override
    public Product queryProductSnap(String snapId, ProductRetParam param) {
        Product product = new Product();
        product.setProductSnapshootId(snapId);
        if (param.getBaseTag() == 1 || param.getSkuTemplateTag() == 1 || param.getAttributeTag() == 1 || param.getSkuTag() == 1) {
            TbProductHistoryWithBLOBs tbProductHistoryWithBLOBs = productHistoryDao.getById(snapId);
            if (tbProductHistoryWithBLOBs != null) {
                    Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbProductHistoryWithBLOBs);
                    BeanUtil.fillBeanData(product, stringObjectMap);
                    ProductUtil.fillAliasProduct(product, tbProductHistoryWithBLOBs);
                    product.setSkuTemplate(tbProductHistoryWithBLOBs.getSkuTemplate());
                    product.setAttribute(tbProductHistoryWithBLOBs.getAttribute());
                    product.setProductSku(ProductUtil.getSkuByJson(tbProductHistoryWithBLOBs.getProductSku()));
                } else {
                    product = null;
                }
        }

        return product;
    }

    @Override
    public List<ProductSurvey> queryProductBatch(int fromType, List<String> productIds) {
        List<ProductSurvey> productSurveyList = null;
        if(fromType == 1) {
            //query from db
            ProductSurveyQueryParam param = new ProductSurveyQueryParam();
            param.setProductIdList(productIds);
            productSurveyList = productDaoImpl.productSurveyQuery(param);
        } else if(fromType == 2) {
            //query from cache
        	//redis中获取
        	List<String> productSurveyJsonStrList= baseRedis.getMMap(ProductCommons.RK_PRODUCTSURVEY_MAP, productIds.toArray(new String[productIds.size()]));
        	
        	//如果redis为空，则从数据库获取，并存入redis中
        	if(CollectionUtils.isEmpty(productSurveyJsonStrList)){
        		//数据库中获取，并组织返回参数
        		ProductSurveyQueryParam param = new ProductSurveyQueryParam();
                param.setProductIdList(productIds);
                productSurveyList = productDaoImpl.productSurveyQuery(param);
                //组织map参数，放入缓存中
                Map<String,String> map = new HashMap<String,String>();
                for(ProductSurvey productSurvey : productSurveyList){
                	map.put(productSurvey.getProductId(), JSON.toJSONString(productSurvey));
                }
                baseRedis.putMap(ProductCommons.RK_PRODUCTSURVEY_MAP, map);
        	}else{

        		productSurveyList = new ArrayList<ProductSurvey>();
        		for(String productSurveyJsonStr : productSurveyJsonStrList){
        			productSurveyList.add(JSON.parseObject(productSurveyJsonStr, ProductSurvey.class));
        		}
        	}
        }
        return productSurveyList;
    }

    @Override
    public ProductSku getProductHotSku(String productId, int storehouseId, String skuNum) {
        //query from cache
        ProductSku productSku = productRedis.getProductSkuSingle(productId, storehouseId, skuNum);
        if(productSku != null) {
            return productSku;
        }

        //query from db
        TbProductSku tbProductSku = productDaoImpl.getProductHotSku(productId, storehouseId, skuNum);
        if (tbProductSku == null) {
            return null;
        }
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbProductSku);
        productSku = new ProductSku();
        BeanUtil.fillBeanData(productSku, stringObjectMap);
        productSku.setCurPrice(PriceUtils.intToStr(tbProductSku.getCurPrice()));
        productSku.setOrgPrice(PriceUtils.intToStr(tbProductSku.getOrgPrice()));

        //load cache
        this.productRedis.removeProductSkuCache(productId);
        ProductSku cacheProductSku = this.getProductSku(productId);
        this.productRedis.setProductSkuCache(productId, cacheProductSku);

        return productSku;
    }
}
