package com.jfshare.manager.server;

import com.jfshare.finagle.thrift.manager.ProductOpt;
import com.jfshare.finagle.thrift.manager.QueryConditions;
import com.jfshare.manager.model.TbProductOperator;
import com.jfshare.manager.server.support.IHandler;
import com.jfshare.manager.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHandler implements IHandler {

    private Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    List<ProductOpt> rConvertTbProductOperators(List<TbProductOperator> tbProductOperators) {
        if(CollectionUtils.isEmpty(tbProductOperators)) {
            return new ArrayList<ProductOpt>();
        }

        List<ProductOpt> productOpts = new ArrayList<>();
        for(TbProductOperator tbProductOperator : tbProductOperators) {
            ProductOpt productOpt = new ProductOpt();
            productOpt.setProductId(tbProductOperator.getProductId());
            productOpt.setActiveState(tbProductOperator.getProductState());
            productOpt.setCreateTime(DateTimeUtil.DateTimeToStr(tbProductOperator.getCreateTime()));
            productOpt.setDesc(tbProductOperator.getOptDesc());
            productOpt.setOperatorId(tbProductOperator.getOptUserId());
            productOpt.setOperatorType(tbProductOperator.getOptUserType());
            productOpts.add(productOpt);
        }

        return productOpts;
    }

    boolean verifyParams(QueryConditions conditions) {
        if(conditions == null || StringUtils.isBlank(conditions.getProductId())) {
            return false;
        }
        return true;
    }

    boolean verifyParams(String param) {
        if(StringUtils.isEmpty(param)) {
            return false;
        }
        return true;
    }
}
