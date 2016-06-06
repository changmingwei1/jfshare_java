package com.jfshare.productTask.mybatis.mapper.automatic;

import com.jfshare.productTask.mybatis.model.automatic.TbProductCard;
import com.jfshare.productTask.mybatis.model.automatic.TbProductCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface TbProductCardMapper {
    int countByExample(TbProductCardExample example);

    int deleteByExample(TbProductCardExample example);

    @Delete({
        "delete from tb_product_card",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_product_card (seller_id, product_id, ",
        "card_number, password, ",
        "transaction_id, create_time, ",
        "create_id, last_update_time, ",
        "last_update_id, state, ",
        "sku_num, buyer_id, ",
        "checked_time, ",
        "sold_time)",
        "values (#{sellerId,jdbcType=INTEGER}, #{productId,jdbcType=VARCHAR}, ",
        "#{cardNumber,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{transactionId,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{createId,jdbcType=INTEGER}, #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{lastUpdateId,jdbcType=INTEGER}, #{state,jdbcType=INTEGER}, ",
        "#{skuNum,jdbcType=VARCHAR}, #{buyerId,jdbcType=INTEGER}, ",
        "#{checkedTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{soldTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(TbProductCard record);

    int insertSelective(TbProductCard record);

    List<TbProductCard> selectByExample(TbProductCardExample example);

    @Select({
        "select",
        "id, seller_id, product_id, card_number, password, transaction_id, create_time, ",
        "create_id, last_update_time, last_update_id, state, sku_num, buyer_id, checked_time, ",
        "sold_time",
        "from tb_product_card",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbProductCard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductCard record, @Param("example") TbProductCardExample example);

    int updateByExample(@Param("record") TbProductCard record, @Param("example") TbProductCardExample example);

    int updateByPrimaryKeySelective(TbProductCard record);

    @Update({
        "update tb_product_card",
        "set seller_id = #{sellerId,jdbcType=INTEGER},",
          "product_id = #{productId,jdbcType=VARCHAR},",
          "card_number = #{cardNumber,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "transaction_id = #{transactionId,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "create_id = #{createId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "sku_num = #{skuNum,jdbcType=VARCHAR},",
          "buyer_id = #{buyerId,jdbcType=INTEGER},",
          "checked_time = #{checkedTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "sold_time = #{soldTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbProductCard record);
}