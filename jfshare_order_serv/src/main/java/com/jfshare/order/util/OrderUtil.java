package com.jfshare.order.util;

import com.jfshare.finagle.thrift.aftersale.AfterSaleOrder;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderParam;
import com.jfshare.finagle.thrift.order.*;
import com.jfshare.finagle.thrift.trade.BuyInfo;
import com.jfshare.order.common.Commons;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.BizUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.PriceUtils;
import com.jfshare.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2015/10/27.
 */
public class OrderUtil {
    /**
     *  OrderModel->OrderDetail
     * @param orderModel
     * @return
     */
    public static Order rConvertOrderModel(OrderModel orderModel) {
        if(orderModel == null) {
            return new Order();
        }
        Order orderDetail = new Order();
        orderDetail.setActiveState(orderModel.getActiveState());
        orderDetail.setClosingPrice(PriceUtils.intToStr(orderModel.getClosingPrice()));
        orderDetail.setBuyerComment(orderModel.getBuyerComment());
        orderDetail.setOrderStateTypeDesc(OrderCancelReason.findByValue(orderModel.getOrderStateTypeDesc()));
        orderDetail.setCancelTime(DateTimeUtil.DateTimeToStr(orderModel.getCancelTime()));
        orderDetail.setCreateTime(DateTimeUtil.DateTimeToStr(orderModel.getCreateTime()));
        orderDetail.setDeliverTime(DateTimeUtil.DateTimeToStr(orderModel.getDeliverTime()));
        orderDetail.setExchangeCash(PriceUtils.intToStr(orderModel.getExchangeCash()));
        orderDetail.setExchangeScore(orderModel.getExchangeScore());
        orderDetail.setFromSource(orderModel.getFromSource());
        orderDetail.setSellerId(orderModel.getSellerId());
        orderDetail.setSellerName(orderModel.getSellerName());
        orderDetail.setUserId(orderModel.getUserId());
        orderDetail.setOrderId(orderModel.getOrderId());
        orderDetail.setTradeCode(orderModel.getTradeCode());
        orderDetail.setThirdId(orderModel.getThirdId());
        orderDetail.setOrderState(orderModel.getOrderState());
        orderDetail.setSuccessTime(DateTimeUtil.DateTimeToStr(orderModel.getSuccessTime()));
        orderDetail.setLastUpdateTime(DateTimeUtil.DateTimeToStr(orderModel.getLastUpdateTime()));
        orderDetail.setUserName(orderModel.getUserName());
        orderDetail.setSettlementState(orderModel.getSettlementState());
        orderDetail.setSettlementTime(DateTimeUtil.DateTimeToStr(orderModel.getSettlementTime()));
        orderDetail.setOrderStateType(OrderStateType.findByValue(orderModel.getOrderStateType()));
        orderDetail.setOrderTag(orderModel.getOrderTag());
        orderDetail.setWi(orderModel.getWi());
        orderDetail.setTradePayId(orderModel.getTradePayId());
        orderDetail.setActiveState(orderModel.getActiveState());
        orderDetail.setOrderBatch(orderModel.getOrderBatch());
        orderDetail.setThirdScore(orderModel.getThirdScore());
        orderDetail.setPostage(PriceUtils.intToStr(orderModel.getPostage()));
        orderDetail.setPostageExt(orderModel.getPostageext());
        orderDetail.setOrderType(orderModel.getOrderType());
        orderDetail.setThirdPrice(PriceUtils.intToStr(orderModel.getThirdPrice()));

        DeliverInfo deliverInfo = new DeliverInfo();
        deliverInfo.setReceiverTele(orderModel.getReceiverTele());
        deliverInfo.setReceiverMobile(orderModel.getReceiverMobile());
        deliverInfo.setReceiverName(orderModel.getReceiverName());
        deliverInfo.setReceiverAddress(orderModel.getReceiverAddress());
        deliverInfo.setExpressName(orderModel.getExpressName());
        deliverInfo.setBuyerComment(orderModel.getBuyerComment());
        deliverInfo.setAddressId(orderModel.getAddressId());
        deliverInfo.setCityId(orderModel.getCityId());
        deliverInfo.setCityName(orderModel.getCityName());
        deliverInfo.setCountyId(orderModel.getCountyId());
        deliverInfo.setCountyName(orderModel.getCountyName());
        deliverInfo.setProvinceId(orderModel.getProvinceId());
        deliverInfo.setProvinceName(orderModel.getProvinceName());
        deliverInfo.setExpressId(orderModel.getExpressId() + "");
        deliverInfo.setExpressNo(orderModel.getExpressNo());
        deliverInfo.setExpressName(orderModel.getExpressName());
        deliverInfo.setPostCode(orderModel.getPostCode());
        deliverInfo.setUserId(orderModel.getUserId());
        deliverInfo.setOrderId(orderModel.getOrderId());
        deliverInfo.setBuyerComment(orderModel.getBuyerComment());
        deliverInfo.setSellerComment(orderModel.getSellerComment());
        deliverInfo.setPostCode(orderModel.getPostCode());
        orderDetail.setDeliverInfo(deliverInfo);

        PayInfo payInfo = new PayInfo();
        payInfo.setPayAccount(orderModel.getPayAccount());
        if(orderModel.getPayChannel() != null) {
            payInfo.setPayChannel(orderModel.getPayChannel()+"");
        }
        payInfo.setPayId(orderModel.getPayId());
        payInfo.setPayState(orderModel.getPayState());
        payInfo.setPayTime(DateTimeUtil.DateTimeToStr(orderModel.getPayTime()));
        orderDetail.setPayInfo(payInfo);

        List<OrderInfo> productInfos = new ArrayList<OrderInfo>();
        for(TbOrderInfoRecord tbOrderInfoRecord : orderModel.getTbOrderInfoList()) {
            OrderInfo productInfo = new OrderInfo();
            productInfo.setProductId(tbOrderInfoRecord.getProductId());
            productInfo.setProductName(tbOrderInfoRecord.getProductName());
            productInfo.setViceName(tbOrderInfoRecord.getViceName());
            productInfo.setSubjectId(ConvertUtil.getString(tbOrderInfoRecord.getSubjectId()));
            productInfo.setBrandId(ConvertUtil.getString(tbOrderInfoRecord.getBrandId()));
            productInfo.setProductSnapshootId(tbOrderInfoRecord.getProductSnapshootId());
            productInfo.setSkuNum(tbOrderInfoRecord.getSkuNum());
            productInfo.setSkuDesc(tbOrderInfoRecord.getSkuDesc());
            productInfo.setCurPrice(PriceUtils.intToStr(tbOrderInfoRecord.getCurPrice()));
            productInfo.setOrgPrice(PriceUtils.intToStr(tbOrderInfoRecord.getOrgPrice()));
            productInfo.setImagesUrl(tbOrderInfoRecord.getImagesUrl());
            productInfo.setShelf(tbOrderInfoRecord.getShelf());
            productInfo.setSellerClassNum(tbOrderInfoRecord.getSellerClassNum());
            productInfo.setCount(tbOrderInfoRecord.getCount());
            productInfo.setLastUpdateTime(DateTimeUtil.DateTimeToStr(tbOrderInfoRecord.getLastUpdateTime()));
            productInfo.setLastUpdateUserId(tbOrderInfoRecord.getLastUpdateUserId());
            productInfo.setCreateTime(DateTimeUtil.DateTimeToStr(tbOrderInfoRecord.getCreateTime()));
            productInfo.setCreateUserId(tbOrderInfoRecord.getCreateUserId());
            productInfo.setType(tbOrderInfoRecord.getType()+"");
            productInfo.setWi(tbOrderInfoRecord.getWi());
            productInfo.setExchangeScore(tbOrderInfoRecord.getExchangeScore());
            productInfo.setExchangeCash(PriceUtils.intToStr(tbOrderInfoRecord.getExchangeCash()));
            if(tbOrderInfoRecord.getStorehouseId() != null)
                productInfo.setStorehouseId(tbOrderInfoRecord.getStorehouseId());
            if(tbOrderInfoRecord.getPostageTemplateId() != null)
                productInfo.setPostageTemplateId(tbOrderInfoRecord.getPostageTemplateId());
            if(tbOrderInfoRecord.getThirdexchangerate() != null)
                productInfo.setThirdExchangeRate(tbOrderInfoRecord.getThirdexchangerate());
            if(tbOrderInfoRecord.getPostageext() != null) {
                productInfo.setPostageExt(tbOrderInfoRecord.getPostageext());
            }
            productInfos.add(productInfo);
        }
        orderDetail.setProductList(productInfos);
        return orderDetail;
    }

