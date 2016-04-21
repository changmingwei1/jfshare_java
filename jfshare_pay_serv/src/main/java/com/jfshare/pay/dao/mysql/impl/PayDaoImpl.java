package com.jfshare.pay.dao.mysql.impl;

import com.jfshare.pay.dao.mysql.IPayDao;
import com.jfshare.pay.model.TbPayRecordExample;
import com.jfshare.pay.model.TbPayRecordWithBLOBs;
import com.jfshare.pay.model.mapper.TbPayRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
@Repository
public class PayDaoImpl implements IPayDao {
    @Autowired
    private TbPayRecordMapper tbPayRecordMapper;

    @Override
    public int insert(TbPayRecordWithBLOBs tbPayRecord) {
        return tbPayRecordMapper.insertSelective(tbPayRecord);
    }

    @Override
    public int update(TbPayRecordWithBLOBs payRecord) {
        return tbPayRecordMapper.updateByPrimaryKeySelective(payRecord);
    }

    @Override
    public TbPayRecordWithBLOBs queryByPayId(String payId) {
        TbPayRecordExample example = new TbPayRecordExample();
        TbPayRecordExample.Criteria criteria = example.createCriteria();
        criteria.andPayIdEqualTo(payId);
        List<TbPayRecordWithBLOBs> tbPayRecordWithBLOBses = tbPayRecordMapper.selectByExampleWithBLOBs(example);
        return tbPayRecordWithBLOBses.isEmpty() ? null : tbPayRecordWithBLOBses.get(0);
    }
}
