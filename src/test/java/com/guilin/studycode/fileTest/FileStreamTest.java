package com.guilin.studycode.fileTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description:  文件流
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */



@SpringBootTest
@RunWith(SpringRunner.class)
public class FileStreamTest {

    /*
     * 实现对图片的复制操作
     *
     *如果只是单纯的复制操作，文本文件和非文本文件都可以处理（底层都是二进制数据）。
     * */
    @Test
    public void test() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File srcFile = new File("E:\\fileUp\\test\\桌面背景图\\壁纸.jpg");
            File destFile = new File("壁纸.jpg");

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            byte []buffer = new byte[1024];
            int len;
            while((len = fis.read(buffer)) != -1){
                fos.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
