package com.example.shiva.alarmtut;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Javier on 25/01/2018.
 */

public class DateUtil {
    public static String formatDate1 = "yyyy-MM-dd kk:mm:ss";
    public static String formatDate2 = "dd/MM/yyyy hh:mm a";

    public static String defaultDate = ".. / .. / ....";
    public static String defaultTime = ".. : ..";
    public static String defaultDuration = ".. : .. : ..";

    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String formatted = sdf.format(date);
        return formatted.substring(0, 23) + "Z";
    }

    public static Calendar toCalendar(final String iso8601string) throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "-0000");
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(s);
        calendar.setTime(date);
        return calendar;
    }

    public static String convertDate(String dateString, String formatDate,
                                     String formatConvert) throws ParseException {
        Date date = new SimpleDateFormat(formatDate).parse(dateString);
        SimpleDateFormat sdf = new SimpleDateFormat(formatConvert);
        return sdf.format(date);
    }

    public static String convertDate(Date date, String formatConvert){
        SimpleDateFormat sdf = new SimpleDateFormat(formatConvert);
        return sdf.format(date);
    }

    public static String getLocalTimezone(){
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }
}
