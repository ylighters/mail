package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.Message;

@Mapper
public interface MessageMapper extends BaseDao<Message> {
  
    int insertSelective(Message record);

    int updateByPrimaryKeySelective(Message record);
    
    List<Message> findAllMessage(@Param("type")String type);
}