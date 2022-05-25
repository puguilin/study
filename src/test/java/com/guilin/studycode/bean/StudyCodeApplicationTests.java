package com.guilin.studycode.bean;

import cn.hutool.core.date.DateTime;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.entrity.User;
import com.guilin.studycode.mapper.StudentMapper;
import com.guilin.studycode.service.StudentService;
import com.guilin.studycode.service.UserService;
import com.guilin.studycode.utils.MD5Util;
import com.guilin.studycode.utils.ObjectEmptyUtil;
import com.guilin.studycode.utils.date.StringDateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.guilin.studycode.utils.MD5Util.MD5Encode;


@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.guilin.studycode.mapper")
@EnableCaching
public class StudyCodeApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;



    @Autowired
    private StudentMapper studentMapper;


    @Test
    public void queryById() {
        Student student = studentService.queryById(31);
        System.out.println("student " + student.toString() );
    }

    @Test
    public void selectById() {
       //service默认的方法
        Student student = studentService.getById(String.valueOf(31));
        System.out.println("student " + student.toString() );
    }

    @Test
    public void save() {
        Student student = new Student();
        student.setSNAME("test");
        student.setSNO("005");
        student.setSSEX("F");
        int student1 = studentService.saveStudent(student);
        System.out.println("student1 " + student1);
    }

    @Test
    public void addOrUpdate() {
        Student student = new Student();
        student.setSNAME("add03");
        student.setSNO("003");
        student.setSSEX("F");
        student.setUpdateDate(new DateTime(new Date()));
        student.setCreateDate(new DateTime(new Date()));
        boolean student1 = studentService.addOrUpdate(student);
        System.out.println("student1 " + student1);
    }

    @Test
    public void addOrUpdateUser() {
        User user =new User();
        user.setId(261);
        user.setName("ceshi02");
        user.setAge("11");
        user.setSex("男");
        user.setAddress("南昌");
        user.setHeight("175");
        user.setMobile("17521225989");
        user.setCreateTime(new DateTime(new Date()));
        //加密
        String p ="123456";
        String md5 = MD5Encode(p);
        String password = MD5Util.MD5Decrypt(md5);
        user.setPassword(password);
        boolean b = userService.saveOrUpdate(user);
        System.out.println("user " + user);
        System.out.println("md5 " + md5);
        System.out.println("password " + password);
    }


    @Test
    public void queryByIdAndPassward() {
        String p ="123456";
        String md5 = MD5Encode(p);
        String password = MD5Util.MD5Decrypt(md5);
       // User user = userService.queryByIdAndPassward(261,password); //和下面的是一样的
        User user = userService.queryByIdAndPassward(261,MD5Util.MD5Decrypt((MD5Encode(p))));

        System.out.println("password " + password );

        if(ObjectEmptyUtil.isEmpty(user)){
            System.out.println("user " + "账号或密码错误" );
        }
        System.out.println("user " + user.toString() );
    }

    // 通过map保存
    @Test
    public void saveStudentMap() {
        Map<String, String> out = new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("SNO", "102");
        map.put("SNAME", "测试map2");
        map.put("SSEX", "M");
        map.put("remark", "备注");
        SimpleDateFormat simpleDateFormat = StringDateUtil.dateFormat(4);
        String createDate = simpleDateFormat.format(new Date());
        String updateDate = simpleDateFormat.format(new Date());
        map.put("createDate", createDate);
        // map.put("updateDate", updateDate);
        Map<String, String> result = studentService.saveStudentMap(map);;
        System.out.println(result);
    }

}
