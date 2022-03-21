package com.guilin.studycode.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guilin.studycode.entrity.User;
import com.guilin.studycode.mapper.UserMapper;
import com.guilin.studycode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryByIdAndPassward(int id, String password) {
        return userMapper.queryByIdAndPassward(id,password);
    }

    @Override
    public IPage<User> pageQuery(int page, int limit, String name, String mobile) {
        Page<User> result = new Page<>(page, limit);
        IPage<User> userIPage = userMapper.pageQuery(result, name,mobile);
        return userIPage;
    }


}
