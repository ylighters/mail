package com.qt.mail.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qt.mail.common.annotation.SysLog;
import com.qt.mail.common.utils.R;
import com.qt.mail.common.validator.ValidatorUtils;
import com.qt.mail.modules.sys.entity.RandomCheck;
import com.qt.mail.modules.sys.service.RandomService;
import com.qt.mail.modules.sys.vo.CompanyVO;





/**
 * 菜单相关
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/random")
public class RandomController extends AbstractController{
	
	@Autowired
	private RandomService randomService;
	
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:video:list")*/
	public R list(@RequestParam Map<String, Object> params){
		return randomService.findRandomListByPage(params);
	}
	
	
	
	@RequestMapping("/wdList/{randomId}")
	/*@RequiresPermissions("sys:video:list")*/
	public Object wdList(@PathVariable String randomId){
		return randomService.findRandomListByParentId(randomId);
	}
	
	
	
	
	
	@RequestMapping("/randomCheck")
	/*@RequiresPermissions("sys:random:randomCheck")*/
	public R randomCheck(@RequestBody RandomCheck rc){
		ValidatorUtils.validateEntity(rc);
		rc.setOperPerson(this.getUserId());
		return randomService.randomCheck(rc);
	}
	
	
	
	
	@SysLog("保存抽检")
	@RequestMapping("/saveRandom")
	/*@RequiresPermissions("sys:random:save")*/
	public R save(@RequestBody RandomCheck rc){
		ValidatorUtils.validateEntity(rc);
		return randomService.save(rc);
	}
	
	
	@SysLog("删除抽检结果")
	@RequestMapping("/deleteResult")
	/*@RequiresPermissions("sys:random:deleteResult")*/
	public R delete(String companyId,String randomId){
		return randomService.deleteResult(companyId,randomId);
	}
	
	
	@RequestMapping("/getAndUpdateRs/{randomId}")
	public R getAndUpdateRs(@PathVariable("randomId")String randomId){
		return randomService.getAndUpdateRs(randomId);
	}
	
	
	@RequestMapping("/saveResult")
	public R saveResult(String companyId,String randomId){
		return randomService.saveResult(companyId,randomId,this.getUserId());
	}
	
	
	@SysLog("删除抽检结果")
	@RequestMapping("/delete")
	/*@RequiresPermissions("sys:random:delete")*/
	public R delete(@RequestBody String[] randomIds){
		return randomService.deleteBatch(randomIds);
	}
}
