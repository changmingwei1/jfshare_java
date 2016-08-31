package com.jfshare.score.model.mapper;

import com.jfshare.score.model.TbScoreUser;
import com.jfshare.score.model.TbScoreUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbScoreUserMapper {
    int countByExample(TbScoreUserExample example);

    int deleteByExample(TbScoreUserExample example);

    @Delete({
        "delete from tb_score_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userId);

    @Insert({
        "insert into tb_score_user (user_id, amount)",
        "values (#{userId,jdbcType=INTEGER}, #{amount,jdbcType=INTEGER})"
    })
    int insert(TbScoreUser record);

    int insertSelective(TbScoreUser record);

    List<TbScoreUser> selectByExample(TbScoreUserExample example);

    @Select({
        "select",
        "user_id, amount,version",
        "from tb_score_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbScoreUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") TbScoreUser record, @Param("example") TbScoreUserExample example);

    int updateByExample(@Param("record") TbScoreUser record, @Param("example") TbScoreUserExample example);

    int updateByPrimaryKeySelective(TbScoreUser record);
    int updateByPrimaryKeyCashAmount(TbScoreUser parm);

    @Update({
        "update tb_score_user",
        "set amount = #{amount,jdbcType=INTEGER}",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbScoreUser record);
}