package com.qt.mail.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.qt.mail.modules.sys.dao.CheckResultMapper;
import com.qt.mail.modules.sys.dao.CompanyMapper;
import com.qt.mail.modules.sys.dao.RandomCheckMapper;
import com.qt.mail.modules.sys.entity.CheckResult;
import com.qt.mail.modules.sys.entity.Company;
import com.qt.mail.modules.sys.entity.RandomCheck;
import com.qt.mail.modules.sys.service.RandomService;
import com.qt.mail.modules.sys.vo.CompanyVO;




@Service("randomService")
public class RandomServiceImpl implements RandomService {
     
	@Autowired
	private RandomCheckMapper rcMapper;
	
	@Autowired
	private CheckResultMapper crMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	
	@Override
	public R findRandomListByPage(Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);
		List<RandomCheck> randomList = rcMapper.queryList(query);
		int total = rcMapper.queryTotal(query);
		PageUtils pageUtil = new PageUtils(randomList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	@Transactional
	@Override
	public R randomCheck(RandomCheck randomCheck) {
		//生成随机ID
		String randomId=CodeHelper.createUUID();
		randomCheck.setId(randomId);
		//randomCheck.setCount(count);
		randomCheck.setOperDate(DateUtil.getDateTime());
		randomCheck.setState(Constant.BaseType.DISABLE.getValue());
		
		//随机抽检
		//List<CheckResult> list=new ArrayList<CheckResult>();
		List<Company> companyList=new ArrayList<Company>();
		if(randomCheck.getType().equals(Constant.RandomType.Random.getValue())){
			companyList=companyMapper.findRandomCheckIds(randomCheck.getCount());
			/*randomCheck.setCheckRule("随机抽取");*/
		   	
		}else if(randomCheck.getType().equals(Constant.RandomType.ByLever.getValue())){
				companyList=companyMapper.findCompanyIdsByLaver((randomCheck.getCjslA()==null||randomCheck.getCjslA().equals("")?"0":randomCheck.getCjslA()),
						(randomCheck.getCjslB()==null||randomCheck.getCjslB().equals("")?"0":randomCheck.getCjslB()),
						(randomCheck.getCjslC()==null||randomCheck.getCjslC().equals("")?"0":randomCheck.getCjslC()),
						(randomCheck.getCjslD()==null||randomCheck.getCjslD().equals("")?"0":randomCheck.getCjslD()));
			
		}
		randomCheck.setCount(companyList.size()+"");
		rcMapper.insert(randomCheck);
		if(companyList.size()>0){
			//批量插入
			Map<String, Object> map = new HashMap<>();
			map.put("randomId", randomId);
			map.put("companyList", companyList);
			crMapper.save(map);//默认不可用
		}
		
		
		return R.ok().put("randomId",randomId).put("resultList",companyList).put("randomSize",companyList.size());
	}

	@Transactional
	@Override
	public R save(RandomCheck randomCheck) {
		RandomCheck old=rcMapper.selectByPrimaryKey(randomCheck.getId());
		
		old.setOperDate(DateUtil.getDateTime());
		old.setState(Constant.BaseType.ABLE.getValue());
		old.setRemark(randomCheck.getRemark());
		List<CheckResult> list=crMapper.findCheckResultListByColumn(randomCheck.getId(),null);

		old.setCount(list.size()+"");
		int ct=rcMapper.updateByPrimaryKey(old);
		if(ct==1){
			return R.ok();
		}else{
			return R.error("保存异常,请于管理员联系！");
		}
	}


	@Override
	public R deleteResult(String companyId, String randomId) {
		//删除当前
		List<CheckResult> list=crMapper.findCheckResultListByColumn(randomId, companyId);
		if(list!=null){
			crMapper.deleteByPrimaryKey(list.get(0).getId());
			return R.ok();
		}
		 return R.error("参数异常，请与管理员联系");
	}


	@Override
	public R getAndUpdateRs(String randomId) {
		List<CompanyVO> companyList=crMapper.findCompanyListByRandomId(randomId);
		return R.ok().put("resultList",companyList).put("randomSize",companyList.size());

	}


	@Transactional
	@Override
	public R saveResult(String companyId, String randomId,String userId) {
		
		if(null==randomId||"".equals(randomId)||randomId.equals("null")){
			randomId=CodeHelper.createUUID();
			RandomCheck rc=new RandomCheck();
			rc.setId(randomId);
			rc.setOperDate(DateUtil.getDateTime());
			rc.setOperPerson(userId);
			rc.setState(Constant.BaseType.DISABLE.getValue());
			rc.setType(Constant.RandomType.ByHand.getValue());
			rcMapper.insert(rc);
		}
		CheckResult cr=new CheckResult();
		cr.setCompanyId(companyId);
		cr.setFlag(randomId);
		cr.setId(CodeHelper.createUUID());
		cr.setRandomId(randomId);
		cr.setState(Constant.BaseType.DISABLE.getValue());
		crMapper.insert(cr);
		//得到列表
		List<CompanyVO> list=crMapper.findCompanyListByRandomId(randomId);
		return R.ok().put("randomId",randomId).put("resultList",list).put("randomSize",list.size());
	}

	@Override
	public R deleteBatch(String[] randomIds) {
		rcMapper.deleteBatch(randomIds);
		return R.ok();
	}

	@Override
	public Object findRandomListByParentId(String randomId) {
		//companyMapper.findRandomCheckIds(sl)
		List<CompanyVO> list=crMapper.findCompanyListByRandomId(randomId);
		//PageUtils pageUtil = new PageUtils(list,list.size(),list.size(),1);
		//return R.ok().put("page", pageUtil);
		System.out.println(list.size()+"\t"+randomId);
		Map map=new HashMap();
		map.put("rows",list);
		//return map;
		return map;
	}
	
	
	
	
	
	
	
}
