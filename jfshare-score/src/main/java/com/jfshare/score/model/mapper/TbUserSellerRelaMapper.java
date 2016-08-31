package com.jfshare.score.model.mapper;

import com.jfshare.score.model.TbUserSellerRela;
import com.jfshare.score.model.TbUserSellerRelaExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbUserSellerRelaMapper {
    int countByExample(TbUserSellerRelaExample example);

    int deleteByExample(TbUserSellerRelaExample example);

    @Delete({
        "delete from tb_user_seller_rela",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_user_seller_rela (id, user_id, ",
        "createtime, ",
        "seller_id)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=VARCHAR}, ",
        "#{createtime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{sellerId,jdbcType=VARCHAR})"
    })
    int insert(TbUserSellerRela record);

    int insertSelective(TbUserSellerRela record);

    List<TbUserSellerRela> selectByExample(TbUserSellerRelaExample example);

    @Select({
        "select",
        "id, user_id, createtime, seller_id",
        "from tb_user_seller_rela",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbUserSellerRela selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbUserSellerRela record, @Param("example") TbUserSellerRelaExample example);

    int updateByExample(@Param("record") TbUserSellerRela record, @Param("example") TbUserSellerRelaExample example);

    int updateByPrimaryKeySelective(TbUserSellerRela record);

    @Update({
        "update tb_user_seller_rela",
        "set user_id = #{userId,jdbcType=VARCHAR},",
          "createtime = #{createtime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "seller_id = #{sellerId,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbUserSellerRela record);
}