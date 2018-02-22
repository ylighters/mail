package com.qt.mail.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.PageUtils;
import com.qt.mail.common.utils.Query;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.MessageMapper;
import com.qt.mail.modules.sys.dao.UserMapper;
import com.qt.mail.modules.sys.entity.Company;
import com.qt.mail.modules.sys.entity.Message;
import com.qt.mail.modules.sys.service.MaterialService;




@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
	@Autowired
	private MessageMapper messageMapper;
	
	/*@Autowired
	private UserMapper userMapper;*/
	

	@Override
	public R findMaterialListByPage(Map<String, Object> map) {
		//查询列表数据
		Query query = new Query(map);
		List<Message> userList = messageMapper.queryList(query);
		int total = messageMapper.queryTotal(query);
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	
	}
	 
	
	@Transactional
	@Override
	public R save(Message message) {
		message.setAddDate(DateUtil.getDateTime());
		message.setId(CodeHelper.createUUID());
		message.setType(Constant.UserType.POST.getValue());//邮政局发布
		message.setState(Constant.BaseType.ABLE.getValue());
		message.setCount(0);
		int ct=messageMapper.insert(message);
		if(ct==1){
			return R.ok();
		}else{
			return R.error("添加异常，请于管理员联系！");
		}
		//return R.ok();
	}


	@Override
	public R getInfoById(String messageId) {
		return R.ok().put("message",messageMapper.selectByPrimaryKey(messageId));
	}


	@Transactional
	@Override
	public R update(Message message) {
		message.setAddDate(DateUtil.getDateTime());
		int ct=messageMapper.updateByPrimaryKeySelective(message);
		if(ct==1){
			return R.ok();
		}else{
			return R.error("修改异常，请于管理员联系！");
		}
	}


	@Transactional
	@Override
	public R deleteBatch(String[] messageIds) {
		 messageMapper.deleteBatch(messageIds);
		 return R.ok();
	}



/*	@Override
	public R getCompanyInfo(String companyId) {
         Company company=companyMapper.selectByPrimaryKey(companyId);
		return R.ok().put("company",company);
	}


	@Override
	public R queryCompanyListByState(String type) {
		return R.ok().put("companyList",companyMapper.findComanyListByState(type));
	}

    @Transactional
	@Override
	public R update(Company company) {
		company.setOperDate(DateUtil.getDateTime());
		int ct=companyMapper.updateByPrimaryKey(company);
		if(ct==1){
			return R.ok();
		}else{
			return R.error().put("msg","修改异常，请与管理员联系！");
		}
	}

    @Transactional
	@Override
	public R deleteCompanys(String[] companyIds) {
		//删除没有网点和用户的用户
    	for(String id:companyIds){
    		//查询是否可以删除
    		int sons=companyMapper.findCompanyByParentId(id).size();
    		int users=userMapper.findUserListByDeptId(id,Constant.UserType.KDY.getValue()).size();
    		if(sons!=0||users!=0){
    			continue;
    		}else{
    		   companyMapper.deleteByPrimaryKey(id);	
    		}
    	}
		return R.ok();
	}
	*/
	

	

	
}
