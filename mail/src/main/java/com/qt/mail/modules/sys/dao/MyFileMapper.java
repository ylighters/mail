package com.qt.mail.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.qt.mail.modules.sys.entity.MyFile;

@Mapper
public interface MyFileMapper extends BaseDao<MyFile>{
  
    int insertSelective(MyFile record);

    int updateByPrimaryKeySelective(MyFile record);
}