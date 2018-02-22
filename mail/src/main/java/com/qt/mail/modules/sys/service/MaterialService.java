package com.qt.mail.modules.sys.service;


import java.util.Map;

import com.jhlabs.image.LightFilter.Material;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.entity.Message;


/**
 * 资料下载
 * 
 */
public interface MaterialService {
	 R findMaterialListByPage(Map<String, Object> params);
	 
	 R save(Message message);
	 
	 R getInfoById(String messageId);
	 
	 R update(Message message);
	 
	 R deleteBatch(String[] messageIds);
	 
	 /* 
	 
	 R getInfoById(String materialId);
	 
	
	 
	*/
}
