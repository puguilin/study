package com.guilin.studycode.fileTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @description: 文件写入到磁盘  从内存中写出数据到硬盘文件里
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class O3_FileWriterTest {

    /**
     *
     *
     * * 一、流的分类：
     * *     1、操作数据单位：字节流、字符流
     * *     2、数据的流向：输入流、输出流
     * *     3、流的角色：节点流、处理流
     * *
     * * 二、流的体系结构
     * *    抽象基类              节点流(文件流）           缓冲流（处理流的一种）
     * *    InputStream           FileInputStream         BufferedInputStream
     * *    OutputStream          FileOutputStream        BufferedOutputStream
     * *    Reader                FileReader              BufferedReader
     * *    Writer                FileWriter              BufferedWriter
     * *
     * *
     * * */

    /*
     * 从内存中写出数据到硬盘文件里
     *
     * 说明：
     * 1、输出操作，对应的File可以不存在的。并不会报异常
     * 2、
     *   File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建此文件
     *   File对应的硬盘中的文件如果存在：
     *       如果流使用的构造器是：FileWriter(file,false)/FileWriter(file):对原有文件的覆盖
     *       如果流使用的构造器是：FileWriter(file,true)：不会对原有文件覆盖，而是在原有文件的基础上追加内容
     *
     * */
    @Test
    public void test()  {
        FileWriter fw = null;
        try {
            //1、提供File类的对象，指明写出到文件
            File file = new File("hello1.txt");

            //2、FileWriter的对象，用于数据的写出
            //ture:在文件现有内容的后面添加
            //false:覆盖文件
            fw = new FileWriter(file,false);

            //3、写入操作
            fw.write("I hava a dream\n");
            fw.write("You hava a dream too");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4、流资源的关闭
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
