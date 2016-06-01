package com.jfshare.trade.util;

import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.finagle.thrift.baseTemplate.BaseTemplateServ;
import com.jfshare.finagle.thrift.baseTemplate.CalculatePostageResult;
import com.jfshare.finagle.thrift.baseTemplate.SellerPostageReturn;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductResult;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.trade.*;
import com.jfshare.score2cash.services.impl.ScoreToCashService;
import com.jfshare.score2cash.utils.NumberUtil;
import com.jfshare.trade.dao.redis.IBuyLimitRedis;
import com.jfshare.trade.dao.redis.impl.BuyLimitRedisImpl;
import com.jfshare.trade.model.manual.PaymentInfo;
import com.jfshare.trade.server.depend.*;
import com.jfshare.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/10/31.
 */
@Component
public class CheckUtil {
    private Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    @Autowired
    private AddressClient addressClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private IBuyLimitRedis buyLimitRedis;
    @Autowired
    private StockClient stockClient;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private CartClient cartClient;
    @Autowired
    private ScoreClient scoreClient;
    @Autowired
    private ScoreToCashService scoreToCashService;
    @Autowired
    private BaseTemplateClient baseTemplateClient;

    /**
     * 确认订单参数检测
     * @param buyInfo
     * @return
     */
    public List<FailDesc> orderConfirmParam(BuyInfo buyInfo) {
        List<FailDesc> fails = new ArrayList<FailDesc>();
        if (buyInfo == null) {
            fails.add(FailCode.PARAM_ERROR);
            return fails;
        }
        if (buyInfo.getUserId() <= 0) {
            fails.add(FailCode.USER_ID_ERROR);
        }

        if (isVirOrder(buyInfo.getTradeCode())) {
            if(StringUtil.isNullOrEmpty(buyInfo.getDeliverInfo().getReceiverMobile())) {
                fails.add(FailCode.RECEIVER_ADDRESS_NULL_ERROR);
            }
        } else {
            if (buyInfo.getDeliverInfo() == null || buyInfo.getDeliverInfo().getAddressId() <= 0) {
                fails.add(FailCode.RECEIVER_ADDRESS_NULL_ERROR);
            }
        }

        if(buyInfo.getSellerDetailList() == null || buyInfo.getSellerDetailList().size() < 0) {
            fails.add(FailCode.PRODUCT_UNSELECT_ERROR);
            return fails;
        }

        List<BuySellerDetail> sellerDetailList = buyInfo.getSellerDetailList();
        for(int i = 0; i < sellerDetailList.size(); i++)
        {
            List<OrderInfo> productList = sellerDetailList.get(i).getProductList();
            if(productList == null || productList.size() <= 0 ) {
                fails.add(FailCode.PRODUCT_UNSELECT_ERROR);
                return fails;
            }
            for(int j = 0; j < productList.size(); j++)
            {
                if(StringUtil.isNullOrEmpty(productList.get(j).getProductId()) || productList.get(j).getCount() <=0 || StringUtil.isNullOrEmpty(productList.get(j).getCurPrice()) ||
                        PriceUtils.strToInt(productList.get(j).getCurPrice()) <= 0 ) {
                    fails.add(FailCode.PRODUCT_FIELD_ERROR);
                }
            }
        }

        return fails;
    }

    /**
     * 是否为虚拟商品的订单
     */
    public boolean isVirOrder(String tradeCode) {
        if (tradeCode == null) {
            return false;
        }

        return tradeCode.equals(ConstantUtil.TRADE_CODE.ORDER_CODE_TEL_FARE.getEnumVal()) || tradeCode.equals(ConstantUtil.TRADE_CODE.ORDER_CODE_VIR_KAMI.getEnumVal())
                || tradeCode.equals(ConstantUtil.TRADE_CODE.ORDER_CODE_VIR_KAONLY.getEnumVal());
    }

