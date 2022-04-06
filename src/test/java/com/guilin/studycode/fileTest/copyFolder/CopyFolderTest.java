package com.guilin.studycode.fileTest.copyFolder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @description: 复制指定源位置的所有文件、文件夹到指定的目标位置
 * @author: puguilin
 * @date: 2022/4/6
 * @version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class CopyFolderTest {

    @Test  //复制指定源位置的所有文件、文件夹到指定的目标位置
    public void test() throws IOException {
        File srcFile = new File("E:\\logs");
        File desFile = new File("F:\\");
        copyFolder(srcFile, desFile);
    }

    private  void copyFolder(File srcFile, File desFile) throws IOException {
        if(srcFile.isDirectory()) {
             //是文件夹,首先在目标位置创建同名文件夹，然后遍历文件夹下的文件，进行递归调用copyFolder函数
             File newFolder = new File(desFile, srcFile.getName());
             newFolder.mkdir();
             File[] fileArray = srcFile.listFiles();
             for(File file : fileArray) {
                     copyFolder(file, newFolder);
                 }
        }else{
             //是文件，直接copy到目标文件夹
             File newFile = new File(desFile, srcFile.getName());
             copyFile(srcFile, newFile);
        }
    }

    private  void copyFile(File srcFile, File newFile) throws IOException {
        //复制文件到指定位置
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
        byte[] b = new byte[1024];
        Integer len = 0;
        while((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
        bis.close();
        bos.close();
    }

}
