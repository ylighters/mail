package com.qt.mail.modules.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qt.mail.modules.api.dao.ApiTokenDao;
import com.qt.mail.modules.api.entity.ApiTokenEntity;
import com.qt.mail.modules.api.service.ApiTokenService;


@Service("apiTokenService")
public class ApiTokenServiceImpl implements ApiTokenService {
	@Autowired
	private ApiTokenDao tokenDao;
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;

	@Override
	public ApiTokenEntity queryByUserId(Long userId) {
		return tokenDao.queryByUserId(userId);
	}

	@Override
	public ApiTokenEntity queryByToken(String token) {
		return tokenDao.queryByToken(token);
	}

	@Override
	public void save(ApiTokenEntity token){
		tokenDao.insert(token);
	}
	
	@Override
	public void update(ApiTokenEntity token){
		tokenDao.updateByPrimaryKey(token);
	}

	@Override
	public Map<String, Object> createToken(long userId) {
		//生成一个token
		String token = UUID.randomUUID().toString();
		//当前时间
		Date now = new Date();

		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		ApiTokenEntity tokenEntity = queryByUserId(userId);
		if(tokenEntity == null){
			tokenEntity = new ApiTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			save(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			update(tokenEntity);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("expire", EXPIRE);

		return map;
	}
}
