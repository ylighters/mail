package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.RoleMenu;
@Mapper
public interface RoleMenuMapper extends BaseDao<RoleMenu>{
    int deleteByPrimaryKey(String id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);
    
    int deleteByRoleId(@Param("roleId")String roleId);
    
    List<String> queryMenuIdsByRoleId(@Param("roleId")String roleId);
}