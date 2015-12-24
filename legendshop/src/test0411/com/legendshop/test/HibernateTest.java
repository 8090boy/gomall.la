package com.legendshop.test;

import java.text.NumberFormat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test/applicationContextHibernate.xml")
public class HibernateTest {

	@Resource
	private HibernateDaoImpl hibernateDao;
	
	
	@Test
	public void saveUser(){
		int total = 1000;
		long t1 = System.currentTimeMillis();
		System.out.println("开始插入数据库 total = " + total);
		for (int i = 0; i < total; i++) {
			User user = new User();
			user.setUsername("username" + i);
			user.setRealname("realname" + i);
			user.setPassword("password" + i);
			user.setMemo("memo" + i);
			hibernateDao.save(user);
		}
		long t2 = System.currentTimeMillis();
		System.out.println(total + "个数据插入数据库，一共用时 t2 - t1 = " + (t2 - t1));
		System.out.println("平均用时 (t2 - t1)/" + total +",  " + (t2 - t1) / total);
	}
	
	@Test
	public void queryUser(){
		long count = hibernateDao.stat("select count(*) from user");
		System.out.println("Hibernate: 数据库中有 " + count + " 个用户数据");
		System.out.println("1. 内存为 = " + getMemery());
		User user = null;
		long total = count;
		long t1 = System.currentTimeMillis();
		for (long i = 1; i <= total; i++) {
			
			user = hibernateDao.findUser(i);
			//System.out.println("user" + user);
			if(user == null){
				throw new RuntimeException("user is null");
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("2. 内存为 = " + getMemery());
		System.out.println(  "查找" +total+ "个数据，一共用时 t2 - t1 = " + (t2 - t1));
		System.out.println("平均用时 (t2 - t1)/" + total +",  " + (float)(t2 - t1) / total);
	}
	
	public String getMemery(){
		Runtime runtime = Runtime.getRuntime(); 

		NumberFormat format = NumberFormat.getInstance(); 

		StringBuilder sb = new StringBuilder(); 
		long maxMemory = runtime.maxMemory(); 
		long allocatedMemory = runtime.totalMemory(); 
		long freeMemory = runtime.freeMemory(); 

		sb.append("free memory: " + format.format(freeMemory / 1024 /1024) + "<br/>");  
		sb.append("allocated memory: " + format.format(allocatedMemory / 1024/1024) + "<br/>"); 
		sb.append("max memory: " + format.format(maxMemory / 1024/1024) + "<br/>"); 
		sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory))/1024/1024));
		return sb.toString();
	}
}
