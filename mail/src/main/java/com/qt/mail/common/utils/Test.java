package com.qt.mail.common.utils;  
  
import java.net.URL;

import javax.xml.namespace.QName;
/*import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis2.Constants;*/

import com.qt.mail.modules.sys.entity.User;
  
public class Test {/*  
  
      public String invokeRemoteFuc() throws ServiceException {  
    	// 远端调用的函数名
          String method = "test";
          // webservice端口
          String servicePoint = "http://192.168.1.109:8081/Service1.asmx";
          // 默认命名空间
          String xmlns = "http://tempuri.org/";
          // SOAPAction
          String SOAPAction = "http://tempuri.org/test";
          
          String resultlog=null;
           
          Service service = new Service();
          Call call = (Call) service.createCall();;
          try {
              // 访问的webservice地址
              call.setTargetEndpointAddress(new URL(servicePoint));
              // 被调用的webservice方法
              call.setOperationName(new QName(xmlns, method));
               
              // 设置参数
              call.addParameter("motherCard", XMLType.XSD_STRING, ParameterMode.IN);
              call.addParameter("childCard", XMLType.XSD_STRING, ParameterMode.IN);
              call.addParameter("money", XMLType.XSD_STRING, ParameterMode.IN);
              call.setUseSOAPAction(true);
              // 返回值类型
              call.setReturnType(Constants.XSD_STRING);
              call.setSOAPActionURI(SOAPAction);
               
            
              // 调用webservice方法
               resultlog = (String)call.invoke(new Object[] {"1111","1111",11});
              // 打印输出函数值
              return resultlog;
          } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
		return resultlog;
        }  
  
        public static void main(String[] args) {  
        	Test t = new Test();  
            String result;
			try {
				result = t.invokeRemoteFuc();
				System.out.println(result);  
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
            
        }  
      
*/}  