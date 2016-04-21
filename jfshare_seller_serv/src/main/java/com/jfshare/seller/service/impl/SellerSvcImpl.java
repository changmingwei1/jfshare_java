package com.jfshare.seller.service.impl;

import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.seller.LoginLog;
import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.seller.dao.mysql.ISellerDao;
import com.jfshare.seller.dao.redis.ISellerJedis;
import com.jfshare.seller.model.TbSeller;
import com.jfshare.seller.service.ISellerSvc;
import com.jfshare.seller.util.ConstantUtil;
import com.jfshare.seller.util.FailCode;
import com.jfshare.seller.util.SellerUtil;
import com.jfshare.utils.ConvertUtil;
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
        if (StringUtil.isNullOrEmpty(seller.getPwdEnc()) || !seller.getPwdEnc().matches("^[a-zA-Z0-9_]+$")) {
            FailCode.addFails(result, FailCode.pwdRule);
        }

        int tryFailCount = sellerJedis.getTryFailCount(seller.getLoginName().trim());
        if (tryFailCount >= ConvertUtil.getInt(PropertiesUtil.getProperty("jfx_seller_serv", "try_login_limit", "5"))) {
            FailCode.addFails(result, FailCode.tryTimeLimitError);
            return;
        }
        List<TbSeller> sellers = sellerDao.checkLogin(seller);
        if (sellers.size() != 1) {
            FailCode.addFails(result, FailCode.loginError);
            return;
        }

        TbSeller tbSeller = sellers.get(0);
        seller.setSellerId(tbSeller.getSellerId());
        seller.setLoginName(tbSeller.getLoginName());
        seller.setSellerName(tbSeller.getSellerName());
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
        return false;
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
}
