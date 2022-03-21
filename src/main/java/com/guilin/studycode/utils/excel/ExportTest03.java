/**
 *
 *
 */
package com.guilin.studycode.utils.excel;

/**
 *@Desc 
 *
 * @author guilin
 *
 *
 * @Date 2019年2月22日 下午4:13:36
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


/*
 * 使用步骤:
 * 1.新建一个类,例如StudentVO.
 * 2.设置哪些属性需要导出,哪些需要设置提示.
 * 3.设置实体数据
 * 4.调用exportExcel方法.
 */
public class ExportTest03 {
	public static void main(String[] args) {
		// 初始化数据
		List<StudentVO> list = new ArrayList<StudentVO>();
 
		StudentVO vo = new StudentVO(1,"李坤",26,"五期提高班","天融信","北京","513721199212257232");
		list.add(vo);
		StudentVO vo2 = new StudentVO(2,"曹贵生",26,"五期提高班","中银","上海","513721199212257232");
		list.add(vo2);
		StudentVO vo3 = new StudentVO(3,"柳波",26,"五期提高班","中银","成都","513721199212257232");
		list.add(vo3);
 
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("E:\\test\\success3.xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ExcelUtil<StudentVO> util = new ExcelUtil<StudentVO>(StudentVO.class);// 创建工具类.
		util.exportExcel(list, "学生信息", 65536, out);// 导出
		System.out.println("----执行完毕----------");
	}
 
}

