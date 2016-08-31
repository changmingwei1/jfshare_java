package com.jfshare.card.dao.mysql;

import com.jfshare.finagle.thrift.card.CardQueryParam;
import com.jfshare.finagle.thrift.card.ScoreCardServ;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

/**
 * 测试excel导出
 * chiwenheng
 * 2016-08-12-34
 */
public class TestExcelExport {


    public static void main(String[] args) throws Exception {

        TFramedTransport transport = new TFramedTransport(new TSocket("127.0.0.1", 2003));
        TBinaryProtocol protocol = new TBinaryProtocol(transport);
        ScoreCardServ.Client client = new ScoreCardServ.Client(protocol);
        transport.open();
        CardQueryParam params =new CardQueryParam();
        params.setCardName("jfx133530");
        client.exportExcelByqueryCards(13,params,"1223");
        transport.close();

    }

}