    /**
     * 获取收货地址信息
     * @param buyInfo
     * @return 失败返回null
     */
    public AddressInfo orderConfirmAddress(BuyInfo buyInfo) {
        boolean virOrder = this.isVirOrder(buyInfo.getTradeCode());
        if (virOrder) {
            return new AddressInfo();
        }
        AddressInfo addressInfo = addressClient.queryAddressById(buyInfo.getDeliverInfo().getAddressId(), buyInfo.getUserId());

        if (addressInfo==null || addressInfo.getProvinceId() <=0 || addressInfo.getCityId() <= 0 ||
                StringUtil.isNullOrEmpty(addressInfo.getProvinceName()) || StringUtil.isNullOrEmpty(addressInfo.getCityName()) ||
                StringUtil.isNullOrEmpty(addressInfo.getReceiverName()) || StringUtil.isNullOrEmpty(addressInfo.getAddress()) ||
                (StringUtil.isNullOrEmpty(addressInfo.getMobile()) && StringUtil.isNullOrEmpty(addressInfo.getTel()))
        ) {
            return null;
        }

        return addressInfo;
    }

    /**
     * 获取商品sku信息
     * @param buyInfo
     * @return
     */
    public List<ProductResult> queryHotSKUsByParallel(BuyInfo buyInfo) {
        List<OrderInfo> productInfos = new ArrayList<OrderInfo>();
        for (BuySellerDetail sellerDetail : buyInfo.getSellerDetailList()) {
            for(OrderInfo productInfo : sellerDetail.getProductList()) {
                productInfos.add(productInfo);
            }
        }
        return productClient.queryHotSKUsByParallel(productInfos);
    }

    /**
     * 验证商品sku信息
     * @param productRets
     * @return
     */
    public List<FailDesc> orderConfirmProduct(BuyInfo buyInfo, List<ProductResult> productRets) {
        List<FailDesc> fails = new ArrayList<FailDesc>();
        if (productRets == null || productRets.size() <= 0) {
            fails.add(FailCode.PRODUCT_GET_ERROR);
            return fails;
        }

        for(ProductResult productRet : productRets) {
            if (productRet == null
                    || productRet.getResult() == null
                    || productRet.getResult().getCode() == 1
                    || productRet.getProduct() == null
                    || productRet.getProduct().getProductSku() == null
                    || CollectionUtils.isEmpty(productRet.getProduct().getProductSku().getSkuItems())) {
                fails.add(FailCode.PRODUCT_GET_ERROR);
                return fails;
            }
            Product product = productRet.getProduct();
            if (!this.productOnSale(product.getActiveState())) {
                fails.add(FailCode.PRODUCT_GET_ERROR);
                return fails;
            }
        }

        fails.addAll(productInBuyLimit(buyInfo, productRets));

        return fails;
    }

    /**
     * 验证商品限购
     * @param buyInfo
     * @param productRets
     * @return
     */
    private List<FailDesc> productInBuyLimit(BuyInfo buyInfo, List<ProductResult> productRets) {
        List<FailDesc> fails = new ArrayList<FailDesc>();
        Map<String, Integer> productMaxBuyLimitMap = new HashMap<String, Integer>(); //1.商品限购集合
        for(ProductResult productRet : productRets) {
            Product product = productRet.getProduct();

            if (product.getMaxBuyLimit() > 0) {  //有限购条件
                productMaxBuyLimitMap.put(product.getProductId(), product.getMaxBuyLimit());
            }
        }

        if (productMaxBuyLimitMap.size() > 0) {
            Map<String, Integer> productCurCountMap = new HashMap<String, Integer>(); //2.商品当前购买集合
            for (BuySellerDetail sellerDetail : buyInfo.getSellerDetailList()) {
                for (OrderInfo productInfo : sellerDetail.getProductList()) {
                    if (productMaxBuyLimitMap.containsKey(productInfo.getProductId())) { //有限购条件
                        if (productCurCountMap.containsKey(productInfo.getProductId())) {
                            productCurCountMap.put(productInfo.getProductId(), productCurCountMap.get(productInfo.getProductId()) + productInfo.getCount());
                        } else {
                            productCurCountMap.put(productInfo.getProductId(), productInfo.getCount());
                        }
                    }
                }
            }
            for (Map.Entry<String, Integer> entry : productMaxBuyLimitMap.entrySet()) {
                if (productMaxBuyLimitMap.containsKey(entry.getKey())) { //有限购条件
                    int buyHistoryCount = buyLimitRedis.getBuyHistoryCount(buyInfo.getUserId(), entry.getKey());   //3.商品历史购买
                    if (buyHistoryCount < 0) {
                        fails.add(FailCode.PRODUCT_BUYLIMIT_HISTORY_ERROR);
                        return fails;
                    }

                    if (buyHistoryCount + ConvertUtil.getInt(productCurCountMap.get(entry.getKey()), 0) > entry.getValue()) {
                        logger.warn("商品超过限购数量productId：" + entry.getKey() + "，限购：" + entry.getValue() + "，历史购买量：" + buyHistoryCount + "，当前购买量：" + productCurCountMap.get(entry.getKey()));
                        fails.add(FailCode.PRODUCT_BUYLIMIT_ERROR);
                    }
                }
            }
        }

        return fails;
    }

