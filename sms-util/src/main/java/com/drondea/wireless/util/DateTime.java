package com.drondea.wireless.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 时间对象
 * @author Administrator
 *
 */
public class DateTime implements Externalizable {

	public static final long DAY = 1000L * 60 * 60 * 24;

	public static final long HOUR = 1000L * 60 * 60;

	public static final long MINUTE = 1000L * 60;

	public static final long MINUTE_10 = 1000L * 60 * 10;

	public static final long HALF_HOUR = 1000L * 60 * 30;

	public static final long SECOND = 1000L;

	public static final String Y_M_D_H_M_S_1 = "yyyy-MM-dd HH:mm:ss";
	public static final String Y_M_D_H_M_S_2 = "yyyyMMddHHmmss";
    public static final String Y_M_D_H_M_S_S_1 = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String Y_M_D_H_M_S_S_2 = "yyyyMMddHHmmssSSS";
	public static final String Y_M_D_H_M = "yyyyMMddHHmm";
	public static final String Y_M_D_H = "yyyyMMddHH";
	public static final String Y_M_D_1 = "yyyy-MM-dd";
	public static final String Y_M_D_2 = "yyyyMMdd";
	public static final String H_M_S_1 = "HH:mm:ss";
	public static final String H_M_S_2 = "HHmmss";
	public static final String Y_M_D_1_00_00_00="yyyy-MM-dd 00:00:00";
	public static final String Y_M_D_1_23_59_59="yyyy-MM-dd 23:59:59";
	public static final String Y_M_D_1_23_59_59_999="yyyy-MM-dd 23:59:59.999";

	private long milliseconds;

	public DateTime() {
		super();
		this.milliseconds = currentTimeMillis();
		return;

	}

	public DateTime(long milliseconds) {
		super();
		this.milliseconds = milliseconds;
	}


	public static String getString() {
		return getString(new Date(), Y_M_D_H_M_S_1);
	}
	public static String getString(Date date) {
		return getString(date, Y_M_D_H_M_S_1);
	}

	public static String getCurrentDayMinDate(){
		return getString(new Date(),Y_M_D_1)+" 00:00:00";
	}
	public static String getCurrentDayMaxDate(){
		return getString(new Date(),Y_M_D_1)+" 23:59:59";
	}

	/**
	 * 高并发场景勿用，每次创建SimpleDateFormat对象会增加GC压力，可以用线程共享变量SimpleDateFormat解决
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getString(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 高并发场景勿用，每次创建SimpleDateFormat对象会增加GC压力
	 * @param pattern
	 * @param str
	 * @return
	 */
	public static Date getDate(String pattern, String str) {
		try {
			return new SimpleDateFormat(pattern).parse(str);
		} catch (ParseException e) {
			SuperLogger.error(e);
			return null;
		}
	}

	public static Date getDate(String str) {
		return getDate(Y_M_D_H_M_S_1, str);
	}

	/**
	 * 判断天数 Date
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		long daysBetween = between(smdate, bdate) / (1000 * 3600 * 24);
		return (int) daysBetween;
	}

	/**
	 * 判断小时数 Date
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int hoursBetween(Date smdate, Date bdate) {
		long hoursBetween = between(smdate, bdate) / (1000 * 3600);
		return (int) hoursBetween;
	}

	/**
	 * 判断天数 Sting
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) {
		// between计算出值超过int范围，先强转会造成精度丢失
		long daysBetween = between(getDate(Y_M_D_H_M_S_1, smdate), getDate(Y_M_D_H_M_S_1, bdate))/(1000 * 3600 * 24);
		return (int) daysBetween;
	}

	/**
	 * 判断小时数 String
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int hoursBetween(String smdate, String bdate) {
		long hoursBetween = between(getDate(Y_M_D_H_M_S_1, smdate), getDate(Y_M_D_H_M_S_1, bdate))/(1000 * 3600);
		return (int) hoursBetween;
	}

	/**
	 * 判断分钟数 Sting
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int minuteBetween(String smdate, String bdate) {
		long minuteBetween = between(getDate(Y_M_D_H_M_S_1, smdate), getDate(Y_M_D_H_M_S_1, bdate)) / (1000 * 60);
		return (int) minuteBetween;
	}

	/**
	 * 判断秒数
	 * @param smdate
	 * @param bdate
	 * @return
	 */
	public static int secondsBetween(String smdate, String bdate) {
		long secondsBetween = between(getDate(Y_M_D_H_M_S_1, smdate), getDate(Y_M_D_H_M_S_1, bdate)) / 1000;
		return (int)secondsBetween;
	}


