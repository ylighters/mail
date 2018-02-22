package com.qt.mail.modules.sys.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.UserToken;

@Mapper
public interface UserTokenMapper extends BaseDao<UserToken>{
   /* int deleteByPrimaryKey(String id);

    int insert(UserToken record);*/

    int insertSelective(UserToken record);

    UserToken selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserToken record);

  /*  int updateByPrimaryKey(UserToken record);*/
    
    UserToken getByUserId(@Param("userId")String userId);
    
    UserToken getByToken(@Param("token")String token);
}