package com.qt.mail.modules.sys.controller;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qt.mail.common.annotation.SysLog;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Menu;
import com.qt.mail.modules.sys.service.MenuService;
import com.qt.mail.modules.sys.service.ShiroService;




/**
 * 菜单相关
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends AbstractController{
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/getNav.do")
	public R nav(){
		List<Menu> menuList = menuService.findUserMenuList(getUserId());
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return R.ok().put("menuList", menuList).put("permissions", permissions);
	}
	
	
	@RequestMapping("/list.do")
	public List<Menu> getMenuTree(){
		return menuService.findMenuList();
	}
	
	
	
	
	
	
}
