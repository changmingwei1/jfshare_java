package com.jfshare.seller.model;

import org.joda.time.DateTime;

public class TbUserSellerRela {
    private Integer id;

    private String userId;

    private DateTime createtime;

    private String sellerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public DateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(DateTime createtime) {
        this.createtime = createtime;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }
}