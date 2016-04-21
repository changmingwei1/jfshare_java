package com.jfshare.order.util;

import com.jfshare.finagle.thrift.order.OrderCount;
import com.jfshare.order.common.OrderConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2015/10/27.
 */
public class ResultConvertUtils {
    public static int getTotalForOrderStat(List<OrderCount> orderCounts, int orderState) {

        int total = 0;
        Map<Integer,Integer> stateNum = new HashMap<Integer,Integer>();
        for(int i=0; i<orderCounts.size(); ++i){
            stateNum.put(orderCounts.get(i).getOrderState(), orderCounts.get(i).getCount());
        }

        //计算订单的各种状态的总数量
        if(orderCounts != null){
            switch (orderState) {
                case 1:
                    if(stateNum.get(OrderConstant.WAIT_PAY_STATE) != null){
                        total = stateNum.get(OrderConstant.WAIT_PAY_STATE);
                    }
                    if(stateNum.get(OrderConstant.PAYING_STATE) != null){
                        total = stateNum.get(OrderConstant.PAYING_STATE);
                    }
                    break;
                case 2:
                    if(stateNum.get(OrderConstant.WAIT_DELIVER_STATE) != null){
                        total = stateNum.get(OrderConstant.WAIT_DELIVER_STATE);
                    }
                    break;
                case 3:
                    if(stateNum.get(OrderConstant.FINISH_DELIVER_STATE) != null){
                        total = stateNum.get(OrderConstant.FINISH_DELIVER_STATE);
                    }
                    break;
                case 5:
                    if(stateNum.get(OrderConstant.TRADE_SUCCESS_STATE) != null){
                        total = stateNum.get(OrderConstant.TRADE_SUCCESS_STATE);
                    }
                    break;
                case 7:
                    if(stateNum.get(OrderConstant.TRADE_CLOSE_STATE) != null){
                        total = stateNum.get(OrderConstant.TRADE_CLOSE_STATE);
                    }
                    break;
                case 8:
                    if(stateNum.get(OrderConstant.ORDER_STATE_WAIT_AUDIT) != null){
                        total = stateNum.get(OrderConstant.ORDER_STATE_WAIT_AUDIT);
                    }
                    break;
                default:

                    for(OrderCount orderCount : orderCounts){
                        total += orderCount.getCount();
                    }
                    break;
            }
        }

        return total;
    }
}
