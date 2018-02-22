package com.qt.mail.modules.sys.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.qt.mail.common.validator.group.AddGroup;
import com.qt.mail.common.validator.group.UpdateGroup;
import com.qt.mail.modules.sys.entity.ViewType.AppView;
import com.qt.mail.modules.sys.entity.ViewType.NormalView;

public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3073276977756589425L;
	
	@JsonView(AppView.class) 
	private String id;//id
	 @NotBlank(message="用户编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String code;//编码

	@JsonView(AppView.class)
    @NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;//姓名

    
    @NotBlank(message="密码不能为空", groups = AddGroup.class)
    private String pwd;//密码

    
    @NotBlank(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String deptId;//部门

//    private String roleId;//角色

    @JsonView(AppView.class)
    private String state;//状态

    @JsonView(AppView.class)
    private String email;//邮箱
    
    @JsonView(AppView.class)
    private String sex;//性别
    
    @JsonView(AppView.class)
    private String address;//地址
    
    @JsonView(AppView.class)
    private String icon;//图标

    

	@JsonView(AppView.class)
    @NotBlank(message="手机不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String phone;//手机

    private String addDate;//添加时间

    private String type;//类型
    
    private String ylzd;//预留

    
    private String salt;//标识
    
    
    
    private List<String> roleList;//角色ids
    
    @JsonView(AppView.class)
    private String deptName;
    
    

	@JsonView(AppView.class)
    private String visitUrl;//头像路径

    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}
	
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getYlzd() {
        return ylzd;
    }

    public void setYlzd(String ylzd) {
        this.ylzd = ylzd == null ? null : ylzd.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}


}