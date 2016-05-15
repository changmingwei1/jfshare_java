package com.jfshare.baseTemplate.mybatis.mapper.automatic;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface TbPostageTemplateMapper {
    int countByExample(TbPostageTemplateExample example);

    int deleteByExample(TbPostageTemplateExample example);

    @Delete({
        "delete from tb_postage_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_postage_template (seller_id, name, ",
        "type, postage_info, ",
        "template_group, template_desc, ",
        "create_time, ",
        "create_id, last_update_time, ",
        "last_update_id, deleted)",
        "values (#{sellerId,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{postageInfo,jdbcType=VARCHAR}, ",
        "#{templateGroup,jdbcType=INTEGER}, #{templateDesc,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{createId,jdbcType=INTEGER}, #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{lastUpdateId,jdbcType=INTEGER}, #{deleted,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(TbPostageTemplate record);

    int insertSelective(TbPostageTemplate record);

    List<TbPostageTemplate> selectByExample(TbPostageTemplateExample example);

    @Select({
        "select",
        "id, seller_id, name, type, postage_info, template_group, template_desc, create_time, ",
        "create_id, last_update_time, last_update_id, deleted",
        "from tb_postage_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbPostageTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbPostageTemplate record, @Param("example") TbPostageTemplateExample example);

    int updateByExample(@Param("record") TbPostageTemplate record, @Param("example") TbPostageTemplateExample example);

    int updateByPrimaryKeySelective(TbPostageTemplate record);

    @Update({
        "update tb_postage_template",
        "set seller_id = #{sellerId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "postage_info = #{postageInfo,jdbcType=VARCHAR},",
          "template_group = #{templateGroup,jdbcType=INTEGER},",
          "template_desc = #{templateDesc,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "create_id = #{createId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbPostageTemplate record);
}