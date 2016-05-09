package com.jfshare.product.util;

import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2015/11/22.
 */
public class ValidateUtil {

    public static List<FailDesc> productBasicValidate(Product product) {
        List<FailDesc> failDescs = new ArrayList<FailDesc>();
        if(product == null || product.getProductSku() == null) {
            failDescs.add(FailCode.PARAM_ERROR);
            return failDescs;
        }

        if(StringUtils.isBlank(product.getProductName())) {
            failDescs.add(FailCode.PRODUCT_VALIDATE_PRODUCTNAME);
        }

        ProductSku productSku = product.getProductSku();
        if(CollectionUtils.isEmpty(productSku.getSkuItems())) {
            if(PriceUtils.strToInt(productSku.getOrgPrice()) <= 0
                    || PriceUtils.strToInt(productSku.getCurPrice()) <= 0
                    || PriceUtils.strToInt(productSku.getOrgPrice()) < PriceUtils.strToInt(productSku.getCurPrice())){
                failDescs.add(FailCode.PRODUCT_VALIDATE_PRICE);
            }
        } else {
            for(ProductSkuItem skuItem : productSku.getSkuItems()) {
                if(PriceUtils.strToInt(skuItem.getOrgPrice()) <= 0
                        || PriceUtils.strToInt(skuItem.getCurPrice()) <= 0
                        || PriceUtils.strToInt(skuItem.getOrgPrice()) < PriceUtils.strToInt(skuItem.getCurPrice())){
                    failDescs.add(FailCode.PRODUCT_VALIDATE_PRICE);
                    break;
                }
            }
        }

        if(StringUtils.isBlank(product.getImgKey())
                ||product.getImgKey().split(",").length==0
                ||StringUtils.isBlank(product.getImgKey().split(",")[0])) {
            failDescs.add(FailCode.PRODUCT_VALIDATE_MAINIMG);
        }

        if(StringUtils.isBlank(product.getDetailContent())
                ||product.getImgKey().split(",").length==0
                ||StringUtils.isBlank(product.getImgKey().split(",")[0])) {
            failDescs.add(FailCode.PRODUCT_VALIDATE_DETAIL);
        }

        return failDescs;
    }
}
