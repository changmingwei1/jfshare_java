package com.jfshare.product.model.mapper;

import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.TbProductCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
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
        "insert into tb_product_card (id, seller_id, ",
        "product_id, card_number, ",
        "password, transaction_id, ",
        "create_time, create_id, ",
        "last_update_time, last_update_id, ",
        "state, sku_num, buyer_id, ",
        "check_time)",
        "values (#{id,jdbcType=INTEGER}, #{sellerId,jdbcType=INTEGER}, ",
        "#{productId,jdbcType=VARCHAR}, #{cardNumber,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{transactionId,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createId,jdbcType=INTEGER}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{lastUpdateId,jdbcType=INTEGER}, ",
        "#{state,jdbcType=INTEGER}, #{skuNum,jdbcType=VARCHAR}, #{buyerId,jdbcType=INTEGER}, ",
        "#{checkTime,jdbcType=TIMESTAMP})"
    })
    int insert(TbProductCard record);

    int insertSelective(TbProductCard record);

    List<TbProductCard> selectByExample(TbProductCardExample example);

    @Select({
        "select",
        "id, seller_id, product_id, card_number, password, transaction_id, create_time, ",
        "create_id, last_update_time, last_update_id, state, sku_num, buyer_id, check_time",
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
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_id = #{createId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "sku_num = #{skuNum,jdbcType=VARCHAR},",
          "buyer_id = #{buyerId,jdbcType=INTEGER},",
          "check_time = #{checkTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbProductCard record);
}