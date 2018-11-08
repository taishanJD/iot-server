package com.quarkdata.data.util;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 切割时间段类
 * @author huliang
 * @date 1/31/18
 *
 */
public class DateCalculate {
	 
	  /**
	   * 切割时间段
	   *
	   * @param dateType 交易类型 M/D/H/N -->每月/每天/每小时/每分钟
	   * @param start yyyy-MM-dd HH:mm:ss
	   * @param end  yyyy-MM-dd HH:mm:ss
	   * @return
	   */
	  public static Set<String> cutDate(String dateType, String start, String end) {
	    try {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      Date dBegin = sdf.parse(start);
	      Date dEnd = sdf.parse(end);
	      return findDates(dateType, dBegin, dEnd);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }
	 
	  public static Set<String> findDates(String dateType, Date dBegin, Date dEnd) throws Exception {
	  	Set<String> listDate = new HashSet<>();
	    Calendar calBegin = Calendar.getInstance();
	    calBegin.setTime(dBegin);
	    Calendar calEnd = Calendar.getInstance();
	    calEnd.setTime(dEnd);
	    // 起始时间闭区间，结束时间闭区间
	    listDate.add(new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime()));
	    while (calEnd.after(calBegin)) {
	      switch (dateType) {
	        case "M":
	          calBegin.add(Calendar.MONTH, 1);
	          break;
	        case "D":
	          calBegin.add(Calendar.DAY_OF_YEAR, 1);break;
	        case "H":
	          calBegin.add(Calendar.HOUR, 1);break;
	        case "N":
	          calBegin.add(Calendar.SECOND, 1);break;
	      }
	      if (calEnd.after(calBegin))
	        listDate.add(new SimpleDateFormat("yyyy-MM-dd").format(calBegin.getTime()));
	      else
	        listDate.add(new SimpleDateFormat("yyyy-MM-dd").format(calEnd.getTime()));
	    }
	    return listDate;
	  }
	 
	 
	  public static void main(String[] args) {
	    String start = "2016-02-01 21:35:42";
	    String end = "2016-02-02 23:48:50";
	    Set<String> list = cutDate("D", start, end);
	    for (String str :list){
	      System.out.println(str);
	    }
	  }
}
