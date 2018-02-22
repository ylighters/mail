package com.qt.mail.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;



@Component
public class DateUtil {
    
    private static Date oldDate;
    private static int seq = 0;
    private static int SIGNID_VERSION = 32;
  
    public static void main(String args[]){
    	System.out.println("当前时间："+DateUtil.getDateTime());
    	System.out.println("增加后时间："+DateUtil.getAddDate(DateUtil.getDateTime(),24));
    //	System.out.println(DataUtil.getDistanceMinutes("2016-09-24 11:42:47",DataUtil.getDateTime()));
		// System.out.println(DataUtil.getDistanceTime(DataUtil.getDateTime(), "2014-10-11 10:09:10"));
    }
    /**
     * 给时间增加几小时
     * @param date 需要增加的时间
     * @param jg 增加的小时数
     * @return
     */
    public static String getAddDate(String datee,int jg){
    	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 Calendar c = Calendar.getInstance();  
         Date date = null;  
         try {  
             date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datee);  
         } catch (ParseException e) {  
             e.printStackTrace();  
         }  
         c.setTime(date);  
         int day = c.get(Calendar.HOUR);  
         c.set(Calendar.HOUR, day + jg);  
         String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());  
         return dayAfter;  
    }
    
    
    /**
     * 比较两个日期 
     * @param DATE1
     * @param DATE2
     * @return
     */
	public static int compare_date(String DATE1, String DATE2) {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() >= dt2.getTime()) {
	                return 1;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                return -1;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return -2;
	    }
    /** 
     * 两个时间之间相差距离多少天 
     * @param one 时间参数 1： 
     * @param two 时间参数 2： 
     * @return 相差天数 
     */  
    public static long getDistanceDays(String str1, String str2) throws Exception{  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        Date one;  
        Date two;  
        long days=0;  
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            } else {  
                diff = time1 - time2;  
            }  
            days = diff / (1000 * 60 * 60 * 24);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return days;  
    }
    
   
    
    
    /** 
     * 两个时间相差距离多少天多少小时多少分多少秒 
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
     * @return long[] 返回值为：{天, 时, 分, 秒} 
     */  
    public static long[] getDistanceTimes(String str1, String str2) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date one;  
        Date two;  
        long day = 0;  
        long hour = 0;  
        long min = 0;  
        long sec = 0;  
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            } else {  
                diff = time1 - time2;  
            }  
            day = diff / (24 * 60 * 60 * 1000);  
            hour = (diff / (60 * 60 * 1000) - day * 24);  
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        long[] times = {day, hour, min, sec};  
        return times;  
    }  
    
    
    /**
     * 比较两个时间相差多少小时
     * @param str1
     * @param str2
     * @return
     */
    public static long getDistanceHours(String str1, String str2) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date one;  
        Date two;  
        long min = 0;  
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            diff = time2 - time1;
            min = ((diff / (60 * 1000)));  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return min;  
    }  
    
    
    
    
    /** 
     * 某个类的专用，不通用
     * 两个时间相差多少分钟
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
     * @return 
     */  
    public static long getDistanceMinutes(String str1, String str2) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date one;  
        Date two;  
        long day = 0;  
        long hour = 0;  
        long min = 0;  
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            }else{
            	return -1;
            } 
            System.out.println(diff);
            
            
            day = diff / (24 * 60 * 60 * 1000);  
           
            if(day>0){
            	return -1;
            }
            hour = (diff / (60 * 60 * 1000) - day * 24);  
            if(hour>0){
            	return -1;
            }
            min = diff / (60 * 1000);
            
        } catch (ParseException e) {  
            e.printStackTrace();  
        }   
        return (int)min;  
    }  
    /** 
     * 两个时间相差距离多少天多少小时多少分多少秒 
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
     * @return String 返回值为：xx天xx小时xx分xx秒 
     */  
    public static double getDistanceTime(String str1, String str2) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date one;  
        Date two;  
        double day = 0;  
        double hour = 0;  
        long min = 0;  
        long sec = 0;  
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            double diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            } else {  
                diff = time1 - time2;  
            }  
           day = diff / (24 * 60 * 60 * 1000);  
          //  hour = (diff / (60 * 60 * 1000) );  
            //min = ((diff / (60 * 1000)));  
           // sec = (diff/1000);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        //return day + "天" + hour + "小时" + min + "分" + sec + "秒"; 
        return day;
    } 
    /**
     * 取得当前系统时间yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime() {
        /*
         * Calendar theCa = Calendar.getInstance();
         *
         * String strDate = Integer.toString(theCa.get(Calendar.YEAR)) +
         * Integer.toString((theCa.get(Calendar.MONTH) + 1)) +
         * Integer.toString(theCa.get(Calendar.DAY_OF_MONTH))+" " +
         * Integer.toString(theCa.get(Calendar.HOUR_OF_DAY))+":" +
         * Integer.toString(theCa.get(Calendar.MINUTE))+":" +
         * Integer.toString(theCa.get(Calendar.SECOND));
         */
        Date theDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(theDate);
    }

    /**
     * 取得当前系统时间yyyy-MM-dd
     */
    public static String getDate() {
        /*
         * Calendar theCa = Calendar.getInstance();
         *
         * String strDate = Integer.toString(theCa.get(Calendar.YEAR)) +
         * Integer.toString((theCa.get(Calendar.MONTH) + 1)) +
         * Integer.toString(theCa.get(Calendar.DAY_OF_MONTH))+" " +
         * Integer.toString(theCa.get(Calendar.HOUR_OF_DAY))+":" +
         * Integer.toString(theCa.get(Calendar.MINUTE))+":" +
         * Integer.toString(theCa.get(Calendar.SECOND));
         */
        Date theDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(theDate);
    }

    /**
     * 字符串转换成时间yyyy-MM-dd
     */
    public static Date getDateTime(String dateStr) {
        // String dateStr="2005-05-20 11：20：00";
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date newdate = myFmt.parse(dateStr);
            return newdate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 取得当前系统时间yyyy-MM-dd_HH:mm:ss
     */
    public static String getPrintTime() {
        /*
         * Calendar theCa = Calendar.getInstance();
         *
         * String strDate = Integer.toString(theCa.get(Calendar.YEAR)) +
         * Integer.toString((theCa.get(Calendar.MONTH) + 1)) +
         * Integer.toString(theCa.get(Calendar.DAY_OF_MONTH))+" " +
         * Integer.toString(theCa.get(Calendar.HOUR_OF_DAY))+":" +
         * Integer.toString(theCa.get(Calendar.MINUTE))+":" +
         * Integer.toString(theCa.get(Calendar.SECOND));
         */
        Date theDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return df.format(theDate);
    }

    /**
     *
     */
    public static int getMonthSub(String dateStr1, String dateStr2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = myFormatter.parse(dateStr1);
            Date date2 = myFormatter.parse(dateStr2);

            Calendar myCal1 = Calendar.getInstance();
            Calendar myCal2 = Calendar.getInstance();
            myCal1.setTime(date1);
            myCal2.setTime(date2);

            if (myCal1.after(myCal2)) {
                myCal1.setTime(date2);
                myCal2.setTime(date1);
                //System.out.println("请把早的日期写在前面");
                //return "";
            }

            int year1 = myCal1.get(Calendar.YEAR);
            int year2 = myCal2.get(Calendar.YEAR);

            int month1 = myCal1.get(Calendar.MONTH);
            int month2 = myCal2.get(Calendar.MONTH);

            //System.out.println(dateStr1 + "   与   " + dateStr2 + "   月份差为   "+ ((year2 - year1) * 12 - (month1 - month2)));
            int resDate = (year2 - year1) * 12 - (month1 - month2);
            return resDate;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }

    }

    /**
     * 去掉回车换行
     */
    public static String trimEnter(String str) {
        return str.replaceAll("\\n", "").replaceAll("\\r", "");

    }

    /**
     * 取得request中的对象
     */
    public static String getString(HttpServletRequest request, String s) {
        String value = request.getParameter(s);
        // value.equalsIgnoreCase("null") ? request.getParameter(s).trim() : ""
        // String myvalue= value.equalsIgnoreCase("null") ?
        // request.getParameter(s).trim() : "";
        if (value == null || value.equalsIgnoreCase("null") || value.equals("")) {
            return "";
        }

        return value;
    }

    /**
     * 取得request中的对象
     */
    public static String getString0(HttpServletRequest request, String s) {
        String value = request.getParameter(s);
        // value.equalsIgnoreCase("null") ? request.getParameter(s).trim() : ""
        // String myvalue= value.equalsIgnoreCase("null") ?
        // request.getParameter(s).trim() : "";
        if (value == null || value.equalsIgnoreCase("null") || value.equals("")) {
            return "0";
        }

        return value;
    }

    /**
     * 取得request中的对象
     */
    public static int getInt(HttpServletRequest request, String s) {
        String value = request.getParameter(s);

        if (value == null || value.equalsIgnoreCase("null")) {
            return 0;
        }

        return Integer.parseInt(value);
    }

    /**
     * 取得request中的对象
     */
    public static String getDoubleStr(HttpServletRequest request, String s) {
        String value = request.getParameter(s);

        if (value == null || value.equalsIgnoreCase("null") || value.equals("")) {
            return "0.0";
        }

        return Double.valueOf(value).toString();
    }

   

   

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {

        String dest = "";

        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }

        return dest;

    }

    /**
     * 功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author liujl
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
           // System.out.println("相隔的天数="+day);   
        } catch (Exception e) {
          
            e.printStackTrace();
        }
        return day;
    }
     /**
     * 计算某天X个日前的日期
     */
    public static String getBeforeDateDays(String dateStr, int X)  {
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            int theWeekDay = 0;
            int allWorkDay = 0;
            int month = 0, day = 0;
            String monthStr = "", dayStr = "";
            Date date1 = myFormatter.parse(dateStr);
            Calendar theCa = Calendar.getInstance();
            theCa.setTime(date1);
            //theCa.add(Calendar.DATE, +1);
            for (;;) {
                if (allWorkDay >= X) {
                    break;
                }
    //            theWeekDay = theCa.get(Calendar.DAY_OF_WEEK);
    //            if (theWeekDay == 6) {
    //                theCa.add(Calendar.DATE, +2);
    //            }else if (theWeekDay == 7) {
    //                theCa.add(Calendar.DATE, +1);
    //            } else {
                allWorkDay++;
                theCa.add(Calendar.DATE, -1);
                // }

            }

            month = (theCa.get(Calendar.MONTH) + 1);
            if (month < 10) {
                monthStr = "0" + month;
            } else {
                monthStr = "" + month;
            }
            day = theCa.get(Calendar.DAY_OF_MONTH);
            if (day < 10) {
                dayStr = "0" + day;
            } else {
                dayStr = "" + day;
            }

            return theCa.get(Calendar.YEAR) + "-" + monthStr + "-" + dayStr;
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            return dateStr;
        }
    }
}
