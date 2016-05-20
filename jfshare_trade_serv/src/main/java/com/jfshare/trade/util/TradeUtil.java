package com.jfshare.trade.util;

import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.finagle.thrift.order.DeliverInfo;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.finagle.thrift.order.PayInfo;
import com.jfshare.finagle.thrift.product.Product;
import com.jfshare.finagle.thrift.product.ProductResult;
import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.finagle.thrift.stock.LockInfo;
import com.jfshare.finagle.thrift.trade.BuyInfo;
import com.jfshare.finagle.thrift.trade.BuySellerDetail;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.PriceUtils;
import com.jfshare.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2015/10/20.
 */
public class TradeUtil {

    /**
     * 转换为锁库存参数
     * @param productList
     * @return
     */
    public static List<LockInfo> convertToLockInfos(List<OrderInfo> productList) {
        List<LockInfo> lockInfos = new ArrayList<LockInfo>();
        for (OrderInfo productInfo : productList) {
            LockInfo lockInfo = new LockInfo();
            lockInfo.setProductId(productInfo.getProductId());
            lockInfo.setSkuNum(productInfo.getSkuNum());
            lockInfo.setApplyCount(productInfo.getCount());
            lockInfo.setLockCount(0);
            lockInfo.setStorehouseId(productInfo.getStorehouseId());

            lockInfos.add(lockInfo);
        }

        return lockInfos;
    }

    /**
     * 转换为下单参数
     * @param buyInfo
     * @param sellerOrderIdsMap
     * @param productRets
     * @param addressInfo
     * @return
     */
    public static List<Order> convertToOrder(BuyInfo buyInfo, Map<Integer, String> sellerOrderIdsMap, List<ProductResult> productRets, AddressInfo addressInfo) {
        List<Order> orderList = new ArrayList<Order>();

        Map<String, Product> skuMaps = new HashMap<String, Product>(); //sku集合
        for (ProductResult productRet : productRets) {
            skuMaps.put(productRet.getProduct().getProductId() + "#" + ConvertUtil.getStringPlus(productRet.getProduct().getProductSku().getSkuNum()), productRet.getProduct());
        }

        List<BuySellerDetail> sellerDetails = buyInfo.getSellerDetailList();
        for (BuySellerDetail sellerDetail : sellerDetails) {
            Order order = new Order();
            DateTime curTime = new DateTime();
            long cancelMillis = ConvertUtil.getLong(PropertiesUtil.getProperty("jfx_trade_serv", "order_timeout", "30"))*60*1000;
            String orderBatch = buyInfo.getFromBatch() + "-" + sellerOrderIdsMap.get(sellerDetails.get(0).getSellerId()) + "-" + sellerOrderIdsMap.size();

            order.setOrderId(sellerOrderIdsMap.get(sellerDetail.getSellerId()));
            order.setTradeCode(StringUtil.isNullOrEmpty(buyInfo.getTradeCode()) ? ConstantUtil.TRADE_CODE.ORDER_CODE_ENTITY.getEnumVal() : buyInfo.getTradeCode().trim());
            order.setUserId(buyInfo.getUserId());
            order.setUserName(buyInfo.getUserName());
            order.setCreateTime(ConvertUtil.DateTimeToStr(curTime));
            order.setCancelTime(ConvertUtil.DateTimeToStr(new DateTime(curTime.getMillis() + cancelMillis)));
            order.setSellerId(sellerDetail.getSellerId());
            order.setSellerName(sellerDetail.getSellerName());
            order.setOrderState(ConstantUtil.ORDER_STATE.WAIT_PAY.getEnumVal());
            order.setBuyerComment(StringUtil.delHTMLTag(sellerDetail.getBuyerComment()));
            order.setLastUpdateTime(ConvertUtil.DateTimeToStr(curTime));
            order.setLastUpdateUserId(buyInfo.getUserId());
            order.setCreateUserId(buyInfo.getUserId());
            order.setWi(buyInfo.getWi());
            order.setFromSource(buyInfo.getFromSource());
            order.setOrderBatch(orderBatch);

            PayInfo payInfo = new PayInfo();
            payInfo.setPayState(ConstantUtil.PAY_STATE.INIT.getEnumVal());
            order.setPayInfo(payInfo);

            if (buyInfo.getDeliverInfo() != null) {
                DeliverInfo deliverInfo = new DeliverInfo();
                if (addressInfo.getProvinceId() > 0) {
                    Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(addressInfo);
                    BeanUtil.fillBeanData(deliverInfo, stringObjectMap);
                    TradeUtil.fillAlisDeliverInfo(deliverInfo, addressInfo);
                } else {
                    deliverInfo.setReceiverMobile(ConvertUtil.getStringPlus(buyInfo.getDeliverInfo().getReceiverMobile()));
                }
                order.setDeliverInfo(deliverInfo);
            }

            int totalPrice = 0;
            int totalPostage = 0;
            List<OrderInfo> productInfos = new ArrayList<OrderInfo>();
            for (OrderInfo item : sellerDetail.getProductList()) {
                item.setOrderId(order.getOrderId());
                //以数据库中查出的商品信息为准
                Product productDb = skuMaps.get(item.getProductId() + "#" + ConvertUtil.getStringPlus(item.getSkuNum()));
                Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(productDb);
                BeanUtil.fillBeanData(item, stringObjectMap);
                ProductSkuItem skuItem = productDb.getProductSku().getSkuItems().get(0);
                if (skuItem != null) {
                    item.setSellerClassNum(skuItem.getSellerClassNum());
                    item.setShelf(skuItem.getShelf());
                    item.setCurPrice(skuItem.getCurPrice());
                    item.setOrgPrice(skuItem.getOrgPrice());
                    //无sku图片取商品主图
                    if (!StringUtil.isNullOrEmpty(skuItem.getVPicture())) {
                        item.setImagesUrl(skuItem.getVPicture());
                    } else {
                        String imgKey = productDb.getImgKey();
                        if (!StringUtil.isNullOrEmpty(imgKey)) {
                            String[] imgs = imgKey.split(",");
                            if (imgs.length > 0) {
                                item.setImagesUrl(imgs[0].trim());
                            }
                        }
                    }
                    item.setSkuDesc(skuItem.getSkuName());
                    item.setThirdExchangeRate(productDb.getThirdExchangeRate());
                    item.setStorehouseId(skuItem.getStorehouseId());
                    item.setPostageTemplateId(productDb.getPostageId());
                    item.setWeight(skuItem.getWeight());
                    totalPrice += PriceUtils.strToInt(skuItem.getCurPrice()) * item.getCount();
                }
                productInfos.add(item);
            }
            order.setPostage(PriceUtils.intToStr(totalPostage));
            order.setClosingPrice(PriceUtils.intToStr(totalPrice + totalPostage));
            order.setProductList(productInfos);

            orderList.add(order);
        }

        return orderList;
    }

