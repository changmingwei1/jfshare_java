package com.jfshare.order.server.depend;

import com.google.common.collect.Lists;
import com.jfshare.finagle.thrift.aftersale.*;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.finagle.thrift.pay.PayServ;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.ridge.ConfigManager;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/10/31.
 */
@org.springframework.stereotype.Service
public class AfterSaleClient {
    private Logger logger = LoggerFactory.getLogger(AfterSaleClient.class);
    private AfterSaleServ.ServiceIface service = null;
    @Autowired
    private ConfigManager configManager;

    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "after_sale_serv_ips");
        String port = configManager.getConfigValue("jfx_public_client", "after_sale_serv_port");
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
        service = new AfterSaleServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

    public int queryAfterSaleOrderCount(AfterSaleOrderParam param) {
        try {
            Pagination pagination = new Pagination();
            pagination.setCurrentPage(1);
            pagination.setNumPerPage(1);
            AfterSaleOrderResult result = Await.result(service.queryAfterSaleOrder(param, pagination));
            if (result.getResult().getCode() == 0) {
               return result.getPagination().getTotalCount();
            } else {
                logger.warn("调用AfterSaleServ----" + param + ",失败结果=" + result.getResult());
            }
        } catch (Exception e) {
            logger.error("调用AfterSaleServ----异常！", e);
        }

        return 0;
    }

    public AfterSaleOrderResult queryAfterSaleOrder(AfterSaleOrderParam param) {
        try {
            int count = this.queryAfterSaleOrderCount(param);
            if(count == 0) {
                return null;
            }
            Pagination pagination = new Pagination();
            pagination.setCurrentPage(1);
            pagination.setNumPerPage(count);
            AfterSaleOrderResult result = Await.result(service.queryAfterSaleOrder(param, pagination));
            if (result.getResult().getCode() == 0) {
                return result;
            } else {
                logger.warn("调用AfterSaleServ.queryAfterSaleOrder----" + param + ",失败结果=" + result.getResult());
            }
        } catch (Exception e) {
            logger.error("调用AfterSaleServ.queryAfterSaleOrder----异常！", e);
        }

        return null;
    }

    /**
     * 查询订单售后信息
     * @param orderDetails
     * @return
     * @throws Exception
     */
    public List<AfterSale> queryAfterSales(List<Order> orderDetails) throws Exception {
        int size = orderDetails.size();
        int batchCount = 100;

        AfterSaleQueryParam param = new AfterSaleQueryParam();
        List<AfterSale> afterSales = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            param.addToOrderIdList(orderDetails.get(i).getOrderId());
            if(i % batchCount == 0) {
                AfterSaleResult result = Await.result(service.queryAfterSale(param));
                if(result.getResult().getCode() == 0 && CollectionUtils.isNotEmpty(result.getAfterSaleList())) {
                    afterSales.addAll(result.getAfterSaleList());
                }
                param.getOrderIdList().clear();
            }
        }

        return afterSales;
    }
}
