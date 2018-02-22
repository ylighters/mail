package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.Company;
@Mapper
public interface CompanyMapper extends BaseDao<Company>{
  

    int insert(Company record);

    int insertSelective(Company record);

 
    int updateByPrimaryKeySelective(Company record);
    
    List<Company> findComanyListByState(@Param("type")String type);
    
    List<Company> findCompanyByParentId(@Param("parentId")String parentId);
    
    //app 专用
    List<Company> findComanyListByFlagDate(@Param("flagDate")String flagDate,@Param("wz")String wz);
    
    //随机抽检
    List<Company>  findRandomCheckIds(@Param("sl")String sl);
    //随机抽检
    List<Company>  findCompanyIdsByLaver(@Param("a")String a,@Param("b")String b,@Param("c")String c,@Param("d")String d);

    
}