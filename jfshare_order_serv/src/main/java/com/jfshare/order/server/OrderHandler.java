package com.jfshare.order.server;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.order.*;
import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.finagle.thrift.pay.PayRet;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.order.dao.IOrderJedis;
import com.jfshare.order.exceptions.BaseException;
import com.jfshare.order.exceptions.DataVerifyException;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.server.depend.ExpressClient;
import com.jfshare.order.server.depend.PayClient;
import com.jfshare.order.service.DeliverService;
import com.jfshare.order.service.OrderService;
import com.jfshare.order.util.*;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Service(value = "orderHandler")
public class OrderHandler extends BaseHandler implements OrderServ.Iface {
    private Logger logger = LoggerFactory.getLogger(OrderHandler.class);
    
    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "deliverService")
    private DeliverService deliverService;

    @Autowired
    private PayClient payClient;

    @Autowired
    private ExpressClient expressClient;

    @Autowired
    private IOrderJedis orderJedis;

    @Override
    public Result createOrder(List<Order> orderList) throws TException {
        Result result = new Result(0);
        try {
            logger.info("订单入库----参数,orderList" + orderList);
            int ret = orderService.insertOrderList(orderList);
            if (ret < 0) {
                result.setCode(1);
                FailCode.addFails(result, FailCode.ORDER_INSERT_ERROR);
            }
        } catch (Exception e) {
            logger.error("$$$$订单入库----程序异常错误！orderList=" + orderList, e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
            throw new RuntimeException("$$$$$$$$确认订单发生异常");
        }

        return result;
    }

    @Override
    public Result updateDeliverInfo(int userType, int userId, DeliverInfo deliverInfo) throws TException {
        Result result = new Result(0);
        try {
            if (userType<=0 || userId <= 0 || deliverInfo == null || StringUtils.isBlank(deliverInfo.getOrderId())) {
                logger.warn(MessageFormat.format("updateDeliverInfo参数验证失败！userType[{0}],userId[{1}],orderId[{2}]", userType, userId, deliverInfo.getOrderId()));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }
            OrderModel orderModel = orderService.buyerQueryProfile(userId, deliverInfo.getOrderId());
            if (orderModel == null) {
                logger.warn(MessageFormat.format("updateDeliverInfo订单不存在！userType[{0}],userId[{1}],orderId[{2}]", userType, userId, deliverInfo.getOrderId()));
                FailCode.addFails(result, FailCode.ORDER_NO_EXIST);
                return result;
            }
            if(StringUtils.isNotBlank(deliverInfo.getExpressId()))
                orderModel.setExpressId(Integer.parseInt(deliverInfo.getExpressId()));
            if(StringUtils.isNotBlank(deliverInfo.getExpressNo()))
                orderModel.setExpressNo(deliverInfo.getExpressNo());

            deliverService.updateDeliverInfo(orderModel);

        }catch (BaseException be) {
            List<FailDesc> failDescs = be.getFailDescs();
            logger.error(MessageFormat.format("$$$$修改发货信息----更新数据库失败！userType[{0}],userId[{1}],orderId[{2}]", userType, userId, deliverInfo.getOrderId()));
            return ResultBuilder.createFailNormalResult(failDescs);
        } catch (Exception e) {
            logger.error(MessageFormat.format("$$$$修改发货信息----程序异常错误！userType[{0}],userId[{1}],orderId[{2}]", userType, userId, deliverInfo.getOrderId()), e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
            throw new RuntimeException("$$$$$$$$修改发货信息发生异常");
        }

        return result;
    }

    @Override
    public Result deliver(int sellerId, DeliverInfo deliverInfo) throws TException {
        //TODO 校验参数
        try {
            deliverService.updateOrderDeliverInfo(sellerId, deliverInfo);
            expressClient.subscribeExpressPost(deliverInfo);
        } catch (BaseException be) {
            List<FailDesc> failDescs = be.getFailDescs();
            logger.error("发货失败!");
            return ResultBuilder.createFailNormalResult(failDescs);
        } catch(Exception e) {
            logger.error("发货失败，系统异常");
            e.printStackTrace();
            return ResultBuilder.createFailNormalResult(FailCode.SYS_ERROR);
        }
        return ResultBuilder.createNormalResult();
    }

    @Override
    public Result deliverVir(DeliverVirParam param) throws TException {
        Result result = new Result(0);
        try {
            if (param == null || param.getSellerId() <= 0 || StringUtils.isBlank(param.getOrderId())) {
                logger.warn(MessageFormat.format("deliverVir参数验证失败！sellerId[{0}],orderId[{1}]", param.getSellerId(), param.getOrderId()));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }

            OrderModel orderModel = orderService.sellerQueryDetail(param.getSellerId(), param.getOrderId());
            if (orderModel == null) {
                logger.warn(MessageFormat.format("deliverVir查询订单不存在！sellerId[{0}],orderId[{1}]", param.getSellerId(), param.getOrderId()));
                FailCode.addFails(result, FailCode.ORDER_NO_EXIST);
                return result;
            }

            if(orderModel.getTradeCode().equalsIgnoreCase(ConstantUtil.TRADE_CODE.ORDER_CODE_VIR_KAMI.getEnumVal()) == false) {
                logger.warn(MessageFormat.format("deliverVir非卡密虚拟商品！sellerId[{0}],orderId[{1}],tradeCode[{2}]", param.getSellerId(), param.getOrderId()), orderModel.getTradeCode());
                FailCode.addFails(result, FailCode.DELIVER_INVALIDATE_TRADECODE);
                return result;
            }

            deliverService.updateDeliverInfo(orderModel);

            //TODO 调用商品服务获取卡密
            //TODO 发送短信
        } catch (BaseException be) {
            List<FailDesc> failDescs = be.getFailDescs();
            logger.error("发货失败!");
            return ResultBuilder.createFailNormalResult(failDescs);
        } catch(Exception e) {
            logger.error("发货失败，系统异常");
            e.printStackTrace();
            return ResultBuilder.createFailNormalResult(FailCode.SYS_ERROR);
        }
        return ResultBuilder.createNormalResult();
    }

    @Override
    public Result updateExpressInfo(int sellerId, String orderId, String expressId, String expressNo, String expressName) throws TException {
        Result result = new Result(0);
        try {
            if (sellerId<=0 || StringUtils.isBlank(expressId) || StringUtils.isBlank(expressNo) || StringUtils.isBlank(orderId)) {
                logger.warn(MessageFormat.format("updateExpressInfo参数验证失败！sellerId[{0}],orderId[{1}],expressId[{2}],expressNo[{3}]", sellerId, orderId, expressId, expressNo));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }
            OrderModel orderModel = orderService.sellerQueryDetail(sellerId, orderId);
            if (orderModel == null) {
                logger.warn(MessageFormat.format("updateExpressInfo订单不存在！sellerId[{0}],orderId[{1}],expressId[{2}],expressNo[{3}]", sellerId, orderId, expressId, expressNo));
                FailCode.addFails(result, FailCode.ORDER_NO_EXIST);
                return result;
            }

            orderModel.setExpressId(Integer.parseInt(expressId));
            orderModel.setExpressNo(expressNo);
            orderModel.setExpressName(expressName);

            deliverService.updateDeliverInfo(orderModel);

        }catch (BaseException be) {
            List<FailDesc> failDescs = be.getFailDescs();
            logger.error(MessageFormat.format("$$$$修改订单快递信息----更新数据库失败！sellerId[{0}],orderId[{1}],expressId[{2}],expressNo[{3}]", sellerId, orderId, expressId, expressNo));
            return ResultBuilder.createFailNormalResult(failDescs);
        } catch (Exception e) {
            logger.error(MessageFormat.format("$$$$修改订单快递信息----程序异常错误！sellerId[{0}],orderId[{1}],expressId[{2}],expressNo[{3}]", sellerId, orderId, expressId, expressNo));
            FailCode.addFails(result, FailCode.SYS_ERROR);
            throw new RuntimeException("$$$$$$$$修改订单快递信息发生异常");
        }

        return result;
    }

    @Override
    public BatchDeliverResult batchDeliver(int sellerId, BatchDeliverParam param) throws TException {
        return null;
    }

    @Override
    public Result confirmReceipt(int userType, int userId, String orderId) throws TException {
        Result result = new Result();
        result.setCode(0);
        try {
            if (userType<=0 || userId <= 0 || StringUtil.isNullOrEmpty(orderId)) {
                logger.warn(MessageFormat.format("confirmReceipt参数验证失败！userType[{0}],userId[{1}],orderId[{2}]", userType, userId, orderId));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }
            OrderModel orderModel = orderService.buyerQueryProfile(userId, orderId);
            if (orderModel == null) {
                logger.warn(MessageFormat.format("confirmReceipt订单不存在！userType[{0}],userId[{1}],orderId[{2}]", userType, userId, orderId));
                FailCode.addFails(result, FailCode.ORDER_NO_EXIST);
                return result;
            }
            //TODO 检测订单状态
            int ret = orderService.confirmReceipt(orderModel);
            if (ret <= 0) {
                FailCode.addFails(result, FailCode.RECEIPT_FAIL);
                return result;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("$$$$订单确认收货----程序异常错误！userType[{0}],userId[{1}],orderId[{2}]", userType, userId, orderId), e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
            throw new RuntimeException("$$$$$$$$订单确认收货发生异常");
        }

        return result;
    }

    @Override
    public Result cancelOrder(int userType, int userId, String orderId, int reason) throws TException {
        Result result = new Result();
        result.setCode(0);
        try {
            if (userType<=0 || userId <= 0 || StringUtil.isNullOrEmpty(orderId)) {
                logger.warn(MessageFormat.format("cancelOrder参数验证失败！userType[{0}],userId[{1}],orderId[{2}],reason[{3}]", userType, userId, orderId, reason));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }
            OrderModel orderModel = orderService.buyerQueryProfile(userId, orderId);
            if (orderModel == null) {
                logger.warn(MessageFormat.format("cancelOrder订单不存在！userType[{0}],userId[{1}],orderId[{2}],reason[{3}]", userType, userId, orderId, reason));
                FailCode.addFails(result, FailCode.ORDER_NO_EXIST);
                return result;
            }
            //TODO 检测订单状态
            int ret = orderService.cancelOrder(orderModel, userType, reason);
            if (ret <= 0) {
                FailCode.addFails(result, FailCode.CANCEL_FAIL);
                return result;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("$$$$订单关闭----程序异常错误！userType[{0}],userId[{1}],orderId[{2}],reason[{3}]", userType, userId, orderId, reason), e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
            throw new RuntimeException("$$$$$$$$订单关闭发生异常");
        }

        return result;
    }

    @Override
    public ExportOrderResult queryExportOrderInfo(int sellerId, OrderQueryConditions conditions) throws TException {
        return null;
    }

    @Override
    public OrderProfileResult orderProfileQuery(int userType, int userId, OrderQueryConditions conditions) throws TException {
        List<Order> orderDetails = null;

        int total = 0;
        List<FailDesc> failDescs = ParamCheck.UserIdCheck(userId);
        if(CollectionUtils.isNotEmpty(failDescs)) {
            return ResultBuilder.createFailOrderProfileResult(failDescs);
        }
        try {
            List<OrderModel> orderModels = null;
            conditions = super.verifyConditions(conditions);

            if(userType == BizUtil.USER_TYPE.BUYER.getEnumVal()) {
                conditions.setUserId(userId);
                total = orderService.buyerQueryOrderStat(conditions);
                if (total > 0) {
                    orderModels = orderService.buyerQueryList(conditions);
                }
            } else if(userType == BizUtil.USER_TYPE.SELLER.getEnumVal()) {
                conditions.setSellerId(userId);
                //查询订单数量
                total = orderService.sellerQueryOrderStat(conditions);
                if (total > 0) {
                    //查询订单列表
                    orderModels = orderService.sellerQueryList(conditions);
                }
            } else {
                return ResultBuilder.createFailOrderProfileResult(FailCode.PARAM_ERROR);
            }
            orderDetails = OrderUtil.rConvertOrderModels(orderModels);

        } catch (DataVerifyException e) {
            logger.error("发生DataVerifyException", e);
            return ResultBuilder.createFailOrderProfileResult(e.getFailDescs());
        }

        return ResultBuilder.createOrderProfileResult(conditions, orderDetails, total);
    }

    @Override
    public OrderDetailResult queryOrderDetail(int userType, int userId, String orderId) throws TException {

        Order orderDetail = null;
        try {
            OrderModel orderModel = null;

            if(userType == BizUtil.USER_TYPE.BUYER.getEnumVal()) {
                orderModel = orderService.buyerQueryDetail(userId, orderId);
            } else if(userType == BizUtil.USER_TYPE.SELLER.getEnumVal()) {
                orderModel = orderService.sellerQueryDetail(userId, orderId);
            } else {
                return ResultBuilder.createFailOrderDetailResult(FailCode.PARAM_ERROR);
            }

            orderDetail = OrderUtil.rConvertOrderModel(orderModel);

        } catch (Exception e) {
            logger.error("查询失败！", e);
            return ResultBuilder.createFailOrderDetailResult(FailCode.SYS_ERROR);
        }

        return ResultBuilder.createOrderDetailResult(orderDetail);
    }

    @Override
    public OrderStateResult orderStateQuery(int userType, int userId, OrderQueryConditions conditions) throws TException {
        List<OrderCount> orderStateCounts = null;

        int total = 0;
        List<FailDesc> failDescs = ParamCheck.UserIdCheck(userId);
        if(CollectionUtils.isNotEmpty(failDescs)) {
            return ResultBuilder.createFailOrderStateResult(failDescs);
        }
        try {
            List<OrderModel> orderModels = null;
            conditions = super.verifyConditions(conditions);

            if(userType == BizUtil.USER_TYPE.BUYER.getEnumVal()) {
                conditions.setUserId(userId);
                total = orderService.buyerQueryOrderStat(conditions);
                if (total > 0) {
                    orderModels = orderService.buyerQueryStateList(conditions);
                }
            } else if(userType == BizUtil.USER_TYPE.SELLER.getEnumVal()) {
                conditions.setSellerId(userId);
                //查询订单数量
                total = orderService.sellerQueryOrderStat(conditions);
                if (total > 0) {
                    //查询订单列表
                    orderModels = orderService.sellerQueryStateList(conditions);
                }
            } else {
                failDescs.add(FailCode.PARAM_ERROR);
                return ResultBuilder.createFailOrderStateResult(failDescs);
            }
            orderStateCounts = OrderUtil.countOrdersByState(orderModels);

        } catch (DataVerifyException e) {
            logger.error("发生DataVerifyException", e);
            return ResultBuilder.createFailOrderStateResult(e.getFailDescs());
        } catch (Exception e) {
            failDescs.add(FailCode.SYS_ERROR);
            logger.error("发送Exception", e);
            return ResultBuilder.createFailOrderStateResult(failDescs);
        }

        return ResultBuilder.createOrderStateResult(orderStateCounts);
    }

    /**
     * 发起申请支付请求
     * 验证订单通过后调用支付服务获取支付url
     * @param param
     * @return
     * @throws TException
     */
    @Override
    public StringResult payApply(PayParam param) throws TException {
        StringResult stringResult = new StringResult();
        Result result = new Result();
        result.setCode(0);
        stringResult.setResult(result);
        try {
            if (StringUtil.isNullOrEmpty(param) || param.getUserId() <= 0 || StringUtil.isNullOrEmpty(param.getOrderIdList()) || param.getOrderIdList().isEmpty() ||
                    StringUtil.isNullOrEmpty(param.getPayChannel()) || param.getPayChannel().getPayChannel() < 0) {
                logger.warn(MessageFormat.format("申请支付----payApply参数验证失败！param[{0}]", param));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return stringResult;
            }

            List<OrderModel> orderModels = orderService.buyerQueryList(param.getUserId(), param.getOrderIdList());
            if (orderModels == null || orderModels.size() != param.getOrderIdList().size()) {
                logger.warn(MessageFormat.format("申请支付----payApply获取订单信息有误！param[{0}]", param));
                FailCode.addFails(result, FailCode.ORDER_INFO_ERROR);
                return stringResult;
            }

            //检测并修复订单状态
            for (OrderModel order : orderModels) {
                try {
                    //尝试修复订单状态
                    int orderState = OrderUtil.parseOrderState(order);
                    if (orderState != order.getOrderState()) {
                        order.setOrderState(orderState);
                        if (orderState == ConstantUtil.ORDER_STATE.PAYING.getEnumVal()) {
                            orderService.updateOrderPaying(order);
                        } else if (orderState == ConstantUtil.ORDER_STATE.TRADE_CLOSE.getEnumVal()) {
                            orderService.cancelOrder(order, BizUtil.USER_TYPE.SYSTEM.getEnumVal(), 0);
                        }
                    }
                } catch (Exception e) {
                    logger.warn("试图修复订单状态错误", e);
                }

                FailDesc stateFail = ParamCheck.checkOrderPayState(order.getOrderState());
                if (stateFail != null) {
                    logger.error("申请支付----支付失败，订单状态异常！", stateFail);
                    FailCode.addFails(result, stateFail);
                    return stringResult;
                }
            }

            FailDesc payChannelFail = ParamCheck.checkPayChannel(param.getPayChannel().getPayChannel());
            if (payChannelFail != null) {
                FailCode.addFails(result, payChannelFail);
                return stringResult;
            }

            OrderUtil.setPayLimit(orderModels, param.getPayChannel().getPayChannel());
            int thirdScores = OrderUtil.setThirdScore(orderModels, param.getPayChannel().getPayChannel());
            String tradePayId = IdCreator.getTradePayId(param.getUserId(), param.getOrderIdList());
            orderService.updateOrderPaying(orderModels, tradePayId);

            //申请支付链接
            PayReq payReq = new PayReq();
            payReq.setTokenId(PropertiesUtil.getProperty("jfx_order_serv", "pay_token_id"));
            payReq.setOrderNo(tradePayId);
            payReq.setExtraParam(param.getUserId() + "_" + StringUtils.join(param.getOrderIdList().toArray(),",")); //userId_orderId1,orderId2 ...
            payReq.setTitle(OrderUtil.getPayTitle(orderModels));
            payReq.setPrice(OrderUtil.getPayPrice(orderModels));
            payReq.setScore(thirdScores);
            payReq.setPayChannel(param.getPayChannel().getPayChannel());
            payReq.setPayIp(param.getPayChannel().getPayIp());
            payReq.setReturnUrl(param.getPayChannel().getReturnUrl());
            payReq.setCustId(param.getPayChannel().getCustId());
            payReq.setCustType(param.getPayChannel().getCustType());
            payReq.setProcustID(param.getPayChannel().getProcustID());
            payReq.setRemark("订单支付");

            String payUrl = payClient.payUrl(payReq);
            if (payUrl == null) {
                FailCode.addFails(result, FailCode.PAY_SYS_FAIL);
                return stringResult;
            }
            stringResult.setValue(payUrl);
        } catch (Exception e) {
            logger.error("$$$$申请支付----申请支付请求失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
        }

        return stringResult;
    }

    @Override
    public StringResult payFinish(String payRes) throws TException {
        StringResult stringResult = new StringResult();
        Result result = new Result();
        result.setCode(0);
        stringResult.setResult(result);
        try {
            PayRet payRet = JSON.parseObject(payRes, PayRet.class);
            if (StringUtil.isNullOrEmpty(payRet) ||  StringUtil.isNullOrEmpty(payRet.getPayId()) || StringUtil.isNullOrEmpty(payRet.getTokenId()) || StringUtil.isNullOrEmpty(payRet.getOrderNo()) ||
                    StringUtil.isNullOrEmpty(payRet.getExtraParam()) || payRet.getPrice() < 0 || payRet.getScore() < 0 ||
                    payRet.getPayChannel() <= 0 || payRet.getRetCode() <= 0 || payRet.getThirdPrice() < 0 || payRet.getThirdScore() < 0 ||
                    payRet.getBizCode() <= 0 || StringUtil.isNullOrEmpty(payRet.getSign())) {
                logger.warn(MessageFormat.format("支付通知----接收支付平台通知参数验证失败！payRes[{0}]", payRes));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return stringResult;
            }
            logger.info("1.支付通知----payUrl基本参数验证成功");

            //私钥验证
            if (!PropertiesUtil.getProperty("jfx_order_serv", "pay_token_id").equals(payRet.getTokenId())) {
                logger.warn("支付通知----payUrl支付令牌校验失败！");
                FailCode.addFails(result, FailCode.TOKEN_ID_AUTH_FAIL);
                return stringResult;
            }
            logger.info("2.支付通知----payUrl私钥验证成功");
            //签名验证
            StringBuilder sbUrl = new StringBuilder(500);
            sbUrl.append("tokenId=").append(payRet.getTokenId());
            sbUrl.append("&orderNo=").append(payRet.getOrderNo());
            sbUrl.append("&processTime=").append(payRet.getProcessTime());
            sbUrl.append("&payChannel=").append(payRet.getPayChannel());
            String sign = CryptoUtil.md5Encode(sbUrl.toString());
            if (!sign.equals(payRet.getSign())) {
                logger.warn("支付通知----payUrl支付签名校验失败！sign=" + payRet.getSign());
                FailCode.addFails(result, FailCode.SIGN_AUTH_FAIL);
                return stringResult;
            }
            logger.info("3.支付通知----payUrl签名验证成功");

            if (payRet.getExtraParam().indexOf("_") < 0) {
                logger.warn("支付通知----payUrl订单数据ExtarParam校验失败！extraParam=" + payRet.getExtraParam());
                FailCode.addFails(result, FailCode.EXTRA_PARAM＿FAIL);
                return stringResult;
            }
            logger.info("4.支付通知----payUrl订单号信息验证成功");

            String[] extra = payRet.getExtraParam().split("_");
            int userId = NumberUtils.toInt(extra[0], 0);
            String[] orderIds = extra[1].split(",");
            List<String> orderIdList =  Arrays.asList(orderIds);

            List<OrderModel> orderModels = orderService.buyerQueryList(userId, orderIdList);
            if (orderModels == null || orderModels.size() != orderIdList.size()) {
                logger.warn(MessageFormat.format("支付通知----接收支付平台通知获取订单信息有误！payRes[{0}]", payRes));
                FailCode.addFails(result, FailCode.ORDER_INFO_ERROR);
                return stringResult;
            }
            logger.info("5.支付通知----payUrl订单有效性验证成功");

            List<FailDesc> amountFails = ParamCheck.checkOrderPayAmount(orderModels, payRet);
            if (amountFails.size() > 0) {
               FailCode.addFails(result, amountFails);
                return stringResult;
            }
            logger.info("6.支付通知----payUrl订单总价和第三方积分验证成功");

            for(OrderModel order : orderModels) {
                int reti = orderService.payRet(order, payRet);
                if (reti < 0) {
                    FailCode.addFails(result, FailCode.ORDER_UPDATE_ERROR);
                    return stringResult;
                }
            }
            logger.info("7.支付通知----payUrl订单状态更新成功");
            orderJedis.addPayState(payRet.getPayId(), payRet.getRetCode(), orderModels.get(0).getCancelTime());

            stringResult.setValue("1");
        } catch (Exception e) {
            logger.error("$$$$支付通知----接收支付平台通知失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
        }

        return stringResult;
    }

    @Override
    public PayStateResult payState(PayState payState) throws TException {
        PayStateResult payStateResult = new PayStateResult();
        Result result = new Result();
        result.setCode(0);
        payStateResult.setResult(result);
        try {
            if (payState == null || StringUtil.isNullOrEmpty(payState.getPayId())) {
                logger.warn(MessageFormat.format("payState参数验证失败！payId[{0}]]", payState.getPayId()));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return payStateResult;
            }
            PayState payState1 = orderJedis.getPayState(payState.getPayId());

            if(payState1 == null) {
                logger.warn("获取支付状态失败redis出错,payId：" + payState.getPayId());
                FailCode.addFails(result, FailCode.PAY_STATE_GET_FAIL);
                return payStateResult;
            }

            payStateResult.setPayState(payState1);
        } catch (Exception e) {
            logger.error("获取支付状态失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
        }

        return payStateResult;
    }
}
