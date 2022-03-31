package com.guilin.studycode.fileTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @description:  缓冲流
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class BufferedStreamTest {

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


    @Test  //实现非文本文件的复制
    public void test1()  {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
//        1、造文件
            File srcFile = new File("壁纸.jpg");
            File destFile = new File("E:\\fileUp\\test\\桌面背景图\\校准壁纸.jpg");
//        2、造流
//        2.1 造节点流
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);
//        2.2 造缓冲流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);
//        3 复制的细节：读取、写入
            byte[] buffer = new byte[1024];
            int len;
            while((len = bis.read(buffer)) != -1){
                bos.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //      3、关闭资源
            //        要求：先关闭外层的流，再关闭内层的流
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //        说明：关闭外层流的同时，内层流也会自动的进行关闭。关于内层流的关闭，我们可以省略
//            fis.close();
//            fos.close();
        }
    }


}
