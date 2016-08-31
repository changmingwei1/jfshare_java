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

//    @org.junit.Test
//    public void payUrl() throws TException {
//        try{
//            if (DO_IT == 0)
//                return;
//            System.out.println("start:" + System.currentTimeMillis());
//            for(int i=0; i<DO_IT; i++){
////                params[[PayReq(tokenId:fvBLEJEHNOw=, orderNo:fa44514e9acb568385da064067e00feb, extraParam:24_4660024, title:聚分享订单, price:10, score:100, payChannel:1, payIp:null, returnUrl:, remark:null, custId:100017286150)]
//                PayReq payReq = new PayReq();
//                payReq.setTokenId("fvBLEJEHNOw=");
//                payReq.setPayChannel(1);
//                payReq.setOrderNo("9f7724514ddd94cf5a60352a2a01aa733");
//
//                payReq.setPrice(1000);
//                payReq.setScore(100);
//                payReq.setPayChannel(1);
//                payReq.setCustId("18979177165");
//                payReq.setScore2cashAmount(100);
//                payReq.setCustType("7");
//                payReq.setProcustID("15");
//                payReq.setTitle("jfx订单");
//                StringResult stringResult = client.payUrl(payReq);
//                System.out.println(stringResult);
//            }
//            System.out.println("end:" + System.currentTimeMillis());
//        }catch(Exception e){System.out.println(e);}
//    }

    @org.junit.Test
    public void payRes() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                PayRes payRes = new PayRes();
                payRes.setPayChannel(10);
                payRes.setResUrl("acsg9cHcHzhnalO9NUk/9VA4IKB/wGGqVIXGXjnhdGV3LlXC7sEpwJrJm6vjgN9hun5IYPZSpAN9cIw/wERKAUaqi5f r63ApoxOcb5ZR0HILD/A6MYUaCFUeU637fZAK06uNnwOVg 0RkPaam3K AS5nSMR5387AoSRDXBZPTWROZLyIVApBaBq8yOuO46HZ7 3gs0776N6SPlIbyNL4NAMN2SN05TKDWv/pMJMnuOmz9pIbjGiUpiQ60RgjzAseHtsk2cTaQM6V5g FaRxICDeJQgDc0t4byzDzz0b6NvWO17uwoHPk47DRl4ZF8WL5 ExLYqSj2ULddWa9N181S72tfQfqSpVPkQKkdviRqK4gIONYfli6WFwoyhVMSDcSnuAxl4xme/ JONoZslCLk0Pp3A7IyVIBT  1YWkR7ZNap3Ez4wOYAWVrX0fvbc1fU7FouYjYsci26qVEyBJ3wp/934bki RanyJyoGZekXPwOVqrti6WH9iOL6y  qOSAhAaJz39DcYQWgcKtTulYN2idVridmz4R5 y4JjGTXFtx0lgI125rmyrLF1sdtUfojcPd4stJE/l6RJbxLNwMPC79pKaUC2l02ajoKaXRGn0azHv9l1unyFosSGRRncXdQER67XFHAaUNW3UGawOgXFqoXmJdwRQLG8AMb0NICVuifX6QtbU5ly7Kq0fW9hrXtrOB2svrkxPJmBW7S4hpGk6LFMyc8b8UZ46XMb6Q0NtxZDuLJbhce D  CgNL2gLbv67iJis9QPJgBh2MTv2Uj2gETCHSelmWF48OJUyx9fn0gN5CtKqD80vy5SBkiZErpgqx0JwB0GbIidDlIAyo33enkocOFqASUAYhCBaBos/OtzZ8QjnFZgQI YbS3KglD8njDWYHJohdX/kwzJ8RJjfRsW 5H6UGW6b3YollKc5OLjN4o6HfIHJpwY7nWWQT6xTFWILL4ebEQRypo0G2STfjPBmHPUGg/PyJYLs4vmN7sgKMKG3 M9zdmQJa3dDxo8RC1URfiDuwoaX0ogDJLue7yJ5a0WYLKtPlz/bx5DiXLq9D/wImsMZBBigg6C4kct1oPHLRIKbNfaHOJPasQC3GsibfFyDBPALadO2kR3HRAJfT4OFAE51wG3GjcIo6sMQSbe809GAMM1NAbRNIrr/i BgxKcFlTnUT2mXl4qI4UZ23W0l9rOIoofJbytL9CNHbHEUZUn2M3WQSXeA7AnMXQ47 krrjSOlZ4XSgavIXjkdqdv8CdZvu07UFgKVF6bNsIynNem6aTy0cBWg4ffDZ1D8mY9fWnoOBMfET3Io50TzDVaZzh/o6x0HkZLnUQEE1kwRqg1BKquOFghzisZkrwCbhpFvtOp0kFK faIXtN2L/whbAXfp42r2Bk2XQpZgxZc6N4uSPMEoJ7DUo66Anzk7jycGWWhgY58beInon3vpY/Km7iz6DOBqSE62FgK85GsP olbLxF2w0BdO1QlZahENfKR/mJtCzX5DQJqLwbMESvJmP8sitG4vUMuc796v3rWEYPXg08LOeLMKxq8 nqo9rSkOVqZg6Dt8haAyyzYfDlbYPeoDNASPinDBMYdcf0IZS2KvW3YEUKZ5RUKzlZvDsGoPuWPQHePlPPuU dMUaM/LoMnWdvSLGyS8Y1YI1aTj5lQUdPDkWZHCqfP8SBl6f/B90qKba2M06Xh5F4Q iHb/U1jc79dTJBPQrBt91ydAa5264S8t/ygDvka6GNbFVmCPyIJGJbefhvmqbKLTWMUo13ErqKWsnL5bwm0u0Oh9WfqaxMzy4ZQX3XxdyzuSMFHCaQRou5ipL2u4vD0xrRf3gArl5aX5ac u1RO3LFOcRGqEPVYFwgB9Im2bOhWbKhRV9IrYwa8m48B4otWZaFA8EqPyB1PbCl L6PoFnS8VNgBD9AfJo1xGHMuUD0kjYQkYFw00e0ERXIfKFUWKUFg/C21kPoW9OUmyVrDZLdUTajjxcF5rrAaVShj6AbWVOe/GKN9KpP7y0D9h3sT3b8ijJ0cuq/gJpvS0l0L/90pZS0BkbeEheqPNY1h5buH4sstvgjnIMMP/XQXws/lZti2H9BgWyEXDIjULql/hI4Cu5bOTwNN9FaO3bleL7o/uaQin/JeJ4ylqU5LtlTjLVW09xMCURbC/yE55hm3f3z6mvwZuy5Qh OIEkQvCpYfuurJaGiVjly05KZcoJP89xSDiJd2axCswRNnaai y0lhjaqLFfOQ94ubgb3Whp9GDJpSdmfO1d HERLkxsdnZPdDR8uG tvEFZVE1UWGebjKGbuV4tTI5Z04ouCJ16egckvJMvWtk946x50Ggy5hYtqRliIv8xDPqJAoCjwZTJRYcG4HUk3P7CzWQceYmUtvYS6VMyhN3OCJvEW3Rrq2pSnpIiTtZ9cp67gUQLUjd2Ysve30ThIReBD63uS1ZKyzijGtlm4wC/0CJYYtkO0xKCVFJ7Wbrbhz1KZlAKWBMlW8vAsXY3pzsdBc54ajpRuDgiQPFE5xD1BMnI1uzipBSC1vJ3zzuXmL1k7o2yInV5NU4bQJkLdLvM7ZEbbFDEkfVwqYoGi p8fz4=");
                StringResult stringResult = client.payNotify(payRes);
                System.out.println(stringResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

//    @org.junit.Test
//    public void queryPayResult() throws TException {
//        try{
//            if (DO_IT == 0)
//                return;
//            System.out.println("start:" + System.currentTimeMillis());
//                payRetQueryParams param = new payRetQueryParams();
//                param.setPayId("2016062714443821852905");
//                StringResult stringResult = client.queryPayResult(param);
//                System.out.println(stringResult);
//            System.out.println("end:" + System.currentTimeMillis());
//        }catch(Exception e){System.out.println(e);}
//    }
}
