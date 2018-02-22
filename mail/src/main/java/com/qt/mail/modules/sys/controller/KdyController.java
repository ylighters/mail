package com.qt.mail.modules.sys.controller;

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
import com.qt.mail.common.validator.group.AddGroup;
import com.qt.mail.common.validator.group.UpdateGroup;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.service.KdyService;





/**
 * 菜单相关
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/kdy")
public class KdyController extends AbstractController{
	
	@Autowired
	private KdyService kdyService;
	
	
	@RequestMapping("/info/{userId}")
	public R getInfo(@PathVariable("userId")String userId){
		return kdyService.getInfoById(userId);
	}
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:kdy:list")*/
	public R list(@RequestParam Map<String, Object> params){
		return kdyService.findKdyListByPage(params);
	}
	
	
	@SysLog("保存快递员")
	@RequestMapping("/save")
	/*@RequiresPermissions("sys:kdy:save")*/
	public R save(@RequestBody Kdy kdy){
		ValidatorUtils.validateEntity(kdy,AddGroup.class);
		return kdyService.save(kdy);
	}

	@SysLog("更新用户")
	@RequestMapping("/update")
	/*@RequiresPermissions("sys:kdy:update")*/
	public R update(@RequestBody Kdy kdy){
		ValidatorUtils.validateEntity(kdy,UpdateGroup.class);
		return kdyService.update(kdy);
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	/*@RequiresPermissions("sys:kdy:delete")*/
	public R delete(@RequestBody String[] kdyIds){
		
		return kdyService.deleteBatch(kdyIds);
	}
}
