package com.qt.mail.modules.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qt.mail.modules.sys.entity.User;

/**
 * Controller公共组件
 * 
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected User getUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	protected String getUserId() {
		return getUser().getId();
	}

	protected String getDeptId() {
		return getUser().getDeptId();
	}
	/*
	protected String getRoleId() {
		return getUser().getRoleId();
	}*/
	
}
