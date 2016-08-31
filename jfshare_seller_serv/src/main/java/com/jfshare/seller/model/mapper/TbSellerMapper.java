package com.jfshare.seller.model.mapper;

import com.jfshare.seller.model.TbSeller;
import com.jfshare.seller.model.TbSellerExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbSellerMapper {
    int countByExample(TbSellerExample example);

    int deleteByExample(TbSellerExample example);

    @Delete({
        "delete from tb_seller",
        "where seller_id = #{sellerId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer sellerId);

    @Insert({
        "insert into tb_seller (seller_id, login_name, ",
        "seller_name, shop_name, ",
        "company_name, contact_name, ",
        "open_bank, account_holder, ",
        "account_number, pwd_enc, ",
        "fav_img, state, birthday, ",
        "sex, id_card, mobile, ",
        "tel, email, provinceId, ",
        "provinceName, cityId, ",
        "cityName, countyId, ",
        "countyName, address, ",
        "postCode, remark, ",
        "serial, create_time, ",
        "last_update_time)",
        "values (#{sellerId,jdbcType=INTEGER}, #{loginName,jdbcType=VARCHAR}, ",
        "#{sellerName,jdbcType=VARCHAR}, #{shopName,jdbcType=VARCHAR}, ",
        "#{companyName,jdbcType=VARCHAR}, #{contactName,jdbcType=VARCHAR}, ",
        "#{openBank,jdbcType=VARCHAR}, #{accountHolder,jdbcType=VARCHAR}, ",
        "#{accountNumber,jdbcType=VARCHAR}, #{pwdEnc,jdbcType=VARCHAR}, ",
        "#{favImg,jdbcType=VARCHAR}, #{state,jdbcType=TINYINT}, #{birthday,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{sex,jdbcType=TINYINT}, #{idCard,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{tel,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{provinceid,jdbcType=INTEGER}, ",
        "#{provincename,jdbcType=VARCHAR}, #{cityid,jdbcType=INTEGER}, ",
        "#{cityname,jdbcType=VARCHAR}, #{countyid,jdbcType=INTEGER}, ",
        "#{countyname,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{postcode,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
        "#{serial,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbSeller record);

    int insertSelective(TbSeller record);

    List<TbSeller> selectByExample(TbSellerExample example);

    @Select({
        "select",
        "seller_id, login_name, seller_name, shop_name, company_name, contact_name, open_bank, ",
        "account_holder, account_number, pwd_enc, fav_img, state, birthday, sex, id_card, ",
        "mobile, tel, email, provinceId, provinceName, cityId, cityName, countyId, countyName, ",
        "address, postCode, remark, serial, create_time, last_update_time",
        "from tb_seller",
        "where seller_id = #{sellerId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbSeller selectByPrimaryKey(Integer sellerId);

    int updateByExampleSelective(@Param("record") TbSeller record, @Param("example") TbSellerExample example);

    int updateByExample(@Param("record") TbSeller record, @Param("example") TbSellerExample example);

    int updateByPrimaryKeySelective(TbSeller record);

    @Update({
        "update tb_seller",
        "set login_name = #{loginName,jdbcType=VARCHAR},",
          "seller_name = #{sellerName,jdbcType=VARCHAR},",
          "shop_name = #{shopName,jdbcType=VARCHAR},",
          "company_name = #{companyName,jdbcType=VARCHAR},",
          "contact_name = #{contactName,jdbcType=VARCHAR},",
          "open_bank = #{openBank,jdbcType=VARCHAR},",
          "account_holder = #{accountHolder,jdbcType=VARCHAR},",
          "account_number = #{accountNumber,jdbcType=VARCHAR},",
          "pwd_enc = #{pwdEnc,jdbcType=VARCHAR},",
          "fav_img = #{favImg,jdbcType=VARCHAR},",
          "state = #{state,jdbcType=TINYINT},",
          "birthday = #{birthday,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "sex = #{sex,jdbcType=TINYINT},",
          "id_card = #{idCard,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "tel = #{tel,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "provinceId = #{provinceid,jdbcType=INTEGER},",
          "provinceName = #{provincename,jdbcType=VARCHAR},",
          "cityId = #{cityid,jdbcType=INTEGER},",
          "cityName = #{cityname,jdbcType=VARCHAR},",
          "countyId = #{countyid,jdbcType=INTEGER},",
          "countyName = #{countyname,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "postCode = #{postcode,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "serial = #{serial,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where seller_id = #{sellerId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbSeller record);
}