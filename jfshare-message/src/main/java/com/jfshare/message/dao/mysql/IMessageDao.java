package com.jfshare.message.dao.mysql;

import java.util.List;

import com.jfshare.finagle.thrift.message.GetUpgradeParam;
import com.jfshare.finagle.thrift.message.GetUpgradeParamStr;
import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.message.model.TbAppUpgrade;
import com.jfshare.message.model.TbAppVerinfo;
import com.jfshare.message.model.TbMessage;

public interface IMessageDao {
	TbMessage getMessageById(int id);
	List<TbMessage> getMessageByTitle(String title);
	List<TbMessage> getMessageByStatus(int status);
	List<TbMessage> getAllMessage();
	int updateMessage(TbMessage tbMessage);
	int deleteMessage(SystemMessage tbMessage);
	int addMessage(TbMessage tbMessage);
	List<TbMessage> getMessage(SystemMessage tbMessage);
	List<TbAppUpgrade> getAppUpgrade(GetUpgradeParam param);
	List<TbAppVerinfo> getAppVerinfo(GetUpgradeParamStr param);
	int addAppUpgrade(TbAppUpgrade upgrade);
	int updateAppUpgrade(TbAppUpgrade upgrade);
}
