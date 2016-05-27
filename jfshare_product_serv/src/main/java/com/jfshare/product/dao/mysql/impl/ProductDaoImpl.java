package com.jfshare.product.dao.mysql.impl;

import com.jfshare.finagle.thrift.product.ProductSurvey;
import com.jfshare.finagle.thrift.product.ProductSurveyQueryParam;
import com.jfshare.product.dao.mysql.IProductDao;
import com.jfshare.product.exceptions.MybatisDMLException;
import com.jfshare.product.model.*;
import com.jfshare.product.model.mapper.TbProductMapper;
import com.jfshare.product.model.mapper.TbProductSkuMapper;
import com.jfshare.product.model.mapper.manual.ManualTbProductMapper;
import com.jfshare.product.util.FailCode;
import com.jfshare.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
@Repository
public class ProductDaoImpl implements IProductDao {
    @Autowired
    private TbProductMapper tbProductMapper;
    
    @Autowired
    private ManualTbProductMapper manualTbProductMapper;

    @Autowired
    private TbProductSkuMapper tbProductSkuMapper;

    @Override
    public TbProduct queryProduct(String productId) {
        TbProductExample example = new TbProductExample();
        TbProductExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(productId);
        List<TbProduct> tbProducts = tbProductMapper.selectByExample(example);
        return tbProducts.size() > 0 ? tbProducts.get(0) : null;
    }
    @Override
    public TbProductWithBLOBs queryProductWithBLOBs(String productId) {
        return tbProductMapper.selectByPrimaryKey(productId);
    }
	@Override
	public List<ProductSurvey> productSurveyQueryPage(ProductSurveyQueryParam param) {
		
		return manualTbProductMapper.productSurveyQueryPage(param);
	}

    @Override
    public List<TbProductSku> getProductSku(String productId) {
        TbProductSkuExample example = new TbProductSkuExample();
        TbProductSkuExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(productId);
        return tbProductSkuMapper.selectByExample(example);
    }

    @Override
    public TbProductSku getProductHotSku(String productId, String skuNum) {
        TbProductSkuExample example = new TbProductSkuExample();
        TbProductSkuExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(productId);
        criteria.andSkuNumEqualTo(ConvertUtil.getString(skuNum));
        List<TbProductSku> tbProductSkus = tbProductSkuMapper.selectByExample(example);
        return tbProductSkus.size() > 0 ? tbProductSkus.get(0) : null;
    }

    @Override
    public TbProductSku getProductHotSku(String productId, int storehouseId, String skuNum) {
        TbProductSkuExample example = new TbProductSkuExample();
        TbProductSkuExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(productId);
        criteria.andStorehouseIdEqualTo(storehouseId);
        criteria.andSkuNumEqualTo(ConvertUtil.getString(skuNum));
        List<TbProductSku> tbProductSkus = tbProductSkuMapper.selectByExample(example);
        return tbProductSkus.size() > 0 ? tbProductSkus.get(0) : null;
    }

    @Override
    public void addProduct(TbProductWithBLOBs tbProduct) {
        tbProductMapper.insertSelective(tbProduct);
    }

    @Override
    public void updateProduct(TbProductWithBLOBs tbProduct) {
        tbProductMapper.updateByPrimaryKeySelective(tbProduct);
    }

    @Override
    public void updateProductState(TbProductWithBLOBs tbProduct, int oldState) {
        TbProductExample example = new TbProductExample();
        TbProductExample.Criteria criteria = example.createCriteria();
        criteria.andActiveStateEqualTo(oldState);
        criteria.andIdEqualTo(tbProduct.getId());
        int rCount = tbProductMapper.updateByExampleSelective(tbProduct, example);
        if(rCount <= 0) {
            throw new MybatisDMLException(FailCode.MYBATIS_UPDATE_ERROR);
        }
    }
    
	@Override
	public List<ProductSurvey> queryFloorProduct(int floorId) {
		return manualTbProductMapper.queryFloorProduct(floorId);
	}
	
	@Override
	public List<ProductSurvey> productSurveyQuery(ProductSurveyQueryParam param) {
		return manualTbProductMapper.productSurveyQuery(param);
	}

	@Override
	public ProductSurvey productSurveyQueryById(ProductSurveyQueryParam param){
		return manualTbProductMapper.productSurveyQueryById(param);
	}
}
