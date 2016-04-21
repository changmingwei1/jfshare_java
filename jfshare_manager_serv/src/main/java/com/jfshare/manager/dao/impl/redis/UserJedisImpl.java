package com.jfshare.manager.dao.impl.redis;

import com.alibaba.fastjson.JSON;
import com.jfshare.manager.dao.IUserJedis;
import com.jfshare.manager.model.TbCommissioner;
import com.jfshare.manager.util.ConstantUtil;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 用户缓存信息，支持分布式缓存
 * @author lilx
 * @createTime 2015年11月17日 下午11:32:20
 * @version 1.0
 * @remark 活跃用户统计|在线用户|指定页面在线用户|用户ID排序|
 *
 */
@Repository(value = "userJedis")
public class UserJedisImpl implements IUserJedis {
	private final static transient Logger logger = LoggerFactory.getLogger(UserJedisImpl.class);
	@Resource
	private JedisPool jedisPool;
	
	@Override
	public int addOnline(TbCommissioner tbCommissioner, String sId) {
		int ret = 0;
        tbCommissioner.setPwdEnc(null);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
				String uId = ConvertUtil.getString(tbCommissioner.getCsId());
				int timeOut = getTimeOutByUID(jedis, uId);
				Transaction transaction = jedis.multi();
				transaction.setex(ConstantUtil.REDIS_SID_PREFIX + sId, timeOut, JSON.toJSONString(tbCommissioner));
				transaction.setex(ConstantUtil.REDIS_UID_PREFIX + uId, timeOut, sId);
			    transaction.exec();
			    ret = 1;
                logger.debug(MessageFormat.format("用户[{0}],已添加至在线用户列表，ret=[{1}]", tbCommissioner.getLoginName(), ret));
			}
		} catch (Exception e) {
			logger.error("add user online error！", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return ret;
	}
	@Override
	public int removeOnline(String uId) {
        int ret = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(jedis != null) {
                String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId);
                Transaction transaction = jedis.multi();
                transaction.del(ConstantUtil.REDIS_SID_PREFIX + sId);
                transaction.del(ConstantUtil.REDIS_UID_PREFIX + uId);
                transaction.exec();
                ret = 1;
                logger.debug(MessageFormat.format("用户[{0}],已从在线用户列表移除，ret=[{1}]", uId, ret));
            }
        } catch (Exception e) {
            logger.error("removeOnline error！uId=" + uId, e);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return ret;
	}
	@Override
	public int removeOnline(String uId, String sId) {
        int ret = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(jedis != null) {
                String sIdVal = jedis.get(ConstantUtil.REDIS_SID_PREFIX + sId);
                if (sIdVal == null) {
                    logger.info(MessageFormat.format("用户[{0}],从在线列表移除时sId[{1}]检查不存在，ret=[{2}]", uId, sId, ret));
                    return ret;
                }
                Transaction transaction = jedis.multi();
                transaction.del(ConstantUtil.REDIS_SID_PREFIX + sId);
                transaction.del(ConstantUtil.REDIS_UID_PREFIX + uId);
                transaction.exec();
                ret = 1;
                logger.debug(MessageFormat.format("用户[{0}],已从在线用户列表移除，ret=[{1}]", uId, ret));
            }
        } catch (Exception e) {
            logger.error("removeOnline error！uId=" + uId +",sId=" + sId, e);
            ret = -1;
        } finally {
            jedisPool.returnResource(jedis);
        }

        return ret;
	}
	@Override
	public TbCommissioner getOnlineBySID(String sId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(jedis != null) {
                String userStr = jedis.get(ConstantUtil.REDIS_SID_PREFIX + sId);
                return JSON.parseObject(userStr, TbCommissioner.class);
            }
        } catch (Exception e) {
            logger.error("get onlineBySID error！sId=" + sId, e);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return null;
	}
	@Override
	public TbCommissioner getOnlineByUID(String uId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(jedis != null) {
                String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId);
                String userStr = jedis.get(ConstantUtil.REDIS_SID_PREFIX + sId);
                return JSON.parseObject(userStr, TbCommissioner.class);
            }
        } catch (Exception e) {
            logger.error("get onlineByUID error！uId=" + uId, e);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return null;
	}
	@Override
	public List<String> getOnlines() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(jedis != null) {
                String keyPattern = ConstantUtil.REDIS_UID_PREFIX + "*";
                Set<String> onlines = jedis.keys(keyPattern);
                logger.debug("getOnlines size=" + onlines.size());
                List<String> uIds = new ArrayList<String>();
                for (Iterator<String> it = onlines.iterator(); it.hasNext();) {
                    String item = it.next(); //user:online:uid:123
                    String uId = item.substring(item.lastIndexOf(":") + 1);
                    uIds.add(uId);
                }
                return uIds;
            }
        } catch (Exception e) {
            logger.error("get user onlines error！", e);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return null;
	}
	@Override
	public List<TbCommissioner> getOnlinesByPage(int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getOnlinesCount() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(jedis != null) {
                String keyPattern = ConstantUtil.REDIS_UID_PREFIX + "*";
                Set<String> onlines = jedis.keys(keyPattern);
                logger.debug("getOnlinesCount size=" + onlines.size());
                return onlines.size();
            }
        } catch (Exception e) {
            logger.error("get user onlinesCount error！", e);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return 0;
	}
	@Override
	public boolean isOnline(String uId) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			    boolean online = jedis.exists(ConstantUtil.REDIS_UID_PREFIX + uId);
                if(online) {
                    this.updateTimeOut(uId, this.getTimeOutByUID(jedis, uId));
                }
                return online;
			}
		} catch (Exception e) {
			logger.error("check user online state error！", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return false;
	}
	@Override
	public boolean isOnline(String uId, String sId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(jedis != null) {
                String loginSId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId);
                if(StringUtils.isBlank(loginSId)) {
                    return false;
                }
                boolean online = loginSId.equals(sId);
                if(online) {
                    this.updateTimeOut(uId, this.getTimeOutByUID(jedis, uId));
                }
                return online;
            }
        } catch (Exception e) {
            logger.error("check user online state error！", e);
        } finally {
            jedisPool.returnResource(jedis);
        }

        return false;
	}
	@Override
	public int addTryFail(TbCommissioner tbCommissioner) {
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
				String failKey = ConstantUtil.REDIS_FAIL_PREFIX + ConvertUtil.getString(tbCommissioner.getCsId()) + ":" + new DateTime().getMillis();
		        jedis.setex(failKey, ConstantUtil.REDIS_FAIL_TIMEOUT_S, "1");
		        ret = 1;
			}
		} catch (Exception e) {
			logger.error("add login fail times error！", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return ret;
	}
	@Override
	public int getTryFailCount(String uId) {
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
				String failKeyPattern = ConstantUtil.REDIS_FAIL_PREFIX + uId + ":*";
		        ret = jedis.keys(failKeyPattern).size();
		        logger.debug(MessageFormat.format("用户[{0}],最近登陆失败尝试次数为[{1}]", uId, ret));
			}
		} catch (Exception e) {
			logger.error("get login fail times error！", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return ret;
	}
	@Override
	public int clearTryFail(String uId) {
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
				String failKeyPattern = ConstantUtil.REDIS_FAIL_PREFIX + uId + ":*";
				Set<String> keys = jedis.keys(failKeyPattern);
				ret = keys.size();
				for (String key : keys) {
					jedis.del(key);
				}
		        logger.debug(MessageFormat.format("用户[{0}],清除最近登陆失败尝试次数成功", uId));
			}
		} catch (Exception e) {
			logger.error("clear login fail times error！", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return ret;
	}
	@Override
	public int getTimeOutByUID(Jedis jedis, String uId) {
		int timeout = 0;
		if(jedis != null) {
			String timeoutStr = jedis.get(ConstantUtil.REDIS_UID_TIMEOUT_PREFIX + uId);
			if (!StringUtil.isNullOrEmpty(timeoutStr)) {
				timeout = ConvertUtil.getInt(timeoutStr);
			} else {
				timeout = ConvertUtil.getInt(PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, "def_login_timeout", "0"));
			}
		}
		
		return timeout;
	}
	@Override
	public int updateTimeOut(String uId, int timeout) {
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
				String timeoutKey = ConstantUtil.REDIS_UID_TIMEOUT_PREFIX + uId;
				String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId);
				Transaction transaction = jedis.multi();
				transaction.set(timeoutKey, ConvertUtil.getString(timeout));
				transaction.expire(ConstantUtil.REDIS_SID_PREFIX + sId, timeout);
				transaction.expire(ConstantUtil.REDIS_UID_PREFIX + uId, timeout);
				transaction.exec();
				ret = 1;
				logger.debug(MessageFormat.format("用户[{0}],更新登陆超时时间[{1}]成功", uId, timeout));
			}
		} catch (Exception e) {
			logger.error("update login timeout error！", e);
		} finally {
			jedisPool.returnResource(jedis);
		}

		return ret;
	}
}
