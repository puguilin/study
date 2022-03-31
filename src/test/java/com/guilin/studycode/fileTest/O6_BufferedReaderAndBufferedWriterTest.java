package com.guilin.studycode.fileTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @description: BufferedReader和BufferedWriter
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class O6_BufferedReaderAndBufferedWriterTest {



    /*
     * 1、缓冲流
     * BufferedInputStream
     * BufferedOutputStream
     *
     * BufferedReader
     * BufferedWriter
     *
     * 2、作用：提供流的读取、写入的速度
     *   提高读写速度的原因：内部提供了一个缓冲区
     *
     * 3、处理流，就是“套接”在已有的流的基础上
     * */
    @Test
    public void test2(){

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(new File("hello.txt")));
            bw = new BufferedWriter(new FileWriter(new File("hello2.txt")));

            //方式一、使用char[]数组
//            char []buffer = new char[1024];
//            int len;
//            while((len = br.read(buffer)) != -1){
//                bw.write(buffer,0,len);
//            }
            //方式二、使用String
            String data;
            while((data = br.readLine()) != null){
                //方式一
//                    bw.write(data+"\n");//data中不包含换行符
                //方式二
                bw.write(data);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(bw  != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
