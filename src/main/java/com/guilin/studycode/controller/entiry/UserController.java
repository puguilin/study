package com.guilin.studycode.controller.entiry;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guilin.studycode.dto.UserDto;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.entrity.User;
import com.guilin.studycode.mapper.UserMapper;
import com.guilin.studycode.service.UserService;
import com.guilin.studycode.utils.MD5Util;
import com.guilin.studycode.utils.ObjectEmptyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.guilin.studycode.utils.MD5Util.MD5Encode;


@RequestMapping("/user")
@RestController
@Api(tags = "用户相关接口")
public class UserController {


    //  通过  Mybatis-Plus插件操作
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
       // String md5 = MD5Util.MD5Decrypt((MD5Encode(password)));
        //解密
            // 1 先对其算法加密
        String kl = MD5Util.KL(MD5Encode(password));
        // 2 再进行解密
        String md5 = MD5Util.MD5Decrypt(kl);
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
        user.setSex(dto.getSex());
        user.setAddress(dto.getAddress());
        user.setHeight(dto.getHeight());
        user.setMobile(dto.getMobile());
        user.setCreateTime(new Date());
        user.setRemark(dto.getRemark());
        //加密
       // String password = MD5Util.KL(MD5Encode(dto.getPassword()));
        String password = MD5Util.MD5Encode(dto.getPassword());
        user.setPassword(password);
        boolean b = userService.saveOrUpdate(user);
        if(b){
            logger.info("saveOrUpdate is OK ");
        }
    }

    @PostMapping("/getNextvalId")
    @ApiOperation(value = "获取表中的id")
    public String  getNextvalId() {
        String tableName = "seq_test";
        String id = userMapper.getId(tableName);
        return id ;
    }

    //根据年龄排序获取用户
    @GetMapping("/getUserByAge")
    @ApiOperation(value = "根据年龄排序获取用户（多字段排序）")
    public List<User> getUserByAge() {
        List<User> list = userService.list();
        // https://blog.csdn.net/zhouzhiwengang/article/details/112312266

        //按年龄倒序排序 并且按创建时间升序排序
        List<User> collect = list.stream()
                .sorted(Comparator.comparing(User::getAge).reversed()
                .thenComparing(User::getCreateTime))
                .collect(Collectors.toList());

        return collect;
    }
}
