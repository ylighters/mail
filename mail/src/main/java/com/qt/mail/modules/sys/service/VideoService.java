package com.qt.mail.modules.sys.service;


import java.util.Map;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Message;
import com.qt.mail.modules.sys.entity.Video;


/**
 * 资料下载
 * 
 */
public interface VideoService {
	 R findVideoListByPage(Map<String, Object> params);
	
	 R getInfoById(String videoId);
	 
     R save(Video video);
	 
	 R update(Video video);
	 
	 R deleteBatch(String[] videoIds);	
}
