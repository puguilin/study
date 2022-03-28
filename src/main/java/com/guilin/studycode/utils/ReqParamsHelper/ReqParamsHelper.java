package com.guilin.studycode.utils.ReqParamsHelper;

import com.guilin.studycode.utils.MD5Util;

import java.util.*;

/**
 * @description:请求参数工具类  请求系统参数按照字母先后顺序排序
 * @author: puguilin
 * @date: 2022/3/28
 * @version: 1.0
 */

public class ReqParamsHelper {


    public static String genReqStringByMap(Map<String,String> params) {
        //keySet获取键集
        List<String> keys = new ArrayList<String>(params.keySet());
        //对键集进行排序
        Collections.sort(keys,new Comparator<String>() {

            @Override
            public int compare(String s1, String s2) {

                // key升序   s1.compareTo(s2)  age=18&sex=male&usernanme=Aran
                // key降序   s2.compareTo(s1);  usernanme=Aran&sex=male&age=18
                return s1.compareTo(s2);
            }
        });

        String preStr = "";
        for(int i = 0;i < keys.size();i++) {
            String curKey = keys.get(i);
            String curValue = params.get(curKey);
            // 如果是最后一个参数，则不加“&”
            if(i== keys.size() - 1) {
                preStr = preStr + curKey + "=" + curValue;
            } else {
                preStr = preStr + curKey + "=" + curValue + "&";
            }
        }

        System.out.println("prestr:  " + preStr);
        //采用MD5(32位小写)加密方式
        String s = MD5Util.MD5Encode(preStr).toLowerCase();
        System.out.println("MD5s:  " + s);
        return preStr;
    }


    public static Map<String,String> genMap(){
        Map<String,String> paramsMap = new HashMap<>();
        int myAge = 18;
        paramsMap.put("usernanme", "Aran");
        paramsMap.put("sex", "male");
        paramsMap.put("age",String.valueOf(myAge));
        return paramsMap;
    }

    public static void main(String[] args) {
        System.out.println("排序后的结果：" + genReqStringByMap(genMap()));
    }



}
