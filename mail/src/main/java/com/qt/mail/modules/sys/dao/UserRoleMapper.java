package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.UserRole;
@Mapper
public interface UserRoleMapper extends BaseDao<UserRole>{
   
    int insert(UserRole record);

    int insertSelective(UserRole record);


    int updateByPrimaryKeySelective(UserRole record);
    
    void deleteByUserId(@Param("userId")String userId);
    
    List<String> getRoleListByUserId(@Param("userId")String userId);

    
}