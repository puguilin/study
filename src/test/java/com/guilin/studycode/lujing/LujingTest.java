package com.guilin.studycode.lujing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;

/**
 * @description:lujing
 * @author: puguilin
 * @date: 2022/4/13
 * @version: 1.0
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class LujingTest {

    /**
     *
     * String path = xxx.class.getClassLoader().getResource("targetFile.txt").getPath();
     *
     * #java获取文件目录
     * ├── pom.xml
     * ├── src
     * │   ├── main
     * │   │   ├── java
     * │   │   │   ├── com
     * │   │   │   │   ├── guilin
     * │   │   │   │   │    ├──studycode
     * │   │   │   │   │    │     ├── ResourceTest.java
     * │   │   │   │   │    │     └── Resource.java
     * │   │   └── resources
     * │   │   │   ├── conf
     * │   │   │   │   ├── sysConf.json
     * │   │   │   └── request.xml
     * └── local.iml
     * ————————————————
     * 版权声明：本文为CSDN博主「Captain249」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/Captain249/article/details/98172911
     *
     *
     *
     */

    @Test
    public void test2() {
            // 1、通过本类的Class的getResource方法
            String a1 = LujingTest.class.getResource("/com/guilin/studycode/Resource.class").getPath();
        // String a2 = LujingTest.class.getResource("Resource.class").getPath();
            String a3 = LujingTest.class.getResource("/request.xml").getPath();
           // String a4 = LujingTest.class.getResource("../../request.xml").getPath();
            String a5 = LujingTest.class.getResource("/config/sysConf.json").getPath();
           // String a6 = LujingTest.class.getResource("../../config/sysConf.json").getPath();

            System.out.println("通过Class的getResource方法a1= "+a1); // /E:/studyCode/StudyCode/target/classes/com/guilin/studycode/Resource.class
           // System.out.println("通过Class的getResource方法a2= "+a2);
            System.out.println("通过Class的getResource方法a3= "+a3); // /E:/studyCode/StudyCode/target/classes/request.xml
           // System.out.println("通过Class的getResource方法a4= "+a4);
            System.out.println("通过Class的getResource方法a5= "+a5); //  /E:/studyCode/StudyCode/target/classes/config/sysConf.json
           // System.out.println("通过Class的getResource方法a6= "+a6);

            // 2、通过本类的Class的ClassLoader的getResource方3
            String b1 = LujingTest.class.getClassLoader().getResource("com/guilin/studycode/Resource.class").getPath();
            String b2 = LujingTest.class.getClassLoader().getResource("request.xml").getPath();
            String b3 = LujingTest.class.getClassLoader().getResource("config/sysConf.json").getPath();
            System.out.println("通过本类的ClassLoader的getResource方法b1= "+b1);
            System.out.println("通过本类的ClassLoader的getResource方法b2= "+b2);
            System.out.println("通过本类的ClassLoader的getResource方法b3= "+b3);

            // 3、通过ClassLoader的getSystemClassLoader方法
            String c1 = ClassLoader.getSystemClassLoader().getResource("com/guilin/studycode/Resource.class").getPath();
            String c2 = ClassLoader.getSystemClassLoader().getResource("request.xml").getPath();
            String c3 = ClassLoader.getSystemClassLoader().getResource("config/sysConf.json").getPath();

            System.out.println("通过ClassLoader的getSystemClassLoader方法c1= "+c1);
            System.out.println("通过ClassLoader的getSystemClassLoader方法c2= "+c2);
            System.out.println("通过ClassLoader的getSystemClassLoader方法c3= "+c3);

            // 4、通过ClassLoader的getSystemResource方法
            String d1 = ClassLoader.getSystemResource("com/guilin/studycode/Resource.class").getPath();
            String d2 = ClassLoader.getSystemResource("request.xml").getPath();
            String d3 = ClassLoader.getSystemResource("config/sysConf.json").getPath();

            System.out.println("通过ClassLoader的getSystemResource方法d1= "+d1);
            System.out.println("通过ClassLoader的getSystemResource方法d2= "+d2);
            System.out.println("通过ClassLoader的getSystemResource方法d3= "+d3);

            // 5、通过Thread方式
            String e1 = Thread.currentThread().getContextClassLoader().getResource("com/guilin/studycode/Resource.class").getPath();
            String e2 = Thread.currentThread().getContextClassLoader().getResource("request.xml").getPath();
            String e3 = Thread.currentThread().getContextClassLoader().getResource("config/sysConf.json").getPath();
            System.out.println("通过通过Thread方法e1= "+e1);
            System.out.println("通过通过Thread方法e2= "+e2);
            System.out.println("通过通过Thread方法e3= "+e3);

    }

    /**
     *
     * 通过Class的getResource方法a1= /E:/studyCode/StudyCode/target/classes/com/guilin/studycode/Resource.class
     * 通过Class的getResource方法a3= /E:/studyCode/StudyCode/target/classes/request.xml
     * 通过Class的getResource方法a5= /E:/studyCode/StudyCode/target/classes/config/sysConf.json
     * 通过本类的ClassLoader的getResource方法b1= /E:/studyCode/StudyCode/target/classes/com/guilin/studycode/Resource.class
     * 通过本类的ClassLoader的getResource方法b2= /E:/studyCode/StudyCode/target/classes/request.xml
     * 通过本类的ClassLoader的getResource方法b3= /E:/studyCode/StudyCode/target/classes/config/sysConf.json
     * 通过ClassLoader的getSystemClassLoader方法c1= /E:/studyCode/StudyCode/target/classes/com/guilin/studycode/Resource.class
     * 通过ClassLoader的getSystemClassLoader方法c2= /E:/studyCode/StudyCode/target/classes/request.xml
     * 通过ClassLoader的getSystemClassLoader方法c3= /E:/studyCode/StudyCode/target/classes/config/sysConf.json
     * 通过ClassLoader的getSystemResource方法d1= /E:/studyCode/StudyCode/target/classes/com/guilin/studycode/Resource.class
     * 通过ClassLoader的getSystemResource方法d2= /E:/studyCode/StudyCode/target/classes/request.xml
     * 通过ClassLoader的getSystemResource方法d3= /E:/studyCode/StudyCode/target/classes/config/sysConf.json
     * 通过通过Thread方法e1= /E:/studyCode/StudyCode/target/classes/com/guilin/studycode/Resource.class
     * 通过通过Thread方法e2= /E:/studyCode/StudyCode/target/classes/request.xml
     * 通过通过Thread方法e3= /E:/studyCode/StudyCode/target/classes/config/sysConf.json
     *
     */




}
