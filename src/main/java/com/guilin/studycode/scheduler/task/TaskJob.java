package com.guilin.studycode.scheduler.task;

import cn.hutool.json.JSONObject;
import com.guilin.studycode.scheduler.service.ScheduleService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Component
public class TaskJob extends QuartzJobBean {


    private static final Logger logger = LoggerFactory.getLogger(TaskJob.class);


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        logger.info("执行的定时任务是 =={}", jobDetail.getDescription());

        //获取详细信息
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//        String jobcc = dataMap.getString("jobcc");
//        String jobaa = dataMap.getString("jobaa");
        String cronScheduleDetail = dataMap.getString("cronScheduleDetail");

//        System.out.println("======jobcc=======" + jobcc);
//        System.out.println("======jobaa=======" + jobaa);
        System.out.println("======cronScheduleDetail=======" + cronScheduleDetail);


    }

}
