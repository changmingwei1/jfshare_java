package com.jfshare.manager.util;

import com.jfshare.finagle.thrift.manager.Commissioner;
import com.jfshare.finagle.thrift.manager.ProductOpt;
import com.jfshare.finagle.thrift.manager.SlotImage;
import com.jfshare.manager.common.ManagerCommons;
import com.jfshare.manager.model.TbCommissioner;
import com.jfshare.manager.model.TbProductOperator;
import com.jfshare.manager.model.TbSlotImage;
import com.jfshare.utils.CryptoUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;

/**
 * Created by Lenovo on 2015/12/1.
 */
public class BeanConvertUtil {
    public static TbCommissioner commissioner2TbCommissioner(Commissioner commissioner) {
        TbCommissioner tbCommissioner = new TbCommissioner();
        tbCommissioner.setCsId(commissioner.getCsId());
        tbCommissioner.setCsName(commissioner.getCsName());
        tbCommissioner.setLoginName(commissioner.getLoginName());
        if (!StringUtil.isNullOrEmpty(commissioner.getPwdEnc())) {
            tbCommissioner.setPwdEnc(CryptoUtil.encryptDBPwd(commissioner.getPwdEnc().trim()));
        }
        return tbCommissioner;
    }
    
    
    public static SlotImage tbSlotImage2SlotImage(TbSlotImage tbSlotImage){

    	//System.out.println(tbSlotImage);
    	
		SlotImage slotImage = new SlotImage();
		
		slotImage.setLastUpdateTime(tbSlotImage.getLastUpdateTime().toString(DateTimeUtil.FORMAT_DEFAULT));
		slotImage.setType(tbSlotImage.getType());
		slotImage.setWidth(tbSlotImage.getWidth());
		slotImage.setHeight(tbSlotImage.getHeight());
		slotImage.setJump(tbSlotImage.getJump());
		slotImage.setImgKey(tbSlotImage.getImgKey());
		slotImage.setId(tbSlotImage.getId());
		slotImage.setIsDelete(tbSlotImage.getIsDelete());
		slotImage.setCreateTime(tbSlotImage.getCreateTime().toString(DateTimeUtil.FORMAT_DEFAULT));
		
		return slotImage;
    }

    public static TbProductOperator ProductOpt2TbProductOperator(ProductOpt productOpt) {
        TbProductOperator tbProductOperator = new TbProductOperator();
        tbProductOperator.setProductId(productOpt.getProductId());
        tbProductOperator.setProductState(productOpt.getActiveState());
        tbProductOperator.setOptTime(DateUtils.strToDateTime(productOpt.getCreateTime()));
        tbProductOperator.setOptUserId(productOpt.getOperatorId());
        tbProductOperator.setOptUserType(productOpt.getOperatorType());
        tbProductOperator.setOptDesc(ManagerCommons.OptUser.getDescByCode(productOpt.getOperatorType())
                + ": " + productOpt.getDesc());
        return tbProductOperator;
    }
}
