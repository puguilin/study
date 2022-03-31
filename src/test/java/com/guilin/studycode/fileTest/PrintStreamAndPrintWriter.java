package com.guilin.studycode.fileTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @description: PrintStream和PrintWriter  打印流
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class PrintStreamAndPrintWriter {

    /**
     *
     *  打印流
     *
     * 实现将 基本数据类型 的数据格式转化为字符串输出
     * 打印流：PrintStream和PrintWriter
     * 提供了一系列重载的print()和print()方法，用于多种数据类型的输出
     * PrintStream和PrintWriter的输出不会抛出IOException异常
     * PrintStream和PrintWriter的自动flush功能
     * PrintStream打印的所有字符都使用平台默认字符编码转换为字节。在需要写入字符而不是写入字节的情况下，应该使用PrintWriter类
     * System.out返回的是PrintStream的实例
     *
     *
     */

    @Test
    public void test() {
        PrintStream ps = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File("hello11.txt"));
//            创建打印输出流，设置为自动刷新模式（写入换行符或字节'\n'时都会刷新缓冲区）
            ps = new PrintStream(fos, true);
            if (ps != null) {//把标准输出流(控制台输出)改成文件
                System.out.println(ps);
            }
            for (int i = 0; i <= 255; i++) {//ASCII字符
                System.out.println((char) i);
                if (i % 50 == 0) {//每50个数据一行
                    System.out.println();//换行
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null)
                ps.close();
        }
    }
}