package com.jfshare.manager.service;

import com.jfshare.finagle.thrift.manager.ProductOpt;
import com.jfshare.finagle.thrift.manager.QueryConditions;
import com.jfshare.manager.dao.IProductOptDao;
import com.jfshare.manager.model.TbProductOperator;
import com.jfshare.manager.util.BeanConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *************************************************************************
 * @文件名称: ProductStockService.java
 *
 * @包路径  : com.jfshare.manager.server
 *
 *
 * @类描述:   管理中心商品服务接口服务类
 *
 * @创建人:   sushi
 *
 * @修改记录:
-----------------------------------------------------------------------------------------------
时间						|		修改人		|		修改的方法		|		修改描述
-----------------------------------------------------------------------------------------------
|					|					|
-----------------------------------------------------------------------------------------------

 **************************************************************************
 */
@Service(value = "productService")
public class ProductService {
    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private IProductOptDao productOptDao;

    public List<TbProductOperator> getProductOptRecord(QueryConditions conditions) {
        return productOptDao.getProductOperators(conditions);
    }

    public void insertProductOptLog(ProductOpt productOpt) {
        TbProductOperator tbProductOperator = BeanConvertUtil.ProductOpt2TbProductOperator(productOpt);
        productOptDao.insert(tbProductOperator);
    }
}
 