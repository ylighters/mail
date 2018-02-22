package com.qt.mail.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qt.mail.common.annotation.SysLog;
import com.qt.mail.common.utils.R;
import com.qt.mail.common.validator.ValidatorUtils;
import com.qt.mail.modules.sys.entity.Role;
import com.qt.mail.modules.sys.service.RoleService;






/**
 * 角色
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/role")
public class RoleController extends AbstractController{
	
	@Autowired
	private RoleService roleService;
	
	
	/**
	 * 所有角色列表
	 */
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:role:list")*/
	public R list(@RequestParam Map<String, Object> params){
		return roleService.queryRoleListByPage(params);
	}
	
	
	@RequestMapping("/select")
	/*@RequiresPermissions("sys:role:list")*/
	public R select(){
		return roleService.queryList();
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping("/save")
	/*@RequiresPermissions("sys:role:save")*/
	public R save(@RequestBody Role role){
		ValidatorUtils.validateEntity(role);
		role.setOperPerson(this.getUserId());
	    int ct=roleService.save(role);
		if(ct==1){
			return R.ok();
		}else{
			return R.error().put("msg","保存异常！");
		}
		
	}
	
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	/*@RequiresPermissions("sys:role:update")*/
	public R update(@RequestBody Role role){
		ValidatorUtils.validateEntity(role);
		
		roleService.update(role);
		
		return R.ok();
	}
	
	
	
	
	/**
	 * 得到角色信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/info/{roleId}")
	/*@RequiresPermissions("sys:role:info")*/
	public R info(@PathVariable("roleId") String roleId){
		return  roleService.getRoleInfo(roleId);
	}
	
	
	
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	/*@RequiresPermissions("sys:role:delete")*/
	public R delete(@RequestBody String[] roleIds){
		return roleService.deleteRoles(roleIds);
	}
	
	
	
	
	
	
}
