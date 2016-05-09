package com.jfshare.product.service.transaction;

import com.jfshare.finagle.thrift.product.ProductOpt;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.product.dao.mysql.IProductDao;
import com.jfshare.product.dao.mysql.IProductHistoryDao;
import com.jfshare.product.dao.mysql.IProductSkuDao;
import com.jfshare.product.model.TbProductHistoryWithBLOBs;
import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.TbProductWithBLOBs;
import com.jfshare.product.util.ConvertUtil;
import com.jfshare.product.util.IdCreator;
import com.jfshare.utils.JsonMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lenovo on 2015/10/20.
 */
@Service
public class ProductTransactionManager {

    private static final Logger logger = LoggerFactory.getLogger(ProductTransactionManager.class);

    @Autowired
    private IProductHistoryDao productHistoryDao;

    @Autowired
    private IProductSkuDao productSkuDao;

    @Autowired
    private IProductDao productDao;

    @Transactional(propagation= Propagation.REQUIRED)
    public void addProduct(TbProductWithBLOBs tbProductWithBLOBs, ProductSku productSku) {
        tbProductWithBLOBs.setCreateUserId(tbProductWithBLOBs.getSellerId());
        tbProductWithBLOBs.setLastUpdateId(tbProductWithBLOBs.getSellerId());
        tbProductWithBLOBs.setCreateTime(new DateTime().toDate());
        //1.插入快照信息
        TbProductHistoryWithBLOBs tbProductHistoryWithBLOBs = ConvertUtil.convertToTbProductHistoryWithBLOBs(tbProductWithBLOBs);
        ProductSkuItem productSkuItem = new ProductSkuItem();
        productSkuItem.setCurPrice(productSku.getCurPrice());
        productSkuItem.setOrgPrice(productSku.getOrgPrice());
        productSkuItem.setSellerClassNum(productSku.getSellerClassNum());
        productSkuItem.setShelf(productSku.getShelf());
        productSkuItem.setSkuNum(productSku.getSkuNum() == null ? "" : productSku.getSkuNum());
        productSkuItem.setVPicture(productSku.getVPicture());
        productSku.addToSkuItems(productSkuItem);
        tbProductHistoryWithBLOBs.setProductSku(JsonMapper.toJsonNotNull(productSku.getSkuItems()));
        tbProductHistoryWithBLOBs.setProductSnapshootId(tbProductWithBLOBs.getId());
        productHistoryDao.add(tbProductHistoryWithBLOBs);

        //2.插入sku信息
        List<TbProductSku> tbProductSkus = ConvertUtil.convertTbProductSkus(productSku, tbProductWithBLOBs.getId());
        productSkuDao.addProductSku(tbProductSkus);

        //4.插入商品信息
        tbProductWithBLOBs.setProductSnapshootId(tbProductWithBLOBs.getId());
        productDao.addProduct(tbProductWithBLOBs);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void updateProduct(TbProductWithBLOBs tbProduct, ProductSku productSku) {
        tbProduct.setCreateUserId(tbProduct.getSellerId());
        tbProduct.setLastUpdateId(tbProduct.getSellerId());
        //1.插入快照信息
        TbProductHistoryWithBLOBs tbProductHistoryWithBLOBs = ConvertUtil.convertToTbProductHistoryWithBLOBs(tbProduct);
        ProductSkuItem productSkuItem = new ProductSkuItem();
        productSkuItem.setCurPrice(productSku.getCurPrice());
        productSkuItem.setOrgPrice(productSku.getOrgPrice());
        productSkuItem.setSellerClassNum(productSku.getSellerClassNum());
        productSkuItem.setShelf(productSku.getShelf());
        productSkuItem.setSkuNum(productSku.getSkuNum() == null ? "" : productSku.getSkuNum());
        productSkuItem.setVPicture(productSku.getVPicture());
        productSku.addToSkuItems(productSkuItem);
        tbProductHistoryWithBLOBs.setProductSku(JsonMapper.toJsonNotNull(productSku.getSkuItems()));
        tbProductHistoryWithBLOBs.setProductSnapshootId(IdCreator.getSnapshotId());
        productHistoryDao.add(tbProductHistoryWithBLOBs);

        //2.插入sku信息
        List<TbProductSku> tbProductSkus = ConvertUtil.convertTbProductSkus(productSku, tbProduct.getId());
        productSkuDao.delSkuByProductId(tbProductHistoryWithBLOBs.getProductId());
        productSkuDao.addProductSku(tbProductSkus);

        //4.插入商品信息
        tbProduct.setProductSnapshootId(tbProductHistoryWithBLOBs.getProductSnapshootId());
        productDao.updateProduct(tbProduct);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void updateProductState(TbProductHistoryWithBLOBs tbProductHistory, ProductOpt productOpt) {
        tbProductHistory.setProductSnapshootId(IdCreator.getSnapshotId());
        tbProductHistory.setActiveState(productOpt.getActiveState());
        tbProductHistory.setLastUpdateTime(null);
        //1.插入快照信息
        productHistoryDao.add(tbProductHistory);

        //2.修改商品信息
        TbProductWithBLOBs tbProduct = new TbProductWithBLOBs();
        tbProduct.setId(tbProductHistory.getProductId());
        tbProduct.setProductSnapshootId(tbProductHistory.getProductSnapshootId());
        tbProduct.setActiveState(productOpt.getActiveState());
        tbProduct.setLastUpdateTime(new DateTime().toDate());
        productDao.updateProductState(tbProduct, productOpt.curState);
    }
}
