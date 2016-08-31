package com.jfshare.card.dao.mysql;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfshare.card.model.TbBatchActivity;
import com.jfshare.card.model.TbBatchCardsRecord;
import com.jfshare.card.model.TbBatchCardsRecordExample;
import com.jfshare.card.model.mapper.TbBatchCardsRecordMapper;
import com.jfshare.card.server.ServHandle;
import com.jfshare.card.service.IScoreCardServ;
import com.jfshare.card.util.ScoreCardUtil;
import com.jfshare.card.util.TypeStatEnum;
import com.jfshare.finagle.thrift.card.ActivityBatchResult;
import com.jfshare.finagle.thrift.card.ActivityBean;
import com.jfshare.finagle.thrift.card.ActivityQueryParam;
import com.jfshare.finagle.thrift.card.CardQueryParam;
import com.jfshare.finagle.thrift.card.CardRecordBatchResult;
import com.jfshare.finagle.thrift.card.DirectRechargeResult;
import com.jfshare.finagle.thrift.card.ExcelExportResult;
import com.jfshare.finagle.thrift.card.InvalidOneActivityResult;
import com.jfshare.finagle.thrift.card.RechargeParam;
import com.jfshare.finagle.thrift.card.ToRechargeParams;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.utils.DateUtil;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jfshare.card.util.ScoreCardUtil.decryptPsd;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class BatchActivityDaoTest {

    private  Logger logger = LoggerFactory.getLogger(ServHandle.class);

    @Autowired
    private BatchActivityDao batchActivityDao;


    @Autowired
    private IScoreCardServ  iScoreCardServ;


    @Autowired
    private BatchCardDao batchCardDao;


    @Autowired
    private ServHandle handler;

    @Autowired
    private TbBatchCardsRecordMapper tbBatchCardsRecordMapper;

    private Pagination pagination;

    //初始化 分页信息
    @Before
    public void testBefore(){
        pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(3);
    }


    @Test
    public void insertOne() throws Exception {
        /*for(int i=0;i<5;i++){

            TbBatchActivity entity=new TbBatchActivity();
            entity.setName("测试 " +i);
            entity.setCreateTime(new DateTime());
            entity.setEndTime(new DateTime(DateUtil.addDay(new Date(),10+i)));
            entity.setMultiRechargeEnable("2");
            entity.setCurStatus("1");
            entity.setPieceValue((60+i*5));
            entity.setTotalCount(24+i);
            entity.setRechargeType("1");
            batchActivityDao.insertOne(entity);
        }*/


        int i=9;

        ActivityBean entity=new ActivityBean();
        entity.setName("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十");
//        entity.setCreateTime(new DateTime());
//        entity.setEndTime(new DateTime(DateUtil.addDay(new Date(),10+i)));
        entity.setEndTime("2016-10-12 12:12:12");
        entity.setStartTime("2016-02-03 12:12:12");
        entity.setMultiRechargeEnable("0");
        entity.setCurStatus("1");
        entity.setPieceValue("60");
        entity.setTotalCount(84+i);
        entity.setRechargeType("1");
//        batchActivityDao.insertOne(entity);

        handler.createOneActivity(entity);
        // 生成对应的 充值卡

        System.out.println("entity.getId() = ");


    }


    //    @Test
    public void updateOne() throws Exception {

        System.out.println(iScoreCardServ!=null ? "  22 is not null":"null");

        ActivityBean params=new ActivityBean();
        params.setPieceValue(20+"");
        params.setName("22");
        params.setStartTime("2016-02-03 12:21:21");
        params.setEndTime("2016-09-03 12:21:21");
//        params.setCurStatus("3");
        params.setRechargeType("1");
        params.setMultiRechargeEnable("0");
        params.setTotalCount(9);


//        iScoreCardServ.createOneActivity(params);


    }


    //    @Test
    public void batchInsert() throws Exception {

        List<TbBatchCardsRecord> list = new ArrayList<TbBatchCardsRecord>();
        for(int i=0;i<60;i++){

            TbBatchCardsRecord entity=new TbBatchCardsRecord();
            entity.setActivityId(1);
            entity.setCardName("手机充值卡");
            entity.setCardPsd("psd22222");
            entity.setSendStatus(TypeStatEnum.Card_Sended.getState());
            entity.setRechargeStatus(TypeStatEnum.Card_Un_Recharge.getState());

            list.add(entity);

        }

        batchCardDao.batchCommit(1000,list);


    }
    //    @Test
    public void deleteOne() throws Exception {

        System.out.println(batchCardDao!=null?" not null ":"null");
    }

    //    @Test
    public void queryByParams4Activity() throws Exception {
        Pagination p = new Pagination();
        p.setCurrentPage(1);
        p.setNumPerPage(3);
        ActivityQueryParam params=new ActivityQueryParam();
//        params.setName("2");
        params.setMinEndTime(DateUtil.date2Str(DateUtil.addDay(new Date(),-1)));
//        iScoreCardServ.queryActivities(params,p);


    }
    //    @Test
    public void queryByParams4Cards() throws Exception {
        Pagination p = new Pagination();
        p.setCurrentPage(1);
        p.setNumPerPage(3);
        CardQueryParam param = new CardQueryParam();
        param.setCardName("jfx135807");
//        iScoreCardServ.queryCards(13, param,p);

    }


    //    @Test
    public void testServHandler(){

        System.out.println(tbBatchCardsRecordMapper!=null?"is  not  null  ":"null null");

        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(1, 10);
        TbBatchCardsRecordExample params=new TbBatchCardsRecordExample();
        List<TbBatchCardsRecord>  list =batchCardDao.queryByParams(params);
        System.out.println("list size = "+list.size());

        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);
        System.out.println("page.getPageNum = " +page.getPageNum());
        System.out.println("page.getPageSize = " +page.getPageSize());
        System.out.println("page.getStartRow = " +page.getStartRow());
        System.out.println("page.getEndRow = " +page.getEndRow());
        System.out.println("page.getTotal = " +page.getTotal());
        System.out.println("page.getPages = " +page.getPages());
        System.out.println("page.getFirstPage = " +page.getLastPage());




    }




    //    @Test
    public void testQueryActivity() throws TException {

        ActivityQueryParam params=new ActivityQueryParam();
        params.setName("Test");
        Pagination p = new Pagination();
        p.setCurrentPage(1);
        p.setNumPerPage(20);

        ActivityBatchResult result =handler.queryActivities(params,p);

        System.out.println(result);

    }

    //    @Test
    public void testdirectRecharge() throws Exception{
        DirectRechargeResult directRechargeResult=new DirectRechargeResult();

        Result result=new Result();
        directRechargeResult.setResult(result);

        ToRechargeParams params=new ToRechargeParams();
        params.setActivityId("46");
        params.setFilePath("F:\\");
        params.setValidataStr("123123123");
        iScoreCardServ.directRecharge(directRechargeResult,params);

    }
    @Test
    public void  testJM(){
        String pString = decryptPsd("339e1f0117062dad");
        String pString1 = decryptPsd("38c14f51460674ae");
        String pString2 = decryptPsd("669a1f5f120a21fe");
        String pString3 = decryptPsd("34c81a05400470af");
        String pString4 = decryptPsd("339d1b5f1c0173ad");
        String pString5 = decryptPsd("32cf1c01165120af");
        String pString6 = decryptPsd("38cc4b5e410a77af");
        String pString7 = decryptPsd("64cf4805160126af");
        String pString8 = decryptPsd("64ca1d02175777af");
        String pString9 = decryptPsd("65cb4853130721f9");
        System.out.println("====================jfx595115====================" + pString);
        System.out.println("====================jfx592130====================" + pString1);
        System.out.println("====================jfx595926====================" + pString2);
        System.out.println("====================jfx591319====================" + pString3);
        System.out.println("====================jfx596523====================" + pString4);
        System.out.println("====================jfx593324====================" + pString5);
        System.out.println("====================jfx591916====================" + pString6);
        System.out.println("====================jfx592727====================" + pString7);
        System.out.println("====================jfx597120====================" + pString8);
        System.out.println("====================jfx594732====================" + pString9);
    }


