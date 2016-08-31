package com.jfshare.buyer.dao.mysql.impl;

import com.jfshare.buyer.dao.mysql.IUserDao;
import com.jfshare.buyer.model.TbThirdUserinfo;
import com.jfshare.buyer.model.TbThirdUserinfoExample;
import com.jfshare.buyer.model.TbUser;
import com.jfshare.buyer.model.TbUserExample;
import com.jfshare.buyer.model.TbUserThirdparty;
import com.jfshare.buyer.model.TbUserThirdpartyExample;
import com.jfshare.buyer.model.mapper.TbThirdUserinfoMapper;
import com.jfshare.buyer.model.mapper.TbUserMapper;
import com.jfshare.buyer.model.mapper.TbUserThirdpartyMapper;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.CryptoUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
@Repository
public class UserDaoImpl implements IUserDao {
    @Autowired
    private TbUserMapper tUserMapper;

    @Autowired
    private TbUserThirdpartyMapper tbUserThirdpartyMapper;
    
    @Autowired
    private TbThirdUserinfoMapper tbThirdUserinfoMapper;

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
    public int insertBuyerH5(Buyer buyer) {
    	TbUser user = new TbUser();
		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(buyer);
		BeanUtil.fillBeanData(user, stringObjectMap);
		user.setPwdEnc(CryptoUtil.encryptDBPwd(buyer.getPwdEnc().trim()));
		user.setBirthday(DateUtils.strToDateTime(buyer.getBirthday(), DateUtils.Simple_DateTime_Format));
		user.setCreateTime(new DateTime());
        int rows = tUserMapper.insert(user);
        if(rows > 0){
			return user.getUserId();
		}
        return -100;
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
    public List<TbUser> getListBuyer(List<Integer> userIdList) {
    	 TbUserExample example = new TbUserExample();
         TbUserExample.Criteria criteria = example.createCriteria();
         criteria.andUserIdIn(userIdList);
        return tUserMapper.selectByExample(example);
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
    public TbUserThirdparty getThirdUser(String mobile) {
        TbUserThirdpartyExample example = new TbUserThirdpartyExample();
        TbUserThirdpartyExample.Criteria criteria = example.createCriteria();
        if(!StringUtil.isNullOrEmpty(mobile)){
            criteria.andAccountNoEqualTo(mobile);
        }
        List<TbUserThirdparty> tbList = tbUserThirdpartyMapper.selectByExample(example);
        if(tbList.size() > 0) {
            return tbList.get(0);
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
    
    @Override
    public int userIsExistForThird(String cust_id, int thirdType) {
        TbThirdUserinfoExample example = new TbThirdUserinfoExample();
        TbThirdUserinfoExample.Criteria criteria = example.createCriteria();
        criteria.andCustIdEqualTo(cust_id);
        criteria.andThirdTypeEqualTo(thirdType);
        return tbThirdUserinfoMapper.countByExample(example);
    }
    
    @Override
    public  List<TbUser> selectByUserPK(TbThirdUserinfo ttu) {
        return tbThirdUserinfoMapper.selectByUserPK(ttu);
    }
    
    @Override
    public List<TbUser> userExistForMoblie(String moblie){
         TbUserExample example = new TbUserExample();
         TbUserExample.Criteria criteria = example.createCriteria();
         criteria.andMobileEqualTo(moblie);
         List<TbUser> tbUsers = tUserMapper.selectByExample(example);
         return tbUsers;
    }
    
    @Override
    public int insertThirdUserInfo(TbThirdUserinfo ttu) {
        return tbThirdUserinfoMapper.insert(ttu);
    }
    @Override
    public int insertUser(TbUser tbu){
    	return tbThirdUserinfoMapper.insertUser(tbu);
    }
    @Override
    public int userIsExistForMobile(String mobile, int thirdType){
    	 TbThirdUserinfoExample example = new TbThirdUserinfoExample();
         TbThirdUserinfoExample.Criteria criteria = example.createCriteria();
         criteria.andMobileEqualTo(mobile);
         criteria.andThirdTypeEqualTo(thirdType);
         return tbThirdUserinfoMapper.countByExample(example);
    }
}
