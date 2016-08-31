package com.jfshare.score.model.mapper;

import com.jfshare.score.model.TbRequestInterfaceLog;
import com.jfshare.score.model.TbRequestInterfaceLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

public interface TbRequestInterfaceLogMapper {
    int countByExample(TbRequestInterfaceLogExample example);

    int deleteByExample(TbRequestInterfaceLogExample example);

    @Delete({
        "delete from tb_request_interface_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_request_interface_log (id, appcode, ",
        "requestdate, ",
        "sign, spid, outorderid, ",
        "deviceno, devicetype, ",
        "provinceid, custid, ",
        "num, remark, activeid, ",
        "exptime, ",
        "type, sysname, syscode, ",
        "reperrcode, reperrmsg)",
        "values (#{id,jdbcType=INTEGER}, #{appcode,jdbcType=VARCHAR}, ",
        "#{requestdate,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{sign,jdbcType=CHAR}, #{spid,jdbcType=CHAR}, #{outorderid,jdbcType=VARCHAR}, ",
        "#{deviceno,jdbcType=VARCHAR}, #{devicetype,jdbcType=VARCHAR}, ",
        "#{provinceid,jdbcType=VARCHAR}, #{custid,jdbcType=VARCHAR}, ",
        "#{num,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, #{activeid,jdbcType=VARCHAR}, ",
        "#{exptime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{type,jdbcType=VARCHAR}, #{sysname,jdbcType=VARCHAR}, #{syscode,jdbcType=VARCHAR}, ",
        "#{reperrcode,jdbcType=VARCHAR}, #{reperrmsg,jdbcType=VARCHAR})"
    })
    @SelectKey(before = false, keyProperty = "id", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    int insert(TbRequestInterfaceLog record);

    int insertSelective(TbRequestInterfaceLog record);

    List<TbRequestInterfaceLog> selectByExample(TbRequestInterfaceLogExample example);

    @Select({
        "select",
        "id, appcode, requestdate, sign, spid, outorderid, deviceno, devicetype, provinceid, ",
        "custid, num, remark, activeid, exptime, type, sysname, syscode, reperrcode, ",
        "reperrmsg",
        "from tb_request_interface_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbRequestInterfaceLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbRequestInterfaceLog record, @Param("example") TbRequestInterfaceLogExample example);

    int updateByExample(@Param("record") TbRequestInterfaceLog record, @Param("example") TbRequestInterfaceLogExample example);

    int updateByPrimaryKeySelective(TbRequestInterfaceLog record);

    @Update({
        "update tb_request_interface_log",
        "set appcode = #{appcode,jdbcType=VARCHAR},",
          "requestdate = #{requestdate,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "sign = #{sign,jdbcType=CHAR},",
          "spid = #{spid,jdbcType=CHAR},",
          "outorderid = #{outorderid,jdbcType=VARCHAR},",
          "deviceno = #{deviceno,jdbcType=VARCHAR},",
          "devicetype = #{devicetype,jdbcType=VARCHAR},",
          "provinceid = #{provinceid,jdbcType=VARCHAR},",
          "custid = #{custid,jdbcType=VARCHAR},",
          "num = #{num,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "activeid = #{activeid,jdbcType=VARCHAR},",
          "exptime = #{exptime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "type = #{type,jdbcType=VARCHAR},",
          "sysname = #{sysname,jdbcType=VARCHAR},",
          "syscode = #{syscode,jdbcType=VARCHAR},",
          "reperrcode = #{reperrcode,jdbcType=VARCHAR},",
          "reperrmsg = #{reperrmsg,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbRequestInterfaceLog record);
    @Update({
        "update tb_request_interface_log",
        "set reperrcode = #{reperrcode,jdbcType=VARCHAR},",
          "reperrmsg = #{reperrmsg,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateById(TbRequestInterfaceLog record);
}