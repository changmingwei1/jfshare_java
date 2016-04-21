package com.jfshare.address.db.redis;

import com.jfshare.address.impl.AddressImpl;
import com.jfshare.address.util.AddressInfo2JsonUtil;
import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.ridge.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

/**
 * 初始化缓存数据
 */
@Repository
public class AddressRedisInit {
	private Logger logger = LoggerFactory.getLogger(AddressRedisInit.class);
	@Autowired
	@Qualifier("addressImpl")
	private AddressImpl addressImpl;

	@Autowired
	private IAddressJedis addressJedis;

	@PostConstruct
	public void init() {
		//是否启用初始化开关
		String initState = PropertiesUtil.getProperty("jfx_address_serv", "initstate", "0");
		boolean needInit = initState.trim().equals("1") ? true : false;
		if (needInit) {
			try {
				String updateLock = addressJedis.get(RedisConst._ADD_UPDATE_LOCK);
				if (!"1".equals(updateLock)) {
					// 设置更新锁为1，锁定
					addressJedis.set(RedisConst._ADD_UPDATE_LOCK, "1");
					// 设置过期时间
					addressJedis.setExpire(RedisConst._ADD_UPDATE_LOCK, RedisConst._EXPIRE_TIME);

					HashMap map = new HashMap();
					Long startTime = System.currentTimeMillis();
					int index = 0;// 显示顺序
					int limintCount = 1000;
					int start = 0;
					int end = start + limintCount;
					while (true) {
						List<AddressInfo> addressInfoList = this.addressImpl.queryAllAddress(start, end);
						if (addressInfoList == null || addressInfoList.size() <= 0) {
							break;
						}
						logger.info("------用户收件人初始化写入Redis缓存--开始------共计：" + addressInfoList.size());
						for (AddressInfo addressInfo : addressInfoList) {
							try {
								int userId = addressInfo.getUserId();
								// 如果找不到userid，说明是新的用户，index初始0
								if (!map.containsKey(userId)) {
									// 先删除原有的地址，再添加新的
									addressJedis.deleteKeys(RedisConst._RECEIVER_ADDRESS + String.valueOf(userId));
									index = 0;
									map.put(userId, userId);
								}

								addressInfo.setIndex(index++);
								String address = AddressInfo2JsonUtil.AddressInfoToJson(addressInfo);
//								addressJedis.setMap(RedisConst._RECEIVER_ADDRESS + userId, String.valueOf(addressInfo.getId()), address);  map格式存储
								addressJedis.saveSortAddress(RedisConst._RECEIVER_ADDRESS + userId, index, address);
								// 设置过期时间
								addressJedis.setExpire(RedisConst._RECEIVER_ADDRESS + userId, RedisConst._EXPIRE_TIME);
								logger.info("------用户收件人初始化写入Redis缓存--userId：" + addressInfo.userId + "------内容：" + address);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}

						//下一个分页
						start = start + limintCount;
						end = start + limintCount;
					}
					logger.info("------用户收件人初始化写入Redis缓存-----结束----------用时：" + (System.currentTimeMillis() - startTime));
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				// 设置更新锁为0，释放
				addressJedis.set(RedisConst._ADD_UPDATE_LOCK, "0");
				logger.info("初始化用户收件人信息到Redis报错，释放锁。");
			}
		}
	}

}
