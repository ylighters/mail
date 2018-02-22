package com.qt.mail.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.Kdy;
@Mapper
public interface KdyMapper extends BaseDao<Kdy> {
   
    int insertSelective(Kdy record);

    int updateByPrimaryKeySelective(Kdy record);
    
    Kdy findKdyByLxfs(@Param("lxfs")String lxfs);
}