    /**
     * 商品是否销售中状态
     * @param activeState
     * @return
     */
    private boolean productOnSale(int activeState) {
        return (activeState>=300 && activeState < 400) ? true : false;
    }

    /**
     * 每个卖家 生成订单号
     * @param buyInfo
     * @return key:sellerId, value:orderId
     */
    public Map<Integer,String> orderConfirmGerOrderId(BuyInfo buyInfo) {
        Map<Integer, String> orderIdMap = new HashMap<Integer, String>();
        for (BuySellerDetail sellerDetail : buyInfo.getSellerDetailList()) {
            orderIdMap.put(sellerDetail.getSellerId(), IdCreator.getOrderIdExZk(buyInfo.getUserId()));
        }
        return orderIdMap;

    }

    /**
     * 获取锁定库存信息
     * @param buyInfo
     * @param sellerOrderIdsMap
     * 失败返回null
     */
    public List<LockInfo> lockStockByParallel(BuyInfo buyInfo, Map<Integer, String> sellerOrderIdsMap) {
        Map<Integer, List<LockInfo>> sellerDetailLockInfos = new HashMap<Integer, List<LockInfo>>(); //key:sellerId, value:lockstocks
        for (BuySellerDetail sellerDetail : buyInfo.getSellerDetailList()) {
            sellerDetailLockInfos.put(sellerDetail.getSellerId(), TradeUtil.convertToLockInfos(sellerDetail.getProductList()));
        }

        List<LockInfo> lockInfos = stockClient.lockStock(sellerOrderIdsMap, sellerDetailLockInfos);

        return lockInfos;
    }

    /**
     * 验证库存
     * @param lockInfos  库存
     * @return
     */
    public List<FailDesc> orderConfirmStock(List<LockInfo> lockInfos) {
        List<FailDesc> fails = new ArrayList<FailDesc>();
        if (lockInfos.size() <= 0) {
            fails.add(FailCode.STOCK_GET_ERROR);
            return fails;
        }

        for (LockInfo lockInfo : lockInfos) {
            if (lockInfo == null) {
                fails.add(FailCode.STOCK_GET_ERROR);
                return fails;
            }

            if (lockInfo.getApplyCount() != lockInfo.getLockCount()) {
                fails.add(FailCode.STOCK_LACK_ERROR.setDesc(FailCode.STOCK_LACK_ERROR.getDesc()));
                logger.info("库存不足，stockInfo=" + lockInfo);
            }
        }

        return fails;
    }

    /**
     * 验证价格
     * @param buyInfo
     * @param orderList
     * @return
     */
    public List<FailDesc> orderConfirmPrice(BuyInfo buyInfo, List<Order> orderList) {
        List<FailDesc> fails = new ArrayList<FailDesc>();
        int commitAmount = PriceUtils.strToInt(buyInfo.getAmount());
        int realAmount = 0;
        for (Order item : orderList) {
            realAmount += TradeUtil.getRealPayPrice(item);
        }

        if(commitAmount <= 0) {
            fails.add(FailCode.PAY_PRICE_ERROR);
        } else if (commitAmount != realAmount)  {
            logger.error("$$$$校验商品价格变动错误！提交价格[" + PriceUtils.intToStr(commitAmount) + "]元，实际价格[" + PriceUtils.intToStr(realAmount) + "]元");
            fails.add(FailCode.PAY_PRICE_CHANGE_ERROR.setDesc("提交价格[" + PriceUtils.intToStr(commitAmount) + "]元，实际价格[" + PriceUtils.intToStr(realAmount) + "]元"));
        }

        return fails;
    }

    /**
     * 验证入库
     * @param orderList
     * @return
     */
    public List<FailDesc> orderConfirmOrder(List<Order> orderList) {
        List<FailDesc> fails = new ArrayList<FailDesc>();
        Result ret =  orderClient.createOrder(orderList);
        if (ret==null) {
            fails.add(FailCode.ORDER_PERSIS_CALL_ERROR);
        } else if (ret.getCode()==1){
            fails.add(FailCode.ORDER_PERSIS_RET_ERROR);
        }

        return fails;
    }

