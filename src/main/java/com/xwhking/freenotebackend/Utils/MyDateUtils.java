package com.xwhking.freenotebackend.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 计算两个日期相差的天数
 */
public class MyDateUtils {
    /**
     * 两个日期相距天数
     * @param small
     * @param big
     * @return
     */
    public static Long getBetweenDays(LocalDate small,LocalDate big){
        return ChronoUnit.DAYS.between(small, big);
    }

    /**
     * 两个日期相距月数
     * @param small
     * @param big
     * @return
     */
    public static Long getBetweenMonths(LocalDate small,LocalDate big){
        return ChronoUnit.MONTHS.between(small,big);
    }

    /**
     * 相距年数年数没有大小
     * @param small
     * @param big
     * @return
     */
    public static Long getBetweenYears(LocalDate small,LocalDate big){
        return Math.abs(ChronoUnit.YEARS.between(small,big));
    }

    /**
     * 格式化日期类输出
     * @param date
     * @return
     */
    public static String getFormatDate(LocalDate date){
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 格式化日期
        return date.format(formatter);
    }

    public static void main(String[] args) {
        LocalDate date1 = LocalDate.parse("2023-08-15");
        LocalDate date2 = LocalDate.parse("2022-08-15");
        System.out.println(getBetweenDays(date2,date1));
        System.out.println(getBetweenMonths(date1,date2));
        System.out.println(getBetweenYears(date2,date1));
        System.out.println(getFormatDate(LocalDate.now()));
    }
}
