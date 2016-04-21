package com.jfshare.address.impl;

import com.jfshare.address.model.mapper.TbReceiverAddressMapper;
import com.jfshare.address.model.TbReceiverAddress;
import com.jfshare.address.model.TbReceiverAddressExample;
import com.jfshare.finagle.thrift.address.AddressInfo;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Repository
public class AddressImpl extends SqlSessionDaoSupport {
    private static int count = 0;
    private static String curTime = "00000000";

    @Autowired
    private TbReceiverAddressMapper tbReceiverAddressMapper;
    @Resource(name="sqlSessionFactoryRead")
    private SqlSessionFactory sqlSessionFactoryRead;
    
    
    public int add(AddressInfo addressInfo) {

        int userId = addressInfo.getUserId();
        TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
        TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        int count = this.tbReceiverAddressMapper.countByExample(tbReceiverAddressExample);
//        int id = getId();
        TbReceiverAddress tbReceiverAddress = new TbReceiverAddress();
//        tbRecviverAddress.setId(id);
        tbReceiverAddress.setUserId(addressInfo.getUserId());
        tbReceiverAddress.setReceiverName(addressInfo.getReceiverName());
        tbReceiverAddress.setMobile(addressInfo.getMobile());
        tbReceiverAddress.setTelCode(addressInfo.getTelCode());
        tbReceiverAddress.setTel(addressInfo.getTel());
        tbReceiverAddress.setTelExtNumber(addressInfo.getTelExtNumber());
        tbReceiverAddress.setProvinceId(addressInfo.getProvinceId());
        tbReceiverAddress.setProvinceName(addressInfo.getProvinceName());
        tbReceiverAddress.setCityId(addressInfo.getCityId());
        tbReceiverAddress.setCityName(addressInfo.getCityName());
        tbReceiverAddress.setCountyId(addressInfo.getCountyId());
        tbReceiverAddress.setCountyName(addressInfo.getCountyName());
        tbReceiverAddress.setAddress(addressInfo.getAddress());
        tbReceiverAddress.setPostCode(addressInfo.getPostCode());
        if (count > 0) {
            if (addressInfo.getIsDefault() == 1) {
                tbReceiverAddress.setIsDefault(1);
            } else {
                tbReceiverAddress.setIsDefault(0);
            }
        } else {
            tbReceiverAddress.setIsDefault(1);
        }
        tbReceiverAddress.setCreateTime(new DateTime());
        tbReceiverAddress.setCreateUserId(addressInfo.getUserId());
        tbReceiverAddress.setLastUpdateTime(new DateTime());
        tbReceiverAddress.setLastUpdateUserId(addressInfo.getUserId());
        tbReceiverAddress.setEmail(addressInfo.getEmail());
//        tbReceiverAddressMapper.insertSelective(tbReceiverAddress);
        int cnt = this.getSqlSession().insert("insertAddressId", tbReceiverAddress);
        int id = 0;
        if (cnt == 1) {
            id = tbReceiverAddress.getId();
            if (addressInfo.getIsDefault() == 1) {
                setDefaultAddress(addressInfo.getUserId(), id);
            }
            return id;
        } else {
            return id;
        }
    }

