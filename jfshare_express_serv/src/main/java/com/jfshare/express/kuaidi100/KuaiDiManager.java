package com.jfshare.express.kuaidi100;

import com.jfshare.express.Util.ConvertUtil;
import com.jfshare.express.Util.FailCode;
import com.jfshare.express.kuaidi100.pojo.TaskRequest;
import com.jfshare.express.kuaidi100.pojo.TaskResponse;
import com.jfshare.express.kuaidi100.utils.HttpRequest;
import com.jfshare.express.kuaidi100.utils.JacksonHelper;
import com.jfshare.express.model.mapper.TbExpressTraceMapper;
import com.jfshare.express.model.pojo.TbExpressExample;
import com.jfshare.express.model.pojo.TbExpressTrace;
import com.jfshare.express.model.pojo.TbExpressTraceExample;
import com.jfshare.finagle.thrift.express.ExpressTrace;
import com.jfshare.finagle.thrift.express.SubscribeRes;
import com.jfshare.finagle.thrift.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by bq on 16/3/7.
 */
@Service
public class KuaiDiManager {
    private Logger logger = LoggerFactory.getLogger(KuaiDiManager.class);

    @Autowired
    private TbExpressTraceMapper tbExpressTraceMapper;
    public Result subscribe(TaskRequest req, String orderId){
        Result result = new Result(0);
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("schema", "json");
        p.put("param", JacksonHelper.toJSON(req));
        try {
            String ret = HttpRequest.postData(Constant.KUAIDI100_SERVER_URL, p, "UTF-8");
            TaskResponse resp = JacksonHelper.fromJSON(ret, TaskResponse.class);

            if(resp.getResult() == true){
                System.out.println("订阅成功");
                TbExpressTrace tbExpressTrace = ConvertUtil.toTbExpressTrace(req, orderId);
                int insertRes = tbExpressTraceMapper.insertSelective(tbExpressTrace);
                logger.info("record ==> 更新物流日志完成， 影响记录条数[{}]条", insertRes);
            }else{
                logger.warn("订阅失败==> Req:{} | res:{}", req.toString(), ret);
                result.setCode(1);
                result.addToFailDescList(FailCode.subScribeFail);
            }
        } catch (Exception e) {
            result.setCode(1);
            result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
            logger.error("订阅失败==> 发生异常", e);
        }
        return  result;
    }

    public void record(SubscribeRes res) {
        TbExpressTrace tbExpressTrace = ConvertUtil.toTbExpressTrace(res);
        if(tbExpressTrace == null) {
            logger.info("record ==> 更新物流日志失败， 无效的请求参数, res:{}", res.toString());
        }
        TbExpressTraceExample tbExpressTraceExample = new TbExpressTraceExample();
        TbExpressTraceExample.Criteria criteria = tbExpressTraceExample.createCriteria();
        criteria.andComEqualTo(res.getCom());
        criteria.andNumEqualTo(res.getNu());
        int updateRes = tbExpressTraceMapper.updateByExampleSelective(tbExpressTrace, tbExpressTraceExample);
        logger.info("record ==> 更新物流日志完成， 影响记录条数[{}]条", updateRes);
    }

    public ExpressTrace queryTrace(String orderId) {
        TbExpressTrace tbExpressTrace = tbExpressTraceMapper.selectByPrimaryKey(orderId);
        return ConvertUtil.toExpressTrace(tbExpressTrace);
    }
}
