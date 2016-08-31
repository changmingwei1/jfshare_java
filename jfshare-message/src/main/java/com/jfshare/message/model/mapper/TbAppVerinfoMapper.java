package com.jfshare.message.model.mapper;

import com.jfshare.message.model.TbAppVerinfo;
import com.jfshare.message.model.TbAppVerinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbAppVerinfoMapper {
    int countByExample(TbAppVerinfoExample example);

    int deleteByExample(TbAppVerinfoExample example);

    @Delete({
        "delete from tb_app_verinfo",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_app_verinfo (id, app_type, ",
        "version, min_version, ",
        "max_version, upgrade_type, ",
        "url, upgrade_desc, ",
        "create_time)",
        "values (#{id,jdbcType=INTEGER}, #{appType,jdbcType=INTEGER}, ",
        "#{version,jdbcType=VARCHAR}, #{minVersion,jdbcType=VARCHAR}, ",
        "#{maxVersion,jdbcType=VARCHAR}, #{upgradeType,jdbcType=INTEGER}, ",
        "#{url,jdbcType=VARCHAR}, #{upgradeDesc,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbAppVerinfo record);

    int insertSelective(TbAppVerinfo record);

    List<TbAppVerinfo> selectByExample(TbAppVerinfoExample example);

    @Select({
        "select",
        "id, app_type, version, min_version, max_version, upgrade_type, url, upgrade_desc, ",
        "create_time",
        "from tb_app_verinfo",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbAppVerinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAppVerinfo record, @Param("example") TbAppVerinfoExample example);

    int updateByExample(@Param("record") TbAppVerinfo record, @Param("example") TbAppVerinfoExample example);

    int updateByPrimaryKeySelective(TbAppVerinfo record);

    @Update({
        "update tb_app_verinfo",
        "set app_type = #{appType,jdbcType=INTEGER},",
          "version = #{version,jdbcType=VARCHAR},",
          "min_version = #{minVersion,jdbcType=VARCHAR},",
          "max_version = #{maxVersion,jdbcType=VARCHAR},",
          "upgrade_type = #{upgradeType,jdbcType=INTEGER},",
          "url = #{url,jdbcType=VARCHAR},",
          "upgrade_desc = #{upgradeDesc,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbAppVerinfo record);
}