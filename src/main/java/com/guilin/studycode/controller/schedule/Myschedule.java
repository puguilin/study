package com.guilin.studycode.controller.schedule;

import com.guilin.studycode.scheduler.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;


/**
 * 定时任务
 */

@RequestMapping("/task")
@RestController
@Api(tags = "定时任务接口")
public class Myschedule {

    @Autowired
    private ScheduleService scheduleService;

    private static final Logger logger = LoggerFactory.getLogger(Myschedule.class);



    @PostMapping("/task")
    @ApiOperation("定时任务1 ")
    public void task() {
        logger.info("开始执行任务1  ={}", LocalDateTime.now());
        scheduleService.addSchedule("cronScheduleDetail", "cronScheduleGroup", "com.guilin.studycode.scheduler.task.TaskJob", "cronScheduleTrigger", "cronTriggerGroup", "0/8 * * * * ?");
        //scheduleService.pauseSchedule("cronScheduleDetail", "cronScheduleGroup");
        //scheduleService.resumeSchedule("cronScheduleDetail", "cronScheduleGroup");
    }

    @PostMapping("/task2")
    @ApiOperation("开启定时任务2 ")
    public void task2() {
        logger.info("开始执行任务2 ---add-- ={}", LocalDateTime.now());
        scheduleService.addSchedule("task2", "task2", "com.guilin.studycode.scheduler.task.TaskJob2", "cronScheduleTrigger2", "cronTriggerGroup2", "0/5 * * * * ?");
        //scheduleService.pauseSchedule("cronScheduleDetail", "cronScheduleGroup");
        //scheduleService.resumeSchedule("cronScheduleDetail", "cronScheduleGroup");
    }

    @PostMapping("/pause")
    @ApiOperation("暂停定时任务2 ")
    public void pause() {
        logger.info("暂停定时任务2 --pause-- ={}", LocalDateTime.now());
        scheduleService.pauseSchedule("task2", "task2");
    }

    @PostMapping("/resume")
    @ApiOperation("重启定时任务2 ")
    public void resume() {
        logger.info("重启定时任务2--resume--  ={}", LocalDateTime.now());
        scheduleService.resumeSchedule("task2", "task2");
    }

    @PostMapping("/deleteSchedule")
    @ApiOperation("删除定时任务2 ")
    public void deleteSchedule() {
        logger.info("删除定时任务2--delete--  ={}", LocalDateTime.now());
        scheduleService.deleteSchedule("task2", "task2");
    }

}
