package com.jfshare.message.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jfshare.finagle.thrift.message.GetUpgradeParam;
import com.jfshare.finagle.thrift.message.GetUpgradeParamStr;
import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.message.dao.mysql.IMessageDao;
import com.jfshare.message.dao.redis.IMessageRedis;
import com.jfshare.message.dao.redis.RedisConst;
import com.jfshare.message.model.TbAppUpgrade;
import com.jfshare.message.model.TbAppUpgradeExample;
import com.jfshare.message.model.TbAppVerinfo;
import com.jfshare.message.model.TbAppVerinfoExample;
import com.jfshare.message.model.TbMessage;
import com.jfshare.message.model.TbMessageExample;
import com.jfshare.message.model.TbMessageExample.Criteria;
import com.jfshare.message.model.mapper.TbAppUpgradeMapper;
import com.jfshare.message.model.mapper.TbAppVerinfoMapper;
import com.jfshare.message.model.mapper.TbMessageMapper;
import com.jfshare.message.util.MessageUtil;
import com.jfshare.utils.JsonUtil;
import com.jfshare.utils.StringUtil;

@Repository
public class MessageDaoImpl implements IMessageDao {
	@Autowired
	private TbMessageMapper tbMessageMapper;
	@Autowired
	private TbAppUpgradeMapper tbAppUpgradeMapper;
	@Autowired
	private IMessageRedis messageRedisImpl;
	@Autowired
	private TbAppVerinfoMapper tbAppVerinfoMapper;


