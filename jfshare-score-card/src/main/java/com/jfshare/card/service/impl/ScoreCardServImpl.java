package com.jfshare.card.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfshare.card.dao.mysql.BatchActivityDao;
import com.jfshare.card.dao.mysql.BatchCardDao;
import com.jfshare.card.dao.mysql.BatchRechargeCardDao;
import com.jfshare.card.model.TbBatchActivity;
import com.jfshare.card.model.TbBatchActivityExample;
import com.jfshare.card.model.TbBatchActivityExample.Criteria;
import com.jfshare.card.model.TbBatchCardsRecord;
import com.jfshare.card.model.TbBatchCardsRecordExample;
import com.jfshare.card.model.TbBatchRechargeRecord;
import com.jfshare.card.model.TbBatchRechargeRecordExample;
import com.jfshare.card.model.mapper.ex.TbBatchActivityEx;
import com.jfshare.card.server.depend.BuyerClient;
import com.jfshare.card.server.depend.ScoreClient;
import com.jfshare.card.service.IScoreCardServ;
import com.jfshare.card.util.Constants;
import com.jfshare.card.util.DateUtils;
import com.jfshare.card.util.FailCode;
import com.jfshare.card.util.FileUtil;
import com.jfshare.card.util.ScoreCardUtil;
import com.jfshare.card.util.ScoreCartCommons;
import com.jfshare.card.util.TypeStatEnum;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.finagle.thrift.buyer.BuyerResult;
import com.jfshare.finagle.thrift.buyer.LoginLog;
import com.jfshare.finagle.thrift.card.ActivityBatchResult;
import com.jfshare.finagle.thrift.card.ActivityBean;
import com.jfshare.finagle.thrift.card.ActivityQueryParam;
import com.jfshare.finagle.thrift.card.ActivityResult;
import com.jfshare.finagle.thrift.card.BatchActivity;
import com.jfshare.finagle.thrift.card.BatchCardRecord;
import com.jfshare.finagle.thrift.card.BatchRechargeCardRecord;
import com.jfshare.finagle.thrift.card.CardBatchResult;
import com.jfshare.finagle.thrift.card.CardQueryParam;
import com.jfshare.finagle.thrift.card.CardRecordBatchResult;
import com.jfshare.finagle.thrift.card.CardRecordResult;
import com.jfshare.finagle.thrift.card.CardResult;
import com.jfshare.finagle.thrift.card.DirectRechargeResult;
import com.jfshare.finagle.thrift.card.ExcelExportResult;
import com.jfshare.finagle.thrift.card.InvalidOneActivityResult;
import com.jfshare.finagle.thrift.card.RechargeParam;
import com.jfshare.finagle.thrift.card.ToRechargeParams;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.DateUtil;
import com.jfshare.utils.StringUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * server实现类 chiwenheng 2016-08-12-29
 */
