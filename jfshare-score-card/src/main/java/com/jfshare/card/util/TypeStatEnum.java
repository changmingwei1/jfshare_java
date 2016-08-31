package com.jfshare.card.util;

/**
 * 使用枚举 表示常量 数据字段
 *
 * Created by chiwenheng on 16/8/4.
 */
public enum TypeStatEnum {


    recharge_manual("0","手动"),
    recharge_auto("1","自动"),

    Activity_Status_Useful("0","可用"),
    Activity_Status_Cancel("1","作废"),
    Activity_Status_Expire("2","过期"),

    Card_Un_Send("0","未发放"),
    Card_Sended("1","已经发放"),

    Card_Un_Recharge("0","未充值"),
    Card_Recharge("1","已充值");





    private String state;

    private String stateInfo;

    TypeStatEnum(String state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public String getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static TypeStatEnum stateOf(int index){
        for(TypeStatEnum state :values()){
            if(state.getState().equals(index)){
                return state;
            }
        }
        return null;
    }



}
