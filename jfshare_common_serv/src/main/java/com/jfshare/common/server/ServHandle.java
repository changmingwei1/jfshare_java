package com.jfshare.common.server;

import com.jfshare.common.shortmsg.CaptchaWithKey;
import com.jfshare.common.shortmsg.MsgHandler;
import com.jfshare.common.shortmsg.sender.Sender;
import com.jfshare.finagle.thrift.common.*;
import com.jfshare.common.db.redis.AreaRedisInit;
import com.jfshare.common.db.redis.ICommonJedis;
import com.jfshare.common.db.redis.RedisConst;
import com.jfshare.common.impl.CommonImpl;
import com.jfshare.common.model.TbIp2addressDic;
import com.jfshare.common.util.Common;
import com.jfshare.common.util.FailCode;
import com.jfshare.common.util.IIpAttribution;
import com.jfshare.common.util.Util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Service(value = "handler")
public class ServHandle implements CommonServ.Iface {
    private Logger logger = LoggerFactory.getLogger(ServHandle.class);

    @Autowired
    @Qualifier("commonImpl")
    private CommonImpl commonImpl;

    @Autowired
    private IIpAttribution ipAttribution;

    @Autowired
    private Util util;    

	@Autowired
	private AreaRedisInit areaRedisInit;
	
	@Autowired
	private ICommonJedis commonJedis;

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @Autowired
    private MsgHandler msgHandler;

    @Autowired
    private Sender signinMsgSender;

    public CommonImpl getCommonImpl() {
        return commonImpl;
    }

    public void setCommonImpl(CommonImpl commonImpl) {
        this.commonImpl = commonImpl;
    }

    
    public AreaInfoResult province() throws TException {
        logger.info("province...");
//        List<AreaInfo> areaInfoList = this.commonImpl.province();
        List<AreaInfo> areaInfoList = this.util.getProvinceList();
        Result result = new Result();
        result.setCode(0);
        AreaInfoResult areaInfoResult = new AreaInfoResult();
        areaInfoResult.setResult(result);
        if (areaInfoList == null) {
            areaInfoList = new ArrayList<AreaInfo>();
        }
        areaInfoResult.setAreaInfo(areaInfoList);
        return areaInfoResult;
    }

    public AreaInfoResult city(int provinceId) throws TException {
        logger.info("city...");
        logger.info(provinceId + "...");
        List<AreaInfo> areaInfoList = commonJedis.getCityList(""+provinceId);
        if(areaInfoList == null || areaInfoList.size()<=0){
        	logger.info("----查询市列表，redis没有数据，从数据库中查找----provinceId="+provinceId);
        	areaInfoList = this.commonImpl.city(provinceId);
        }
        Result result = new Result();
        result.setCode(0);
        AreaInfoResult areaInfoResult = new AreaInfoResult();
        areaInfoResult.setResult(result);
        if (areaInfoList == null) {
            areaInfoList = new ArrayList<AreaInfo>();
        }
        areaInfoResult.setAreaInfo(areaInfoList);
        return areaInfoResult;
    }

    public AreaInfoResult county(int cityId) throws TException {
        logger.info("county...");
        logger.info(cityId + "...");
        List<AreaInfo> areaInfoList = commonJedis.getCountyList(""+cityId);
        if(areaInfoList == null || areaInfoList.size()<=0){
        	logger.info("----查询区县列表，redis没有数据，从数据库中查找----cityId="+cityId);
        	areaInfoList = this.commonImpl.city(cityId);
        }
        Result result = new Result();
        result.setCode(0);
        AreaInfoResult areaInfoResult = new AreaInfoResult();
        areaInfoResult.setResult(result);
        if (areaInfoList == null) {
            areaInfoList = new ArrayList<AreaInfo>();
        }
        areaInfoResult.setAreaInfo(areaInfoList);
        return areaInfoResult;
    }

    public AreaInfoResult street(int countyId) throws TException {
        logger.info("street...");
        logger.info(countyId + "...");
        List<AreaInfo> areaInfoList = this.commonImpl.street(countyId);
        Result result = new Result();
        result.setCode(0);
        AreaInfoResult areaInfoResult = new AreaInfoResult();
        areaInfoResult.setResult(result);
        if (areaInfoList == null) {
            areaInfoList = new ArrayList<AreaInfo>();
        }
        areaInfoResult.setAreaInfo(areaInfoList);
        return areaInfoResult;
    }

