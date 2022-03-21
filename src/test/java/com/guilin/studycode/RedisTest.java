package com.guilin.studycode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.utils.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:redisc 测试
 * @author: puguilin
 * @date: 2022/3/18
 * @version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;  //使用时 只能注入调用方法  不能通过new实例来调用方法

    @Test
    public void addKey() throws JSONException {
        //redisTemplate.opsForValue().set("测试002", "测试001");
        // 或者
       // redisUtil.set("测试003", "测试003");
        Student student = new Student();
        student.setSNAME("test");
        student.setSNO("005");
        student.setSSEX("F");

        //redisUtil.set("学生",student);
//        Student tt = (Student)redisUtil.get("学生");
//        System.out.println("tt " +tt);


        Map<String, Object> map = new HashMap<>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");
        map.put("学生", student);

        //添加map
        //redisUtil.set("map",map);

        // String str = JSON.toJSONString(map);
        // JSONObject jsonObject = JSON.parseObject(str);


       // Map<String, Object>  map1 = (HashMap)redisUtil.get("map");

        // 获取map map里面包含对象
        String dd = JSON.toJSONString(redisUtil.get("map"));
        JSONObject jsonObject = JSON.parseObject(dd);
        JSONObject json = jsonObject.getJSONObject("学生");

        //JSONObject 转 java对象
        Student student1 = JSONObject.toJavaObject(json, Student.class);
        System.out.println("student1 " +student1);

        System.out.println("test" + "fghjkl");
        System.out.println("test22" + "fghjkl22");



    }
}
