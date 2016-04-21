package com.jfshare.express.impl;

import com.jfshare.express.Util.FailCode;
import com.jfshare.express.model.mapper.TbExpressMapper;
import com.jfshare.express.model.pojo.TbExpress;
import com.jfshare.express.model.pojo.TbExpressExample;
import com.jfshare.finagle.thrift.express.ExpressInfo;
import com.jfshare.finagle.thrift.express.ExpressQueryConditions;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xujunchi
 * Date: 14-2-22
 * Time: 上午9:28
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ExpressImpl {

    @Autowired
    private SqlSessionFactory sqlSessionFactoryRead;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private Logger logger = LoggerFactory.getLogger(ExpressImpl.class);
    @Autowired
    private TbExpressMapper tbExpressMapper;

    @Transactional
    public com.jfshare.finagle.thrift.result.StringResult add(ExpressInfo expressInfo) {
        com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
        List<com.jfshare.finagle.thrift.result.FailDesc> failDescList = new ArrayList<com.jfshare.finagle.thrift.result.FailDesc>();
        com.jfshare.finagle.thrift.result.StringResult stringResult = new com.jfshare.finagle.thrift.result.StringResult();
        if (!FailCode.isEmpty(expressInfo.getName().trim())) {
            failDescList.add(FailCode.nameIsEmpty);
        }
        if (!FailCode.isEmpty(expressInfo.getQueryUrl().trim())) {
            failDescList.add(FailCode.queryUrlIsEmpty);
        }
        if (!FailCode.isEmpty(expressInfo.getNameRule().trim())) {
        	failDescList.add(FailCode.nameRuleIsEmpty);
        }
        if (!failDescList.isEmpty()) {
            result.setCode(1);
            result.setFailDescList(failDescList);
            stringResult.setResult(result);
            return stringResult;
        }
        TbExpressExample tbExpressExample = new TbExpressExample();
        TbExpressExample.Criteria criteria = tbExpressExample.createCriteria();
        criteria.andNameEqualTo(expressInfo.getName().trim());
        List<TbExpress> tbExpressList = tbExpressMapper.selectByExample(tbExpressExample);
        if (!tbExpressList.isEmpty()) {
            failDescList.add(FailCode.expressIsExist);
            result.setCode(1);
            result.setFailDescList(failDescList);
            stringResult.setResult(result);
            return stringResult;
        }

        TbExpress tbExpress = new TbExpress();
        tbExpress.setName(expressInfo.getName().trim());
        tbExpress.setQueryUrl(expressInfo.getQueryUrl());
        tbExpress.setStatus(1);
        tbExpress.setComment(expressInfo.getComment());
        tbExpress.setCreateTime(new DateTime());
        tbExpress.setCreateUserId(expressInfo.getCreateUserId());
        tbExpress.setLastUpdateTime(new DateTime());
        tbExpress.setLastUpdateUserId(expressInfo.getLastUpdateUserId());
        tbExpress.setKuaidiKey(expressInfo.getKuaidiKey());
        tbExpress.setNameRule(expressInfo.getNameRule().trim());
        tbExpress.setExpressNoRule(expressInfo.getExpressNoRule());
        tbExpress.setGrabState(expressInfo.getGrabState());
        tbExpress.setTypeState(expressInfo.getTypeState());
        tbExpress.setDnfTel(expressInfo.getDnfTel());
        tbExpress.setUserType(expressInfo.getUserType());
        tbExpress.setSerialNum(expressInfo.getSerialNum());

        tbExpressMapper.insertSelective(tbExpress);

        String id = "";
        TbExpressExample tbExpressExample1 = new TbExpressExample();
        TbExpressExample.Criteria criteria1 = tbExpressExample1.createCriteria();
        criteria1.andNameEqualTo(expressInfo.getName());
        List<TbExpress> tbExpressList1 = tbExpressMapper.selectByExample(tbExpressExample1);
        if (!tbExpressList1.isEmpty()) {
            id = tbExpressList1.get(0).getId() + "";
        }
        result.setCode(0);
        stringResult.setResult(result);
        stringResult.setValue(id);
        return stringResult;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public boolean expressNameIsExist(String name, int id) {
        TbExpressExample tbExpressExample = new TbExpressExample();
        TbExpressExample.Criteria criteria = tbExpressExample.createCriteria();
        criteria.andNameEqualTo(name.trim());
        criteria.andIdNotEqualTo(id);
        List<TbExpress> tbExpressList = tbExpressMapper.selectByExample(tbExpressExample);
        if (!tbExpressList.isEmpty()) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean update(ExpressInfo expressInfo) {
        int id = expressInfo.getId();

        TbExpress tbExpress = new TbExpress();
        tbExpress.setId(id);
        tbExpress.setName(expressInfo.getName());
        tbExpress.setQueryUrl(expressInfo.getQueryUrl());
        if (expressInfo.getStatus() > 0)
            tbExpress.setStatus(expressInfo.getStatus());
        tbExpress.setComment(expressInfo.getComment());
        tbExpress.setLastUpdateUserId(expressInfo.getLastUpdateUserId());
        tbExpress.setLastUpdateTime(new DateTime());
        tbExpress.setKuaidiKey(expressInfo.getKuaidiKey());
        tbExpress.setNameRule(expressInfo.getNameRule().trim());
        tbExpress.setExpressNoRule(expressInfo.getExpressNoRule());
        tbExpress.setGrabState(expressInfo.getGrabState());
        tbExpress.setTypeState(expressInfo.getTypeState());
        tbExpress.setDnfTel(expressInfo.getDnfTel());
        tbExpress.setUserType(expressInfo.getUserType());
        if (expressInfo.getSerialNum() > 0)
            tbExpress.setSerialNum(expressInfo.getSerialNum());

        tbExpressMapper.updateByPrimaryKeySelective(tbExpress);

        return true;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<ExpressInfo> query() {

        TbExpressExample tbExpressExample = new TbExpressExample();
        TbExpressExample.Criteria criteria = tbExpressExample.createCriteria();
        criteria.andStatusEqualTo(1);
        tbExpressExample.setOrderByClause("last_update_time desc");
        List<TbExpress> tbExpressList = this.tbExpressMapper.selectByExample(tbExpressExample);
        List<ExpressInfo> expressInfoList = new ArrayList<ExpressInfo>();
        for (TbExpress tbExpress : tbExpressList) {
            ExpressInfo expressInfo = new ExpressInfo();
            expressInfo.setId(tbExpress.getId());
            expressInfo.setName(tbExpress.getName());
            expressInfo.setQueryUrl(tbExpress.getQueryUrl());
            expressInfo.setStatus(tbExpress.getStatus());
            expressInfo.setComment(tbExpress.getComment());
            expressInfo.setCreateUserId(tbExpress.getCreateUserId());
            if (tbExpress.getCreateTime() != null) {
                expressInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbExpress.getCreateTime().toDate()));
            } else {
                expressInfo.setCreateTime("");
            }
            if (tbExpress.getLastUpdateTime() != null) {
                expressInfo.setLastUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbExpress.getLastUpdateTime().toDate()));
            } else {
                expressInfo.setLastUpdateTime("");
            }
            expressInfo.setLastUpdateUserId(tbExpress.getLastUpdateUserId());
            expressInfo.setKuaidiKey(tbExpress.getKuaidiKey());
            expressInfo.setNameRule(tbExpress.getNameRule());
            expressInfo.setExpressNoRule(tbExpress.getExpressNoRule());
            expressInfo.setGrabState(tbExpress.getGrabState());
            expressInfo.setTypeState(tbExpress.getTypeState());
            expressInfo.setDnfTel(tbExpress.getDnfTel());
            expressInfo.setUserType(tbExpress.getUserType());
            expressInfo.setSerialNum(tbExpress.getSerialNum());
            
            expressInfoList.add(expressInfo);
        }

        return expressInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ExpressInfo queryById(int id) {
        ExpressInfo expressInfo = new ExpressInfo();
        TbExpressExample tbExpressExample = new TbExpressExample();
        TbExpressExample.Criteria criteria = tbExpressExample.createCriteria();
        criteria.andIdEqualTo(id);
        //criteria.andStatusEqualTo(1);  标记删除可查询
        List<TbExpress> tbExpressList = this.tbExpressMapper.selectByExample(tbExpressExample);
        if (!tbExpressList.isEmpty()) {
            TbExpress tbExpress = tbExpressList.get(0);
            expressInfo.setId(tbExpress.getId());
            expressInfo.setName(tbExpress.getName());
            expressInfo.setQueryUrl(tbExpress.getQueryUrl());
            expressInfo.setStatus(tbExpress.getStatus());
            expressInfo.setComment(tbExpress.getComment());
            expressInfo.setCreateUserId(tbExpress.getCreateUserId());
            if (tbExpress.getCreateTime() != null) {
                expressInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbExpress.getCreateTime().toDate()));
            } else {
                expressInfo.setCreateTime("");
            }
            if (tbExpress.getLastUpdateTime() != null) {
                expressInfo.setLastUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbExpress.getLastUpdateTime().toDate()));
            } else {
                expressInfo.setLastUpdateTime("");
            }
            expressInfo.setLastUpdateUserId(tbExpress.getLastUpdateUserId());
            expressInfo.setKuaidiKey(tbExpress.getKuaidiKey());
            expressInfo.setNameRule(tbExpress.getNameRule());
            expressInfo.setExpressNoRule(tbExpress.getExpressNoRule());
            expressInfo.setGrabState(tbExpress.getGrabState());
            expressInfo.setTypeState(tbExpress.getTypeState());
            expressInfo.setDnfTel(tbExpress.getDnfTel());
            expressInfo.setUserType(tbExpress.getUserType());
            expressInfo.setSerialNum(tbExpress.getSerialNum());

        }
        return expressInfo;
    }

    @Transactional
    public boolean deleteExpress(int id) {
        TbExpress tbExpress = new TbExpress();
        tbExpress.setId(id);
        tbExpress.setStatus(2);
        this.tbExpressMapper.updateByPrimaryKeySelective(tbExpress);
        return true;
    }
    
    /**
     * 字段查重
     * 快递公司名称；快递100 key；关键字
     * @param info
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<com.jfshare.finagle.thrift.result.FailDesc> checkExpressInfo(ExpressInfo info) {
		 List<com.jfshare.finagle.thrift.result.FailDesc> failDescList = new ArrayList<com.jfshare.finagle.thrift.result.FailDesc>();
         SqlSession sqlSession = null;
		 List<ExpressInfo> infos = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            infos = sqlSession.selectList("checkExpressInfo", info);
        } finally {
            if(sqlSession != null) {
                sqlSession.close();
            }
        }
		 boolean[] hasError = new boolean[]{false, false};
		 for (ExpressInfo expressInfo : infos) {
			if (hasError[0] && hasError[1]) break;
			
			if (!hasError[0] && info.getName() != null 
					&& !expressInfo.getName().trim().isEmpty() && expressInfo.getName().trim().equals(info.getName().trim())) {
				failDescList.add(FailCode.nameIsExist);
				hasError[0] = true;
			}
			if (!hasError[1] && info.getNameRule() != null && expressInfo.getNameRule() != null 
					&& !expressInfo.getNameRule().trim().isEmpty() && expressInfo.getNameRule().trim().equals(info.getNameRule().trim())) {
				failDescList.add(FailCode.nameRuleExist);
				hasError[1] = true;
			}
		 }
		 
		 return failDescList;
	}
	
	/**
	 * 物流列表的查询
	 * @param conditions
	 * @return
	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<ExpressInfo> queryByConditions(ExpressQueryConditions conditions) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("conditions", conditions);
        SqlSession sqlSession = null;
        List<ExpressInfo> expressInfos = null;
		
		int curPage = conditions.getCurPage() <=1 ? 1 : conditions.getCurPage();
		if (conditions.getPageSize() > 0) {
			paraMap.put("start", (curPage-1) * conditions.getPageSize());
			paraMap.put("end", conditions.getPageSize());
		}

        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            expressInfos = sqlSession.selectList("selectExpressByConditions", paraMap);
        } finally {
            if(sqlSession != null) {
                sqlSession.close();
            }
        }
        return expressInfos;
	}

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	public int queryCountByConditions(ExpressQueryConditions conditions) {
        SqlSession sqlSession = null;
        int res = 0;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("conditions", conditions);
            res = (Integer)sqlSession.selectOne("countExpressByConditions", paraMap);
        } finally {
            if(sqlSession != null) {
                sqlSession.close();
            }
        }
		return res;
	}

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<ExpressInfo> queryByIds(List<Integer> delIdsList) {
        SqlSession sqlSession = null;
        List<ExpressInfo> expressInfos = null;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            expressInfos = sqlSession.selectList("queryFreight");
            if (delIdsList.size() > 0) {
                StringBuffer sBuffer = new StringBuffer();
                sBuffer.append("-1");
                for (Integer id : delIdsList) {
                    sBuffer.append("," + id);
                }
                Map<String, Object> paraMap = new HashMap<String, Object>();
                paraMap.put("ids",sBuffer.toString());
                expressInfos = sqlSession.selectList("queryByIds", paraMap);
            } else {
                expressInfos = new ArrayList<ExpressInfo>();
            }

        } finally {
            if(sqlSession != null) {
                sqlSession.close();
            }
        }
        return expressInfos;
	}

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<ExpressInfo> queryFreight() {
        SqlSession sqlSession = null;
        List<ExpressInfo> expressInfos;
        try {
            sqlSession = sqlSessionFactoryRead.openSession();
            expressInfos = sqlSession.selectList("queryFreight");
        } finally {
            if(sqlSession != null) {
                sqlSession.close();
            }
        }
        return expressInfos;
	}

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<ExpressInfo> queryExpressSimpleInfo() {
    	TbExpressExample tbExpressExample = new TbExpressExample();
        TbExpressExample.Criteria criteria = tbExpressExample.createCriteria();
        criteria.andStatusEqualTo(1);
        tbExpressExample.setOrderByClause("type_state, serial_num, name");
        List<TbExpress> tbExpressList = this.tbExpressMapper.selectByExample(tbExpressExample);
        List<ExpressInfo> expressInfoList = new ArrayList<ExpressInfo>();
        for (TbExpress tbExpress : tbExpressList) {
            ExpressInfo expressInfo = new ExpressInfo();
            expressInfo.setId(tbExpress.getId());
            expressInfo.setName(tbExpress.getName());
            expressInfo.setQueryUrl(tbExpress.getQueryUrl());
            expressInfo.setTypeState(tbExpress.getTypeState());
            expressInfo.setUserType(tbExpress.getUserType());
            expressInfoList.add(expressInfo);
        }

        return expressInfoList;
	}
}
