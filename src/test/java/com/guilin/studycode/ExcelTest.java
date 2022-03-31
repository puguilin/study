package com.guilin.studycode;

import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.utils.ExcelTool;
import com.guilin.studycode.utils.FileParseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:excel导入导出
 * @author: puguilin
 * @date: 2022/3/30
 * @version: 1.0
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelTest {


    @Test //导出excel
    public void exportExcel(){
        //File file =new File("E:\\fileUp\\test\\test.xls");
        File file =new File("E:\\fileUp\\test\\test.xlsx");
        //File file =new File(""); //流出现异常，尽快确认！
        List<Student> list = new ArrayList<Student>();

        Student vo = new Student(1,"001","tom","F","测试",new Date(),new Date());
        Student vo1 = new Student(2,"002","tom02","F","测试02",new Date(),new Date());

        list.add(vo);
        list.add(vo1);

        //类的字段属性
        String[] feilds = {"ID","SNO","SNAME","SSEX","remark","createDate","updateDate"};
        // 创建 表头 单元格
        List<String> head = new ArrayList<>();
        head.add("ID");
        head.add("学号");
        head.add("姓名");
        head.add("性别");
        head.add("备注");
        head.add("修改时间");
        head.add("创建时间");

        //导出
        ExcelTool.exportExcel(Student.class, list, feilds, file,head);

        System.out.println("----执行导出数据完毕----------");
    }


    @Test  //导入
    public void  importExcel(){
        File file =new File("E:\\fileUp\\test\\test.xlsx");
        ExcelTool tool = new ExcelTool(file.getAbsolutePath());
        //类的字段属性
        String[] feilds = {"ID","SNO","SNAME","SSEX","remark","createDate","updateDate"};

        /**
         * Student.class   数据对象
         * feilds 对象属性
         * sheetIndex  读取第几个工作表
         * spkit  跳过第几行开始读取数据
         */
        FileParseResult<Student> excelData = tool.getExcelData(Student.class, feilds, 0, 1);
        List<Student> resultList = excelData.getResultList();
        System.out.println("导入的数据为 " + resultList);
        System.out.println("----执行导入数据完毕----------");
    }

    @Test  //获取所有的列
    public void  getAllData(){

        File file =new File("E:\\fileUp\\test\\test.xlsx");
        ExcelTool tool = new ExcelTool(file.getAbsolutePath());
        List<String[]> allData = tool.getAllData(0);
        for (String[] allDatum : allData) {
            for (int i = 0; i < allDatum.length; i++) {
                System.out.println("所有数据列为 " + allDatum[i]);
            }
        }
        System.out.println("----获取所有数据列完毕----------");
    }




}