	public static long between(Date beginDate, Date endDate) {
		return endDate.getTime() - beginDate.getTime();
	}

	/**
	 * ----------------------
	 *
	 * @param str
	 * @return
	 */
	public static boolean isValidDate(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		// 指定日期格式
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String strDate = format.format(new Date());
		if (str.equals(strDate)) {
			return true;
		}
		strDate = format.format(addDay(-1));
		if (str.equals(strDate)) {
			return true;
		}
		strDate = format.format(addDay(1));
		if (str.equals(strDate)) {
			return true;
		}
		return false;
	}

	public static Date addYear(Date date, int year) {
		return add(date, Calendar.YEAR, year);
	}

	public static Date addMonth(Date date, int month) {
		return add(date, Calendar.MONTH, month);
	}

	public static Date addDay(Date date, int days) {
		return add(date, Calendar.DATE, days);
	}
	public static Date addHour(Date date, int hours) {
		return add(date, Calendar.HOUR, hours);
	}

	public static Date addSecond(Date date, int second) {
		return add(date, Calendar.SECOND, second);
	}

	public static Date addMilliSecond(Date date, int milliseconds) {
		return add(date, Calendar.MILLISECOND, milliseconds);
	}

	public static Date addDay(int days) {
		return addDay(new Date(), days);
	}

