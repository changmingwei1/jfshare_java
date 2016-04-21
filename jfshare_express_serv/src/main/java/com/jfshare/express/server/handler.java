package com.jfshare.express.server;


import com.jfshare.express.Util.CommUtil;
import com.jfshare.express.Util.ConvertUtil;
import com.jfshare.express.Util.FailCode;
import com.jfshare.express.db.redis.IExpressJedis;
import com.jfshare.express.impl.ExpressImpl;
import com.jfshare.express.kuaidi100.KuaiDiManager;
import com.jfshare.express.kuaidi100.pojo.TaskRequest;
import com.jfshare.finagle.thrift.express.*;
import com.jfshare.finagle.thrift.expressstat.ExpressWayInfo;
import com.jfshare.finagle.thrift.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 */

@Service(value = "handler")
public class handler implements ExpressServ.Iface {
    private Logger logger = LoggerFactory.getLogger(handler.class);

    @Autowired
    @Qualifier("expressImpl")
    private ExpressImpl expressImpl;
    
	@Autowired
	private IExpressJedis expressJedisImpl;

	@Autowired
	private KuaiDiManager kuaiDiManager;
	
//	@Autowired
//	private ExpressStatClient expressStatClient;

    public ExpressImpl getProductImpl() {
        return expressImpl;
    }

    public void setProductImpl(ExpressImpl expressImpl) {
        this.expressImpl = expressImpl;
    }

    //@Override
    public com.jfshare.finagle.thrift.result.StringResult addExpress(ExpressInfo info) throws TException {
        logger.info("addExpress...");
        logger.info(info.toString());

        com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
        List<com.jfshare.finagle.thrift.result.FailDesc> failDescList = new ArrayList<com.jfshare.finagle.thrift.result.FailDesc>();
        com.jfshare.finagle.thrift.result.StringResult stringResult = new com.jfshare.finagle.thrift.result.StringResult();
        stringResult.setResult(result);
        info.setId(-1); //for valid
        if (!validExpress(info, result, failDescList)) {
        	return stringResult;
        }
        
        com.jfshare.finagle.thrift.result.StringResult sResult = this.expressImpl.add(info);
        
        //add express && express prefix && sync expressNotify
        if (sResult.getResult().getCode() == 0) {
        	info.setId(Integer.parseInt(sResult.getValue()));
        	List<ExpressInfo> curExpress = new ArrayList<ExpressInfo>();
        	curExpress.add(info);
        	expressJedisImpl.setExpressPrefix(curExpress);
        	
        	 if (expressJedisImpl.existsExpressInfo()) {
        		 expressJedisImpl.setExpressInfo(curExpress);
             } else {
                 List<ExpressInfo> expressInfoList = this.expressImpl.query();
                 expressJedisImpl.setExpressInfo(expressInfoList);
             }
        	 
        	 if (info.getGrabState() == 1) {
	        	 ExpressWayInfo expressWayInfo = new ExpressWayInfo();
	             expressWayInfo.setCompanyKey(info.getId()+"");
	             expressWayInfo.setCompanyName(info.getName());
	             expressWayInfo.setKuaidiKey(info.getKuaidiKey());
//	             int ret = this.expressStatClient.syncExpressWayInfo(expressWayInfo);
//	             if (ret < 0) {
//	            	 logger.error("物流id="+info.getId()+"同步至物流抓取失败！");
//	             }
        	 }
        	 
        	 
        	 //删除精简物流信息缓存
        	 expressJedisImpl.removeAllExpressSimple();
        }

        return sResult;
    }

    //@Override
    public com.jfshare.finagle.thrift.result.Result updateExpress(ExpressInfo info) throws TException {
        logger.info("updateExpress...");
        logger.info(info.toString());
        com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
        List<com.jfshare.finagle.thrift.result.FailDesc> failDescList = new ArrayList<com.jfshare.finagle.thrift.result.FailDesc>();
        if (!validExpress(info, result, failDescList)) {
        	return result;
        }
        
        ExpressInfo oldInfo = this.queryById(info.getId());
        if (this.expressImpl.update(info)) {
            result.setCode(0);
            
            //del old express prefix
        	expressJedisImpl.removeExpressPrefix(oldInfo);
        	if (info.getStatus() == 1) {
        		//update express prefix
        	    List<ExpressInfo> curExpress = new ArrayList<ExpressInfo>();
        	    curExpress.add(info);
        	    expressJedisImpl.setExpressPrefix(curExpress);

	        	//update express
	       	    if (expressJedisImpl.existsExpressInfo()) {
	       		   expressJedisImpl.setExpressInfo(curExpress);
	            } else {
	                List<ExpressInfo> expressInfoList = this.expressImpl.query();
	                expressJedisImpl.setExpressInfo(expressInfoList);
	            }
        	} else if (oldInfo.getStatus() == 1) {
        		//del old express
           	    if (expressJedisImpl.existsExpressInfo()) {
           	    	expressJedisImpl.removeExpress(oldInfo);
                } 
        	}
       	    
       	    //sync expressNotify
       	    if (info.getGrabState() == 1) {
			   	 ExpressWayInfo expressWayInfo = new ExpressWayInfo();
			     expressWayInfo.setCompanyKey(info.getId()+"");
			     expressWayInfo.setCompanyName(info.getName());
			     expressWayInfo.setKuaidiKey(info.getKuaidiKey());
//			     int ret = this.expressStatClient.syncExpressWayInfo(expressWayInfo);
//			     if (ret < 0) {
//			    	 logger.error("物流id="+info.getId()+"同步至物流抓取失败！");
//			     }
       	    }
       	    
       	    //删除精简物流信息缓存
       	    expressJedisImpl.removeAllExpressSimple();
        	
        } else {
            result.setCode(1);
        }
        return result;
    }

