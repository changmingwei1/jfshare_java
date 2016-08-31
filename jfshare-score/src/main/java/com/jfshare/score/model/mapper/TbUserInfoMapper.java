package com.jfshare.score.model.mapper;

import com.jfshare.score.model.TbUserInfo;
import com.jfshare.score.model.TbUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbUserInfoMapper {
    int countByExample(TbUserInfoExample example);

    int deleteByExample(TbUserInfoExample example);

    @Delete({
        "delete from tb_user_info",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userId);

    @Insert({
        "insert into tb_user_info (user_id, amount, ",
        "mobile, create_time)",
        "values (#{userId,jdbcType=INTEGER}, #{amount,jdbcType=INTEGER}, ",
        "#{mobile,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbUserInfo record);

    int insertSelective(TbUserInfo record);

    List<TbUserInfo> selectByExample(TbUserInfoExample example);
    List<TbUserInfo> selectUserInfo(TbUserInfoExample example);
    
    @Select({
        "select",
        "user_id, amount, mobile, create_time",
        "from tb_user_info",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbUserInfo selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") TbUserInfo record, @Param("example") TbUserInfoExample example);

    int updateByExample(@Param("record") TbUserInfo record, @Param("example") TbUserInfoExample example);

    int updateByPrimaryKeySelective(TbUserInfo record);

    @Update({
        "update tb_user_info",
        "set amount = #{amount,jdbcType=INTEGER},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbUserInfo record);
}