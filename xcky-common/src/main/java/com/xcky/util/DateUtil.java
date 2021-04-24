package com.xcky.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author lbchen
 */
public class DateUtil {
    /**
     * 日期时间格式: 年-月-日 时:分:秒
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期时间格式: yyyyMMdd
     */
    public static final String DATE_PATTERN = "yyyyMMdd";
    /**
     * 日期时间格式: yyyy年MM月dd日
     */
    public static final String DATE_CHAR_PATTERN = "yyyy年MM月dd日";
    /**
     * 日期时间格式: 年月日时分秒
     */
    public static final String DATE_TIME_SIMPLE_PATTERN = "yyyyMMddHHmmss";
    /**
     * 日期时间格式:年-月-日T时:分:秒+0800<br>
     * 如:2021-01-02T21:11:58+0800
     */
    public static final String DATE_TIME_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * 将日期对象转换成字符串【非线程安全】
     *
     * @param date 日期对象
     * @return 转换成"yyyy-MM-dd HH:mm:ss"格式的日期字符串
     */
    public static String getTimeStrByDate(Date date) {
        return getTimeStrByDate(date, DATE_TIME_PATTERN);
    }

    /**
     * 将日期对象转换成字符串【非线程安全】
     *
     * @param date 日期对象
     * @return 转换成<code>pattern</code>格式的日期字符串
     * @Param pattern 日期格式
     */
    public static String getTimeStrByDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 日期转化成字符串【线程安全】
     *
     * @param localDateTime 日期时间对象
     * @return 转换成<code>yyyy-MM-dd HH:mm:ss</code>格式的日期字符串
     */
    public static String getStrByLocalDateTime(LocalDateTime localDateTime) {
        return getStrByLocalDateTime(localDateTime, DATE_TIME_PATTERN);
    }

    /**
     * 日期转化成字符串【线程安全】
     *
     * @param localDateTime 日期时间对象
     * @param pattern       日期格式
     * @return 转换成<code>pattern</code>格式的日期字符串
     */
    public static String getStrByLocalDateTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * 获取当前时间戳，单位秒
     *
     * @return 时间戳数字(秒)
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     *
     * @return 时间戳数字(毫秒)
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }

}
