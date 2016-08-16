package com.jfshare.product.model.mapper;

import com.jfshare.product.model.TbThirdPartyProductSnapshot;
import com.jfshare.product.model.TbThirdPartyProductSnapshotExample;
import com.jfshare.product.model.TbThirdPartyProductSnapshotWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbThirdPartyProductSnapshotMapper {
    int countByExample(TbThirdPartyProductSnapshotExample example);

    int deleteByExample(TbThirdPartyProductSnapshotExample example);

    @Delete({
        "delete from tb_third_party_product_snapshot",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_third_party_product_snapshot (id, product_id, ",
        "third_party_product_id, third_party_identify, ",
        "name, vice_name, ",
        "img_key, detail_key, ",
        "active_state, product_snapshoot_id, ",
        "type, create_time, ",
        "create_user_id, last_update_time, ",
        "last_update_id, state, ",
        "tags, storehouse_ids, ",
        "postage_id, stock_state, ",
        "price_state, offer_state, ",
        "stock_info, sellerClassNum, ",
        "price, sku_template, ",
        "attribute)",
        "values (#{id,jdbcType=INTEGER}, #{productId,jdbcType=VARCHAR}, ",
        "#{thirdPartyProductId,jdbcType=VARCHAR}, #{thirdPartyIdentify,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{viceName,jdbcType=VARCHAR}, ",
        "#{imgKey,jdbcType=VARCHAR}, #{detailKey,jdbcType=VARCHAR}, ",
        "#{activeState,jdbcType=INTEGER}, #{productSnapshootId,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=INTEGER}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateId,jdbcType=INTEGER}, #{state,jdbcType=INTEGER}, ",
        "#{tags,jdbcType=VARCHAR}, #{storehouseIds,jdbcType=VARCHAR}, ",
        "#{postageId,jdbcType=INTEGER}, #{stockState,jdbcType=INTEGER}, ",
        "#{priceState,jdbcType=INTEGER}, #{offerState,jdbcType=INTEGER}, ",
        "#{stockInfo,jdbcType=VARCHAR}, #{sellerclassnum,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=INTEGER}, #{skuTemplate,jdbcType=LONGVARCHAR}, ",
        "#{attribute,jdbcType=LONGVARCHAR})"
    })
    int insert(TbThirdPartyProductSnapshotWithBLOBs record);

    int insertSelective(TbThirdPartyProductSnapshotWithBLOBs record);

    List<TbThirdPartyProductSnapshotWithBLOBs> selectByExampleWithBLOBs(TbThirdPartyProductSnapshotExample example);

    List<TbThirdPartyProductSnapshot> selectByExample(TbThirdPartyProductSnapshotExample example);

    @Select({
        "select",
        "id, product_id, third_party_product_id, third_party_identify, name, vice_name, ",
        "img_key, detail_key, active_state, product_snapshoot_id, type, create_time, ",
        "create_user_id, last_update_time, last_update_id, state, tags, storehouse_ids, ",
        "postage_id, stock_state, price_state, offer_state, stock_info, sellerClassNum, ",
        "price, sku_template, attribute",
        "from tb_third_party_product_snapshot",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("ResultMapWithBLOBs")
    TbThirdPartyProductSnapshotWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbThirdPartyProductSnapshotWithBLOBs record, @Param("example") TbThirdPartyProductSnapshotExample example);

    int updateByExampleWithBLOBs(@Param("record") TbThirdPartyProductSnapshotWithBLOBs record, @Param("example") TbThirdPartyProductSnapshotExample example);

    int updateByExample(@Param("record") TbThirdPartyProductSnapshot record, @Param("example") TbThirdPartyProductSnapshotExample example);

    int updateByPrimaryKeySelective(TbThirdPartyProductSnapshotWithBLOBs record);

    @Update({
        "update tb_third_party_product_snapshot",
        "set product_id = #{productId,jdbcType=VARCHAR},",
          "third_party_product_id = #{thirdPartyProductId,jdbcType=VARCHAR},",
          "third_party_identify = #{thirdPartyIdentify,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "vice_name = #{viceName,jdbcType=VARCHAR},",
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
          "stock_info = #{stockInfo,jdbcType=VARCHAR},",
          "sellerClassNum = #{sellerclassnum,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=INTEGER},",
          "sku_template = #{skuTemplate,jdbcType=LONGVARCHAR},",
          "attribute = #{attribute,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(TbThirdPartyProductSnapshotWithBLOBs record);

    @Update({
        "update tb_third_party_product_snapshot",
        "set product_id = #{productId,jdbcType=VARCHAR},",
          "third_party_product_id = #{thirdPartyProductId,jdbcType=VARCHAR},",
          "third_party_identify = #{thirdPartyIdentify,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "vice_name = #{viceName,jdbcType=VARCHAR},",
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
          "stock_info = #{stockInfo,jdbcType=VARCHAR},",
          "sellerClassNum = #{sellerclassnum,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbThirdPartyProductSnapshot record);
}