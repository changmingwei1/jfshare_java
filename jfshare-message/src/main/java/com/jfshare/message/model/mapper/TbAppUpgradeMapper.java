package com.jfshare.message.model.mapper;

import com.jfshare.message.model.TbAppUpgrade;
import com.jfshare.message.model.TbAppUpgradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbAppUpgradeMapper {
    int countByExample(TbAppUpgradeExample example);

    int deleteByExample(TbAppUpgradeExample example);

    @Delete({
        "delete from tb_app_upgrade",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_app_upgrade (id, app_type, ",
        "version, min_version, ",
        "max_version, upgrade_type, ",
        "url, upgrade_desc, ",
        "create_time)",
        "values (#{id,jdbcType=INTEGER}, #{appType,jdbcType=INTEGER}, ",
        "#{version,jdbcType=INTEGER}, #{minVersion,jdbcType=INTEGER}, ",
        "#{maxVersion,jdbcType=INTEGER}, #{upgradeType,jdbcType=INTEGER}, ",
        "#{url,jdbcType=VARCHAR}, #{upgradeDesc,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbAppUpgrade record);

    int insertSelective(TbAppUpgrade record);

    List<TbAppUpgrade> selectByExample(TbAppUpgradeExample example);

    @Select({
        "select",
        "id, app_type, version, min_version, max_version, upgrade_type, url, upgrade_desc, ",
        "create_time",
        "from tb_app_upgrade",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbAppUpgrade selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAppUpgrade record, @Param("example") TbAppUpgradeExample example);

    int updateByExample(@Param("record") TbAppUpgrade record, @Param("example") TbAppUpgradeExample example);

    int updateByPrimaryKeySelective(TbAppUpgrade record);

    @Update({
        "update tb_app_upgrade",
        "set app_type = #{appType,jdbcType=INTEGER},",
          "version = #{version,jdbcType=INTEGER},",
          "min_version = #{minVersion,jdbcType=INTEGER},",
          "max_version = #{maxVersion,jdbcType=INTEGER},",
          "upgrade_type = #{upgradeType,jdbcType=INTEGER},",
          "url = #{url,jdbcType=VARCHAR},",
          "upgrade_desc = #{upgradeDesc,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbAppUpgrade record);
}