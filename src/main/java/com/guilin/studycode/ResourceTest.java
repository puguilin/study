package com.guilin.studycode;

/**
 * @description:
 * @author: puguilin
 * @date: 2022/4/14
 * @version: 1.0
 */

public class ResourceTest {

    /**
     *  由于maven项目打包会把 src/main/java 和 src/main/resources 下的文件放到 target/classes 下，所以统一以根路径代表此目录
     *
     *  this.getClass().getResource("/").getPath()
     *        |______src/main/java                                             如果实在java文件下
     *        |          |____   /E:/studyCode/StudyCode/target/classes/
     *        |______src/test/java
     *                  |____   /E:/studyCode/StudyCode/target/test-classes/   如果实在test文件下
     *
     *
     *
     */

    public static void main(String[] args) {
        String root = ResourceTest.class.getResource("/").getPath();
        System.out.println(" root " + root);  //  root /E:/studyCode/StudyCode/target/classes/
    }
}
