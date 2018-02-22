package com.qt.mail.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qt.mail.common.utils.Constant;
import com.qt.mail.modules.sys.dao.MenuMapper;
import com.qt.mail.modules.sys.entity.Menu;
import com.qt.mail.modules.sys.service.MenuService;



/**
 * 系统用户
 * 
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> findUserMenuList(String userId) {
		//系统管理员，拥有最高权限
		if(userId.equals(Constant.SUPER_ADMIN)){
			return this.getAllMenuList(Constant.ROOT_ID,menuMapper.findMenuListByUserId(null));
		}else{
			return this.getAllMenuList(Constant.ROOT_ID,menuMapper.findMenuListByUserId(userId));
		}
	}

	@Override
	public Set<String> findUserPermissions(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 递归菜单
	 * @param parentId
	 * @param list
	 * @return
	 */
	private List<Menu> getAllMenuList(String parentId,List<Menu> list){
		List<Menu> li=new ArrayList<Menu>();
		for(Menu menu:list){
		   if(menu.getParentId().equals(parentId)){
			   menu.setList(this.getAllMenuList(menu.getId(),list));
			   li.add(menu);
		   }  	
		}
		return li;
	}

	@Override
	public List<Menu> findMenuList() {
		return menuMapper.findMenuListByUserId(null);
	}
	
	
	
/*	*//**
	 * 获取所有菜单列表
	 *//*
	private List<Menu> getAllMenuList(List<String> menuIdList){
		//查询根菜单列表
		List<Menu> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}
*/
   
	
	
}
