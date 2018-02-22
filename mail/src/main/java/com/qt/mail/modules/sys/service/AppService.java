package com.qt.mail.modules.sys.service;


import org.springframework.web.multipart.MultipartFile;

import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.entity.Message;
import com.qt.mail.modules.sys.entity.User;


/**
 * app管理
 * 
 */
public interface AppService {
	
	R loginIn(String phone,String pwd);
	
	R findAllCompany(String flagDate);
	
	R findAllDept();
	
	R findRoleList();
	
	R uploadImage(MultipartFile file,String width,String height);
	
	R uploadFile(MultipartFile file);
	
	R saveUser(User user);
	
	R updatePwd(String userId,String oldPwd,String newPwd);
	
	R sendYzm(String phone);
	
	R updatePwdByPhone(String phone,String newPwd);
	
	R findVideo(String companyId);
	
	R findContentList(String type);
	
	R getContentInfo(String messageId);
	
	R findRandomList(String userId);
	
	R findResultList(String randomId);
	
	R saveRandom(String userId,String[] companyIds,String remark);
	
	R saveMaterial(Message message);
	
	R findNewApp();
	
	R updateUserInfo(User user);
	
	R saveKdy(Kdy kdy);
	
	R kdyLogin(String lxfs,String pwd);
	

}
