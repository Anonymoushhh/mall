package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by 帅虎的电脑 on 2019/1/24.
 */
public class DateTimeUtil {
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    //joda-time
    public static Date strToDate(String dateTimeStr){//"yyyy-MM-dd HH:mm:ss"
        org.joda.time.format.DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
    public static String dateToStr(Date date){
        if(date == null)
            return StringUtils.EMPTY;
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }
    public static Date strToDate(String dateTimeStr,String formatStr){//"yyyy-MM-dd HH:mm:ss"
        org.joda.time.format.DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
    public static String dateToStr(Date date,String formatStr){
        if(date == null)
            return StringUtils.EMPTY;
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }
}
