package com.guilin.studycode.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.mapper.StudentMapper;
import com.guilin.studycode.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student queryById(int id) {

        return  studentMapper.queryById(id);
    }

    @Override
    public int saveStudent(Student student) {
        int insert = studentMapper.insert(student);
        return insert;
    }

    @Override
    public IPage<Student> pageQuery(int page, int limit, String ssex,String sname) {

        Page<Student> result = new Page<>(page, limit);
        IPage<Student> studentIPage = studentMapper.pageQuery(result, ssex,sname);
        return studentIPage;

    }

    @Override
    public Map<String, String> saveStudentMap(Map<String, String> map) {

        Map<String, String> out = new HashMap();
        out.put("code", "0000");
        out.put("detail", "成功");
        int res = studentMapper.saveStudentMap(map);
        if(res > 0){
            out.put("code", "0000");
            out.put("detail", "保存成功");

        }else {
            out.put("code", "4444");
            out.put("detail", "保存失败");
        }
        return out;
    }

    @Override
    public boolean addOrUpdate(Student student) {
        boolean b = saveOrUpdate(student);

        return b;
    }
}