    public boolean update(AddressInfo addressInfo) {

        int id = addressInfo.getId();
        TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
        TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andUserIdEqualTo(addressInfo.getUserId());
        int count = this.tbReceiverAddressMapper.countByExample(tbReceiverAddressExample);
        if (count == 0) {
            return false;
        }
        TbReceiverAddress tbReceiverAddress = new TbReceiverAddress();
        tbReceiverAddress.setId(id);
        tbReceiverAddress.setUserId(addressInfo.getUserId());
        tbReceiverAddress.setReceiverName(addressInfo.getReceiverName());
        tbReceiverAddress.setMobile(addressInfo.getMobile());
        tbReceiverAddress.setTelCode(addressInfo.getTelCode());
        tbReceiverAddress.setTel(addressInfo.getTel());
        tbReceiverAddress.setTelExtNumber(addressInfo.getTelExtNumber());
        tbReceiverAddress.setProvinceId(addressInfo.getProvinceId());
        tbReceiverAddress.setProvinceName(addressInfo.getProvinceName());
        tbReceiverAddress.setCityId(addressInfo.getCityId());
        tbReceiverAddress.setCityName(addressInfo.getCityName());
        tbReceiverAddress.setCountyId(addressInfo.getCountyId());
        tbReceiverAddress.setCountyName(addressInfo.getCountyName());
        tbReceiverAddress.setAddress(addressInfo.getAddress());
        tbReceiverAddress.setPostCode(addressInfo.getPostCode());
        tbReceiverAddress.setEmail(addressInfo.getEmail());
        if (addressInfo.getIsDefault() == 0) {
            tbReceiverAddress.setIsDefault(0);
        } else {
            setDefaultAddress(addressInfo.getUserId(), id);
            tbReceiverAddress.setIsDefault(1);
        }
        tbReceiverAddress.setLastUpdateTime(new DateTime());
        tbReceiverAddress.setLastUpdateUserId(addressInfo.getUserId());
        int ret = tbReceiverAddressMapper.updateByPrimaryKeySelective(tbReceiverAddress);

//		TbReceiverAddressExample example = new TbReceiverAddressExample();
//		TbReceiverAddressExample.Criteria c = example.createCriteria();
//		c.andIdEqualTo(tbReceiverAddress.getId());
//		c.andUserIdEqualTo(tbReceiverAddress.getUserId());
//		int ret1 = tbReceiverAddressMapper.u
//		int ret = tbReceiverAddressMapper.updateByExample(tbReceiverAddress, example);
		if(ret>0){
			return true;
		}else{
			return false;
		}
    }

    public boolean delete(int userId, int id) {

        TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
        TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andUserIdEqualTo(userId);
        int count = this.tbReceiverAddressMapper.countByExample(tbReceiverAddressExample);
        if (count == 0) {
            return false;
        }
        tbReceiverAddressMapper.deleteByPrimaryKey(id);
        return true;
    }

    public List<AddressInfo> queryAddress(int userId) {
        SqlSession sqlsession = this.sqlSessionFactoryRead.openSession();
         
        try{
            List<AddressInfo> addressInfoList = new ArrayList<AddressInfo>();
            TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
            TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
            criteria.andUserIdEqualTo(userId);
            tbReceiverAddressExample.setOrderByClause("is_default DESC,last_update_time DESC");
          
            
            List<TbReceiverAddress> tbReceiverAddressList = new ArrayList<TbReceiverAddress> ();
            try{
                tbReceiverAddressList = sqlsession.selectList("selectByExample", tbReceiverAddressExample);
            }catch(Exception e){
                logger.error(e);
                tbReceiverAddressList = tbReceiverAddressMapper.selectByExample(tbReceiverAddressExample);
            }
            for (TbReceiverAddress tbReceiverAddress : tbReceiverAddressList) {
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setId(tbReceiverAddress.getId());
                addressInfo.setUserId(tbReceiverAddress.getUserId());
                addressInfo.setReceiverName(tbReceiverAddress.getReceiverName());
                addressInfo.setMobile(tbReceiverAddress.getMobile());
                addressInfo.setTelCode(tbReceiverAddress.getTelCode());
                addressInfo.setTel(tbReceiverAddress.getTel());
                addressInfo.setTelExtNumber(tbReceiverAddress.getTelExtNumber());
                if (tbReceiverAddress.getProvinceId() == null) {
                    addressInfo.setProvinceId(0);
                } else {
                    addressInfo.setProvinceId(tbReceiverAddress.getProvinceId());
                }
                addressInfo.setProvinceName(tbReceiverAddress.getProvinceName());
                if (tbReceiverAddress.getCityId() == null) {
                    addressInfo.setCityId(0);
                } else {
                    addressInfo.setCityId(tbReceiverAddress.getCityId());
                }
                addressInfo.setCityName(tbReceiverAddress.getCityName());
                if (tbReceiverAddress.getCountyId() == null) {
                    addressInfo.setCountyId(0);
                } else {
                    addressInfo.setCountyId(tbReceiverAddress.getCountyId());
                }
                addressInfo.setCountyName(tbReceiverAddress.getCountyName());
                addressInfo.setAddress(tbReceiverAddress.getAddress());
                addressInfo.setPostCode(tbReceiverAddress.getPostCode());
                addressInfo.setIsDefault(tbReceiverAddress.getIsDefault());
                addressInfo.setEmail(tbReceiverAddress.getEmail());
                addressInfoList.add(addressInfo);
            }
            return addressInfoList;
        }finally{
            sqlsession.close();
        }
        
       
    }

