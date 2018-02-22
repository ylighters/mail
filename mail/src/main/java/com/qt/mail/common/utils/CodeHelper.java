package com.qt.mail.common.utils;

import java.util.UUID;

/**
* 类名称：CodeHelper  
* 类描述：   生成id及code工具
* mhc
*
 */
public class CodeHelper {
	/**
	 * 生成UUID
	 * @return
	 */
	public static String createUUID(){
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}
	/**
	 * 
	 * @param args
	 */
	public static String createYzm(int number){
		String pwd="";
		for (int i = 0; i < number; i++) {
			double dblR = Math.random() * 10;
			int intR = (int) Math.floor(dblR);
			pwd += intR;
		}
		return pwd;
	}
	
	public static void main(String args[]){
		for(int i=0;i<10;i++){
		System.out.println(CodeHelper.createUUID());
		}
	}
	
}