	public static Date add(Date date, int type, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, days);
		date = calendar.getTime();
		return date;
	}

	public static Date warpTodayMinDate(String time) {
		Calendar now = Calendar.getInstance();
		Date date = ObjectUtils.defaultIfNull(
				DateTime.toDate(time, DateTime.H_M_S_1),
				DateTime.getDate(DateTime.getCurrentDayMinDate()));
		date = DateUtils.setYears(date, now.get(Calendar.YEAR));
		date = DateUtils.setMonths(date, now.get(Calendar.MONTH));
		date = DateUtils.setDays(date, now.get(Calendar.DATE));
		return date;
	}

	public static Date warpTodayMaxDate(String time) {
		Calendar now = Calendar.getInstance();
		Date date = ObjectUtils.defaultIfNull(
				DateTime.toDate(time, DateTime.H_M_S_1),
				DateTime.getTheDayBeforeMaxDate(new Date(), 0));
		date = DateUtils.setYears(date, now.get(Calendar.YEAR));
		date = DateUtils.setMonths(date, now.get(Calendar.MONTH));
		date = DateUtils.setDays(date, now.get(Calendar.DATE));
		return date;
	}

	/**
	 * 1:yyMMddHHmm
	 * @param s
	 * @param f
	 */
	public DateTime(String s,int f)
	{
		Calendar cal=Calendar.getInstance();
		if(f==1)
		{
			if(StringUtils.isEmpty(s))
			{
				return;
			}
			if(s.length()!=10)
			{
				return;
			}
			cal.set(2000+Integer.parseInt(s.substring(0,2)), Integer.parseInt(s.substring(2,4)), Integer.parseInt(s.substring(4,6)), Integer.parseInt(s.substring(6,8)),  Integer.parseInt(s.substring(8,10)));
		}

		this.milliseconds=cal.getTimeInMillis();
	}
	public DateTime(Calendar cal) {
		super();
		this.milliseconds = cal.getTimeInMillis();
	}

	public long getMilliseconds() {
		return this.milliseconds;

	}

	public Calendar getCalendar()
	{
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(this.milliseconds);
		return cal;
	}

	public void setMilliseconds(long milliseconds) {
		this.milliseconds = milliseconds;
	}

	public void reset() {
		this.milliseconds = currentTimeMillis();
	}

	public void reset(long milliseconds) {
		this.milliseconds = milliseconds;
	}

	public String formatDate(String formatString) {
		DateFormat df=new SimpleDateFormat(formatString);
		return df.format(getCalendar().getTime());
	}

	/**
	 *
	 * @param sDate
	 * @param sFmt
	 * @return
	 */
	public static Date toDate(String sDate, String sFmt) {
		if (StringUtils.isEmpty(sDate) || StringUtils.isEmpty(sFmt)) {
			return null;
		}
		SimpleDateFormat sdfFrom = null;
		java.util.Date dt = null;
		try {
			sdfFrom = new SimpleDateFormat(sFmt);
			dt = sdfFrom.parse(sDate);
		} catch (Exception ex) {
			SuperLogger.error("error", ex);
			return null;
		} finally {
			sdfFrom = null;
		}
		return dt;
	}



	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(this.milliseconds);

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException {

		this.milliseconds = in.readLong();
	}

	public long currentTimeMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public int getIntTime(String formatString)
	{
		return Integer.parseInt(this.formatDate(formatString));
	}

	/**
	 * 校验日期格式
	 * @param dateString 要校验的日期
	 * @param formatStr 要校验的格式
	 * @return
	 */
	public static boolean isValidDate(String dateString, String formatStr) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(dateString);
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			SuperLogger.error(e.getMessage(), e);
			convertSuccess = false;
		} catch (NullPointerException e) {
			SuperLogger.error(e.getMessage(), e);
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static Date getTheDayBeforeMinDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,00);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		calendar.set(Calendar.MILLISECOND,0);
		if (days == 0) {
			return calendar.getTime();
		}
		return addDay(calendar.getTime(),days);
	}

	//获取当前时间前（后）某一天的零点时刻
	public static Date getTheDayBeforeMinDate(int days) {
		return getTheDayBeforeMinDate(new Date(), days);
	}

	//获取当前时间前（后）某一天的23:59:59.999
	public static Date getTheDayBeforeMaxDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND, 999);
		if (days == 0) {
			return calendar.getTime();
		}
		return addDay(calendar.getTime(),days);
	}

	//获取传入时间的最大毫秒时间
	public static Date getTimeForMaxMillisecond(Date date) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		if(date == null) {
			return getTheDayBeforeMaxDate(0);
		}
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	//获取当前时间前（后）某一天的23:59:59.999
	public static Date getTheDayBeforeMaxDate(int days) {
		return getTheDayBeforeMaxDate(new Date(), days);
	}

	//获取hours小时后的整点时刻
	public static Date getTheHourAfterMinHour(int hours) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime( new Date());
//		calendar.set(Calendar.HOUR_OF_DAY,00);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		return addHour(calendar.getTime(),hours);
	}
	//获取hours小时后的59分59秒
	public static Date getTheHourAfterMaxHour(int hours) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime( new Date());