    public boolean setDefaultAddress(int userId, int id) {
        SqlSession sqlsession = this.sqlSessionFactoryRead.openSession();
        
        try{
//            TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
//            TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
//            criteria.andIdEqualTo(id);
//             Integer count = null;
//            try{
//                count = (Integer)sqlsession.selectOne( "countByExample", tbReceiverAddressExample);
//            }catch(Exception e){
//                logger.error(e);
//                count = this.tbReceiverAddressMapper.countByExample(tbReceiverAddressExample);
//            }
//          
//            if (count ==null || count == 0) {
//                return false;
//            }

            TbReceiverAddressExample tbReceiverAddressExample1 = new TbReceiverAddressExample();
            TbReceiverAddressExample.Criteria criteria1 = tbReceiverAddressExample1.createCriteria();
            criteria1.andUserIdEqualTo(userId);
//             List<TbReceiverAddress> tbReceiverAddressList1 = tbReceiverAddressMapper.selectByExample(tbReceiverAddressExample1);
           
            List<TbReceiverAddress> tbReceiverAddressList = sqlsession.selectList("selectByExample", tbReceiverAddressExample1);
            
            for (TbReceiverAddress tbReceiverAddress : tbReceiverAddressList) {
                int receiverId = tbReceiverAddress.getId();
                int isDefault = tbReceiverAddress.getIsDefault()==null? 0 : tbReceiverAddress.getIsDefault();
                if (receiverId == id && isDefault == 0) {
                    tbReceiverAddress.setIsDefault(1);
                }
                if (receiverId != id && isDefault == 1) {
                    tbReceiverAddress.setIsDefault(0);
                }
                tbReceiverAddressMapper.updateByPrimaryKeySelective(tbReceiverAddress);
            }
        }finally{
            sqlsession.close();
        }
      
        return true;
    }

    public AddressInfo queryAddressById(int id, int userId) {
        SqlSession sqlsession = this.sqlSessionFactoryRead.openSession();
        
        try{
            AddressInfo addressInfo = new AddressInfo();
            TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
            TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
            criteria.andIdEqualTo(id);
            criteria.andUserIdEqualTo(userId);
           
            List<TbReceiverAddress> tbReceiverAddressList =  new ArrayList<TbReceiverAddress> ();
            
            try{
                tbReceiverAddressList = sqlsession.selectList("selectByExample", tbReceiverAddressExample);
            }catch(Exception e){
                logger.error(e);
                tbReceiverAddressList = tbReceiverAddressMapper.selectByExample(tbReceiverAddressExample);
                
            }
            
            if (tbReceiverAddressList.isEmpty()) {
                return addressInfo;
            }
            TbReceiverAddress tbReceiverAddress = tbReceiverAddressList.get(0);
            addressInfo.setId(tbReceiverAddress.getId());
            addressInfo.setUserId(tbReceiverAddress.getUserId());
            addressInfo.setReceiverName(tbReceiverAddress.getReceiverName());
            addressInfo.setMobile(tbReceiverAddress.getMobile());
            addressInfo.setTelCode(tbReceiverAddress.getTelCode());
            addressInfo.setTel(tbReceiverAddress.getTel());
            addressInfo.setTelExtNumber(tbReceiverAddress.getTelExtNumber());
            if (tbReceiverAddress.getProvinceId() == null) {
                addressInfo.setProvinceId(0);
            } else {
                addressInfo.setProvinceId(tbReceiverAddress.getProvinceId());
            }
            addressInfo.setProvinceName(tbReceiverAddress.getProvinceName());
            if (tbReceiverAddress.getCityId() == null) {
                addressInfo.setCityId(0);
            } else {
                addressInfo.setCityId(tbReceiverAddress.getCityId());
            }
            addressInfo.setCityName(tbReceiverAddress.getCityName());
            if (tbReceiverAddress.getCountyId() == null) {
                addressInfo.setCountyId(0);
            } else {
                addressInfo.setCountyId(tbReceiverAddress.getCountyId());
            }
            addressInfo.setCountyName(tbReceiverAddress.getCountyName());
            addressInfo.setAddress(tbReceiverAddress.getAddress());
            addressInfo.setPostCode(tbReceiverAddress.getPostCode());
            addressInfo.setIsDefault(tbReceiverAddress.getIsDefault());
            addressInfo.setEmail(tbReceiverAddress.getEmail());
            return addressInfo;
        }finally{
            sqlsession.close();
        }
        
     
    }