    public AttributionResult numberAttribution(String number) throws TException {
        logger.info("numberAttribution...");
        logger.info(number + "...");

        number = number.substring(0, 7);
        Attribution attribution = this.commonImpl.numberAttribution(number);

        Result result = new Result();
        result.setCode(0);
        AttributionResult attributionResult = new AttributionResult();
        attributionResult.setResult(result);
        if (attribution == null) {
            logger.info("没有查到数据...:" + number);
            result.setCode(1);
            List<FailDesc> failDescList = new ArrayList<FailDesc>();
            failDescList.add(FailCode.DATA_NO_EXIST);
            result.setFailDescList(failDescList);
            attributionResult.setResult(result);
            return attributionResult;
        }
        attributionResult.setAreaInfo(attribution);
        return attributionResult;
    }

    public AttributionOperatorResult numberAttributionOperator(String number) throws TException {
        logger.info("numberAttributionOperator...");
        logger.info(number + "...");
        number = number.substring(0, 7);
        AttributionOperator attributionOperator = this.commonImpl.numberAttributionOperator(number);

        Result result = new Result();
        result.setCode(0);
        AttributionOperatorResult attributionOperatorResult = new AttributionOperatorResult();
        attributionOperatorResult.setResult(result);
        if (attributionOperator == null) {
            attributionOperator = new AttributionOperator(0, "", 0, "", "");
        }
        attributionOperatorResult.setAttributionOperator(attributionOperator);
        return attributionOperatorResult;
    }

    public AttributionResult ipAttribution(String ip) throws TException {
    	

		String startTime =  Common.formatTimeToMill(null);
    	logger.info("【获取IP归属地】开始:" + ip+"......");
        Result result = new Result();
        AttributionResult attributionResult = new AttributionResult();
        try {
        	TbIp2addressDic tbIp2addressDic = ipAttribution.getIpAttribution(ip);
        	
	        if (tbIp2addressDic == null) {
	            logger.info("没有查到数据...:" + ip);
	            result.setCode(1);
	            List<FailDesc> failDescList = new ArrayList<FailDesc>();
	            failDescList.add(FailCode.DATA_NO_EXIST);
	            result.setFailDescList(failDescList);
	            attributionResult.setResult(result);
	            return attributionResult;
	        }
	        
        	Attribution attribution = new Attribution();
        	attribution.setCityId(tbIp2addressDic.getCityId());
        	attribution.setCityName(tbIp2addressDic.getCityName());
        	attribution.setProvinceId(tbIp2addressDic.getProvinceId());
        	attribution.setProvinceName(tbIp2addressDic.getProvinceName());
        	

	        result.setCode(0);
	        attributionResult.setResult(result);
	        attributionResult.setAreaInfo(attribution);
	        logger.info("【获取IP归属地】ip:" + ip + ", 省份：" + tbIp2addressDic.getProvinceName() + ",市区：" + tbIp2addressDic.getCityName());
	        return attributionResult;
        
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常...:" + e.getMessage());
            result.setCode(1);
            List<FailDesc> failDescList = new ArrayList<FailDesc>();
            failDescList.add(FailCode.SYSTEM_EXCEPTION);
            result.setFailDescList(failDescList);
            attributionResult.setResult(result);
            return attributionResult;
        } finally {
            logger.info("attributionResult:" + attributionResult.toString());
            
			logger.info("【获取IP归属地-统计时间】　ip:" +
					" 开始时间：" + startTime + "结束时间："+Common.formatTimeToMill(null) + "*******************************");
        }
    	/*
        logger.info("ipAttribution...");
        logger.info(ip + "...");
        Result result = new Result();
        AttributionResult attributionResult = new AttributionResult();
        try {
            Attribution attribution = this.commonImpl.ipAttribution(ip);

            if (attribution == null) {
                logger.info("没有查到数据...:" + ip);
                result.setCode(1);
                List<FailDesc> failDescList = new ArrayList<FailDesc>();
                failDescList.add(FailCode.DATA_NO_EXIST);
                result.setFailDescList(failDescList);
                attributionResult.setResult(result);
                return attributionResult;
            }
            result.setCode(0);
            attributionResult.setResult(result);
            attributionResult.setAreaInfo(attribution);
            return attributionResult;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常...:" + e.getMessage());
            result.setCode(1);
            List<FailDesc> failDescList = new ArrayList<FailDesc>();
            failDescList.add(FailCode.SYSTEM_EXCEPTION);
            result.setFailDescList(failDescList);
            attributionResult.setResult(result);
            return attributionResult;
        } finally {
            logger.info("attributionResult:" + attributionResult.toString());
        }
        */
    }

