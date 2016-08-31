package com.jfshare.card.model.mapper;

import com.jfshare.card.model.TbBatchCardsRecord;
import com.jfshare.card.model.TbBatchCardsRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbBatchCardsRecordMapper {
    int countByExample(TbBatchCardsRecordExample example);

    int deleteByExample(TbBatchCardsRecordExample example);

    @Delete({
        "delete from tb_batch_cards_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_batch_cards_record (id, activity_id, ",
        "card_name, card_psd, ",
        "send_status, recharge_status, ",
        "recharge_account, recharge_time, ",
        "create_time)",
        "values (#{id,jdbcType=INTEGER}, #{activityId,jdbcType=INTEGER}, ",
        "#{cardName,jdbcType=VARCHAR}, #{cardPsd,jdbcType=VARCHAR}, ",
        "#{sendStatus,jdbcType=CHAR}, #{rechargeStatus,jdbcType=CHAR}, ",
        "#{rechargeAccount,jdbcType=VARCHAR}, #{rechargeTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbBatchCardsRecord record);

    int insertSelective(TbBatchCardsRecord record);

    List<TbBatchCardsRecord> selectByExample(TbBatchCardsRecordExample example);

    @Select({
        "select",
        "id, activity_id, card_name, card_psd, send_status, recharge_status, recharge_account, ",
        "recharge_time, create_time",
        "from tb_batch_cards_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbBatchCardsRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbBatchCardsRecord record, @Param("example") TbBatchCardsRecordExample example);

    int updateByExample(@Param("record") TbBatchCardsRecord record, @Param("example") TbBatchCardsRecordExample example);

    int updateByPrimaryKeySelective(TbBatchCardsRecord record);

    @Update({
        "update tb_batch_cards_record",
        "set activity_id = #{activityId,jdbcType=INTEGER},",
          "card_name = #{cardName,jdbcType=VARCHAR},",
          "card_psd = #{cardPsd,jdbcType=VARCHAR},",
          "send_status = #{sendStatus,jdbcType=CHAR},",
          "recharge_status = #{rechargeStatus,jdbcType=CHAR},",
          "recharge_account = #{rechargeAccount,jdbcType=VARCHAR},",
          "recharge_time = #{rechargeTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbBatchCardsRecord record);
}