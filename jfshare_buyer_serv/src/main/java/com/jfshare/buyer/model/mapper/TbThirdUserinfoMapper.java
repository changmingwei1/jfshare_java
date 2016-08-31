package com.jfshare.buyer.model.mapper;

import com.jfshare.buyer.model.TbThirdUserinfo;
import com.jfshare.buyer.model.TbThirdUserinfoExample;
import com.jfshare.buyer.model.TbThirdUserinfoKey;
import com.jfshare.buyer.model.TbUser;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

public interface TbThirdUserinfoMapper {
    int countByExample(TbThirdUserinfoExample example);

    int deleteByExample(TbThirdUserinfoExample example);

    @Delete({
        "delete from tb_third_userinfo",
        "where mobile = #{mobile,jdbcType=VARCHAR}",
          "and third_type = #{thirdType,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(TbThirdUserinfoKey key);

    @Insert({
        "insert into tb_third_userinfo (mobile, third_type, ",
        "id, user_id, cust_id, ",
        "ext_info, ext1, ext2)",
        "values (#{mobile,jdbcType=VARCHAR}, #{thirdType,jdbcType=INTEGER}, ",
        "#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{custId,jdbcType=VARCHAR}, ",
        "#{extInfo,jdbcType=VARCHAR}, #{ext1,jdbcType=VARCHAR}, #{ext2,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler})"
    })
    int insert(TbThirdUserinfo record);

    int insertSelective(TbThirdUserinfo record);

    List<TbThirdUserinfo> selectByExample(TbThirdUserinfoExample example);

    @Select({
        "select",
        "mobile, third_type, id, user_id, cust_id, ext_info, ext1, ext2, create_time, ",
        "update_time",
        "from tb_third_userinfo",
        "where mobile = #{mobile,jdbcType=VARCHAR}",
          "and third_type = #{thirdType,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbThirdUserinfo selectByPrimaryKey(TbThirdUserinfoKey key);
    
    @Select({
        "select",
        "b.*",
        "from tb_third_userinfo a , tb_user b",
        "where a.user_id = b.user_id and a.cust_id = #{custId,jdbcType=VARCHAR}",
          "and a.third_type = #{thirdType,jdbcType=INTEGER}"
    })
    @ResultMap("UserResultMap")
    List<TbUser> selectByUserPK(TbThirdUserinfoKey key);

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
        "serial)",
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
        "#{serial,jdbcType=INTEGER})"
    })
    @SelectKey(before = false, keyProperty = "userId", resultType =Integer.class, statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    int insertUser(TbUser record);
    
    int updateByExampleSelective(@Param("record") TbThirdUserinfo record, @Param("example") TbThirdUserinfoExample example);

    int updateByExample(@Param("record") TbThirdUserinfo record, @Param("example") TbThirdUserinfoExample example);

    int updateByPrimaryKeySelective(TbThirdUserinfo record);

    @Update({
        "update tb_third_userinfo",
        "set id = #{id,jdbcType=INTEGER},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "cust_id = #{custId,jdbcType=VARCHAR},",
          "ext_info = #{extInfo,jdbcType=VARCHAR},",
          "ext1 = #{ext1,jdbcType=VARCHAR},",
          "ext2 = #{ext2,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where mobile = #{mobile,jdbcType=VARCHAR}",
          "and third_type = #{thirdType,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbThirdUserinfo record);
}