//    @Test
    public void testQueryRechargeCardList() {
        CardRecordBatchResult result = new CardRecordBatchResult();
        iScoreCardServ.queryRechargeCards(result, 60, pagination);

        System.out.println(result);

    }


//    @Test
    public void testInvalidActivity(){
        try {

            InvalidOneActivityResult result =handler.invalidOneActivity(42,"123456");
            System.out.println("######   "+result + "    ######");

        } catch (TException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDecryPsd(){

        String temp =ScoreCardUtil.decryptPsd("34cf4f02465273a8");
        System.out.println("######   "+temp + "    ######");

    }


    @Test
    public void testExportExcel(){
        try {
            CardQueryParam params=new CardQueryParam();
//            params.setCardName("jfx4506087");

            ExcelExportResult result  =handler.exportExcelByqueryCards(450,params,"123456");
            System.out.println(result);
        } catch (TException e) {
            e.printStackTrace();
        }


    }

//    @Test
    public void testRuntimeError(){

        logger.info("infof  info infof  info infof  info infof  info infof  info infof  info infof  info ");
        logger.error("errorerrorerrorerrorerrorerrorerrorerror");

    }

    @Test
    public void testRechargeError(){

        ToRechargeParams params = new ToRechargeParams();

        try {

            params.setActivityId("473");
            params.setValidataStr("123456");
            params.setFilePath("http://120.24.153.102:3000/system/v1/jfs_image/DB67E2F3E79BD83AAA0BAEB1BA70EC2F.xlsx");

            handler.directRecharge(params);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }







}