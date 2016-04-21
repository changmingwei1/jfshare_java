package com.jfshare.manager.dao.impl.mybatis;

import com.jfshare.finagle.thrift.manager.Commissioner;
import com.jfshare.manager.dao.IUserDao;
import com.jfshare.manager.model.TbCommissioner;
import com.jfshare.manager.model.TbCommissionerExample;
import com.jfshare.manager.model.mappers.TbCommissionerMapper;
import com.jfshare.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
@Repository(value = "userDao")
public class UserDaoImpl implements IUserDao {

    @Autowired
    private TbCommissionerMapper tbCommissionerMapper;

    @Override
    public int loginNameIsExist(String loginName) {
        TbCommissionerExample example = new TbCommissionerExample();
        TbCommissionerExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        return tbCommissionerMapper.countByExample(example);
    }

    @Override
    public int insert(TbCommissioner tbCommissioner) {
        return tbCommissionerMapper.insertSelective(tbCommissioner);
    }

    @Override
    public int update(TbCommissioner tbCommissioner) {
        return tbCommissionerMapper.updateByPrimaryKeySelective(tbCommissioner);
    }

    @Override
    public TbCommissioner getCommissioner(int csId) {
        return tbCommissionerMapper.selectByPrimaryKey(csId);
    }

    @Override
    public List<TbCommissioner> checkLogin(Commissioner cs) {
        TbCommissionerExample example = new TbCommissionerExample();
        TbCommissionerExample.Criteria criteria = example.createCriteria();
        criteria.andLoginNameEqualTo(cs.getLoginName());
        criteria.andPwdEncEqualTo(CryptoUtil.encryptDBPwd(cs.getPwdEnc()));
        return (List<TbCommissioner>) tbCommissionerMapper.selectByExample(example);
    }
}
