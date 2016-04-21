package com.jfshare.express.db.redis.impl;

import com.alibaba.fastjson.JSON;
import com.jfshare.express.Util.CommUtil;
import com.jfshare.express.db.redis.IExpressJedis;
import com.jfshare.express.model.pojo.TbExpress;
import com.jfshare.finagle.thrift.express.ExpressInfo;
import com.jfshare.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.*;

@Repository
public class ExpressJedisImpl implements IExpressJedis {
    private Logger logger = LoggerFactory.getLogger(ExpressJedisImpl.class);
    private final String expressPrefix = "express:prefix:";
    private final String expressInfo = "express:info";
    private final String expressSimpleInfo = "express:simpleinfo";
    
    @Resource
    private JedisPool jedisPool;
	/**
	 * 存储结构用 sortedSet  zsetName=prefix_xx key=expressId,score=取到的物流序号
	 *1. 每个物流找到：名称、Id，        索引列为：全名、全拼、缩写 （前缀为 express_prefix:）
	 * 其中索引列为redis的key, Id为member，score为取出数据的排序号
	 * 2. Id作为索引id, 索引列作为Item, 名称作为sortItem
	 * Id 为 key, 名称为score
	 * 3. 查找使用search
	 * 
	 * 注： 输入与补全相对应时，如 输入red 补全为redis,则 zset key=autocomplete的统一名称，好管理， member为 prefix、prefix*(表全内容)，zrank、zrange查找后值取带*的极为补全的结果集
	 */
	@Override
	public long setExpressPrefix(List<ExpressInfo> expressInfoList) {
		Jedis jedis = null;
		long retCount = 0;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
	        	//事务
		        Transaction transaction = jedis.multi();
				for (int i = 0; i < expressInfoList.size(); i ++) {
					Set<String> curExpressPrefixs = new TreeSet<String>(CommUtil.getAllPrefixByName(expressInfoList.get(i).getName()));
					for (Iterator<String> it = curExpressPrefixs.iterator(); it.hasNext();) {
				       transaction.zadd(expressPrefix + it.next(), i, expressInfoList.get(i).getId() + "");
				       retCount ++;
				   }
				}
				transaction.exec();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("初始化物流搜索失败");
		} finally {
		    jedisPool.returnResource(jedis);
		}
		logger.info("物流数量=" + expressInfoList.size() + ", 增加物流搜索条数=" + retCount);
		
