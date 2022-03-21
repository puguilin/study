package com.guilin.studycode.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public final class BeanUtils {

	public BeanUtils() {

	}

	/**
	 *
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map beanToMap(final Object object) {
		return new BeanMap(object);
	}

	public static <T> T convertBean(Object object, Class<T> entityClass) {
		if (null == object) {
			return null;
		}
		return JSON.parseObject(JSON.toJSONString(object), entityClass);
	}

	/**
	 * 方法说明：对象转化为Map
	 * 
	 * @param object
	 * @return
	 */
	public static Map<?, ?> objectToMap(Object object) {
		return convertBean(object, Map.class);
	}

	/**
	 * @param <T>
	 * @param t1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T t1) {
		try {
			return (T) org.apache.commons.beanutils.BeanUtils.cloneBean(t1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 数据拷贝
	 * 
	 * @param target
	 *            目标变量
	 * @param source
	 *            值
	 * @return
	 */
	public static <T> T copyProperty(T target, Object source) {
		try {
			if (source == null) {
				return null;
			}
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
			return (T) target;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用对象source中的数据初始化一个新对象
	 * 
	 * @param target
	 *            目标对象类型
	 * @param source
	 *            源对象
	 * @return 目标对象
	 */
	public static <T> T getNewObj(Class<T> target, Object source) {
		if (source == null) {
			return null;
		}
		T obj1;
		try {
			obj1 = (T) target.newInstance();
			copyProperty(obj1, source);
			return obj1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 拷贝List成员为一个新成员，要求源List满足泛型条件，目标List满足泛型条件
	 * 
	 * @param target
	 *            目标List中成员类型
	 * @param source
	 *            源List
	 * @return 满足target类型的目标List
	 */
	public static <T> List<T> getNewListByAnnother(Class<T> target, List<?> source) {
		List<T> resultList = new ArrayList<T>();
		if (target == null) {
			return resultList;
		}
		resultList = source.stream().map(x -> {
			T o = null;
			try {
				o = (T) target.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return copyProperty(o, x);
		}).collect(Collectors.toList());
		return resultList;
	}

	/*----------------------------lin_wei  start  复制实体时有null不会复制-------------------------------------*/
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static void copyPropertiesIgnoreNull(Object src, Object target) {
		org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
	/*----------------------------lin_wei  end  复制实体时有null不会复制-------------------------------------*/

	/**
	 * 获取所有子类属性
	 * 
	 * @param dest
	 *            src
	 * @return
	 */
	public static Field[] getFields(Class<?> dest, Class<?> src) {
		Field[] destField = null;
		List<Field> list = new ArrayList<Field>();
		Class<? extends Object> destClass = dest;
		while (destClass != null && !destClass.equals(src)) {
			Field[] declaredFields = destClass.getDeclaredFields();
			for (Field field : declaredFields) {
				list.add(field);
			}
			destClass = destClass.getSuperclass();
		}
		destField = list.toArray(new Field[list.size()]);
		return destField;
	}

}
