package com.guilin.studycode.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:自定义注解类 定义一个方法级别的@log注解
 * @author: puguilin
 * @date: 2022/3/22
 * @version: 1.0
 */

  //**********************************************************************************************************
    /**
     * @Target
     *
     * 作用：用于描述注解的使用范围（即：被描述的注解可以用在什么地方）
     * 具体使用可参考： https://www.iteye.com/blog/josh-persistence-2226493
     *
     * 取值(ElementType)有：
     * 　1.CONSTRUCTOR:用于描述构造器
     *   2.FIELD:用于描述域即类成员变量
     * 　3.LOCAL_VARIABLE:用于描述局部变量
     * 　4.METHOD:用于描述方法
     * 　5.PACKAGE:用于描述包
     * 　6.PARAMETER:用于描述参数
     * 　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
     *
     */
    //**********************************************************************************************************


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String value() default "";
}