    /**
     * 物流校验
     * @param info
     * @param result
     * @param failDescList
     * @return
     */
	private boolean validExpress(ExpressInfo info, com.jfshare.finagle.thrift.result.Result result,
			List<com.jfshare.finagle.thrift.result.FailDesc> failDescList) {
		if (!FailCode.isEmpty(info.getName())) {
            failDescList.add(FailCode.nameIsEmpty);
        }
        if (!FailCode.isEmpty(info.getQueryUrl())) {
            failDescList.add(FailCode.queryUrlIsEmpty);
        }
        if (!FailCode.isEmpty(info.getNameRule())) {
        	failDescList.add(FailCode.nameRuleIsEmpty);
        }
        if (!FailCode.isEmpty(info.getId())) {
            failDescList.add(FailCode.expressIsEmpty);
        }
        if (info.getGrabState() == 1 && !FailCode.isEmpty(info.getKuaidiKey())) {
        	failDescList.add(FailCode.kuaidiKeyIsEmpty);
        }
        if (info.getTypeState() < 0) {
        	failDescList.add(FailCode.typeStateIsEmpty);
        }
        if (info.getUserType() < 0) {
        	failDescList.add(FailCode.userTypeIsEmpty);
        }
        if (info.getSerialNum() <= 0) {
        	failDescList.add(FailCode.serialNumInvalid);
        }
        if (!failDescList.isEmpty()) {
            result.setCode(1);
            result.setFailDescList(failDescList);
            return false;
        }
        
        //字段查重
        failDescList = this.expressImpl.checkExpressInfo(info);        
        if (!failDescList.isEmpty()) {
            result.setCode(1);
            result.setFailDescList(failDescList);
            return false;
        }
        
        return true;
	}
    
    /**	
     * 启用停用物流
     * @param id
     * @param status
     * @return
     */
    public com.jfshare.finagle.thrift.result.Result updateStatus(int id, int status) {
    	com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
    	List<com.jfshare.finagle.thrift.result.FailDesc> failInfos = new ArrayList<com.jfshare.finagle.thrift.result.FailDesc>();
    	if (id <= 0 || status <= 0) {
    		result.setCode(1);
    		 com.jfshare.finagle.thrift.result.FailDesc failDesc = new com.jfshare.finagle.thrift.result.FailDesc();
			 failDesc.setDesc("参数有误");
			 failInfos.add(failDesc);
			 result.setFailDescList(failInfos);
    		return result;
    	}
    	try {
    		result.setCode(0);
			ExpressInfo info = queryById(id);
			if (status != info.getStatus()) {
				//启用
				if (status == CommUtil.ExpressStatus.USE.getEnumVal()) {
				   if (!validExpress(info, result, failInfos)) {
			        	return result;
			       }
			       info.setStatus(status);
				   expressImpl.update(info);
		           List<ExpressInfo> curExpress = new ArrayList<ExpressInfo>();
		           curExpress.add(info);
			       expressJedisImpl.setExpressInfo(curExpress);
				} else if (status == CommUtil.ExpressStatus.STOP.getEnumVal()) {
				   info.setStatus(status);
				   expressImpl.update(info);
	           	   expressJedisImpl.removeExpress(info);
	           	   expressJedisImpl.removeExpressPrefix(info);
				}
				
				//删除精简物流信息缓存
	       	    expressJedisImpl.removeAllExpressSimple();
			}
		} catch (TException e) {
			result.setCode(1);
   		    com.jfshare.finagle.thrift.result.FailDesc failDesc = new com.jfshare.finagle.thrift.result.FailDesc();
			failDesc.setDesc("系统异常");
			failInfos.add(failDesc);
			result.setFailDescList(failInfos);
			logger.error("启用停用失败!", e);
		}
    	
    	return result;
    }