    /**
     * OrderDetail -> OrderModel
     * @param orderModel
     * @return
     */
    public static OrderModel rConvertOrderModel(Order orderModel) {
        if(orderModel == null) {
            return new OrderModel();
        }
        OrderModel orderDetail = new OrderModel();
        orderDetail.setActiveState(orderModel.getActiveState());
        orderDetail.setClosingPrice(PriceUtils.strToInt(orderModel.getClosingPrice()));
        orderDetail.setBuyerComment(orderModel.getBuyerComment());
        if (orderModel.getOrderStateTypeDesc() != null) {
            orderDetail.setOrderStateTypeDesc(orderModel.getOrderStateTypeDesc().getValue());
        }
        orderDetail.setCancelTime(DateTimeUtil.strToDateTime(orderModel.getCancelTime()));
        orderDetail.setCreateTime(DateTimeUtil.strToDateTime(orderModel.getCreateTime()));
        orderDetail.setDeliverTime(DateTimeUtil.strToDateTime(orderModel.getDeliverTime()));
        orderDetail.setExchangeCash(PriceUtils.strToInt(orderModel.getExchangeCash()));
        orderDetail.setExchangeScore(orderModel.getExchangeScore());
        orderDetail.setFromSource(orderModel.getFromSource());
        orderDetail.setSellerId(orderModel.getSellerId());
        orderDetail.setSellerName(orderModel.getSellerName());
        orderDetail.setUserId(orderModel.getUserId());
        orderDetail.setOrderId(orderModel.getOrderId());
        orderDetail.setTradeCode(orderModel.getTradeCode());
        orderDetail.setThirdId(orderModel.getThirdId());
        orderDetail.setOrderState(orderModel.getOrderState());
        orderDetail.setSuccessTime(DateTimeUtil.strToDateTime(orderModel.getSuccessTime()));
        orderDetail.setLastUpdateTime(DateTimeUtil.strToDateTime(orderModel.getLastUpdateTime()));
        orderDetail.setUserName(orderModel.getUserName());
        orderDetail.setSettlementState((byte) orderModel.getSettlementState());
        orderDetail.setSettlementTime(DateTimeUtil.strToDateTime(orderModel.getSettlementTime()));
        if (orderModel.getOrderStateType() != null) {
            orderDetail.setOrderStateType(orderModel.getOrderStateType().getValue());
        }
        orderDetail.setOrderTag(orderModel.getOrderTag());
        orderDetail.setWi(orderModel.getWi());
        orderDetail.setTradePayId(orderModel.getTradePayId());
        orderDetail.setActiveState(orderModel.getActiveState());
        orderDetail.setOrderBatch(orderModel.getOrderBatch());
        orderDetail.setThirdScore(orderModel.getThirdScore());
        orderDetail.setPostage(PriceUtils.strToInt(orderModel.getPostage()));
        orderDetail.setPostageext(orderModel.getPostageExt());
        orderDetail.setOrderType(orderModel.getOrderType());

        DeliverInfo deliverInfo = orderModel.getDeliverInfo();
        if (deliverInfo != null) {
            orderDetail.setReceiverTele(deliverInfo.getReceiverTele());
            orderDetail.setReceiverMobile(deliverInfo.getReceiverMobile());
            orderDetail.setReceiverName(deliverInfo.getReceiverName());
            orderDetail.setReceiverAddress(deliverInfo.getReceiverAddress());
            orderDetail.setExpressName(deliverInfo.getExpressName());
            orderDetail.setAddressId(deliverInfo.getAddressId());
            orderDetail.setCityId(deliverInfo.getCityId());
            orderDetail.setCityName(deliverInfo.getCityName());
            orderDetail.setCountyId(deliverInfo.getCountyId());
            orderDetail.setCountyName(deliverInfo.getCountyName());
            orderDetail.setProvinceId(deliverInfo.getProvinceId());
            orderDetail.setProvinceName(deliverInfo.getProvinceName());
            orderDetail.setExpressId(ConvertUtil.getInt(deliverInfo.getExpressId(), 0));
            orderDetail.setExpressNo(deliverInfo.getExpressNo());
            orderDetail.setPostCode(deliverInfo.getPostCode());
        }

        PayInfo payInfo = orderModel.getPayInfo();
        if (payInfo != null) {
            orderDetail.setPayAccount(payInfo.getPayAccount());
            if (payInfo.getPayChannel() != null) {
                orderDetail.setPayChannel(ConvertUtil.getInt(payInfo.getPayChannel()));
            }
            orderDetail.setPayId(payInfo.getPayId());
            orderDetail.setPayState(payInfo.getPayState());
            orderDetail.setPayTime(DateTimeUtil.strToDateTime(payInfo.getPayTime()));
        }

        List<TbOrderInfoRecord> productInfos = new ArrayList<TbOrderInfoRecord>();
        for(OrderInfo orderInfo : orderModel.getProductList()) {
            TbOrderInfoRecord productInfo = new TbOrderInfoRecord();
            productInfo.setOrderId(orderModel.getOrderId());
            productInfo.setProductId(orderInfo.getProductId());
            productInfo.setProductName(orderInfo.getProductName());
            productInfo.setViceName(orderInfo.getViceName());
            productInfo.setSubjectId(ConvertUtil.getInt(orderInfo.getSubjectId(), 0));
            productInfo.setBrandId((ConvertUtil.getInt(orderInfo.getBrandId(), 0)));
            productInfo.setProductSnapshootId(orderInfo.getProductSnapshootId());
            productInfo.setSkuNum(orderInfo.getSkuNum());
            productInfo.setSkuDesc(orderInfo.getSkuDesc());
            productInfo.setCurPrice(PriceUtils.strToInt(orderInfo.getCurPrice()));
            productInfo.setOrgPrice(PriceUtils.strToInt(orderInfo.getOrgPrice()));
            productInfo.setImagesUrl(orderInfo.getImagesUrl());
            productInfo.setShelf(orderInfo.getShelf());
            productInfo.setSellerClassNum(orderInfo.getSellerClassNum());
            productInfo.setCount(orderInfo.getCount());
            productInfo.setLastUpdateTime(DateTimeUtil.strToDateTime(orderInfo.getLastUpdateTime()));
            productInfo.setLastUpdateUserId(orderInfo.getLastUpdateUserId());
            productInfo.setCreateTime(DateTimeUtil.strToDateTime(orderInfo.getCreateTime()));
            productInfo.setCreateUserId(orderInfo.getCreateUserId());
            productInfo.setType(ConvertUtil.getInt(orderInfo.getType(), 0));
            productInfo.setWi(orderInfo.getWi());
            productInfo.setExchangeScore(orderInfo.getExchangeScore());
            productInfo.setExchangeCash(PriceUtils.strToInt(orderInfo.getExchangeCash()));
            productInfo.setStorehouseId(orderInfo.getStorehouseId());
            productInfo.setPostage(PriceUtils.strToInt(orderInfo.getPostage()));
            productInfo.setPostageTemplateId(orderInfo.getPostageTemplateId());
            productInfo.setThirdexchangerate(orderInfo.getThirdExchangeRate());
            productInfo.setPostageext(orderInfo.getPostageExt());
            productInfos. add(productInfo);
        }
        orderDetail.setTbOrderInfoList(productInfos);
        return orderDetail;
    }

