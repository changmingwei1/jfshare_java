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
        }
        return seller;
    }
}
