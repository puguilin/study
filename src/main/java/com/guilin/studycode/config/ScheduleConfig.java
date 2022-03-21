//package com.guilin.studycode.config;
//
//import com.guilin.studycode.scheduler.task.TaskJob;
//import org.quartz.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import java.util.Date;
//
//import static org.quartz.JobBuilder.newJob;
//
///**
// * @description:任务配置类
// * @author: puguilin
// * @date: 2022/3/18
// * @version: 1.0
// */
//
//@Configuration
//public class ScheduleConfig {
//
//     //指定具体的定时任务类
//    @Bean
//    public JobDetail uploadTaskDetail() {
//
//        JobDetail job = newJob(TaskJob.class)
//                .withIdentity("TaskJob", "TaskJob") // 给job命名并分组  jobName, jobGroup
//                .storeDurably()
//                .withDescription("TaskJob")//详情task
//                // JobDataMap .usingJobData()
//                // 或者  job.getJobDataMap().put("jobaa", "呵呵");
//                //然后在任务类中进行 获取 String jobcc = dataMap.getString("jobcc");
//                .usingJobData("jobcc", "Hello World!")
//                .build();
//                job.getJobDataMap().put("jobaa", "在任务类中获取");
//
//        return job;
//    }
//
//    @Bean
//    public Trigger uploadTaskTrigger() {
//
//        //TODO 这里设定执行方式
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
//                .forJob(uploadTaskDetail())
//                .withIdentity("TaskJob", "TaskJob")  // 给job命名并分组  jobName, jobGroup
//                .startAt(new Date())
//                .startNow()
//                .withSchedule(scheduleBuilder)
//                .build();
//
//        return cronTrigger;
//
//    }
//
//}