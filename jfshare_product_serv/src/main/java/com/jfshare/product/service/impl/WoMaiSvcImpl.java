package com.jfshare.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.jfshare.product.service.IWoMaiSvc;
import com.jfshare.product.util.CodeUtil;
import com.jfshare.product.util.HttpUtils;
import org.elasticsearch.common.jackson.core.JsonStreamContext;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.index.engine.Engine;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/5/16.
 */
@Service
public class WoMaiSvcImpl implements IWoMaiSvc {

    @Override
    public int importProduct(String path) {
        return 0;
    }

    public String getWoMaiUrl() {
        // TODO: 2016/5/17 通过配置获取
        String domain = "http://sandbox.womaiapp.com/api/rest";

        return domain;
    }

    public Map<String, String> getPostParams(String method, Map param) {
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
     * @param productId
     * @return
     */
    private String getItemDetail(String productId) {
        Map param = new HashMap();
        param.put("skuid", productId);
        String url = this.getWoMaiUrl();
        try {
            HttpUtils.httpPostUTF8(url, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取我买在对接平台上发布的所有商品上下架状态
     * @param productIds
     * @return
     */
    private List<String> getItemstatus(List<String> productIds) {
        String ids = StringUtils.join(productIds, ",");
        Map param = new HashMap();
        param.put("skuid", ids);
        String url = this.getWoMaiUrl();


        return null;
    }

    /**
     * 获取我买在对接平台发布的商品图片信息
     * @param productIds
     * @return
     */
    private List<String> getItemimage(List<String> productIds) {
        String ids = StringUtils.join(productIds, ",");
        Map param = new HashMap();
        param.put("skuid", ids);
        String url = this.getWoMaiUrl();

        return null;
    }



}
