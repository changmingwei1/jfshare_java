package com.jfshare.product.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {
    public static FailDesc SYSTEM_EXCEPTION;
    public static FailDesc PARAM_ERROR;

	public static FailDesc PRODUCT_NULL_ERROR;
	public static FailDesc PRODUCT_SKU_NULL_ERROR;
	public static FailDesc PRODUCT_SNAP_NULL_ERROR;
    public static FailDesc PRODUCT_NOT_EXISTS_ERROR;
	public static FailDesc PRODUCT_STATE_ERROR;
    public static FailDesc PRODUCT_NOT_EDITBALE;

    public static FailDesc MYBATIS_UPDATE_ERROR;


    public static FailDesc PRODUCT_VALIDATE_PRODUCTNAME;
    public static FailDesc PRODUCT_VALIDATE_PRICE;
    public static FailDesc PRODUCT_VALIDATE_MAINIMG;
    public static FailDesc PRODUCT_VALIDATE_DETAIL;


    static {
        SYSTEM_EXCEPTION = new FailDesc("system", "2000", "系统异常");
        PARAM_ERROR = new FailDesc("param", "2001", "参数有误");

		PRODUCT_NULL_ERROR = new FailDesc("product", "3000", "商品信息为空");
		PRODUCT_SKU_NULL_ERROR = new FailDesc("skunum", "3001", "商品SKU为空");
        PRODUCT_NOT_EXISTS_ERROR = new FailDesc("product", "3002", "商品不存在");
		PRODUCT_SNAP_NULL_ERROR = new FailDesc("skunum", "3003", "商品快照为空");

        PRODUCT_STATE_ERROR = new FailDesc("state", "4000", "商品状态已变化");
        PRODUCT_NOT_EDITBALE = new FailDesc("state", "4001", "当前商品不可编辑");

        MYBATIS_UPDATE_ERROR = new FailDesc("mybatis", "5000", "数据库更新失败");

        //商品基本信息校验
        PRODUCT_VALIDATE_PRODUCTNAME = new FailDesc("product", "3100", "商品名称校验失败，名称不可为空且不超过30个字符");
        PRODUCT_VALIDATE_PRICE = new FailDesc("product", "3101", "商品价格校验失败，价格应大于0，原价不可低于现价");
        PRODUCT_VALIDATE_MAINIMG = new FailDesc("product", "3103", "商品主图校验失败，商品主图未上传");
        PRODUCT_VALIDATE_DETAIL = new FailDesc("product", "3103", "商品详情校验失败，商品详情不能为空");
    }
    
    /**
     * 结果集中添加错误码
     * @param result
     * @param failDesc
     */
    public static void addFails(Result result, FailDesc... failDesc) {
		result.setCode(1);
		List<FailDesc> fails = result.getFailDescList();
		if (fails == null) {
		    fails = new ArrayList<FailDesc>();
			result.setFailDescList(fails);
		}
		for (FailDesc item : failDesc) {
			fails.add(item);
		}
	}
    
}
