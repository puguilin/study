package com.guilin.studycode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.entrity.User;

public interface UserService extends IService<User> {


    /**
     * 根据用户id和密码查询学生信息
     * @param id
     * @param password
     * @return
     */
    User queryByIdAndPassward (int id,String password);
    /**
     * 根据（姓名或者手机号）条件分页查询印章
     * @param name
     * @param page
     * @param limit
     * @return
     */

    IPage<User> pageQuery(int page, int limit, String name, String mobile);
}

