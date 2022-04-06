package com.guilin.studycode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:Docker
 * @author: puguilin
 * @date: 2022/4/1
 * @version: 1.0
 */

@RestController
public class DockerController {
    @RequestMapping("/")
    public String index() {
        return "Hello Docker!";
    }
}
