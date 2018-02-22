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
import com.qt.mail.modules.sys.dao.LogMapper;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.entity.Log;
import com.qt.mail.modules.sys.service.KdyService;
import com.qt.mail.modules.sys.service.LogService;


/**
 * 
 * 
 */
@Service("logService")
public class LogServiceImpl implements LogService {
	@Autowired
	private LogMapper logMapper;

	@Override
	public R findLogListByPage(Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);
		List<Log> kdyList = logMapper.queryList(query);
	
		int total = logMapper.queryTotal(query);
		PageUtils pageUtil = new PageUtils(kdyList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	@Override
	public void save(Log log) {
		log.setId(CodeHelper.createUUID());
		logMapper.insert(log);
	}
	


	

}
