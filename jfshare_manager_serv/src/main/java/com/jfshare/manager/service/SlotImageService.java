package com.jfshare.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jfshare.finagle.thrift.manager.QuerySlotImageParam;
import com.jfshare.finagle.thrift.manager.SlotImage;
import com.jfshare.manager.common.ManagerCommons;
import com.jfshare.manager.dao.impl.redis.SlotImageJedis;
import com.jfshare.manager.model.TbSlotImage;
import com.jfshare.manager.model.TbSlotImageExample;
import com.jfshare.manager.model.mappers.TbSlotImageMapper;
import com.jfshare.manager.util.BeanConvertUtil;

/**
 * *************************************************************************
 * @文件名称: SlotImageService.java
 *
 * @包路径  : com.jfshare.manager.service 
 *				 
 * @版权所有: (C) 2015
 *
 * @类描述:  
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2015年12月2日 - 上午3:16:17 
 *	
 **************************************************************************
 */
@Service
public class SlotImageService {

	@Autowired
	private TbSlotImageMapper tbSlotImageMapper;
	
	@Autowired
	private SlotImageJedis slotImageJedis;
	
	/**
	 * 	方法描述:  保存广告图片
	 *   
	 *  @author  ljx 创建时间 2015年12月2日 上午3:15:48
	 */
	public String saveSlotImage(SlotImage slotImage){
		
		Integer type = slotImage.getType();
		String imgKey = slotImage.getImgKey();
		String jump = slotImage.getJump();
		
		//组织mysql参数
		TbSlotImage record = new TbSlotImage();
		record.setImgKey(imgKey);
		record.setJump(jump);
		record.setType(type);
		record.setWidth(slotImage.getWidth());
		record.setHeight(slotImage.getHeight());
		
		//插入，mysql
		tbSlotImageMapper.insertSelective(record);

		//删除redis数据
		slotImageJedis.del(ManagerCommons.RKP_SLOTIMAGE_TYPE+type);
		
		return "ok";
	}
	
	/**
	 * 	方法描述:  修改
	 *   
	 *  @author  ljx 创建时间 2015年12月2日 下午10:33:22
	 */
	public String updateSlotImage(SlotImage slotImage){
		
		Integer type = slotImage.getType();
		String imgKey = slotImage.getImgKey();
		String jump = slotImage.getJump();
		
		TbSlotImage record = new TbSlotImage();
		record.setId(slotImage.getId());
		record.setImgKey(imgKey);
		record.setJump(jump);
		record.setType(type);
		record.setIsDelete(slotImage.getIsDelete());
		
		//修改数据库
		tbSlotImageMapper.updateByPrimaryKeySelective(record);

		//删除redis数据
		slotImageJedis.del(ManagerCommons.RKP_SLOTIMAGE_TYPE+type);
		slotImageJedis.del(ManagerCommons.RKP_SLOTIMAGE_ID+record.getId());
		return "ok";
	}
	
	/**
	 * 	方法描述:  查询广告图片
	 *   
	 *  @author  ljx 创建时间 2015年12月2日 上午3:15:59
	 */
	public List<SlotImage> querySlotImageList(QuerySlotImageParam querySlotImageParam){
		
		//返回结果
		List<SlotImage> slotImageList = new ArrayList<SlotImage>();
		
		//数据库结果
		List<TbSlotImage> tbSlotImageList = new ArrayList<TbSlotImage>();
		
		Integer type = querySlotImageParam.getType();
		
		Map<String, String> hashRedis = slotImageJedis.querySlotImageByType(ManagerCommons.RKP_SLOTIMAGE_TYPE+type);
		
		if(hashRedis == null || hashRedis.size() == 0){
			
			TbSlotImageExample example = new TbSlotImageExample();
			TbSlotImageExample.Criteria criteria = example.createCriteria();
			criteria.andTypeEqualTo(querySlotImageParam.getType());
			criteria.andIsDeleteEqualTo(0);
			
			//从数据库查处结果
			tbSlotImageList = tbSlotImageMapper.selectByExample(example);

			
			Map<String, String> hash = new HashMap<String, String>();
			for(TbSlotImage tbSlotImage :tbSlotImageList){
				hash.put(tbSlotImage.getImgKey(), JSON.toJSONString(tbSlotImage));
			}
			//放入redis
			slotImageJedis.saveSlotImageKeyType(ManagerCommons.RKP_SLOTIMAGE_TYPE+type, hash);
			
			// bean转换
			for(TbSlotImage tbSlotImage : tbSlotImageList){
				
				slotImageList.add(BeanConvertUtil.tbSlotImage2SlotImage(tbSlotImage));
				
			}
			
		}else{
			// bean转换
			for(Map.Entry<String, String> entry : hashRedis.entrySet()){
				
				TbSlotImage tbSlotImage = JSON.parseObject(entry.getValue(), TbSlotImage.class);
				
				
				slotImageList.add(BeanConvertUtil.tbSlotImage2SlotImage(tbSlotImage));
			}
		}
		
		return slotImageList;
	}
	
	/**
	 * 	方法描述:  查找一个
	 *   
	 *  @author  ljx 创建时间 2015年12月3日 上午7:06:00
	 */
	public SlotImage querySlotImageOne(int id){
		
		//返回结果
		SlotImage slotImage = new SlotImage();
		
		//数据库结果
		TbSlotImage tbSlotImage = new TbSlotImage();
		
		
		String slotImageJsonStr = slotImageJedis.querySlotImageById(ManagerCommons.RKP_SLOTIMAGE_ID+id);
		
		if(slotImageJsonStr == null || slotImageJsonStr.length() == 0){
			
			
			//从数据库查处结果
			tbSlotImage = tbSlotImageMapper.selectByPrimaryKey(id);
			
			//放入redis
			slotImageJedis.saveSlotImageById(ManagerCommons.RKP_SLOTIMAGE_ID+id, JSON.toJSONString(tbSlotImage));
			
			// bean转换
			slotImage = BeanConvertUtil.tbSlotImage2SlotImage(tbSlotImage);
			
		}else{
			// bean转换
			tbSlotImage = JSON.parseObject(slotImageJsonStr, TbSlotImage.class);
			slotImage = BeanConvertUtil.tbSlotImage2SlotImage(tbSlotImage);
		}
		
		return slotImage;
	}
}
