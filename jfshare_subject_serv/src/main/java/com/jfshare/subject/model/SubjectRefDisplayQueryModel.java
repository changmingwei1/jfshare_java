package com.jfshare.subject.model;

public class SubjectRefDisplayQueryModel {

    /* 前台类目或者后台类目ID */
    private Integer id;
    /* 前台类目或者后台类目对应的后台类目或者前台类目 */
    private String ids;
    /* 如果是后台类目对应前台类目，指定前台类目的类型 */
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
