package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Map;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Log;

/**
 * 系统日志
 * 
 */
public interface LogService {
	
	R findLogListByPage(Map<String, Object> params);
	
	void save(Log log);
}
