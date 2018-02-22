package com.qt.mail.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qt.mail.common.utils.PageUtils;
import com.qt.mail.common.utils.Query;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.service.LogService;


/**
 * 系统日志
 * 
 */
@Controller
@RequestMapping("/log")
public class LogController {
	@Autowired
	private LogService logService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:log:list")*/
	public R list(@RequestParam Map<String, Object> params){
		
		return logService.findLogListByPage(params);
	}
	
}
