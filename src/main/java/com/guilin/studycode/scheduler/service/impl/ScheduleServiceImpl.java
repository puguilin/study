package com.guilin.studycode.scheduler.service.impl;

import com.guilin.studycode.scheduler.service.ScheduleService;

import lombok.SneakyThrows;
import org.quartz.*;
import org.quartz.impl.calendar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.quartz.DateBuilder.newDate;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Override
    public void addSchedule(String jobName, String jobGroup, String jobClassName, String triggerName, String triggerGroup, String cronExpression) {
        try {
            Class className = Class.forName(jobClassName);
            QuartzJobBean jobBean = (QuartzJobBean) className.newInstance();
            JobDetail jobDetail = JobBuilder.newJob(jobBean.getClass())
                    .withIdentity(jobName, jobGroup)
                    .storeDurably()
                    .withDescription(jobName)//详情task
                    // JobDataMap .usingJobData()
                    // 或者  job.getJobDataMap().put("jobaa", "呵呵");
                    //然后在任务类中进行 获取 String jobcc = dataMap.getString("jobcc");
                    .usingJobData(jobName, jobGroup)
                    .build();

            //========================================cronTrigger=======================================
            //========================================cronTrigger=======================================
            //========================================cronTrigger=======================================

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroup)
                    .startAt(new Date())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();


            //========================================simpleTrigger=======================================
            //========================================simpleTrigger=======================================
            //========================================simpleTrigger=======================================
            /*SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroup)// 定义名字和组
                    .startAt(new Date())//定义开始时间
                    .withSchedule(    //定义任务调度的时间间隔和次数
                            SimpleScheduleBuilder
                                    .simpleSchedule()
                                    .withIntervalInSeconds(2)//定义时间间隔是2秒
                                    .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY)//定义重复执行次数是无限次
                    )
                    .build();*/
            //scheduler.scheduleJob(jobDetail, simpleTrigger);

            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
        } catch (SchedulerException | ClassNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void pauseSchedule(String jobName, String jobGroup) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeSchedule(String jobName, String jobGroup) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSchedule(String jobName, String jobGroup) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    //======================================================日历型的===================================================
    //======================================================日历型的===================================================
    //======================================================日历型的===================================================
    @Override
    @SneakyThrows
    public void addScheduleCalendar(String jobName, String jobGroup, String jobClassName, String triggerName, String triggerGroup, String cronExpression) {
        try {
            scheduler.addCalendar("annuals", cronCalendar(), false, false);
            Class className = Class.forName(jobClassName);
            QuartzJobBean jobBean = (QuartzJobBean) className.newInstance();
            JobDetail jobDetail = JobBuilder.newJob(jobBean.getClass())
                    .withIdentity(jobName, jobGroup)
                    .storeDurably()
                    .build();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().modifiedByCalendar("annuals")
                    .withIdentity(triggerName, triggerGroup)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
        } catch (SchedulerException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private Calendar annualCalendar() {
        AnnualCalendar annualCalendar = new AnnualCalendar();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(newDate().inMonthOnDay(11, 5).build());
        annualCalendar.setDayExcluded(gregorianCalendar, true);
        return annualCalendar;
    }

    private Calendar cronCalendar() throws ParseException {
        CronCalendar cronCalendar = new CronCalendar("* 0-10,20-30 * * * ?");
        return cronCalendar;
    }

    private Calendar dailyCalendar() {
        DailyCalendar dailyCalendar = new DailyCalendar("08:00:00", "10:00:00");
        return dailyCalendar;
    }

    private Calendar holidayCalendar() {
        HolidayCalendar holidayCalendar = new HolidayCalendar();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(newDate().inMonthOnDay(11, 4).build());
        holidayCalendar.addExcludedDate(gregorianCalendar.getTime());
        return holidayCalendar;
    }

    private Calendar monthlyCalendar() {
        MonthlyCalendar monthlyCalendar = new MonthlyCalendar();
        monthlyCalendar.setDayExcluded(4, true);
        return monthlyCalendar;
    }

    private Calendar weeklyCalendar() {
        WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
        weeklyCalendar.setDayExcluded(1, true);
        return weeklyCalendar;
    }

}

