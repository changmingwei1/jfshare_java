import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.finagle.thrift.pay.PayRes;
import com.jfshare.finagle.thrift.pay.PayServ;
import com.jfshare.finagle.thrift.pay.payRetQueryParams;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.utils.CryptoUtil;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class TestThrift {
    // 0的时候不执行测试用例，其他值的时候执行
    private  final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private PayServ.Client client;

    private  final String IP = "127.0.0.1";//"120.24.153.155";
//    private  final String IP = "101.201.38.182";//"120.24.153.155";

    private  final Integer PORT = 1993;


/*	public  static void main(String...client ) throws Exception{
	    setUp();
	    testQueryAddress();
	    tearDown();
	}
	*/

    @Before
    public  void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new PayServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }

    @org.junit.Test
    public void payUrl() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
//                params[[PayReq(tokenId:fvBLEJEHNOw=, orderNo:fa44514e9acb568385da064067e00feb, extraParam:24_4660024, title:聚分享订单, price:10, score:100, payChannel:1, payIp:null, returnUrl:, remark:null, custId:100017286150)]
                PayReq payReq = new PayReq();
                payReq.setTokenId("fvBLEJEHNOw=");
                payReq.setPayChannel(1);
                payReq.setOrderNo("9f7724514ddd94cf5a60352a2a01aa733");

                payReq.setPrice(1000);
                payReq.setScore(100);
                payReq.setPayChannel(1);
                payReq.setCustId("18979177165");
                payReq.setScore2cashAmount(100);
                payReq.setCustType("7");
                payReq.setProcustID("15");
                payReq.setTitle("jfx订单");
                StringResult stringResult = client.payUrl(payReq);
                System.out.println(stringResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void payRes() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
//                PayRes payRes = new PayRes();
//                payRes.setPayChannel(2);
//                payRes.setResUrl("{\"discount\":\"0.00\",\"payment_type\":\"1\",\"subject\":\"聚分享订单\",\"trade_no\":\"2016052221001004190216261526\",\"buyer_email\":\"huazhehuo110@163.com\",\"gmt_create\":\"2016-05-22 19:30:06\",\"notify_type\":\"trade_status_sync\",\"quantity\":\"1\",\"out_trade_no\":\"0c34b1140a80ab2c5d024213221d159b\",\"seller_id\":\"2088701691132875\",\"notify_time\":\"2016-05-22 19:44:51\",\"body\":\"订单支付\",\"trade_status\":\"TRADE_SUCCESS\",\"is_total_fee_adjust\":\"N\",\"total_fee\":\"0.01\",\"gmt_payment\":\"2016-05-22 19:30:07\",\"seller_email\":\"windy@jfshare.com\",\"price\":\"0.01\",\"buyer_id\":\"2088012954102192\",\"notify_id\":\"0e32c605099e9a23c7ad2886cf62b52hgu\",\"use_coupon\":\"N\",\"sign_type\":\"RSA\",\"sign\":\"aNrgIFblGOtum6pN6MyuRE8L3b9QlzICJzBkKt8xwU4iv4JJ3M+poW7mq66WdV3LWaBf2SwclGYugUBtKkfmxe5sVZ7XSCbsCrISUIZ1kuP3qCPr9qaOgAr4MkJr/elC47aGKMw5BUNRkAj32XdEYIKZGDfcVXXII8VSOd0xczI=\"}");

                PayRes payRes = new PayRes();
                payRes.setPayChannel(1);
                payRes.setResUrl("acsg9cHcHzhnalO9NUk/9VA4IKB/wGGqVIXGXjnhdGV3LlXC7sEpwJrJm6vjgN9hun5IYPZSpAN9cIw/wERKAUaqi5f r63ApoxOcb5ZR0HILD/A6MYUaCFUeU637fZAK06uNnwOVg 0RkPaam3K AS5nSMR5387AoSRDXBZPTWROZLyIVApBaBq8yOuO46HZ7 3gs0776N6SPlIbyNL4NAMN2SN05TKDWv/pMJMnuOmz9pIbjGiUpiQ60RgjzAseHtsk2cTaQM6V5g FaRxICDeJQgDc0t4byzDzz0b6NvWO17uwoHPk47DRl4ZF8WL5 ExLYqSj2ULddWa9N181S72tfQfqSpVPkQKkdviRqK4gIONYfli6WFwoyhVMSDcSnuAxl4xme/ JONoZslCLk0Pp3A7IyVINPrDaV7HkISpZ14RwLoFpuLmyjXn ka5DNH4U 3vtUbuCdaItTWAHVc3RJVxP4XOarZW9E73E1rqXdiqftXFBE6Skf0FW0Zk25s3tjPXWWQgNf7WkupUyXd6OCpTOHjLas9lzkbNlSAO3NeBz4U6NrHQ14 FpbU2aW0VC5/f9I2GPjHVipSRLORvdy0IG8dMhRvYeep5meQMwa0m9ywxn33mzkN6BMGz8pTYv4qxVlb1sWIlrNqPewM6dqDqrdic3RnlHxU6cS00W lQIrtPRcqlazbDbhW9JfovGiW6APw4dfnYClOSzLD/nnZ2Fg2mj ZufZKXyTIeX6njpFRtrVD0g tmB0X0t8iFj8/dCvNu3D29MPIoLqbASbRaiZ7FE o3OMGghRLySg7 j4RmEXtk1IkngVMHMRG5XI63gq0u0IXk0szOBWzpqpqd7eiQ36IOVhqaxNQYmK08huSOSE/GVWAHyF/a7AZBAviFKso8ldCt2c5ImBpi5qGMBs1//5IL8KR8ENpYWek3t6rkM6RiWV31e6iErf1  btjAsqdauEExStrdU3CEaqSyR2itDxT48gjtcYGIBCAUBmF4fz6TpHMuc uGn PC/s4ED45HPmv6Z8FNy2exMbvm7pwE88I6aR/WSI3R3O1b 28dO5 lNqm6Lz8 yFQs7/EbW2LYH0J3mqCWuxOcWUm3xgSELvq6HO37GjSnj8n6IbgecSi H5BgO9tZp0mrfkyLM M5PVNv4UyMyw9ISo2Qo8IQzm2TvErT31nLlkEJ70cqu5RQkw8PQsQxN6QdspdTuuAuIXWtZfDSNRd5Z6vLzcyo7feyyHDL1dCAa27MmrOUKVcPt2u 2chpDFno28sCWpazZMoE9QH2x6vCIr7RPLp0YmQ8F79a3a1znNjFCRESnuD9qWj BrZwI9Pi6Gho81tn1HUz7Tgmuo5nIq0KH3zqD0WW/0Hntd6gIRcWzcKdS4/Tq8rKmGOWfoIDGTLm59LgAyIqF2rzZRgDdvJYhknG7DCpzv1/e1D65QqQ/pqTN/zRisBW4iLJt4 YEkaRlcNtvRCpzQ0FmoULS8B5t Q6r9kYDqN1NaoSz3wUCiq/MUkeeVByA8sE0Zjro/kuyKGmrp0jlYW57fiBuPYBQW3Z3TfRDUc1YYG1LFlODhWhwNBCCLAE4wvtiQcoeGt9YO0ku5iY8e17Awy af2omZtW3/iCmKwsWlgQ61PG2/zYhDYT8vHWeA K7eDMvDiBze7EMACykw1HHUjc7p4CrJNe 6B9mNggcBw01nsunr9Pna72Ul6GDH7p/C252rsST2cnwZw1 JmWv WewtQCzpkhySth9KqXlyyeaydwfrQPbS6G/FjFPussi80fqHMUtuD21eKRMhnX/5gIhdY9Xx mC1gTmEQzAinjTgZA90x2G0BNN mTbKPEpLcJN1X5NbK47NHEj4KVISWw2p5uiDZLTaWKmtkA2UmSPOq3K9EjarwUh2y AyVvZVlWT3UYVhkpgRoyfJ7kIEsVI5g9DPk0wxbkCE9TdrcMoxdCNHxfUvF12vmuBUbmsX5HwOvJTuBE/aw/ HqWMx15CELdYGe0k5ceetMe7Y1DV s2Qp/oypHgrvHnRekJNE31qN8UsFGBITUUr/Gbx7HXTETJedz9W929qUIcwQlyVpBFhGlByPYGbakx2oPp3/EA/BqF2yy/2dmBDS vNoV PdhLT1NliNDpDS/WVl2r baOFO1JlI/fz9hAnDemQjes4BfwuKE19Lp4o1P9lsdetNeX84L/I2xkKeQWpY0DIAwj1PyUoaxlOO/c1Kk7IBXjL5bDxyh1o6DWc98M6rcsNRM245X/Y1A27FNMp9uUTwxByGAu7T YTEIF/D8Zv/4TuSiNX7cJgi/3uM4x GsUXfePzBhSLv25iE0egQ8h8A2soov28lHYboUYHfApNFDGfvP4UZAracER7IJ CZAXbAz/aAbGghM2hErhS2pCG2Vo0DmJ WLBj7fBrKl");

                StringResult stringResult = client.payNotify(payRes);
                System.out.println(stringResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void queryPayResult() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
                payRetQueryParams param = new payRetQueryParams();
                param.setPayId("2016062714443821852905");
                StringResult stringResult = client.queryPayResult(param);
                System.out.println(stringResult);
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }
}
