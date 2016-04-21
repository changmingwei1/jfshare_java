package com.jfshare.express.db.redis;

import java.util.List;

import com.jfshare.finagle.thrift.express.ExpressInfo;


public interface IExpressJedis {

	/**
	 * 创建物流自动补全索引
	 * @param expressInfoList
	 * @return 
	 */
	long setExpressPrefix(List<ExpressInfo> expressInfoList);
	
	/**
	 * 删除某物流自动补全索引
	 */
	boolean removeExpressPrefix(ExpressInfo expressInfo);
	
	
	
	/**
	 * 物流缓存是否存在
	 * @return
	 */
	boolean existsExpressInfo();
	
	/**
	 * 获取缓存物流信息
	 * @return
	 */
	List<ExpressInfo> query();
	
	/**
	 * 添加物流缓存信息
	 * @param expressInfoList
	 * @return
	 */
	long setExpressInfo(List<ExpressInfo> expressInfoList);

	/**
	 * 根据Id查询缓存物流
	 * @param id
	 * @return
	 */
	ExpressInfo queryById(int id);

	/**
	 * 删除物流缓存
	 * @param oldInfo
	 */
	void removeExpress(ExpressInfo oldInfo);

	boolean removeAllExpress();

	List<ExpressInfo> queryByIds(List<String> validIdsList);

	/**
	 * 精简物流信息缓存是否存在
	 * @return
	 */
	boolean existsExpressSimpleInfo();
	
	/**
	 * 
	 *  Function:
	 *  功能说明：加载精简物流信息缓存信息
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年2月10日 下午5:40:37
	 *	 返回类型: void    
	 *  @param expressInfoList
	 */
	void setExpressSimpleInfo(List<ExpressInfo> expressInfoList);
	
	/**
	 * 
	 *  Function:
	 *  功能说明：删除精简物流信息
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年2月10日 下午5:40:37
	 *	 返回类型: void    
	 */
	boolean removeAllExpressSimple();

	/**
	 *  Function:
	 *  功能说明：查询精简物流信息
	 *	 使用说明：
	 *  @author  sushi  DateTime 2015年2月10日 下午6:42:40
	 *	 返回类型: List<ExpressInfo>    
	 *  @return
	 */
	List<ExpressInfo> queryExpressSimpleInfo();
}
