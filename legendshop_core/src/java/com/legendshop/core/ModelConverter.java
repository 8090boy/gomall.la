package com.legendshop.core;

import org.springframework.core.convert.converter.Converter;

import com.legendshop.model.dynamic.Model;
import com.legendshop.util.AppUtils;

public class ModelConverter implements Converter<String, Model[]> {
	public Model[] convert(String source) {
		if (AppUtils.isBlank(source)) {
			return null;
		}
		System.out.println("input source  =" + source);

		return null;
	}
}