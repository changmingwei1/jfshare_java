package com.jfshare.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.product.commons.ProductCommons;
import com.jfshare.product.model.manual.WoMaiError;
import com.jfshare.product.service.IWoMaiSvc;
import com.jfshare.product.util.CodeUtil;
import com.jfshare.product.util.FileUtil;
import com.jfshare.product.util.HttpUtils;
import com.jfshare.ridge.PropertiesUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.elasticsearch.common.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public void syncWoMaiProduct() {
        // 获取商品池pageNum
        List<String> pageNums = this.getItemPageNum();
        for (String pageNum : pageNums) {
            // 获取每个商品池内的商品
            List<String> productIds = this.getItemList(pageNum);
            for (String productId : productIds) {

                Product product = new Product();
                // 获取商品sku
//                this.getStock();

                // 获取商品价格

                // 获取商品详情

                // 获取商品状态

                // 获取商品图片，并上传

                // 保存商品
            }
        }
    }

    @Override
    public String getWoMaiWarehouseId(String storehouseId) {
        return null;
    }

    @Override
    public String getStorehouseId(String warehouseId) {
        return null;
    }

    private String getWoMaiUrl() {
        // TODO: 2016/5/17 通过配置获取
        String domain = "http://sandbox.womaiapp.com/api/rest";

        return domain;
    }

    private Map<String, String> getHttpParams(String method, Map param) {
        Map<String, String> postParams = new HashMap<String, String>();
        // TODO: 2016/5/17 通过配置获取
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
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemPageNum = JSON.parseObject(detailJson);
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

    private List<String> getItemList(String pageNum) {
        List<String> ids = new ArrayList<String>();
        Map<String, String> param = new HashMap();
        param.put("pageNum", pageNum);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemlist.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject dataObject = JSON.parseObject(detailJson);
            // TODO: 2016/7/26 判断是否返回错误信息

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
    private void getItemDetail(String woMaiId, Product product) {

        Map<String, String> param = new HashMap();
        param.put("skuid", woMaiId);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemdetail.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemDetail = JSON.parseObject(detailJson);
            JSONArray details = itemDetail.getJSONArray("itemdetail");

            JSONObject productObject = details.getJSONObject(0);

            // 商品名称
            product.setProductName(productObject.getString("goodsname"));
            // 商品描述
            product.setDetailContent(productObject.getString("prodescription"));

            for (ProductSkuItem productSkuItem : product.getProductSku().getSkuItems()) {
                productSkuItem.setWeight(productObject.getInteger("weight") / 1000 + "");
            }


            System.out.println(((Map)details.get(0)).get("goodsid"));
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
    private void getItemstatus(String woMaiId, Product product) {
        Map param = new HashMap();
        param.put("skuid", woMaiId);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemstatus.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemDetail = JSON.parseObject(detailJson);


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
    private void getItemimage(String woMaiId, Product product) {
        Map param = new HashMap();
        param.put("skuid", woMaiId);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemimage.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemDetail = JSON.parseObject(detailJson);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取商品库存信息
     * {
     *  "area": "",
     *  "warehouseid": "100",
     *  "skuids": "5178122,	3418933,	5367933,	510986"
     * }
     * @return
     */
    private void getStock(String woMaiId, Product product) {
        Map param = new HashMap();
        param.put("skuids", woMaiId);
        String url = this.getWoMaiUrl();
        // TODO: 2016/7/26 获取我买仓库信息
        List<String> warehouseIds = new ArrayList<String>();
        Map<String, String> httpParam = this.getHttpParams("womai.inventory.get", param);
        try {
            // 循环获取每个仓库下的商品库存
            for (String warehouseId : warehouseIds) {
                param.put("warehouseid", warehouseId);
                String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
                JSONObject dataObject = JSON.parseObject(detailJson);
                JSONArray stockList = dataObject.getJSONArray("Inventory");
                for (int i = 0; i < stockList.size(); i++) {
                    JSONObject stock = stockList.getJSONObject(i);

                    ProductSkuItem productSkuItem = new ProductSkuItem();
                    productSkuItem.setStorehouseId(Integer.parseInt(this.getStorehouseId(warehouseId)));
                    productSkuItem.setSkuNum("");

                    ProductSku productSku = product.getProductSku();
                    productSku.addToSkuItems(productSkuItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getPrice(String woMaiId, Product product) {

        Map param = new HashMap();
        param.put("skuids", woMaiId);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.price.get", param);
        try {
            String priceJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject priceObject = JSON.parseObject(priceJson);
            JSONArray priceList = priceObject.getJSONArray("price");
            ProductSku productSku = product.getProductSku();
            for (int i = 0; i < productSku.getSkuItems().size(); i++) {
                product.getProductSku().getSkuItems().get(i).setCurPrice(priceList.getJSONObject(0).getString("price"));
            }
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



}
