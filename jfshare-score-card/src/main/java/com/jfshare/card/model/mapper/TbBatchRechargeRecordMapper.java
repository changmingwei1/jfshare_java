package com.jfshare.card.model.mapper;

import com.jfshare.card.model.TbBatchRechargeRecord;
import com.jfshare.card.model.TbBatchRechargeRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbBatchRechargeRecordMapper {
    int countByExample(TbBatchRechargeRecordExample example);

    int deleteByExample(TbBatchRechargeRecordExample example);

    @Delete({
        "delete from tb_batch_recharge_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_batch_recharge_record (id, card_name, ",
        "piece_value, recharge_type, ",
        "user_id, activity_id, ",
        "recharge_time)",
        "values (#{id,jdbcType=INTEGER}, #{cardName,jdbcType=VARCHAR}, ",
        "#{pieceValue,jdbcType=INTEGER}, #{rechargeType,jdbcType=CHAR}, ",
        "#{userId,jdbcType=INTEGER}, #{activityId,jdbcType=INTEGER}, ",
        "#{rechargeTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbBatchRechargeRecord record);

    int insertSelective(TbBatchRechargeRecord record);

    List<TbBatchRechargeRecord> selectByExample(TbBatchRechargeRecordExample example);

    @Select({
        "select",
        "id, card_name, piece_value, recharge_type, user_id, activity_id, recharge_time",
        "from tb_batch_recharge_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbBatchRechargeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbBatchRechargeRecord record, @Param("example") TbBatchRechargeRecordExample example);

    int updateByExample(@Param("record") TbBatchRechargeRecord record, @Param("example") TbBatchRechargeRecordExample example);

    int updateByPrimaryKeySelective(TbBatchRechargeRecord record);

    @Update({
        "update tb_batch_recharge_record",
        "set card_name = #{cardName,jdbcType=VARCHAR},",
          "piece_value = #{pieceValue,jdbcType=INTEGER},",
          "recharge_type = #{rechargeType,jdbcType=CHAR},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "activity_id = #{activityId,jdbcType=INTEGER},",
          "recharge_time = #{rechargeTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbBatchRechargeRecord record);
}