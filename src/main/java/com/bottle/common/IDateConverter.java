package com.bottle.common;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/***
 * 
 * @author Rainman.
 *
 */
public interface IDateConverter {
	public final String _Time_Format_Splitter = "_";
	public final String _Time_Format_NineteenBits = "yyyy-MM-dd HH:mm:ss";
	public final String _Time_Format_TwentyThreeBits = "yyyy_MM_dd_HH:mm:ss:SSS";
	public final String _Time_Format_TenBits = "yyyy_MM_dd";
	
	public final String YMDMap_Key_Year = "year";
	public final String YMDMap_Key_Month = "month";
	public final String YMDMap_Key_Day = "day";
	public final int YMD_Key_Num = 3;
	public final int _Hour_Max = 24;
	public final int _Hour_Min = 0;
	public final int _Minute_Max = 60;
	public final int _Minute_Min = 0;
	public final int _Second_Max = 60;
	public final int _Second_Min = 0;
	public final long _OneSecond_InMillisecond = 1000L;
	public final long _OneMinute_InMillisecond = 60L*_OneSecond_InMillisecond;
	public final long _OneHour_InMillisecond = 60L*_OneMinute_InMillisecond;
	public final long _OneDay_InMillisecond = 24L*_OneHour_InMillisecond;

	public final TimeZone gmtZeroTimeZone = TimeZone.getTimeZone("GMT");
	/***
	 * get current time stamp, in nineteen bits
	 * 2014-05-27 01:46:05
	 * @return
	 */
	String getCurrentTimestampInNineteenBitsInGMT();
	
	
	String convertTimestampToNineteenBitsInGMT(final long timestamp) throws RuntimeException;
	String convertTimestampToTwentyThreeBitsInGMT(final long timestamp) throws RuntimeException;
	
	/***
	 * get current date, in year-month-day format, such as 1970-01-01
	 * @return
	 */
	String getCurrentDateInYMD();
	
	/***
	 * get date string, in the YMD format, by the offset value, and the default timezone (GMT)
	 * 
	 * @param offset, the offset to today. today:0, yesterday:-1, tomorrow:1
	 * @return
	 */
	String getDateStrInYMDByOffsetAndDefaultTimezone(final int offset);
	
	/***
	 * for a input date string, like 2014-10-14, we could extract the year, month, and day number, and insert them to the map.
	 * The splitter is pre-define, like '-' or '-', depend on the previous use of the sign.
	 * @param dateStrInYMD
	 * @return
	 */
	Map<String, String> extractYMDNumberByDateStrInYMDFormat(final String dateStrInYMD);
	
	long computeInitialDedayForSchedualTask(final int targetHour, final int targetMinute, final int targetSecond, final long currentTimestamp);
	
	/***
	 * get timestamp string, by given Calendar, and format
	 * @param cal -- Calendar
	 * @param format -- format, like "yyyy-MM-dd HH:mm:ss Z"
	 * @return
	 */
	String getTimestampStrByCalendarAndFormat(final Calendar cal, final String format);


	Date getPreviousDate(Date currentDate,int previousOffset);
	
	Timestamp getCurrentTime_InTimestampFormat();
}
