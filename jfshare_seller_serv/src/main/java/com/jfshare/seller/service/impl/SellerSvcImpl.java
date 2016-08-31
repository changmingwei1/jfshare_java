package com.jfshare.seller.service.impl;

import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.seller.LoginLog;
import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.finagle.thrift.seller.SellerVip;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.seller.dao.mysql.ISellerDao;
import com.jfshare.seller.dao.redis.ISellerJedis;
import com.jfshare.seller.model.TbSeller;
import com.jfshare.seller.model.TbUserSellerRela;
import com.jfshare.seller.server.depend.BuyerClient;
import com.jfshare.seller.service.ISellerSvc;
import com.jfshare.seller.util.ConstantUtil;
import com.jfshare.seller.util.FailCode;
import com.jfshare.seller.util.SellerUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service
public class SellerSvcImpl implements ISellerSvc {

    @Autowired
    private ISellerDao sellerDao;
    
    @Autowired
    private BuyerClient buyerClient;
    
    
    @Autowired
    private ISellerJedis sellerJedis;

    @Override
    public boolean sellerIsExist(String loginName) {
        int count = sellerDao.sellerIsExist(loginName);

        return count > 0 ? true : false;
    }

    @Override
    public void validSeller(Result result, Seller seller) {
        if (StringUtil.isNullOrEmpty(seller.getLoginName()) || seller.getLoginName().length() < 6) {
            FailCode.addFails(result, FailCode.loginNameLen);
        }
        if (StringUtil.isNullOrEmpty(seller.getPwdEnc()) || !seller.getPwdEnc().matches("^[a-zA-Z0-9_]+$")) {
            FailCode.addFails(result, FailCode.pwdRule);
        }

        if (result.getFailDescList() != null && !result.getFailDescList().isEmpty()) {
            return;
        }

        if (sellerIsExist(seller.getLoginName().trim())) {
            FailCode.addFails(result, FailCode.loginNameHasExist);
        }
    }

    @Override
    public void validPwd(Result result, Seller seller) {

    }

    @Override
    public int insert(Seller seller) {
        TbSeller tbSeller = SellerUtil.seller2TbSeller(seller);
        tbSeller.setCreateTime(new DateTime());
        return sellerDao.insert(tbSeller);
    }

    @Override
    public void validLogin(Result result, Seller seller) {
        if (StringUtil.isNullOrEmpty(seller.getLoginName()) || seller.getLoginName().length() < 6) {
            FailCode.addFails(result, FailCode.loginNameLen);
        }
//        if (StringUtil.isNullOrEmpty(seller.getPwdEnc()) || !seller.getPwdEnc().matches("^[a-zA-Z0-9_]+$")) {
	    if (StringUtil.isNullOrEmpty(seller.getPwdEnc())) {
	        FailCode.addFails(result, FailCode.pwdRule);
        }

        int tryFailCount = sellerJedis.getTryFailCount(seller.getLoginName().trim());
        if (tryFailCount >= ConvertUtil.getInt(PropertiesUtil.getProperty("jfx_seller_serv", "try_login_limit", "5"))) {
            FailCode.addFails(result, FailCode.tryTimeLimitError);
            return;
        }
        List<TbSeller> sellers = sellerDao.checkLogin(seller);
        if (sellers.size() < 1) {
            FailCode.addFails(result, FailCode.loginError);
            return;
        }

        TbSeller tbSeller = sellers.get(0);
        seller.setSellerId(tbSeller.getSellerId());
        seller.setLoginName(tbSeller.getLoginName());
        seller.setSellerName(tbSeller.getSellerName());
        
        seller.setAccountHolder(tbSeller.getAccountHolder());
        seller.setAccountNumber(tbSeller.getAccountNumber());
        seller.setAddress(tbSeller.getAddress());
        seller.setCityId(tbSeller.getCityid());
        seller.setCityName(tbSeller.getCityname());
        seller.setCompanyName(tbSeller.getCompanyName());
        seller.setContactName(tbSeller.getContactName());
        seller.setCountyId(tbSeller.getCountyid());
        seller.setCountyName(tbSeller.getCountyname());
        seller.setOpenBank(tbSeller.getOpenBank());
        seller.setProvinceId(tbSeller.getProvinceid());
        seller.setProvinceName(tbSeller.getProvincename());
        seller.setRemark(tbSeller.getRemark());
        seller.setShopName(tbSeller.getShopName());
        seller.setMobile(tbSeller.getMobile());
        seller.setTel(tbSeller.getTel());
        seller.setEmail(tbSeller.getEmail());
        seller.setCreateTime(tbSeller.getCreateTime().toString(("yyyy-MM-dd")));
    }

