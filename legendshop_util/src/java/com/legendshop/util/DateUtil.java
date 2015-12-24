/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class DateUtil {

	/** The Constant CM_LONG_DATE_FORMAT. */
	public static final String CM_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** The Constant CM_SHORT_DATE_FORMAT. */
	public static final String CM_SHORT_DATE_FORMAT = "yyyyMMdd";

	/** The Constant CM_SHORT_MONTH_FORMAT. */
	public static final String CM_SHORT_MONTH_FORMAT = "yyyy-MM";

	/** The Constant CM_SHORT_YEAR_FORMAT. */
	public static final String CM_SHORT_YEAR_FORMAT = "yyyy";

	/** The Constant YEAR_MONTH. */
	public static final String YEAR_MONTH = "yyyyMM";

	/** The Constant MONTH. */
	public final static String[] MONTH = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	/** The Constant DAY. */
	public final static String[] DAY = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

	/** The date format. */
	public static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

	/**
	 * 取得今天的日期.
	 * 
	 * @return the today
	 */
	public static String getToday() {
		Date myDate = new Date();
		String today = DateUtil.DateToString(myDate, CM_SHORT_DATE_FORMAT);
		return today;
	}

	/**
	 * 取得今天的日期.
	 * 
	 * @return timeformat
	 */
	public static long getTodayInTimeFormat() {
		Date myDate = new Date();
		long today = myDate.getTime();
		return today;
	}

	/**
	 * 取得今年年份.
	 * 
	 * @return the now year
	 */
	public static String getNowYear() {
		Date myDate = new Date();
		String nowYear = DateUtil.DateToString(myDate, CM_SHORT_YEAR_FORMAT);
		return nowYear;
	}

	/**
	 * 获得当前时间.
	 * 
	 * @return String
	 */
	public static java.sql.Timestamp getNowTime() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * 取得当月的月份.
	 * 
	 * @return the month
	 */
	public static String getMonth() {
		Date myDate = new Date();
		String month = DateUtil.DateToString(myDate, CM_SHORT_MONTH_FORMAT);
		return month;
	}

	/**
	 * 取得当月的年月.
	 * 
	 * @param ymFormat
	 *            the ym format
	 * @return the month
	 */
	public static String getMonth(String ymFormat) {
		Date myDate = new Date();
		String month = DateUtil.DateToString(myDate, ymFormat);
		return month;
	}

	/**
	 * 取得下月的月份,形式如yyyy-MM.
	 * 
	 * @return the next month
	 */
	public static String getNextMonth() {
		Date myDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.MONTH, 1);

		String nextmonth = DateUtil.DateToString(cal.getTime(), CM_SHORT_MONTH_FORMAT);
		return nextmonth;
	}

	/**
	 * 取得下月的月份,形式如y.
	 * 
	 * @param ymFormat
	 *            格式如:yyyyMM
	 * @return the next month
	 */
	public static String getNextMonth(String ymFormat) {
		Date myDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.MONTH, 1);

		String nextmonth = DateUtil.DateToString(cal.getTime(), ymFormat);
		return nextmonth;
	}

	/**
	 * Gets the month date.
	 * 
	 * @param myDate
	 *            the my date
	 * @param month
	 *            the month
	 * @return the month date
	 */
	public static java.sql.Date getMonthDate(Date myDate, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.MONTH, month);
		java.util.Date newDate = cal.getTime();
		return new java.sql.Date(newDate.getTime());
	}

	/**
	 * 取得上月的月份.
	 * 
	 * @return the up month
	 */
	public static String getUpMonth() {
		Date myDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.MONTH, -1);

		String nextmonth = DateUtil.DateToString(cal.getTime(), CM_SHORT_MONTH_FORMAT);
		return nextmonth;
	}

	/**
	 * 取得参数指定年月的上个月.
	 * 
	 * @param year
	 *            指定年
	 * @param month
	 *            指定月
	 * @param format
	 *            指定格式 如: "yyyyMMdd"
	 * @return the up month
	 */
	public static String getUpMonth(String year, String month, String format) {
		Date myDate = DateUtil.getDate(year, month, "01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.MONTH, -1);

		String nextmonth = DateUtil.DateToString(cal.getTime(), format);
		return nextmonth;
	}

	/**
	 * 取得参数指定年月的下个月.
	 * 
	 * @param year
	 *            指定年
	 * @param month
	 *            指定月
	 * @param format
	 *            指定格式 如: "yyyyMMdd"
	 * @return the next month
	 */
	public static String getNextMonth(String year, String month, String format) {
		Date myDate = DateUtil.getDate(year, month, "01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.MONTH, 1);

		String nextmonth = DateUtil.DateToString(cal.getTime(), format);
		return nextmonth;
	}

	/**
	 * 取得从某一时间开始的一段年份.
	 * 
	 * @param currdate
	 *            Date
	 * @param len
	 *            int
	 * @return List
	 */
	public static List getYear(Date currdate, int len) {
		List lists = new ArrayList();
		Calendar cal = Calendar.getInstance();
		int ln = Math.abs(len);
		if (len >= 0) {
			for (int i = 0; i < len; i++) {
				cal.setTime(currdate);
				cal.add(Calendar.YEAR, i);

				String year = DateUtil.DateToString(cal.getTime(), CM_SHORT_YEAR_FORMAT);
				lists.add(year);
			}
		} else {
			for (int i = 1; i <= ln; i++) {
				cal.setTime(currdate);
				cal.add(Calendar.YEAR, -i);
				String year = DateUtil.DateToString(cal.getTime(), CM_SHORT_YEAR_FORMAT);
				lists.add(year);
			}

		}
		return lists;
	}

	/**
	 * 取得明日的日期.
	 * 
	 * @return the tomorrow
	 */
	public static String getTomorrow() {
		Date myDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, 1);
		String tomorrow = DateUtil.DateToString(cal.getTime(), CM_SHORT_DATE_FORMAT);
		return tomorrow;
	}

	/**
	 * 取得后天的日期.
	 * 
	 * @return the day after tomorrow
	 */
	public static String getDayAfterTomorrow() {
		Date myDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, 2);
		String tomorrow = DateUtil.DateToString(cal.getTime(), CM_SHORT_DATE_FORMAT);
		return tomorrow;
	}

	/**
	 * 取得昨日的日期.
	 * 
	 * @return the yesterday
	 */
	public static String getYesterday() {
		Date myDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, -1);
		String yesterday = DateUtil.DateToString(cal.getTime(), CM_SHORT_DATE_FORMAT);
		return yesterday;
	}

	/**
	 * 取得日期的完整打印格式.
	 * 
	 * @param date
	 *            the date
	 * @return the full date string
	 */
	public static String getFullDateString(String date) {
		Date myDate = DateUtil.StringToDate(date);
		return dateFormat.format(myDate);
	}

	/**
	 * 日期变为字符串.
	 * 
	 * @param date
	 *            the date
	 * @param iso
	 *            the iso
	 * @return the string
	 */
	public static String DateToString(Date date, String iso) {
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(iso);
		return format.format(date);
	}

	/**
	 * 字符串变为日期.
	 * 
	 * @param date
	 *            the date
	 * @return the date
	 */
	public static Date StringToDate(String date) {
		Date myDate = new Date();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(DateUtil.CM_SHORT_DATE_FORMAT);
		try {
			myDate = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return myDate;
	}

	/**
	 * 字符串变为日期.
	 * 
	 * @param date
	 *            the date
	 * @param formatStr
	 *            the format str
	 * @return the date
	 */
	public static Date StringToDate(String date, String formatStr) {
		Date myDate = new Date();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr);
		try {
			myDate = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return myDate;
	}

	/**
	 * 根据起始日期 及 间隔时间 得到结束日期.
	 * 
	 * @param startDate
	 *            起始日期
	 * @param offset
	 *            间隔时间
	 * @return 结束日期
	 */
	public static String getEndDate(String startDate, int offset) {
		Calendar cal = Calendar.getInstance();
		Date day = DateUtil.StringToDate(startDate);
		cal.setTime(day);
		cal.add(Calendar.DATE, offset);

		return DateUtil.DateToString(cal.getTime(), CM_SHORT_DATE_FORMAT);
	}

	/**
	 * 根据起始日期 及 间隔时间 得到结束日期 得到的格式是yyyy-MM-dd.
	 * 
	 * @param startDate
	 *            起始日期
	 * @param offset
	 *            间隔时间
	 * @return 结束日期
	 */

	public static String getEndDateForSQLDate(String startDate, int offset) {
		Calendar cal = Calendar.getInstance();
		Date day = DateUtil.StringToDateByFormat(startDate, CM_SHORT_DATE_FORMAT);
		cal.setTime(day);
		cal.add(Calendar.DATE, offset);

		return DateUtil.DateToString(cal.getTime(), CM_SHORT_DATE_FORMAT);
	}

	/**
	 * 把指定格式的字符串变为日期型.
	 * 
	 * @param date
	 *            the date
	 * @param iso
	 *            the iso
	 * @return the date
	 */
	public static Date StringToDateByFormat(String date, String iso) {
		Date myDate = new Date();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(iso);
		try {
			myDate = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return myDate;
	}

	/**
	 * 取得指定年月所有星期五的日期的集合.
	 * 
	 * @param year
	 *            指定年
	 * @param month
	 *            指定月
	 * @return list 星期五的日期的集合
	 */
	public static List getEndWeekDayOfMonth(String year, String month) {
		List list = new ArrayList();
		String date = "";
		int days = daysInMonth(year, month);
		int weekday = 0;
		for (int i = 1; i <= days; i++) {
			weekday = getWeekOfMonth(year, month, String.valueOf(i));
			if (weekday == 5) {
				if (i < 10) {
					list.add(year + month + "0" + String.valueOf(i));
				} else {
					list.add(year + month + String.valueOf(i));
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println("end week list[" + i + "]:" + list.get(i));
		}

		return list;
	}

	/**
	 * 获得指定年月的天数.
	 * 
	 * @param argYear
	 *            the arg year
	 * @param argMonth
	 *            the arg month
	 * @return int 天数
	 */
	public static int daysInMonth(String argYear, String argMonth) {
		int year = Integer.parseInt(argYear);
		int month = Integer.parseInt(argMonth);

		GregorianCalendar c = new GregorianCalendar(year, month, 0);
		int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		daysInMonths[1] += c.isLeapYear(c.get(GregorianCalendar.YEAR)) ? 1 : 0;
		return daysInMonths[c.get(GregorianCalendar.MONTH)];
	}

	/**
	 * 得到日期中是星期几.
	 * 
	 * @param year
	 *            String
	 * @param month
	 *            String
	 * @param day
	 *            String
	 * @return String
	 */
	public static int getWeekOfMonth(String year, String month, String day) {
		java.sql.Date myDate = getDate(year, month, day);
		int index = 0;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(myDate);
			index = cal.get(Calendar.DAY_OF_WEEK);
			if (index <= 1) {
				index = 7;
			} else {
				index = index - 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index;

	}

	/**
	 * 根据年月日得到日期.
	 * 
	 * @param year
	 *            String 年 YYYY
	 * @param month
	 *            String 月MM
	 * @param day
	 *            String 日dd
	 * @return Date
	 */
	public static java.sql.Date getDate(String year, String month, String day) {
		java.sql.Date result = null;
		try {
			String str = year + "-" + month + "-" + day;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = dateFormat.parse(str);
			result = new java.sql.Date(date1.getTime());
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
		return result;
	}

	/**
	 * 取得当前月的第一天.
	 * 
	 * @return string format
	 */
	public static String getFirstDayOfMonth() {
		StringBuffer buff = new StringBuffer();
		String today = DateUtil.getToday();
		buff.append(today.substring(0, 6)).append("01");
		return buff.toString();
	}

	/**
	 * 取得当前月的第一天.
	 * 
	 * @return long format
	 */
	public static long getFirstDayOfMonthInTimeFormat() {
		StringBuffer buff = new StringBuffer();
		String today = DateUtil.getToday();
		buff.append(today.substring(0, 6)).append("01");
		long time = (DateUtil.StringToDateByFormat(buff.toString(), "yyyyMMdd")).getTime();

		return time;
	}

	/**
	 * 取得距离当前月月底的n个月的第一天.
	 * 
	 * @param offSet
	 *            the off set
	 * @return the first day of offset month
	 */
	public static String getFirstDayOfOffsetMonth(int offSet) {
		StringBuffer buff = new StringBuffer();
		Date myDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.MONTH, offSet);

		String nextmonth = DateUtil.DateToString(cal.getTime(), YEAR_MONTH);
		buff.append(nextmonth).append("01");

		return buff.toString();
	}

	/**
	 * 把字符型时间转化成长整型时间.
	 * 
	 * @param argStr
	 *            the arg str
	 * @return the long
	 */
	public static long StingToLong(String argStr) {
		return (DateUtil.StringToDateByFormat(argStr, DateUtil.CM_SHORT_DATE_FORMAT)).getTime();
	}

	/**
	 * 按日期格式formatStr,把字符型时间转化成长整型时间.
	 * 
	 * @param argStr
	 *            the arg str
	 * @param formatStr
	 *            the format str
	 * @return the long
	 */
	public static long StingToLong(String argStr, String formatStr) {
		return (DateUtil.StringToDateByFormat(argStr, formatStr)).getTime();
	}

	/**
	 * 取得参数日期上个月的最后一天.
	 * 
	 * @param argDate
	 *            the arg date
	 * @return endDateOfUpMonth 格式如"yyyyMMdd"
	 */
	public static String getEndDateOfUpMonth(Date argDate) {
		StringBuffer buff = new StringBuffer();
		// java.util.Date --> String
		String date = DateUtil.DateToString(argDate, DateUtil.CM_SHORT_DATE_FORMAT);
		// 得到参数日期的上个年月
		String upMonth = DateUtil.getUpMonth(date.substring(0, 4), date.substring(4, 6), DateUtil.YEAR_MONTH);
		// 上个月的最后一天 = 上个月(yyyyMM) + 上个月总天数
		buff.append(upMonth).append(DateUtil.daysInMonth(upMonth.substring(0, 4), upMonth.substring(4)));
		date = buff.toString();
		buff = null;
		return date;
	}

	/**
	 * 取得参数日期下个月的最后一天.
	 * 
	 * @param argDate
	 *            the arg date
	 * @return endDateOfUpMonth 格式如"yyyyMMdd"
	 */
	public static String getEndDateOfNextMonth(Date argDate) {
		StringBuffer buff = new StringBuffer();
		// java.util.Date --> String
		String date = DateUtil.DateToString(argDate, DateUtil.CM_SHORT_DATE_FORMAT);
		// 得到参数日期的上个年月
		String nextMonth = DateUtil.getNextMonth(date.substring(0, 4), date.substring(4, 6), DateUtil.YEAR_MONTH);
		// 上个月的最后一天 = 上个月(yyyyMM) + 上个月总天数
		buff.append(nextMonth).append(DateUtil.daysInMonth(nextMonth.substring(0, 4), nextMonth.substring(4)));
		date = buff.toString();
		buff = null;
		return date;
	}

	/**
	 * Adds the specified (signed) amount of time to the given time field, based
	 * on the calendar's rules.
	 * 
	 * @param date
	 *            the date
	 * @param field
	 *            the field
	 * @param amount
	 *            the amount
	 * @return the date
	 */
	public static Date add(Date date, int field, long amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, (int) amount);
		return calendar.getTime();
	}

	/**
	 * Gets the first day of offset month.
	 * 
	 * @param date
	 *            the date
	 * @param formatStr
	 *            the format str
	 * @param offSet
	 *            the off set
	 * @return the first day of offset month
	 */
	public static String getFirstDayOfOffsetMonth(String date, String formatStr, int offSet) {
		Date myDate = DateUtil.StringToDate(date, formatStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, offSet);
		String ret = DateUtil.DateToString(cal.getTime(), DateUtil.CM_SHORT_DATE_FORMAT);
		return ret;
	}

	/**
	 * Gets the first day of month.
	 * 
	 * @param date
	 *            the date
	 * @param cm_short_date_format2
	 *            the cm_short_date_format2
	 * @return the first day of month
	 */
	public static Date getFirstDayOfMonth(String date, String cm_short_date_format2) {

		return null;
	}

	/**
	 * Get the time several months ago.
	 * 
	 * @param months
	 * @return
	 */
	public static Date getTimeMonthsAgo(int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 0 - months);

		return cal.getTime();
	}

}
