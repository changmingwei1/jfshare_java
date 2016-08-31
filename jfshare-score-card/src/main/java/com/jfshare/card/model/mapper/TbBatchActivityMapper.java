package com.jfshare.card.model.mapper;

import com.jfshare.card.model.TbBatchActivity;
import com.jfshare.card.model.TbBatchActivityExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface TbBatchActivityMapper {
    int countByExample(TbBatchActivityExample example);

    int deleteByExample(TbBatchActivityExample example);

    @Delete({
        "delete from tb_batch_activity",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_batch_activity (id, name, ",
        "piece_value, total_count, ",
        "recharge_type, create_time, ",
        "start_time, ",
        "end_time, ",
        "cur_status, password, ",
        "multi_recharge_enable)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{pieceValue,jdbcType=INTEGER}, #{totalCount,jdbcType=INTEGER}, ",
        "#{rechargeType,jdbcType=CHAR}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{startTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{endTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{curStatus,jdbcType=CHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{multiRechargeEnable,jdbcType=CHAR})"
    })
    @SelectKey(before = false, keyProperty = "id", resultType =Integer.class, statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    int insert(TbBatchActivity record);

    int insertSelective(TbBatchActivity record);

    List<TbBatchActivity> selectByExample(TbBatchActivityExample example);

    @Select({
        "select",
        "id, name, piece_value, total_count, recharge_type, create_time, start_time, ",
        "end_time, cur_status, password, multi_recharge_enable",
        "from tb_batch_activity",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbBatchActivity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbBatchActivity record, @Param("example") TbBatchActivityExample example);

    int updateByExample(@Param("record") TbBatchActivity record, @Param("example") TbBatchActivityExample example);

    int updateByPrimaryKeySelective(TbBatchActivity record);

    @Update({
        "update tb_batch_activity",
        "set name = #{name,jdbcType=VARCHAR},",
          "piece_value = #{pieceValue,jdbcType=INTEGER},",
          "total_count = #{totalCount,jdbcType=INTEGER},",
          "recharge_type = #{rechargeType,jdbcType=CHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "start_time = #{startTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "end_time = #{endTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "cur_status = #{curStatus,jdbcType=CHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "multi_recharge_enable = #{multiRechargeEnable,jdbcType=CHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbBatchActivity record);
}