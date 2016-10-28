package com.tctvn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private final static String FORMAT_YYYYMMDD = "yyyy/MM/dd";

    public static Date stringToDate(String string) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_YYYYMMDD);
        try {
            Date date = formatter.parse(string);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToString(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDD);
            String stringDate = sdf.format(date);
            return stringDate;
        } else {
            return "";
        }
    }
}
