package com.jfshare.express.kuaidi100;

import com.jfshare.ridge.PropertiesUtil;

/**
 * Created by bq on 16/3/7.
 */
public class Constant {

    private static final String APP_KEY = "jfx_express_serv";
    private static final String ALIAS_KUAIDI100_KEY = "kuaidi100_key";
    private static final String ALIAS_CALLBACK_URL = "callback_url";
    public static final String KUAIDI100_SERVER_URL = "http://www.kuaidi100.com/poll";

    public static String getKuaiDi100Key() {
        return PropertiesUtil.getProperty(APP_KEY, ALIAS_KUAIDI100_KEY, "OIUcmqpE5378");
    }

    public static String getCallbackUrl() {
        return PropertiesUtil.getProperty(APP_KEY, ALIAS_CALLBACK_URL, "http://120.24.153.102:13006/kuaidi/subscribe");
    }

    public enum Status{
        /*监控状态:
            polling:监控中，
            shutdown:结束，
            abort:中止，
            updateall：重新推送。
            其中当快递单为已签收时status=shutdown，当message为“3天查询无记录”或“60天无变化时”status= abort ，
            对于stuatus=abort的状度，需要增加额外的处理逻辑，详见本节最后的说明 */
        polling,
        shutdown,
        abort,
        updateall
    }
}
