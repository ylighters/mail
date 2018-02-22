package com.qt.mail.modules.sys.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.qt.mail.common.validator.group.AddGroup;
import com.qt.mail.common.validator.group.UpdateGroup;

public class Company {
	@JsonView(ViewType.TreeView.class)
	private String id; //id

    @JsonView(ViewType.TreeView.class)
    @NotBlank(message="公司/网点名称不能为空", groups = {AddGroup.class,UpdateGroup.class})
    private String name;//名称

    @JsonView(ViewType.TreeView.class)
    @NotBlank(message="所属公司/网点名称不能为空", groups = {AddGroup.class,UpdateGroup.class})
    private String parentId;//父类Id
    
    @NotBlank(message="编码不能为空", groups = {AddGroup.class})
    private String code;//编码

    private String address;//地址

    private String legalPerson;//法人
    
    @NotBlank(message="联系方式不能为空", groups = {AddGroup.class,UpdateGroup.class})
    private String lxfs;//联系方式

    private String remark;//备注

    private String state;//状态

    private String icon;//图标

    @NotNull(message="经度不能为空", groups = {AddGroup.class,UpdateGroup.class})
    private String jd;//经度
    
    @NotNull(message="维度不能为空", groups = {AddGroup.class,UpdateGroup.class})
    private String wd;//维度
    
    private String type;//类型
    
    private String parentName; 
    
    private String operDate;//操作日期
    
    private String visitUrl;//访问路径
    
    @Size(max=300,message="负责区域不能超过300字",groups = {AddGroup.class,UpdateGroup.class})
    private String area;//负责区域
    
    @Size(max=600,message="安检设备不能超过600字", groups = {AddGroup.class,UpdateGroup.class})
    private String equipment;//安检设备
    
    @Size(max=300,message="财政补助不能超过300字", groups = {AddGroup.class,UpdateGroup.class})
    private String financialNeed;//财政补助
    
    @NotNull(message="安全等级不能为空", groups = {AddGroup.class,UpdateGroup.class})
    private String securityLever;//安检等级
    
    private int kdCount;
    private int xCount;
    private int jkCount;
    private int carCount;
    private int slcCount;
    
    @NotNull(message="网点类型不能为空", groups = {AddGroup.class,UpdateGroup.class})
    private String lever;
    
    
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof Company) {
            Company com = (Company) obj;
            return (id.equals(com.id));
        }
    	return super.equals(obj);
    }

    public int getKdCount() {
		return kdCount;
	}

	public void setKdCount(int kdCount) {
		this.kdCount = kdCount;
	}

	public int getxCount() {
		return xCount;
	}

	public void setxCount(int xCount) {
		this.xCount = xCount;
	}

	public int getJkCount() {
		return jkCount;
	}

	public void setJkCount(int jkCount) {
		this.jkCount = jkCount;
	}

	public int getCarCount() {
		return carCount;
	}

	public void setCarCount(int carCount) {
		this.carCount = carCount;
	}

	
	public int getSlcCount() {
		return slcCount;
	}

	public void setSlcCount(int slcCount) {
		this.slcCount = slcCount;
	}

	public String getLever() {
		return lever;
	}

	public void setLever(String lever) {
		this.lever = lever;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getFinancialNeed() {
		return financialNeed;
	}

	public void setFinancialNeed(String financialNeed) {
		this.financialNeed = financialNeed;
	}

	public String getSecurityLever() {
		return securityLever;
	}

	public void setSecurityLever(String securityLever) {
		this.securityLever = securityLever;
	}

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs == null ? null : lxfs.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }


	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperDate() {
		return operDate;
	}

	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

}