package com.guilin.studycode.scheduler.task;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * 具体要实现是任务逻辑
 */
public class TaskJob2 extends QuartzJobBean {


    private static final Logger logger = LoggerFactory.getLogger(TaskJob2.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobBuilder jobBuilder = jobDetail.getJobBuilder();
        logger.info("执行的定时任务是 =={}", jobDetail.getDescription());
        Job jobInstance = context.getJobInstance();
        logger.info("jobInstance --{}", jobInstance.toString());
        Scheduler scheduler = context.getScheduler();
        logger.info("scheduler --{}", scheduler.toString());

        //TODO 写日志到数据库中



    }

}
