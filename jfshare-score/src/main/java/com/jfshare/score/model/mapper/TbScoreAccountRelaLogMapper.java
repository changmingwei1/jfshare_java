package com.jfshare.score.model.mapper;

import com.jfshare.score.model.TbScoreAccountRelaLog;
import com.jfshare.score.model.TbScoreAccountRelaLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

public interface TbScoreAccountRelaLogMapper {
    int countByExample(TbScoreAccountRelaLogExample example);

    int deleteByExample(TbScoreAccountRelaLogExample example);

    @Delete({
        "delete from tb_score_account_rela_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_score_account_rela_log (id, appcode, ",
        "requestdate, ",
        "sign, spid, deviceno, ",
        "devicetype, outcustid, ",
        "token, exceedtime, ",
        "type, sysname, syscode, ",
        "reperrcode, reperrmsg)",
        "values (#{id,jdbcType=INTEGER}, #{appcode,jdbcType=VARCHAR}, ",
        "#{requestdate,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{sign,jdbcType=CHAR}, #{spid,jdbcType=CHAR}, #{deviceno,jdbcType=VARCHAR}, ",
        "#{devicetype,jdbcType=VARCHAR}, #{outcustid,jdbcType=VARCHAR}, ",
        "#{token,jdbcType=VARCHAR}, #{exceedtime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{type,jdbcType=VARCHAR}, #{sysname,jdbcType=VARCHAR}, #{syscode,jdbcType=VARCHAR}, ",
        "#{reperrcode,jdbcType=VARCHAR}, #{reperrmsg,jdbcType=VARCHAR})"
    })
    @SelectKey(before = false, keyProperty = "id", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    int insert(TbScoreAccountRelaLog record);

    int insertSelective(TbScoreAccountRelaLog record);

    List<TbScoreAccountRelaLog> selectByExample(TbScoreAccountRelaLogExample example);

    @Select({
        "select",
        "id, appcode, requestdate, sign, spid, deviceno, devicetype, outcustid, token, ",
        "exceedtime, type, sysname, syscode, reperrcode, reperrmsg",
        "from tb_score_account_rela_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbScoreAccountRelaLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbScoreAccountRelaLog record, @Param("example") TbScoreAccountRelaLogExample example);

    int updateByExample(@Param("record") TbScoreAccountRelaLog record, @Param("example") TbScoreAccountRelaLogExample example);

    int updateByPrimaryKeySelective(TbScoreAccountRelaLog record);

    @Update({
        "update tb_score_account_rela_log",
        "set appcode = #{appcode,jdbcType=VARCHAR},",
          "requestdate = #{requestdate,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "sign = #{sign,jdbcType=CHAR},",
          "spid = #{spid,jdbcType=CHAR},",
          "deviceno = #{deviceno,jdbcType=VARCHAR},",
          "devicetype = #{devicetype,jdbcType=VARCHAR},",
          "outcustid = #{outcustid,jdbcType=VARCHAR},",
          "token = #{token,jdbcType=VARCHAR},",
          "exceedtime = #{exceedtime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "type = #{type,jdbcType=VARCHAR},",
          "sysname = #{sysname,jdbcType=VARCHAR},",
          "syscode = #{syscode,jdbcType=VARCHAR},",
          "reperrcode = #{reperrcode,jdbcType=VARCHAR},",
          "reperrmsg = #{reperrmsg,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbScoreAccountRelaLog record);
}