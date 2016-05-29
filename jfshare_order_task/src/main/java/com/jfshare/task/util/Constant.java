package com.jfshare.task.util;

/**
 * Created by Lenovo on 2016/5/25.
 */
public class Constant {
    /* Redis ========================================================*/

    public static final String ORDER_OPT_QUEUE_KEY = "order_opt_queue";

    /* elasticsearch ========================================================*/

    public static final String ES_ORDER_TYPE = "esOrder";

    /* final constant defined */


    /* order opt enum*/
    public enum OptType {
        order_create,
        order_paying,
        order_pay,
        order_close,
        order_deliver,
        order_deliver_batch,
        order_confirm
        ;
    }
}
