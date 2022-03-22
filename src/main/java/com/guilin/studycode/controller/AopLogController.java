package com.guilin.studycode.controller;

import com.guilin.studycode.config.Log;
import com.guilin.studycode.service.SysLogService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:aop 日志记录
 * @author: puguilin
 * @date: 2022/3/22
 * @version: 1.0
 */
@RequestMapping("/log")
@RestController
@Api(tags = "aop接口")
public class AopLogController {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(AopLogController.class);

    @Autowired
    private SysLogService logService;



    /**
     * @param name:
     * @param nick:
     * @return Object
     * @author guilin  实现AOP记录面向开发者的日志（日志入库--日志记录到数据库）
     * @description TODO
     * @date 2022/3/22 13:35
     */
    @RequestMapping("/aop")
    @ResponseBody
    @Log("测试aoplog")
    public Object aop(String name, String nick) {
        Map<String, Object> map = new HashMap<>();
        log.info("我被执行了！");
        map.put("res", "ok");
        map.put("name", name);
        map.put("nick", nick);
        return map;
    }



    /***
     * @param name:
     * @param nick:
     * @return Object
     * @author guilin  日志不入库，控制台打印出来即可。
     * @description TODO
     * @date 2022/3/22 13:35
     */
    @RequestMapping("/testaop3")
    @ResponseBody
    public Object testAop3(String name, String nick) {
        Map<String, Object> map = new HashMap<>();

        log.info("testaop3 我被执行了！");
        map.put("res", "ok");
        return map;
    }
}