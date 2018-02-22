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
import com.qt.mail.modules.sys.entity.Message;
import com.qt.mail.modules.sys.entity.Video;
import com.qt.mail.modules.sys.service.VideoService;





/**
 * 菜单相关
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/video")
public class VideoController extends AbstractController{
	
	@Autowired
	private VideoService videoService;
	
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:video:list")*/
	public R list(@RequestParam Map<String, Object> params){
		return videoService.findVideoListByPage(params);
	}
	
	
	@RequestMapping("/info/{videoId}")
	public R getInfo(@PathVariable("videoId")String videoId){
		return videoService.getInfoById(videoId);
	}
	
	
	@SysLog("保存视频地址")
	@RequestMapping("/save")
	/*@RequiresPermissions("sys:video:save")*/
	public R save(@RequestBody Video video){
		ValidatorUtils.validateEntity(video);
		return videoService.save(video);
	}

	@SysLog("更新视频地址")
	@RequestMapping("/update")
	/*@RequiresPermissions("sys:video:update")*/
	public R update(@RequestBody Video video){
		return videoService.update(video);
	}
	
	@SysLog("删除视频地址")
	@RequestMapping("/delete")
	/*@RequiresPermissions("sys:video:delete")*/
	public R delete(@RequestBody String[] videoIds){
		return videoService.deleteBatch(videoIds);
	}
}
