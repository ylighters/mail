package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Map;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Dept;


public interface DeptService {
	List<Dept> queryList(Map<String, Object> map);
	
	void save(Dept dept);
	
	R getDeptInfo(String deptId);
	
	List<Dept> queryList(String deptId);
	
	R deleteDept(String deptId);
}