    public AddressInfo queryAddressInfo(int id, int userId) {
        
        SqlSession sqlsession = this.sqlSessionFactoryRead.openSession();
        try{
            AddressInfo addressInfo = new AddressInfo();
            TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
            TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
            criteria.andIdEqualTo(id);
            criteria.andUserIdEqualTo(userId);
            
            List<TbReceiverAddress> tbReceiverAddressList = new ArrayList<TbReceiverAddress> ();
            
            try{
            tbReceiverAddressList = sqlsession.selectList("selectByExample", tbReceiverAddressExample);
            }catch(Exception e){
                logger.error(e);
                tbReceiverAddressList  = tbReceiverAddressMapper.selectByExample(tbReceiverAddressExample);
            }
            
            if (tbReceiverAddressList.isEmpty()) {
                return null;
            }
            TbReceiverAddress tbReceiverAddress = tbReceiverAddressList.get(0);
            addressInfo.setId(tbReceiverAddress.getId());
            addressInfo.setUserId(tbReceiverAddress.getUserId());
            addressInfo.setReceiverName(tbReceiverAddress.getReceiverName());
            addressInfo.setMobile(tbReceiverAddress.getMobile());
            addressInfo.setTelCode(tbReceiverAddress.getTelCode());
            addressInfo.setTel(tbReceiverAddress.getTel());
            addressInfo.setTelExtNumber(tbReceiverAddress.getTelExtNumber());
            if (tbReceiverAddress.getProvinceId() == null) {
                addressInfo.setProvinceId(0);
            } else {
                addressInfo.setProvinceId(tbReceiverAddress.getProvinceId());
            }
            addressInfo.setProvinceName(tbReceiverAddress.getProvinceName());
            if (tbReceiverAddress.getCityId() == null) {
                addressInfo.setCityId(0);
            } else {
                addressInfo.setCityId(tbReceiverAddress.getCityId());
            }
            addressInfo.setCityName(tbReceiverAddress.getCityName());
            if (tbReceiverAddress.getCountyId() == null) {
                addressInfo.setCountyId(0);
            } else {
                addressInfo.setCountyId(tbReceiverAddress.getCountyId());
            }
            addressInfo.setCountyName(tbReceiverAddress.getCountyName());
            addressInfo.setAddress(tbReceiverAddress.getAddress());
            addressInfo.setPostCode(tbReceiverAddress.getPostCode());
            addressInfo.setIsDefault(tbReceiverAddress.getIsDefault());
            addressInfo.setEmail(tbReceiverAddress.getEmail());
            return addressInfo;
        }finally{
            sqlsession.close();
        }
        
       
    }

