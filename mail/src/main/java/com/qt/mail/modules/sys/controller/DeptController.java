package com.qt.mail.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qt.mail.common.annotation.SysLog;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.PageUtils;
import com.qt.mail.common.utils.Query;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Dept;
import com.qt.mail.modules.sys.entity.Menu;
import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.service.DeptService;
import com.qt.mail.modules.sys.service.ShiroService;
import com.qt.mail.modules.sys.service.UserService;






/**
 * 部门相关
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends AbstractController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private DeptService deptService;
	
	/**
	 * 得到部门树信息
	 * @return
	 */
	@RequestMapping("/tree.do")
	/*@RequiresPermissions("sys:dept:list")*/
	public List<Dept> tree(){
		Map<String, Object> map = new HashMap<>();
		return deptService.queryList(map);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:dept:list")*/
	public List<Dept> getlist(){
		Map<String, Object> map = new HashMap<>();
		return deptService.queryList(map);
	}
	
	
	@RequestMapping("/tree/{deptId}")
	/*@RequiresPermissions("sys:dept:list")*/
	public List<Dept> getTreelist(@PathVariable("deptId")String deptId){
		return deptService.queryList(deptId);
	}
	
	
	/**
	 * 保存部门
	 * @param dept
	 * @return
	 */
	@SysLog("保存部门")
	@RequestMapping("/save")
/*	@RequiresPermissions("sys:dept:save")*/
	public R save(@RequestBody Dept dept){
	
		deptService.save(dept);
		
		return R.ok();
	}
	
	@SysLog("修改部门")
	@RequestMapping("/info/{deptId}")
	/*@RequiresPermissions("sys:role:info")*/
	public R info(@PathVariable("deptId") String deptId){
		
		return  deptService.getDeptInfo(deptId);
	}
	
	
	
	/**
	 * 删除
	 */
	@SysLog("删除部门")
	@RequestMapping("/delete")
	/*@RequiresPermissions("sys:dept:delete")*/
	public R delete(String deptId){
		return deptService.deleteDept(deptId);
		
	}
	
	
	
}
