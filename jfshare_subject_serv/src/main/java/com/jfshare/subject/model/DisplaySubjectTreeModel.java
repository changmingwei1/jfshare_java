package com.jfshare.subject.model;

import com.jfshare.subject.bean.TbDisplaySubject;

public class DisplaySubjectTreeModel {

    private Integer id;
    private String name;
    private String image;
    private Integer pid;
    private Integer sorted;
    private Integer isLeaf;
    private String subjectIds;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(String subjectIds) {
        this.subjectIds = subjectIds;
    }

    public void exchange(TbDisplaySubject tbDisplaySubject) {
        this.id = tbDisplaySubject.getId();
        this.name = tbDisplaySubject.getName();
        this.image = tbDisplaySubject.getImage();
        this.pid = tbDisplaySubject.getPid();
        this.sorted = tbDisplaySubject.getSorted();
        this.isLeaf = tbDisplaySubject.getIsLeaf();
        this.subjectIds = tbDisplaySubject.getSubjectIds();
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