    @Override
    public int addTryFail(Seller seller) {
        TbSeller user = new TbSeller();
        user.setLoginName(seller.getLoginName().trim());
        return sellerJedis.addTryFail(user);
    }

    @Override
    public void addOnline(TbSeller tbSeller, LoginLog loginLog) {
        sellerJedis.addOnline(tbSeller, loginLog.getTokenId());
    }

    @Override
    public int clearTryFail(TbSeller tbSeller) {
        if (tbSeller == null || StringUtil.isNullOrEmpty(tbSeller.getLoginName())) {
            return 0;
        }
        return sellerJedis.clearTryFail(tbSeller.getLoginName());
    }

    @Override
    public boolean isOnline(int sellerId, String tokenId) {
        boolean isOnline = false;
        int signinModel = NumberUtils.toInt(PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, ConstantUtil.SIGNIN_MODEL), 0);
        if(signinModel == ConstantUtil.SIGNIN_MODEL_SINGLE) {
            isOnline = this.sellerJedis.isOnline(ConvertUtil.getString(sellerId), tokenId);
        } else {
            TbSeller tbSeller = this.sellerJedis.getOnlineBySID(tokenId);
            if(tbSeller != null && tbSeller.getSellerId().intValue() == sellerId) {
                isOnline = true;
            }
        }
        return isOnline;
    }

    @Override
    public Seller getOnline(int sellerId, String tokenId) {
        return null;
    }

    @Override
    public Seller getSeller(int sellerId) {
        TbSeller tbSeller = sellerDao.getSeller(sellerId);
        return SellerUtil.tbSeller2Seller(tbSeller);
    }

    @Override
    public boolean updatePwd(int sellerId, String newPwd) {
        return false;
    }

    @Override
    public boolean updateSeller(Seller seller) {
    	 TbSeller tbSeller = SellerUtil.seller2TbSeller(seller);
         tbSeller.setCreateTime(new DateTime());
         int i = sellerDao.update(tbSeller);
 
        return i > 0 ? true : false;
    }

    @Override
    public void removeOnline(Result result, int sellerId, String tokenId) {
        int ret = sellerJedis.removeOnline(ConvertUtil.getString(sellerId), tokenId);
        if (ret == -1) {
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }
    }

    @Override
    public List<Seller> getSellerList(List<Integer> sellerIds) {
        List<TbSeller> tbSellers = sellerDao.getSellerList(sellerIds);
        List<Seller> sellers = new ArrayList<Seller>();
        for (TbSeller item : tbSellers) {
            sellers.add(SellerUtil.tbSeller2Seller(item));
        }

        return sellers;
    }

	@Override
	public List<Seller> getSellerBySeller(Seller seller) {
		List<TbSeller> tbSellers = sellerDao.getSellerBySeller(seller);
        List<Seller> sellers = new ArrayList<Seller>();
        for (TbSeller item : tbSellers) {
            sellers.add(SellerUtil.tbSeller2Seller(item));
        }
        
		return sellers;
	}

	@Override
	public int insertUserSellerRela(String userId, String sellerId) {
		TbUserSellerRela tb = new TbUserSellerRela();
		tb.setUserId(userId);
		tb.setSellerId(sellerId);
		sellerDao.deleteByuserId(userId);
		int row = sellerDao.insertUserSellerRela(tb);
		return row;
	}

	@Override
	public int deleteUserSellerRealByuserId(String userId) {
		int delRows = sellerDao.deleteByuserId(userId);
		return delRows;
	}

	@Override
	public List<SellerVip> querySellerVipList(String sellerId) {
		List<SellerVip> vipList =  new ArrayList<SellerVip>();//会员列表list(包含会员总数)
		List<TbUserSellerRela> usrList= sellerDao.selectUsrListBySellerId(sellerId);//查询卖家会员列表
		if(!usrList.isEmpty()){
			
			 Buyer buyer = new  Buyer();//买家信息
			for(TbUserSellerRela tb: usrList){
				if(!StringUtil.isNullOrEmpty(tb.getUserId())){
					   buyer = buyerClient.queryBuyerByUserId(tb.getUserId());//调用买家服务，查询买家用户信息
				}
			    if(buyer != null){
					SellerVip sellerVip = new SellerVip();//卖家信息

			    	if(!StringUtil.isNullOrEmpty(buyer.getFavImg())){
			    		sellerVip.setFavimg(buyer.getFavImg());
			    	}if(!StringUtil.isNullOrEmpty(buyer.getLoginName())){
			    		sellerVip.setUsername(buyer.getLoginName());
			    	}
			    	sellerVip.setRegedate(DateUtils.date2Str(tb.getCreatetime().toDate(), DateUtils.Simple_DateTime_Format));
					vipList.add(sellerVip);
			    }  
			}
		}
		return vipList;
	}
    
    
}
