package com.jfshare.score.model.mapper;

import com.jfshare.score.model.TbScoreTrade;
import com.jfshare.score.model.TbScoreTradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbScoreTradeMapper {
    int countByExample(TbScoreTradeExample example);

    int deleteByExample(TbScoreTradeExample example);

    @Delete({
        "delete from tb_score_trade",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_score_trade (id, user_id, ",
        "trade_time, ",
        "in_or_out, type, amount, ",
        "trader)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{tradeTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{inOrOut,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, #{amount,jdbcType=INTEGER}, ",
        "#{trader,jdbcType=INTEGER})"
    })
    int insert(TbScoreTrade record);

    int insertSelective(TbScoreTrade record);

    List<TbScoreTrade> selectByExample(TbScoreTradeExample example);
    
    @Select({
        "select",
        "id, user_id, trade_time, in_or_out, type, amount, trader",
        "from tb_score_trade",
        "where id = #{id,jdbcType=INTEGER} order by trade_time desc"
    })
    @ResultMap("BaseResultMap")
    TbScoreTrade selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbScoreTrade record, @Param("example") TbScoreTradeExample example);

    int updateByExample(@Param("record") TbScoreTrade record, @Param("example") TbScoreTradeExample example);

    int updateByPrimaryKeySelective(TbScoreTrade record);

    @Update({
        "update tb_score_trade",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "trade_time = #{tradeTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "in_or_out = #{inOrOut,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "amount = #{amount,jdbcType=INTEGER},",
          "trader = #{trader,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbScoreTrade record);
}