package com.legendshop.test;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLServerConnectionTest {
		public static void main(String[] srg) {
		  String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //加载JDBC驱动
		  String dbURL = "jdbc:sqlserver://192.168.2.7:1433; DatabaseName=legendshop3";  //连接服务器和数据库
		  String userName = "legendshop";  //默认用户名
		  String userPwd = "legendshop";  //密码
		  Connection dbConn;

		  try {
		   Class.forName(driverName);
		   dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
		   System.out.println("Connection Successful!");  //如果连接成功 控制台输出Connection Successful!
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		}
		}