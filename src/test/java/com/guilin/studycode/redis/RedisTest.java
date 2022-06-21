package com.guilin.studycode.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.guilin.studycode.entrity.Student;
import com.guilin.studycode.utils.MD5Util;
import com.guilin.studycode.utils.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @description:redisc 测试
 * @author: puguilin
 * @date: 2022/3/18
 * @version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;  //使用时 只能注入调用方法  不能通过new实例来调用方法

    @Autowired
    private MD5Util mD5Util;


    @Test
    public void addKey() throws JSONException {
        //redisTemplate.opsForValue().set("测试002", "测试001");
        // 或者
       // redisUtil.set("测试003", "测试003");
        Student student = new Student();
        student.setSNAME("test");
        student.setSNO("005");
        student.setSSEX("F");

        //redisUtil.set("学生",student);
//        Student tt = (Student)redisUtil.get("学生");
//        System.out.println("tt " +tt);


        Map<String, Object> map = new HashMap<>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");
        map.put("学生", student);

        //添加map
        //redisUtil.set("map",map);

        // String str = JSON.toJSONString(map);
        // JSONObject jsonObject = JSON.parseObject(str);


       // Map<String, Object>  map1 = (HashMap)redisUtil.get("map");

        // 获取map map里面包含对象
        String dd = JSON.toJSONString(redisUtil.get("map"));
        JSONObject jsonObject = JSON.parseObject(dd);
        JSONObject json = jsonObject.getJSONObject("学生");

        //JSONObject 转 java对象
        Student student1 = JSONObject.toJavaObject(json, Student.class);
        System.out.println("student1 " +student1);



    }

    @Test
    public void random(){
        Random r = new Random();
        System.out.println("时间： " + System.currentTimeMillis() + "随机数 "+ (int)(Math.random()*9+1)*10000);
        System.out.println(" auth_no = 时间 + 随机数：" + System.currentTimeMillis() + (int)(Math.random()*9+1)*10000);
    }
    //   1652845224708随机数 10000
    //   165284522470890000

    @Test
    public void md5() {
        /*
         *
         *
         *
         * auth_no  秒级时间戳+随机数(5位)
         *
         *
         * CLIENT_SECRET Md5(密钥+auth_no)密钥存储在本地，不在网络上传送
         *
         接入秘钥  - H5（测试环境）
             6eWev1StXQEhxwVXsPro
             PG7NOyr1q3uXOIBTlEnk
             *
            Md5(密钥+auth_no)密钥存储在本地，不在网络上传送
         * System.currentTimeMillis()  毫秒
         */

        Random r = new Random();
        System.out.println("时间： " + System.currentTimeMillis()/1000 + "随机数 "+ (int)(Math.random()*9+1)*10000);
        String auth_no = String.valueOf(System.currentTimeMillis()/1000) + String.valueOf((int) (Math.random() * 9 + 1) * 10000);
        System.out.println(" auth_no = 时间 + 随机数：" + auth_no);

        //测试环境秘钥  需要client_id 和client_secret 配对使用
        // String  m = "PG7NOyr1q3uXOIBTlEnk";
        // 生产环境秘钥  需要client_id 和client_secret 配对使用
        String  m = "MFF8FEbwh9sBRyEhkYwk";
        String value = m + auth_no;
        String s = mD5Util.MD5Encode(value);
        System.out.println("CLIENT_SECRET = " + s);
    }

}


/*

        "VALUEADDEDORDER_REQ":"联调测试",    	            必选	Object
        "RECORD_SEQUENCE_ID":"202205201543230001",	    必选	String		流水号，格式为时间戳+序列号：YYYYMMDDHHMMSSxxxx，其中xxxx为序列号，从0001开始，排满9999后重新循环
        "SYSTEM_ID":"516",	                            必选	String	    业务平台编码（2-3位），由CAP统一分配
        "SOURCEDEVICE_ID":"100275",	                    必选	String		业务平台设备编码（6位），由CAP统一分配
        "TOKEN":"17eda07face8c43e9af3d64cb066aecf",		    String	    认证token（免二次确认业务场景可不填，如：小额免密、2B产品）
        "CONVEYSTRING":"",		                            String	    随机字符串（正常业务不填，特殊）
        "ORDER_ID":"6822052051600001",	                必选	String		16位编码：CAP编码标识68（2位）+年（2位）+月（2位）+日（2位）+业务平台编码（2-3位）+序列（6-5位）注：1.业务平台编码：见SYSTEM_ID；2.分配平台编码为2位的，后面补6位序列编码；分配平台编码为3位的，后面补5位序列编码。3.序列必须是数字，且一天内的序列值不能重复。示例：21年3月15日来自系统编码01的订单号：6821031501000001保持全局唯一（与CANCEL_ID不重复）
        "MDN":"13264613669",	                        必选	String		订购手机号（订购关系拥有者用户号码和付费用户号码）
        "SP_PRODUCT_ID":"8000674500",	                必选	String		PRM产生的合作伙伴产品ID
        "SUBSCRIPTION_TIME":"20220520145600",	        必选	String		订购时间，格式：yyyyMMddHHmmss
        "EFFECTIVE_START_TYPE":"0",	                    必选	String	    生效类型：0立即生效；1次月生效
        "ORDER_METHOD":"02",		                        String		订购渠道：00 不确定01 营业厅面对面02 客服电话03 短信04 Web网站05 WAP门户06 IVR07 保留08 Java09 SP门户10 营销渠道批量操作11 营销渠道单个操作0E 网上商城对接的业务平台可申请扩展
        "STAFF_ID":"",		                            String		受理工号（cb可识别的工号，业务平台无特殊需求可不传，cap默认使用统一工号）
        "RECOM_PERSONID":"",		                    String		发展人ID
        "RECOM_PERSONNAME":"测试发展人",		                String		发展人名称
        "RECOM_DEPARTID":"012",		                        String		发展人部门ID
        "FEE_POINT":"1",		                            String	    计费点；0：非计费点。（为非计费点订单预留，目前默认1）
        "FEE_TYPE":"",		                                String		计费类型：目前不使用（为非计费点订单预留，目前没有这种业务模式）
        "DEPART_ID":"IT",		                            String		受理部门
        "MDN_CONFIRM":"17521274826",		                String		二次确认手机号(固网专用，非固网可不传)
        "NET_TYPE":"",		                                String		网络类型：1移网业务，2固网业务 说明：不传默认1移网业务
        "FEE":"",		                                    String	    单位：元。目前不使用（为非计费点订单预留，目前没有这种业务模式）""

                "" CAP编码标识68（2位）+年（2位）+月（2位）+日（2位）+业务平台编码（2-3位）+序列（6-5位）注：1.业务平台编码：见SYSTEM_ID；2.分配平台编码为2位的，后面补6位序列编码；分配平台编码为3位的，后面补5位序列编码。3.序列必须是数字，且一天内的序列值不能重复。
                示例：21年3月15日来自系统编码01的订单号：6821031501000001保持全局唯一（与CANCEL_ID不重复）




*/


















