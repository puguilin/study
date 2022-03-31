package com.guilin.studycode.fileTest;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @description: 标准的输入流，输出流
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */

public class O8_OtherStreamTest {

    /*
     * 1、标准的输入、输出流
     * 1.1
     * System.in：标准的输入流，默认从键盘输入
     * System.out:标准的输出流，默认从控制台输出
     * 1.2
     * System类的setIn(InputStream is)/setOut(PrintStream ps)方式重新指定输入和输出的流
     *
     * 1.3练习：
     * 从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续进行输入操作
     * 知道当输入'e'或者"exit"时，退出程序
     * */
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(System.in);//从键盘输入
            br = new BufferedReader(isr);

            String date;
            while(true){
                String data = br.readLine();
                if("e".equalsIgnoreCase(data)||"exit".equalsIgnoreCase(data)){
                    System.out.println("程序结束");
                    break;
                }
                String s = data.toUpperCase();
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
