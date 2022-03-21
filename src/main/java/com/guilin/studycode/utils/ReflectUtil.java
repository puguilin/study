package com.guilin.studycode.utils;

import com.guilin.studycode.entrity.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;


/**
 * java通过反射遍历实体类的属性和值，以及所有方法
 *<p>业务中经常会用到hashMap,也是因为它比较好遍历，map里存储的key value键值对，可以通过好多种方法很方便的遍历出来，比如keySet,Iterator等。</p>
 * <p>实体类也可以做到同样的效果，不管是实体类还是map,里面存储的无非都是键值对信息，在map里是key value的形式，在实体类里是属性 值的形式。</p>
 * <p>在业务场景中，有可能会有这样的需求：从数据库查出来的list,我们想要在程序里遍历把一些我们不希望返回到前端的信息给替换掉，比如往往给前端的格式是统一的String字符串，如果有null值，需要替换成空字符串的需求（当然不是所有人有，只是举个例子）。</p>
 * <p>这样，如果返回的类型是List&lt;Map&lt;String, Object&gt;&gt;，那么是比较容易处理的，只需要遍历list里的map的所有key和value，判断如果value是null，重新put这个key为空字符串就ok.</p>
 * <p>如果是实体类就需要用到java反射来达到同样的效果。<br>
 */
public class ReflectUtil {

    /**
     * 根据属性，获取get方法
     *
     * @param ob   对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    public static Object getGetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i].invoke(ob);
            }
        }
        return null;
    }

    /**
     * 根据属性，拿到set方法，并把值set到对象中
     *
     * @param obj       对象
     * @param clazz     对象的class
     * @param typeClass
     * @param value
     *     示例：    DataReq req=new DataReq();
     *         String field2 = "start";  属性名
     *         ReflectUtil.setValue(req,DataReq.class,field2,DataReq.class.getDeclaredField(field2).getType(),"汪汪");
     *
     *
     */
    public static void setValue(Object obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) {
        filedName = removeLine(filedName);
        String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        try {
            Method method = clazz.getDeclaredMethod(methodName, new Class[] { typeClass });
            method.invoke(obj, new Object[] { getClassTypeValue(typeClass, value) });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 通过class类型获取获取对应类型的值
     *
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }
    /**
     * 处理字符串 如： abc_dex ---&gt; abcDex
     *
     * @param str
     * @return
     */
    public static String removeLine(String str) {
        if (null != str && str.contains("_")) {
            int i = str.indexOf("_");
            char ch = str.charAt(i + 1);
            char newCh = (ch + "").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i + 1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }

 /*   public static void main(String[] args) {
        User user = new User();
        user.setUserId(1);
        user.setUserName("zhaohy");
        user.setSex(1);
        user.setJob("programmer");
        user.setEmail("1025802940@qq.com");

        System.out.println("通过反射遍历实体类属性，和值");
        //通过反射遍历实体类属性，和值
        Field[] fields = User.class.getDeclaredFields();
        for(Field field: fields) {
            //反射时让私有变量变成可访问
            field.setAccessible(true);
            try {
                System.out.println("实体类的属性名称：" + field.getName() + " 类型：" + field.getType() + " 属性的值：" + field.get(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("遍历实体类的方法名");
        //遍历实体类的方法名
        Method[] methods = User.class.getMethods();
        for(Method method:methods) {
            System.out.println("实体类的方法名：" + method.getName());
        }

        System.out.println("获取值方法二：通过拼接get方法来获取值打印get方法的值,注意如果是boolean类型需要做特殊处理");
        //获取值方法二：通过拼接get方法来获取值打印get方法的值,注意如果是boolean类型需要做特殊处理
        for(Field field: fields) {
            try {
                System.out.println("实体类的属性名称：" + field.getName() + " 类型：" + field.getType() + " 属性的值：" + getGetMethod(user, field.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("通过反射来给属性赋值方法一");
        //通过反射来给属性赋值方法一
        for(Field field: fields) {
            //反射时让私有变量变成可访问
            field.setAccessible(true);
            try {
                if(field.getName().equals("userName")) {
                    field.set(user, "zhaohyTest");
                }
                System.out.println("实体类的属性名称：" + field.getName() + " 类型：" + field.getType() + " 属性的值：" + field.get(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("通过反射来给属性赋值方法二，拼接setget方法的方式，注意如果是boolean类型需要做特殊处理");
        //通过反射来给属性赋值方法二,拼接setget方法的方式，注意如果是boolean类型需要做特殊处理
        for(Field field: fields) {
            try {
                if(field.getName().equals("userName")) {
                    setValue(user, User.class, field.getName(), field.getType(), "zhaohyTest2");
                }
                System.out.println("实体类的属性名称：" + field.getName() + " 类型：" + field.getType() + " 属性的值：" + getGetMethod(user, field.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }*/
}
