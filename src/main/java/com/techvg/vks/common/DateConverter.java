package com.techvg.vks.common;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    private DateConverter(){}

    public static Date getDate(String strDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate transDate1 = LocalDate.parse(strDate, formatter);
        Instant instant = transDate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static int dayTimeDiff(LocalDate startDate, LocalDate endDate){

        int endDay = endDate.getDayOfMonth();
        int startDay = startDate.getDayOfMonth();
        int diffTillDate = endDay - startDay;
        return diffTillDate;
    }

    public static int getDayOfMonth(Date aDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int dayDiff(Date startDate, Date endDate){

        long endTime = endDate.getTime();
        long startTime = startDate.getTime();

        long diffTillDate = endTime - startTime;
        return (int) (diffTillDate / (24 * 60 * 60 * 1000));
    }

    public static Date incrementDateByDays(int daysCount, Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, daysCount);
        return cal.getTime();
    }

    public static Date incrementDateByMonths(int monthCount, Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthCount);
        return cal.getTime();
    }

    public static Timestamp getTimestamp(Date date, boolean dayStart){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        getDay(cal,0,dayStart);
        return new Timestamp(cal.getTimeInMillis());
    }

    public static Timestamp firstDayOfMonth(boolean start){
        Calendar cal = Calendar.getInstance();
        int firstDay = cal.getActualMinimum(Calendar.DATE);
        getDay(cal,firstDay,start);
        return new Timestamp(cal.getTimeInMillis());
    }

    public static Timestamp lastDayOfMonth(boolean start){
        Calendar cal = Calendar.getInstance();
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        getDay(cal,lastDay,start);
        return new Timestamp(cal.getTimeInMillis());
    }

    private static Calendar getDay(Calendar cal, int day, boolean start){
        if(day!=0)
            cal.set(Calendar.DAY_OF_MONTH,day);
        if(start){
            cal.set(Calendar.HOUR, 00);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.MILLISECOND, 00);
        }else{
            cal.set(Calendar.HOUR, 12);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.MILLISECOND, 59);
        }
        return cal;
    }
}
