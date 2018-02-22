package com.qt.mail.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.qt.mail.modules.sys.entity.Log;

@Mapper
public interface LogMapper extends BaseDao<Log>{
   
   
    int insertSelective(Log record);

    int updateByPrimaryKeySelective(Log record);
}