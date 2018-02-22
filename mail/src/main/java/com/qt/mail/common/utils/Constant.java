package com.qt.mail.common.utils;

/**
 * 常量
 * 
 */
public class Constant {
	/** 超级管理员ID */
	public static final String SUPER_ADMIN = "e8430f5645a64b2288f0de3e61b19f8e";
	
	/** 最上层菜单Id */
	public static final String ROOT_ID = "0";
	
	/**文件路径*/
	public static final String File_Base_Path = "E:/myFile/";
//	public static final String File_Base_Path = "F:/mail/myFile";//服务器
	
	
	
	/**项目访问路径*/
	public static final String WEB_PATH = "http://192.168.1.101:8080/";
//	public static final String WEB_PATH = "http://112.53.69.186:8079/";//服务器
	
	/**项目访问路径*/
	public static final String Content_Html_Path = "modules/sys/app.html";
	
	
	
	
	/**用户类型*/
	public enum RandomType {
        /**
         * 随机
         */
    	Random("random"),
        /**
         * 手动
         */
        ByHand("byHand"),
        /**
         * app抽检
         */
        ByApp("byApp"),
        /**
         * 按等级
         */
		ByLever("byLever");
		
		
		
        private String value;

        private RandomType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
	
	
	
	
	/**用户类型*/
	public enum UserType {
        /**
         * 目录
         */
    	KDY("kdy"),
        /**
         * 菜单
         */
        POST("post");

        private String value;

        private UserType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
	
	/**禁用启用状态*/
	public enum BaseType {
        /**
         * 目录
         */
    	ABLE("0"),
        /**
         * 菜单
         */
        DISABLE("1");

        private String value;

        private BaseType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    /**
     * 定时任务状态
     * 
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }


}
