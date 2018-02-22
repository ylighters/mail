package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.Video;

@Mapper
public interface VideoMapper extends BaseDao<Video>{

    int insertSelective(Video record);


    int updateByPrimaryKeySelective(Video record);
    
    
    List<Video> findVideoList(@Param("companyId")String companyId);

}