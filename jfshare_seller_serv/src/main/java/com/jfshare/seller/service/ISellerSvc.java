package com.jfshare.seller.service;

import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.seller.LoginLog;
import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.seller.model.TbSeller;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface ISellerSvc {
    boolean sellerIsExist(String loginName);

    void validSeller(Result result, Seller seller);

    public void validPwd(Result result, Seller seller);

    int insert(Seller seller);

    void validLogin(Result result, Seller seller);

    int addTryFail(Seller seller);

    void addOnline(TbSeller tbSeller, LoginLog loginLog);

    int clearTryFail(TbSeller tbSeller);

    boolean isOnline(int sellerId, String tokenId);

    Seller getOnline(int sellerId, String tokenId);

    Seller getSeller(int sellerId);

    boolean updatePwd(int sellerId, String newPwd);

    boolean updateSeller(Seller seller);

    void removeOnline(Result result, int sellerId, String tokenId);

    List<Seller> getSellerList(List<Integer> sellerIds);
}
