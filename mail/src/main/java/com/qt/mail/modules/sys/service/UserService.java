package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Map;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.User;


/**
 * 系统用户
 * 
 */
public interface UserService {

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(String userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<String> queryAllMenuId(String userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	User queryUserByPhone(String username);
	
	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	User queryObject(String userId);
	
	/**
	 * 查询用户列表
	 */
	List<User> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	R save(User user);
	
	/**
	 * 修改用户
	 */
	R update(User user);
	
	R resetPwd(String userId);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(String[] userIds);
	
	
	R getCountInfo();
}
