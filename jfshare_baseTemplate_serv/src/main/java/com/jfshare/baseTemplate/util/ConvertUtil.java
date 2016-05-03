package com.jfshare.baseTemplate.util;

import com.alibaba.fastjson.JSON;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;
import com.jfshare.baseTemplate.mybatis.model.manual.PostageModel;
import com.jfshare.finagle.thrift.baseTemplate.Postage;
import com.jfshare.finagle.thrift.baseTemplate.PostageTemplate;
import com.jfshare.finagle.thrift.baseTemplate.Storehouse;
import com.jfshare.utils.JsonMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2015/10/20.
 */
public class ConvertUtil {


    /**
     * 邮费模板数据库实体转换成map
     * @param tbPostageTemplate 邮费模板数据库实体
     * @return redis对应的map
     */
    public static Map<String, String> tbPostageTemplate2Redis(TbPostageTemplate tbPostageTemplate) {

        Map<String, String> postageTemplateMap = new HashMap<>();
        postageTemplateMap.put("id", tbPostageTemplate.getId() + "");
        postageTemplateMap.put("sellerId", tbPostageTemplate.getSellerId() + "");
        postageTemplateMap.put("name", tbPostageTemplate.getName());
        postageTemplateMap.put("type", tbPostageTemplate.getType() + "");
        postageTemplateMap.put("postageInfo", tbPostageTemplate.getPostageInfo());
        return postageTemplateMap;
    }

    /**
     * 邮费模板map转换成数据库实体
     * @param postageTemplateMap
     * @return
     */
    public static TbPostageTemplate redis2TbPostageTemplate(Map<String, String> postageTemplateMap) {
        TbPostageTemplate tbPostageTemplate = new TbPostageTemplate();
        tbPostageTemplate.setId(Integer.parseInt(postageTemplateMap.get("id")));
        tbPostageTemplate.setSellerId(Integer.parseInt(postageTemplateMap.get("sellerId")));
        tbPostageTemplate.setName(postageTemplateMap.get("name"));
        tbPostageTemplate.setType(Integer.parseInt(postageTemplateMap.get("type")));
        tbPostageTemplate.setPostageInfo(postageTemplateMap.get("postageInfo"));
        return tbPostageTemplate;
    }

    /**
     * 邮费模板数据库实体转换成thrift实体
     * @param tbPostageTemplate
     * @return
     */
    public static PostageTemplate tbPostageTemplate2Thrift(TbPostageTemplate tbPostageTemplate) {

        PostageTemplate postageTemplate = new PostageTemplate();
        postageTemplate.setId(tbPostageTemplate.getId());
        postageTemplate.setSellerId(tbPostageTemplate.getSellerId());
        postageTemplate.setName(tbPostageTemplate.getName());
        postageTemplate.setType(tbPostageTemplate.getType());
        List<PostageModel> postageModels = JSON.parseArray(tbPostageTemplate.getPostageInfo(), PostageModel.class);
        for (PostageModel postageModel : postageModels) {
            postageTemplate.addToPostageList(new Postage(postageModel.getSupportProvince(), postageModel.getRule()));
        }
        return postageTemplate;
    }

    /**
     * 邮费模板thrift实体转换成数据库实体
     * @param postageTemplate
     * @return
     */
    public static TbPostageTemplate thrift2TbPostageTemplate(PostageTemplate postageTemplate) {

        TbPostageTemplate tbPostageTemplate = new TbPostageTemplate();
        tbPostageTemplate.setId(postageTemplate.getId() == 0 ? null : postageTemplate.getId());
        tbPostageTemplate.setSellerId(postageTemplate.getSellerId() == 0 ? null : postageTemplate.getSellerId());
        tbPostageTemplate.setName(postageTemplate.getName());
        tbPostageTemplate.setType(postageTemplate.getType() == 0 ? null : postageTemplate.getType());
        if (postageTemplate.getPostageList() == null) {
            tbPostageTemplate.setPostageInfo("[]");
        } else {
            List<PostageModel> postageModels = new ArrayList<>();
            for (Postage postage : postageTemplate.getPostageList()) {
                PostageModel model = new PostageModel();
                model.setSupportProvince(postage.getSupportProvince());
                model.setRule(postage.getRule());
                postageModels.add(model);
            }
            tbPostageTemplate.setPostageInfo(JsonMapper.toJson(postageModels));
        }
        return tbPostageTemplate;
    }

    /**
     * 仓库数据库实体转换成map
     * @param tbStorehouse
     * @return
     */
    public static Map<String, String> tbStorehouse2Redis(TbStorehouse tbStorehouse) {

        Map<String, String> postageTemplateMap = new HashMap<>();
        postageTemplateMap.put("id", tbStorehouse.getId() + "");
        postageTemplateMap.put("sellerId", tbStorehouse.getSellerId() + "");
        postageTemplateMap.put("name", tbStorehouse.getName());
        postageTemplateMap.put("supportProvince", tbStorehouse.getSupportProvince());
        return postageTemplateMap;
    }

    /**
     * 仓库map转换成数据库实体
     * @param storehouseMap
     * @return
     */
    public static TbStorehouse redis2TbStorehouse(Map<String, String> storehouseMap) {
        TbStorehouse tbStorehouse = new TbStorehouse();
        tbStorehouse.setId(Integer.parseInt(storehouseMap.get("id")));
        tbStorehouse.setSellerId(Integer.parseInt(storehouseMap.get("sellerId")));
        tbStorehouse.setName(storehouseMap.get("name"));
        tbStorehouse.setSupportProvince(storehouseMap.get("supportProvince"));
        return tbStorehouse;
    }

    /**
     * 仓库数据库实体转换成thrift实体
     * @param tbStorehouse
     * @return
     */
    public static Storehouse tbStorehouse2Thrift(TbStorehouse tbStorehouse) {

        Storehouse storehouse = new Storehouse();
        storehouse.setId(tbStorehouse.getId());
        storehouse.setSellerId(tbStorehouse.getSellerId());
        storehouse.setName(tbStorehouse.getName());
        storehouse.setSupportProvince(tbStorehouse.getSupportProvince());
        return storehouse;
    }

    /**
     * 仓库thrift实体转换成数据库实体
     * @param storehouse
     * @return
     */
    public static TbStorehouse thrift2TbStorehouse(Storehouse storehouse) {

        TbStorehouse tbStorehouse = new TbStorehouse();
        tbStorehouse.setId(storehouse.getId() == 0 ? null : storehouse.getId());
        tbStorehouse.setSellerId(storehouse.getSellerId() == 0 ? null : storehouse.getSellerId());
        tbStorehouse.setName(storehouse.getName());
        tbStorehouse.setSupportProvince(storehouse.getSupportProvince());
        return tbStorehouse;
    }


}