    public static List<Order> rConvertOrderModels(List<OrderModel> orderModels) {
        List<Order> orderDetails = new ArrayList<>();
        if(CollectionUtils.isEmpty(orderModels)) {
            return orderDetails;
        }

        for(OrderModel orderModel : orderModels) {
            orderDetails.add(rConvertOrderModel(orderModel));
        }

        return orderDetails;
    }

    /**
     * 获取订单应当所处订单状态
     * @param order
     * @return
     */
    public static int parseOrderState(OrderModel order) {
        DateTime createTime = order.getCreateTime();
        DateTime cancelTime = order.getCancelTime();
        DateTime payTime = order.getPayTime();
        DateTime curTime = new DateTime();
        //设置默认支付时间
        if (payTime == null) {
            int payTimeout = ConvertUtil.getInt(PropertiesUtil.getProperty("jfx_trade_serv", "pay_timeout", "30"));
            payTime = cancelTime.plusMinutes(payTimeout);
        }

        if (order.getOrderState() == ConstantUtil.ORDER_STATE.WAIT_PAY.getEnumVal()) {
            if (order.getPayState() == ConstantUtil.PAY_STATE.INIT.getEnumVal()) {
                if (curTime.getMillis() > cancelTime.getMillis()) {
                    return ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal(); //订单该关闭了
                }
            } else if (order.getPayState() == ConstantUtil.PAY_STATE.ING.getEnumVal()) {
                if (curTime.getMillis() > cancelTime.getMillis()) {
                    if (curTime.getMillis() > payTime.getMillis()) {
                        return ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal(); //订单该关闭了
                    } else {
                        return  ConstantUtil.ORDER_STATE.PAYING.getEnumVal(); //订单支付中
                    }
                }
            }
        } else if (order.getOrderState() == ConstantUtil.ORDER_STATE.PAYING.getEnumVal()) {
            if (curTime.getMillis() > cancelTime.getMillis()) {
                return  ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal(); //订单该关闭了
            }
        }

        return order.getOrderState();
    }

