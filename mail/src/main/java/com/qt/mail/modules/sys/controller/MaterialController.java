package com.qt.mail.modules.sys.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qt.mail.common.annotation.SysLog;
import com.qt.mail.common.utils.R;
import com.qt.mail.common.validator.ValidatorUtils;
import com.qt.mail.common.validator.group.AddGroup;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.entity.Message;
import com.qt.mail.modules.sys.service.KdyService;
import com.qt.mail.modules.sys.service.MaterialService;





/**
 * 菜单相关
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/material")
public class MaterialController extends AbstractController{
	
	@Autowired
	private MaterialService materialService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:material:list")*/
	public R list(@RequestParam Map<String, Object> params){
		return materialService.findMaterialListByPage(params);
	}
	
	
	@SysLog("保存资料")
	@RequestMapping("/save")
	/*@RequiresPermissions("sys:material:save")*/
	public R save(@RequestBody Message message){
		ValidatorUtils.validateEntity(message);
		message.setAddPerson(this.getUserId());
		return materialService.save(message);
	}
	
	
	@RequestMapping("/info/{messageId}")
	public R getInfo(@PathVariable("messageId")String messageId){
		return materialService.getInfoById(messageId);
	}
	
	
	@SysLog("更新资料")
	@RequestMapping("/update")
	/*@RequiresPermissions("sys:material:update")*/
	public R update(@RequestBody Message message){
		ValidatorUtils.validateEntity(message);
		return materialService.update(message);
	}
	
	/**
	 * 删除资料
	 */
	@SysLog("删除资料")
	@RequestMapping("/delete")
	/*@RequiresPermissions("sys:material:delete")*/
	public R delete(@RequestBody String[] messageIds){
		
		return materialService.deleteBatch(messageIds);
	}
}
