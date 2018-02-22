package com.qt.mail.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.PageUtils;
import com.qt.mail.common.utils.Query;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.CheckResultMapper;
import com.qt.mail.modules.sys.dao.CompanyMapper;
import com.qt.mail.modules.sys.dao.UserMapper;
import com.qt.mail.modules.sys.entity.Company;
import com.qt.mail.modules.sys.service.CompanyService;




@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CheckResultMapper crMapper;
	

	@Override
	public R queryCompanyListByPage(Map<String, Object> map) {
		//查询列表数据
		Query query = new Query(map);
		List<Company> userList = companyMapper.queryList(query);
		int total = companyMapper.queryTotal(query);
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	
	}
	
	
	/**
	 * 添加公司或网点
	 */
	@Transactional
	@Override
	public R save(Company company) {
		company.setId(CodeHelper.createUUID());
		company.setState(Constant.BaseType.ABLE.getValue());
		company.setOperDate(DateUtil.getDateTime());
		/*if(company.getType().equals(Constant.BaseType.ABLE.getValue())){//公司
			company.setParentId(Constant.ROOT_ID);
		}*/
		int ct=companyMapper.insert(company);
		if(ct==1){
			return R.ok();
		}else{
			return R.error().put("msg","添加异常，请与管理员联系！");
		}
	}


	@Override
	public R getCompanyInfo(String companyId) {
         Company company=companyMapper.selectByPrimaryKey(companyId);
		return R.ok().put("company",company);
	}


	@Override
	public R queryCompanyListByState(String type) {
		return R.ok().put("companyList",companyMapper.findComanyListByState(type));
	}

    @Transactional
	@Override
	public R update(Company company) {
		company.setOperDate(DateUtil.getDateTime());
		int ct=companyMapper.updateByPrimaryKey(company);
		if(ct==1){
			return R.ok();
		}else{
			return R.error().put("msg","修改异常，请与管理员联系！");
		}
	}

    @Transactional
	@Override
	public R deleteCompanys(String[] companyIds) {
		//删除没有网点和用户的用户
    	for(String id:companyIds){
    		//查询是否可以删除
    		int sons=companyMapper.findCompanyByParentId(id).size();
    		int users=userMapper.findUserListByDeptId(id,Constant.UserType.KDY.getValue()).size();
    		int checks=crMapper.findCheckResultListByColumn(null,id).size();
    		if(sons==0||users==0||checks==0){
    			continue;
    		}else{
    		   companyMapper.deleteByPrimaryKey(id);	
    		}
    	}
		return R.ok();
	}


	@Override
	public List<Company> findAllCompany(String companyId) {
		List<Company> list=companyMapper.findComanyListByState(null);
		if(null!=companyId&&!"".equals(companyId)){
			List<Company> rlist=companyMapper.findCompanyByParentId(companyId);
			Company n=new Company();
			n.setId(companyId);
			rlist.add(n);
			if(null!=rlist&&rlist.size()>0){
				list.removeAll(rlist);
			}
		}
		return list;
	}


	@Override
	public R delete(String companyId) {
		int sons=companyMapper.findCompanyByParentId(companyId).size();
		int users=userMapper.findUserListByDeptId(companyId,Constant.UserType.KDY.getValue()).size();
		if(sons!=0||users!=0){
			return R.error("存在子部门或快递人员，不可删除！");
		}else{
			int ct=companyMapper.deleteByPrimaryKey(companyId);
		}
		return R.ok();
		
	}
	
/**
	 * 修改角色
	 *//*
	@Override
	public int update(Role role) {
		int ct=roleMapper.updateByPrimaryKey(role);
		roleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
		return ct;
	}

	
	@Override
	public R getRoleInfo(String roleId) {
         Role role =roleMapper.selectByPrimaryKey(roleId);
		
		//查询角色对应的菜单
		List<String> menuIdList = roleMenuService.queryMenuIdsByRoleId(roleId);
		role.setMenuIdList(menuIdList);
		return R.ok().put("role", role);
	}

	*//**
	 * 删除用户
	 *//*
	@Transactional
	@Override
	public R deleteRoles(String[] roleIds) {
		roleMapper.deleteBatch(roleIds);
		
		return R.ok();
	}

	@Override
	public R queryList() {
		return R.ok().put("roleList",roleMapper.queryAll());
	}

	*/
	

	

	
}
