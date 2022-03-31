package com.guilin.studycode.fileTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 * @description:  文件读取
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class O2_FileReaderTest {

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




    public static void main(String[] args) {
        //在main方法中 没有指定绝对路径 该文件是相对于当前工程下的
        File file1 = new File("hello.txt");
        System.out.println(file1.getAbsolutePath());
    }

    /*
     * 将hello.txt文件内容读入程序中，并输出到控制台
     *
     * 说明点：
     * 1、read()的理解：返回读入的一个字符串。如果达到文件末尾，返回-1
     * 2、异常的处理：为了保证资源一定可以执行关闭操作。需要使用try-catch-finally处理
     * 3、读入的文件一定要存在，否则就会报异常
     * */


    @Test   //读取文件  升级前 用read（）方法
    public void test1(){
        java.io.FileReader fr = null;
        try {
        //1、实例化File类的对象，指明要操作的文件
            File file1 = new File("hello.txt");//在test测试中 该文件是相对于当前modle下的
            System.out.println(file1.getAbsolutePath());
        //2、提供具体的流
            fr = new java.io.FileReader(file1);
        // 3、数据的读入

             //read():返回读入的一个字符。如果达到文件末尾，返回-1
          // 方式一、
            /*int read = fr.read();
            while(read != -1){
                System.out.print((char)read);
                read = fr.read();
            }*/

          // 方式二、语法上针对方式一的修改
            int data;
            while((data = fr.read() )!= -1)
                System.out.print((char)data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//      4、流的关闭操作
            try {
                if(fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    // 读取文件 升级后   对read()方法的操作升级: 使用read的重载方法
    @Test
    public void test2()  {
        java.io.FileReader fr = null;
        try {
            //1、File类的实例化
            File file = new File("hello.txt");

            //2、流的实例化
            fr = new java.io.FileReader(file);

            //3、读入操作
            //read(char[] cbuf):返回每次读入cbuf数组中字符的个数。如果达到文件末尾，返回-1
            char[] cbuf = new char[1024];
            int len;
            while((len =fr.read(cbuf)) != -1){

                //方式一
                //错误的写法，如果最后的数据小于数组的长度，则只会将最后的数据覆盖到数组的前几个，数组的后几个数据保持不变并输出
               /* for(int i = 0;i<cbuf.length;i++){
                    System.out.print(cbuf[i]);
                }*/

                //正确写法
               /* for(int i = 0;i<len;i++){
                    System.out.print(cbuf[i]);  // // 测试fileRed
                }*/

                //方式二、
                //错误的写法,错误原因与方式一一样
                 /*  String str = new String(cbuf);//将字符数组转换为字符串
                System.out.println(str);   // 测试fileRed */

                //正确写法
                String str = new String(cbuf,0,len);
                System.out.println(str);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4、资源的关闭
            try {
                if(fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
