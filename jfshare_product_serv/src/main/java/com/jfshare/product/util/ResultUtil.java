package com.jfshare.product.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2015/10/22.
 */
public class ResultUtil {
    private final static int CODE_SUCCESS = 0;
    private final static int CODE_FAILURE = 1;

    public static Result createNormalFailResult(FailDesc failDesc) {
        Result result = new Result(CODE_FAILURE);
        List<FailDesc> failList = new ArrayList<FailDesc>();
        failList.add(failDesc);
        result.setFailDescList(failList);
        return result;
    }

    public static StringResult createNormalStringResult(String value) {
        Result r = new Result(CODE_SUCCESS);
        StringResult stringResult = new StringResult(r);
        stringResult.setValue(value);
        return stringResult;

    }

    public static StringResult createNormalFailStringResult(FailDesc failDesc) {
        Result result = new Result(CODE_FAILURE);
        result.addToFailDescList(failDesc);
        StringResult stringResult = new StringResult(result);
        return stringResult;
    }

    public static StringResult createNormalFailStringResult(List<FailDesc> failDescs) {
        Result result = new Result(CODE_FAILURE);
        result.setFailDescList(failDescs);
        StringResult stringResult = new StringResult(result);
        return stringResult;
    }

    public static Result createNormalResult() {
        Result r = new Result();
        r.setCode(CODE_SUCCESS);
        return r;
    }
}
