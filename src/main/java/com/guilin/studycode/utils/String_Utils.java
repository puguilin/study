package com.guilin.studycode.utils;


import org.apache.commons.lang.StringUtils;

/**
 * @description:字符串判空操作
 * @author: puguilin
 * @date: 2022/3/10
 * @version: 1.0
 *
 * 参考来源： https://www.iteye.com/blog/zpball-1776566
 *
 */

public class String_Utils {



    /**
     *
     *判断是否是一个空字条串，如果不含有有效字符，返回tru
     *      StringUtils.isBlank(str)方法， import org.apache.commons.lang.StringUtils;
     *
     *      blank= true blank2= true blank3= true blank4= true
     *
     */


    public static void main(String[] args) {
        String str1 = "";
        String str2 = " ";
        String str3 = "\t";
        String str4 = null;

        boolean blank = StringUtils.isBlank(str1);
        boolean blank2 = StringUtils.isBlank(str2);
        boolean blank3 = StringUtils.isBlank(str3);
        boolean blank4 = StringUtils.isBlank(str4);
        System.out.println(" blank= " + blank + " blank2= "+ blank2 +  " blank3= "+ blank3 + " blank4= " + blank4 );

        String[] ab_cd_efs = StringUtils.split("ab cd ef", null, 0);
        System.out.println( "ab_cd_efs " + ab_cd_efs.toString());
    }

    /**
     *
     * 分解字符串
     *
     *   StringUtils.split(null, *, *)            = null
     *   StringUtils.split("", *, *)              = []
     *   StringUtils.split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
     *   StringUtils.split("ab   de fg", null, 0) = ["ab", "cd", "ef"]
     *   StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     *   StringUtils.split("ab:cd:ef", ":", 1)    = ["ab:cd:ef"]
     *   StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     *   StringUtils.split(String str,String separatorChars,int max) str为null时返回null
     *   separatorChars为null时默认为按空格分解，max为0或负数时分解没有限制，max为1时返回整个字符串，max为分解成的个数
     *
     *
     * 去除字符串前后指定的字符
     *
     *   StringUtils.strip(null, *)          = null
     *   StringUtils.strip("", *)            = ""
     *   StringUtils.strip("abc", null)      = "abc"
     *   StringUtils.strip(" abc ", null)    = "abc"
     *   StringUtils.strip("  abcyx", "xyz") = "  abc"
     *   StringUtils.strip(String str,String stripChars) str为null时返回null,stripChars为null时默认为空格
     *
     *
     *
     */


}
