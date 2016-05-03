package com.jfshare.baseTemplate.util;

import com.jfshare.baseTemplate.dao.mysql.IPostageTemplateDao;
import com.jfshare.baseTemplate.dao.mysql.IStorehouseDao;
import com.jfshare.baseTemplate.dao.redis.BaseRedis;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Lenovo on 2016/4/28.
 */
@Service
public class ReloadRedis {

    @Resource
    private IPostageTemplateDao templateDao;

    @Resource
    private IStorehouseDao storehouseDao;

    @Resource
    private BaseRedis baseRedis;

    /**
     * 加载邮费模板缓存
     * @param id
     */
    public void reloadPostageTemplate(int id) {

    }

    /**
     * 加载仓库缓存
     * @param id
     */
    public void reloadStorehouse(int id) {

    }

}
