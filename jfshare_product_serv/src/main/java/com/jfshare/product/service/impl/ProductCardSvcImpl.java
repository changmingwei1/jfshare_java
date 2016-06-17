package com.jfshare.product.service.impl;

import com.jfshare.finagle.thrift.product.AldCaptchaItem;
import com.jfshare.finagle.thrift.product.DayAldCaptchaItem;
import com.jfshare.product.commons.ProductCommons;
import com.jfshare.product.dao.mysql.IProductCardDao;
import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;
import com.jfshare.product.service.IProductCartSvc;
import com.jfshare.product.util.FileUtil;
import com.jfshare.ridge.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
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
    public boolean importProductCard(int sellerId, String path) throws Exception {

        // TODO: 2016/5/22  dsdfsdf sd 
        // 读取文件中的数据
        HSSFWorkbook hssfWorkbook = null;
        File localFile = null;
        try {
            // 下载文件到本地
            String localFolder = localPath + sellerId + "/";
            String fileName = "" + System.currentTimeMillis() + ".xls";
            boolean flag = FileUtil.downloadFile(path, localFolder, fileName);
            if (!flag) {
                return flag;
            }
            localFile = new File(localFolder + fileName);
            Date now = new Date();
            InputStream is = new FileInputStream(localFile);
            hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                TbProductCard productCard = new TbProductCard();
                productCard.setSellerId(sellerId);
                productCard.setProductId(row.getCell(0).getStringCellValue());
                productCard.setSkuNum(row.getCell(1).getStringCellValue());
                productCard.setCardNumber(row.getCell(2).getStringCellValue());
                productCard.setPassword(row.getCell(3).getStringCellValue());
                productCard.setCreateTime(now);
                // 添加卡密信息
                this.productCardDao.add(productCard);

            }
        } finally {
            // TODO: 2016/5/28 删除文件
            if (localFile != null) {
                localFile.delete();
            }
        }

        return true;
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
    public int queryProductCardViewListCount(Map queryMap) {
        return this.productCardDao.queryProductCardViewListCount(queryMap);
    }

    @Override
    public List<TbProductCard> queryProductCardViewList(Map queryMap) {
        return this.productCardDao.queryProductCardViewList(queryMap);
    }

    @Override
    public int useProductCard(TbProductCard productCard) {
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