    public static void setPayLimit(List<OrderModel> orderModels, int payChannel) {
        int payTimeout = ConvertUtil.getInt(PropertiesUtil.getProperty("jfx_trade_serv", "pay_timeout", "30"));
        for (OrderModel order : orderModels) {
            order.setPayTime(new DateTime().plusMinutes(payTimeout));
            order.setPayChannel(payChannel);
        }
    }

    /**
     * 获取支付标题
     * @param orderModels
     * @return
     */
    public static String getPayTitle(List<OrderModel> orderModels) {
        StringBuilder strTitle = new StringBuilder();
        strTitle.append("聚分享");

        strTitle.append("订单");

        return strTitle.toString();
    }

    /**
     * 获取用户实际支付金额
     * @param orderModels 订单列表
     * @return
     */
    public static int getPayPrice(List<OrderModel> orderModels) {
        int totalPayPrice = 0;
        for (OrderModel order: orderModels) {
            totalPayPrice += getPayPrice(order);
        }

        return totalPayPrice;
    }

    /**
     * 获取用户实际支付金额
     * @param order 订单
     * @return
     */
    public static int getPayPrice(OrderModel order) {
        int orderPayPrice = order.getClosingPrice() - order.getExchangeCash();
        return orderPayPrice;
    }

    /**
     * 获取用户实际支付金额,、积分、支付方式
     * @param order 订单
     * @return
     */
    public static String getPayPrice(Order order) {
        String retStr = "";
        if(order.getPayInfo().getPayState() != ConstantUtil.PAY_STATE.SUCCESS.getEnumVal()) {
            return "未支付";
        }

        if(String.valueOf(BizUtil.PAY_CHANNEL.TIAN_YI.getEnumVal()).equals(order.getPayInfo().getPayChannel())) {
            int thirdPrice = PriceUtils.strToInt(order.getThirdPrice());
            int thirdScore = order.getThirdScore();

            if(thirdPrice > 0) {
                retStr += PriceUtils.intToStr(thirdPrice) + "元";
            }

            if(thirdScore > 0) {
                retStr += retStr.length() > 0 ? " + ": "";
                retStr += thirdScore + "天翼积分";
            }
            retStr += "(天翼支付)";
        } else {
            int closingPrice = PriceUtils.strToInt(order.getClosingPrice());
            int exchangeCash = PriceUtils.strToInt(order.getExchangeCash());
            int orderPayPrice = closingPrice - exchangeCash;
            int score = order.getExchangeScore();
            if(orderPayPrice > 0) {
                retStr += PriceUtils.intToStr(orderPayPrice) + "元";
            }
            if(score > 0) {
                retStr += retStr.length() > 0 ? " + ": "";
                retStr += score + "聚分享积分";
            }
            retStr +=  "(" + BizUtil.PAY_CHANNEL.getEnumByValue(Integer.parseInt(order.getPayInfo().getPayChannel())).getDesc() + ")";
        }

        return retStr;
    }

