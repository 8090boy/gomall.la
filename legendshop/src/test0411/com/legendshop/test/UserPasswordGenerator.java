/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.test;

import com.legendshop.util.MD5Util;

/**
 * The Class PasswordMaker.
 * DB password encrypt: DatasourcePropertiesFactory
 */
public class UserPasswordGenerator {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		String userName = "root";
		String password = "root";
		System.out.println("User Name is " + userName);
		System.out.println("Password is " + password);
		System.out.println("Encrypted Password is " + MD5Util.Md5Password(userName, password));
	}
}
