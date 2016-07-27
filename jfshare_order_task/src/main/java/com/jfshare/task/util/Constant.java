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

    public static final String MSG_TITLE_ORDER_DELIVER = "发货通知";
    public static final String MSG_TITLE_ORDER_CONFIRM_RECEIPT = "确认收货通知";
    public static final String MSG_TEMPLATE_ORDER_DELIVER = "您有1笔订单已发货，订单号{{orderId}}，查看详情";
    public static final String MSG_TEMPLATE_ORDER_CONFIRM_RECEIPT = "您有1笔订单已确认收货，订单号{{orderId}}，查看详情";
}
