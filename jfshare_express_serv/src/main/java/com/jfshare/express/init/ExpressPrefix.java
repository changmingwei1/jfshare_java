package com.jfshare.express.init;

import com.jfshare.express.Util.fastjsonex.FastJsonExUtils;
import com.jfshare.express.db.redis.IExpressJedis;
import com.jfshare.express.impl.ExpressImpl;
import com.jfshare.finagle.thrift.express.ExpressInfo;
import com.jfshare.ridge.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 提供物流自动补全redis数据源
 * @author lenovo
 *
 */
@Service
public class ExpressPrefix {
	@Resource(name="expressImpl")
    private ExpressImpl expressImpl;
	
	@Autowired
	private IExpressJedis expressJedisImpl;
	
	  
	@PostConstruct
    public void initExpressPrefix() {
		FastJsonExUtils.initSupportDateTimeEx();

		String initState = PropertiesUtil.getProperty("jfx_express_serv", "initstate", "0");
		boolean needInit = initState.trim().equals("1") ? true : false;

		if (needInit) {
			List<ExpressInfo> expressInfoList = expressImpl.query();
			expressJedisImpl.setExpressPrefix(expressInfoList);

			if (expressJedisImpl.existsExpressInfo()) {
				needInit = expressJedisImpl.removeAllExpress();
			}
            if(needInit) {
				expressJedisImpl.setExpressInfo(expressInfoList);
			}

			//删除精简物流信息缓存
			expressJedisImpl.removeAllExpressSimple();
		}
    }
}
