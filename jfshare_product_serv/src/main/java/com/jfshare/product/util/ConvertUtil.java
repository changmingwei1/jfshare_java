package com.jfshare.product.util;

import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductOpt;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.product.model.TbProductHistoryWithBLOBs;
import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.TbProductWithBLOBs;
import com.jfshare.product.model.enums.ProductOptEnum;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lenovo on 2015/10/20.
 */
public class ConvertUtil {

    public static TbProductWithBLOBs convertTbProductWithBLOBs(Product product) {
        TbProductWithBLOBs tbProductWithBLOBs = new TbProductWithBLOBs();
        tbProductWithBLOBs.setId(product.getProductId());
        tbProductWithBLOBs.setName(product.getProductName());
        tbProductWithBLOBs.setViceName(product.getViceName());
        tbProductWithBLOBs.setSellerId(product.getSellerId());
        tbProductWithBLOBs.setSubjectId(product.getSubjectId());
        tbProductWithBLOBs.setBrandId(product.getBrandId());
        tbProductWithBLOBs.setImgKey(product.getImgKey());
        tbProductWithBLOBs.setDetailKey(product.getDetailKey());
        tbProductWithBLOBs.setMaxBuyLimit(product.getMaxBuyLimit());
        tbProductWithBLOBs.setActiveState(product.getActiveState());
        tbProductWithBLOBs.setTags(product.getTags());

        //set blobs
        tbProductWithBLOBs.setSkuTemplate(product.getSkuTemplate());
        tbProductWithBLOBs.setAttribute(product.getAttribute());
        tbProductWithBLOBs.setLastUpdateId(product.getSellerId());

        return tbProductWithBLOBs;
    }

    public static TbProductHistoryWithBLOBs convertToTbProductHistoryWithBLOBs(TbProductWithBLOBs tbProductWithBLOBs) {
        TbProductHistoryWithBLOBs tbProductHistoryWithBLOBs = new TbProductHistoryWithBLOBs();
        tbProductHistoryWithBLOBs.setProductId(tbProductWithBLOBs.getId());
        tbProductHistoryWithBLOBs.setName(tbProductWithBLOBs.getName());
        tbProductHistoryWithBLOBs.setViceName(tbProductWithBLOBs.getViceName());
        tbProductHistoryWithBLOBs.setSellerId(tbProductWithBLOBs.getSellerId());
        tbProductHistoryWithBLOBs.setSubjectId(tbProductWithBLOBs.getSubjectId());
        tbProductHistoryWithBLOBs.setBrandId(tbProductWithBLOBs.getBrandId());
        tbProductHistoryWithBLOBs.setImgKey(tbProductWithBLOBs.getImgKey());
        tbProductHistoryWithBLOBs.setDetailKey(tbProductWithBLOBs.getDetailKey());
        tbProductHistoryWithBLOBs.setMaxBuyLimit(tbProductWithBLOBs.getMaxBuyLimit());
        tbProductHistoryWithBLOBs.setActiveState(tbProductWithBLOBs.getActiveState());
        tbProductHistoryWithBLOBs.setTags(tbProductWithBLOBs.getTags());
        //set blobs
        tbProductHistoryWithBLOBs.setSkuTemplate(tbProductWithBLOBs.getSkuTemplate());
        tbProductHistoryWithBLOBs.setAttribute(tbProductWithBLOBs.getAttribute());
        tbProductHistoryWithBLOBs.setLastUpdateId(tbProductWithBLOBs.getSellerId());

        return tbProductHistoryWithBLOBs;
    }

    public static List<TbProductSku> convertTbProductSkus(ProductSku productSku, String productId) {
        Map<String, ProductSkuItem> skuMap = productSku.productSkuMap;
        Set<String> keys = skuMap.keySet();
        List<TbProductSku> tbProductSkus = new ArrayList<TbProductSku>();
        for(String key : keys) {
            TbProductSku tbProductSku = new TbProductSku();
            ProductSkuItem productSkuItem = skuMap.get(key);
            tbProductSku.setProductId(productId);
            tbProductSku.setSkuNum(key);
            tbProductSku.setShelf(productSkuItem.getShelf());
            tbProductSku.setCurPrice(PriceUtils.strToInt(productSkuItem.getCurPrice()));
            tbProductSku.setOrgPrice(PriceUtils.strToInt(productSkuItem.getOrgPrice()));
            tbProductSku.setSellerClassNum(productSkuItem.getSellerClassNum());
            tbProductSku.setvPicture(productSkuItem.getVPicture());
            tbProductSku.setSkuName(productSkuItem.getSkuName());
            tbProductSkus.add(tbProductSku);
        }
        return tbProductSkus;
    }

    public static com.jfshare.finagle.thrift.manager.ProductOpt productOpt2LogProductOpt(ProductOpt productOpt) {
        com.jfshare.finagle.thrift.manager.ProductOpt logProductOpt = new com.jfshare.finagle.thrift.manager.ProductOpt();
        logProductOpt.setProductId(productOpt.getProductId());
        logProductOpt.setOperatorId(productOpt.getOperatorId());
        logProductOpt.setCreateTime(DateTimeUtil.DateTimeToStr(DateTimeUtil.currentDateTime()));
        logProductOpt.setOperatorType(productOpt.getOperatorType());
        String desc = ProductOptEnum.getDescByState(productOpt.getActiveState());
        if(StringUtils.isNotBlank(productOpt.getDesc())) {
            desc += "，详情：" + productOpt.getDesc();
        }
        logProductOpt.setDesc(desc);
        logProductOpt.setActiveState(productOpt.getActiveState());

        return logProductOpt;
    }
    
}
