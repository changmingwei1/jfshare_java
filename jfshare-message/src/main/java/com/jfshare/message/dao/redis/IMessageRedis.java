package com.jfshare.message.dao.redis;

import java.util.List;
import java.util.Map;

import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.message.model.TbMessage;

public interface IMessageRedis {
	public boolean setExpire(String key, int seconds);
    public String set(String key, String value);
    public String get(String key);	
	public String getMap(String key, String field);
	public Long setMap(String key, String field, String value);
	public boolean isExists(String key);
	public boolean deleteKeyField(String key, String field);
	public boolean deleteKeys(String... keys);
	public boolean isHExists(String key, String field);
	public Map<String, String> getHAll(String key);
	public long deleteLikeKey(String key);
	
	
	TbMessage getMessageById(int id);
	List<TbMessage> getMessageByTitle(String title);
	List<TbMessage> getMessageByStatus(int status);
	List<TbMessage> getAllMessage();
	int updateMessage(TbMessage tbMessage);
	int deleteMessage(SystemMessage tbMessage);
	int addMessage(TbMessage tbMessage);
	List<TbMessage> getMessage(SystemMessage tbMessage);
}
