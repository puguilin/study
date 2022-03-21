package com.guilin.studycode.utils.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSON {



	//TODO JSONObject 转 java对象  请看RedisTest 类


	
	//json字符串-简单对象型
	private static final String  JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";
	//json字符串-数组类型
	private static final String  JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";
	//复杂格式json字符串
	private static final String  COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":"
			+ "{\"courseName\":\"english\",\"code\":1270},\"students\":["
			+ "{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";

	
	public static void main1(String[] args) {
		

		/**
		 * json字符串-简单对象型与JSONObject之间的转换
		 */

        //JSONObject jsonObject = JSON.parseObject(JSON_OBJ_STR);
        JSONObject jsonObject1 = JSONObject.parseObject(JSON_OBJ_STR); //因为JSONObject继承了JSON，所以这样也是可以的
        System.out.println(jsonObject1);//{"studentAge":12,"studentName":"lily"}
        jsonObject1.put("11", "oo");
        System.out.println(jsonObject1.toJSONString());//{"11":"oo","studentAge":12,"studentName":"lily"}

        System.out.println(jsonObject1.getString("studentName")+" :"+jsonObject1.getInteger("studentAge")
        +": "+jsonObject1.getString("11")); //lily :12: oo

    }
	
	public static void main(String[] args) {
		/**
	     * json字符串-数组类型与JSONArray之间的转换
	     */
		// JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";


	        JSONArray jsonArray1 = JSONArray.parseArray(JSON_ARRAY_STR);//因为JSONArray继承了JSON，所以这样也是可以的

	        //遍历方式1
	        int size = jsonArray1.size();
	        for (int i = 0; i < size; i++){
	            JSONObject jsonObject = jsonArray1.getJSONObject(i);
	            System.out.println(jsonObject);//{"studentAge":12,"studentName":"lily"} 
	            System.out.println(jsonObject.getString("studentName")+":"+jsonObject.getInteger("studentAge")); //lily:12
	        }

	        //遍历方式2
	        for (Object obj : jsonArray1) {
	            JSONObject jsonObject = (JSONObject) obj;
	            System.out.println("=============");//{"studentAge":12,"studentName":"lily"}
	            System.out.println(jsonObject);
	            System.out.println(jsonObject.getString("studentName")+":"+jsonObject.getInteger("studentAge"));
	        }
	    }
	
	public static void main3(String[] args) {
		/**
	     * 复杂json格式字符串与JSONObject之间的转换
	     */
		/*COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":"
				                 + "{\"courseName\":\"english\",\"code\":1270},\"students\":["
				                        + "{\"studentName\":\"lily\",\"studentAge\":12},"
				                        + "{\"studentName\":\"lucy\",\"studentAge\":15}"
				                                                                          + "]"
				         +  "}";
*/
		

	        JSONObject jsonObject1 = JSONObject.parseObject(COMPLEX_JSON_STR);//因为JSONObject继承了JSON，所以这样也是可以的
	        System.out.println(jsonObject1);
	        String teacherName = jsonObject1.getString("teacherName");
	        Integer teacherAge = jsonObject1.getInteger("teacherAge");
	        JSONObject course = jsonObject1.getJSONObject("course");
	        JSONArray students = jsonObject1.getJSONArray("students");
	        String students1 = jsonObject1.getJSONArray("students").getString(0);
	        System.out.println(teacherName);//crystall
	        System.out.println(teacherAge);//27
	        System.out.println(course);//{"courseName":"english","code":1270}
	        System.out.println(students);//[{"studentAge":12,"studentName":"lily"},{"studentAge":15,"studentName":"lucy"}]
	        System.out.println(students1);//{"studentAge":12,"studentName":"lily"}
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
