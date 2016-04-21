package com.jfshare.subject.model;

import com.jfshare.subject.bean.TbSubject;

import java.io.Serializable;


public class SubjectTreeModel implements Serializable{

    private Integer id;
    private String name;
    private Integer pid;
    private Integer sorted;
    private Integer isLeaf;
    private String displayIds;
    private int status;

    public SubjectTreeModel() {

    }

    public SubjectTreeModel(Integer id, String name, Integer pid, Integer sorted, Integer isLeaf, String displayIds, int status) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.sorted = sorted;
        this.isLeaf = isLeaf;
        this.displayIds = displayIds;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getDisplayIds() {
        return displayIds;
    }

    public void setDisplayIds(String displayIds) {
        this.displayIds = displayIds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void exchange(TbSubject tbSubject) {
        this.id = tbSubject.getId();
        this.name = tbSubject.getName();
        this.pid = tbSubject.getPid();
        this.sorted = tbSubject.getSorted();
        this.isLeaf = tbSubject.getIsLeaf();
        this.displayIds = tbSubject.getDisplayIds();
        this.status = tbSubject.getStatus();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DisplaySubjectTreeModel)) {
            return false;
        }
        if(!(this.id == ((DisplaySubjectTreeModel) obj).getId())) {
            return false;
        }
        return true;
    }
}
