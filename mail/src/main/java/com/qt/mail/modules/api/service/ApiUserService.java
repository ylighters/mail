package com.qt.mail.modules.api.service;



import java.util.List;
import java.util.Map;

import com.qt.mail.modules.api.entity.ApiUserEntity;

/**
 * 用户
 * 
 */
public interface ApiUserService {

	ApiUserEntity queryObject(String userId);
	
	List<ApiUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(String mobile, String password);
	
	void update(ApiUserEntity user);
	
	void delete(String userId);
	
	void deleteBatch(String[] userIds);

	ApiUserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param mobile    手机号
	 * @param password  密码
	 * @return          返回用户ID
	 */
	long login(String mobile, String password);
}
