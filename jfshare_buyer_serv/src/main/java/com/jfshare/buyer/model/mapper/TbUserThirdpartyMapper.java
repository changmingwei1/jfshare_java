package com.jfshare.buyer.model.mapper;

import com.jfshare.buyer.model.TbUserThirdparty;
import com.jfshare.buyer.model.TbUserThirdpartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbUserThirdpartyMapper {
    int countByExample(TbUserThirdpartyExample example);

    int deleteByExample(TbUserThirdpartyExample example);

    @Delete({
        "delete from tb_user_thirdparty",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_user_thirdparty (id, user_id, ",
        "cust_id, third_type, ",
        "user_name, account_no, ",
        "ext_info)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{custId,jdbcType=VARCHAR}, #{thirdType,jdbcType=INTEGER}, ",
        "#{userName,jdbcType=VARCHAR}, #{accountNo,jdbcType=VARCHAR}, ",
        "#{extInfo,jdbcType=VARCHAR})"
    })
    int insert(TbUserThirdparty record);

    int insertSelective(TbUserThirdparty record);

    List<TbUserThirdparty> selectByExample(TbUserThirdpartyExample example);

    @Select({
        "select",
        "id, user_id, cust_id, third_type, user_name, account_no, ext_info",
        "from tb_user_thirdparty",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbUserThirdparty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbUserThirdparty record, @Param("example") TbUserThirdpartyExample example);

    int updateByExample(@Param("record") TbUserThirdparty record, @Param("example") TbUserThirdpartyExample example);

    int updateByPrimaryKeySelective(TbUserThirdparty record);

    @Update({
        "update tb_user_thirdparty",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "cust_id = #{custId,jdbcType=VARCHAR},",
          "third_type = #{thirdType,jdbcType=INTEGER},",
          "user_name = #{userName,jdbcType=VARCHAR},",
          "account_no = #{accountNo,jdbcType=VARCHAR},",
          "ext_info = #{extInfo,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbUserThirdparty record);
}