    /**
     * 填充DeliverInfo别名字段
     * @param deliverInfo
     * @param addressInfo
     */
    private static void fillAlisDeliverInfo(DeliverInfo deliverInfo, AddressInfo addressInfo) {
        deliverInfo.setReceiverMobile(ConvertUtil.getStringPlus(addressInfo.getMobile()));
        String telCode = StringUtil.isNullOrEmpty(addressInfo.getTelCode()) ? StringUtil.Empty : ConvertUtil.getStringPlus(addressInfo.getTelCode()) + "-";
        String telExt =  StringUtil.isNullOrEmpty(addressInfo.getTelExtNumber()) ? StringUtil.Empty : "-" + ConvertUtil.getStringPlus(addressInfo.getTelExtNumber());
        deliverInfo.setReceiverTele(telCode + ConvertUtil.getStringPlus(addressInfo.getTel()) + telExt);
        deliverInfo.setAddressId(addressInfo.getId());
        deliverInfo.setReceiverAddress(ConvertUtil.getStringPlus(addressInfo.getAddress()));
    }

    /**
     * 获取买家实际支付金额
     * @param item
     * @return
     */
    public static int getRealPayPrice(Order item) {
        int totalAmount = PriceUtils.strToInt(item.getClosingPrice());
        totalAmount -= PriceUtils.strToInt(item.getExchangeCash());
        return totalAmount;
    }

    /**
     * 获取买家支付非邮费部分金额
     * @param buyInfo
     * @param orderList
     * @return
     */
    public static int getPostageAmount(BuyInfo buyInfo, List<Order> orderList) {
        int totalPostage = 0;
        for(Order order : orderList)
            totalPostage += PriceUtils.strToInt(order.getPostage());

        return totalPostage;
    }
}
