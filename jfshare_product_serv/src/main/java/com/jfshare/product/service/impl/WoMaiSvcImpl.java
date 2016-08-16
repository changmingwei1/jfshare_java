package com.jfshare.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.product.commons.ProductCommons;
import com.jfshare.product.dao.mongo.IProductDetailMongo;
import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.TbThirdPartyProductExample;
import com.jfshare.product.model.TbThirdPartyProductSnapshotWithBLOBs;
import com.jfshare.product.model.TbThirdPartyProductWithBLOBs;
import com.jfshare.product.model.manual.ProductDetail;
import com.jfshare.product.model.manual.WoMaiError;
import com.jfshare.product.model.mapper.TbThirdPartyProductMapper;
import com.jfshare.product.model.mapper.TbThirdPartyProductSnapshotMapper;
import com.jfshare.product.model.mapper.manual.ManualTbThirdPartyProductMapper;
import com.jfshare.product.model.mapper.manual.ManualTbThirdPartyProductSnapshotMapper;
import com.jfshare.product.service.IWoMaiSvc;
import com.jfshare.product.util.FileUtil;
import com.jfshare.product.util.HttpUtils;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.JsonMapper;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/5/16.
 */
@Service
public class WoMaiSvcImpl implements IWoMaiSvc {

    private Logger logger = LoggerFactory.getLogger(WoMaiSvcImpl.class);

    @Resource
    private IProductDetailMongo productDetailMongo;

    @Resource
    private TbThirdPartyProductMapper tbThirdPartyProductMapper;

    @Resource
    private TbThirdPartyProductSnapshotMapper tbThirdPartyProductSnapshotMapper;

    @Resource
    private ManualTbThirdPartyProductMapper manualTbThirdPartyProductMapper;

    @Resource
    private ManualTbThirdPartyProductSnapshotMapper manualTbThirdPartyProductSnapshotMapper;

    private String localPath = PropertiesUtil.getProperty(ProductCommons.APP_KEY, "womai_product_import_path");

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean importProduct(int sellerId, String path) {

        Product product = new Product();

        // 读取文件中的数据
        HSSFWorkbook hssfWorkbook = null;
        try {
            // 下载文件到本地
            localPath = localPath + sellerId + "/";
            String fileName = "womai" + System.currentTimeMillis() + ".xls";
            boolean flag = FileUtil.downloadFile(path, localPath, fileName);
            if (!flag) {
                return flag;
            }

            Date now = new Date();
            InputStream is = new FileInputStream(new File(localPath + fileName));
            hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            // 读取数据
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            }
        } catch (Exception e) {
            logger.error("<<<<<<<< importProduct error !! --- sellerId : " + sellerId + ", path : " + path, e);
            return false;
        }

        // 获取商品sku

        // 获取商品详情

        // 获取商品状态

        // 获取商品图片，并上传