	@Override
	public Result subscribeExpressPost(SubscribeReq req) throws TException {
		if(StringUtils.isBlank(req.getCompany()) && req.getComId() > 0) {
			req.setCompany(this.queryById(req.getComId()).getKuaidiKey());
		}
		TaskRequest taskRequest = ConvertUtil.toTaskRequest(req);
		Result result = null;
		if(taskRequest == null) {

			result = new Result(1);
			result.addToFailDescList(FailCode.PARAMS_INVALID);
		} else {

			result = kuaiDiManager.subscribe(taskRequest, req.getOrderId());
		}
		return result;
	}

	@Override
	public Result subscribeExpressRecord(SubscribeRes res) throws TException {
		Result result = new Result(0);
		try {
			kuaiDiManager.record(res);
		} catch (Exception e) {
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
			logger.error("subscribeExpressRecord ==> 更新物流跟踪信息时发生系统异常!", e);
		}
		return result;
	}

	@Override
	public ExpressResult expressQuery(ExpressParams params) throws TException {
		ExpressResult expressResult = new ExpressResult();
		expressResult.setResult(new Result(0));
		int queryType = params.getQueryType();
		try {
			if((queryType & 1) > 0) {
                //快递公司信息
                if(params.getComId() <= 0) {
                    expressResult.getResult().setCode(1);
                    expressResult.getResult().addToFailDescList(FailCode.PARAMS_INVALID);
                    return expressResult;
                }
                expressResult.setExpressInfo(this.queryById(params.getComId()));
            }

			if((queryType & 2) > 0) {
                //物流跟踪信息
                if(StringUtils.isBlank(params.getOrderId())) {
                    expressResult.getResult().setCode(1);
                    expressResult.getResult().addToFailDescList(FailCode.PARAMS_INVALID);
                    return expressResult;
                }
                ExpressTrace expressTrace = kuaiDiManager.queryTrace(params.getOrderId());
                expressResult.setExpressTrace(expressTrace);
            }
		} catch (TException e) {
			expressResult.getResult().setCode(1);
			expressResult.getResult().addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}

		return expressResult;
	}

	//@Override
    public ExpressInfoResult query() throws TException {
        logger.info("query...");
        List<ExpressInfo> expressInfoList = new ArrayList<ExpressInfo>();
        if (expressJedisImpl.existsExpressInfo()) {
        	expressInfoList = expressJedisImpl.query();
        } else {
            expressInfoList = this.expressImpl.query();
            expressJedisImpl.setExpressInfo(expressInfoList);
        }
        
        com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
        result.setCode(0);
        ExpressInfoResult expressInfoResult = new ExpressInfoResult();
        expressInfoResult.setResult(result);
        expressInfoResult.setExpressInfoList(expressInfoList);
        return expressInfoResult;
    }
    
    public ExpressInfoResult queryEx() throws TException {
        logger.info("query...");
        List<ExpressInfo> expressInfoList = new ArrayList<ExpressInfo>();
        if (expressJedisImpl.existsExpressSimpleInfo()) {
        	expressInfoList = expressJedisImpl.queryExpressSimpleInfo();
        } else {
            expressInfoList = this.expressImpl.queryExpressSimpleInfo();
            expressJedisImpl.setExpressSimpleInfo(expressInfoList);
        }
        
        com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
        result.setCode(0);
        ExpressInfoResult expressInfoResult = new ExpressInfoResult();
        expressInfoResult.setResult(result);
        expressInfoResult.setExpressInfoList(expressInfoList);
        return expressInfoResult;
    }

    //@Override
    public ExpressInfo queryById(int id) throws TException {
    	logger.info("queryById... id=" + id);
	    ExpressInfo expressInfo = expressJedisImpl.queryById(id);
	    return expressInfo == null ? this.expressImpl.queryById(id) : expressInfo;
    }

    //@Override
    public com.jfshare.finagle.thrift.result.Result deleteExpress(int id) throws TException {
        com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
        ExpressInfo oldInfo = this.queryById(id);
        if (this.expressImpl.deleteExpress(id)) {
            result.setCode(0);
            
            //del express prefix
        	expressJedisImpl.removeExpressPrefix(oldInfo);
        	//del express
       	    if (expressJedisImpl.existsExpressInfo()) {
       	    	expressJedisImpl.removeExpress(oldInfo);
            } else {
                List<ExpressInfo> expressInfoList = this.expressImpl.query();
                expressJedisImpl.setExpressInfo(expressInfoList);
            }
       	    
       	    //删除精简物流信息缓存
       	    expressJedisImpl.removeAllExpressSimple();
        	
        } else {
            result.setCode(1);
        }
        return result;
    }
    
