package com.jfshare.baseTemplate.mybatis.mapper.automatic;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouseExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface TbStorehouseMapper {
    int countByExample(TbStorehouseExample example);

    int deleteByExample(TbStorehouseExample example);

    @Delete({
        "delete from tb_storehouse",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_storehouse (seller_id, name, ",
        "support_province, create_time, ",
        "create_id, last_update_time, ",
        "last_update_id, deleted)",
        "values (#{sellerId,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{supportProvince,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{createId,jdbcType=INTEGER}, #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{lastUpdateId,jdbcType=INTEGER}, #{deleted,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(TbStorehouse record);

    int insertSelective(TbStorehouse record);

    List<TbStorehouse> selectByExample(TbStorehouseExample example);

    @Select({
        "select",
        "id, seller_id, name, support_province, create_time, create_id, last_update_time, ",
        "last_update_id, deleted",
        "from tb_storehouse",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbStorehouse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbStorehouse record, @Param("example") TbStorehouseExample example);

    int updateByExample(@Param("record") TbStorehouse record, @Param("example") TbStorehouseExample example);

    int updateByPrimaryKeySelective(TbStorehouse record);

    @Update({
        "update tb_storehouse",
        "set seller_id = #{sellerId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "support_province = #{supportProvince,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "create_id = #{createId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbStorehouse record);
}