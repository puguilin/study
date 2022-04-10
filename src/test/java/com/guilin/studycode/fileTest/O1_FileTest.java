package com.guilin.studycode.fileTest;

import com.alibaba.fastjson.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * @description: File类的使用
 * @author: puguilin
 * @date: 2022/3/31
 * @version: 1.0
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class O1_FileTest {

    /**
     *
     *   https://blog.51cto.com/u_15127673/4165141
     *      *
     *      *
     *      * * 一、流的分类：
     *      * *     1、操作数据单位：字节流、字符流
     *      * *     2、数据的流向：输入流、输出流
     *      * *     3、流的角色：节点流、处理流
     *      * *
     *      * * 二、流的体系结构
     *      * *    抽象基类              节点流(文件流）           缓冲流（处理流的一种）
     *      * *    InputStream           FileInputStream         BufferedInputStream
     *      * *    OutputStream          FileOutputStream        BufferedOutputStream
     *      * *    Reader                FileReader              BufferedReader
     *      * *    Writer                FileWriter              BufferedWriter
     *      * *
     *      * *
     *      * *
     *
     *
     *
     * 1、如何创建File类的实例
     *       File(String filePath)
     *       File(String parentPath,String childPath)
     *      File(File parentFile,String childPath)
     *
     *  2、路劲
     *       相对路径:相较于某个路径下，指明的路径
     *       绝对路径：包含盘符在内的文件或文件目录的路径
     *
     *  3、路径分隔符
     *       Windows：\\
     *       Linux: /
     *
     *  4、File类的创建功能
     *      public boolean createNewFile() ：创建文件。若文件存在，则不创建，返回false
     *      public boolean mkdir() ：创建文件目录。如果此文件目录存在，就不创建了。如果此文件目录的上层目录不存在，也不创建。
     *      public boolean mkdirs() ：创建文件目录。如果上层文件目录不存在，一并创建
     *   注意事项：如果你创建文件或者文件目录没有写盘符路径，那么，默认在项目路径下。
     *
     *  5、
     *
     */

    @Test
    //创建文件 如果你创建文件或者文件目录没有写盘符路径，那么，默认在项目路径下。
    // 判断文件是否存在 不存在则创建文件 创建文件前要判断目录是否存在 否则报错 找不到路径
    public void test1() throws JSONException, IOException {
        File file=new File("E:\\fileUp\\test\\2.txt");
        File file1 = new File("hello.txt");
        file1.createNewFile(); // 如果你创建文件或者文件目录没有写盘符路径，那么，默认在项目路径下。

        if(!file.exists()) {
            try {
                //获取文件的父目录
                /*String parent = file.getParent();//E:\fileUp\test
                File parentFile =new File(parent);
                * 和下面的  file.getParentFile() 其实都是一样的
                */
                //获取文件的父目录
                File parentFile = file.getParentFile(); //E:\fileUp\test

                // 判断文件夹是否存在  如果文件夹不存在则创建文件夹
                if  (!parentFile .exists()  && !parentFile .isDirectory()) {
                    System.out.println("文件夹：" + parentFile +" 不存在,准备创建文件夹" + parentFile);
                    parentFile .mkdir();
                }
                // 创建文件  创建文件前要判断父目录是否存在 不存在要先创建目录在创建文件
                boolean newFile = file.createNewFile(); //创建文件。若文件存在，则不创建，返回false
                System.out.println("newFile" + newFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Test
    //   判断文件夹是否存在 不存在则创建文件
    //   mkdir()创建文件目录。如果此文件目录存在，就不创建了。如果此文件目录的上层目录不存在，也不创建。
    public void test2(){
        File file =new File("E:\\fileUp\\test");
        //如果文件夹不存在则创建
        if  (!file .exists()  && !file .isDirectory())
        {
            System.out.println("文件夹不存在 正在创建文件"+ file);
            file .mkdir();
        } else
        {
            System.out.println("文件夹目录" + file + "存在");
        }
    }

    @Test
    // mkdirs()创建文件目录。如果上层文件目录不存在，一并创建
    public void creatFile() throws IOException {
        File file =new File("E:\\fileUp\\test\\test22\\2.txt");

        //获取2.TXT的父目录
        File parentFile = file.getParentFile();
        //如果文件夹不存在则创建
        if  (!parentFile .exists()  && !parentFile .isDirectory()) {
            System.out.println("文件夹 正在创建文件"+ file);
            parentFile .mkdirs();
        }
        file.createNewFile(); //创建文件
    }

    @Test
    // 创建File类的实例
    public void test3(){
//        构造器一、  File(String filePath)
        File file1 = new File("hello.txt");//相对于当前model
        File file2 = new File("E:\\fileUp\\test\\我的笔记\\he.txt");
        System.out.println(file1);
        System.out.println(file2);

        //   构造器二、 File(String parentPath,String childPath)
        File file3 = new File("E:\\fileUp\\test\\java笔记","我的笔记");
        System.out.println(file3);

        //  构造器三、 File(File parentFile,String childPath)
        File file4 = new File(file3,"hi.txt");
        System.out.println(file4);
    }


    /**
     * File类的获取功能
     * public String getAbsolutePath()：获取绝对路径
     * public String getPath() ：获取路径
     * public String getName() ：获取名称
     * public String getParent()：获取上层文件目录路径。若无，返回null
     * public long length() ：获取文件长度（即：字节数）。不能获取目录的长度。
     * public long lastModified() ：获取最后一次的修改时间，毫秒值
     * public String[] list() ：获取指定目录下的所有文件或者文件目录的名称数组
     * public File[] listFiles() ：获取指定目录下的所有文件或者文件目录的File数组
     *
     */
    @Test  // 文件获取  和 判断文件类型  MultipartFile file1
    public  void getFile(MultipartFile file1){
        File file=new File("E:\\fileUp\\test\\2.txt");
        String absolutePath = file.getAbsolutePath(); //E:\fileUp\test\2.txt
        File parentFile = file.getParentFile(); //E:\fileUp\test
        String parent = file.getParent(); // E:\fileUp\test
        String path = file.getPath(); //E:\fileUp\test\2.txt
        String name = file.getName(); //2.txt
        long length = file.length(); // 0
        long l = file.lastModified(); //1648629572774
        String[] list = file.list(); //null
        File[] files = file.listFiles(); //null



        // MultipartFile file
        String originalFilename = file1.getOriginalFilename(); //获取文件全名 测试上传.xlsx

        //判断文件类型
        // 方法一 originalFilename.endsWith("xlsx") true判断文件是什么类型

        // 方法二   if (!originalFilename.matches("^.+\\.(?i)(xls)$") && !originalFilename.matches("^.+\\.(?i)(xlsx)$")) {
        //            logger.error("上传文件格式不正确");
        //        }
        // 方法三 获取文件类型 xlsx
        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase(Locale.US);
        if (!fileType.equals("xlsx")) {
            System.out.println("msg文件类型错误不是excel类型" + fileType);
        }
    }

    /**
     * File类的判断功能
     *  public boolean isDirectory()：判断是否是文件目录
     *  public boolean isFile() ：判断是否是文件
     *  public boolean exists() ：判断是否存在
     *  public boolean canRead() ：判断是否可读
     *  public boolean canWrite() ：判断是否可写
     *  public boolean isHidden() ：判断是否隐藏
     *
     * File类的删除功能
     *       public boolean delete()：删除文件或者文件夹
     *    删除注意事项：
     *       1、Java中的删除不走回收站。
     *       2、要删除一个文件目录，请注意该文件目录内不能包含文件或者文件目录
     *
     *
     */
}
