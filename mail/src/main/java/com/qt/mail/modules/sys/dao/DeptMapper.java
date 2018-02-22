package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.Dept;
@Mapper
public interface DeptMapper extends BaseDao<Dept>{
/*    int deleteByPrimaryKey(String id);*/

    int insert(Dept record);

    int insertSelective(Dept record);

/*    Dept selectByPrimaryKey(String id);*/

    int updateByPrimaryKeySelective(Dept record);

  /*  int updateByPrimaryKey(Dept record);*/
    
    List<String> queryListByParentId(@Param("parentId")String parentId);
    
}