	/**
	 * 获得用户的默认收货地址
	 * @param id
	 * @param userId
	 * @return
	 * @throws TException
	 */
    public AddressInfo getDefaultAddress(int userId) {
        
        SqlSession sqlsession = this.sqlSessionFactoryRead.openSession();
        try{
            AddressInfo addressInfo = new AddressInfo();
            TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
            TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
            criteria.andIsDefaultEqualTo(1);
            criteria.andUserIdEqualTo(userId);
            
            List<TbReceiverAddress> tbReceiverAddressList = new ArrayList<TbReceiverAddress> ();
            
            try{
            	tbReceiverAddressList = sqlsession.selectList("selectByExample", tbReceiverAddressExample);
            }catch(Exception e){
                logger.error(e);
                tbReceiverAddressList  = tbReceiverAddressMapper.selectByExample(tbReceiverAddressExample);
            }
            
            if (tbReceiverAddressList.isEmpty()) {
                return null;
            }
            TbReceiverAddress tbReceiverAddress = tbReceiverAddressList.get(0);
            addressInfo.setId(tbReceiverAddress.getId());
            addressInfo.setUserId(tbReceiverAddress.getUserId());
            addressInfo.setReceiverName(tbReceiverAddress.getReceiverName());
            addressInfo.setMobile(tbReceiverAddress.getMobile());
            addressInfo.setTelCode(tbReceiverAddress.getTelCode());
            addressInfo.setTel(tbReceiverAddress.getTel());
            addressInfo.setTelExtNumber(tbReceiverAddress.getTelExtNumber());
            if (tbReceiverAddress.getProvinceId() == null) {
                addressInfo.setProvinceId(0);
            } else {
                addressInfo.setProvinceId(tbReceiverAddress.getProvinceId());
            }
            addressInfo.setProvinceName(tbReceiverAddress.getProvinceName());
            if (tbReceiverAddress.getCityId() == null) {
                addressInfo.setCityId(0);
            } else {
                addressInfo.setCityId(tbReceiverAddress.getCityId());
            }
            addressInfo.setCityName(tbReceiverAddress.getCityName());
            if (tbReceiverAddress.getCountyId() == null) {
                addressInfo.setCountyId(0);
            } else {
                addressInfo.setCountyId(tbReceiverAddress.getCountyId());
            }
            addressInfo.setCountyName(tbReceiverAddress.getCountyName());
            addressInfo.setAddress(tbReceiverAddress.getAddress());
            addressInfo.setPostCode(tbReceiverAddress.getPostCode());
            addressInfo.setIsDefault(tbReceiverAddress.getIsDefault());
            addressInfo.setEmail(tbReceiverAddress.getEmail());
            return addressInfo;
        }finally{
            sqlsession.close();
        }
    }
    public int getCount(int userId) {
        int cnt = 0;
        Object obj = this.getSqlSession().selectOne("getCount", userId);
        if (obj != null) {
            cnt = Integer.parseInt(obj.toString());
        }
        return cnt;
    }
    /**
     * 根据用户id查询收货人地址，查询主库
     * @param userId
     * @return
     */
    public List<AddressInfo> queryAddressByMasterData(int userId) {

        List<AddressInfo> addressInfoList = new ArrayList<AddressInfo>();
        try{
            TbReceiverAddressExample tbReceiverAddressExample = new TbReceiverAddressExample();
            TbReceiverAddressExample.Criteria criteria = tbReceiverAddressExample.createCriteria();
            criteria.andUserIdEqualTo(userId);
            tbReceiverAddressExample.setOrderByClause("is_default DESC,last_update_time DESC");
          
            
            List<TbReceiverAddress> tbReceiverAddressList = new ArrayList<TbReceiverAddress> ();
            try{
                tbReceiverAddressList = this.getSqlSession().selectList("selectByExample", tbReceiverAddressExample);
            }catch(Exception e){
                logger.error(e);
                tbReceiverAddressList = tbReceiverAddressMapper.selectByExample(tbReceiverAddressExample);
            }
            for (TbReceiverAddress tbReceiverAddress : tbReceiverAddressList) {
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setId(tbReceiverAddress.getId());
                addressInfo.setUserId(tbReceiverAddress.getUserId());
                addressInfo.setReceiverName(tbReceiverAddress.getReceiverName());
                addressInfo.setMobile(tbReceiverAddress.getMobile());
                addressInfo.setTelCode(tbReceiverAddress.getTelCode());
                addressInfo.setTel(tbReceiverAddress.getTel());
                addressInfo.setTelExtNumber(tbReceiverAddress.getTelExtNumber());
                if (tbReceiverAddress.getProvinceId() == null) {
                    addressInfo.setProvinceId(0);
                } else {
                    addressInfo.setProvinceId(tbReceiverAddress.getProvinceId());
                }
                addressInfo.setProvinceName(tbReceiverAddress.getProvinceName());
                if (tbReceiverAddress.getCityId() == null) {
                    addressInfo.setCityId(0);
                } else {
                    addressInfo.setCityId(tbReceiverAddress.getCityId());
                }
                addressInfo.setCityName(tbReceiverAddress.getCityName());
                if (tbReceiverAddress.getCountyId() == null) {
                    addressInfo.setCountyId(0);
                } else {
                    addressInfo.setCountyId(tbReceiverAddress.getCountyId());
                }
                addressInfo.setCountyName(tbReceiverAddress.getCountyName());
                addressInfo.setAddress(tbReceiverAddress.getAddress());
                addressInfo.setPostCode(tbReceiverAddress.getPostCode());
                addressInfo.setIsDefault(tbReceiverAddress.getIsDefault());
                addressInfo.setEmail(tbReceiverAddress.getEmail());
                addressInfoList.add(addressInfo);
            }
            return addressInfoList;
        }catch(Exception e){
        	logger.error(e.getMessage());
        }
        return addressInfoList;
        
       
    }