    /**
     * 设置第三方支付所需积分
     * @param orderModels
     * @param payChannel
     * @return 返回需要使用的第三方总积分
     */
    public static int setThirdScore(List<OrderModel> orderModels, int payChannel) {
        int thirdScores = 0;
        for (OrderModel order : orderModels) {
            int orderThirdScore = 0;
            for (TbOrderInfoRecord orderInfo : order.getTbOrderInfoList()) {
                orderInfo.setThirdScore(0);
                if (payChannel == BizUtil.PAY_CHANNEL.TIAN_YI.getEnumVal()) {
                    if(NumberUtils.toInt(orderInfo.getThirdexchangerate()) > 0) {
                        orderInfo.setThirdScore(orderInfo.getCount() * 100);
                    }
                }
                orderThirdScore += orderInfo.getThirdScore();
            }
            order.setThirdScore(orderThirdScore);
            thirdScores += order.getThirdScore();
        }

        return thirdScores;
    }

    /**
     * 获取第三方积分抵扣金额合计
     * @param orderModels
     * @param payChannel
     * @return
     */
    public static int getThirdScore2Cash(List<OrderModel> orderModels, int payChannel) {
        int thirdScore2CashAmount = 0;
        if (payChannel == BizUtil.PAY_CHANNEL.TIAN_YI.getEnumVal()) {
            for (OrderModel order : orderModels) {
                for (TbOrderInfoRecord orderInfo : order.getTbOrderInfoList()) {
                    if(NumberUtils.toInt(orderInfo.getThirdexchangerate()) > 0) {
                        thirdScore2CashAmount += NumberUtils.toInt(orderInfo.getThirdexchangerate()) * orderInfo.getCount();
                    } else {
                        thirdScore2CashAmount += orderInfo.getCurPrice() * orderInfo.getCount();
                    }
                }
            }
        }
        return thirdScore2CashAmount;
    }