        // 保存商品
        return false;
    }

    @Scheduled(cron="0 0 0,12 * * ? ")   //每天0点和12点更新
    @Override
    public void syncWoMaiProduct() {
        // 获取商品池pageNum
        List<String> pageNums = this.getItemPageNum();
        for (String pageNum : pageNums) {
            // 获取每个商品池内的商品
            List<String> woMaiIds = this.getItemList(pageNum);
            for (String woMaiId : woMaiIds) {
                TbThirdPartyProductWithBLOBs product = this.getProduct(woMaiId);
                // 保存商品
                this.saveWoMaiProduct(product);
            }
        }
    }

    @Override
    public TbThirdPartyProductWithBLOBs getProduct(String woMaiId) {

        TbThirdPartyProductWithBLOBs productWithBLOBs = new TbThirdPartyProductWithBLOBs();

        productWithBLOBs.setThirdPartyIdentify(1);
        productWithBLOBs.setThirdPartyProductId(woMaiId);
        // 获取商品sku
        this.getStock(woMaiId, productWithBLOBs);

        // 获取商品价格
        this.getPrice(woMaiId, productWithBLOBs);

        // 获取商品详情
        this.getItemDetail(woMaiId, productWithBLOBs);

        // 获取商品状态
        this.getItemStatus(woMaiId, productWithBLOBs);

        // 获取商品图片，并上传
        this.getItemImage(woMaiId, productWithBLOBs);

        return productWithBLOBs;
    }

    // TODO: 从配置中获取
    String storeHouseMapping = "54-0,56-100,57-200";

    @Override
    public String getWoMaiWarehouseId(String storehouseId) {
        String storeHouseMapping = this.storeHouseMapping;
        for (String mapping : storeHouseMapping.split(",")) {
            if (storehouseId.equals(mapping.split("-")[0])) {
                return mapping.split("-")[1];
            }
        }
        return null;
    }

    @Override
    public String getStorehouseId(String warehouseId) {
        String storeHouseMapping = this.storeHouseMapping;
        for (String mapping : storeHouseMapping.split(",")) {
            if (warehouseId.equals(mapping.split("-")[1])) {
                return mapping.split("-")[0];
            }
        }
        return null;
    }

    @Override
    public List<String> getAllWoMaiWarehouseId() {
        List<String> woMaiWarehouseIds = new ArrayList<String>();
        String storeHouseMapping = this.storeHouseMapping;
        for (String mapping : storeHouseMapping.split(",")) {
            woMaiWarehouseIds.add(mapping.split("-")[1]);
        }
        return woMaiWarehouseIds;
    }

    @Override
    public List<TbThirdPartyProductWithBLOBs> queryThirdPartyProductList(Map queryMap) {
        return this.manualTbThirdPartyProductMapper.queryThirdPartyProductList(queryMap);
    }

    @Override
    public int queryThirdPartyProductCount(Map queryMap) {
        return this.manualTbThirdPartyProductMapper.queryThirdPartyProductCount(queryMap);
    }

    @Override
    public List<TbThirdPartyProductSnapshotWithBLOBs> queryThirdPartyProductSnapshotList(Map queryMap) {
        return this.manualTbThirdPartyProductSnapshotMapper.queryThirdPartyProductSnapshotList(queryMap);
    }

    @Override
    public int queryThirdPartyProductSnapshotCount(Map queryMap) {
        return this.manualTbThirdPartyProductSnapshotMapper.queryThirdPartyProductSnapshotCount(queryMap);
    }

    private String getWoMaiUrl() {
        // TODO: 2016/5/17 通过配置获取
        String domain = "http://sandbox.womaiapp.com/api/rest";

        return domain;
    }

    private Map<String, String> getHttpParams(String method, Map param) {
        Map<String, String> postParams = new HashMap<String, String>();
        // TODO: 通过配置获取
        String appKey = "160519";
        String appSecret = "a8c5j5nh5g08mpeg401xkspdfjqgx9j6";
        String paramStr = JSON.toJSONString(param);
        StringBuffer sign = new StringBuffer();
        sign.append(method).append(appKey).append(appSecret);
        if(param != null) {
            sign.append(paramStr);
        }

        postParams.put("method", method);
        postParams.put("appKey", appKey);
//        postParams.put("sign", CodeUtil.getMD5Upper(sign.toString()));
        postParams.put("sign", DigestUtils.md5Hex(sign.toString()).toUpperCase());
        if (param != null) {
            postParams.put("param", JSON.toJSONString(param));
        }
        return postParams;
    }

    /**
     * 获取我买在对接平台发布的所有商品池编号
     * @return
     */
    private List<String> getItemPageNum() {
        List<String> pageNumList = new ArrayList<String>();
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itempagenum.get", null);
        try {
            String pageNumJson = HttpUtils.httpPostUTF8(url, httpParam);
            // 调用结果错误
            if (pageNumJson.contains("error_response")) {
                WoMaiError woMaiError = this.getFailInfo(pageNumJson);
                logger.error("<<<<<<<< getItemPageNum error, error info : " + woMaiError.getMemo());
                return new ArrayList<String>();
            }
            JSONObject itemPageNum = JSON.parseObject(pageNumJson);
            JSONArray pageNums = itemPageNum.getJSONArray("itempagenum");
            for (int i = 0; i < pageNums.size(); i++) {
                JSONObject pageNum = pageNums.getJSONObject(i);
                pageNumList.add(pageNum.getString("page_num"));
            }
        } catch (Exception e) {
            logger.error("<<<<<<<< getItemPageNum --- error !", e);
        }
        return pageNumList;
    }

    /**
     * 根据商品池获取商品池内所有商品
     * @param pageNum
     * @return
     */
    private List<String> getItemList(String pageNum) {
        List<String> ids = new ArrayList<String>();
        Map<String, String> param = new HashMap();
        param.put("pageNum", pageNum);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemlist.get", param);
        try {
            String itemsJson = HttpUtils.httpPostUTF8(url, httpParam);
            // 调用结果错误
            if (itemsJson.contains("error_response")) {
                WoMaiError woMaiError = this.getFailInfo(itemsJson);
                logger.error("<<<<<<<< getItemList error, pageNum : " + pageNum + ", error info : " + woMaiError.getMemo());
                return new ArrayList<String>();
            }
            JSONObject dataObject = JSON.parseObject(itemsJson);
            JSONArray itemList = dataObject.getJSONArray("itemlist");
            for (int i = 0; i < itemList.size(); i++) {
                JSONObject item = itemList.getJSONObject(i);
                ids.add(item.getString("itemid"));
            }
        } catch (Exception e) {
            logger.error("<<<<<<<< getItemList --- error, pageNum : " + pageNum, e);
        }

        return ids;
    }

    /**
     * 获取我买网商品详情
     * {
     *    "itemdetail": [
     *        {
     *            "goodsid": "516764",
     *            "weight": "40",
     *            "brandName": "",
     *            "category": "15073",
     *            "goodsname": "MISS	FACE	透明质酸清润焕采眼霜	30g",
     *            "place_production": "杭州",
     *            "goodsbarcode": "6922239920352",
     *            "unit": "个",
     *            "prodescription": "",
     *            "specification": "1*30g",
     *            "pubflag": "1"
     *        }
     *    ]
     * }
     * @param woMaiId
     * @return
     */
    private void getItemDetail(String woMaiId, TbThirdPartyProductWithBLOBs product) {

        Map<String, String> param = new HashMap();
        param.put("skuid", woMaiId);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemdetail.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            // 调用结果错误
            if (detailJson.contains("error_response")) {
                WoMaiError woMaiError = this.getFailInfo(detailJson);
                logger.error("<<<<<<<< getItemDetail error, woMaiId : " + woMaiId + ", error info : " + woMaiError.getMemo());
                return;
            }
            JSONObject itemDetail = JSON.parseObject(detailJson);
            JSONArray details = itemDetail.getJSONArray("itemdetail");

            JSONObject productObject = details.getJSONObject(0);

            // 商品名称
            product.setName(productObject.getString("goodsname"));
            // 商品描述
            ProductDetail pdb = new ProductDetail();
            pdb.setSellerId(ProductCommons.WOMAI_SELLER_ID);
            pdb.setDetailContent(productObject.getString("prodescription"));
            String strDetailKey = this.productDetailMongo.add(pdb);
            product.setDetailKey(strDetailKey);

            // 商家编码
            product.setSellerclassnum(productObject.getString("goodsbarcode"));

            // 商品属性
            List<Map<String, String>> properties = new ArrayList<Map<String, String>>();
            Map<String, String> propertiesMap = new HashMap<String, String>();
            propertiesMap.put("id", "1");
            propertiesMap.put("name", "产地");
            propertiesMap.put("value", productObject.getString("place_production"));
            properties.add(propertiesMap);
            product.setAttribute(JsonMapper.toJson(properties));

            // 商品重量
            List<TbProductSku> productSkus = JsonMapper.toList(product.getSkuTemplate(),TbProductSku.class, ArrayList.class);
            for (TbProductSku productSku : productSkus) {
                productSku.setWeight(productObject.getInteger("weight") / 1000 + "");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取我买在对接平台上发布的所有商品上下架状态
     * {
     *    "itemstatus": [
     *        {
     *            "goodsid": "524955",
     *            "status": "1"
     *        },
     *        {
     *            "goodsid": "522909",
     *            "status": "1"
     *        }
     *    ]
     *    }
     * 注：state1为上架，0为下架。
     * @return
     */
    private void getItemStatus(String woMaiId, TbThirdPartyProductWithBLOBs product) {
        Map param = new HashMap();
        param.put("skuid", woMaiId);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemstatus.get", param);
        try {
            String statusJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemStatus = JSON.parseObject(statusJson);
            JSONArray statusList = itemStatus.getJSONArray("itemstatus");
            // 调用结果错误
            if (statusJson.contains("error_response")) {
                WoMaiError woMaiError = this.getFailInfo(statusJson);
                logger.error("<<<<<<<< getItemStatus error, woMaiId : " + woMaiId + ", error info : " + woMaiError.getMemo());
                return;
            }
            product.setActiveState("1".equals(statusList.getJSONObject(0).getString("status")) ? 300 : 101);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取我买在对接平台发布的商品图片信息
     * {
     *    "itemimage": [
     *      {
     *            "goodsid": "516785",
     *            "image": [
     *                {
     *                "path": "http://ftest.womaiapp.com/upload/601/602/50341/954434/954434_4_pic500_1322.png",
     *                "isprimary": "4"
     *                },
     *                {
     *                "path": "http://ftest.womaiapp.com/upload/601/602/50341/954434/954434_1_pic500_5042.png",
     *                "isprimary": "1"
     *                },
     *                {
     *                "path": "http://ftest.womaiapp.com/upload/601/602/50341/954434/954434_2_pic500_9328.png",
     *                "isprimary": "2"
     *                },
     *                {
     *                "path": "http://ftest.womaiapp.com/upload/601/602/50341/954434/954434_3_pic500_2244.jpg",
     *                "isprimary": "3"
     *                }
     *            ]
     *        },
     *        {
     *            "goodsid": "524955",
     *           "image": [
     *                {
     *                    "path": "http://ftest.womaiapp.com/upload/601/602/50341/954434/954434_4_pic500_1322.png",
     *                    "isprimary": "4"
     *                },
     *                {
     *                    "path": "http://ftest.womaiapp.com/upload/601/602/50341/954434/954434_1_pic500_5042.png",
     *                    "isprimary": "1"
     *                },
     *                {
     *                    "path": "http://ftest.womaiapp.com/upload/601/602/50341/954434/954434_2_pic500_9328.png",
     *                    "isprimary": "2"
     *                },
     *                {
     *                    "path": "http://ftest.womaiapp.com/upload/601/602/50341/954434/954434_3_pic500_2244.jpg",
     *                    "isprimary": "3"
     *                }
     *            ]
     *        }
     *    ]
     * }
     * @return
     */
    private void getItemImage(String woMaiId, TbThirdPartyProductWithBLOBs product) {
        Map param = new HashMap();
        param.put("skuid", woMaiId);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemimage.get", param);
        try {
            String imageJson = HttpUtils.httpPostUTF8(url, httpParam);
            // 调用结果错误
            if (imageJson.contains("error_response")) {
                WoMaiError woMaiError = this.getFailInfo(imageJson);
                logger.error("<<<<<<<< getItemImage error, woMaiId : " + woMaiId + ", error info : " + woMaiError.getMemo());
                return;
            }
            JSONObject imageObject = JSON.parseObject(imageJson);

            JSONArray imageJsonList = imageObject.getJSONArray("itemimage").getJSONObject(0).getJSONArray("image");
            String[] imageList = new String[imageJsonList.size()];
            for (int i = 0; i < imageJsonList.size(); i++) {
                imageList[Integer.parseInt(imageJsonList.getJSONObject(i).getString("isprimary")) - 1] = imageJsonList.getJSONObject(i).getString("path");
            }
            product.setImgKey(JsonMapper.toJson(imageList));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取商品库存信息
     * {
     *   "Inventory": [
     *       {
     *           "skuid": "954018",
     *           "inventory": "0"
     *       }
     *    ]
     * }
     * @return
     */
    private void getStock(String woMaiId, TbThirdPartyProductWithBLOBs product) {
        Map param = new HashMap();
        param.put("skuids", woMaiId);
        String url = this.getWoMaiUrl();
        // 获取我买仓库信息
        List<String> warehouseIds = this.getAllWoMaiWarehouseId();
        Map<String, String> httpParam = this.getHttpParams("womai.inventory.get", param);
        try {

            // 商品sku
            List<TbProductSku> productSkus = new ArrayList<TbProductSku>();
            TbProductSku productSku = new TbProductSku();
            productSku.setSkuNum("");
            productSkus.add(productSku);
            Map<String, Integer> stockInfoMap = new HashMap<String, Integer>();
            List<String> storeHouseIds = new ArrayList<String>();

            // 循环获取每个仓库下的商品库存
            for (String warehouseId : warehouseIds) {
                param.put("warehouseid", warehouseId);
                String stockJson = HttpUtils.httpPostUTF8(url, httpParam);
                // 调用结果错误，继续查询下一个仓库是否有库存
                if (stockJson.contains("error_response")) {
                    WoMaiError woMaiError = this.getFailInfo(stockJson);
                    logger.error("<<<<<<<< getStock error, woMaiId : " + woMaiId + ", warehouseId : " + warehouseId + ", error info : " + woMaiError.getMemo());
                    continue;
                }
                storeHouseIds.add(this.getStorehouseId(warehouseId));
                JSONObject dataObject = JSON.parseObject(stockJson);
                JSONArray stockList = dataObject.getJSONArray("Inventory");
                for (int i = 0; i < stockList.size(); i++) {
                    JSONObject stock = stockList.getJSONObject(i);
                    stockInfoMap.put(this.getStorehouseId(warehouseId), stock.getInteger("inventory"));
                    productSku = new TbProductSku();
                    productSku.setSkuNum("");
                    productSku.setStorehouseId(Integer.parseInt(this.getStorehouseId(warehouseId)));
                    productSkus.add(productSku);
                }
            }
            product.setSkuTemplate(JsonMapper.toJson(productSkus));
            product.setStockInfo(JsonMapper.toJson(stockInfoMap));
            product.setStorehouseIds(StringUtils.join(storeHouseIds, ","));
            int haveStockNum = this.haveStockNum(stockInfoMap);
            // 全区有货
            if (stockInfoMap.size() == 3 && haveStockNum == 3) {
                product.setStockState(1);
            } else if (stockInfoMap.size() == 0 || haveStockNum == 0) {
                // 全区缺货
                product.setStockState(3);
            } else {
                // 部分缺货
                product.setStockState(2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getPrice(String woMaiId, TbThirdPartyProductWithBLOBs product) {

        Map param = new HashMap();
        param.put("skuids", woMaiId);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.price.get", param);
        try {
            String priceJson = HttpUtils.httpPostUTF8(url, httpParam);
            if (priceJson.contains("error_response")) {
                WoMaiError woMaiError = this.getFailInfo(priceJson);
                logger.error("<<<<<<<< getPrice error, woMaiId : " + woMaiId + ", error info : " + woMaiError.getMemo());
                return;
            }
            JSONObject priceObject = JSON.parseObject(priceJson);
            JSONArray priceList = priceObject.getJSONArray("price");

            List<TbProductSku> productSkus = JsonMapper.toList(product.getSkuTemplate(), TbProductSku.class, ArrayList.class);
            int price = PriceUtils.strToInt(priceList.getJSONObject(0).getString("price"));
            for (TbProductSku productSku : productSkus) {
                productSku.setCurPrice(price);
                productSku.setOrgPrice(price);
                productSku.setRefPrice(price);
            }

            product.setSkuTemplate(JsonMapper.toJson(productSkus));
            product.setPrice(price);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WoMaiError getFailInfo(String jsonStr) {
        // 例： {"error_response":{"code":10001,"msg":null,"sub_code":11001,"sub_msg":null,"memo":"查不到该商品信息"}}
        JSONObject errorResponse = JSON.parseObject(jsonStr).getJSONObject("error_response");
        WoMaiError woMaiError = new WoMaiError();
        woMaiError.setCode(errorResponse.getString("code"));
        woMaiError.setMsg(errorResponse.getString("msg"));
        woMaiError.setSubCode(errorResponse.getString("sub_code"));
        woMaiError.setSubMsg(errorResponse.getString("sub_msg"));
        woMaiError.setMemo(errorResponse.getString("memo"));
        return woMaiError;
    }

    public void saveWoMaiProduct(TbThirdPartyProductWithBLOBs productWithBLOBs) {
        // 获取上一次同步的商品信息
        TbThirdPartyProductExample example = new TbThirdPartyProductExample();
        TbThirdPartyProductExample.Criteria criteria = example.createCriteria();
        criteria.andThirdPartyIdentifyEqualTo(productWithBLOBs.getThirdPartyIdentify());
        criteria.andThirdPartyProductIdEqualTo(productWithBLOBs.getThirdPartyProductId());
        try {
            List<TbThirdPartyProductWithBLOBs> dbProductWithBLOBsList = this.tbThirdPartyProductMapper.selectByExampleWithBLOBs(example);
            // 比较价格状态
            if (CollectionUtils.isEmpty(dbProductWithBLOBsList)) {
                // 商品第一次导入
                productWithBLOBs.setPriceState(3);
            } else {
                if (productWithBLOBs.getPrice() == null) {
                    productWithBLOBs.setPrice(0);
                }
                // 上升
                if (productWithBLOBs.getPrice() > dbProductWithBLOBsList.get(0).getPrice()) {
                    productWithBLOBs.setPriceState(1);
                }
                // 下降
                if (productWithBLOBs.getPrice() < dbProductWithBLOBsList.get(0).getPrice()) {
                    productWithBLOBs.setPriceState(2);
                }
                // 持平
                if (productWithBLOBs.getPrice() == dbProductWithBLOBsList.get(0).getPrice()) {
                    productWithBLOBs.setPriceState(3);
                }
            }
            // 保存快照
            TbThirdPartyProductSnapshotWithBLOBs productSnapshotWithBLOBs = new TbThirdPartyProductSnapshotWithBLOBs();
            BeanUtils.copyProperties(productSnapshotWithBLOBs, productWithBLOBs);
            this.tbThirdPartyProductSnapshotMapper.insertSelective(productSnapshotWithBLOBs);
            // 保存商品
            // 商品不存在，插入商品
            Date now = new Date();
            if (CollectionUtils.isEmpty(dbProductWithBLOBsList)) {
                productWithBLOBs.setCreateTime(now);
                productWithBLOBs.setLastUpdateTime(now);
                this.tbThirdPartyProductMapper.insertSelective(productWithBLOBs);
            } else {
                // 商品存在，更新商品
                productWithBLOBs.setLastUpdateTime(now);
                this.tbThirdPartyProductMapper.updateByExampleSelective(productWithBLOBs, example);
            }
        } catch (Exception e) {
            logger.error("<<<<<<<< saveWoMaiProduct error, woMaiId : " + productWithBLOBs.getThirdPartyProductId(), e);
        }
    }

    /**
     * 有库存仓库个数
     * @param map
     * @return
     */
    private int haveStockNum(Map<String, Integer> map) {
        int num = 0;
        for (Integer stock : map.values()) {
            if (stock.intValue() > 0) {
                num ++;
            }
        }
        return num;
    }

}
