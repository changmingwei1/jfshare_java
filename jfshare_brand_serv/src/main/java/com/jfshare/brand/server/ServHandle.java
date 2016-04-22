package com.jfshare.brand.server;

import com.jfshare.brand.service.IBrandSvc;
import com.jfshare.brand.util.FailCode;
import com.jfshare.finagle.thrift.brand.BrandInfo;
import com.jfshare.finagle.thrift.brand.BrandResult;
import com.jfshare.finagle.thrift.brand.BrandServ;
import com.jfshare.finagle.thrift.brand.QueryParam;
import com.jfshare.finagle.thrift.result.Result;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value="handler")
public class ServHandle implements BrandServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Autowired
    private IBrandSvc brandSvcImpl;

	@Override
	public BrandResult query() throws TException {
		BrandResult brandResult = new BrandResult();
		Result result = new Result();
		result.setCode(0);
		brandResult.setResult(result);

		try {
			List<BrandInfo> brands = brandSvcImpl.query();
			brandResult.setBrandInfo(brands);
		} catch (Exception e) {
			logger.error("查询品牌出错！", e);
			result.setCode(1);
		}

		return brandResult;
	}

	@Override
	public BrandResult queryByPage(QueryParam param) throws TException {
		BrandResult brandResult = new BrandResult();
		Result result = new Result();
		result.setCode(0);
		brandResult.setResult(result);
		if (param == null) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return brandResult;
		}

		try {
			int total = -1;
			if (param.getPageSize() > 0) {
				total = brandSvcImpl.queryCount(param);
			}
			if (total != 0) {
				List<BrandInfo> brands = brandSvcImpl.queryByPage(param);
				brandResult.setBrandInfo(brands);
			}
			int pageSize = param.getPageSize()==0 ? Integer.MAX_VALUE : param.getPageSize();
			int pageCount = total%pageSize == 0 ? total/pageSize : total/pageSize + 1;
			brandResult.setTotal(total);
			brandResult.setPageCount(pageCount);
		}  catch (Exception e) {
			logger.error("分页查询品牌出错！params=" + param, e);
			result.setCode(1);
		}

		return brandResult;
	}

	@Override
	public BrandResult queryBatch(List<Integer> idList) throws TException {
		BrandResult brandResult = new BrandResult();
		Result result = new Result();
		result.setCode(0);
		brandResult.setResult(result);
		if (idList == null || idList.size() == 0) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return brandResult;
		}

		try {
			List<Integer> validIdList = new ArrayList<Integer>();
			for(Integer id : idList) {
				if (id==null || id <=0) {
					continue;
				}
				validIdList.add(id);
			}

			List<BrandInfo> brands = brandSvcImpl.queryByIds(validIdList);
			brandResult.setBrandInfo(brands);
		} catch (Exception e) {
			logger.error("批量查询品牌出错！params=" + idList, e);
			result.setCode(1);
		}

		return brandResult;
	}

	@Override
	public Result addBrand(BrandInfo brand) throws TException {
		Result result = new Result();
		result.setCode(0);
		try {
			int ret = brandSvcImpl.addBrand(brand);
			if(ret!=0){
				result.setCode(1);
				logger.info("参数问题，添加品牌出错！");
			}
		} catch (Exception e) {
			logger.error("添加品牌出错！", e);
			result.setCode(1);
		}
		return result;
	}

	

	@Override
	public Result deleteBrand(int id) throws TException {
		Result result = new Result();
		result.setCode(0);
		
		try{
			brandSvcImpl.deleteBrand(id);
		}catch(Exception e){
			logger.error("删除品牌出错！", e);
			result.setCode(1);
		}
		return result;
	}

	@Override
	public Result updateBrand(BrandInfo brand) throws TException {
		Result result = new Result();
		result.setCode(0);
		try {
			int ret = brandSvcImpl.updateBrand(brand);
			if(ret!=0){
				result.setCode(1);
				logger.info("参数问题，修改品牌出错！");
			}
		} catch (Exception e) {
			logger.error("修改品牌出错！", e);
			result.setCode(1);
		}
		return result;
	}

	@Override
	public BrandResult queryBySubject(int id) throws TException {
		BrandResult brandResult = new BrandResult();
		Result result = new Result();
		result.setCode(0);
		brandResult.setResult(result);
		try {
			List<BrandInfo> brands = brandSvcImpl.queryBySubject(id);
			brandResult.setBrandInfo(brands);
		} catch (Exception e) {
			logger.error("查询品牌出错！", e);
			result.setCode(1);
		}

		return brandResult;
	}
	
	
}
