package com.qt.mail.modules.api.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qt.mail.modules.api.entity.ApiUserEntity;
import com.qt.mail.modules.api.service.ApiUserService;
import com.qt.mail.modules.sys.dao.UserMapper;
import com.qt.mail.modules.sys.entity.User;



@Service("apiUserService")
public class ApiUserServiceImpl implements ApiUserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public ApiUserEntity queryObject(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApiUserEntity> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void save(String mobile, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ApiUserEntity user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBatch(String[] userIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ApiUserEntity queryByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long login(String mobile, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