    public static List<OrderCount> countOrdersByState(List<OrderModel> orderModels) {
        Map<Integer, OrderCount> stateMap = new HashMap<Integer, OrderCount>();
        stateMap.put(ConstantUtil.ORDER_STATE.WAIT_PAY.getEnumVal(), new OrderCount(ConstantUtil.ORDER_STATE.WAIT_PAY.getEnumVal(), 0));
        stateMap.put(ConstantUtil.ORDER_STATE.WAIT_AUDIT.getEnumVal(), new OrderCount(ConstantUtil.ORDER_STATE.WAIT_AUDIT.getEnumVal(), 0));
        stateMap.put(ConstantUtil.ORDER_STATE.WAIT_DELIVER.getEnumVal(), new OrderCount(ConstantUtil.ORDER_STATE.WAIT_DELIVER.getEnumVal(), 0));
        stateMap.put(ConstantUtil.ORDER_STATE.FINISH_DELIVER.getEnumVal(), new OrderCount(ConstantUtil.ORDER_STATE.FINISH_DELIVER.getEnumVal(), 0));
        stateMap.put(ConstantUtil.ORDER_STATE.WAIT_COMMENT.getEnumVal(), new OrderCount(ConstantUtil.ORDER_STATE.WAIT_COMMENT.getEnumVal(), 0));
        stateMap.put(ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal(), new OrderCount(ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal(), 0));
        if(CollectionUtils.isNotEmpty(orderModels)) {
            for(OrderModel orderModel : orderModels) {
                int state = orderModel.getOrderState() / 10 * 10;
                if(stateMap.containsKey(state)) {
                    stateMap.get(state).setCount(stateMap.get(state).getCount() + 1);
                }
            }
        }

        List<OrderCount> orderCounts = new ArrayList<>();
        for(Integer key : stateMap.keySet()) {
            orderCounts.add(stateMap.get(key));
        }

        return orderCounts;
    }

