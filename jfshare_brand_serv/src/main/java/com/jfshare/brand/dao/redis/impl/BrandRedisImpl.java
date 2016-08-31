package com.jfshare.brand.dao.redis.impl;

import java.util.List;
import javax.annotation.Resource;
import net.sf.json.JSONSerializer;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import com.jfshare.brand.dao.redis.IBrandRedis;
import com.jfshare.brand.model.TbBrand;
import com.jfshare.finagle.thrift.brand.QueryParam;

@Repository
public class BrandRedisImpl implements IBrandRedis {
	@Resource
	//private JedisPool jedisUserPool;
	private JedisSentinelPool jedisUserPool;
	@Override
	public void addAllBrand(List<TbBrand> brands) {
		Jedis jedis = null;
		try {
            jedis = jedisUserPool.getResource();
            if(jedis != null) {
            	for(TbBrand brand:brands){
            		jedis.hset("brand:Infos",brand.getId().toString(),JSONSerializer.toJSON(brand).toString());
            	}
            	jedis.expire("brand:Infos", 60);          	
            }
        } catch (Exception e) {
        	System.out.print("jedis error " + e);
        } finally {
            jedisUserPool.returnResource(jedis);
        }
		
	}

	@Override
	public List<TbBrand> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbBrand> queryByIds(List<Integer> validIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TbBrand getBrand(int id) {
		
        return null;
	}

	@Override
	public List<TbBrand> queryByPage(QueryParam param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addBrand(TbBrand brand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBrand(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBrand(TbBrand brand) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
