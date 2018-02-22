package com.qt.mail.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.Constant.BaseType;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.DeptMapper;
import com.qt.mail.modules.sys.entity.Dept;
import com.qt.mail.modules.sys.service.DeptService;



@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptMapper deptMapper;

	@Override
	public List<Dept> queryList(Map<String, Object> map) {
		return deptMapper.queryList(new HashMap<String,Object>());
	}

	@Override
	public List<Dept> queryList(String deptId) {
		List<Dept> list=deptMapper.queryList(new HashMap<String,Object>());
		if(null!=deptId&&!"".equals(deptId)&&!"null".equals(deptId)){
		    return this.getTreeJson(deptId,Constant.ROOT_ID, list);	
		}else{
			System.out.println("this");
			return list;
		}
	}
	
	
	private List<Dept> getTreeJson(String deptId,String parentId,List<Dept> list){
		List<Dept> newList=new ArrayList<Dept>();
		for(Dept dept:list){
			if(dept.getParentId().equals(parentId)){
				if(!dept.getId().equals(deptId)){
					dept.setChildren(this.getTreeJson(deptId,dept.getId(),list));
					newList.add(dept);
				}
			}
		}
		return newList;
	}
	
	
	
	
	@Transactional
	@Override
	public void save(Dept dept) {
		dept.setId(CodeHelper.createUUID());
		dept.setOperDate(DateUtil.getDateTime());
		dept.setState(BaseType.ABLE.getValue());
		if(null==dept.getParentId()||"".equals(dept.getParentId())){
			dept.setParentId(Constant.ROOT_ID);
		}
		deptMapper.insert(dept);
	}

	
	@Override
	public R getDeptInfo(String deptId) {
		return R.ok().put("dept",deptMapper.selectByPrimaryKey(deptId));
	}

	@Transactional
	@Override
	public R deleteDept(String deptId) {
		//判断是否有子部门
		List<String> deptList = deptMapper.queryListByParentId(deptId);
		if(deptList.size() > 0){
			return R.error("请先删除子部门");
		}
		deptMapper.deleteByPrimaryKey(deptId);
		
		return R.ok();
	}

	
	

	
}
