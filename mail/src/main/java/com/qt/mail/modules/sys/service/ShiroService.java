package com.qt.mail.modules.sys.service;


import java.util.Set;

import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.entity.UserToken;

/**
 * shiro相关接口
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(String userId);

    UserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    User queryUser(String userId);
}
