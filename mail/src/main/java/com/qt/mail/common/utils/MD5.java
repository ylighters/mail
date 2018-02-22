package com.qt.mail.common.utils;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

/**
 * 类名称：MD5
 * 类描述：  加密工具
 * mhc
 */
public class MD5 {

    public static String md5(String str) {
        try {
        	str=(new BASE64Encoder()).encodeBuffer(str.getBytes());
            MessageDigest md = MessageDigest.getInstance("MD5");
          //  System.out.println("得到字符串为："+str);
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    public static void main(String args[]){
        String s = new String("123456");
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + md5(s));
    }
}
