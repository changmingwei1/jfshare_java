package com.jfshare.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.product.commons.ProductCommons;
import com.jfshare.product.model.TbProduct;
import com.jfshare.product.model.manual.WoMaiError;
import com.jfshare.product.service.IWoMaiSvc;
import com.jfshare.product.util.CodeUtil;
import com.jfshare.product.util.FileUtil;
import com.jfshare.product.util.HttpUtils;
import com.jfshare.ridge.PropertiesUtil;
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


        // 获取商品详情

        // 获取商品状态

        // 获取商品图片，并上传

        // 保存商品
        return false;
    }

    public String getWoMaiUrl() {
        // TODO: 2016/5/17 通过配置获取
        String domain = "http://sandbox.womaiapp.com/api/rest";

        return domain;
    }

    public Map<String, String> getHttpParams(String method, Map param) {
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
        postParams.put("sign", CodeUtil.getMD5Upper(sign.toString()));
        if (param != null) {
            postParams.put("param", JSON.toJSONString(param));
        }
        return postParams;
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
     * @param productId
     * @return
     */
    private String getItemDetail(String productId, Product product) {

        Map<String, String> param = new HashMap();
        param.put("skuid", productId);
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

            ProductSku productSku = new ProductSku();
            ProductSkuItem skuItem = new ProductSkuItem();
            skuItem.setSkuNum("");


            System.out.println(((Map)details.get(0)).get("goodsid"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
     * @param productIds
     * @return
     */
    private List<String> getItemstatus(List<String> productIds) {
        String ids = StringUtils.join(productIds, ",");
        Map param = new HashMap();
        param.put("skuid", ids);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemstatus.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemDetail = JSON.parseObject(detailJson);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
     * @param productIds
     * @return
     */
    private List<String> getItemimage(List<String> productIds) {
        String ids = StringUtils.join(productIds, ",");
        Map param = new HashMap();
        param.put("skuid", ids);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.itemimage.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemDetail = JSON.parseObject(detailJson);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取商品库存信息
     * {
     *  "area": "",
     *  "warehouseid": "100",
     *  "skuids": "5178122,	3418933,	5367933,	510986"
     * }
     * @param productIds
     * @return
     */
    private List<String> getStock(List<String> productIds) {
        String ids = StringUtils.join(productIds, ",");
        Map param = new HashMap();
        param.put("skuids", ids);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.inventory.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemDetail = JSON.parseObject(detailJson);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    private void getPrice(List<String> productIds) {

        String ids = StringUtils.join(productIds, ",");
        Map param = new HashMap();
        param.put("skuids", ids);
        String url = this.getWoMaiUrl();
        Map<String, String> httpParam = this.getHttpParams("womai.price.get", param);
        try {
            String detailJson = HttpUtils.httpPostUTF8(url, httpParam);
            JSONObject itemDetail = JSON.parseObject(detailJson);


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