@Service
public class ScoreCardServImpl implements IScoreCardServ {

	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());
	private String localPath = PropertiesUtil.getProperty(ScoreCartCommons.APP_KEY, "womai_product_import_path");

	@Autowired
	private BatchActivityDao batchActivityDao;
	@Autowired
	private BatchCardDao batchCardDao;
	@Autowired
	private BatchRechargeCardDao batchRechargeCardDao;
	@Autowired
	private ScoreClient scoreClient;
	@Autowired
	private BuyerClient buyerClient;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public void createOneActivity(ActivityResult retResult, ActivityBean entity) {

		try {

			TbBatchActivity bean = new TbBatchActivity();
			bean.setName(entity.getName());
			bean.setMultiRechargeEnable(entity.getMultiRechargeEnable());
			bean.setTotalCount(entity.getTotalCount());
			bean.setRechargeType(entity.getRechargeType());
			bean.setCurStatus("0");// 默认状态未作废
			bean.setStartTime(new DateTime(DateUtil.parseDate(entity.getStartTime())));
			bean.setEndTime(new DateTime(DateUtil.parseDate(entity.getEndTime())));
			bean.setPieceValue(Integer.parseInt(entity.getPieceValue()));
			bean.setCreateTime(new DateTime());

			batchActivityDao.insertOne(bean);

		} catch (Exception e) {
			e.printStackTrace();
			FailCode.addFails(retResult.getResult(), FailCode.repquestParamError);

		}

	}

	@Override
	public void queryActivities(ActivityBatchResult retResult, ActivityQueryParam param, Pagination pagination) {

		List<BatchActivity> activityList = new ArrayList<BatchActivity>();
		retResult.setActivityList(activityList);
		Pagination page = new Pagination();
		page.setTotalCount(0);
		page.setPageNumCount(0);
		retResult.setPagination(page);
		//result.get
		try {
			SqlSession Session = sqlSessionFactory.openSession();
			
			Map<String,String> map = new HashMap<String,String>();
			
			String nowTime = DateUtils.getNowDateTimeString();
			
			System.out.println(nowTime);
			map.put("name",param.getName());
			map.put("curStatus",param.getCurStatus());
			
			map.put("minStartTime", param.getMinStartTime());
			map.put("maxStartTime", param.getMaxStartTime());
			
			map.put("maxEndTime", param.getMaxEndTime());
			map.put("minEndTime", param.getMinEndTime());
			
			map.put("minPieceValue", param.getMinPieceValue());
			map.put("maxPieceValue", param.getMaxPieceValue());
			
			map.put("nowTime", nowTime);
			
			int offset = pagination.getCurrentPage();// 从1开始
			int numPerPage = pagination.getNumPerPage();
			
			PageHelper.startPage(offset, numPerPage);
			
			//
			List<TbBatchActivityEx> list = batchActivityDao.queryByParams(map);
			// 用PageInfo对结果进行包装
			PageInfo pager = new PageInfo(list);
			page.setTotalCount((int) pager.getTotal());
			page.setPageNumCount(pager.getPages());
			page.setCurrentPage(pagination.getCurrentPage());
			page.setNumPerPage(pagination.getNumPerPage());
			for (int i = 0; i < list.size(); i++) {
				String curStatus = "1";
				TbBatchActivityEx tbBatchActivityEx = list.get(i);
				/**
				 * <!-- 可用 -->
		<if test="curStatus == 2">
			<![CDATA[AND act.end_time >=#{nowTime} AND act.cur_status != '1']]>
		</if>
		<!-- 过期 -->
		<if test="curStatus == 3">
			<![CDATA[AND act.end_time <=#{nowTime} AND act.cur_status != '1']]>
		</if>
				 */
				if (!"1".equals(tbBatchActivityEx.getCurStatus())) {
					if (tbBatchActivityEx.getEndTime().isAfterNow()) {
						curStatus = "2";
					}else if(tbBatchActivityEx.getEndTime().isBeforeNow()){
						curStatus="3";
					}
				}
				BatchActivity batchActivity = new BatchActivity();
				batchActivity.setCreateTime(tbBatchActivityEx.getCreateTime().toString("yyyy-MM-dd HH:mm:ss"));
				batchActivity.setCurStatus(curStatus);
				batchActivity.setName(tbBatchActivityEx.getName());
				batchActivity.setEndTime(tbBatchActivityEx.getEndTime().toString("yyyy-MM-dd"));
				batchActivity.setId(tbBatchActivityEx.getId());
				batchActivity.setMultiRechargeEnable(tbBatchActivityEx.getMultiRechargeEnable());
				batchActivity.setPieceValue(tbBatchActivityEx.getPieceValue()+"");
				batchActivity.setRechargeType(tbBatchActivityEx.getRechargeType());
				batchActivity.setStartTime(tbBatchActivityEx.getStartTime().toString("yyyy-MM-dd"));
				batchActivity.setTotalCount(tbBatchActivityEx.getTotalCount());
				batchActivity.setUsedCount(tbBatchActivityEx.getUsedCount());
				batchActivity.setSendCount(tbBatchActivityEx.getSendsCount());
				retResult.getActivityList().add(batchActivity);
			}
		} catch (Exception e) {
			logger.info("查询活动列表发生异常--------------》"+e);
		}
		
	}

	@Override
	public void queryActivityById(ActivityResult retResult, int activityId) {

		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);

		TbBatchActivity source = batchActivityDao.queryById(activityId);
		BatchActivity target = new BatchActivity();
		BeanUtils.copyProperties(source, target);
		target.setStartTime(ScoreCardUtil.dateTime2Str(source.getStartTime()));
		target.setEndTime(ScoreCardUtil.dateTime2Str(source.getEndTime()));
		target.setPieceValue(source.getPieceValue() + "");
		retResult.setActivity(target);

	}

	@Override
	public void queryCards(CardBatchResult retResult, int activityId, CardQueryParam param, Pagination pagination) {

		TbBatchCardsRecordExample bean = new TbBatchCardsRecordExample();
		TbBatchCardsRecordExample.Criteria criteria = bean.createCriteria();
		bean.setOrderByClause("card_name asc");
		if (!StringUtil.isNullOrEmpty(activityId)) {
			criteria.andActivityIdEqualTo(activityId);
		}

		if (param != null) {
			if (!StringUtil.isNullOrEmpty(param.getSendStatus())) {
				criteria.andSendStatusEqualTo(param.getSendStatus());
			}
			if (!StringUtil.isNullOrEmpty(param.getRechargeStatus())) {
				criteria.andRechargeStatusEqualTo(param.getRechargeStatus());
			}
			if (!StringUtil.isNullOrEmpty(param.getCardName())) {
				criteria.andCardNameLike(param.getCardName());
			}
			if (!StringUtil.isNullOrEmpty(param.getRechargeAccount())) {
				criteria.andRechargeAccountLike(param.getRechargeAccount() );
			}
			if (!StringUtil.isNullOrEmpty(param.getCardPsd())) {
				String encryPsd = ScoreCardUtil.encrytCardPsd(param.getCardPsd());// 先加密
																					// 在查询
				criteria.andCardPsdEqualTo(encryPsd);
			}
		}

		int offset = pagination.getCurrentPage();// 从1开始
		int numPerPage = pagination.getNumPerPage();

		PageHelper.startPage(offset, numPerPage);
		List<TbBatchCardsRecord> templist = batchCardDao.queryByParams(bean);
		// 用PageInfo对结果进行包装
		PageInfo page = new PageInfo(templist);
		Pagination p=new Pagination();
		retResult.setPagination(p);
		retResult.getPagination().setTotalCount((int) page.getTotal());
		retResult.getPagination().setPageNumCount(page.getPages());
		retResult.getPagination().setCurrentPage(pagination.getCurrentPage());
		retResult.getPagination().setNumPerPage(pagination.getNumPerPage());


		List<BatchCardRecord> retList = new ArrayList<BatchCardRecord>();
		for (TbBatchCardsRecord source : templist) {
			source.setCardPsd(ScoreCardUtil.decryptPsd(source.getCardPsd()));// 针对卡号
																				// 进行解密
			String temp = source.getCardPsd();
			source.setCardPsd(temp.replace(temp.substring(2, 6), "****"));// 将中间的部分替换成**
			BatchCardRecord target = new BatchCardRecord();
			BeanUtils.copyProperties(source, target);
			if(source.getRechargeTime()!=null){
				target.setRechargeTime(ScoreCardUtil.dateTime2Str(source.getRechargeTime()));
			}
			retList.add(target);

		}

		retResult.setCardList(retList);

	}

	@Override
	public void queryCardById(CardResult retResult, int cardId) {

		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);

		TbBatchCardsRecord source = batchCardDao.queryById(cardId);

		BatchCardRecord target = new BatchCardRecord();
		BeanUtils.copyProperties(source, target);
		target.setRechargeTime(ScoreCardUtil.dateTime2Str(source.getRechargeTime()));
		retResult.setCard(target);

	}

	@Override
	public void queryRechargeCards(CardRecordBatchResult retResult, int userId,
			Pagination pagination) {

		TbBatchRechargeRecordExample bean = new TbBatchRechargeRecordExample();
		TbBatchRechargeRecordExample.Criteria criteria= bean.createCriteria();
		bean.setOrderByClause("recharge_time desc");
		criteria.andUserIdEqualTo(userId);


		int offset = pagination.getCurrentPage();// 从1开始
		int numPerPage = pagination.getNumPerPage();

		PageHelper.startPage(offset, numPerPage);
		List<TbBatchRechargeRecord> templist = batchRechargeCardDao.queryByParams(bean);

		// 用PageInfo对结果进行包装
		PageInfo page = new PageInfo(templist);
		Pagination p=new Pagination();
		retResult.setPagination(p);
		retResult.getPagination().setTotalCount((int) page.getTotal());
		retResult.getPagination().setPageNumCount(page.getPages());
		retResult.getPagination().setCurrentPage(pagination.getCurrentPage());
		retResult.getPagination().setNumPerPage(pagination.getNumPerPage());


		List<BatchRechargeCardRecord> retList = new ArrayList<BatchRechargeCardRecord>();
		for (TbBatchRechargeRecord source : templist) {
			BatchRechargeCardRecord target = new BatchRechargeCardRecord();
			BeanUtils.copyProperties(source, target);
			target.setRechargeTime(ScoreCardUtil.dateTime2Str(source.getRechargeTime()));
			retList.add(target);

		}
		retResult.setRechargeCardRecordList(retList);

	}

	@Override
	public void queryRechargeCardById(CardRecordResult retResult, int rechargeCardId) {

		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);

		TbBatchRechargeRecord source = batchRechargeCardDao.queryById(rechargeCardId);

		BatchRechargeCardRecord target = new BatchRechargeCardRecord();
		BeanUtils.copyProperties(source, target);
		target.setRechargeTime(ScoreCardUtil.dateTime2Str(source.getRechargeTime()));
		retResult.setRechargeCardRecord(target);

	}

	@Override
	public void exportExcelByqueryCards(ExcelExportResult retResult, int activityId, CardQueryParam param, String psd) {

		if (!validataPassword(psd)) {//密码未校验通过

			FailCode.addFails(retResult.getResult(), FailCode.repquestParamError);

			return;

		}

		TbBatchCardsRecordExample bean = new TbBatchCardsRecordExample();
		TbBatchCardsRecordExample.Criteria criteria = bean.createCriteria();

		if(StringUtil.isNullOrEmpty(activityId)){// 未指定activityId
			FailCode.addFails(retResult.getResult(), FailCode.ActivityTypeExportExcelErrorWithoutActivityId);
			return;
		}

		criteria.andActivityIdEqualTo(activityId);// 批次活动的ID

		if (param != null) {
			if (!StringUtil.isNullOrEmpty(param.getSendStatus())) {
				criteria.andSendStatusEqualTo(param.getSendStatus());
			}
			if (!StringUtil.isNullOrEmpty(param.getRechargeStatus())) {
				criteria.andRechargeStatusEqualTo(param.getRechargeStatus());
			}
			if (!StringUtil.isNullOrEmpty(param.getCardName())) {
				criteria.andCardNameLike("%" + param.getCardName() + "%");
			}
			if (!StringUtil.isNullOrEmpty(param.getRechargeAccount())) {
				criteria.andRechargeAccountLike("%" + param.getRechargeAccount() + "%");
			}
			if (!StringUtil.isNullOrEmpty(param.getCardPsd())) {
				String encryPsd = ScoreCardUtil.encrytCardPsd(param.getCardPsd());// 先加密
																					// 在查询
				criteria.andCardPsdEqualTo(encryPsd);
			}
		}

		TbBatchActivityExample beanActivity=new TbBatchActivityExample();
		TbBatchActivityExample.Criteria criteria1=beanActivity.createCriteria();
		criteria1.andIdEqualTo(activityId);//  查询activity的名称
		List<TbBatchActivity> tempActivityList=batchActivityDao.queryByParams(beanActivity);
		if(tempActivityList==null || tempActivityList.size()!=1){// 查询批次活动,系统异常
			FailCode.addFails(retResult.getResult(), FailCode.ActivityTypeExportExcelError);
			return;
		}



		List<TbBatchCardsRecord> templist = batchCardDao.queryByParams(bean);
		for (TbBatchCardsRecord card : templist) {
			String beforeStr = card.getCardPsd();
			System.out.println(" ------------------------ "+beforeStr);
			card.setCardPsd(ScoreCardUtil.decryptPsd(beforeStr));// 针对卡号 进行解密
		}

		TbBatchActivity batchActivity=tempActivityList.get(0);

		String path = ScoreCardUtil.excelExportRedbag(batchActivity.getName(),templist);
		if(StringUtil.isNullOrEmpty(path)){// 导出excel 时候 ,系统异常
			FailCode.addFails(retResult.getResult(), FailCode.ActivityTypeExportExcelError);
			return;
		}
		retResult.setPath(path);

	}

	@Override
	@Transactional
	public StringResult recharge(StringResult stringResult, RechargeParam param, String mobile) throws Exception{
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);
		// 根据卡号查询活动id
		TbBatchCardsRecordExample tbBatchCardsRecordExample = new TbBatchCardsRecordExample();
		com.jfshare.card.model.TbBatchCardsRecordExample.Criteria createCriteria = tbBatchCardsRecordExample
				.createCriteria();
		createCriteria.andCardNameEqualTo(param.getCardName());
		List<TbBatchCardsRecord> tbBatchCardsRecords = batchCardDao.queryByParams(tbBatchCardsRecordExample);
		if (!StringUtil.isNullOrEmpty(tbBatchCardsRecords) && tbBatchCardsRecords.size() <= 0) {
			FailCode.addFails(stringResult.getResult(), FailCode.CARD_PWD_ERROR);
			return stringResult;
		}
		TbBatchCardsRecord tbBatchCardsRecord = tbBatchCardsRecords.get(0);
		// 卡密是否正确
		String pString = ScoreCardUtil.decryptPsd(tbBatchCardsRecord.getCardPsd());
		if (!param.getCardPsd().equals(pString)) {
			FailCode.addFails(stringResult.getResult(), FailCode.CARD_PWD_ERROR);
			return stringResult;
		}
		// 是否已使用
		if (tbBatchCardsRecord.getRechargeStatus().equals("1")) {
			FailCode.addFails(stringResult.getResult(), FailCode.CARD_USED);
			return stringResult;
		}
		// 取出活动id
		int activityId = tbBatchCardsRecord.getActivityId();
		// 根据活动id查询批次活动表tb_batch_activity，获取充值的限制条件
		TbBatchActivityExample tbBatchActivityExample = new TbBatchActivityExample();
		Criteria criteria = tbBatchActivityExample.createCriteria();
		criteria.andIdEqualTo(activityId);
		List<TbBatchActivity> tbBatchActivities = batchActivityDao.queryByParams(tbBatchActivityExample);
		TbBatchActivity tbBatchActivity = tbBatchActivities.get(0);
		// 必须为手动的
		if (tbBatchActivity.getRechargeType().equals("1")) {
			FailCode.addFails(stringResult.getResult(), FailCode.CARD_FREEZE);
			return stringResult;
		}
		// 是否作废
		if (tbBatchActivity.getCurStatus().equals("1")) {
			FailCode.addFails(stringResult.getResult(), FailCode.CARD_FREEZE);
			return stringResult;
		}
		// 是否在有效期
		int b = ScoreCardUtil.compare_date(tbBatchActivity.getStartTime(), tbBatchActivity.getEndTime());
		if (b == 0) {
			FailCode.addFails(stringResult.getResult(), FailCode.activityTypeParamError);
			return stringResult;
		} else if (b == 2){
			FailCode.addFails(stringResult.getResult(), FailCode.CARD_EXPIRED);
			return stringResult;
		}
		// 是否允许单账号多次充值
		if (tbBatchActivity.getMultiRechargeEnable().equals("1")) {
			TbBatchCardsRecordExample example = new TbBatchCardsRecordExample();
			com.jfshare.card.model.TbBatchCardsRecordExample.Criteria criteria2 = example.createCriteria();
			criteria2.andActivityIdEqualTo(tbBatchActivity.getId());
			List<TbBatchCardsRecord> list = batchCardDao.queryByParams(example);
			List<String> mobiles = new ArrayList<String>();
			for (TbBatchCardsRecord tbBatchCardsRecord2 : list) {
				mobiles.add(tbBatchCardsRecord2.getRechargeAccount());
			}
			if (mobiles.contains(mobile)) {
				FailCode.addFails(stringResult.getResult(), FailCode.CARD_REPEATED);
				return stringResult;
			}
		}
		// --------开始充值--------
		// 更新记录表
		tbBatchCardsRecord.setRechargeAccount(mobile);
		tbBatchCardsRecord.setRechargeTime(new DateTime());
		tbBatchCardsRecord.setRechargeStatus("1");
		tbBatchCardsRecord.setSendStatus("1");
		batchCardDao.updateOne(tbBatchCardsRecord.getId(), tbBatchCardsRecord);
		// 插入充值记录表
		TbBatchRechargeRecord tbBatchRechargeRecord = new TbBatchRechargeRecord();
		tbBatchRechargeRecord.setCardName(param.getCardName());
		tbBatchRechargeRecord.setPieceValue(tbBatchActivity.getPieceValue());
		tbBatchRechargeRecord.setRechargeType("0");
		tbBatchRechargeRecord.setUserId(param.getUserId());
		tbBatchRechargeRecord.setActivityId(activityId);
		tbBatchRechargeRecord.setRechargeTime(new DateTime());
		batchRechargeCardDao.insertOne(tbBatchRechargeRecord);
		// 调用积分充值接口
		ScoreTrade scoreTrade = new ScoreTrade();
		scoreTrade.setAmount(tbBatchActivity.getPieceValue());
		scoreTrade.setInOrOut(1);
		scoreTrade.setTradeId("recharge-" + mobile + "-" + param.getCardName());
		scoreTrade.setUserId(param.getUserId());
		scoreTrade.setType(9); // 积分卡充值（新增）
		scoreTrade.setTrader(3); // 聚分享商城
		stringResult = scoreClient.income(scoreTrade);
		if (stringResult.getResult().getCode() == 1) {
			throw new RuntimeException("调用积分充值失败");
		}
		stringResult.setValue(String.valueOf(tbBatchActivity.getPieceValue()));
		return stringResult;
	}

	@Override
	public boolean validataPassword(String validataStr) {

		String password = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "export_psd", "0");
		if (!validataStr.equals(password)) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void directRecharge(DirectRechargeResult directRechargeResult, ToRechargeParams params) throws Exception {
		// 1校验密码
		if (!this.validataPassword(params.getValidataStr())) {
			FailCode.addFails(directRechargeResult.getResult(), FailCode.validataParamError);
			return;
		}

		// 2下载并解析excel
		String filePath = params.getFilePath();
		// ------------------------------------------

		// 读取文件中的数据
		Workbook wb = null;
		File localFile = null;
		Queue<String> listStr = new LinkedList<String>();
		try {

			// 下载文件到本地
			String localFolder = localPath + params.getActivityId() + "/";
			String fileName = "" + System.currentTimeMillis() + ".xls";
			boolean flag = FileUtil.downloadFile(filePath, localFolder, fileName);
			if (!flag) {
				logger.error("<<<<<<<< directRecharge excel file error !! ---- ActivityId : " + params.getActivityId()
						+ ", filePath : " + filePath);
				FailCode.addFails(directRechargeResult.getResult(), FailCode.validataParamError);
				return;
			}
			localFile = new File(localFolder + fileName);
			Date now = new Date();
			InputStream is = new FileInputStream(localFile);

			try {
				wb = new XSSFWorkbook(is);
			} catch (Exception ex) {
				wb = new HSSFWorkbook(is);
			}

			Sheet sheet = wb.getSheetAt(0);

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				listStr.add(row.getCell(0).getStringCellValue().trim());
			}

			logger.error("<<<<<<<<解析excel文件获取的手机号总数为 : " + listStr.size());
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException();

		} finally {
			// 删除文件
			if (localFile != null) {
				 localFile.delete();
			}
		}
		// --------------------------------------------
		// 3根据Id查询批次活动
		TbBatchActivity source = batchActivityDao.queryById(Integer.valueOf(params.getActivityId()));

		if (source == null) {
			FailCode.addFails(directRechargeResult.getResult(), FailCode.repquestParamError);
			return;
		}
		// 积分面值
		int scoreNum = source.getPieceValue();

		// 4判断是否自动，且为可用的（即未过期，未作废）
		int flag = ScoreCardUtil.compare_date(source.getStartTime(), source.getEndTime());
		//recharge_type充值方式：0手动 1自动 ，cur_status 是否作废：0 未作废 ，1作废
		if ("0".equals(source.getRechargeType()) || "1".equals(source.getCurStatus()) || (flag!=1)) {
			FailCode.addFails(directRechargeResult.getResult(), FailCode.activityTypeParamError);
			return;
		}

		// 5查询未发放、未充值的卡密
		TbBatchCardsRecord tbBatchCardsRecord = new TbBatchCardsRecord();
		tbBatchCardsRecord.setActivityId(Integer.valueOf(params.getActivityId()));
		List<TbBatchCardsRecord> listRecord = batchCardDao.queryUnpaidout(tbBatchCardsRecord);

		List<TbBatchCardsRecord> unRechareges = new ArrayList<TbBatchCardsRecord>();// 未充值对象
		List<String> recharegeMobiles = new ArrayList<String>();// 已充值手机号

		if (listRecord.size() > 0) {
			for (TbBatchCardsRecord tbr : listRecord) {
				// 未充值
				if ("0".equals(tbr.getRechargeStatus())) {
					unRechareges.add(tbr);
				} else {// 已充值
					recharegeMobiles.add(tbr.getRechargeAccount());
				}
			}
		}

		int sucessNum = 0;
		int failedNum = 0;
		int total = listStr.size();
		// 6开始充值
		// 6.1判断是否允许单个账号多次充值，每个手机号只充值1次--再次出现的手机号计入 失败条目
		for (TbBatchCardsRecord tbrec : unRechareges) {
			// 弹出队列数据
			String account = listStr.poll();

			if (account == null) {
				break;
			}

			// -----------------------------
			if ("1".equals(source.getMultiRechargeEnable()) && recharegeMobiles.contains(account)) {
				failedNum += 1;
				continue;
			}
			// -------------------------------

			// a 更新记录表
			tbrec.setRechargeAccount(account);
			tbrec.setRechargeTime(new DateTime());
			tbrec.setRechargeStatus("1");
			tbrec.setSendStatus("1");
			batchCardDao.updateOne(tbrec.getId(), tbrec);

			// b 手机号注册
			// c 更新相应库表--卡密表、卡密记录表、积分明细表
			// ------------调用buyer服务-----------------------
			logger.info("--------调用buyer服务--start---------------");

			Buyer buyer = new Buyer();
			LoginLog loginLog = new LoginLog();

			buyer.setMobile(account);
			buyer.setState(98);// 充值注册用户

			loginLog.setClientType(99);

			BuyerResult buyerResult = buyerClient.tosigin(buyer, loginLog);
			Buyer byd = buyerResult.getBuyer();
			// buyerIds.add(byd.getUserId());
			logger.info("--------调用buyer服务--end---------------");

			// -------------调用score服务-----------------------
			logger.info("--------调用score服务--start---------------");
			if (!StringUtil.isNullOrEmpty(byd.getUserId())) {

				// 插入充值记录表-- 放在这里插入的原因：userid的获取
				TbBatchRechargeRecord tbBatchRechargeRecord = new TbBatchRechargeRecord();
				tbBatchRechargeRecord.setCardName(tbrec.getCardName());
				tbBatchRechargeRecord.setPieceValue(source.getPieceValue());
				tbBatchRechargeRecord.setRechargeType("1");// 0 手动 1自动
				tbBatchRechargeRecord.setUserId(byd.getUserId());
				tbBatchRechargeRecord.setActivityId(tbrec.getActivityId());
				tbBatchRechargeRecord.setRechargeTime(new DateTime());
				batchRechargeCardDao.insertOne(tbBatchRechargeRecord);

				ScoreTrade scoreTrade = new ScoreTrade();
				scoreTrade.setAmount(scoreNum);
				scoreTrade.setInOrOut(1);// 兑入
				scoreTrade.setTradeId("recharge-" + account + "-" + tbrec.getCardName());
				scoreTrade.setUserId(byd.getUserId());
				scoreTrade.setType(9); // 积分卡充值（新增）
				scoreTrade.setTrader(3); // 聚分享商城

				StringResult scoreResult = scoreClient.income(scoreTrade);
				if (scoreResult.getResult().getCode() == 1) {
					throw new RuntimeException("调用积分充值失败");
				}

				// Result result= scoreResult.getResult();
				// result.getCode();
			}
			logger.info("--------调用score服务--end---------------");

			// 增加1个
			recharegeMobiles.add(account);
		}

		if (recharegeMobiles.size() == 0) {
			sucessNum = total;
		} else {
			failedNum = listStr.size() + failedNum;
			sucessNum = total - failedNum;
		}
		directRechargeResult.setSucessNum(sucessNum);
		directRechargeResult.setFailedNum(failedNum);
	}

	@Override
	public void invalidOneActivity(InvalidOneActivityResult retResult, int activityId, String psd) {

		if (!validataPassword(psd)) {//密码未校验通过

			FailCode.addFails(retResult.getResult(), FailCode.repquestParamError);

			return;

		}

		TbBatchActivityExample bean=new TbBatchActivityExample();
		TbBatchActivityExample.Criteria criteria=bean.createCriteria();

		criteria.andIdEqualTo(activityId);
		DateTime timeNow=new DateTime();

		//criteria.andStartTimeLessThanOrEqualTo(timeNow);
		criteria.andEndTimeGreaterThanOrEqualTo(timeNow);//当前有效
		criteria.andCurStatusEqualTo(TypeStatEnum.Activity_Status_Useful.getState());// 当前可用

		List<TbBatchActivity> tempList =batchActivityDao.queryByParams(bean);

		// 当前活动已经被作作废或者过期。....
		if(null==tempList || tempList.size()!=1){
			FailCode.addFails(retResult.getResult(), FailCode.ActivityTypeInvalidAlready);
			return;
		}

		TbBatchActivity temEntity=tempList.get(0);
		temEntity.setCurStatus(TypeStatEnum.Activity_Status_Cancel.getState());// 作废掉

		batchActivityDao.updateOne(activityId,temEntity);

	}
}
