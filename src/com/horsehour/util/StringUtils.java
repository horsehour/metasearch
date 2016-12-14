package com.horsehour.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.horsehour.util.MathUtils;

/**
 * @author Chunheng Jiang
 * @version 1.0
 * @since Feb. 07, 2015
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static class JDBCStringUtils extends com.mysql.jdbc.StringUtils {}

	/**
	 * 查询文本中文字符的起始位置
	 * 
	 * @param str
	 * @return index of Chinese char in str
	 */
	public static int indexOfChinese(String str){
		int i = 0;
		int len = str.length();
		for (; i < len; i++)
			if (isChinese(str.charAt(i)))
				break;
		if (i == 0)
			i = -1;
		return i;
	}

	/**
	 * 判断是否为中文字符
	 * 
	 * @param ch
	 * @return true if the char is an Chinese char
	 */
	public static boolean isChinese(char ch){
		return ch >= '\u4E00' && ch <= '\u9FA5' || ch >= '\uF900' && ch <= '\uFA2D';
	}

	/**
	 * @param str
	 * @param regex The regular expression to look for
	 * @param n nth regex match, zero based
	 * @return The nth regex match in a string
	 */
	public static String match(String str, String regex, int n){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (!m.find())
			return null;

		try {
			return m.group(n).trim();
		} catch (IllegalStateException e) {
			return null;
		}
	}

	/**
	 * @param str
	 * @param regex
	 * @return matched content
	 */
	public static String match(String str, String regex){
		return match(str, regex, 0);
	}

	/**
	 * @param str
	 * @param regex
	 * @return All regex matches in a string
	 */
	public static String[] matchAll(String str, String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);

		if (!m.find())
			return null;

		int n = m.groupCount();
		String[] result = new String[n];
		for (int i = 0; i < n; i++)
			result[i] = m.group(i).trim();
		return result;
	}

	/**
	 * 剔除文本中的噪声（如连续空格,&nbsp;等）
	 * @param str
	 * @return clean content
	 */
	public static String rmNoise(String str){
		str = str.replaceAll(" |　|\r|\n|\t", "");// 中英文空格
		str = str.replaceAll("&nbsp;", "");
		return str;
	}

	/**
	 * 剔除文本中的html标签标记
	 * @param str
	 * @return clean content
	 */
	public static String rmHtmlTag(String str){
		return str.replaceAll("<[^>]*?>", "").trim();
	}

	/**
	 * short text
	 * @param str
	 * @return statistics
	 */
	public static Map<Character, Integer> charSTAT(String str){
		Map<Character, Integer> stat = new HashMap<Character, Integer>();
		for (char ch : str.toLowerCase().toCharArray()) {
			if (stat.containsKey(ch))
				stat.put(ch, stat.get(ch) + 1);
			else
				stat.put(ch, 1);
		}
		return stat;
	}

	/**
	 * short text
	 * 
	 * @param str
	 * @param delim
	 * @return statistics
	 */
	public static Map<String, Integer> substrSTAT(String str, String delim){
		Map<String, Integer> stat = new HashMap<String, Integer>();
		for (String sub : str.toLowerCase().split(delim)) {
			if (stat.containsKey(sub))
				stat.put(sub, stat.get(sub) + 1);
			else
				stat.put(sub, 1);
		}
		return stat;
	}

	public static Map<String, Integer> substrSTAT(String str){
		return substrSTAT(str, " ");
	}

	/**
	 * @param str1
	 * @param str2
	 * @param delim
	 * @return calculate the similarity between two strings based on substr
	 */
	public static double getSim(String str1, String str2, String delim){
		Map<String, Integer> sstat1 = substrSTAT(str1, delim);
		Map<String, Integer> sstat2 = substrSTAT(str2, delim);

		Set<String> strset = new TreeSet<String>();
		strset.addAll(sstat1.keySet());
		strset.addAll(sstat2.keySet());

		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();

		for (String sub : strset) {
			Integer count = sstat1.get(sub);
			if (count == null)
				list1.add(0);
			else
				list1.add(count);

			count = sstat2.get(sub);
			if (count == null)
				list2.add(0);
			else
				list2.add(count);
		}

		return MathUtils.getSimCosine(list1, list2);
	}

	public static double getSim(String str1, String str2){
		return getSim(str1, str2, " ");
	}

	/**
	 * @param str1
	 * @param str2
	 * @return calculate the similarity between two strings
	 */
	public static double getCharSim(String str1, String str2){
		Map<Character, Integer> stat1 = charSTAT(str1);
		Map<Character, Integer> stat2 = charSTAT(str2);

		Set<Character> charset = new TreeSet<Character>();
		charset.addAll(stat1.keySet());
		charset.addAll(stat2.keySet());

		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();

		for (char ch : charset) {
			Integer count = stat1.get(ch);
			if (count == null)
				list1.add(0);
			else
				list1.add(count);

			count = stat2.get(ch);
			if (count == null)
				list2.add(0);
			else
				list2.add(count);
		}

		return MathUtils.getSimCosine(list1, list2);
	}

	/**
	 * @param idx starts from 0
	 * @return name of corresponding month
	 */
	public static String getMonth(int idx){
		switch (idx) {
			case Calendar.JANUARY:
				return "January";

			case Calendar.FEBRUARY:
				return "February";

			case Calendar.MARCH:
				return "March";

			case Calendar.APRIL:
				return "April";

			case Calendar.MAY:
				return "May";

			case Calendar.JUNE:
				return "June";

			case Calendar.JULY:
				return "July";

			case Calendar.AUGUST:
				return "August";

			case Calendar.SEPTEMBER:
				return "September";

			case Calendar.OCTOBER:
				return "October";

			case Calendar.NOVEMBER:
				return "November";

			case Calendar.DECEMBER:
				return "December";

			default:
				return "Invalid Month";
		}
	}
}
