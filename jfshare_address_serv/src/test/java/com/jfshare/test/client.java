package com.jfshare.test;

import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.finagle.thrift.address.AddressServ;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class client {
    public void startClient() {
        TTransport transport;
        try {
            System.out.println("thrift client connext server at 12308 port ");
//            transport = new TFramedTransport(new TSocket("172.16.2.66", 12308));
            transport = new TFramedTransport(new TSocket("101.201.38.182", 1985));
//            transport = new TFramedTransport(new TSocket("localhost", 1985));
            TProtocol protocol = new TBinaryProtocol(transport);
            AddressServ.Client client = new AddressServ.Client(protocol);
            transport.open();

            AddressInfo addressInfo1 = new AddressInfo();
            addressInfo1.setUserId(17);
//            addressInfo1.setId(224150701);
            addressInfo1.setReceiverName("名字");
            addressInfo1.setMobile("13810008888");
            addressInfo1.setTel("88888888");
            addressInfo1.setTelCode("010");
            addressInfo1.setProvinceId(110000);
            addressInfo1.setProvinceName("北京市");
            addressInfo1.setCityId(110100);
            addressInfo1.setCityName("北京市");
            addressInfo1.setCountyId(110102);
            addressInfo1.setCountyName("朝阳");
            addressInfo1.setAddress("朝阳公园111");
            addressInfo1.setPostCode("100000");
            addressInfo1.setIsDefault(1);

            AddressInfo addressInfo2 = new AddressInfo();
            addressInfo2.setUserId(222);
//            addressInfo2.setId(220105901);
            addressInfo2.setReceiverName("团800");
            addressInfo2.setMobile("18610009999");
            addressInfo2.setTel("");
            addressInfo2.setProvinceId(2);
            addressInfo2.setProvinceName("辽宁省");
            addressInfo2.setCityId(20);
            addressInfo2.setCityName("大连市");
            addressInfo2.setCountyId(200);
            addressInfo2.setCountyName("甘井子区");
            addressInfo2.setAddress("甘井子大街5号");
            addressInfo2.setPostCode("116033");
            addressInfo2.setIsDefault(0);

            AddressInfo addressInfo3 = new AddressInfo();
            addressInfo3.setUserId(17);
//            addressInfo2.setId(12309);
            addressInfo3.setReceiverName("thrift");
            addressInfo3.setMobile("15810228592");
            addressInfo3.setTel("6843329");
            addressInfo3.setTelCode("010");
            addressInfo3.setProvinceId(110000);

            addressInfo3.setCityId(110000);
            addressInfo3.setCountyId(110000);
            addressInfo3.setAddress("甘井子大街5号");
            addressInfo3.setPostCode("116033");
            addressInfo3.setEmail("");
//            addressInfo3.setIsDefault(0);

            System.out.println(client.addAddress(addressInfo1));
//            System.out.println(client.updateAddress(addressInfo2));
//            System.out.println(client.delAddress(12333, 1402201002));
//            System.out.println(client.queryAddress(368469));
//            System.out.println(client.setDefaultAddress(123, 1402201001));
//            System.out.println(client.queryAddressById(225092809,111));
            transport.close();
            System.out.println("thrift client close connexion");
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("thrift client init ");
        client client = new client();
        System.out.println("thrift client start ");
        client.startClient();
        System.out.println("thrift client end ");
    }
}