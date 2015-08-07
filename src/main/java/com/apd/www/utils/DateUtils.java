package com.apd.www.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Liuhong on 2015/7/31.
 */
public class DateUtils {
    static SimpleDateFormat sdfshot = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat sdfLongMonthCn = new SimpleDateFormat("yyyy-MM");

    public static String getDateLong(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfLong.format(date);
            return nowDate;
        } catch (Exception e) {
            return "";
        }
    }
    public static Date getLastMonthFirstDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = sdfshot.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();
        Date first_day=null;
        try {
            first_day = sdfLong.parse(day_first);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return first_day;
    }


    public static Date getLastMonthLastDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = sdfshot.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();
        Date last_day=null;
        try {
            last_day = sdfLong.parse(day_last);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  last_day;
    }


    public static Date addDays(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)) - 1,
                    Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.DATE, i);
            return gCal.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public static String addMonths(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)) - 1,
                    Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.MONTH, i);
            return sdfLong.format(gCal.getTime());
        } catch (Exception e) {
            return "";
        }
    }

}
