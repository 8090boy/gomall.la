package com.legendshop.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleSqlQuery;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test/applicationContext.xml")
public class JdbcTest {

	@Resource
	private BaseJdbcDao baseJdbcDao;
	
	@Test
	public void update(){
		int result = baseJdbcDao.update("delete from user where id = ?", 2);
		System.out.println("result = " + result);
	}
	
	@Test
	public void saveUser(){
		int total = 3000;
		System.out.println("JDBC开始插入数据库 total = " + total);
		long t1 = System.currentTimeMillis();
		
		for (int i = 0; i < total; i++) {
			User user = new User();
			user.setUsername("username" + i);
			user.setRealname("realname" + i);
			user.setPassword("password" + i);
			user.setMemo("memo" + i);
			
			baseJdbcDao.updateNamed("insert into user(username, realname, password, memo) "+
					         "values(:username, :realname, :password, :memo)", user);
		}
		long t2 = System.currentTimeMillis();
		System.out.println(total + "个数据插入数据库，一共用时 t2 - t1 = " + (t2 - t1));
		System.out.println("平均用时 (t2 - t1)/" + total +",  " + (t2 - t1) / total);
	}
	
	@Test
	public void queryUser(){
		long count = baseJdbcDao.stat("select count(*) from user");
		System.out.println("JDBC数据库中有 " + count + " 个用户数据");
		System.out.println("1. 内存为 = " + getMemery());
		User user = null;
		long total = count;
		long t1 = System.currentTimeMillis();
		for (int i = 1; i <= total; i++) {
			user = baseJdbcDao.get("select * from user where id = ?", User.class, i);
			//System.out.println("user" + user);
			if(user == null){
				throw new RuntimeException("user is null");
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("2. 内存为 = " + getMemery());
		System.out.println(  "查找" +total+ "个数据，一共用时 t2 - t1 = " + (t2 - t1));
		System.out.println("平均用时 (t2 - t1)/" + total +",  " + (float) (t2 - t1) / total);
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
	
	@Test
	public void queryUserByJDBC(){
		long count = baseJdbcDao.stat("select count(*) from user");
		System.out.println("JDBC数据库中有 " + count + " 个用户数据");
		System.out.println("1. 内存为 = " + getMemery());
		User user = null;
		long total = count;
		long t1 = System.currentTimeMillis();
		for (int i = 1; i <= total; i++) {
			user = baseJdbcDao.getJdbcTemplate().query("select * from user where id = ?", new Object[]{i}, new ResultSetExtractorImpl());
		//	user = baseJdbcDao.find("select password from user where id = ?", User.class, i);
		//	System.out.println("user " + user);
			if(user == null){
				throw new RuntimeException("user is null");
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("2. 内存为 = " + getMemery());
		System.out.println(  "查找" +total+ "个数据，一共用时 t2 - t1 = " + (t2 - t1));
		System.out.println("平均用时 (t2 - t1)/" + total +",  " +  (float)(t2 - t1) / total);
	}
	
	@Test
	public void queryUserByJDBC1(){
		long count = baseJdbcDao.stat("select count(*) from user");
		System.out.println("JDBC数据库中有 " + count + " 个用户数据");
		System.out.println("1. 内存为 = " + getMemery());
		User user = null;
		long total = count;
		long t1 = System.currentTimeMillis();
		for (int i = 1; i <= total; i++) {
			List<User> userList = baseJdbcDao.getJdbcTemplate().query("select * from user where id = ?", new Object[]{i}, new RowMapperUser());
			//System.out.println("userList size = " + userList.size());
		//	user = baseJdbcDao.find("select password from user where id = ?", User.class, i);
		//	System.out.println("user " + user);
			if(userList == null){
				throw new RuntimeException("user is null");
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("2. 内存为 = " + getMemery());
		System.out.println(  "查找" +total+ "个数据，一共用时 t2 - t1 = " + (t2 - t1));
		System.out.println("平均用时 (t2 - t1)/" + total +",  " +  (float)(t2 - t1) / total);
	}
	
	class RowMapperUser implements RowMapper<User>{
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setRealname(rs.getString("realname"));
			user.setPassword(rs.getString("password"));
			user.setMemo(rs.getString("memo"));
			return user;
		}
	}
	
  class ResultSetExtractorImpl implements ResultSetExtractor<User>{
	@Override
	public User extractData(ResultSet rs) throws SQLException, DataAccessException {
		User user = null;
		while(rs.next()){
			user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setRealname(rs.getString("realname"));
			user.setPassword(rs.getString("password"));
			user.setMemo(rs.getString("memo"));
		}
	

		return user;
	}
	  
  }
	
	@Test
	@SuppressWarnings("serial")
	public void batchUpdate(){
		List<User> list = new ArrayList<User>(){{
			add(new User(1, null, null, "changePassword", null));
			add(new User(2, null, null, "changePassword", null));
			add(new User(3, null, null, "changePassword", null));
		}};
		
		baseJdbcDao.updateNamed("update user set password = :password where id = :id", list);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void updateMap(){
		Map<String, Object> map = new HashMap<String, Object>(){{
			put("password", "changePawword");
			put("id", 110);	
		}};
		
		baseJdbcDao.updateNamedMap("update user set password = :password where id = :id", map);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void batchUpdateMap(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list.add(new HashMap<String, Object>() {{
			put("password", "changePawword110");
			put("id", 110);
		}});
		
		list.add(new HashMap<String, Object>() {{
			put("password", "changePawword3");
			put("id", 3);
		}});
		
		list.add(new HashMap<String, Object>() {{
			put("password", "changePawword2");
			put("id", 2);
		}});
		
		baseJdbcDao.updateNamedMap("update user set password = :password where id = :id", list);
	}
	
	@Test
	public void queryPage(){
		User user = baseJdbcDao.get("select * from user where id = ?", User.class, 110);
		System.out.println(user);
		
//		List<User> list = baseJdbcDao.query("select * from user", User.class);
//		for(User obj : list){
//		//	System.out.println(obj);
//		}
		System.out.println("------------");
		//long count = baseJdbcDao.stat("select count(*) from user");
		//PageHelper<User> result = baseJdbcDao.query("select * from user limit ?, ?", count, User.class, 1, 2);
		String queryString = "select * from user where id > ?";
		String allCountString = "select count(*) from user where id > ?";
		SimpleSqlQuery query = new SimpleSqlQuery(User.class, queryString, allCountString,new Object[]{100});
		query.setPageSize(15);
		query.setCurPage("3");
		PageSupport  ps = baseJdbcDao.find(query);
		List list2 = ps.getResultList();
		for (int i = 0; i < list2.size(); i++) {
			User u = (User)list2.get(i);
			System.out.println(u);
		}

	}
}
