package com.qt.mail.modules.sys.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.modules.sys.dao.RoleMapper;
import com.qt.mail.modules.sys.dao.UserRoleMapper;
import com.qt.mail.modules.sys.entity.Role;
import com.qt.mail.modules.sys.service.UserRoleService;



/**
 * 系统用户
 * 
 */
@Service("userRoleService")
public class RoleMenuServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	@Transactional
	@Override
	public void saveOrUpdate(String userId, List<String> roleIdList) {
		userRoleMapper.deleteByUserId(userId);
		if(roleIdList!=null&&roleIdList.size()!=0){
		  
			Iterator<String> stuIter = roleIdList.iterator();    
		    while (stuIter.hasNext()) {    
		        String student = stuIter.next(); 
		        Role role=roleMapper.selectByPrimaryKey(student);
		        if(role==null){
		        	stuIter.remove();
		        }
		    }    
			
			
			
			Map<String, Object> map = new HashMap<>();
			map.put("userId", userId);
			map.put("roleIdList", roleIdList);
			userRoleMapper.save(map);
		}
		
	}


   
	
	
}
