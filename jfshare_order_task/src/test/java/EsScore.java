
import com.alibaba.fastjson.JSON;


import java.util.Date;

/**
 * Created by Lenovo on 2016/7/15.
 */
public class EsScore {
    // 订单批次号
    private String orderBatch;
    // 订单id
    private String orderId;
    // 用户id
    private int userId;
    // 积分数
    private int score;
    // 类型， ConstantUtil.SCORE_TYPE.enumVal
    private int type;
    // 操作时间
    private Date optTime;

    public EsScore() {
    }

    public EsScore(String orderBatch, String orderId, int userId, int score, int type) {
        this.orderBatch = orderBatch;
        this.orderId = orderId;
        this.userId = userId;
        this.score = score;
        this.type = type;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getOrderBatch() {
        return orderBatch;
    }

    public void setOrderBatch(String orderBatch) {
        this.orderBatch = orderBatch;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EsScore{" +
                "orderBatch='" + orderBatch + '\'' +
                ", orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", score=" + score +
                ", type=" + type +
                ", optTime=" + optTime +
                '}';
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
