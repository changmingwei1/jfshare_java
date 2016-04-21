package com.jfshare.ridge.json.template;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here
 */
public class MysqlTemplate extends AbstractConfigTemplate {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1179222538464159623L;
	/**
     * 数据库实例
     */
    private List<MysqlDb> dbs = new ArrayList<MysqlDb>();

    public List<MysqlDb> getDbs() {
        return dbs;
    }

    public void setDbs(List<MysqlDb> dbs) {
        this.dbs = dbs;
    }
}
