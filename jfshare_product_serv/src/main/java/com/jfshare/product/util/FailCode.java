package com.jfshare.product.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {
    public static FailDesc SYSTEM_EXCEPTION = new FailDesc("system", "2000", "系统异常");
    public static FailDesc PARAM_ERROR = new FailDesc("param", "2001", "参数有误");

	public static FailDesc PRODUCT_NULL_ERROR = new FailDesc("product", "3000", "商品信息为空");
	public static FailDesc PRODUCT_SKU_NULL_ERROR = new FailDesc("skunum", "3001", "商品SKU为空");
	public static FailDesc PRODUCT_SNAP_NULL_ERROR = new FailDesc("skunum", "3003", "商品快照为空");
    public static FailDesc PRODUCT_NOT_EXISTS_ERROR = new FailDesc("product", "3002", "商品不存在");
	public static FailDesc PRODUCT_STATE_ERROR = new FailDesc("state", "4000", "商品状态已变化");
    public static FailDesc PRODUCT_NOT_EDITBALE = new FailDesc("state", "4001", "当前商品不可编辑");

    public static FailDesc MYBATIS_UPDATE_ERROR = new FailDesc("mybatis", "5000", "数据库更新失败");

    //商品基本信息校验
    public static FailDesc PRODUCT_VALIDATE_PRODUCTNAME = new FailDesc("product", "3100", "商品名称校验失败，名称不可为空且不超过30个字符");
    public static FailDesc PRODUCT_VALIDATE_PRICE = new FailDesc("product", "3101", "商品价格校验失败，价格应大于0，原价不可低于现价");
    public static FailDesc PRODUCT_VALIDATE_MAINIMG = new FailDesc("product", "3103", "商品主图校验失败，商品主图未上传");
    public static FailDesc PRODUCT_VALIDATE_DETAIL = new FailDesc("product", "3103", "商品详情校验失败，商品详情不能为空");


    //卡密信息错误提示
    public static FailDesc PRODUCT_CARD_GET_FAIL = new FailDesc("productCard", "5500", "获取商品卡密信息失败");
    public static FailDesc PRODUCT_CARD_USE_FAIL = new FailDesc("productCard", "5501", "商品卡密使用失败");
    public static FailDesc PRODUCT_CARD_IMPORT_FAIL = new FailDesc("productCard", "5502", "商品卡密导入失败");
    public static FailDesc PRODUCT_CARD_IMPORT_PRODUCT_ERROR = new FailDesc("productCard", "5503", "选择导入的商品和模板中的商品不一致");

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
