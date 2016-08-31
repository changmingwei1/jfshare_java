package com.jfshare.buyer.dao.mysql;

import com.jfshare.buyer.model.TbThirdUserinfo;
import com.jfshare.buyer.model.TbUser;
import com.jfshare.buyer.model.TbUserThirdparty;
import com.jfshare.finagle.thrift.buyer.Buyer;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IUserDao {
    int userIsExist(String loginName);

    int insert(TbUser user);
    
    int insertBuyerH5(Buyer buyer);

    int update(TbUser user);

    TbUser getBuyer(int userId);

    List<TbUser> checkLogin(Buyer buyer);

    TbUser getBuyerByThirdUser(TbUserThirdparty tbUserThirdparty);
    TbUserThirdparty getThirdUser(String mobile);


    int insertThirdParty(TbUserThirdparty tbUserThirdparty);

    TbUserThirdparty getThirdParty(int userId, int thirdPartyType);

    TbUser getBuyerByLoginName(String loginName);
    
    int userIsExistForThird(String cust_id,int thirdType);
    List<TbUser> selectByUserPK(TbThirdUserinfo ttu);
    List<TbUser> userExistForMoblie(String moblie);
    int insertThirdUserInfo(TbThirdUserinfo ttu);
    int insertUser(TbUser tbu);
    int userIsExistForMobile(String mobile, int thirdType);  
    List<TbUser> getListBuyer(List<Integer> userIdList);
}
