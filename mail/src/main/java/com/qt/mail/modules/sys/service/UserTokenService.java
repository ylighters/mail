package com.qt.mail.modules.sys.service;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.UserToken;

/**
 * 用户Token
 * 
 */
public interface UserTokenService {

	UserToken queryByUserId(String userId);

	UserToken queryByToken(String token);
	
	void save(UserToken token);
	
	void update(UserToken token);

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(String userId);

}
