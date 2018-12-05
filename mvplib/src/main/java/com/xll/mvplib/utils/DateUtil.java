package com.xll.mvplib.utils;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间操作工具类
 *
 * @author xll
 * @date 2018/1/1
 */
public class DateUtil {
    private static final String TAG = DateUtil.class.getSimpleName();
    private static final String TIME_YM = "yyyyMM";
    private static final String TIME_YMD = "yyyyMMdd";
    private static final String TIME_YMD2 = "yyyy-MM-dd";
    private static final String TIME_YMDL = "yyyy/MM/dd";
    private static final String TIME_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_YMDHMS_WITHOUT_FORMAT = "yyyyMMddHHmmss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMDddHHmmss", Locale.getDefault());

    public static String getCurrentTimeWithoutFormat(){
        simpleDateFormat.applyPattern(TIME_YMDHMS_WITHOUT_FORMAT);
        return simpleDateFormat.format(new Date());
    }

    public static String getCurrentTimeYM(){
        simpleDateFormat.applyPattern(TIME_YM);
        return simpleDateFormat.format(new Date());
    }

    public static String formatTimeYM(String time){
        simpleDateFormat.applyPattern(TIME_YM);
        if (StringUtil.isStringNull(time) || time.length() < 6){
            return "";
        }
        Date date;//解析字符串，转化为Date
        String t = time.substring(0, 6);
        try {
            date = simpleDateFormat.parse(t);
            return simpleDateFormat.format(date);//格式化String类型
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getCurrentTimeYMD(){
        simpleDateFormat.applyPattern(TIME_YMD);
        return simpleDateFormat.format(new Date());
    }

    public static String getCurrentTimeYMDHMS(){
        simpleDateFormat.applyPattern(TIME_YMDHMS);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 格式化时间为 年月日 时分秒
     * @param time
     * @return
     */
    public static String formatTimeYMDHMS(String time){
        if (StringUtil.isStringNull(time) || time.length() < 14){
            return time;
        }
        Date date;//解析字符串，转化为Date
        String t = time.substring(0, 14);
        try {
            simpleDateFormat.applyPattern(TIME_YMDHMS_WITHOUT_FORMAT);
            date = simpleDateFormat.parse(t);
            simpleDateFormat.applyPattern(TIME_YMDHMS);
            return simpleDateFormat.format(date);//格式化String类型
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取time与当前时间之间的年数
     */
    public static int getYearsBetweenNow(String time){
        if (StringUtil.isStringNull(time)){
            return -1;
        }
        simpleDateFormat.applyPattern(TIME_YMD);
        try {
            Date date = simpleDateFormat.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());//当前时间
            int nowYear = calendar.get(Calendar.YEAR);
            int nowDay = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(date); //传过来的时间
            int oldYear = calendar.get(Calendar.YEAR);
            int oldDay = calendar.get(Calendar.DAY_OF_YEAR);
            int years = nowYear - oldYear;
            if (nowDay - oldDay >= 0){
                years = years + 1;
            }
            return years;
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.t(TAG).i(e.getMessage());
        }
        return 0;
    }

    /**
     * 得到当前时间的几个月前或几个月后时间点
     * @param months -1 1个月前  1 1个月后
     * @return eg：2017-2-23 8:37:50
     */
    public static String getTimeBeforeOrAfterMonths(int months){
        simpleDateFormat.applyPattern(TIME_YMDHMS);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, months);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 得到当前时间的几天前或几天后时间点
     * @param days
     * @return
     */
    public static String getTimeBeforeOrAfterDays(int days){
        simpleDateFormat.applyPattern(TIME_YMDHMS);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * Make the date format yyyy/MM/dd
     * 将时间格式化为 年/月/日
     *
     * @param str the string to be formatted
     *            被格式化的字符串
     * @return yyyy/MM/dd
     */
    public static String dateFormatYMDL(String str) {
        if (StringUtil.isStringNull(str)){
            return "";
        }
        String time = str;
        Date date;//解析字符串，转化为Date
        if (str.length() == 8){
            simpleDateFormat.applyPattern(TIME_YMD);
        }else if (str.length() >= 10){
            time = str.substring(0, 10);
            simpleDateFormat.applyPattern(TIME_YMD2);
        }
        try {
            date = simpleDateFormat.parse(time);
            simpleDateFormat.applyPattern(TIME_YMDL);
            return simpleDateFormat.format(date);//格式化String类型
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param month 获得当前日期的前几个月的日期
     * @return yyyyMMdd
     */
    public static String getDateBeforeOrAfterMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        simpleDateFormat.applyPattern(TIME_YMD);
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @param day 获得当前日期的前几天或几天后
     * @return yyyyMMdd
     */
    public static String getDateBeforeOrAfterDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        simpleDateFormat.applyPattern(TIME_YMD);
        return simpleDateFormat.format(date);
    }
}
