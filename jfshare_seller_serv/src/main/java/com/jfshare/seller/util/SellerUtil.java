package com.jfshare.seller.util;

import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.seller.model.TbSeller;
import com.jfshare.utils.CryptoUtil;
import com.jfshare.utils.StringUtil;

/**
 * Created by lenovo on 2015/11/19.
 */
public class SellerUtil {
    public static TbSeller seller2TbSeller(Seller seller) {
        TbSeller tbSeller = new TbSeller();
        tbSeller.setSellerId(seller.getSellerId());
        tbSeller.setSellerName(seller.getSellerName());
        tbSeller.setLoginName(seller.getLoginName());
        tbSeller.setAccountHolder(seller.getAccountHolder());
        tbSeller.setAccountNumber(seller.getAccountNumber());
        tbSeller.setAddress(seller.getAddress());
        tbSeller.setCityid(seller.getCityId());
        tbSeller.setCityname(seller.getCityName());
        tbSeller.setCompanyName(seller.getCompanyName());
        tbSeller.setContactName(seller.getContactName());
        tbSeller.setCountyid(seller.getCountyId());
        tbSeller.setCountyname(seller.getCountyName());
        tbSeller.setOpenBank(seller.getOpenBank());
        tbSeller.setProvinceid(seller.getProvinceId());
        tbSeller.setProvincename(seller.getProvinceName());
        tbSeller.setRemark(seller.getRemark());
        tbSeller.setShopName(seller.getShopName());
        tbSeller.setMobile(seller.getMobile());
        tbSeller.setTel(seller.getTel());
        tbSeller.setEmail(seller.getEmail());
        
        if (!StringUtil.isNullOrEmpty(seller.getPwdEnc())) {
            tbSeller.setPwdEnc(CryptoUtil.encryptDBPwd(seller.getPwdEnc().trim()));
        }
        return tbSeller;
    }

    public static Seller tbSeller2Seller(TbSeller tbSeller) {
        Seller seller = new Seller();
        if(tbSeller != null) {
            seller.setSellerId(tbSeller.getSellerId());
            seller.setSellerName(tbSeller.getSellerName());
            seller.setLoginName(tbSeller.getLoginName());
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
        }
        return seller;
    }
}
