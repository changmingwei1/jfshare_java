package com.jfshare.message.model.mapper;

import com.jfshare.message.model.TbMessage;
import com.jfshare.message.model.TbMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbMessageMapper {
    int countByExample(TbMessageExample example);

    int deleteByExample(TbMessageExample example);

    @Delete({
        "delete from tb_message",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_message (id, title, ",
        "content, pushTarget, ",
        "begin_date, ",
        "end_date, ",
        "push_flag, create_time, ",
        "msg_type, alert)",
        "values (#{id,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, ",
        "#{content,jdbcType=VARCHAR}, #{pushtarget,jdbcType=INTEGER}, ",
        "#{beginDate,jdbcType=DATE,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2DateTypeHandler}, ",
        "#{endDate,jdbcType=DATE,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2DateTypeHandler}, ",
        "#{pushFlag,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{msgType,jdbcType=INTEGER}, #{alert,jdbcType=VARCHAR})"
    })
    int insert(TbMessage record);

    int insertSelective(TbMessage record);

    List<TbMessage> selectByExample(TbMessageExample example);

    @Select({
        "select",
        "id, title, content, pushTarget, begin_date, end_date, push_flag, create_time, ",
        "msg_type, alert",
        "from tb_message",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbMessage record, @Param("example") TbMessageExample example);

    int updateByExample(@Param("record") TbMessage record, @Param("example") TbMessageExample example);

    int updateByPrimaryKeySelective(TbMessage record);

    @Update({
        "update tb_message",
        "set title = #{title,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "pushTarget = #{pushtarget,jdbcType=INTEGER},",
          "begin_date = #{beginDate,jdbcType=DATE,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2DateTypeHandler},",
          "end_date = #{endDate,jdbcType=DATE,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2DateTypeHandler},",
          "push_flag = #{pushFlag,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "msg_type = #{msgType,jdbcType=INTEGER},",
          "alert = #{alert,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbMessage record);
}