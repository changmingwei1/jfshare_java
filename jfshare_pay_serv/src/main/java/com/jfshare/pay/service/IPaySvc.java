package com.jfshare.pay.service;

import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.pay.model.TbPayRecordWithBLOBs;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IPaySvc {
    int insert(PayReq payReq, String payUrl, String payId);
    int update(TbPayRecordWithBLOBs payRecord);
    TbPayRecordWithBLOBs queryByPayId(String payId);
}
