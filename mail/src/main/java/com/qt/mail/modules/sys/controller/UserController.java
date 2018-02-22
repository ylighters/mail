package com.qt.mail.modules.sys.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
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
import com.qt.mail.modules.sys.entity.Menu;
import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.service.ShiroService;
import com.qt.mail.modules.sys.service.UserService;





/**
 * 菜单相关
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController{
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	
	@RequestMapping("/getInfo/{userId}")
	public R getInfo(@PathVariable("userId")String userId){
		return R.ok().put("user",userService.queryObject(userId));
	}
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:user:list")*/
	public R list(@RequestParam Map<String, Object> params){
		params.put("userType",Constant.UserType.POST.getValue());//只查看邮政用户
		//查询列表数据
		Query query = new Query(params);
		List<User> userList = userService.queryList(query);
		int total = userService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	@SysLog("保存用户")
	@RequestMapping("/save")
	/*@RequiresPermissions("sys:user:save")*/
	public R save(@RequestBody User user){
		return userService.save(user);
	}
	
	@SysLog("更新用户")
	@RequestMapping("/update")
	/*@RequiresPermissions("sys:user:update")*/
	public R update(@RequestBody User user){
		return userService.update(user);
	}
	
	@SysLog("更新密码")
	@RequestMapping("/reset/{userId}")
	/*@RequiresPermissions("sys:user:update")*/
	public R reset(@PathVariable String userId){
		return userService.resetPwd(userId);
	}
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
/*	@RequiresPermissions("sys:user:delete")*/
	public R delete(@RequestBody String[] userIds){
		if(ArrayUtils.contains(userIds,Constant.SUPER_ADMIN)){
			return R.error("系统管理员不能删除");
		}
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		userService.deleteBatch(userIds);
		
		return R.ok();
	}
}
