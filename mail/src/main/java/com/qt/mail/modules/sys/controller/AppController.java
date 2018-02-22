package com.qt.mail.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.entity.Message;
import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.entity.ViewType;
import com.qt.mail.modules.sys.service.AppService;

/**
 * app接口
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	private AppService appService;
	/**
	 * 登陆
	 * @param phone
	 * @param pwd
	 * @return
	 */
	@JsonView(ViewType.AppView.class) 
	@RequestMapping("/login")
	public R login(@RequestParam(required=true)String phone,@RequestParam(required=true)String pwd){
	   return appService.loginIn(phone,pwd);
	}
	
	/**
	 * 得到所有网点
	 * @param flagDate
	 * @return
	 */
	@RequestMapping("/findAllCompany")
	public R getCompany(String flagDate){
		if(null==flagDate||"".equals(flagDate)){
			flagDate="2017-01-01 00:00:00";
		}
		return appService.findAllCompany(flagDate);
	}
	
	
	/**
	 * 查看所有视频地址
	 * @param companyId
	 * @return
	 */
	@RequestMapping("/findVideo")
	public R findVideo(@RequestParam(required=false)String companyId){
		return appService.findVideo(companyId);
	}
	
	
	
	/**
	 * 查看所有公告
	 * @return
	 */
	@JsonView(ViewType.AppView.class)
	@RequestMapping("/findContentList")
	public R findContentList(@RequestParam(required=false)String type){
		return appService.findContentList(type);
	}
	
	
	/**
	 * 得到内容
	 * @param messageId
	 * @return
	 */
	@RequestMapping("/getContentInfo/{messageId}")
	public R getContentInfo(@PathVariable String messageId){
		return appService.getContentInfo(messageId);
	}
	
	
	/**
	 * 查看抽检列表
	 * @param userId
	 * @return
	 */
	@RequestMapping("/findRandomList")
	public R findRandomList(@RequestParam(required=false)String userId){
		return appService.findRandomList(userId);
	}
	
	/**
	 * 查看抽检结果列表
	 * @param userId
	 * @return
	 */
	@RequestMapping("/findResultList")
	public R findResultList(@RequestParam(required=false)String randomId){
		return appService.findResultList(randomId);
	}
	
	
	
	/**
	 * 保存抽检结果
	 * @param messageId
	 * @return
	 */
	@RequestMapping("/saveRandom")
	public R saveRandom(String userId,String[] randomIds,String remark){
		return appService.saveRandom(userId,randomIds,remark);
	}
	
	
	
	/**
	 * 得到所有邮政部门列表
	 * @return
	 */
	@JsonView(ViewType.AppView.class) 
	@RequestMapping("/findAllDept")
	public R findAllDept(){
		return appService.findAllDept();
	}
	
	
	/**
	 * 得到所有角色
	 * @return
	 */
	@JsonView(ViewType.AppView.class) 
	@RequestMapping("/findRoleList")
	public R findAllRole(){
		return appService.findRoleList();
	}
	
	/**
	 * 上传头像
	 * @return
	 */
	@RequestMapping("/uploadImage")
	public R uploadImage(@RequestParam("file") MultipartFile file,String width,String height){
		return appService.uploadImage(file,width,height);
	}
	
	/**
	 * 上传文件
	 * @return
	 */
	@RequestMapping("/uploadFile")
	public R uploadFile(@RequestParam("file") MultipartFile file){
		return appService.uploadFile(file);
	}
	
	/**
	 * 添加资料
	 * @param file
	 * @return
	 */
	@RequestMapping("/saveMaterial")
	public R uploadFile(@RequestBody Message message){
		return appService.saveMaterial(message);
	}
	
	
	
	
	/**
	 * 注册邮政局用户
	 * @param user
	 * @return
	 */
	@RequestMapping("/saveUser")
	public R saveUser(@RequestBody User user){
	   return appService.saveUser(user);
	}
	
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	@RequestMapping("/updatePwd")
	public R updatePwd(@RequestParam(required=true)String userId,@RequestParam(required=true)String oldPwd,@RequestParam(required=true)String newPwd){
	   return appService.updatePwd(userId,oldPwd,newPwd);
	}
	
	
	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping("/sendYzm")
	public R sendYzm(@RequestParam(required=true)String phone){
	   return appService.sendYzm(phone);
	}
	
	/**
	 * 用手机更新密码
	 * @param phone
	 * @param newPwd
	 * @return
	 */
	@RequestMapping("/updatePwdByPhone")
	public R updatePwdByPhone(@RequestParam(required=true)String phone,@RequestParam(required=true)String newPwd){
	   return appService.updatePwdByPhone(phone,newPwd);
	}
	
	
	/**
	 * 最新app
	 * @return
	 */
	@RequestMapping("/findNewApp")
	public R findNewApp(){
	   return appService.findNewApp();
	}
	
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/updateUserInfo")
	public R updateUserInfo(@RequestBody User user){
	   return appService.updateUserInfo(user);
	}
	
	
	
	
	
	
	
	/**
	 * 快递人员登陆
	 * @param user
	 * @return
	 */
	@RequestMapping("/saveKdy")
	public R saveKdy(@RequestBody Kdy kdy){
	   return appService.saveKdy(kdy);
	}
	
	/**
	 * 快递人员注册
	 * @param kdy
	 * @return
	 */
	@RequestMapping("/kdyLogin")
	public R kdyLogin(@RequestParam(required=true)String lxfs,@RequestParam(required=true)String pwd){
	   return appService.kdyLogin(lxfs,pwd);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
