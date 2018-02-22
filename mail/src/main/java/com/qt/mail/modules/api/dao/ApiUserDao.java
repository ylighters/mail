package com.qt.mail.modules.api.dao;

import org.apache.ibatis.annotations.Mapper;

import com.qt.mail.modules.api.entity.ApiUserEntity;
import com.qt.mail.modules.sys.dao.BaseDao;

/**
 * 用户
 * 
 */
@Mapper
public interface ApiUserDao extends BaseDao<ApiUserEntity> {

    ApiUserEntity queryByMobile(String mobile);
}
