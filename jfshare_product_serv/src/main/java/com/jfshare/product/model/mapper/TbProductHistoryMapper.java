package com.jfshare.product.model.mapper;

import com.jfshare.product.model.TbProductHistory;
import com.jfshare.product.model.TbProductHistoryExample;
import com.jfshare.product.model.TbProductHistoryWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbProductHistoryMapper {
    int countByExample(TbProductHistoryExample example);

    int deleteByExample(TbProductHistoryExample example);

    @Delete({
        "delete from tb_product_history",
        "where product_snapshoot_id = #{productSnapshootId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String productSnapshootId);

    @Insert({
        "insert into tb_product_history (product_snapshoot_id, product_id, ",
        "seller_id, name, ",
        "vice_name, subject_id, ",
        "brand_id, img_key, ",
        "detail_key, max_buy_limit, ",
        "active_state, type, ",
        "remark, create_time, ",
        "create_user_id, last_update_time, ",
        "last_update_id, activeState, ",
        "state, tags, sku_template, ",
        "attribute, product_sku)",
        "values (#{productSnapshootId,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
        "#{sellerId,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{viceName,jdbcType=VARCHAR}, #{subjectId,jdbcType=INTEGER}, ",
        "#{brandId,jdbcType=INTEGER}, #{imgKey,jdbcType=VARCHAR}, ",
        "#{detailKey,jdbcType=VARCHAR}, #{maxBuyLimit,jdbcType=INTEGER}, ",
        "#{activeState,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=INTEGER}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateId,jdbcType=INTEGER}, #{activestate,jdbcType=INTEGER}, ",
        "#{state,jdbcType=INTEGER}, #{tags,jdbcType=VARCHAR}, #{skuTemplate,jdbcType=LONGVARCHAR}, ",
        "#{attribute,jdbcType=LONGVARCHAR}, #{productSku,jdbcType=LONGVARCHAR})"
    })
    int insert(TbProductHistoryWithBLOBs record);

    int insertSelective(TbProductHistoryWithBLOBs record);

    List<TbProductHistoryWithBLOBs> selectByExampleWithBLOBs(TbProductHistoryExample example);

    List<TbProductHistory> selectByExample(TbProductHistoryExample example);

    @Select({
        "select",
        "product_snapshoot_id, product_id, seller_id, name, vice_name, subject_id, brand_id, ",
        "img_key, detail_key, max_buy_limit, active_state, type, remark, create_time, ",
        "create_user_id, last_update_time, last_update_id, activeState, state, tags, ",
        "sku_template, attribute, product_sku",
        "from tb_product_history",
        "where product_snapshoot_id = #{productSnapshootId,jdbcType=VARCHAR}"
    })
    @ResultMap("ResultMapWithBLOBs")
    TbProductHistoryWithBLOBs selectByPrimaryKey(String productSnapshootId);

    int updateByExampleSelective(@Param("record") TbProductHistoryWithBLOBs record, @Param("example") TbProductHistoryExample example);

    int updateByExampleWithBLOBs(@Param("record") TbProductHistoryWithBLOBs record, @Param("example") TbProductHistoryExample example);

    int updateByExample(@Param("record") TbProductHistory record, @Param("example") TbProductHistoryExample example);

    int updateByPrimaryKeySelective(TbProductHistoryWithBLOBs record);

    @Update({
        "update tb_product_history",
        "set product_id = #{productId,jdbcType=VARCHAR},",
          "seller_id = #{sellerId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "vice_name = #{viceName,jdbcType=VARCHAR},",
          "subject_id = #{subjectId,jdbcType=INTEGER},",
          "brand_id = #{brandId,jdbcType=INTEGER},",
          "img_key = #{imgKey,jdbcType=VARCHAR},",
          "detail_key = #{detailKey,jdbcType=VARCHAR},",
          "max_buy_limit = #{maxBuyLimit,jdbcType=INTEGER},",
          "active_state = #{activeState,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "activeState = #{activestate,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "tags = #{tags,jdbcType=VARCHAR},",
          "sku_template = #{skuTemplate,jdbcType=LONGVARCHAR},",
          "attribute = #{attribute,jdbcType=LONGVARCHAR},",
          "product_sku = #{productSku,jdbcType=LONGVARCHAR}",
        "where product_snapshoot_id = #{productSnapshootId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(TbProductHistoryWithBLOBs record);

    @Update({
        "update tb_product_history",
        "set product_id = #{productId,jdbcType=VARCHAR},",
          "seller_id = #{sellerId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "vice_name = #{viceName,jdbcType=VARCHAR},",
          "subject_id = #{subjectId,jdbcType=INTEGER},",
          "brand_id = #{brandId,jdbcType=INTEGER},",
          "img_key = #{imgKey,jdbcType=VARCHAR},",
          "detail_key = #{detailKey,jdbcType=VARCHAR},",
          "max_buy_limit = #{maxBuyLimit,jdbcType=INTEGER},",
          "active_state = #{activeState,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "activeState = #{activestate,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "tags = #{tags,jdbcType=VARCHAR}",
        "where product_snapshoot_id = #{productSnapshootId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(TbProductHistory record);
}