package com.quarkdata.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

	public static String DATETIME_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

	private static String DATE_FORMAT = "yyyy/MM/dd";

	private static String DATE_FORMAT_DAY = "yyyy-MM-dd";

	private static String LONG_DATE_FORMAT = "yyyyMMddHHmmss";

	private static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

	private static final String[] constellationArr = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
			"天蝎座", "射手座", "魔羯座" };

	private static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };

	private static final String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	private static final Integer[] weekDayNum = { 7, 1, 2, 3, 4, 5, 6 };

	private final static long minute = 60 * 1000;// 1分钟

	private final static long hour = 60 * minute;// 1小时

	private final static long day = 24 * hour;// 1天

	private final static long month = 31 * day;// 月

	private final static long year = 12 * month;// 年

	private static final String[] weeks = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };


	/**
	 * 返回文字描述的日期 该方法用来生成那种回复留言 或者帖子的回复时间显示 刚刚 几分钟前 等等
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeFormatText( Date date ) {
		if ( date == null ) { return null; }
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if ( diff > year ) {
			r = diff / year;
			return r + "年前";
		}
		if ( diff > month ) {
			r = diff / month;
			return r + "个月前";
		}
		if ( diff > day ) {
			r = diff / day;
			return r + "天前";
		}
		if ( diff > hour ) {
			r = diff / hour;
			return r + "个小时前";
		}
		if ( diff > minute ) {
			r = diff / minute;
			return r + "分钟前";
		}
		return "刚刚";
	}


	/**
	 * 返回日期是星期几 文字星期几
	 * <p>
	 *
	 * @param dt
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public static String getWeekOfDate( Date dt ) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if ( w < 0 ) {
			w = 0;
		}

		return weekDays[w];
	}


	/**
	 * 返回日期是星期几 数字
	 * <p>
	 *
	 * @param dt
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public static Integer getWeekOfDateNum( Date dt ) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if ( w < 0 ) {
			w = 1;
		}

		return weekDayNum[w];
	}


	/**
	 * Get the previous time, from how many days to now.
	 *
	 * @param days
	 *            How many days.
	 * @return The new previous time.
	 */
	public static Date previous( int days ) {
		return new Date(System.currentTimeMillis() - days * 3600000L * 24L);
	}


	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm:ss".
	 */
	public static String formatDateLong( Date d ) {
		if ( d == null ) { return ""; }
		return new SimpleDateFormat(LONG_DATE_FORMAT).format(d);
	}


	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm".
	 */
	public static String formatDateTime( Date d ) {
		if ( d == null ) { return ""; }
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}


	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm".
	 */
	public static String formatDateTime( long d ) {
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}


	/**
	 * convert date and time to String like format
	 */
	public static String formatDateTime( String format, Date date ) {
		return new SimpleDateFormat(format).format(date);
	}


	/**
	 * Convert date to String like "yyyy-MM-dd".
	 */
	public static String formatDate( Date d ) {
		if ( d == null ) { return ""; }
		return new SimpleDateFormat(DATE_FORMAT_DAY).format(d);
	}


	/**
	 * Parse date like "yyyy-MM-dd".
	 */
	public static Date parseDate( String d ) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(d);
		} catch ( Exception e ) {}
		return null;
	}


	/**
	 * Parse date like "yyyy-MM-dd".
	 */
	public static Date parseyyyyMMddDate( String d ) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(d);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Parse date and time like "yyyy-MM-dd hh:mm".
	 */
	public static Date parseDateTime( String dt ) {
		try {
			return new SimpleDateFormat(DATETIME_FORMAT).parse(dt);
		} catch ( Exception e ) {}
		return null;
	}


	// -----------------获取指定日期的年份，月份，日份，小时，分，秒，毫秒----------------------------

	/**
	 * 获取指定日期的年份
	 *
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date: 2012-03-07
	 */
	public static int getYearOfDate( Date p_date ) {
		if ( null != p_date ) {
			Calendar c = Calendar.getInstance();
			c.setTime(p_date);
			return c.get(Calendar.YEAR);
		}
		return -1;
	}


	/**
	 * 获取指定日期的月份
	 *
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date: 2012-03-07
	 */
	public static int getMonthOfDate( Date p_date ) {
		if ( null != p_date ) {
			Calendar c = Calendar.getInstance();
			c.setTime(p_date);
			return c.get(Calendar.MONTH) + 1;
		}
		return -1;
	}


	/**
	 * 获取指定日期的日份
	 *
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date: 2012-03-07
	 */
	public static int getDayOfDate( Date p_date ) {
		if ( null != p_date ) {
			Calendar c = Calendar.getInstance();
			c.setTime(p_date);
			return c.get(Calendar.DAY_OF_MONTH);
		}
		return -1;
	}


	/**
	 * 获取指定日期的小时
	 *
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date: 2012-03-07
	 */
	public static int getHourOfDate( Date p_date ) {
		if ( null != p_date ) {
			Calendar c = Calendar.getInstance();
			c.setTime(p_date);
			return c.get(Calendar.HOUR_OF_DAY);
		}
		return -1;
	}


	/**
	 * 获取指定日期的
	 *
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date: 2012-03-07
	 */
	public static int getMinuteOfDate( Date p_date ) {
		if ( null != p_date ) {
			Calendar c = Calendar.getInstance();
			c.setTime(p_date);
			return c.get(Calendar.MINUTE);
		}
		return -1;
	}


	/**
	 * 获取指定日期的秒钟
	 *
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date: 2012-03-07
	 */
	public static int getSecondOfDate( Date p_date ) {
		if ( null != p_date ) {
			Calendar c = Calendar.getInstance();
			c.setTime(p_date);
			return c.get(Calendar.SECOND);
		}
		return -1;
	}


	/**
	 * 获取指定日期的毫秒
	 *
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date: 2012-03-07
	 */
	public static long getMillisOfDate( Date p_date ) {
		if ( null != p_date ) {
			Calendar c = Calendar.getInstance();
			c.setTime(p_date);
			return c.getTimeInMillis();
		}
		return -1;
	}


	/**
	 * 根据日期获取年龄
	 *
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge( Date birthDay ) throws Exception {
		Calendar cal = Calendar.getInstance();

		if ( cal.before(birthDay) ) { throw new IllegalArgumentException(
				"The birthDay is before Now.It's unbelievable!"); }

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if ( monthNow <= monthBirth ) {
			if ( monthNow == monthBirth ) {
				// monthNow==monthBirth
				if ( dayOfMonthNow < dayOfMonthBirth ) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}

		return age;
	}


	/**
	 * Java 毫秒转换为（天：时）方法
	 *
	 * @param ms
	 * @return
	 */
	@SuppressWarnings( "hiding" )
	public static String formatDayHour( long ms, int validDay ) {
		int temp = (int) (ms / (60 * 60 * 1000));
		int day = 0;
		int hour = 0;
		if ( temp % 24 == 0 ) {
			day = (validDay * 24 - temp) / 24;
			hour = 0;
		} else {
			day = (validDay * 24 - temp) / 24;
			if ( temp < 24 ) {
				hour = 24 - temp;
			} else {
				hour = 24 - temp % 24;
			}
		}
		return new StringBuffer().append(day).append("天").append(hour).append("小时").toString();
	}


	/**
	 * 根据日期获取生肖
	 *
	 * @return
	 */
	public static String date2Zodica( Calendar time ) {
		return zodiacArr[time.get(Calendar.YEAR) % 12];
	}


	/**
	 * 根据日期获取星座
	 *
	 * @param time
	 * @return
	 */
	@SuppressWarnings( "hiding" )
	public static String date2Constellation( Calendar time ) {
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if ( day < constellationEdgeDay[month] ) {
			month = month - 1;
		}
		if ( month >= 0 ) { return constellationArr[month]; }
		// default to return 魔羯
		return constellationArr[11];
	}


	/**
	 * 获取当前java.sql.Timestamp
	 *
	 * @return 当前java.sql.Timestamp
	 * @see Date
	 */
	public final static Timestamp nowDateTime() {
		return new Timestamp(new Date().getTime());
	}


	/**
	 * 获取当前java.sql.Timestamp
	 *
	 * @return 当前java.sql.Timestamp
	 * @see Date
	 */
	public final static Date nowDate() {
		return new Date();
	}


	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween( Date smdate, Date bdate ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newSmdate = null;
		Date newBdate = null;
		try {
			newSmdate = sdf.parse(sdf.format(smdate));
			newBdate = sdf.parse(sdf.format(bdate));
		} catch ( Exception e ) {
			logger.error(e.getMessage(), e);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(newSmdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(newBdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}


	/**
	 *
	 * 比较两个字符串时间 之间相差的天数
	 * <p>
	 *
	 * @param smdate
	 *            小
	 * @param bdate
	 *            大
	 * @return
	 */
	public static int daysBetweenStr( String smdate, String bdate ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newSmdate = null;
		Date newBdate = null;
		try {
			newSmdate = sdf.parse(smdate);
			newBdate = sdf.parse(bdate);
		} catch ( Exception e ) {
			logger.error(e.getMessage(), e);
		}
		return daysBetween(newSmdate, newBdate);
	}


	/**
	 *
	 * 比较传入日期是否已过期(与服务器当前日期比较)
	 *
	 * @param inputDate
	 *            传入日期
	 * @return true 未过期(即传入日期大于当前日期)
	 * @return false 已过期(即传入日期小于等于当前日期)
	 */
	public static Boolean isOverdate( Date inputDate ) {
		Date nowDate = new Date();
		return nowDate.before(inputDate);
	}


	/**
	 *
	 * 返回一个日期加上几个月后的时间
	 *
	 * @param inputDate
	 *            传入日期
	 */
	public static Date getDatePlusMonths( Date inputDate, int monthNum ) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inputDate);
		calendar.add(Calendar.MONTH, monthNum);
		return calendar.getTime();
	}


	/**
	 * 返回一个日期加上几天后的日期
	 * 
	 * @param inputDate
	 *            传入的日期
	 * @param dayNum
	 *            增加的天数
	 */
	public static Date getDatePlusDays( Date inputDate, int dayNum ) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inputDate);
		calendar.add(Calendar.DAY_OF_WEEK, dayNum);
		return calendar.getTime();
	}


	/**
	 * 返回两个日期相差月数(不足一月的不算)
	 *
	 * @param date1
	 * @param date2
	 * @return 相差月数
	 */
	public static int getBetweenMonth( Date date1, Date date2 ) {

		SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal1.setTime(date1);
		cal2.setTime(date2);

		int yearBetween = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		int monthBetween = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		int d1 = Integer.parseInt(sdf2.format(cal1.getTime()));
		int d2 = Integer.parseInt(sdf2.format(cal2.getTime()));
		int monthMax = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
		if ( d1 > monthMax ) {
			d1 = monthMax;
		}
		if ( d1 > d2 ) {
			monthBetween -= 1;
		}
		return 12 * yearBetween + monthBetween;
	}


	/**
	 * @Description 获取当前中国时区的TIMESTAMP日期
	 * @return
	 */
	public static Timestamp getSysTimestamp() {
		final TimeZone zone = TimeZone.getTimeZone("GMT+8");// 获取中国时区
		TimeZone.setDefault(zone);// 设置时区
		return new Timestamp(new Date().getTime());
	}


	/**
	 * 格式日期为字符串内容
	 * <p>
	 *
	 * @param date
	 *            时间
	 * @param pattern
	 *            日期格式,例： yyyyMMddHHmmss
	 * @return String 格式后的字符串日期
	 */
	public static String formatDate( Date date, String pattern ) {
		if ( date == null ) { return null; }
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}


	/**
	 * 格式long类型日期为 Date
	 * <p>
	 *
	 * @param time
	 *            long类型日期
	 * @return Date
	 */
	public static Date formatDate( long time ) {
		return new Date(time);
	}


	public static String formateDateStr( Long time ) {
		return formatDate(formatDate(time), DATETIME_FORMAT);
	}


	public static String timeStampUrl( String url ) {
		if ( url != null ) { return url + "?t=" + System.currentTimeMillis(); }
		return "";
	}


	/**
	 * 以天为单位改变当前日期
	 * 
	 * @param dayNum
	 *            增加或减少的天数
	 * @param op
	 *            操作,有"+"和"-"两项
	 * @return 增加后的日期
	 */
	public static Date changeDate( Integer dayNum, String op ) {
		if ( dayNum == null ) { return null; }
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + ("+".equals(op) ? dayNum : -dayNum));
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch ( ParseException e ) {
			e.printStackTrace();

		}
		return endDate;
	}


	/**
	 * 以小时为单位改变传入的日期
	 * 
	 * @param date
	 *            被改变的日期
	 * @param hour
	 *            增加或减少的小时数
	 * @param op
	 *            操作,有"+"和"-"两项
	 * @return 返回改后变的时间
	 */
	public static Date changeDateByHour( Date date, Integer hourNum, String op ) {
		if ( date == null ) { return null; }
		if ( hourNum == null || hourNum == 0 ) { return date; }
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, (cal.get(Calendar.HOUR) + ("+".equals(op) ? hourNum : -hourNum)));
		Date after = null;
		try {
			after = dft.parse(dft.format(cal.getTime()));
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return after;
	}


	public static boolean isValidDate( String str ) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_DAY);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch ( ParseException e ) {
			e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}


	/**
	 * 根据传入的数字和日期格式返回本周内的周几所代表的日期
	 * 
	 * @param index
	 *            周几的序号
	 * @param format
	 *            日期格式 return 返回本周内的星期几
	 */
	public static String dateOfWeek( int index, String format ) {
		Calendar calendar = Calendar.getInstance();
		while ( calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY ) {
			calendar.add(Calendar.DATE, -1);
		}
		calendar.add(Calendar.DATE, index - 1);
		return new SimpleDateFormat(format).format(calendar.getTime());
	}


	// 获得当前周- 周一的日期
	public static String getCurrentMonday() {
		int mondayPlus = getMondayPlus();
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String[] mondays = df.format(monday).split("-");
		String month = mondays[1].length() == 1 ? "0" + mondays[1] : mondays[1];
		String day = mondays[2].length() == 1 ? "0" + mondays[2] : mondays[2];
		String preMonday = mondays[0] + "-" + month + "-" + day;
		return preMonday;
	}


	// 获得当前周- 周日 的日期
	public static String getPreviousSunday() {
		int mondayPlus = getMondayPlus();
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String[] mondays = df.format(monday).split("-");
		String month = mondays[1].length() == 1 ? "0" + mondays[1] : mondays[1];
		String day = mondays[2].length() == 1 ? "0" + mondays[2] : mondays[2];
		String preMonday = mondays[0] + "-" + month + "-" + day;
		return preMonday;
	}


	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DAY);


	// 获得当前月--开始日期
	public static String getMinMonthDate() {
		Calendar calendar = Calendar.getInstance();
		try {
			Date theDate = calendar.getTime();
			GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
			gcLast.setTime(theDate);
			// 设置为第一天
			gcLast.set(Calendar.DAY_OF_MONTH, 1);
			return dateFormat.format(gcLast.getTime());
		} catch ( Exception e ) {
			e.printStackTrace();
		}


		return null;
	}


	// 获得当前月--结束日期
	public static String getMaxMonthDate() {
		Calendar calendar = Calendar.getInstance();
		try {
			// 设置日期为本月最大日期
			calendar.set(Calendar.DATE, calendar.getActualMaximum(calendar.DATE));
			return dateFormat.format(calendar.getTime());
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}


	// 获得当前日期与本周一相差的天数
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if ( dayOfWeek == 1 ) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}


	/**
	 * 获取某月的最后一天
	 * 
	 * @param:@param year
	 * @param:@param month
	 * @return:String
	 */
	public static String getLastDayOfMonth( int year, int month ) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}


	/**
	 * 获取两个日期(不包含当日，日要减1:例如：2016-05-02开始 ， 需改成2016-05-01 开始) 区间多少个 周几
	 * */
	public static Map getMondayCount( String from, String to, int day ) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		Calendar calendar = Calendar.getInstance();
		String[] array = { from, to };
		Date[] ds = new Date[array.length];
		for ( int i = 0 ; i < array.length ; i++ ) {
			String[] fs = array[i].split("[^\\d]+");
			calendar.set(Integer.parseInt(fs[0]), Integer.parseInt(fs[1]) - 1, Integer.parseInt(fs[2]));
			ds[i] = calendar.getTime();
		}
		int count = 0;
		for ( Date x = ds[0] ; x.compareTo(ds[1]) <= 0 ; ) {
			calendar.setTime(x);
			calendar.add(Calendar.DATE, 1);
			x = calendar.getTime();
			if ( calendar.get(Calendar.DAY_OF_WEEK) == day ) {
				String date = DateUtil.formatDate(x, "yyyy-MM-dd");
				count++;
				map.put(count, date);
			}
		}
		System.out.println(map);
		return map;
	}


	/**
	 * 根据传入的数字返回当月第一天或当月最后一天
	 * 
	 * @param type
	 *            0：第一天，1:最后一天
	 * @param format
	 *            日期格式 return yyyy-MM-dd
	 */
	public static Date getMouthDate( int type ) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 获取当前月第一天：
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
			// 获取当前月最后一天
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			if ( type == 0 ) {
				return df.parse(format.format(c.getTime()));
			} else if ( type == 1 ) { return df.parse(format.format(ca.getTime())); }
			return df.parse(format.format(new Date()));
		} catch ( ParseException e ) {
		
		}
		return null;
	}


	/**
	 * 返回日期是星期几 文字星期几
	 * <p>
	 *
	 * @param dt
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public static String getWeeks( Date dt ) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if ( w < 0 ) {
			w = 0;
		}

		return weeks[w];
	}


	/**
	 * 根据传入的数字返回周一或周日的日期
	 * 
	 * @param day1
	 *            获取周一:0; 周日:6; 上周一:-7; 上周日:-1; 下周一:7;下周日:13
	 * @param format
	 *            日期格式 return yyyy-MM-dd
	 * 
	 */


	public static Date getWeekDate( int day1 ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		String newDate = sdf.format(new Date());// 当前日期
		Date time;
		try {
			time = sdf.parse(newDate);
			cal.setTime(time);
			// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
			int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
			if ( 1 == dayWeek ) {
				cal.add(Calendar.DAY_OF_MONTH, -1);
			}
			cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
			cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - cal.get(Calendar.DAY_OF_WEEK));// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
			cal.add(Calendar.DATE, day1);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(sdf.format(cal.getTime()));
		} catch ( ParseException e ) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 获取指定日期 所在周的 周一 ： 周日
	 * */
	public static String convertWeekByDate( Date time ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if ( 1 == dayWeek ) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String imptimeBegin = sdf.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		return imptimeBegin + ":" + imptimeEnd;
	}


	/**
	 * 得到本周周一
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getMondayOfThisWeek() {
		Long millons = System.currentTimeMillis()
				/ (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
		Date date = new Date(millons);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, -1); // 解决周日会出现 并到下一周的情况
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}


	/**
	 * 得到下周周一0点0秒
	 */
	public static Date getNextMonday() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if ( week > 2 ) {
			cal.add(Calendar.DAY_OF_MONTH, -(week - 2) + 7);
		} else {
			cal.add(Calendar.DAY_OF_MONTH, 2 - week + 7);
		}
		long millions = cal.getTime().getTime()
				/ (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
		Date rDate = new Date(millions);
		return rDate;
	}


	/**
	 * 得到下周周日23点59秒
	 */
	public static Date getNextSunday() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if ( week > 1 ) {
			cal.add(Calendar.DAY_OF_MONTH, -(week - 1) + 7);
		} else {
			cal.add(Calendar.DAY_OF_MONTH, 1 - week + 7);
		}

		long millions = cal.getTime().getTime()
				/ (1000 * 3600 * 24) * (1000 * 3600 * 24) + 1000 * 3600 * 24 - TimeZone.getDefault().getRawOffset();
		Date rDate = new Date(millions);
		return rDate;
	}


	/**
	 * 得到本周周日
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if ( day_of_week == 0 ) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 7);
		Long millons = c.getTimeInMillis()
				/ (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset() + 1000 * 3600 * 24;
		Date date = new Date(millons);
		return date;
	}


	/**
	 * 得到本月第一天
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getFirstDayOfThisMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);

		Long millons = c.getTimeInMillis()
				/ (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
		Date date = new Date(millons);

		return date;
	}


	/**
	 * 得到本月最后一天
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getLastDayOfThisMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		Long millons = c.getTimeInMillis()
				/ (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset() + 1000 * 3600 * 24;
		Date date = new Date(millons);

		return date;
	}


	/**
	 * 给指定时间加上分钟
	 *
	 * @param inputDate		时间
	 * @param minuteNum		分钟数
	 * @return 
	 */
	public static Date getDatePlusMinute( Date inputDate, int minuteNum ) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inputDate);
		calendar.add(Calendar.MINUTE, minuteNum);
		return calendar.getTime();
	}


	/**
	 * 比较2个时间大小
	 *
	 * @param dt1
	 * @param dt2
	 * @return dt1>dt2返回1，dt1<dt2返回-1,相等返回1
	 */
	public static int compareDate( Date dt1, Date dt2 ) {
		try {
			if ( dt1.getTime() > dt2.getTime() ) {
				return 1;
			} else if ( dt1.getTime() < dt2.getTime() ) {
				return -1;
			} else {
				return 0;
			}
		} catch ( Exception exception ) {
			exception.printStackTrace();
		}
		return 0;
	}


	/**
	 * 返回文字描述的日期 该方法用来生成那种回复留言 或者帖子的回复时间显示 刚刚 几分钟前 等等
	 *
	 * @param date
	 * @return
	 */
	public static String getBeforeTimeFormatText( Date date ) {
		if ( date == null ) { return null; }
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if ( diff / minute > 30 ) {//
			return "30分钟前";
		}
		if ( diff > minute ) {
			r = diff / minute;
			return r + "分钟前";
		}
		return "刚刚";
	}


	/**
	 * 第一个时间是否在第二个之后
	 * <p>
	 * 只比较到天
	 *
	 * @param date1
	 * @param date2
	 */
	public static Boolean isAfterForDay( Date date1, Date date2 ) {
		if ( date1 == null || date2 == null ) { return null; }
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(format.format(date1)).after(format.parse(format.format(date2)));
		} catch ( ParseException e ) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 以天为单位改变当前日期
	 * 
	 * @param dayNum
	 *            增加或减少的天数
	 * @param op
	 *            操作,有"+"和"-"两项
	 * @return 增加后的日期 
	 */
	public static Date changeDateTime( Integer dayNum, String op ) {
		if ( dayNum == null ) { return null; }
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + ("+".equals(op) ? dayNum : -dayNum));
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch ( ParseException e ) {
			e.printStackTrace();

		}
		return endDate;
	}


	/**
	 * 给指定时间减去分钟
	 *
	 * @param inputDate		时间
	 * @param minuteNum		分钟数
	 * @return 
	 */
	public static Date getDateSubMinute( Date inputDate, int minuteNum ) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inputDate);

		int now = calendar.get(Calendar.MINUTE);
		calendar.set(calendar.MINUTE, now - minuteNum);
		return calendar.getTime();
	}
	/**
	 * 给指定时间减去小时
	 *
	 * @param inputDate		时间
	 * @param minuteNum		分钟数
	 * @return 
	 */
	public static Date getDateSubHour( Date inputDate, int hourNum ) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inputDate);

		int now = calendar.get(Calendar.HOUR);
		calendar.set(calendar.HOUR, now - hourNum);
		return calendar.getTime();
	}
	
	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm:sss".
	 */
	public static String formatDateMilliSecond( Date d ) {
		if ( d == null ) { return ""; }
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(d);
	}
	
		// date类型转换为String类型
	 	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 	// data Date类型的时间
	 	public static String dateToString(Date data, String formatType) {
	 		return new SimpleDateFormat(formatType).format(data);
	 	}
	 
	 	// long类型转换为String类型
	 	// currentTime要转换的long类型的时间
	 	// formatType要转换的string类型的时间格式
	 	public static String longToString(long currentTime, String formatType)
	 			throws ParseException {
	 		Date date = longToDate(currentTime, formatType); // long类型转成Date类型
	 		String strTime = dateToString(date, formatType); // date类型转成String
	 		return strTime;
	 	}
	 
	 	// string类型转换为date类型
	 	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	 	// HH时mm分ss秒，
	 	// strTime的时间格式必须要与formatType的时间格式相同
	 	public static Date stringToDate(String strTime, String formatType)
	 			throws ParseException {
	 		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
	 		Date date = null;
	 		date = formatter.parse(strTime);
	 		return date;
	 	}
	 
	 	// long转换为Date类型
	 	// currentTime要转换的long类型的时间
	 	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 	public static Date longToDate(long currentTime, String formatType)
	 			throws ParseException {
	 		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
	 		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
	 		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
	 		return date;
	 	}
	 
	 	// string类型转换为long类型
	 	// strTime要转换的String类型的时间
	 	// formatType时间格式
	 	// strTime的时间格式和formatType的时间格式必须相同
	 	public static long stringToLong(String strTime, String formatType)
	 			throws ParseException {
	 		Date date = stringToDate(strTime, formatType); // String类型转成date类型
	 		if (date == null) {
	 			return 0;
	 		} else {
	 			long currentTime = dateToLong(date); // date类型转成long类型
	 			return currentTime;
	 		}
	 	}
	 
	 	// date类型转换为long类型
	 	// date要转换的date类型的时间
	 	public static long dateToLong(Date date) {
	 		return date.getTime();
	 	}

	public static void main( String args[] ) throws Exception {

		// System.out.println(compareDate(DateUtil.parseyyyyMMddDate("2016-11-1"),
		// new Date()));
		// String date = getWeekOfDate(new Date());
		// System.out.println(date);
		// System.out.println(daysBetweenStr("2016-05-16", "2016-05-31"));
		// Date date = new Date();
		// Date after = changeDateByHour(date, 2, "+");
		// System.out.println(DateUtil.formatDate(after,
		// "yyyy-MM-dd HH:mm:ss"));
		// System.out.println(dateOfWeek(7, "yyyy-MM-dd"));

		// Date date = new Date();
		// Date d2 = getDatePlusMinute(date, 5);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// System.out.println(sdf.format(date));
		// System.out.println(sdf.format(d2));

		// System.out.println(getBetweenMonth(new Date(), new Date()));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


		// int range = DateUtil.daysBetween(sdf.parse("2016-11-02 04:30:06"),
		// new Date());
		// System.out.println(range);

		// System.out.println(getTimeFormatText(DateUtil.parseDateTime("2016-10-28 23:24:00")));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = format.parse("2016-11-14 13:10:44");
		Date date2 = format.parse("2016-11-14 18:10:44");
		System.out.println(isAfterForDay(date1, date2));


		// System.out.println(sdf.format(changeDateTime(1, "-")));
		System.out.println(sdf.format(new Date()));
		System.out.println(sdf.format(getDateSubMinute(new Date(), 5)));
		System.out.println("========================================");
		System.out.println(formatDate(changeDate(1, "-")));

	}
}
