package com.jfshare.buyer.dao.redis.impl;

import com.google.gson.Gson;
import com.jfshare.buyer.dao.redis.IUserJedis;
import com.jfshare.buyer.model.TbUser;
import com.jfshare.buyer.util.ConstantUtil;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.ConvertUtil;
import com.jfshare.utils.StringUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Transaction;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * 用户缓存信息，支持分布式缓存
 * @author lilx
 * @createTime 2015年11月17日 下午11:32:20
 * @version 1.0
 * @remark 活跃用户统计|在线用户|指定页面在线用户|用户ID排序|
 *
 */
@Repository
public class UserJedisImpl implements IUserJedis {
	private final static transient Logger logger = LoggerFactory.getLogger(UserJedisImpl.class);
//	@Resource
//	private JedisPool jedisUserPool;
	@Autowired
	@Qualifier("redisSentinel")
	private JedisSentinelPool jedisUserPool;	
	
	public JedisSentinelPool getJedisUserPool() {
		return jedisUserPool;
	}
	@Override
	public int addOnline(TbUser user, String sId) {
		
		int ret = 0;
        user.setPwdEnc(null);
		Jedis jedis = null;
		try {
			jedis = jedisUserPool.getResource();
			if(jedis != null) {
				String uId = ConvertUtil.getString(user.getUserId());
				int timeOut = getTimeOutByUID(jedis, uId);
				Transaction transaction = jedis.multi();
				GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
            	builder.registerTypeAdapter(DateTime.class, new JsonSerializer<DateTime>()
            	{
					@Override
					public JsonElement serialize(DateTime src, Type typeOfSrc,
							JsonSerializationContext context) {
						return new JsonPrimitive(src.getMillis());
					}
                });
            	builder.registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>()
                    	{
							@Override
							public DateTime deserialize(JsonElement json,
									Type typeOfT,
									JsonDeserializationContext context)
									throws JsonParseException {
								final long time = json.getAsLong();
						    	return new DateTime(time);
							}
                        });
            	Gson gson = builder.create();
				//transaction.setex(ConstantUtil.REDIS_SID_PREFIX + sId, timeOut, JSON.toJSONString(user));
            	//transaction.setex(ConstantUtil.REDIS_SID_PREFIX + sId, timeOut, gson.toJson(user));
				//无用token删除 transaction.setex(ConstantUtil.REDIS_UID_PREFIX + uId, timeOut, sId);
            	transaction.setex(ConstantUtil.REDIS_SID_PREFIX + uId, timeOut, gson.toJson(user));
			    transaction.exec();
			    ret = 1;
                logger.debug(MessageFormat.format("用户[{0}],已添加至在线用户列表，ret=[{1}]", user.getLoginName(), ret));
			}
		} catch (Exception e) {
			logger.error("登陆时添加用户个人信息到redis异常", e);
		} finally {
			jedisUserPool.returnResource(jedis);
		}
		
		return ret;
	}
	@Override
	public int removeOnline(String uId) {
        int ret = 0;
        Jedis jedis = null;
        try {
            jedis = jedisUserPool.getResource();
            if(jedis != null) {
                //String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId);
                Transaction transaction = jedis.multi();
                //删除用户个人信息
                transaction.del(ConstantUtil.REDIS_SID_PREFIX + uId);
                //删除登陆token
                transaction.del(ConstantUtil.REDIS_UID_PREFIX + uId);
                transaction.exec();
                ret = 1;
                logger.debug(MessageFormat.format("用户[{0}],已从在线用户列表移除，ret=[{1}]", uId, ret));
            }
        } catch (Exception e) {
            logger.error("removeOnline error！uId=" + uId, e);
        } finally {
            jedisUserPool.returnResource(jedis);
        }

        return ret;
	}
