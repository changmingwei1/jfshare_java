package com.jfshare.seller.dao.mysql.impl;

import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.seller.dao.mysql.ISellerDao;
import com.jfshare.seller.model.TbSeller;
import com.jfshare.seller.model.TbSellerExample;
import com.jfshare.seller.model.TbUserSellerRela;
import com.jfshare.seller.model.TbUserSellerRelaExample;
import com.jfshare.seller.model.mapper.TbSellerMapper;
import com.jfshare.seller.model.mapper.TbUserSellerRelaMapper;
import com.jfshare.utils.CryptoUtil;
import com.jfshare.utils.StringUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
@Repository(value = "sellerDao")
public class SellerDaoImpl implements ISellerDao {
    @Autowired
    private TbSellerMapper tbSellerMapper;
    
    @Autowired
    private TbUserSellerRelaMapper userSellerRelaMapper;

    @Override
    public int sellerIsExist(String loginName) {
        TbSellerExample example = new TbSellerExample();
        TbSellerExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        return tbSellerMapper.countByExample(example);
    }

    @Override
    public int insert(TbSeller tbSeller) {
        return tbSellerMapper.insertSelective(tbSeller);
    }

    @Override
    public int update(TbSeller tbSeller) {
        return tbSellerMapper.updateByPrimaryKeySelective(tbSeller);
    }

    @Override
    public TbSeller getSeller(int sellerId) {
        return tbSellerMapper.selectByPrimaryKey(sellerId);
    }

    @Override
    public List<TbSeller> checkLogin(Seller seller) {
        TbSellerExample example = new TbSellerExample();
        TbSellerExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(seller.getLoginName());
        criteria.andPwdEncEqualTo(CryptoUtil.encryptDBPwd(seller.getPwdEnc()));
        return (List<TbSeller>)tbSellerMapper.selectByExample(example);
    }

    @Override
    public List<TbSeller> getSellerList(List<Integer> sellerIds) {
        TbSellerExample example = new TbSellerExample();
        TbSellerExample.Criteria criteria = example.createCriteria();
        criteria.andSellerIdIn(sellerIds);
        return (List<TbSeller>)tbSellerMapper.selectByExample(example);
    }

	@Override
	public List<TbSeller> getSellerBySeller(Seller seller) {
		TbSellerExample example = new TbSellerExample();
		TbSellerExample.Criteria criteria = example.createCriteria();
		if(seller.getSellerId() > 0)
			criteria.andSellerIdEqualTo(seller.getSellerId());
		if(seller.getSellerName() != null)
			criteria.andSellerNameLike("%" + seller.getSellerName() + "%");
		if(seller.getLoginName() != null) 
			criteria.andLoginNameLike("%" + seller.getLoginName() + "%");
		return tbSellerMapper.selectByExample(example);
	}

	@Override
	public int insertUserSellerRela(TbUserSellerRela tbUserSellerRela) {
		 return userSellerRelaMapper.insertSelective(tbUserSellerRela);
	}

	@Override
	public int deleteByuserId(String userId) {
		return userSellerRelaMapper.deleteByuserId(userId);
	}

	@Override
	public List<TbUserSellerRela> selectUsrListBySellerId(String sellerId) {
		TbUserSellerRelaExample example = new TbUserSellerRelaExample();
		TbUserSellerRelaExample.Criteria criteria = example.createCriteria();
		if(!StringUtil.isNullOrEmpty(sellerId))
			criteria.andSellerIdEqualTo(sellerId);
		example.setOrderByClause("createtime desc");
		return userSellerRelaMapper.selectByExample(example);
	}
    
}
