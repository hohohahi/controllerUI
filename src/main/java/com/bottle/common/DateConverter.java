package com.bottle.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/***
 * 
 * @author Rainman.
 *
 */
@Service
public class DateConverter implements IDateConverter {
	private static final Logger _logger = Logger.getLogger(DateConverter.class);

	@Override
	public String getCurrentTimestampInNineteenBitsInGMT() {
		return getCurrentTimestampInNineteenBitsByTimeZone(gmtZeroTimeZone);
	}

	private String getCurrentTimestampInNineteenBitsByTimeZone(TimeZone currentTimeZone){
		return getDateByFormatAndTimeZoneAndOffset(IDateConverter._Time_Format_NineteenBits, currentTimeZone, 0);
	}
	
	public String convertTimestampToNineteenBitsInGMT(final long timestamp) throws RuntimeException{		
		return convertTimestampToStrByTimeZoneAndFormat(gmtZeroTimeZone, _Time_Format_NineteenBits, timestamp);
	}
	
	private String convertTimestampToStrByTimeZoneAndFormat(TimeZone currentTimeZone, final String format, long timestamp){
		if (timestamp < 0){
			String errorMessage = "timestamp is less than 0. timestamp:" + timestamp;
			_logger.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		
		Calendar currentTimeCalendar = Calendar.getInstance(currentTimeZone); 
		currentTimeCalendar.setTimeInMillis(timestamp);
        
        java.text.SimpleDateFormat sdf = new SimpleDateFormat(format); 
        sdf.setTimeZone(currentTimeZone);
        String currentTimeFormat = sdf.format(currentTimeCalendar.getTime());
        
		return currentTimeFormat;
	}
	
	@Override
	public String getCurrentDateInYMD() {
		return getDateStrInYMDByTimeZoneAndOffeset(gmtZeroTimeZone, 0);
	}
	
	/***
	 * 
	 * @param currentTimeZone -- time zone
	 * @param offset -- the offset to today. for example, yesterday:-1, tomorrow:1, today:0
	 * @return
	 */
	private String getDateStrInYMDByTimeZoneAndOffeset(final TimeZone currentTimeZone, final int offset){ 
		return getDateByFormatAndTimeZoneAndOffset(IDateConverter._Time_Format_TenBits, currentTimeZone, offset);
	}
	
	private String getDateByFormatAndTimeZoneAndOffset(String format, TimeZone currentTimeZone, final int offset){
		Calendar currentTimeCalendar = Calendar.getInstance(currentTimeZone);  
		currentTimeCalendar.add(Calendar.DATE, offset);
		
        java.text.SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(currentTimeZone);
        String currentTimeFormat = sdf.format(currentTimeCalendar.getTime());
        
		return currentTimeFormat;
	}
	
	/***
	 * 
	 * @param offset -- offset to today. for example, yesterday:-1, tomorrow:1, today:0
	 * @return
	 */
	
	public final String getDateStrInYMDByOffsetAndDefaultTimezone(final int offset){		
		return getDateStrInYMDByTimeZoneAndOffeset(gmtZeroTimeZone, offset);
	}
	
	public static void main(String [] args){
		System.out.println(new DateConverter().getDateStrInYMDByOffsetAndDefaultTimezone(0));
		System.out.println(new DateConverter().getDateStrInYMDByOffsetAndDefaultTimezone(1));
		System.out.println(new DateConverter().getDateStrInYMDByOffsetAndDefaultTimezone(2));
		long currentTimestamp = 1403359895856L;
		String currentTimeInNineteen = new DateConverter().convertTimestampToNineteenBitsInGMT(currentTimestamp);
		System.out.println(currentTimeInNineteen);
		
		currentTimestamp = 1403311823483L;
		currentTimeInNineteen = new DateConverter().convertTimestampToNineteenBitsInGMT(currentTimestamp);
		System.out.println(currentTimeInNineteen);
		
		currentTimestamp = System.currentTimeMillis();
		currentTimeInNineteen = new DateConverter().convertTimestampToNineteenBitsInGMT(currentTimestamp);
		System.out.println(currentTimeInNineteen);
		
		currentTimestamp = 0L;
		currentTimeInNineteen = new DateConverter().convertTimestampToNineteenBitsInGMT(currentTimestamp);
		System.out.println(currentTimeInNineteen);
		//2014-06-21 14:11:35

		String currentData = new DateConverter().getCurrentDateInYMD();
		System.out.println(currentData);
		//2014-06-21 24:50:23

	}

	@Override
	public Map<String, String> extractYMDNumberByDateStrInYMDFormat(String dateStrInYMD) {
		if (null == dateStrInYMD){
			throw new NullPointerException("dateStrInYMD is null.");
		}
		
		String [] numStrArray = dateStrInYMD.split(_Time_Format_Splitter);
		
		if (null == numStrArray){
			final String errorMessage = "extractYMDNumberByDateStrInYMDFormat: numStrArray is null. dateStrInYMD:" + dateStrInYMD;
			throw new NullPointerException(errorMessage);
		}
		
		final int arrayLength = numStrArray.length;
		if (arrayLength != YMD_Key_Num){
			final String errorMessage = "extractYMDNumberByDateStrInYMDFormat: numStrArray's size is invalid. arrayLength:" + arrayLength
											+ "--YMD_Key_Num:" + YMD_Key_Num
											+ "--_Time_Format_Splitter:" + _Time_Format_Splitter
											+ "--dateStrInYMD:" + dateStrInYMD;
			throw new RuntimeException(errorMessage);
		}
		
		final String yearStr = numStrArray[0];
		final String monthStr = numStrArray[1];
		final String dayStr = numStrArray[2];
		
		if ((false == StringUtils.isNumeric(yearStr))
				|| (false == StringUtils.isNumeric(monthStr))
				|| (false == StringUtils.isNumeric(dayStr))){
			final String errorMessage = "extractYMDNumberByDateStrInYMDFormat: numStrArray's content is not numeric(at least some of them). yearStr:" + yearStr
											+ "--monthStr:" + monthStr
											+ "--dayStr:" + dayStr
											+ "--YMD_Key_Num:" + YMD_Key_Num
											+ "--_Time_Format_Splitter:" + _Time_Format_Splitter
											+ "--arrayLength:" + arrayLength
											+ "--dateStrInYMD:" + dateStrInYMD;
			throw new RuntimeException(errorMessage);
		}
		
		Map<String, String> YMDNumberMap = new HashMap<String, String>();
		YMDNumberMap.put(YMDMap_Key_Year, yearStr);
		YMDNumberMap.put(YMDMap_Key_Month, monthStr);
		YMDNumberMap.put(YMDMap_Key_Day, dayStr);
		
		return YMDNumberMap;
	}

	@Override
	public long computeInitialDedayForSchedualTask(final int targetHour, final int targetMinute, final int targetSecond, final long currentTimestamp) {
		//1. check hour parameter.
		if ((_Hour_Min > targetHour)
				|| (_Hour_Max < targetHour)){
			final String errorMessage = "computeInitialDedayForSchedualTask: target hour is invalid. targetHour:" + targetHour
											+ "--targetMinute:" + targetMinute
											+ "--targetSecond:" + targetSecond
											+ "--currentTimestamp:" + currentTimestamp;
			throw new IllegalArgumentException(errorMessage);
		}
		
		//2. check minute parameter.
		if ((_Minute_Min > targetMinute)
				|| (_Minute_Max < targetMinute)){
			final String errorMessage = "computeInitialDedayForSchedualTask: target minute is invalid. targetHour:" + targetHour
											+ "--targetMinute:" + targetMinute
											+ "--targetSecond:" + targetSecond
											+ "--currentTimestamp:" + currentTimestamp;
			throw new IllegalArgumentException(errorMessage);
		}
		
		//3. check second parameter.
		if ((_Second_Min > targetSecond)
				|| (_Second_Max < targetSecond)){
			final String errorMessage = "computeInitialDedayForSchedualTask: target second is invalid. targetHour:" + targetHour
					+ "--targetMinute:" + targetMinute
					+ "--targetSecond:" + targetSecond
					+ "--currentTimestamp:" + currentTimestamp;
			throw new IllegalArgumentException(errorMessage);
		}
		
		//4. check current timestamp parameter.
		if (currentTimestamp < 0){
			final String errorMessage = "computeInitialDedayForSchedualTask: currentTimestamp is invalid. targetHour:" + targetHour
					+ "--targetMinute:" + targetMinute
					+ "--targetSecond:" + targetSecond
					+ "--currentTimestamp:" + currentTimestamp;
			throw new IllegalArgumentException(errorMessage);
		}
		
		Calendar currentTimeCalendar = Calendar.getInstance(gmtZeroTimeZone);
		currentTimeCalendar.setTimeInMillis(currentTimestamp);
		currentTimeCalendar.set(Calendar.HOUR_OF_DAY, targetHour);
		currentTimeCalendar.set(Calendar.MINUTE, targetMinute);
		currentTimeCalendar.set(Calendar.SECOND, targetSecond);
		
		final long targetTimeInMillis = currentTimeCalendar.getTimeInMillis();
	    final long initDelay  = targetTimeInMillis - currentTimestamp;
	    
	    long rtnInitialDelay = 0L; 
	    if (initDelay > 0){
	    	rtnInitialDelay = initDelay;
	    }
	    else{
	    	rtnInitialDelay = _OneDay_InMillisecond + initDelay;
	    }
	    
		return rtnInitialDelay;
	}

	@Override
	public String convertTimestampToTwentyThreeBitsInGMT(long timestamp)
			throws RuntimeException {
		return convertTimestampToStrByTimeZoneAndFormat(gmtZeroTimeZone, _Time_Format_TwentyThreeBits, timestamp);
	}

	@Override
	public String getTimestampStrByCalendarAndFormat(final Calendar cal, final String format) {
		if (null == format){
			throw new NullPointerException("format is null.");
		}
		
		Calendar validCalendar;
		if (null == cal){
			validCalendar = Calendar.getInstance(gmtZeroTimeZone);
		}
		else{
			validCalendar = cal;
		}
		
		final SimpleDateFormat sdf=new SimpleDateFormat(format);
		Date date=validCalendar.getTime();
		final String timestampStr=sdf.format(date);
		
		return timestampStr;
	}

	@Override
	public Date getPreviousDate(Date currentDate,int previousOffset){
		Date newDate=null;
		
		if(currentDate!=null){
			Calendar date = Calendar.getInstance();
			date.setTime(currentDate);
			date.set(Calendar.DATE, date.get(Calendar.DATE) - previousOffset);
			newDate=date.getTime();
		}
		return newDate;
	}

	@Override
	public Timestamp getCurrentTime_InTimestampFormat() {
		Timestamp currentTimeInTimestamp = new Timestamp(System.currentTimeMillis());
		
		return currentTimeInTimestamp;
	}
}
