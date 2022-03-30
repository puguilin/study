package com.guilin.studycode.controller.entiry;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guilin.studycode.dto.UserDto;
import com.guilin.studycode.entrity.User;
import com.guilin.studycode.mapper.UserMapper;
import com.guilin.studycode.service.UserService;
import com.guilin.studycode.utils.MD5Util;
import com.guilin.studycode.utils.ObjectEmptyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.guilin.studycode.utils.MD5Util.MD5Encode;


@RequestMapping("/user")
@RestController
@Api(tags = "用户相关接口")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    //查询方法
    @GetMapping("/queryById")
    @ApiOperation(value = "根据用户id查询学生信息（service）")
    public User queryById(int id){
        return userService.getById(id);
    }

    //查询方法
    @GetMapping("/queryByIdAndPassward")
    @ApiOperation(value = "根据用户id和密码查询学生信息")
    public User queryById(int id,String password){
        String md5 = MD5Util.MD5Decrypt((MD5Encode(password)));
        User user = userService.queryByIdAndPassward(id, md5);
        if(ObjectEmptyUtil.isNotEmpty(user)){
          return user;
        }
        return null;
    }


    //查询方法
    @GetMapping("/getById")
    @ApiOperation(value = "根据学生id查询学生信息(mapper)")
    public User getById(int id){
        return userMapper.selectById(id);
    }

    @GetMapping("/pageQuery")
    @ApiOperation(value = "根据条件分页查询")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "当前页码", required = true, paramType = "query"),
    @ApiImplicitParam(name = "limit", value = "每页条数", required = true, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "用户姓名", paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "手机号",  paramType = "query")
    })
    public IPage<User> pageQuery(int page, int limit,String name,String mobile) {
        return userService.pageQuery(page, limit,name,mobile);
    }

    //添加或修改学生信息
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "添加或修改学生信息")
    public void saveOrUpdate(UserDto dto){
        User user =new User();
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setAddress(dto.getAddress());
        user.setHeight(dto.getHeight());
        user.setMobile(dto.getMobile());
        //加密

        user.setPassword(dto.getPassword());

        boolean b = userService.saveOrUpdate(user);
        if(b){
            logger.info("saveOrUpdate is OK ");
        }
    }


}