//		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return addHour(calendar.getTime(),hours);
	}

	//毫秒转yyyy-MM-dd HH:mm:ss日期
	public static String getFormatDateByMS(Long millisecond){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(millisecond));
	}
	//毫秒转HH小时mm分ss秒格式时间长度
	public static String getFormatDurationByMS(Long millisecond){
		SimpleDateFormat formatter = new SimpleDateFormat("HH小时mm分ss秒");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
		return formatter.format(millisecond);
	}

	//获取两个日之前所有日
	public static List<String> getBetweenDates(String start, String end) {
		List<String> result = new ArrayList<String>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date start_date = sdf.parse(start);
			Date end_date = sdf.parse(end);
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start_date);
			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end_date);
			while (tempStart.before(tempEnd)||tempStart.equals(tempEnd)) {
				result.add(sdf.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Collections.reverse(result);
		return result;
	}

	//获取两个月之间所有月份
	public static List<String> getBetweenMonth(String minDate, String maxDate){
		ArrayList<String> result = new ArrayList<String>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
			Calendar min = Calendar.getInstance();
			Calendar max = Calendar.getInstance();
			min.setTime(sdf.parse(minDate));
			min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
			max.setTime(sdf.parse(maxDate));
			max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
			Calendar curr = min;
			while (curr.before(max)) {
				result.add(sdf.format(curr.getTime()));
				curr.add(Calendar.MONTH, 1);
			}
		}catch (ParseException e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 判断当天月的天数
	 * @param date 例如：2019-09 2019-09-01
	 * @return
	 */
	public static int getDaysOfMonth(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**获取上n个小时整点小时时间
	 * @param date
	 * @return
	 */
	public static String getLastHourTime(Date date, int n){
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY) - n);
		date = ca.getTime();
		return sdf.format(date);
	}

	public static boolean verifyDateFormat(String dateStr) {
		boolean result = false;
		if(org.apache.commons.lang.StringUtils.isNotEmpty(dateStr)){
			if(dateStr.length() == Y_M_D_H_M_S_1.length()){
				try {
					new SimpleDateFormat(Y_M_D_H_M_S_1).parse(dateStr);
					result = true;
				} catch (ParseException e) {
				}
			}else if(dateStr.length() == Y_M_D_1.length()){
				try {
					new SimpleDateFormat(Y_M_D_1).parse(dateStr);
					result = true;
				} catch (ParseException e) {
				}
			}
		}
		return result;
	}

	/**
	 * 分时间段查询，获取查询时间段集合
	 *
	 * @param timeInterval
	 *            时间间隔，单位秒
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return list集合
	 */
	public static List<DateInterVal> getIntervalDate(int timeInterval, Date startDate,
											   Date endDate) {
		List<DateInterVal> resultList = new ArrayList<>();
		startDate = startDate == null ? getTheDayBeforeMinDate(0) : startDate;
		endDate = endDate == null ? getTheDayBeforeMaxDate(0) : endDate;
		if (DateUtils.isSameInstant(startDate, endDate)) {
			resultList.add(new DateInterVal(startDate, endDate));
			return resultList;
		}

		//开始时间
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(startDate);
		//结束时间
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(endDate);

		//calendarStart <= calendarEnd
		while(calendarStart.before(calendarEnd)){
			Date before = calendarStart.getTime();
			calendarStart.add(Calendar.SECOND, timeInterval);
			if (calendarStart.before(calendarEnd)) {
				resultList.add(new DateInterVal(before, calendarStart.getTime()));
			} else {
				resultList.add(new DateInterVal(before, endDate));
			}

		}

		return resultList;
	}

	/**
	 * 分割日期，边界无重复值，上一个边界为999毫秒
	 * @param timeInterval
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<DateInterVal> getIntervalDateWithoutMax(int timeInterval, Date startDate,
													 Date endDate) {
		List<DateInterVal> intervalDate = getIntervalDate(timeInterval, startDate, endDate);
		for (int i = 0; i < intervalDate.size() - 1; i++) {
			DateInterVal dateInterVal = intervalDate.get(i);
			Date end = dateInterVal.getEnd();
			//减1个毫秒
			dateInterVal.setEnd(addMilliSecond(end, -1));
		}
		return intervalDate;
	}


	/**当前时间减 n 分钟
	 * @param n
	 * @return
	 */
	public static String getCurentTimeBeforeMinutes(int n){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, -n);
		Date date = ca.getTime();
		return sdf.format(date);
	}

    /**
     * 获取标准格林威治格式时间
     * 例如：Date: Tue, 15 Nov 2019 08:12:31 GMT
     * @return
     */
    public static String getGMTDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        Calendar calendar = Calendar.getInstance();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        return sdf.format(calendar.getTime());
    }

	/**
	 * Cassandra 日期使用UTC
	 * 获取UTC时间
	 * @param date
	 * @return
	 */
	public static Long getUTCMills(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 设置UTC时区
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date parseDate = sdf.parse(date);
			return parseDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getStartOfDay(Date date) {
		return getTheDayBeforeMinDate(date, 0);
	}

	public static Date getStartOfDay(String dateStr) {
		Date date = getDate(dateStr);
		return getStartOfDay(date);
	}

	public static Date getEndOfDay(String dateStr) {
		Date date = getDate(dateStr);
		return getEndOfDay(date);
	}

	public static Date getEndOfDay(Date date) {
		return getTheDayBeforeMaxDate(date, 0);
	}

	public static List<DateInterVal> getDayIntervalDate(Date startDate,
												  Date endDate) {

		List<DateInterVal> resultList = new ArrayList<>();
		if (startDate.getTime() > endDate.getTime()) {
			return resultList;
		}
		//判断同一天
		if (DateUtils.isSameDay(startDate, endDate)) {
			resultList.add(new DateInterVal(startDate, endDate));
			return resultList;
		}
		//开始时间
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(startDate);
		//结束时间
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(endDate);

		int count = 0;
		Date start;
		Date end;
		//calendarStart <= calendarEnd
		while(!DateUtils.isSameDay(calendarStart, calendarEnd)){
			//第一次
			if (count == 0) {
				start = startDate;
			} else {
				start = getStartOfDay(calendarStart.getTime());
			}
			end = getEndOfDay(calendarStart.getTime());

			resultList.add(new DateInterVal(start, end));

			count ++;
			calendarStart.add(Calendar.DAY_OF_MONTH, 1);
		}

		//最后一天的时间段
		start = getStartOfDay(endDate);
		resultList.add(new DateInterVal(start, endDate));
		return resultList;
	}


	/**
	 * 获取本周一日期
	 * @param date
	 * @return
	 */
	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		//周日减一天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}

	/**
	 * 获取下周一
	 * @param date
	 * @return
	 */
	public static Date getNextWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, 7);
		return cal.getTime();
	}


	public static class DateInterVal{
		private Date begin;
		private Date end;

		public DateInterVal(Date begin, Date end) {
			this.begin = begin;
			this.end = end;
		}

		public Date getBegin() {
			return begin;
		}

		public void setBegin(Date begin) {
			this.begin = begin;
		}

		public Date getEnd() {
			return end;
		}

		public void setEnd(Date end) {
			this.end = end;
		}
	}

	public static String formatDate(Date date, String formatString) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		return sdf.format(date);
	}

    public static String utsDateToString(String oldDate) {
	    try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date1 = df1.parse(date.toString());
            return getString(date1);
        }catch (Exception e){
	        SuperLogger.error(e.getMessage(),e);
        }
        return null;
    }

	/**
	 * 获取时间戳的秒数的后几位
	 * @param timestamp
	 * @param divisor
	 * @return
	 */
    public static int getSimpleSecond(long timestamp, int divisor) {
		return (int) (timestamp / 1000 % divisor);
	}

	/**
	 * 获取过去7天内的日期数组
	 * @param intervals      intervals天内
	 * @return              日期数组
	 */
	public static ArrayList<String> getDays(int intervals) {
		ArrayList<String> pastDaysList = new ArrayList<>();
		for (int i = intervals -1; i >= 0; i--) {
			pastDaysList.add(getPastDate(i));
		}
		return pastDaysList;
	}
	/**
	 * 获取过去第几天的日期
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

//	public static void main(String[] args) {
//		Date date1 = getDate("2021-04-23 01:00:00");
//		Date date2 = getDate("2021-04-23 00:00:00");
//		List<DateInterVal> dayIntervalDate = getDayIntervalDate(date1, date2);
//		dayIntervalDate.forEach(item -> {
//			System.out.println(item.getBegin());
//			System.out.println(item.getEnd());
//		});
//	}

}
