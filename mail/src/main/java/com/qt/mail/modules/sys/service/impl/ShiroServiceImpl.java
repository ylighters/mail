package com.qt.mail.modules.sys.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qt.mail.common.utils.Constant;
import com.qt.mail.modules.sys.dao.MenuMapper;
import com.qt.mail.modules.sys.dao.UserMapper;
import com.qt.mail.modules.sys.dao.UserTokenMapper;
import com.qt.mail.modules.sys.entity.Menu;
import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.entity.UserToken;
import com.qt.mail.modules.sys.service.ShiroService;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserTokenMapper userTokenMapper;

    @Override
    public Set<String> getUserPermissions(String userId) {
        List<String> permsList;
        //系统管理员，拥有最高权限
        if(userId.equals(Constant.SUPER_ADMIN)){
            List<Menu> menuList = menuMapper.queryList(new HashMap());
            permsList = new ArrayList<>(menuList.size());
            for(Menu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = userMapper.queryPermsByUserId(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public UserToken queryByToken(String token) {
        return userTokenMapper.getByToken(token);
    }

    @Override
    public User queryUser(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
