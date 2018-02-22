package com.qt.mail.modules.sys.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qt.mail.common.validator.group.AddGroup;
import com.qt.mail.common.validator.group.UpdateGroup;

public class RandomCheck {
	
    private String id;

    private String operDate;

    private String operPerson;

    private String checkRule;

    private String type;

    private String remark;
    
    @NotBlank(message="抽检数量不能为空")
    private String count;//抽检数量
  
    private String cjslA;//抽检数量
    
    private String cjslB;//抽检数量
    
    private String cjslC;//抽检数量
    
    private String cjslD;//抽检数量
    
    private String operPersonName;//抽检人姓名
    
    
   


	public String getCount() {
		return count;
	}

	public String getCjslA() {
		return cjslA;
	}

	public void setCjslA(String cjslA) {
		this.cjslA = cjslA;
	}

	public String getCjslB() {
		return cjslB;
	}

	public void setCjslB(String cjslB) {
		this.cjslB = cjslB;
	}

	public String getCjslC() {
		return cjslC;
	}

	public void setCjslC(String cjslC) {
		this.cjslC = cjslC;
	}

	public String getCjslD() {
		return cjslD;
	}

	public void setCjslD(String cjslD) {
		this.cjslD = cjslD;
	}

	public void setCount(String count) {
		this.count = count;
	}


	private String state;//状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate == null ? null : operDate.trim();
    }

    public String getOperPerson() {
        return operPerson;
    }

    public void setOperPerson(String operPerson) {
        this.operPerson = operPerson == null ? null : operPerson.trim();
    }

    public String getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(String checkRule) {
        this.checkRule = checkRule == null ? null : checkRule.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
		this.state = state;
	}

	public String getOperPersonName() {
		return operPersonName;
	}

	public void setOperPersonName(String operPersonName) {
		this.operPersonName = operPersonName;
	}
}