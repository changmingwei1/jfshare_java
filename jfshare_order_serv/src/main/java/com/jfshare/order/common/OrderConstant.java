package com.jfshare.order.common;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderConstant {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderConstant.class);
	
	 /*
     * 0初始,
		1待支付
		2待发货
		3已发货
		4 退货申请
		5 交易成功
		6交易取消
		7交易关闭
		8 审核中
		99 用户付款中
		98 商家退款中
     */
	public static int INIT_STATE = 0;
	public static int WAIT_PAY_STATE = 10;
    public static int WAIT_AUDIT_STATE = 20;
	public static int WAIT_DELIVER_STATE = 30;
	public static int FINISH_DELIVER_STATE = 40;
	public static int RETURN_APPLY_STATE = 4;
	public static int TRADE_SUCCESS_STATE = 5;
	public static int TRADE_CANCEL_STATE = 6;
	public static int TRADE_CLOSE_STATE = 7;
	public static int PAYING_STATE = 99;

	public static int ORDER_STATE_INIT = 0;
	public static int ORDER_STATE_WAIT_PAY = 1;
	public static int ORDER_STATE_WAIT_DELIVER = 2;
	public static int ORDER_STATE_FINISH_DELIVER = 3;
	public static int ORDER_STATE_RETURN_APPLY = 4;
	public static int ORDER_STATE_TRADE_SUCCESS = 5;
	public static int ORDER_STATE_TRADE_CANCEL = 6;
	public static int ORDER_STATE_TRADE_CLOSE = 7;
	public static int ORDER_STATE_WAIT_AUDIT = 8; //审核中
	public static int ORDER_STATE_PAYING = 99;
	public static int ORDER_STATE_SELLER_REFUND = 98;
	
	public static int ORDER_SETTLEMENT_STATE_INIT = 0;
	public static int ORDER_SETTLEMENT_STATE_WAIT= 1;
	public static int ORDER_SETTLEMENT_STATE_FINISH= 2;
	public static int ORDER_SETTLEMENT_STATE_ALLOW = 3;
	
	public static int PAY_STATE_INIT = 0;
	public static int PAY_STATE_FAIL = -1;
	public static int PAY_STATE_SUCCESS = 1;
	public static int PAY_STATE_ING = 2;
	
	
	
	
	public static String ORDER_CODE_EXCHANGE = "Z0001";
	public static String ORDER_CODE_TEL_FARE = "Z0002";
	public static String ORDER_CODE_ENTITY = "Z0003";
	public static String ORDER_CODE_COD = "Z0004";  //货到付款
	public static String ORDER_CODE_SCORE_CASH = "Z0005"; 
	public static String ORDER_CODE_DAY_HONGBAO = "Z0006"; 
	public static String ORDER_CODE_SUBSIDY_ONEYUANQIANG = "Z9001"; 
	public static String ORDER_CODE_SUBSIDY_XIANSHIQIANG = "Z9002"; 
	public static String ORDER_CODE_SUBSIDY_LIJIAN = "Z9003"; 
	
	
	//10000同意退款、10001退款中、10002退款成功、10003退款失败
	public static int REFUND_STATE_ALLOW = 10000;
	public static int REFUND_STATE_ING = 10001;
	public static int REFUND_STATE_SUCCESS = 10002;
	public static int REFUND_STATE_FAIL = 10003;
	
	public static List<Integer> OrderStates1 = new ArrayList<Integer>();
	public static List<Integer> OrderStates7 = new ArrayList<Integer>();
	public static List<Integer> OrderStates2 = new ArrayList<Integer>();
	static{
		OrderStates1.add(ORDER_STATE_WAIT_PAY);
		OrderStates1.add(ORDER_STATE_PAYING);
		
		OrderStates7.add(ORDER_STATE_TRADE_CLOSE);
		OrderStates7.add(ORDER_STATE_SELLER_REFUND);
		OrderStates7.add(REFUND_STATE_ALLOW);
		OrderStates7.add(REFUND_STATE_ING);
		OrderStates7.add(REFUND_STATE_SUCCESS);
		OrderStates7.add(REFUND_STATE_FAIL);
		
		OrderStates2.add(ORDER_STATE_WAIT_DELIVER);
		OrderStates2.add(ORDER_STATE_WAIT_AUDIT);
	}
	
	
	//商品的状态
	public static int PRODUCT_STATE_INIT = 0;
	public static int PRODUCT_STATE_ONLINE = 1;
	public static int PRODUCT_STATE_OFFLINE = 2;
	public static int PRODUCT_STATE_SLAES = 3;
	
	//商品类型
	public static String PRODUCT_TYPE_SCORE_CASH = "1";
	
	
	public static String STOCK_UPDATE_DB = "db";
	public static String STOCK_UPDATE_REDIS = "redis";
	
	
	
	//订单redis通知
	
	public static String NOTIFY_NEW_ORDER = "new_order_notify";
	public static String NOTIFY_UPDATE_ORDER = "update_order_notify";	
	public static String NOTIFY_STOCK_UPDATE = "stock_update_notify";
	public static String NOTIFY_MOBILE_CHANGE = "mobile_change_notify";
	public static String NOTIFY_EXCHANGE_DELIVER = "exchange_deliver_notify";
	
	
	//orderStateInfo 查询时 order 不存在 返回-1
	public static Integer ORDER_NOT_EXIST = -1;
	
	/*
alipay   支付宝PC支付
alipaywap 支付宝WAP支付
alipayapp 支付宝手机控件支付
 
unionpay 银联pc支付
unionpaywap 银联WAP支付
unionpayapp 银联手机控件支付
 
weixinapp 微信手机控件支付
weixinpub 微信公共号支付
 
tenpay  财付通PC支付
tenpayapp 财付通手机控件支付
	 */
	
	public static String PAY_CHANNEL_ALIPAY= "alipay";
	public static String PAY_CHANNEL_ALIPAY_WAP= "alipaywap";
	public static String PAY_CHANNEL_ALIPAY_APP= "alipayapp";
	public static String PAY_CHANNEL_UNIONPAY= "unionpay";
	public static String PAY_CHANNEL_UNIONPAY_WAP= "unionpaywap";
	public static String PAY_CHANNEL_UNIONPAY_APP= "unionpayapp";
	public static String PAY_CHANNEL_WEIXIN_APP= "weixinapp";
	public static String PAY_CHANNEL_WEIXIN_PUB= "weixinpub";
	public static String PAY_CHANNEL_TENPAY= "tenpay";
	public static String PAY_CHANNEL_TENPAY_APP= "tenpayapp";
	public static String PAY_CHANNEL_WEIXIN_PC= "weixinpc";
	
	public static String PAY_CHANNEL_TENPAY_ICBC_D= "tenpay_icbc_d";
	public static String PAY_CHANNEL_TENPAY_CCB_D= "tenpay_ccb_d";
	public static String PAY_CHANNEL_TENPAY_BOC_D= "tenpay_boc_d";
	public static String PAY_CHANNEL_TENPAY_ABC_D= "tenpay_abc_d";
	public static String PAY_CHANNEL_TENPAY_COMM_D= "tenpay_comm_d";
	public static String PAY_CHANNEL_TENPAY_CMB_D= "tenpay_cmb_d";
	public static String PAY_CHANNEL_TENPAY_CITIC_D= "tenpay_citic_d";
	public static String PAY_CHANNEL_TENPAY_SPDB_D= "tenpay_spdb_d";
	public static String PAY_CHANNEL_TENPAY_TENPAY_CIB_D= "tenpay_cib_d";
	public static String PAY_CHANNEL_TENPAY_CMBC_D= "tenpay_cmbc_d";
	public static String PAY_CHANNEL_WEIXINAPP12 = "weixinapp12";
	public static String PAY_CHANNEL_BAIFUBAOWAP = "baifubaowap";
	public static String PAY_CHANNEL_BAIFUBAO = "baifubao";
	public static String PAY_CHANNEL_UNIONPAYACP = "unionpayacp";
	
	public static String PAY_CHANNEL_BAIFUBAO_401_D = "baifubao_401_d";
	public static String PAY_CHANNEL_BAIFUBAO_501_D = "baifubao_501_d";
	public static String PAY_CHANNEL_BAIFUBAO_1903_D = "baifubao_1903_d";
	public static String PAY_CHANNEL_YIXIN_PUB = "yixinpub";

    public static String PAY_CHANNEL_BAIFUBAO_4015_D_EP = "baifubao_4015_d_ep";
    public static String PAY_CHANNEL_BAIFUBAO_4017_D_EP = "baifubao_4017_d_ep";
    public static String PAY_CHANNEL_BAIFUBAO_4008_D_EP = "baifubao_4008_d_ep";

	public static String PAY_CHANNEL_WEIXIN_WAP = "weixinwap";
	public static String PAY_CHANNEL_UNIONPAYACP_APP = "unionpayacpapp";
	public static String PAY_CHANNEL_UNIONPAYACP_WAP = "unionpayacpwap";

	public static String PAY_CHANNEL_BAIFUBAO_1902_D = "baifubao_1902_d";

	public static Map<String, Integer> payChannelMap = new HashMap<String, Integer>();
	public static Map<Integer, String> payChannelDBMap = new HashMap<Integer, String>();
	

	/**
	 * 订单取消类型
	 */
	public static final Integer CANCEL_DEFAULT = 0;
	public static final Integer SYSTEM_CANCEL = 101;
	public static final Integer BUYER_CANCEL = 102;
	public static final Integer REFUND_CANCEL = 103;
	public static final Integer COMPLAIN_CANCEL = 104;
	//是黄牛订单，取消
	public static final Integer SCALPER_CANCEL = 105;
	
	
	/**
	 * 手动取消订单内容描述
	 */
	public static final Integer CANCEL_REASON_DEFAULT = 0;
	public static final Integer CANCLE_REASON_DROP_IT = 1;
	public static final Integer CANCLE_REASON_MSG_ERROR = 2;
	public static final Integer CANCLE_REASON_REBUY = 3;
	public static final Integer CANCLE_REASON_OUT_OF_STOCK = 4;
	public static final Integer CANCLE_REASON_FACE_TO_FACE_DEAL = 5;
	public static final Integer CANCLE_REASON_OTHER = 6;
	
	//订单从属表type字段，送积分
	public static final Integer ORDERSUBORDINATE_TYPE_GIVE = 1;
	
	/**
	 * 超卖订单状态
	 * 0：正常，1：超卖
	 */
	public static final Integer ORDER_TAG_NOT_OVER = 0;
	public static final Integer ORDER_TAG_OVER = 1;
	
	/**
	 * 超卖订单处理状态
	 * 0为未处理，  1为已经处理
	 */
	public static final Integer OVERSOLDORDER_WAIT = 0;
	public static final Integer OVERSOLDORDER_NOT_WAIT = 1;
	
	/**
	 * 超卖订单处理方式
	 * handleType 0 发货， 1 退款
	 */
	public static final Integer OVERSOLDORDER_DELIVER = 0;
	public static final Integer OVERSOLDORDER_REFUND = 1;
	
	
	public static final Integer ORDER_OVERSOLD_CHECK_STATE =1000;
	
	
	public static final String ORDER_OPERATOR_CREATE = "create";
	public static final String ORDER_OPERATOR_CANCEL = "cancel";
	public static final String ORDER_OPERATOR_PAY = "pay";
	public static final String ORDER_OPERATOR_DELIVER = "deliver";

	public static final String ORDER_OPERATOR_DELIVER_BATCH = "deliver_batch";	
	public static final String ORDER_OPERATOR_DELIVER_REMIND = "deliver_remind";	
	public static final String ORDER_OPERATOR_UPDATE_EXPRESS = "update_express";
	public static final String ORDER_OPERATOR_UPDATE_ADDRESS = "update_address";
	public static final String ORDER_OPERATOR_PAYING = "paying";
	public static final String ORDER_OPERATOR_CONFIRM_RECEIPT = "confirm_receipt";
	public static final String ORDER_OPERATOR_COMPLAIN_STATE = "compalin_state";
	public static final String ORDER_OPERATOR_REFUND_STATE = "refund_state";
	public static final String ORDER_OPERATOR_TEL_CHARGE_FINISH = "tel_charge_finish";
	
	public static String ORDER_OPERATOR_COMPLAIN_CLOSE = "complain_close";
	public static String ORDER_OPERATOR_REFUND_CLOSE = "refund_close";
	
	public static String ORDER_OPERATOR_OVER_SOLD_CLOSE = "over_sold_close";
	
	public static String ORDER_OPERATOR_UPDATE_SETTLEMENT_FINISH = "settlement_finish";
	public static String ORDER_OPERATOR_UPDATE_SETTLEMENT_STATE = "update_settlement_state";
	
	public static String ORDER_OPERATOR_OPEN_COMPLAIN = "open_complain";
	public static String ORDER_OPERATOR_OPEN_REFUND = "open_refund";

	public static String ORDER_OPERATOR_CHECK_PASS = "order_check_pass";
	public static final String ORDER_CLOSE_SCALPER= "order_close_scalper";

	public static final String ORDER_OPERATOR_UPDATE_ORDER = "update_order";
	public static final String ORDER_OPERATOR_UPDATE_POSTAGE = "update_postage";
	
	/** 订单删除状态, revert:从永久删除恢复;delete:永久删除订单*/
	public static final String ORDER_DELETE_STATE_REVERT = "order_revert_cs";
	public static final String ORDER_DELETE_STATE_COMPLETE_DELETE = "order_delete_buyer";
	
	public static String FROM_SOURCE_WEB = "jfshare-web";
	public static String FROM_SOURCE_MOBILE = "jfshare-mobile";
	
	public static int DB_THREAD_TIMEOUT = 5000;
	
	/** 订单的售后维权期限 ：交易成功后 20天*/
	public static long ORDER_REFUND_EXPIRE = 20 * 24 * 60 * 60 * 1000;
	
	/**订单删除状态 0:初始 1:回收站中 2:永久删除*/
	public static int ORDER_STATE_NORMAL = 0;
	public static int ORDER_STATE_DELETED = 1;
	public static int ORDER_STATE_COMPLETE_DELETED = 2;
	
	/** redis key 分隔符 */
	public static String SEPARATOR = ":";
	/** redis 支付状态 key 的前缀 */
	public static String PAY_STATE_KEY_PREFIX = "pay";
	/** 支付状态： 未支付 */
	public static String PAY_STATE_WAIT = "0";
	/** 支付状态： 支付完成 */
	public static String PAY_STATE_FINISH = "1";
	

	/**订单待处理数据的类型值 tb_order_pending_info表的handle_type*/
	public static int ORDER_PENDING_15UNPAID_PUSH = 1;//15分钟未支付通知 
	public static int ORDER_PENDING_UNPAID_TIMEOUT_CLOSE = 2;//订单未支付超时关闭订单
	public static int ORDER_PENDING_WAIT_AUDIT = 3;//订单状态审核中变为待发货
	
	
	public static String getFromSource(int from)
	{
	 	String fromSource = OrderConstant.FROM_SOURCE_WEB;
	 	if(from == 2)
	 		fromSource = OrderConstant.FROM_SOURCE_MOBILE;
	 	
	 	return fromSource;
	}
	
	
	/**
	 * 提供给无线通知买家的状态类型值
	 * @author lenovo
	 *
	 */
	public static class OrderPushStatusEnum {
		
		/**1:卖家已发货*/
		public final static int SELLER_CREATE_EXPRESS = 1;
		
		/**2:卖家取消订单*/
		public final static int SELLER_CANCELED = 2;
		
		/**3:退款过程中*/
		public final static int DRAWBACK_PROCESSING = 3;
		
		/**4:售后过程中*/
		public final static int REFUND_PROCESSING = 4;
		
		/**5:维权过程中*/
		public final static int COMPLAIN_PROCESSING = 5;
		
		/**6:退款成功信息*/
		public final static int REFUND_SUCCESS = 6;
		
		/**7:维权结果信息（有退款）*/
		public final static int COMPLAIN_DRAWBACK = 7;
		
		/**8:维权结果信息（无退款）*/
		public final static int COMPLAIN_NO_DRAWBACK = 8;
		
		/**9:申请退款买家超时未处理信息*/
		public final static int DRAWBACK_SELLER_OVERTIME = 9;
		
		/**10:超卖处理订单：若订单状态由“交易关闭”变为“等待卖家发货”*/
		public final static int WAIT_SELLER_RETURN = 10;
		
		/**11:卖家发起维权*/
		public final static int SELLER_COMPLAIN = 11;
		
	}
	
	/** 买家用户 */
	public static int BUYER_USERTYPE = 1;
	/** 卖家用户 */
	public static int SELLER_USERTYPE = 2;
	/** 客服用户 */
	public static int CUSTOMER_SERVICE_USERTYPE = 3;

	//订单支付 通知 redisKey
	public static String ORDER_PAY_NOTIFY = "order_pay_notify";
	
	/**
	 * 活动便宜类型
	 * @author lenovo
	 *
	 */
	public static class CheapAmountType {
		/** 普通活动  */
		public final static int DEFAULT_TYPE = 0; 
		/** 一元抢  */
		public final static int ONE_YUAN_QIANG = 1;
		/** 限时抢  */
		public final static int XIAN_SHI_QIANG = 2;
		/** 拍下立减  */
		public final static int LI_JIAN = 3;

		public final static int PT_MAN_JIAN = 101;
	}
}
