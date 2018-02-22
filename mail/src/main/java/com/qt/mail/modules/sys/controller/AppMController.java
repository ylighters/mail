package com.qt.mail.modules.sys.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qt.mail.common.annotation.SysLog;
import com.qt.mail.common.utils.R;
import com.qt.mail.common.validator.ValidatorUtils;
import com.qt.mail.modules.sys.entity.App;
import com.qt.mail.modules.sys.service.AndroidService;


/**
 * app管理
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/android")
public class AppMController extends AbstractController{
	
	@Autowired
	private AndroidService androidService;
	
	
	@RequestMapping("/list")
/*	@RequiresPermissions("sys:app:list")*/
	public R list(@RequestParam Map<String, Object> params){
		return androidService.findAppListByPage(params);
	}
	
	
	@SysLog("保存app")
	@RequestMapping("/save")
	/*@RequiresPermissions("sys:app:save")*/
	public R save(@RequestBody App app){
		ValidatorUtils.validateEntity(app);
		return androidService.save(app);
	}
	
	@SysLog("删除app")
	@RequestMapping("/delete")
/*	@RequiresPermissions("sys:app:delete")*/
	public R delete(@RequestBody String[] kdyIds){
		return androidService.deleteBatch(kdyIds);
	}
	
}
