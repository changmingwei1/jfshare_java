package com.jfshare.address.server;

import java.util.ArrayList;
import java.util.List;

import com.jfshare.address.db.redis.IAddressJedis;
import com.jfshare.address.db.redis.RedisConst;
import com.jfshare.address.impl.AddressImpl;
import com.jfshare.address.util.AddressInfo2JsonUtil;
import com.jfshare.address.util.Common;
import com.jfshare.address.util.FailCode;
import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.finagle.thrift.address.AddressInfoListResult;
import com.jfshare.finagle.thrift.address.AddressInfoResult;
import com.jfshare.finagle.thrift.address.AddressServ;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "handler")
public class ServHandle implements AddressServ.Iface {
	private Logger logger = LoggerFactory.getLogger(ServHandle.class);
    private Logger eagleyeLogger = LoggerFactory.getLogger("com.jfshare.AddressServLogger");
	@Autowired
	@Qualifier("addressImpl")
	private AddressImpl addressImpl;

	@Autowired
	private IAddressJedis addressJedis;

	public AddressImpl getProductImpl() {
		return addressImpl;
	}

	public void setProductImpl(AddressImpl addressImpl) {
		this.addressImpl = addressImpl;
	}

	
	public StringResult addAddress(AddressInfo addressInfo) throws TException {
//		logger.info("addressInfo:" + addressInfo.toString());
		eagleyeLogger.info("addAddress：新增收货人地址，userId="+addressInfo.getUserId()+",ReceiverName="+addressInfo.getReceiverName()
				+",ProvinceName="+addressInfo.getProvinceName()+",CityName="+addressInfo.getCityName()+"，Address="+addressInfo.getAddress()+",Mobile="+addressInfo.getMobile());
		
		Result result = new Result();
		StringResult stringResult = new StringResult();
		List<FailDesc> failDescList = new ArrayList<FailDesc>();
		int userId = addressInfo.getUserId();
		int cnt = this.addressImpl.getCount(userId);
		//收件人地址不能超过20个
		if (cnt > 19) {
			failDescList.add(FailCode.receiverAddressOverCount);
			if (!failDescList.isEmpty()) {
				result.setCode(1);
				result.setFailDescList(failDescList);
				return stringResult.setResult(result);
			}
		}
		//检查收件人地址的合法性
		failDescList = checkAddressInfo(addressInfo);
		if (!failDescList.isEmpty()) {
			result.setCode(1);
			result.setFailDescList(failDescList);
		} else {
			int id = this.addressImpl.add(addressInfo);
			stringResult.setValue(id + "");
			result.setCode(0);

			//用户收件人地址写入Redis缓存
			addressToRedis(addressInfo.getUserId());

		}
		stringResult.setResult(result);
		logger.info("新增收货人地址完成，stringResult:" + stringResult.toString());
		return stringResult;
	}