    /**
     * 初始化缓存时使用
     * @param start
     * @param end
     * @return
     */
    public List<AddressInfo> queryAllAddress(int start,int end) {
        SqlSession sqlsession = this.sqlSessionFactoryRead.openSession();

        try {
            List<TbReceiverAddress> tbReceiverAddressList = new ArrayList<TbReceiverAddress>();
            List<AddressInfo> addressInfoList = new ArrayList<AddressInfo>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start",start);
            map.put("end", end);
            tbReceiverAddressList = this.getSqlSession().selectList("queryAllAddress", map);

            for (TbReceiverAddress tbReceiverAddress : tbReceiverAddressList) {
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setId(tbReceiverAddress.getId());
                if (tbReceiverAddress.getUserId() != null) {
                    addressInfo.setUserId(tbReceiverAddress.getUserId());
                }
                addressInfo.setReceiverName(tbReceiverAddress.getReceiverName());
                addressInfo.setMobile(tbReceiverAddress.getMobile());
                addressInfo.setTelCode(tbReceiverAddress.getTelCode());
                addressInfo.setTel(tbReceiverAddress.getTel());
                addressInfo.setTelExtNumber(tbReceiverAddress.getTelExtNumber());
                if (tbReceiverAddress.getProvinceId() == null) {
                    addressInfo.setProvinceId(0);
                } else {
                    addressInfo.setProvinceId(tbReceiverAddress.getProvinceId());
                }
                addressInfo.setProvinceName(tbReceiverAddress.getProvinceName());
                if (tbReceiverAddress.getCityId() == null) {
                    addressInfo.setCityId(0);
                } else {
                    addressInfo.setCityId(tbReceiverAddress.getCityId());
                }
                addressInfo.setCityName(tbReceiverAddress.getCityName());
                if (tbReceiverAddress.getCountyId() == null) {
                    addressInfo.setCountyId(0);
                } else {
                    addressInfo.setCountyId(tbReceiverAddress.getCountyId());
                }
                addressInfo.setCountyName(tbReceiverAddress.getCountyName());
                addressInfo.setAddress(tbReceiverAddress.getAddress());
                addressInfo.setPostCode(tbReceiverAddress.getPostCode());
                if (tbReceiverAddress.getIsDefault() != null) {
                    addressInfo.setIsDefault(tbReceiverAddress.getIsDefault());
                }
                addressInfo.setEmail(tbReceiverAddress.getEmail());
                addressInfoList.add(addressInfo);
            }
            return addressInfoList;
        } finally {
            sqlsession.close();
        }
    }
}
