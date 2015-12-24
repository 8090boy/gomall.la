/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class TimestampConverter extends DateConverter {

	/** The Constant TS_FORMAT. */
	public static final String TS_FORMAT = ConvertDateUtil.getDatePattern() + " HH:mm:ss.S";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.util.converter.DateConverter#convertToDate(java.lang.Class
	 * , java.lang.Object)
	 */
	protected Object convertToDate(Class type, Object value) {
		DateFormat df = new SimpleDateFormat(TS_FORMAT);
		if (value instanceof String) {
			try {
				if (StringUtils.isEmpty(value.toString())) {
					return null;
				}

				return df.parse((String) value);
			} catch (Exception pe) {
				throw new ConversionException("Error converting String to Timestamp");
			}
		}

		throw new ConversionException("Could not convert " + value.getClass().getName() + " to " + type.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.util.converter.DateConverter#convertToString(java.lang
	 * .Class, java.lang.Object)
	 */
	protected Object convertToString(Class type, Object value) {
		DateFormat df = new SimpleDateFormat(TS_FORMAT);
		if (value instanceof Date) {
			try {
				return df.format(value);
			} catch (Exception e) {
				throw new ConversionException("Error converting Timestamp to String");
			}
		}

		return value.toString();
	}
}