    /**
     * 设置返回参数
     * @param createOrderResult
     * @param orderList
     */
    public void orderConfirmSetRet(BuyInfo buyInfo, List<Order> orderList, OrderConfirmResult createOrderResult) {
        if (createOrderResult.getResult().getCode() == 0) {
            if (orderList.size() > 0) {
                int thirdScore = 0;
                int thirdScore2cashAmount = 0;
                List<String> orderIds = new ArrayList<String>();
                for (Order item : orderList) {
                    orderIds.add(item.getOrderId());
                    for(OrderInfo info : item.getProductList()) {
                        if (PriceUtils.strToInt(info.getCurPrice()) >= 100) {
                            thirdScore += 100 * info.getCount();
                            thirdScore2cashAmount += thirdScore * NumberUtils.toInt(info.getThirdExchangeRate(), 1);
                        }
                    }
                }
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setCreateTime(orderList.get(0).getCreateTime());
                paymentInfo.setCancelTime(orderList.get(0).getCancelTime());
                paymentInfo.setPrice(PriceUtils.intToStr(PriceUtils.strToInt(buyInfo.getAmount()) - thirdScore2cashAmount));
                paymentInfo.setThirdScore(String.valueOf(thirdScore)); //若使用第三方积分的总数

                createOrderResult.setOrderIdList(orderIds);
                createOrderResult.setExtend(JsonMapper.toJson(paymentInfo));
            }
        }
    }

    public boolean deleteCart(BuyInfo buyInfo, List<Order> orderList) {
        return cartClient.deleteItem(buyInfo.getUserId(), orderList);
    }

    public boolean releaseStock(Map<Integer, String> sellerOrderIdsMap, BuyInfo buyInfo) {
        if (sellerOrderIdsMap.isEmpty()) {
            return true;
        }

        Map<Integer, List<LockInfo>> sellerDetailLockInfos = new HashMap<Integer, List<LockInfo>>(); //key:sellerId, value:lockstocks
        for (BuySellerDetail sellerDetail : buyInfo.getSellerDetailList()) {
            sellerDetailLockInfos.put(sellerDetail.getSellerId(), TradeUtil.convertToLockInfos(sellerDetail.getProductList()));
        }

        return stockClient.releaseStock(sellerOrderIdsMap, sellerDetailLockInfos);
    }

    public List<FailDesc> orderConfirmScore2Cash(BuyInfo buyInfo, List<Order> orderList) {
        //未使用积分抵现, 直接返回
        if(NumberUtil.parseInteger(buyInfo.getExchangeCash()) == 0 && buyInfo.getExchangeScore() == 0)
            return null;

        List<FailDesc> failDescs = new ArrayList<FailDesc>();
        ExchangeParam param = new ExchangeParam();
        param.setAmount(String.valueOf(TradeUtil.getPostageAmount(buyInfo, orderList)));
        param.setScore(String.valueOf(buyInfo.getExchangeScore()));
        param.setUserId(buyInfo.getUserId());
        List<ExchangeProduct> productList = new ArrayList<ExchangeProduct>();

        for(Order order : orderList) {
            for(OrderInfo orderInfo : order.getProductList()) {
                ExchangeProduct e = new ExchangeProduct();
                e.setProductId(orderInfo.getProductId());
                e.setSkuNum(orderInfo.getSkuNum());
                e.setPrice(PriceUtils.intToStr(PriceUtils.strToInt(orderInfo.getRefPrice()) * orderInfo.getCount()));
            }
        }
        param.setProductList(productList);
        ExchangeResult exchangeScore = scoreToCashService.getExchangeScore(param);
        Map<String, ExchangeDetail> orderExchangeResMap = new HashMap<String, ExchangeDetail>();
        if(exchangeScore != null
                && exchangeScore.getResult().getCode() == 0
                && CollectionUtils.isNotEmpty(exchangeScore.getExchangeDetailList())) {
            for(ExchangeDetail edetail : exchangeScore.getExchangeDetailList())
                orderExchangeResMap.put(edetail.getProductId().concat(":").concat(edetail.getSkuNum()), edetail);
        } else {
            failDescs.addAll(exchangeScore.getResult().getFailDescList());
            return failDescs;
        }

        for(Order order : orderList) {
            int exchangeCashOrder = 0;
            int exchangeScoreOrder = 0;
            for(OrderInfo orderInfo : order.getProductList()) {
                ExchangeDetail e = orderExchangeResMap.get(orderInfo.getProductId().concat(":").concat(orderInfo.getSkuNum()));
                orderInfo.setExchangeCash(e.getAmount());
                orderInfo.setExchangeScore(NumberUtil.parseInteger(e.getScore()));
                exchangeCashOrder += NumberUtil.parseInteger(e.getAmount());
                exchangeScoreOrder += NumberUtil.parseInteger(e.getScore());
            }
            order.setExchangeCash(String.valueOf(exchangeCashOrder));
            order.setExchangeScore(exchangeScoreOrder);
        }

        //扣减用户积分
        scoreClient.reduceScore(buyInfo.getUserId(), buyInfo.getExchangeScore());

        return failDescs;
    }

