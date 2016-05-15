package com.jfshare.product.dao.redis.impl;

import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.product.dao.redis.BaseRedis;
import com.jfshare.product.dao.redis.ConstRedis;
import com.jfshare.product.dao.redis.ProductRedis;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.JsonMapper;
import com.jfshare.utils.PriceUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lenovo on 2015/11/22.
 */
@Service(value = "productRedis")
public class ProductRedisImpl implements ProductRedis{

    private final static Logger logger = LoggerFactory.getLogger(ProductRedisImpl.class);

    @Autowired
    private BaseRedis baseRedis;

    @Override
    public Product getProductAll(String productId) {
        Map<String, String> productMap = baseRedis.getMapAll(ConstRedis.CACHE_PRODUCT_PREFIX + productId);
        Product product = null;
        if(MapUtils.isNotEmpty(productMap)) {
            product = new Product();
            BeanUtil.fillBeanDataStringMap(product, productMap);
        }
        return product;
    }

    @Override
    public Map<String, String> getProductBase(String productId) {
        return null;
    }

    @Override
    public String getProductAttr(String productId) {
        return baseRedis.getMap(ConstRedis.CACHE_PRODUCT_SKU_PREFIX + productId, ConstRedis.CACHE_PRODUCT_FIELD_ATTR);
    }

    @Override
    public String getProductTemp(String productId) {
        return baseRedis.getMap(ConstRedis.CACHE_PRODUCT_SKU_PREFIX + productId, ConstRedis.CACHE_PRODUCT_FIELD_TEMP);
    }

    @Override
    public ProductSku getProductSku(String productId) {
        Map<String, String> cacheSkuMap = baseRedis.getMapAll(ConstRedis.CACHE_PRODUCT_SKU_PREFIX + productId);
        if(MapUtils.isEmpty(cacheSkuMap)) {
            return null;
        }
        ProductSku productSku = new ProductSku();
        int minCurPrice=Integer.MAX_VALUE, maxCurPrice=0, minOrgPrice=Integer.MAX_VALUE,maxOrgPrice=0;
//        Map<String, ProductSkuItem> productSkuMap = new HashMap<String, ProductSkuItem>();
        List<ProductSkuItem> skuItems = new ArrayList<ProductSkuItem>();
        for(String key : cacheSkuMap.keySet()) {
            if ("".endsWith(key)) {
                productSku = JsonMapper.toObject(cacheSkuMap.get(key), ProductSku.class);
            } else {
                ProductSkuItem productSkuItem = JsonMapper.toObject(cacheSkuMap.get(key), ProductSkuItem.class);
//                productSkuMap.put(key, productSkuItem);
                skuItems.add(productSkuItem);
                int curPrice = PriceUtils.strToInt(productSkuItem.getCurPrice());
                int orgPrice = PriceUtils.strToInt(productSkuItem.getOrgPrice());

                if (minCurPrice > curPrice) {
                    minCurPrice = curPrice;
                }
                if (maxCurPrice < curPrice) {
                    maxCurPrice = curPrice;
                }
                if (minOrgPrice > orgPrice) {
                    minOrgPrice = orgPrice;
                }
                if (maxOrgPrice < orgPrice) {
                    maxOrgPrice = orgPrice;
                }
            }
        }

        if (skuItems.size() > 0) {
            productSku.setMinCurPrice(PriceUtils.intToStr(minCurPrice));
            productSku.setMaxCurPrice(PriceUtils.intToStr(maxCurPrice));
            productSku.setMinOrgPrice(PriceUtils.intToStr(minOrgPrice));
            productSku.setMaxOrgPrice(PriceUtils.intToStr(maxOrgPrice));
            productSku.setSkuItems(skuItems);
        }
        return productSku;
    }

    @Override
    public ProductSku getProductSkuSingle(String productId, String skuNum) {
        String productSkuItemJson = baseRedis.getMap(ConstRedis.CACHE_PRODUCT_SKU_PREFIX + productId, skuNum);
        if(StringUtils.isBlank(productSkuItemJson)) {
            return null;
        }
        ProductSkuItem productSkuItem = JsonMapper.toObject(productSkuItemJson, ProductSkuItem.class);
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(productSkuItem);
        ProductSku productSku = new ProductSku();
        BeanUtil.fillBeanData(productSku, stringObjectMap);
        productSku.setSkuNum(skuNum);
        return productSku;
    }

    @Override
    public ProductSku getProductSkuSingle(String productId, int storehouseId, String skuNum) {
        String productSkuItemJson = baseRedis.getMap(ConstRedis.CACHE_PRODUCT_SKU_PREFIX + productId, storehouseId + ":" + skuNum);
        if(StringUtils.isBlank(productSkuItemJson)) {
            return null;
        }
        ProductSkuItem productSkuItem = JsonMapper.toObject(productSkuItemJson, ProductSkuItem.class);
       /* Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(productSkuItem);
        ProductSku productSku = new ProductSku();
        BeanUtil.fillBeanData(productSku, stringObjectMap);*/
        ProductSku productSku = new ProductSku();
        productSku.addToSkuItems(productSkuItem);
        productSku.setSkuNum(skuNum);
        return productSku;
    }

    @Override
    public boolean setProductCache(Product product) {
        String productId = product.getProductId();
        Map<String, String> productMap = BeanUtil.transThrift2StringMap(product);
        return baseRedis.putMap(ConstRedis.CACHE_PRODUCT_PREFIX + productId, productMap);
    }

    @Override
    public boolean setProductSkuCache(String productId, ProductSku productSku) {
//        Map<String, ProductSkuItem> skuItemMap = productSku.getProductSkuMap();
        List<ProductSkuItem> productSkuItems = productSku.getSkuItems();
        Map<String, String> cacheSkuMap = new HashMap<String, String>();
        if(CollectionUtils.isEmpty(productSkuItems)) {
            //无sku商品
            cacheSkuMap.put("", JsonMapper.toJsonNotNull(productSku));
        } else {
            for(ProductSkuItem skuItem : productSkuItems) {
                // 缓存中的key换成  仓库ID:skuNum
                cacheSkuMap.put(skuItem.getStorehouseId() + ":" + skuItem.getSkuNum(), JsonMapper.toJsonNotNull(skuItem));
            }
        }
        return baseRedis.putMap(ConstRedis.CACHE_PRODUCT_SKU_PREFIX + productId, cacheSkuMap);
    }

    @Override
    public String removeProductCache(String productId) {
        return baseRedis.removeKV(ConstRedis.CACHE_PRODUCT_PREFIX + productId);
    }

    public String removeProductCacheAll(String productId) {
        baseRedis.removeKV(ConstRedis.CACHE_PRODUCT_PREFIX + productId);
        baseRedis.removeKV(ConstRedis.CACHE_PRODUCT_SKU_PREFIX + productId);
        return "OK";
    }

    public String removeProductSkuCache(String productId) {
        return baseRedis.removeKV(ConstRedis.CACHE_PRODUCT_SKU_PREFIX + productId);
    }
    
    //存放商品列表
    public Boolean setProductListMember(String key,String value, long price){
    	return baseRedis.putSortSet(key, value, price);
    }
    
    //获取商品列表
    public Set<String> getProductList(String key){
    	return baseRedis.getSortSet(key);
    }

	@Override
	public long removeProductListMember(String key, String... values) {
		return baseRedis.removeSortSet(key, values);
	}

    @Override
    public long addProductClickRate(String productId) {
        long rate = this.baseRedis.incr(ConstRedis.PRODUCT_CLICK_RATE + productId);
        // TODO: 2016/5/15 判断做处理

        return rate;
    }
}