    public AttributionResult cityAttribution(int cityId) throws TException {
        logger.info("cityAttribution...");
		logger.info(cityId + "...");
		Attribution attribution = new Attribution();
		/**
		 * 发现有调用者传的是区县id，用来获取区县名称，这种调用redis不能做出判断，故保留原有接口方法，暂时不从redis获取数据
		 * 
		// 读取redis中的数据
		if (cityId != null) {
			String provinceId = cityId.toString().substring(0, 2) + "0000";// 拼接省ID
			// 取得省下边的所有市列表
			List<String> listCity = commonJedis.getList(RedisConst._ADD_PROVINCE_MORE + provinceId);
			if (listCity != null && listCity.size() > 0) {
				// 获得市名称和ID
				for (String string : listCity) {
					Map<String, String> map = JsonMapper.toMap(string);
					if (map != null) {
						// 设置市名称、ID
						if (map.get("id").equals(cityId.toString())) {
							attribution.setCityId(cityId);
							attribution.setCityName(map.get("name"));
							break;
						}
					}
				}
				// 取得省列表
				List<String> listProvince = commonJedis.getList(RedisConst._ADD_COUNTRY_CN_MORE);
				// 获得省名称和ID
				for (String string : listProvince) {
					Map<String, String> map = JsonMapper.toMap(string);
					if (map != null) {
						// 设置省名称、ID
						if (map.get("id").equals(provinceId.toString())) {
							attribution.setProvinceId(Integer.parseInt(provinceId));
							attribution.setProvinceName(map.get("name"));
							break;
						}
					}
				}
			} else {
				logger.info("-----redis没有命中省信息，从数据库中查找----");
				attribution = this.commonImpl.cityAttribution(cityId);
			}
		}
	*/
		attribution = this.commonImpl.cityAttribution(cityId);
        Result result = new Result();
        result.setCode(0);
        AttributionResult attributionResult = new AttributionResult();
        attributionResult.setResult(result);
        if (attribution == null) {
            attribution = new Attribution(0, "", 0, "");
        }
        attributionResult.setAreaInfo(attribution);
        return attributionResult;
    }

    public AttributionIdResult idByName(String provinceName, String cityName, String countyName) throws TException {
        logger.info("idByName...");
        logger.info(provinceName + "..." + cityName + "..." + countyName + "...");
        AttributionId attributionId = this.commonImpl.idByName(provinceName, cityName, countyName);

        Result result = new Result();
        result.setCode(0);
        AttributionIdResult attributionIdResult = new AttributionIdResult();
        attributionIdResult.setResult(result);
        if (attributionId == null) {
            attributionId = new AttributionId(0, 0, 0);
        }
        attributionIdResult.setAttributionId(attributionId);
        return attributionIdResult;
    }

    /**
     * 根据区县ID，查找到省ID和市ID
     * @param countyId
     * @return
     * @throws TException
     */
    public AttributionIdResult getAttributionIdByCountyId(int countyId) throws TException {
    	logger.info("------根据区县ID，查找到省ID和市ID-----开始-----countyId="+countyId);
    	AttributionIdResult attributionIdResult= new AttributionIdResult();
        Result result = new Result();
    	String ids = commonJedis.get(RedisConst._ADD_COUNTYID+countyId);
    	if(ids != null && !ids.equals("")){
    		String[] arrId = ids.split("_");
    		AttributionId attributionId = new AttributionId(); 
    		attributionId.setProvinceId(Integer.parseInt(arrId[0]));
    		attributionId.setCityId(Integer.parseInt(arrId[1]));
    		attributionId.setCountyId(Integer.parseInt(arrId[2]));
    		attributionIdResult.setAttributionId(attributionId);
    		
            result.setCode(0);
    		attributionIdResult.setResult(result);
    	}else{
    		logger.info("------根据区县ID，查找到省ID和市ID-----redis没有命中！！！！----------countyId="+countyId);
    		AreaInfo areaInfo = commonImpl.getAreaInfoById(countyId);
    		if(areaInfo != null){
	    		AttributionId attributionId = new AttributionId(); 
	    		attributionId.setProvinceId(areaInfo.getProvinceId());
	    		attributionId.setCityId(areaInfo.getCityId());
	    		attributionId.setCountyId(areaInfo.getCountyId());
	    		attributionIdResult.setAttributionId(attributionId);
	    		
	            result.setCode(0);
	    		attributionIdResult.setResult(result);
    		}else{
        		logger.info("------根据区县ID，查找到省ID和市ID-----没有找到相应的数据！！！！----------countyId="+countyId);
	            List<FailDesc> failDescList = new ArrayList<FailDesc>();
	            failDescList.add(FailCode.DATA_NO_EXIST);
	            result.setFailDescList(failDescList);
	            result.setCode(1);
	    		attributionIdResult.setResult(result);
    		}
    	}
    	
    	return attributionIdResult;
    }

