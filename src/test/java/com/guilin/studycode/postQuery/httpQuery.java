package com.guilin.studycode.postQuery;

import com.guilin.studycode.utils.ReqParamsHelper.RequestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:  获取token
 * @author: puguilin
 * @date: 2022/4/1
 * @version: 1.0
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class httpQuery {

    RequestUtil http = new RequestUtil();

    @Test //获取token
    public void  test1(){

        /**
         *  apiUrl: 接口地址
         * username: 用户名
         * password:密码
         * tenantUrl: 租户标识
         */
        String apiUrl = "null" ;
        String username = "null" ;
        String password = "null" ;
        String tenantUrl = "null" ;
        String token =  http.doPostToken(apiUrl,username,password,tenantUrl);
        System.out.println("token " + token);

    }
}
