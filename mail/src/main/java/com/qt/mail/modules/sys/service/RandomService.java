package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Map;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.RandomCheck;
import com.qt.mail.modules.sys.vo.CompanyVO;


/**
 * 资料下载
 * 
 */
public interface RandomService {
	 R findRandomListByPage(Map<String, Object> params);
	 
	 Object findRandomListByParentId(String randomId);
	 
	 R randomCheck(RandomCheck randomCheck);
	
	 R save(RandomCheck randomCheck);
	 
	 R deleteResult(String companyId,String randomId);
	 
	 R getAndUpdateRs(String randomId);
	 
	 R saveResult(String companyId,String randomId,String userId);
	 
	 R deleteBatch(String[] randomIds);
	 
}
