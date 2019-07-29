package com.atfarm.fcstats.counter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Period {
	
	public enum Type {
		DAY, HOUR, MINUTE, SECOND;
	}
	
	private Type type;
	private int size;
	
	public Period (Type type, int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Period size must be > 1");
		}
		this.type = type;
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public Type getType() {
		return type;
	}
	
	/**
	 * The period start date string given and endDate. The start date is formatted by Period.formatDate().
	 * Example: When endDate corresponds to 2019-07-27T23:01:43+0000 (ISO_8601) this is what
	 * startDateAsString() will return for different Periods:
	 * 	 
	 * <ul>
	 * <li>Period is 3 DAYs: 2019/07/25</li>
	 * <li>Period is 3 HOURs: 2019/07/27 21</li>
	 * <li>Period is 3 MINUTEs: 2019/07/27 22:59</li>
	 * <li>Period is 3 SECONDs: 2019/07/27 23:01:41</li>
	 * </ul>
	 * 
	 * @return
	 */
	public String startDateAsString(Date endDate) {		
		Date startDate = Period.sumPeriodToDate(endDate,-size + 1, type);
		String startDateStr = Period.formatDate(startDate, type);
		return startDateStr;
	}	
	
	/**
	 * Returns the start date of the period given and endDate. The time information irrelevant for the period type is zeroed.
	 * Example: When endDate corresponds to 2019-07-27T23:01:43+0000 (ISO_8601) this the dates represented in ISO_8601 that
	 * startDate() would return for different Periods:
	 * 	 
	 * <ul>
	 * <li>Period is 3 DAYs: 2019-07-27T00:00:00+0000</li>
	 * <li>Period is 3 HOURs: 2019-07-27T23:00:00+0000</li>
	 * <li>Period is 3 MINUTEs: 2019-07-27T23:01:00+0000</li>
	 * <li>Period is 3 SECONDs: 2019-07-27T23:01:43+0000</li>
	 * </ul>
	 * @param endDate
	 * @return
	 */
	public Date startDate(Date endDate) {
		Date startDate = Period.sumPeriodToDate(endDate,-size + 1, type);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		
		switch (type) {
		case SECOND:
			calendar.set(Calendar.MILLISECOND, 0);
			break;
		case MINUTE:
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			break;
		case HOUR:
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);			
			calendar.set(Calendar.MINUTE, 0);
			break;
		case DAY:
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);			
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR, 0);
		}		
		
		return calendar.getTime();
		
	}
	
	
	/**
	 * Formats a date according to a period type. The time fields that are not relevant for the period are eliminated.
	 * Given a date of corresponding to '2019-07-27T23:01:43+0000' (ISO_8601) this is what formatDate would return
	 * for each period type:
	 * <ul>
	 * <li>DAY: 2019/07/27</li>
	 * <li>HOUR: 2019/07/27 23</li>
	 * <li>MINUTE: 2019/07/27 23:01</li>
	 * <li>SECOND: 2019/07/27 23:01:43</li>
	 * </ul>
	 * 
	 * @param date
	 * @param type
	 * @return
	 */
	public static String formatDate(Date date, Type type) {
		String periodDateFormat = "";
		switch (type) {
		case SECOND:
			periodDateFormat = "yyyy/MM/dd HH:mm:ss";
			break;
		case MINUTE:
			periodDateFormat = "yyyy/MM/dd HH:mm";
			break;
		case HOUR:
			periodDateFormat = "yyyy/MM/dd HH";
			break;
		case DAY:
			periodDateFormat = "yyyy/MM/dd";
		}

		SimpleDateFormat format = new SimpleDateFormat(periodDateFormat);

		return format.format(date);		
		
	}
	
	public static Date sumPeriodToDate(Date date, int size,  Type type) {
		if(size == 0) {
			return date;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		switch (type) {
		case SECOND:
			c.add(Calendar.SECOND, size);
			break;
		case MINUTE:
			c.add(Calendar.MINUTE, size);
			break;
		case HOUR:
			c.add(Calendar.HOUR, size);
			break;
		case DAY:
			c.add(Calendar.DAY_OF_MONTH, size);
		}		
		return c.getTime();
	}

	@Override
	public String toString() {
		return "Period [type=" + type + ", size=" + size + "]";
	}
		
}