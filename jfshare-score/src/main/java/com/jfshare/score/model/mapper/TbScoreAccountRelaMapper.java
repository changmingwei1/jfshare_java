package com.jfshare.score.model.mapper;

import com.jfshare.score.model.TbScoreAccountRela;
import com.jfshare.score.model.TbScoreAccountRelaExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbScoreAccountRelaMapper {
    int countByExample(TbScoreAccountRelaExample example);

    int deleteByExample(TbScoreAccountRelaExample example);

    @Delete({
        "delete from tb_score_account_rela",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_score_account_rela (id, user_id, ",
        "account, create_time, ",
        "type, state)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{account,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{type,jdbcType=VARCHAR}, #{state,jdbcType=VARCHAR})"
    })
    int insert(TbScoreAccountRela record);

    int insertSelective(TbScoreAccountRela record);

    List<TbScoreAccountRela> selectByExample(TbScoreAccountRelaExample example);

    @Select({
        "select",
        "id, user_id, account, create_time, type, state",
        "from tb_score_account_rela",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbScoreAccountRela selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbScoreAccountRela record, @Param("example") TbScoreAccountRelaExample example);

    int updateByExample(@Param("record") TbScoreAccountRela record, @Param("example") TbScoreAccountRelaExample example);

    int updateByPrimaryKeySelective(TbScoreAccountRela record);

    @Update({
        "update tb_score_account_rela",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "account = #{account,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "type = #{type,jdbcType=VARCHAR},",
          "state = #{state,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbScoreAccountRela record);
}