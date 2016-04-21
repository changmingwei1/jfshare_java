package com.jfshare.product.model.enums;

import com.jfshare.product.commons.ProductCommons;

/**
 * Created by Lenovo on 2015/12/20.
 */
public enum ProductOptEnum{
    PRODUCT_OPT_CREATE_SELLER(ProductCommons.PRODUCT_STATE_CREATE, "商品创建"),
    PRODUCT_OPT_OFFLINE_SELLER(ProductCommons.PRODUCT_STATE_OFFLINE_SELLER, "商家下架"),
    PRODUCT_OPT_REVIEW_UNPASS_CS(ProductCommons.PRODUCT_STATE_REVIEW_UNPASS, "商品审核未通过"),
    PRODUCT_OPT_OFFLINE_CS(ProductCommons.PRODUCT_STATE_OFFLINE_CS, "管理员下架"),
    PRODUCT_OPT_REVIEW_APPLY_SELLER(ProductCommons.PRODUCT_STATE_REVIEW_APPLY, "商品申请上架"),
    PRODUCT_OPT_REVIEW_PASS_CS(ProductCommons.PRODUCT_STATE_ONSELL, "商品审核通过"),
    PRODUCT_OPT_UNKNOWN(999, "")
    ;
    private int state;
    private String desc;
    ProductOptEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByState(int state) {
        String desc = "";
        switch (state) {
            case 100:{
                desc = ProductOptEnum.PRODUCT_OPT_CREATE_SELLER.getDesc();
                break;
            }
            case 101:{
                desc = ProductOptEnum.PRODUCT_OPT_OFFLINE_SELLER.getDesc();
                break;
            }
            case 102:{
                desc = ProductOptEnum.PRODUCT_OPT_REVIEW_UNPASS_CS.getDesc();
                break;
            }
            case 103:{
                desc = ProductOptEnum.PRODUCT_OPT_OFFLINE_CS.getDesc();
                break;
            }
            case 200:{
                desc = ProductOptEnum.PRODUCT_OPT_REVIEW_APPLY_SELLER.getDesc();
                break;
            }
            case 300:{
                desc = ProductOptEnum.PRODUCT_OPT_REVIEW_PASS_CS.getDesc();
                break;
            }
            default:{
                desc = ProductOptEnum.PRODUCT_OPT_UNKNOWN.getDesc();
            }
        }

        return desc;
    }
}
