package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Role;
import com.qt.mail.modules.sys.entity.User;


/**
 * 系统用户
 * 
 */
public interface UserRoleService {
	
	void saveOrUpdate(String userId,List<String> roleIdList);
	
}
