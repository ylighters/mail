package com.qt.mail.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.UserTokenMapper;
import com.qt.mail.modules.sys.entity.UserToken;
import com.qt.mail.modules.sys.oauth2.TokenGenerator;
import com.qt.mail.modules.sys.service.UserTokenService;


@Service("userTokenService")
public class UserTokenServiceImpl implements UserTokenService {
	@Autowired
	private UserTokenMapper userTokenMapper;
	//12小时后过期
	//private final static int EXPIRE = 3600 * 12;
	private final static int EXPIREHOUR = 12;//12小时
	
	@Override
	public UserToken queryByUserId(String userId) {
		return userTokenMapper.getByUserId(userId);
	}
	@Override
	public UserToken queryByToken(String token) {
		return userTokenMapper.getByToken(token);
	}
	@Override
	public void save(UserToken token) {
		userTokenMapper.insert(token);
	}
	@Override
	public void update(UserToken token) {
		userTokenMapper.updateByPrimaryKey(token);
	}
	@Transactional
	@Override
	public R createToken(String userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();
		//当前时间
		String now=DateUtil.getDateTime();//Date now = new Date();
		//过期时间
		//Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
		String expireTime=DateUtil.getAddDate(now,EXPIREHOUR);
		//判断是否生成过token
		UserToken tokenEntity = queryByUserId(userId);
		if(tokenEntity == null){
			tokenEntity = new UserToken();
			tokenEntity.setId(CodeHelper.createUUID());
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);;
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
		R r = R.ok().put("token", token).put("expire", EXPIREHOUR);
		return r;
	}
	

	
}
