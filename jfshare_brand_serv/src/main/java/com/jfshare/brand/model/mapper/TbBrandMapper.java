package com.jfshare.brand.model.mapper;

import com.jfshare.brand.model.TbBrand;
import com.jfshare.brand.model.TbBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbBrandMapper {
    int countByExample(TbBrandExample example);

    int deleteByExample(TbBrandExample example);

    @Delete({
        "delete from tb_brand",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_brand (id, name, ",
        "img_key, url, serial, ",
        "remark, create_time, ",
        "create_id, last_update_time, ",
        "last_update_id, state)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{imgKey,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{serial,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createId,jdbcType=INTEGER}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateId,jdbcType=INTEGER}, #{state,jdbcType=INTEGER})"
    })
    int insert(TbBrand record);

    int insertSelective(TbBrand record);

    List<TbBrand> selectByExample(TbBrandExample example);

    @Select({
        "select",
        "id, name, img_key, url, serial, remark, create_time, create_id, last_update_time, ",
        "last_update_id, state",
        "from tb_brand",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbBrand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbBrand record, @Param("example") TbBrandExample example);

    int updateByExample(@Param("record") TbBrand record, @Param("example") TbBrandExample example);

    int updateByPrimaryKeySelective(TbBrand record);

    @Update({
        "update tb_brand",
        "set name = #{name,jdbcType=VARCHAR},",
          "img_key = #{imgKey,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "serial = #{serial,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_id = #{createId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbBrand record);
}