package com.feicui365.live.util;



import com.feicui365.live.model.entity.MatchDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static long date2TimeStamp(String timeString) {
        long l = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d;
        try{
            d = sdf.parse(timeString);
            l = d.getTime();
        } catch(ParseException e){
            e.printStackTrace();
        }
        return l;
    }


    public static long date2TimeStamp2(String timeString) {
        long l = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try{
            d = sdf.parse(timeString);
            l = d.getTime()/1000L;
        } catch(ParseException e){
            e.printStackTrace();
        }
        return l;
    }


    public static String timeStamp2Minute(long timeStamp){


        return String.valueOf(timeStamp/(1000 * 60)/1000);
    }

    public static long javaTimeStamp2phpTimeStamp(long timeStamp){
        long javaTimeStamp=timeStamp*1000;
        return javaTimeStamp;
    }

    public static String timeToString(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date;
        try {
            date = sdf.parse(sdf.format(new Date(time)));
            //Date date = sdf.parse(sdf.format(new Long(s)));// 等价于
            return sdf.format(date);
        } catch (NumberFormatException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }

    public static String allTimeToString(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日   hh:mm");
        Date date;
        try {
            date = sdf.parse(sdf.format(new Date(time)));
            //Date date = sdf.parse(sdf.format(new Long(s)));// 等价于
            return sdf.format(date);
        } catch (NumberFormatException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }
    public static String allTimeToString2(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(sdf.format(new Date(time)));
            //Date date = sdf.parse(sdf.format(new Long(s)));// 等价于
            return sdf.format(date);
        } catch (NumberFormatException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }

    //获取过去一周时间
    public static ArrayList<MatchDate> pastDaysList(int intervals) {
        ArrayList<MatchDate> pastDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    //获取未来一周时间
    public static ArrayList<MatchDate> fetureDaysList(int intervals) {
        ArrayList<MatchDate> fetureDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }

    public static MatchDate getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        String uiDate = format.format(today);

        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
        String date = format2.format(today);


        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        MatchDate matchDate = new MatchDate();
        matchDate.setUiDate(uiDate);
        matchDate.setDate(date);
        matchDate.setWeek(weekDays[w]);

        return matchDate;
    }

    public static MatchDate getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        String uiDate = format.format(today);

        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
        String date = format2.format(today);


        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        MatchDate matchDate = new MatchDate();
        matchDate.setUiDate(uiDate);
        matchDate.setDate(date);
        matchDate.setWeek(weekDays[w]);

        return matchDate;
    }

    public static long CalTime2Minute(long  diff) {
        long minute=diff/1000/60;
        return minute;
    }

    public static long CalTime2Hour(long  diff) {
        long hour=diff/1000/60/60;
        return hour;
    }
    public static long timeDifference(long  diff) {
        long day = diff / (24 * 60 * 60 * 1000);
        long hour = (diff / (60 * 60 * 1000) - day * 24);
        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);;
        return min;
    }
}
