package com.jfshare.productTask.mybatis.mapper.automatic;

import com.jfshare.productTask.mybatis.model.automatic.TbProductCardStatistics;
import com.jfshare.productTask.mybatis.model.automatic.TbProductCardStatisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface TbProductCardStatisticsMapper {
    int countByExample(TbProductCardStatisticsExample example);

    int deleteByExample(TbProductCardStatisticsExample example);

    @Delete({
        "delete from tb_product_card_statistics",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_product_card_statistics (seller_id, product_id, ",
        "sold_num, checked_num, ",
        "statistics_date)",
        "values (#{sellerId,jdbcType=INTEGER}, #{productId,jdbcType=VARCHAR}, ",
        "#{soldNum,jdbcType=INTEGER}, #{checkedNum,jdbcType=INTEGER}, ",
        "#{statisticsDate,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(TbProductCardStatistics record);

    int insertSelective(TbProductCardStatistics record);

    List<TbProductCardStatistics> selectByExample(TbProductCardStatisticsExample example);

    @Select({
        "select",
        "id, seller_id, product_id, sold_num, checked_num, statistics_date",
        "from tb_product_card_statistics",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbProductCardStatistics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductCardStatistics record, @Param("example") TbProductCardStatisticsExample example);

    int updateByExample(@Param("record") TbProductCardStatistics record, @Param("example") TbProductCardStatisticsExample example);

    int updateByPrimaryKeySelective(TbProductCardStatistics record);

    @Update({
        "update tb_product_card_statistics",
        "set seller_id = #{sellerId,jdbcType=INTEGER},",
          "product_id = #{productId,jdbcType=VARCHAR},",
          "sold_num = #{soldNum,jdbcType=INTEGER},",
          "checked_num = #{checkedNum,jdbcType=INTEGER},",
          "statistics_date = #{statisticsDate,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbProductCardStatistics record);
}