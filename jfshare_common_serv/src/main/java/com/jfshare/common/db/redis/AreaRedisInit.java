package com.jfshare.common.db.redis;

import com.jfshare.common.impl.CommonImpl;
import com.jfshare.common.util.AreaInfo2JsonUtil;
import com.jfshare.finagle.thrift.common.AreaInfo;
import com.jfshare.ridge.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AreaRedisInit {
	private Logger logger = LoggerFactory.getLogger(AreaRedisInit.class);
	@Autowired
	@Qualifier("commonImpl")
	private CommonImpl commonImpl;
	@Autowired
	private ICommonJedis commonJedis;

	@PostConstruct
	public void init() {
		//是否启用初始化开关
		String initState = PropertiesUtil.getProperty("jfx_common_serv", "initstate", "0");
		boolean needInit = initState.trim().equals("1") ? true : false;
		if (needInit) {
			try {
				String updateLock = commonJedis.get(RedisConst._ADD_UPDATE_LOCK);
				if (!"1".equals(updateLock)) {

					// 设置更新锁为1，锁定
					commonJedis.set(RedisConst._ADD_UPDATE_LOCK, "1");
					// 设置过期时间
					commonJedis.setExpire(RedisConst._ADD_UPDATE_LOCK, RedisConst._EXPIRE_TIME);

					//事务  库存持久化到数据库， 添加redis的通知队列
					Jedis Jedis = commonJedis.getJedisPoolResource();
					Transaction transaction = Jedis.multi();

					// 加载地址信息
					this.loadAdd(transaction);
					// 加载地址信息，包括shortname和邮编
					this.loadAddMore(transaction);

					transaction.exec();

					// 设置更新锁为0，释放
					commonJedis.set(RedisConst._ADD_UPDATE_LOCK, "0");
					logger.info("初始化省市区信息到Redis完成，释放锁。");
				} else {
					logger.info("有其他服务正在更新redis的省市区数据，本次服务启动不进行更新redis！！");
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				// 设置更新锁为0，释放
				commonJedis.set(RedisConst._ADD_UPDATE_LOCK, "0");
				logger.info("初始化省市区信息到Redis报错，释放锁。");
			}
		}
	}

	// 初始化地址
	private void loadAdd(Transaction transaction) {
		List<AreaInfo> provinceList = this.commonImpl.province();
		List<AreaInfo> cityList = new ArrayList<AreaInfo>();
		List<AreaInfo> countyList = new ArrayList<AreaInfo>();
		logger.info("初始化省市区信息到Redis..................");
		// 先从Redis删除省
		commonJedis.deleteKeys(transaction,RedisConst._ADD_COUNTRY_CN);
		for (AreaInfo province : provinceList) {
			// 插入省的List
			commonJedis.rpush(transaction,RedisConst._ADD_COUNTRY_CN, province.getId() + "_" + province.getName());
			logger.info("-----------插入省数据到redis----------" + RedisConst._ADD_COUNTRY_CN + "=" + province.getId() + "_" + province.getName());

			// 先从Redis删除市
			commonJedis.deleteKeys(transaction,RedisConst._ADD_PROVINCE + province.getId());
			// 循环插入市的List
			cityList = this.commonImpl.city(province.getId());
			for (AreaInfo city : cityList) {
				commonJedis.rpush(transaction,RedisConst._ADD_PROVINCE + province.getId(), city.getId() + "_" + city.getName());
				logger.info("-----------插入市数据到redis----------" + RedisConst._ADD_PROVINCE + province.getId() + "=" + city.getId() + "_"
						+ city.getName());
				// 先从Redis删除区县
				commonJedis.deleteKeys(transaction,RedisConst._ADD_CITY + city.getId());
				// 循环插入区县的List
				countyList = this.commonImpl.county(city.getId());
				for (AreaInfo county : countyList) {
					commonJedis.rpush(transaction,RedisConst._ADD_CITY + city.getId(), county.getId() + "_" + county.getName());
					logger.info("-----------插入区县数据到redis----------" + RedisConst._ADD_CITY + city.getId() + "=" + county.getId() + "_"
							+ county.getName());
				}
			}
		}
		logger.info("初始化省市区信息到Redis完成！！");
	}

	/**
	 *  初始化地址包括shortname和邮编
	 *  
	    省市区信息redis的存储结构，存储的为json字符串，对应的内容： id，名称，短名称，邮编，拼音，首字母：

		省列表：key="addCountryMore:CN",value={ "id":, "name":,"shortName":,"postCode":,"pinyin":,"initial":,}
		
		市列表：key="addProvinceMore:省id",value={ "id":, "name":,"shortName":,"postCode":,"pinyin":,"initial":,}
		
		区县列表：key="addCityMore:市id",value{ "id":, "name":,"shortName":,"postCode":,"pinyin":,"initial":,}
		
		Value存储的为List结构。
	 */
	private void loadAddMore(Transaction transaction) {

		List<AreaInfo> provinceList = this.commonImpl.province();
		List<AreaInfo> cityList = new ArrayList<AreaInfo>();
		List<AreaInfo> countyList = new ArrayList<AreaInfo>();
		logger.info("初始化省市区信息到Redis（包含短地址）..................");
		// 先从Redis删除省
		commonJedis.deleteKeys(transaction,RedisConst._ADD_COUNTRY_CN_MORE);
		for (AreaInfo province : provinceList) {
			// 插入省的List包含短地址
			commonJedis.rpush(transaction,RedisConst._ADD_COUNTRY_CN_MORE, AreaInfo2JsonUtil.areaInfoToJson(province));
			logger.info("-----------插入省数据到redis，包含短地址----------" + RedisConst._ADD_COUNTRY_CN_MORE + "=" +AreaInfo2JsonUtil.areaInfoToJson(province));

			// 先从Redis删除市
			commonJedis.deleteKeys(transaction,RedisConst._ADD_PROVINCE_MORE + province.getId());
			// 循环插入市的List
			cityList = this.commonImpl.city(province.getId());
			for (AreaInfo city : cityList) {
				// 包含短地址
				commonJedis.rpush(transaction,RedisConst._ADD_PROVINCE_MORE + province.getId(),AreaInfo2JsonUtil.areaInfoToJson(city));
				logger.info("-----------插入市数据到redis，包含短地址----------" + RedisConst._ADD_PROVINCE_MORE + province.getId() + "=" + AreaInfo2JsonUtil.areaInfoToJson(city));
				// 先从Redis删除区县
				commonJedis.deleteKeys(transaction,RedisConst._ADD_CITY_MORE + city.getId());
				// 循环插入区县的List
				countyList = this.commonImpl.county(city.getId());
				for (AreaInfo county : countyList) {
					// 包含短地址
					commonJedis.rpush(transaction,RedisConst._ADD_CITY_MORE + city.getId(), AreaInfo2JsonUtil.areaInfoToJson(county));
					//根据区县ID，保存省市区ID
					commonJedis.set(transaction,RedisConst._ADD_COUNTYID+county.getCountyId(), county.getProvinceId()+ "_" +county.getCityId()+ "_" +county.getCountyId() );
					
					logger.info("-----------插入区县数据到redis,包含短地址----------" + RedisConst._ADD_CITY_MORE + city.getId() + "=" + AreaInfo2JsonUtil.areaInfoToJson(county));
				}
			}
		}
		logger.info("初始化省市区信息到Redis完成（包含短地址）！！");
	}

}
