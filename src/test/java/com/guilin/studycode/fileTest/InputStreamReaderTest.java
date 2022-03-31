package com.guilin.studycode.fileTest;

import org.junit.Test;

import java.io.*;

/**
 * @description: InputStreamReader 转换流
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */

public class InputStreamReaderTest {

    /*
     *
     *
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
     * *
     *
     * 处理流之二、转换流的使用
     * 1、转换流
     *   InputStreamReader：将一个字节的输入流转换为字符的输入流
     *   OutputStreamWriter：将一个字符的输出流转换为字节的输出流
     *
     * 2、作用：提供字节流与字符流之间的转换
     *
     * 3、解码：字节、字节数组 --->字符数组、字符串
     *    编码：字符数组、字符串 ---> 字节、字节数组
     *
     * 4、字符集
     * */


    /*
     * InputStreamReader的使用，实现字节的输入流到字符的输入流
     * */
    @Test
    public void test1()  {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        try {
            fis = new FileInputStream(new File("hello.txt"));
//        InputStreamReader isr = new InputStreamReader(fis);//使用系统默认的字符集
            //参数二，指明字符集，具体使用哪个字符集取决于文件保存时使用的字符集
            isr = new InputStreamReader(fis,"UTF-8");

            char []cubf = new char[20];
            int len;
            while((len = isr.read(cubf)) != -1){
                System.out.print(new String(cubf,0,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(isr != null){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /*
     * 综合使用InputStreamReader和OutputStreamWriter
     * */
    @Test
    public void test2(){
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        try {
            File file = new File("hello.txt");
            File file2 = new File("hello_gbk.txt");

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(file2);

            isr = new InputStreamReader(fis,"utf-8");
            osw = new OutputStreamWriter(fos,"gbk");//以gbk字符集的形式写入文件

            char buffer[] = new char[1024];
            int len;
            while((len = isr.read(buffer)) != -1){
                osw.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(osw != null)
                    osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(isr != null)
                    isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
