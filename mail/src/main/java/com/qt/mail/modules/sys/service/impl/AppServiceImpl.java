package com.qt.mail.modules.sys.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.HgSmsSend;
import com.qt.mail.common.utils.ImageUtil;
import com.qt.mail.common.utils.MD5;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.AppMapper;
import com.qt.mail.modules.sys.dao.CheckResultMapper;
import com.qt.mail.modules.sys.dao.CompanyMapper;
import com.qt.mail.modules.sys.dao.DeptMapper;
import com.qt.mail.modules.sys.dao.KdyMapper;
import com.qt.mail.modules.sys.dao.MessageMapper;
import com.qt.mail.modules.sys.dao.MyFileMapper;
import com.qt.mail.modules.sys.dao.RandomCheckMapper;
import com.qt.mail.modules.sys.dao.RoleMapper;
import com.qt.mail.modules.sys.dao.UserMapper;
import com.qt.mail.modules.sys.dao.VideoMapper;
import com.qt.mail.modules.sys.entity.App;
import com.qt.mail.modules.sys.entity.CheckResult;
import com.qt.mail.modules.sys.entity.Company;
import com.qt.mail.modules.sys.entity.Dept;
import com.qt.mail.modules.sys.entity.Kdy;
import com.qt.mail.modules.sys.entity.Message;
import com.qt.mail.modules.sys.entity.MyFile;
import com.qt.mail.modules.sys.entity.RandomCheck;
import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.service.AppService;
import com.qt.mail.modules.sys.service.UserRoleService;




