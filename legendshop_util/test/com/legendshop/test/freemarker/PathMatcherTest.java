package com.legendshop.test.freemarker;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class PathMatcherTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PathMatcher pathMatcher =  new AntPathMatcher();
		boolean result = pathMatcher.match("admin/**/*.html", "admin/sdfsf.html");
		System.out.println("result = " + result);
	}

}