    /**
     * 获取买家支付非邮费部分金额
     * @param orderList
     * @return
     */
    public static int getPostageAmount(List<OrderModel> orderList) {
        int totalPostage = 0;
        for(OrderModel order : orderList)
            totalPostage += order.getClosingPrice() - order.getPostage();
        return totalPostage;
    }

    /**
     * 购物车订单总金额
     * @param orderList
     * @return
     */
    public static int gettotalAmount(List<OrderModel> orderList) {
        int totalClosingPrice = 0;
        for(OrderModel order : orderList)
            totalClosingPrice += order.getClosingPrice();
        return totalClosingPrice;
    }

    public static AfterSaleOrderParam orderQueryConditions2AfterSaleOrderParam(OrderQueryConditions orderQueryConditions){
        AfterSaleOrderParam afterSaleOrderParam = new AfterSaleOrderParam();
        if(orderQueryConditions.getSellerId() > 0) {
            afterSaleOrderParam.setSellerId(orderQueryConditions.getSellerId());
        }
        if(StringUtils.isNotBlank(orderQueryConditions.getStartTime())) {
            afterSaleOrderParam.setStartTime(orderQueryConditions.getStartTime());
        }
        if(StringUtils.isNotBlank(orderQueryConditions.getEndTime())) {
            afterSaleOrderParam.setEndTime(orderQueryConditions.getEndTime());
        }
        if(StringUtils.isNotBlank(orderQueryConditions.getOrderId())) {
            afterSaleOrderParam.setOrderId(orderQueryConditions.getOrderId());
        }
        return afterSaleOrderParam;
    }

    public static List<String> afterSaleOrders2ListOrderId(List<AfterSaleOrder> afterSaleOrders) {
        List<String> orderIds = new ArrayList<String>();
        for(AfterSaleOrder afterSaleOrder : afterSaleOrders) {
            orderIds.add(afterSaleOrder.getOrderId());
        }
        return orderIds;
    }

    /**
     * 获取订单支付申请备注
     * @param orderModels
     * @return
     */
    public static String getRemark(List<OrderModel> orderModels) {
        String s = "订单支付";
        OrderModel orderModel = orderModels.get(0);
        if(orderModel.getPayChannel() == BizUtil.PAY_CHANNEL.TIAN_YI.getEnumVal()) {
            TbOrderInfoRecord tbOrderInfoRecord = orderModel.getTbOrderInfoList().get(0);
            String productName = tbOrderInfoRecord.getProductName();
            String productId = tbOrderInfoRecord.getProductId();
            int count = tbOrderInfoRecord.getCount();
            s = "百分尊享-" + productId + productName + "x" + count;
        }

        return s;
    }

    public static int getTotalUseScore2Cash(List<OrderModel> orderModels) {
        int total = 0;
        for(OrderModel model : orderModels) {
            total += model.getExchangeCash();
        }
        return total;
    }
}
