package com.qt.mail.common.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

public class HgSmsSend {


	
	public static void main(String[] a)
	{
		
		try{
			String url="http://101.200.29.88:8082/SendMT/SendMessage";
		
			String UserName="jiningyuanwang";						//用户名
			String UserPass="jiningyuanwang";								//密码
		    String content=URLEncoder.encode("【济宁远望软件】您好，您的快件存放在D2楼大厅，请速去领取，取件编号", "UTF-8");									//发送内容
			String mobile="18766860185";										//手机号
			String parameter="CorpID="+UserName+"&Pwd="+UserPass+"&Content="+content+"&Mobile="+mobile;
			System.out.println(url+"?"+parameter);
			System.out.println(HgSmsSend.httpPostSend(url,parameter,"UTF-8"));
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private final static String url="http://101.200.29.88:8082/SendMT/SendMessage";
    private final static String UserName="jiningyuanwang";	
	private final static String UserPass="jiningyuanwang";
	
	
	public static String sendSms(String content,String mobile){
		 
		try {
			 content=URLEncoder.encode("【济宁远望软件】"+content,"UTF-8");
				String parameter="CorpID="+UserName+"&Pwd="+UserPass+"&Content="+content+"&Mobile="+mobile;
			return HgSmsSend.httpPostSend(url,parameter,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
			
		}
	}
	
	public static String sendSmsXYD(String content,String mobile){
		 
		try {
			 /*content=URLEncoder.encode("【鲜一点】"+content,"UTF-8");
				String parameter="CorpID="+UserName+"&Pwd="+UserPass+"&Content="+content+"&Mobile="+mobile;*/
			content=URLEncoder.encode("【济宁远望软件】"+content,"UTF-8");
			String parameter="CorpID="+UserName+"&Pwd="+UserPass+"&Content="+content+"&Mobile="+mobile;
			/* content=URLEncoder.encode("【济宁远望软件】"+content,"UTF-8");
				String parameter="CorpID="+UserName+"&Pwd="+UserPass+"&Content="+content+"&Mobile="+mobile;*/
			return HgSmsSend.httpPostSend(url,parameter,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
			
		}
	}
	
	public static String httpPostSend(String sendUrl,String parameter,String encoded) throws
	SocketTimeoutException, Exception
	{
		String urlPath = sendUrl;
		StringBuffer sbf = new StringBuffer();
		BufferedWriter writer = null;
		BufferedReader reader = null;
		HttpURLConnection uc = null;
		try {
			URL url = new URL(urlPath);
			uc = (HttpURLConnection) url.openConnection();
			uc.setConnectTimeout(30000);
			uc.setReadTimeout(30000);
			uc.setRequestMethod("POST");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			
			writer = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(),encoded)); // 向服务器传送数据
			writer.write(parameter);
			writer.flush();
			writer.close();
			reader = new BufferedReader(new InputStreamReader(uc.getInputStream(),encoded)); // 读取服务器响应信息
			String line;
	
			while ((line = reader.readLine()) != null) {
				sbf.append(line);
			}
			reader.close();
			uc.disconnect();	
		} 
	   
	    catch (SocketTimeoutException e){throw new SocketTimeoutException(); }
		catch (IOException e) {e.printStackTrace();throw new IOException();} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("无法连接服务器");
		} finally {
			closeIO(writer, reader);
		}
		return sbf.toString().trim();
	}
	
	private static void closeIO(BufferedWriter writer, BufferedReader reader) {
		if (writer != null) {
			try {
				writer.close();
				writer = null;
			} catch (Exception e) {

			}
		}
		if (reader != null) {
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {

			}
		}

	}

}
