package com.jfshare.buyer.dao.mysql.impl;

import com.jfshare.buyer.dao.mysql.IUserDao;
import com.jfshare.buyer.model.TbUser;
import com.jfshare.buyer.model.TbUserExample;
import com.jfshare.buyer.model.TbUserThirdparty;
import com.jfshare.buyer.model.TbUserThirdpartyExample;
import com.jfshare.buyer.model.mapper.TbUserMapper;
import com.jfshare.buyer.model.mapper.TbUserThirdpartyMapper;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.utils.CryptoUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
@Repository
public class UserDaoImpl implements IUserDao {
    @Autowired
    private TbUserMapper tUserMapper;

    @Autowired
    private TbUserThirdpartyMapper tbUserThirdpartyMapper;

    @Override
    public int userIsExist(String loginName) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        return tUserMapper.countByExample(example);
    }

    @Override
    public int insert(TbUser user) {
        return tUserMapper.insertSelective(user);
    }

    @Override
    public int update(TbUser user) {
        return tUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public TbUser getBuyer(int userId) {
        return tUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<TbUser> checkLogin(Buyer buyer) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(buyer.getLoginName());
        criteria.andPwdEncEqualTo(CryptoUtil.encryptDBPwd(buyer.getPwdEnc()));

        return (List<TbUser>)tUserMapper.selectByExample(example);
    }

    @Override
    public TbUser getBuyerByThirdUser(TbUserThirdparty tbUserThirdparty) {
        TbUser tbUser = null;
        TbUserThirdpartyExample example = new TbUserThirdpartyExample();
        TbUserThirdpartyExample.Criteria criteria = example.createCriteria();
        criteria.andThirdTypeEqualTo(tbUserThirdparty.getThirdType());
        criteria.andAccountNoEqualTo(tbUserThirdparty.getAccountNo());
        List<TbUserThirdparty> tbUserThirdparties = tbUserThirdpartyMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(tbUserThirdparties)) {
            TbUserThirdparty tbUserThirdpartyRes = tbUserThirdparties.get(0);
            return this.getBuyer(tbUserThirdpartyRes.getUserId());
        }

        return null;
    }

    @Override
    public int insertThirdParty(TbUserThirdparty tbUserThirdparty) {
        return tbUserThirdpartyMapper.insert(tbUserThirdparty);
    }

    @Override
    public TbUserThirdparty getThirdParty(int userId, int thirdPartyType) {
        TbUserThirdparty tbUserThirdparty = null;
        TbUserThirdpartyExample example = new TbUserThirdpartyExample();
        TbUserThirdpartyExample.Criteria criteria = example.createCriteria();
        criteria.andThirdTypeEqualTo(thirdPartyType);
        criteria.andUserIdEqualTo(userId);
        List<TbUserThirdparty> tbUserThirdparties = tbUserThirdpartyMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(tbUserThirdparties)) {
            tbUserThirdparty = tbUserThirdparties.get(0);
        }
        return tbUserThirdparty;
    }

    @Override
    public TbUser getBuyerByLoginName(String loginName) {
        TbUser tbUser = null;
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<TbUser> tbUsers = tUserMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(tbUsers)) {
            tbUser = tbUsers.get(0);
            return tbUser;
        }

        return null;
    }
}
