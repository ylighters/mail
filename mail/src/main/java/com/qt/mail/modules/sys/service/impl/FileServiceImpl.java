package com.qt.mail.modules.sys.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qt.mail.common.exception.RRException;
import com.qt.mail.common.utils.CodeHelper;
import com.qt.mail.common.utils.Constant;
import com.qt.mail.common.utils.DateUtil;
import com.qt.mail.common.utils.ImageUtil;
import com.qt.mail.common.utils.R;
import com.qt.mail.modules.sys.dao.MyFileMapper;
import com.qt.mail.modules.sys.entity.MyFile;
import com.qt.mail.modules.sys.service.FileService;




@Service("fileService")
public class FileServiceImpl implements FileService {
	@Autowired
	private MyFileMapper fileMapper;

	@Override
	public R uploadFile(MultipartFile file) {
		//保存当前文件
		//String basePath=ClassUtils.getDefaultClassLoader().getResource("").getPath();
		String fileName=file.getOriginalFilename();
		String tempDir=CodeHelper.createUUID();
	    String suffixName = fileName.substring(fileName.lastIndexOf("."));
	    String newName=CodeHelper.createYzm(6)+"-source"+suffixName;//防止中文名称出问题
	    String normalName=newName.replace("source","normal");
		File fileSourcePath = new File(Constant.File_Base_Path+tempDir+"/");
		File fileSource = new File(fileSourcePath,newName);
		if (!fileSourcePath.exists()) {
		    fileSourcePath.mkdirs();
		}
		try {
			file.transferTo(fileSource);
			File dest=new File(fileSourcePath,normalName);
			ImageUtil.resizeImage(fileSource, dest);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RRException("文件拷贝失败");
		}
		MyFile myFile=new MyFile();
		myFile.setId(CodeHelper.createUUID());
		myFile.setAddDate(DateUtil.getDateTime());
		myFile.setFileName(fileName);
		myFile.setFilePath(Constant.File_Base_Path+tempDir+"/"+normalName);
		myFile.setState(Constant.BaseType.ABLE.getValue());
		myFile.setVisitPath(tempDir+"/"+normalName);
		fileMapper.insert(myFile);
		return R.ok().put("fileId",myFile.getId()).put("visitUrl",myFile.getVisitPath());
	}

	@Override
	public R uploadAllFile(MultipartFile file) {
		//保存当前文件
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
			throw new RRException("文件拷贝失败");
		}
		MyFile myFile=new MyFile();
		myFile.setId(CodeHelper.createUUID());
		myFile.setAddDate(DateUtil.getDateTime());
		myFile.setFileName(fileName);
		myFile.setFilePath(Constant.File_Base_Path+tempDir+"/"+newName);
		myFile.setState(Constant.BaseType.ABLE.getValue());
		myFile.setVisitPath(tempDir+"/"+newName);
		fileMapper.insert(myFile);
		return R.ok().put("fileId",myFile.getId()).put("visitUrl",myFile.getVisitPath());
	}

	@Override
	public void downloadFile(HttpServletRequest request,
			HttpServletResponse response,String fileId) {
		MyFile file=fileMapper.selectByPrimaryKey(fileId);
		if(file==null){
			throw new RRException("文件未找到");
		}else{
			try {
				this.download(request, response,file.getFileName(), file.getFilePath());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RRException("下载异常，文件丢失或文件位置被修改！");
			}
		}
	}
	

	/**
	 * 
	 * @param request
	 * @param response
	 * @param storeName 下载名称
	 * @param ctxPath 下载路径
	 * @throws Exception
	 */
	public void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String ctxPath)
			throws Exception {
		response.reset();
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("GBK");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String downLoadPath = ctxPath;
		long fileLength = new File(downLoadPath).length();
		response.setContentType("application/octet-stream");//
		response.setCharacterEncoding("GBK");
		response.setHeader("Content-disposition", "attachment; filename=\""
				+ new String(storeName.getBytes("gb2312"), "ISO8859-1") + "\"");
		response.setHeader("Content-Length", String.valueOf(fileLength));
		ServletOutputStream out=response.getOutputStream();
		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(out);
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		
		bis.close();
		bos.close();
	}

	@Override
	public R uploadImage(MultipartFile file) {
		//保存当前文件
		String fileName=file.getOriginalFilename();
		String tempDir=CodeHelper.createUUID();
	    String suffixName = fileName.substring(fileName.lastIndexOf("."));
	    if(suffixName.toLowerCase().equals(".jpg")||suffixName.toLowerCase().equals(".jpeg")
	    		||suffixName.toLowerCase().equals(".png")||suffixName.toLowerCase().equals(".gif")){
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
	 			throw new RRException("文件拷贝失败");
	 		}
	 		MyFile myFile=new MyFile();
	 		myFile.setId(CodeHelper.createUUID());
	 		myFile.setAddDate(DateUtil.getDateTime());
	 		myFile.setFileName(fileName);
	 		myFile.setFilePath(Constant.File_Base_Path+tempDir+"/"+newName);
	 		myFile.setState(Constant.BaseType.ABLE.getValue());
	 		myFile.setVisitPath(tempDir+"/"+newName);
	 		fileMapper.insert(myFile);
	 		return R.ok().put("error",0).put("url",Constant.WEB_PATH+"myFile/"+myFile.getVisitPath());
	    }else{
	    	return R.error().put("error",1).put("message","文件格式不正确");
	    }
	   
	
	}
	

	
}
