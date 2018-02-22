package com.qt.mail.modules.sys.entity;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;

public class Message {
	@JsonView(ViewType.AppView.class) 
    private String id;
    
	@JsonView(ViewType.AppView.class) 
    @NotBlank(message="标题不能为空")
    @Length(max=100,message="标题不能超过100字")
    private String title;

    private String content;

    private String addPerson;

    private String addDate;
    
    private String fileIds;

    @JsonView(ViewType.AppView.class) 
    private Integer count;

    private String state;

    @JsonView(ViewType.AppView.class) 
    private String type;

    @JsonView(ViewType.AppView.class) 
    private String visitUrl;
    
    private String remark;
    
    @JsonView(ViewType.AppView.class) 
    private String addPersonName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAddPerson() {
        return addPerson;
    }

    public void setAddPerson(String addPerson) {
        this.addPerson = addPerson == null ? null : addPerson.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds == null ? null : fileIds.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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

	public String getAddPersonName() {
		return addPersonName;
	}

	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}
}