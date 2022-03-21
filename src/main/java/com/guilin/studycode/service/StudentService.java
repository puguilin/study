package com.guilin.studycode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guilin.studycode.entrity.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentService  extends IService<Student> {

    Student queryById (int id);

    boolean addOrUpdate(Student student);

    int saveStudent(Student student);

    /**
     * 根据（性别）条件分页查询印章
     * @param ssex
     * @param page
     * @param limit
     * @return
     */
    IPage<Student> pageQuery(int page, int limit,String ssex,String sname);
}
