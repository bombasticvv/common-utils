package com.utils.part1.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 采用ThreadLocal实现到dateFormat
 */
public class DateUtil {

    private static ThreadLocal<SimpleDateFormat> DEFALUT_SDF = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmssSSS");
        }
    };
    private static ThreadLocal<SimpleDateFormat> YMDHMS_SDF = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> Y_M_D_H_M_S_SDF = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> Y_M_D_SDF = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> YMDHM_SDF = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmm");
        }
    };

    public static String getNow() {
        Calendar ca = Calendar.getInstance();
        return DEFALUT_SDF.get().format(ca.getTime());
    }

    public static String getTimeString(Date date) {
        return YMDHMS_SDF.get().format(date);
    }

    public static String getDateString(Date date) {
        return Y_M_D_SDF.get().format(date);
    }

    public static Date getTime(String date) {
        try {
            return YMDHMS_SDF.get().parse(date);
        } catch (ParseException e) {
           e.printStackTrace();
           return null;
        }
    }

    public static String getDateStringWhole(Date date) {
        return Y_M_D_H_M_S_SDF.get().format(date);
    }

    public static boolean isYMDHMDate(String date) {
        try {
            YMDHM_SDF.get().parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isYMDHMSDate(String date) {
        try {
            YMDHMS_SDF.get().parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static String getYesterday(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH) - 1);
        return Y_M_D_SDF.get().format(ca.getTime());
    }
    
    public static void main(String[] args) throws Exception {
        Date date = Y_M_D_SDF.get().parse("2017-08-01");
        System.out.println(DateUtil.getYesterday(date));
    }
}
