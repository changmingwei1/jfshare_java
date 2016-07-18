package com.jfshare.pay.server;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.pay.*;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.pay.model.TbPayRecordWithBLOBs;
import com.jfshare.pay.server.depend.OrderClient;
import com.jfshare.pay.service.IPaySvc;
import com.jfshare.pay.util.FailCode;
import com.jfshare.pay.util.PayUtil;
import com.jfshare.pay.util.alipay.util.AlipayNotify;
import com.jfshare.pay.util.hebaopay.HebaoSubmit;
import com.jfshare.pay.util.weixinpay.WeixinSubmit;
import com.jfshare.utils.CryptoUtil;
import com.jfshare.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Service(value="handler")
public class ServHandle implements PayServ.Iface {
    private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
    @Autowired
    private IPaySvc paySvcImpl;
    @Autowired
    private OrderClient orderClient;

    @Override
    public StringResult payUrl(PayReq payReq) throws TException {
        StringResult stringResult = new StringResult();
        Result result = new Result();
        result.setCode(0);
        stringResult.setResult(result);
        try {
            String payUrl = null;
            //基本参数验证
            if (StringUtil.isNullOrEmpty(payReq) || StringUtil.isNullOrEmpty(payReq.getTokenId()) || StringUtil.isNullOrEmpty(payReq.getOrderNo()) ||
                    StringUtil.isNullOrEmpty(payReq.getTitle()) || payReq.getPrice() < 0 ||
                    payReq.getScore() < 0 || payReq.getPayChannel() <= 0) {
                logger.warn(MessageFormat.format("申请支付----payUrl参数验证失败！PayReq[{0}]", payReq));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return stringResult;
            }
            //私钥验证
            if (StringUtil.isNullOrEmpty(CryptoUtil.decryptString(payReq.getTokenId()))) {
                logger.warn("申请支付----payUrl支付令牌认证失败！");
                FailCode.addFails(result, FailCode.TOKEN_ID_AUTH_FAIL);
                return stringResult;
            }

            //生成平台流水号
            String payId = PayUtil.getPayId(payReq);
            //生成支付链接
            if (payReq.getPayChannel() == 1) {
                if (StringUtil.isNullOrEmpty(payReq.getCustId()) || StringUtil.isNullOrEmpty(payReq.getCustType())) {
                    logger.warn("申请支付----payUrl参数custId|custType验证失败！");
                    FailCode.addFails(result, FailCode.PARAM_ERROR);
                    return stringResult;
                }
                payId = PayUtil.getTianYiPayId();//奇葩的payId
                payUrl = PayUtil.getReqTY(payReq, payId);
            } else if (payReq.getPayChannel() == 2) {
                payUrl = PayUtil.getReqAliPay(payReq, payId);
            } else if (payReq.getPayChannel() == 3 || payReq.getPayChannel() == 4 ||
                    payReq.getPayChannel() == 9) {
                payUrl = PayUtil.getWeixinPay(payReq, payId);
            } else if (payReq.getPayChannel() == 6) {
                payUrl = PayUtil.getHebaoH5(payReq, payId);
            } else if (payReq.getPayChannel() == 5) {
                payUrl = PayUtil.getReqAliH5(payReq, payId);
            } else if (payReq.getPayChannel() == 7) {
                payUrl = PayUtil.getReqAliApp(payReq, payId);
            } else if (payReq.getPayChannel() == 8) {
                payUrl = PayUtil.getHebaoPay(payReq, payId);
            }

            if (payId == null || payUrl == null) {
                logger.error("生成payId或payUrl失败! payId:" + payId + ", payUrl:" + payUrl);
                FailCode.addFails(result, FailCode.PARAM_NOT_VALID);
                return stringResult;
            }

            int ret = 0;
            try {
                ret = paySvcImpl.insert(payReq, payUrl, payId);
            } catch (DuplicateKeyException e) {
                logger.warn("payId插入时重复异常！" + e);
            }

            if (ret <= 0) {
                FailCode.addFails(result, FailCode.REQ_DB_ERROR);
                return stringResult;
            }

            logger.info(MessageFormat.format("申请支付----支付链接已生成，payUrl {0}", payUrl));

            stringResult.setValue(payUrl);
        } catch (Exception e) {
            logger.error("$$$$申请支付----申请支付请求失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
        }

        return stringResult;
    }

    /**
     * 1.ty demo: {"AppCode":"CX","RequestDate":"20151228153038","Sign":"f4e1f80bf60042f61325dcf297f368ed",
     "PayIntegral":"0","Integral":"0","Voucher":"0","PayMoney":"10","CommodityName":"",
     "UserNameTX":"18515063312","SPOrderID":"1e412cc63f45eb451422bcb7a94e50d0",
     "AttachPra":"http://120.24.153.102:13005/pay/notify/jf189","OrderID":"33648798",
     "PayType":"8"}
     * 2.alipay demo:  {"discount":"0.00","payment_type":"1","subject":"聚分享订单","trade_no":"2016030821001004070224134185","buyer_email":"larson1987@sina.com","gmt_create":"2016-03-08 15:33:13","notify_type":"trade_status_sync","quantity":"1","out_trade_no":"c6d497e026db6505e5f81b54a07e697a","seller_id":"2088701691132875","notify_time":"2016-03-08 15:37:10","body":"订单支付","trade_status":"TRADE_SUCCESS","is_total_fee_adjust":"N","total_fee":"0.01","gmt_payment":"2016-03-08 15:33:26","seller_email":"windy@jfshare.com","price":"0.01","buyer_id":"2088202656550070","notify_id":"6444905f4c05292d989f91777405adfgji","use_coupon":"N","sign_type":"MD5","sign":"3f840b3af19c19abef90c3251ac3c055"}
     * 3.weixinpay demo: {"<xml><appid><![CDATA[wxd20631014da48053]]></appid>\n<bank_type><![CDATA[CFT]]></bank_type>\n<cash_fee><![CDATA[1]]></cash_fee>\n<device_info><![CDATA[WEB]]></device_info>\n<fee_type><![CDATA[CNY]]></fee_type>\n<is_subscribe><![CDATA[N]]></is_subscribe>\n<mch_id><![CDATA[1241526502]]></mch_id>\n<nonce_str><![CDATA[sRM2BsvjmIuVEdoV9JZX]]></nonce_str>\n<openid><![CDATA[oJRWAswP73f77gMomB0aPvg1vtK4]]></openid>\n<out_trade_no><![CDATA[47b8f2167948711fbc4b2539f609b4d1]]></out_trade_no>\n<result_code><![CDATA[SUCCESS]]></result_code>\n<return_code><![CDATA[SUCCESS]]></return_code>\n<sign><![CDATA[609212CF1150D4208F81C800B17FBA93]]></sign>\n<time_end><![CDATA[20160313114228]]></time_end>\n<total_fee>1</total_fee>\n<trade_type><![CDATA[NATIVE]]></trade_type>\n<transaction_id><![CDATA[1009750877201603133948161542]]></transaction_id>\n</xml>":""}
     * @param payRes
     * @return
     * @throws TException
     */
    @Override
    public StringResult payNotify(PayRes payRes) throws TException {
        StringResult stringResult = new StringResult();
        Result result = new Result();
        result.setCode(0);
        stringResult.setResult(result);
        try {
            //基本参数验证
            if (StringUtil.isNullOrEmpty(payRes) || StringUtil.isNullOrEmpty(payRes.getResUrl()) || payRes.getPayChannel() <= 0) {
                logger.warn(MessageFormat.format("支付通知----payRes参数验证失败！payRes[{0}]", payRes));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return stringResult;
            }

            TbPayRecordWithBLOBs payResRecord = null;
            //天翼
            if (payRes.getPayChannel() == 1) {
                payResRecord = PayUtil.getResTianYi(payRes);
            } else if (payRes.getPayChannel() == 2 || payRes.getPayChannel() == 5 ||
                    payRes.getPayChannel() == 7) {
                Map<String, String> params = (Map)JSON.parseObject(payRes.getResUrl());
                boolean verify = AlipayNotify.verify(params);
                if (!verify) {
                    logger.error(MessageFormat.format("$$$$支付通知----alipay支付参数非法,验证签名失败! payRes[{0}]", payRes));
                    FailCode.addFails(result, FailCode.PARAM_ERROR);
                    return stringResult;
                }

                payResRecord = PayUtil.getResAliPay(payRes, params);
            } else if (payRes.getPayChannel() == 3 || payRes.getPayChannel() == 4 ||
                    payRes.getPayChannel() == 9) {
                Map<String, String> params = (Map)JSON.parseObject(payRes.getResUrl());
                String resUrlXml = null;
                for (String key : params.keySet()) { //取json的key是xml内容
                    resUrlXml = key;
                }
                Map<String, String> resMap = WeixinSubmit.xmlStr2Map(resUrlXml);
                Map<String, String> signParams = WeixinSubmit.buildRequestPara(resMap, payRes.getPayChannel());
                boolean verify = WeixinSubmit.verify(resMap, signParams, payRes.getPayChannel());
                if (!verify) {
                    logger.error(MessageFormat.format("$$$$支付通知----weinxin支付参数非法,验证签名失败! payRes[{0}]", payRes));
                    FailCode.addFails(result, FailCode.PARAM_ERROR);
                    return stringResult;
                }
                payResRecord = PayUtil.getResWeixinPay(payRes, resMap);
            } else if (payRes.getPayChannel() == 8 || payRes.getPayChannel() == 6) {
                Map<String, String> params = (Map)JSON.parseObject(payRes.getResUrl());
                boolean verify = HebaoSubmit.verify(params);
                if (!verify) {
                    logger.error(MessageFormat.format("$$$$支付通知----hebaopay支付参数非法,验证签名失败! payRes[{0}]", payRes));
                    FailCode.addFails(result, FailCode.PARAM_ERROR);
                    return stringResult;
                }

                payResRecord = PayUtil.getResHebaoPay(payRes, params);
            }

            if (payResRecord == null || StringUtil.isNullOrEmpty(payResRecord.getPayId())) {
                FailCode.addFails(result, FailCode.RES_PARSE_ERROR);
                return stringResult;
            }

            logger.info("$$$$支付通知----获取支付申请pay_id={}", payResRecord.getPayId());
            TbPayRecordWithBLOBs dbRecord = paySvcImpl.queryByPayId(payResRecord.getPayId());
            if (dbRecord == null) {
                logger.error(MessageFormat.format("$$$$支付通知----支付申请数据不存在，payRes{0}, payResRecord{1}", payRes, payResRecord));
                FailCode.addFails(result, FailCode.RES_NO_ERROR);
                return stringResult;
            }
            payResRecord.setId(dbRecord.getId());
            int ret = paySvcImpl.update(payResRecord);

            if (ret <= 0) {
                FailCode.addFails(result, FailCode.RES_DB_ERROR);
                return stringResult;
            }

            dbRecord = paySvcImpl.queryByPayId(payResRecord.getPayId());
            PayRet payRet = PayUtil.toPayRet(dbRecord);
            String payNotify = JSON.toJSONString(payRet);

            stringResult.setValue(payNotify);

            orderClient.payFinish(payNotify);
        } catch (Exception e) {
            logger.error("$$$$支付通知----处理失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYS_ERROR);
        }

        return stringResult;
    }

    @Override
    public StringResult queryPayResult(payRetQueryParams params) throws TException {
        StringResult stringResult = new StringResult();
        stringResult.setResult(new Result(0));
        if(params == null || StringUtils.isBlank(params.getPayId())) {
            logger.warn("支付结果查询----payRetQueryParams参数验证失败！params:[{}]", params);
            FailCode.addFails(stringResult.getResult(), FailCode.PARAM_ERROR);
            return stringResult;
        }

        try {
            TbPayRecordWithBLOBs payRecord = paySvcImpl.queryByPayId(params.getPayId());
            PayRet payRet = new PayRet();
            payRet.setThirdPrice(payRecord.getThirdPrice());
            payRet.setThirdScore(payRecord.getThirdScore());
            stringResult.setValue(JSON.toJSONString(payRet));
        } catch (Exception e) {
            logger.error("支付结果查询----处理失败，系统异常！", e);
            FailCode.addFails(stringResult.getResult(), FailCode.SYS_ERROR);
        }

        return stringResult;
    }
}
