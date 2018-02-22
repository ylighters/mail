package com.qt.mail.modules.sys.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class App {
    private String id;
    
    @NotNull(message="版本不能为空")
    private double version;

    @NotBlank(message="apk文件不能为空")
    private String fileId;

    private String addDate;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

   

    public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}