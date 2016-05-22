package com.jfshare.baseTemplate.server;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;
import com.jfshare.baseTemplate.mybatis.model.manual.ProductPostageModel;
import com.jfshare.baseTemplate.mybatis.model.manual.ProductRefProvinceModel;
import com.jfshare.baseTemplate.mybatis.model.manual.ProductStorehouseModel;
import com.jfshare.baseTemplate.mybatis.model.manual.SellerPostageModel;
import com.jfshare.baseTemplate.mybatis.model.manual.SellerPostageReturnModel;
import com.jfshare.baseTemplate.service.IPostageTemplateSvc;
import com.jfshare.baseTemplate.service.IStorehouseSvc;
import com.jfshare.baseTemplate.util.ConvertUtil;
import com.jfshare.baseTemplate.util.FailCode;
import com.jfshare.finagle.thrift.baseTemplate.BaseTemplateServ;
import com.jfshare.finagle.thrift.baseTemplate.CalculatePostageParam;
import com.jfshare.finagle.thrift.baseTemplate.CalculatePostageResult;
import com.jfshare.finagle.thrift.baseTemplate.DeliverStorehouseParam;
import com.jfshare.finagle.thrift.baseTemplate.DeliverStorehouseResult;
import com.jfshare.finagle.thrift.baseTemplate.PostageTemplate;
import com.jfshare.finagle.thrift.baseTemplate.PostageTemplateQueryParam;
import com.jfshare.finagle.thrift.baseTemplate.PostageTemplateResult;
import com.jfshare.finagle.thrift.baseTemplate.ProductPostageBasic;
import com.jfshare.finagle.thrift.baseTemplate.ProductRefProvince;
import com.jfshare.finagle.thrift.baseTemplate.ProductStorehouse;
import com.jfshare.finagle.thrift.baseTemplate.SellerPostageBasic;
import com.jfshare.finagle.thrift.baseTemplate.SellerPostageReturn;
import com.jfshare.finagle.thrift.baseTemplate.SellerPostageTemplateParam;
import com.jfshare.finagle.thrift.baseTemplate.Storehouse;
import com.jfshare.finagle.thrift.baseTemplate.StorehouseQueryParam;
import com.jfshare.finagle.thrift.baseTemplate.StorehouseResult;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.common.metrics.Measurable;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value="handler")
public class ServHandle implements BaseTemplateServ.Iface {

	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);

	@Resource
    private IStorehouseSvc storehouseSvc;

	@Resource
	private IPostageTemplateSvc postageTemplateSvc;

	@Override
	public StringResult addStorehouse(Storehouse storehouse) throws TException {
		long start = System.currentTimeMillis();
		logger.info(">>>> addStorehouse ---- storehouse : {}", storehouse.toString());
		StringResult stringResult = new StringResult();
		Result result = new Result();
		stringResult.setResult(result);
		try {
			TbStorehouse tbStorehouse = ConvertUtil.thrift2TbStorehouse(storehouse);
			this.storehouseSvc.addStorehouse(tbStorehouse);
			stringResult.setValue(tbStorehouse.getId() + "");
		} catch (Exception e) {
			logger.error("<<<<<<<< addStorehouse error! ", e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		logger.info("<<<< addStorehouse ---- storehouse : {}, use time : {}ms!", storehouse.toString(), (System.currentTimeMillis() - start));
		return stringResult;
	}

	@Override
	public Result updateStorehouse(Storehouse storehouse) throws TException {
		long start = System.currentTimeMillis();
		logger.info(">>>> updateStorehouse ---- storehouse : {}", storehouse.toString());
		Result result = new Result();
		try {
			TbStorehouse tbStorehouse = ConvertUtil.thrift2TbStorehouse(storehouse);
			this.storehouseSvc.updateStorehouse(tbStorehouse);
			// 清除缓存
			this.storehouseSvc.removeCache(storehouse.getId());
		} catch (Exception e) {
			logger.error("<<<<<<<< updateStorehouse error! ", e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		logger.info("<<<< updateStorehouse ---- storehouse : {}, use time : {}ms!", storehouse.toString(), (System.currentTimeMillis() - start));
		return result;
	}

	@Override
	public Result deleteStorehouse(int sellerId, int id) throws TException {
		long start = System.currentTimeMillis();
		logger.info(">>>> deleteStorehouse ---- sellerId : {}, id : {}", sellerId, id);
		Result result = new Result();
		try {
			this.storehouseSvc.deleteStorehouse(sellerId, id);
			// 清除缓存
			this.storehouseSvc.removeCache(id);
		} catch (Exception e) {
			logger.error("<<<<<<<< deleteStorehouse error! ", e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		logger.info("<<<< deleteStorehouse ---- sellerId : {}, id : {}, use time : {}ms!", new Object[] {sellerId, id, System.currentTimeMillis() - start});
		return result;
	}

	@Override
	public Result deleteStorehouseBatch(List<Storehouse> storehouseList) throws TException {
		long start = System.currentTimeMillis();
		List<TbStorehouse> tbStorehouseList = new ArrayList<>();
		StringBuffer param = new StringBuffer();
		for (Storehouse storehouse : storehouseList) {
			param.append("sellerId : ").append(storehouse.getSellerId()).append(", id : ").append(storehouse.getId()).append(" , ");
			tbStorehouseList.add(ConvertUtil.thrift2TbStorehouse(storehouse));
		}
		logger.info(">>>> deleteStorehouseBatch ---- param : {}", param.toString());
		Result result = new Result();
		try {
			this.storehouseSvc.deleteStorehouseBatch(tbStorehouseList);
			// 清除缓存
			for (Storehouse storehouse : storehouseList) {
				this.storehouseSvc.removeCache(storehouse.getId());
			}
		} catch (Exception e) {
			logger.error("<<<<<<<< deleteStorehouseBatch error! ", e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		logger.info("<<<< deleteStorehouseBatch ----  param : {} use time : {}ms!", new Object[] {param.toString(), System.currentTimeMillis() - start});
		return result;
	}

	@Override
	public StorehouseResult queryStorehouse(StorehouseQueryParam param) throws TException {
		StorehouseResult storehouseResult = new StorehouseResult();
		Result result = new Result();
		storehouseResult.setResult(result);
		if (param.getSellerId() == 0) {
			result.setCode(1);
			result.addToFailDescList(FailCode.PARAM_ERROR);
			return storehouseResult;
		}
		Map queryMap = new HashMap();
		queryMap.put("id", param.getId());
		queryMap.put("sellerId", param.getSellerId());
		List<Storehouse> storehouseList = new ArrayList<>();
		try {
			List<TbStorehouse> tbStorehouseList = this.storehouseSvc.queryStorehouse(queryMap);
			for (TbStorehouse tbStorehouse : tbStorehouseList) {
				storehouseList.add(ConvertUtil.tbStorehouse2Thrift(tbStorehouse));
			}
		} catch (Exception e) {
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
			logger.error("<<<<<<<< queryStorehouse ---- error !! sellerId : " + param.getSellerId() + ", id : " + param.getId(), e);
		}
		storehouseResult.setStorehouseList(storehouseList);
		return storehouseResult;
	}

	@Override
	public StorehouseResult getStorehouse(List<Integer> storehouseIds) throws TException {

		StorehouseResult storehouseResult = new StorehouseResult();
		Result result = new Result();
		storehouseResult.setResult(result);
		List<Storehouse> storehouseList = new ArrayList<>();
		try {
			for (Integer storehouseId : storehouseIds) {
				TbStorehouse tbStorehouse = this.storehouseSvc.getById(storehouseId);
				if (tbStorehouse == null) {
					continue;
				}
				storehouseList.add(ConvertUtil.tbStorehouse2Thrift(tbStorehouse));
			}
			// 所有的仓库都不存在，返回错误信息
			if (CollectionUtils.isEmpty(storehouseList)) {
				result.setCode(1);
				result.addToFailDescList(FailCode.STOREHOUSE_NOT_EXIST);
			}
		} catch (Exception e) {
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
			logger.error("<<<<<<<< getStorehouse ---- error!! ", e);
		}
		storehouseResult.setStorehouseList(storehouseList);
		return storehouseResult;
	}

	@Override
	public DeliverStorehouseResult getDeliverStorehouse(DeliverStorehouseParam param) throws TException {
		DeliverStorehouseResult deliverStorehouseResult = new DeliverStorehouseResult();
		Result result = new Result();
		deliverStorehouseResult.setResult(result);
		if(CollectionUtils.isEmpty(param.getProductRefProvinceList())) {
			result.setCode(1);
			result.addToFailDescList(FailCode.PARAM_ERROR);
			return deliverStorehouseResult;
		}
		List<ProductRefProvinceModel> productRefProvinceModels = new ArrayList<>();
		try {
			for (ProductRefProvince productRefProvince : param.getProductRefProvinceList()) {
                ProductRefProvinceModel model = new ProductRefProvinceModel();
                model.setSellerId(productRefProvince.getSellerId());
                model.setProductId(productRefProvince.getProductId());
                model.setStorehouseIds(productRefProvince.getStorehouseIds());
                model.setSendToProvince(productRefProvince.getSendToProvince());
                productRefProvinceModels.add(model);
            }

			List<ProductStorehouseModel> productStorehouseModels = this.storehouseSvc.getDeliverStorehouse(productRefProvinceModels);

			for (ProductStorehouseModel productStorehouseModel : productStorehouseModels) {
                ProductStorehouse productStorehouse = new ProductStorehouse();
                productStorehouse.setSellerId(productStorehouseModel.getSellerId());
                productStorehouse.setProductId(productStorehouseModel.getProductId());
                productStorehouse.setStorehouseId(productStorehouseModel.getStorehouseId());
                deliverStorehouseResult.addToProductStorehouseList(productStorehouse);
            }
		} catch (Exception e) {
			logger.error("<<<<<<<< getDeliverStorehouse error !! param : " + param.toString(), e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}

		return deliverStorehouseResult;
	}

	@Override
	public StringResult addPostageTemplate(PostageTemplate postageTemplate) throws TException {
		long start = System.currentTimeMillis();
		logger.info(">>>> addPostageTemplate ---- postageTemplate : {}", postageTemplate.toString());
		StringResult stringResult = new StringResult();
		Result result = new Result();
		stringResult.setResult(result);
		try {
			TbPostageTemplate tbPostageTemplate = ConvertUtil.thrift2TbPostageTemplate(postageTemplate);
			this.postageTemplateSvc.addPostageTemplate(tbPostageTemplate);
			stringResult.setValue(tbPostageTemplate.getId() + "");
		} catch (Exception e) {
			logger.error("<<<<<<<< addPostageTemplate error! ", e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		logger.info("<<<< addPostageTemplate ---- postageTemplate : {}, use time : {}ms!", postageTemplate.toString(), (System.currentTimeMillis() - start));
		return stringResult;
	}

	@Override
	public Result updatePostageTemplate(PostageTemplate postageTemplate) throws TException {
		long start = System.currentTimeMillis();
		logger.info(">>>> updatePostageTemplate ---- postageTemplate : {}", postageTemplate.toString());
		Result result = new Result();
		try {
			TbPostageTemplate tbPostageTemplate = ConvertUtil.thrift2TbPostageTemplate(postageTemplate);
			this.postageTemplateSvc.updatePostageTemplate(tbPostageTemplate);
			// 清除缓存
			this.postageTemplateSvc.removeCache(postageTemplate.getId());
		} catch (Exception e) {
			logger.error("<<<<<<<< updatePostageTemplate error! ", e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		logger.info("<<<< updatePostageTemplate ---- postageTemplate : {}, use time : {}ms!", postageTemplate.toString(), (System.currentTimeMillis() - start));
		return result;
	}

	@Override
	public Result deletePostageTemplate(int sellerId, int id) throws TException {
		long start = System.currentTimeMillis();
		logger.info(">>>> deletePostageTemplate ---- sellerId : {}, id : {}", sellerId, id);
		Result result = new Result();
		try {
			this.postageTemplateSvc.deletePostageTemplate(sellerId, id);
			// 清除缓存
			this.postageTemplateSvc.removeCache(id);
		} catch (Exception e) {
			logger.error("<<<<<<<< deletePostageTemplate error! ", e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		logger.info("<<<< deletePostageTemplate ---- sellerId : {}, id : {}, use time : {}ms!", new Object[] {sellerId, id, System.currentTimeMillis() - start});
		return result;
	}

	@Override
	public Result deletePostageTemplateBatch(List<PostageTemplate> postageTemplateList) throws TException {
		long start = System.currentTimeMillis();
		List<TbPostageTemplate> tbPostageTemplateList = new ArrayList<>();
		StringBuffer param = new StringBuffer();
		for (PostageTemplate postageTemplate : postageTemplateList) {
			param.append("sellerId : ").append(postageTemplate.getSellerId()).append(", id : ").append(postageTemplate.getId()).append(" , ");
			tbPostageTemplateList.add(ConvertUtil.thrift2TbPostageTemplate(postageTemplate));
		}
		logger.info(">>>> deletePostageTemplateBatch ---- param : {}", param.toString());
		Result result = new Result();
		try {
			this.postageTemplateSvc.deletePostageTemplateBatch(tbPostageTemplateList);
			// 清除缓存
			for (PostageTemplate postageTemplate : postageTemplateList) {
				this.postageTemplateSvc.removeCache(postageTemplate.getId());
			}
		} catch (Exception e) {
			logger.error("<<<<<<<< deletePostageTemplateBatch error! ", e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		logger.info("<<<< deletePostageTemplateBatch ----  param : {} use time : {}ms!", new Object[] {param.toString(), System.currentTimeMillis() - start});
		return result;
	}

	@Override
	public PostageTemplateResult queryPostageTemplate(PostageTemplateQueryParam param) throws TException {
		PostageTemplateResult postageTemplateResult = new PostageTemplateResult();
		Result result = new Result();
		postageTemplateResult.setResult(result);
		if (param.getSellerId() == 0) {
			result.setCode(1);
			result.addToFailDescList(FailCode.PARAM_ERROR);
			return postageTemplateResult;
		}
		Map queryMap = new HashMap();
		queryMap.put("id", param.getId());
		queryMap.put("sellerId", param.getSellerId());
		queryMap.put("type", param.getType());
		queryMap.put("name", param.getName());
		queryMap.put("templateGroup", param.getTemplateGroup());
		queryMap.put("isUsed", param.getIsUsed());
		List<PostageTemplate> postageTemplateList = new ArrayList<>();
		try {
			List<TbPostageTemplate> tbPostageTemplateList = this.postageTemplateSvc.queryPostageTemplate(queryMap);
			for (TbPostageTemplate tbPostageTemplate : tbPostageTemplateList) {
				postageTemplateList.add(ConvertUtil.tbPostageTemplate2Thrift(tbPostageTemplate));
			}
		} catch (Exception e) {
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
			logger.error("<<<<<<<< queryPostageTemplate ---- error !! sellerId : " + param.getSellerId() + ", id : " + param.getId(), e);
		}
		postageTemplateResult.setPostageTemplateList(postageTemplateList);
		return postageTemplateResult;
	}

	@Override
	public PostageTemplateResult getPostageTemplate(List<Integer> postageTemplateIds) throws TException {
		PostageTemplateResult postageTemplateResult = new PostageTemplateResult();
		Result result = new Result();
		postageTemplateResult.setResult(result);
		List<PostageTemplate> postageTemplateList = new ArrayList<>();
		try {
			for (Integer postageTemplateId : postageTemplateIds) {
				TbPostageTemplate tbPostageTemplate = this.postageTemplateSvc.getById(postageTemplateId);
				if (tbPostageTemplate == null) {
					continue;
				}
				postageTemplateList.add(ConvertUtil.tbPostageTemplate2Thrift(tbPostageTemplate));
			}
			// 所有的邮费模板都不存在，返回错误信息
			if (CollectionUtils.isEmpty(postageTemplateList)) {
				result.setCode(1);
				result.addToFailDescList(FailCode.POSTAGETEMPLATE_NOT_EXIST);
			}
		} catch (Exception e) {
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
			logger.error("<<<<<<<< getPostageTemplate ---- error !!", e);
		}
		postageTemplateResult.setPostageTemplateList(postageTemplateList);
		return postageTemplateResult;
	}

	@Override
	public PostageTemplateResult getSellerPostageTemplate(SellerPostageTemplateParam param) throws TException {



		return null;
	}

	@Override
	public Result setDefaultPostageTemplate(PostageTemplate postageTemplate) throws TException {
		logger.info(">>>> setDefaultPostageTemplate ---- postageTemplate : {}", postageTemplate.toString());
		Result result = new Result(0);
		try {
			TbPostageTemplate tbPostageTemplate = ConvertUtil.thrift2TbPostageTemplate(postageTemplate);
			this.postageTemplateSvc.setDefaultPostageTemplate(tbPostageTemplate);
		} catch (Exception e) {
			logger.error("<<<<<<<< setDefaultPostageTemplate error !! postageTemplate");
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		return result;
	}

	@Override
	public CalculatePostageResult calculatePostage(CalculatePostageParam param) throws TException {

		CalculatePostageResult calculatePostageResult = new CalculatePostageResult();
		Result result = new Result();
		calculatePostageResult.setResult(result);

		List<SellerPostageModel> sellerPostageModels = new ArrayList<>();
		List<ProductPostageModel> productPostageModels = new ArrayList<>();

		String sendTProvince = param.getSendToProvince();

		// 转换实体
		for (SellerPostageBasic sellerPostageBasic : param.getSellerPostageBasicList()) {
			SellerPostageModel sellerPostageModel = new SellerPostageModel();
			for (ProductPostageBasic productPostageBasic : sellerPostageBasic.getProductPostageBasicList()) {
				ProductPostageModel productPostageModel = new ProductPostageModel();
				productPostageModel.setProductId(productPostageBasic.getProductId());
				productPostageModel.setTemplateId(productPostageBasic.getTemplateId());
				productPostageModel.setNumber(productPostageBasic.getNumber());
				productPostageModel.setWeight(productPostageBasic.getWeight());
				productPostageModel.setAmount(productPostageBasic.getAmount());
				productPostageModels.add(productPostageModel);
			}
			sellerPostageModel.setSellerId(sellerPostageBasic.getSellerId());
			sellerPostageModel.setProductPostageModels(productPostageModels);
			sellerPostageModels.add(sellerPostageModel);
		}
		List<SellerPostageReturnModel> sellerPostageList = new ArrayList<>();
		try {
//			total = this.postageTemplateSvc.calculatePostage(calculatePostageModel);
			sellerPostageList = this.postageTemplateSvc.calculatePostage(sellerPostageModels, sendTProvince);
		} catch (Exception e) {
			logger.error("<<<<<<<< calculatePostage error !! param : " + param.toString(), e);
			result.setCode(1);
			result.addToFailDescList(FailCode.POSTAGE_CALCULATE_PARAM_ERROR);
			return calculatePostageResult;
		}
		if (CollectionUtils.isEmpty(sellerPostageList)) {
			logger.error("<<<<<<<< calculatePostage error !! param : " + param.toString());
			result.setCode(1);
			result.addToFailDescList(FailCode.POSTAGE_CALCULATE_FAIL);
		} else {
			// 转换实体
			int totalPostage = 0;
			for (SellerPostageReturnModel model : sellerPostageList) {
				calculatePostageResult.addToSellerPostageReturnList(new SellerPostageReturn(model.getSellerId(), model.getPostage() + ""));
				totalPostage += model.getPostage();
			}
			calculatePostageResult.setTotalPostage(totalPostage + "");
		}

		return calculatePostageResult;
	}
}
