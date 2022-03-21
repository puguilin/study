package com.guilin.studycode.utils;


import org.apache.commons.lang.StringUtils;

/**
 * 身份证加密工具类
 */
public class IDCardUtils {

    /**
     * 证件类型加密
     * @param cerNo
     * @return
     */
    public static String encryptCerNo(String cerNo) {
        String newCerNo = "***";
        if (StringUtils.isNotBlank(cerNo) && cerNo.length() >= 3) {
            String preNo = cerNo.substring(0, 3);
            String suffixNo = cerNo.substring(3, cerNo.length());
            if (StringUtils.isNotBlank(suffixNo)) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < suffixNo.length(); i++) {
                    buffer.append("*");
                }
                newCerNo = preNo + buffer;
            }
        } else {
            newCerNo = cerNo;
        }
        return newCerNo;
    }

    public static void main(String[] args) {
        String tt = "513721199212257232";
        //RenewalUtils dd = new RenewalUtils();
        String string = IDCardUtils.encryptCerNo(tt);
        System.out.println(string);
    }
}
