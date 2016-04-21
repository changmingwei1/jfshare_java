package com.jfshare.express.server;

import com.jfshare.finagle.thrift.express.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


/**
 * Created by IntelliJ IDEA.
 * User: jerry
 * Date: 12-2-7
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
public class client {
    public static void main(String[] args) {
        try {
//            TTransport transport = new TSocket("localhost", 12309);
//            TProtocol protocol = new TBinaryProtocol(transport);
//            TTransport transport = new TFramedTransport(new TSocket("192.168.10.57", 12309));
//            TTransport transport = new TFramedTransport(new TSocket("192.168.10.66", 12309));
            TTransport transport = new TFramedTransport(new TSocket("127.0.0.1", 1989));
            transport.open();
//            ExpressServ.Client client = new ExpressServ.Client(protocol);
            ExpressServ.Client client = new ExpressServ.Client(new TBinaryProtocol(transport));
            ExpressInfo expressInfo = new ExpressInfo();
//            expressInfo.setName("顺丰");
//            expressInfo.setQueryUrl("");

//            ExpressInfo expressInfo1 = new ExpressInfo();
//            expressInfo1.setName("wo风");
//            expressInfo1.setQueryUrl("http://www.yto.net.cn/cn/index/index.html");
//            expressInfo1.setNameRule("\\s*风\\s*");
//            expressInfo1.setSerialNum(222);
//            StringResult addExpress = client.addExpress(expressInfo1);
//            System.out.println(addExpress.getResult().code);
//            if (addExpress.getResult().code == 1) {
//            	System.out.println(addExpress.getResult().getFailDescList());
//            }

//            ExpressInfo expressInfo2 = new ExpressInfo();
//            expressInfo2.setId(378);
//            expressInfo2.setName("sss");
//            expressInfo2.setQueryUrl("dddd");
//            expressInfo2.setNameRule("^风$");
//
//            System.out.println(client.updateExpress(expressInfo2));
            
            ExpressQueryConditions conditions = new ExpressQueryConditions();
            //conditions.setName("天");
            //conditions.setCurPage(2);
            //conditions.setPageSize(10);
//            conditions.setId(74);
//            conditions.setGrabState(-1);
//            conditions.setOrderByClause("id desc");
//            ExpressInfoPaginate result = client.queryByConditions(conditions);
//            System.out.println(result.getResult().getCode());
//            System.out.println(result.getTotal());
//            System.out.println(result.getPageCount());
//            System.out.println(result.getExpressInfoList().toString());
            /*subscribeExpressPost --------------------*/
            SubscribeReq req = new SubscribeReq();
            req.setNumber("5532852471719");
            req.setCompany("");
            req.setOrderId("123334");
            req.setComId(2);
            System.out.println(client.subscribeExpressPost(req));
            /*subscribeExpressPost --------------------*/
//            SubscribeRes res = new SubscribeRes();
//            res.setNu("550285247179");
//            res.setCom("tiantian");
//            res.setStatus("polling");
//            res.setIscheck("1");
//            res.setTraceJSON("测试记录");
//            System.out.println(client.subscribeExpressRecord(res));
//            System.out.println(client.queryEx());
//            System.out.println(client.queryById(23));
//            System.out.println(client.deleteExpress(2));
            
//            List<Integer> idList = new ArrayList<Integer>();
//            idList.add(0);
//            idList.add(74);
//            idList.add(1);
//            ExpressInfoResult queryBatch = client.queryBatch(idList);
//            System.out.println(queryBatch.getExpressInfoList().toString());
            
//            ExpressInfoResult queryFreight = client.queryFreight();
//              System.out.println(queryFreight.getExpressInfoList().toString());
//            ExpressParams params = new ExpressParams();
//            params.setOrderId("123465");
//            params.setComId(1);
//            params.setQueryType(2);
//            ExpressResult result = client.expressQuery(params);
//            System.err.println("expressQuery result:" + result);
//            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

}
