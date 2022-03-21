package com.guilin.studycode;

import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.utils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Set;

/**
 * @description:对象转化
 * @author: puguilin
 * @date: 2022/3/18
 * @version: 1.0
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class convertersTest {

    @Test
    public void test() {
        Student student = new Student();
        student.setSNAME("test");
        student.setSNO("005");
        student.setSSEX("F");

        BeanUtils utils = new BeanUtils();
        Map map = utils.beanToMap(student);

        System.out.println("student " + student);
        for (Object key : map.keySet()) {
            System.out.println("key= "+ key + " and value= " + map.get(key));
        }

    }
}
