package cn.wanru.fund.util;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author xxf
 * @date 17-9-10
 */
public class DateUtil {

    private static final String pattern = "yyyy-MM-dd";

    private static final DateTimeFormatter formatter =
            DateTimeFormat.forPattern(pattern);

    private DateUtil() {

    }

    public static String toString(LocalDate date) {
        return date.toString(pattern);
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date,formatter);
    }
}