    public ExpressInfoPaginate queryByConditions(ExpressQueryConditions conditions) throws TException {
    	logger.info("queryByConditions...");
    	 com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
    	 ExpressInfoPaginate expressInfoPaginate = new ExpressInfoPaginate();
    	 List<ExpressInfo> expressInfoList = new ArrayList<ExpressInfo>();
        
    	try {
    		int pagesize = conditions.getPageSize()==0 ? 10 : conditions.getPageSize();
    		int total = this.expressImpl.queryCountByConditions(conditions);
    		int pageCount = 0;
    		if (total > 0) {
    			pageCount = total%pagesize == 0 ? total/pagesize : total/pagesize + 1;
	    	    expressInfoList = this.expressImpl.queryByConditions(conditions);
    		}
	    	result.setCode(0);
	    	expressInfoPaginate.setTotal(total);
	    	expressInfoPaginate.setPageCount(pageCount);
	    	expressInfoPaginate.setExpressInfoList(expressInfoList);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		logger.error("queryByConditions查询物流信息出错！" + conditions.toString());
    		result.setCode(1);
    	}
    	
    	expressInfoPaginate.setResult(result);
    	 
        return expressInfoPaginate;
	}
    
	public ExpressInfoResult queryBatch(List<Integer> idList) throws TException {
         ExpressInfoResult expressInfoResult = new ExpressInfoResult();
		 List<ExpressInfo> expressInfoList = new ArrayList<ExpressInfo>();
		 List<com.jfshare.finagle.thrift.result.FailDesc> failInfos = new ArrayList<com.jfshare.finagle.thrift.result.FailDesc>();
		 com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
         result.setCode(0);
         try {
        	 List<String> validIdsList = new ArrayList<String>();
        	 for (Integer id : idList) {
         		if (id==null || id <= 0) continue;
 				validIdsList.add(id.toString());
 			 }
        	 
        	 List<ExpressInfo> validExress = this.expressJedisImpl.queryByIds(validIdsList);
        	 expressInfoList.addAll(validExress);
        	 Set<Integer> validSet = new HashSet<Integer>();
        	 for (ExpressInfo item : validExress) {
        		 validSet.add(Integer.valueOf(item.getId()));
			 }
        	 List<Integer> delIdsList = new ArrayList<Integer>();
        	 for (Integer id : idList) {
        		if (id==null || id <= 0) continue;
				if (!validSet.contains(id)) {
					delIdsList.add(id);
				}
			 }
        	 if (delIdsList.size() > 0) {
        		 expressInfoList.addAll(this.expressImpl.queryByIds(delIdsList));
        	 }
         } catch (Exception e) {
        	 logger.error("查询queryBatch失败！", e);
        	 result.setCode(1);
    		 com.jfshare.finagle.thrift.result.FailDesc failDesc = new com.jfshare.finagle.thrift.result.FailDesc();
			 failDesc.setDesc("系统异常");
			 failInfos.add(failDesc);
			 result.setFailDescList(failInfos);
         }
         expressInfoResult.setResult(result);
         expressInfoResult.setExpressInfoList(expressInfoList);
         
         return expressInfoResult;
	}
	
    /**
     * 获取货运名称
     * @return
     */
    public ExpressInfoResult queryFreight() {
    	 ExpressInfoResult expressInfoResult = new ExpressInfoResult();
		 List<ExpressInfo> expressInfoList = new ArrayList<ExpressInfo>();
		 List<com.jfshare.finagle.thrift.result.FailDesc> failInfos = new ArrayList<com.jfshare.finagle.thrift.result.FailDesc>();
		 com.jfshare.finagle.thrift.result.Result result = new com.jfshare.finagle.thrift.result.Result();
         result.setCode(0);
         try {
        	 expressInfoList = this.expressImpl.queryFreight();
         } catch (Exception e) {
        	 logger.error("查询queryFreight失败！", e);
        	 result.setCode(1);
    		 com.jfshare.finagle.thrift.result.FailDesc failDesc = new com.jfshare.finagle.thrift.result.FailDesc();
			 failDesc.setDesc("系统异常");
			 failInfos.add(failDesc);
			 result.setFailDescList(failInfos);
         }
         expressInfoResult.setResult(result);
         expressInfoResult.setExpressInfoList(expressInfoList);
         
         return expressInfoResult;
	}

	@Override
	public ExpressInfoResult queryBySeller(int sellerId) throws TException {
		return null;
	}
}
