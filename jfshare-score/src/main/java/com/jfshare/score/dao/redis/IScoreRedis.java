package com.jfshare.score.dao.redis;

public interface IScoreRedis {
	public String getCashAmountByUserid(String key);
	public String getCashAmountByMobile(String mobile);
	public String setCashAmountByUserid(String key, String value);
	public String setCashAmountByMobile(String monile, String value);
	//测试获取1000次redis数据性能
	public int getRedisbyKey(String key,int count);
}
