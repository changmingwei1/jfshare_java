package com.jfshare.buyer.dao.mysql;

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

    int update(TbUser user);

    TbUser getBuyer(int userId);

    List<TbUser> checkLogin(Buyer buyer);

    TbUser getBuyerByThirdUser(TbUserThirdparty tbUserThirdparty);

    int insertThirdParty(TbUserThirdparty tbUserThirdparty);

    TbUserThirdparty getThirdParty(int userId, int thirdPartyType);

    TbUser getBuyerByLoginName(String loginName);
}
