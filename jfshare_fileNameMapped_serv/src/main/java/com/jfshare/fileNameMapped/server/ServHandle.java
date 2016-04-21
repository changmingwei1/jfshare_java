package com.jfshare.fileNameMapped.server;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfshare.fileNameMapped.service.FileNameMappedService;
import com.jfshare.fileNameMapped.util.FailCode;
import com.jfshare.fileNameMapped.util.ResultUtil;
import com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedInfo;
import com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedResult;
import com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedServ;
import com.jfshare.finagle.thrift.fileNameMapped.QueryParam;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;

@Service(value="handler")
public class ServHandle implements FileNameMappedServ.Iface {
	
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	
	@Autowired
    private FileNameMappedService fileNameMappedService;
	
	/**
	 * 	方法描述:  保存文件映射关系
	 *
	 *	@see com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedServ.Iface#saveFileNameMappedInfo(com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedInfo)
	 *  @author : ljx 创建时间 2015年11月29日 下午10:51:16
	 */
	@Override
	public StringResult saveFileNameMappedInfo(FileNameMappedInfo fileNameMappedInfo)
			throws TException {
		logger.info(fileNameMappedInfo.toString());
		
        StringResult stringResult = null;
        String filename = "";
        try {
        	
        	//参数验证
        	if(StringUtils.isBlank(fileNameMappedInfo.getFilename())
        			|| StringUtils.isBlank(fileNameMappedInfo.getFileid())){
                stringResult = ResultUtil.createNormalFailStringResult(FailCode.PARAM_ERROR);
                logger.warn(FailCode.PARAM_ERROR.getDesc());
        	}else{
            	//保存记录
            	filename = fileNameMappedService.saveFileNameMappedInfo(fileNameMappedInfo);
                stringResult = ResultUtil.createNormalStringResult(filename);
        	}
        	
        } catch (Exception e) {
            stringResult = ResultUtil.createNormalFailStringResult(FailCode.SYSTEM_EXCEPTION);
            logger.error(FailCode.SYSTEM_EXCEPTION.getDesc(), e);
        }

        return stringResult;
        
	}
	
	/**
	 * 	方法描述:  查询文件映射关系
	 *
	 *	@see com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedServ.Iface#queryFileNameMapped(com.jfshare.finagle.thrift.fileNameMapped.QueryParam)
	 *  @author : ljx 创建时间 2015年11月29日 下午10:51:26
	 */
	@Override
	public FileNameMappedResult queryFileNameMapped(QueryParam queryParam)
			throws TException {
		logger.info(queryParam.toString());
		FileNameMappedResult fileNameMappedResult = new FileNameMappedResult();
		Result result = new Result();
		result.setCode(0);
		fileNameMappedResult.setResult(result);

		try {
			FileNameMappedInfo fileNameMappedInfo = fileNameMappedService.queryFileNameMapped(queryParam.getFilename());
			fileNameMappedResult.setFileNameMappedInfo(fileNameMappedInfo);
		} catch (Exception e) {
			logger.error("查询品牌出错！", e);
			result.setCode(1);
		}

		return fileNameMappedResult;
	}


}
