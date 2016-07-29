package com.jfshare.product.model.mapper;

import com.jfshare.product.model.TbThirdPartyProduct;
import com.jfshare.product.model.TbThirdPartyProductExample;
import com.jfshare.product.model.TbThirdPartyProductWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbThirdPartyProductMapper {
    int countByExample(TbThirdPartyProductExample example);

    int deleteByExample(TbThirdPartyProductExample example);

    @Delete({
        "delete from tb_third_party_product",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_third_party_product (id, third_party_product_id, ",
        "third_party_identify, name, ",
        "vice_name, subject_id, ",
        "brand_id, img_key, ",
        "detail_key, active_state, ",
        "product_snapshoot_id, type, ",
        "create_time, create_user_id, ",
        "last_update_time, last_update_id, ",
        "state, tags, storehouse_ids, ",
        "postage_id, stock_state, ",
        "price_state, offer_state, ",
        "sku_template, attribute)",
        "values (#{id,jdbcType=INTEGER}, #{thirdPartyProductId,jdbcType=INTEGER}, ",
        "#{thirdPartyIdentify,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{viceName,jdbcType=VARCHAR}, #{subjectId,jdbcType=INTEGER}, ",
        "#{brandId,jdbcType=INTEGER}, #{imgKey,jdbcType=VARCHAR}, ",
        "#{detailKey,jdbcType=VARCHAR}, #{activeState,jdbcType=INTEGER}, ",
        "#{productSnapshootId,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=INTEGER}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{lastUpdateId,jdbcType=INTEGER}, ",
        "#{state,jdbcType=INTEGER}, #{tags,jdbcType=VARCHAR}, #{storehouseIds,jdbcType=VARCHAR}, ",
        "#{postageId,jdbcType=INTEGER}, #{stockState,jdbcType=INTEGER}, ",
        "#{priceState,jdbcType=INTEGER}, #{offerState,jdbcType=INTEGER}, ",
        "#{skuTemplate,jdbcType=LONGVARCHAR}, #{attribute,jdbcType=LONGVARCHAR})"
    })
    int insert(TbThirdPartyProductWithBLOBs record);

    int insertSelective(TbThirdPartyProductWithBLOBs record);

    List<TbThirdPartyProductWithBLOBs> selectByExampleWithBLOBs(TbThirdPartyProductExample example);

    List<TbThirdPartyProduct> selectByExample(TbThirdPartyProductExample example);

    @Select({
        "select",
        "id, third_party_product_id, third_party_identify, name, vice_name, subject_id, ",
        "brand_id, img_key, detail_key, active_state, product_snapshoot_id, type, create_time, ",
        "create_user_id, last_update_time, last_update_id, state, tags, storehouse_ids, ",
        "postage_id, stock_state, price_state, offer_state, sku_template, attribute",
        "from tb_third_party_product",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("ResultMapWithBLOBs")
    TbThirdPartyProductWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbThirdPartyProductWithBLOBs record, @Param("example") TbThirdPartyProductExample example);

    int updateByExampleWithBLOBs(@Param("record") TbThirdPartyProductWithBLOBs record, @Param("example") TbThirdPartyProductExample example);

    int updateByExample(@Param("record") TbThirdPartyProduct record, @Param("example") TbThirdPartyProductExample example);

    int updateByPrimaryKeySelective(TbThirdPartyProductWithBLOBs record);

    @Update({
        "update tb_third_party_product",
        "set third_party_product_id = #{thirdPartyProductId,jdbcType=INTEGER},",
          "third_party_identify = #{thirdPartyIdentify,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "vice_name = #{viceName,jdbcType=VARCHAR},",
          "subject_id = #{subjectId,jdbcType=INTEGER},",
          "brand_id = #{brandId,jdbcType=INTEGER},",
          "img_key = #{imgKey,jdbcType=VARCHAR},",
          "detail_key = #{detailKey,jdbcType=VARCHAR},",
          "active_state = #{activeState,jdbcType=INTEGER},",
          "product_snapshoot_id = #{productSnapshootId,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "tags = #{tags,jdbcType=VARCHAR},",
          "storehouse_ids = #{storehouseIds,jdbcType=VARCHAR},",
          "postage_id = #{postageId,jdbcType=INTEGER},",
          "stock_state = #{stockState,jdbcType=INTEGER},",
          "price_state = #{priceState,jdbcType=INTEGER},",
          "offer_state = #{offerState,jdbcType=INTEGER},",
          "sku_template = #{skuTemplate,jdbcType=LONGVARCHAR},",
          "attribute = #{attribute,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(TbThirdPartyProductWithBLOBs record);

    @Update({
        "update tb_third_party_product",
        "set third_party_product_id = #{thirdPartyProductId,jdbcType=INTEGER},",
          "third_party_identify = #{thirdPartyIdentify,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "vice_name = #{viceName,jdbcType=VARCHAR},",
          "subject_id = #{subjectId,jdbcType=INTEGER},",
          "brand_id = #{brandId,jdbcType=INTEGER},",
          "img_key = #{imgKey,jdbcType=VARCHAR},",
          "detail_key = #{detailKey,jdbcType=VARCHAR},",
          "active_state = #{activeState,jdbcType=INTEGER},",
          "product_snapshoot_id = #{productSnapshootId,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "last_update_id = #{lastUpdateId,jdbcType=INTEGER},",
          "state = #{state,jdbcType=INTEGER},",
          "tags = #{tags,jdbcType=VARCHAR},",
          "storehouse_ids = #{storehouseIds,jdbcType=VARCHAR},",
          "postage_id = #{postageId,jdbcType=INTEGER},",
          "stock_state = #{stockState,jdbcType=INTEGER},",
          "price_state = #{priceState,jdbcType=INTEGER},",
          "offer_state = #{offerState,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbThirdPartyProduct record);
}