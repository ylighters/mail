package com.qt.mail.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.R;
import com.qt.mail.common.validator.ValidatorUtils;
import com.qt.mail.common.validator.group.AddGroup;
import com.qt.mail.common.validator.group.UpdateGroup;
import com.qt.mail.modules.sys.dao.CompanyMapper;
import com.qt.mail.modules.sys.dao.KdyMapper;
import com.qt.mail.modules.sys.dao.RandomCheckMapper;
import com.qt.mail.modules.sys.dao.UserMapper;
import com.qt.mail.modules.sys.dao.UserRoleMapper;
import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.service.UserRoleService;
import com.qt.mail.modules.sys.service.UserService;


/**
 * 系统用户
 * 
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private UserRoleService userRoleService;
	

	@Autowired
	private RandomCheckMapper rcMapper;
	
	@Autowired
	private KdyMapper kdyMapper;

	@Override
	public List<String> queryAllPerms(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> queryAllMenuId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User queryUserByPhone(String username) {
		return userMapper.queryUserByPhone(username);
	}

	@Override
	public User queryObject(String userId) {
		User user=userMapper.selectByPrimaryKey(userId);
		user.setRoleList(userRoleMapper.getRoleListByUserId(userId));
		if(null!=user.getIcon()&&!"".equals(user.getIcon())){
			user.setVisitUrl(Constant.WEB_PATH+"myFile/"+user.getVisitUrl());	
		}
		return user;
	}

	@Override
	public List<User> queryList(Map<String, Object> map) {
		return userMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return userMapper.queryTotal();
	}

	@Transactional
	@Override
	public R save(User user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        user.setAddDate(DateUtil.getDateTime());
        user.setId(CodeHelper.createUUID());
        String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPwd((new Sha256Hash(user.getPwd(), salt).toHex()));
		user.setSalt(salt);
		user.setType(Constant.UserType.POST.getValue());
		userMapper.insert(user);
		
		userRoleService.saveOrUpdate(user.getId(),user.getRoleList());

		return R.ok();
	}
	@Transactional
	@Override
	public R update(User user) {
        ValidatorUtils.validateEntity(user,UpdateGroup.class);
        user.setAddDate(DateUtil.getDateTime());
		userMapper.updateByPrimaryKeySelective(user);
		userRoleService.saveOrUpdate(user.getId(),user.getRoleList());
		return R.ok();
		
	}
	@Transactional
	@Override
	public void deleteBatch(String[] userIds) {
		userMapper.deleteBatch(userIds);
	}


	@Override
	public R getCountInfo() {
		Map<String,Object> map=new HashMap<String,Object>();
		//得到统计信息
		int companyCount=companyMapper.queryTotal();
		int userCount=userMapper.queryTotal();
		int rcCount=rcMapper.queryTotal();
		int kdyCount=kdyMapper.queryTotal();
		map.put("companyCount",companyCount);
		map.put("userCount",userCount);
		map.put("rcCount",rcCount);
		map.put("kdyCount",kdyCount);
		return R.ok().put("count",map);
	}

	@Override
	public R resetPwd(String userId) {
		User user=userMapper.selectByPrimaryKey(userId);
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPwd((new Sha256Hash("123456", salt).toHex()));
		int ct=userMapper.updatePwd(user);
		if(ct==1){
			return R.ok();
		}else{
		    return R.error("更新异常！");	
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
