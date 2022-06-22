package com.guilin.studycode.controller.entiry;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guilin.studycode.dto.StudentDto;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.mapper.StudentMapper;
import com.guilin.studycode.service.StudentService;
import com.guilin.studycode.utils.NullProcessUtil;
import com.guilin.studycode.utils.date.StringDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/student")
@RestController
@Api(tags = "学生相关接口")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    //查询方法
    @GetMapping("/queryById")
    @ApiOperation(value = "根据学生id查询学生信息")
    public Student queryById(int id){
        return studentService.queryById(id);
    }

    //查询方法
    @GetMapping("/getById")
    @ApiOperation(value = "根据学生id查询学生信息(默认)")
    public Student getById(int id){
        return studentService.getById(id);
    }

    @GetMapping("/pageQuery")
    @ApiOperation(value = "根据条件分页查询")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "当前页码", required = true, paramType = "query"),
    @ApiImplicitParam(name = "limit", value = "每页条数", required = true, paramType = "query"),
    @ApiImplicitParam(name = "ssex", value = "学生性别", paramType = "query"),
    @ApiImplicitParam(name = "sname", value = "学生性名",  paramType = "query")
    })
    public IPage<Student> pageQuery(int page, int limit,String ssex,String sname) {
        return studentService.pageQuery(page, limit,ssex,sname);
    }


    @GetMapping("/orderBy")
    @ApiOperation(value = "根据条件排序")
    public List<Student> orderBy() {

        List<Student> studentList=studentMapper.selectList(new QueryWrapper<Student>()
                .eq("SSEX","F")
                .like("SNAME", "add")
                //.orderBy("age")
                .orderByDesc("SNO","ID")

        );
        System.out.println("*******************"+studentList);
        for (Student student : studentList) {
            System.out.println(student.toString());
        }

        return studentList;
    }

    //添加或修改学生信息
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "添加或修改学生信息")
    public void saveOrUpdate(StudentDto dto){
        Student student =new Student();
        student.setSNAME(dto.getSNAME());
        student.setSNO(dto.getSNO());
        student.setSSEX(dto.getSSEX());
        student.setUpdateDate(dto.getUpdateDate());
        student.setCreateDate(new DateTime(new Date()));
       // student.setCreateDate(new DateTime(dto.getCreateDate())); //或者是页面传进来的值
        boolean b = studentService.saveOrUpdate(student);
        if(b){
            logger.info("saveOrUpdate is OK ");
        }
    }

    // 通过map保存
    @PostMapping("/saveOrUpdateByMap")
    @ApiOperation(value = "用map来接受参数——添加或修改学生信息")
    public Map saveStudentMap(@RequestBody Map map1) {
        Map<String, String> out = new HashMap<String, String>();

        String SNO = NullProcessUtil.nvlToString(map1.get("SNO"), "");

        Map<String, String> map = new HashMap<String, String>();
      //  map.put("SNO", SNO);
        map.put("SNO", "102");
        map.put("SNAME", "测试map2");
        map.put("SSEX", "M");
        map.put("remark", "备注");
        SimpleDateFormat simpleDateFormat = StringDateUtil.dateFormat(4);
        String createDate = simpleDateFormat.format(new Date());
        String updateDate = simpleDateFormat.format(new Date());
        map.put("createDate", createDate);
        // map.put("updateDate", updateDate);
        Map<String, String> result = studentService.saveStudentMap(map);;
        System.out.println(result);
        return result;

    }

}
