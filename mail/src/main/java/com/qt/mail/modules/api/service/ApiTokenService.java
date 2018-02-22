package com.qt.mail.modules.api.service;


import java.util.Map;

import com.qt.mail.modules.api.entity.ApiTokenEntity;

/**
 * 用户Token
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:07
 */
public interface ApiTokenService {

	ApiTokenEntity queryByUserId(Long userId);

	ApiTokenEntity queryByToken(String token);
	
	void save(ApiTokenEntity token);
	
	void update(ApiTokenEntity token);

	/**
	 * 生成token
	 * @param userId  用户ID
	 * @return        返回token相关信息
	 */
	Map<String, Object> createToken(long userId);

}
