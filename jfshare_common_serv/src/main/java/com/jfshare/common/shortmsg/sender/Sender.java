package com.jfshare.common.shortmsg.sender;


import com.jfshare.common.util.Common;
import com.jfshare.common.util.FailCode;
import com.jfshare.common.util.HttpRequest;
import com.jfshare.common.util.MD5Util;
import com.jfshare.finagle.thrift.result.FailDesc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by Lenovo on 2016/3/8.
 */
public class Sender {
    private Logger logger = LoggerFactory.getLogger(Sender.class);

    public void setConfig(MsgServConfig config) {
        this.config = config;
    }

    private MsgServConfig config = null;

    public Sender builder(MsgServConfig config) {
        if(config != null) {
            this.setConfig(config);
        } else {
            this.setConfig(new MsgServConfig());
        }
        return this;
    }

    public Sender builder() {
        this.setConfig(new MsgServConfig());
        return this;
    }

    private Sender() {
        this.setConfig(new MsgServConfig());
    }

    private Sender(MsgServConfig config) {
        this.setConfig(config);
    }

    public FailDesc send(String msg, String teles){
        HashMap<String, String> p = new HashMap<String, String>();
        long dateTime = System.currentTimeMillis()/1000;
        p.put("cpid", this.config.getCpid());
        p.put("password", this.getPwdMD5(this.config.getPassword(), dateTime));
        p.put("channelid", this.config.getChannelid());
        p.put("msg", msg);
        p.put("tele",teles);
        p.put("timestamp", String.valueOf(dateTime));

        try {
            logger.info("send ==> params: {}", p);
            String ret = HttpRequest.postData("http://admin.sms9.net/houtai/sms.php", p, "gbk");
            logger.info(ret);
            if(StringUtils.isNotBlank(ret) && !ret.startsWith("success:")) {
                if(Common.MsgFailCode.send_num_out_of_limit.getCode().equals(ret.split(":")[1])) {
                    return FailCode.SEND_MSG_COUNT_OUT_OF_LIMIT;
                } else{
                    return FailCode.SEND_MSG_FAILURE;
                }
            }
        } catch (Exception e) {
            logger.info("send ==> exception", e);
        }

        return null;
    }

    private String getPwdMD5(String password, long dateTime) {
        String str = password + "_" + dateTime + "_" + MsgServConfig.TOPSKY;
        try {
            return MD5Util.md5Encode(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
