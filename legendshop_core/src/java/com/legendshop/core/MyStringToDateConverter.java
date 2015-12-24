package com.legendshop.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.legendshop.util.AppUtils;

public class MyStringToDateConverter implements Converter<String, Date> {
	public Date convert(String source) {
		if (AppUtils.isBlank(source)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}