package com.qt.mail.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qt.mail.modules.sys.entity.CheckResult;
import com.qt.mail.modules.sys.entity.Company;
import com.qt.mail.modules.sys.vo.CompanyVO;
@Mapper
public interface CheckResultMapper extends BaseDao<CheckResult> {
   

    int insertSelective(CheckResult record);


    int updateByPrimaryKeySelective(CheckResult record);
    
    void saveByCompanyIds();
    
    List<CheckResult> findCheckResultListByColumn(@Param("randomId")String randomId,@Param("companyId")String companyId);
    
    List<CompanyVO> findCompanyListByRandomId(@Param("randomId")String randomId);
}