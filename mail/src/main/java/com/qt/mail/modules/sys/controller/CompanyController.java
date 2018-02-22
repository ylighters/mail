package com.qt.mail.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.qt.mail.common.annotation.SysLog;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.R;
import com.qt.mail.common.validator.ValidatorUtils;
import com.qt.mail.common.validator.group.AddGroup;
import com.qt.mail.common.validator.group.UpdateGroup;
import com.qt.mail.modules.sys.entity.Company;
import com.qt.mail.modules.sys.entity.ViewType;
import com.qt.mail.modules.sys.service.CompanyService;

/**
 * 快递公司
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends AbstractController{
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 查看快递公司分页
	 */
	@RequestMapping("/list")
	/*@RequiresPermissions("sys:role:list")*/
	public R list(@RequestParam Map<String, Object> params){
		return companyService.queryCompanyListByPage(params);
	}

	@RequestMapping("/listAll")
	/*@RequiresPermissions("sys:role:listAll")*/
	public List<Company> listAll(){
		return companyService.findAllCompany(null);
	}

	
	/**
	 * 保存快递公司
	 */
	@SysLog("保存快递公司")
	@RequestMapping("/save")
	/*@RequiresPermissions("sys:company:save")*/
	public R save(@RequestBody Company company){
		ValidatorUtils.validateEntity(company,AddGroup.class);
	    return companyService.save(company);
	}
	
	/**
	 * 得到公司信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/info/{companyId}")
	/*@RequiresPermissions("sys:company:info")*/
	public R info(@PathVariable("companyId") String companyId){
		return  companyService.getCompanyInfo(companyId);
	}
	
	/**
	 * 查询公司列表
	 * @return
	 */
	@JsonView(ViewType.TreeView.class)
	@RequestMapping("/companyList/{type}")
	/*@RequiresPermissions("sys:role:list")*/
	public R companyList(@PathVariable("type")String type){
		if(type.equals("all")){
			type=null;
		}
		return companyService.queryCompanyListByState(type) ;
	}
	
	
	@JsonView(ViewType.TreeView.class)
	@RequestMapping("/allList")
	/*@RequiresPermissions("sys:role:list")*/
	public R companyAllList(String companyId){
		
	    List<Company> list=companyService.findAllCompany(companyId);
	    
	    Company c=new Company();
	    c.setId(Constant.ROOT_ID);
	    c.setName("一级网点");
	    list.add(c);
		return R.ok().put("companyList",list);
	}
	
	
	
	/**
	 * 修改快递公司
	 */
	@SysLog("修改快递公司")
	@RequestMapping("/update")
	/*@RequiresPermissions("sys:company:update")*/
	public R update(@RequestBody Company company){
		ValidatorUtils.validateEntity(company,UpdateGroup.class);
		return companyService.update(company);
	}
	
	/*@RequestMapping("/select")
	@RequiresPermissions("sys:role:list")
	public R select(){
		return roleService.queryList();
	}
	
	*//**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	/*@RequiresPermissions("sys:company:delete")*/
	public R delete(@RequestBody String[] companyIds){
		return companyService.deleteCompanys(companyIds);
	}
	
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/deleteOne/{companyId}")
	/*@RequiresPermissions("sys:company:delete")*/
	public R delete(@PathVariable String companyId){
		return companyService.delete(companyId);
	}
	
	
	
	
}
