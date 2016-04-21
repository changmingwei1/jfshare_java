package com.jfshare.product.model.mapper;

import com.jfshare.product.model.TbProduct;
import com.jfshare.product.model.TbProductExample;
import com.jfshare.product.model.TbProductWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbProductMapper {
    int countByExample(TbProductExample example);

    int deleteByExample(TbProductExample example);

    @Delete({
        "delete from tb_product",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    @Insert({
        "insert into tb_product (id, seller_id, ",
        "name, vice_name, ",
        "subject_id, brand_id, ",
        "img_key, detail_key, ",
        "max_buy_limit, active_state, ",
        "product_snapshoot_id, type, ",
        "remark, create_time, ",
        "create_user_id, last_update_time, ",
        "last_update_id, state, ",
        "tags, sku_template, ",
        "attribute)",
        "values (#{id,jdbcType=VARCHAR}, #{sellerId,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{viceName,jdbcType=VARCHAR}, ",
        "#{subjectId,jdbcType=INTEGER}, #{brandId,jdbcType=INTEGER}, ",
        "#{imgKey,jdbcType=VARCHAR}, #{detailKey,jdbcType=VARCHAR}, ",
        "#{maxBuyLimit,jdbcType=INTEGER}, #{activeState,jdbcType=INTEGER}, ",
        "#{productSnapshootId,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=INTEGER}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateId,jdbcType=INTEGER}, #{state,jdbcType=INTEGER}, ",
        "#{tags,jdbcType=VARCHAR}, #{skuTemplate,jdbcType=LONGVARCHAR}, ",
        "#{attribute,jdbcType=LONGVARCHAR})"
    })
    int insert(TbProductWithBLOBs record);

    int insertSelective(TbProductWithBLOBs record);

    List<TbProductWithBLOBs> selectByExampleWithBLOBs(TbProductExample example);

    List<TbProduct> selectByExample(TbProductExample example);

    @Select({
        "select",
        "id, seller_id, name, vice_name, subject_id, brand_id, img_key, detail_key, max_buy_limit, ",
        "active_state, product_snapshoot_id, type, remark, create_time, create_user_id, ",
        "last_update_time, last_update_id, state, tags, sku_template, attribute",
        "from tb_product",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("ResultMapWithBLOBs")
    TbProductWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbProductWithBLOBs record, @Param("example") TbProductExample example);

    int updateByExampleWithBLOBs(@Param("record") TbProductWithBLOBs record, @Param("example") TbProductExample example);

    int updateByExample(@Param("record") TbProduct record, @Param("example") TbProductExample example);

    int updateByPrimaryKeySelective(TbProductWithBLOBs record);

    @Update({
        "update tb_product",
        "set seller_id = #{sellerId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "vice_name = #{viceName,jdbcType=VARCHAR},",
          "subject_id = #{subjectId,jdbcType=INTEGER},",
          "brand_id = #{brandId,jdbcType=INTEGER},",
          "img_key = #{imgKey,jdbcType=VARCHAR},",
          "detail_key = #{detailKey,jdbcType=VARCHAR},",
          "max_buy_limit = #{maxBuyLimit,jdbcType=INTEGER},",
          "active_state = #{activeState,jdbcType=INTEGER},",
          "product_snapshoot_id = #{productSnapshootId,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "tags = #{tags,jdbcType=VARCHAR},",
          "sku_template = #{skuTemplate,jdbcType=LONGVARCHAR},",
          "attribute = #{attribute,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(TbProductWithBLOBs record);

    @Update({
        "update tb_product",
        "set seller_id = #{sellerId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "vice_name = #{viceName,jdbcType=VARCHAR},",
          "subject_id = #{subjectId,jdbcType=INTEGER},",
          "brand_id = #{brandId,jdbcType=INTEGER},",
          "img_key = #{imgKey,jdbcType=VARCHAR},",
          "detail_key = #{detailKey,jdbcType=VARCHAR},",
          "max_buy_limit = #{maxBuyLimit,jdbcType=INTEGER},",
          "active_state = #{activeState,jdbcType=INTEGER},",
          "product_snapshoot_id = #{productSnapshootId,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "tags = #{tags,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(TbProduct record);
}