package com.jfshare.score.model;

import org.joda.time.DateTime;

public class TbScoreAccountRelaLog {
    private Integer id;

    private String appcode;

    private DateTime requestdate;

    private String sign;

    private String spid;

    private String deviceno;

    private String devicetype;

    private String outcustid;

    private String token;

    private DateTime exceedtime;

    private String type;

    private String sysname;

    private String syscode;

    private String reperrcode;

    private String reperrmsg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode == null ? null : appcode.trim();
    }

    public DateTime getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(DateTime requestdate) {
        this.requestdate = requestdate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid == null ? null : spid.trim();
    }

    public String getDeviceno() {
        return deviceno;
    }

    public void setDeviceno(String deviceno) {
        this.deviceno = deviceno == null ? null : deviceno.trim();
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype == null ? null : devicetype.trim();
    }

    public String getOutcustid() {
        return outcustid;
    }

    public void setOutcustid(String outcustid) {
        this.outcustid = outcustid == null ? null : outcustid.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public DateTime getExceedtime() {
        return exceedtime;
    }

    public void setExceedtime(DateTime exceedtime) {
        this.exceedtime = exceedtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSysname() {
        return sysname;
    }

    public void setSysname(String sysname) {
        this.sysname = sysname == null ? null : sysname.trim();
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode == null ? null : syscode.trim();
    }

    public String getReperrcode() {
        return reperrcode;
    }

    public void setReperrcode(String reperrcode) {
        this.reperrcode = reperrcode == null ? null : reperrcode.trim();
    }

    public String getReperrmsg() {
        return reperrmsg;
    }

    public void setReperrmsg(String reperrmsg) {
        this.reperrmsg = reperrmsg == null ? null : reperrmsg.trim();
    }
}