package com.qt.mail.modules.sys.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qt.mail.common.validator.group.AddGroup;
import com.qt.mail.common.validator.group.UpdateGroup;

public class Kdy {
    private String id;
    
    @NotBlank(message="用户姓名不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    @NotBlank(message="身份证号不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String codeNumber;

    @NotBlank(message="快递证号不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String kdCode;

    @NotBlank(message="联系方式不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String lxfs;

    @NotBlank(message="所属公司/网点不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String companyId;

    @NotBlank(message="密码不能为为空", groups = AddGroup.class)
    private String pwd;
    
    private String operDate;

    private String remark;
    
    public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber == null ? null : codeNumber.trim();
    }

    public String getKdCode() {
        return kdCode;
    }

    public void setKdCode(String kdCode) {
        this.kdCode = kdCode == null ? null : kdCode.trim();
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs == null ? null : lxfs.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate == null ? null : operDate.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}