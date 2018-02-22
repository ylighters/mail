package com.qt.mail.modules.sys.service;


import java.util.Map;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.App;


/**
 * 快递员管理
 * 
 */
public interface AndroidService {
	 R findAppListByPage(Map<String, Object> params);
	 
	 R save(App app);
	 
	 R deleteBatch(String[] appIds);

}
