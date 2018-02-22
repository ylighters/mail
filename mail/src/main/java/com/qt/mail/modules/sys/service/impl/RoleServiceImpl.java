package com.qt.mail.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.PageUtils;
import com.qt.mail.common.utils.Query;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.RoleMapper;
import com.qt.mail.modules.sys.entity.Role;
import com.qt.mail.modules.sys.service.RoleMenuService;
import com.qt.mail.modules.sys.service.RoleService;




@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleMenuService roleMenuService;

	@Override
	public R queryRoleListByPage(Map<String, Object> map) {
		//查询列表数据
		Query query = new Query(map);
		List<Role> userList = roleMapper.queryList(query);
		int total = roleMapper.queryTotal(query);
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	
	}

	/**
	 * 添加角色
	 */
	@Transactional
	@Override
	public int save(Role role) {
		role.setId(CodeHelper.createUUID());
		role.setOperDate(DateUtil.getDateTime());
		int ct=roleMapper.insert(role);
		//删除当前用户角色关系
		roleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
	
		return ct;
	}


	/**
	 * 修改角色
	 */
	@Override
	public int update(Role role) {
		int ct=roleMapper.updateByPrimaryKey(role);
		roleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
		return ct;
	}

	
	@Override
	public R getRoleInfo(String roleId) {
         Role role =roleMapper.selectByPrimaryKey(roleId);
		
		//查询角色对应的菜单
		List<String> menuIdList = roleMenuService.queryMenuIdsByRoleId(roleId);
		role.setMenuIdList(menuIdList);
		return R.ok().put("role", role);
	}

	/**
	 * 删除用户
	 */
	@Transactional
	@Override
	public R deleteRoles(String[] roleIds) {
		roleMapper.deleteBatch(roleIds);
		
		return R.ok();
	}

	@Override
	public R queryList() {
		return R.ok().put("roleList",roleMapper.queryAll());
	}

	
	

	

	
}
