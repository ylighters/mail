package com.qt.mail.modules.sys.service;


import java.util.Map;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Kdy;


/**
 * 快递员管理
 * 
 */
public interface KdyService {
	 R findKdyListByPage(Map<String, Object> params);
	 
	 R save(Kdy kdy);
	 
	 R getInfoById(String kdyId);
	 
	 R update(Kdy kdy);
	 
	 R deleteBatch(String[] kdyIds);
}
