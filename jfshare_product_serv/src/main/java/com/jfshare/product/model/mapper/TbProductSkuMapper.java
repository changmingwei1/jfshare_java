package com.jfshare.product.model.mapper;

import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.TbProductSkuExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbProductSkuMapper {
    int countByExample(TbProductSkuExample example);

    int deleteByExample(TbProductSkuExample example);

    @Delete({
        "delete from tb_product_sku",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_product_sku (id, product_id, ",
        "sku_num, sku_name, ",
        "seller_class_num, shelf, ",
        "cur_price, org_price, ",
        "comment, last_update_time, ",
        "last_update_user_id, create_time, ",
        "create_user_id, v_picture, ",
        "ref_price, weight, ",
        "storehouse_id)",
        "values (#{id,jdbcType=INTEGER}, #{productId,jdbcType=VARCHAR}, ",
        "#{skuNum,jdbcType=VARCHAR}, #{skuName,jdbcType=VARCHAR}, ",
        "#{sellerClassNum,jdbcType=VARCHAR}, #{shelf,jdbcType=VARCHAR}, ",
        "#{curPrice,jdbcType=INTEGER}, #{orgPrice,jdbcType=INTEGER}, ",
        "#{comment,jdbcType=VARCHAR}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateUserId,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=INTEGER}, #{vPicture,jdbcType=VARCHAR}, ",
        "#{refPrice,jdbcType=INTEGER}, #{weight,jdbcType=VARCHAR}, ",
        "#{storehouseId,jdbcType=INTEGER})"
    })
    int insert(TbProductSku record);

    int insertSelective(TbProductSku record);

    List<TbProductSku> selectByExample(TbProductSkuExample example);

    @Select({
        "select",
        "id, product_id, sku_num, sku_name, seller_class_num, shelf, cur_price, org_price, ",
        "comment, last_update_time, last_update_user_id, create_time, create_user_id, ",
        "v_picture, ref_price, weight, storehouse_id",
        "from tb_product_sku",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbProductSku selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductSku record, @Param("example") TbProductSkuExample example);

    int updateByExample(@Param("record") TbProductSku record, @Param("example") TbProductSkuExample example);

    int updateByPrimaryKeySelective(TbProductSku record);

    @Update({
        "update tb_product_sku",
        "set product_id = #{productId,jdbcType=VARCHAR},",
          "sku_num = #{skuNum,jdbcType=VARCHAR},",
          "sku_name = #{skuName,jdbcType=VARCHAR},",
          "seller_class_num = #{sellerClassNum,jdbcType=VARCHAR},",
          "shelf = #{shelf,jdbcType=VARCHAR},",
          "cur_price = #{curPrice,jdbcType=INTEGER},",
          "org_price = #{orgPrice,jdbcType=INTEGER},",
          "comment = #{comment,jdbcType=VARCHAR},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_user_id = #{lastUpdateUserId,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "v_picture = #{vPicture,jdbcType=VARCHAR},",
          "ref_price = #{refPrice,jdbcType=INTEGER},",
          "weight = #{weight,jdbcType=VARCHAR},",
          "storehouse_id = #{storehouseId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbProductSku record);
}