		return retCount;
	}
	
	@Override
	public boolean removeExpressPrefix(ExpressInfo expressInfo) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
	        	//事务
		        Transaction transaction = jedis.multi();
		        Set<String> curExpressPrefixs = new TreeSet<String>(CommUtil.getAllPrefixByName(expressInfo.getName()));
				for (Iterator<String> it = curExpressPrefixs.iterator(); it.hasNext();) {
			       transaction.zrem(expressPrefix + it.next(),  expressInfo.getId() + "");
			   }
		        transaction.exec();
			}
			logger.info("删除物流" + expressInfo.getId()+":"+expressInfo.getName() + "索引成功");
			
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("删除物流" + expressInfo.getId()+":"+expressInfo.getName() + "索引错误");
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return false;
	}

	/*---------以下为物流缓存相关---------*/
	
	
	@Override
	public boolean existsExpressInfo() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			    long klen = jedis.hlen(expressInfo);
			    return klen > 0 ? true : false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("查找redis中物流是否存在出错！");
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return false;
	}

	@Override
	public List<ExpressInfo> query() {
		List<ExpressInfo> infos = new ArrayList<ExpressInfo>();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			    List<String> infoJsons = jedis.hvals(expressInfo);
			    for (String json : infoJsons) {
			    	//do faster change to fastjsonEx
//			    	TbExpress tbExpress = JsonMapper.toObject(json, TbExpress.class);
			    	TbExpress tbExpress = JSON.parseObject(json, TbExpress.class);
			    	infos.add(CommUtil.tbExpress2ExpressInfo(tbExpress));
				}
			    logger.info("查询redis中物流信息成功！数量："+infos.size());
			    return infos;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("查找redis中物流信息出错！");
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return infos;
	}

	@Override
	public long setExpressInfo(List<ExpressInfo> expressInfoList) {
		long ret = 0;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			   for (ExpressInfo info : expressInfoList) {
			       String json = JsonMapper.toJsonNotNull(CommUtil.expressInfo2tbExpress(info));
			       jedis.hset(expressInfo, info.getId() + "", json);
			   }
			   logger.info("插入redis中物流信息成功！数量："+expressInfoList.size());
			   return expressInfoList.size();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("插入redis中物流出错！");
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return ret;
	}

	@Override
	public ExpressInfo queryById(int id) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			    String json = jedis.hget(expressInfo, id+"");
			    if (json != null) {
			    	TbExpress tbExpress = JsonMapper.toObject(json, TbExpress.class);
			    	logger.info("查询redis中id为"+id+"的物流信息成功！");
			    	return CommUtil.tbExpress2ExpressInfo(tbExpress);
			    }
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("查找redis中物流id为"+id+"出错！");
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return null;
	}

	@Override
	public void removeExpress(ExpressInfo info) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			    jedis.hdel(expressInfo, info.getId()+"");
			    logger.info("删除redis中物流id为"+info.getId()+"成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("删除redis中物流id为"+info.getId()+"出错！");
		} finally {
		    jedisPool.returnResource(jedis);
		}
	}

	@Override
	public boolean removeAllExpress() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			    jedis.del(expressInfo);
			    logger.info("删除redis中所有物流成功！");
			    return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("删除redis中所有物流出错！");
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return false;
	}

	@Override
	public List<ExpressInfo> queryByIds(List<String> validIdsList) {
		List<ExpressInfo> infos = new ArrayList<ExpressInfo>();
		Jedis jedis = null;
		try {
			if (validIdsList.size() == 0) return infos;
			jedis = jedisPool.getResource();
			if(jedis != null) {
			    List<String> infoJsons = jedis.hmget(expressInfo, (String[]) validIdsList.toArray(new String[validIdsList.size()]));
			    for (String json : infoJsons) {
			    	if (json == null) continue;
			    	TbExpress tbExpress = JsonMapper.toObject(json, TbExpress.class);
			    	infos.add(CommUtil.tbExpress2ExpressInfo(tbExpress));
				}
			    logger.info("查询redis中部分物流成功！数量："+infos.size());
			    return infos;
			}
		} catch (Exception e) {
			logger.error("查找redis中部分物流信息出错！", e);
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return infos;
	}

	@Override
	public boolean existsExpressSimpleInfo() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			   return jedis.exists(expressSimpleInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("查找redis中物流简化信息是否存在出错！");
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return false;
	}

	@Override
	public void setExpressSimpleInfo(List<ExpressInfo> expressInfoList) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Transaction transaction = jedis.multi();
			transaction.del(expressSimpleInfo);
			for (ExpressInfo info : expressInfoList) {
				String json = JsonMapper.toJsonNotNull(CommUtil.expressInfo2tbExpress(info));
				transaction.rpush(expressSimpleInfo, json);
			}
			transaction.exec();
			logger.info(">>>setExpressSimpleInfo ---- 插入redis中物流信息成功！数量："+expressInfoList.size());
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("<<<<<<setExpressSimpleInfo ---- 插入redis中物流出错！----", e);
		} finally {
		    jedisPool.returnResource(jedis);
		}
	}

	@Override
	public boolean removeAllExpressSimple() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis != null) {
			    jedis.del(expressSimpleInfo);
			    logger.info(">>>removeAllExpressSimple ---- 删除redis中所有物流精简信息成功！");
			    return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("<<<<<<removeAllExpressSimple ---- 删除redis中所有精简物流信息出错！", e);
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return false;
	}

	@Override
	public List<ExpressInfo> queryExpressSimpleInfo() {
		List<ExpressInfo> infos = new ArrayList<ExpressInfo>();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		    List<String> infoJsons = jedis.lrange(expressSimpleInfo, 0, -1);
		    for (String json : infoJsons) {
		    	TbExpress tbExpress = JsonMapper.toObject(json, TbExpress.class);
		    	infos.add(CommUtil.tbExpress2ExpressInfo(tbExpress));
			}
		    logger.info(">>>queryExpressSimpleInfo ---- 查询redis中物流精简信息成功！数量："+infos.size());
		    return infos;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("<<<<<<queryExpressSimpleInfo ---- 查找redis中物流信息出错！", e);
		} finally {
		    jedisPool.returnResource(jedis);
		}
		
		return infos;
	}

}
