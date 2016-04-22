package com.jfshare.brand.dao.mysql;

import java.util.List;

import com.jfshare.brand.model.TbSubjectBrandKey;

public interface ISubjectBrandDao {
	List<TbSubjectBrandKey> queryBySubjectId(int id);
}
