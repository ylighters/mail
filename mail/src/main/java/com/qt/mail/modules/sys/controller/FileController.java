package com.qt.mail.modules.sys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qt.mail.common.annotation.SysLog;
import com.qt.mail.common.exception.RRException;
import com.qt.mail.common.utils.R;
import com.qt.mail.common.validator.ValidatorUtils;
import com.qt.mail.modules.sys.entity.Role;
import com.qt.mail.modules.sys.service.CompanyService;
import com.qt.mail.modules.sys.service.FileService;
import com.qt.mail.modules.sys.service.RoleService;

/**
 * 文件管理
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/file")
public class FileController extends AbstractController{
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 上传图片
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	/*@RequiresPermissions("sys:oss:all")*/
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		return fileService.uploadFile(file);
	}
	
	
	/**
	 * 富文本编辑器专用
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadImage")
	/*@RequiresPermissions("sys:oss:all")*/
	public R uploadImage(@RequestParam("imgFile") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		return fileService.uploadImage(file);
	}
	
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFile")
	/*@RequiresPermissions("sys:oss:all")*/
	public R uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		return fileService.uploadAllFile(file);
	}
	
	
	/**
	 * 下载文件
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadFile/{fileId}")
	/*@RequiresPermissions("sys:oss:all")*/
	public void downloadFile(HttpServletRequest request,
			HttpServletResponse response,@PathVariable String fileId) throws Exception {
		 fileService.downloadFile(request,response,fileId);
	}
	
	
	
	
	
	
	
}
