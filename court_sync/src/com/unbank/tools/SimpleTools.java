package com.unbank.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimpleTools {
	public static String dateToString(Date data, String format) {
		if (data == null) {
			data = new Date();
		}
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(format);
		String ctime = formatter.format(data);
		return ctime;
	}

	public static String getyyyyMMddTimeString(Date date, int num) {
		date = getMyDate(date, num);
		return dateToString(date, "yyyy-MM-dd");
	}

	public static Date getMyDate(Date date, int num) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DATE, num);
		date = calendar.getTime();
		return date;
	}

	public static Date stringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
		}
		return date;
	}

}
