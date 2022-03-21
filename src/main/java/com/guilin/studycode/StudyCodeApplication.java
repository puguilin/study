package com.guilin.studycode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"com.guilin.studycode.mapper"})
@EnableScheduling
public class StudyCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyCodeApplication.class, args);
    }

}