	@Override
	public TbMessage getMessageById(int id) {
		TbMessage tbMessage = new TbMessage();
		TbMessageExample example = new TbMessageExample();
		TbMessageExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbMessage> tbMessages = tbMessageMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(tbMessages)) {
			tbMessage = tbMessages.get(0);
		}
		return tbMessage;
	}

	@Override
	public List<TbMessage> getMessageByTitle(String title) {
		return null;
	}

	@Override
	public List<TbMessage> getMessageByStatus(int status) {
		return null;
	}

	@Override
	public List<TbMessage> getAllMessage() {
		TbMessageExample example = new TbMessageExample();
		example.setOrderByClause("create_time desc");
		return tbMessageMapper.selectByExample(example);
	}

	@Override
	public int updateMessage(TbMessage tbMessage) {
		int count = tbMessageMapper.updateByPrimaryKeySelective(tbMessage);
		TbMessage newTbMsg = tbMessageMapper.selectByPrimaryKey(tbMessage.getId());
		Map<String, String> map = MessageUtil.tbMessageToMap(newTbMsg);
		String value = JsonUtil.mapToJson(map);
		messageRedisImpl.setMap(RedisConst._SYSTEM_MESSAGE, String.valueOf(tbMessage.getId()), value);
		
		return count;
	}

	@Override
	public int deleteMessage(SystemMessage message) {
		messageRedisImpl.deleteKeyField(RedisConst._SYSTEM_MESSAGE, String.valueOf(message.getId()));
		return tbMessageMapper.deleteByPrimaryKey(message.getId());
	}

	@Override
	public int addMessage(TbMessage message) {
		int result = tbMessageMapper.insertSelective(message);
		// 添加redis
		TbMessageExample example = new TbMessageExample();
		Criteria criteria = example.createCriteria();
		criteria.andCreateTimeGreaterThan(DateTime.now().minusMinutes(1));
		List<TbMessage> tbMessages = tbMessageMapper.selectByExample(example);
		int id = 0;
		if(tbMessages != null && tbMessages.size() > 0){
			for (TbMessage tbMessage : tbMessages) {
				if(tbMessage.getId() > id && tbMessage.getTitle().equals(message.getTitle())){
					id = tbMessage.getId();
				}
			}
			message.setId(id);
			message.setPushFlag(0);
			Map<String, String> map = MessageUtil.tbMessageToMap(message);
			String msgJson = JsonUtil.mapToJson(map);
			// 添加redis
			messageRedisImpl.setMap(RedisConst._SYSTEM_MESSAGE, String.valueOf(message.getId()), msgJson);
		}
		return result;
	}

	@Override
	public List<TbMessage> getMessage(SystemMessage message) {
		List<TbMessage> tbMessages = new ArrayList<TbMessage>();

		TbMessageExample example = new TbMessageExample();
		TbMessageExample.Criteria criteria = example.createCriteria();
		if (message.getId() > 0)
			criteria.andIdEqualTo(message.getId());
		if (!StringUtil.isNullOrEmpty(message.getTitle())) {
			if (!message.getTitle().contains("%")) {
				criteria.andTitleLike("%" + message.getTitle() + "%");
			} else {
				criteria.andTitleLike(message.getTitle());
			}
		}
		switch (message.getStatus()) {
		case 1: // 未开始
			criteria.andBeginDateGreaterThan(DateTime.now());
			break;
		case 2: // 进行中
			criteria.andBeginDateLessThanOrEqualTo(DateTime.now());
			criteria.andEndDateGreaterThanOrEqualTo(DateTime.now());
			break;
		case 3: // 已结束
			criteria.andEndDateGreaterThan(DateTime.now());
			break;
		}
		if(message.getPushTarget() > 0){
			if(message.getPushTarget()==1){
				List<Integer> list=new ArrayList<Integer>();
				list.add(1);
				list.add(0);
				criteria.andPushtargetIn(list);
			}else{
				criteria.andPushtargetEqualTo(message.getPushTarget());	
			}
		}
		example.setOrderByClause("id desc");
		tbMessages = tbMessageMapper.selectByExample(example);
		return tbMessages;
	}

	@Override
	public List<TbAppUpgrade> getAppUpgrade(GetUpgradeParam param) {
		TbAppUpgradeExample example = new TbAppUpgradeExample();
		TbAppUpgradeExample.Criteria criteria = example.createCriteria();
		if(param.getAppType() > 0)
			criteria.andAppTypeEqualTo(param.getAppType());
		if(param.getVersion() > 0){
			criteria.andMaxVersionGreaterThanOrEqualTo(param.getVersion());
			criteria.andMinVersionLessThanOrEqualTo(param.getVersion());
		}
//			criteria.andVersionEqualTo(param.getVersion());
			
		example.setOrderByClause("id desc");
		return tbAppUpgradeMapper.selectByExample(example);
	}

	@Override
	public int addAppUpgrade(TbAppUpgrade upgrade) {
		return tbAppUpgradeMapper.insertSelective(upgrade);
	}

	@Override
	public int updateAppUpgrade(TbAppUpgrade upgrade) {
		TbAppUpgradeExample example = new TbAppUpgradeExample();
		TbAppUpgradeExample.Criteria criteria = example.createCriteria();
		if(upgrade.getAppType() > 0)
			criteria.andAppTypeEqualTo(upgrade.getAppType());
		if(upgrade.getVersion() > 0)
			criteria.andVersionEqualTo(upgrade.getVersion());
		return tbAppUpgradeMapper.updateByExampleSelective(upgrade, example);
	}

	@Override
	public List<TbAppVerinfo> getAppVerinfo(GetUpgradeParamStr param) {
		TbAppVerinfoExample example = new TbAppVerinfoExample();
		TbAppVerinfoExample.Criteria criteria = example.createCriteria();
		if(param.getAppType() > 0)
			criteria.andAppTypeEqualTo(param.getAppType());
//		if(param.getVersion() > 0){
//			criteria.andMaxVersionGreaterThanOrEqualTo(param.getVersion());
//			criteria.andMinVersionLessThanOrEqualTo(param.getVersion());
//		}
//			criteria.andVersionEqualTo(param.getVersion());
			
		example.setOrderByClause("id desc");
		return tbAppVerinfoMapper.selectByExample(example);
	}
	
}
