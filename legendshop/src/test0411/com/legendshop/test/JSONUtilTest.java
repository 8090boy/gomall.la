package com.legendshop.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.legendshop.model.entity.Province;
import com.legendshop.util.JSONUtil;

public class JSONUtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Province province = new Province();
		province.setId(1);
		province.setProvince("province");
		province.setProvinceid("provinceid");
		String json1 = JSONUtil.getJson(province);
		System.out.println("1. "+ json1);
		Map  map = JSONUtil.getMap(json1);
		Province  result = JSONUtil.getObject(json1,Province.class);
		System.out.println("1. 1"+ map);
		System.out.println("1. 2"+ result);
		List<Province> list = new ArrayList<Province>();
		list.add(province);
		String json2 = JSONUtil.getJson(list);
		System.out.println("2. "+  json2);
		List<Province> list2 = JSONUtil.getArray(json2, Province.class);
		System.out.println("2. 2 "+  list2);

	}

}
