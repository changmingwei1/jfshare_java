package com.jfshare.product.service.impl;

import com.jfshare.finagle.thrift.product.AldCaptchaItem;
import com.jfshare.finagle.thrift.product.DayAldCaptchaCount;
import com.jfshare.finagle.thrift.product.DayAldCaptchaItem;
import com.jfshare.product.commons.ProductCommons;
import com.jfshare.product.dao.mysql.IProductCardDao;
import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;
import com.jfshare.product.service.IProductCartSvc;
import com.jfshare.product.util.FileUtil;
import com.jfshare.ridge.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/28.
 */
@Service
public class ProductCardSvcImpl implements IProductCartSvc {

    private Logger logger = LoggerFactory.getLogger(ProductCardSvcImpl.class);

    @Resource
    private IProductCardDao productCardDao;

    private String localPath = PropertiesUtil.getProperty(ProductCommons.APP_KEY, "womai_product_import_path");

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int importProductCard(int sellerId, String productId, String path) throws Exception {

        // 读取文件中的数据
        HSSFWorkbook hssfWorkbook = null;
        File localFile = null;
        try {
            // 下载文件到本地
            String localFolder = localPath + sellerId + "/";
            String fileName = "" + System.currentTimeMillis() + ".xls";
            boolean flag = FileUtil.downloadFile(path, localFolder, fileName);
            if (!flag) {
                logger.error("<<<<<<<< importProductCard excel file error !! ---- productId : " + productId + ", path : " + path);
                return 1;
            }
            localFile = new File(localFolder + fileName);
            Date now = new Date();
            InputStream is = new FileInputStream(localFile);
            hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            List<TbProductCard> productCards = new ArrayList<TbProductCard>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                TbProductCard productCard = new TbProductCard();
                productCard.setSellerId(sellerId);
                productCard.setProductId(row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue());
                if (StringUtils.isBlank(productCard.getProductId())) {
                    break;
                }
                // 如果商品ID不是传参，返回失败
                if (!productCard.getProductId().equals(productId)) {
                    logger.error("<<<<<<<< importProductCard productId not same !! ---- productId : " + productId + ", path : " + path);
                    return 2;
                }
                productCard.setSkuNum(row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue());
                productCard.setCardNumber(row.getCell(2).getStringCellValue());
                productCard.setPassword(row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue());
                productCard.setCreateTime(now);
                // 添加卡密信息
                productCards.add(productCard);

            }
            int success = 0;
            for (TbProductCard productCard : productCards) {
                success += this.productCardDao.add(productCard);
            }
            if (success != productCards.size()) {
                logger.error("<<<<<<<< importProductCard have same card !! ---- productId : " + productId + ", path : " + path);
                return 3;
            }
        } finally {
            // 删除文件
            if (localFile != null) {
                localFile.delete();
            }
        }

        return 0;
    }

    @Override
    public List<TbProductCard> getProductCard(Map queryMap) {
        // 存在transactionId对应的卡密
        List<TbProductCard> productCardList = this.productCardDao.selectAvailableCard(queryMap);
        if (CollectionUtils.isNotEmpty(productCardList)) {
            return productCardList;
        }
        int num = Integer.parseInt(queryMap.get("num").toString());
        int lockNum = this.productCardDao.lockProductCard(queryMap);
        if(lockNum == 0) {
            // 没有可用的卡密
            return null;
        }
        // 如果锁定的数量小于获取个数
        if(lockNum < num) {
            // 卡密数量不够，释放锁定的卡密
            this.productCardDao.releaseProductCard(queryMap);
            return null;
        }
        productCardList = this.productCardDao.selectAvailableCard(queryMap);
        return productCardList;
    }

    @Override
    public List<TbProductCard> queryProductCard(Map queryMap) {
        List<TbProductCard> productCardList = this.productCardDao.selectAvailableCard(queryMap);
        return productCardList;
    }

    @Override
    public int statisticsProductCardCount(Map queryMap) {
        return this.productCardDao.statisticsProductCardCount(queryMap);
    }

    @Override
    public List<ProductCardStatisticsModel> statisticsProductCard(Map queryMap) {
        return this.productCardDao.statisticsProductCard(queryMap);

    }

    @Override
    public List<ProductCardStatisticsModel> statisticsSkuProductCard(Map queryMap) {
        return this.productCardDao.statisticsSkuProductCard(queryMap);
    }

    @Override
    public int statisticsSkuProductCardCount(Map queryMap) {
        return this.productCardDao.statisticsSkuProductCardCount(queryMap);
    }

    @Override
    public int queryProductCardViewListCount(Map queryMap) {
        return this.productCardDao.queryProductCardViewListCount(queryMap);
    }

    @Override
    public List<TbProductCard> queryProductCardViewList(Map queryMap) {
        return this.productCardDao.queryProductCardViewList(queryMap);
    }

    @Override
    public TbProductCard useProductCard(TbProductCard productCard) {
        return this.productCardDao.useProductCard(productCard);
    }

    @Override
    public int sellerProductCardCount(Map queryMap) {
        return this.productCardDao.sellerProductCardCount(queryMap);
    }

    @Override
    public List<AldCaptchaItem> sellerProductCardList(Map queryMap) {
        return this.productCardDao.sellerProductCardList(queryMap);
    }

    @Override
    public int sellerProductCardDayAllCount(Map queryMap) {
        return this.productCardDao.sellerProductCardDayAllCount(queryMap);
    }

    @Override
    public List<DayAldCaptchaCount> sellerProductCardDayAllList(Map queryMap) {
        return this.productCardDao.sellerProductCardDayAllList(queryMap);
    }

    @Override
    public int sellerProductCardDayCount(Map queryMap) {
        return this.productCardDao.sellerProductCardDayCount(queryMap);
    }

    @Override
    public List<DayAldCaptchaItem> sellerProductCardDayList(Map queryMap) {
        return this.productCardDao.sellerProductCardDayList(queryMap);
    }

    @Override
    public int getProductCardCount(Map queryMap) {
        return this.productCardDao.getProductCardCount(queryMap);
    }

    @Override
    public int queryProductCardCheckCount(Map queryMap) {
        return this.productCardDao.queryProductCardCheckCount(queryMap);
    }

    @Override
    public List<TbProductCard> queryProductCardCheckList(Map queryMap) {
        return this.productCardDao.queryProductCardCheckList(queryMap);
    }
}
