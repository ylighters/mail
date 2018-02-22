package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Company;
import com.qt.mail.modules.sys.entity.Role;
import com.qt.mail.modules.sys.entity.User;


/**
 * 系统用户
 * 
 */
public interface CompanyService {

	List<Company> findAllCompany(String companyId);
	
	R queryCompanyListByPage(Map<String, Object> map);
	
	R save(Company company);
	
	R getCompanyInfo(String companyId);
	
	R queryCompanyListByState(String type);
	
	R update(Company company);
	
	R deleteCompanys(String[] companyIds);
	
	R delete(String companyId);
	
	
	
}