//	@Override
//	public int removeOnline(String uId, String sId) {
//        int ret = 0;
//        Jedis jedis = null;
//        try {
//            jedis = jedisUserPool.getResource();
//            if(jedis != null) {
//                String sIdVal = jedis.get(ConstantUtil.REDIS_SID_PREFIX + sId);
//                if (sIdVal == null) {
//                    logger.info(MessageFormat.format("用户[{0}],从在线列表移除时sId[{1}]检查不存在，ret=[{2}]", uId, sId, ret));
//                    return ret;
//                }
//                Transaction transaction = jedis.multi();
//                transaction.del(ConstantUtil.REDIS_SID_PREFIX + sId);
//                transaction.del(ConstantUtil.REDIS_UID_PREFIX + uId);
//                transaction.exec();
//                ret = 1;
//                logger.debug(MessageFormat.format("用户[{0}],已从在线用户列表移除，ret=[{1}]", uId, ret));
//            }
//        } catch (Exception e) {
//            logger.error("removeOnline error！uId=" + uId +",sId=" + sId, e);
//            ret = -1;
//        } finally {
//            jedisUserPool.returnResource(jedis);
//        }
//
//        return ret;
//	}
//	@Override
//	public TbUser getOnlineBySID(String sId) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisUserPool.getResource();
//            if(jedis != null) {
//                String userStr = jedis.get(ConstantUtil.REDIS_SID_PREFIX + sId);
//                if(StringUtils.isNotBlank(userStr)) {
//                    return JSON.parseObject(userStr, TbUser.class);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("get onlineBySID error！sId=" + sId, e);
//        } finally {
//            jedisUserPool.returnResource(jedis);
//        }
//
//        return null;
//	}
//	@Override
//	public TbUser getOnlineByUID(String uId,LoginLog loginLog) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisUserPool.getResource();
//            int clientType = loginLog.getClientType();
//            String clientFlag = null;
//          //保证android和ios只能登陆一个
//    		if(clientType ==2 || clientType ==1 ){
//    			 clientFlag = "Mobile";
//    		}if(clientType ==3){
//    			clientFlag = "H5";
//    		}if(clientType ==4){
//    			clientFlag = "Web";
//    		}
//            if(jedis != null) {
//                //String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId+clientFlag);
//                String userStr = jedis.get(ConstantUtil.REDIS_SID_PREFIX + uId);
//                GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
//            	builder.registerTypeAdapter(DateTime.class, new JsonSerializer<DateTime>()
//            	{
//					@Override
//					public JsonElement serialize(DateTime src, Type typeOfSrc,
//							JsonSerializationContext context) {
//						return new JsonPrimitive(src.getMillis());
//					}
//                });
//            	builder.registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>()
//                    	{
//							@Override
//							public DateTime deserialize(JsonElement json,
//									Type typeOfT,
//									JsonDeserializationContext context)
//									throws JsonParseException {
//								final long time = json.getAsLong();
//						    	return new DateTime(time);
//							}
//                        });
//            	Gson gson = builder.create();
//            	return gson.fromJson(userStr, TbUser.class); 
//                //return JSON.parseObject(userStr, TbUser.class);
//            }
//        } catch (Exception e) {
//            logger.error("get onlineByUID error！uId=" + uId, e);
//        } finally {
//            jedisUserPool.returnResource(jedis);
//        }
//
//        return null;
//	}
	@Override
	public TbUser getOnlineByUID(String uId) {
        Jedis jedis = null;
        try {
            jedis = jedisUserPool.getResource();
            if(jedis != null) {
                //String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId+clientFlag);
                String userStr = jedis.get(ConstantUtil.REDIS_SID_PREFIX + uId);
                GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
            	builder.registerTypeAdapter(DateTime.class, new JsonSerializer<DateTime>()
            	{
					@Override
					public JsonElement serialize(DateTime src, Type typeOfSrc,
							JsonSerializationContext context) {
						return new JsonPrimitive(src.getMillis());
					}
                });
            	builder.registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>()
                    	{
							@Override
							public DateTime deserialize(JsonElement json,
									Type typeOfT,
									JsonDeserializationContext context)
									throws JsonParseException {
								final long time = json.getAsLong();
						    	return new DateTime(time);
							}
                        });
            	Gson gson = builder.create();
            	return gson.fromJson(userStr, TbUser.class); 
            }
        } catch (Exception e) {
            logger.error("get onlineByUID error！uId=" + uId, e);
        } finally {
            jedisUserPool.returnResource(jedis);
        }

        return null;
	}
	@Override
	public List<String> getOnlines() {
        Jedis jedis = null;
        try {
            jedis = jedisUserPool.getResource();
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
            jedisUserPool.returnResource(jedis);
        }

        return null;
	}
	@Override
	public List<TbUser> getOnlinesByPage(int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getOnlinesCount() {
        Jedis jedis = null;
        try {
            jedis = jedisUserPool.getResource();
            if(jedis != null) {
                String keyPattern = ConstantUtil.REDIS_UID_PREFIX + "*";
                Set<String> onlines = jedis.keys(keyPattern);
                logger.debug("getOnlinesCount size=" + onlines.size());
                return onlines.size();
            }
        } catch (Exception e) {
            logger.error("get user onlinesCount error！", e);
        } finally {
            jedisUserPool.returnResource(jedis);
        }

        return 0;
	}
	@Override
	public boolean isOnline(String uId) {
		Jedis jedis = null;
		try {
			jedis = jedisUserPool.getResource();
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
			jedisUserPool.returnResource(jedis);
		}
		
		return false;
	}
	@Override
	public boolean isOnline(String uId, String sId) {
        Jedis jedis = null;
        try {
            jedis = jedisUserPool.getResource();
            if(jedis != null) {
                String loginSId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId);
                boolean online = loginSId.equals(sId);
                if(online) {
                    this.updateTimeOut(uId, this.getTimeOutByUID(jedis, uId));
                }
                return online;
            }
        } catch (Exception e) {
            logger.error("check user online state error！", e);
        } finally {
            jedisUserPool.returnResource(jedis);
        }

        return false;
	}
	@Override
	public int addTryFail(TbUser user) {
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisUserPool.getResource();
			if(jedis != null) {
				String failKey = ConstantUtil.REDIS_FAIL_PREFIX + ConvertUtil.getString(user.getLoginName()) + ":" + new DateTime().getMillis();
				//String failKey = ConstantUtil.REDIS_FAIL_PREFIX + user.getLoginName();
		        jedis.setex(failKey, ConstantUtil.REDIS_FAIL_TIMEOUT_S, "1");
		        ret = 1;
			}
		} catch (Exception e) {
			logger.error("add login fail times error！", e);
		} finally {
			jedisUserPool.returnResource(jedis);
		}
		
		return ret;
	}
	@Override
	public int getTryFailCount(String uId) {
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisUserPool.getResource();
			if(jedis != null) {
				String failKeyPattern = ConstantUtil.REDIS_FAIL_PREFIX + uId + ":*";
		        ret = jedis.keys(failKeyPattern).size();
		        logger.debug(MessageFormat.format("用户[{0}],最近登陆失败尝试次数为[{1}]", uId, ret));
			}
		} catch (Exception e) {
			logger.error("get login fail times error！", e);
		} finally {
			jedisUserPool.returnResource(jedis);
		}
		
		return ret;
	}
	@Override
	public int clearTryFail(String uId) {
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisUserPool.getResource();
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
			jedisUserPool.returnResource(jedis);
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
			jedis = jedisUserPool.getResource();
			if(jedis != null) {
				//String timeoutKey = ConstantUtil.REDIS_UID_TIMEOUT_PREFIX + uId;
				//String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId);
				Transaction transaction = jedis.multi();
				//transaction.set(timeoutKey, ConvertUtil.getString(timeout));
				//transaction.expire(ConstantUtil.REDIS_SID_PREFIX + sId, timeout);
				transaction.expire(ConstantUtil.REDIS_SID_PREFIX + uId, timeout);
				transaction.expire(ConstantUtil.REDIS_UID_PREFIX + uId, timeout);
				transaction.exec();
				ret = 1;
				logger.debug(MessageFormat.format("用户[{0}],更新登陆超时时间[{1}]成功", uId, timeout));
			}
		} catch (Exception e) {
			logger.error("update login timeout error！", e);
		} finally {
			jedisUserPool.returnResource(jedis);
		}

		return ret;
	}
	@Override
	public int setTokenTimestamp(String uId, String time,String clientFlag) {
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisUserPool.getResource();
			if(jedis != null) {
				int timeOut = getTimeOutByUID(jedis, uId);
				
				String key = ConstantUtil.REDIS_UID_TOKEN_TIMESTAMP + uId+clientFlag;
				//String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId);
				Transaction transaction = jedis.multi();
				transaction.set(key, ConvertUtil.getString(time));
				transaction.expire(key, timeOut);
				transaction.exec();
				ret = 1;
				logger.debug(MessageFormat.format("用户[{0}],更新登陆超时时间[{1}]成功", uId, time));
			}
		} catch (Exception e) {
			logger.error("set token Timestamp error！", e);
		} finally {
			jedisUserPool.returnResource(jedis);
		}
		return ret;
	}
	@Override
	public String getTokenTimestamp(String uId,String clientFlag) {
		
		String timestamp = "";
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisUserPool.getResource();
			if(jedis != null) {
				timestamp = jedis.get(ConstantUtil.REDIS_UID_TOKEN_TIMESTAMP + uId+clientFlag);
		        logger.debug(MessageFormat.format("用户[{0}],最近登陆失败尝试次数为[{1}]", uId, ret));
			}
		} catch (Exception e) {
			logger.error("get login fail times error！", e);
		} finally {
			jedisUserPool.returnResource(jedis);
		}
		
		return timestamp;
	}
	@Override
	public int addOnlineToken(int uId, String sId, int clientType) {
		String clientFlag = null;
		//保证android和ios只能登陆一个
		if(clientType ==2 || clientType ==1 ){
			 clientFlag = "Mobile";
		}if(clientType ==3){
			clientFlag = "H5";
		}if(clientType ==4){
			clientFlag = "Web";
		}
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisUserPool.getResource();
			if(jedis != null) {
				int timeOut = getTimeOutByUID(jedis, String.valueOf(uId));
				
				String key = ConstantUtil.REDIS_UID_TOKEN + uId+clientFlag;
				Transaction transaction = jedis.multi();
				transaction.set(key, sId);
				transaction.expire(key, timeOut);
				transaction.exec();
				ret = 1;
				logger.debug(MessageFormat.format("用户[{0}],更新登陆token值[{1}]成功", uId, sId));
			}
		} catch (Exception e) {
			logger.error("set token Timestamp error！", e);
		} finally {
			jedisUserPool.returnResource(jedis);
		}
		return ret;
	}
	@Override
	public String getOnlineToken(String uId, String clientFlag) {
		String token = "";
		Jedis jedis = null;
		int ret = 0;
		try {
			jedis = jedisUserPool.getResource();
			if(jedis != null) {
				token = jedis.get(ConstantUtil.REDIS_UID_TOKEN + uId+clientFlag);
		        logger.debug(MessageFormat.format("用户[{0}],最近登陆失败尝试次数为[{1}]", uId, ret));
			}
		} catch (Exception e) {
			logger.error("get login fail times error！", e);
		} finally {
			jedisUserPool.returnResource(jedis);
		}
		
		return token;
	}
	@Override
	public boolean isOnlineToken(String uId, String sId,int clientType) {
		String clientFlag = null;
		//保证android和ios只能登陆一个
		if(clientType ==2 || clientType ==1 ){
			 clientFlag = "Mobile";
		}if(clientType ==3){
			clientFlag = "H5";
		}if(clientType ==4){
			clientFlag = "Web";
		}
		 Jedis jedis = null;
	        try {
	            jedis = jedisUserPool.getResource();
	            if(jedis != null) {
	                String loginSId = jedis.get(ConstantUtil.REDIS_UID_TOKEN + uId+clientFlag);
	                if(StringUtil.isNullOrEmpty(loginSId)){
	                	logger.error("根据用户ID"+uId+"查询token失败");
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
	            jedisUserPool.returnResource(jedis);
	        }

	        return false;
	}
//	@Override
//	public int updateOnline(TbUser tbuser, LoginLog loginLog) {
//		int ret = 0;
//		Jedis jedis = null;
//		String uId = null;
//        try {
//            jedis = jedisUserPool.getResource();
//            int clientType = loginLog.getClientType();
//            String clientFlag = null;
//          //保证android和ios只能登陆一个
//    		if(clientType ==2 || clientType ==1 ){
//    			 clientFlag = "Mobile";
//    		}if(clientType ==3){
//    			clientFlag = "H5";
//    		}if(clientType ==4){
//    			clientFlag = "Web";
//    		}
//            if(jedis != null) {				
//            	uId = String.valueOf(tbuser.getUserId());
//                String sId = jedis.get(ConstantUtil.REDIS_UID_PREFIX + uId+clientFlag);
//                GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
//            	builder.registerTypeAdapter(DateTime.class, new JsonSerializer<DateTime>()
//            	{
//					@Override
//					public JsonElement serialize(DateTime src, Type typeOfSrc,
//							JsonSerializationContext context) {
//						return new JsonPrimitive(src.getMillis());
//					}
//                });
//            	builder.registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>()
//                    	{
//							@Override
//							public DateTime deserialize(JsonElement json,
//									Type typeOfT,
//									JsonDeserializationContext context)
//									throws JsonParseException {
//								final long time = json.getAsLong();
//						    	return new DateTime(time);
//							}
//                        });
//            	Gson gson = builder.create();
//            	jedis.set(ConstantUtil.REDIS_SID_PREFIX + sId, gson.toJson(tbuser));
//            	ret = 1;
//                logger.debug(MessageFormat.format("用户[{0}],已更新至在线用户列表，ret=[{1}]", tbuser.getLoginName(), ret));
//            }
//        } catch (Exception e) {
//            logger.error("get onlineByUID error！uId=" + uId, e);
//        } finally {
//            jedisUserPool.returnResource(jedis);
//        }
//
//        return 0;
//	}
	@Override
	public int updateOnline(TbUser tbuser) {
		int ret = 0;
		Jedis jedis = null;
		String uId = null;
        try {
            jedis = jedisUserPool.getResource();
            if(jedis != null) {				
            	uId = String.valueOf(tbuser.getUserId());
                GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
            	builder.registerTypeAdapter(DateTime.class, new JsonSerializer<DateTime>()
            	{
					@Override
					public JsonElement serialize(DateTime src, Type typeOfSrc,
							JsonSerializationContext context) {
						return new JsonPrimitive(src.getMillis());
					}
                });
            	builder.registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>()
                    	{
							@Override
							public DateTime deserialize(JsonElement json,
									Type typeOfT,
									JsonDeserializationContext context)
									throws JsonParseException {
								final long time = json.getAsLong();
						    	return new DateTime(time);
							}
                        });
            	Gson gson = builder.create();
            	jedis.set(ConstantUtil.REDIS_SID_PREFIX + uId, gson.toJson(tbuser));
            	ret = 1;
                logger.debug(MessageFormat.format("用户[{0}],已更新至在线用户列表，ret=[{1}]", tbuser.getLoginName(), ret));
            }
        } catch (Exception e) {
            logger.error("get onlineByUID error！uId=" + uId, e);
        } finally {
            jedisUserPool.returnResource(jedis);
        }

        return 0;
	}

}
