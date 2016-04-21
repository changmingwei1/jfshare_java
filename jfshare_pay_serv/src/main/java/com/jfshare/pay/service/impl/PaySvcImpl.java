package com.jfshare.pay.service.impl;

import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.pay.dao.mysql.IPayDao;
import com.jfshare.pay.model.TbPayRecordWithBLOBs;
import com.jfshare.pay.service.IPaySvc;
import com.jfshare.utils.BeanUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service
public class PaySvcImpl implements IPaySvc {
    @Autowired
    private IPayDao payDaoImpl;

    @Override
    public int insert(PayReq payReq, String payUrl, String payId) {
        TbPayRecordWithBLOBs tbPayRecord  = new TbPayRecordWithBLOBs();
        Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(payReq);
        BeanUtil.fillBeanData(tbPayRecord, stringObjectMap);
        tbPayRecord.setPayId(payId);
        tbPayRecord.setThirdReq(payUrl);
        tbPayRecord.setPayState(1);
        tbPayRecord.setCreateTime(new DateTime());
        tbPayRecord.setBizCode(1);
       return payDaoImpl.insert(tbPayRecord);
    }

    @Override
    public int update(TbPayRecordWithBLOBs payRecord) {
        return payDaoImpl.update(payRecord);
    }

    @Override
    public TbPayRecordWithBLOBs queryByPayId(String payId) {
        return payDaoImpl.queryByPayId(payId);
    }
}