    /**
	 * 根据省市区ID，查找到省市区name.
	   规则:查询county信息，则上级的省市ID为必填，同理查询city信息，则上级的省ID为必填.如果只查询省信息，则可以只传省ID
     * @param provinceId
     * @param cityId
     * @param countyId
     * @return
     * @throws TException 
     */
    public 	AttributionResult getAttributionNameById(int provinceId, int cityId, int countyId) throws TException {
    	AttributionResult attributionResult = new AttributionResult();
    	Attribution attribution = new Attribution();
    	logger.info("----根据省市区ID，查找到省市区name(getAttributionNameById)---参数 provinceId="+provinceId +"，cityId="+cityId+"，countyId="+countyId);
    	if(provinceId<=0){
    		logger.info("---参数错误-provinceId不能为空--provinceId="+provinceId);
    		Result result = FailCode.createErrorResult(FailCode.PARAM_EXCEPTION);
    		attributionResult.setResult(result);
    		return attributionResult;
    	}
    	//取得省信息
    	AreaInfoResult areaInfoResult = this.province();
    	if(areaInfoResult != null && areaInfoResult.getAreaInfo() != null && areaInfoResult.getAreaInfo().size()>0){
    		List<AreaInfo> list = areaInfoResult.getAreaInfo();
    		for (AreaInfo areaInfo : list) {
				if(areaInfo.getId()==provinceId){
					attribution.setProvinceId(provinceId);
					attribution.setProvinceName(areaInfo.getName());
					attribution.setShortProvinceName(areaInfo.getShortName());
					break;
				}
			}
    	}
    	//取得市信息
    	areaInfoResult = this.city(provinceId);
    	if(areaInfoResult != null && areaInfoResult.getAreaInfo() != null && areaInfoResult.getAreaInfo().size()>0){
    		List<AreaInfo> list = areaInfoResult.getAreaInfo();
    		for (AreaInfo areaInfo : list) {
				if(areaInfo.getId()==cityId){
					attribution.setCityId(cityId);
					attribution.setCityName(areaInfo.getName());
					attribution.setShortCityName(areaInfo.getShortName());
					break;
				}
			}
    	}
    	//取得区信息
    	areaInfoResult = this.county(cityId);
    	if(areaInfoResult != null && areaInfoResult.getAreaInfo() != null && areaInfoResult.getAreaInfo().size()>0){
    		List<AreaInfo> list = areaInfoResult.getAreaInfo();
    		for (AreaInfo areaInfo : list) {
				if(areaInfo.getId()==countyId){
					attribution.setCountyId(countyId);
					attribution.setCountyName(areaInfo.getName());
					attribution.setShortCountyName(areaInfo.getShortName());
					break;
				}
			}
    	}
    	Result result= new Result();
    	result.setCode(0);
		attributionResult.setResult(result);
		attributionResult.setAreaInfo(attribution);
    	return attributionResult;
    }

