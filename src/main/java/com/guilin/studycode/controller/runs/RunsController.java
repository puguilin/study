package com.guilin.studycode.controller.runs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @description:   启动时自动执行一些代码   @Service  implements  ApplicationRunner  实现run的方法
 * @author: puguilin
 * @date: 2022/5/17
 * @version: 1.0
 */
@Service
public class RunsController implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("启动时自动执行");
    }
}
