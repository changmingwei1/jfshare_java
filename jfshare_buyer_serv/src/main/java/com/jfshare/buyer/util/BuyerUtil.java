package com.jfshare.buyer.util;

import com.jfshare.buyer.model.TbUser;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.CryptoUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;

import java.util.Map;

/**
 * Created by lenovo on 2015/11/19.
 */
public class BuyerUtil {
    public static TbUser buyer2TbUser(Buyer buyer) {
        TbUser user = new TbUser();
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(buyer);
        BeanUtil.fillBeanData(user, stringObjectMap);
        user.setUserId(buyer.getUserId());
        user.setUserName(buyer.getUserName());
        if (!StringUtil.isNullOrEmpty(buyer.getPwdEnc())) {
            user.setPwdEnc(CryptoUtil.encryptDBPwd(buyer.getPwdEnc().trim()));
        }
        user.setBirthday(DateUtils.strToDateTime(buyer.getBirthday()));

        return user;
    }

    public static Buyer tbUser2Buyer(TbUser tbUser) {
        Buyer buyer = new Buyer();
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbUser);
        BeanUtil.fillBeanData(buyer, stringObjectMap);
        buyer.setUserId(tbUser.getUserId());
        buyer.setUserName(tbUser.getUserName());

        return buyer;
    }
}
