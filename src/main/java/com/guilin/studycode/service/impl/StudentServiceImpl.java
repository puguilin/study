package com.guilin.studycode.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.mapper.StudentMapper;
import com.guilin.studycode.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public boolean addOrUpdate(Student student) {
        boolean b = saveOrUpdate(student);

        return b;
    }
}
