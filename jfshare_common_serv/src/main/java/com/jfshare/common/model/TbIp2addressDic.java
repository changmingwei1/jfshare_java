package com.jfshare.common.model;

public class TbIp2addressDic {
    private Integer id;

    private Integer ipStartInt;

    private String ipStartStr;

    private Integer ipEndInt;

    private String ipEndStr;

    private Integer cityId;

    private String cityName;

    private Integer provinceId;

    private String provinceName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIpStartInt() {
        return ipStartInt;
    }

    public void setIpStartInt(Integer ipStartInt) {
        this.ipStartInt = ipStartInt;
    }

    public String getIpStartStr() {
        return ipStartStr;
    }

    public void setIpStartStr(String ipStartStr) {
        this.ipStartStr = ipStartStr == null ? null : ipStartStr.trim();
    }

    public Integer getIpEndInt() {
        return ipEndInt;
    }

    public void setIpEndInt(Integer ipEndInt) {
        this.ipEndInt = ipEndInt;
    }

    public String getIpEndStr() {
        return ipEndStr;
    }

    public void setIpEndStr(String ipEndStr) {
        this.ipEndStr = ipEndStr == null ? null : ipEndStr.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }
}