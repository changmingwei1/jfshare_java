package com.jfshare.address.model.mapper;

import com.jfshare.address.model.TbReceiverAddress;
import com.jfshare.address.model.TbReceiverAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbReceiverAddressMapper {
    int countByExample(TbReceiverAddressExample example);

    int deleteByExample(TbReceiverAddressExample example);

    @Delete({
        "delete from tb_receiver_address",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_receiver_address (id, user_id, ",
        "receiver_name, mobile, ",
        "tel_code, tel, tel_ext_number, ",
        "province_id, province_name, ",
        "city_id, city_name, ",
        "county_id, county_name, ",
        "address, post_code, ",
        "is_default, create_time, ",
        "create_user_id, last_update_time, ",
        "last_update_user_id, email)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{receiverName,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{telCode,jdbcType=VARCHAR}, #{tel,jdbcType=VARCHAR}, #{telExtNumber,jdbcType=VARCHAR}, ",
        "#{provinceId,jdbcType=INTEGER}, #{provinceName,jdbcType=VARCHAR}, ",
        "#{cityId,jdbcType=INTEGER}, #{cityName,jdbcType=VARCHAR}, ",
        "#{countyId,jdbcType=INTEGER}, #{countyName,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{postCode,jdbcType=VARCHAR}, ",
        "#{isDefault,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{createUserId,jdbcType=INTEGER}, #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{lastUpdateUserId,jdbcType=INTEGER}, #{email,jdbcType=VARCHAR})"
    })
    int insert(TbReceiverAddress record);

    int insertSelective(TbReceiverAddress record);

    List<TbReceiverAddress> selectByExample(TbReceiverAddressExample example);

    @Select({
        "select",
        "id, user_id, receiver_name, mobile, tel_code, tel, tel_ext_number, province_id, ",
        "province_name, city_id, city_name, county_id, county_name, address, post_code, ",
        "is_default, create_time, create_user_id, last_update_time, last_update_user_id, ",
        "email",
        "from tb_receiver_address",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbReceiverAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbReceiverAddress record, @Param("example") TbReceiverAddressExample example);

    int updateByExample(@Param("record") TbReceiverAddress record, @Param("example") TbReceiverAddressExample example);

    int updateByPrimaryKeySelective(TbReceiverAddress record);

    @Update({
        "update tb_receiver_address",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "receiver_name = #{receiverName,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "tel_code = #{telCode,jdbcType=VARCHAR},",
          "tel = #{tel,jdbcType=VARCHAR},",
          "tel_ext_number = #{telExtNumber,jdbcType=VARCHAR},",
          "province_id = #{provinceId,jdbcType=INTEGER},",
          "province_name = #{provinceName,jdbcType=VARCHAR},",
          "city_id = #{cityId,jdbcType=INTEGER},",
          "city_name = #{cityName,jdbcType=VARCHAR},",
          "county_id = #{countyId,jdbcType=INTEGER},",
          "county_name = #{countyName,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "post_code = #{postCode,jdbcType=VARCHAR},",
          "is_default = #{isDefault,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "last_update_user_id = #{lastUpdateUserId,jdbcType=INTEGER},",
          "email = #{email,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbReceiverAddress record);
}