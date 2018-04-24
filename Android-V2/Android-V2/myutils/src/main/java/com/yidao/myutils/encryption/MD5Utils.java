package com.yidao.myutils.encryption;

import java.security.MessageDigest;

/**
 * Created by Lee on 2017/6/9.
 */

public class MD5Utils {
    public static final int LOWER_CASE = 0;
    public static final int CAPITAL = 1;
    char[] hexDigits;
    /**
     *
     * @param s
     * @param type 0：小写 1:大写
     * @return
     */
    public  String getMD5String(String s,int type) {
        if(type == CAPITAL){
            char[] temp = {'0', '1', '2', '3', '4',
                    '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F'};
            hexDigits = temp;
        }else {
            char[] temp = {'0', '1', '2', '3', '4',
                    '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            hexDigits = temp;
        }
        try {
            byte[] btInput = s.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
