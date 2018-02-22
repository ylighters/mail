package com.qt.mail.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.MD5;
import com.qt.mail.common.utils.PageUtils;
import com.qt.mail.common.utils.Query;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.KdyMapper;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.service.KdyService;


/**
 * 
 * 
 */
@Service("kdyService")
public class KdyServiceImpl implements KdyService {
	@Autowired
	private KdyMapper kdyMapper;

	@Override
	public R findKdyListByPage(Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);
		List<Kdy> kdyList = kdyMapper.queryList(query);
	
		int total = kdyMapper.queryTotal(query);
		PageUtils pageUtil = new PageUtils(kdyList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	

	@Transactional
	@Override
	public R save(Kdy kdy) {
		kdy.setId(CodeHelper.createUUID());
		kdy.setOperDate(DateUtil.getDateTime());
		kdy.setPwd(MD5.md5(kdy.getPwd()));
		int ct=kdyMapper.insert(kdy);
		if(ct==1){
			return R.ok();
		}else{
			return R.error().put("msg","保存异常，请与管理员联系！");
		}
	}


	@Override
	public R getInfoById(String kdyId) {
		return R.ok().put("kdy",kdyMapper.selectByPrimaryKey(kdyId));
	}

    @Transactional
	@Override
	public R update(Kdy kdy) {
		kdy.setOperDate(DateUtil.getDateTime());
		int ct=kdyMapper.updateByPrimaryKey(kdy);
		if(ct==1){
			return R.ok();
		}else{
			return R.error().put("msg","修改异常，请与管理员联系！");
		}
	}

    @Transactional
	@Override
	public R deleteBatch(String[] kdyIds) {
		int ct= kdyMapper.deleteBatch(kdyIds);
		return R.ok();
	}
	

}
