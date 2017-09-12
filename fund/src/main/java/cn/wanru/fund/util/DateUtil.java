package cn.wanru.fund.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author xxf
 * @date 17-9-10
 */
public class DateUtil {

    private static final String date_pattern = "yyyy-MM-dd";

    private static final String date_time_pattern = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter date_formatter =
            DateTimeFormat.forPattern(date_pattern);

    private static final DateTimeFormatter date_time_formatter =
            DateTimeFormat.forPattern(date_time_pattern);


    private DateUtil() {

    }

    public static String toString(LocalDate date) {
        return date.toString(date_pattern);
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, date_formatter);
    }

    public static DateTime parseDateTime(String value) {
        return DateTime.parse(value,date_time_formatter);
    }
}
