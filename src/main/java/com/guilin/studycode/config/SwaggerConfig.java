package com.guilin.studycode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket applicationApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("guilin")
                .select()  // 选择哪些路径和api会生成document
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.guilin.studycode.controller"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(applicationInfo());//用来创建该Api的基本信息(这些基本信息会展现在文档页面中)
    }

    private ApiInfo applicationInfo() {
        ApiInfo apiInfo = new ApiInfo("接口管理",//大标题
                "api接口可视化管理" ,//小标题
                "0.1",//版本
                "",
                new Contact("蒲贵林", "https://home.cnblogs.com/u/JonaLin/", "17521274926@163.com").toString(),// 作者
                "我的博客",//链接显示文字
                "https://home.cnblogs.com/u/JonaLin/"//网站链接
        );
        return apiInfo;
    }




}


