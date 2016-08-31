package com.jfshare.aftersale.model.mapper;

import com.jfshare.aftersale.model.TbAfterSaleOrder;
import com.jfshare.aftersale.model.TbAfterSaleOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbAfterSaleOrderMapper {
    int countByExample(TbAfterSaleOrderExample example);

    int deleteByExample(TbAfterSaleOrderExample example);

    @Delete({
        "delete from tb_after_sale_order",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_after_sale_order (id, user_id, ",
        "seller_id, order_id, ",
        "order_time)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{sellerId,jdbcType=INTEGER}, #{orderId,jdbcType=VARCHAR}, ",
        "#{orderTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbAfterSaleOrder record);

    int insertSelective(TbAfterSaleOrder record);

    List<TbAfterSaleOrder> selectByExample(TbAfterSaleOrderExample example);

    @Select({
        "select",
        "id, user_id, seller_id, order_id, order_time",
        "from tb_after_sale_order",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbAfterSaleOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAfterSaleOrder record, @Param("example") TbAfterSaleOrderExample example);

    int updateByExample(@Param("record") TbAfterSaleOrder record, @Param("example") TbAfterSaleOrderExample example);

    int updateByPrimaryKeySelective(TbAfterSaleOrder record);

    @Update({
        "update tb_after_sale_order",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "seller_id = #{sellerId,jdbcType=INTEGER},",
          "order_id = #{orderId,jdbcType=VARCHAR},",
          "order_time = #{orderTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbAfterSaleOrder record);
}