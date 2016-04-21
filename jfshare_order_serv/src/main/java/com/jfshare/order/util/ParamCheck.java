package com.jfshare.order.util;


import com.jfshare.finagle.thrift.pay.PayRet;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.ConvertUtil;
import org.scalactic.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: jerry
 * Date: 12-2-7
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
public class ParamCheck {
	private static Logger logger = LoggerFactory.getLogger(ParamCheck.class);

    public static FailDesc CreateFailDescObject(String name,
            String failCode,
            String desc)
    {
        FailDesc failDesc = new FailDesc();
        failDesc.setName(name);
        failDesc.setFailCode(failCode);
        failDesc.setDesc(desc);
        return  failDesc;
    }

    /**
     * 用户ID 合法性检测
     * @param userId
     * @return
     */
    public static List<FailDesc> UserIdCheck(int userId)
    {
        List<FailDesc> failList = new ArrayList<FailDesc>();
        if (userId <= 0 ) {
        	logger.info("用户id<=0 *******************************");
            failList.add(FailCode.USER_ID_ERROR);
        }
        return failList;
    }
    
    /**
     * 订单ID  合法性检测
     * @param orderId
     * @return
     */
    public static List<FailDesc> OrderIdCheck(String orderId)
    {
        List<FailDesc> failList = new ArrayList<FailDesc>();

        if(orderId == null || orderId.isEmpty())
        {
        	logger.info("订单ID为空 *******************************");
        	failList.add(FailCode.ORDERID_NULL);
        }

        return failList;
    }
    
    /**
	 * 加强版string正则校验
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean isStrMatchReg(String str, String regex) {
		try {
			return str.matches(regex);
		} catch (Exception e) {
			logger.error(e.getStackTrace().toString());
			logger.error("正则式["+regex+"]格式有误！");
			return false;
		}
	}
	

    /**
     * 检查最大的查询数量
     * @param count
     * @return
     */
    public static List<FailDesc> CheckMaxQueryCount(int count)
    {
        List<FailDesc> failList = new ArrayList<FailDesc>();
        if (count > 50 ) {
        	logger.info("最大分页数量大于50！！");
            failList.add(FailCode.OUT_MAX_QUERY_COUNT);
        }
        return failList;
    }

    public static FailDesc checkOrderPayState(int orderState) {
        if (orderState == ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal()) {
            return FailCode.PAY_ORDER_CLOSE;
        } else if (orderState != ConstantUtil.ORDER_STATE.WAIT_PAY.getEnumVal()) {
            return FailCode.PAY_ORDER_PAY_FINISH;
        }

        return null;
    }

    /**
     * 验证支付方式是否有效
     * @param payChannel
     * @return
     */
    public static FailDesc checkPayChannel(int payChannel) {
        boolean payTypeValid = false;
        String payChannels = PropertiesUtil.getProperty("jfx_pay_serv", "pay_channels");
        String[] split = payChannels.split(",");
        for (String item : split) {
            if (item.trim().equals(String.valueOf(payChannel))) {
                payTypeValid = true;
                break;
            }
        }

        return payTypeValid ? null : FailCode.PAY_CHANNEL_NULL;
    }

    public static List<FailDesc> checkOrderPayAmount(List<OrderModel> orderModels, PayRet payRet) {
        List<FailDesc> failList = new ArrayList<FailDesc>();
        int thirdPrices = 0;
        int thirdScores = 0;
        for (OrderModel order : orderModels) {
            thirdPrices += OrderUtil.getPayPrice(order);
            thirdScores += order.getThirdScore();
        }
        if (thirdPrices + thirdScores * ConvertUtil.getInt(PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_score_rate"))
                != payRet.getThirdPrice() + payRet.getThirdScore() * ConvertUtil.getInt(PropertiesUtil.getProperty("jfx_pay_serv", "pay_ty_score_rate"))
        ) {
            logger.error("支付总金额或积分不符， 实际金额:" + thirdPrices + ", 支付结果金额:" + payRet.getThirdPrice()+ "， 实际积分:" + thirdScores + ", 支付结果积分:" + payRet.getThirdScore());
            failList.add(FailCode.THIRD_PRICE＿FAIL);
        } else {
            if (thirdScores != payRet.getThirdScore()) {
                logger.warn("TODO 支付后改变了金额和积分的支付形式，后续更新订单中商品使用的积分");
            }
        }

        return failList;
    }
}
