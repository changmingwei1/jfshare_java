package com.jfshare.aftersale.model.mapper;

import com.jfshare.aftersale.model.TbAfterSale;
import com.jfshare.aftersale.model.TbAfterSaleExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TbAfterSaleMapper {
    int countByExample(TbAfterSaleExample example);

    int deleteByExample(TbAfterSaleExample example);

    @Insert({
        "insert into tb_after_sale (user_id, seller_id, ",
        "order_id, product_id, ",
        "sku_num, type, reason, ",
        "user_comment, approve_comment, ",
        "state, apply_time, ",
        "approve_time)",
        "values (#{userId,jdbcType=INTEGER}, #{sellerId,jdbcType=INTEGER}, ",
        "#{orderId,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
        "#{skuNum,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, #{reason,jdbcType=VARCHAR}, ",
        "#{userComment,jdbcType=VARCHAR}, #{approveComment,jdbcType=VARCHAR}, ",
        "#{state,jdbcType=INTEGER}, #{applyTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{approveTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbAfterSale record);

    int insertSelective(TbAfterSale record);

    List<TbAfterSale> selectByExample(TbAfterSaleExample example);
    List<TbAfterSale> selectCountByExample(TbAfterSaleExample example);

    int updateByExampleSelective(@Param("record") TbAfterSale record, @Param("example") TbAfterSaleExample example);

    int updateByExample(@Param("record") TbAfterSale record, @Param("example") TbAfterSaleExample example);
}