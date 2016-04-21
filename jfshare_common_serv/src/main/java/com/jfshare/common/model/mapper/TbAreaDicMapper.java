package com.jfshare.common.model.mapper;

import com.jfshare.common.model.TbAreaDic;
import com.jfshare.common.model.TbAreaDicExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbAreaDicMapper {
    int countByExample(TbAreaDicExample example);

    int deleteByExample(TbAreaDicExample example);

    @Delete({
        "delete from tb_area_dic",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_area_dic (id, name, ",
        "short_name, area_property, ",
        "province_id, city_id, ",
        "city_name, county_id, ",
        "postcode, delete_state, ",
        "pinyin, initial)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{shortName,jdbcType=VARCHAR}, #{areaProperty,jdbcType=VARCHAR}, ",
        "#{provinceId,jdbcType=INTEGER}, #{cityId,jdbcType=INTEGER}, ",
        "#{cityName,jdbcType=VARCHAR}, #{countyId,jdbcType=INTEGER}, ",
        "#{postcode,jdbcType=VARCHAR}, #{deleteState,jdbcType=INTEGER}, ",
        "#{pinyin,jdbcType=VARCHAR}, #{initial,jdbcType=VARCHAR})"
    })
    int insert(TbAreaDic record);

    int insertSelective(TbAreaDic record);

    List<TbAreaDic> selectByExample(TbAreaDicExample example);

    @Select({
        "select",
        "id, name, short_name, area_property, province_id, city_id, city_name, county_id, ",
        "postcode, delete_state, pinyin, initial",
        "from tb_area_dic",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbAreaDic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAreaDic record, @Param("example") TbAreaDicExample example);

    int updateByExample(@Param("record") TbAreaDic record, @Param("example") TbAreaDicExample example);

    int updateByPrimaryKeySelective(TbAreaDic record);

    @Update({
        "update tb_area_dic",
        "set name = #{name,jdbcType=VARCHAR},",
          "short_name = #{shortName,jdbcType=VARCHAR},",
          "area_property = #{areaProperty,jdbcType=VARCHAR},",
          "province_id = #{provinceId,jdbcType=INTEGER},",
          "city_id = #{cityId,jdbcType=INTEGER},",
          "city_name = #{cityName,jdbcType=VARCHAR},",
          "county_id = #{countyId,jdbcType=INTEGER},",
          "postcode = #{postcode,jdbcType=VARCHAR},",
          "delete_state = #{deleteState,jdbcType=INTEGER},",
          "pinyin = #{pinyin,jdbcType=VARCHAR},",
          "initial = #{initial,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbAreaDic record);
}