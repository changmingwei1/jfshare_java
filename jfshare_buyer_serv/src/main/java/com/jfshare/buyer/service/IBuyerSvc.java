package com.jfshare.buyer.service;

import java.util.List;

import com.jfshare.buyer.model.TbThirdUserinfo;
import com.jfshare.buyer.model.TbUser;
import com.jfshare.finagle.thrift.buyer.AuthInfo;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.finagle.thrift.buyer.BuyerListResult;
import com.jfshare.finagle.thrift.buyer.H5ThirdLoginParam;
import com.jfshare.finagle.thrift.buyer.H5ThirdLoginResult;
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
    
    //添加用户个人信息到redis
    void addOnline(TbUser user, LoginLog loginLog);
    
    //更新用户信息redis
    void updateOnline(TbUser user);
    
    void addOnlineToken(TbUser user, LoginLog loginLog);

    int clearTryFail(TbUser user);

    boolean isOnline(int userId, String tokenId);

    Buyer getOnline(int userId, String tokenId, LoginLog loginLog);

    Buyer getBuyer(int userId);
    
    TbUser getTbuser(int userId);
    
    boolean updatePwd(int userId, String newPwd);

    boolean updateBuyer(Buyer buyer);

    void removeOnline(Result result, int userId, String tokenId);

    Buyer checkThirdCustomId(ThirdpartyUser thirdUser);

    void insertThirdPartyRel(ThirdpartyUser thirdUser, int userId);
        
    ThirdpartyUser queryThirdPartyInfo(int userId, int code);

    Buyer getBuyerByLoginName(String loginName);

    Buyer createUserThird(ThirdpartyUser thirdUser) throws Exception;
    
    AuthInfo createAuth(String userId, String mobile, String email, String appId,int clientType) throws Exception;
    boolean verificationToken(String uid, AuthInfo authInfo, String appId,int clientType) throws Exception;
    
    boolean verificationTokenByonline(LoginLog loginLog, AuthInfo authInfo) throws Exception;
	boolean userIsExistForThird(String cust_id, int thirdType);
	List<TbUser> selectByUserPK(TbThirdUserinfo ttu);
	TbUser addUserAndThirdUser(TbThirdUserinfo ttu,TbUser tu);

	void getListBuyer(BuyerListResult buyerListResult,List<Integer> userIdList);
	
	//Buyer getBuyerRedis(int userId,int clientType);
	
	//从redis获取用户信息
	Buyer getBuyerRedis(int userId);
	
	void h5ThirdLogin(H5ThirdLoginResult h5ThirdLoginResult,H5ThirdLoginParam param);
}
