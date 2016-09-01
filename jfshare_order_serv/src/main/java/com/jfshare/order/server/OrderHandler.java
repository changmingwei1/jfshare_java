package com.jfshare.order.server;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrder;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderResult;
import com.jfshare.finagle.thrift.express.ExpressInfo;
import com.jfshare.finagle.thrift.order.*;
import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.finagle.thrift.pay.PayRet;
import com.jfshare.finagle.thrift.product.ProductCard;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.trade.BuyInfo;
import com.jfshare.order.dao.IOrderEs;
import com.jfshare.order.dao.IOrderJedis;
import com.jfshare.order.dao.impl.jedis.BasicRedis;
import com.jfshare.order.exceptions.BaseException;
import com.jfshare.order.exceptions.DataVerifyException;
import com.jfshare.order.model.EsOrder;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.order.server.depend.*;
import com.jfshare.order.service.DeliverService;
import com.jfshare.order.service.ExportService;
import com.jfshare.order.service.OrderService;
import com.jfshare.order.service.ScoreService;
import com.jfshare.order.util.*;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.thrift.TException;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;

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
    private ProductClient productClient;

    @Autowired
    private CommonClient commonClient;

    @Autowired
    private TradeClient tradeClient;

    @Autowired
    private IOrderJedis orderJedis;

    @Autowired
    private FileOpUtil fileOpUtil;

    @Autowired
    private IOrderEs orderEs;

    @Autowired
    private ExportService exportService;

    @Autowired
    private BasicRedis basicRedis;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private AfterSaleClient afterSaleClient;

    @Override
    public Result createOrder(List<Order> orderList) throws TException {
        Result result = new Result();
        result.setCode(0);
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
        if(userType == BizUtil.USER_TYPE.SELLER.getEnumVal()) {
            //TODO 校验参数
            try {
                OrderModel orderModel = orderService.sellerQueryDetail(userId, deliverInfo.getOrderId());
                if (orderModel == null) {
                    logger.warn(MessageFormat.format("updateExpressInfo订单不存在！sellerId[{0}],orderId[{1}],expressId[{2}],expressNo[{3}]", userId, deliverInfo.getOrderId(), deliverInfo.getExpressId(), deliverInfo.getExpressNo()));
                    return ResultBuilder.createFailNormalResult(FailCode.ORDER_NO_EXIST);
                }

                if(StringUtils.isNotBlank(deliverInfo.getExpressId()) && NumberUtils.isNumber(deliverInfo.getExpressId())) {
                    orderModel.setExpressId(Integer.parseInt(deliverInfo.getExpressId()));
                }
                if(StringUtils.isNotBlank(deliverInfo.getExpressNo())) {
                    orderModel.setExpressNo(deliverInfo.getExpressNo());
                }
                if(StringUtils.isNotBlank(deliverInfo.getExpressName())) {
                    orderModel.setExpressName(deliverInfo.getExpressName());
                }
                if(StringUtils.isNotBlank(deliverInfo.getSellerComment())) {
                    orderModel.setSellerComment(deliverInfo.getSellerComment());
                }

                deliverService.updateDeliverInfo(orderModel);
                deliverInfo.setExpressId(String.valueOf(orderModel.getExpressId()));
                deliverInfo.setExpressNo(orderModel.getExpressNo());
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

        } else if(userType == BizUtil.USER_TYPE.BUYER.getEnumVal()) {
            return new Result(1);
        }

        return ResultBuilder.createNormalResult();
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

            //获取卡密
            List<ProductCard> productCards = productClient.getProductCard(orderModel);
            //修改订单状态
            deliverService.updateDeliverInfoVir(orderModel);

            //发送短信
            StringBuilder msgContent = new StringBuilder();
            TbOrderInfoRecord orderInfo = orderModel.getTbOrderInfoList().get(0);
            if(orderModel.getTradeCode().equals(ConstantUtil.TRADE_CODE.ORDER_CODE_VIR_KAMI.getEnumVal())) {
                //您已成功购买*****（商品名称+规格）消费券*（数量）张，券码*********，密码*********，券码*********，密码*********，请前往商家验证消费。
                msgContent.append("您已成功购买")
                        .append(orderInfo.getProductName()).append(" ")
                        .append(orderInfo.getSkuDesc()).append(" ")
                        .append("消费券").append(orderInfo.getCount()).append("张");
                for(ProductCard card : productCards) {
                    msgContent.append("券码:").append(card.getCardNumber()).append(", ");
                    if(StringUtils.isNotEmpty(card.getPassword())) {
                        msgContent.append("密码:").append(card.getPassword()).append("，");
                    }
                }
                msgContent.append("请前往商家验证消费。");
            } else if(orderModel.getTradeCode().equals(ConstantUtil.TRADE_CODE.ORDER_CODE_VIR_KAONLY.getEnumVal())) {
                //您已成功购买*****（商品名称+规格）消费券*（数量）张，券码*********，*********，*********，请前往商家验证消费。
                msgContent.append("您已成功购买")
                        .append(orderInfo.getProductName()).append(" ")
                        .append(orderInfo.getSkuDesc()).append(" ")
                        .append("消费券").append(orderInfo.getCount()).append("张，券码：");
                for(ProductCard card : productCards) {
                    msgContent.append(card.getCardNumber()).append("，");
                }
                msgContent.append("请前往商家验证消费。");
            }

            commonClient.sendMsg(orderModel.getReceiverMobile(), msgContent.toString());

            this.confirmReceipt(BizUtil.USER_TYPE.BUYER.getEnumVal(), orderModel.getUserId(), param.getOrderId());

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
            OrderModel orderModel = orderService.buyerQueryDetail(userId, orderId);
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

//    @Override
//    public OverSoldOrderProfileResult overSoldOrder(OverSoldQueryConditions conditions) throws TException {
//        return null;
//    }

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
                logger.warn("申请支付----payApply参数验证失败！param[{}]", param);
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return stringResult;
            }

            List<OrderModel> orderModels = orderService.buyerQueryListFull(param.getUserId(), param.getOrderIdList());
            logger.info("申请支付----payApply获取订单信息:{}", JSON.toJSONString(orderModels));
            if (orderModels == null || orderModels.size() != param.getOrderIdList().size()) {
                logger.warn("申请支付----payApply获取订单信息有误！param[{}]", param);
                FailCode.addFails(result, FailCode.ORDER_INFO_ERROR);
                return stringResult;
            }

            int useScore = param.getExchangeScore();
            int lockScore = 0;
            for(OrderModel order : orderModels) {
                lockScore += order.getExchangeScore();
            }
            //检查之前是否锁定了积分
            logger.info("申请支付----积分参数校验, lockScore={}, useScore={}", lockScore, useScore);
            if(lockScore > 0 && lockScore != useScore){
                logger.warn("申请支付----积分校验失败, 之前锁定积分:{}, 当前是使用积分:{}", lockScore, useScore);
                FailCode.addFails(result, FailCode.PAY_SCORE_CHECK_FAIL);
                return stringResult;
            }

            //拆分积分
            if(lockScore == 0 && useScore > 0){
                //验证积分抵现
                BuyInfo buyInfo = new BuyInfo();
                buyInfo.setAmount(PriceUtils.intToStr(OrderUtil.gettotalAmount(orderModels)));
                buyInfo.setExchangeScore(useScore);
                buyInfo.setExchangeCash(param.getExchangeCash());
                buyInfo.setUserId(param.getUserId());
                List<FailDesc> score2CashFailList = tradeClient.score2cash(orderModels, buyInfo);
                if(CollectionUtils.isNotEmpty(score2CashFailList)) {
                    logger.error("申请支付----扣减积分失败！fails=" + score2CashFailList);
                    FailCode.addFails(result, FailCode.PAY_SCORE_CHECK_FAIL);
                    return stringResult;
                }
            }
            logger.info("申请支付----积分抵现校验通过");

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
            int thirdScore2Cash = OrderUtil.getThirdScore2Cash(orderModels, param.getPayChannel().getPayChannel());
            logger.info("申请支付----请求url, thirdScores={}, thirdScore2Cash={}", thirdScores, thirdScore2Cash);
            String tradePayId = IdCreator.getTradePayId(param.getUserId(), param.getOrderIdList());
            if(param.payChannel.payChannel == BizUtil.PAY_CHANNEL.TIAN_YI.getEnumVal()) {
                tradePayId = "17921" + orderModels.get(0).getOrderBatch().replaceAll("-","");
            }
            orderService.updateOrderPaying(orderModels, tradePayId);



            //全积分支付
//            boolean isOnlyScore = OrderUtil.gettotalAmount(orderModels) == PriceUtils.strToInt(param.getExchangeCash());
            boolean isOnlyScore = checkIsOnlyScorePay(orderModels);
            if(isOnlyScore) {
                stringResult.setValue("全积分支付成功");
                stringResult.getResult().setCode(2);
                int ret = orderService.payOnlyScore(orderModels);
                if(ret > 0) {
                    logger.error("申请支付----全积分支付成功！");
                }

                //虚拟商品需要自动发货
                autoDeliver4VirtualOrder(orderModels);
                logger.info("申请支付----虚拟商品自动发货成功");

                scoreService.finishOrderPay(orderModels);
                logger.info("申请支付----赠送购物积分成功");

                return stringResult;
            }

            //申请支付链接
            PayReq payReq = new PayReq();
            payReq.setTokenId(PropertiesUtil.getProperty("jfx_order_serv", "pay_token_id"));
            payReq.setOrderNo(tradePayId);
            payReq.setExtraParam(param.getUserId() + "_" + StringUtils.join(param.getOrderIdList().toArray(),",")); //userId_orderId1,orderId2 ...
            payReq.setTitle(OrderUtil.getPayTitle(orderModels));
            payReq.setScore(thirdScores);
            payReq.setScore2cashAmount(thirdScore2Cash);
            payReq.setPrice(OrderUtil.getPayPrice(orderModels));
            payReq.setPayChannel(param.getPayChannel().getPayChannel());
            payReq.setPayIp(param.getPayChannel().getPayIp());
            payReq.setReturnUrl(param.getPayChannel().getReturnUrl());
            payReq.setCustId(param.getPayChannel().getCustId());
            payReq.setCustType(param.getPayChannel().getCustType());
            payReq.setProcustID(param.getPayChannel().getProcustID());
            payReq.setRemark(OrderUtil.getRemark(orderModels));

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

    private boolean checkIsOnlyScorePay(List<OrderModel> orderModels) {
        for(OrderModel order : orderModels) {
            if(order.getExchangeCash()-order.getClosingPrice()!=0) {
            	logger.info("--------》"+(order.getExchangeCash()-order.getClosingPrice()));
            	logger.info("exchange--------"+order.getExchangeCash()+"ClosingPrice-----------》"+order.getClosingPrice());
                return false;
            }
        }
        return true;
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

            List<OrderModel> orderModels = orderService.buyerQueryListFull(userId, orderIdList);
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

            //虚拟商品需要自动发货
            autoDeliver4VirtualOrder(orderModels);
            logger.info("8.支付通知----虚拟商品自动发货成功");

            scoreService.finishOrderPay(orderModels);
            logger.info("9.支付通知----赠送购物积分成功");

            stringResult.setValue("1");
        } catch (Exception e) {
            logger.error("$$$$支付通知----接收支付平台通知失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
        }

        return stringResult;
    }

    private void autoDeliver4VirtualOrder(List<OrderModel> orderModels) throws TException {
        for(OrderModel orderModel : orderModels) {
            if(ConstantUtil.TRADE_CODE.isVirOrder(orderModel.getTradeCode())) {
                DeliverVirParam param = new DeliverVirParam(orderModel.getSellerId(), orderModel.getOrderId());
                Result delverVirResult = deliverVir(param);
                if(delverVirResult == null || delverVirResult.getCode() != 0) {
                    logger.error("虚拟商品自动发货----失败，需要手工发货， 失败原因:{}", delverVirResult.getFailDescList());
                }
            }
        }
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

    @Override
    public StringResult batchExportOrder(int sellerId, OrderQueryConditions conditions) throws TException {
        //从ES恢复订单数据
        StringResult stringResult = new StringResult(new Result(0));
        if(org.apache.commons.lang3.StringUtils.isBlank(conditions.getOrderId())) {
            return new StringResult(new Result(1));
        }

        OrderProfileResult orderProfileResult = this.orderProfileQueryFull(conditions);
        try {
            OrderModel orderModel = OrderUtil.rConvertOrderModel(orderProfileResult.getOrderProfilePage().getOrderProfileList().get(0));
            int ret = orderService.insertOrder(orderModel);
            stringResult.setValue(ret+"");
        } catch (Exception e) {
            logger.error("恢复数据异常，", e);
            stringResult.setValue("异常， 恢复失败---------------" + conditions.getOrderId());
        }
        return stringResult;
    }

    @Override
    public StringResult batchExportOrderFull(OrderQueryConditions conditions) throws TException {
        StringResult stringResult = new StringResult();
        Result result = new Result();
        result.setCode(0);
        stringResult.setResult(result);
        stringResult.setValue("");
        try {
            if (StringUtil.isNullOrEmpty(conditions)) {
                logger.warn("batchExportOrderFull参数验证失败！, OrderQueryConditions:{}", conditions);
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return stringResult;
            }

            AfterSaleOrderResult afterSaleOrderResult = null;
            //查询售后订单
            if(conditions.getOrderState() == 1000) {
                AfterSaleOrderParam afterSaleOrderParam = OrderUtil.orderQueryConditions2AfterSaleOrderParam(conditions);
                afterSaleOrderResult = afterSaleClient.queryAfterSaleOrder(afterSaleOrderParam);
                if(afterSaleOrderResult == null || CollectionUtils.isEmpty(afterSaleOrderResult.getAfterSaleOrders())) {
                    logger.info("batchExportOrderFull！无要导出的售后订单数据");
                    FailCode.addFails(result, FailCode.NO_AFTERSALE_ORDER_RECORD);
                    return stringResult;
                }
                List<String> orderIds = OrderUtil.afterSaleOrders2ListOrderId(afterSaleOrderResult.getAfterSaleOrders());
                conditions.setOrderIds(orderIds);
            }

            String queryKey = "orderExport:"+DigestUtils.md5Hex(DateTimeUtil.getCurrentDateYMDHMS());
            exportService.asyncExport(conditions, queryKey, afterSaleOrderResult == null ? null : afterSaleOrderResult.getAfterSaleList());
            stringResult.setValue(queryKey);


        } catch (Exception e) {
            logger.error("批量导出订单失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
        }

        return stringResult;
    }

    @Override
    public StringResult getExportOrderResult(String queryKey) throws TException {
        StringResult stringResult = new StringResult(new Result(1));
        if(StringUtils.isBlank(queryKey)) {
            stringResult.getResult().addToFailDescList(FailCode.PARAM_ERROR);
            return stringResult;
        }

        String exportRet =  exportService.getExportResult(queryKey);
        stringResult.setValue(exportRet);
        stringResult.getResult().setCode(0);

        return stringResult;
    }

    @Override
    public BatchDeliverResult batchDeliverOrder(int sellerId, BatchDeliverParam param) throws TException {
        BatchDeliverResult batchDeliverResult = new BatchDeliverResult();
        Result result = new Result();
        result.setCode(0);
        batchDeliverResult.setResult(result);
        try {
            List<BatchDeliverFailInfo> failInfoList = new ArrayList<BatchDeliverFailInfo>();
            List<Order> orderList = param.getOrderList();
            //1、empty list
            if (orderList == null || orderList.size() == 0) {
                logger.info("无订单导入数据..........................");
                result.setCode(1);
                BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                failInfo.setOrderId("无");
                failInfo.setDesc("无订单导入数据");
                failInfoList.add(failInfo);
                batchDeliverResult.setFailInfo(failInfoList);
                logger.warn("deliver$$deliverEx batchDeliverEx  无订单导入数据 failInfoList ==>", result);
                return batchDeliverResult;
            }

            List<ExpressInfo> expressList = expressClient.query();
            if (expressList.size() == 0) {
                logger.error("sellerId:"+sellerId+",批量发货调用express的query接口获取物流信息失败！");
                logger.error("$$$$ batchDeliverEx sellerId:" + sellerId + ",批量发货调用express的query接口获取物流信息失败！");
                result.setCode(1);
                BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                failInfo.setOrderId("无");
                failInfo.setDesc("获取物流信息失败");
                failInfoList.add(failInfo);
                batchDeliverResult.setFailInfo(failInfoList);
                logger.warn("deliver$$deliverEx batchDeliverEx failInfoList BatchFailInfo =  {}", result);
                return  batchDeliverResult;
            }


            //2、valid empty orderId & repeat id|
            Set<String> orderIdSet = new HashSet<String>();
            Map<String, Integer> orderIdIndexMap = new HashMap<String, Integer>(); // key:orderId,value:index
            for(int i =0 ; i < orderList.size(); i++) {
                if (StringUtil.isNullOrEmpty(orderList.get(i).getOrderId())) {
                    BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                    failInfo.setIndex(i);
                    failInfo.setOrderId("无");
                    failInfo.setDesc("订单号为空"); //缺少订单编号
                    failInfo.setOrder(orderList.get(i));
                    failInfoList.add(failInfo);
                }
                else {
                    // trimallOrderId
                    orderList.get(i).setOrderId(orderList.get(i).getOrderId().trim());
                    if (orderIdSet.contains(orderList.get(i).getOrderId()) &&
                            !ParamCheck.deliverCanSame(orderList, orderIdIndexMap.get(orderList.get(i).getOrderId()), i) ) {
                        BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                        failInfo.setOrderId(orderList.get(i).getOrderId());
                        failInfo.setIndex(i);
                        failInfo.setDesc("订单号重复");
                        failInfo.setOrder(orderList.get(i));
                        failInfoList.add(failInfo);
                    }
                    else {
                        orderIdSet.add(orderList.get(i).getOrderId());
                        orderIdIndexMap.put(orderList.get(i).getOrderId(), i);
                    }
                }
            }

            List<Order> resultOrderList = new ArrayList<Order>(); //合法订单列表
            List<String> resultOrderIdList = new ArrayList<String>(); //合法订单ID
            List<String> resultExpressNoList = new ArrayList<String>();


            //3、valid empty expressName、expressNo; exist express; valid expressNo
            List<BatchDeliverFailInfo> failExpressList  = ParamCheck.checkOrderExpressInfoEx(orderList,
                    resultOrderList, resultOrderIdList, resultExpressNoList, expressList, param.getDeliverType());
            if(failExpressList.size() > 0) {
                logger.info("sellerId:"+sellerId+",快递信息错误的订单..........................");
                logger.warn("batchDeliverEx sellerId:" + sellerId + ",快递信息错误的订单..........................");
                failInfoList.addAll(failExpressList);
            }

            //验证全部是错误
            if(resultOrderList.size() == 0) {
                logger.info("订单都不存在错误的订单..........................");
                logger.warn("batchDeliverEx 订单都不存在错误的订单..........................");
            }


            //4、valid order in db
            List<OrderModel> existOrderList = resultOrderIdList.size()==0 ? new ArrayList<OrderModel>():orderService.getSellerOrderBatch(sellerId, resultOrderIdList);
            List<Order> existOrderResultList = null;
            if(existOrderList.size() < resultOrderList.size()) {
                List<String> noExistOrderIdList = new ArrayList<String>();
                existOrderResultList = new ArrayList<Order>();
                //检测出是存在的订单
                for(Order order : resultOrderList) {
                    String existId = null;
                    for(OrderModel existOrder : existOrderList) {
                        if(existOrder.getOrderId().equals(order.getOrderId())) {
                            existId = order.getOrderId();
                            existOrderResultList.add(order);
                            break;
                        }

                    }
                    if(existId == null)
                        noExistOrderIdList.add(order.getOrderId());
                }

                //不存在的订单的行号等信息
                List<BatchDeliverFailInfo> noExistOrderList = ParamCheck.checkOrderNotExist(orderList, noExistOrderIdList);
                if(noExistOrderList != null && noExistOrderList.size() > 0) {   //--------------订单不存在
                    failInfoList.addAll(noExistOrderList);
                    logger.info("sellerId:"+sellerId+",有不存在的订单..........................");
                }
            }


            //5、valid error orderstate 检测是否存在非等待发货、非已发货的订单号
            List<String> existOrderIds = new ArrayList<String>();
            for (OrderModel orderProfile : existOrderList) {
                existOrderIds.add(orderProfile.getOrderId());
                if (orderProfile.getOrderState() != ConstantUtil.ORDER_STATE.WAIT_DELIVER.getEnumVal()) {
                    logger.info("存在订单状态错误的订单:"+orderProfile.getOrderId() + ",订单状态为：" + orderProfile.getOrderState());
                    logger.warn("batchDeliverEx 存在订单状态错误的订单: {}   订单状态为： {}", orderProfile.getOrderId(), orderProfile.getOrderState());
                    BatchDeliverFailInfo failInfo = new BatchDeliverFailInfo();
                    if (orderIdIndexMap.containsKey(orderProfile.getOrderId())) {
                        failInfo.setIndex(orderIdIndexMap.get(orderProfile.getOrderId()));
                    }
                    failInfo.setOrderId(orderProfile.getOrderId());
                    failInfo.setDesc(ParamCheck.getOrderStateError(orderProfile));
                    Order order = OrderUtil.rConvertOrderModel(orderProfile);
                    ParamCheck.fillOrderExpress(order, orderList);
                    failInfo.setOrder(order);
                    failInfoList.add(failInfo);
                }
            }

            //7. get failorders
            Set<String> failOrderIds = new HashSet<String>();
            if (failInfoList.size() > 0) {
                result.setCode(1);
                List<BatchDeliverFailInfo> validFailList = ParamCheck.getBatchDeliverFailInfo(failInfoList);
                batchDeliverResult.setFailInfo(validFailList);
                failOrderIds = ParamCheck.getFailOrders(validFailList);
            }

            //8. deliver part success orders
            List<Order> deliverOrderList = ParamCheck.getCanDeliverList(orderList, failOrderIds);

            if (deliverOrderList.size() > 0) {
                for (Order order : deliverOrderList) {
                    OrderModel tbOrder = orderService.sellerQueryDetail(sellerId, order.getOrderId());

                    if(tbOrder != null) {
                        ParamCheck.fillDeliverOrderInfo(order, tbOrder);
                    }
                }

                int ret = orderService.batchDeliverOp(sellerId, deliverOrderList, expressList, param.getDeliverType());
                if (ret < 0) {
                    logger.info("sellerId:"+sellerId+",发货失败.........................");
                    result.setCode(1);
                    BatchDeliverFailInfo fail = new BatchDeliverFailInfo();
                    fail.setDesc("发货失败");
                    batchDeliverResult.addToFailInfo(fail);
                }
            }


            if(1 == result.getCode()){
                logger.warn("deliver$$deliverEx 操作有异常！  result: {}",result);
            }
            logger.info("batchDeliverEx 正常返回  返回的 result : {} ", result);
            logger.info("batchDeliverEx 批量发结束(batchDeliver) --- <<< End");
        } catch (Exception e) {
            logger.error("批量发货失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
        }

        return batchDeliverResult;
    }

    @Override
    public OrderProfileResult orderProfileQueryFull(OrderQueryConditions conditions) throws TException {
        OrderProfileResult orderProfileResult = new OrderProfileResult();
        orderProfileResult.setResult(new Result(0));
        orderProfileResult.setOrderProfilePage(new OrderProfilePage());
        if((StringUtils.isBlank(conditions.getStartTime()) || StringUtils.isBlank(conditions.getEndTime()))
                && StringUtils.isBlank(conditions.getOrderId())
                && CollectionUtils.isEmpty(conditions.getOrderIds())) {
            return ResultBuilder.createFailOrderProfileResult(FailCode.PARAM_ERROR);
        }

        SearchHits searchHits = orderEs.search(conditions);
        int total = (int)searchHits.getTotalHits();

        if(total > 0) {
            orderProfileResult.getOrderProfilePage().setTotal(total);
            for(SearchHit searchHit : searchHits.getHits()) {
                EsOrder esOrder = JSON.parseObject(searchHit.getSourceAsString(), EsOrder.class);
                orderProfileResult.getOrderProfilePage().addToOrderProfileList(JSON.parseObject(esOrder.getOrderJson(), Order.class));
            }
            orderProfileResult.getOrderProfilePage().setCount(searchHits.getHits().length);
            orderProfileResult.getOrderProfilePage().setCurPage(conditions.getCurPage());
            if(conditions.getCount() > 0) {
                int pageCount = total % conditions.getCount() == 0 ? total / conditions.getCount() : total / conditions.getCount() + 1;
                orderProfileResult.getOrderProfilePage().setPageCount(pageCount);
            }
        }

        return orderProfileResult;
    }

    @Override
    public OrderDetailResult queryOrderDetailOffline(int userType, int userId, String orderId) throws TException {
        Order orderDetail = null;
        try {
            OrderModel orderModel = null;

            if(userType == BizUtil.USER_TYPE.BUYER.getEnumVal()) {
                orderModel = orderService.buyerQueryDetailOffline(userId, orderId);
            } else if(userType == BizUtil.USER_TYPE.SELLER.getEnumVal()) {
                orderModel = orderService.sellerQueryDetailOffline(userId, orderId);
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
    public OrderProfileResult orderProfileQueryOffline(int userType, int userId, OrderQueryConditions conditions) throws TException {
        List<Order> orderDetails = null;

        int total = 0;
        //验证用户ID是否正确
        List<FailDesc> failDescs = ParamCheck.UserIdCheck(userId);
        if(CollectionUtils.isNotEmpty(failDescs)) {
            return ResultBuilder.createFailOrderProfileResult(failDescs);
        }
        try {
        	//初始化查询参数conditions
            List<OrderModel> orderModels = null;
            conditions = super.verifyConditions(conditions);
            //用户类型，1买家，2卖家，3系统
            if(userType == BizUtil.USER_TYPE.BUYER.getEnumVal()) {
                conditions.setUserId(userId);
                total = orderService.buyerQueryOrderStatOffline(conditions);
                if (total > 0) {
                    orderModels = orderService.buyerQueryListOffline(conditions);
                }
            } else if(userType == BizUtil.USER_TYPE.SELLER.getEnumVal()) {
                conditions.setSellerId(userId);
                //查询订单数量
                total = orderService.sellerQueryOrderStatOffline(conditions);
                if (total > 0) {
                    //查询订单列表
                    orderModels = orderService.sellerQueryListOffline(conditions);
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
}
