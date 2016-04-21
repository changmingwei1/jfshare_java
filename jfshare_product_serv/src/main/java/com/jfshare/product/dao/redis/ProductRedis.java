package com.jfshare.product.dao.redis;

import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductSku;

import java.util.Map;
import java.util.Set;

/**
 * Created by Lenovo on 2015/11/21.
 */
public interface ProductRedis {

    public Product getProductAll(String productId);

    public Map<String, String> getProductBase(String productId);

    public String getProductAttr(String productId);

    public String getProductTemp(String productId);

    public ProductSku getProductSku(String productId);

    public ProductSku getProductSkuSingle(String productId, String skuNum);

    public boolean setProductCache(Product product);

    public boolean setProductSkuCache(String productId, ProductSku productSku);

    public String removeProductCache(String productId);

    public String removeProductSkuCache(String productId);

    public String removeProductCacheAll(String productId);
    
    public Boolean setProductListMember(String key,String value, long price);
    
    public long removeProductListMember(String key,String... value);
    
    public Set<String> getProductList(String key);
}
