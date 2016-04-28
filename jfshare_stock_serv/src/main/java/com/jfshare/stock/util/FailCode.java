package com.jfshare.stock.util;

import com.jfshare.finagle.thrift.result.FailDesc;

public class FailCode {
	
	//公共错误代码
	public static final FailDesc SYS_ERROR = 			new FailDesc("SYS_ERROR", "5000", "系统异常");
	
	public static final FailDesc EntryIsNull = 		new FailDesc("DATA_ERROR", "5201", "带转换实体为null");
	public static final FailDesc parametersEmpty = 		new FailDesc("DATA_ERROR", "5001", "库存接口参数不合法");
	public static final FailDesc skuNumIsNull = 		new FailDesc("DATA_ERROR", "5002", "skum编码为null");
	public static final FailDesc stockCountError = 		new FailDesc("DATA_ERROR", "5003", "设置的库存数不能为负数");
	
	public static final FailDesc redisError = 			new FailDesc("REDIS_ERROR", "5101", "服务访问redis发生异常");
	public static final FailDesc deleteStockError = 	new FailDesc("OPT_ERROR", "9001", "删除库存数据失败");
	public static final FailDesc createStockFail = 		new FailDesc("OPT_ERROR", "9002", "创建库存数据失败");
	public static final FailDesc supplyStockFail = 		new FailDesc("OPT_ERROR", "9003", "补充库存失败");
    public static final FailDesc stockNotEnough = 		new FailDesc("OPT_ERROR", "9004", "库存数量不足");
    public static final FailDesc productStockNotExists = new FailDesc("DATA_ERROR", "9005", "该productId在tb_product_stock和tb_product_stock_info中的库存信息不存在");
    
    //查询库存错误代码
    public static final FailDesc stockInfoNotExists = 	new FailDesc("DATA_ERROR", "4041", "库存信息不存在");
}
