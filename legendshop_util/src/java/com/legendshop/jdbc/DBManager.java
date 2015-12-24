package com.legendshop.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBManager
{
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(DBManager.class);
	private static DBManager instance = null;
	private String sDBDriver;
	private String sConnStr;
	private String User;
	private String Pass;

    private DBManager()
    {
		sDBDriver = Config.getInstance().getDbDriver();
		sConnStr = Config.getInstance().getDbConnectString();
		User = Config.getInstance().getDbUsername();
		Pass = Config.getInstance().getDbPasswd();
		if(sDBDriver == null || sConnStr == null || User == null || Pass ==null){
			throw new RuntimeException("missing JDBC information");
		}
		try {
			Class.forName(sDBDriver);
		} catch (ClassNotFoundException classnotfoundexception) {
			System.err.println("connectDB(): "
					+ classnotfoundexception.getMessage());
		}
    }

    public static DBManager getInstance()
    { 
    	if(instance==null)
    		instance = new DBManager();
        return instance;
    }
    
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(sConnStr, User, Pass);
	}
	
	public Connection getConnection(String sConnStr,String User,String Pass) throws SQLException {
		return DriverManager.getConnection(sConnStr, User, Pass);
	}
	
    public void cleanup(Connection conn, Statement ps, ResultSet rs)
    {
        try
        {
            if(rs != null)
            {
                rs.close();
                rs = null;
            }
        }
        catch(Exception e)
        {
            log.error("cleanup", e);
        }
        try
        {
            if(ps != null)
            {
                ps.close();
                ps = null;
            }
        }
        catch(Exception e)
        {
        	 log.error("close Statement", e);
        }
        try
        {
            if(conn != null && !conn.isClosed())
            {
                conn.close();
                conn = null;
            }
        }
        catch(Exception e)
        {
       	 log.error("close Connection", e);
        }
    }
    
    //just for sample to follow
    public ResultSet executeQuery(String sql, Object values[])
    {
        PreparedStatement st = null;
        Connection conn = null;
        ResultSet rs = null;
        try
        {
            conn = getConnection();
            st = conn.prepareStatement(sql);
            for(int i = 0; values != null && i < values.length; i++)
                st.setObject(i + 1, values[i]);

            rs = st.executeQuery();
            return rs;
        }
        catch(Exception se)
        {
        	 log.error("SQLException in DBManager.exceuteQuery, sql is :\r\n" + sql, 2);
        	 log.error("executeQuery", se);
        }
        finally
        {
            cleanup(conn, st, rs);
        }
        return rs;
    }

 

}