@Service("appService")
public class AppServiceImpl implements AppService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private DeptMapper deptMapper;
	
	@Autowired
	private MyFileMapper fileMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private VideoMapper videoMapper;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private RandomCheckMapper rcMapper;
	
	@Autowired
	private CheckResultMapper crMapper;
	
	@Autowired
	private AppMapper appMapper;
	
	@Autowired
	private KdyMapper kdyMapper;
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	@Override
	public R loginIn(String phone, String pwd) {
		User user=userMapper.queryUserByPhone(phone);
		//账号不存在、密码错误
		if(user == null || !user.getPwd().equals(new Sha256Hash(pwd, user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}
		if(null!=user.getVisitUrl()&&!"".equals(user.getVisitUrl())){
			user.setVisitUrl(Constant.WEB_PATH+"myFile/"+user.getVisitUrl());
		}
		return R.ok().put("user",user);
	}


	@Override
	public R findAllCompany(String flagDate) {
		String wz=Constant.WEB_PATH+"myFile/";
		return R.ok().put("companyList", companyMapper.findComanyListByFlagDate(flagDate,wz));
	}


	@Override
	public R findAllDept() {
		return R.ok().put("deptList",deptMapper.queryList(new HashMap<String,Object>()));
	}

    @Transactional
	@Override
	public R uploadImage(MultipartFile file, String width, String height) {
		boolean isZh=(null!=width&&!"".equals(width)&&null!=height&&!"".equals(height));
		String fileName=file.getOriginalFilename();
		String tempDir=CodeHelper.createUUID();
	    String suffixName = fileName.substring(fileName.lastIndexOf("."));
	    if(suffixName.toLowerCase().equals(".jpg")||suffixName.toLowerCase().equals(".jpeg")
	    		||suffixName.toLowerCase().equals(".png")||suffixName.toLowerCase().equals(".gif")){
	    	String newName=CodeHelper.createYzm(6)+"-source"+suffixName;//防止中文名称出问题
	    	String normalName=newName.replace("source","normal");
			File fileSourcePath = new File(Constant.File_Base_Path+tempDir+"/");
			File fileSource = new File(fileSourcePath,newName);
			if (!fileSourcePath.exists()) {
			    fileSourcePath.mkdirs();
			}
			try {
				file.transferTo(fileSource);
				if(isZh){
					File dest=new File(fileSourcePath,normalName);
					ImageUtil.resizeImage(fileSource, dest,width,height);
				}
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return R.error("网络繁忙，图片上传失败！");
			}
			MyFile myFile=new MyFile();
			myFile.setId(CodeHelper.createUUID());
			myFile.setAddDate(DateUtil.getDateTime());
			myFile.setFileName(fileName);
			myFile.setFilePath(Constant.File_Base_Path+tempDir+"/"+(isZh?normalName:newName));
			myFile.setState(Constant.BaseType.ABLE.getValue());
			myFile.setVisitPath(tempDir+"/"+(isZh?normalName:newName));
			fileMapper.insert(myFile);
			return R.ok().put("fileId",myFile.getId()).put("visitUrl",Constant.WEB_PATH+"myFile/"+myFile.getVisitPath());
        }else{
        	return R.error("图片格式错误！");
        }
	}


	@Transactional
	@Override
	public R saveUser(User user) {
		user.setId(CodeHelper.createUUID());
		user.setAddDate(DateUtil.getDateTime());
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPwd((new Sha256Hash(user.getPwd(), salt).toHex()));
		user.setType(Constant.UserType.POST.getValue());
		user.setState(Constant.BaseType.ABLE.getValue());
		Dept dept=deptMapper.selectByPrimaryKey(user.getDeptId());
		if(dept==null){
			return R.error("部门错误，注册失败！");
		}
		//判断用户手机是否已经存在
		User exist=userMapper.queryUserByPhone(user.getPhone());
		if(exist!=null){
			return R.error("账号已经存在！注册失败！");
		}
		int ct=userMapper.insert(user);
		if(ct==1){
			userRoleService.saveOrUpdate(user.getId(),user.getRoleList());
		   return R.ok(); 	
		}else{
		   return R.error("属性缺失！注册失败！");
		}
	}


	@Override
	public R findRoleList() {
		return R.ok().put("roleList", roleMapper.queryAll());
	}

    @Transactional
	@Override
	public R updatePwd(String userId, String oldPwd, String newPwd) {
		User user=userMapper.selectByPrimaryKey(userId);
		//账号不存在、密码错误
		if(user == null || !user.getPwd().equals(new Sha256Hash(oldPwd, user.getSalt()).toHex())) {
			return R.error("旧密码错误！");
		}
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPwd((new Sha256Hash(newPwd, salt).toHex()));
		int ct=userMapper.updatePwd(user);
		if(ct==1){
			return R.ok();
		}else{
		    return R.error("更新异常！");	
		}
	}


	@Override
	public R sendYzm(String phone) {
		User user=userMapper.queryUserByPhone(phone);
		//账号不存在、密码错误
		if(user == null) {
			return R.error("用户手机未找到！");
		}
		String yzm=CodeHelper.createYzm(6);
		String content="您的验证码是【"+yzm+"】【济宁远望软件】";
		HgSmsSend.sendSms(content,phone);
		return R.ok();
	}


	@Transactional
	@Override
	public R updatePwdByPhone(String phone, String newPwd) {
		User user=userMapper.queryUserByPhone(phone);
		//账号不存在、密码错误
		if(user == null) {
			return R.error("用户手机未找到！");
		}
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPwd((new Sha256Hash(newPwd, salt).toHex()));
		int ct=userMapper.updatePwd(user);
		if(ct==1){
			return R.ok();
		}else{
		    return R.error("更新异常！");	
		}
	}


	@Override
	public R findVideo(String companyId) {
		return R.ok().put("videoList",videoMapper.findVideoList(companyId));
	}


	
	@Override
	public R findContentList(String type) {
		List<Message> list=messageMapper.findAllMessage(type);
		for(Message m:list){
			m.setVisitUrl(Constant.WEB_PATH+Constant.Content_Html_Path+"?"+m.getId());
		}
		return R.ok().put("contentList",list);
	}


	@Override
	public R getContentInfo(String messageId) {
		return R.ok().put("contentInfo",messageMapper.selectByPrimaryKey(messageId));
	}


	@Override
	public R findRandomList(String userId) {
		return R.ok().put("randomList", rcMapper.findRandomList(userId));
	}


	@Override
	public R findResultList(String randomId) {
		return R.ok().put("resultList",crMapper.findCompanyListByRandomId(randomId));
	}


	@Transactional
	@Override
	public R saveRandom(String userId, String[] companyIds, String remark) {
		User user=userMapper.selectByPrimaryKey(userId);
		String randomId=CodeHelper.createUUID();
		if(user==null){
			return R.error("用户Id错误");
		}else{
			
			RandomCheck rc=new RandomCheck();
			rc.setId(randomId);
			rc.setOperDate(DateUtil.getDateTime());
			rc.setOperPerson(userId);
			rc.setRemark(remark);
			rc.setState(Constant.BaseType.ABLE.getValue());
			rc.setType(Constant.RandomType.ByApp.getValue());
			rcMapper.insert(rc);
			int count=0;
			for(String id:companyIds){
				Company com=companyMapper.selectByPrimaryKey(id);
				if(com==null){
					continue;
				}else{
					count++;
					CheckResult cr=new CheckResult();
					cr.setCompanyId(id);
					cr.setFlag(randomId);
					cr.setId(CodeHelper.createUUID());
					cr.setRandomId(randomId);
					cr.setState(Constant.BaseType.ABLE.getValue());
					crMapper.insert(cr);	
				}
			}
			rc.setCount(count+"");
			rcMapper.updateByPrimaryKeySelective(rc);
			
		}
		return R.ok();
	}


	@Transactional
	@Override
	public R uploadFile(MultipartFile file) {
		String fileName=file.getOriginalFilename();
		String tempDir=CodeHelper.createUUID();
	    String suffixName = fileName.substring(fileName.lastIndexOf("."));
	   

    	String newName=CodeHelper.createYzm(6)+"-source"+suffixName;//防止中文名称出问题
		File fileSourcePath = new File(Constant.File_Base_Path+tempDir+"/");
		File fileSource = new File(fileSourcePath,newName);
		if (!fileSourcePath.exists()) {
		    fileSourcePath.mkdirs();
		}
		try {
			file.transferTo(fileSource);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.error("网络繁忙，文件上传失败！");
		}
		MyFile myFile=new MyFile();
		myFile.setId(CodeHelper.createUUID());
		myFile.setAddDate(DateUtil.getDateTime());
		myFile.setFileName(fileName);
		myFile.setFilePath(Constant.File_Base_Path+tempDir+"/"+newName);
		myFile.setState(Constant.BaseType.ABLE.getValue());
		myFile.setVisitPath(tempDir+"/"+newName);
		fileMapper.insert(myFile);
		return R.ok().put("fileId",myFile.getId()).put("visitUrl",Constant.WEB_PATH+"myFile/"+myFile.getVisitPath());
    
	}


	@Transactional
	@Override
	public R saveMaterial(Message message) {
		User user=userMapper.selectByPrimaryKey(message.getAddPerson());
		if(user==null){
			return R.error("添加人员未找到！");
		}
		message.setId(CodeHelper.createUUID());
		message.setAddDate(DateUtil.getDate());
		message.setCount(0);
		message.setState(Constant.BaseType.ABLE.getValue());
		//判断当前用户是邮政人员还是快递人员
		message.setType(Constant.UserType.KDY.getValue());
		int ct=messageMapper.insert(message);
		if(ct==1){
			return R.ok();
		}else{
			return R.error("添加异常，字段缺失！");
		}
	}

	
	@Override
	public R findNewApp() {
		App app=appMapper.findNewApp();
		if(app==null){
			return R.error("不存在app!");
		}
		MyFile file=fileMapper.selectByPrimaryKey(app.getFileId());
		return R.ok().put("url",Constant.WEB_PATH+"myFile/"+file.getVisitPath()).put("version",app.getVersion());
	}


	@Transactional
	@Override
	public R updateUserInfo(User user) {
		User u=userMapper.selectByPrimaryKey(user.getId());
		if(u!=null){
			int ct=userMapper.updateByPrimaryKeySelective(user);
			if(ct==1){
				return R.ok();
			}else{
				return R.error("数据异常，更新失败！");
			}
		}else{
			return R.error("未找到此用户");
		}
		
	}


	@Transactional
	@Override
	public R saveKdy(Kdy kdy) {
		Company company=companyMapper.selectByPrimaryKey(kdy.getCompanyId());
		if(company==null){
			return R.error("所属公司未找到，添加失败！");
		}
		Kdy ex=kdyMapper.findKdyByLxfs(kdy.getLxfs());
		if(ex!=null){
			return R.error("快递员联系方式已存在，添加失败！");
		}
		kdy.setId(CodeHelper.createUUID());
		kdy.setOperDate(DateUtil.getDateTime());
		kdy.setPwd(MD5.md5(kdy.getPwd()));
		int ct=kdyMapper.insert(kdy);
		if(ct==1){
			return R.ok();
		}else{
			return R.error("数据错误，添加失败！");
		}
	}


	@Override
	public R kdyLogin(String lxfs, String pwd) {
		Kdy ex=kdyMapper.findKdyByLxfs(lxfs);
		if(ex!=null&&ex.getPwd().equals(MD5.md5(pwd))){
			return R.ok().put("kdyInfo",ex);
		}else{
			return R.error("账号或密码错误！");
		}
	}




	
	
	
}
