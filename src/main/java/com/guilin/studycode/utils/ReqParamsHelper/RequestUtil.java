package com.guilin.studycode.utils.ReqParamsHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description:请求 POST  GET方法
 * @author: puguilin
 * @date: 2022/3/28
 * @version: 1.0
 */

public class RequestUtil {

    @Resource
    RestTemplate restTemplate;


    private HttpHeaders headers;
    private HttpEntity<String> requestEntity;
    private ObjectMapper objectMapper;

    private static Logger log = LoggerFactory.getLogger("RequestUtil");



    //获取token
    /**
     * @param apiUrl: 接口地址
     * @param username: 用户名
     * @param password:密码
     * @param tenantUrl: 租户标识
     * @return String
     * @author guilin
     * @description
     * @date 2022/4/1 11:13
     */
    public  String doPostToken(String apiUrl, String username, String password, String tenantUrl) {
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        String idToken = null;
        try {
            // 构造一个URL对象
            URL url = new URL(apiUrl);
            // 获取URLConnection对象
            conn = (HttpURLConnection) url.openConnection();
            // 限制socket等待建立连接的时间，超时将会抛出java.net.SocketTimeoutException
            conn.setConnectTimeout(3000);
            // 限制输入流等待数据到达的时间，超时将会抛出java.net.SocketTimeoutException
            conn.setReadTimeout(3000);
            // 设定请求的方法为"POST"，默认是GET
            conn.setRequestMethod("POST");
            // 设置传送的内容类型是json格式
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 接收的内容类型也是json格式
            conn.setRequestProperty("Accept", "application/json;charset=utf-8");
            // 设置是否从httpUrlConnection读入，默认情况下是true
            conn.setDoInput(true);
            // 由于URLConnection在默认的情况下不允许输出，所以在请求输出流之前必须调用setDoOutput(true)。为一个HTTP URL将doOutput设置为true时，请求方法将由GET变为POST
            conn.setDoOutput(true);
            // 是否使用缓存，Post方式不能使用缓存
            conn.setUseCaches(false);
            // 准备数据
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", DigestUtils.md5Hex(password));
            json.put("tenantUrl", tenantUrl);
            // 返回一个OutputStream，可以用来写入数据传送给服务器
            out = conn.getOutputStream();
            // 将数据写入到输出流中
            out.write(json.toString().getBytes(StandardCharsets.UTF_8));
            // 刷新管道
            out.flush();
            // 建立连接
            conn.connect();
            // 判断数字响应码是否是200
            int responseCode = conn.getResponseCode();
            String result = "";
            if (responseCode == 200) {
                // 获取输入流
                in = conn.getInputStream();
                // 获取返回的内容
                StringWriter sw = new StringWriter();
                InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
                char[] buffer = new char[4096];
                for (int n = 0; -1 != (n = reader.read(buffer)); ) {
                    sw.write(buffer, 0, n);
                }
                result = sw.toString();
                // 处理返回的内容
                if (result != null && !"".equals(result.trim())) {
                    JSONObject rjo = JSONObject.parseObject(result, Feature.OrderedField);
                    if (rjo != null && rjo.containsKey("retCode")) {
                        int retCode = rjo.getInteger("retCode");
                        if (retCode == 0 && rjo.containsKey("data")) {
                            JSONObject data = rjo.getJSONObject("data");
                            if (null != data && data.containsKey("idToken")) {
                                // 获取最终想要的数据
                                idToken = data.getString("idToken");
                            }
                        }
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            throw new RuntimeException("获取token出现连接/超时异常");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取token时执行内部代码时出现异常");
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return idToken;
    }

    /**
     *  Map<String,String> queryMap = new HashMap<String,String>();
     * 	Map resultMap = null;
     * 	queryMap.put("cityCode", cityCode);
     * 	queryMap.put("ifGroup", ifGroup);
     *
     * 	//生成接口流水 yyyymmddh24miss+16位随机序列或字符串组成
     * 	String dateTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
     * 	String prea = dateTime + getRandomString(16);
     *
     *	resultMap = requestUtil.doPostMapStr("Url", queryMap);
     *
     * 发送请求
     * @param requstMap
     * @param url
     * @param requstMap
     * @return
     */
    public Map doPostMapStr(String url, Map<String,String> requstMap){
        String json = "";
        try {
            json = objectMapper.writeValueAsString(requstMap);
        } catch (JsonProcessingException e) {
            log.error("doPost2|Exception:" +e.getMessage());
            e.printStackTrace();
        }

        System.out.println(json);
        requestEntity = new HttpEntity<String>(json, headers);
        String resultStr= restTemplate.postForObject(url, requestEntity, String.class);
        System.out.println(resultStr);
        Map retrunMap = JSON.parseObject(resultStr, HashMap.class);
        return retrunMap;
    }


    /**
     *发送请求
     * @param requstMap
     * @param url
     * @param requstMap
     * @return
     */
    public Map doPostMap(String url,Map<String,Object> requstMap){
        String json = "";
        try {
            json = objectMapper.writeValueAsString(requstMap);
        } catch (JsonProcessingException e) {
            log.error("doPost2|Exception:" +e.getMessage());
            e.printStackTrace();
        }
        //System.out.println("请求参数为："+json);
        requestEntity = new HttpEntity<String>(json, headers);
        String resultStr= restTemplate.postForObject(url, requestEntity, String.class);
        Map retrunMap = JSON.parseObject(resultStr,HashMap.class);
        return retrunMap;
    }


    /**
     * 发送请求
     * @param json
     * @param url
     * @param json
     * @return
     */
    public Map doPostMapStr(String url,String json){

        System.out.println(json);
        requestEntity = new HttpEntity<String>(json, headers);
        String resultStr= restTemplate.postForObject(url, requestEntity, String.class);
        System.out.println(resultStr);
        Map retrunMap = JSON.parseObject(resultStr,HashMap.class);
        return retrunMap;
    }

    /**
     * 发送请求
     * @param json
     * @param url
     * @param json
     * @return
     */
    public String doPostStr(String url,String json){

        System.out.println(json);
        requestEntity = new HttpEntity<String>(json, headers);
        String resultStr= restTemplate.postForObject(url, requestEntity, String.class);
        System.out.println(resultStr);
        //Map retrunMap = JSON.parseObject(resultStr,HashMap.class);
        //return retrunMap;
        return resultStr;
    }


    /**
     *
     *  Map resultMap = new HashMap<>();
     *  Date d = new Date();
     *
     *  //请求报文整体参数
     *  Map<String, Object> req_map = new HashMap<>();
     *
     *  String app_secret = nlshop_secret;
     *  String APP_ID = nlshop_appid;
     *
     *  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
     *  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
     *  String TIMESTAMP = sdf.format(d);
     *  String TRANS_ID = formatter.format(d) + getRandom();
     *  String end = "APP_ID" + APP_ID + "TIMESTAMP" + TIMESTAMP + "TRANS_ID" + TRANS_ID + app_secret;
     *
     *  String TOKEN = md5(end);
     *
     *  //请求报文头
     *  Map<String, String> UNI_BSS_HEAD = new HashMap<>();
     *  UNI_BSS_HEAD.put("APP_ID", APP_ID);
     *  UNI_BSS_HEAD.put("TIMESTAMP", TIMESTAMP);
     *  UNI_BSS_HEAD.put("TRANS_ID", TRANS_ID);
     *  UNI_BSS_HEAD.put("TOKEN", TOKEN);
     *
     *  //报文头放入请求报文
     *  req_map.put("UNI_BSS_HEAD", UNI_BSS_HEAD);// 报文头
     *
     *  //请求报文体
     *  Map<String, Object> UNI_BSS_BODY = new HashMap<>();
     *  Map<String, Object> QRY_ROLES_BY_STAFF_REQ = new HashMap<>();
     *
     *  //生成json数组传
     *  QRY_ROLES_BY_STAFF_REQ.put("STAFF_ID",staffId);
     *  QRY_ROLES_BY_STAFF_REQ.put("PROVINCE_CODE","75");
     *  UNI_BSS_BODY.put("QRY_ROLES_BY_STAFF_REQ",QRY_ROLES_BY_STAFF_REQ);
     *  req_map.put("UNI_BSS_BODY", UNI_BSS_BODY);// 报文体信息
     *
     *  //请求报文附加信息
     *  Map<String, Object> UNI_BSS_ATTACHED = new HashMap<>();
     *  String MEDIA_INFO="";
     *  UNI_BSS_ATTACHED.put("MEDIA_INFO", MEDIA_INFO);
     *  //报文附加信息放入请求报文
     *  req_map.put("UNI_BSS_ATTACHED", UNI_BSS_ATTACHED);// 报文附加信息
     *  //处理 请求报文头
     *  Map<String ,String> _headers = new HashMap<>();
     *  _headers.put("Content-Type", "application/json; charset=UTF-8");
     *  _headers.put("Accept", "application/json");
     *  //不用压缩设置为空即可，压缩设置未gzip
     *  _headers.put("Accept-Encoding", "");
     *  log.info("queryRolesByStaff请求报文:"+req_map);
     *
     *  //发起访问请求
     *  resultMap = requestUtil.doPostMapForAbilityShop("url", req_map,_headers);
     *
     *
     * 发送请求
     * @param requstMap
     * @param url
     * @param _headers
     * @return
     */
    public Map doPostMapForAbilityShop(String url,Map<String,Object> requstMap,Map<String ,String> _headers ) {
        String json = "";
        int code=0;
        Map  retrunMap  = new HashMap();
        Map rep = new HashMap();
        Map<String, String> UNI_BSS_HEAD = new HashMap<>();
        String bodyAsString = "";
        String rspcode = "";
        try {
            json = objectMapper.writeValueAsString(requstMap);
        } catch (JsonProcessingException e) {
            log.error("doPost2|Exception:" +e.getMessage());
            e.printStackTrace();
            retrunMap.put("code","4444");
            retrunMap.put("detail","接口请求异常："+e.getMessage());
            return retrunMap;
        }
        System.out.println("doPostMapForAbilityShop请求参数为："+json);
        try {
            SSLContext sslcontext = createIgnoreVerifySSL();
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslcontext,
                    NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpClient;
            if ((url).substring(0, 5).equalsIgnoreCase("https")) {
                httpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();
            } else {
                httpClient = HttpClientBuilder.create().disableContentCompression().build();
            }
            //if (timeout == null || timeout.length() == 0) {
            //	timeout = "30";
            //}
            //超时时间默认写死30
            int tt = Integer.parseInt("30") * 1000;
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(tt).setConnectTimeout(tt)
                    .setSocketTimeout(tt).build();
            HttpPost po = new HttpPost(url);

            po.addHeader("Content-Type", (String) _headers.get("Content-Type"));
            po.addHeader("Accept",   (String) _headers.get("Accept"));
            po.addHeader("Accept-Encoding",  (String) _headers.get("Accept-Encoding"));

            po.setConfig(requestConfig);

            StringEntity entity = new StringEntity(json, "utf-8");
            po.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(po);

            code = response.getStatusLine().getStatusCode();
            bodyAsString = EntityUtils.toString(response.getEntity());
            System.out.println(bodyAsString.toString());
            if (code == 200) {
                Map result = objectMapper.readValue(bodyAsString,Map.class);
                //读取head部分的code情况
                if (result.containsKey("UNI_BSS_HEAD")) {
                    UNI_BSS_HEAD = (Map)result.get("UNI_BSS_HEAD");
                    rspcode = (String)UNI_BSS_HEAD.get("RESP_CODE");
                    String  rspdesc = (String)UNI_BSS_HEAD.get("RESP_DESC");
                    if ("00000".equals(rspcode))
                    {
                        rspcode ="0000";
                        retrunMap.put("code", rspcode);
                        retrunMap.put("detail", rspdesc);
                        //只取body，其他的不要
                        Map  UNI_BSS_BODY_OUT = (Map) result.get("UNI_BSS_BODY");
                        retrunMap.put("rep",UNI_BSS_BODY_OUT);
                    }

                }
            }else{
                retrunMap.put("code", "4444");
                retrunMap.put("detail", "接口发送请求异常，返回HTTP错误，HTTP "+code);
                rep.put("msg",bodyAsString);
                retrunMap.put("rep",rep);
            }
        } catch (IOException e) {
            e.printStackTrace();
            retrunMap.put("code","4444");
            retrunMap.put("detail","远程接口请求IOException异常："+e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            retrunMap.put("code","4444");
            retrunMap.put("detail","远程接口请求NoSuchAlgorithmException异常："+e.getMessage());
        } catch (KeyManagementException e) {
            e.printStackTrace();
            retrunMap.put("code","4444");
            retrunMap.put("detail","远程接口请求KeyManagementException异常："+e.getMessage());
        }
        return retrunMap;
    }


    /**
     *  调用 doPostMapForAbilityShop（）方法
     *
     */
    public static void main(String[] args) {
        RequestUtil util = new RequestUtil();
        String staffId = "123";
        Map map = util.queryRolesByStaff(staffId);
        System.out.println("map: " + map);

    }
    public Map queryRolesByStaff(String staffId){

        Map resultMap = new HashMap<>();
        Date d = new Date();
        //请求报文整体参数
        Map<String, Object> req_map = new HashMap<>();

        String app_secret = "nlshop_secret";
        String APP_ID = "nlshop_appid";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String TIMESTAMP = sdf.format(d);
        String TRANS_ID = formatter.format(d) + getRandom();
        String end = "APP_ID" + APP_ID + "TIMESTAMP" + TIMESTAMP + "TRANS_ID" + TRANS_ID + app_secret;
        String TOKEN = md5(end);
        //请求报文头
        Map<String, String> UNI_BSS_HEAD = new HashMap<>();
        UNI_BSS_HEAD.put("APP_ID", APP_ID);
        UNI_BSS_HEAD.put("TIMESTAMP", TIMESTAMP);
        UNI_BSS_HEAD.put("TRANS_ID", TRANS_ID);
        UNI_BSS_HEAD.put("TOKEN", TOKEN);
        //报文头放入请求报文
        req_map.put("UNI_BSS_HEAD", UNI_BSS_HEAD);// 报文头
        //请求报文体
        Map<String, Object> UNI_BSS_BODY = new HashMap<>();
        Map<String, Object> QRY_ROLES_BY_STAFF_REQ = new HashMap<>();

        //生成json数组传
        QRY_ROLES_BY_STAFF_REQ.put("STAFF_ID",staffId);
        QRY_ROLES_BY_STAFF_REQ.put("PROVINCE_CODE","75");
        UNI_BSS_BODY.put("QRY_ROLES_BY_STAFF_REQ",QRY_ROLES_BY_STAFF_REQ);
        req_map.put("UNI_BSS_BODY", UNI_BSS_BODY);// 报文体信息

        //请求报文附加信息
        Map<String, Object> UNI_BSS_ATTACHED = new HashMap<>();
        String MEDIA_INFO="";
        UNI_BSS_ATTACHED.put("MEDIA_INFO", MEDIA_INFO);
        //报文附加信息放入请求报文
        req_map.put("UNI_BSS_ATTACHED", UNI_BSS_ATTACHED);// 报文附加信息
        //处理 请求报文头
        Map<String ,String> _headers = new HashMap<>();
        _headers.put("Content-Type", "application/json; charset=UTF-8");
        _headers.put("Accept", "application/json");
        //不用压缩设置为空即可，压缩设置未gzip
        _headers.put("Accept-Encoding", "");
        log.info("queryRolesByStaff请求报文:"+req_map);
        //发起访问请求
        resultMap = doPostMapForAbilityShop("url", req_map,_headers);
        log.info("queryRolesByStaff响应报文:"+resultMap);
        //{UNI_BSS_ATTACHED={MEDIA_INFO=}, UNI_BSS_BODY={QRY_ROLES_BY_STAFF_REQ={STAFF_ID=123, PROVINCE_CODE=75}}, UNI_BSS_HEAD={APP_ID=nlshop_appid, TIMESTAMP=2022-03-28 14:09:50 861, TRANS_ID=20220328140950861760155, TOKEN=7e0225425401bd3b2cb5461e36901f43}}
        return resultMap;
    }


    /**
     * 发送请求
     * @param requstMap
     * @param url
     * @param requstMap
     * @return
     */
    public String doPostMapByQrCode(String url,Map<String,Object> requstMap){
        String json = "";
        try {
            json = objectMapper.writeValueAsString(requstMap);
        } catch (JsonProcessingException e) {
            log.error("doPost2|Exception:" +e.getMessage());
            e.printStackTrace();
        }
        System.out.println("请求参数为："+json);
        requestEntity = new HttpEntity<String>(json, headers);
        String resultStr= restTemplate.postForObject(url, requestEntity, String.class);
        return resultStr;
    }

    /**
     * 发送请求 post
     * @param url
     * @param json
     * @return
     */
    public Map<String,String> doPostJson(String url,String json){
        requestEntity = new HttpEntity<String>(json, headers);
        String resultStr= restTemplate.postForObject(url, requestEntity, String.class);
        Map retrunMap = JSON.parseObject(resultStr,HashMap.class);
        return retrunMap;
    }

    /**
     * 发送请求get
     * @param url
     * @param url
     * @return
     */
    public Map<String,String> doGetRequest(String url){
        String resultStr= restTemplate.getForObject(url, String.class);
        log.info("公众号返回报文："+ resultStr);
        Map retrunMap = JSON.parseObject(resultStr,HashMap.class);
        return retrunMap;
    }


    // 不使用证书
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSL");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }


    public static String md5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }
        try {
            // d27aaf52af26c0549de0ff11aa04595b
            return String.format("%032x", new BigInteger(1, md5.digest(str.getBytes())));
        } catch (Exception e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");

        }
    }

    public static String getRandom() {
        int i = 0;
        StringBuilder st = new StringBuilder();
        while (i < 6) {
            i++;
            st.append(ThreadLocalRandom.current().nextInt(10));
        }
        return st.toString();
    }
}
