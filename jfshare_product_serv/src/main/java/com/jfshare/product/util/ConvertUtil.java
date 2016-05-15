package com.jfshare.product.util;

import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductCard;
import com.jfshare.finagle.thrift.product.ProductCardStatistics;
import com.jfshare.finagle.thrift.product.ProductCardView;
import com.jfshare.finagle.thrift.product.ProductOpt;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.TbProductHistoryWithBLOBs;
import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.TbProductWithBLOBs;
import com.jfshare.product.model.enums.ProductOptEnum;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;
import com.jfshare.product.model.vo.Page;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        tbProductWithBLOBs.setStorehouseIds(product.getStorehouseIds());
        tbProductWithBLOBs.setPostageId(product.getPostageId());

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
        List<TbProductSku> tbProductSkus = new ArrayList<TbProductSku>();
        for(ProductSkuItem productSkuItem : productSku.getSkuItems()) {
            TbProductSku tbProductSku = new TbProductSku();
            tbProductSku.setProductId(productId);
            tbProductSku.setSkuNum(productSkuItem.getSkuNum());
            tbProductSku.setShelf(productSkuItem.getShelf());
            tbProductSku.setCurPrice(PriceUtils.strToInt(productSkuItem.getCurPrice()));
            tbProductSku.setOrgPrice(PriceUtils.strToInt(productSkuItem.getOrgPrice()));
            tbProductSku.setSellerClassNum(productSkuItem.getSellerClassNum());
            tbProductSku.setvPicture(productSkuItem.getVPicture());
            tbProductSku.setSkuName(productSkuItem.getSkuName());
            tbProductSku.setWeight(productSkuItem.getWeight());
            tbProductSku.setRefPrice(PriceUtils.strToInt(productSkuItem.getRefPrice()));
            tbProductSku.setStorehouseId(productSkuItem.getStorehouseId());
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

    public static ProductCard tbProductCard2Thrift(TbProductCard tbProductCard) {
        if(tbProductCard == null) {
            return null;
        }
        ProductCard productCard = new ProductCard();
        productCard.setProductId(tbProductCard.getProductId());
        productCard.setCardNumber(tbProductCard.getCardNumber());
        productCard.setPassword(tbProductCard.getPassword());
        productCard.setSkuNum(tbProductCard.getSkuNum());
        return productCard;
    }

    public static ProductCardView tbProductCard2ViewThrift(TbProductCard tbProductCard) {
        if(tbProductCard == null) {
            return null;
        }
        ProductCardView productCardView = new ProductCardView();
        productCardView.setProductId(tbProductCard.getProductId());
        productCardView.setCardNumber(tbProductCard.getCardNumber());
        productCardView.setPassword(tbProductCard.getPassword());
        productCardView.setState(tbProductCard.getState());
        productCardView.setSkuNum(tbProductCard.getSkuNum());
        return productCardView;
    }

    public static TbProductCard thrift2TbProductCard(ProductCard productCard) {
        TbProductCard tbProductCard = new TbProductCard();
        tbProductCard.setSellerId(productCard.getSellerId());
        tbProductCard.setProductId(productCard.getProductId());
        tbProductCard.setCardNumber(productCard.getCardNumber());
        tbProductCard.setPassword(productCard.getPassword());
        tbProductCard.setSkuNum(productCard.getSkuNum());
        return tbProductCard;
    }


    public static Pagination page2Pagination(Page page) {
        Pagination pagination = new Pagination();
        pagination.setPageNumCount(page.getPageCount());
        pagination.setTotalCount(page.getTotal());
        return pagination;
    }

    public static ProductCardStatistics productCardStatisticsModel2Thrift(ProductCardStatisticsModel model) {

        ProductCardStatistics statistics = new ProductCardStatistics();
        statistics.setProductId(model.getProductId());
        statistics.setProductName(model.getProductName());
        statistics.setSkuNum(model.getSkuNum());
        statistics.setTotal(model.getTotal());
        statistics.setUsedNum(model.getTotal() - model.getUnusedNum());
        statistics.setUnusedNum(model.getUnusedNum());
        statistics.setCreateTime(DateTimeUtil.DateTimeToStr(model.getCreateTime()));
        return statistics;
    }
    
}
