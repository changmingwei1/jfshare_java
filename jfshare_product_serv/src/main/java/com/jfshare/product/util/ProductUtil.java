package com.jfshare.product.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.finagle.thrift.product.ProductSurvey;
import com.jfshare.finagle.thrift.subject.SubjectNode;
import com.jfshare.finagle.thrift.subject.SubjectTreeResult;
import com.jfshare.product.commons.ProductCommons;
import com.jfshare.product.model.TbProduct;
import com.jfshare.product.model.TbProductHistoryWithBLOBs;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.JsonMapper;
import com.jfshare.utils.PriceUtils;
import com.jfshare.utils.StringUtil;

/**
 * Created by lenovo on 2015/10/9.
 */
public class ProductUtil {
    /**
     * 填充不能自动填充的字段
     * @param product
     * @param tbProduct
     */
    public static void fillAliasProduct(Product product, TbProduct tbProduct) {
        product.setProductId(tbProduct.getId());
        product.setProductName(tbProduct.getName());
    }
	public static void fillAliasProduct(Product product,  TbProductHistoryWithBLOBs tbProductHistoryWithBLOBs) {
		product.setProductId(tbProductHistoryWithBLOBs.getProductId());
		product.setProductName(tbProductHistoryWithBLOBs.getName());
	}
    
    /**
     * 	方法描述:  制作商品列表 缓存 用到的key
     *   
     *  @author  ljx 创建时间 2015年12月5日 下午6:58:32
     */
    public static List<String> buildProductListRK(SubjectTreeResult subjectTreeResult, Integer brandId){
    	List<String> keyList = new ArrayList<String>();
    	if(subjectTreeResult != null && subjectTreeResult.getSubjectNodes() != null && subjectTreeResult.getSubjectNodes().size() > 0){

        	for(SubjectNode subjectNode : subjectTreeResult.getSubjectNodes()){
        		int subjectId = subjectNode.getId();
        		
        		String key1 = ProductCommons.RK_PRODUCT_LIST + subjectId + "_" + brandId + "_create_time DESC";
        		String key2 = ProductCommons.RK_PRODUCT_LIST + subjectId + "_" + brandId + "_cur_price DESC";
        		String key3 = ProductCommons.RK_PRODUCT_LIST + subjectId + "_" + brandId + "_cur_price ASC";
        		String key4 = ProductCommons.RK_PRODUCT_LIST + subjectId + "_0_create_time DESC";
        		String key5 = ProductCommons.RK_PRODUCT_LIST + subjectId + "_0_cur_price DESC";
        		String key6 = ProductCommons.RK_PRODUCT_LIST + subjectId + "_0_cur_price ASC";
        		
        		keyList.add(key1);
        		keyList.add(key2);
        		keyList.add(key3);
        		keyList.add(key4);
        		keyList.add(key5);
        		keyList.add(key6);
        	}
    	}
    	return keyList;
    }
    
    public static long getScore(String key, ProductSurvey productSurvey){
    	if(key.contains("create_time DESC")){
    		String str = productSurvey.getCreateTime();
    		return 0 - DateTimeUtil.strToDateTime(str).getMillis();
    	}
    	if(key.contains("cur_price DESC")){
    		return 0 - Long.parseLong(productSurvey.getCurPrice());
    	}
    	if(key.contains("cur_price ASC")){
    		return Long.parseLong(productSurvey.getCurPrice());
    		
    	}
    	return 0L;
    }

	public static ProductSku getSkuByJson(String skuJson) {
		JSONObject jsonObject = JSONObject.parseObject(skuJson);
		ProductSku productSku = new ProductSku();
		Map<String,ProductSkuItem> productSkuMap = new HashMap<String,ProductSkuItem>();
		int minCurPrice=Integer.MAX_VALUE, maxCurPrice=0, minOrgPrice=Integer.MAX_VALUE,maxOrgPrice=0;
		for(Map.Entry<String , Object> entry : jsonObject.entrySet()){
//			System.out.println(entry.getKey() + "," + entry.getValue());
			JSONObject skuInfo = JSONObject.parseObject(entry.getValue().toString());
			//sku总
			if (StringUtil.isNullOrEmpty(entry.getKey())) {
				BeanUtil.fillBeanData(productSku, jsonObject);
				productSku.setCurPrice(skuInfo.getString("curPrice"));
				productSku.setOrgPrice(skuInfo.getString("orgPrice"));
			} else {
				ProductSkuItem skuItem = new ProductSkuItem();
				BeanUtil.fillBeanData(skuItem, skuInfo);
				skuItem.setCurPrice(skuInfo.getString("curPrice"));
				skuItem.setOrgPrice(skuInfo.getString("orgPrice"));

				productSkuMap.put(entry.getKey(), skuItem);

				int curPrice = PriceUtils.strToInt(skuInfo.getString("curPrice"));
				int orgPrice = PriceUtils.strToInt(skuInfo.getString("orgPrice"));
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

		if (productSkuMap.size() > 0) {
			productSku.setMinCurPrice(PriceUtils.intToStr(minCurPrice));
			productSku.setMaxCurPrice(PriceUtils.intToStr(maxCurPrice));
			productSku.setMinOrgPrice(PriceUtils.intToStr(minOrgPrice));
			productSku.setMaxOrgPrice(PriceUtils.intToStr(maxOrgPrice));
			productSku.setProductSkuMap(productSkuMap);
		}

		return productSku;
	}

	public static void main (String args[]) {
		String json = "{\"\":{\"sellerClassNum\":\"\",\"shelf\":\"\",\"curPrice\":\"0\",\"orgPrice\":\"0\",\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"setShelf\":true,\"setVPicture\":false,\"setSkuName\":false},\"1-1:100-101\":{\"sellerClassNum\":\"\",\"shelf\":\"\",\"curPrice\":\"1999.90\",\"orgPrice\":\"4299.00\",\"skuName\":\"颜色-军绿色:功率-2匹\",\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"setShelf\":true,\"setVPicture\":false,\"setSkuName\":true},\"1-1:100-102\":{\"sellerClassNum\":\"\",\"shelf\":\"\",\"curPrice\":\"1999.90\",\"orgPrice\":\"4299.00\",\"skuName\":\"颜色-军绿色:功率-3匹\",\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"setShelf\":true,\"setVPicture\":false,\"setSkuName\":true},\"1-2:100-101\":{\"sellerClassNum\":\"\",\"shelf\":\"\",\"curPrice\":\"999\",\"orgPrice\":\"4299.00\",\"vPicture\":\"3AA68C82FD2A4419D1F4F755833A82A3.jpg\",\"skuName\":\"颜色-天蓝色:功率-2匹\",\"vpicture\":\"3AA68C82FD2A4419D1F4F755833A82A3.jpg\",\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":true},\"1-2:100-102\":{\"sellerClassNum\":\"\",\"shelf\":\"\",\"curPrice\":\"1999.90\",\"orgPrice\":\"4299.00\",\"vPicture\":\"3AA68C82FD2A4419D1F4F755833A82A3.jpg\",\"skuName\":\"颜色-天蓝色:功率-3匹\",\"vpicture\":\"3AA68C82FD2A4419D1F4F755833A82A3.jpg\",\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":true}}";
		System.out.println(getSkuByJson(json));
	}
}
