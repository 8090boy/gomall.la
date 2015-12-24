package com.legendshop.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void addCookie(HttpServletResponse response, int age, String name, String value) {
		Cookie cookies = new Cookie(name, value);
		cookies.setPath("/");
		// cookies.setMaxAge(-1);//设置cookie经过多长秒后被删除。如果0，就说明立即删除。如果是负数就表明当浏览器关闭时自动删除。
		cookies.setMaxAge(age);
		response.addCookie(cookies);
	}

	public static void addCookie(HttpServletResponse response, String name, String value) {
		addCookie(response, -1, name, value);
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		if (cookieName != null) {
			Cookie cookie = getCookie(request, cookieName);
			if (cookie != null) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		if(request == null){
			return null;
		}
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		try {
			if (cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					if (cookie.getName().equals(cookieName)) {
						return cookie;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		if (cookieName != null) {
			Cookie cookie = getCookie(request, cookieName);
			if (cookie != null) {
				cookie.setMaxAge(0);// 如果0，就说明立即删除
				cookie.setPath("/");// 不要漏掉
				response.addCookie(cookie);
				return true;
			}
		}
		return false;
	}

}