	public Result updateAddress(AddressInfo addressInfo) throws TException {
//		logger.info("addressInfo:" + addressInfo.toString());
		eagleyeLogger.info("updateAddress：修改收货人地址，userId="+addressInfo.getUserId()+",id="+addressInfo.getId()+",ReceiverName="+addressInfo.getReceiverName()
				+",ProvinceName="+addressInfo.getProvinceName()+",CityName="+addressInfo.getCityName()+"，Address="+addressInfo.getAddress()+",Mobile="+addressInfo.getMobile());
		
		Result result = new Result();		
		//检查收件人地址的合法性
		List<FailDesc> failDescList = checkAddressInfo(addressInfo);
		if (!failDescList.isEmpty()) {
			result.setCode(1);
			result.setFailDescList(failDescList);
		} else {
			if (this.addressImpl.update(addressInfo)) {
				result.setCode(0);

				//用户收件人地址写入Redis缓存
				addressToRedis(addressInfo.getUserId());
			} else {
				failDescList.add(FailCode.idIsNone);
				result.setCode(1);
				result.setFailDescList(failDescList);
			}
		}
		logger.info("修改收货人地址完成，result:" + result.toString());
		return result;
	}

	
	public Result delAddress(int userId, int id) throws TException {
//		logger.info("id:" + id + "..." + "userId:" + userId);
		eagleyeLogger.info("delAddress：删除收货人地址，userId="+userId+",id="+id);

		List<FailDesc> failDescList = new ArrayList<FailDesc>();
		if (!FailCode.isEmpty(id)) {
			failDescList.add(FailCode.idIsEmpty);
		}
		if (!FailCode.isEmpty(userId)) {
			failDescList.add(FailCode.userIdIsEmpty);
		}

		Result result = new Result();
		if (!failDescList.isEmpty()) {
			result.setCode(1);
			result.setFailDescList(failDescList);
		} else {
			if (this.addressImpl.delete(userId, id)) {
				result.setCode(0);

				//用户收件人地址写入Redis缓存
				addressToRedis(userId);
//				
//				// Redis中删除		
//				List<AddressInfo> addressInfoList = addressJedis.getAddressInfoByUserId(""+userId);
//				AddressInfo addressInfo = null;
//				if(addressInfoList != null && addressInfoList.size()>=0){
//					for (AddressInfo info : addressInfoList) {
//						if(info != null && info.getId() == id){
//							addressInfo = info;
//							break;
//						}
//					}
//				}
//				if(addressInfo != null){
//					addressJedis.zremAddress(RedisConst._RECEIVER_ADDRESS + userId, AddressInfo2JsonUtil.AddressInfoToJson(addressInfo));
//				}
			} else {
				failDescList.add(FailCode.idIsNone);
				result.setCode(1);
				result.setFailDescList(failDescList);
			}

		}
		logger.info("删除收货人地址完成，result:" + result.toString());
		return result;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public AddressInfoListResult queryAddress(int userId) throws TException {
		String startTime = Common.formatTimeToMill(null);

//		logger.info("userId:" + userId + "...");
		logger.info("根据买家id获取地址列表，userId：" + userId);

		List<AddressInfo> addressInfoList = addressJedis.getAddressInfoByUserId(""+userId);
		if(addressInfoList == null || addressInfoList.size()<=0){
			logger.info("------根据买家id获取地址列表，在redis中没找到，到数据库中查询-------userId="+userId);
			logger.info("根据买家id获取地址列表，,数据库查询开始，开始时间：" + Common.formatTimeToMill(null));
			addressInfoList = this.addressImpl.queryAddress(userId);
			logger.info("根据买家id获取地址列表，,数据库查询结束，结束时间：" + Common.formatTimeToMill(null));

			// 用户收件人地址写入Redis缓存
			addressToRedis(addressInfoList);
		}

		Result result = new Result();
		result.setCode(0);
		AddressInfoListResult addressInfoListResult = new AddressInfoListResult();
		addressInfoListResult.setResult(result);
		addressInfoListResult.setAddressInfoList(addressInfoList);

		logger.info("根据买家id获取地址列表，开始时间：" + startTime + "，结束时间：" + Common.formatTimeToMill(null));
		logger.info("addressInfoListResult:" + addressInfoListResult);

//		// 判断地址是否存在在Redis，如果存在不重新写入
//		if (!addressJedis.isExists(RedisConst._RECEIVER_ADDRESS + userId)) {
//			// 用户收件人地址写入Redis缓存
//			addressToRedis(userId);
//		}

		return addressInfoListResult;
	}

	
	public Result setDefaultAddress(int userId, int id) throws TException {
//		logger.info("设置默认收货人地址，id:" + id + "..." + "userId:" + userId);
		eagleyeLogger.info("setDefaultAddress：设置默认收货人地址，userId="+userId+",id="+id);

		List<FailDesc> failDescList = new ArrayList<FailDesc>();
		if (!FailCode.isEmpty(id)) {
			failDescList.add(FailCode.idIsEmpty);
		}
		if (!FailCode.isEmpty(userId)) {
			failDescList.add(FailCode.userIdIsEmpty);
		}

		Result result = new Result();
		if (!failDescList.isEmpty()) {
			result.setCode(1);
			result.setFailDescList(failDescList);
		} else {
			if (this.addressImpl.setDefaultAddress(userId, id)) {
				result.setCode(0);

				// 用户收件人地址写入Redis缓存
				addressToRedis(userId);
			} else {
				failDescList.add(FailCode.idIsNone);
				result.setCode(1);
				result.setFailDescList(failDescList);
			}
		}
		logger.info("设置默认收货人地址完成，result:" + result.toString());
		return result;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public AddressInfo queryAddressById(int id, int userId) throws TException {
		logger.info("queryAddressById查询收货地址，id:" + id + "..." + "userId:" + userId);
		List<AddressInfo> addressInfoList = addressJedis.getAddressInfoByUserId(""+userId);
		AddressInfo addressInfo = null;
		if(addressInfoList != null && addressInfoList.size()>=0){
			for (AddressInfo info : addressInfoList) {
				if(info != null && info.getId() == id){
					addressInfo = info;
					break;
				}
			}
		}
		if(addressInfo == null){
			logger.info("------获取买家收件人地址列表，在redis中没找到，到数据库中查询-------userId="+userId);
			addressInfo = this.addressImpl.queryAddressById(id, userId);
		}
		if (addressInfo != null) {
			logger.info("addressInfo:" + addressInfo.toString());
		} else {
			logger.info("addressInfo:" + addressInfo);
		}
		return addressInfo;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public AddressInfoResult queryAddressInfo(int id, int userId) throws TException {
		logger.info("queryAddressInfo查询收货地址，id:" + id + "..." + "userId:" + userId + "...");		
		List<AddressInfo> addressInfoList = addressJedis.getAddressInfoByUserId(""+userId);
		AddressInfo addressInfo = null;
		if(addressInfoList != null && addressInfoList.size()>=0){
			for (AddressInfo info : addressInfoList) {
				if(info != null && info.getId() == id){
					addressInfo = info;
					break;
				}
			}
		}
		if(addressInfo == null){
			logger.info("------获取买家收件人地址列表，在redis中没找到，到数据库中查询-------userId="+userId);
			addressInfo = this.addressImpl.queryAddressInfo(id, userId);
		}
		AddressInfoResult addressInfoResult = new AddressInfoResult();
		Result result = new Result();
		if (addressInfo == null) {
			List<FailDesc> failDescList = new ArrayList<FailDesc>();
			failDescList.add(FailCode.DATA_NO_EXIST);
			result.setCode(1).setFailDescList(failDescList);
			addressInfoResult.setResult(result);
			logger.info("addressInfoResult:" + addressInfoResult.toString());
			return addressInfoResult;
		} else {
			result.setCode(0);
			addressInfoResult.setResult(result);
			addressInfoResult.setAddressInfo(addressInfo);
			logger.info("addressInfoResult:" + addressInfoResult.toString());
			return addressInfoResult;
		}
	}

	/**
	 * 获得用户的默认收货地址
	 * @param id
	 * @param userId
	 * @return
	 * @throws TException
	 */
	public AddressInfoResult getDefaultAddress(int userId) throws TException {
		logger.info("获取用户默认收货地址，userId:" + userId);
		AddressInfoResult addressInfoResult = new AddressInfoResult();
		List<AddressInfo> addressInfoList = addressJedis.getAddressInfoByUserId(""+userId);
		AddressInfo addressInfo = null;
		if(addressInfoList != null && addressInfoList.size()>=0){
			for (AddressInfo info : addressInfoList) {
				if(info != null && info.isDefault==1){
					addressInfo = info;
					break;
				}
			}
		}
		if(addressInfo == null){
			logger.info("------获取买家收件人默认地址，在redis中没找到，到数据库中查询-------userId="+userId);
			addressInfo = this.addressImpl.getDefaultAddress(userId);
		}
		Result result = new Result();
		if (addressInfo == null) {
			List<FailDesc> failDescList = new ArrayList<FailDesc>();
			failDescList.add(FailCode.DATA_NO_EXIST);
			result.setCode(1).setFailDescList(failDescList);
			addressInfoResult.setResult(result);
			logger.info("获取用户默认收货地址失败，没有找到默认的收货地址，userId:" + userId);
			return addressInfoResult;
		} else {
			result.setCode(0);
			addressInfoResult.setResult(result);
			addressInfoResult.setAddressInfo(addressInfo);
			logger.info("获取用户默认收货地址成功，userId:" + userId+",地址ID:"+addressInfo.getId());
			return addressInfoResult;
		}	
	}
	
	

	// 用户收件人地址写入Redis缓存
	private void addressToRedis(Integer userId) {
		// 先删除原有的地址，再添加新的
		addressJedis.deleteKeys(RedisConst._RECEIVER_ADDRESS + String.valueOf(userId));
		List<AddressInfo> addressInfoList = this.addressImpl.queryAddressByMasterData(userId);
		if (addressInfoList.size() > 0) {
			try {
				eagleyeLogger.info("用户收件人地址写入Redis缓存，userid:" + userId);		

				int index = 0;// 显示顺序
				for (AddressInfo info : addressInfoList) {
					index++;
					info.setIndex(index);
					addressJedis.saveSortAddress(RedisConst._RECEIVER_ADDRESS + userId, index, AddressInfo2JsonUtil.AddressInfoToJson(info));
				}
				// 设置过期时间
				addressJedis.setExpire(RedisConst._RECEIVER_ADDRESS + userId, RedisConst._EXPIRE_TIME);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	// 用户收件人地址写入Redis缓存
	private void addressToRedis(List<AddressInfo> addressInfoList) {
//		List<AddressInfo> addressInfoList = this.addressImpl.queryAddress(userId);
		if (addressInfoList.size() > 0) {
			try {
				int userId = addressInfoList.get(0).getUserId();
				eagleyeLogger.info("用户收件人地址addressInfoList写入Redis缓存，userid:" + userId);
				// 先删除原有的地址，再添加新的
				addressJedis.deleteKeys(RedisConst._RECEIVER_ADDRESS + userId);				

				int index = 0;// 显示顺序
				for (AddressInfo info : addressInfoList) {
					index++;
					info.setIndex(index);
					addressJedis.saveSortAddress(RedisConst._RECEIVER_ADDRESS + userId, index, AddressInfo2JsonUtil.AddressInfoToJson(info));
				}
				// 设置过期时间
				addressJedis.setExpire(RedisConst._RECEIVER_ADDRESS + userId, RedisConst._EXPIRE_TIME);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	
	
	// 用户收件人地址写入Redis缓存
	private void addressToRedis(Integer userId, AddressInfo addressInfo) {
		List<AddressInfo> addressInfoList = this.addressImpl.queryAddress(userId);
		if (addressInfoList.size() > 0) {
			try {
				logger.info("------用户收件人地址写入Redis缓存，userid:" + userId);
				// 先删除原有的地址，再添加新的
				addressJedis.deleteKeys(RedisConst._RECEIVER_ADDRESS + String.valueOf(userId));				

				//addressInfo不为Null，是修改地址信息的
				if (addressInfo != null) {
					int i=0;
					//在列表中删除被修改的地址，再重新加入
					for (AddressInfo info : addressInfoList) {
						//如果新增的是默认地址，将原有的默认改为非默认
						if(addressInfo.getIsDefault()==1 && info.getIsDefault()==1){
							info.setIsDefault(0);
						}
						if(info.getId()==addressInfo.getId()){
							addressInfoList.remove(i);
							break;
						}
						i++;
					}
					// 如果原有地址列表第一位是默认地址，被修改的放入第二位，默认地址的后面
					if (addressInfoList.get(0).getIsDefault() == 1) {
						addressInfoList.add(1, addressInfo);
						// 如果被修改的地址是默认地址，放入第一位
					} else {
						addressInfoList.add(0, addressInfo);
					}
				}
				int index = 0;// 显示顺序
				for (AddressInfo info : addressInfoList) {
					index++;
					info.setIndex(index);
//					addressJedis.setMap(RedisConst._RECEIVER_ADDRESS + userId, String.valueOf(info.getId()), AddressInfo2JsonUtil.AddressInfoToJson(info));
					addressJedis.saveSortAddress(RedisConst._RECEIVER_ADDRESS + userId, index, AddressInfo2JsonUtil.AddressInfoToJson(info));
				}
				// 设置过期时间
				addressJedis.setExpire(RedisConst._RECEIVER_ADDRESS + userId, RedisConst._EXPIRE_TIME);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	// 设置收件人默认地址写入Redis缓存
	private void setIsdefaultRedis(Integer userId, AddressInfo addressInfo) {
		List<AddressInfo> addressInfoList = this.addressImpl.queryAddress(userId);
		if (addressInfoList.size() > 0) {
			try {
				logger.info("------设置收件人默认地址写入Redis缓存，userid:" + userId);
				// 先删除原有的地址，再添加新的
				addressJedis.deleteKeys(RedisConst._RECEIVER_ADDRESS + String.valueOf(userId));

				// addressInfo不为Null，是修改地址信息的
				if (addressInfo != null) {
					int i = 0;
					// 在列表中删除被修改的地址，再重新加入
					for (AddressInfo info : addressInfoList) {
						// 原来的默认地址改为非默认
						if (info.getIsDefault() == 1) {
							info.setIsDefault(0);
						}
						if (info.getId() == addressInfo.getId()) {
							addressInfoList.remove(i);
							break;
						}
						i++;
					}
					// 设置被修改的地址为默认地址，放入第一位
					addressInfo.setIsDefault(1);
					addressInfoList.add(0, addressInfo);
				}
				int index = 0;// 显示顺序
				for (AddressInfo info : addressInfoList) {
					index++;
					info.setIndex(index);
//					addressJedis.setMap(RedisConst._RECEIVER_ADDRESS + userId, String.valueOf(info.getId()), AddressInfo2JsonUtil.AddressInfoToJson(info));
					addressJedis.saveSortAddress(RedisConst._RECEIVER_ADDRESS + userId, index, AddressInfo2JsonUtil.AddressInfoToJson(info));
				}
				// 设置过期时间
				addressJedis.setExpire(RedisConst._RECEIVER_ADDRESS + userId, RedisConst._EXPIRE_TIME);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	//检查AddressInfo的合法性
	private List<FailDesc> checkAddressInfo(AddressInfo addressInfo){
		List<FailDesc> failDescList = new ArrayList<FailDesc>();
		int userId = addressInfo.getUserId();
		//校验userid
		if (!FailCode.isEmpty(userId)) {
			failDescList.add(FailCode.userIdIsEmpty);
		}
		//校验用户名称
		String receiverName = addressInfo.getReceiverName();
		if (!FailCode.isEmpty(receiverName)) {
			failDescList.add(FailCode.receiverNameIsEmpty);
		} else {
			if (!FailCode.receiverNameOverLength(receiverName)) {
				failDescList.add(FailCode.receiverNameOverLength);
			}
		}
		//校验联系电话
		String mobile = addressInfo.getMobile();
		String tel = addressInfo.getTel();
		String telCode = addressInfo.getTelCode();
		String telExt = addressInfo.getTelExtNumber();
		if (!FailCode.isEmpty(mobile) && !FailCode.isEmpty(tel)) {
			failDescList.add(FailCode.receiverMobileTelIsEmpty);
		} else {
			if (FailCode.isEmpty(mobile) && !FailCode.receiverMobileNotLegal(mobile)) {
				failDescList.add(FailCode.receiverMobileNotLegal);
			}
			if (!FailCode.isEmpty(mobile) && !FailCode.isEmpty(tel)) {
				failDescList.add(FailCode.receiverTelIsEmpty);
			}
			if (!FailCode.isEmpty(mobile) && !FailCode.isEmpty(telCode)) {
				failDescList.add(FailCode.telCodeIsEmpty);
			}

			if (FailCode.isEmpty(telCode) && !FailCode.telCodeNotLegal(telCode)) {
				failDescList.add(FailCode.telCodeNotLegal);
			}
			if (FailCode.isEmpty(tel) && !FailCode.telNotLegal(tel)) {
				failDescList.add(FailCode.telNotLegal);
			}
			if (FailCode.isEmpty(telExt) && !FailCode.telExtNotLegal(telExt)) {
				failDescList.add(FailCode.telExtNotLegal);
			}
		}



		//校验省ID是否在省列表里，不在认为非法
		if(addressInfo.getProvinceId() <= 0 || !addressJedis.isExists(RedisConst._ADD_PROVINCE+addressInfo.getProvinceId())){
			failDescList.add(FailCode.provinceIdIsEmpty);
		}
		//校验市ID是否在市列表里，不在认为非法
		if(addressInfo.getCityId() <= 0 || !addressJedis.isExists(RedisConst._ADD_CITY+addressInfo.getCityId())){
			failDescList.add(FailCode.cityIdIsEmpty);
		}
//		//校验省ID
//		if (!FailCode.provinceIdNotLegal(addressInfo.getProvinceId())) {
//			failDescList.add(FailCode.provinceIdIsEmpty);
//		}
//		//校验市ID
//		if (!FailCode.cityIdNotLegal(addressInfo.getCityId())) {
//			failDescList.add(FailCode.cityIdIsEmpty);
//		}
		//校验区ID
		if (!FailCode.isEmpty(addressInfo.getCountyId())) {
			failDescList.add(FailCode.countyIdIsEmpty);
		}		
		//校验省名称
		if (!FailCode.isEmpty(addressInfo.getProvinceName())) {
			failDescList.add(FailCode.provinceNameIsEmpty);
		}
		//校验市名称
		if (!FailCode.isEmpty(addressInfo.getCityName())) {
			failDescList.add(FailCode.cityNameIsEmpty);
		}
		//校验详细地址
		String address = addressInfo.getAddress();
		if (!FailCode.isEmpty(address)) {
			failDescList.add(FailCode.receiverAddressIsEmpty);
		} else {
			if (!FailCode.receiverAddrShort(address)) {
				failDescList.add(FailCode.receiverAddressShort);
			}
			if (!FailCode.receiverAddrOverLength(address)) {
				failDescList.add(FailCode.receiverAddressOverLength);
			}
		}
		//校验email
		String email = addressInfo.getEmail();
		if (FailCode.isEmpty(email) && !FailCode.emailOverLength(email)) {
			failDescList.add(FailCode.emailOverLength);
		}
		//校验邮编
		if (!FailCode.isEmpty(addressInfo.getPostCode())) {
			failDescList.add(FailCode.postCodeIsEmpty);
		} else {
			if (!FailCode.postCodeNotLegal(addressInfo.getPostCode())) {
				failDescList.add(FailCode.postCodeNotLegal);
			}
		}
		return failDescList;
	}
	
}
