package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Map;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Role;
import com.qt.mail.modules.sys.entity.User;


/**
 * 系统用户
 * 
 */
public interface RoleService {

	
	R queryRoleListByPage(Map<String, Object> map);
	
	R queryList();
	
	int save(Role role);
	
	int update(Role role);
	
	R getRoleInfo(String roleId);
	
	R deleteRoles(String[] roleIds);
}
