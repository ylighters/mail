package com.qt.mail.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.PageUtils;
import com.qt.mail.common.utils.Query;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.VideoMapper;
import com.qt.mail.modules.sys.entity.Video;
import com.qt.mail.modules.sys.service.VideoService;




@Service("videoService")
public class VideoServiceImpl implements VideoService {
     
	@Autowired
	private VideoMapper videoMapper;
	
	
	@Override
	public R findVideoListByPage(Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);
		List<Video> videoList = videoMapper.queryList(query);
		int total = videoMapper.queryTotal(query);
		PageUtils pageUtil = new PageUtils(videoList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}


	@Override
	public R getInfoById(String videoId) {
		return R.ok().put("video",videoMapper.selectByPrimaryKey(videoId));
	}


	@Transactional
	@Override
	public R save(Video video) {
		video.setId(CodeHelper.createUUID());
		video.setOperDate(DateUtil.getDateTime());
		int ct=videoMapper.insert(video);
		if(ct==1){
			return R.ok();
		}else{
			return R.error("数据异常，添加失败！");
		}
	}

	@Transactional
	@Override
	public R update(Video video) {
		video.setOperDate(DateUtil.getDateTime());
		int ct=videoMapper.updateByPrimaryKey(video);
		if(ct==1){
			return R.ok();
		}else{
			return R.error("数据异常，修改失败！");
		}
	}

	@Transactional
	@Override
	public R deleteBatch(String[] videoIds) {
		videoMapper.deleteBatch(videoIds);
		return R.ok();
	}
	
	
	
	
	
	/*
	
	
	@Autowired
	private UserMapper userMapper;
	

	
	
	
	*//**
	 * 添加公司或网点
	 *//*
	@Transactional
	@Override
	public R save(Message message) {
		return R.ok();
	}


	@Override
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
	
	

	

	
*/}
