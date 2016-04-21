package com.jfshare.product.util.asynclog;

import com.jfshare.finagle.thrift.manager.ProductOpt;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Lenovo on 2015/12/19.
 */
public class LogCommon {
    public static LinkedBlockingQueue<ProductOpt> asyncProductOptLogsQueue = new LinkedBlockingQueue<ProductOpt>(10000);
    public final static int PRE_SEND_COUNT_LIMIT = 1000;
}
