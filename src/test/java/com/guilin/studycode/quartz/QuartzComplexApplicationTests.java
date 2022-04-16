package com.guilin.studycode.quartz;

import com.guilin.studycode.scheduler.service.ScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuartzComplexApplicationTests {


    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void contextLoads() {
        scheduleService.addSchedule("cronScheduleDetail", "cronScheduleGroup", "com.guilin.studycode.scheduler.task.TaskJob", "cronScheduleTrigger", "cronTriggerGroup", "0/5 * * * * ?");
        //scheduleService.pauseSchedule("cronScheduleDetail", "cronScheduleGroup");
        //scheduleService.resumeSchedule("cronScheduleDetail", "cronScheduleGroup");
    }

}