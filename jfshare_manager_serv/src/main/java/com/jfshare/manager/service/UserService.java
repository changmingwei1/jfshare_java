package com.jfshare.manager.service;

import com.jfshare.finagle.thrift.manager.Commissioner;
import com.jfshare.finagle.thrift.manager.LoginLog;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.manager.dao.IUserDao;
import com.jfshare.manager.dao.IUserJedis;
import com.jfshare.manager.model.TbCommissioner;
import com.jfshare.manager.util.BeanConvertUtil;
import com.jfshare.manager.util.FailCode;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.StringUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service(value = "userService")
public class UserService{

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserJedis userJedis;

    public boolean loginNameIsExist(String loginName) {
        int count = userDao.loginNameIsExist(loginName);

        return count > 0 ? true : false;
    }

    public void validUser(Result result, Commissioner commissioner) {
        if (StringUtil.isNullOrEmpty(commissioner.getLoginName()) || commissioner.getLoginName().length() < 6) {
            FailCode.addFails(result, FailCode.loginNameLen);
        }
        if (StringUtil.isNullOrEmpty(commissioner.getPwdEnc()) || !commissioner.getPwdEnc().matches("^[a-zA-Z0-9_]+$")) {
            FailCode.addFails(result, FailCode.pwdRule);
        }

        if (result.getFailDescList() != null && !result.getFailDescList().isEmpty()) {
            return;
        }

        if (loginNameIsExist(commissioner.getLoginName().trim())) {
            FailCode.addFails(result, FailCode.loginNameHasExist);
        }
    }

    public void validPwd(Result result, Commissioner commissioner) {

    }

    public int insert(Commissioner commissioner) {
        TbCommissioner tbCommissioner = BeanConvertUtil.commissioner2TbCommissioner(commissioner);
        tbCommissioner.setCreateTime(new DateTime());
        return userDao.insert(tbCommissioner);
    }

    public void validLogin(Result result, Commissioner commissioner) {
        if (StringUtil.isNullOrEmpty(commissioner.getLoginName()) || commissioner.getLoginName().length() < 6) {
            FailCode.addFails(result, FailCode.loginNameLen);
        }
        if (StringUtil.isNullOrEmpty(commissioner.getPwdEnc()) || !commissioner.getPwdEnc().matches("^[a-zA-Z0-9_]+$")) {
            FailCode.addFails(result, FailCode.pwdRule);
        }

        int tryFailCount = userJedis.getTryFailCount(commissioner.getLoginName().trim());
        if (tryFailCount >= ConvertUtil.getInt(PropertiesUtil.getProperty("jfx_seller_serv", "try_login_limit", "5"))) {
            FailCode.addFails(result, FailCode.tryTimeLimitError);
            return;
        }
        List<TbCommissioner> commissioners = userDao.checkLogin(commissioner);
        if (commissioners.size() != 1) {
            FailCode.addFails(result, FailCode.loginError);
            return;
        }

        TbCommissioner tbCommissioner = commissioners.get(0);
        commissioner.setCsId(tbCommissioner.getCsId());
        commissioner.setLoginName(tbCommissioner.getLoginName());
        commissioner.setCsName(tbCommissioner.getCsName());
    }

    public int addTryFail(Commissioner commissioner) {
        TbCommissioner tbCommissioner = new TbCommissioner();
        tbCommissioner.setLoginName(commissioner.getLoginName().trim());
        return userJedis.addTryFail(tbCommissioner);
    }

    public void addOnline(TbCommissioner tbCommissioner, LoginLog loginLog) {
        userJedis.addOnline(tbCommissioner, loginLog.getTokenId());
    }

    public int clearTryFail(TbCommissioner tbCommissioner) {
        if (tbCommissioner == null || StringUtil.isNullOrEmpty(tbCommissioner.getLoginName())) {
            return 0;
        }
        return userJedis.clearTryFail(tbCommissioner.getLoginName());
    }

    public boolean isOnline(int csId, String tokenId) {
        return userJedis.isOnline(ConvertUtil.getString(csId), tokenId);
    }

    public Commissioner getOnline(int csId, String tokenId) {
        return null;
    }

    public Commissioner getCommissioner(int csId) {
        return null;
    }

    public boolean updatePwd(int csId, String newPwd) {
        return false;
    }

    public boolean updateCommissioner(Commissioner commissioner) {
        return false;
    }

    public void removeOnline(Result result, int csId, String tokenId) {
        int ret = userJedis.removeOnline(ConvertUtil.getString(csId), tokenId);
        if (ret == -1) {
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }
    }
}
