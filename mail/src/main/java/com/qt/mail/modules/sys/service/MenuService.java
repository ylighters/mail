package com.qt.mail.modules.sys.service;


import java.util.List;
import java.util.Set;

import com.qt.mail.modules.sys.entity.Menu;

/**
 * 菜单
 * @author Administrator
 *
 */
public interface MenuService {
   
	//得到菜单children模式
	List<Menu> findUserMenuList(String userId);
   
   Set<String> findUserPermissions(String userId);
   
    /**
     * 得到列表数据
     * @return
     */
    List<Menu> findMenuList();
}
