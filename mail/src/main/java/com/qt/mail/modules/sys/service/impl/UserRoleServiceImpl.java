package com.qt.mail.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.modules.sys.dao.RoleMenuMapper;
import com.qt.mail.modules.sys.service.RoleMenuService;



/**
 * 系统用户
 * 
 */
@Service("roleMenuService")
public class UserRoleServiceImpl implements RoleMenuService {
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Transactional
	@Override
	public void saveOrUpdate(String roleId, List<String> menuIdList) {
		roleMenuMapper.deleteByRoleId(roleId);
		if(menuIdList!=null&&menuIdList.size()!=0){
			Map<String, Object> map = new HashMap<>();
			map.put("roleId", roleId);
			map.put("menuIdList", menuIdList);
			roleMenuMapper.save(map);
		}
		
	}

	@Override
	public List<String> queryMenuIdsByRoleId(String roleId) {
		return roleMenuMapper.queryMenuIdsByRoleId(roleId);
	}

   
	
	
}
