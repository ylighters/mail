package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Role;
import com.qt.mail.modules.sys.entity.User;


/**
 * 文件管理
 * 
 */
public interface FileService {
	R uploadFile(MultipartFile file);
	
	R uploadAllFile(MultipartFile file);
	
	void downloadFile(HttpServletRequest request,
			HttpServletResponse response,String fileId);
	
	R uploadImage(MultipartFile file);

}
