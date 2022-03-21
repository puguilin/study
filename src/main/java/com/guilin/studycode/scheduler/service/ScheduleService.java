package com.guilin.studycode.scheduler.service;

public interface ScheduleService {

    /**
     * 添加定时任务Job
     * @param jobName
     * @param jobGroup
     * @param jobClassName
     * @param triggerName
     * @param triggerGroup
     * @param cronExpression
     */
    void addSchedule(String jobName, String jobGroup, String jobClassName, String triggerName, String triggerGroup, String cronExpression);

    /**
     * 添加定时任务Job --Calendar 日历类型的
     * @param jobName
     * @param jobGroup
     * @param jobClassName
     * @param triggerName
     * @param triggerGroup
     * @param cronExpression
     */
    void addScheduleCalendar(String jobName, String jobGroup, String jobClassName, String triggerName, String triggerGroup, String cronExpression);
    /**
     * 暂停定时任务
     * @param jobName
     * @param jobGroup
     */
    void pauseSchedule(String jobName, String jobGroup);

    /**
     * 重启定时任务
     * @param jobName
     * @param jobGroup
     */
    void resumeSchedule(String jobName, String jobGroup);

    /**
     * 删除定时任务
     * @param jobName
     * @param jobGroup
     */
    void deleteSchedule(String jobName, String jobGroup);
}

