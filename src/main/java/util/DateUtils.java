package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	public final static String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	public final static String DATE_TIME_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_FORMAT = "dd-MM-yyyy";
	public final static String TIMEZONE = "America/Sao_Paulo";

	public static boolean isDateTimeFormatValid(String strDate, String strDateFormat, boolean lenient) {
		try {
			SimpleDateFormat dateformatter = new SimpleDateFormat(strDateFormat);
			dateformatter.setLenient(lenient);
			dateformatter.parse(strDate);
		} catch (java.text.ParseException e) {
			return false;
		}
		return true;
	}

	public static boolean isDateTimeFormatValid(String strDate, String strDateFormat) {
		return isDateTimeFormatValid(strDate, strDateFormat, false);
	}

	public static String toString(Date date, String strDateFormat) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
			return dateFormat.format(date);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public static Date toDate(String strDate, String strDateFormat) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
			return dateFormat.parse(strDate);
		} catch (java.text.ParseException e) {
			return null;
		}
	}

	public static Date toDate(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.getTime();
	}

	public static Date toDate() {
		Calendar cal = DateUtils.toCalendar(new Date());
		TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
		cal.setTimeZone(tz);
		return cal.getTime();
	}

	public static Date toDate(Long time) {
		Calendar cal = DateUtils.toCalendar(new Date(time));
		TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
		cal.setTimeZone(tz);
		return cal.getTime();
	}

	public static Calendar toCalendar(Date date) {
		if (date == null) {
			return null;
		}
		TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(tz);
		calendar.setTime(date);
		return calendar;
	}
}
