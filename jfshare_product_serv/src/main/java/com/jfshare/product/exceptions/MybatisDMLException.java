package com.jfshare.product.exceptions;

import com.jfshare.finagle.thrift.result.FailDesc;

/**
 * Created by Lenovo on 2015/11/2.
 */
public class MybatisDMLException extends BaseException{
    public MybatisDMLException(String message) {
        super(message);
    }

    public MybatisDMLException(FailDesc failDesc) {
        super(failDesc);
    }

    public MybatisDMLException(Throwable e) {
        super(e);
    }

    public MybatisDMLException(String message, Throwable cause) {
        super(message, cause);
    }
}
