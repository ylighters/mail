package com.qt.mail.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.qt.mail.modules.sys.entity.App;

@Mapper
public interface AppMapper extends BaseDao<App>{
    int insertSelective(App record);

    int updateByPrimaryKeySelective(App record);
    
    App findNewApp();
}