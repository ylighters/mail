package com.qt.mail.modules.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.qt.mail.common.utils.R;
import com.qt.mail.common.utils.ShiroUtils;
import com.qt.mail.modules.sys.entity.User;
import com.qt.mail.modules.sys.service.UserService;
import com.qt.mail.modules.sys.service.UserTokenService;



/**
 * 登陆相关
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserTokenService userTokenService;
	
	@RequestMapping("/getYzm.do")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}
	
	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @param captcha
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(String username, String password, String captcha)throws IOException {
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}
		User user=userService.queryUserByPhone(username);
		//账号不存在、密码错误
		if(user == null || !user.getPwd().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}

		//账号锁定
		if(!user.getState().equals("0")){
			return R.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		R r = userTokenService.createToken(user.getId());
		return r;
	}
	
	
	@RequestMapping(value = "/getCountInfo")
	public R getCountInfo(){
		
		return userService.getCountInfo();
	}
	
}
