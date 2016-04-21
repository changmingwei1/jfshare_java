package com.jfshare.common.model.mapper;

import com.jfshare.common.model.TbIp2addressDic;
import com.jfshare.common.model.TbIp2addressDicExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbIp2addressDicMapper {
    int countByExample(TbIp2addressDicExample example);

    int deleteByExample(TbIp2addressDicExample example);

    @Delete({
        "delete from tb_ip2address_dic",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_ip2address_dic (id, ip_start_int, ",
        "ip_start_str, ip_end_int, ",
        "ip_end_str, city_id, ",
        "city_name, province_id, ",
        "province_name)",
        "values (#{id,jdbcType=INTEGER}, #{ipStartInt,jdbcType=INTEGER}, ",
        "#{ipStartStr,jdbcType=VARCHAR}, #{ipEndInt,jdbcType=INTEGER}, ",
        "#{ipEndStr,jdbcType=VARCHAR}, #{cityId,jdbcType=INTEGER}, ",
        "#{cityName,jdbcType=VARCHAR}, #{provinceId,jdbcType=INTEGER}, ",
        "#{provinceName,jdbcType=VARCHAR})"
    })
    int insert(TbIp2addressDic record);

    int insertSelective(TbIp2addressDic record);

    List<TbIp2addressDic> selectByExample(TbIp2addressDicExample example);

    @Select({
        "select",
        "id, ip_start_int, ip_start_str, ip_end_int, ip_end_str, city_id, city_name, ",
        "province_id, province_name",
        "from tb_ip2address_dic",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbIp2addressDic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbIp2addressDic record, @Param("example") TbIp2addressDicExample example);

    int updateByExample(@Param("record") TbIp2addressDic record, @Param("example") TbIp2addressDicExample example);

    int updateByPrimaryKeySelective(TbIp2addressDic record);

    @Update({
        "update tb_ip2address_dic",
        "set ip_start_int = #{ipStartInt,jdbcType=INTEGER},",
          "ip_start_str = #{ipStartStr,jdbcType=VARCHAR},",
          "ip_end_int = #{ipEndInt,jdbcType=INTEGER},",
          "ip_end_str = #{ipEndStr,jdbcType=VARCHAR},",
          "city_id = #{cityId,jdbcType=INTEGER},",
          "city_name = #{cityName,jdbcType=VARCHAR},",
          "province_id = #{provinceId,jdbcType=INTEGER},",
          "province_name = #{provinceName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbIp2addressDic record);
}