package com.jfshare.pay.model.mapper;

import com.jfshare.pay.model.TbPayRecord;
import com.jfshare.pay.model.TbPayRecordExample;
import com.jfshare.pay.model.TbPayRecordWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbPayRecordMapper {
    int countByExample(TbPayRecordExample example);

    int deleteByExample(TbPayRecordExample example);

    @Delete({
        "delete from tb_pay_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_pay_record (id, pay_id, ",
        "token_id, order_no, ",
        "extra_param, title, ",
        "price, score, pay_channel, ",
        "pay_ip, return_url, ",
        "remark, pay_state, ",
        "biz_code, third_id, ",
        "third_price, third_score, ",
        "third_account, third_time, ",
        "process_time, ",
        "create_time, ",
        "last_update_time, ",
        "third_req, third_ret)",
        "values (#{id,jdbcType=INTEGER}, #{payId,jdbcType=VARCHAR}, ",
        "#{tokenId,jdbcType=VARCHAR}, #{orderNo,jdbcType=VARCHAR}, ",
        "#{extraParam,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=INTEGER}, #{score,jdbcType=INTEGER}, #{payChannel,jdbcType=INTEGER}, ",
        "#{payIp,jdbcType=VARCHAR}, #{returnUrl,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR}, #{payState,jdbcType=INTEGER}, ",
        "#{bizCode,jdbcType=INTEGER}, #{thirdId,jdbcType=VARCHAR}, ",
        "#{thirdPrice,jdbcType=INTEGER}, #{thirdScore,jdbcType=INTEGER}, ",
        "#{thirdAccount,jdbcType=VARCHAR}, #{thirdTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{processTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}, ",
        "#{thirdReq,jdbcType=LONGVARCHAR}, #{thirdRet,jdbcType=LONGVARCHAR})"
    })
    int insert(TbPayRecordWithBLOBs record);

    int insertSelective(TbPayRecordWithBLOBs record);

    List<TbPayRecordWithBLOBs> selectByExampleWithBLOBs(TbPayRecordExample example);

    List<TbPayRecord> selectByExample(TbPayRecordExample example);

    @Select({
        "select",
        "id, pay_id, token_id, order_no, extra_param, title, price, score, pay_channel, ",
        "pay_ip, return_url, remark, pay_state, biz_code, third_id, third_price, third_score, ",
        "third_account, third_time, process_time, create_time, last_update_time, third_req, ",
        "third_ret",
        "from tb_pay_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("ResultMapWithBLOBs")
    TbPayRecordWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbPayRecordWithBLOBs record, @Param("example") TbPayRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") TbPayRecordWithBLOBs record, @Param("example") TbPayRecordExample example);

    int updateByExample(@Param("record") TbPayRecord record, @Param("example") TbPayRecordExample example);

    int updateByPrimaryKeySelective(TbPayRecordWithBLOBs record);

    @Update({
        "update tb_pay_record",
        "set pay_id = #{payId,jdbcType=VARCHAR},",
          "token_id = #{tokenId,jdbcType=VARCHAR},",
          "order_no = #{orderNo,jdbcType=VARCHAR},",
          "extra_param = #{extraParam,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=INTEGER},",
          "score = #{score,jdbcType=INTEGER},",
          "pay_channel = #{payChannel,jdbcType=INTEGER},",
          "pay_ip = #{payIp,jdbcType=VARCHAR},",
          "return_url = #{returnUrl,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "pay_state = #{payState,jdbcType=INTEGER},",
          "biz_code = #{bizCode,jdbcType=INTEGER},",
          "third_id = #{thirdId,jdbcType=VARCHAR},",
          "third_price = #{thirdPrice,jdbcType=INTEGER},",
          "third_score = #{thirdScore,jdbcType=INTEGER},",
          "third_account = #{thirdAccount,jdbcType=VARCHAR},",
          "third_time = #{thirdTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "process_time = #{processTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "third_req = #{thirdReq,jdbcType=LONGVARCHAR},",
          "third_ret = #{thirdRet,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(TbPayRecordWithBLOBs record);

    @Update({
        "update tb_pay_record",
        "set pay_id = #{payId,jdbcType=VARCHAR},",
          "token_id = #{tokenId,jdbcType=VARCHAR},",
          "order_no = #{orderNo,jdbcType=VARCHAR},",
          "extra_param = #{extraParam,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=INTEGER},",
          "score = #{score,jdbcType=INTEGER},",
          "pay_channel = #{payChannel,jdbcType=INTEGER},",
          "pay_ip = #{payIp,jdbcType=VARCHAR},",
          "return_url = #{returnUrl,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "pay_state = #{payState,jdbcType=INTEGER},",
          "biz_code = #{bizCode,jdbcType=INTEGER},",
          "third_id = #{thirdId,jdbcType=VARCHAR},",
          "third_price = #{thirdPrice,jdbcType=INTEGER},",
          "third_score = #{thirdScore,jdbcType=INTEGER},",
          "third_account = #{thirdAccount,jdbcType=VARCHAR},",
          "third_time = #{thirdTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "process_time = #{processTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "create_time = #{createTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler},",
          "last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP,typeHandler=com.jfshare.mybatis.typehandler.JodaDateTime2TimestampTypeHandler}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbPayRecord record);
}