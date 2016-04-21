package com.jfshare.fileNameMapped.model;

import java.util.Date;

public class TbFileNameMapped {
    private Integer id;

    private String filenameMd5;

    private String filename;

    private String fileid;

    private Integer backupState;

    private Date backupTime;

    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilenameMd5() {
        return filenameMd5;
    }

    public void setFilenameMd5(String filenameMd5) {
        this.filenameMd5 = filenameMd5 == null ? null : filenameMd5.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid == null ? null : fileid.trim();
    }

    public Integer getBackupState() {
        return backupState;
    }

    public void setBackupState(Integer backupState) {
        this.backupState = backupState;
    }

    public Date getBackupTime() {
        return backupTime;
    }

    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
}