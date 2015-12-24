/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class TimerUtil {

	/** The Constant logger. */
	private final static Logger logger = Logger.getRootLogger();

	/** The time type. */
	private static String timeType = "yyyy-MM-dd";

	/** The TIM e_ min. */
	public static String TIME_MIN = "MIN";

	/** The TIM e_ hour. */
	public static String TIME_HOUR = "HOUR";

	/** The TIM e_ day. */
	public static String TIME_DAY = "DAY";

	/** The TIM e_ month. */
	public static String TIME_MONTH = "MONTH";

	/** The TIM e_ year. */
	public static String TIME_YEAR = "YEAR";

	/** The MI d_ dat a_ format. */
	public static String MID_DATA_FORMAT;

	/** The calendar. */
	public static Calendar calendar = new GregorianCalendar();

	/** The date format. */
	public static DateFormat dateFormat;

	/** The JAV a_ dat e_ format. */
	public static String JAVA_DATE_FORMAT = "yyyy:MM:dd HH:mm:ss";

	/** The ORACL e_ dat e_ format. */
	public static String ORACLE_DATE_FORMAT = "YYYY:MM:DD HH24:mi:ss";

	/** The MSSQLSERVE r_ dat e_ format. */
	public static String MSSQLSERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	static {
		MID_DATA_FORMAT = "yyyy-MM-dd";
		dateFormat = new SimpleDateFormat(MID_DATA_FORMAT);
	}

	/**
	 * Instantiates a new timer util.
	 */
	public TimerUtil() {
	}

	/**
	 * Instantiates a new timer util.
	 * 
	 * @param timeType
	 *            the time type
	 */
	public TimerUtil(String timeType) {
		this.timeType = timeType;
	}

	/**
	 * 得到现有 String 型的日期格式.
	 * 
	 * @return the str current date
	 */
	public String getStrCurrentDate() {
		String strTime = null;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(timeType);
			strTime = simpledateformat.format(new Date());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return strTime;
	}

	/**
	 * 得到长时间格式的long 1970.
	 * 
	 * @param time
	 *            the time
	 * @return the time to long
	 */
	public long getTimeToLong(String time) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(timeType);
			ParsePosition pos = new ParsePosition(0);
			date = formatter.parse(time, pos);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return date.getTime();
	}

	/**
	 * 得到现有的时间.
	 * 
	 * @return the current date
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * Gets the now date short.
	 * 
	 * @return the now date short
	 */
	public static Date getNowDateShort() {
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String s = simpledateformat.format(date);
		ParsePosition parseposition = new ParsePosition(0);
		Date date1 = simpledateformat.parse(s, parseposition);
		return date1;
	}

	/**
	 * 得到String长型现在的时间.
	 * 
	 * @return the str date
	 */
	public static String getStrDate() {
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 得到String短型现在的时间.
	 * 
	 * @return the str date short
	 */
	public static String getStrDateShort() {
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 根据String得到Date型现在的时间.
	 * 
	 * @param s
	 *            the s
	 * @return the date
	 */
	public static Date strToDate(String s) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition parseposition = new ParsePosition(0);
		Date date = simpledateformat.parse(s, parseposition);
		return date;
	}

	/**
	 * 根据Date得到String型现在的时间.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String dateToStr(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 根据Date得到String型现在的时间.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String dateToStrShort(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 根据Date得到String型现在的时间.
	 * 
	 * @param s
	 *            the s
	 * @return the date
	 */
	public static Date strToDateShort(String s) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition parseposition = new ParsePosition(0);
		Date date = simpledateformat.parse(s, parseposition);
		return date;
	}

	/**
	 * Gets the last date.
	 * 
	 * @param l
	 *            the l
	 * @return the last date
	 */
	public static Date getLastDate(long l) {
		Date date = new Date();
		long l1 = date.getTime() - 0x74bad00L * l;
		Date date1 = new Date(l1);
		return date1;
	}

	/**
	 * 计算日期.
	 * 
	 * @param date
	 *            the date
	 * @param integer
	 *            the integer
	 * @param s
	 *            the s
	 * @return the date
	 */
	public static Date getDate(Date date, Integer integer, String s) {
		if (integer != null)
			return getDate(date, integer.intValue(), s);
		else
			return date;
	}

	/**
	 * 计算日期.
	 * 
	 * @param date
	 *            the date
	 * @param i
	 *            the i
	 * @param s
	 *            the s
	 * @return the date
	 */
	public static Date getDate(Date date, int i, String s) {
		calendar.setTime(date);
		if (s.equalsIgnoreCase(TIME_MIN))
			calendar.add(12, i);
		else if (s.equalsIgnoreCase(TIME_HOUR))
			calendar.add(11, i);
		else if (s.equalsIgnoreCase(TIME_DAY))
			calendar.add(5, i);
		else if (s.equalsIgnoreCase(TIME_MONTH))
			calendar.add(2, i);
		else if (s.equalsIgnoreCase(TIME_YEAR))
			calendar.add(1, i);
		return calendar.getTime();
	}

	/**
	 * Gets the oracle date str.
	 * 
	 * @param date
	 *            the date
	 * @return the oracle date str
	 */
	public static String getOracleDateStr(Date date) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(JAVA_DATE_FORMAT);
			String s = simpledateformat.format(date);
			return s;
		}
	}

	/**
	 * Gets the sql server date str.
	 * 
	 * @param date
	 *            the date
	 * @return the sql server date str
	 */
	public static String getSqlServerDateStr(Date date) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(MSSQLSERVER_DATE_FORMAT);
			String s = simpledateformat.format(date);
			return s;
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		TimerUtil timerUtil = new TimerUtil("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date date2 = getCurrentDate();
		Date date3 = TimerUtil.strToDateShort("2006-12-12");
		long nowLong = date3.getTime();
		System.out.println("getNowDay==" + timerUtil);
		System.out.println("nowLong==" + nowLong);
		System.out.println("date   ==" + TimerUtil.dateToStr(date));
		System.out.println("date111==" + TimerUtil.dateToStr(getDate(date, 100, TimerUtil.TIME_YEAR)));
		
		System.out.println("-----------------");
		Date today = TimerUtil.getNowDateShort();
		System.out.println(" today = " + today);
		Date startDate = TimerUtil.strToDateShort("2013-09-16");
		Date endDate = TimerUtil.strToDateShort("2013-09-17");
		System.out.println("Start Date = " + startDate + ", endDate = " + endDate);
		System.out.println(" startDate.after(today) = " + startDate.after(today));
		System.out.println("endDate.before(today) = " + endDate.before(today));
	}
}
