/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 * 
 */
public final class BeanHelper extends BeanUtils {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(BeanHelper.class);

	/** The Constant sdf. */
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/** The Constant ldf. */
	private static final DateFormat ldf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** The Constant nf. */
	private static final NumberFormat nf = NumberFormat.getInstance();

	static {
		nf.setMinimumIntegerDigits(1);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}

	/**
	 * 日期解析.
	 * 
	 * @param date
	 *            the date
	 * @return the date
	 */
	public static Date parseDate(String date) {
		if (date == null || date.trim().equals("")) {
			return null;
		}
		Date d = null;
		try {
			// if(date.length() > 10){
			// d = ldf.parse(date);
			// }else{
			// d = sdf.parse(date);
			// }
			d = sdf.parse(date);
		} catch (Exception ex) {
			throw new RuntimeException("错误的日期格式 : " + date);
		}
		return d;
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return null;
		String d = null;
		try {
			d = sdf.format(date);
		} catch (Exception ex) {
			throw new RuntimeException("无法解析的日期 : " + date);
		}
		return d;
	}

	/**
	 * 复制Bean属性,根据要进行拷贝的源和目标的数据类型自动完成数据转换.
	 * 
	 * @param dest
	 *            目标对象
	 * @param src
	 *            源对象
	 * @param ignoreNull
	 *            是否忽略空的属性
	 */
	public static void copyProperties(Object dest, Object src, boolean ignoreNull) {
		try {
			Set fromPropertys = BeanUtils.describe(src).keySet();
			Set toPropertys = BeanUtils.describe(dest).keySet();
			for (Object oPropertyName : fromPropertys) {
				String propertyName = oPropertyName.toString();
				if (!propertyName.equals("class")) {
					if (toPropertys.contains(propertyName)) {
						Object valueToBeCopied = PropertyUtils.getNestedProperty(src, propertyName);
						if (valueToBeCopied == null && ignoreNull)
							continue;
						Class srcDataType = PropertyUtils.getPropertyDescriptor(src, propertyName).getPropertyType();
						Class destDataType = PropertyUtils.getPropertyDescriptor(dest, propertyName).getPropertyType();
						if (srcDataType == destDataType) {// 源类型和目标类型一致,不需转换,直接复制
							PropertyUtils.setNestedProperty(dest, propertyName, valueToBeCopied);
							continue;
						}
						if (srcDataType == String.class) {// 源类型为字符串
							valueToBeCopied = ConvertUtils.convert((String) valueToBeCopied, destDataType);
						} else if (destDataType == String.class) {// 目标类型为字符串
							valueToBeCopied = ConvertUtils.convert(valueToBeCopied);
						}
						if (valueToBeCopied != null && destDataType == valueToBeCopied.getClass()) {
							PropertyUtils.setNestedProperty(dest, propertyName, valueToBeCopied);
						} else {
							log.warn("源数据类型[" + srcDataType.getName() + "]和目标数据类型[" + destDataType.getName() + "]不匹配, 忽略此次赋值");
						}

					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("复制属性出错 ...", e);
		}
	}

	/**
	 * 复制属性,忽略空的属性.
	 * 
	 * @param dest
	 *            the dest
	 * @param orig
	 *            the orig
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
		copyProperties(dest, orig, true);
	}

	/**
	 * 从字符串获取一个Integer.
	 * 
	 * @param src
	 *            the src
	 * @param defaultValue
	 *            the default value
	 * @return the int
	 */
	public static Integer getInt(String src, Integer defaultValue) {
		if (src == null || src.trim().equals("")) {
			return defaultValue;
		} else {
			Integer result = null;
			try {
				result = Integer.valueOf(src);
			} catch (Exception e) {
				result = defaultValue;

			}
			return result;
		}
	}

	/**
	 * 从字符串获取一个Long.
	 * 
	 * @param src
	 *            the src
	 * @param defaultValue
	 *            the default value
	 * @return the long
	 */
	public static Long getLong(String src, Long defaultValue) {
		if (src == null || src.trim().equals("")) {
			return defaultValue;
		} else {
			Long result = null;
			try {
				result = Long.valueOf(src);
			} catch (Exception e) {
				result = defaultValue;

			}
			return result;
		}
	}

	/**
	 * 从字符串获取一个Float.
	 * 
	 * @param src
	 *            the src
	 * @param defaultValue
	 *            the default value
	 * @return the float
	 */
	public static Float getFloat(String src, Float defaultValue) {
		if (src == null || src.trim().equals("")) {
			return defaultValue;
		} else {
			Float result = null;
			try {
				result = Float.valueOf(src);
			} catch (Exception e) {
				result = defaultValue;

			}
			return result;
		}
	}

	/**
	 * 从字符串获取一个Double.
	 * 
	 * @param src
	 *            the src
	 * @param defaultValue
	 *            the default value
	 * @return the double
	 */
	public static Double getDouble(String src, Double defaultValue) {
		if (src == null || src.trim().equals("")) {
			return defaultValue;
		} else {
			Double result = null;
			try {
				result = Double.valueOf(src);
			} catch (Exception e) {
				result = defaultValue;
			}
			return result;
		}
	}

	/**
	 * 获取一个整数,默认值为 0.
	 * 
	 * @param src
	 *            the src
	 * @return the int
	 */
	public static Integer getInt(String src) {
		return getInt(src, 0);
	}

	/**
	 * 获取一个Long,默认值为 0.
	 * 
	 * @param src
	 *            the src
	 * @return the long
	 */
	public static Long getLong(String src) {
		return getLong(src, 0L);
	}

	/**
	 * 获取一个Float,默认值为 0.
	 * 
	 * @param src
	 *            the src
	 * @return the float
	 */
	public static Float getFloat(String src) {
		return getFloat(src, 0.0F);
	}

	/**
	 * 获取一个Double,默认值为 0.
	 * 
	 * @param src
	 *            the src
	 * @return the double
	 */
	public static Double getDouble(String src) {
		return getDouble(src, 0.0);
	}

	/**
	 * 通过反射分行查看对象的属性和属性值.
	 * 
	 * @param obj
	 *            the obj
	 * @return the string
	 */
	public static String describeAsString(Object obj) {
		return ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * 获取非空字符串.
	 * 
	 * @param src
	 *            the src
	 * @return the not null string
	 */
	public static String getNotNullString(Object src) {
		return (src == null) ? "" : String.valueOf(src);
	}

	/**
	 * 获取bean中的属性.
	 * 
	 * @param bean
	 *            the bean
	 * @param propertyName
	 *            the property name
	 * @return the bean property
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 */
	public static Object getBeanProperty(Object bean, String propertyName) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return PropertyUtils.getProperty(bean, propertyName);
	}

	/**
	 * Gets the double by object.
	 * 
	 * @param obj
	 *            the obj
	 * @return the double by object
	 */
	public static Double getDoubleByObject(Object obj) {
		if (obj == null) {
			return 0.0;
		} else {
			Double result = null;
			try {
				result = Double.valueOf(String.valueOf(obj));
			} catch (Exception ex) {
				result = 0.0;
			}
			return result;
		}
	}

	/**
	 * Gets the big decimal by object.
	 * 
	 * @param obj
	 *            the obj
	 * @return the big decimal by object
	 */
	public static BigDecimal getBigDecimalByObject(Object obj) {
		if (obj == null) {
			return BigDecimal.valueOf(0.0);
		} else {
			BigDecimal result = null;
			try {
				result = new BigDecimal(String.valueOf(obj));
			} catch (Exception ex) {
				result = BigDecimal.valueOf(0.0);
				;
			}
			return result;
		}
	}

	/**
	 * 将对象的属性放置到Map中.
	 * 
	 * @param src
	 *            the src
	 * @return the map
	 * @throws Exception
	 *             the exception
	 */
	public static Map Object2Map(Object src) throws Exception {
		return describe(src);
	}

	/**
	 * 将 Map 中和 target 的同名属性复制到 target 对象中.
	 * 
	 * @param map
	 *            the map
	 * @param target
	 *            the target
	 * @throws Exception
	 *             the exception
	 */
	public static void Map2Object(Map map, Object target) throws Exception {
		populate(target, map);
	}

	/**
	 * 格式化数字.
	 * 
	 * @param number
	 *            the number
	 * @return the string
	 */
	public static String formatNumber(Object number) {
		if (number != null || number instanceof Number) {
			return nf.format(number);
		}
		return "";
	}

	/**
	 * 将对象转换成对应的字符串表示.
	 * 
	 * @param value
	 *            the value
	 * @return the object
	 */
	public static Object converToString(Object value) {
		if (value == null)
			return null;
		if (value instanceof String)
			return value;
		if (value instanceof Date) {
			return formatDate((Date) value);
		} else if (value instanceof Double) {
			return formatNumber(value);
		} else {
			return String.valueOf(value);
		}
	}

	/**
	 * Object2 object.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param source
	 *            the source
	 * @return the object
	 */
	public static Object Object2Object(Class clazz, Object source) {
		try {
			Object targetInstance = clazz.newInstance();
			return Object2Object(targetInstance, source);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象source中的同名属性复制到target中.
	 * 
	 * @param target
	 *            the target
	 * @param source
	 *            the source
	 * @return the object
	 */
	public static Object Object2Object(Object target, Object source) {
		try {
			Method[] s_method = source.getClass().getDeclaredMethods();
			// get value from source
			List valueList = new ArrayList();
			for (int i1 = 0; i1 < s_method.length; i1++) {
				String str = s_method[i1].getName();
				if (str.indexOf("get") == 0) {
					String fieldname = getFieldName(str);
					Object value1 = s_method[i1].invoke(source, new Object[0]);
					ObjectParam op = new ObjectParam();
					op.setFieldname(fieldname);
					op.setFieldvalue(value1);
					valueList.add(op);
				}
			}

			Method[] t_method = target.getClass().getDeclaredMethods();
			for (int i1 = 0; i1 < t_method.length; i1++) {
				String str = t_method[i1].getName();
				if (str.indexOf("set") == 0) {
					String fieldname = getFieldName(str);
					Object value = null;
					for (int i = 0; i < valueList.size(); i++) {
						ObjectParam op = (ObjectParam) valueList.get(i);
						String fname = op.getFieldname();
						if (fieldname.equals(fname)) {
							value = op.getFieldvalue();
							t_method[i1].invoke(target, new Object[] { value });
							break;
						}
					}

				}
			}
			return target;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Filter the specified string for characters that are sensitive to HTML
	 * interpreters, returning the string with these characters replaced by the
	 * corresponding character entities.
	 * 
	 * @param value
	 *            The string to be filtered and returned
	 * @return the string
	 */
	public static String filter(String value) {

		if (value == null || value.length() == 0) {
			return value;
		}

		StringBuffer result = null;
		String filtered = null;
		for (int i = 0; i < value.length(); i++) {
			filtered = null;
			switch (value.charAt(i)) {
			case '<':
				filtered = "&lt;";
				break;
			case '>':
				filtered = "&gt;";
				break;
			}

			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);
					if (i > 0) {
						result.append(value.substring(0, i));
					}
					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}
		return result == null ? value : result.toString();
	}

	/**
	 * Gets the field name.
	 * 
	 * @param fieldname
	 *            the fieldname
	 * @return the field name
	 */
	private static String getFieldName(String fieldname) {
		if (fieldname == null || "".equals(fieldname))
			return null;
		else {
			return fieldname.substring(3).toLowerCase();// Character.toLowerCase().toUpperCase(fieldname.charAt(2))+fieldname.substring(3);
		}
	}

	/**
	 * The Class ObjectParam.
	 */
	static class ObjectParam {

		/** The fieldname. */
		public String fieldname;

		/** The fieldvalue. */
		public Object fieldvalue;

		/**
		 * Gets the fieldname.
		 * 
		 * @return the fieldname
		 */
		public String getFieldname() {
			return fieldname;
		}

		/**
		 * Sets the fieldname.
		 * 
		 * @param fieldname
		 *            the new fieldname
		 */
		public void setFieldname(String fieldname) {
			this.fieldname = fieldname;
		}

		/**
		 * Gets the fieldvalue.
		 * 
		 * @return the fieldvalue
		 */
		public Object getFieldvalue() {
			return fieldvalue;
		}

		/**
		 * Sets the fieldvalue.
		 * 
		 * @param fieldvalue
		 *            the new fieldvalue
		 */
		public void setFieldvalue(Object fieldvalue) {
			this.fieldvalue = fieldvalue;
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @param object
	 *            the object
	 * @param propertyName
	 *            the property name
	 * @return the declared field
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param propertyName
	 *            the property name
	 * @return the declared field
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}

	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @param object
	 *            the object
	 * @param propertyName
	 *            the property name
	 * @return the object
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			log.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @param object
	 *            the object
	 * @param propertyName
	 *            the property name
	 * @param newValue
	 *            the new value
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			log.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	/**
	 * 暴力调用对象函数,忽略private,protected修饰符的限制.
	 * 
	 * @param object
	 *            the object
	 * @param methodName
	 *            the method name
	 * @param params
	 *            the params
	 * @return the object
	 * @throws NoSuchMethodException
	 *             如果没有该Method时抛出.
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				// 方法不在当前类定义,继续向上转型
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * 按Filed的类型取得Field列表.
	 * 
	 * @param object
	 *            the object
	 * @param type
	 *            the type
	 * @return the fields by type
	 */
	public static List<Field> getFieldsByType(Object object, Class type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 按FiledName获得Field的类型.
	 * 
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @return the property type
	 * @throws NoSuchFieldException
	 *             the no such field exception
	 */
	public static Class getPropertyType(Class type, String name) throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	/**
	 * 获得field的getter函数名称.
	 * 
	 * @param type
	 *            the type
	 * @param fieldName
	 *            the field name
	 * @return the getter name
	 */
	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * 获得field的getter函数,如果找不到该方法,返回null.
	 * 
	 * @param type
	 *            the type
	 * @param fieldName
	 *            the field name
	 * @return the getter method
	 */
	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
