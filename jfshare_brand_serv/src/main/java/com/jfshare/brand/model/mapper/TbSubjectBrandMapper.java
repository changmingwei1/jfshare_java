package com.jfshare.brand.model.mapper;

import com.jfshare.brand.model.TbSubjectBrandExample;
import com.jfshare.brand.model.TbSubjectBrandKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TbSubjectBrandMapper {
    int countByExample(TbSubjectBrandExample example);

    int deleteByExample(TbSubjectBrandExample example);

    @Delete({
        "delete from tb_subject_brand",
        "where subject_id = #{subjectId,jdbcType=INTEGER}",
          "and brand_id = #{brandId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(TbSubjectBrandKey key);

    @Insert({
        "insert into tb_subject_brand (subject_id, brand_id)",
        "values (#{subjectId,jdbcType=INTEGER}, #{brandId,jdbcType=INTEGER})"
    })
    int insert(TbSubjectBrandKey record);

    int insertSelective(TbSubjectBrandKey record);

    List<TbSubjectBrandKey> selectByExample(TbSubjectBrandExample example);

    int updateByExampleSelective(@Param("record") TbSubjectBrandKey record, @Param("example") TbSubjectBrandExample example);

    int updateByExample(@Param("record") TbSubjectBrandKey record, @Param("example") TbSubjectBrandExample example);
}