    public List<FailDesc> orderConfirmPostage(BuyInfo buyInfo, List<Order> orderList) {
        if (isVirOrder(buyInfo.getTradeCode())) {
            return null;
        }

        List<FailDesc> failDescList = new ArrayList<FailDesc>();
        CalculatePostageResult calculatePostageResult = baseTemplateClient.calcPostage(buyInfo, orderList);
        if(calculatePostageResult == null || calculatePostageResult.getResult().getCode() == 1) {
            failDescList.add(FailCode.SYSTEM_EXCEPTION);
            return failDescList;
        }

        for(Order order : orderList) {
            for(SellerPostageReturn  s : calculatePostageResult.getSellerPostageReturnList()) {
                if(order.getSellerId() == s.getSellerId()) {
                    order.setPostage(s.getPostage());
                    int postage = PriceUtils.strToInt(s.getPostage());
                    int closingPrice = PriceUtils.strToInt(order.getClosingPrice());
                    order.setClosingPrice(PriceUtils.intToStr(closingPrice + postage));
                    order.setPostageExt(s.getPostageTemplate());
                    splitPostateWithProduct(order);
                }
            }
        }
        return failDescList;
    }

    private void splitPostateWithProduct(Order order) {
        int s = PriceUtils.strToInt(order.getPostage());    //订单邮费
        int amount = PriceUtils.strToInt(order.getClosingPrice()) - s;  //订单商品金额（不含邮费）
        int a = 0; // 累计邮费
        String splitPostageLog = "orderId="+order.getOrderId()+", postage="+s+", 拆分结果:";
        /** 按商品拆分积分和金额 */
        List<OrderInfo> orderInfos = order.getProductList();
        for (int i = 0; i < orderInfos.size(); i++) {
            OrderInfo e = orderInfos.get(i);

            /** 每个商品可分配的邮费(单位分) */
            int pPostage = 0;

            if (i == orderInfos.size() - 1) { // 最后一次用总数量减去已经分配的钱和积分(避免四舍五入不相等的情况)
                pPostage = s - a;
            } else {
                double pPrice = PriceUtils.strToInt(e.getCurPrice());
                pPostage = NumberUtil.parseInteger((pPrice / amount) * s);

                a += pPostage;
            }

            e.setPostage(PriceUtils.intToStr(pPostage));
            splitPostageLog += pPostage + "|";
        }

        logger.info("订单邮费拆分----" + splitPostageLog);
    }

    public void releaseScore2Cash(BuyInfo buyInfo, String transId) {
        scoreClient.incomeScore(buyInfo.getUserId(), transId, buyInfo.getExchangeScore());
    }

    public List<FailDesc> orderConfirmParamOffline(BuyInfo buyInfo) {
        List<FailDesc> fails = new ArrayList<FailDesc>();
        if (buyInfo == null) {
            fails.add(FailCode.PARAM_ERROR);
            return fails;
        }
        if (buyInfo.getUserId() <= 0) {
            fails.add(FailCode.USER_ID_ERROR);
        }

        if(CollectionUtils.isEmpty(buyInfo.getSellerDetailList()) ||
              buyInfo.getSellerDetailList().size() != 1 || buyInfo.getSellerDetailList().get(0).getSellerId() <= 0) {
            fails.add(FailCode.PRODUCT_UNSELECT_ERROR);
            return fails;
        }

        if(PriceUtils.strToInt(buyInfo.getAmount()) <= 0) {
            fails.add(FailCode.PAY_PRICE_ERROR);
            return fails;
        }

        return fails;
    }
}
