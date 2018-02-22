package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.RandomCheck;

@Mapper
public interface RandomCheckMapper extends BaseDao<RandomCheck>{
   

    int insertSelective(RandomCheck record);

 

    int updateByPrimaryKeySelective(RandomCheck record);
    
    
    List<RandomCheck> findRandomList(@Param("userId")String userId);
}