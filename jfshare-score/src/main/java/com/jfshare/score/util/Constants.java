package com.jfshare.score.util;

public class Constants {
    public static final String ZK_POSTURL= "jfx_score_serv";
	//积分兑出天翼方地址
	public static final String POSTURL = "http://116.228.50.38:8090/api/SpOrderManage/PublishSpIntegral";
	//public static final String POSTURL = "http://116.228.55.143/preview/NewAPI/api/SpOrderManage/PublishSpIntegral";
	 
	//积分兑出传输格式
	public static final String CONTENTTYPE = "application/json";
	//积分兑出省编号  目前只针对手机号 默认35
	public static final String PROVINCEID = "35";
	//天翼方要求业务编号
	public static final String SPID = "171269";
	//天翼方要求应用编号
	public static final String APPCODE = "JFX";
	//兑入聚分享要求应用编号
	public static final String ENTER_APPCODE = "TEL";
	//天翼方私密key
    public static final String SINGKEY = "JFX842123445";   
    //兑出积分备注
    public static final String REMARK = "向天翼兑出积分";
    //兑出积分成功状态
    public static final String CASH_STATE_SUCCESS = "0000";
    
  //兑出积分成功状态
    public static final String ENTER_STATE_SUCCESS = "0000";
  //账号绑定成功状态
    public static final String RELA_STATE_SUCCESS = "0000";
    //兑出积分成功消息
    public static final String CASH_SUCCESS = "积分发行成功";
    
    //兑出积分成功消息
    public static final String ENTER_SUCCESS = "积分兑入成功";
  //账号绑定成功消息
    public static final String RELA_ACCOUNT_SUCCESS = "账号绑定成功";
    
    
    //兑出积分最大额
    public static final int MAXAMOUNT = 50000;
    //交易方。 1:中国电信， 2:老树咖啡， 3:聚分享商城   4:聚分享手机商城
    public static final int CASHTRADER = 1;
    /** 交易类型
     *  1:电积分兑换聚分享积分    2:聚分享积分兑换电信积分
     *  3:线下消费抵扣           4:线上消费抵扣
     *  5:聚分享商城购物累积积分  6:聚分享商城活动赠送积分
     */
    public static final Integer CASHTYPE = 2;
    public static final Integer ENTERTYPE = 1;
    
    /* 积分收入、支出标识  1：收入  2：支出 */
    public static final int CASHINOROUT = 2;
    public static final int ENTERINOROUT = 1;	
	/*积分兑出设备类型  
	2	商旅认证手机
	5	会员卡
	7	天翼手机号
	9	固话
	11	宽带帐号
	18	非电信手机号（联通、移动）*/
	public static final String TYDEVICETYPE = "7";
	public static final String NOTTYDEVICETYPE = "18";
	
	public static final String REDIS_MOBILE_CASH = "score:mobile:cash:";
	public static final String REDIS_USERID_CASH = "score:userid:cash:";
	public static final String TRADEID_CASH_USERID = "jfdc-";
	public static final String TRADEID_ENTER_USERID = "jfdr-";
}
