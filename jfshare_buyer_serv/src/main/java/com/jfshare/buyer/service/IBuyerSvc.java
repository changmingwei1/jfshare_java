package com.jfshare.buyer.service;

import com.jfshare.buyer.model.TbUser;
import com.jfshare.finagle.thrift.buyer.AuthInfo;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.finagle.thrift.buyer.LoginLog;
import com.jfshare.finagle.thrift.buyer.ThirdpartyUser;
import com.jfshare.finagle.thrift.result.Result;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IBuyerSvc {
    boolean userIsExist(String loginName);

    void validUser(Result result, Buyer buyer);

    public void validPwd(Result result, Buyer buyer);

    int insert(Buyer buyer);

    void validLogin(Result result, Buyer buyer);

    int addTryFail(Buyer buyer);

    void addOnline(TbUser user, LoginLog loginLog);

    int clearTryFail(TbUser user);

    boolean isOnline(int userId, String tokenId);

    Buyer getOnline(int userId, String tokenId);

    Buyer getBuyer(int userId);

    boolean updatePwd(int userId, String newPwd);

    boolean updateBuyer(Buyer buyer);

    void removeOnline(Result result, int userId, String tokenId);

    Buyer checkThirdCustomId(ThirdpartyUser thirdUser);

    void insertThirdPartyRel(ThirdpartyUser thirdUser, int userId);

    ThirdpartyUser queryThirdPartyInfo(int userId, int code);

    Buyer getBuyerByLoginName(String loginName);

    Buyer createUserThird(ThirdpartyUser thirdUser);
    
    AuthInfo createAuth(String userId, String mobile, String email, String appId,int clientType) throws Exception;
    boolean verificationToken(String uid, AuthInfo authInfo, String appId,int clientType) throws Exception;
    
}
