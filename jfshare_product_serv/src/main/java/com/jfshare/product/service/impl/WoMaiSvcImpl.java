package com.jfshare.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.jfshare.product.service.IWoMaiSvc;
import com.jfshare.product.util.CodeUtil;
import com.jfshare.product.util.HttpUtils;
import org.elasticsearch.common.jackson.core.JsonStreamContext;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.index.engine.Engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/5/16.
 */
public class WoMaiSvcImpl implements IWoMaiSvc {

    @Override
    public int importProduct(String path) {
        return 0;
    }

    private String getWoMaiUrl(String method, Map param) {
        StringBuffer url = new StringBuffer();
        // TODO: 2016/5/17 通过配置获取
        String domain = "http://sandbox.womaiapp.com/api/rest";
        String appKey = "425113";
        String appSecret = "";
        String paramStr = JSON.toJSONString(param);
        StringBuffer sign = new StringBuffer();
        sign.append(method).append(appKey).append(appSecret).append(paramStr);

        url.append(domain).append("?")
                .append("method=").append(method)
                .append("&appKey=").append(appKey)
                .append("&sign=").append(CodeUtil.getMD5Upper(sign.toString()))
                .append("&param=").append(paramStr);

        return url.toString();
    }

    /**
     * 获取我买网商品详情
     * @param productId
     * @return
     */
    private String getItemDetail(String productId) {
        Map param = new HashMap();
        param.put("skuid", productId);
        String url = this.getWoMaiUrl("womai.itemdetail.get", param);
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
        String url = this.getWoMaiUrl("womai.itemstatus.get", param);

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
        String url = this.getWoMaiUrl("womai.itemimage.get", param);

        return null;
    }



}
