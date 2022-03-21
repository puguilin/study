package com.guilin.studycode.scheduler.task;

import com.guilin.studycode.scheduler.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @description:  任务初始化
 * @author: puguilin
 * @date: 2022/3/17
 * @version: 1.0
 */


//@Configuration
//public class SystemSchedulerInit implements ApplicationRunner {
//
//    private static final Logger logger = LoggerFactory.getLogger(SystemSchedulerInit.class);
//
//    @Autowired
//    private ScheduleService scheduleService;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        logger.info("开始执行任务1  ={}", LocalDateTime.now());
//        scheduleService.addSchedule("cronScheduleDetail", "cronScheduleGroup", "com.guilin.studycode.scheduler.task.TaskJob", "cronScheduleTrigger", "cronTriggerGroup", "0/2 * * * * ?");
//        scheduleService.addSchedule("task2", "task2", "com.guilin.studycode.scheduler.task.TaskJob2", "cronScheduleTrigger2", "cronTriggerGroup2", "0/5 * * * * ?");
//
//    }
//}
