package com.jfshare.order.server.depend;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.trade.*;
import com.jfshare.order.model.OrderModel;
import com.jfshare.order.model.TbOrderInfoRecord;
import com.jfshare.order.util.FailCode;
import com.jfshare.ridge.ConfigManager;
import com.jfshare.utils.PriceUtils;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/10/31.
 */
@org.springframework.stereotype.Service
public class TradeClient {
    private Logger logger = LoggerFactory.getLogger(TradeClient.class);
    private TradeServ.ServiceIface service = null;
    @Autowired
    private ConfigManager configManager;

    @Autowired
    private ScoreClient scoreClient;


    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "trade_serv_ips");
        String port = configManager.getConfigValue("jfx_public_client", "trade_port");
        StringBuilder hosts = new StringBuilder();
        String[] ipArray = ips.split(";");
        for(String ip : ipArray) {
            hosts.append(ip.trim()).append(":").append(port);
        }
        ClientBuilder<ThriftClientRequest, byte[], ClientConfig.Yes, ClientConfig.Yes, ClientConfig.Yes> clientBuilder = ClientBuilder.get()
                .codec(ThriftClientFramedCodec.get())   //设置解码器类型
                .hosts(hosts.toString())    //设置访问ip和端口
                .hostConnectionLimit(100)  //连接池
                //.timeout(new Duration(5000L))  //总的请求超时
                .retries(3)   // 重试次数
                .keepAlive(true)     //是否是长连接
                ;
        Service<ThriftClientRequest, byte[]> client = ClientBuilder.safeBuild(clientBuilder);
        client.isAvailable();
        service = new TradeServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }


    public List<FailDesc> score2cash(List<OrderModel> orderList, BuyInfo buyInfo) {
        //未使用积分抵现, 直接返回
        if (NumberUtils.toInt(buyInfo.getExchangeCash()) == 0 && buyInfo.getExchangeScore() == 0)
            return null;

        List<FailDesc> failDescs = new ArrayList<FailDesc>();
        ExchangeParam param = new ExchangeParam();
        param.setAmount(buyInfo.getAmount());
        param.setScore(String.valueOf(buyInfo.getExchangeScore()));
        param.setUserId(buyInfo.getUserId());

        for (OrderModel order : orderList) {
            for (TbOrderInfoRecord orderInfo : order.getTbOrderInfoList()) {
                ExchangeProduct e = new ExchangeProduct();
                e.setProductId(orderInfo.getProductId());
                e.setSkuNum(orderInfo.getSkuNum());
                e.setPrice(PriceUtils.intToStr(orderInfo.getCurPrice() * orderInfo.getCount()));
                param.addToProductList(e);
            }
        }

        Map<String, ExchangeDetail> orderExchangeResMap = new HashMap<String, ExchangeDetail>();
        try {
            ExchangeResult exchangeScore = Await.result(service.getExchangeScore(param));
            if (exchangeScore != null
                    && exchangeScore.getResult().getCode() == 0
                    && CollectionUtils.isNotEmpty(exchangeScore.getExchangeDetailList())) {
                for (ExchangeDetail edetail : exchangeScore.getExchangeDetailList()) {

                    orderExchangeResMap.put(edetail.getProductId().concat(":").concat(edetail.getSkuNum()), edetail);
                }
            } else {
                failDescs.addAll(exchangeScore.getResult().getFailDescList());
                return failDescs;
            }
        } catch (Exception e) {
            failDescs.add(FailCode.SYS_ERROR);
            logger.error("调用积分服务异常, buyerId={}, exchangeScore={}", buyInfo.getUserId(), buyInfo.getExchangeScore());
            return failDescs;
        }

        for (OrderModel order : orderList) {
            int exchangeCashOrder = 0;
            int exchangeScoreOrder = 0;
            for (TbOrderInfoRecord orderInfo : order.getTbOrderInfoList()) {
                ExchangeDetail e = orderExchangeResMap.get(orderInfo.getProductId().concat(":").concat(orderInfo.getSkuNum()));
                orderInfo.setExchangeCash(PriceUtils.strToInt(e.getAmount()));
                orderInfo.setExchangeScore(Integer.parseInt(e.getScore()));
                exchangeCashOrder += PriceUtils.strToInt(e.getAmount());
                exchangeScoreOrder += Integer.parseInt(e.getScore());
            }
            order.setExchangeCash(exchangeCashOrder);
            order.setExchangeScore(exchangeScoreOrder);
        }

        //扣减用户积分
        scoreClient.reduceScore(buyInfo.getUserId(), buyInfo.getExchangeScore());

        return failDescs;

    }
}
