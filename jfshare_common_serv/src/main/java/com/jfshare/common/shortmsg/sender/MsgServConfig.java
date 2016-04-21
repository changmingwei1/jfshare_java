package com.jfshare.common.shortmsg.sender;

import com.jfshare.ridge.PropertiesUtil;

/**
 * Created by Lenovo on 2016/3/8.
 */
public class MsgServConfig {

    private String cpid;
    private String password;
    private String channelid;
    public static final String TOPSKY = "topsky";

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public MsgServConfig(String cpid, String password, String channelid){
        this.cpid = cpid;
        this.cpid = password;
        this.cpid = channelid;
    }

    public MsgServConfig(){
//        this.cpid = PropertiesUtil.getProperty("jfx_common_serv", "cpid", "13088");
//        this.cpid = PropertiesUtil.getProperty("jfx_common_serv", "password", "587321");
//        this.cpid = PropertiesUtil.getProperty("jfx_common_serv", "channelid", "1462");
    }
}
