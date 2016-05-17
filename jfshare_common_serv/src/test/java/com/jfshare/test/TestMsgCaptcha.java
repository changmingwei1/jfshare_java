package com.jfshare.test;

import com.jfshare.finagle.thrift.common.Captcha;
import com.jfshare.finagle.thrift.common.CommonServ;
import com.jfshare.finagle.thrift.common.MsgCaptcha;
import com.jfshare.finagle.thrift.common.ShortMsg;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.*;

/**
 * Created by Lenovo on 2016/3/28.
 */
public class TestMsgCaptcha {
    @org.junit.Test
    public void sendMsgCaptcha(){
        TTransport transport = null;
        try {
//            transport = new TSocket("localhost", 1984);
            transport = new TSocket("120.24.153.155", 1984);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(new TFramedTransport(transport));

            CommonServ.Client client = new CommonServ.Client(protocol);
            MsgCaptcha msgCaptcha = new MsgCaptcha();
            msgCaptcha.setMobile("18610418281");
            msgCaptcha.setType("buyer_signin");
//            msgCaptcha.setCaptchaDesc("5L4N");
//            System.err.println("result:"+client.validateMsgCaptcha(msgCaptcha));
//            System.err.println("result:"+client.sendMsgCaptcha(msgCaptcha));
            ShortMsg shortMsg = new ShortMsg("18610418281", "您已成功购买*****（商品名称+规格）消费券*（数量）1张，券码123456789，密码a1231，券码*********，密码*********，请前往商家验证消费。");
            System.err.println("==>" + client.sendMsg(shortMsg));
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            transport.close();
        }
    }
}
