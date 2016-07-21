package com.jfshare.fileNameMapped.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedInfo;
import com.jfshare.fileNameMapped.dao.redis.BaseRedis;
import com.jfshare.fileNameMapped.model.TbFileNameMapped;
import com.jfshare.fileNameMapped.model.TbFileNameMappedExample;
import com.jfshare.fileNameMapped.model.mapper.TbFileNameMappedMapper;
import com.jfshare.utils.BeanUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * *************************************************************************
 * @文件名称: FileNameMappedService.java
 *
 * @包路径  : com.jfshare.fileNameMapped.service 
 *				 
 * @版权所有: (C) 2015
 *
 * @类描述:  
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2015年11月30日 - 上午12:02:27 
 *	
 **************************************************************************
 */
@Service
public class FileNameMappedService {
	
	private static final transient Logger logger = LoggerFactory.getLogger(FileNameMappedService.class);
	
	@Autowired
	private TbFileNameMappedMapper tbFileNameMappedMapper;
	
	@Autowired
	private BaseRedis baseRedis;
	
	/**
	 * 	方法描述:  添加文件映射关系
	 *   
	 *  @author  ljx 创建时间 2015年11月29日 下午11:44:35
	 */
	@Transactional
	public String saveFileNameMappedInfo(FileNameMappedInfo fileNameMappedInfo){
		String filename = fileNameMappedInfo.getFilename();
		String md5Str = DigestUtils.md5Hex(filename);

		baseRedis.putKV("FileNameMapped:"+filename, fileNameMappedInfo.getFileid());
		TbFileNameMappedExample example = new TbFileNameMappedExample();
		TbFileNameMappedExample.Criteria criteria = example.createCriteria();
		criteria.andFilenameMd5EqualTo(md5Str);
		tbFileNameMappedMapper.deleteByExample(example);

		//组织参数
		TbFileNameMapped tbFileNameMapped = new TbFileNameMapped();
		tbFileNameMapped.setFileid(fileNameMappedInfo.getFileid());
		tbFileNameMapped.setFilename(filename);
		tbFileNameMapped.setBackupState(1);
		tbFileNameMapped.setFilenameMd5(md5Str);

		//insert => DB
		int insertRet = tbFileNameMappedMapper.insertSelective(tbFileNameMapped);
		logger.info("图片映射----插入数据库结果：{}", insertRet > 0 ? "成功" : "失败");

		return filename;
	}
	
	/**
	 * 	方法描述: 查找映射关系 
	 *   
	 *  @author  ljx 创建时间 2015年11月29日 下午11:36:36
	 */
	public FileNameMappedInfo queryFileNameMapped(String fileName){
		
		FileNameMappedInfo fileNameMappedInfo = new FileNameMappedInfo();
		
		//组织数据库查询参数
		TbFileNameMappedExample example = new TbFileNameMappedExample();
		TbFileNameMappedExample.Criteria criteria =  example.createCriteria();
		criteria.andFilenameEqualTo(fileName);
		
		List<TbFileNameMapped> tbFileNameMappedList  = tbFileNameMappedMapper.selectByExample(example);
		
		//给结果 赋值
		if(tbFileNameMappedList != null && tbFileNameMappedList.size() > 0){
			 Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbFileNameMappedList.get(0));
	         BeanUtil.fillBeanData(fileNameMappedInfo, stringObjectMap);
		}
		
		return fileNameMappedInfo;
		
	}
}
