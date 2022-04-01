package com.guilin.studycode;

import com.alibaba.fastjson.JSONObject;
import com.guilin.studycode.utils.IpHttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @description:
 * @author: puguilin
 * @date: 2022/4/1
 * @version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class IpHttpUtilsTest {




    /**
     *`
     *  一、通过ip获取所在地址位置
     *
     *      方法一、使用http://whois.pconline.com.cn
     *      方法二、使用https://ip.cn/api/index
     *
     *   二、通过手机号获取归属地
     *
     *`
     *`
     */
    @Test //通过ip获取所在地址位置
    public void test1(){
        String url = "http://whois.pconline.com.cn/ipJson.jsp";
        String ip = "223.72.75.159";
        String rspStr = IpHttpUtils.sendGet(url, "ip=" + ip + "&json=true", "GBK");
        JSONObject obj = JSONObject.parseObject(rspStr);
        String region = obj.getString("pro");
        String city = obj.getString("city");
        System.out.println("所在地址位置：" + region + " " + city);
    }

    @Test //通过ip获取所在地址位置
    public void test2(){
        String url = "https://ip.cn/api/index";
        String ip = "223.72.75.159";
        String rspStr = IpHttpUtils.sendGet(url, "ip=" + ip + "&type=1", "UTF-8");
        JSONObject obj = JSONObject.parseObject(rspStr);
        String address = (String) obj.get("address");
        System.out.println("所在地址位置：" + address);

    }

    @Test //通过手机号获取归属地
    public void test() throws IOException {
        String url = "https://www.ip138.com/mobile.asp";
        String mobile = "17521274926";
        Document doc = Jsoup.connect(url + "?mobile=" + mobile + "&action=mobile").timeout(1000 * 60 * 30).get();
        JXDocument jxDocument = new JXDocument(doc.getAllElements());
        Element element = (Element)jxDocument.selOne("//table/tbody/tr[2]/td[2]/span");
        System.out.println("手机号归属地：" + element.text());


    }
}
