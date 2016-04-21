package com.jfshare.fileNameMapped.model.mapper;

import com.jfshare.fileNameMapped.model.TbFileNameMapped;
import com.jfshare.fileNameMapped.model.TbFileNameMappedExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbFileNameMappedMapper {
    int countByExample(TbFileNameMappedExample example);

    int deleteByExample(TbFileNameMappedExample example);

    @Delete({
        "delete from tb_file_name_mapped",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_file_name_mapped (id, filename_md5, ",
        "filename, fileid, ",
        "backup_state, backup_time, ",
        "source)",
        "values (#{id,jdbcType=INTEGER}, #{filenameMd5,jdbcType=VARCHAR}, ",
        "#{filename,jdbcType=VARCHAR}, #{fileid,jdbcType=VARCHAR}, ",
        "#{backupState,jdbcType=INTEGER}, #{backupTime,jdbcType=TIMESTAMP}, ",
        "#{source,jdbcType=VARCHAR})"
    })
    int insert(TbFileNameMapped record);

    int insertSelective(TbFileNameMapped record);

    List<TbFileNameMapped> selectByExample(TbFileNameMappedExample example);

    @Select({
        "select",
        "id, filename_md5, filename, fileid, backup_state, backup_time, source",
        "from tb_file_name_mapped",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    TbFileNameMapped selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbFileNameMapped record, @Param("example") TbFileNameMappedExample example);

    int updateByExample(@Param("record") TbFileNameMapped record, @Param("example") TbFileNameMappedExample example);

    int updateByPrimaryKeySelective(TbFileNameMapped record);

    @Update({
        "update tb_file_name_mapped",
        "set filename_md5 = #{filenameMd5,jdbcType=VARCHAR},",
          "filename = #{filename,jdbcType=VARCHAR},",
          "fileid = #{fileid,jdbcType=VARCHAR},",
          "backup_state = #{backupState,jdbcType=INTEGER},",
          "backup_time = #{backupTime,jdbcType=TIMESTAMP},",
          "source = #{source,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbFileNameMapped record);
}