    @Override
    public CaptchaResult getCaptcha(String id) throws TException {
        Result r = new Result(0);
        CaptchaResult captchaResult = new CaptchaResult();
        Captcha captcha = new Captcha();
        captchaResult.setResult(r);
        captchaResult.setCaptcha(captcha);

        BufferedImage image = imageCaptchaService.getImageChallengeForID(id);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", out);
            byte[] imgBytes = out.toByteArray();
            captcha.setId(id);
            captcha.setCaptchaBytes(imgBytes);
        } catch (Exception e) {
            logger.error("生成验证码失败", e);
            r.setCode(1);
            r.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
        } finally {
            if(out!= null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return captchaResult;
    }

    @Override
    public CaptchaResult getQRCode(String id) throws TException {
        Result r = new Result(0);
        CaptchaResult captchaResult = new CaptchaResult();
        Captcha captcha = new Captcha();
        captchaResult.setResult(r);
        captchaResult.setCaptcha(captcha);

        ByteArrayOutputStream out = null;
        try {
            id = new String(id.getBytes("UTF-8"),"ISO-8859-1");
            out = QRCode.from(id)
                    .to(ImageType.PNG).withSize(265, 265).stream();
            byte[] retBytes = out.toByteArray();
            captcha.setId(id);
            captcha.setCaptchaBytes(retBytes);
        } catch (IOException e) {
            logger.error("生成二维码流失败!", id);
            r.setCode(1);
            r.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
        } finally {
            if(out!= null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return captchaResult;
    }

    @Override
    public Result sendMsg(ShortMsg msg) throws TException {
        Result result = new Result(0);

        if(msg == null || StringUtils.isBlank(msg.getMobile()) || StringUtils.isBlank(msg.getContent())){
            result.setCode(1);
            result.addToFailDescList(FailCode.PARAM_EXCEPTION);
            return result;
        }

        try {
            boolean sendRet = signinMsgSender.send(msg.getContent(), msg.getMobile());
            if(!sendRet) {
                result.setCode(1);
                result.addToFailDescList(FailCode.SEND_MSG_FAILURE);
                return result;
            }
        } catch (Exception e) {
            result.setCode(1);
            result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
            logger.error("sendMsgCaptcha ==> Exception", e);
        }
        return result;
    }

    @Override
    public Result validateCaptcha(Captcha captcha) throws TException {
        boolean vRes = false;
        Result r = new Result(0);
        try {
            vRes = imageCaptchaService.validateResponseForID(captcha.getId(), captcha.getValue());
        } catch (CaptchaServiceException e) {
            vRes = false;
        }
        logger.error("验证结果：" + vRes);
        if(vRes) {
            return r;
        } else {
            return FailCode.createErrorResult(FailCode.CAPTCHA_VALIDATE_FAILURE);
        }
    }

    @Override
    public Result sendMsgCaptcha(MsgCaptcha captcha) throws TException {
        Result result = new Result(0);

        if(captcha == null || StringUtils.isBlank(captcha.getMobile()) || StringUtils.isBlank(captcha.getType())){
            result.setCode(1);
            result.addToFailDescList(FailCode.PARAM_EXCEPTION);
            return result;
        }

        try {
            FailDesc createFailDesc = null;
            CaptchaWithKey captchaWithKey = msgHandler.getMsgCaptcha(captcha, createFailDesc);
            if(createFailDesc != null) {
                result.setCode(1);
                result.addToFailDescList(createFailDesc);
                return result;
            }

            boolean sendRet = signinMsgSender.send(captchaWithKey.getCaptcha()+"(动态验证码)， 请在3分钟内填写", captcha.getMobile());
            if(!sendRet) {
                result.setCode(1);
                result.addToFailDescList(FailCode.SEND_MSG_FAILURE);
                return result;
            }
        } catch (Exception e) {
            result.setCode(1);
            result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
            logger.error("sendMsgCaptcha ==> Exception", e);
        }
        return result;
    }

    @Override
    public Result validateMsgCaptcha(MsgCaptcha captcha) throws TException {
        Result result = new Result(0);

        if(captcha == null
                || StringUtils.isBlank(captcha.getMobile())
                || StringUtils.isBlank(captcha.getType())
                || StringUtils.isBlank(captcha.getCaptchaDesc())){
            result.setCode(1);
            result.addToFailDescList(FailCode.PARAM_EXCEPTION);
            return result;
        }

        try {
            FailDesc validateRes = msgHandler.validateMsgCaptcha(captcha);
            if(validateRes != null) {
                result.setCode(1);
                result.addToFailDescList(validateRes);
            }
        } catch (Exception e) {
            result.setCode(1);
            result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
            e.printStackTrace();
        }
        return result;
    }

}
