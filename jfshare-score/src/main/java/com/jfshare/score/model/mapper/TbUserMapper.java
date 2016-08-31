package com.jfshare.score.model.mapper;

import com.jfshare.score.model.TbUser;
import com.jfshare.score.model.TbUserExample;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;


public interface TbUserMapper {
    int countByExample(TbUserExample example);

    int deleteByExample(TbUserExample example);

    @Delete({
        "delete from tb_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userId);

    @Insert({
        "insert into tb_user (user_id, login_name, ",
        "user_name, pwd_enc, ",
        "fav_img, state, birthday, ",
        "sex, id_card, mobile, ",
        "tel, email, provinceId, ",
        "provinceName, cityId, ",
        "cityName, countyId, ",
        "countyName, address, ",
        "postCode, degree, ",
        "salary, remark, ",
        "serial, create_time, ",
        "last_update_time)",
        "values (#{userId,jdbcType=INTEGER}, #{loginName,jdbcType=VARCHAR}, ",
        "#{userName,jdbcType=VARCHAR}, #{pwdEnc,jdbcType=VARCHAR}, ",
        "#{favImg,jdbcType=VARCHAR}, #{state,jdbcType=INTEGER}, #{birthday,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{sex,jdbcType=INTEGER}, #{idCard,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
        "#{tel,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{provinceid,jdbcType=INTEGER}, ",
        "#{provincename,jdbcType=VARCHAR}, #{cityid,jdbcType=INTEGER}, ",
        "#{cityname,jdbcType=VARCHAR}, #{countyid,jdbcType=INTEGER}, ",
        "#{countyname,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{postcode,jdbcType=VARCHAR}, #{degree,jdbcType=INTEGER}, ",
        "#{salary,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, ",
        "#{serial,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    @SelectKey(before = false, keyProperty = "userId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS user_id")
    int insert(TbUser record);

    int insertSelective(TbUser record);

    List<TbUser> selectByExample(TbUserExample example);

    @Select({
        "select",
        "user_id, login_name, user_name, pwd_enc, fav_img, state, birthday, sex, id_card, ",
        "mobile, tel, email, provinceId, provinceName, cityId, cityName, countyId, countyName, ",
        "address, postCode, degree, salary, remark, serial, create_time, last_update_time",
        "from tb_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbUser selectByPrimaryKey(Integer userId);
    
    @Select({
        "select",
        "user_id, login_name, user_name, pwd_enc, fav_img, state, birthday, sex, id_card, ",
        "mobile, tel, email, provinceId, provinceName, cityId, cityName, countyId, countyName, ",
        "address, postCode, degree, salary, remark, serial, create_time, last_update_time",
        "from tb_user",
        "where mobile = #{mobile,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    TbUser selectByMobile(String mobile);

    int updateByExampleSelective(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByExample(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    @Update({
        "update tb_user",
        "set login_name = #{loginName,jdbcType=VARCHAR},",
          "user_name = #{userName,jdbcType=VARCHAR},",
          "pwd_enc = #{pwdEnc,jdbcType=VARCHAR},",
          "fav_img = #{favImg,jdbcType=VARCHAR},",
          "state = #{state,jdbcType=INTEGER},",
          "birthday = #{birthday,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "sex = #{sex,jdbcType=INTEGER},",
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
          "degree = #{degree,jdbcType=INTEGER},",
          "salary = #{salary,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "serial = #{serial,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbUser record);
}