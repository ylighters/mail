package com.qt.mail.modules.api.dao;

import org.apache.ibatis.annotations.Mapper;

import com.qt.mail.modules.api.entity.ApiTokenEntity;
import com.qt.mail.modules.sys.dao.BaseDao;

/**
 * 用户Token
 * 
 */
@Mapper
public interface ApiTokenDao extends BaseDao<ApiTokenEntity> {
    
    ApiTokenEntity queryByUserId(Long userId);

    ApiTokenEntity queryByToken(String token);
	
}
