package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.User;
@Mapper
public interface UserMapper extends BaseDao<User>{
  
  

    int insertSelective(User record);
    
    int updatePwd(User user);

    int updateByPrimaryKeySelective(User record);

    
    User queryUserByPhone(@Param("phone")String phone);
    
    List<String> queryPermsByUserId(@Param("userId")String userId);
    
    List<String> queryAllMenuId(@Param("userId")String userId);
    
    List<User> findUserListByDeptId(@Param("deptId")String deptId,@Param("userType")String userType);
    
}