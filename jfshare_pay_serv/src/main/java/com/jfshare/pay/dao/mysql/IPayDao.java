package com.jfshare.pay.dao.mysql;

import com.jfshare.pay.model.TbPayRecordWithBLOBs;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IPayDao {
    int insert(TbPayRecordWithBLOBs tbPayRecord);

    int update(TbPayRecordWithBLOBs payRecord);

    TbPayRecordWithBLOBs queryByPayId(String payId);
}
