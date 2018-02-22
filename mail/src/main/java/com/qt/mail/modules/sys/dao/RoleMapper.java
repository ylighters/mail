package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.Role;
@Mapper
public interface RoleMapper extends BaseDao<Role>{
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    int updateByPrimaryKeySelective(Role record);
    
    List<Role> queryAll();
    
    
    
}