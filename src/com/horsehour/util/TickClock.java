package com.horsehour.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Chunheng Jiang
 * @version 1.0
 * @resume Helps to record time cost by process
 */
public class TickClock {
	private static long start = 0, end = 0;

	/**
	 * begin timing
	 */
	public static void beginTick(){
		start = System.currentTimeMillis();
	}

	/**
	 * stop timing
	 */
	public static void stopTick(){
		end = System.currentTimeMillis();
		showElapsed();
	}

	/**
	 * show the times elapsed
	 */
	private static void showElapsed(){
		float elapsedSec = (float) (end - start) / 1000;
		StringBuilder sb = new StringBuilder("[Time Elapsed: ");
		if (elapsedSec > 3600)
			sb.append(elapsedSec / 3600 + " Hours.]");
		else if (elapsedSec > 60)
			sb.append(elapsedSec / 60 + " Minutes.]");
		else
			sb.append(elapsedSec + " Seconds.]");
		System.out.println(sb.toString());
	}

	/**
	 * @param date
	 * @return 取得date中的年份
	 */
	public static int getYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(1);
	}

	/**
	 * @param date
	 * @return 取得date中的月份
	 */
	public static int getMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(2) + 1;
	}

	/**
	 * @param date
	 * @return 取得date中的日
	 */
	public static int getDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(5);
	}

	/**
	 * @return 当前日期
	 */
	public static String today(){
		return new Date(System.currentTimeMillis()).toString();
	}

	/**
	 * @param str
	 * @return 从字符串(yyyy-mm-dd)中解析date对象
	 */
	public static Date getDate(String str){
		String[] fields = str.split("-");
		str = fields[0];
		for (int j = 1; j < fields.length; j++) {
			if (Integer.parseInt(fields[j]) < 10)
				fields[j] = "0" + fields[j];
			str += "-" + fields[j];
		}
		return Date.valueOf(str);
	}

	/**
	 * 根据具体日期计算周
	 * <p>
	 * sun 1
	 * <p>
	 * Mon 2 ...
	 * 
	 * @param date
	 * @return weak of day
	 */
	public static int weakOfDay(java.util.Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * @param datestr
	 * @param sdf
	 * @return weak of day
	 */
	public static int weakOfDay(String datestr, SimpleDateFormat sdf){
		java.util.Date date = null;
		try {
			date = sdf.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		return weakOfDay(date);
	}

	/**
	 * @param datestr
	 * @param sdf
	 * @return weak of day
	 */
	public static String weakInDay(String datestr, SimpleDateFormat sdf){
		java.util.Date date = null;
		try {
			date = sdf.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return new SimpleDateFormat("EEEE").format(date);
	}
}
