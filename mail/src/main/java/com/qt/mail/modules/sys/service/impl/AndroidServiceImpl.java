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
import com.qt.mail.modules.sys.dao.AppMapper;
import com.qt.mail.modules.sys.entity.App;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.service.AndroidService;


/**
 * 
 * 
 */
@Service("androidService")
public class AndroidServiceImpl implements AndroidService {
	@Autowired
	private AppMapper appMapper;

	@Override
	public R findAppListByPage(Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);
		List<App> kdyList = appMapper.queryList(query);
		int total = appMapper.queryTotal(query);
		PageUtils pageUtil = new PageUtils(kdyList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	@Transactional
	@Override
	public R save(App app) {
		app.setId(CodeHelper.createUUID());
		app.setAddDate(DateUtil.getDateTime());
		int ct=appMapper.insert(app);
		if(ct==1){
			return R.ok();
		}else{
			return R.error("添加app异常！");
		}
	}
	
	
	 @Transactional
	 @Override
		public R deleteBatch(String[] appIds) {
			int ct= appMapper.deleteBatch(appIds);
			if(ct>0){
				return R.ok();
			}else{
				return R.error("未找到要删除的数据");
			}
		}


	

}
