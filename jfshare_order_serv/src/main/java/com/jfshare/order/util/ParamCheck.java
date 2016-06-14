package com.jfshare.order.util;


import com.jfshare.finagle.thrift.express.ExpressInfo;
import com.jfshare.finagle.thrift.order.BatchDeliverFailInfo;
import com.jfshare.finagle.thrift.order.DeliverInfo;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.finagle.thrift.pay.PayRet;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.order.common.OrderConstant;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.PriceUtils;
import com.jfshare.utils.StringUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.scalactic.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;


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
        int thirdScore2Cach = 0;
        for (OrderModel order : orderModels) {
            thirdPrices += OrderUtil.getPayPrice(order);
            thirdScores += order.getThirdScore();
            for(TbOrderInfoRecord orderInfo : order.getTbOrderInfoList()) {
                thirdScore2Cach += NumberUtils.toInt(orderInfo.getThirdexchangerate());
            }
        }

        logger.info("支付金额校验， order.thirdScores={}, order.thirdScore2Cash={}, order.thirdPrices={}, pay.thirdPrices={}, pay.thirdScores={}", thirdScores, thirdScore2Cach, thirdPrices, payRet.getThirdPrice(), payRet.getThirdScore());
        //天翼尊享模式支付
        if((thirdScores > 0 && thirdScore2Cach > 0 && thirdPrices != payRet.getThirdPrice() + thirdScore2Cach) || (thirdPrices != payRet.getThirdPrice() + payRet.getThirdScore())) {
            logger.error("支付总金额或积分不符， 实际金额:" + thirdPrices + ", 支付结果金额:" + payRet.getThirdPrice()+ "， 实际积分:" + thirdScores + ", 第三方积分抵扣金额:"  + thirdScore2Cach + "支付结果积分:" + payRet.getThirdScore());
            failList.add(FailCode.THIRD_PRICE＿FAIL);
        } else {
            if (thirdScores != payRet.getThirdScore()) {
                logger.warn("TODO 支付后改变了金额和积分的支付形式，后续更新订单中商品使用的积分");
            }
        }

        return failList;
    }

    /**
     * @param orderList
     * @param j 数组下标
     * @param i 数组下标
     * @return 订单编号+快递公司+运单号 相同返回true,其他情况返回false
     */
    public static boolean deliverCanSame(List<Order> orderList, int j, int i) {
        if (orderList.size() <= j || orderList.size() <= i) return false;
        DeliverInfo deliverInfo1 = orderList.get(j).getDeliverInfo();
        DeliverInfo deliverInfo2 = orderList.get(i).getDeliverInfo();
        try {
            if (orderList.get(j).getOrderId().equals(orderList.get(i).getOrderId()) &&
                    deliverInfo1.getExpressName().trim().equals(deliverInfo2.getExpressName().trim()) &&
                    deliverInfo1.getExpressNo().trim().equals(deliverInfo2.getExpressNo().trim())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 不同导入模板不同的物流名称校验规则
     * @param orderList
     * @param resultOrderList
     * @param resultOrderIdList
     * @param resultExpressNoList
     * @param expressList
     * @param templateType
     * @return
     */
    public static List<BatchDeliverFailInfo> checkOrderExpressInfoEx(List<Order> orderList,
                                                                     List<Order> resultOrderList, List<String> resultOrderIdList,
                                                                     List<String> resultExpressNoList, List<ExpressInfo> expressList, int templateType)
    {
        List<BatchDeliverFailInfo> failInfoList = new ArrayList<BatchDeliverFailInfo>();
        for(int i =0 ; i < orderList.size(); i++)
        {
            if(
                    orderList.get(i).getDeliverInfo().getExpressName() == null ||
                            orderList.get(i).getDeliverInfo().getExpressNo() == null ||
                            orderList.get(i).getDeliverInfo().getExpressName().trim().isEmpty() ||
                            orderList.get(i).getDeliverInfo().getExpressNo().trim().isEmpty()) {

                BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                failInfo.setIndex(i);
                failInfo.setOrderId(orderList.get(i).getOrderId());
                failInfo.setDesc("发货失败！快递公司或运单号为空");
                failInfo.setOrder(orderList.get(i));

                failInfoList.add(failInfo);
            }
//            else if (orderList.get(i).getDeliverInfo().getSellerExpressDesc() != null &&
//                    !orderList.get(i).getDeliverInfo().getSellerExpressDesc().trim().isEmpty() &&
//                    orderList.get(i).getDeliverInfo().getSellerExpressDesc().trim().length() > 100) {
//                //有卖家物流备注且超过100个字
//                BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
//                failInfo.setIndex(i);
//                failInfo.setOrderId(orderList.get(i).getOrderId());
//                failInfo.setDesc("发货失败！留言信息不能超过100个字");
//                failInfo.setOrder(orderList.get(i));
//
//                failInfoList.add(failInfo);
//            }
            else  {
                String fuzzyExpressName = orderList.get(i).getDeliverInfo().getExpressName().trim();
                ExpressInfo curExpressInfo = getExpressInfoByFuzzyName(fuzzyExpressName, expressList);

                if (curExpressInfo == null) {
                    BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                    failInfo.setIndex(i);
                    failInfo.setOrderId(orderList.get(i).getOrderId());
                    failInfo.setDesc("发货失败！不支持该快递公司发货");
                    failInfo.setOrder(orderList.get(i));

                    failInfoList.add(failInfo);
                }
                else if (!ParamCheck.validExpressNo(orderList.get(i).getDeliverInfo().getExpressNo().trim(), curExpressInfo)) {
                    BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                    failInfo.setIndex(i);
                    failInfo.setOrderId(orderList.get(i).getOrderId());
                    failInfo.setDesc("发货失败！运单号错误，快递公司和运单号格式不匹配");
                    failInfo.setOrder(orderList.get(i));

                    failInfoList.add(failInfo);
                }
                else {
                    if (curExpressInfo != null) {
                        orderList.get(i).getDeliverInfo().setExpressId(curExpressInfo.getId()+"");
                        orderList.get(i).getDeliverInfo().setExpressName(curExpressInfo.getName());
                        orderList.get(i).getDeliverInfo().setExpressNo(orderList.get(i).getDeliverInfo().getExpressNo().trim());
                    }
                    resultOrderList.add(orderList.get(i));
                    resultOrderIdList.add(orderList.get(i).getOrderId());
                }
            }

            resultExpressNoList.add(orderList.get(i).getDeliverInfo().getExpressNo());
        }

        return failInfoList;
    }

    /**
     * 通过模糊的物流名获取物流信息
     * @param expressFuzzyName 模糊物流名
     * @param expressList 物流列表
     * @return 最匹配的物流信息,失败返回null
     */
    public static ExpressInfo getExpressInfoByFuzzyName(String expressFuzzyName, List<ExpressInfo> expressList) {
        if (expressFuzzyName == null || expressList == null) {
            return null;
        }
        ExpressInfo expressInfo = new ExpressInfo();

        //find maxLen matched NameRule expressFuzzyName=EMSjingjioo  rules=EMS|EMSjingji  match=EMSjingji
        int maxNameRuleLen = 0;
        for (ExpressInfo curExpress : expressList) {
            String curRule = curExpress.getNameRule();
            if (!StringUtil.isNullOrEmpty(curRule) && ParamCheck.isStrMatchReg(expressFuzzyName, curRule) && curRule.length() > maxNameRuleLen) {
                expressInfo = curExpress;
                maxNameRuleLen = curRule.length();
            }
        }

        return maxNameRuleLen > 0 ? expressInfo : null;
    }

    /**
     * 检测运单号是否合法
     * @createTime 2014年5月20日 下午5:37:25
     * @param expressNo
     * @return boolean
     */
    public static boolean validExpressNo(String expressNo, ExpressInfo curExpressInfo) {
        if (StringUtil.isNullOrEmpty(expressNo) || curExpressInfo == null ) return false;
        if (StringUtil.isNullOrEmpty(curExpressInfo.getExpressNoRule())) return isStrMatchReg(expressNo, OrderConstant.DEF_EXPRESS_NO_RULE) ? true : false;

        if (isStrMatchReg(expressNo, curExpressInfo.getExpressNoRule())) {
            return true;
        }

        return false;
    }

    public static List<BatchDeliverFailInfo> checkOrderNotExist(List<Order> orderList, List<String> noExistOrderIdList) {
        List<BatchDeliverFailInfo> failInfoList = new ArrayList<BatchDeliverFailInfo>();

        for(String orderId : noExistOrderIdList)
        {
            for(int i =0;i < orderList.size(); i++)
            {
                if(orderId == orderList.get(i).getOrderId())
                {
                    BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                    failInfo.setIndex(i);
                    failInfo.setOrderId(orderList.get(i).getOrderId());
                    failInfo.setDesc("订单号不存在");
                    failInfo.setOrder(orderList.get(i));

                    failInfoList.add(failInfo);
                }
            }
        }

        return failInfoList;
    }

    /**
     * 获取导入订单状态错误信息
     * @param orderProfile
     * @return
     */
    public static String getOrderStateError(OrderModel orderProfile) {
        String msg = "订单{0}，不能进行发货，请仔细核对！";
        String msg2 = "订单【已发货】，发货时间为{0}，不能再次进行发货，请仔细核对！";
        if (orderProfile.getOrderState() == OrderConstant.ORDER_STATE_FINISH_DELIVER || orderProfile.getOrderState() == OrderConstant.ORDER_STATE_TRADE_SUCCESS) {
            return MessageFormat.format(msg2, DateTimeUtil.DateTimeToStr(orderProfile.getDeliverTime()));
        } else if (orderProfile.getOrderState() == OrderConstant.ORDER_STATE_WAIT_PAY) {
            return MessageFormat.format(msg, "未付款");
        } else if (orderProfile.getOrderState() == OrderConstant.ORDER_STATE_TRADE_CLOSE) {
            return MessageFormat.format(msg, "已关闭");
        } else if (orderProfile.getOrderState() == OrderConstant.ORDER_STATE_WAIT_AUDIT) {
            return MessageFormat.format(msg, "不是待发货状态");
        }

        return "订单状态错误";
    }

    /**
     * 从orderList中找到订单order, 并填充物流信息
     * @param order
     * @param orderList
     */
    public static void fillOrderExpress(Order order, List<Order> orderList) {
        //import order data for expressName、expressNo
        Order importOrder = null;
        if (orderList != null) {
            for (Order item : orderList) {
                if (item.getOrderId() != null && item.getOrderId().equals(order.getOrderId())) {
                    importOrder = item;
                    break;
                }
            }

            //use import expressName、expressNo、sellerExpressDesc
            if (importOrder != null) {
                DeliverInfo deliverInfo = order.getDeliverInfo();
                DeliverInfo importDeliverInfo  = importOrder.getDeliverInfo();
                if (importDeliverInfo !=null) {
                    if (importDeliverInfo.getExpressName() != null && !importDeliverInfo.getExpressName().isEmpty())
                        deliverInfo.setExpressName(importDeliverInfo.getExpressName());
                    if (importDeliverInfo.getExpressNo() != null && !importDeliverInfo.getExpressNo().isEmpty())
                        deliverInfo.setExpressNo(importDeliverInfo.getExpressNo());
                }
            }
        }
    }

    /**
     * 根据所有错误信息，返回每条订单中第一个错误信息
     * @param failInfoList
     * @return
     */
    public static List<BatchDeliverFailInfo> getBatchDeliverFailInfo(
            List<BatchDeliverFailInfo> failInfoList) {
        List<BatchDeliverFailInfo> validFailInfos = new ArrayList<BatchDeliverFailInfo>();
        Set<Integer> indexSet = new HashSet<Integer>();
        for (BatchDeliverFailInfo failInfo : failInfoList) {
            if (!indexSet.contains(failInfo.getIndex())) {
                validFailInfos.add(failInfo);
                indexSet.add(failInfo.getIndex());
            }
        }

        List<BatchDeliverFailInfo> validSortFailInfos = new ArrayList<BatchDeliverFailInfo>();
        Map<Integer, BatchDeliverFailInfo> sortFailIndex = new TreeMap<Integer, BatchDeliverFailInfo>();
        for (BatchDeliverFailInfo item : validFailInfos) {
            sortFailIndex.put(item.getIndex(), item);
        }

        Set<Map.Entry<Integer, BatchDeliverFailInfo>> set = sortFailIndex.entrySet();
        for (Iterator<Map.Entry<Integer, BatchDeliverFailInfo>> it = set.iterator(); it.hasNext();) {
            Map.Entry<Integer, BatchDeliverFailInfo> entry = (Map.Entry<Integer, BatchDeliverFailInfo>) it.next();
            validSortFailInfos.add(entry.getValue());
        }

        return validSortFailInfos;
    }

    /**
     * 返回失败的订单Id集合
     * @param validFailList
     * @return
     */
    public static Set<String> getFailOrders(List<BatchDeliverFailInfo> validFailList) {
        Set<String> idsSet = new HashSet<String>();
        if (validFailList == null) return idsSet;

        for (BatchDeliverFailInfo failInfo : validFailList) {
            idsSet.add(failInfo.getOrderId());
        }

        return idsSet;
    }

    /**
     * 返回可发货订单列表
     * @param orderList
     * @param failOrderIds
     * @return
     */
    public static List<Order> getCanDeliverList(List<Order> orderList,
                                                Set<String> failOrderIds) {
        List<Order> canDeliverList = new ArrayList<Order>();
        Set<String> canDeliverOrderId = new HashSet<String>();
        for (Order order : orderList) {
            if (order.getOrderId() != null && !failOrderIds.contains(order.getOrderId()) &&
                    !canDeliverOrderId.contains(order.getOrderId())) {
                canDeliverList.add(order);
                canDeliverOrderId.add(order.getOrderId());
            }
        }

        return canDeliverList;
    }

    /**
     * 补全Order信息
     * @param order
     * @param tbOrder
     * @return void
     */
    public static void fillDeliverOrderInfo(Order order, OrderModel tbOrder) {
        order.setSellerId(tbOrder.getSellerId());
        order.setUserId(tbOrder.getUserId());
        if (tbOrder.getUserName() != null) {
            order.setUserName(tbOrder.getUserName());
        }
        order.setClosingPrice(PriceUtils.intToStr(tbOrder.getClosingPrice()));
        order.setCreateTime(DateTimeUtil.DateTimeToStr(tbOrder.getCreateTime()));
        if (tbOrder.getOrderState() != null) {
            order.setOrderState(tbOrder.getOrderState());
        }
        if (tbOrder.getTradeCode() != null) {
            order.setTradeCode(tbOrder.getTradeCode());
        }

        DeliverInfo deliverInfo = order.getDeliverInfo();
//		if (tbOrder.getExpressId() != null) {
//		    deliverInfo.setExpressId(Integer.toString(tbOrder.getExpressId()));
//		}
        if (tbOrder.getBuyerComment() != null) {
            deliverInfo.setBuyerComment(tbOrder.getBuyerComment());
        }
        if (tbOrder.getSellerComment() != null) {
            deliverInfo.setSellerComment(tbOrder.getSellerComment());
        }
        if (tbOrder.getReceiverName() != null) {
            deliverInfo.setReceiverName(tbOrder.getReceiverName());
        }
        if (tbOrder.getReceiverMobile() != null) {
            deliverInfo.setReceiverMobile(tbOrder.getReceiverMobile());
        }
        if (tbOrder.getReceiverTele() != null) {
            deliverInfo.setReceiverTele(tbOrder.getReceiverTele());
        }
        if (tbOrder.getProvinceId() != null) {
            deliverInfo.setProvinceId(tbOrder.getProvinceId());
        }
        if (tbOrder.getCityId() != null) {
            deliverInfo.setCityId(tbOrder.getCityId());
        }
        if (tbOrder.getCountyId() != null) {
            deliverInfo.setCountyId(tbOrder.getCountyId());
        }
        if (tbOrder.getReceiverAddress() != null) {
            deliverInfo.setReceiverAddress(tbOrder.getReceiverAddress());
        }
        if (tbOrder.getPostCode() != null) {
            deliverInfo.setPostCode(tbOrder.getPostCode());
        }
    }
}
