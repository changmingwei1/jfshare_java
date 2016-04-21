package com.jfshare.express.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.express.kuaidi100.Constant;
import com.jfshare.express.kuaidi100.pojo.TaskRequest;
import com.jfshare.express.model.pojo.TbExpressTrace;
import com.jfshare.finagle.thrift.express.ExpressTrace;
import com.jfshare.finagle.thrift.express.SubscribeReq;
import com.jfshare.finagle.thrift.express.SubscribeRes;
import com.jfshare.finagle.thrift.express.TraceItem;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/3/8.
 */
public class ConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(ConvertUtil.class);

    public static TaskRequest toTaskRequest(SubscribeReq req) {
        TaskRequest taskRequest = new TaskRequest();

        if(StringUtils.isBlank(req.getCompany()) || StringUtils.isBlank(req.getNumber())) {
            return null;
        }

        taskRequest.setNumber(req.getNumber());
        taskRequest.setCompany(req.getCompany());

        if(StringUtils.isNotBlank(req.getFrom())) {
            taskRequest.setFrom(req.getFrom());
        }

        if(StringUtils.isNotBlank(req.getTo())) {
            taskRequest.setTo(req.getTo());
        }

        taskRequest.setKey(Constant.getKuaiDi100Key());
        taskRequest.getParameters().put("callbackurl", Constant.getCallbackUrl());
        return taskRequest;
    }

    public static TbExpressTrace toTbExpressTrace(SubscribeRes res) {
        if(StringUtils.isBlank(res.getCom()) || StringUtils.isBlank(res.getNu())){
            return null;
        }

        TbExpressTrace tbExpressTrace = new TbExpressTrace();
        tbExpressTrace.setCom(res.getCom());
        tbExpressTrace.setIscheck(NumberUtils.toInt(res.getIscheck()));
        tbExpressTrace.setStatus(res.getStatus());
        tbExpressTrace.setTracejson(res.getTraceJSON());
        tbExpressTrace.setNum(res.getNu());
        return tbExpressTrace;
    }


    public static TbExpressTrace toTbExpressTrace(TaskRequest req, String orderId) {
        if(StringUtils.isBlank(req.getCompany()) || StringUtils.isBlank(req.getNumber())){
            return null;
        }

        TbExpressTrace tbExpressTrace = new TbExpressTrace();
        tbExpressTrace.setCom(req.getCompany());
        tbExpressTrace.setNum(req.getNumber());
        tbExpressTrace.setOrderId(orderId);
        tbExpressTrace.setCreateTime(DateTime.now());
        tbExpressTrace.setStatus("subscribe");
        return tbExpressTrace;
    }

    public static ExpressTrace toExpressTrace(TbExpressTrace tbExpressTrace) {
        ExpressTrace expressTrace = new ExpressTrace();

        expressTrace.setNu(tbExpressTrace.getNum());
        expressTrace.setOrderId(tbExpressTrace.getOrderId());
        expressTrace.setStatus(tbExpressTrace.getStatus());

        String traceStr = tbExpressTrace.getTracejson();
        expressTrace.setTraceItems(ExpressTraceJsonHandler.deserialize(traceStr));

        return expressTrace;
    }


    public static class ExpressTraceJsonHandler{
        private static Logger logger = LoggerFactory.getLogger(ExpressTraceJsonHandler.class);
        private static ObjectMapper objectMapper;
        static {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        public static List<TraceItem> deserialize(String s) {
            List<TraceItem> list = null;

            if(StringUtils.isNotBlank(s)) {
                try {
                    list = objectMapper.readValue(s, new TypeReference<List<TraceItem>>(){});

                    logger.info("TraceItem deserialize success, list==> :" + list);
                } catch (IOException e) {
                    logger.error("TraceItem deserialize failure.", e);
                }
            }

            return CollectionUtils.isEmpty(list) ? new ArrayList<TraceItem>() : list;
        }
    }

    public static void main(String[] str) {
        String strJson = "[{\"time\":\"2016-02-22 13:37:26\",\"ftime\":\"2016-02-22 13:37:26\",\"context\":\"快件已签收,签收人是草签，签收网点是北京市朝阳安华桥\"},{\"time\":\"2016-02-22 07:51:50\",\"ftime\":\"2016-02-22 07:51:50\",\"context\":\"北京市朝阳安华桥的牛鹏超18518350628正在派件\"},{\"time\":\"2016-02-22 07:02:10\",\"ftime\":\"2016-02-22 07:02:10\",\"context\":\"快件到达北京市朝阳安华桥，上一站是北京集散，扫描员是张彪18519292322\"},{\"time\":\"2016-02-22 01:40:35\",\"ftime\":\"2016-02-22 01:40:35\",\"context\":\"快件由北京集散发往北京市朝阳安华桥\"},{\"time\":\"2016-02-20 22:42:14\",\"ftime\":\"2016-02-20 22:42:14\",\"context\":\"快件由温州分拨中心发往北京集散\"},{\"time\":\"2016-02-20 19:56:29\",\"ftime\":\"2016-02-20 19:56:29\",\"context\":\"快件由苍南(0577-59905999)发往温州分拨中心\"},{\"time\":\"2016-02-20 19:50:09\",\"ftime\":\"2016-02-20 19:50:09\",\"context\":\"快件由苍南(0577-59905999)发往北京(010-53703166转8039或8010)\"},{\"time\":\"2016-02-20 19:50:08\",\"ftime\":\"2016-02-20 19:50:08\",\"context\":\"苍南(0577-59905999)已进行装袋扫描\"},{\"time\":\"2016-02-20 19:46:22\",\"ftime\":\"2016-02-20 19:46:22\",\"context\":\"苍南(0577-59905999)的龙港公司已收件，扫描员是龙港公司\"}]";
        List<TraceItem> list = ExpressTraceJsonHandler.deserialize(strJson);
        for(TraceItem item : list)
            System.err.println("item==> " + item);
    }
}
